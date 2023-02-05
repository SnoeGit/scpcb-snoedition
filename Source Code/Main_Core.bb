Include "Source Code\Math_Core.bb"
Include "Source Code\Strict_Functions_Core.bb"

Type Fonts
	Field FontID%[MaxFontIDAmount]
End Type

Const MaxFontIDAmount% = 8
; ~ Fonts ID Constants
;[Block]
Const Font_Default% = 0
Const Font_Default_Big% = 1
Const Font_Digital% = 2
Const Font_Digital_Big% = 3
Const Font_Journal% = 4
Const Font_Console% = 5
Const Font_Credits% = 6
Const Font_Credits_Big% = 7
;[End Block]

Global fo.Fonts = New Fonts

Global ButtonSFX% = LoadSound_Strict("SFX\Interact\Button.ogg")
Global ButtonSFX2% = LoadSound_Strict("SFX\Interact\Button2.ogg")

Global MenuWhite%, MenuGray%, MenuBlack%

MenuWhite = LoadImage_Strict("GFX\menu\menu_white.png")
MenuGray = LoadImage_Strict("GFX\menu\menu_gray.png")
MenuBlack = LoadImage_Strict("GFX\menu\menu_black.png")

Global SplitSpace$

Type Mouse
	Field MouseHit1%, MouseHit2%
	Field MouseDown1%
	Field DoubleClick%, DoubleClickSlot%
	Field LastMouseHit1%
	Field MouseUp1%
	Field Mouselook_X_Inc#, Mouselook_Y_Inc#
	Field Mouse_Left_Limit%, Mouse_Right_Limit%
	Field Mouse_Top_Limit%, Mouse_Bottom_Limit%
	Field Mouse_X_Speed_1#, Mouse_Y_Speed_1#
	Field Viewport_Center_X%, Viewport_Center_Y%
End Type

Global mo.Mouse = New Mouse

Type Launcher
	Field TotalGFXModes%
	Field GFXModes%
	Field SelectedGFXMode%
	Field GFXModeWidths%[64], GFXModeHeights%[64]
End Type

If opt\LauncherEnabled Then
	Local lnchr.Launcher = New Launcher
	
	lnchr\TotalGFXModes = CountGfxModes3D()
	
	opt\AspectRatio = 1.0
	
	UpdateLauncher(lnchr)
	
	Delete(lnchr)
EndIf

; ~ New "fake fullscreen" - ENDSHN Psst, it's called borderless windowed mode -- Love Mark
If opt\DisplayMode = 1 Then
	Graphics3DExt(DesktopWidth(), DesktopHeight(), 0, 4)
	
	opt\RealGraphicWidth = DesktopWidth()
	opt\RealGraphicHeight = DesktopHeight()
	
	opt\AspectRatio = (Float(opt\GraphicWidth) / Float(opt\GraphicHeight)) / (Float(opt\RealGraphicWidth) / Float(opt\RealGraphicHeight))
Else
	opt\AspectRatio = 1.0
	opt\RealGraphicWidth = opt\GraphicWidth
	opt\RealGraphicHeight = opt\GraphicHeight
	Graphics3DExt(opt\GraphicWidth, opt\GraphicHeight, 0, (opt\DisplayMode = 2) + 1)
EndIf

Const VersionNumber$ = "1.1"

AppTitle(Format(GetLocalString("misc", "title"), VersionNumber))

Global MenuScale# = opt\GraphicHeight / 1024.0

mo\Mouselook_X_Inc = 0.3 ; ~ This sets both the sensitivity and direction (+ / -) of the mouse on the X axis
mo\Mouselook_Y_Inc = 0.3 ; ~ This sets both the sensitivity and direction (+ / -) of the mouse on the Y axis
mo\Mouse_Left_Limit = 250 * MenuScale
mo\Mouse_Right_Limit = opt\GraphicWidth - mo\Mouse_Left_Limit
mo\Mouse_Top_Limit = 150 * MenuScale
mo\Mouse_Bottom_Limit = opt\GraphicHeight - mo\Mouse_Top_Limit ; ~ As above

; ~ Viewport
mo\Viewport_Center_X = opt\GraphicWidth / 2
mo\Viewport_Center_Y = opt\GraphicHeight / 2

SetBuffer(BackBuffer())

Const TICK_DURATION# = 70.0 / 60.0

Type FramesPerSeconds
	Field Accumulator#
	Field PrevTime%
	Field CurrTime%
	Field FPS%
	Field TempFPS%
	Field Goal%
	Field LoopDelay%
	Field Factor#[2]
End Type

Global fps.FramesPerSeconds = New FramesPerSeconds

SeedRnd(MilliSecs2())

fps\LoopDelay = MilliSecs2()

Global WireFrameState%

Global GameSaved%
Global CanSave%

If opt\PlayStartup Then PlayStartupVideos()

Global CursorIMG% = LoadImage_Strict("GFX\Menu\cursor.png")
CursorIMG = ScaleImage2(CursorIMG, MenuScale, MenuScale)

Global SelectedLoadingScreen.LoadingScreens, LoadingScreenAmount%, LoadingScreenText%
Global LoadingBack% = LoadImage_Strict("LoadingScreens\loading_back.png")
LoadingBack = ScaleImage2(LoadingBack, MenuScale, MenuScale)

InitLoadingScreens(LoadingScreensFile)

Const FontsPath$ = "GFX\Fonts\"

If (Not opt\PlayStartup) Then fo\FontID[Font_Default] = LoadFont_Strict(FontsPath + GetFileLocalString(FontsFile, "Default", "File"), GetFileLocalString(FontsFile, "Default", "Size"))
fo\FontID[Font_Default_Big] = LoadFont_Strict(FontsPath + GetFileLocalString(FontsFile, "Default_Big", "File"), GetFileLocalString(FontsFile, "Default_Big", "Size"))
fo\FontID[Font_Digital] = LoadFont_Strict(FontsPath + GetFileLocalString(FontsFile, "Digital", "File"), GetFileLocalString(FontsFile, "Digital", "Size"))
fo\FontID[Font_Digital_Big] = LoadFont_Strict(FontsPath + GetFileLocalString(FontsFile, "Digital_Big", "File"), GetFileLocalString(FontsFile, "Digital_Big", "Size"))
fo\FontID[Font_Journal] = LoadFont_Strict(FontsPath + GetFileLocalString(FontsFile, "Journal", "File"), GetFileLocalString(FontsFile, "Journal", "Size"))
fo\FontID[Font_Console] = LoadFont_Strict(FontsPath + GetFileLocalString(FontsFile, "Console", "File"), GetFileLocalString(FontsFile, "Console", "Size"))

SetFont2(fo\FontID[Font_Default_Big])

Global BlinkMeterIMG% = LoadImage_Strict("GFX\HUD\blink_meter(1).png")
BlinkMeterIMG = ScaleImage2(BlinkMeterIMG, MenuScale, MenuScale)

RenderLoading(0, GetLocalString("loading", "core.main"))

Type Player
	Field Terminated# = False
	Field KillAnim%, KillAnimTimer#, FallTimer#, DeathTimer#
	Field Sanity#, RestoreSanity%
	Field ForceMove#, ForceAngle#
	Field Playable%, PlayTime%
	Field BlinkTimer#, BLINKFREQ#, BlinkEffect#, BlinkEffectTimer#, EyeIrritation#, EyeStuck#
	Field Stamina#, StaminaEffect#, StaminaEffectTimer#
	Field CameraShakeTimer#, Shake#, CameraShake#, BigCameraShake#
	Field Vomit%, VomitTimer#, Regurgitate%
	Field HeartBeatRate#, HeartBeatTimer#, HeartBeatVolume#
	Field Injuries#, Bloodloss#, PrevInjuries#, PrevBloodloss#, HealTimer#, QuickHealTimer#
	Field DropSpeed#, HeadDropSpeed#, CurrSpeed#
	Field Crouch%, CrouchState#
	Field SndVolume#
	Field SelectedEnding%, EndingScreen%, EndingTimer#
	Field CreditsScreen%, CreditsTimer#
	Field BlurVolume#, BlurTimer#
	Field LightBlink#, LightFlash#
	Field CurrCameraZoom#
	Field RefinedItems%
	Field Deaf%, DeafTimer#
	Field Zombie%
	Field Detected%
	Field ExplosionTimer#
	Field Zone%
	Field Collider%, Head%
	Field StopHidingTimer#
	Field Funds%, UsedMastercard%
	Field EscapeTimer%
End Type

Global me.Player = New Player

Type WearableItems
	Field GasMask%, GasMaskFogTimer#
	Field HazmatSuit%
	Field BallisticVest%
	Field BallisticHelmet%
	Field NightVision%, NVGTimer#, IsNVGBlinking%
	Field SCRAMBLE%
End Type

Global wi.WearableItems = New WearableItems

RenderLoading(5, GetLocalString("loading", "core.achv"))

Include "Source Code\Achievements_Core.bb"

Global CameraPitch#

Global GrabbedEntity%

Global CoffinDistance# = 100.0

Global SoundTransmission%

Global MainMenuOpen%, MenuOpen%, InvOpen%

RenderLoading(10, GetLocalString("loading", "core.diff"))

Include "Source Code\Difficulty_Core.bb"

Global MTFTimer#

Global RadioState#[9]
Global RadioState2%[9]
Global RadioState3%[10]

; ~ Textures Constants
;[Block]
Const MaxOverlayTextureIDAmount% = 13
Const MaxOverlayIDAmount% = 11
Const MaxIconIDAmount% = 7
Const MaxImageIDAmount% = 8
;[End Block]

Type Textures
	Field IconID%[MaxIconIDAmount]
	Field ImageID%[MaxImageIDAmount]
	Field OverlayTextureID%[MaxOverlayTextureIDAmount]
	Field OverlayID%[MaxOverlayIDAmount]
End Type

Global t.Textures = New Textures

Include "Source Code\Loading_Core.bb"
Include "Source Code\Console_Core.bb"

Global SubjectName$ = GetLocalString("misc", "subject")

Type Messages
	Field Txt$
	Field Timer#
	Field DeathMsg$
	Field KeyPadMsg$
	Field KeyPadTimer#
	Field KeyPadInput$
	Field HintTxt$
	Field HintTimer#
	Field HintY#
End Type

Global msg.Messages = New Messages

Function CreateMsg%(Txt$, Sec# = 6.0)
	If SelectedDifficulty\Name = "Apollyon" Lor (Not opt\HUDEnabled) Then Return
	
	msg\Txt = Txt
	msg\Timer = 70.0 * Sec
End Function

Function UpdateMessages%()
	If SelectedDifficulty\Name = "Apollyon" Lor (Not opt\HUDEnabled) Then Return
	
	If msg\Timer > 0.0 Then
		msg\Timer = msg\Timer - fps\Factor[0]
	Else
		msg\Timer = 0.0 : msg\Txt = ""
	EndIf
End Function

Function RenderMessages%()
	If SelectedDifficulty\Name = "Apollyon" Lor (Not opt\HUDEnabled) Then Return
	
	If msg\Timer > 0.0 Then
		Local Temp% = False
		
		If (Not (InvOpen Lor OtherOpen <> Null)) Then
			If SelectedItem <> Null Then
				If SelectedItem\ItemTemplate\TempName = "paper" Lor SelectedItem\ItemTemplate\TempName = "oldpaper" Then Temp = True
			ElseIf I_294\Using Lor d_I\SelectedDoor <> Null Lor SelectedScreen <> Null
				Temp = True
			EndIf
		EndIf
		
		Local Temp2% = Min(msg\Timer / 2.0, 255.0)
		
		SetFont2(fo\FontID[Font_Default])
		If (Not Temp) Then
			Color(Temp2, Temp2, Temp2)
			Text2(mo\Viewport_Center_X, mo\Viewport_Center_Y + (200 * MenuScale), msg\Txt, True)
		Else
			Color(Temp2, Temp2, Temp2)
			Text2(mo\Viewport_Center_X, opt\GraphicHeight * 0.94, msg\Txt, True)
		EndIf
	EndIf
	Color(255, 255, 255)
	If opt\ShowFPS Then
		SetFont2(fo\FontID[Font_Console])
		Text2(20 * MenuScale, 20 * MenuScale, "FPS: " + fps\FPS)
		SetFont2(fo\FontID[Font_Default])
	EndIf
End Function

Function CreateHintMsg%(Txt$, Sec# = 6.0)
	If SelectedDifficulty\Name = "Apollyon" Lor (Not opt\HUDEnabled) Then Return
	
	msg\HintTxt = Txt
	msg\HintTimer = 70.0 * Sec
End Function

Function UpdateHintMessages%()
	If SelectedDifficulty\Name = "Apollyon" Lor (Not opt\HUDEnabled) Then Return
	
	Local Scale# = opt\GraphicHeight / 768.0
	Local Width = StringWidth(msg\HintTxt) + (20 * Scale)
	Local Height% = 30 * Scale
	
	If msg\HintTxt <> "" Then
		If msg\HintTimer > 0.0 Then
			If msg\HintY < Height Then
				msg\HintY = Min(msg\HintY + (2.0 * fps\Factor[0]), Height)
			Else
				msg\HintY = Height
			EndIf
			msg\HintTimer = msg\HintTimer - fps\Factor[0]
		Else
			If msg\HintY > 0.0 Then
				msg\HintY = Max(msg\HintY - (2.0 * fps\Factor[0]), 0.0)
			Else
				msg\HintTxt = ""
				msg\HintTimer = 0.0
				msg\HintY = 0.0
			EndIf
		EndIf
	EndIf
	
End Function

Function RenderHintMessages%()
	If SelectedDifficulty\Name = "Apollyon" Lor (Not opt\HUDEnabled) Then Return
	
	Local Scale# = opt\GraphicHeight / 768.0
	Local Width = StringWidth(msg\HintTxt) + (20 * Scale)
	Local Height% = 30 * Scale
	Local x% = mo\Viewport_Center_X - (Width / 2)
	Local y% = (-Height) + msg\HintY
	
	If msg\HintTxt <> "" Then
		RenderFrame(x, y, Width, Height)
		Color(255, 255, 255)
		SetFont2(fo\FontID[Font_Default])
		Text2(mo\Viewport_Center_X, y + (Height / 2), msg\HintTxt, True, True)
	EndIf
End Function

Global Camera%

RenderLoading(15, GetLocalString("loading", "core.subtitle"))

Include "Source Code\Subtitles_Core.bb"

RenderLoading(20, GetLocalString("loading", "core.sound"))

Include "Source Code\Sounds_Core.bb"

Global OptionsMenu% = 0
Global QuitMsg% = 0

Global InFacility% = True

Global ForestNPC%, ForestNPCTex%, ForestNPCData#[3]

RenderLoading(25, GetLocalString("loading", "core.item"))

Include "Source Code\Items_Core.bb"

RenderLoading(30, GetLocalString("loading", "core.particle"))

Include "Source Code\Particles_Core.bb"

RenderLoading(35, GetLocalString("loading", "core.grap"))

Include "Source Code\Graphics_Core.bb"

RenderLoading(40, GetLocalString("loading", "core.map"))

Include "Source Code\Map_Core.bb"

RenderLoading(60, GetLocalString("loading", "core.npc"))

Include "Source Code\NPCs_Core.bb"

RenderLoading(65, GetLocalString("loading", "core.event"))

Include "Source Code\Events_Core.bb"

; ~ Collisions Constants
;[Block]
Const HIT_MAP% = 1
Const HIT_PLAYER% = 2
Const HIT_ITEM% = 3
Const HIT_APACHE% = 4
Const HIT_178% = 5
Const HIT_DEAD% = 6
;[End Block]

Collisions(HIT_PLAYER, HIT_MAP, 2, 2)
Collisions(HIT_PLAYER, HIT_PLAYER, 1, 3)
Collisions(HIT_ITEM, HIT_MAP, 2, 2)
Collisions(HIT_APACHE, HIT_APACHE, 1, 2)
Collisions(HIT_178, HIT_MAP, 2, 2)
Collisions(HIT_178, HIT_178, 1, 3)
Collisions(HIT_DEAD, HIT_MAP, 2, 2)

Global ShouldEntitiesFall% = True
Global PlayerFallingPickDistance# = 10.0

Global MTFCameraCheckTimer# = 0.0
Global MTFCameraCheckDetected% = False

RenderLoading(70, GetLocalString("loading", "core.save"))

Include "Source Code\Save_Core.bb"

RenderLoading(80, GetLocalString("loading", "core.menu"))

Include "Source Code\Menu_Core.bb"

InitMainMenuAssets()
MainMenuOpen = True

ResetInput()

RenderLoading(100)

Global Input_ResetTime# = 0.0

Type SCP005
	Field ChanceToSpawn%
End Type

Global I_005.SCP005 = New SCP005

Type SCP008
	Field Timer#
	Field Revert%
End Type

Global I_008.SCP008 = New SCP008

Type SCP035
	Field Sad%
End Type

Global I_035.SCP035 = New SCP035

Type SCP294
	Field Using%
	Field ToInput$
End Type

Global I_294.SCP294 = New SCP294

Type SCP409
	Field Timer#
	Field Revert%
End Type

Global I_409.SCP409 = New SCP409

Type SCP427
	Field Using%
	Field Timer#
	Field Sound%[2]
	Field SoundCHN%[2]
End Type

Global I_427.SCP427 = New SCP427

Type SCP714
	Field Using%
End Type

Global I_714.SCP714 = New SCP714

Type SCP1025
	Field State#[8]
End Type

Global I_1025.SCP1025 = New SCP1025

Type SCP1499
	Field Using%
	Field PrevX#, PrevY#, PrevZ#
	Field PrevRoom.Rooms
	Field x#, y#, z#
	Field Sky%
End Type

Global I_1499.SCP1499 = New SCP1499

Type SCP500
	Field Taken%
End Type

Global I_500.SCP500 = New SCP500

Type MapZones
	Field Transition%[2]
	Field HasCustomForest%
	Field HasCustomMT%
End Type

Global I_Zone.MapZones = New MapZones

InitErrorMsgs(12, True)
SetErrorMsg(0, Format(GetLocalString("error", "title"), VersionNumber))

SetErrorMsg(1, Format(Format(GetLocalString("error", "date"), CurrentDate(), "{0}"), CurrentTime(), "{1}"))
SetErrorMsg(2, Format(Format(Format(GetLocalString("error", "os"), SystemProperty("os"), "{0}"), (32 + (GetEnv("ProgramFiles(X86)") <> 0) * 32), "{1}"), SystemProperty("osbuild"), "{2}"))
SetErrorMsg(3, Format(Format(Format(GetLocalString("error", "cpu"), Trim(SystemProperty("cpuname")), "{0}"), SystemProperty("cpuarch"), "{1}"), GetEnv("NUMBER_OF_PROCESSORS"), "{2}"))
SetErrorMsg(4, Format(Format(Format(GetLocalString("error", "gpu"), GfxDriverName(CountGfxDrivers()), "{0}"), ((TotalVidMem() / 1024) - (AvailVidMem() / 1024)), "{1}"), (TotalVidMem() / 1024), "{2}"))

SetErrorMsg(10, Format(GetLocalString("error", "ex"), "_CaughtError_") + Chr(10))
SetErrorMsg(11, GetLocalString("error", "shot")) 

Function CatchErrors%(Location$)
	SetErrorMsg(5, Format(Format(GetLocalString("error", "status"), ((TotalPhys() / 1024) - (AvailPhys() / 1024)), "{0}"), (TotalPhys() / 1024), "{1}"))
	SetErrorMsg(9, Format(GetLocalString("error", "error"), Location))
End Function

Repeat
	Cls()
	Local ElapsedMilliSecs%
	
	fps\CurrTime = MilliSecs2()
	
	ElapsedMilliSecs = fps\CurrTime - fps\PrevTime
	If (ElapsedMilliSecs > 0 And ElapsedMilliSecs < 500) Then fps\Accumulator = fps\Accumulator + Max(0.0, Float(ElapsedMilliSecs) * 70.0 / 1000.0)
	fps\PrevTime = fps\CurrTime
	
	If opt\FrameLimit > 0.0 Then
		Local WaitingTime% = (1000.0 / opt\FrameLimit) - (MilliSecs2() - fps\LoopDelay)
		
		Delay(WaitingTime)
		fps\LoopDelay = MilliSecs2()
	EndIf
	
	fps\Factor[0] = TICK_DURATION
	fps\Factor[1] = fps\Factor[0]
	
	If MainMenuOpen Then
		UpdateMainMenu()
	Else
		UpdateGame()
	EndIf
	
	RenderGamma()
	
	If KeyHit(key\SCREENSHOT) Then GetScreenshot()
	
	If opt\ShowFPS Then
		If fps\Goal < MilliSecs2() Then
			fps\FPS = fps\TempFPS
			fps\TempFPS = 0
			fps\Goal = MilliSecs2() + 1000
		Else
			fps\TempFPS = fps\TempFPS + 1
		EndIf
	EndIf
	
	Flip(opt\VSync)
Forever

Function UpdateGame%()
	CatchErrors("Uncaught (UpdateGame)")
	
	Local e.Events, ev.Events, r.Rooms
	Local i%, TempStr$
	Local RN$ = PlayerRoom\RoomTemplate\Name
	
	If SelectedCustomMap = Null Then
		TempStr = GetLocalString("menu", "new.seed") + RandomSeed
	Else
		If Len(ConvertToUTF8(SelectedCustomMap\Name)) > 15 Then
			TempStr = GetLocalString("menu", "new.map") + Left(ConvertToUTF8(SelectedCustomMap\Name), 14) + "..."
		Else
			TempStr = GetLocalString("menu", "new.map") + ConvertToUTF8(SelectedCustomMap\Name)
		EndIf
	EndIf
	SetErrorMsg(6, TempStr)
	SetErrorMsg(7, Format(GetLocalString("misc", "room"), PlayerRoom\RoomTemplate\Name))
	
	For ev.Events = Each Events
		If ev\room = PlayerRoom Then
			SetErrorMsg(8, Format(Format(Format(Format(Format(GetLocalString("misc", "event"), ev\EventID, "{0}"), ev\EventState, "{1}"), ev\EventState2, "{2}"), ev\EventState3, "{3}"), ev\EventState4, "{4}") + Chr(10))
			Exit
		EndIf
	Next
	
	While fps\Accumulator > 0.0
		fps\Accumulator = fps\Accumulator - TICK_DURATION
		If fps\Accumulator <= 0.0 Then CaptureWorld()
		
		If MenuOpen Lor ConsoleOpen Then fps\Factor[0] = 0.0
		
		UpdateMouseInput()
		
		If (Not mo\MouseDown1) And (Not mo\MouseHit1) Then GrabbedEntity = 0
		
		If mm\ShouldDeleteGadgets Then DeleteMenuGadgets()
		mm\ShouldDeleteGadgets = False
		
		UpdateMusic()
		If opt\EnableSFXRelease Then AutoReleaseSounds()
		
		UpdateStreamSounds()
		
		If (Not MenuOpen) And (Not ConsoleOpen) And me\EndingTimer >= 0.0 Then
			If ga\DrawHandIcon Then ga\DrawHandIcon = False
			For i = 0 To 3
				If ga\DrawArrowIcon[i] Then ga\DrawArrowIcon[i] = False
			Next
			
			me\RestoreSanity = True
			ShouldEntitiesFall = True
			
			UpdateDistanceTimer()
			
			If PlayerInReachableRoom(False, True) Then
				UpdateSecurityCams()
				
				ShouldPlay = Min(me\Zone, 2.0)
				
				If Rand(1500) = 1 Then
					For i = 0 To 5
						If AmbientSFX(i, CurrAmbientSFX) <> 0 Then
							If (Not ChannelPlaying(AmbientSFXCHN)) Then FreeSound_Strict(AmbientSFX(i, CurrAmbientSFX)) : AmbientSFX(i, CurrAmbientSFX) = 0
						EndIf
					Next
					
					PositionEntity(SoundEmitter, EntityX(Camera) + Rnd(-1.0, 1.0), 0.0, EntityZ(Camera) + Rnd(-1.0, 1.0))
					
					If Rand(3) = 1 Then me\Zone = 3
					
					If RN = "cont1_173_intro" Then
						me\Zone = 4
					ElseIf forest_event <> Null
						If forest_event\EventState = 1.0 Then
							me\Zone = 5
							PositionEntity(SoundEmitter, EntityX(SoundEmitter), 30.0, EntityZ(SoundEmitter))
						EndIf
					EndIf
					
					CurrAmbientSFX = Rand(0, AmbientSFXAmount[me\Zone] - 1)
					
					Select me\Zone
						Case 0, 1, 2
							;[Block]
							If (Not AmbientSFX(me\Zone, CurrAmbientSFX)) Then AmbientSFX(me\Zone, CurrAmbientSFX) = LoadSound_Strict("SFX\Ambient\Zone" + (me\Zone + 1) + "\Ambient" + (CurrAmbientSFX + 1) + ".ogg")
							;[End Block]
						Case 3
							;[Block]
							If (Not AmbientSFX(me\Zone, CurrAmbientSFX)) Then AmbientSFX(me\Zone, CurrAmbientSFX) = LoadSound_Strict("SFX\Ambient\General\Ambient" + (CurrAmbientSFX + 1) + ".ogg")
							;[End Block]
						Case 4
							;[Block]
							If (Not AmbientSFX(me\Zone, CurrAmbientSFX)) Then AmbientSFX(me\Zone, CurrAmbientSFX) = LoadSound_Strict("SFX\Ambient\Pre-breach\Ambient" + (CurrAmbientSFX + 1) + ".ogg")
							;[End Block]
						Case 5
							;[Block]
							If (Not AmbientSFX(me\Zone, CurrAmbientSFX)) Then AmbientSFX(me\Zone, CurrAmbientSFX) = LoadSound_Strict("SFX\Ambient\Forest\Ambient" + (CurrAmbientSFX + 1) + ".ogg")
							;[End Block]
					End Select
					
					AmbientSFXCHN = PlaySound2(AmbientSFX(me\Zone, CurrAmbientSFX), Camera, SoundEmitter)
				EndIf
				UpdateSoundOrigin(AmbientSFXCHN, Camera, SoundEmitter)
				
				If Rand(50000) = 3 Then
					me\LightBlink = Rnd(1.0, 2.0)
					PlaySound_Strict(LoadTempSound("SFX\SCP\079\Broadcast" + Rand(8) + ".ogg"))
				EndIf
			EndIf
			
			mon_I\UpdateCheckpoint1 = False
			mon_I\UpdateCheckpoint2 = False
			
			me\SndVolume = CurveValue(0.0, me\SndVolume, 5.0)
			
			If (Not IsPlayerOutsideFacility()) Then HideDistance = 17.0
			UpdateZoneColor()
			UpdateDeaf()
			UpdateEmitters()
			If RN = "dimension_1499" And QuickLoadPercent > 0 And QuickLoadPercent < 100 Then ShouldEntitiesFall = False
			UpdateMouseLook()
			UpdateMoving()
			UpdateVomit()
			UpdateEscapeTimer()
			InFacility = CheckForPlayerInFacility()
			CurrStepSFX = 0
			If RN = "dimension_1499" Then
				If QuickLoadPercent = -1 Lor QuickLoadPercent = 100 Then UpdateDimension1499()
				UpdateLeave1499()
			ElseIf RN = "dimension_106"
				If QuickLoadPercent = -1 Lor QuickLoadPercent = 100 Then UpdateDimension106()
			Else
				UpdateDoors()
				UpdateScreens()
				UpdateRoomLights()
				If IsPlayerOutsideFacility() Then
					If QuickLoadPercent = -1 Lor QuickLoadPercent = 100 Then UpdateEndings()
				Else
					UpdateRooms()
					If QuickLoadPercent = -1 Lor QuickLoadPercent = 100 Then UpdateEvents()
				EndIf
				TimeCheckpointMonitors()
				UpdateMonitorSaving()
			EndIf
			UpdateDecals()
			UpdateMTF()
			UpdateNPCs()
			UpdateItems()
			UpdateParticles()
			Use427()
			
			If chs\InfiniteStamina Then me\Stamina = 100.0
			If chs\NoBlink Then me\BlinkTimer = me\BLINKFREQ
			
			me\BlurVolume = Min(CurveValue(0.0, me\BlurVolume, 20.0), 0.95)
			If me\BlurTimer > 0.0 Then
				me\BlurVolume = Max(Min(0.95, me\BlurTimer / 1000.0), me\BlurVolume)
				me\BlurTimer = Max(me\BlurTimer - fps\Factor[0], 0.0)
			EndIf
			
			Local DarkAlpha# = 0.0
			
			If me\Sanity < 0.0 Then
				If me\RestoreSanity Then me\Sanity = Min(me\Sanity + fps\Factor[0], 0.0)
				If me\Sanity < -200.0 Then
					DarkAlpha = Max(Min((-me\Sanity - 200.0) / 700.0, 0.6), DarkAlpha)
					If (Not me\Terminated) Then
						me\HeartBeatVolume = Min(Abs(me\Sanity + 20.00) / 500.0, 1.0)
						me\HeartBeatRate = Max(70.0 + Abs(me\Sanity + 200.0) / 6.0, me\HeartBeatRate)
					EndIf
				EndIf
			EndIf
			
			If me\EyeStuck > 0.0 Then
				me\BlinkTimer = me\BLINKFREQ
				me\EyeStuck = Max(me\EyeStuck - fps\Factor[0], 0.0)
				
				If me\EyeStuck < 9000.0 Then me\BlurTimer = Max(me\BlurTimer, (9000.0 - me\EyeStuck) * 0.5)
				If me\EyeStuck < 6000.0 Then DarkAlpha = Min(Max(DarkAlpha, (6000.0 - me\EyeStuck) / 5000.0), 1.0)
				If me\EyeStuck < 9000.0 And me\EyeStuck + fps\Factor[0] >= 9000.0 Then CreateMsg(GetLocalString("msg", "eyedrop.tear"))
			EndIf
			
			If me\BlinkTimer < 0.0 Then
				If me\BlinkTimer <= -10.0 And (RN = "room3_storage" And EntityY(me\Collider) > (-4100.0) * RoomScale) Lor RN <> "room3_storage" Then me\BlurTimer = Max(me\BlurTimer - (fps\Factor[0] / 1.6), 0.0)
				If me\BlinkTimer > -5.0 Then
					DarkAlpha = Max(DarkAlpha, Sin(Abs(me\BlinkTimer * 18.0)))
				ElseIf me\BlinkTimer > -15.0
					DarkAlpha = 1.0
				Else
					DarkAlpha = Max(DarkAlpha, Abs(Sin(me\BlinkTimer * 18.0)))
				EndIf
				
				If me\BlinkTimer <= -20.0 Then
					; ~ Randomizes the frequency of blinking. Scales with difficulty
					Select SelectedDifficulty\OtherFactors
						Case EASY
							;[Block]
							me\BLINKFREQ = Rnd(490.0, 700.0)
							;[End Block]
						Case NORMAL
							;[Block]
							me\BLINKFREQ = Rnd(455.0, 665.0)
							;[End Block]
						Case HARD
							;[Block]
							me\BLINKFREQ = Rnd(420.0, 630.0)
							;[End Block]
						Case HARDER
							;[Block]
							me\BLINKFREQ = Rnd(385.0, 595.0)
							;[End Block]
						Case EXTREME
							;[Block]
							me\BLINKFREQ = Rnd(200.0, 400.0)
							;[End Block]
					End Select
					me\BlinkTimer = me\BLINKFREQ
					If (RN = "room3_storage" And EntityY(me\Collider) > (-4100.0) * RoomScale) Lor RN <> "room3_storage" Then me\BlurTimer = Max(me\BlurTimer - Rnd(60.0, 100.0), 0.0)
				EndIf
				me\BlinkTimer = me\BlinkTimer - fps\Factor[0]
			Else
				me\BlinkTimer = me\BlinkTimer - (fps\Factor[0] * 0.6 * me\BlinkEffect)
				If wi\NightVision = 0 And wi\SCRAMBLE = 0 Then
					If me\EyeIrritation > 0.0 Then me\BlinkTimer = me\BlinkTimer - Min((me\EyeIrritation / 100.0) + 1.0, 4.0) * fps\Factor[0]
				EndIf
			EndIf
			
			me\EyeIrritation = Max(0.0, me\EyeIrritation - fps\Factor[0])
			
			If me\BlinkEffectTimer > 0.0 Then
				me\BlinkEffectTimer = me\BlinkEffectTimer - (fps\Factor[0] / 70.0)
			Else
				me\BlinkEffect = 1.0
			EndIf
			
			me\LightBlink = Max(me\LightBlink - (fps\Factor[0] / 35.0), 0.0)
			If me\LightBlink > 0.0 And wi\NightVision = 0 Then DarkAlpha = Min(Max(DarkAlpha, me\LightBlink * Rnd(0.3, 0.8)), 1.0)
			
			If I_294\Using Then DarkAlpha = 1.0
			
			If wi\NightVision = 0 Then DarkAlpha = Max((1.0 - SecondaryLightOn) * 0.9, DarkAlpha)
			
			If me\Terminated Then
				NullSelectedStuff()
				me\BlurTimer = me\KillAnimTimer * 5.0
				If me\SelectedEnding <> -1 Then
					MenuOpen = True
					me\EndingTimer = (-me\Terminated) * 0.1
				Else
					me\KillAnimTimer = me\KillAnimTimer + fps\Factor[0]
					If me\KillAnimTimer >= 400.0 Then MenuOpen = True
				EndIf
				DarkAlpha = Max(DarkAlpha, Min(Abs(me\Terminated / 400.0), 1.0))
			Else
				If (Not EntityHidden(t\OverlayID[9])) Then HideEntity(t\OverlayID[9])
				me\KillAnimTimer = 0.0
			EndIf
			
			If me\FallTimer < 0.0 Then
				If SelectedItem <> Null Then
					If Instr(SelectedItem\ItemTemplate\TempName, "hazmatsuit") Lor Instr(SelectedItem\ItemTemplate\TempName, "vest") Then
						If wi\HazmatSuit = 0 And wi\BallisticVest = 0 Then DropItem(SelectedItem)
					EndIf
				EndIf
				NullSelectedStuff()
				me\BlurTimer = Abs(me\FallTimer * 10.0)
				me\FallTimer = me\FallTimer - fps\Factor[0]
				DarkAlpha = Max(DarkAlpha, Min(Abs(me\FallTimer / 400.0), 1.0))
			EndIf
			
			If me\LightFlash > 0.0 Then
				If EntityHidden(t\OverlayID[6]) Then ShowEntity(t\OverlayID[6])
				EntityAlpha(t\OverlayID[6], Max(Min(me\LightFlash + Rnd(-0.2, 0.2), 1.0), 0.0))
				me\LightFlash = Max(me\LightFlash - (fps\Factor[0] / 70.0), 0.0)
			Else
				If (Not EntityHidden(t\OverlayID[6])) Then HideEntity(t\OverlayID[6])
			EndIf
			
			If SelectedItem <> Null And (Not InvOpen) And OtherOpen = Null Then
				If IsItemInFocus() Then DarkAlpha = Max(DarkAlpha, 0.5)
			EndIf
			
			If SelectedScreen <> Null Lor d_I\SelectedDoor <> Null Then DarkAlpha = Max(DarkAlpha, 0.5)
			
			If DarkAlpha <> 0.0 Then
				If EntityHidden(t\OverlayID[5]) Then ShowEntity(t\OverlayID[5])
				EntityAlpha(t\OverlayID[5], DarkAlpha)
			Else
				If (Not EntityHidden(t\OverlayID[5])) Then HideEntity(t\OverlayID[5])
			EndIf
		EndIf
		
		If fps\Factor[0] = 0.0 Then
			UpdateWorld(0.0)
		Else
			UpdateWorld()
			ManipulateNPCBones()
		EndIf
		
		UpdateWorld2()
		
		UpdateGUI()
		
		If KeyHit(key\INVENTORY) Then
			If d_I\SelectedDoor = Null And SelectedScreen = Null And (Not I_294\Using) And me\Playable And (Not me\Zombie) And me\VomitTimer >= 0.0 And me\FallTimer >= 0.0 And (Not me\Terminated) And me\SelectedEnding = -1 Then
				Local W$ = ""
				Local V# = 0.0
				
				If SelectedItem <> Null Then
					W = SelectedItem\ItemTemplate\TempName
					V = SelectedItem\State
					If W = "paper" Lor W = "badge" Lor W = "oldpaper" Lor W = "ticket" Lor W = "scp1025" Then
						If SelectedItem\ItemTemplate\Img <> 0 Then FreeImage(SelectedItem\ItemTemplate\Img) : SelectedItem\ItemTemplate\Img = 0
					EndIf
				EndIf
				If (W <> "vest" And W <> "finevest" And W <> "hazmatsuit" And W <> "finehazmatsuit" And W <> "veryfinehazmatsuit" And W <> "hazmatsuit148") Lor V = 0.0 Lor V = 100.0 Then
					If InvOpen Then
						StopMouseMovement()
					Else
						mo\DoubleClickSlot = -1
					EndIf
					InvOpen = (Not InvOpen)
					If OtherOpen <> Null Then OtherOpen = Null
					SelectedItem = Null
				EndIf
			EndIf
		EndIf
		
		If KeyHit(key\SAVE) Then
			If SelectedDifficulty\SaveType < NO_SAVES Then
				If QuickLoadPercent > -1 Then CanSave = 0
				If CanSave = 0 Then ; ~ Scripted location
					CreateHintMsg(GetLocalString("save", "failed.now"))
				ElseIf CanSave = 1 ; ~ Endings / Intro location
					CreateHintMsg(GetLocalString("save", "failed.location"))
				Else ; ~ Can save
					If SelectedDifficulty\SaveType = SAVE_ANYWHERE Then
						If as\Timer <= 70.0 * 5.0 Then
							CancelAutoSave()
						Else
							SaveGame(CurrSave\Name)
						EndIf
					Else
						If SelectedScreen = Null And sc_I\SelectedMonitor = Null Then
							CreateHintMsg(GetLocalString("save", "failed.screen"))
						Else
							SaveGame(CurrSave\Name)
						EndIf
					EndIf
				EndIf
				If QuickLoadPercent > -1 Then CreateHintMsg(msg\HintTxt + GetLocalString("save", "failed.loading"))
				CanSave = 2
			Else
				CreateHintMsg(GetLocalString("save", "disable"))
			EndIf
		ElseIf SelectedDifficulty\SaveType = SAVE_ON_SCREENS And (SelectedScreen <> Null Lor sc_I\SelectedMonitor <> Null)
			If msg\HintTxt = "" Lor msg\HintTimer <= 0.0 Then
				CreateHintMsg(Format(GetLocalString("save", "save"), key\Name[key\SAVE]))
			EndIf
			If mo\MouseHit2 Then sc_I\SelectedMonitor = Null
		EndIf
		UpdateAutoSave()
		
		If KeyHit(key\CONSOLE) Then
			If opt\CanOpenConsole And SelectedDifficulty\Name <> "Apollyon" Then
				If ConsoleOpen Then
					UsedConsole = True
					ResumeSounds()
					StopMouseMovement()
					mm\ShouldDeleteGadgets = True
				Else
					PauseSounds()
				EndIf
				ConsoleOpen = (Not ConsoleOpen)
				FlushKeys()
			EndIf
		EndIf
		
		If me\EndingTimer < 0.0 Then
			If me\SelectedEnding <> -1 Then UpdateEnding()
		Else
			If me\SelectedEnding = -1 Then UpdateMenu()
		EndIf
		
		UpdateMessages()
		UpdateHintMessages()
		UpdateSubtitles()
		
		UpdateConsole()
		
		UpdateQuickLoading()
		
		UpdateAchievementMsg()
		
		ResetDistanceTimer()
	Wend
	
	; ~ Go out of function immediately if the game has been quit
	If MainMenuOpen Then Return
	
	RenderGame()
	
	CatchErrors("UpdateGame")
End Function

Function RenderGame%()
	If fps\Factor[0] > 0.0 And PlayerInReachableRoom(False, True) Then RenderSecurityCams()
	
	RenderWorld2(Max(0.0, 1.0 + (fps\Accumulator / TICK_DURATION)))
	
	If (Not MenuOpen) And (Not InvOpen) And (OtherOpen = Null) And (d_I\SelectedDoor = Null) And (Not ConsoleOpen) And (Not I_294\Using) And (SelectedScreen = Null) And me\EndingTimer >= 0.0 Then
		RenderRoomLights(Camera)
	EndIf
	
	RenderBlur(me\BlurVolume)
	
	RenderGUI()
	
	RenderMessages()
	RenderHintMessages()
	RenderSubtitles()
	
	If me\EndingTimer < 0.0 Then
		If me\SelectedEnding <> -1 Then RenderEnding()
	Else
		If me\SelectedEnding = -1 Then RenderMenu()
	EndIf
	
	RenderConsole()
	
	RenderQuickLoading()
	
	RenderAchievementMsg()
End Function

Function Kill%(IsBloody% = False)
	If chs\GodMode Then Return
	
	Local de.Decals
	
	StopBreathSound()
	
	If (Not me\Terminated) Then
		If IsBloody Then
			If EntityHidden(t\OverlayID[9]) Then ShowEntity(t\OverlayID[9])
			de.Decals = CreateDecal(DECAL_BLOOD_6, PickedX(), PickedY() + 0.005, PickedZ(), 90.0, Rnd(360.0), 0.0, 0.1)
			de\SizeChange = 0.002
			EntityParent(de\OBJ, PlayerRoom\OBJ)
		EndIf
		
		me\KillAnim = Rand(0, 1)
		PlaySound_Strict(DamageSFX[0])
		If SelectedDifficulty\SaveType = NO_SAVES Then
			DeleteGame(CurrSave)
			LoadSavedGames()
		EndIf
		
		me\Terminated = True
		ShowEntity(me\Head)
		PositionEntity(me\Head, EntityX(Camera, True), EntityY(Camera, True), EntityZ(Camera, True), True)
		ResetEntity(me\Head)
		RotateEntity(me\Head, 0.0, EntityYaw(Camera), 0.0)
	EndIf
End Function

Function StopMouseMovement%()
	MouseXSpeed() : MouseYSpeed() : MouseZSpeed()
	mo\Mouse_X_Speed_1 = 0.0
	mo\Mouse_Y_Speed_1 = 0.0
End Function

Function ResetInput%()
	FlushKeys()
	FlushMouse()
	mo\MouseHit1 = False
	mo\MouseHit2 = False
	mo\MouseDown1 = False
	mo\MouseUp1 = False
	GrabbedEntity = 0
	Input_ResetTime = 10.0
End Function

Function NullSelectedStuff%()
	InvOpen = False
	I_294\Using = False
	d_I\SelectedDoor = Null
	SelectedScreen = Null
	sc_I\SelectedMonitor = Null
	SelectedItem = Null
	OtherOpen = Null
	d_I\ClosestButton = 0
	GrabbedEntity = 0
End Function

Function ResetNegativeStats%(Revive% = False)
	Local e.Events
	Local i%
	
	me\Injuries = 0.0
	me\Bloodloss = 0.0
	
	me\BlurTimer = 0.0
	me\LightFlash = 0.0
	me\LightBlink = 0.0
	me\CameraShake = 0.0
	
	me\DeafTimer = 0.0
	
	me\DeathTimer = 0.0
	
	me\VomitTimer = 0.0
	me\HeartBeatVolume = 0.0
	
	If me\BlinkEffect > 1.0 Then
		me\BlinkEffect = 1.0
		me\BlinkEffectTimer = 0.0
	EndIf
	
	If me\StaminaEffect > 1.0 Then
		me\StaminaEffect = 1.0
		me\StaminaEffectTimer = 0.0
	EndIf
	me\Stamina = 100.0
	
	For i = 0 To 6
		I_1025\State[i] = 0.0
	Next
	
	If I_427\Timer >= 70.0 * 360.0 Then I_427\Timer = 0.0
	I_008\Timer = 0.0
	I_409\Timer = 0.0
	
	If Revive Then
		ClearCheats()
		
		; ~ If death by SCP-173 or SCP-106, enable GodMode, prevent instant death again -- Salvage
		If n_I\Curr173 <> Null Then
		If n_I\Curr173\Idle = 1 Then
			CreateConsoleMsg(Format(GetLocalString("console", "revive.by"), "SCP-173"))
				chs\GodMode = True
				n_I\Curr173\Idle = 0
			EndIf
		ElseIf n_I\Curr106 <> Null
			If EntityDistanceSquared(me\Collider, n_I\Curr106\Collider) < 4.0 Then
				CreateConsoleMsg(Format(GetLocalString("console", "revive.by"), "SCP-106"))
				chs\GodMode = True
			EndIf
		EndIf
		
		me\DropSpeed = -0.1
		me\HeadDropSpeed = 0.0
		me\Shake = 0.0
		me\CurrSpeed = 0.0
		
		me\FallTimer = 0.0
		MenuOpen = False
		
		HideEntity(me\Head)
		ShowEntity(me\Collider)
		
		me\Terminated = False
		me\KillAnim = 0
	EndIf
End Function

Function SetCrouch%(NewCrouch%)
	Local Temp%
	
	If me\Stamina > 0.0 Then
		If NewCrouch <> me\Crouch Then
			PlaySound_Strict(CrouchSFX)
			me\Stamina = me\Stamina - Rnd(8.0, 16.0)
			me\SndVolume = Max(1.0, me\SndVolume)
			
			If me\Stamina < 10.0 Then
				Temp = 0
				If wi\GasMask > 0 Lor I_1499\Using > 0 Then Temp = 1
				If (Not ChannelPlaying(BreathCHN)) Then BreathCHN = PlaySound_Strict(BreathSFX((Temp), 0))
			EndIf
			
			me\Crouch = NewCrouch
		EndIf
	EndIf
End Function

Function InjurePlayer%(Injuries_#, Infection# = 0.0, BlurTimer_# = 0.0, VestFactor# = 0.0, HelmetFactor# = 0.0)
	me\Injuries = me\Injuries + Injuries_ - ((wi\BallisticVest = 1) * VestFactor) - ((wi\BallisticVest = 2) * VestFactor * 1.4) - (me\Crouch * wi\BallisticHelmet * HelmetFactor)
	me\BlurTimer = me\BlurTimer + BlurTimer_
	I_008\Timer = I_008\Timer + (Infection * (wi\HazmatSuit = 0))
End Function

Function UpdateCough%(Chance_%)
	If (Not me\Terminated) Then
		If Rand(Chance_) = 1 Then
			If (Not ChannelPlaying(CoughCHN)) Then CoughCHN = PlaySound_Strict(CoughSFX[Rand(0, 2)])
		EndIf
	EndIf
End Function

Function UpdateMoving%()
	CatchErrors("Uncaught (UpdateMoving)")
	
	Local de.Decals
	Local Sprint# = 1.0, Speed# = 0.018
	Local Pvt%, i%, Angle#
	
	If chs\SuperMan Then
		Speed = Speed * 3.0
		
		chs\SuperManTimer = chs\SuperManTimer + fps\Factor[0]
		
		me\CameraShake = Sin(chs\SuperManTimer / 5.0) * (chs\SuperManTimer / 1500.0)
		
		If chs\SuperManTimer > 70.0 * 50.0 Then
			msg\DeathMsg = GetLocalString("console", "superman")
			Kill()
			If EntityHidden(t\OverlayID[0]) Then ShowEntity(t\OverlayID[0])
		Else
			me\BlurTimer = 500.0
			If (Not EntityHidden(t\OverlayID[0])) Then HideEntity(t\OverlayID[0])
		EndIf
	EndIf
	
	If me\DeathTimer > 0.0 Then
		me\DeathTimer = me\DeathTimer - fps\Factor[0]
		If me\DeathTimer < 1.0 Then me\DeathTimer = -1.0
	ElseIf me\DeathTimer < 0.0 
		Kill()
	EndIf
	
	If me\CurrSpeed > 0.0 Then
		me\Stamina = Min(me\Stamina + (0.15 * fps\Factor[0] / 1.25), 100.0)
	Else
		me\Stamina = Min(me\Stamina + (0.15 * fps\Factor[0] * 1.25), 100.0)
	EndIf
	
	If me\StaminaEffectTimer > 0.0 Then
		me\StaminaEffectTimer = me\StaminaEffectTimer - (fps\Factor[0] / 70.0)
	Else
		me\StaminaEffect = 1.0
	EndIf
	
	Local Temp#, Temp3%
	
	If (Not me\Terminated) And (Not chs\NoClip) And (PlayerRoom\RoomTemplate\Name <> "dimension_106") And (KeyDown(key\SPRINT) And (Not InvOpen) And OtherOpen = Null) Then
		If me\Stamina < 5.0 Then
			Temp3 = 0
			If wi\GasMask > 0 Lor I_1499\Using > 0 Then Temp3 = 1
			If (Not ChannelPlaying(BreathCHN)) Then BreathCHN = PlaySound_Strict(BreathSFX((Temp3), 0))
		ElseIf me\Stamina < 40.0
			If (Not ChannelPlaying(BreathCHN)) Then
				Temp3 = 0
				If wi\GasMask > 0 Lor I_1499\Using > 0 Then Temp3 = 1
				BreathCHN = PlaySound_Strict(BreathSFX((Temp3), Rand(3)))
				ChannelVolume(BreathCHN, Min((70.0 - me\Stamina) / 70.0, 1.0) * opt\SFXVolume * opt\MasterVolume)
			EndIf
		EndIf
	EndIf
	
	If I_714\Using = 3 Then
		me\Stamina = Min(me\Stamina, 10.0)
		me\Sanity = Max(-760.0, me\Sanity)
	ElseIf I_714\Using = 2
		me\Stamina = Min(me\Stamina, 25.0)
	ElseIf n_I\Curr513_1 <> Null Lor I_035\Sad
		me\Sanity = Min(me\Sanity, -100.5)
	EndIf
	
	If wi\BallisticVest = 2 Then me\Stamina = Min(me\Stamina, 60.0)
	
	If me\Zombie Then
		If me\Crouch Then SetCrouch(False)
	EndIf
	
	If Abs(me\CrouchState - me\Crouch) < 0.001 Then
		me\CrouchState = me\Crouch
	Else
		me\CrouchState = CurveValue(me\Crouch, me\CrouchState, 10.0)
	EndIf
	
	If d_I\SelectedDoor = Null And SelectedScreen = Null And (Not I_294\Using) Then
		If (Not chs\NoClip) Then
			If (me\Playable And (KeyDown(key\MOVEMENT_DOWN) Xor KeyDown(key\MOVEMENT_UP)) Lor (KeyDown(key\MOVEMENT_RIGHT) Xor KeyDown(key\MOVEMENT_LEFT))) Lor me\ForceMove > 0.0 Then
				If (Not me\Crouch) And (KeyDown(key\SPRINT) And (Not InvOpen) And OtherOpen = Null) And me\Stamina > 0.0 And (Not me\Zombie) Then
					Sprint = 2.5
					me\Stamina = me\Stamina - (fps\Factor[0] * 0.4 * me\StaminaEffect)
					If me\Stamina <= 0.0 Then me\Stamina = -20.0
				EndIf
				
				If PlayerRoom\RoomTemplate\Name = "dimension_106" Then
					If EntityY(me\Collider) < 2000.0 * RoomScale Lor EntityY(me\Collider) > 2608.0 * RoomScale Then
						me\Stamina = 0.0
						Speed = 0.015
						Sprint = 1.0
					EndIf
				EndIf
				
				If InvOpen Lor OtherOpen <> Null Then Speed = 0.009
				
				If me\ForceMove > 0.0 Then Speed = Speed * me\ForceMove
				
				If SelectedItem <> Null Then
					If (SelectedItem\ItemTemplate\TempName = "firstaid" Lor SelectedItem\ItemTemplate\TempName = "finefirstaid" Lor SelectedItem\ItemTemplate\TempName = "firstaid2") And wi\HazmatSuit = 0 Then Sprint = 0.0
				EndIf
				
				Temp = (me\Shake Mod 360.0)
				
				Local TempCHN%, TempCHN2%
				
				If me\Playable Then me\Shake = ((me\Shake + fps\Factor[0] * Min(Sprint, 1.5) * 7.0) Mod 720.0)
				If Temp < 180.0 And (me\Shake Mod 360.0) >= 180.0 And (Not me\Terminated) Then
					If CurrStepSFX = 0 Lor CurrStepSFX = 3 Then
						Temp = GetStepSound(me\Collider)
						
						If PlayerRoom\RoomTemplate\Name = "dimension_106" Lor PlayerRoom\RoomTemplate\Name = "room2_scientists_2" Then
							Temp3 = 5
						Else
							Temp3 = 0
						EndIf
						
						If Sprint = 2.5 Then
							TempCHN = PlaySound_Strict(StepSFX(Temp, 0, Rand(0, 7 - Temp3)))
						Else
							TempCHN = PlaySound_Strict(StepSFX(Temp, 1 - (Temp3 / 5), Rand(0, 7 - Temp3)))
						EndIf
						If CurrStepSFX = 3 Then TempCHN2 = PlaySound_Strict(Step2SFX[Rand(13, 14)])
					ElseIf CurrStepSFX = 1
						TempCHN = PlaySound_Strict(StepSFX(2, 0, Rand(0, 2)))
					ElseIf CurrStepSFX = 2
						TempCHN = PlaySound_Strict(Step2SFX[Rand(0, 2)])
					EndIf
					If Sprint = 2.5 Then
						me\SndVolume = Max(4.0, me\SndVolume)
					Else
						me\SndVolume = Max(2.5 - (me\Crouch * 0.6), me\SndVolume)
					EndIf
					ChannelVolume(TempCHN, (1.0 - (me\Crouch * 0.6)) * opt\SFXVolume * opt\MasterVolume)
					ChannelVolume(TempCHN2, (1.0 - (me\Crouch * 0.6)) * opt\SFXVolume * opt\MasterVolume)
				EndIf
			EndIf
		Else
			If (KeyDown(key\SPRINT) And (Not InvOpen) And OtherOpen = Null) Then
				Sprint = 2.5
			ElseIf KeyDown(key\CROUCH)
				Sprint = 0.5
			EndIf
		EndIf
		If KeyHit(key\CROUCH) And me\Playable And (Not me\Zombie) And me\Bloodloss < 65.0 And I_427\Timer < 70.0 * 390.0 And (Not chs\NoClip) And (SelectedItem = Null Lor (SelectedItem\ItemTemplate\TempName <> "firstaid" And SelectedItem\ItemTemplate\TempName <> "finefirstaid" And SelectedItem\ItemTemplate\TempName <> "firstaid2")) Then SetCrouch((Not me\Crouch))
		
		Local Temp2# = (Speed * Sprint) / (1.0 + me\CrouchState)
		
		If chs\NoClip Then
			me\Shake = 0.0
			me\CurrSpeed = 0.0
			me\Crouch = False
			
			RotateEntity(me\Collider, WrapAngle(EntityPitch(Camera)), WrapAngle(EntityYaw(Camera)), 0.0)
			
			Temp2 = Temp2 * chs\NoClipSpeed
			
			If KeyDown(key\MOVEMENT_DOWN) Then MoveEntity(me\Collider, 0.0, 0.0, (-Temp2) * fps\Factor[0])
			If KeyDown(key\MOVEMENT_UP) Then MoveEntity(me\Collider, 0.0, 0.0, Temp2 * fps\Factor[0])
			
			If KeyDown(key\MOVEMENT_LEFT) Then MoveEntity(me\Collider, (-Temp2) * fps\Factor[0], 0.0, 0.0)
			If KeyDown(key\MOVEMENT_RIGHT) Then MoveEntity(me\Collider, Temp2 * fps\Factor[0], 0.0, 0.0)
			
			ResetEntity(me\Collider)
		Else
			Temp2 = Temp2 / Max((me\Injuries + 3.0) / 3.0, 1.0)
			If me\Injuries > 0.5 Then Temp2 = Temp2 * Min((Sin(me\Shake / 2.0) + 1.2), 1.0)
			Temp = False
			If (Not me\Zombie) Then
				If KeyDown(key\MOVEMENT_DOWN) And me\Playable Then
					If (Not KeyDown(key\MOVEMENT_UP)) Then
						Temp = True
						Angle = 180.0
						If KeyDown(key\MOVEMENT_LEFT) Then
							If (Not KeyDown(key\MOVEMENT_RIGHT)) Then Angle = 135.0
						ElseIf KeyDown(key\MOVEMENT_RIGHT)
							Angle = -135.0
						EndIf
					Else
						If KeyDown(key\MOVEMENT_LEFT) Then
							If (Not KeyDown(key\MOVEMENT_RIGHT)) Then
								Temp = True
								Angle = 90.0
							EndIf
						ElseIf KeyDown(key\MOVEMENT_RIGHT)
							Temp = True
							Angle = -90.0
						EndIf
					EndIf
				ElseIf KeyDown(key\MOVEMENT_UP) And me\Playable
					Temp = True
					Angle = 0.0
					If KeyDown(key\MOVEMENT_LEFT) Then
						If (Not KeyDown(key\MOVEMENT_RIGHT)) Then Angle = 45.0
					ElseIf KeyDown(key\MOVEMENT_RIGHT)
						Angle = -45.0
					EndIf
				ElseIf me\ForceMove > 0.0
					Temp = True
					Angle = me\ForceAngle
				ElseIf me\Playable
					If KeyDown(key\MOVEMENT_LEFT) Then
						If (Not KeyDown(key\MOVEMENT_RIGHT)) Then
							Temp = True
							Angle = 90.0
						EndIf
					ElseIf KeyDown(key\MOVEMENT_RIGHT)
						Temp = True
						Angle = -90.0
					EndIf
				EndIf
			Else
				Temp = True
				Angle = me\ForceAngle
			EndIf
			
			Angle = WrapAngle(EntityYaw(me\Collider, True) + Angle + 90.0)
			
			If Temp Then
				me\CurrSpeed = CurveValue(Temp2, me\CurrSpeed, 20.0)
			Else
				me\CurrSpeed = Max(CurveValue(0.0, me\CurrSpeed - 0.1, 1.0), 0.0)
			EndIf
			
			If me\Playable Then TranslateEntity(me\Collider, Cos(Angle) * me\CurrSpeed * fps\Factor[0], 0.0, Sin(Angle) * me\CurrSpeed * fps\Factor[0], True)
			
			Local CollidedFloor% = False
			
			For i = 1 To CountCollisions(me\Collider)
				If CollisionY(me\Collider, i) < EntityY(me\Collider) - 0.25 Then CollidedFloor = True
			Next
			
			If CollidedFloor Then
				If PlayerRoom\RoomTemplate\Name = "dimension_106" Lor PlayerRoom\RoomTemplate\Name = "room2_scientists_2" Then
					Temp3 = 5
				Else
					Temp3 = 0
				EndIf
				
				If me\DropSpeed < -0.07 Then
					If CurrStepSFX = 0 Lor CurrStepSFX = 3 Then
						PlaySound_Strict(StepSFX(GetStepSound(me\Collider), 0, Rand(0, 7 - Temp3)))
						If CurrStepSFX = 3 Then PlaySound_Strict(Step2SFX[Rand(13, 14)])
					ElseIf CurrStepSFX = 1
						PlaySound_Strict(StepSFX(2, 0, Rand(0, 2)))
					ElseIf CurrStepSFX = 2
						PlaySound_Strict(Step2SFX[Rand(0, 2)])
					EndIf
					me\SndVolume = Max(3.0, me\SndVolume)
				EndIf
				me\DropSpeed = 0.0
			Else
				If PlayerFallingPickDistance <> 0.0 Then
					Local Pick# = LinePick(EntityX(me\Collider), EntityY(me\Collider), EntityZ(me\Collider), 0.0, -PlayerFallingPickDistance, 0.0)
					
					If Pick Then
						me\DropSpeed = Min(Max(me\DropSpeed - (0.006 * fps\Factor[0]), -2.0), 0.0)
					Else
						me\DropSpeed = 0.0
					EndIf
				Else
					me\DropSpeed = Min(Max(me\DropSpeed - (0.006 * fps\Factor[0]), -2.0), 0.0)
				EndIf
			EndIf
			PlayerFallingPickDistance = 10.0
			
			If me\Playable And ShouldEntitiesFall Then TranslateEntity(me\Collider, 0.0, me\DropSpeed * fps\Factor[0], 0.0)
		EndIf
		
		me\ForceMove = False
	EndIf
	
	If me\Injuries > 1.0 Then
		Temp2 = me\Bloodloss
		me\BlurTimer = Max(Max(Sin(MilliSecs2() / 100.0) * me\Bloodloss * 30.0, me\Bloodloss * 2.0 * (2.0 - me\CrouchState)), me\BlurTimer)
		If (Not I_427\Using) And I_427\Timer < 70.0 * 360.0 Then me\Bloodloss = Min(me\Bloodloss + (Min(me\Injuries, 4.0) / 300.0) * fps\Factor[0], 100.0)
		If Temp2 <= 65.0 And me\Bloodloss > 65.0 Then CreateMsg(GetLocalString("msg", "bloodloss"))
	EndIf
	
	Update008()
	Update409()
	
	If me\Bloodloss > 0.0 And me\VomitTimer >= 0.0 Then
		If Rnd(200.0) < Min(me\Injuries, 4.0) Then
			Pvt = CreatePivot()
			PositionEntity(Pvt, EntityX(me\Collider) + Rnd(-0.05, 0.05), EntityY(me\Collider) - 0.05, EntityZ(me\Collider) + Rnd(-0.05, 0.05))
			TurnEntity(Pvt, 90.0, 0.0, 0.0)
			EntityPick(Pvt, 0.3)
			
			de.Decals = CreateDecal(Rand(DECAL_BLOOD_DROP_1, DECAL_BLOOD_DROP_2), PickedX(), PickedY() + 0.005, PickedZ(), 90.0, Rnd(360.0), 0.0, Rnd(0.03, 0.08) * Min(me\Injuries, 2.5))
			de\SizeChange = Rnd(0.001, 0.0015) : de\MaxSize = de\Size + Rnd(0.008, 0.009)
			EntityParent(de\OBJ, PlayerRoom\OBJ)
			TempCHN = PlaySound_Strict(DripSFX[Rand(0, 3)])
			ChannelVolume(TempCHN, Rnd(0.0, 0.8) * opt\SFXVolume * opt\MasterVolume)
			ChannelPitch(TempCHN, Rand(20000, 30000))
			
			FreeEntity(Pvt)
		EndIf
		
		me\CurrCameraZoom = Max(me\CurrCameraZoom, (Sin(Float(MilliSecs2()) / 20.0) + 1.0) * me\Bloodloss * 0.2)
		
		If me\Bloodloss > 65.0 Then
			If (Not me\Crouch) Then SetCrouch(True)
		EndIf
		If me\Bloodloss >= 100.0 Then
			Kill(True)
			me\HeartBeatVolume = 0.0
		ElseIf me\Bloodloss > 80.0
			me\HeartBeatRate = Max(150.0 - (me\Bloodloss - 80.0) * 5.0, me\HeartBeatRate)
			me\HeartBeatVolume = Max(me\HeartBeatVolume, 0.75 + (me\Bloodloss - 80.0) * 0.0125)
		ElseIf me\Bloodloss > 35.0
			me\HeartBeatRate = Max(70.0 + me\Bloodloss, me\HeartBeatRate)
			me\HeartBeatVolume = Max(me\HeartBeatVolume, (me\Bloodloss - 35.0) / 60.0)
		EndIf
	EndIf
	
	If me\HealTimer > 0.0 Then
		me\HealTimer = Max(me\HealTimer - (fps\Factor[0] / 70.0), 0.0)
		me\Bloodloss = Min(me\Bloodloss + (1.0 / 200.0) * fps\Factor[0], 100.0)
		me\Injuries = Max(me\Injuries - (fps\Factor[0] / 70.0) / 30.0, 0.0)
	EndIf
	
	If me\QuickHealTimer > 0.0 Then
		me\QuickHealTimer = Max(me\QuickHealTimer - (fps\Factor[0] / 70.0), 0.0)
		me\Injuries = Max(me\Injuries - (fps\Factor[0] / 70.0) / 5.0, 0.0)
	EndIf
		
	If me\Playable Then
		If KeyHit(key\BLINK) Then me\BlinkTimer = 0.0
		If KeyDown(key\BLINK) And me\BlinkTimer < -10.0 Then me\BlinkTimer = -10.0
	EndIf
	
	If me\HeartBeatVolume > 0.0 Then
		If me\HeartBeatTimer <= 0.0 Then
			TempCHN = PlaySound_Strict(HeartBeatSFX)
			ChannelVolume(TempCHN, me\HeartBeatVolume * opt\SFXVolume * opt\MasterVolume)
			
			me\HeartBeatTimer = 70.0 * (60.0 / Max(me\HeartBeatRate, 1.0))
		Else
			me\HeartBeatTimer = me\HeartBeatTimer - fps\Factor[0]
		EndIf
		me\HeartBeatVolume = Max(me\HeartBeatVolume - fps\Factor[0] * 0.05, 0.0)
	EndIf
	
	CatchErrors("UpdateMoving")
End Function

Function UpdateMouseInput%()
	If Input_ResetTime > 0.0 Then
		Input_ResetTime = Max(Input_ResetTime - fps\Factor[0], 0.0)
	Else
		mo\DoubleClick = False
		mo\MouseHit1 = MouseHit(1)
		If mo\MouseHit1 Then
			If MilliSecs2() - mo\LastMouseHit1 < 800 Then mo\DoubleClick = True
			mo\LastMouseHit1 = MilliSecs2()
		EndIf
		
		Local PrevMouseDown1% = mo\MouseDown1
		
		mo\MouseDown1 = MouseDown(1)
		If PrevMouseDown1 And (Not mo\MouseDown1) Then
			mo\MouseUp1 = True 
		Else
			mo\MouseUp1 = False
		EndIf
		
		mo\MouseHit2 = MouseHit(2)
	EndIf
End Function

Function UpdateMouseLook%()
	CatchErrors("Uncaught (UpdateMouseLook)")
	
	Local p.Particles
	Local i%
	
	me\CameraShake = Max(me\CameraShake - (fps\Factor[0] / 10.0), 0.0)
	me\BigCameraShake = Max(me\BigCameraShake - (fps\Factor[0] / 10.0), 0.0)
	
	CameraZoom(Camera, Min(1.0 + (me\CurrCameraZoom / 400.0), 1.1) / (Tan((2.0 * ATan(Tan((opt\FOV) / 2.0) * (Float(opt\RealGraphicWidth) / Float(opt\RealGraphicHeight)))) / 2.0)))
	me\CurrCameraZoom = Max(me\CurrCameraZoom - fps\Factor[0], 0.0)
	
	If (Not me\Terminated) And me\FallTimer >= 0.0 Then
		me\HeadDropSpeed = 0.0
		
		If IsNaN(EntityX(me\Collider)) Then
			PositionEntity(me\Collider, EntityX(Camera, True), EntityY(Camera, True) - 0.5, EntityZ(Camera, True), True)
			CreateConsoleMsg(Format(GetLocalString("console", "xyz.reset"), EntityX(me\Collider)))
		EndIf
		
		Local Up# = (Sin(me\Shake) / (20.0 + me\CrouchState * 20.0)) * 0.6
		Local Roll# = Max(Min(Sin(me\Shake / 2.0) * 2.5 * Min(me\Injuries + 0.25, 3.0), 8.0), -8.0)
		
		PositionEntity(Camera, EntityX(me\Collider), EntityY(me\Collider) + Up + 0.6 + me\CrouchState * (-0.3), EntityZ(me\Collider))
		RotateEntity(Camera, 0.0, EntityYaw(me\Collider), Roll * 0.5)
		
		; ~ Update the smoothing que to smooth the movement of the mouse
		If opt\InvertMouseX Then
			mo\Mouse_X_Speed_1 = CurveValue(-MouseXSpeed() * (opt\MouseSensitivity + 0.6), mo\Mouse_X_Speed_1, (6.0 / (opt\MouseSensitivity + 1.0)) * opt\MouseSmoothing)
		Else
			mo\Mouse_X_Speed_1 = CurveValue(MouseXSpeed() * (opt\MouseSensitivity + 0.6), mo\Mouse_X_Speed_1, (6.0 / (opt\MouseSensitivity + 1.0)) * opt\MouseSmoothing)
		EndIf
		If IsNaN(mo\Mouse_X_Speed_1) Then mo\Mouse_X_Speed_1 = 0.0
		If opt\InvertMouseY Then
			mo\Mouse_Y_Speed_1 = CurveValue(-MouseYSpeed() * (opt\MouseSensitivity + 0.6), mo\Mouse_Y_Speed_1, (6.0 / (opt\MouseSensitivity + 1.0)) * opt\MouseSmoothing)
		Else
			mo\Mouse_Y_Speed_1 = CurveValue(MouseYSpeed() * (opt\MouseSensitivity + 0.6), mo\Mouse_Y_Speed_1, (6.0 / (opt\MouseSensitivity + 1.0)) * opt\MouseSmoothing)
		EndIf
		If IsNaN(mo\Mouse_Y_Speed_1) Then mo\Mouse_Y_Speed_1 = 0.0
		
		If InvOpen Lor I_294\Using Lor OtherOpen <> Null Lor d_I\SelectedDoor <> Null Lor SelectedScreen <> Null Then StopMouseMovement()
		
		Local The_Yaw# = ((mo\Mouse_X_Speed_1)) * mo\Mouselook_X_Inc / (1.0 + wi\BallisticVest)
		Local The_Pitch# = ((mo\Mouse_Y_Speed_1)) * mo\Mouselook_Y_Inc / (1.0 + wi\BallisticVest)
		
		TurnEntity(me\Collider, 0.0, -The_Yaw, 0.0) ; ~ Turn the user on the Y (Yaw) axis
		CameraPitch = CameraPitch + The_Pitch
		; ~ Limit the user's camera to within 180.0 degrees of pitch rotation. Returns useless values so we need to use a variable to keep track of the camera pitch
		If CameraPitch > 70.0 Then CameraPitch = 70.0
		If CameraPitch < -70.0 Then CameraPitch = -70.0
		
		Local ShakeTimer# = me\CameraShake + me\BigCameraShake
		
		RotateEntity(Camera, WrapAngle(CameraPitch + Rnd(-ShakeTimer, ShakeTimer)), WrapAngle(EntityYaw(me\Collider) + Rnd(-ShakeTimer, ShakeTimer)), Roll) ; ~ Pitch the user's camera up and down
		
		If PlayerRoom\RoomTemplate\Name = "dimension_106" Then
			If EntityY(me\Collider) < 2000.0 * RoomScale Lor EntityY(me\Collider) > 2608.0 * RoomScale Then RotateEntity(Camera, WrapAngle(EntityPitch(Camera)), WrapAngle(EntityYaw(Camera)), Roll + WrapAngle(Sin(MilliSecs2() / 150.0) * 30.0)) ; ~ Pitch the user's camera up and down
		EndIf
	Else
		If (Not EntityHidden(me\Collider)) Then HideEntity(me\Collider)
		PositionEntity(Camera, EntityX(me\Head), EntityY(me\Head), EntityZ(me\Head))
		
		Local CollidedFloor% = False
		
		For i = 1 To CountCollisions(me\Head)
			If CollisionY(me\Head, i) < EntityY(me\Head) - 0.01 Then CollidedFloor = True
		Next
		
		If CollidedFloor Then
			me\HeadDropSpeed = 0.0
		Else
			If (Not me\KillAnim) Then
				MoveEntity(me\Head, 0.0, 0.0, me\HeadDropSpeed)
				RotateEntity(me\Head, CurveAngle(-90.0, EntityPitch(me\Head), 20.0), EntityYaw(me\Head), EntityRoll(me\Head))
				RotateEntity(Camera, CurveAngle(EntityPitch(me\Head) - 40.0, EntityPitch(Camera), 40.0), EntityYaw(Camera), EntityRoll(Camera))
			Else
				MoveEntity(me\Head, 0.0, 0.0, -me\HeadDropSpeed)
				RotateEntity(me\Head, CurveAngle(90.0, EntityPitch(me\Head), 20.0), EntityYaw(me\Head), EntityRoll(me\Head))
				RotateEntity(Camera, CurveAngle(EntityPitch(me\Head) + 40.0, EntityPitch(Camera), 40.0), EntityYaw(Camera), EntityRoll(Camera))
			EndIf
			me\HeadDropSpeed = me\HeadDropSpeed - (0.002 * fps\Factor[0])
		EndIf
	EndIf
	
	UpdateDust()
	
	; ~ Limit the mouse's movement. Using this method produces smoother mouselook movement than centering the mouse each loop
	If (Not InvOpen) And (Not I_294\Using) And OtherOpen = Null And d_I\SelectedDoor = Null And SelectedScreen = Null Then
		If (ScaledMouseX() > mo\Mouse_Right_Limit) Lor (ScaledMouseX() < mo\Mouse_Left_Limit) Lor (ScaledMouseY() > mo\Mouse_Bottom_Limit) Lor (ScaledMouseY() < mo\Mouse_Top_Limit) Then MoveMouse(mo\Viewport_Center_X, mo\Viewport_Center_Y)
	EndIf
	
	If wi\GasMask > 0 Lor wi\HazmatSuit > 0 Lor I_1499\Using > 0 Then
		If wi\GasMask = 3 Lor I_1499\Using = 2 Then
			me\Stamina = Min(100.0, me\Stamina + (100.0 - me\Stamina) * 0.01 * fps\Factor[0])
		ElseIf wi\GasMask > 1 Lor wi\HazmatSuit = 3
			me\Stamina = Min(100.0, me\Stamina + (100.0 - me\Stamina) * 0.002 * fps\Factor[0])
		EndIf
		
		If wi\HazmatSuit > 0 Then
			If wi\HazmatSuit = 1 Then me\Stamina = Min(me\Stamina, 60.0)
			If EntityHidden(t\OverlayID[2]) Then ShowEntity(t\OverlayID[2])
		Else
			If EntityHidden(t\OverlayID[1]) Then ShowEntity(t\OverlayID[1])
		EndIf
		
		If (Not me\Terminated) Then
			If (Not ChannelPlaying(BreathCHN)) Then
				If (Not ChannelPlaying(BreathGasRelaxedCHN)) Then BreathGasRelaxedCHN = PlaySound_Strict(BreathGasRelaxedSFX)
			Else
				If ChannelPlaying(BreathGasRelaxedCHN) Then StopChannel(BreathGasRelaxedCHN) : BreathGasRelaxedCHN = 0
			EndIf
		EndIf
		
		If wi\GasMask <> 2 And wi\GasMask <> 4 And wi\HazmatSuit <> 2 And wi\HazmatSuit <> 4 Then
			; ~ TODO: Make more realistic
			If ChannelPlaying(BreathCHN) Then
				wi\GasMaskFogTimer = Min(wi\GasMaskFogTimer + (fps\Factor[0] * Rnd(0.4, 1.5)), 100.0)
			ElseIf wi\GasMask = 3 Lor wi\HazmatSuit = 3 Then
				If me\CurrSpeed > 0.0 And (KeyDown(key\SPRINT) And (Not InvOpen) And OtherOpen = Null) Then
					wi\GasMaskFogTimer = Min(wi\GasMaskFogTimer + (fps\Factor[0] * Rnd(0.2, 0.6)), 100.0)
				Else
					wi\GasMaskFogTimer = Max(0.0, wi\GasMaskFogTimer - (fps\Factor[0] * 0.3))
				EndIf
			Else
				wi\GasMaskFogTimer = Max(0.0, wi\GasMaskFogTimer - (fps\Factor[0] * 0.3))
			EndIf
			If wi\GasMaskFogTimer > 0.0 Then
				If EntityHidden(t\OverlayID[10]) Then ShowEntity(t\OverlayID[10])
				EntityAlpha(t\OverlayID[10], Min(((wi\GasMaskFogTimer * 0.2) ^ 2.0) / 1000.0, 0.45))
			EndIf
		EndIf
	Else
		If ChannelPlaying(BreathGasRelaxedCHN) Then StopChannel(BreathGasRelaxedCHN) : BreathGasRelaxedCHN = 0
		wi\GasMaskFogTimer = Max(0.0, wi\GasMaskFogTimer - (fps\Factor[0] * 0.3))
		If (Not EntityHidden(t\OverlayID[1])) Then HideEntity(t\OverlayID[1])
		If (Not EntityHidden(t\OverlayID[2])) Then HideEntity(t\OverlayID[2])
		If (Not EntityHidden(t\OverlayID[10])) Then HideEntity(t\OverlayID[10])
	EndIf
	
	If wi\BallisticHelmet Then
		If EntityHidden(t\OverlayID[8]) Then ShowEntity(t\OverlayID[8])
	Else
		If (Not EntityHidden(t\OverlayID[8])) Then HideEntity(t\OverlayID[8])
	EndIf
	
	If wi\NightVision > 0 Lor wi\SCRAMBLE > 0 Then
		If EntityHidden(t\OverlayID[4]) Then ShowEntity(t\OverlayID[4])
		If wi\NightVision = 2 Then
			EntityColor(t\OverlayID[4], 0.0, 100.0, 200.0)
		ElseIf wi\NightVision = 3
			EntityColor(t\OverlayID[4], 200.0, 0.0, 0.0)
		ElseIf wi\NightVision = 1
			EntityColor(t\OverlayID[4], 0.0, 200.0, 0.0)
		Else
			EntityColor(t\OverlayID[4], 100.0, 100.0, 100.0)
		EndIf
		EntityTexture(t\OverlayID[0], t\OverlayTextureID[12])
	Else
		If (Not EntityHidden(t\OverlayID[4])) Then HideEntity(t\OverlayID[4])
		EntityTexture(t\OverlayID[0], t\OverlayTextureID[0])
	EndIf
	
	Update1025()
	
	CatchErrors("UpdateMouseLook")
End Function

; ~ Fog Constants
;[Block]
Const FogColorLCZ$ = "005005005"
Const FogColorHCZ$ = "007002002"
Const FogColorEZ$ = "007007012"
Const FogColorStorageTunnels$ = "002007000"
Const FogColorOutside$ = "255255255"
Const FogColorDimension_1499$ = "096097104"
Const FogColorPD$ = "000000000"
Const FogColorPDTrench$ = "038055047"
Const FogColorForest$ = "098133162"
Const FogColorForestChase$ = "032044054"
;[End Block]

Global CurrFogColorR#, CurrFogColorG#, CurrFogColorB#

; ~ Ambient Color Constants
;[Block]
Const AmbientColorLCZ$ = "030030030"
Const AmbientColorHCZ$ = "030023023"
Const AmbientColorEZ$ = "023023030"
;[End Block]

Global CurrFogColor$, CurrAmbientColor$
Global CurrAmbientColorR#, CurrAmbientColorG#, CurrAmbientColorB#

Const ZoneColorChangeSpeed# = 50.0

Function SetZoneColor%(FogColor$, AmbientColor$ = AmbientColorLCZ)
	CurrFogColor = FogColor
	CurrAmbientColor = AmbientColor
End Function

Function UpdateZoneColor%()
	Local r.Rooms, e.Events
	Local i%
	
	LightVolume = CurveValue(TempLightVolume, LightVolume, 50.0)
	If PlayerRoom\RoomTemplate\Name = "cont1_173_intro" Lor IsPlayerOutsideFacility() Then
		CameraFogMode(Camera, 0)
		CameraFogRange(Camera, 5.0, 30.0)
		CameraRange(Camera, 0.05, 60.0)
		If (Not EntityHidden(t\OverlayID[0])) Then HideEntity(t\OverlayID[0])
	Else
		CameraFogMode(Camera, 1)
		CameraFogRange(Camera, opt\CameraFogNear * LightVolume, opt\CameraFogFar * LightVolume)
		CameraRange(Camera, 0.05, Min(opt\CameraFogFar * LightVolume * 1.5, 28.0))
		If EntityHidden(t\OverlayID[0]) Then ShowEntity(t\OverlayID[0])
	EndIf
	For r.Rooms = Each Rooms
		For i = 0 To r\MaxLights - 1
			If r\Lights[i] <> 0 Then
				EntityAutoFade(r\LightSprites[i], opt\CameraFogNear * LightVolume, opt\CameraFogFar * LightVolume)
			Else
				Exit
			EndIf
		Next
	Next
	
	CurrFogColor$ = ""
	CurrAmbientColor$ = ""
	
	If PlayerRoom\RoomTemplate\Name = "room3_storage" And EntityY(me\Collider) < (-4100.0) * RoomScale Then
		SetZoneColor(FogColorStorageTunnels)
	ElseIf IsPlayerOutsideFacility()
		SetZoneColor(FogColorOutside)
	ElseIf PlayerRoom\RoomTemplate\Name = "dimension_1499"
		SetZoneColor(FogColorDimension_1499)
	ElseIf PlayerRoom\RoomTemplate\Name = "dimension_106"
		For e.Events = Each Events
			If e\EventID = e_dimension_106 Then
				If e\EventState2 = PD_TrenchesRoom Lor e\EventState2 = PD_TowerRoom Then
					SetZoneColor(FogColorPDTrench)
				ElseIf e\EventState2 = PD_FakeTunnelRoom
					SetZoneColor(FogColorHCZ, AmbientColorHCZ)
				Else
					SetZoneColor(FogColorPD)
				EndIf
				Exit
			EndIf
		Next
	ElseIf (PlayerRoom\RoomTemplate\Name = "room2_mt" And (EntityY(me\Collider, True) >= 8.0 And EntityY(me\Collider, True) <= 12.0)) Lor (PlayerRoom\RoomTemplate\Name = "cont2_409" And EntityY(me\Collider) < (-3728.0) * RoomScale) Lor (PlayerRoom\RoomTemplate\Name = "cont1_895" And EntityY(me\Collider) < (-1200.0) * RoomScale) Then
		SetZoneColor(FogColorHCZ, AmbientColorHCZ)
	ElseIf forest_event <> Null
		If forest_event\EventState = 1.0 Then
			SetZoneColor(FogColorForest)
			If forest_event\room\NPC[0] <> Null Then
				If forest_event\room\NPC[0]\State >= 2.0 Then SetZoneColor(FogColorForestChase)
			EndIf
		EndIf
	EndIf
	
	If CurrFogColor = "" Then
		Select me\Zone
			Case 0
				;[Block]
				SetZoneColor(FogColorLCZ, AmbientColorLCZ)
				;[End Block]
			Case 1
				;[Block]
				SetZoneColor(FogColorHCZ, AmbientColorHCZ)
				;[End Block]
			Case 2
				;[Block]
				SetZoneColor(FogColorEZ, AmbientColorEZ)
				;[End Block]
		End Select
	EndIf
	
	CurrFogColorR = CurveValue(Left(CurrFogColor, 3), CurrFogColorR, ZoneColorChangeSpeed)
	CurrFogColorG = CurveValue(Mid(CurrFogColor, 4, 3), CurrFogColorG, ZoneColorChangeSpeed)
	CurrFogColorB = CurveValue(Right(CurrFogColor, 3), CurrFogColorB, ZoneColorChangeSpeed)
	
	CameraFogColor(Camera, CurrFogColorR, CurrFogColorG, CurrFogColorB)
	CameraClsColor(Camera, CurrFogColorR, CurrFogColorG, CurrFogColorB)
	
	CurrAmbientColorR = CurveValue(Left(CurrAmbientColor, 3), CurrAmbientColorR, ZoneColorChangeSpeed)
	CurrAmbientColorG = CurveValue(Mid(CurrAmbientColor, 4, 3), CurrAmbientColorG, ZoneColorChangeSpeed)
	CurrAmbientColorB = CurveValue(Right(CurrAmbientColor, 3), CurrAmbientColorB, ZoneColorChangeSpeed)
End Function

Function UpdateGUI%()
	CatchErrors("Uncaught (UpdateGUI)")
	
	Local e.Events, it.Items, r.Rooms
	Local Temp%, x%, y%, z%, i%
	Local x2#, ProjY#, Scale#, Pvt%
	Local n%, xTemp%, yTemp%, StrTemp$
	
	If I_294\Using Then Update294()
	
	If d_I\ClosestButton <> 0 And (Not InvOpen) And (Not I_294\Using) And OtherOpen = Null And d_I\SelectedDoor = Null And SelectedScreen = Null And (Not MenuOpen) And (Not ConsoleOpen) Then
		If mo\MouseUp1 Then
			mo\MouseUp1 = False
			If d_I\ClosestDoor <> Null Then
				If d_I\ClosestDoor\Code <> "" Then
					d_I\SelectedDoor = d_I\ClosestDoor
				ElseIf me\Playable Then
					UseDoor(d_I\ClosestDoor)
				EndIf
			EndIf
		EndIf
	EndIf
	
	If SelectedScreen <> Null Then
		If mo\MouseUp1 Lor mo\MouseHit2 Then
			SelectedScreen = Null
			mo\MouseUp1 = False
		EndIf
	EndIf
	
	Local PrevInvOpen% = InvOpen, MouseSlot% = 66
	Local ShouldDrawHUD% = True
	
	If d_I\SelectedDoor <> Null Then
		If SelectedItem <> Null Then
			If SelectedItem\ItemTemplate\TempName = "scp005" Then
				UseDoor(d_I\SelectedDoor)
				ShouldDrawHUD = False
			Else
				SelectedItem = Null
			EndIf
		EndIf
		If ShouldDrawHUD Then
			Pvt = CreatePivot()
			PositionEntity(Pvt, EntityX(d_I\ClosestButton, True), EntityY(d_I\ClosestButton, True), EntityZ(d_I\ClosestButton, True))
			RotateEntity(Pvt, 0.0, EntityYaw(d_I\ClosestButton, True) - 180.0, 0.0)
			MoveEntity(Pvt, 0.0, 0.0, 0.22)
			PositionEntity(Camera, EntityX(Pvt), EntityY(Pvt), EntityZ(Pvt))
			PointEntity(Camera, d_I\ClosestButton)
			FreeEntity(Pvt)
			
			CameraZoom(Camera, Min(1.0 + (me\CurrCameraZoom / 400.0), 1.1) / Tan((2.0 * ATan(Tan((60.0) / 2.0) * opt\RealGraphicWidth / opt\RealGraphicHeight)) / 2.0))
			CameraProject(Camera, EntityX(d_I\ClosestButton, True), EntityY(d_I\ClosestButton, True) + (MeshHeight(d_I\ButtonModelID[BUTTON_DEFAULT_MODEL]) * 0.015), EntityZ(d_I\ClosestButton, True))
			ProjY = ProjectedY()
			CameraProject(Camera, EntityX(d_I\ClosestButton, True), EntityY(d_I\ClosestButton, True) - (MeshHeight(d_I\ButtonModelID[BUTTON_DEFAULT_MODEL]) * 0.015), EntityZ(d_I\ClosestButton, True))
			Scale = (ProjectedY() - ProjY) / (462.0 * MenuScale)
			
			x = mo\Viewport_Center_X - ImageWidth(t\ImageID[4]) * (Scale / 2)
			y = mo\Viewport_Center_Y - ImageHeight(t\ImageID[4]) * (Scale / 2)
			
			If msg\KeyPadMsg <> "" Then
				msg\KeyPadTimer = msg\KeyPadTimer - fps\Factor[0]
				If msg\KeyPadTimer <= 0.0 Then
					msg\KeyPadMsg = ""
					d_I\SelectedDoor = Null
					StopMouseMovement()
				EndIf
			EndIf
			
			x = x + (44 * MenuScale * Scale)
			y = y + (249 * MenuScale * Scale)
			
			For n = 0 To 3
				For i = 0 To 2
					xTemp = x + ((58.5 * MenuScale * Scale) * n)
					yTemp = y + ((67 * MenuScale * Scale) * i)
					
					Temp = False
					If MouseOn(xTemp, yTemp, 54 * MenuScale * Scale, 65 * MenuScale * Scale) And msg\KeyPadMsg = "" Then
						If mo\MouseUp1 Then
							PlaySound_Strict(ButtonSFX)
							
							Select (n + 1) + (i * 4)
								Case 1, 2, 3
									;[Block]
									msg\KeyPadInput = msg\KeyPadInput + ((n + 1) + (i * 4))
									;[End Block]
								Case 4
									;[Block]
									msg\KeyPadInput = msg\KeyPadInput + "0"
									;[End Block]
								Case 5, 6, 7
									;[Block]
									msg\KeyPadInput = msg\KeyPadInput + ((n + 1) + (i * 4) - 1)
									;[End Block]
								Case 8
									;[Block]
									UseDoor(d_I\SelectedDoor)
									If msg\KeyPadInput = d_I\SelectedDoor\Code Then
										d_I\SelectedDoor = Null
										StopMouseMovement()
									Else
										msg\KeyPadMsg = GetLocalString("msg", "denied")
										msg\KeyPadTimer = 210.0
										msg\KeyPadInput = ""
									EndIf
									;[End Block]
								Case 9, 10, 11
									;[Block]
									msg\KeyPadInput = msg\KeyPadInput + ((n + 1) + (i * 4) - 2)
									;[End Block]
								Case 12
									;[Block]
									msg\KeyPadInput = ""
									;[End Block]
							End Select
							If Len(msg\KeyPadInput) > 4 Then msg\KeyPadInput = Left(msg\KeyPadInput, 4)
						EndIf
					Else
						Temp = False
					EndIf
				Next
			Next
			
			If mo\MouseHit2 Then
				d_I\SelectedDoor = Null
				StopMouseMovement()
			EndIf
		Else
			d_I\SelectedDoor = Null
		EndIf
	Else
		msg\KeyPadInput = ""
		msg\KeyPadTimer = 0.0
		msg\KeyPadMsg = ""
	EndIf
	
	If KeyHit(1) And me\EndingTimer >= 0.0 And me\SelectedEnding = -1 And me\KillAnimTimer <= 400.0 Then
		If MenuOpen Then
			ResumeSounds()
			If OptionsMenu <> 0 Then SaveOptionsINI()
			StopMouseMovement()
			mm\ShouldDeleteGadgets = True
		Else
			PauseSounds()
		EndIf
		MenuOpen = (Not MenuOpen)
		
		mm\AchievementsMenu = 0
		OptionsMenu = 0
		QuitMsg = 0
		
		d_I\SelectedDoor = Null
		SelectedScreen = Null
		sc_I\SelectedMonitor = Null
		I_294\Using = False
		If SelectedItem <> Null Then
			If Instr(SelectedItem\ItemTemplate\TempName, "vest") Lor Instr(SelectedItem\ItemTemplate\TempName, "hazmatsuit") Then
				If wi\BallisticVest = 0 And wi\HazmatSuit = 0 Then DropItem(SelectedItem)
				SelectedItem = Null
			EndIf
		EndIf
	EndIf
	
	Local PrevOtherOpen.Items, PrevItem.Items
	Local OtherSize%, OtherAmount%
	Local IsEmpty%
	Local IsMouseOn%
	Local ClosedInv%
	Local INVENTORY_GFX_SIZE% = 70 * MenuScale
	Local INVENTORY_GFX_SPACING% = 35 * MenuScale
	
	If OtherOpen <> Null Then
		PrevOtherOpen = OtherOpen
		OtherSize = OtherOpen\InvSlots
		
		For i = 0 To OtherSize - 1
			If OtherOpen\SecondInv[i] <> Null Then OtherAmount = OtherAmount + 1
		Next
		
		InvOpen = False
		d_I\SelectedDoor = Null
		
		Local TempX% = 0
		
		x = mo\Viewport_Center_X - ((INVENTORY_GFX_SIZE * 10 / 2) + (INVENTORY_GFX_SPACING * ((10 / 2) - 1))) / 2
		y = mo\Viewport_Center_Y - (INVENTORY_GFX_SIZE * ((OtherSize / 10 * 2) - 1)) - INVENTORY_GFX_SPACING
		
		ItemAmount = 0
		IsMouseOn = -1
		For n = 0 To OtherSize - 1
			If MouseOn(x, y, INVENTORY_GFX_SIZE, INVENTORY_GFX_SIZE) Then IsMouseOn = n
			
			If IsMouseOn = n Then MouseSlot = n
			If OtherOpen = Null Then Exit
			
			If OtherOpen\SecondInv[n] <> Null And SelectedItem <> OtherOpen\SecondInv[n] Then
				If IsMouseOn = n Then
					If SelectedItem = Null Then
						If mo\MouseHit1 Then
							SelectedItem = OtherOpen\SecondInv[n]
							
							If mo\DoubleClick And mo\DoubleClickSlot = n Then
								If OtherOpen\SecondInv[n]\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[OtherOpen\SecondInv[n]\ItemTemplate\Sound])
								OtherOpen = Null
								ClosedInv = True
								InvOpen = False
								mo\DoubleClick = False
							EndIf
						EndIf
					EndIf
				EndIf
				ItemAmount = ItemAmount + 1
			Else
				If IsMouseOn = n And mo\MouseHit1 Then
					For z = 0 To OtherSize - 1
						If OtherOpen\SecondInv[z] = SelectedItem Then
							OtherOpen\SecondInv[z] = Null
							Exit
						EndIf
					Next
					OtherOpen\SecondInv[n] = SelectedItem
					SelectedItem = Null
				EndIf
			EndIf
			
			x = x + INVENTORY_GFX_SIZE + INVENTORY_GFX_SPACING
			TempX = TempX + 1
			If TempX = 5 Then
				TempX = 0
				y = y + (INVENTORY_GFX_SIZE * 2)
				x = mo\Viewport_Center_X - ((INVENTORY_GFX_SIZE * 10 / 2) + (INVENTORY_GFX_SPACING * ((10 / 2) - 1))) / 2
			EndIf
		Next
		
		If mo\MouseHit1 Then mo\DoubleClickSlot = IsMouseOn
		
		If SelectedItem <> Null Then
			If (Not mo\MouseDown1) Lor mo\MouseHit2 Then
				If MouseSlot = 66 Then
					If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
					ShowEntity(SelectedItem\Collider)
					PositionEntity(SelectedItem\Collider, EntityX(Camera), EntityY(Camera), EntityZ(Camera))
					RotateEntity(SelectedItem\Collider, EntityPitch(Camera), EntityYaw(Camera) + Rnd(-20.0, 20.0), 0.0)
					MoveEntity(SelectedItem\Collider, 0.0, -0.1, 0.1)
					RotateEntity(SelectedItem\Collider, 0.0, EntityYaw(Camera) + Rnd(-110.0, 110.0), 0.0)
					ResetEntity(SelectedItem\Collider)
					SelectedItem\Dropped = 1
					SelectedItem\Picked = False
					For z = 0 To OtherSize - 1
						If OtherOpen\SecondInv[z] = SelectedItem Then
							OtherOpen\SecondInv[z] = Null
							Exit
						EndIf
					Next
					
					IsEmpty = True
					If OtherOpen\ItemTemplate\TempName = "wallet" Then
						If (Not IsEmpty) Then
							For z = 0 To OtherSize - 1
								If OtherOpen\SecondInv[z] <> Null Then
									Local Name$ = OtherOpen\SecondInv[z]\ItemTemplate\TempName
									
									If Name <> "25ct" And Name <> "coin" And Name <> "scp588" And Name <> "key" And Name <> "scp860" And Name <> "scp500pill" And Name <> "scp500pilldeath" And Name <> "pill" Then
										IsEmpty = False
										Exit
									EndIf
								EndIf
							Next
						EndIf
					Else
						For z = 0 To OtherSize - 1
							If OtherOpen\SecondInv[z] <> Null
								IsEmpty = False
								Exit
							EndIf
						Next
					EndIf
					
					If IsEmpty Then
						If OtherOpen\ItemTemplate\TempName = "clipboard" Then
							OtherOpen\InvImg = OtherOpen\ItemTemplate\InvImg2
							SetAnimTime(OtherOpen\Model, 17.0)
						ElseIf OtherOpen\ItemTemplate\TempName = "wallet"
							SetAnimTime(OtherOpen\Model, 0.0)
						EndIf
					EndIf
					
					SelectedItem = Null
					
					If (Not mo\MouseHit2) Then
						OtherOpen = Null
						ClosedInv = True
						MoveMouse(mo\Viewport_Center_X, mo\Viewport_Center_Y)
					EndIf
				Else
					If PrevOtherOpen\SecondInv[MouseSlot] = Null Then
						For z = 0 To OtherSize - 1
							If PrevOtherOpen\SecondInv[z] = SelectedItem Then
								PrevOtherOpen\SecondInv[z] = Null
								Exit
							EndIf
						Next
						PrevOtherOpen\SecondInv[MouseSlot] = SelectedItem
						SelectedItem = Null
					ElseIf PrevOtherOpen\SecondInv[MouseSlot] <> SelectedItem
						PrevItem = PrevOtherOpen\SecondInv[MouseSlot]
						
						Select SelectedItem\ItemTemplate\TempName
							Default
								;[Block]
								For z = 0 To OtherSize - 1
									If PrevOtherOpen\SecondInv[z] = SelectedItem Then
										PrevOtherOpen\SecondInv[z] = PrevItem
										Exit
									EndIf
								Next
								PrevOtherOpen\SecondInv[MouseSlot] = SelectedItem
								SelectedItem = Null
								;[End Block]
						End Select
					EndIf
				EndIf
				SelectedItem = Null
			EndIf
		EndIf
		
		If ClosedInv And (Not InvOpen) Then
			OtherOpen = Null
			StopMouseMovement()
		EndIf
	ElseIf InvOpen
		d_I\SelectedDoor = Null
		
		x = mo\Viewport_Center_X - ((INVENTORY_GFX_SIZE * MaxItemAmount / 2) + (INVENTORY_GFX_SPACING * ((MaxItemAmount / 2) - 1))) / 2
		y = mo\Viewport_Center_Y - INVENTORY_GFX_SIZE - INVENTORY_GFX_SPACING
		
		If MaxItemAmount = 2 Then
			y = y + INVENTORY_GFX_SIZE
			x = x - ((INVENTORY_GFX_SIZE * MaxItemAmount / 2) + INVENTORY_GFX_SPACING) / 2
		EndIf
		
		ItemAmount = 0
		IsMouseOn = -1
		For n = 0 To MaxItemAmount - 1
			If MouseOn(x, y, INVENTORY_GFX_SIZE, INVENTORY_GFX_SIZE) Then IsMouseOn = n
			
			If IsMouseOn = n Then MouseSlot = n
			
			If Inventory(n) <> Null And SelectedItem <> Inventory(n) Then
				If IsMouseOn = n Then
					If SelectedItem = Null Then
						If mo\MouseHit1 Then
							SelectedItem = Inventory(n)
							If mo\DoubleClick And mo\DoubleClickSlot = n Then
								If Inventory(n)\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[Inventory(n)\ItemTemplate\Sound])
								InvOpen = False
								mo\DoubleClick = False
								
								If Rand(100 - Int((-me\Stamina * Rnd(5.0, 10.0)))) = 1 Then
									If SelectedItem\ItemTemplate\TempName = "paper" Lor SelectedItem\ItemTemplate\TempName = "oldpaper" Then
										CreateMsg(GetLocalString("msg", "droprnd.paper"))
									ElseIf SelectedItem\ItemTemplate\TempName = "badge" Lor SelectedItem\ItemTemplate\TempName = "oldbadge"
										CreateMsg(Format(GetLocalString("msg", "droprnd.badge"), SelectedItem\ItemTemplate\Name))
									Else
										CreateMsg(Format(GetLocalString("msg", "droprnd.others"), SelectedItem\ItemTemplate\Name))
									EndIf
									DropItem(SelectedItem, False)
									SelectedItem = Null
									Return
								EndIf
							EndIf
						EndIf
					EndIf
				EndIf
				ItemAmount = ItemAmount + 1
			Else
				If IsMouseOn = n And mo\MouseHit1 Then
					For z = 0 To MaxItemAmount - 1
						If Inventory(z) = SelectedItem Then
							Inventory(z) = Null
							Exit
						EndIf
					Next
					Inventory(n) = SelectedItem
					SelectedItem = Null
				EndIf
			EndIf
			
			x = x + INVENTORY_GFX_SIZE + INVENTORY_GFX_SPACING
			If MaxItemAmount >= 4 And n = (MaxItemAmount / 2) - 1 Then
				y = y + (INVENTORY_GFX_SIZE * 2) 
				x = mo\Viewport_Center_X - ((INVENTORY_GFX_SIZE * MaxItemAmount / 2) + (INVENTORY_GFX_SPACING * ((MaxItemAmount / 2) - 1))) / 2
			EndIf
		Next
		
		If mo\MouseHit1 Then mo\DoubleClickSlot = IsMouseOn
		
		If SelectedItem <> Null Then
			If (Not mo\MouseDown1) Lor mo\MouseHit2 Then
				If MouseSlot = 66 Then
					Select SelectedItem\ItemTemplate\TempName
						Case "vest", "finevest", "hazmatsuit", "finehazmatsuit", "veryfinehazmatsuit", "hazmatsuit148"
							;[Block]
							CreateHintMsg(GetLocalString("msg", "takeoff"))
							;[End Block]
						Case "scp427"
							;[Block]
							If I_427\Using > 0 Then
								CreateHintMsg(GetLocalString("msg", "takeoff"))
							Else
								DropItem(SelectedItem)
								If (Not mo\MouseHit2) Then InvOpen = False
							EndIf
							;[End Block]
						Case "scp714", "coarse714"
							;[Block]
							If (I_714\Using = 3 And SelectedItem\ItemTemplate\TempName = "scp714") Lor (I_714\Using = 2 And SelectedItem\ItemTemplate\TempName = "coarse714") Then
								CreateHintMsg(GetLocalString("msg", "takeoff"))
							Else
								DropItem(SelectedItem)
								If (Not mo\MouseHit2) Then InvOpen = False
							EndIf
							;[End Block]
						Case "scp1499", "fine1499"
							;[Block]
							If (I_1499\Using = 1 And SelectedItem\ItemTemplate\TempName = "scp1499") Lor (I_1499\Using = 2 And SelectedItem\ItemTemplate\TempName = "fine1499") Then
								CreateHintMsg(GetLocalString("msg", "takeoff"))
							Else
								DropItem(SelectedItem)
								If (Not mo\MouseHit2) Then InvOpen = False
							EndIf
							;[End Block]
						Case "gasmask", "finegasmask", "veryfinegasmask", "gasmask148"
							;[Block]
							If (wi\GasMask = 1 And SelectedItem\ItemTemplate\TempName = "gasmask") Lor (wi\GasMask = 2 And SelectedItem\ItemTemplate\TempName = "finegasmask") Lor (wi\GasMask = 3 And SelectedItem\ItemTemplate\TempName = "veryfinegasmask") Lor (wi\GasMask = 4 And SelectedItem\ItemTemplate\TempName = "gasmask148") Then
								CreateHintMsg(GetLocalString("msg", "takeoff"))
							Else
								DropItem(SelectedItem)
								If (Not mo\MouseHit2) Then InvOpen = False
							EndIf
							;[End Block]
						Case "helmet"
							;[Block]
							If wi\BallisticHelmet Then
								CreateHintMsg(GetLocalString("msg", "takeoff"))
							Else
								DropItem(SelectedItem)
								If (Not mo\MouseHit2) Then InvOpen = False
							EndIf
							;[End Block] 
						Case "nvg", "veryfinenvg", "finenvg"
							;[Block]
							If (wi\NightVision = 1 And SelectedItem\ItemTemplate\TempName = "nvg") Lor (wi\NightVision = 3 And SelectedItem\ItemTemplate\TempName = "finenvg") Lor (wi\NightVision = 2 And SelectedItem\ItemTemplate\TempName = "veryfinenvg") Then
								CreateHintMsg(GetLocalString("msg", "takeoff"))
							Else
								DropItem(SelectedItem)
								If (Not mo\MouseHit2) Then InvOpen = False
							EndIf
							;[End Block]
						Case "scramble", "finescramble"
							;[Block]
							If (wi\SCRAMBLE = 1 And SelectedItem\ItemTemplate\TempName = "scramble") Lor (wi\SCRAMBLE = 2 And SelectedItem\ItemTemplate\TempName = "finescramble") Then
								CreateHintMsg(GetLocalString("msg", "takeoff"))
							Else
								DropItem(SelectedItem)
								If (Not mo\MouseHit2) Then InvOpen = False
							EndIf
							;[End Block]
						Default
							;[Block]
							DropItem(SelectedItem)
							If (Not mo\MouseHit2) Then InvOpen = False
							;[End Block]
					End Select
					
					If (Not mo\MouseHit2) Then
						MoveMouse(mo\Viewport_Center_X, mo\Viewport_Center_Y)
						StopMouseMovement()
					EndIf
				Else
					If Inventory(MouseSlot) = Null Then
						For z = 0 To MaxItemAmount - 1
							If Inventory(z) = SelectedItem Then
								Inventory(z) = Null
								Exit
							EndIf
						Next
						Inventory(MouseSlot) = SelectedItem
						SelectedItem = Null
					ElseIf Inventory(MouseSlot) <> SelectedItem
						PrevItem = Inventory(MouseSlot)
						
						Select SelectedItem\ItemTemplate\TempName
							Case "paper", "key0", "key1", "key2", "key3", "key4", "key5", "key6", "keyomni", "playcard", "mastercard", "oldpaper", "badge", "oldbadge", "ticket", "25ct", "coin", "scp588", "key", "scp860", "scp500pill", "scp500pilldeath", "pill"
								;[Block]
								If Inventory(MouseSlot)\ItemTemplate\TempName = "clipboard" Then
									; ~ Add an item to wallet
									Local added.Items = Null
									Local b$ = SelectedItem\ItemTemplate\TempName
									Local c%, ri%
									
									If b <> "25ct" And b <> "coin" And b <> "scp588" And b <> "key" And b <> "scp860" And b <> "scp500pill" And b <> "scp500pilldeath" And b <> "pill" Then
										For c = 0 To Inventory(MouseSlot)\InvSlots - 1
											If Inventory(MouseSlot)\SecondInv[c] = Null Then
												If SelectedItem <> Null Then
													Inventory(MouseSlot)\SecondInv[c] = SelectedItem
													Inventory(MouseSlot)\State = 1.0
													SetAnimTime(Inventory(MouseSlot)\Model, 0.0)
													Inventory(MouseSlot)\InvImg = Inventory(MouseSlot)\ItemTemplate\InvImg
													
													For ri = 0 To MaxItemAmount - 1
														If Inventory(ri) = SelectedItem Then
															Inventory(ri) = Null
															PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
															Exit
														EndIf
													Next
													added = SelectedItem
													SelectedItem = Null
													Exit
												EndIf
											EndIf
										Next
										If SelectedItem <> Null Then
											CreateMsg(GetLocalString("msg", "clipboard.full"))
										Else
											If added\ItemTemplate\TempName = "paper" Lor added\ItemTemplate\TempName = "oldpaper" Then
												CreateMsg(GetLocalString("msg", "clipboard.paper"))
											ElseIf added\ItemTemplate\TempName = "badge" Lor added\ItemTemplate\TempName = "oldbadge"
												CreateMsg(Format(GetLocalString("msg", "clipboard.badge"), added\ItemTemplate\Name))
											Else
												CreateMsg(Format(GetLocalString("msg", "clipboard.add"), added\ItemTemplate\Name))
											EndIf
										EndIf
									Else
										For z = 0 To MaxItemAmount - 1
											If Inventory(z) = SelectedItem Then
												Inventory(z) = PrevItem
												Exit
											EndIf
										Next
										Inventory(MouseSlot) = SelectedItem
									EndIf
								ElseIf Inventory(MouseSlot)\ItemTemplate\TempName = "wallet" Then
									; ~ Add an item to clipboard
									added.Items = Null
									b = SelectedItem\ItemTemplate\TempName
									If b <> "paper" And b <> "oldpaper" Then
										For c = 0 To Inventory(MouseSlot)\InvSlots - 1
											If Inventory(MouseSlot)\SecondInv[c] = Null Then
												If SelectedItem <> Null Then
													Inventory(MouseSlot)\SecondInv[c] = SelectedItem
													Inventory(MouseSlot)\State = 1.0
													If b <> "25ct" And b <> "coin" And b <> "scp588" And b <> "key" And b <> "scp860" And b <> "scp500pill" And b <> "scp500pilldeath" And b <> "pill" Then SetAnimTime(Inventory(MouseSlot)\Model, 3.0)
													Inventory(MouseSlot)\InvImg = Inventory(MouseSlot)\ItemTemplate\InvImg
													
													For ri = 0 To MaxItemAmount - 1
														If Inventory(ri) = SelectedItem Then
															Inventory(ri) = Null
															PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
															Exit
														EndIf
													Next
													added = SelectedItem
													SelectedItem = Null
													Exit
												EndIf
											EndIf
										Next
										If SelectedItem <> Null Then
											CreateMsg(GetLocalString("msg", "wallet.full"))
										Else
											CreateMsg(Format(GetLocalString("msg", "wallet.add"), added\ItemTemplate\Name))
										EndIf
									Else
										For z = 0 To MaxItemAmount - 1
											If Inventory(z) = SelectedItem Then
												Inventory(z) = PrevItem
												Exit
											EndIf
										Next
										Inventory(MouseSlot) = SelectedItem
									EndIf
								Else
									For z = 0 To MaxItemAmount - 1
										If Inventory(z) = SelectedItem Then
											Inventory(z) = PrevItem
											Exit
										EndIf
									Next
									Inventory(MouseSlot) = SelectedItem
								EndIf
								SelectedItem = Null
								;[End Block]
							Case "coarsebat"
								;[Block]
								Select Inventory(MouseSlot)\ItemTemplate\TempName
									Case "nav"
										;[Block]
										If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
										RemoveItem(SelectedItem)
										Inventory(MouseSlot)\State = Rnd(50.0)
										CreateMsg(GetLocalString("msg", "nav.bat"))
										;[End Block]
									Case "nav310"
										;[Block]
										CreateMsg(GetLocalString("msg", "nav.bat.notfit"))
										;[End Block]
									Case "navulti", "nav300"
										;[Block]
										CreateMsg(GetLocalString("msg", "nav.bat.no"))
										;[End Block]
									Case "radio"
										;[Block]
										If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
										RemoveItem(SelectedItem)
										Inventory(MouseSlot)\State = Rnd(50.0)
										CreateMsg(GetLocalString("msg", "radio.bat"))
										;[End Block]
									Case "18vradio"
										;[Block]
										CreateMsg(GetLocalString("msg", "radio.bat.notfit"))
										;[End Block]
									Case "fineradio", "veryfineradio"
										;[Block]
										CreateMsg(GetLocalString("msg", "radio.bat.no"))
										;[End Block]
									Case "nvg"
										;[Block]
										If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
										RemoveItem(SelectedItem)
										Inventory(MouseSlot)\State = Rnd(500.0)
										CreateMsg(GetLocalString("msg", "nvg.bat"))
										;[End Block]
									Case "finenvg"
										;[Block]
										CreateMsg(GetLocalString("msg", "nvg.bat.no"))
										;[End Block]
									Case "veryfinenvg"
										;[Block]
										CreateMsg(GetLocalString("msg", "nvg.bat.notfit"))
										;[End Block]
									Case "scramble"
										;[Block]
										If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
										RemoveItem(SelectedItem)
										Inventory(MouseSlot)\State = Rnd(500.0)
										CreateMsg(GetLocalString("msg", "gear.bat"))
										;[End Block]
									Case "finescramble"
										;[Block]
										CreateMsg(GetLocalString("msg", "gear.bat.notfit"))
										;[End Block]
									Default
										;[Block]
										For z = 0 To MaxItemAmount - 1
											If Inventory(z) = SelectedItem Then
												Inventory(z) = PrevItem
												Exit
											EndIf
										Next
										Inventory(MouseSlot) = SelectedItem
										SelectedItem = Null
										;[End Block]
								End Select
								;[End Block]
							Case "bat"
								;[Block]
								Select Inventory(MouseSlot)\ItemTemplate\TempName
									Case "nav"
										;[Block]
										If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
										RemoveItem(SelectedItem)
										Inventory(MouseSlot)\State = Rnd(100.0)
										CreateMsg(GetLocalString("msg", "nav.bat"))
										;[End Block]
									Case "nav310"
										;[Block]
										CreateMsg(GetLocalString("msg", "nav.bat.notfit"))
										;[End Block]
									Case "navulti", "nav300"
										;[Block]
										CreateMsg(GetLocalString("msg", "nav.bat.no"))
										;[End Block]
									Case "radio"
										;[Block]
										If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
										RemoveItem(SelectedItem)
										Inventory(MouseSlot)\State = Rnd(100.0)
										CreateMsg(GetLocalString("msg", "radio.bat"))
										;[End Block]
									Case "18vradio"
										;[Block]
										CreateMsg(GetLocalString("msg", "radio.bat.notfit"))
										;[End Block]
									Case "fineradio", "veryfineradio"
										;[Block]
										CreateMsg(GetLocalString("msg", "radio.bat.no"))
										;[End Block]
									Case "nvg"
										;[Block]
										If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
										RemoveItem(SelectedItem)
										Inventory(MouseSlot)\State = Rnd(1000.0)
										CreateMsg(GetLocalString("msg", "nvg.bat"))
										;[End Block]
									Case "finenvg"
										;[Block]
										CreateMsg(GetLocalString("msg", "nvg.bat.no"))
										;[End Block]
									Case "veryfinenvg"
										;[Block]
										CreateMsg(GetLocalString("msg", "nvg.bat.notfit"))
										;[End Block]
									Case "scramble"
										;[Block]
										If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
										RemoveItem(SelectedItem)
										Inventory(MouseSlot)\State = Rnd(1000.0)
										CreateMsg(GetLocalString("msg", "gear.bat"))
										;[End Block]
									Case "finescramble"
										;[Block]
										CreateMsg(GetLocalString("msg", "gear.bat.notfit"))
										;[End Block]
									Default
										;[Block]
										For z = 0 To MaxItemAmount - 1
											If Inventory(z) = SelectedItem Then
												Inventory(z) = PrevItem
												Exit
											EndIf
										Next
										Inventory(MouseSlot) = SelectedItem
										SelectedItem = Null
										;[End Block]
								End Select
								;[End Block]
							Case "finebat"
								;[Block]
								Select Inventory(MouseSlot)\ItemTemplate\TempName
									Case "nav"
										;[Block]
										CreateMsg(GetLocalString("msg", "nav.bat.notfit"))
										;[End Block]
									Case "nav310"
										;[Block]
										If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
										RemoveItem(SelectedItem)
										Inventory(MouseSlot)\State = Rnd(100.0)
										CreateMsg(GetLocalString("msg", "nav.bat"))
										;[End Block]
									Case "navulti", "nav300"
										;[Block]
										CreateMsg(GetLocalString("msg", "nav.bat.no"))
										;[End Block]
									Case "radio"
										;[Block]
										CreateMsg(GetLocalString("msg", "radio.bat.notfit"))
										;[End Block]
									Case "18vradio"
										;[Block]
										If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
										RemoveItem(SelectedItem)
										Inventory(MouseSlot)\State = Rnd(100.0)
										CreateMsg(GetLocalString("msg", "radio.bat"))
										;[End Block]
									Case "fineradio", "veryfineradio"
										;[Block]
										CreateMsg(GetLocalString("msg", "radio.bat.no"))
										;[End Block]
									Case "nvg"
										;[Block]
										CreateMsg(GetLocalString("msg", "nvg.bat.notfit"))
										;[End Block]
									Case "finenvg"
										;[Block]
										CreateMsg(GetLocalString("msg", "nvg.bat.no"))
										;[End Block]
									Case "veryfinenvg"
										;[Block]
										If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
										RemoveItem(SelectedItem)
										Inventory(MouseSlot)\State = Rnd(1000.0)
										CreateMsg(GetLocalString("msg", "nvg.bat"))
										;[End Block]
									Case "scramble"
										;[Block]
										CreateMsg(GetLocalString("msg", "gear.bat.notfit"))
										;[End Block]
									Case "finescramble"
										;[Block]
										If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
										RemoveItem(SelectedItem)
										Inventory(MouseSlot)\State = Rnd(1000.0)
										CreateMsg(GetLocalString("msg", "gear.bat"))
										;[End Block]
									Default
										;[Block]
										For z = 0 To MaxItemAmount - 1
											If Inventory(z) = SelectedItem Then
												Inventory(z) = PrevItem
												Exit
											EndIf
										Next
										Inventory(MouseSlot) = SelectedItem
										SelectedItem = Null
										;[End Block]
								End Select
								;[End Block]
							Case "veryfinebat", "killbat"
								;[Block]
								Select Inventory(MouseSlot)\ItemTemplate\TempName
									Case "nav"
										;[Block]
										If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
										RemoveItem(SelectedItem)
										Inventory(MouseSlot)\State = Rnd(1000.0)
										CreateMsg(GetLocalString("msg", "nav.bat"))
										;[End Block]
									Case "nav310"
										;[Block]
										CreateMsg(GetLocalString("msg", "nav.bat.notfit"))
										;[End Block]
									Case "navulti", "nav300"
										;[Block]
										CreateMsg(GetLocalString("msg", "nav.bat.no"))
										;[End Block]
									Case "radio"
										;[Block]
										If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
										RemoveItem(SelectedItem)
										Inventory(MouseSlot)\State = Rnd(1000.0)
										CreateMsg(GetLocalString("msg", "radio.bat"))
										;[End Block]
									Case "18vradio"
										;[Block]
										CreateMsg(GetLocalString("msg", "radio.bat.notfit"))
										;[End Block]
									Case "fineradio", "veryfineradio"
										;[Block]
										CreateMsg(GetLocalString("msg", "radio.bat.no"))
										;[End Block]
									Case "nvg"
										;[Block]
										If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
										RemoveItem(SelectedItem)
										Inventory(MouseSlot)\State = Rnd(10000.0)
										CreateMsg(GetLocalString("msg", "nvg.bat"))
										;[End Block]
									Case "finenvg"
										;[Block]
										CreateMsg(GetLocalString("msg", "nvg.bat.no"))
										;[End Block]
									Case "veryfinenvg"
										;[Block]
										CreateMsg(GetLocalString("msg", "nvg.bat.notfit"))
										;[End Block]
									Case "scramble"
										;[Block]
										If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
										RemoveItem(SelectedItem)
										Inventory(MouseSlot)\State = Rnd(10000.0)
										CreateMsg(GetLocalString("msg", "gear.bat"))
										;[End Block]
									Case "finescramble"
										;[Block]
										CreateMsg(GetLocalString("msg", "gear.bat.notfit"))
										;[End Block]
									Default
										;[Block]
										For z = 0 To MaxItemAmount - 1
											If Inventory(z) = SelectedItem Then
												Inventory(z) = PrevItem
												Exit
											EndIf
										Next
										Inventory(MouseSlot) = SelectedItem
										SelectedItem = Null
										;[End Block]
								End Select
								;[End Block]
							Default
								;[Block]
								For z = 0 To MaxItemAmount - 1
									If Inventory(z) = SelectedItem Then
										Inventory(z) = PrevItem
										Exit
									EndIf
								Next
								Inventory(MouseSlot) = SelectedItem
								SelectedItem = Null
								;[End Block]
						End Select
					EndIf
				EndIf
				SelectedItem = Null
			EndIf
		EndIf
		If (Not InvOpen) Then StopMouseMovement()
	Else
		If SelectedItem <> Null Then
			Select SelectedItem\ItemTemplate\TempName
				Case "nvg", "veryfinenvg", "finenvg"
					;[Block]
						me\CurrSpeed = CurveValue(0.0, me\CurrSpeed, 5.0)
						
						SelectedItem\State3 = Min(SelectedItem\State3 + (fps\Factor[0] / 1.5), 100.0)
						
						If SelectedItem\State3 = 100.0 Then
							If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
							
							If (wi\NightVision = 1 And SelectedItem\ItemTemplate\TempName = "nvg") Lor (wi\NightVision = 2 And SelectedItem\ItemTemplate\TempName = "veryfinenvg") Lor (wi\NightVision = 3 And SelectedItem\ItemTemplate\TempName = "finenvg") Then
								CreateMsg(GetLocalString("msg", "nvg.off"))
								wi\NightVision = 0
								wi\NVGTimer = 600.0 ;Reset timer
								opt\CameraFogFar = opt\StoredCameraFogFar
								If SelectedItem\State > 0.0 Then PlaySound_Strict(NVGSFX[1])
							Else
								CreateMsg(GetLocalString("msg", "nvg.on"))
								Select SelectedItem\ItemTemplate\TempName
									Case "nvg"
										;[Block]
										wi\NightVision = 1
										;[End Block]
									Case "veryfinenvg"
										;[Block]
										wi\NightVision = 2
										;[End Block]
									Case "finenvg"
										;[Block]
										wi\NightVision = 3
										;[End Block]
								End Select
								wi\GasMask = 0 : wi\SCRAMBLE = 0 : wi\BallisticHelmet = False
								If wi\NightVision = 0 Then opt\StoredCameraFogFar = opt\CameraFogFar
								opt\CameraFogFar = HideDistance
								If SelectedItem\State > 0.0 Then PlaySound_Strict(NVGSFX[0])
							EndIf
							SelectedItem\State3 = 0.0
							SelectedItem = Null
						EndIf
					;[End Block]
				Case "scp513"
					;[Block]
					PlaySound_Strict(LoadTempSound("SFX\SCP\513\Bell.ogg"))
					
					GiveAchievement(Achv513)
					
					If n_I\Curr513_1 = Null Then n_I\Curr513_1 = CreateNPC(NPCType513_1, 0.0, 0.0, 0.0)
					SelectedItem = Null
					;[End Block]
				Case "scp500pill"
					;[Block]
					If CanUseItem(True) Then
						GiveAchievement(Achv500)
						
						If I_008\Timer > 0.0 Then
							CreateMsg(GetLocalString("msg", "pill.nausea"))
							I_008\Revert = True
						ElseIf I_409\Timer > 0.0 Then
							CreateMsg(GetLocalString("msg", "pill.crystals"))
							I_409\Revert = True
						Else
							CreateMsg(GetLocalString("msg", "pill"))
						EndIf
						
						me\DeathTimer = 0.0
						me\Stamina = 100.0
						
						For i = 0 To 6
							I_1025\State[i] = 0.0
						Next
						
						If me\StaminaEffect > 1.0 Then
							me\StaminaEffect = 1.0
							me\StaminaEffectTimer = 0.0
						EndIf
						
						If me\BlinkEffect > 1.0 Then
							me\BlinkEffect = 1.0
							me\BlinkEffectTimer = 0.0
						EndIf
						
						For e.Events = Each Events
							If e\EventID = e_1048_a Then
								If e\EventState2 > 0.0 Then
									CreateMsg(GetLocalString("msg", "pill.ears"))
									
									If PlayerRoom = e\room Then me\BlinkTimer = -10.0
									If e\room\Objects[0] <> 0 Then FreeEntity(e\room\Objects[0]) : e\room\Objects[0] = 0
									RemoveEvent(e)
								EndIf
								Exit
							EndIf
						Next
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "veryfinefirstaid"
					;[Block]
					If CanUseItem(True) Then
						Select Rand(5)
							Case 1
								;[Block]
								me\Injuries = 3.5
								CreateMsg(GetLocalString("msg", "bleed"))
								;[End Block]
							Case 2
								;[Block]
								me\Injuries = 0.0
								me\Bloodloss = 0.0
								CreateMsg(GetLocalString("msg", "rapidly"))
								;[End Block]
							Case 3
								;[Block]
								me\Injuries = Max(0.0, me\Injuries - Rnd(0.5, 3.5))
								me\Bloodloss = Max(0.0, me\Bloodloss - Rnd(10.0, 100.0))
								CreateMsg(GetLocalString("msg", "better_1"))
								;[End Block]
							Case 4
								;[Block]
								me\BlurTimer = 10000.0
								me\Bloodloss = 0.0
								CreateMsg(GetLocalString("msg", "nausea"))
								;[End Block]
							Case 5
								;[Block]
								me\BlinkTimer = -10.0
								
								Local RoomName$ = PlayerRoom\RoomTemplate\Name
								
								If RoomName = "dimension_1499" Lor IsPlayerOutsideFacility() Then
									me\Injuries = 2.5
									CreateMsg(GetLocalString("msg", "bleed"))
								Else
									For r.Rooms = Each Rooms
										If r\RoomTemplate\Name = "dimension_106" Then
											PositionEntity(me\Collider, EntityX(r\OBJ), 0.8, EntityZ(r\OBJ))
											ResetEntity(me\Collider)
											UpdateTimer = 0.0
											PlaySound_Strict(Use914SFX)
											me\DropSpeed = 0.0
											n_I\Curr106\State = -2500.0
											Exit
										EndIf
									Next
									CreateMsg(GetLocalString("msg", "aid.106"))
								EndIf
								;[End Block]
						End Select
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "firstaid", "finefirstaid", "firstaid2"
					;[Block]
					If CanUseItem(True, True) Then
						If me\Bloodloss = 0.0 And me\Injuries = 0.0 Then
							CreateMsg(GetLocalString("msg", "aid.no"))
							SelectedItem = Null
							Return
						Else
							me\CurrSpeed = CurveValue(0.0, me\CurrSpeed, 5.0)
							If (Not me\Crouch) Then SetCrouch(True)
							
							SelectedItem\State = Min(SelectedItem\State + (fps\Factor[0] / 5.0), 100.0)
							
							If SelectedItem\State = 100.0 Then
								If SelectedItem\ItemTemplate\TempName = "finefirstaid" Then
									me\Bloodloss = 0.0
									me\Injuries = Max(0.0, me\Injuries - 2.0)
									If me\Injuries = 0.0 Then
										CreateMsg(GetLocalString("msg", "aid.fine"))
									ElseIf me\Injuries > 1.0
										CreateMsg(GetLocalString("msg", "aid.bleed"))
									Else
										CreateMsg(GetLocalString("msg", "aid.sore"))
									EndIf
									RemoveItem(SelectedItem)
								Else
									me\Bloodloss = Max(0.0, me\Bloodloss - Rnd(10.0, 20.0))
									If me\Injuries >= 2.5 Then
										CreateMsg(GetLocalString("msg", "aid.toobad_1"))
										me\Injuries = Max(2.5, me\Injuries - Rnd(0.3, 0.7))
									ElseIf me\Injuries > 1.0
										me\Injuries = Max(0.5, me\Injuries - Rnd(0.5, 1.0))
										If me\Injuries > 1.0 Then
											CreateMsg(GetLocalString("msg", "aid.toobad_2"))
										Else
											CreateMsg(GetLocalString("msg", "aid.stop"))
										EndIf
									Else
										If me\Injuries > 0.5 Then
											me\Injuries = 0.5
											CreateMsg(GetLocalString("msg", "aid.slight"))
										Else
											me\Injuries = me\Injuries / 2.0
											CreateMsg(GetLocalString("msg", "aid.nowalk"))
										EndIf
									EndIf
									
									If SelectedItem\ItemTemplate\TempName = "firstaid2" Then
										Select Rand(6)
											Case 1
												;[Block]
												chs\SuperMan = True
												CreateMsg(GetLocalString("msg", "aid.super"))
												;[End Block]
											Case 2
												;[Block]
												opt\InvertMouseX = (Not opt\InvertMouseX)
												opt\InvertMouseY = (Not opt\InvertMouseY)
												CreateMsg(GetLocalString("msg", "aid.invert"))
												;[End Block]
											Case 3
												;[Block]
												me\BlurTimer = 5000.0
												CreateMsg(GetLocalString("msg", "nausea"))
												;[End Block]
											Case 4
												;[Block]
												me\BlinkEffect = 0.6
												me\BlinkEffectTimer = Rnd(20.0, 30.0)
												;[End Block]
											Case 5
												;[Block]
												me\Bloodloss = 0.0
												me\Injuries = 0.0
												CreateMsg(GetLocalString("msg", "aid.stopall"))
												;[End Block]
											Case 6
												;[Block]
												CreateMsg(GetLocalString("msg", "aid.through"))
												me\Injuries = 3.5
												;[End Block]
										End Select
									EndIf
									RemoveItem(SelectedItem)
								EndIf
							EndIf
						EndIf
					EndIf
					;[End Block]
				Case "eyedrops", "eyedrops2"
					;[Block]
					If CanUseItem() Then
						me\BlinkEffect = 0.6
						me\BlinkEffectTimer = Rnd(20.0, 30.0)
						me\BlurTimer = 200.0
						If SelectedItem\ItemTemplate\TempName = "eyedrops2" Then me\Bloodloss = Max(me\Bloodloss - 10.0, 0.0)
						
						CreateMsg(GetLocalString("msg", "eyedrop.moisturized"))
						
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "fineeyedrops"
					;[Block]
					If CanUseItem() Then
						me\BlinkEffect = 0.4
						me\BlinkEffectTimer = Rnd(30.0, 40.0)
						me\Bloodloss = Max(me\Bloodloss - 1.0, 0.0)
						me\BlurTimer = 200.0
						
						CreateMsg(GetLocalString("msg", "eyedrop.moisturized.very"))
						
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "supereyedrops"
					;[Block]
					If CanUseItem() Then
						me\BlinkEffect = 0.0
						me\BlinkEffectTimer = 60.0
						me\EyeStuck = 10000.0
						me\BlurTimer = 1000.0
						
						CreateMsg(GetLocalString("msg", "eyedrop.moisturized.veryvery"))
						
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "ticket"
					;[Block]
					If (Not SelectedItem\ItemTemplate\Img) Then
						SelectedItem\ItemTemplate\Img = LoadImage_Strict(SelectedItem\ItemTemplate\ImgPath)
						SelectedItem\ItemTemplate\Img = ScaleImage2(SelectedItem\ItemTemplate\Img, MenuScale, MenuScale)
						SelectedItem\ItemTemplate\ImgWidth = ImageWidth(SelectedItem\ItemTemplate\Img) / 2
						SelectedItem\ItemTemplate\ImgHeight = ImageHeight(SelectedItem\ItemTemplate\Img) / 2
						MaskImage(SelectedItem\ItemTemplate\Img, 255, 0, 255)
					EndIf
					
					If SelectedItem\State = 0.0 Then
						CreateMsg(GetLocalString("msg", "ticket"))
						PlaySound_Strict(LoadTempSound("SFX\SCP\1162_ARC\NostalgiaCancer" + Rand(5) + ".ogg"))
						SelectedItem\State = 1.0
					EndIf
					;[End Block]
				Case "scp1025"
					;[Block]
					If (Not SelectedItem\ItemTemplate\Img) Then
						SelectedItem\ItemTemplate\Img = LoadImage_Strict(ItemHUDTexturePath + "page_1025(" + (Int(SelectedItem\State) + 1) + ").png")
						SelectedItem\ItemTemplate\Img = ScaleImage2(SelectedItem\ItemTemplate\Img, MenuScale, MenuScale)
						SelectedItem\ItemTemplate\ImgWidth = ImageWidth(SelectedItem\ItemTemplate\Img) / 2
						SelectedItem\ItemTemplate\ImgHeight = ImageHeight(SelectedItem\ItemTemplate\Img) / 2
						MaskImage(SelectedItem\ItemTemplate\Img, 255, 0, 255)
					EndIf
					
					If SelectedItem\State3 = 0.0 Then
						If I_714\Using = 1 And wi\GasMask <> 4 And wi\HazmatSuit <> 4 Then
							If SelectedItem\State = 7.0 Then
								If I_008\Timer = 0.0 Then I_008\Timer = 1.0
							Else
								I_1025\State[SelectedItem\State] = Max(1.0, I_1025\State[SelectedItem\State])
								I_1025\State[7] = 1 + (SelectedItem\State2 = 2.0) * 2.0 ; ~ 3x as fast if VERYFINE
							EndIf
						EndIf
						If Rand(3 - (SelectedItem\State2 <> 2.0) * SelectedItem\State2) = 1 Then ; ~ Higher chance for good illness if FINE, lower change for good illness if COARSE
							SelectedItem\State = 6.0
						Else
							SelectedItem\State = Rand(0, 7)
						EndIf
						SelectedItem\State3 = 1.0
					EndIf
				Case "book"
					;[Block]
					CreateMsg(GetLocalString("msg", "redbook"))
					SelectedItem = Null
					;[End Block]
				Case "cup"
					;[Block]
					If CanUseItem(True) Then
						Local Drink$ = Trim(SelectedItem\Name)
						
						If Lower(Left(Drink, 6)) = "cup of" Then
							Drink = Right(Drink, Len(Drink) - 7)
						ElseIf Lower(Left(Drink, 8)) = "a cup of"
							Drink = Right(Drink, Len(Drink) - 9)
						EndIf
						
						StrTemp = GetFileLocalString(SCP294File, Drink, "Message", "", False)
						If StrTemp <> "" Then CreateMsg(StrTemp)
						
						If StringToBoolean(GetFileLocalString(SCP294File, Drink, "Lethal", "", False))
							msg\DeathMsg = GetFileLocalString(SCP294File, Drink, "Death Message", "", False)
							Kill()
						EndIf
						me\BlurTimer = Max(Int(GetFileLocalString(SCP294File, Drink, "Blur", "", False)) * 70.0, 0.0)
						If me\VomitTimer = 0.0 Then
							me\VomitTimer = Int(GetFileLocalString(SCP294File, Drink, "Vomit", "", False))
						Else
							me\VomitTimer = Min(me\VomitTimer, Int(GetFileLocalString(SCP294File, Drink, "Vomit", "", False)))
						EndIf
						me\CameraShakeTimer = GetFileLocalString(SCP294File, Drink, "Camera Shake", "", False)
						me\Injuries = Max(me\Injuries + Int(GetFileLocalString(SCP294File, Drink, "Damage", "", False)), 0.0)
						me\QuickHealTimer = Max(me\QuickHealTimer + Int(GetFileLocalString(SCP294File, Drink, "Heal", "", False)), 0.0)
						me\Bloodloss = Max(me\Bloodloss + Int(GetFileLocalString(SCP294File, Drink, "Blood Loss", "", False)), 0.0)
						StrTemp = GetFileLocalString(SCP294File, Drink, "Sound", "", False)
						If StrTemp <> "" Then PlaySound_Strict(LoadTempSound(StrTemp))
						If StringToBoolean(GetFileLocalString(SCP294File, Drink, "Stomach Ache", "", False)) Then I_1025\State[3] = 1.0
						
						If StringToBoolean(GetFileLocalString(SCP294File, Drink, "Infection", "", False)) Then I_008\Timer = I_008\Timer + 1.0
						
						If StringToBoolean(GetFileLocalString(SCP294File, Drink, "Crystallization", "", False)) Then I_409\Timer = I_409\Timer + 1.0
						
						If me\DeathTimer = 0.0 Then
							me\DeathTimer = Int(GetFileLocalString(SCP294File, Drink, "Death Timer", "", False)) * 70.0
						Else
							me\DeathTimer = Min(me\DeathTimer, Int(GetFileLocalString(SCP294File, Drink, "Death Timer", "", False)) * 70.0)
						EndIf
						
						; ~ The state of refined items is more than 1.0 (fine setting increases it by 1, very fine doubles it)
						StrTemp = GetFileLocalString(SCP294File, Drink, "Blink Effect", "", False)
						If StrTemp <> "" Then me\BlinkEffect = Float(StrTemp) ^ SelectedItem\State
						StrTemp = GetFileLocalString(SCP294File, Drink, "Blink Effect Timer", "", False)
						If StrTemp <> "" Then me\BlinkEffectTimer = Float(StrTemp) * SelectedItem\State
						StrTemp = GetFileLocalString(SCP294File, Drink, "Stamina Effect", "", False)
						If StrTemp <> "" Then me\StaminaEffect = Float(StrTemp) ^ SelectedItem\State
						StrTemp = GetFileLocalString(SCP294File, Drink, "Stamina Effect Timer", "", False)
						If StrTemp <> "" Then me\StaminaEffectTimer = Float(StrTemp) * SelectedItem\State
						StrTemp = GetFileLocalString(SCP294File, Drink, "Refuse Message", "", False)
						If StrTemp <> "" Then
							CreateMsg(StrTemp)
						Else
							it.Items = CreateItem("Empty Cup", "emptycup", 0.0, 0.0, 0.0)
							it\Picked = True
							For i = 0 To MaxItemAmount - 1
								If Inventory(i) = SelectedItem Then
									Inventory(i) = it
									Exit
								EndIf
							Next
							EntityType(it\Collider, HIT_ITEM)
							
							RemoveItem(SelectedItem)
						EndIf
						SelectedItem = Null
					EndIf
					;[End Block]
				Case "syringe"
					;[Block]
					If CanUseItem(True, True) Then
						me\HealTimer = 30.0
						me\StaminaEffect = 0.5
						me\StaminaEffectTimer = 20.0
						
						CreateMsg(GetLocalString("msg", "syringe_1"))
						
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "finesyringe"
					;[Block]
					If CanUseItem(True, True) Then
						me\HealTimer = Rnd(20.0, 40.0)
						me\StaminaEffect = Rnd(0.4, 0.7)
						me\StaminaEffectTimer = Rnd(20.0, 30.0)
						
						CreateMsg(GetLocalString("msg", "syringe_2"))
						
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "veryfinesyringe"
					;[Block]
					If CanUseItem(True, True) Then
						Select Rand(3)
							Case 1
								;[Block]
								me\HealTimer = Rnd(40.0, 60.0)
								me\StaminaEffect = 0.1
								me\StaminaEffectTimer = 30.0
								CreateMsg(GetLocalString("msg", "syringe_3"))
								;[End Block]
							Case 2
								;[Block]
								chs\SuperMan = True
								CreateMsg(GetLocalString("msg", "syringe_4"))
								;[End Block]
							Case 3
								;[Block]
								me\VomitTimer = 30.0
								CreateMsg(GetLocalString("msg", "syringe_5"))
								;[End Block]
						End Select
						
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "radio", "18vradio", "fineradio", "veryfineradio"
					;[Block]
					If (Not SelectedItem\ItemTemplate\Img) Then
						SelectedItem\ItemTemplate\Img = LoadImage_Strict(SelectedItem\ItemTemplate\ImgPath)
						SelectedItem\ItemTemplate\Img = ScaleImage2(SelectedItem\ItemTemplate\Img, MenuScale, MenuScale)
						SelectedItem\ItemTemplate\ImgWidth = ImageWidth(SelectedItem\ItemTemplate\Img)
						SelectedItem\ItemTemplate\ImgHeight = ImageHeight(SelectedItem\ItemTemplate\Img)
						MaskImage(SelectedItem\ItemTemplate\Img, 255, 0, 255)
					EndIf
					
					If SelectedItem\ItemTemplate\TempName = "radio" Then SelectedItem\State = Max(0.0, SelectedItem\State - fps\Factor[0] * 0.006)
					If SelectedItem\ItemTemplate\TempName = "18vradio" Then SelectedItem\State = Max(0.0, SelectedItem\State - fps\Factor[0] * 0.003)
					
					; ~ RadioState[5] = Has the "use the number keys" -message been shown yet (True / False)
					; ~ RadioState[6] = A timer for the "code channel"
					; ~ RadioState[7] = Another timer for the "code channel"
					If SelectedItem\State > 0.0 Lor (SelectedItem\ItemTemplate\TempName = "fineradio" Lor SelectedItem\ItemTemplate\TempName = "veryfineradio") Then
						If RadioState[5] = 0.0 Then
							CreateMsg(GetLocalString("msg", "radio"))
							RadioState[5] = 1.0
							RadioState[0] = -1.0
						EndIf
						
						If PlayerRoom\RoomTemplate\Name = "dimension_106" Then
							For i = 0 To 4
								If ChannelPlaying(RadioCHN[i]) Then PauseChannel(RadioCHN[i])
							Next
							If ChannelPlaying(RadioCHN[6]) Then PauseChannel(RadioCHN[6])
							
							If (Not ChannelPlaying(RadioCHN[5])) Then
								RadioCHN[5] = PlaySound_Strict(RadioStatic)
								ResumeChannel(RadioCHN[5])
							EndIf
						ElseIf CoffinDistance < 8.0
							For i = 0 To 4
								If ChannelPlaying(RadioCHN[i]) Then PauseChannel(RadioCHN[i])
							Next
							If ChannelPlaying(RadioCHN[6]) Then PauseChannel(RadioCHN[6])
							
							If (Not ChannelPlaying(RadioCHN[5])) Then
								ResumeChannel(RadioCHN[5])
								RadioCHN[5] = PlaySound_Strict(RadioStatic895)
							EndIf
						Else
							Select Int(SelectedItem\State2)
								Case 0
									;[Block]
									If ChannelPlaying(RadioCHN[5]) Then PauseChannel(RadioCHN[5])
									
									If (Not opt\EnableUserTracks) Then
										If (Not ChannelPlaying(RadioCHN[0])) Then
											ResumeChannel(RadioCHN[0])
											RadioCHN[0] = PlaySound_Strict(RadioStatic)
										EndIf
									ElseIf UserTrackMusicAmount < 1
										If (Not ChannelPlaying(RadioCHN[0])) Then
											ResumeChannel(RadioCHN[0])
											RadioCHN[0] = PlaySound_Strict(RadioStatic)
										EndIf
									Else
										If (Not ChannelPlaying(RadioCHN[0])) Then
											ResumeChannel(RadioCHN[0])
											If (Not UserTrackFlag) Then
												If opt\UserTrackMode Then
													If RadioState[0] < (UserTrackMusicAmount - 1)
														RadioState[0] = RadioState[0] + 1.0
													Else
														RadioState[0] = 0.0
													EndIf
													UserTrackFlag = True
												Else
													RadioState[0] = Rand(0.0, UserTrackMusicAmount - 1)
												EndIf
											EndIf
											If CurrUserTrack <> 0 Then FreeSound_Strict(CurrUserTrack) : CurrUserTrack = 0
											CurrUserTrack = LoadSound_Strict("SFX\Radio\UserTracks\" + UserTrackName[RadioState[0]])
											RadioCHN[0] = PlaySound_Strict(CurrUserTrack)
										Else
											UserTrackFlag = False
										EndIf
										
										If KeyHit(2) Then
											PlaySound_Strict(RadioSquelch)
											If (Not UserTrackFlag) Then
												If opt\UserTrackMode Then
													If RadioState[0] < (UserTrackMusicAmount - 1)
														RadioState[0] = RadioState[0] + 1.0
													Else
														RadioState[0] = 0.0
													EndIf
													UserTrackFlag = True
												Else
													RadioState[0] = Rand(0.0, UserTrackMusicAmount - 1)
												EndIf
											EndIf
											If CurrUserTrack <> 0 Then FreeSound_Strict(CurrUserTrack) : CurrUserTrack = 0
											CurrUserTrack = LoadSound_Strict("SFX\Radio\UserTracks\" + UserTrackName[RadioState[0]])
											RadioCHN[0] = PlaySound_Strict(CurrUserTrack)
										EndIf
									EndIf
									;[End Block]
								Case 1
									;[Block]
									If ChannelPlaying(RadioCHN[5]) Then PauseChannel(RadioCHN[5])
									
									If (Not ChannelPlaying(RadioCHN[1])) Then
										ResumeChannel(RadioCHN[1])
										If RadioState[1] >= 5.0 Then
											RadioCHN[1] = PlaySound_Strict(RadioSFX(0, 1))
											RadioState[1] = 0.0
										Else
											RadioState[1] = RadioState[1] + 1.0
											RadioCHN[1] = PlaySound_Strict(RadioSFX(0, 0))
										EndIf
									EndIf
									;[End Block]
								Case 2
									;[Block]
									If ChannelPlaying(RadioCHN[5]) Then PauseChannel(RadioCHN[5])
									
									If (Not ChannelPlaying(RadioCHN[2])) Then
										ResumeChannel(RadioCHN[2])
										RadioState[2] = RadioState[2] + 1.0
										If RadioState[2] = 17.0 Then RadioState[2] = 1.0
										If Floor(RadioState[2] / 2.0) = Ceil(RadioState[2] / 2.0) Then
											RadioCHN[2] = PlaySound_Strict(RadioSFX(1, Int(RadioState[2] / 2.0)))
										Else
											RadioCHN[2] = PlaySound_Strict(RadioSFX(1, 0))
										EndIf
									EndIf
									;[End Block]
								Case 3
									;[Block]
									If ChannelPlaying(RadioCHN[5]) Then PauseChannel(RadioCHN[5])
									
									If (Not ChannelPlaying(RadioCHN[3])) Then
										ResumeChannel(RadioCHN[3])
										RadioCHN[3] = PlaySound_Strict(RadioStatic)
									EndIf
									
									If MTFTimer > 0.0 Then
										RadioState[3] = RadioState[3] + Max(Rand(-10, 1), 0.0)
										Select RadioState[3]
											Case 40
												;[Block]
												If (Not RadioState2[0]) Then
													RadioCHN[3] = PlaySound_Strict(LoadTempSound("SFX\Character\MTF\Random1.ogg"))
													RadioState[3] = RadioState[3] + 1.0
													RadioState2[0] = True
												EndIf
												;[End Block]
											Case 400
												;[Block]
												If (Not RadioState2[1]) Then
													RadioCHN[3] = PlaySound_Strict(LoadTempSound("SFX\Character\MTF\Random2.ogg"))
													RadioState[3] = RadioState[3] + 1.0
													RadioState2[1] = True
												EndIf
												;[End Block]
											Case 800
												;[Block]
												If (Not RadioState2[2]) Then
													RadioCHN[3] = PlaySound_Strict(LoadTempSound("SFX\Character\MTF\Random3.ogg"))
													RadioState[3] = RadioState[3] + 1.0
													RadioState2[2] = True
												EndIf
												;[End Block]
											Case 1200
												;[Block]
												If (Not RadioState2[3]) Then
													RadioCHN[3] = PlaySound_Strict(LoadTempSound("SFX\Character\MTF\Random4.ogg"))
													RadioState[3] = RadioState[3] + 1.0
													RadioState2[3] = True
												EndIf
												;[End Block]
											Case 1600
												;[Block]
												If (Not RadioState2[4]) Then
													RadioCHN[3] = PlaySound_Strict(LoadTempSound("SFX\Character\MTF\Random5.ogg"))
													RadioState[3] = RadioState[3] + 1.0
													RadioState2[4] = True
												EndIf
												;[End Block]
											Case 2000
												;[Block]
												If (Not RadioState2[5]) Then
													RadioCHN[3] = PlaySound_Strict(LoadTempSound("SFX\Character\MTF\Random6.ogg"))
													RadioState[3] = RadioState[3] + 1.0
													RadioState2[5] = True
												EndIf
												;[End Block]
											Case 2400
												;[Block]
												If (Not RadioState2[6]) Then
													RadioCHN[3] = PlaySound_Strict(LoadTempSound("SFX\Character\MTF\Random7.ogg"))
													RadioState[3] = RadioState[3] + 1.0
													RadioState2[6] = True
												EndIf
												;[End Block]
										End Select
									EndIf
									;[End Block]
								Case 4
									;[Block]
									If ChannelPlaying(RadioCHN[5]) Then PauseChannel(RadioCHN[5])
									
									If (Not ChannelPlaying(RadioCHN[6])) Then
										ResumeChannel(RadioCHN[6])
										RadioCHN[6] = PlaySound_Strict(RadioStatic)
									EndIf
									
									If (Not ChannelPlaying(RadioCHN[4])) Then
										ResumeChannel(RadioCHN[4])
										If (Not RemoteDoorOn) And RadioState[8] = 0 Then
											RadioCHN[4] = PlaySound_Strict(LoadTempSound("SFX\Radio\Chatter3.ogg"))
											RadioState[8] = 1
										Else
											RadioState[4] = RadioState[4] + Max(Rand(-10, 1), 0.0)
											
											Select RadioState[4]
												Case 10
													;[Block]
													If (Not n_I\Curr106\Contained) Then
														If (Not RadioState3[0]) Then
															RadioCHN[4] = PlaySound_Strict(LoadTempSound("SFX\Radio\OhGod.ogg"))
															RadioState[4] = RadioState[4] + 1.0
															RadioState3[0] = True
														EndIf
													EndIf
													;[End Block]
												Case 100
													;[Block]
													If (Not RadioState3[1]) Then
														RadioCHN[4] = PlaySound_Strict(LoadTempSound("SFX\Radio\Chatter2.ogg"))
														RadioState[4] = RadioState[4] + 1.0
														RadioState3[1] = True
													EndIf
													;[End Block]
												Case 158
													;[Block]
													If MTFTimer = 0.0 And (Not RadioState3[2]) Then
														RadioCHN[4] = PlaySound_Strict(LoadTempSound("SFX\Radio\Franklin1.ogg"))
														RadioState[4] = RadioState[4] + 1.0
														RadioState[2] = True
													EndIf
													;[End Block]
												Case 200
													;[Block]
													If (Not RadioState3[3]) Then
														RadioCHN[4] = PlaySound_Strict(LoadTempSound("SFX\Radio\Chatter4.ogg"))
														RadioState[4] = RadioState[4] + 1.0
														RadioState3[3] = True
													EndIf
													;[End Block]
												Case 260
													;[Block]
													If (Not RadioState3[4]) Then
														RadioCHN[4] = PlaySound_Strict(LoadTempSound("SFX\SCP\035\RadioHelp1.ogg"))
														RadioState[4] = RadioState[4] + 1.0
														RadioState3[4] = True
													EndIf
													;[End Block]
												Case 300
													;[Block]
													If (Not RadioState3[5]) Then
														RadioCHN[4] = PlaySound_Strict(LoadTempSound("SFX\Radio\Chatter1.ogg"))
														RadioState[4] = RadioState[4] + 1.0
														RadioState3[5] = True
													EndIf
													;[End Block]
												Case 350
													;[Block]
													If (Not RadioState3[6]) Then
														RadioCHN[4] = PlaySound_Strict(LoadTempSound("SFX\Radio\Franklin2.ogg"))
														RadioState[4] = RadioState[4] + 1.0
														RadioState3[6] = True
													EndIf
													;[End Block]
												Case 400
													;[Block]
													If (Not RadioState3[7]) Then
														RadioCHN[4] = PlaySound_Strict(LoadTempSound("SFX\SCP\035\RadioHelp2.ogg"))
														RadioState[4] = RadioState[4] + 1.0
														RadioState3[7] = True
													EndIf
													;[End Block]
												Case 450
													;[Block]
													If (Not RadioState3[8]) Then
														RadioCHN[4] = PlaySound_Strict(LoadTempSound("SFX\Radio\Franklin3.ogg"))
														RadioState[4] = RadioState[4] + 1.0
														RadioState3[8] = True
													EndIf
													;[End Block]
												Case 600
													;[Block]
													If (Not RadioState3[9]) Then
														RadioCHN[4] = PlaySound_Strict(LoadTempSound("SFX\Radio\Franklin4.ogg"))
														RadioState[4] = RadioState[4] + 1.0
														RadioState3[9] = True
													EndIf
													;[End Block]
											End Select
										EndIf
									EndIf
									;[End Block]
								Case 5
									;[Block]
									If (Not ChannelPlaying(RadioCHN[5])) Then
										ResumeChannel(RadioCHN[5])
										RadioCHN[5] = PlaySound_Strict(RadioStatic)
									EndIf
									;[End Block]
							End Select
							
							If SelectedItem\ItemTemplate\TempName = "veryfineradio" Then
								If ChannelPlaying(RadioCHN[5]) Then PauseChannel(RadioCHN[5])
								
								If (Not ChannelPlaying(RadioCHN[0])) Then
									ResumeChannel(RadioCHN[0])
									RadioCHN[0] = PlaySound_Strict(RadioStatic)
								EndIf
								RadioState[6] = RadioState[6] + fps\Factor[0]
								Temp = Mid(Str(CODE_DR_MAYNARD), RadioState[8] + 1.0, 1)
								If RadioState[6] - fps\Factor[0] <= RadioState[7] * 50.0 And RadioState[6] > RadioState[7] * 50.0 Then
									PlaySound_Strict(RadioBuzz)
									RadioState[7] = RadioState[7] + 1.0
									If RadioState[7] >= Temp Then
										RadioState[7] = 0.0
										RadioState[6] = -100.0
										RadioState[8] = RadioState[8] + 1.0
										If RadioState[8] = 4.0 Then RadioState[8] = 0.0 : RadioState[6] = -200.0
									EndIf
								EndIf
							Else
								For i = 2 To 6
									If KeyHit(i) Then
										If SelectedItem\State2 <> i - 2 Then
											PlaySound_Strict(RadioSquelch)
											PauseChannel(RadioCHN[Int(SelectedItem\State2)])
										EndIf
										SelectedItem\State2 = i - 2
										ResumeChannel(RadioCHN[SelectedItem\State2])
									EndIf
								Next
							EndIf
						EndIf
						
						If SelectedItem\ItemTemplate\TempName = "radio" Lor SelectedItem\ItemTemplate\TempName = "18vradio" Then
							If SelectedItem\State <= 20.0 Then
								UpdateBatteryTimer()
								If BatMsgTimer >= 70.0 * 1.0 Then
									If (Not ChannelPlaying(LowBatteryCHN[0])) Then LowBatteryCHN[0] = PlaySound_Strict(LowBatterySFX[0])
								EndIf
							EndIf
						EndIf
					EndIf
					;[End Block]
				Case "cigarette"
					;[Block]
					If CanUseItem(True) Then
						Select Rand(6)
							Case 1
								;[Block]
								CreateMsg(GetLocalString("msg", "cigarette_1"))
								;[End Block]
							Case 2
								;[Block]
								CreateMsg(GetLocalString("msg", "cigarette_2"))
								;[End Block]
							Case 3
								;[Block]
								CreateMsg(GetLocalString("msg", "cigarette_3"))
								;[End Block]
							Case 4
								;[Block]
								CreateMsg(GetLocalString("msg", "cigarette_4"))
								;[End Block]
							Case 5
								;[Block]
								CreateMsg(GetLocalString("msg", "cigarette_5"))
								;[End Block]
							Case 6
								;[Block]
								CreateMsg(GetLocalString("msg", "cigarette_6"))
								;[End Block]
						End Select
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "scp420j"
					;[Block]
					If CanUseItem(True) Then
						If I_714\Using > 1 Then
							CreateMsg(GetLocalString("msg", "420j.no"))
						Else
							CreateMsg(GetLocalString("msg", "420j.yeah"))
							me\Injuries = Max(me\Injuries - 0.5, 0.0)
							me\BlurTimer = 500.0
							GiveAchievement(Achv420_J)
							PlaySound_Strict(LoadTempSound("SFX\Music\Using420J.ogg"))
						EndIf
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "joint", "scp420s"
					;[Block]
					If CanUseItem(True) Then
						If I_714\Using > 1 Then
							CreateMsg(GetLocalString("msg", "420j.no"))
						Else
							CreateMsg(GetLocalString("msg", "420j.dead"))
							msg\DeathMsg = Format(GetLocalString("death", "joint"), SubjectName)
							Kill()
						EndIf
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "scp714", "coarse714"
					;[Block]
						If CanUseItem(True, True)
							If (I_714\Using > 1 And (SelectedItem\ItemTemplate\TempName = "scp714" Lor SelectedItem\ItemTemplate\TempName = "coarse714")) Then
								CreateMsg(GetLocalString("msg", "714.off"))
								I_714\Using = 1 ; 1 is actually off! Done so I didn't need several If statements for sanity stuff
							Else
								If SelectedItem\ItemTemplate\TempName = "scp714" Then
									I_714\Using = 3
								Else
									I_714\Using = 2
								EndIf
								CreateMsg(GetLocalString("msg", "714.on"))
								GiveAchievement(Achv714)
							EndIf
						EndIf
						SelectedItem = Null
					;[End Block]
				Case "kill714", "ring"
					;[Block]
						If CanUseItem(True, True)
							If SelectedItem\ItemTemplate\TempName = "kill714" Then
								CreateMsg(GetLocalString("msg", "714.dead"))
								Kill()
								msg\DeathMsg = Format(GetLocalString("death", "ring"), SubjectName)
							Else
								CreateMsg(GetLocalString("msg", "714.small"))
							EndIf
						EndIf
						SelectedItem = Null
					;[End Block]
				Case "hazmatsuit", "finehazmatsuit", "veryfinehazmatsuit", "hazmatsuit148"
					;[Block]
					If wi\BallisticVest = 0 Then
						me\CurrSpeed = CurveValue(0.0, me\CurrSpeed, 5.0)
						
						SelectedItem\State = Min(SelectedItem\State + (fps\Factor[0] / 3.75), 100.0)
						
						If SelectedItem\State = 100.0 Then
							If wi\HazmatSuit > 0 Then
								CreateMsg(GetLocalString("msg", "suit.off"))
								wi\HazmatSuit = 0
								DropItem(SelectedItem)
							Else
								If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
								Select SelectedItem\ItemTemplate\TempName
									Case "hazmatsuit"
										;[Block]
										CreateMsg(GetLocalString("msg", "suit.on"))
										wi\HazmatSuit = 1
										;[End Block]
									Case "finehazmatsuit"
										;[Block]
										CreateMsg(GetLocalString("msg", "suit.on.dry"))
										wi\HazmatSuit = 2
										;[End Block]
									Case "veryfinehazmatsuit"
										;[Block]
										CreateMsg(GetLocalString("msg", "suit.on.easy"))
										wi\HazmatSuit = 3
										;[End Block]
									Case "hazmatsuit148"
										;[Block]
										CreateMsg(GetLocalString("msg", "suit.on.dry"))
										wi\HazmatSuit = 4
										;[End Block]
								End Select
								If wi\NightVision > 0 Then opt\CameraFogFar = opt\StoredCameraFogFar : wi\NightVision = 0
								wi\GasMask = 0 : wi\BallisticHelmet = False : wi\SCRAMBLE = 0
								I_427\Using = False : I_714\Using = 1 : I_1499\Using = 0
							EndIf
							SelectedItem\State = 0.0
							SelectedItem = Null
						EndIf
					EndIf
					;[End Block]
				Case "vest", "finevest"
					;[Block]
					me\CurrSpeed = CurveValue(0.0, me\CurrSpeed, 5.0)
					
					SelectedItem\State = Min(SelectedItem\State + (fps\Factor[0] / (2.0 + (0.5 * (SelectedItem\ItemTemplate\TempName = "finevest")))), 100)
					
					If SelectedItem\State = 100.0 Then
						If wi\BallisticVest > 0 Then
							CreateMsg(GetLocalString("msg", "vest.off"))
							wi\BallisticVest = 0
							DropItem(SelectedItem)
						Else
							If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
							Select SelectedItem\ItemTemplate\TempName
								Case "vest"
									;[Block]
									CreateMsg(GetLocalString("msg", "vest.on.slight"))
									wi\BallisticVest = 1
									;[End Block]
								Case "finevest"
									;[Block]
									CreateMsg(GetLocalString("msg", "vest.on.heavy"))
									wi\BallisticVest = 2
									;[End Block]
							End Select
						EndIf
						SelectedItem\State = 0.0
						SelectedItem = Null
					EndIf
					;[End Block]
				Case "gasmask", "finegasmask", "veryfinegasmask", "gasmask148"
					;[Block]
						me\CurrSpeed = CurveValue(0.0, me\CurrSpeed, 5.0)
						
						SelectedItem\State = Min(SelectedItem\State + (fps\Factor[0]) / 1.5, 100.0)
						
						If SelectedItem\State = 100.0 Then
							If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
							
							If (wi\GasMask = 1 And SelectedItem\ItemTemplate\TempName = "gasmask") Lor (wi\GasMask = 2 And SelectedItem\ItemTemplate\TempName = "finegasmask") Lor (wi\GasMask = 3 And SelectedItem\ItemTemplate\TempName = "veryfinegasmask") Lor (wi\GasMask = 4 And SelectedItem\ItemTemplate\TempName = "gasmask148") Then
								CreateMsg(GetLocalString("msg", "mask.off"))
								wi\GasMask = 0
							Else
								wi\SCRAMBLE = 0 : wi\BallisticHelmet = False
								If wi\NightVision > 0 Then opt\CameraFogFar = opt\StoredCameraFogFar : wi\NightVision = 0
								Select SelectedItem\ItemTemplate\TempName
									Case "gasmask"
										;[Block]
										CreateMsg(GetLocalString("msg", "mask.on"))
										wi\GasMask = 1
										;[End Block]
									Case "finegasmask"
										;[Block]
										CreateMsg(GetLocalString("msg", "mask.on.dry"))
										wi\GasMask = 2
										;[End Block]
									Case "veryfinegasmask"
										;[Block]
										CreateMsg(GetLocalString("msg", "mask.on.easy"))
										wi\GasMask = 3
										;[End Block]
									Case "gasmask148"
										;[Block]
										CreateMsg(GetLocalString("msg", "mask.on.dry"))
										wi\GasMask = 4
										;[End Block]
								End Select
							EndIf
							SelectedItem\State = 0.0
							SelectedItem = Null
						EndIf
					;[End Block]
				Case "nav", "nav310", "navulti", "nav300"
					;[Block]
					If SelectedItem\ItemTemplate\TempName = "navulti" Lor SelectedItem\ItemTemplate\TempName = "nav300" Then
						If (Not SelectedItem\ItemTemplate\Img) Then
							SelectedItem\ItemTemplate\Img = LoadImage_Strict(SelectedItem\ItemTemplate\ImgPath)
							SelectedItem\ItemTemplate\Img = ScaleImage2(SelectedItem\ItemTemplate\Img, MenuScale, MenuScale)
							SelectedItem\ItemTemplate\ImgWidth = ImageWidth(SelectedItem\ItemTemplate\Img) / 2
							SelectedItem\ItemTemplate\ImgHeight = ImageHeight(SelectedItem\ItemTemplate\Img) / 2
							MaskImage(SelectedItem\ItemTemplate\Img, 255, 0, 255)
						EndIf
					Else
						If SelectedItem\ItemTemplate\TempName = "nav" Then SelectedItem\State = Max(0.0, SelectedItem\State - fps\Factor[0] * 0.01)
						If SelectedItem\ItemTemplate\TempName = "nav310" Then SelectedItem\State = Max(0.0, SelectedItem\State - fps\Factor[0] * 0.005)
						
						If SelectedItem\State > 0.0 And SelectedItem\State <= 20.0 Then
							UpdateBatteryTimer()
							If BatMsgTimer >= 70.0 * 1.0 Then
								If (Not ChannelPlaying(LowBatteryCHN[0])) Then LowBatteryCHN[0] = PlaySound_Strict(LowBatterySFX[0])
							EndIf
						EndIf
					EndIf
					;[End Block]
				Case "scp1499", "fine1499"
					;[Block]
					If PreventItemOverlapping(True) Then
						
						me\CurrSpeed = CurveValue(0.0, me\CurrSpeed, 5.0)
						
						SelectedItem\State = Min(SelectedItem\State + fps\Factor[0] / 1.5, 100.0)
						
						If SelectedItem\State = 100.0 Then
							If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
							
							If I_1499\Using > 0 Then
								CreateMsg(GetLocalString("msg", "mask.off"))
								I_1499\Using = 0
							Else
								Select SelectedItem\ItemTemplate\TempName
									Case "scp1499"
										;[Block]
										CreateMsg(GetLocalString("msg", "mask.on"))
										I_1499\Using = 1
										;[End Block]
									Case "fine1499"
										;[Block]
										CreateMsg(GetLocalString("msg", "mask.on.easy"))
										I_1499\Using = 2
										;[End Block]
								End Select
								GiveAchievement(Achv1499)
								For r.Rooms = Each Rooms
									If r\RoomTemplate\Name = "dimension_1499" Then
										me\BlinkTimer = -1.0
										I_1499\PrevRoom = PlayerRoom
										I_1499\PrevX = EntityX(me\Collider)
										I_1499\PrevY = EntityY(me\Collider)
										I_1499\PrevZ = EntityZ(me\Collider)
										
										If I_1499\x = 0.0 And I_1499\y = 0.0 And I_1499\z = 0.0 Then
											PositionEntity(me\Collider, r\x + 6086.0 * RoomScale, r\y + 304.0 * RoomScale, r\z + 2292.5 * RoomScale)
											RotateEntity(me\Collider, 0.0, 90.0, 0.0, True)
										Else
											PositionEntity(me\Collider, I_1499\x, I_1499\y + 0.05, I_1499\z)
										EndIf
										ResetEntity(me\Collider)
										TeleportToRoom(r)
										PlaySound_Strict(LoadTempSound("SFX\SCP\1499\Enter.ogg"))
										I_1499\x = 0.0
										I_1499\y = 0.0
										I_1499\z = 0.0
										If n_I\Curr096 <> Null Then
											If n_I\Curr096\SoundCHN <> 0 Then SetStreamVolume_Strict(n_I\Curr096\SoundCHN, 0.0)
										EndIf
										For e.Events = Each Events
											If e\EventID = e_dimension_1499 Then
												If EntityDistanceSquared(e\room\OBJ, me\Collider) > PowTwo(8300.0 * RoomScale) Then
													If e\EventState2 < 5.0 Then e\EventState2 = e\EventState2 + 1.0
												EndIf
												Exit
											EndIf
										Next
										Exit
									EndIf
								Next
							EndIf
							SelectedItem\State = 0.0
							SelectedItem = Null
						EndIf
					EndIf
					;[End Block]
				Case "badge"
					;[Block]
					If (Not SelectedItem\ItemTemplate\Img) Then
						SelectedItem\ItemTemplate\Img = LoadImage_Strict(SelectedItem\ItemTemplate\ImgPath)
						SelectedItem\ItemTemplate\Img = ScaleImage2(SelectedItem\ItemTemplate\Img, MenuScale, MenuScale)
						SelectedItem\ItemTemplate\ImgWidth = ImageWidth(SelectedItem\ItemTemplate\Img) / 2
						SelectedItem\ItemTemplate\ImgHeight = ImageHeight(SelectedItem\ItemTemplate\Img) / 2
					EndIf
					
					If SelectedItem\State = 0.0 Then
						PlaySound_Strict(LoadTempSound("SFX\SCP\1162_ARC\NostalgiaCancer" + Rand(6, 10) + ".ogg"))
						SelectedItem\State = 1.0
					EndIf
					;[End Block]
				Case "oldbadge"
					;[Block]
					If (Not SelectedItem\ItemTemplate\Img) Then
						SelectedItem\ItemTemplate\Img = LoadImage_Strict(SelectedItem\ItemTemplate\ImgPath)
						SelectedItem\ItemTemplate\Img = ScaleImage2(SelectedItem\ItemTemplate\Img, MenuScale, MenuScale)
						SelectedItem\ItemTemplate\ImgWidth = ImageWidth(SelectedItem\ItemTemplate\Img) / 2
						SelectedItem\ItemTemplate\ImgHeight = ImageHeight(SelectedItem\ItemTemplate\Img) / 2
						MaskImage(SelectedItem\ItemTemplate\Img, 255, 0, 255)
					EndIf
					
					If SelectedItem\State = 0.0 Then
						CreateMsg(GetLocalString("msg", "oldbadge"))
						PlaySound_Strict(LoadTempSound("SFX\SCP\1162_ARC\NostalgiaCancer" + Rand(6, 10) + ".ogg"))
						SelectedItem\State = 1.0
					EndIf
					;[End Block]
				Case "key"
					;[Block]
					If SelectedItem\State = 0.0 Then
						PlaySound_Strict(LoadTempSound("SFX\SCP\1162_ARC\NostalgiaCancer" + Rand(6, 10) + ".ogg"))
						CreateMsg(GetLocalString("msg", "lostkey"))
						SelectedItem\State = 1.0
					EndIf
					;[End Block]
				Case "oldpaper"
					;[Block]
					If (Not SelectedItem\ItemTemplate\Img) Then
						SelectedItem\ItemTemplate\Img = LoadImage_Strict(SelectedItem\ItemTemplate\ImgPath)
						SelectedItem\ItemTemplate\Img = ScaleImage2(SelectedItem\ItemTemplate\Img, MenuScale, MenuScale)
						SelectedItem\ItemTemplate\ImgWidth = ImageWidth(SelectedItem\ItemTemplate\Img) / 2
						SelectedItem\ItemTemplate\ImgHeight = ImageHeight(SelectedItem\ItemTemplate\Img) / 2
					EndIf
					
					If SelectedItem\State = 0.0 Then
						me\BlurTimer = 1000.0
						CreateMsg(GetLocalString("msg", "oldpaper"))
						PlaySound_Strict(LoadTempSound("SFX\SCP\1162_ARC\NostalgiaCancer" + Rand(6, 10) + ".ogg"))
						SelectedItem\State = 1.0
					EndIf
					;[End Block]
				Case "coin"
					;[Block]
					If SelectedItem\State = 0.0 Then
						PlaySound_Strict(LoadTempSound("SFX\SCP\1162_ARC\NostalgiaCancer" + Rand(5) + ".ogg"))
						SelectedItem\State = 1.0
					EndIf
					;[End Block]
				Case "scp427"
					;[Block]
					If I_427\Using Then
						CreateMsg(GetLocalString("msg", "427.off"))
						I_427\Using = False
					Else
						GiveAchievement(Achv427)
						CreateMsg(GetLocalString("msg", "427.on"))
						I_427\Using = True
					EndIf
					SelectedItem = Null
					;[End Block]
				Case "pill"
					;[Block]
					If CanUseItem(True) Then
						CreateMsg(GetLocalString("msg", "pill"))
						I_1025\State[0] = 0.0
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "scp500pilldeath"
					;[Block]
					If CanUseItem(True) Then
						CreateMsg(GetLocalString("msg", "pill"))
						
						If I_427\Timer < 70.0 * 360.0 Then I_427\Timer = 70.0 * 360.0
						
						RemoveItem(SelectedItem)
					EndIf
					;[End Block]
				Case "syringeinf"
					;[Block]
					CreateMsg(GetLocalString("msg", "syringe_6"))
					
					me\VomitTimer = 70.0 * 1.0
					
					I_008\Timer = I_008\Timer + (1.0 + (1.0 * SelectedDifficulty\AggressiveNPCs))
					RemoveItem(SelectedItem)
					;[End Block]
				Case "helmet"
					;[Block]
						me\CurrSpeed = CurveValue(0.0, me\CurrSpeed, 5.0)
						
						SelectedItem\State = Min(SelectedItem\State + fps\Factor[0], 100.0)
						
						If SelectedItem\State = 100.0 Then
							If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
							
							If wi\BallisticHelmet Then
								CreateMsg(GetLocalString("msg", "helmet.off"))
								wi\BallisticHelmet = False
							Else
								CreateMsg(GetLocalString("msg", "helmet.on"))
								wi\GasMask = 0 : wi\SCRAMBLE = 0
								If wi\NightVision > 0 Then opt\CameraFogFar = opt\StoredCameraFogFar : wi\NightVision = 0
								wi\BallisticHelmet = True
							EndIf
							SelectedItem\State = 0.0
							SelectedItem = Null
						EndIf
					;[End Block]
				Case "scramble", "finescramble"
					;[Block]
						me\CurrSpeed = CurveValue(0.0, me\CurrSpeed, 5.0)
						
						SelectedItem\State3 = Min(SelectedItem\State3 + (fps\Factor[0] / 1.5), 100.0)
						
						If SelectedItem\State3 = 100.0 Then
							If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
							
							If (wi\SCRAMBLE = 1 And SelectedItem\ItemTemplate\TempName = "scramble") Lor (wi\SCRAMBLE = 2 And SelectedItem\ItemTemplate\TempName = "finescramble") Then
								CreateMsg(GetLocalString("msg", "gear.off"))
								wi\SCRAMBLE = 0
							Else
								wi\GasMask = 0 : wi\BallisticHelmet = False
								If wi\NightVision > 0 Then opt\CameraFogFar = opt\StoredCameraFogFar : wi\NightVision = 0
								CreateMsg(GetLocalString("msg", "gear.on"))
								Select SelectedItem\ItemTemplate\TempName
									Case "scramble"
										;[Block]
										wi\SCRAMBLE = 1
										;[End Block]
									Case "finescramble"
										;[Block]
										wi\SCRAMBLE = 2
										;[End Block]
								End Select
							EndIf
							SelectedItem\State3 = 0.0
							SelectedItem = Null
						EndIf
					;[End Block]
				Case "scp500"
					;[Block]
					If I_500\Taken < 3 Then
						If ItemAmount < MaxItemAmount Then
							For i = 0 To MaxItemAmount - 1
								If Inventory(i) = Null Then
									Inventory(i) = CreateItem("SCP-500-01", "scp500pill", 0.0, 0.0, 0.0)
									Inventory(i)\Picked = True
									Inventory(i)\Dropped = -1
									Inventory(i)\ItemTemplate\Found = True
									HideEntity(Inventory(i)\Collider)
									EntityType(Inventory(i)\Collider, HIT_ITEM)
									EntityParent(Inventory(i)\Collider, 0)
									Exit
								EndIf
							Next
							CreateMsg(GetLocalString("msg", "500"))
							I_500\Taken = I_500\Taken + 1
						Else
							CreateMsg(GetLocalString("msg", "cantcarry"))
						EndIf
						SelectedItem = Null
					Else
						CreateMsg(GetLocalString("msg", "500empty"))
						SelectedItem = Null
					EndIf
					;[End Block]
				Case "scp1123"
					;[Block]
					Use1123()
					SelectedItem = Null
					;[End Block]
				Case "key0", "key1", "key2", "key3", "key4", "key5", "key6", "keyomni", "scp860", "hand", "hand2", "25ct", "scp005", "key", "coin", "scp588", "mastercard", "paper"
					;[Block]
					; ~ Skip this line
					;[End Block]
				Default
					;[Block]
					; ~ Check if the item is an inventory-type object
					If SelectedItem\InvSlots > 0 Then OtherOpen = SelectedItem
					mo\DoubleClick = False
					mo\MouseHit1 = False
					mo\MouseDown1 = False
					mo\LastMouseHit1 = False
					SelectedItem = Null
					;[End Block]
			End Select
			
			If mo\MouseHit2 Then
				Select SelectedItem\ItemTemplate\TempName
					Case "firstaid", "finefirstaid", "firstaid2", "scp1499", "fine1499", "gasmask", "finegasmask", "veryfinegasmask", "gasmask148", "helmet"
						;[Block]
						SelectedItem\State = 0.0
						;[End Block]
					Case "vest", "finevest"
						;[Block]
						SelectedItem\State = 0.0
						If (Not wi\BallisticVest) Then DropItem(SelectedItem, False)
						;[End Block]
					Case "hazmatsuit", "finehazmatsuit", "veryfinehazmatsuit", "hazmatsuit148"
						;[Block]
						SelectedItem\State = 0.0
						If wi\HazmatSuit = 0 Then DropItem(SelectedItem, False)
						;[End Block]
					Case "nvg", "veryfinenvg", "finenvg", "scramble", "finescramble", "scp1025"
						;[Block]
						SelectedItem\State3 = 0.0
						;[End Block]
				End Select
				If SelectedItem\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
				SelectedItem = Null
			EndIf
		Else
			For i = 0 To 6
				If ChannelPlaying(RadioCHN[i]) Then PauseChannel(RadioCHN[i])
			Next
			
			If ChannelPlaying(LowBatteryCHN[0]) Then StopChannel(LowBatteryCHN[0]) : LowBatteryCHN[0] = 0
		EndIf
	EndIf
	
	If SelectedDifficulty\InventorySlots > 3 Then
		For i = 0 To (MaxItemAmount / 2) - 1
			If KeyHit(i + 2) Then
				If OtherOpen = Null And SelectedScreen = Null And (Not InvOpen) And (Not I_294\Using) And (Not MenuOpen) And (Not ConsoleOpen) Then
					If me\Playable And (Not me\Zombie) And (Not me\Terminated) And me\SelectedEnding = -1 Then
						If SelectedItem = Inventory(i) Then
							If SelectedItem <> Null Then PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
							SelectedItem = Null
						ElseIf SelectedItem = Null And Inventory(i) <> Null
							SelectedItem = Inventory(i)
							PlaySound_Strict(PickSFX[SelectedItem\ItemTemplate\Sound])
						EndIf
					EndIf
				EndIf
			EndIf
		Next
	EndIf
	
	For it.Items = Each Items
		If it <> SelectedItem Then
			Select it\ItemTemplate\TempName
				Case "firstaid", "finefirstaid", "firstaid2", "vest", "finevest", "hazmatsuit", "finehazmatsuit", "veryfinehazmatsuit", "hazmatsuit148", "scp1499", "fine1499", "gasmask", "finegasmask", "veryfinegasmask", "gasmask148", "helmet"
					;[Block]
					it\State = 0.0
					;[End Block]
				Case "nvg", "veryfinenvg", "finenvg", "scramble", "finescramble", "scp1025"
					;[Block]
					it\State3 = 0.0
					;[End Block]
			End Select
		EndIf
	Next
	
	If PrevInvOpen And (Not InvOpen) Then MoveMouse(mo\Viewport_Center_X, mo\Viewport_Center_Y)
	
	CatchErrors("UpdateGUI")
End Function

Function RenderHUD%()
	If me\Terminated Lor (Not me\Playable) Then Return
	
	Local x%, y%, Width%, Height%, WalkIconID%, BlinkIconID%
	Local i%
	
	Width = 200 * MenuScale
	Height = 20 * MenuScale
	x = 80 * MenuScale
	y = opt\GraphicHeight - (95 * MenuScale)
	
	Color(255, 255, 255)
	If (I_714\Using > 1 Lor wi\HazmatSuit > 0) And TakeOffTimer < 500.0 Then
		For i = 0 To MaxItemAmount - 1
			If Inventory(i) <> Null Then
				If Instr(Inventory(i)\ItemTemplate\TempName, "hazmatsuit") Then
					If Inventory(i)\State2 < 3.0 And wi\HazmatSuit = 4 Then
						Color(0, 200, 0)
						Rect(x - (53 * MenuScale), y - (43 * MenuScale), 36 * MenuScale, 36 * MenuScale)
					EndIf
				EndIf
			EndIf
		Next
		Color(255, 255, 255)
		Rect(x - (51 * MenuScale), y - (41 * MenuScale), 32 * MenuScale, 32 * MenuScale, False)
		If TakeOffTimer < 125.0 Then
			RenderBar(t\ImageID[1], x, y - (40 * MenuScale), Width, Height, TakeOffTimer, 500.0, 100, 0, 0)
		Else
			RenderBar(BlinkMeterIMG, x, y - (40 * MenuScale), Width, Height, TakeOffTimer, 500.0)
		EndIf
		DrawBlock(t\IconID[7], x - (50 * MenuScale), y - (40 * MenuScale))
	EndIf
	
	Color(255, 255, 255)
	If me\BlinkTimer < 150.0 Then
		RenderBar(t\ImageID[1], x, y, Width, Height, me\BlinkTimer, me\BLINKFREQ, 100, 0, 0)
	Else
		RenderBar(BlinkMeterIMG, x, y, Width, Height, me\BlinkTimer, me\BLINKFREQ)
	EndIf
	Color(0, 0, 0)
	Rect(x - (50 * MenuScale), y, 30 * MenuScale, 30 * MenuScale)
	
	If me\BlurTimer > 550.0 Lor me\BlinkEffect > 1.0 Lor me\LightFlash > 0.0 Lor (((me\LightBlink > 0.0 And (Not chs\NoBlink)) Lor me\EyeIrritation > 0.0) And wi\NightVision = 0) Then
		Color(200, 0, 0)
		Rect(x - (53 * MenuScale), y - (3 * MenuScale), 36 * MenuScale, 36 * MenuScale)
	ElseIf me\BlinkEffect < 1.0 Lor chs\NoBlink
		Color(0, 200, 0)
		Rect(x - (53 * MenuScale), y - (3 * MenuScale), 36 * MenuScale, 36 * MenuScale)
	EndIf
	
	Color(255, 255, 255)
	Rect(x - (51 * MenuScale), y - MenuScale, 32 * MenuScale, 32 * MenuScale, False)
	
	If me\BlinkTimer < 0.0
		BlinkIconID = 4
	Else
		BlinkIconID = 3
	EndIf
	DrawBlock(t\IconID[BlinkIconID], x - (50 * MenuScale), y)
	
	y = opt\GraphicHeight - (55 * MenuScale)
	
	If me\Stamina <= 25.0 Then
		RenderBar(t\ImageID[3], x, y, Width, Height, me\Stamina, 100.0, 50, 0, 0)
	Else
		RenderBar(t\ImageID[2], x, y, Width, Height, me\Stamina, 100.0, 50, 50, 50)
	EndIf
	Color(0, 0, 0)
	Rect(x - (50 * MenuScale), y, 30 * MenuScale, 30 * MenuScale)
	
	If PlayerRoom\RoomTemplate\Name = "dimension_106" Lor I_714\Using > 1 Lor me\Injuries >= 1.5 Lor me\StaminaEffect > 1.0 Lor wi\HazmatSuit = 1 Lor wi\BallisticVest = 2 Lor I_409\Timer >= 55.0 Lor I_1025\State[0] > 0.0 Then
		Color(200, 0, 0)
		Rect(x - (53 * MenuScale), y - (3 * MenuScale), 36 * MenuScale, 36 * MenuScale)
	ElseIf chs\InfiniteStamina Lor me\StaminaEffect < 1.0 Lor wi\GasMask > 1 Lor I_1499\Using = 2 Lor wi\HazmatSuit = 3
		Color(0, 200, 0)
		Rect(x - (53 * MenuScale), y - (3 * MenuScale), 36 * MenuScale, 36 * MenuScale)
	EndIf
	
	Color(255, 255, 255)
	Rect(x - (51 * MenuScale), y - MenuScale, 32 * MenuScale, 32 * MenuScale, False)
	If me\Crouch Then
		WalkIconID = 2
	ElseIf (KeyDown(key\SPRINT) And (Not InvOpen) And OtherOpen = Null) And me\CurrSpeed > 0.0 And (Not chs\NoClip) And me\Stamina > 0.0 Then
		WalkIconID = 1
	Else
		WalkIconID = 0
	EndIf
	DrawBlock(t\IconID[WalkIconID], x - (50 * MenuScale), y)
End Function

Function RenderDebugHUD%()
	Local ev.Events, ch.Chunk
	Local x%, y%, i%
	
	x = 20 * MenuScale
	y = 40 * MenuScale
	
	Color(255, 255, 255)
	SetFont2(fo\FontID[Font_Console])
	
	Select chs\DebugHUD
		Case 1
			;[Block]
			Text2(x, y, Format(GetLocalString("misc", "room"), PlayerRoom\RoomTemplate\Name))
			Text2(x, y + (20 * MenuScale), Format(Format(Format(GetLocalString("console", "debug_1.xyz"), Floor(EntityX(PlayerRoom\OBJ) / 8.0 + 0.5), "{0}"), Floor(EntityZ(PlayerRoom\OBJ) / 8.0 + 0.5), "{1}"), PlayerRoom\Angle, "{2}"))
			For ev.Events = Each Events
				If ev\room = PlayerRoom Then
					Text2(x, y + (40 * MenuScale), Format(Format(GetLocalString("console", "debug_1.event"), ev\EventName, "{0}"), ev\EventID, "{1}"))
					Text2(x, y + (60 * MenuScale), Format(GetLocalString("console", "debug_1.state_1"), ev\EventState))
					Text2(x, y + (80 * MenuScale), Format(GetLocalString("console", "debug_1.state_2"), ev\EventState2))
					Text2(x, y + (100 * MenuScale), Format(GetLocalString("console", "debug_1.state_3"), ev\EventState3))
					Text2(x, y + (120 * MenuScale), Format(GetLocalString("console", "debug_1.state_4"), ev\EventState4))
					Text2(x, y + (140 * MenuScale), Format(GetLocalString("console", "debug_1.str"), ev\EventStr))
					Exit
				EndIf
			Next
			If PlayerRoom\RoomTemplate\Name = "dimension_1499" Then
				Text2(x, y + (180 * MenuScale), Format(Format(GetLocalString("console", "debug_1.chunkxyz"), (Int((EntityX(me\Collider) + 20) / 40)), "{0}"), (Int((EntityZ(me\Collider) + 20) / 40)), "{1}"))
				
				Local CH_Amount% = 0
				
				For ch.Chunk = Each Chunk
					CH_Amount = CH_Amount + 1
				Next
				Text2(x, y + (200 * MenuScale), Format(GetLocalString("console", "debug_1.currchunk"), CH_Amount))
			Else
				Text2(x, y + (200 * MenuScale), Format(Format(Format(GetLocalString("console", "debug_1.currroom"), PlayerRoom\x, "{0}"), PlayerRoom\y, "{1}"), PlayerRoom\z, "{2}"))
			EndIf
			
			If sc_I\SelectedMonitor <> Null Then
				Text2(x, y + (240 * MenuScale), Format(GetLocalString("console", "debug_1.currmon"), sc_I\SelectedMonitor\ScrOBJ))
			Else
				Text2(x, y + (240 * MenuScale), Format(GetLocalString("console", "debug_1.currmon"), "Null"))
			EndIf
			
			If SelectedItem <> Null Then
				Text2(x, y + (280 * MenuScale), Format(GetLocalString("console", "debug_1.currbtn"), SelectedItem\ItemTemplate\Name))
			Else
				Text2(x, y + (280 * MenuScale), Format(GetLocalString("console", "debug_1.currbtn"), "Null"))
			EndIf
			
			Text2(x, y + (320 * MenuScale), Format(GetLocalString("console", "debug_1.currflo"), PlayerElevatorFloor))
			Text2(x, y + (340 * MenuScale), Format(GetLocalString("console", "debug_1.roomflo"), ToElevatorFloor))
			If PlayerInsideElevator Then
				Text2(x, y + (360 * MenuScale), Format(GetLocalString("console", "debug_1.inelev"), "True"))
			Else
				Text2(x, y + (360 * MenuScale), Format(GetLocalString("console", "debug_1.inelev"), "False"))
			EndIf
			
			Text2(x, y + (400 * MenuScale), Format(Format(GetLocalString("console", "debug_1.time"), CurrentDate(), "{0}"), CurrentTime(), "{1}"))
			Text2(x, y + (420 * MenuScale), Format(Format(GetLocalString("console", "debug_1.vidmem"), ((TotalVidMem() / 1024) - (AvailVidMem() / 1024)), "{0}"), (TotalVidMem() / 1024), "{1}"))
			Text2(x, y + (440 * MenuScale), Format(Format(GetLocalString("console", "debug_1.glomem"), ((TotalPhys() / 1024) - (AvailPhys() / 1024)), "{0}"), (TotalPhys() / 1024), "{1}"))
			Text2(x, y + (460 * MenuScale), Format(GetLocalString("console", "debug_1.triamo"), CurrTrisAmount))
			Text2(x, y + (480 * MenuScale), Format(GetLocalString("console", "debug_1.acttex"), ActiveTextures()))
			;[End Block]
		Case 2
			;[Block]
			Text2(x, y, Format(Format(Format(GetLocalString("console", "debug_2.ppos"), FloatToString(EntityX(me\Collider), 1), "{0}"), FloatToString(EntityY(me\Collider), 1), "{1}"), FloatToString(EntityZ(me\Collider), 1), "{2}"))
			Text2(x, y + (20 * MenuScale), Format(Format(Format(GetLocalString("console", "debug_2.proa"), FloatToString(EntityPitch(me\Collider), 1), "{0}"), FloatToString(EntityYaw(me\Collider), 1), "{1}"), FloatToString(EntityRoll(me\Collider), 1), "{2}"))
			
			Text2(x, y + (60 * MenuScale), Format(GetLocalString("console", "debug_2.injuries"), me\Injuries))
			Text2(x, y + (80 * MenuScale), Format(GetLocalString("console", "debug_2.bloodloss"), me\Bloodloss))
			
			Text2(x, y + (120 * MenuScale), Format(GetLocalString("console", "debug_2.blur"), me\BlurTimer))
			Text2(x, y + (140 * MenuScale), Format(GetLocalString("console", "debug_2.blink"), me\LightBlink))
			Text2(x, y + (160 * MenuScale), Format(GetLocalString("console", "debug_2.flash"), me\LightFlash))
			
			Text2(x, y + (200 * MenuScale), Format(GetLocalString("console", "debug_2.freq"), me\BLINKFREQ))
			Text2(x, y + (220 * MenuScale), Format(GetLocalString("console", "debug_2.timer"), me\BlinkTimer))
			Text2(x, y + (240 * MenuScale), Format(GetLocalString("console", "debug_2.effect"), me\BlinkEffect))
			Text2(x, y + (260 * MenuScale), Format(GetLocalString("console", "debug_2.efftim"), me\BlinkEffectTimer))
			Text2(x, y + (280 * MenuScale), Format(GetLocalString("console", "debug_2.eyeirr"), me\EyeIrritation))
			Text2(x, y + (300 * MenuScale), Format(GetLocalString("console", "debug_2.eyestuck"), me\EyeStuck))
			
			Text2(x, y + (340 * MenuScale), Format(GetLocalString("console", "debug_2.stamina"), me\Stamina))
			Text2(x, y + (360 * MenuScale), Format(GetLocalString("console", "debug_2.stameff"), me\StaminaEffect))
			Text2(x, y + (380 * MenuScale), Format(GetLocalString("console", "debug_2.stamtimer"), me\StaminaEffectTimer))
			
			Text2(x, y + (420 * MenuScale), Format(GetLocalString("console", "debug_2.sanity"), me\Sanity))
			
			Text2(x, y + (460 * MenuScale), Format(GetLocalString("console", "debug_2.deaf"), me\DeafTimer))
			
			If me\Terminated Then
				Text2(x + (380 * MenuScale), y, Format(GetLocalString("console", "debug_2.terminated"), "True"))
			Else
				Text2(x + (380 * MenuScale), y, Format(GetLocalString("console", "debug_2.terminated"), "False"))
			EndIf
			
			Text2(x + (380 * MenuScale), y + (20 * MenuScale), Format(GetLocalString("console", "debug_2.death"), me\DeathTimer))
			Text2(x + (380 * MenuScale), y + (40 * MenuScale), Format(GetLocalString("console", "debug_2.fall"), me\FallTimer))
			
			Text2(x + (380 * MenuScale), y + (80 * MenuScale), Format(GetLocalString("console", "debug_2.heal"), me\HealTimer))
			
			Text2(x + (380 * MenuScale), y + (100 * MenuScale), Format(GetLocalString("console", "debug_2.quickheal"), me\QuickHealTimer))
			
			Text2(x + (380 * MenuScale), y + (140 * MenuScale), Format(GetLocalString("console", "debug_2.heartbeat"), me\HeartBeatTimer))
			
			Text2(x + (380 * MenuScale), y + (180 * MenuScale), Format(GetLocalString("console", "debug_2.explosion"), me\ExplosionTimer))
			
			Text2(x + (380 * MenuScale), y + (220 * MenuScale), Format(GetLocalString("console", "debug_2.speed"), me\CurrSpeed))
			
			Text2(x + (380 * MenuScale), y + (260 * MenuScale), Format(GetLocalString("console", "debug_2.camshake"), me\CameraShakeTimer))
			Text2(x + (380 * MenuScale), y + (280 * MenuScale), Format(GetLocalString("console", "debug_2.camzoom"), me\CurrCameraZoom))
			
			Text2(x + (380 * MenuScale), y + (320 * MenuScale), Format(GetLocalString("console", "debug_2.vomit"), me\VomitTimer))
			
			If me\Playable Then
				Text2(x + (380 * MenuScale), y + (360 * MenuScale), Format(GetLocalString("console", "debug_2.playable"), "True"))
			Else
				Text2(x + (380 * MenuScale), y + (360 * MenuScale), Format(GetLocalString("console", "debug_2.playable"), "False"))
			EndIf
			
			Text2(x + (380 * MenuScale), y + (400 * MenuScale), Format(GetLocalString("console", "debug_2.refitems"), me\RefinedItems))
			Text2(x + (380 * MenuScale), y + (420 * MenuScale), Format(GetLocalString("console", "debug_2.funds"), me\Funds))
			Text2(x + (380 * MenuScale), y + (440 * MenuScale), Format(GetLocalString("console", "debug_2.escape"), me\EscapeTimer))
			;[End Block]
		Case 3
			;[Block]
			If n_I\Curr049 <> Null Then
				Text2(x, y, Format(Format(Format(GetLocalString("console", "debug_3.049pos"), FloatToString(EntityX(n_I\Curr049\OBJ), 2), "{0}"), FloatToString(EntityY(n_I\Curr049\OBJ), 2), "{1}"), FloatToString(EntityZ(n_I\Curr049\OBJ), 2), "{2}"))
				Text2(x, y + (20 * MenuScale), Format(GetLocalString("console", "debug_3.049idle"), n_I\Curr049\Idle))
				Text2(x, y + (40 * MenuScale), Format(GetLocalString("console", "debug_3.049state"), n_I\Curr049\State))
			EndIf
			If n_I\Curr096 <> Null Then
				Text2(x, y + (60 * MenuScale), Format(Format(Format(GetLocalString("console", "debug_3.096pos"), FloatToString(EntityX(n_I\Curr096\OBJ), 2), "{0}"), FloatToString(EntityY(n_I\Curr096\OBJ), 2), "{1}"), FloatToString(EntityZ(n_I\Curr096\OBJ), 2), "{2}"))
				Text2(x, y + (80 * MenuScale), Format(GetLocalString("console", "debug_3.096idle"), n_I\Curr096\Idle))
				Text2(x, y + (100 * MenuScale), Format(GetLocalString("console", "debug_3.096state"), n_I\Curr096\State))
			EndIf
			If n_I\Curr106 <> Null Then
				Text2(x, y + (120 * MenuScale), Format(Format(Format(GetLocalString("console", "debug_3.106pos"), FloatToString(EntityX(n_I\Curr106\OBJ), 2), "{0}"), FloatToString(EntityY(n_I\Curr106\OBJ), 2), "{1}"), FloatToString(EntityZ(n_I\Curr106\OBJ), 2), "{2}"))
				Text2(x, y + (140 * MenuScale), Format(GetLocalString("console", "debug_3.106idle"), n_I\Curr106\Idle))
				Text2(x, y + (160 * MenuScale), Format(GetLocalString("console", "debug_3.106state"), n_I\Curr106\State))
			EndIf
			If n_I\Curr173 <> Null Then
				Text2(x, y + (180 * MenuScale), Format(Format(Format(GetLocalString("console", "debug_3.173pos"), FloatToString(EntityX(n_I\Curr173\OBJ), 2), "{0}"), FloatToString(EntityY(n_I\Curr173\OBJ), 2), "{1}"), FloatToString(EntityZ(n_I\Curr173\OBJ), 2), "{2}"))
				Text2(x, y + (200 * MenuScale), Format(GetLocalString("console", "debug_3.173idle"), n_I\Curr173\Idle))
				Text2(x, y + (220 * MenuScale), Format(GetLocalString("console", "debug_3.173state"), n_I\Curr173\State))
			EndIf
			
			Text2(x, y + (260 * MenuScale), Format(GetLocalString("console", "debug_3.pill"), I_500\Taken))
			
			Text2(x, y + (300 * MenuScale), Format(GetLocalString("console", "debug_3.008"), I_008\Timer))
			Text2(x, y + (320 * MenuScale), Format(GetLocalString("console", "debug_3.409"), I_409\Timer))
			Text2(x, y + (340 * MenuScale), Format(GetLocalString("console", "debug_3.427"), Int(I_427\Timer / 70.0)))
			For i = 0 To 7
				Text2(x, y + ((360 + (20 * i)) * MenuScale), Format(Format(GetLocalString("console", "debug_3.1025"), i, "{0}"), I_1025\State[i], "{1}"))
			Next
			
			If I_005\ChanceToSpawn = 1 Then
				Text2(x, y + (540 * MenuScale), GetLocalString("console", "debug_3.005.chamber"))
			ElseIf I_005\ChanceToSpawn >= 2 And I_005\ChanceToSpawn =< 4
				Text2(x, y + (540 * MenuScale), GetLocalString("console", "debug_3.005.409"))
			ElseIf I_005\ChanceToSpawn >= 5 And I_005\ChanceToSpawn =< 10
				Text2(x, y + (540 * MenuScale), GetLocalString("console", "debug_3.005.maynard"))
			EndIf
			;[End Block]
	End Select
	SetFont2(fo\FontID[Font_Default])
End Function

Function RenderGUI%()
	CatchErrors("Uncaught (RenderGUI)")
	
	Local e.Events, it.Items, a_it.Items
	Local Temp%, x%, y%, z%, i%, YawValue#, PitchValue#
	Local x1#, x2#, x3#, y1#, y2#, y3#, z2#, ProjY#, Scale#, Pvt%
	Local n%, xTemp%, yTemp%, StrTemp$
	Local Width%, Height%
	
	If MenuOpen Lor ConsoleOpen Lor d_I\SelectedDoor <> Null Lor InvOpen Lor OtherOpen <> Null Lor me\EndingTimer < 0.0 Then
		ShowPointer()
	Else
		HidePointer()
	EndIf
	
	If PlayerRoom\RoomTemplate\Name = "dimension_106" Then
		For e.Events = Each Events
			If e\room = PlayerRoom Then
				If e\EventState2 = PD_ThroneRoom Then
					If me\BlinkTimer > -16.0 And me\BlinkTimer < -6.0 Then
						If (Not e\Img) Then
							StopChannel(e\SoundCHN) : e\SoundCHN = 0
							If Rand(30) = 1 Then PlaySound_Strict(e\Sound2)
							e\Img = LoadImage_Strict("GFX\Overlays\kneel_mortal.png")
							e\Img = ScaleImage2(e\Img, MenuScale, MenuScale)
						Else
							DrawBlock(e\Img, mo\Viewport_Center_X - (Rand(390, 310) * MenuScale), mo\Viewport_Center_Y - (Rand(290, 310) * MenuScale))
							If (Not ChannelPlaying(e\SoundCHN)) Then e\SoundCHN = PlaySound_Strict(e\Sound)
						EndIf
					Else
						If e\Img <> 0 Then FreeImage(e\Img) : e\Img = 0
						StopChannel(e\SoundCHN) : e\SoundCHN = 0
					EndIf
				EndIf
				Exit
			EndIf
		Next
	EndIf
	
	If I_294\Using Then Render294()
	
	If SelectedDifficulty\Name <> "Apollyon" And opt\HUDEnabled Then
		If (Not InvOpen) And (Not I_294\Using) And OtherOpen = Null And d_I\SelectedDoor = Null And SelectedScreen = Null And (Not MenuOpen) And (Not ConsoleOpen) Then
			
			If d_I\ClosestButton <> 0 Then
				Temp = CreatePivot()
				PositionEntity(Temp, EntityX(Camera), EntityY(Camera), EntityZ(Camera))
				PointEntity(Temp, d_I\ClosestButton)
				YawValue = WrapAngle(EntityYaw(Camera) - EntityYaw(Temp))
				If YawValue > 90.0 And YawValue <= 180.0 Then YawValue = 90.0
				If YawValue > 180.0 And YawValue < 270.0 Then YawValue = 270.0
				PitchValue = WrapAngle(EntityPitch(Camera) - EntityPitch(Temp))
				If PitchValue > 90.0 And PitchValue <= 180.0 Then PitchValue = 90.0
				If PitchValue > 180.0 And PitchValue < 270.0 Then PitchValue = 270.0
				
				FreeEntity(Temp)
				
				DrawBlock(t\IconID[5], mo\Viewport_Center_X + Sin(YawValue) * (opt\GraphicWidth / 3) - (32 * MenuScale), mo\Viewport_Center_Y - Sin(PitchValue) * (opt\GraphicHeight / 3) - (32 * MenuScale))
			EndIf
	
			If ClosestItem <> Null And (Not me\Terminated) Then
				YawValue = -DeltaYaw(Camera, ClosestItem\Collider)
				If YawValue > 90.0 And YawValue <= 180.0 Then YawValue = 90.0
				If YawValue > 180.0 And YawValue < 270.0 Then YawValue = 270.0
				PitchValue = -DeltaPitch(Camera, ClosestItem\Collider)
				If PitchValue > 90.0 And PitchValue <= 180.0 Then PitchValue = 90.0
				If PitchValue > 180.0 And PitchValue < 270.0 Then PitchValue = 270.0
				Text(mo\Viewport_Center_X + Sin(YawValue) * (opt\GraphicWidth / 3), mo\Viewport_Center_Y - Sin(PitchValue) * (opt\GraphicHeight / 3) - (64 * MenuScale), ClosestItem\ItemTemplate\Name, True, False)
				DrawBlock(t\IconID[6], mo\Viewport_Center_X + Sin(YawValue) * (opt\GraphicWidth / 3) - (32 * MenuScale), mo\Viewport_Center_Y - Sin(PitchValue) * (opt\GraphicHeight / 3) - (32 * MenuScale))
			EndIf
			
			If ga\DrawHandIcon Then DrawBlock(t\IconID[5], mo\Viewport_Center_X - (32 * MenuScale), mo\Viewport_Center_Y - (32 * MenuScale))
			For i = 0 To 3
				If ga\DrawArrowIcon[i] Then
					x = mo\Viewport_Center_X - (32 * MenuScale)
					y = mo\Viewport_Center_Y - (32 * MenuScale)
					Select i
						Case 0
							;[Block]
							y = y - (69 * MenuScale)
							;[End Block]
						Case 1
							;[Block]
							x = x + (69 * MenuScale)
							;[End Block]
						Case 2
							;[Block]
							y = y + (69 * MenuScale)
							;[End Block]
						Case 3
							;[Block]
							x = x - (69 * MenuScale)
							;[End Block]
					End Select
					DrawBlock(t\IconID[5], x, y)
					Color(0, 0, 0)
					Rect(x + (4 * MenuScale), y + (4 * MenuScale), 56 * MenuScale, 56 * MenuScale)
					DrawBlock(ga\ArrowIMG[i], x + (21 * MenuScale), y + (21 * MenuScale))
				EndIf
			Next
				
		EndIf
	
		RenderHUD()
	
	EndIf
	If chs\DebugHUD <> 0 Then RenderDebugHUD()
	
	If SelectedScreen <> Null Then
		DrawBlock(SelectedScreen\Img, mo\Viewport_Center_X - ImageWidth(SelectedScreen\Img) / 2, mo\Viewport_Center_Y - ImageHeight(SelectedScreen\Img) / 2)
		
		If mo\MouseUp1 Lor mo\MouseHit2 Then FreeImage(SelectedScreen\Img) : SelectedScreen\Img = 0
	EndIf
	
	Local PrevInvOpen% = InvOpen, MouseSlot% = 66
	Local ShouldDrawHUD% = True
	
	If d_I\SelectedDoor <> Null Then
		If SelectedItem <> Null Then
			If SelectedItem\ItemTemplate\TempName = "scp005" Then ShouldDrawHUD = False
		EndIf
		If ShouldDrawHUD Then
			Pvt = CreatePivot()
			PositionEntity(Pvt, EntityX(d_I\ClosestButton, True), EntityY(d_I\ClosestButton, True), EntityZ(d_I\ClosestButton, True))
			RotateEntity(Pvt, 0.0, EntityYaw(d_I\ClosestButton, True) - 180.0, 0.0)
			MoveEntity(Pvt, 0.0, 0.0, 0.22)
			PositionEntity(Camera, EntityX(Pvt), EntityY(Pvt), EntityZ(Pvt))
			PointEntity(Camera, d_I\ClosestButton)
			FreeEntity(Pvt)
			
			CameraProject(Camera, EntityX(d_I\ClosestButton, True), EntityY(d_I\ClosestButton, True) + (MeshHeight(d_I\ButtonModelID[BUTTON_DEFAULT_MODEL]) * 0.015), EntityZ(d_I\ClosestButton, True))
			ProjY = ProjectedY()
			CameraProject(Camera, EntityX(d_I\ClosestButton, True), EntityY(d_I\ClosestButton, True) - (MeshHeight(d_I\ButtonModelID[BUTTON_DEFAULT_MODEL]) * 0.015), EntityZ(d_I\ClosestButton, True))
			Scale = (ProjectedY() - ProjY) / 462.0
			
			x = mo\Viewport_Center_X - ImageWidth(t\ImageID[4]) * (Scale / 2)
			y = mo\Viewport_Center_Y - ImageHeight(t\ImageID[4]) * (Scale / 2)
			
			SetFont2(fo\FontID[Font_Digital])
			If msg\KeyPadMsg <> "" Then
				If (msg\KeyPadTimer Mod 70.0) < 35.0 Then Text2(mo\Viewport_Center_X, y + (124 * MenuScale * Scale), msg\KeyPadMsg, True, True)
			Else
				Text2(mo\Viewport_Center_X, y + (70 * MenuScale * Scale), GetLocalString("msg", "accesscode"), True, True)
				SetFont2(fo\FontID[Font_Digital_Big])
				Text2(mo\Viewport_Center_X, y + (124 * MenuScale * Scale), msg\KeyPadInput, True, True)
			EndIf
			If opt\DisplayMode = 0 Then DrawImage(CursorIMG, ScaledMouseX(), ScaledMouseY())
		EndIf
	EndIf
	
	Local PrevOtherOpen.Items
	Local OtherSize%, OtherAmount%
	Local IsEmpty%
	Local IsMouseOn%
	Local ClosedInv%
	Local INVENTORY_GFX_SIZE% = 70 * MenuScale
	Local INVENTORY_GFX_SPACING% = 35 * MenuScale
	Local InvImgSize% = (64 * MenuScale) / 2
	
	If OtherOpen <> Null Then
		PrevOtherOpen = OtherOpen
		OtherSize = OtherOpen\InvSlots
		
		For i = 0 To OtherSize - 1
			If OtherOpen\SecondInv[i] <> Null Then OtherAmount = OtherAmount + 1
		Next
		
		Local TempX% = 0
		
		x = mo\Viewport_Center_X - ((INVENTORY_GFX_SIZE * 10 / 2) + (INVENTORY_GFX_SPACING * ((10 / 2) - 1))) / 2
		y = mo\Viewport_Center_Y - (INVENTORY_GFX_SIZE * ((OtherSize / 10 * 2) - 1)) - INVENTORY_GFX_SPACING
		
		IsMouseOn = -1
		For n = 0 To OtherSize - 1
			If MouseOn(x, y, INVENTORY_GFX_SIZE, INVENTORY_GFX_SIZE) Then IsMouseOn = n
			
			If IsMouseOn = n Then
				MouseSlot = n
				Color(255, 0, 0)
				Rect(x - MenuScale, y - MenuScale, INVENTORY_GFX_SIZE + (2 * MenuScale), INVENTORY_GFX_SIZE + (2 * MenuScale))
			EndIf
			
			RenderFrame(x, y, INVENTORY_GFX_SIZE, INVENTORY_GFX_SIZE, (x Mod 64), (x Mod 64))
			
			If OtherOpen = Null Then Exit
			
			If OtherOpen\SecondInv[n] <> Null Then
				If (IsMouseOn = n Lor SelectedItem <> OtherOpen\SecondInv[n]) Then DrawBlock(OtherOpen\SecondInv[n]\InvImg, x + (INVENTORY_GFX_SIZE / 2) - (32 * MenuScale), y + (INVENTORY_GFX_SIZE / 2) - (32 * MenuScale))
			EndIf
			If OtherOpen\SecondInv[n] <> Null And SelectedItem <> OtherOpen\SecondInv[n] Then
				If IsMouseOn = n Then
					Color(255, 255, 255)
					Text2(x + (INVENTORY_GFX_SIZE / 2), y + INVENTORY_GFX_SIZE + INVENTORY_GFX_SPACING - (15 * MenuScale), OtherOpen\SecondInv[n]\ItemTemplate\DisplayName, True)
				EndIf
			EndIf
			
			x = x + INVENTORY_GFX_SIZE + INVENTORY_GFX_SPACING
			TempX = TempX + 1
			If TempX = 5 Then
				TempX = 0
				y = y + (INVENTORY_GFX_SIZE * 2)
				x = mo\Viewport_Center_X - ((INVENTORY_GFX_SIZE * 10 / 2) + (INVENTORY_GFX_SPACING * ((10 / 2) - 1))) / 2
			EndIf
		Next
		
		If SelectedItem <> Null Then
			If mo\MouseDown1 Then
				If MouseSlot = 66 Then
					DrawBlock(SelectedItem\InvImg, ScaledMouseX() - InvImgSize, ScaledMouseY() - InvImgSize)
				ElseIf SelectedItem <> PrevOtherOpen\SecondInv[MouseSlot]
					DrawBlock(SelectedItem\InvImg, ScaledMouseX() - InvImgSize, ScaledMouseY() - InvImgSize)
				EndIf
			EndIf
		EndIf
		
		If opt\DisplayMode = 0 Then DrawImage(CursorIMG, ScaledMouseX(), ScaledMouseY())
	ElseIf InvOpen Then
		x = mo\Viewport_Center_X - ((INVENTORY_GFX_SIZE * MaxItemAmount / 2) + (INVENTORY_GFX_SPACING * ((MaxItemAmount / 2) - 1))) / 2
		y = mo\Viewport_Center_Y - INVENTORY_GFX_SIZE - INVENTORY_GFX_SPACING
		
		If MaxItemAmount = 2 Then
			y = y + INVENTORY_GFX_SIZE
			x = x - ((INVENTORY_GFX_SIZE * MaxItemAmount / 2) + INVENTORY_GFX_SPACING) / 2
		EndIf
		
		IsMouseOn = -1
		For n = 0 To MaxItemAmount - 1
			If MouseOn(x, y, INVENTORY_GFX_SIZE, INVENTORY_GFX_SIZE) Then IsMouseOn = n
			
			If Inventory(n) <> Null Then
				Local ShouldDrawRect% = False
				
				Color(200, 200, 200)
				Select Inventory(n)\ItemTemplate\TempName 
					Case "gasmask"
						;[Block]
						If wi\GasMask = 1 Then ShouldDrawRect = True
						;[End Block]
					Case "finegasmask"
						;[Block]
						If wi\GasMask = 2 Then ShouldDrawRect = True
						;[End Block]
					Case "veryfinegasmask"
						;[Block]
						If wi\GasMask = 3 Then ShouldDrawRect = True
						;[End Block]
					Case "gasmask148"
						;[Block]
						If wi\GasMask = 4 Then ShouldDrawRect = True
						;[End Block]
					Case "hazmatsuit"
						;[Block]
						If wi\HazmatSuit = 1 Then ShouldDrawRect = True
						;[End Block]
					Case "finehazmatsuit"
						;[Block]
						If wi\HazmatSuit = 2 Then ShouldDrawRect = True
						;[End Block]
					Case "veryfinehazmatsuit"
						;[Block]
						If wi\HazmatSuit = 3 Then ShouldDrawRect = True
						;[End Block]
					Case "hazmatsuit148"
						;[Block]"
						If wi\HazmatSuit = 4 Then ShouldDrawRect = True
						;[End Block]
					Case "vest"
						;[Block]
						If wi\BallisticVest = 1 Then ShouldDrawRect = True
						;[End Block]
					Case "finevest"
						;[Block]
						If wi\BallisticVest = 2 Then ShouldDrawRect = True
						;[End Block]
					Case "helmet"
						;[Block]
						If wi\BallisticHelmet Then ShouldDrawRect = True
						;[End Block]
					Case "scp714"
						;[Block]
						If I_714\Using = 3 Then ShouldDrawRect = True
						;[End Block]
					Case "coarse714"
						;[Block]
						If I_714\Using = 2 Then ShouldDrawRect = True
						;[End Block]
					Case "nvg"
						;[Block]
						If wi\NightVision = 1 Then ShouldDrawRect = True
						;[End Block]
					Case "veryfinenvg"
						;[Block]
						If wi\NightVision = 2 Then ShouldDrawRect = True
						;[End Block]
					Case "finenvg"
						;[Block]
						If wi\NightVision = 3 Then ShouldDrawRect = True
						;[End Block]
					Case "scramble"
						;[Block]
						If wi\SCRAMBLE = 1 Then ShouldDrawRect = True
						;[End Block]
					Case "finescramble"
						;[Block]
						If wi\SCRAMBLE = 2 Then ShouldDrawRect = True
						;[End Block]
					Case "scp1499"
						;[Block]
						If I_1499\Using = 1 Then ShouldDrawRect = True
						;[End Block]
					Case "fine1499"
						;[Block]
						If I_1499\Using = 2 Then ShouldDrawRect = True
						;[End Block]
					Case "scp427"
						;[Block]
						If I_427\Using Then ShouldDrawRect = True
						;[End Block]
				End Select
				If ShouldDrawRect Then Rect(x - (3 * MenuScale), y - (3 * MenuScale), INVENTORY_GFX_SIZE + (6 * MenuScale), INVENTORY_GFX_SIZE + (6 * MenuScale))
			EndIf
			
			If IsMouseOn = n Then
				MouseSlot = n
				Color(255, 0, 0)
				Rect(x - MenuScale, y - MenuScale, INVENTORY_GFX_SIZE + (2 * MenuScale), INVENTORY_GFX_SIZE + (2 * MenuScale))
			EndIf
			
			Color(255, 255, 255)
			RenderFrame(x, y, INVENTORY_GFX_SIZE, INVENTORY_GFX_SIZE, (x Mod 64), (x Mod 64))
			
			If Inventory(n) <> Null Then
				If IsMouseOn = n Lor SelectedItem <> Inventory(n) Then
					DrawBlock(Inventory(n)\InvImg, x + (INVENTORY_GFX_SIZE / 2) - (32 * MenuScale), y + (INVENTORY_GFX_SIZE / 2) - (32 * MenuScale))
					SetFont(fo\FontID[Font_Default])
					If n < MaxItemAmount / 2 Then Text(x + (32 * MenuScale), y - (9 * MenuScale), n + 1, True, True)
				EndIf
			EndIf
			
			If Inventory(n) <> Null And SelectedItem <> Inventory(n) Then
				If IsMouseOn = n Then
					If SelectedItem = Null Then
						SetFont2(fo\FontID[Font_Default])
						Color(255, 255, 255)
						Text2(x + (INVENTORY_GFX_SIZE / 2), y + INVENTORY_GFX_SIZE + INVENTORY_GFX_SPACING - (15 * MenuScale), Inventory(n)\DisplayName, True)
					EndIf
				EndIf
			EndIf
			
			x = x + INVENTORY_GFX_SIZE + INVENTORY_GFX_SPACING
			If MaxItemAmount >= 4 And n = (MaxItemAmount / 2) - 1 Then
				y = y + (INVENTORY_GFX_SIZE * 2)
				x = mo\Viewport_Center_X - ((INVENTORY_GFX_SIZE * MaxItemAmount / 2) + (INVENTORY_GFX_SPACING * ((MaxItemAmount / 2) - 1))) / 2
			EndIf
		Next
		
		If SelectedItem <> Null Then
			If mo\MouseDown1 Then
				If MouseSlot = 66 Then
					DrawBlock(SelectedItem\InvImg, ScaledMouseX() - InvImgSize, ScaledMouseY() - InvImgSize)
				ElseIf SelectedItem <> Inventory(MouseSlot)
					DrawBlock(SelectedItem\InvImg, ScaledMouseX() - InvImgSize, ScaledMouseY() - InvImgSize)
				EndIf
			EndIf
		EndIf
		
		If opt\DisplayMode = 0 Then DrawImage(CursorIMG, ScaledMouseX(), ScaledMouseY())
	Else
		If SelectedItem <> Null Then
			Select SelectedItem\ItemTemplate\TempName
				Case "nvg", "veryfinenvg", "finenvg", "scramble", "finescramble"
					;[Block]
					If PreventItemOverlapping() Then
						
						DrawBlock(SelectedItem\ItemTemplate\InvImg, mo\Viewport_Center_X - InvImgSize, mo\Viewport_Center_Y - InvImgSize)
						
						Width = 300 * MenuScale
						Height = 20 * MenuScale
						x = mo\Viewport_Center_X - (Width / 2)
						y = mo\Viewport_Center_Y + (80 * MenuScale)
						
						RenderBar(BlinkMeterIMG, x, y, Width, Height, SelectedItem\State3)
					EndIf
					;[End Block]
				Case "key0", "key1", "key2", "key3", "key4", "key5", "key6", "keyomni", "scp860", "hand", "hand2", "25ct", "scp005", "key", "coin", "scp588", "mastercard"
					;[Block]
					DrawBlock(SelectedItem\ItemTemplate\InvImg, mo\Viewport_Center_X - InvImgSize, mo\Viewport_Center_Y - InvImgSize)
					;[End Block]
				Case "firstaid", "finefirstaid", "firstaid2"
					;[Block]
					If (me\Bloodloss <> 0.0 Lor me\Injuries <> 0.0) And wi\HazmatSuit = 0 Then
						DrawBlock(SelectedItem\ItemTemplate\InvImg, mo\Viewport_Center_X - InvImgSize, mo\Viewport_Center_Y - InvImgSize)
						
						Width = 300 * MenuScale
						Height = 20 * MenuScale
						x = mo\Viewport_Center_X - (Width / 2)
						y = mo\Viewport_Center_Y + (80 * MenuScale)
						
						RenderBar(BlinkMeterIMG, x, y, Width, Height, SelectedItem\State)
					EndIf
					;[End Block]
				Case "paper", "oldpaper"
					;[Block]
					If (Not SelectedItem\ItemTemplate\Img) Then
						Select SelectedItem\ItemTemplate\Name
							Case "Burnt Note" 
								;[Block]
								SelectedItem\ItemTemplate\Img = LoadImage_Strict(ItemHUDTexturePath + "note_Maynard.png")
								SelectedItem\ItemTemplate\Img = ScaleImage2(SelectedItem\ItemTemplate\Img, MenuScale, MenuScale)
								SetBuffer(ImageBuffer(SelectedItem\ItemTemplate\Img))
								Color(0, 0, 0)
								SetFont2(fo\FontID[Font_Default])
								Text2(277 * MenuScale, 469 * MenuScale, CODE_DR_MAYNARD, True, True)
								SetBuffer(BackBuffer())
								;[End Block]
							Case "Unknown Note"
								;[Block]
								SelectedItem\ItemTemplate\Img = LoadImage_Strict(SelectedItem\ItemTemplate\ImgPath)
								SelectedItem\ItemTemplate\Img = ScaleImage2(SelectedItem\ItemTemplate\Img, MenuScale, MenuScale)
								
								SetBuffer(ImageBuffer(SelectedItem\ItemTemplate\Img))
								Color(50, 50, 50)
								SetFont2(fo\FontID[Font_Journal])
								Text2(300 * MenuScale, 295 * MenuScale, CODE_O5_COUNCIL, True, True)
								SetBuffer(BackBuffer())
								;[End Block]
							Case "Document SCP-372"
								;[Block]
								SelectedItem\ItemTemplate\Img = LoadImage_Strict(SelectedItem\ItemTemplate\ImgPath)
								SelectedItem\ItemTemplate\Img = ScaleImage2(SelectedItem\ItemTemplate\Img, MenuScale, MenuScale)
								
								SetBuffer(ImageBuffer(SelectedItem\ItemTemplate\Img))
								Color(37, 45, 137)
								SetFont2(fo\FontID[Font_Journal])
								Text2(383 * MenuScale, 734 * MenuScale, CODE_MAINTENANCE_TUNNELS, True, True)
								SetBuffer(BackBuffer())
								;[End Block]
							Default 
								;[Block]
								SelectedItem\ItemTemplate\Img = LoadImage_Strict(SelectedItem\ItemTemplate\ImgPath)
								SelectedItem\ItemTemplate\Img = ScaleImage2(SelectedItem\ItemTemplate\Img, MenuScale, MenuScale)
								;[End Block]
						End Select
						SelectedItem\ItemTemplate\ImgWidth = ImageWidth(SelectedItem\ItemTemplate\Img) / 2
						SelectedItem\ItemTemplate\ImgHeight = ImageHeight(SelectedItem\ItemTemplate\Img) / 2
					EndIf
					DrawBlock(SelectedItem\ItemTemplate\Img, mo\Viewport_Center_X - SelectedItem\ItemTemplate\ImgWidth, mo\Viewport_Center_Y - SelectedItem\ItemTemplate\ImgHeight)
					;[End Block]
				Case "scp1025"
					;[Block]
					GiveAchievement(Achv1025)
					If (Not SelectedItem\ItemTemplate\Img) Then
						SelectedItem\ItemTemplate\Img = LoadImage_Strict(ItemHUDTexturePath + "page_1025(" + (Int(SelectedItem\State) + 1) + ").png")
						SelectedItem\ItemTemplate\Img = ScaleImage2(SelectedItem\ItemTemplate\Img, MenuScale, MenuScale)
						SelectedItem\ItemTemplate\ImgWidth = ImageWidth(SelectedItem\ItemTemplate\Img) / 2
						SelectedItem\ItemTemplate\ImgHeight = ImageHeight(SelectedItem\ItemTemplate\Img) / 2
					EndIf
					DrawBlock(SelectedItem\ItemTemplate\Img, mo\Viewport_Center_X - SelectedItem\ItemTemplate\ImgWidth, mo\Viewport_Center_Y - SelectedItem\ItemTemplate\ImgHeight)
					;[End Block]
				Case "radio", "18vradio", "fineradio", "veryfineradio"
					;[Block]
					; ~ RadioState[5] = Has the "use the number keys" -message been shown yet (True / False)
					; ~ RadioState[6] = A timer for the "code channel"
					; ~ RadioState[7] = Another timer for the "code channel"
					
					If (Not SelectedItem\ItemTemplate\Img) Then
						SelectedItem\ItemTemplate\Img = LoadImage_Strict(SelectedItem\ItemTemplate\ImgPath)
						SelectedItem\ItemTemplate\Img = ScaleImage2(SelectedItem\ItemTemplate\Img, MenuScale, MenuScale)
						SelectedItem\ItemTemplate\ImgWidth = ImageWidth(SelectedItem\ItemTemplate\Img)
						SelectedItem\ItemTemplate\ImgHeight = ImageHeight(SelectedItem\ItemTemplate\Img)
						MaskImage(SelectedItem\ItemTemplate\Img, 255, 0, 255)
					EndIf
					
					StrTemp = ""
					
					x = opt\GraphicWidth - SelectedItem\ItemTemplate\ImgWidth
					y = opt\GraphicHeight - SelectedItem\ItemTemplate\ImgHeight
					
					DrawImage(SelectedItem\ItemTemplate\Img, x, y)
					
					If SelectedItem\State > 0.0 Lor (SelectedItem\ItemTemplate\TempName = "fineradio" Lor SelectedItem\ItemTemplate\TempName = "veryfineradio") Then
						If PlayerRoom\RoomTemplate\Name <> "dimension_106" And CoffinDistance >= 8.0 Then
							Select Int(SelectedItem\State2)
								Case 0
									;[Block]
									If (Not opt\EnableUserTracks) Then
										StrTemp = Format(GetLocalString("radio", "usertrack"), GetLocalString("radio", "notenable"))
									ElseIf UserTrackMusicAmount < 1
										StrTemp = Format(GetLocalString("radio", "usertrack"), GetLocalString("radio", "nofound"))
									Else
										If ChannelPlaying(RadioCHN[0]) Then StrTemp = Format(GetLocalString("radio", "usertrack"), Upper(UserTrackName[RadioState[0]]) + "          ")
									EndIf
									;[End Block]
								Case 1
									;[Block]
									StrTemp = GetLocalString("radio", "warn")
									;[End Block]
								Case 2
									;[Block]
									StrTemp = GetLocalString("radio", "onsite")
									;[End Block]
								Case 3
									;[Block]
									StrTemp = GetLocalString("radio", "emergency")
									;[End Block]
							End Select
							
							x = x + (66 * MenuScale)
							y = y + (419 * MenuScale)
							
							; ~ Battery
							Color(30, 30, 30)
							If SelectedItem\ItemTemplate\TempName = "radio" Lor SelectedItem\ItemTemplate\TempName = "18vradio" Then
								For i = 0 To 4
									Rect(x, y + ((8 * i) * MenuScale), (43 * MenuScale) - ((i * 6) * MenuScale), 4 * MenuScale, Ceil(SelectedItem\State / 20.0) > 4 - i )
								Next
							EndIf
							
							SetFont2(fo\FontID[Font_Digital])
							Text2(x + (60 * MenuScale), y, GetLocalString("radio", "chn"))
							
							If SelectedItem\ItemTemplate\TempName = "veryfineradio" Then
								StrTemp = ""
								For i = 0 To Rand(5, 30)
									StrTemp = StrTemp + Chr(Rand(100))
								Next
								
								SetFont2(fo\FontID[Font_Digital_Big])
								Text2(x + (97 * MenuScale), y + (16 * MenuScale), Rand(0, 9), True, True)
							Else
								SetFont2(fo\FontID[Font_Digital_Big])
								Text2(x + (97 * MenuScale), y + (16 * MenuScale), Int(SelectedItem\State2 + 1.0), True, True)
							EndIf
							
							SetFont2(fo\FontID[Font_Digital])
							If StrTemp <> "" Then
								StrTemp = Right(Left(StrTemp, (Int(MilliSecs2() / 300) Mod Len(StrTemp))), 10)
								Text2(x + (32 * MenuScale), y + (33 * MenuScale), StrTemp)
							EndIf
							SetFont2(fo\FontID[Font_Default])
						EndIf
					EndIf
					;[End Block]
				Case "hazmatsuit", "finehazmatsuit", "veryfinehazmatsuit", "hazmatsuit148"
					;[Block]
					If wi\BallisticVest = 0 Then
						DrawBlock(SelectedItem\ItemTemplate\InvImg, mo\Viewport_Center_X - InvImgSize, mo\Viewport_Center_Y - InvImgSize)
						
						Width = 300 * MenuScale
						Height = 20 * MenuScale
						x = mo\Viewport_Center_X - (Width / 2)
						y = mo\Viewport_Center_Y + (80 * MenuScale)
						
						RenderBar(BlinkMeterIMG, x, y, Width, Height, SelectedItem\State)
					EndIf
					;[End Block]
				Case "vest", "finevest"
					;[Block]
					DrawBlock(SelectedItem\ItemTemplate\InvImg, mo\Viewport_Center_X - InvImgSize, mo\Viewport_Center_Y - InvImgSize)
					
					Width = 300 * MenuScale
					Height = 20 * MenuScale
					x = mo\Viewport_Center_X - (Width / 2)
					y = mo\Viewport_Center_Y + (80 * MenuScale)
					
					RenderBar(BlinkMeterIMG, x, y, Width, Height, SelectedItem\State)
					;[End Block]
				Case "gasmask", "finegasmask", "veryfinegasmask", "gasmask148", "helmet"
					;[Block]
					If PreventItemOverlapping() Then
						
						DrawBlock(SelectedItem\ItemTemplate\InvImg, mo\Viewport_Center_X - InvImgSize, mo\Viewport_Center_Y - InvImgSize)
						
						Width = 300 * MenuScale
						Height = 20 * MenuScale
						x = mo\Viewport_Center_X - (Width / 2)
						y = mo\Viewport_Center_Y + (80 * MenuScale)
						
						RenderBar(BlinkMeterIMG, x, y, Width, Height, SelectedItem\State)
					EndIf
					;[End Block]
				Case "nav", "nav300", "nav310", "navulti"
					;[Block]
					If (Not SelectedItem\ItemTemplate\Img) Then
						SelectedItem\ItemTemplate\Img = LoadImage_Strict(SelectedItem\ItemTemplate\ImgPath)
						SelectedItem\ItemTemplate\Img = ScaleImage2(SelectedItem\ItemTemplate\Img, MenuScale, MenuScale)
						SelectedItem\ItemTemplate\ImgWidth = ImageWidth(SelectedItem\ItemTemplate\Img) / 2
						SelectedItem\ItemTemplate\ImgHeight = ImageHeight(SelectedItem\ItemTemplate\Img) / 2
						MaskImage(SelectedItem\ItemTemplate\Img, 255, 0, 255)
					EndIf
					
					Local NAV_WIDTH% = 287 * MenuScale
					Local NAV_HEIGHT% = 256 * MenuScale
					
					x = opt\GraphicWidth - SelectedItem\ItemTemplate\ImgWidth + (20 * MenuScale)
					y = opt\GraphicHeight - SelectedItem\ItemTemplate\ImgHeight - (85 * MenuScale)
					
					Local PlayerX%, PlayerZ%
					
					DrawImage(SelectedItem\ItemTemplate\Img, x - SelectedItem\ItemTemplate\ImgWidth, y - SelectedItem\ItemTemplate\ImgHeight + (85 * MenuScale))
					
					SetFont2(fo\FontID[Font_Digital])
					
					Local Offline% = False
					
					If SelectedItem\ItemTemplate\TempName = "nav" Lor SelectedItem\ItemTemplate\TempName = "nav300" Then Offline = True
					
					Local NavWorks% = True
					
					If (Not PlayerInReachableRoom()) Then NavWorks = False
					If (Not NavWorks) Then
						If (MilliSecs2() Mod 800) < 200 Then
							Color(200, 0, 0)
							Text2(x, y + (NAV_HEIGHT / 2) - (80 * MenuScale), GetLocalString("msg", "nav.error"), True)
							Text2(x, y + (NAV_HEIGHT / 2) - (60 * MenuScale), GetLocalString("msg", "nav.locunknown"), True)
						EndIf
					Else
						If (SelectedItem\State > 0.0 Lor (SelectedItem\ItemTemplate\TempName = "nav300" Lor SelectedItem\ItemTemplate\TempName = "navulti")) And (Rnd(CoffinDistance + 15.0) > 1.0 Lor PlayerRoom\RoomTemplate\Name <> "cont1_895") Then
							PlayerX = Floor(EntityX(me\Collider) / RoomSpacing + 0.5)
							PlayerZ = Floor(EntityZ(me\Collider) / RoomSpacing + 0.5)
							
							SetBuffer(ImageBuffer(t\ImageID[7]))
							
							Local xx% = x - SelectedItem\ItemTemplate\ImgWidth
							Local yy% = y - SelectedItem\ItemTemplate\ImgHeight + (85 * MenuScale)
							
							DrawImage(SelectedItem\ItemTemplate\Img, xx, yy)
							
							x = x - (12 * MenuScale) + ((EntityX(me\Collider) - 4.0) Mod RoomSpacing) * (3 * MenuScale)
							y = y + (12 * MenuScale) - ((EntityZ(me\Collider) - 4.0) Mod RoomSpacing) * (3 * MenuScale)
							For x2 = Max(1.0, PlayerX - 6) To Min(MapGridSize - 1, PlayerX + 6)
								For z2 = Max(1.0, PlayerZ - 6) To Min(MapGridSize - 1, PlayerZ + 6)
									If CoffinDistance > 16.0 Lor Rnd(16.0) < CoffinDistance Then 
										If CurrMapGrid\Grid[x2 + (z2 * MapGridSize)] > MapGrid_NoTile And (CurrMapGrid\Found[x2 + (z2 * MapGridSize)] > MapGrid_NoTile Lor (Not Offline)) Then
											Local DrawX% = x + (PlayerX - x2) * (24 * MenuScale), DrawY% = y - (PlayerZ - z2) * (24 * MenuScale) 
											
											Color(30, 30, 30)
											If CurrMapGrid\Grid[(x2 + 1) + (z2 * MapGridSize)] = MapGrid_NoTile Then Rect(DrawX - (12 * MenuScale), DrawY - (12 * MenuScale), MenuScale, 24 * MenuScale)
											If CurrMapGrid\Grid[(x2 - 1) + (z2 * MapGridSize)] = MapGrid_NoTile Then Rect(DrawX + (12 * MenuScale), DrawY - (12 * MenuScale), MenuScale, 24 * MenuScale)
											
											If CurrMapGrid\Grid[x2 + ((z2 - 1) * MapGridSize)] = MapGrid_NoTile Then Rect(DrawX - (12 * MenuScale), DrawY - (12 * MenuScale), 24 * MenuScale, MenuScale)
											If CurrMapGrid\Grid[x2 + ((z2 + 1) * MapGridSize)] = MapGrid_NoTile Then Rect(DrawX - (12 * MenuScale), DrawY + (12 * MenuScale), 24 * MenuScale, MenuScale)
										EndIf
									EndIf
								Next
							Next
							
							SetBuffer(BackBuffer())
							DrawBlockRect(t\ImageID[7], xx + (80 * MenuScale), yy + (70 * MenuScale), xx + (80 * MenuScale), yy + (70 * MenuScale), 270 * MenuScale, 230 * MenuScale)
							If Offline Then
								Color(100, 0, 0)
							Else
								Color(30, 30, 30)
							EndIf
							Rect(xx + (80 * MenuScale), yy + (70 * MenuScale), 270 * MenuScale, 230 * MenuScale, False)
							
							x = opt\GraphicWidth - SelectedItem\ItemTemplate\ImgWidth + (20 * MenuScale)
							y = opt\GraphicHeight - SelectedItem\ItemTemplate\ImgHeight - (85 * MenuScale)
							
							If Offline Then
								Color(100, 0, 0)
							Else
								Color(30, 30, 30)
							EndIf
							If (MilliSecs2() Mod 800) < 200 Then ; ~ TODO: FIND THE WAY TO GET RID OF MILLISECS2
								If Offline Then Text2(x - (NAV_WIDTH / 2) + (10 * MenuScale), y - (NAV_HEIGHT / 2) + (10 * MenuScale), "MAP DATABASE OFFLINE")
								
								YawValue = EntityYaw(me\Collider) - 90.0
								x1 = x + Cos(YawValue) * (6.0 * MenuScale) : y1 = y - Sin(YawValue) * (6.0 * MenuScale)
								x2 = x + Cos(YawValue - 140.0) * (5.0 * MenuScale) : y2 = y - Sin(YawValue - 140.0) * (5.0 * MenuScale)
								x3 = x + Cos(YawValue + 140.0) * (5.0 * MenuScale) : y3 = y - Sin(YawValue + 140.0) * (5.0 * MenuScale)
								
								Line(x1, y1, x2, y2)
								Line(x1, y1, x3, y3)
								Line(x2, y2, x3, y3)
							EndIf
							
							Local SCPs_Found% = 0, Dist#
							
							If SelectedItem\ItemTemplate\TempName = "navulti" And (MilliSecs2() Mod 600) < 400 Then
								If n_I\Curr173 <> Null Then
									Dist = EntityDistanceSquared(Camera, n_I\Curr173\OBJ)
									If Dist < 900.0 Then
										Dist = Sqr(Ceil(Dist / 8.0) * 8.0) ; ~ This is probably done to disguise SCP-173's teleporting behavior
										Color(100, 0, 0)
										Oval(x - (Dist * (3 * MenuScale)), y - (7 * MenuScale) - (Dist * (3 * MenuScale)), Dist * (6 * MenuScale), Dist * (6 * MenuScale), False)
										Text2(x - (NAV_WIDTH / 2) + (10 * MenuScale), y - (NAV_HEIGHT / 2) + (30 * MenuScale), "SCP-173")
										SCPs_Found = SCPs_Found + 1
									EndIf
								EndIf
								If n_I\Curr106 <> Null Then
									Dist = EntityDistanceSquared(Camera, n_I\Curr106\OBJ)
									If Dist < 900.0 Then
										Dist = Sqr(Dist)
										Color(100, 0, 0)
										Oval(x - (Dist * (1.5 * MenuScale)), y - (7 * MenuScale) - (Dist * (1.5 * MenuScale)), Dist * (3 * MenuScale), Dist * (3 * MenuScale), False)
										Text2(x - (NAV_WIDTH / 2) + (10 * MenuScale), y - (NAV_HEIGHT / 2) + (30 * MenuScale) + ((20 * SCPs_Found) * MenuScale), "SCP-106")
										SCPs_Found = SCPs_Found + 1
									EndIf
								EndIf
								If n_I\Curr096 <> Null Then
									Dist = EntityDistanceSquared(Camera, n_I\Curr096\OBJ)
									If Dist < 900.0 Then
										Dist = Sqr(Dist)
										Color(100, 0, 0)
										Oval(x - (Dist * (1.5 * MenuScale)), y - (7 * MenuScale) - (Dist * (1.5 * MenuScale)), Dist * (3 * MenuScale), Dist * (3 * MenuScale), False)
										Text2(x - (NAV_WIDTH / 2) + (10 * MenuScale), y - (NAV_HEIGHT / 2) + (30 * MenuScale) + ((20 * SCPs_Found) * MenuScale), "SCP-096")
										SCPs_Found = SCPs_Found + 1
									EndIf
								EndIf
								If n_I\Curr049 <> Null Then
								If (Not n_I\Curr049\HideFromNVG) Then
									Dist = EntityDistanceSquared(Camera, n_I\Curr049\OBJ)
										If Dist < 900.0 Then
											Dist = Sqr(Dist)
											Color(100, 0, 0)
											Oval(x - (Dist * (1.5 * MenuScale)), y - (7 * MenuScale) - (Dist * (1.5 * MenuScale)), Dist * (3 * MenuScale), Dist * (3 * MenuScale), False)
											Text2(x - (NAV_WIDTH / 2) + (10 * MenuScale), y - (NAV_HEIGHT / 2) + (30 * MenuScale) + ((20 * SCPs_Found) * MenuScale), "SCP-049")
											SCPs_Found = SCPs_Found + 1
										EndIf
									EndIf
								EndIf
								If PlayerRoom\RoomTemplate\Name = "cont1_895" Then
									If CoffinDistance < 8.0 Then
										Dist = Rnd(4.0, 8.0)
										Color(100, 0, 0)
										Oval(x - (Dist * (1.5 * MenuScale)), y - (7 * MenuScale) - (Dist * (1.5 * MenuScale)), Dist * (3 * MenuScale), Dist * (3 * MenuScale), False)
										Text2(x - (NAV_WIDTH / 2) + (10 * MenuScale), y - (NAV_HEIGHT / 2) + (30 * MenuScale) + ((20 * SCPs_Found) * MenuScale), "SCP-895")
									EndIf
								EndIf
							EndIf
							
							Color(30, 30, 30)
							If Offline Then
								Color(100, 0, 0)
								xTemp = x - (NAV_WIDTH / 2) + (196 * MenuScale)
								yTemp = y - (NAV_HEIGHT / 2) + (10 * MenuScale)
								Rect(xTemp, yTemp, 80 * MenuScale, 20 * MenuScale, False)
								
								; ~ Battery
								If SelectedItem\State <= 20.0 Then
									Color(100, 0, 0)
								Else
									Color(30, 30, 30)
								EndIf
								For i = 1 To Min(Ceil(SelectedItem\State / 10.0), 10.0)
									Rect(xTemp + ((i * 8) * MenuScale) - (6 * MenuScale), yTemp + (4 * MenuScale), 4 * MenuScale, 12 * MenuScale)
								Next
								SetFont2(fo\FontID[Font_Digital])
							EndIf
						EndIf
					EndIf
					;[End Block]
				Case "scp1499", "fine1499"
					;[Block]
					If PreventItemOverlapping(True) Then
						
						DrawBlock(SelectedItem\ItemTemplate\InvImg, mo\Viewport_Center_X - InvImgSize, mo\Viewport_Center_Y - InvImgSize)
						
						Width = 300 * MenuScale
						Height = 20 * MenuScale
						x = mo\Viewport_Center_X - (Width / 2)
						y = mo\Viewport_Center_Y + (80 * MenuScale)
						
						RenderBar(BlinkMeterIMG, x, y, Width, Height, SelectedItem\State)
					EndIf
					;[End Block]
				Case "badge"
					;[Block]
					If (Not SelectedItem\ItemTemplate\Img) Then
						SelectedItem\ItemTemplate\Img = LoadImage_Strict(SelectedItem\ItemTemplate\ImgPath)
						SelectedItem\ItemTemplate\Img = ScaleImage2(SelectedItem\ItemTemplate\Img, MenuScale, MenuScale)
						SelectedItem\ItemTemplate\ImgWidth = ImageWidth(SelectedItem\ItemTemplate\Img) / 2
						SelectedItem\ItemTemplate\ImgHeight = ImageHeight(SelectedItem\ItemTemplate\Img) / 2
					EndIf
					DrawBlock(SelectedItem\ItemTemplate\Img, mo\Viewport_Center_X - SelectedItem\ItemTemplate\ImgWidth, mo\Viewport_Center_Y - SelectedItem\ItemTemplate\ImgHeight)
					;[End Block]
				Case "oldbadge", "ticket"
					;[Block]
					If (Not SelectedItem\ItemTemplate\Img) Then
						SelectedItem\ItemTemplate\Img = LoadImage_Strict(SelectedItem\ItemTemplate\ImgPath)
						SelectedItem\ItemTemplate\Img = ScaleImage2(SelectedItem\ItemTemplate\Img, MenuScale, MenuScale)
						SelectedItem\ItemTemplate\ImgWidth = ImageWidth(SelectedItem\ItemTemplate\Img) / 2
						SelectedItem\ItemTemplate\ImgHeight = ImageHeight(SelectedItem\ItemTemplate\Img) / 2
						MaskImage(SelectedItem\ItemTemplate\Img, 255, 0, 255)
					EndIf
					DrawImage(SelectedItem\ItemTemplate\Img, mo\Viewport_Center_X - SelectedItem\ItemTemplate\ImgWidth, mo\Viewport_Center_Y - SelectedItem\ItemTemplate\ImgHeight)
					;[End Block]
			End Select
			
			If SelectedItem <> Null Then
				If SelectedItem\ItemTemplate\Img <> 0 Then
					Local IN$ = SelectedItem\ItemTemplate\TempName
					
					If IN = "paper" Lor IN = "oldpaper" Lor IN = "badge" Lor IN = "oldbadge" Lor IN = "ticket" Lor IN = "scp1025" Then
						For a_it.Items = Each Items
							If a_it <> SelectedItem Then
								Local IN2$ = a_it\ItemTemplate\TempName
								
								If IN2 = "paper" Lor IN2 = "oldpaper" Lor IN2 = "badge" Lor IN2 = "oldbadge" Lor IN2 = "ticket" Lor IN2 = "scp1025" Then
									If a_it\ItemTemplate\Img <> 0 Then
										If a_it\ItemTemplate\Img <> SelectedItem\ItemTemplate\Img Then
											FreeImage(a_it\ItemTemplate\Img) : a_it\ItemTemplate\Img = 0
											Exit
										EndIf
									EndIf
								EndIf
							EndIf
						Next
					EndIf
				EndIf
			EndIf
		EndIf
	EndIf
	
	CatchErrors("RenderGUI")
End Function

; ~ Menu Tab Options Constants
;[Block]
Const MenuTab_Options_Graphics% = 1
Const MenuTab_Options_Audio% = 2
Const MenuTab_Options_Controls% = 3
Const MenuTab_Options_Advanced% = 4
;[End Block]

Function UpdateMenu%()
	CatchErrors("Uncaught (UpdateMenu)")
	
	Local r.Rooms
	Local x%, y%, z%, Width%, Height%, i%
	
	If MenuOpen Then
		If (Not IsPlayerOutsideFacility()) Then
			If me\StopHidingTimer = 0.0 Then
				If n_I\Curr173 <> Null And n_I\Curr106 <> Null Then
					If EntityDistanceSquared(n_I\Curr173\Collider, me\Collider) < 16.0 Lor EntityDistanceSquared(n_I\Curr106\Collider, me\Collider) < 16.0 Then me\StopHidingTimer = 1.0
				EndIf
			ElseIf me\StopHidingTimer < 40.0
				If (Not me\Terminated) Then
					me\StopHidingTimer = me\StopHidingTimer + fps\Factor[0]
					If me\StopHidingTimer >= 40.0 Then
						PlaySound_Strict(HorrorSFX[15])
						CreateMsg(GetLocalString("msg", "stophiding"))
						mm\ShouldDeleteGadgets = True
						MenuOpen = False
						Return
					EndIf
				EndIf
			EndIf
		EndIf
		
		InvOpen = False
		If ConsoleOpen Then
			ConsoleOpen = False
			mm\ShouldDeleteGadgets = True
		EndIf
		
		Width = ImageWidth(t\ImageID[0])
		Height = ImageHeight(t\ImageID[0])
		x = mo\Viewport_Center_X - (Width / 2)
		y = mo\Viewport_Center_Y - (Height / 2)
		
		x = x + (132 * MenuScale)
		y = y + (122 * MenuScale)
		
		If (Not mo\MouseDown1) Then mm\OnSliderID = 0
		
		If mm\AchievementsMenu <= 0 And OptionsMenu > 0 And QuitMsg <= 0 Then
			If UpdateMainMenuButton(x + (101 * MenuScale), y + (460 * MenuScale), 230 * MenuScale, 60 * MenuScale, GetLocalString("menu", "back")) Then
				mm\AchievementsMenu = 0
				OptionsMenu = 0
				QuitMsg = 0
				mo\MouseHit1 = False
				SaveOptionsINI()
				
				AntiAlias(opt\AntiAliasing)
				TextureLodBias(opt\TextureDetailsLevel)
				TextureAnisotropic(opt\AnisotropicLevel)
				mm\ShouldDeleteGadgets = True
			EndIf
			
			If UpdateMainMenuButton(x - (5 * MenuScale), y, 100 * MenuScale, 30 * MenuScale, GetLocalString("options", "grap"), False) Then ChangeOptionTab(MenuTab_Options_Graphics, False)
			If UpdateMainMenuButton(x + (105 * MenuScale), y, 100 * MenuScale, 30 * MenuScale, GetLocalString("options", "audio"), False) Then ChangeOptionTab(MenuTab_Options_Audio, False)
			If UpdateMainMenuButton(x + (215 * MenuScale), y, 100 * MenuScale, 30 * MenuScale, GetLocalString("options", "ctrl"), False) Then ChangeOptionTab(MenuTab_Options_Controls, False)
			If UpdateMainMenuButton(x + (325 * MenuScale), y, 100 * MenuScale, 30 * MenuScale, GetLocalString("options", "avc"), False) Then ChangeOptionTab(MenuTab_Options_Advanced, False)
			
			Select OptionsMenu
				Case MenuTab_Options_Graphics
					;[Block]
					x = x + (270 * MenuScale)
					y = y + (50 * MenuScale)
					
					opt\BumpEnabled = UpdateMainMenuTick(x, y, opt\BumpEnabled, True)
					
					y = y + (30 * MenuScale)
					
					opt\VSync = UpdateMainMenuTick(x, y, opt\VSync)
					
					y = y + (30 * MenuScale)
					
					opt\AntiAliasing = UpdateMainMenuTick(x, y, opt\AntiAliasing, opt\DisplayMode <> 0)
					
					y = y + (30 * MenuScale)
					
					opt\AdvancedRoomLights = UpdateMainMenuTick(x, y, opt\AdvancedRoomLights)
					
					y = y + (40 * MenuScale)
					
					opt\ScreenGamma = UpdateMainMenuSlideBar(x, y, 100 * MenuScale, opt\ScreenGamma * 50.0, 1) / 50.0
					
					y = y + (45 * MenuScale)
					
					opt\ParticleAmount = UpdateMainMenuSlider3(x, y, 100 * MenuScale, opt\ParticleAmount, 2, "MINIMAL", "REDUCED", "FULL")
					
					y = y + (45 * MenuScale)
					
					opt\TextureDetails = UpdateMainMenuSlider5(x, y, 100 * MenuScale, opt\TextureDetails, 3, "0.8", "0.4", "0.0", "-0.4", "-0.8")
					Select opt\TextureDetails
						Case 0
							;[Block]
							opt\TextureDetailsLevel = 0.8
							;[End Block]
						Case 1
							;[Block]
							opt\TextureDetailsLevel = 0.4
							;[End Block]
						Case 2
							;[Block]
							opt\TextureDetailsLevel = 0.0
							;[End Block]
						Case 3
							;[Block]
							opt\TextureDetailsLevel = -0.4
							;[End Block]
						Case 4
							;[Block]
							opt\TextureDetailsLevel = -0.8
							;[End Block]
					End Select
					TextureLodBias(opt\TextureDetailsLevel)
					
					y = y + (35 * MenuScale)
					
					opt\SaveTexturesInVRAM = UpdateMainMenuTick(x, y, opt\SaveTexturesInVRAM, True)
					
					y = y + (40 * MenuScale)
					
					opt\CurrFOV = UpdateMainMenuSlideBar(x, y, 100 * MenuScale, opt\CurrFOV * 2.0, 4) / 2.0
					opt\FOV = opt\CurrFOV + 40
					CameraZoom(Camera, Min(1.0 + (me\CurrCameraZoom / 400.0), 1.1) / Tan((2.0 * ATan(Tan((opt\FOV) / 2.0) * opt\RealGraphicWidth / opt\RealGraphicHeight)) / 2.0))
					
					y = y + (45 * MenuScale)
					
					opt\Anisotropic = UpdateMainMenuSlider5(x, y, 100 * MenuScale, opt\Anisotropic, 5, "Trilinear", "2x", "4x", "8x", "16x")
					Select opt\Anisotropic
						Case 0
							;[Block]
							opt\AnisotropicLevel = 0
							;[End Block]
						Case 1
							;[Block]
							opt\AnisotropicLevel = 2
							;[End Block]
						Case 2
							;[Block]
							opt\AnisotropicLevel = 4
							;[End Block]
						Case 3
							;[Block]
							opt\AnisotropicLevel = 8
							;[End Block]
						Case 4
							;[Block]
							opt\AnisotropicLevel = 16
							;[End Block]
					End Select
					TextureAnisotropic(opt\AnisotropicLevel)
					
					y = y + (35 * MenuScale)
					
					opt\Atmosphere = UpdateMainMenuTick(x, y, opt\Atmosphere, True)
					;[End Block]
				Case MenuTab_Options_Audio
					;[Block]
					x = x + (270 * MenuScale)
					y = y + (50 * MenuScale)
					
					opt\MasterVolume = UpdateMainMenuSlideBar(x, y, 100 * MenuScale, opt\MasterVolume * 100.0, 1) / 100.0
					
					y = y + (40 * MenuScale)
					
					opt\MusicVolume = UpdateMainMenuSlideBar(x, y, 100 * MenuScale, opt\MusicVolume * 100.0, 2) / 100.0
					
					y = y + (40 * MenuScale)
					
					opt\SFXVolume = UpdateMainMenuSlideBar(x, y, 100 * MenuScale, opt\SFXVolume * 100.0, 3) / 100.0
					
					y = y + (40 * MenuScale)
					
					opt\EnableSFXRelease = UpdateMainMenuTick(x, y, opt\EnableSFXRelease, True)
					
					y = y + (30 * MenuScale)
					
					opt\EnableUserTracks = UpdateMainMenuTick(x, y, opt\EnableUserTracks, True)
					
					If opt\EnableUserTracks Then
						y = y + (30 * MenuScale)
						
						opt\UserTrackMode = UpdateMainMenuTick(x, y, opt\UserTrackMode)
						
						UpdateMainMenuButton(x - (270 * MenuScale), y + (30 * MenuScale), 210 * MenuScale, 30 * MenuScale, GetLocalString("options", "scantracks"), False, False, True)
					EndIf
					;[End Block]
				Case MenuTab_Options_Controls
					;[Block]
					If mm\CurrMenuPage = 0 Then
						x = x + (270 * MenuScale)
						y = y + (50 * MenuScale)
						
						opt\MouseSensitivity = (UpdateMainMenuSlideBar(x, y, 100 * MenuScale, (opt\MouseSensitivity + 0.5) * 100.0, 1) / 100.0) - 0.5
						
						y = y + (40 * MenuScale)
						
						opt\InvertMouseX = UpdateMainMenuTick(x, y, opt\InvertMouseX)
						
						y = y + (40 * MenuScale)
						
						opt\InvertMouseY = UpdateMainMenuTick(x, y, opt\InvertMouseY)
						
						y = y + (40 * MenuScale)
						
						opt\MouseSmoothing = UpdateMainMenuSlideBar(x, y, 100 * MenuScale, (opt\MouseSmoothing) * 50.0, 2) / 50.0
						
						y = y + (40 * MenuScale)
						
						If UpdateMainMenuButton(x - (270 * MenuScale), y, 240 * MenuScale, 30 * MenuScale, GetLocalString("options", "controlconfig"), False) Then ChangePage(1)
					Else
						x = x + (200 * MenuScale)
						y = y + (80 * MenuScale)
						
						UpdateMainMenuInputBox(x, y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\MOVEMENT_UP, 210.0)], 3)
						
						y = y + (20 * MenuScale)
						
						UpdateMainMenuInputBox(x, y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\MOVEMENT_LEFT, 210.0)], 4)
						
						y = y + (20 * MenuScale)
						
						UpdateMainMenuInputBox(x, y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\MOVEMENT_DOWN, 210.0)], 5)
						
						y = y + (20 * MenuScale)
						
						UpdateMainMenuInputBox(x, y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\MOVEMENT_RIGHT, 210.0)], 6)
						
						y = y + (20 * MenuScale)
						
						UpdateMainMenuInputBox(x, y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\SPRINT, 210.0)], 7)
						
						y = y + (20 * MenuScale)
						
						UpdateMainMenuInputBox(x, y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\CROUCH, 210.0)], 8)
						
						y = y + (20 * MenuScale)
						
						UpdateMainMenuInputBox(x, y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\BLINK, 210.0)], 9)
						
						y = y + (20 * MenuScale)
						
						UpdateMainMenuInputBox(x, y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\INVENTORY, 210.0)], 10)
						
						y = y + (20 * MenuScale)
						
						UpdateMainMenuInputBox(x, y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\SAVE, 210.0)], 11)
						
						y = y + (20 * MenuScale)
						
						UpdateMainMenuInputBox(x, y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\SCREENSHOT, 210.0)], 13)
						
						If opt\CanOpenConsole Then
							y = y + (20 * MenuScale)
							
							UpdateMainMenuInputBox(x, y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\CONSOLE, 210.0)], 12)
						EndIf
						
						Local TempKey%
						
						For i = 0 To 227
							If KeyHit(i) Then
								TempKey = i
								Exit
							EndIf
						Next
						If TempKey <> 0 Then
							Select SelectedInputBox
								Case 3
									;[Block]
									key\MOVEMENT_UP = TempKey
									;[End Block]
								Case 4
									;[Block]
									key\MOVEMENT_LEFT = TempKey
									;[End Block]
								Case 5
									;[Block]
									key\MOVEMENT_DOWN = TempKey
									;[End Block]
								Case 6
									;[Block]
									key\MOVEMENT_RIGHT = TempKey
									;[End Block]
								Case 7
									;[Block]
									key\SPRINT = TempKey
									;[End Block]
								Case 8
									;[Block]
									key\CROUCH = TempKey
									;[End Block]
								Case 9
									;[Block]
									key\BLINK = TempKey
									;[End Block]
								Case 10
									;[Block]
									key\INVENTORY = TempKey
									;[End Block]
								Case 11
									;[Block]
									key\SAVE = TempKey
									;[End Block]
								Case 12
									;[Block]
									key\CONSOLE = TempKey
									;[End Block]
								Case 13
									;[Block]
									key\SCREENSHOT = TempKey
									;[End Block]
							End Select
							SelectedInputBox = 0
						EndIf
						
						y = y + (40 * MenuScale)
						
						If UpdateMainMenuButton(x - (200 * MenuScale), y, 240 * MenuScale, 30 * MenuScale, GetLocalString("menu", "back"), False) Then ChangePage(0)
					EndIf
					;[End Block]
				Case MenuTab_Options_Advanced
					;[Block]
					x = x + (270 * MenuScale)
					y = y + (50 * MenuScale)
					
					opt\HUDEnabled = UpdateMainMenuTick(x, y, opt\HUDEnabled)
					
					y = y + (30 * MenuScale)
					
					Local PrevCanOpenConsole% = opt\CanOpenConsole
					
					opt\CanOpenConsole = UpdateMainMenuTick(x, y, opt\CanOpenConsole)
					
					If PrevCanOpenConsole Then
						If PrevCanOpenConsole <> opt\CanOpenConsole Then mm\ShouldDeleteGadgets = True
					EndIf
					
					y = y + (30 * MenuScale)
					
					If opt\CanOpenConsole Then opt\ConsoleOpening = UpdateMainMenuTick(x, y, opt\ConsoleOpening)
					
					y = y + (30 * MenuScale)
					
					opt\AchvMsgEnabled = UpdateMainMenuTick(x, y, opt\AchvMsgEnabled)
					
					y = y + (30 * MenuScale)
					
					opt\AutoSaveEnabled = UpdateMainMenuTick(x, y, opt\AutoSaveEnabled, SelectedDifficulty\SaveType <> SAVE_ANYWHERE)
					
					y = y + (30 * MenuScale)
					
					opt\ShowFPS = UpdateMainMenuTick(x, y, opt\ShowFPS)
					
					y = y + (30 * MenuScale)
					
					Local PrevCurrFrameLimit% = opt\CurrFrameLimit > 0.0
					
					If UpdateMainMenuTick(x, y, opt\CurrFrameLimit > 0.0) Then
						opt\CurrFrameLimit = UpdateMainMenuSlideBar(x - (120 * MenuScale), y + (40 * MenuScale), 100 * MenuScale, opt\CurrFrameLimit * 99.0, 1) / 99.0
						opt\CurrFrameLimit = Max(opt\CurrFrameLimit, 0.01)
						opt\FrameLimit = 19 + (opt\CurrFrameLimit * 100.0)
					Else
						opt\CurrFrameLimit = 0.0
						opt\FrameLimit = 0
					EndIf
					
					If PrevCurrFrameLimit Then
						If PrevCurrFrameLimit <> opt\CurrFrameLimit Then mm\ShouldDeleteGadgets = True
					EndIf
					;[End Block]
			End Select
		ElseIf mm\AchievementsMenu <= 0 And OptionsMenu <= 0 And QuitMsg > 0
			Local QuitButton% = 85
			
			If SelectedDifficulty\SaveType = SAVE_ANYWHERE Then
				Local AbleToSave% = True
				
				If CanSave < 2 Then AbleToSave = False
				If AbleToSave Then
					QuitButton = 160
					If UpdateMainMenuButton(x, y + (85 * MenuScale), 430 * MenuScale, 60 * MenuScale, GetLocalString("menu", "savequit")) Then
						me\DropSpeed = 0.0
						SaveGame(CurrSave\Name)
						NullGame()
						CurrSave = Null
						ResetInput()
						Return
					EndIf
				EndIf
			EndIf
			
			If UpdateMainMenuButton(x, y + (QuitButton * MenuScale), 430 * MenuScale, 60 * MenuScale, GetLocalString("menu", "quit")) Then
				NullGame()
				CurrSave = Null
				ResetInput()
				Return
			EndIf
			
			If UpdateMainMenuButton(x + (101 * MenuScale), y + 385 * MenuScale, 230 * MenuScale, 60 * MenuScale, GetLocalString("menu", "back")) Then
				mm\AchievementsMenu = 0
				OptionsMenu = 0
				QuitMsg = 0
				mo\MouseHit1 = False
				mm\ShouldDeleteGadgets = True
			EndIf
		ElseIf mm\AchievementsMenu > 0 And OptionsMenu <= 0 And QuitMsg <= 0
			If UpdateMainMenuButton(x + (101 * MenuScale), y + 345 * MenuScale, 230 * MenuScale, 60 * MenuScale, GetLocalString("menu", "back")) Then
				mm\AchievementsMenu = 0
				OptionsMenu = 0
				QuitMsg = 0
				mo\MouseHit1 = False
				mm\ShouldDeleteGadgets = True
			EndIf
			
			If mm\AchievementsMenu > 0 Then
				If mm\AchievementsMenu <= Floor(Float(MAXACHIEVEMENTS - 1) / 12.0) Then
					If UpdateMainMenuButton(x + (341 * MenuScale), y + (345 * MenuScale), 60 * MenuScale, 60 * MenuScale, ">") Then
						mm\AchievementsMenu = mm\AchievementsMenu + 1
						mm\ShouldDeleteGadgets = True
					EndIf
				Else
					UpdateMainMenuButton(x + (341 * MenuScale), y + (345 * MenuScale), 60 * MenuScale, 60 * MenuScale, ">", True, False, True)
				EndIf
				If mm\AchievementsMenu > 1 Then
					If UpdateMainMenuButton(x + (31 * MenuScale), y + (345 * MenuScale), 60 * MenuScale, 60 * MenuScale, "<") Then
						mm\AchievementsMenu = mm\AchievementsMenu - 1
						mm\ShouldDeleteGadgets = True
					EndIf
				Else
					UpdateMainMenuButton(x + (31 * MenuScale), y + (345 * MenuScale), 60 * MenuScale, 60 * MenuScale, "<", True, False, True)
				EndIf
			EndIf
		Else
			y = y + (10 * MenuScale)
			
			If (Not me\Terminated) Lor me\SelectedEnding <> - 1 Then
				y = y + (75 * MenuScale)
				
				If UpdateMainMenuButton(x, y, 430 * MenuScale, 60 * MenuScale, GetLocalString("menu", "resume"), True, True) Then
					ResumeSounds()
					StopMouseMovement()
					DeleteMenuGadgets()
					MenuOpen = False
					Return
				EndIf
				
				y = y + (75 * MenuScale)
				
				If SelectedDifficulty\SaveType <> NO_SAVES Then
					If GameSaved Then
						If UpdateMainMenuButton(x, y, 430 * MenuScale, 60 * MenuScale, GetLocalString("menu", "load")) Then
							RenderLoading(0, GetLocalString("loading", "files"))
							
							KillSounds()
							LoadGameQuick(CurrSave\Name)
							
							MoveMouse(mo\Viewport_Center_X, mo\Viewport_Center_Y)
							HidePointer()
							
							UpdateTimer = 0.0
							
							For r.Rooms = Each Rooms
								x = Abs(EntityX(me\Collider) - EntityX(r\OBJ))
								z = Abs(EntityZ(me\Collider) - EntityZ(r\OBJ))
								
								If x < 12.0 And z < 12.0 Then
									CurrMapGrid\Found[Floor(EntityX(r\OBJ) / 8.0) + (Floor(EntityZ(r\OBJ) / 8.0) * MapGridSize)] = Max(CurrMapGrid\Found[Floor(EntityX(r\OBJ) / 8.0) + (Floor(EntityZ(r\OBJ) / 8.0) * MapGridSize)], 1.0)
									If x < 4.0 And z < 4.0 Then
										If Abs(EntityY(me\Collider) - EntityY(r\OBJ)) < 1.5 Then PlayerRoom = r
										CurrMapGrid\Found[Floor(EntityX(r\OBJ) / 8.0) + (Floor(EntityZ(r\OBJ) / 8.0) * MapGridSize)] = MapGrid_Tile
									EndIf
								EndIf
							Next
							
							RenderLoading(100)
							
							me\DropSpeed = 0.0
							
							UpdateWorld(0.0)
							
							fps\Factor[0] = 0.0
							
							ResetInput()
							MenuOpen = False
							Return
						EndIf
					Else
						UpdateMainMenuButton(x, y, 430 * MenuScale, 60 * MenuScale, GetLocalString("menu", "load"), True, False, True)
					EndIf
					y = y + (75 * MenuScale)
				EndIf
				
				If UpdateMainMenuButton(x, y, 430 * MenuScale, 60 * MenuScale, GetLocalString("menu", "achievements")) Then
					mm\AchievementsMenu = 1
					mm\ShouldDeleteGadgets = True
				EndIf
				
				y = y + (75 * MenuScale)
				
				If UpdateMainMenuButton(x, y, 430 * MenuScale, 60 * MenuScale, GetLocalString("menu", "options")) Then ChangeOptionTab(MenuTab_Options_Graphics, False)
				
				y = y + (75 * MenuScale)
				
				If UpdateMainMenuButton(x, y, 430 * MenuScale, 60 * MenuScale, GetLocalString("menu", "quit")) Then
					QuitMsg = 1
					mm\ShouldDeleteGadgets = True
				EndIf
			Else
				y = y + (75 * MenuScale)
				
				If SelectedDifficulty\SaveType <> NO_SAVES Then
					If GameSaved Then
						If UpdateMainMenuButton(x, y, 430 * MenuScale, 60 * MenuScale, GetLocalString("menu", "load")) Then
							RenderLoading(0, GetLocalString("loading", "files"))
							
							KillSounds()
							LoadGameQuick(CurrSave\Name)
							
							MoveMouse(mo\Viewport_Center_X, mo\Viewport_Center_Y)
							HidePointer()
							
							UpdateTimer = 0.0
							
							For r.Rooms = Each Rooms
								x = Abs(EntityX(me\Collider) - EntityX(r\OBJ))
								z = Abs(EntityZ(me\Collider) - EntityZ(r\OBJ))
								
								If x < 12.0 And z < 12.0 Then
									CurrMapGrid\Found[Floor(EntityX(r\OBJ) / 8.0) + (Floor(EntityZ(r\OBJ) / 8.0) * MapGridSize)] = Max(CurrMapGrid\Found[Floor(EntityX(r\OBJ) / 8.0) + (Floor(EntityZ(r\OBJ) / 8.0) * MapGridSize)], 1.0)
									If x < 4.0 And z < 4.0 Then
										If Abs(EntityY(me\Collider) - EntityY(r\OBJ)) < 1.5 Then PlayerRoom = r
										CurrMapGrid\Found[Floor(EntityX(r\OBJ) / 8.0) + (Floor(EntityZ(r\OBJ) / 8.0) * MapGridSize)] = MapGrid_Tile
									EndIf
								EndIf
							Next
							
							RenderLoading(100)
							
							me\DropSpeed = 0.0
							
							UpdateWorld(0.0)
							
							fps\Factor[0] = 0.0
							
							ResetInput()
							MenuOpen = False
							Return
						EndIf
					Else
						UpdateMainMenuButton(x, y, 430 * MenuScale, 60 * MenuScale, GetLocalString("menu", "load"), True, False, True)
					EndIf
					y = y + (75 * MenuScale)
				EndIf
				If UpdateMainMenuButton(x, y, 430 * MenuScale, 60 * MenuScale, GetLocalString("menu", "quitmenu")) Then
					NullGame()
					CurrSave = Null
					ResetInput()
					Return
				EndIf
			EndIf
		EndIf
	EndIf
	
	CatchErrors("UpdateMenu")
End Function

Function RenderMenu%()
	CatchErrors("Uncaught (RenderMenu)")
	
	Local x%, y%, Width%, Height%, i%
	Local TempStr$
	
	If (Not InFocus()) Then ; ~ Game is out of focus then pause the game
		MenuOpen = True
		PauseSounds()
		Delay(1000) ; ~ Reduce the CPU take while game is not in focus
	EndIf
	If MenuOpen Then
		Width = ImageWidth(t\ImageID[0])
		Height = ImageHeight(t\ImageID[0])
		x = mo\Viewport_Center_X - (Width / 2)
		y = mo\Viewport_Center_Y - (Height / 2)
		
		DrawBlock(t\ImageID[0], x, y)
		
		Color(255, 255, 255)
		
		If mm\AchievementsMenu > 0 Then
			TempStr = GetLocalString("menu", "achievements")
		ElseIf OptionsMenu > 0 Then
			TempStr = GetLocalString("menu", "options")
		ElseIf QuitMsg > 0 Then
			TempStr = GetLocalString("menu", "quit?")
		ElseIf (Not me\Terminated) Lor me\SelectedEnding <> -1
			TempStr = GetLocalString("menu", "paused")
		Else
			TempStr = GetLocalString("menu", "died")
		EndIf
		SetFont2(fo\FontID[Font_Default_Big])
		Text2(x + (Width / 2) + (47 * MenuScale), y + (48 * MenuScale), TempStr, True, True)
		SetFont2(fo\FontID[Font_Default])
		
		x = x + (132 * MenuScale)
		y = y + (122 * MenuScale)
		
		Local AchvXIMG% = x + (22 * MenuScale)
		Local Scale# = opt\GraphicHeight / 768.0
		Local SeparationConst% = 76 * Scale
		
		If mm\AchievementsMenu <= 0 And OptionsMenu > 0 And QuitMsg <= 0 Then
			Color(0, 255, 0)
			If OptionsMenu = MenuTab_Options_Graphics
				Rect(x - (10 * MenuScale), y - (5 * MenuScale), 110 * MenuScale, 40 * MenuScale, True)
			ElseIf OptionsMenu = MenuTab_Options_Audio
				Rect(x + (100 * MenuScale), y - (5 * MenuScale), 110 * MenuScale, 40 * MenuScale, True)
			ElseIf OptionsMenu = MenuTab_Options_Controls
				Rect(x + (210 * MenuScale), y - (5 * MenuScale), 110 * MenuScale, 40 * MenuScale, True)
			ElseIf OptionsMenu = MenuTab_Options_Advanced
				Rect(x + (320 * MenuScale), y - (5 * MenuScale), 110 * MenuScale, 40 * MenuScale, True)
			EndIf
			
			Local tX# = mo\Viewport_Center_X + (Width / 2)
			Local tY# = y
			Local tW# = 400.0 * MenuScale
			Local tH# = 150.0 * MenuScale
			
			Color(255, 255, 255)
			Select OptionsMenu
				Case MenuTab_Options_Graphics
					;[Block]
					SetFont2(fo\FontID[Font_Default])
					
					y = y + (50 * MenuScale)
					
					Color(100, 100, 100)
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "bump"))
					If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_BumpMapping)
					
					y = y + (30 * MenuScale)
					
					Color(255, 255, 255)
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "vsync"))
					If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_VSync)
					
					y = y + (30 * MenuScale)
					
					Color(255 - (155 * (opt\DisplayMode <> 0)), 255 - (155 * (opt\DisplayMode <> 0)), 255 - (155 * (opt\DisplayMode <> 0)))
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "antialias"))
					If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_AntiAliasing)
					
					y = y + (30 * MenuScale)
					
					Color(255, 255, 255)
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "lights"))
					If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_RoomLights)
					
					y = y + (40 * MenuScale)
					
					Color(255, 255, 255)
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "gamma"))
					If (MouseOn(x + (270 * MenuScale), y, 114 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 1 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_ScreenGamma, opt\ScreenGamma)
					
					y = y + (45 * MenuScale)
					
					Color(255, 255, 255)
					Text2(x, y, GetLocalString("options", "particle"))
					If (MouseOn(x + (270 * MenuScale), y - (9 * MenuScale), 114 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 2 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_ParticleAmount, opt\ParticleAmount)
					
					y = y + (45 * MenuScale)
					
					Color(255, 255, 255)
					Text2(x, y, GetLocalString("options", "lod"))
					If (MouseOn(x + (270 * MenuScale), y - (9 * MenuScale), 114 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 3 Then RenderOptionsTooltip(tX, tY, tW, tH + 100 * MenuScale, Tooltip_TextureLODBias)
					
					y = y + (35 * MenuScale)
					
					Color(100, 100, 100)
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "vram"))
					If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_SaveTexturesInVRAM)
					
					y = y + (40 * MenuScale)
					
					Color(255, 255, 255)
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "fov"))
					Color(255, 255, 0)
					If (MouseOn(x + (270 * MenuScale), y, 114 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 4 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_FOV)
					
					y = y + (45 * MenuScale)
					
					Color(255, 255, 255)
					Text2(x, y, GetLocalString("options", "filter"))
					If (MouseOn(x + (270 * MenuScale), y - (9 * MenuScale), 114 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 5 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_AnisotropicFiltering)
					
					y = y + (35 * MenuScale)
					
					Color(100, 100, 100)
					If opt\Atmosphere Then
						TempStr = GetLocalString("options", "atmo.bright")
					Else
						TempStr = GetLocalString("options", "atmo.dark")
					EndIf
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "atmo") + TempStr)
					If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_Atmosphere)
					;[End Block]
				Case MenuTab_Options_Audio
					;[Block]
					SetFont2(fo\FontID[Font_Default])
					
					y = y + (50 * MenuScale)
					
					Color(255, 255, 255)
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "mastervolume"))
					If (MouseOn(x + (250 * MenuScale), y, 114 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 1 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_MasterVolume, opt\MasterVolume)
					
					y = y + (40 * MenuScale)
					
					Color(255, 255, 255)
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "musicvolume"))
					If (MouseOn(x + (250 * MenuScale), y, 114 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 2 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_MusicVolume, opt\MusicVolume)
					
					y = y + (40 * MenuScale)
					
					Color(255, 255, 255)
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "soundvolume"))
					If (MouseOn(x + (250 * MenuScale), y, 114 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 3 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_SoundVolume, opt\SFXVolume)
					
					y = y + (40 * MenuScale)
					
					Color(100, 100, 100)
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "autorelease"))
					If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH + 220 * MenuScale, Tooltip_SoundAutoRelease)
					
					y = y + (30 * MenuScale)
					
					Color(100, 100, 100)
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "enabletracks"))
					If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_UserTracks)
					
					If opt\EnableUserTracks Then
						y = y + (30 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "trackmode"))
						If opt\UserTrackMode Then
							TempStr = GetLocalString("options", "track.repeat")
						Else
							TempStr = GetLocalString("options", "track.random")
						EndIf
						Text2(x + (310 * MenuScale), y + (5 * MenuScale), TempStr)
						If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_UserTracksMode)
						If MouseOn(x, y + (30 * MenuScale), 210 * MenuScale, 30 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_UserTrackScan)
					EndIf
					;[End Block]
				Case MenuTab_Options_Controls
					;[Block]
					SetFont2(fo\FontID[Font_Default])
					y = y + (50 * MenuScale)
					If mm\CurrMenuPage = 0 Then
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "mousesensitive"))
						If (MouseOn(x + (270 * MenuScale), y, 114 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 1 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_MouseSensitivity, opt\MouseSensitivity)
						
						y = y + (40 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "invertx"))
						If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_MouseInvertX)
						
						y = y + (40 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "inverty"))
						If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_MouseInvertY)
						
						y = y + (40 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "mousesmooth"))
						If (MouseOn(x + (270 * MenuScale), y, 114 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 2 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_MouseSmoothing, opt\MouseSmoothing)
						
						y = y + (40 * MenuScale)
						
						If MouseOn(x, y, 240 * MenuScale, 30 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_ControlConfiguration)
					Else
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("menu", "controlconfig"))
						
						y = y + (30 * MenuScale)
						
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "key.forward"))
						
						y = y + (20 * MenuScale)
						
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "key.left"))
						
						y = y + (20 * MenuScale)
						
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "key.backward"))
						
						y = y + (20 * MenuScale)
						
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "key.right"))
						
						y = y + (20 * MenuScale)
						
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "key.sprint"))
						
						y = y + (20 * MenuScale)
						
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "key.crouch"))
						
						y = y + (20 * MenuScale)
						
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "key.blink"))
						
						y = y + (20 * MenuScale)
						
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "key.inv"))
						
						y = y + (20 * MenuScale)
						
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "key.save"))
						
						y = y + (20 * MenuScale)
						
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "key.screenshot"))
						
						If opt\CanOpenConsole Then
							y = y + (20 * MenuScale)
							
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "key.console"))
						EndIf
						
						If MouseOn(x, y - ((180 + (20 * opt\CanOpenConsole)) * MenuScale), 310 * MenuScale, ((200 + (20 * opt\CanOpenConsole)) * MenuScale)) Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_ControlConfiguration)
						EndIf
					;[End Block]
				Case MenuTab_Options_Advanced
					;[Block]
					SetFont2(fo\FontID[Font_Default])
					
					y = y + (50 * MenuScale)
					
					Color(255, 255, 255)
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "hud"))
					If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_HUD)
					
					y = y + (30 * MenuScale)
					
					Color(255, 255, 255)
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "console"))
					If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_Console)
					
					y = y + (30 * MenuScale)
					
					If opt\CanOpenConsole Then
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "error"))
						If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_ConsoleOnError)
					EndIf
					
					y = y + (30 * MenuScale)
					
					Color(255, 255, 255)
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "achipop"))
					If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_AchievementPopups)
					
					y = y + (30 * MenuScale)
					
					Color(255 - (155 * (SelectedDifficulty\SaveType <> SAVE_ANYWHERE)), 255 - (155 * (SelectedDifficulty\SaveType <> SAVE_ANYWHERE)), 255 - (155 * (SelectedDifficulty\SaveType <> SAVE_ANYWHERE)))
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "save"))
					If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_AutoSave)
					
					y = y + (30 * MenuScale)
					
					Color(255, 255, 255)
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "fps"))
					If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_FPS)
					
					y = y + (30 * MenuScale)
					
					Color(255, 255, 255)
					Text2(x, y + (5 * MenuScale), GetLocalString("options", "frame"))
					Color(255, 255, 255)
					If MouseOn(x + (270 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_FrameLimit, opt\FrameLimit)
					If opt\CurrFrameLimit > 0.0 Then
						Color(255, 255, 0)
						Text2(x, y + (45 * MenuScale), opt\FrameLimit + " FPS")
						If (MouseOn(x + (150 * MenuScale), y + (40 * MenuScale), 114 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 1 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_FrameLimit, opt\FrameLimit)
					EndIf
					;[End Block]
			End Select
		ElseIf mm\AchievementsMenu <= 0 And OptionsMenu <= 0 And QuitMsg > 0
			; ~ Just save this line, ok?
		ElseIf mm\AchievementsMenu > 0 And OptionsMenu <= 0 And QuitMsg <= 0
			If mm\AchievementsMenu > 0 Then
				For i = 0 To 11
					If i + ((mm\AchievementsMenu - 1) * 12) < MAXACHIEVEMENTS Then
						RenderAchvIMG(AchvXIMG, y + ((i / 4) * 120 * MenuScale), i + ((mm\AchievementsMenu - 1) * 12))
					Else
						Exit
					EndIf
				Next
				For i = 0 To 11
					If i + ((mm\AchievementsMenu - 1) * 12) < MAXACHIEVEMENTS Then
						If MouseOn(AchvXIMG + ((i Mod 4) * SeparationConst), y + ((i / 4) * 120 * MenuScale), 64 * Scale, 64 * Scale) Then
							AchievementTooltip(i + ((mm\AchievementsMenu - 1) * 12))
							Exit
						EndIf
					Else
						Exit
					EndIf
				Next
			EndIf
		Else
			SetFont2(fo\FontID[Font_Default])
			Text2(x, y, GetLocalString("menu", "new.diff") + SelectedDifficulty\Name)
			If CurrSave = Null Then
				TempStr = GetLocalString("menu", "dataredacted")
			Else
				TempStr = ConvertToUTF8(CurrSave\Name)
			EndIf
			Text2(x, y + (20 * MenuScale), Format(GetLocalString("menu", "save"), TempStr))
			
			If SelectedCustomMap = Null Then
				TempStr = GetLocalString("menu", "new.seed") + RandomSeed
			Else
				If Len(ConvertToUTF8(SelectedCustomMap\Name)) > 15 Then
					TempStr = GetLocalString("menu", "new.map") + Left(ConvertToUTF8(SelectedCustomMap\Name), 14) + "..."
				Else
					TempStr = GetLocalString("menu", "new.map") + ConvertToUTF8(SelectedCustomMap\Name)
				EndIf
			EndIf
			Text2(x, y + (40 * MenuScale), TempStr)
			
			If me\Terminated And me\SelectedEnding = -1 Then
				y = y + (175 * MenuScale)
				If SelectedDifficulty\SaveType <> NO_SAVES Then
					y = y + (75 * MenuScale)
				EndIf
				SetFont2(fo\FontID[Font_Default])
				RowText(msg\DeathMsg, x, y, 430 * MenuScale, 600 * MenuScale)
			EndIf
		EndIf
		
		RenderMenuButtons()
		RenderMenuTicks()
		RenderMenuInputBoxes()
		RenderMenuSlideBars()
		RenderMenuSliders()
		
		If opt\DisplayMode = 0 Then DrawImage(CursorIMG, ScaledMouseX(), ScaledMouseY())
	EndIf
	
	SetFont2(fo\FontID[Font_Default])
	
	CatchErrors("RenderMenu")
End Function

; ~ Endings ID Constants
;[Block]
Const Ending_A1% = 0
Const Ending_A2% = 1
Const Ending_B1% = 2
Const Ending_B2% = 3
;[End Block]

Function UpdateEnding%()
	Local x%, y%, Width%, Height%, i%
	
	fps\Factor[0] = 0.0
	If me\EndingTimer > -2000.0 Then
		me\EndingTimer = Max(me\EndingTimer - fps\Factor[1], -1111.0)
	Else
		me\EndingTimer = me\EndingTimer - fps\Factor[1]
	EndIf
	
	GiveAchievement(Achv055)
	If ((Not UsedConsole) Lor opt\DebugMode) And SelectedCustomMap = Null Then
		GiveAchievement(AchvConsole)
		If SelectedDifficulty\Name = "Keter" Lor SelectedDifficulty\Name = "Apollyon" Then
			GiveAchievement(AchvKeter)
			SaveAchievementsFile()
		EndIf
	EndIf
	
	ShouldPlay = 66
	
	If me\EndingTimer < -200.0 Then
		StopBreathSound() : me\Stamina = 100.0
		
		If (Not me\EndingScreen) Then
			me\EndingScreen = LoadImage_Strict("GFX\Menu\ending_screen.png")
			me\EndingScreen = ScaleImage2(me\EndingScreen, MenuScale, MenuScale)
			
			ShouldPlay = 23
			opt\CurrMusicVolume = opt\MusicVolume
			StopStream_Strict(MusicCHN) : MusicCHN = 0
			MusicCHN = StreamSound_Strict("SFX\Music\" + Music[23] + ".ogg", opt\CurrMusicVolume * opt\MasterVolume, 0)
			NowPlaying = ShouldPlay
			
			PlaySound_Strict(LightSFX)
		EndIf
		
		If me\EndingTimer > -700.0 Then
			If me\EndingTimer + fps\Factor[1] > -450.0 And me\EndingTimer <= -450.0 Then PlaySound_Strict(LoadTempSound("SFX\Ending\Ending" + (me\SelectedEnding + 1) + ".ogg"))
		Else
			If me\EndingTimer < -1000.0 And me\EndingTimer > -2000.0 Then
				If mm\AchievementsMenu =< 0 Then
					Width = ImageWidth(t\ImageID[0])
					Height = ImageHeight(t\ImageID[0])
					x = mo\Viewport_Center_X - (Width / 2)
					y = mo\Viewport_Center_Y - (Height / 2)
					x = x + (132 * MenuScale)
					y = y + (432 * MenuScale)
					
					If UpdateMainMenuButton(x, y, 430 * MenuScale, 60 * MenuScale, GetLocalString("menu", "achievements"), True) Then
						mm\AchievementsMenu = 1
						mm\ShouldDeleteGadgets = True
					EndIf
					
					y = y + 75 * MenuScale
					
					If UpdateMainMenuButton(x, y, 430 * MenuScale, 60 * MenuScale, GetLocalString("menu", "mainmenu"), True)
						ShouldPlay = 24
						NowPlaying = ShouldPlay
						For i = 0 To 9
							If TempSounds[i] <> 0 Then FreeSound_Strict(TempSounds[i]) : TempSounds[i] = 0
						Next
						StopStream_Strict(MusicCHN) : MusicCHN = 0
						MusicCHN = StreamSound_Strict("SFX\Music\" + Music[NowPlaying] + ".ogg", 0.0, Mode)
						SetStreamVolume_Strict(MusicCHN, opt\MusicVolume * opt\MasterVolume)
						me\EndingTimer = -2000.0
						mm\ShouldDeleteGadgets = True
						ResetInput()
						InitCredits()
					EndIf
				Else
					ShouldPlay = 23
					UpdateMenu()
				EndIf
			; ~ Credits
			ElseIf me\EndingTimer <= -2000.0
				ShouldPlay = 24
				UpdateCredits()
			EndIf
		EndIf
	EndIf
End Function

Function RenderEnding%()
	ShowPointer()
	
	Local itt.ItemTemplates, r.Rooms
	Local x%, y%, Width%, Height%, i%
	
	Select me\SelectedEnding
		Case Ending_A1, Ending_B2
			;[Block]
			ClsColor(Max(255.0 + (me\EndingTimer) * 2.8, 0.0), Max(255.0 + (me\EndingTimer) * 2.8, 0.0), Max(255.0 + (me\EndingTimer) * 2.8, 0.0))
			;[End Block]
		Default
			;[Block]
			ClsColor(0, 0, 0)
			;[End Block]
	End Select
	
	Cls()
	
	If me\EndingTimer < -200.0 Then
		If me\EndingTimer > -700.0 Then
			If Rand(150) < Min((Abs(me\EndingTimer) - 200.0), 155.0) Then
				DrawBlock(me\EndingScreen, mo\Viewport_Center_X - (400 * MenuScale), mo\Viewport_Center_Y - (400 * MenuScale))
			Else
				Color(0, 0, 0)
				Rect(100, 100, opt\GraphicWidth - 200, opt\GraphicHeight - 200)
				Color(255, 255, 255)
			EndIf
		Else
			DrawBlock(me\EndingScreen, mo\Viewport_Center_X - (400 * MenuScale), mo\Viewport_Center_Y - (400 * MenuScale))
			
			If me\EndingTimer < -1000.0 And me\EndingTimer > -2000.0 Then
				Width = ImageWidth(t\ImageID[0])
				Height = ImageHeight(t\ImageID[0])
				x = mo\Viewport_Center_X - (Width / 2)
				y = mo\Viewport_Center_Y - (Height / 2)
				
				DrawBlock(t\ImageID[0], x, y)
				
				Color(255, 255, 255)
				SetFont2(fo\FontID[Font_Default_Big])
				Text2(x + (Width / 2) + (47 * MenuScale), y + (48 * MenuScale), GetLocalString("menu", "end"), True, True)
				SetFont2(fo\FontID[Font_Default])
				
				If mm\AchievementsMenu =< 0 Then
					x = x + (132 * MenuScale)
					y = y + (122 * MenuScale)
					
					Local RoomAmount% = 0, RoomsFound% = 0
					
					For r.Rooms = Each Rooms
						Local RN$ = r\RoomTemplate\Name
						
						If RN <> "gate_a" And RN <> "gate_b" And RN <> "dimension_106" And RN <> "dimension_1499" Then
							RoomAmount = RoomAmount + 1
							RoomsFound = RoomsFound + r\Found
						EndIf
					Next
					
					Local DocAmount% = 0, DocsFound% = 0
					
					For itt.ItemTemplates = Each ItemTemplates
						If itt\TempName = "paper" Then
							DocAmount = DocAmount + 1
							DocsFound = DocsFound + itt\Found
						EndIf
					Next
					
					Local SCPsEncountered% = 1
					
					For i = Achv005 To Achv1499
						SCPsEncountered = SCPsEncountered + achv\Achievement[i]
					Next
					
					Local AchievementsUnlocked% = 0
					
					For i = 0 To MAXACHIEVEMENTS - 1
						AchievementsUnlocked = AchievementsUnlocked + achv\Achievement[i]
					Next
					
					Local EscapeSeconds% = me\EscapeTimer Mod 60
					Local EscapeMinutes% = Floor(me\EscapeTimer / 60)
					Local EscapeHours% = Floor(EscapeMinutes / 60)
					
					EscapeMinutes = EscapeMinutes - (EscapeHours * 60)
					
					Text2(x, y, Format(GetLocalString("menu", "end.scps"), SCPsEncountered))
					Text2(x, y + (20 * MenuScale), Format(Format(GetLocalString("menu", "end.achi"), AchievementsUnlocked, "{0}"), MAXACHIEVEMENTS, "{1}"))
					Text2(x, y + (40 * MenuScale), Format(Format(GetLocalString("menu", "end.room"), RoomsFound, "{0}"), RoomAmount, "{1}"))
					Text2(x, y + (60 * MenuScale), Format(Format(GetLocalString("menu", "end.doc"), DocsFound, "{0}"), DocAmount, "{1}"))
					Text2(x, y + (80 * MenuScale), Format(GetLocalString("menu", "end.914"), me\RefinedItems))
					Text2(x, y + (100 * MenuScale), Format(Format(Format(GetLocalString("menu", "end.escape"), EscapeHours, "{0}"), EscapeMinutes, "{1}"), EscapeSeconds, "{2}"))
				Else
					RenderMenu()
				EndIf
			; ~ Credits
			ElseIf me\EndingTimer <= -2000.0
				RenderCredits()
			EndIf
		EndIf
	EndIf
	
	RenderMenuButtons()
	
	If opt\DisplayMode = 0 Then DrawImage(CursorIMG), ScaledMouseX(), ScaledMouseY()
	
	SetFont2(fo\FontID[Font_Default])
End Function

Type CreditsLine
	Field Txt$
	Field ID%
	Field Stay%
End Type

Function InitCredits%()
	Local cl.CreditsLine
	Local File% = OpenFile_Strict("Credits.txt")
	Local l$
	
	fo\FontID[Font_Credits] = LoadFont_Strict(FontsPath + GetFileLocalString(FontsFile, "Credits", "File"), GetFileLocalString(FontsFile, "Credits", "Size"))
	fo\FontID[Font_Credits_Big] = LoadFont_Strict(FontsPath + GetFileLocalString(FontsFile, "Credits_Big", "File"), GetFileLocalString(FontsFile, "Credits_Big", "Size"))
	
	If (Not me\CreditsScreen) Then
		me\CreditsScreen = LoadImage_Strict("GFX\Menu\credits_screen.png")
		me\CreditsScreen = ScaleImage2(me\CreditsScreen, MenuScale, MenuScale)
	EndIf
	
	Repeat
		l = ReadLine(File)
		cl.CreditsLine = New CreditsLine
		cl\Txt = l
	Until Eof(File)
	
	Delete First CreditsLine
	me\CreditsTimer = 0.0
End Function

Function UpdateCredits%()
	Local cl.CreditsLine, LastCreditLine.CreditsLine
	Local Credits_Y# = ((me\EndingTimer + 2000.0) / 2) + (opt\GraphicHeight + 10.0)
	Local ID%
	Local EndLinesAmount%
	
	ID = 0
	EndLinesAmount = 0
	LastCreditLine = Null
	For cl.CreditsLine = Each CreditsLine
		cl\ID = ID
		If Left(cl\Txt, 1) = "/" Then LastCreditLine = Before(cl)
		If LastCreditLine <> Null Then
			If cl\ID > LastCreditLine\ID Then cl\Stay = True
		EndIf
		If cl\Stay Then EndLinesAmount = EndLinesAmount + 1
		ID = ID + 1
	Next
	If (Credits_Y + (24 * LastCreditLine\ID * MenuScale)) < -StringHeight(LastCreditLine\Txt)
		me\CreditsTimer = me\CreditsTimer + (0.5 * fps\Factor[1])
		If me\CreditsTimer >= 0.0 And me\CreditsTimer < 255.0
			; ~ Just save this line, ok?
		ElseIf me\CreditsTimer >= 255.0
			If me\CreditsTimer > 500.0 Then me\CreditsTimer = -255.0
		Else
			If me\CreditsTimer >= -1.0 Then me\CreditsTimer = -1.0
		EndIf
	EndIf
	
	If GetKey() <> 0 Lor MouseHit(1) Then me\CreditsTimer = -1.0
	
	If me\CreditsTimer = -1.0 Then
		Delete Each CreditsLine
		NullGame(False)
		StopStream_Strict(MusicCHN) : MusicCHN = 0
		ShouldPlay = 21
		CurrSave = Null
		ResetLoadingTextColor()
		ResetInput()
		Return
	EndIf
End Function

Function RenderCredits%()
	Local cl.CreditsLine, LastCreditLine.CreditsLine
	Local Credits_Y# = (me\EndingTimer + 2000.0) / 2 + (opt\GraphicHeight + 10.0)
	Local ID%
	Local EndLinesAmount%
	
	Cls()
	
	If Rand(300) > 1 Then DrawBlock(me\CreditsScreen, mo\Viewport_Center_X - (400 * MenuScale), mo\Viewport_Center_Y - (400 * MenuScale))
	
	ID = 0
	EndLinesAmount = 0
	LastCreditLine = Null
	Color(255, 255, 255)
	For cl.CreditsLine = Each CreditsLine
		cl\ID = ID
		If Left(cl\Txt, 1) = "*" Then
			SetFont2(fo\FontID[Font_Credits_Big])
			If (Not cl\Stay) Then Text2(mo\Viewport_Center_X, Credits_Y + (24 * cl\ID * MenuScale), Right(cl\Txt, Len(cl\Txt) - 1), True)
		ElseIf Left(cl\Txt, 1) = "/"
			LastCreditLine = Before(cl)
		Else
			SetFont2(fo\FontID[Font_Credits])
			If (Not cl\Stay) Then Text2(mo\Viewport_Center_X, Credits_Y + (24 * cl\ID * MenuScale), cl\Txt, True)
		EndIf
		If LastCreditLine <> Null Then
			If cl\ID > LastCreditLine\ID Then cl\Stay = True
		EndIf
		If cl\Stay Then EndLinesAmount = EndLinesAmount + 1
		ID = ID + 1
	Next
	If (Credits_Y + (24 * LastCreditLine\ID * MenuScale)) < -StringHeight(LastCreditLine\Txt)
		If me\CreditsTimer >= 0.0 And me\CreditsTimer < 255.0
			Color(Max(Min(me\CreditsTimer, 255.0), 0.0), Max(Min(me\CreditsTimer, 255.0), 0.0), Max(Min(me\CreditsTimer, 255.0), 0.0))
		ElseIf me\CreditsTimer >= 255.0
			Color(255, 255, 255)
		Else
			Color(Max(Min(-me\CreditsTimer, 255.0), 0.0), Max(Min(-me\CreditsTimer, 255.0), 0.0), Max(Min(-me\CreditsTimer, 255.0), 0.0))
		EndIf
	EndIf
	If me\CreditsTimer <> 0.0 Then
		For cl.CreditsLine = Each CreditsLine
			If cl\Stay Then
				SetFont2(fo\FontID[Font_Credits])
				If Left(cl\Txt, 1) = "/" Then
					Text2(mo\Viewport_Center_X, mo\Viewport_Center_Y + (EndLinesAmount / 2) + (24 * cl\ID * MenuScale), Right(cl\Txt, Len(cl\Txt) - 1), True)
				Else
					Text2(mo\Viewport_Center_X, mo\Viewport_Center_Y + (24 * (cl\ID - LastCreditLine\ID) * MenuScale) - ((EndLinesAmount / 2) * 24 * MenuScale), cl\Txt, True)
				EndIf
			EndIf
		Next
	EndIf
	
	RenderLoadingText(20 * MenuScale, opt\GraphicHeight - (35 * MenuScale), GetLocalString("menu", "anykey"))
	
	Flip(True)
	
	If me\CreditsTimer = -1.0 Then
		FreeFont(fo\FontID[Font_Credits]) : fo\FontID[Font_Credits] = 0
		FreeFont(fo\FontID[Font_Credits_Big]) : fo\FontID[Font_Credits_Big] = 0
		FreeImage(me\CreditsScreen) : me\CreditsScreen = 0
		FreeImage(me\EndingScreen) : me\EndingScreen = 0
		Return
	EndIf
End Function

Function NullGame%(PlayButtonSFX% = True)
	CatchErrors("Uncaught (NullGame)")
	
	Local itt.ItemTemplates, s.Screens, lt.LightTemplates, d.Doors, m.Materials, de.Decals, sc.SecurityCams, e.Events
	Local wp.WayPoints, r.Rooms, it.Items, pr.Props, c.ConsoleMsg, n.NPCs, em.Emitters, rt.RoomTemplates, p.Particles, sub.Subtitles
	Local twp.TempWayPoints, ts.TempScreens, tp.TempProps
	Local i%, x%, y%, Lvl%
	
	KillSounds()
	If PlayButtonSFX Then PlaySound_Strict(ButtonSFX)
	
	DeleteTextureEntriesFromCache(DeleteAllTextures)
	
	QuickLoadPercent = -1
	QuickLoadPercent_DisplayTimer = 0.0
	QuickLoad_CurrEvent = Null
	
	SelectedCustomMap = Null
	
	UsedConsole = False
	
	RoomTempID = 0
	
	GameSaved = False
	CanSave = 0
	
	NullSelectedStuff()
	
	For itt.ItemTemplates = Each ItemTemplates
		itt\Found = False
	Next
	
	Delete Each DoorInstance
	Delete Each SecurityCamInstance
	Delete Each MonitorInstance
	Delete Each LeverInstance
	Delete Each NPCInstance
	Delete Each DecalInstance
	Delete Each ParticleInstance
	Delete Each MiscInstance
	
	; ~ Just remove the Type and create again
	Delete(me)
	me.Player = New Player
	
	Delete(wi)
	wi.WearableItems = New WearableItems
	
	RemoveHazmatTimer = 0.0
	Remove714Timer = 0.0
	
	Delete(I_005)
	I_005.SCP005 = New SCP005
	
	Delete(I_008)
	I_008.SCP008 = New SCP008
	
	Delete(I_035)
	I_035.SCP035 = New SCP035
	
	Delete(I_294)
	I_294.SCP294 = New SCP294
	
	Delete(I_409)
	I_409.SCP409 = New SCP409
	
	Delete(I_427)
	I_427.SCP427 = New SCP427
	
	Delete(I_500)
	I_500.SCP500 = New SCP500
	
	Delete(I_714)
	I_714.SCP714 = New SCP714
	
	Delete(I_1025)
	I_1025.SCP1025 = New SCP1025
	
	Delete(I_1499)
	I_1499.SCP1499 = New SCP1499
	
	ClearCheats()
	WireFrameState = 0
	WireFrame(0)
	
	CoffinDistance = 100.0
	
	MTFTimer = 0.0
	
	Delete(as)
	as.AutoSave = New AutoSave
	
	ShouldPlay = 66
	
	opt\CameraFogFar = 0.0
	opt\CameraFogNear = 0.0
	opt\StoredCameraFogFar = 0.0
	HideDistance = 0.0
	
	LightVolume = 0.0
	CurrFogColorR = 0.0
	CurrFogColorG = 0.0
	CurrFogColorB = 0.0
	CurrAmbientColorR = 0.0
	CurrAmbientColorG = 0.0
	CurrAmbientColorB = 0.0
	CurrFogColor = ""
	CurrAmbientColor = ""
	
	SecondaryLightOn = True
	PrevSecondaryLightOn = True
	RemoteDoorOn = True
	
	SoundTransmission = False
	
	BatMsgTimer = 0.0
	
	Delete(msg)
	msg.Messages = New Messages
	
	ConsoleInput = ""
	ConsoleOpen = False
	
	For c.ConsoleMsg = Each ConsoleMsg
		Delete(c)
	Next
	
	For sub.Subtitles = Each Subtitles
		Delete(sub)
	Next
	
	Delete(CurrMapGrid)
	Delete(I_Zone)
	I_Zone.MapZones = New MapZones
	
	For s.Screens = Each Screens
		Delete(s)
	Next
	SelectedScreen = Null
	
	For ts.TempScreens = Each TempScreens
		Delete(ts)
	Next
	
	For i = 0 To MaxItemAmount - 1
		Inventory(i) = Null
	Next
	ItemAmount = 0
	MaxItemAmount = 0
	SelectedItem = Null
	
	Dim Inventory.Items(0)
	
	GrabbedEntity = 0
	
	Delete(bk)
	bk.BrokenDoor = New BrokenDoor
	
	For d.Doors = Each Doors
		Delete(d)
	Next
	
	For lt.LightTemplates = Each LightTemplates
		Delete(lt)
	Next
	
	For m.Materials = Each Materials
		Delete(m)
	Next
	
	For wp.WayPoints = Each WayPoints
		Delete(wp)
	Next
	
	For twp.TempWayPoints = Each TempWayPoints
		Delete(twp)
	Next
	
	For r.Rooms = Each Rooms
		Delete(r)
	Next
	
	For itt.ItemTemplates = Each ItemTemplates
		Delete(itt)
	Next
	
	For it.Items = Each Items
		Delete(it)
	Next
	
	For pr.Props = Each Props
		Delete(pr)
	Next
	
	For tp.TempProps = Each TempProps
		Delete(tp)
	Next
	
	For de.Decals = Each Decals
		Delete(de)
	Next
	
	ForestNPC = 0
	ForestNPCTex = 0
	
	For n.NPCs = Each NPCs
		Delete(n)
	Next
	
	For e.Events = Each Events
		Delete(e)
	Next
	forest_event = Null
	skull_event = Null
	
	For sc.SecurityCams = Each SecurityCams
		Delete(sc)
	Next
	sc_I\SelectedMonitor = Null
	
	For em.Emitters = Each Emitters
		Delete(em)
	Next
	
	For p.Particles = Each Particles
		Delete(p)
	Next
	
	For rt.RoomTemplates = Each RoomTemplates
		If rt\OBJ <> 0 Then FreeEntity(rt\OBJ) : rt\OBJ = 0
	Next
	
	Delete(t)
	t.Textures = New Textures
	
	DeleteChunks()
	
	OptionsMenu = -1
	QuitMsg = -1
	mm\AchievementsMenu = -1
	
	Delete(achv)
	achv.Achievements = New Achievements
	
	Delete Each AchievementMsg
	CurrAchvMSGID = 0
	
	ClearWorld()
	ResetTimingAccumulator()
	If Camera <> 0 Then Camera = 0
	FreeBlur()
	If Sky <> 0 Then Sky = 0
	InitFastResize()
	
	; ~ Load main menu assets and open main menu
	mm\ShouldDeleteGadgets = True
	InitMainMenuAssets()
	MenuOpen = False
	MainMenuOpen = True
	mm\MainMenuTab = MainMenuTab_Default
	
	CatchErrors("NullGame")
End Function

Function Update294%()
	Local it.Items
	Local x#, y#, xTemp%, yTemp%, StrTemp$, Temp%
	Local Sep1%, Sep2%, Alpha#, Glow%
	Local R%, G%, B%
	
	x = mo\Viewport_Center_X - (ImageWidth(t\ImageID[5]) / 2)
	y = mo\Viewport_Center_Y - (ImageHeight(t\ImageID[5]) / 2)
	
	Temp = True
	If PlayerRoom\SoundCHN <> 0 Then Temp = False
	
	If Temp Then
		If mo\MouseHit1 Then
			xTemp = Floor((ScaledMouseX() - x - (228 * MenuScale)) / (35.5 * MenuScale))
			yTemp = Floor((ScaledMouseY() - y - (342 * MenuScale)) / (36.5 * MenuScale))
			
			Temp = False
			
			If (yTemp >= 0 And yTemp < 5) And (xTemp >= 0 And xTemp < 10) Then
				PlaySound_Strict(ButtonSFX)
				
				StrTemp = ""
				
				Select yTemp
					Case 0
						;[Block]
						StrTemp = ((xTemp + 1) Mod 10)
						;[End Block]
					Case 1
						;[Block]
						Select xTemp
							Case 0
								;[Block]
								StrTemp = "Q"
								;[End Block]
							Case 1
								;[Block]
								StrTemp = "W"
								;[End Block]
							Case 2
								;[Block]
								StrTemp = "E"
								;[End Block]
							Case 3
								;[Block]
								StrTemp = "R"
								;[End Block]
							Case 4
								;[Block]
								StrTemp = "T"
								;[End Block]
							Case 5
								;[Block]
								StrTemp = "Y"
								;[End Block]
							Case 6
								;[Block]
								StrTemp = "U"
								;[End Block]
							Case 7
								;[Block]
								StrTemp = "I"
								;[End Block]
							Case 8
								;[Block]
								StrTemp = "O"
								;[End Block]
							Case 9
								;[Block]
								StrTemp = "P"
								;[End Block]
						End Select
						;[End Block]
					Case 2
						;[Block]
						Select Int(xTemp)
							Case 0
								;[Block]
								StrTemp = "A"
								;[End Block]
							Case 1
								;[Block]
								StrTemp = "S"
								;[End Block]
							Case 2
								;[Block]
								StrTemp = "D"
								;[End Block]
							Case 3
								;[Block]
								StrTemp = "F"
								;[End Block]
							Case 4
								;[Block]
								StrTemp = "G"
								;[End Block]
							Case 5
								;[Block]
								StrTemp = "H"
								;[End Block]
							Case 6
								;[Block]
								StrTemp = "J"
								;[End Block]
							Case 7
								;[Block]
								StrTemp = "K"
								;[End Block]
							Case 8
								;[Block]
								StrTemp = "L"
								;[End Block]
							Case 9 ; ~ Dispense
								;[Block]
								Temp = True
								;[End Block]
						End Select
					Case 3
						;[Block]
						Select Int(xTemp)
							Case 0
								;[Block]
								StrTemp = "Z"
								;[End Block]
							Case 1
								;[Block]
								StrTemp = "X"
								;[End Block]
							Case 2
								;[Block]
								StrTemp = "C"
								;[End Block]
							Case 3
								;[Block]
								StrTemp = "V"
								;[End Block]
							Case 4
								;[Block]
								StrTemp = "B"
								;[End Block]
							Case 5
								;[Block]
								StrTemp = "N"
								;[End Block]
							Case 6
								;[Block]
								StrTemp = "M"
								;[End Block]
							Case 7
								;[Block]
								StrTemp = "-"
								;[End Block]
							Case 8
								;[Block]
								StrTemp = " "
								;[End Block]
							Case 9
								;[Block]
								I_294\ToInput = Left(I_294\ToInput, Max(Len(I_294\ToInput) - 1, 0.0))
								;[End Block]
						End Select
					Case 4
						;[Block]
						StrTemp = " "
						;[End Block]
				End Select
			EndIf
			
			I_294\ToInput = I_294\ToInput + StrTemp
			
			If Temp And I_294\ToInput <> "" Then ; ~ Dispense
				I_294\ToInput = Trim(I_294\ToInput)
				If Left(I_294\ToInput, Min(7, Len(I_294\ToInput))) = "cup of " Then
					I_294\ToInput = Right(I_294\ToInput, Len(I_294\ToInput) - 7)
				ElseIf Left(I_294\ToInput, Min(9, Len(I_294\ToInput))) = "a cup of "
					I_294\ToInput = Right(I_294\ToInput, Len(I_294\ToInput) - 9)
				EndIf
				
				If I_294\ToInput <> "" Then
					Local Drink$ = FindSCP294Drink(I_294\ToInput, True)
				EndIf
				
				If Drink <> "Null" Then
					StrTemp = GetFileLocalString(SCP294File, Drink, "Dispense Sound", "", False)
					If StrTemp = "" Then
						PlayerRoom\SoundCHN = PlaySound_Strict(LoadTempSound("SFX\SCP\294\Dispense1.ogg"))
					Else
						PlayerRoom\SoundCHN = PlaySound_Strict(LoadTempSound(StrTemp))
					EndIf
					
					If me\UsedMastercard Then
						PlaySound_Strict(LoadTempSound("SFX\SCP\294\PullMasterCard.ogg"))
						
						Local i%
						
						If ItemAmount < MaxItemAmount Then
							For i = 0 To MaxItemAmount - 1
								If Inventory(i) = Null Then
									Inventory(i) = CreateItem("Mastercard", "mastercard", 1.0, 1.0, 1.0)
									Inventory(i)\Picked = True
									Inventory(i)\Dropped = -1
									Inventory(i)\ItemTemplate\Found = True
									HideEntity(Inventory(i)\Collider)
									EntityType(Inventory(i)\Collider, HIT_ITEM)
									EntityParent(Inventory(i)\Collider, 0)
									Exit
								EndIf
							Next
						Else
							it.Items = CreateItem("Mastercard", "mastercard", EntityX(me\Collider), EntityY(me\Collider) + 0.3, EntityZ(me\Collider))
							it\ItemTemplate\Found = True
							EntityType(it\Collider, HIT_ITEM)
						EndIf
					EndIf
					
					If StringToBoolean(GetFileLocalString(SCP294File, Drink, "Explosion", "", False)) Then
						me\ExplosionTimer = 135.0
						msg\DeathMsg = GetFileLocalString(SCP294File, Drink, "Death Message", "", False)
					EndIf
					
					StrTemp = GetFileLocalString(SCP294File, Drink, "Color", "", False)
					
					Sep1 = Instr(StrTemp, ", ", 1)
					Sep2 = Instr(StrTemp, ", ", Sep1 + 1)
					R = Trim(Left(StrTemp, Sep1 - 1))
					G = Trim(Mid(StrTemp, Sep1 + 1, Sep2 - Sep1 - 1))
					B = Trim(Right(StrTemp, Len(StrTemp) - Sep2))
					
					Alpha = Float(GetFileLocalString(SCP294File, Drink, "Alpha", 1.0, False))
					Glow = GetFileLocalString(SCP294File, Drink, "Glow", "", False)
					If Glow Then Alpha = -Alpha
					
					it.Items = CreateItem("Cup", "cup", EntityX(PlayerRoom\Objects[1], True), EntityY(PlayerRoom\Objects[1], True), EntityZ(PlayerRoom\Objects[1], True), R, G, B, Alpha)
					it\Name = "Cup of " + Drink
					it\DisplayName = Format(GetLocalString("items", "cupof"), I_294\ToInput)
					EntityType(it\Collider, HIT_ITEM)
				Else
					; ~ Out of range
					I_294\ToInput = GetLocalString("misc", "ofr")
					PlayerRoom\SoundCHN = PlaySound_Strict(LoadTempSound("SFX\SCP\294\OutOfRange.ogg"))
				EndIf
			EndIf
		EndIf
		
		If mo\MouseHit2 Lor (Not I_294\Using) Then
			I_294\Using = False
			I_294\ToInput = ""
			StopMouseMovement()
		EndIf
	Else ; ~ Playing a dispensing sound
		If I_294\ToInput <> GetLocalString("misc", "ofr") Then I_294\ToInput = GetLocalString("misc", "dispensing")
		
		If (Not ChannelPlaying(PlayerRoom\SoundCHN)) Then
			If I_294\ToInput <> GetLocalString("misc", "ofr") Then
				I_294\Using = False
				me\UsedMastercard = False
				StopMouseMovement()
				
				Local e.Events
				
				For e.Events = Each Events
					If PlayerRoom = e\room Then
						e\EventState2 = 0.0
						Exit
					EndIf
				Next
			EndIf
			I_294\ToInput = ""
			PlayerRoom\SoundCHN = 0
		EndIf
	EndIf
End Function

Function Render294%()
	Local x#, y#, xTemp%, yTemp%, Temp%
	
	ShowPointer()
	
	x = mo\Viewport_Center_X - (ImageWidth(t\ImageID[5]) / 2)
	y = mo\Viewport_Center_Y - (ImageHeight(t\ImageID[5]) / 2)
	DrawBlock(t\ImageID[5], x, y)
	If opt\DisplayMode = 0 Then DrawImage(CursorIMG, ScaledMouseX(), ScaledMouseY())
	
	Temp = True
	If PlayerRoom\SoundCHN <> 0 Then Temp = False
	
	Text2(x + (905 * MenuScale), y + (185 * MenuScale), Right(I_294\ToInput, 13), True, True)
	
	If Temp Then
		If mo\MouseHit2 Lor (Not I_294\Using) Then HidePointer()
	Else ; ~ Playing a dispensing sound
		If (Not ChannelPlaying(PlayerRoom\SoundCHN)) Then
			If I_294\ToInput <> GetLocalString("misc", "ofr") Then HidePointer()
		EndIf
	EndIf
End Function

Function Use427%()
	Local de.Decals, e.Events
	Local i%, Pvt%, TempCHN%
	Local PrevI427Timer# = I_427\Timer
	
	If I_427\Timer < 70.0 * 360.0 Then
		If I_427\Using Then
			I_427\Timer = I_427\Timer + fps\Factor[0]
			For e.Events = Each Events
				If e\EventID = e_1048_a Then
					If e\EventState2 > 0.0 Then e\EventState2 = Max(e\EventState2 - (fps\Factor[0] * 0.5), 0.0)
					Exit
				EndIf
			Next
			If me\Injuries > 0.0 Then me\Injuries = Max(me\Injuries - (fps\Factor[0] * 0.0005), 0.0)
			If me\Bloodloss > 0.0 And me\Injuries <= 1.0 Then me\Bloodloss = Max(me\Bloodloss - (fps\Factor[0] * 0.001), 0.0)
			If I_008\Timer > 0.0 Then I_008\Timer = Max(I_008\Timer - (fps\Factor[0] * 0.001), 0.0)
			If I_409\Timer > 0.0 Then I_409\Timer = Max(I_409\Timer - (fps\Factor[0] * 0.003), 0.0)
			For i = 0 To 6
				If I_1025\State[i] > 0.0 Then I_1025\State[i] = Max(I_1025\State[i] - (0.001 * fps\Factor[0] * I_1025\State[7]), 0.0)
			Next
			If (Not I_427\Sound[0]) Then I_427\Sound[0] = LoadSound_Strict("SFX\SCP\427\Effect.ogg")
			If (Not ChannelPlaying(I_427\SoundCHN[0])) Then I_427\SoundCHN[0] = PlaySound_Strict(I_427\Sound[0])
			If I_427\Timer >= 70.0 * 180.0 Then
				If (Not I_427\Sound[1]) Then I_427\Sound[1] = LoadSound_Strict("SFX\SCP\427\Transform.ogg")
				If (Not ChannelPlaying(I_427\SoundCHN[1])) Then I_427\SoundCHN[1] = PlaySound_Strict(I_427\Sound[1])
			EndIf
			If PrevI427Timer < 70.0 * 60.0 And I_427\Timer >= 70.0 * 60.0 Then
				CreateMsg(GetLocalString("msg", "freshener"))
			ElseIf PrevI427Timer < 70.0 * 180.0 And I_427\Timer >= 70.0 * 180.0
				CreateMsg(GetLocalString("msg", "gentlemuscle"))
			EndIf
		Else
			For i = 0 To 1
				If ChannelPlaying(I_427\SoundCHN[i]) Then StopChannel(I_427\SoundCHN[i]) : I_427\SoundCHN[i] = 0
			Next
		EndIf
	Else
		If PrevI427Timer - fps\Factor[0] < 70.0 * 360.0 And I_427\Timer >= 70.0 * 360.0 Then
			CreateMsg(GetLocalString("msg", "muscleswelling"))
		ElseIf PrevI427Timer - fps\Factor[0] < 70.0 * 390.0 And I_427\Timer >= 70.0 * 390.0 Then
			CreateMsg(GetLocalString("msg", "nolegs"))
		EndIf
		I_427\Timer = I_427\Timer + fps\Factor[0]
		If (Not I_427\Sound[0]) Then I_427\Sound[0] = LoadSound_Strict("SFX\SCP\427\Effect.ogg")
		If (Not I_427\Sound[1]) Then I_427\Sound[1] = LoadSound_Strict("SFX\SCP\427\Transform.ogg")
		For i = 0 To 1
			If (Not ChannelPlaying(I_427\SoundCHN[i])) Then I_427\SoundCHN[i] = PlaySound_Strict(I_427\Sound[i])
		Next
		If Rnd(200) < 2.0 Then
			Pvt = CreatePivot()
			PositionEntity(Pvt, EntityX(me\Collider) + Rnd(-0.05, 0.05), EntityY(me\Collider) - 0.05, EntityZ(me\Collider) + Rnd(-0.05, 0.05))
			TurnEntity(Pvt, 90.0, 0.0, 0.0)
			EntityPick(Pvt, 0.3)
			de.Decals = CreateDecal(DECAL_427, PickedX(), PickedY() + 0.005, PickedZ(), 90.0, Rnd(360.0), 0.0, Rnd(0.03, 0.08) * 2.0)
			de\SizeChange = Rnd(0.001, 0.0015) : de\MaxSize = de\Size + 0.009
			EntityParent(de\OBJ, PlayerRoom\OBJ)
			TempCHN = PlaySound_Strict(DripSFX[Rand(0, 3)])
			ChannelVolume(TempCHN, Rnd(0.0, 0.8) * opt\SFXVolume * opt\MasterVolume)
			ChannelPitch(TempCHN, Rand(20000, 30000))
			FreeEntity(Pvt)
			me\BlurTimer = 800.0
		EndIf
		If I_427\Timer >= 70.0 * 420.0 Then
			Kill()
			msg\DeathMsg = GetLocalString("death", "morepower")
		ElseIf I_427\Timer >= 70.0 * 390.0
			If (Not me\Crouch) Then SetCrouch(True)
		EndIf
	EndIf
End Function

Function UpdateMTF%()
	If PlayerRoom\RoomTemplate\Name = "gate_a_entrance" Then Return
	
	Local r.Rooms, n.NPCs
	Local Dist#, i%
	
	If MTFTimer = 0.0 Then
		If Rand(200) = 1 And PlayerRoom\RoomTemplate\Name <> "dimension_1499" Then
			Local entrance.Rooms = Null
			
			For r.Rooms = Each Rooms
				If r\RoomTemplate\Name = "gate_a_entrance" Then 
					entrance = r
					Exit
				EndIf
			Next
			
			If entrance <> Null Then
				If Abs(EntityZ(entrance\OBJ) - EntityZ(me\Collider)) < 36.0 Then
					If me\Zone = 2 Then
						If PlayerInReachableRoom() Then PlayAnnouncement("SFX\Character\MTF\Announc.ogg")
						
						MTFTimer = fps\Factor[0]
						
						Local leader.NPCs
						
						For i = 0 To 2
							n.NPCs = CreateNPC(NPCTypeMTF, EntityX(entrance\OBJ) + 0.3 * (i - 1), 0.6, EntityZ(entrance\OBJ) + 8.0)
							
							If i = 0 Then
								leader = n
							Else
								n\MTFLeader = leader
							EndIf
							
							n\PrevX = i
						Next
					EndIf
				EndIf
			EndIf
		EndIf
	Else
		If MTFTimer <= 70.0 * 120.0 Then
			MTFTimer = MTFTimer + fps\Factor[0]
		ElseIf MTFTimer > 70.0 * 120.0 And MTFTimer < 10000.0
			If PlayerInReachableRoom() Then PlayAnnouncement("SFX\Character\MTF\AnnouncAfter1.ogg")
			MTFTimer = 10000.0
		ElseIf MTFTimer >= 10000.0 And MTFTimer <= 10000.0 + (70.0 * 120.0)
			MTFTimer = MTFTimer + fps\Factor[0]
		ElseIf MTFTimer > 10000.0 + (70.0 * 120.0) And MTFTimer < 20000.0
			If PlayerInReachableRoom() Then PlayAnnouncement("SFX\Character\MTF\AnnouncAfter2.ogg")
			MTFTimer = 20000.0
		ElseIf MTFTimer >= 20000.0 And MTFTimer <= 20000.0 + (70.0 * 60.0)
			MTFTimer = MTFTimer + fps\Factor[0]
		ElseIf MTFTimer > 20000.0 + (70.0 * 60.0) And MTFTimer < 25000.0
			If PlayerInReachableRoom() Then
				PlayAnnouncement("SFX\Character\MTF\ThreatAnnounc" + Rand(3) + ".ogg")
				; ~ If the player has an SCP in their inventory play special voice line
				For i = 0 To MaxItemAmount - 1
					If Inventory(i) <> Null Then
						If (Left(Inventory(i)\ItemTemplate\Name, 4) = "SCP-") And (Left(Inventory(i)\ItemTemplate\Name, 7) <> "SCP-035") And (Left(Inventory(i)\ItemTemplate\Name, 7) <> "SCP-093")
							PlayAnnouncement("SFX\Character\MTF\ThreatAnnouncPossession.ogg")
							Exit
						EndIf
					EndIf
				Next
			EndIf
			MTFTimer = 25000.0
		ElseIf MTFTimer >= 25000.0 And MTFTimer <= 25000.0 + (70.0 * 60.0)
			MTFTimer = MTFTimer + fps\Factor[0]
		ElseIf MTFTimer > 25000.0 + (70.0 * 60.0) And MTFTimer < 30000.0
			If PlayerInReachableRoom() Then PlayAnnouncement("SFX\Character\MTF\ThreatAnnouncFinal.ogg")
			MTFTimer = 30000.0
		EndIf
	EndIf
End Function

Function UpdateCameraCheck%()
	If MTFCameraCheckTimer > 0.0 And MTFCameraCheckTimer < 70.0 * 90.0 Then
		MTFCameraCheckTimer = MTFCameraCheckTimer + fps\Factor[0]
	ElseIf MTFCameraCheckTimer >= 70.0 * 90.0
		MTFCameraCheckTimer = 0.0
		If (Not me\Detected) Then
			If MTFCameraCheckDetected Then
				If PlayerInReachableRoom() Then PlayAnnouncement("SFX\Character\MTF\AnnouncCameraFound" + Rand(2) + ".ogg")
				me\Detected = True
				MTFCameraCheckTimer = 70.0 * 60.0
			Else
				If PlayerInReachableRoom() Then PlayAnnouncement("SFX\Character\MTF\AnnouncCameraNoFound.ogg")
			EndIf
		EndIf
		MTFCameraCheckDetected = False
		If MTFCameraCheckTimer = 0.0 Then me\Detected = False
	EndIf
End Function

Function UpdateExplosion%()
	Local p.Particles
	Local i%
	
	; ~ This here is necessary because the SCP-294's drinks with explosion effect didn't worked anymore -- ENDSHN
	If me\ExplosionTimer > 0.0 Then
		me\ExplosionTimer = me\ExplosionTimer + fps\Factor[0]
		If me\ExplosionTimer < 140.0 Then
			If me\ExplosionTimer - fps\Factor[0] < 5.0 Then
				ExplosionSFX = LoadSound_Strict("SFX\Ending\GateB\Nuke1.ogg")
				PlaySound_Strict(ExplosionSFX)
				me\BigCameraShake = 10.0
				me\ExplosionTimer = 5.0
			EndIf
			me\BigCameraShake = CurveValue(me\ExplosionTimer / 60.0, me\BigCameraShake, 50.0)
		Else
			me\BigCameraShake = Min((me\ExplosionTimer / 20.0), 20.0)
			If me\ExplosionTimer - fps\Factor[0] < 140.0 Then
				me\BlinkTimer = 1.0
				ExplosionSFX = LoadSound_Strict("SFX\Ending\GateB\Nuke2.ogg")
				PlaySound_Strict(ExplosionSFX)
				For i = 0 To (10 + (10 * (opt\ParticleAmount + 1)))
					p.Particles = CreateParticle(PARTICLE_BLACK_SMOKE, EntityX(me\Collider) + Rnd(-0.5, 0.5), EntityY(me\Collider) - Rnd(0.2, 1.5), EntityZ(me\Collider) + Rnd(-0.5, 0.5), Rnd(0.2, 0.6), 0.0, 350.0)
					RotateEntity(p\Pvt, -90.0, 0.0, 0.0, True)
					p\Speed = Rnd(0.05, 0.07)
				Next
			EndIf
			me\LightFlash = Min((me\ExplosionTimer - 140.0) / 10.0, 5.0)
			
			If me\ExplosionTimer > 160.0 Then me\Terminated = True
			If me\ExplosionTimer > 500.0 Then me\ExplosionTimer = 0.0
			
			; ~ A dirty workaround to prevent the collider from falling down into the facility once the nuke goes off, causing the UpdateEvents() function to be called again and crashing the game
			PositionEntity(me\Collider, EntityX(me\Collider), 200.0, EntityZ(me\Collider))
		EndIf
	EndIf
End Function

Function UpdateVomit%()
	CatchErrors("Uncaught (UpdateVomit)")
	
	Local de.Decals
	Local Pvt%
	
	If me\CameraShakeTimer > 0.0 Then
		me\CameraShakeTimer = Max(me\CameraShakeTimer - (fps\Factor[0] / 70.0), 0.0)
		me\CameraShake = 2.0
	EndIf
	
	If me\VomitTimer > 0.0 Then
		me\VomitTimer = me\VomitTimer - (fps\Factor[0] / 70.0)
		
		If (MilliSecs2() Mod 1600) < Rand(200, 400) Then
			If me\BlurTimer = 0.0 Then me\BlurTimer = 70.0 * Rnd(10.0, 20.0)
			me\CameraShake = Rnd(0.0, 2.0)
		EndIf
		
		If Rand(50) = 50 And (MilliSecs2() Mod 4000) < 200 Then PlaySound_Strict(CoughSFX[Rand(0, 2)])
		
		; ~ Regurgitate when timer is below 10 seconds
		If me\VomitTimer < 10.0 And Rnd(0.0, 500.0 * me\VomitTimer) < 2.0 Then
			If (Not ChannelPlaying(VomitCHN)) And me\Regurgitate = 0 Then
				VomitCHN = PlaySound_Strict(LoadTempSound("SFX\SCP\294\Retch" + Rand(2) + ".ogg"))
				me\Regurgitate = MilliSecs2() + 50
			EndIf
		EndIf
		
		If me\Regurgitate > MilliSecs2() And me\Regurgitate <> 0 Then
			mo\Mouse_Y_Speed_1 = mo\Mouse_Y_Speed_1 + 1.0
		Else
			me\Regurgitate = 0
		EndIf
	ElseIf me\VomitTimer < 0.0 Then ; ~ Vomit
		me\VomitTimer = me\VomitTimer - (fps\Factor[0] / 70.0)
		
		If me\VomitTimer > -5.0 Then
			If (MilliSecs2() Mod 400) < 50 Then me\CameraShake = 4.0
			mo\Mouse_X_Speed_1 = 0.0
			me\Playable = False
		Else
			me\Playable = True
		EndIf
		
		If (Not me\Vomit) Then
			me\BlurTimer = 70.0 * 40.0
			VomitSFX = LoadSound_Strict("SFX\SCP\294\Vomit.ogg")
			VomitCHN = PlaySound_Strict(VomitSFX)
			me\PrevInjuries = me\Injuries
			me\PrevBloodloss = me\Bloodloss
			If (Not me\Crouch) Then SetCrouch(True)
			me\Injuries = 1.5
			me\Bloodloss = 70.0
			me\EyeIrritation = 70.0 * 9.0
			
			Pvt = CreatePivot()
			PositionEntity(Pvt, EntityX(Camera), EntityY(me\Collider) - 0.05, EntityZ(Camera))
			TurnEntity(Pvt, 90.0, 0.0, 0.0)
			EntityPick(Pvt, 0.3)
			de.Decals = CreateDecal(DECAL_BLOOD_4, PickedX(), PickedY() + 0.005, PickedZ(), 90.0, 180.0, 0.0, 0.001, 1.0, 0, 1, 0, Rand(200, 255), 0)
			de\SizeChange = 0.001 : de\MaxSize = 0.6
			EntityParent(de\OBJ, PlayerRoom\OBJ)
			FreeEntity(Pvt)
			me\Vomit = True
		EndIf
		
		mo\Mouse_Y_Speed_1 = mo\Mouse_Y_Speed_1 + Max((1.0 + me\VomitTimer / 10.0), 0.0)
		
		If me\VomitTimer < -15.0 Then
			FreeSound_Strict(VomitSFX)
			me\VomitTimer = 0.0
			If (Not me\Terminated) Then PlaySound_Strict(BreathSFX(0, 0))
			me\Injuries = me\PrevInjuries
			me\Bloodloss = me\PrevBloodloss
			me\Vomit = False
		EndIf
	EndIf
	
	CatchErrors("UpdateVomit")
End Function

Global EscapeSecondsTimer# = 70.0

Function UpdateEscapeTimer%()
	CatchErrors("Uncaught (UpdateEscapeTimer)")
	
	For ev.Events = Each Events
		If ev\room = PlayerRoom Then
			If ev\EventName = "cont1_173_intro" Then Return
			Exit
		EndIf
	Next
	
	EscapeSecondsTimer = EscapeSecondsTimer - fps\Factor[0]
	
	If EscapeSecondsTimer <= 0 Then
		me\EscapeTimer = me\EscapeTimer + 1
		EscapeSecondsTimer = 70.0
	EndIf
	
	CatchErrors("UpdateEscapeTimer")
End Function

Function Update008%()
	Local r.Rooms, e.Events, p.Particles, de.Decals
	Local PrevI008Timer#, i%
	Local TeleportForInfect% = True
	
	If (Not PlayerInReachableRoom()) Then TeleportForInfect = False
	If I_008\Timer > 0.0 Then
		If EntityHidden(t\OverlayID[3]) Then ShowEntity(t\OverlayID[3])
		If I_008\Timer < 93.0 Then
			PrevI008Timer = I_008\Timer
			If I_427\Timer < 70.0 * 360.0 Then
				If I_008\Revert Then
					I_008\Timer = Max(0.0, I_008\Timer - (fps\Factor[0] * 0.01))
				Else
					If (Not I_427\Using) Then I_008\Timer = Min(I_008\Timer + (fps\Factor[0] * 0.002), 100.0)
				EndIf
			EndIf
			
			me\BlurTimer = Max(I_008\Timer * 3.0 * (2.0 - me\CrouchState), me\BlurTimer)
			
			me\HeartBeatRate = Max(me\HeartBeatRate, 100.0)
			me\HeartBeatVolume = Max(me\HeartBeatVolume, I_008\Timer / 120.0)
			
			EntityAlpha(t\OverlayID[3], Min(((I_008\Timer * 0.2) ^ 2.0) / 1000.0, 0.5) * (Sin(MilliSecs2() / 8.0) + 2.0))
			
			For i = 0 To 6
				If I_008\Timer > (i * 15.0) + 10.0 And PrevI008Timer <= (i * 15.0) + 10.0 Then
					If (Not I_008\Revert) Then PlaySound_Strict(LoadTempSound("SFX\SCP\008\Voices" + i + ".ogg"))
				EndIf
			Next
			
			If I_008\Timer > 20.0 And PrevI008Timer <= 20.0 Then
				If I_008\Revert Then
					CreateMsg(GetLocalString("msg", "better_2"))
				Else
					CreateMsg(GetLocalString("msg", "feverish"))
				EndIf
			ElseIf I_008\Timer > 40.0 And PrevI008Timer <= 40.0
				If I_008\Revert Then
					CreateMsg(GetLocalString("msg", "nauseafading"))
				Else
					CreateMsg(GetLocalString("msg", "nausea"))
				EndIf
			ElseIf I_008\Timer > 60.0 And PrevI008Timer <= 60.0
				If I_008\Revert Then
					CreateMsg(GetLocalString("msg", "headachefading"))
				Else
					CreateMsg(GetLocalString("msg", "nauseaworse"))
				EndIf
			ElseIf I_008\Timer > 80.0 And PrevI008Timer <= 80.0
				If I_008\Revert Then
					CreateMsg(GetLocalString("msg", "moreener"))
				Else
					CreateMsg(GetLocalString("msg", "faint"))
				EndIf
			ElseIf I_008\Timer >= 91.5
				me\BlinkTimer = Max(Min((-10.0) * (I_008\Timer - 91.5), me\BlinkTimer), -10.0)
				me\Zombie = True
				If I_008\Timer >= 92.7 And PrevI008Timer < 92.7 Then
					If TeleportForInfect Then
						For r.Rooms = Each Rooms
							If r\RoomTemplate\Name = "cont2_008" Then
								PositionEntity(me\Collider, EntityX(r\Objects[7], True), EntityY(r\Objects[7], True), EntityZ(r\Objects[7], True), True)
								ResetEntity(me\Collider)
								r\NPC[0] = CreateNPC(NPCTypeD, EntityX(r\Objects[6], True), EntityY(r\Objects[6], True) + 0.2, EntityZ(r\Objects[6], True))
								r\NPC[0]\State3 = -1.0 : r\NPC[0]\IsDead = True
								r\NPC[0]\Sound = LoadSound_Strict("SFX\SCP\008\KillScientist1.ogg")
								r\NPC[0]\SoundCHN = PlaySound_Strict(r\NPC[0]\Sound)
								ChangeNPCTextureID(r\NPC[0], NPC_CLASS_D_VICTIM_008_TEXTURE)
								TeleportToRoom(r)
								Exit
							EndIf
						Next
					EndIf
				EndIf
			EndIf
		Else
			PrevI008Timer = I_008\Timer
			I_008\Timer = Min(I_008\Timer + (fps\Factor[0] * 0.004), 100.0)
			
			If TeleportForInfect Then
				If I_008\Timer < 94.7 Then
					EntityAlpha(t\OverlayID[3], 0.5 * (Sin(MilliSecs2() / 8.0) + 2.0))
					me\BlurTimer = 900.0
					
					If I_008\Timer > 94.5 Then me\BlinkTimer = Max(Min((-50.0) * (I_008\Timer - 94.5), me\BlinkTimer), -10.0)
					PointEntity(me\Collider, PlayerRoom\NPC[0]\Collider)
					PointEntity(PlayerRoom\NPC[0]\Collider, me\Collider)
					PointEntity(Camera, PlayerRoom\NPC[0]\Collider, EntityRoll(Camera))
					me\ForceMove = 0.75
					me\Injuries = 2.5
					me\Bloodloss = 0.0
					
					Animate2(PlayerRoom\NPC[0]\OBJ, AnimTime(PlayerRoom\NPC[0]\OBJ), 357.0, 381.0, 0.3)
				ElseIf I_008\Timer < 98.5
					EntityAlpha(t\OverlayID[3], 0.5 * (Sin(MilliSecs2() / 5.0) + 2.0))
					me\BlurTimer = 950.0
					
					me\ForceMove = 0.0
					PointEntity(Camera, PlayerRoom\NPC[0]\Collider)
					
					If PrevI008Timer < 94.7 Then
						PlayerRoom\NPC[0]\Sound = LoadSound_Strict("SFX\SCP\008\KillScientist2.ogg")
						PlayerRoom\NPC[0]\SoundCHN = PlaySound_Strict(PlayerRoom\NPC[0]\Sound)
						
						msg\DeathMsg = Format(GetLocalString("death", "0081"), SubjectName)
						Kill()
						de.Decals = CreateDecal(DECAL_BLOOD_2, EntityX(PlayerRoom\NPC[0]\Collider), 544.0 * RoomScale + 0.01, EntityZ(PlayerRoom\NPC[0]\Collider), 90.0, Rnd(360.0), 0.0, 0.8)
						EntityParent(de\OBJ, PlayerRoom\OBJ)
					ElseIf I_008\Timer > 96.0
						me\BlinkTimer = Max(Min((-10.0) * (I_008\Timer - 96.0), me\BlinkTimer), -10.0)
					Else
						me\Terminated = True
					EndIf
					
					If PlayerRoom\NPC[0]\State2 = 0.0 Then
						Animate2(PlayerRoom\NPC[0]\OBJ, AnimTime(PlayerRoom\NPC[0]\OBJ), 13.0, 19.0, 0.3, False)
						If AnimTime(PlayerRoom\NPC[0]\OBJ) >= 19.0 Then PlayerRoom\NPC[0]\State2 = 1.0
					Else
						Animate2(PlayerRoom\NPC[0]\OBJ, AnimTime(PlayerRoom\NPC[0]\OBJ), 19.0, 13.0, -0.3)
						If AnimTime(PlayerRoom\NPC[0]\OBJ) <= 13.0 Then PlayerRoom\NPC[0]\State2 = 0.0
					EndIf
					
					If opt\ParticleAmount > 0 Then
						If Rand(50) = 1 Then
							p.Particles = CreateParticle(PARTICLE_BLOOD, EntityX(PlayerRoom\NPC[0]\Collider), EntityY(PlayerRoom\NPC[0]\Collider), EntityZ(PlayerRoom\NPC[0]\Collider), Rnd(0.05, 0.1), 0.15, 200.0)
							p\Speed = 0.01 : p\SizeChange = 0.01 : p\Alpha = 0.5 : p\AlphaChange = -0.01
							RotateEntity(p\Pvt, Rnd(360.0), Rnd(360.0), 0.0)
						EndIf
					EndIf
					
					PositionEntity(me\Head, EntityX(PlayerRoom\NPC[0]\Collider, True), EntityY(PlayerRoom\NPC[0]\Collider, True) + 0.65, EntityZ(PlayerRoom\NPC[0]\Collider, True), True)
					RotateEntity(me\Head, (1.0 + Sin(MilliSecs2() / 5.0)) * 15.0, PlayerRoom\Angle - 180.0, 0.0, True)
					MoveEntity(me\Head, 0.0, 0.0, -0.4)
					TurnEntity(me\Head, 80.0 + (Sin(MilliSecs2() / 5.0)) * 30.0, (Sin(MilliSecs2() / 5.0)) * 40.0, 0.0)
				EndIf
			Else
				Kill()
				me\BlinkTimer = Max(Min((-10.0) * (I_008\Timer - 96.0), me\BlinkTimer), -10.0)
				If PlayerRoom\RoomTemplate\Name = "dimension_1499" Then
					msg\DeathMsg = GetLocalString("death", "14991")
				ElseIf IsPlayerOutsideFacility() Then
					msg\DeathMsg = Format(GetLocalString("death", "008gate"), SubjectName, "{0}")
					If PlayerRoom\RoomTemplate\Name = "gate_a" Then
						msg\DeathMsg = Format(msg\DeathMsg, "A", "{1}")
					Else
						msg\DeathMsg = Format(msg\DeathMsg, "B", "{1}")
					EndIf
				Else
					msg\DeathMsg = ""
				EndIf
			EndIf
		EndIf
	Else
		If I_008\Revert Then I_008\Revert = False
		If (Not EntityHidden(t\OverlayID[3])) Then HideEntity(t\OverlayID[3])
	EndIf
End Function

Function Update409%()
	Local PrevI409Timer# = I_409\Timer
	
	If I_409\Timer > 0.0 Then
		If EntityHidden(t\OverlayID[7]) Then ShowEntity(t\OverlayID[7])
		If I_427\Timer < 70.0 * 360.0 Then
			If I_409\Revert Then
				I_409\Timer = Max(0.0, I_409\Timer - (fps\Factor[0] * 0.01))
			Else
				If (Not I_427\Using) Then I_409\Timer = Min(I_409\Timer + (fps\Factor[0] * 0.004), 100.0)
			EndIf
		EndIf
		EntityAlpha(t\OverlayID[7], Min(((I_409\Timer * 0.2) ^ 2.0) / 1000.0, 0.5))
		me\BlurTimer = Max(I_409\Timer * 3.0 * (2.0 - me\CrouchState), me\BlurTimer)
		
		If I_409\Timer > 40.0 And PrevI409Timer <= 40.0 Then
			If I_409\Revert Then
				CreateMsg(GetLocalString("msg", "409legs_1"))
			Else
				CreateMsg(GetLocalString("msg", "409legs_2"))
			EndIf
		ElseIf I_409\Timer > 55.0 And PrevI409Timer <= 55.0
			If I_409\Revert Then
				CreateMsg(GetLocalString("msg", "409abdomen_1"))
			Else
				CreateMsg(GetLocalString("msg", "409abdomen_2"))
			EndIf
		ElseIf I_409\Timer > 70.0 And PrevI409Timer <= 70.0
			If I_409\Revert Then
				CreateMsg(GetLocalString("msg", "409arms_1"))
			Else
				CreateMsg(GetLocalString("msg", "409arms_2"))
			EndIf
		ElseIf I_409\Timer > 85.0 And PrevI409Timer <= 85.0
			If I_409\Revert Then
				CreateMsg(GetLocalString("msg", "409head_1"))
			Else
				CreateMsg(GetLocalString("msg", "409head_2"))
			EndIf
		ElseIf I_409\Timer > 93.0 And PrevI409Timer <= 93.0
			If (Not I_409\Revert) Then
				PlaySound_Strict(DamageSFX[13])
				me\Injuries = Max(me\Injuries, 2.0)
			EndIf
		ElseIf I_409\Timer > 94.0
			I_409\Timer = Min(I_409\Timer + (fps\Factor[0] * 0.004), 100.0)
			me\Playable = False
			me\BlurTimer = 4.0
			me\CameraShake = 3.0
		EndIf
		If I_409\Timer >= 55.0 Then
			me\StaminaEffect = 1.2
			me\StaminaEffectTimer = 1.0
			me\Stamina = Min(me\Stamina, 60.0)
		EndIf
		If I_409\Timer >= 96.9222 Then
			msg\DeathMsg = Format(GetLocalString("death", "409"), SubjectName)
			Kill(True)
		EndIf
	Else
		If I_409\Revert Then I_409\Revert = False
		If (Not EntityHidden(t\OverlayID[7])) Then HideEntity(t\OverlayID[7])
	EndIf
End Function

Function Update1025%()
	Local i%
	Local Factor1025# = fps\Factor[0] * I_1025\State[7]
	
	For i = 0 To 6
		If I_1025\State[i] > 0.0 Then
			Select i
				Case 0 ; ~ Common cold
					;[Block]
					UpdateCough(1000)
					me\Stamina = me\Stamina - (Factor1025 * 0.3)
					;[End Block]
				Case 1 ; ~ Chicken pox
					;[Block]
					If Rand(9000) = 1 Then CreateMsg(GetLocalString("msg", "skinitchy"))
					;[End Block]
				Case 2 ; ~ Cancer of the lungs
					;[Block]
					UpdateCough(800)
					me\Stamina = me\Stamina - (Factor1025 * 0.1)
					;[End Block]
				Case 3 ; ~ Appendicitis
					; ~ 0.035 / sec = 2.1 / min
					If (Not I_427\Using) And I_427\Timer < 70.0 * 360.0 Then I_1025\State[i] = I_1025\State[i] + (Factor1025 * 0.0005)
					If I_1025\State[i] > 20.0 Then
						If I_1025\State[i] - Factor1025 <= 20.0 Then CreateMsg(GetLocalString("msg", "stomachunbearable"))
						me\Stamina = me\Stamina - (Factor1025 * 0.3)
					ElseIf I_1025\State[i] > 10.0
						If I_1025\State[i] - Factor1025 <= 10.0 Then CreateMsg(GetLocalString("msg", "stomachaching"))
					EndIf
					;[End Block]
				Case 4 ; ~ Asthma
					;[Block]
					If me\Stamina < 35.0 Then
						UpdateCough(Int(140.0 + me\Stamina * 8.0))
						me\CurrSpeed = CurveValue(0.0, me\CurrSpeed, 10.0 + me\Stamina * 15.0)
					EndIf
					;[End Block]
				Case 5 ; ~ Cardiac arrest
					;[Block]
					If (Not I_427\Using) And I_427\Timer < 70.0 * 360.0 Then I_1025\State[i] = I_1025\State[i] + (Factor1025 * 0.35)
					
					; ~ 35 / sec
					If I_1025\State[i] > 110.0 Then
						me\HeartBeatRate = 0.0
						me\BlurTimer = Max(me\BlurTimer, 500.0)
						If I_1025\State[i] > 140.0 Then
							msg\DeathMsg = GetLocalString("death", "1025")
							Kill()
						EndIf
					Else
						me\HeartBeatRate = Max(me\HeartBeatRate, 70.0 + I_1025\State[i])
						me\HeartBeatVolume = 1.0
					EndIf
					;[End Block]
				Case 6 ; ~ Secondary polycythemia
					;[Block]
					If (Not I_427\Using) And I_427\Timer < 70.0 * 360.0 Then I_1025\State[i] = I_1025\State[i] + 0.00025 * Factor1025 * (100.0 / I_1025\State[i])
					me\Stamina = Min(100.0, me\Stamina + (90.0 - me\Stamina) * I_1025\State[i] * Factor1025 * 0.00008)
					If I_1025\State[i] > 15.0 And I_1025\State[i] - Factor1025 <= 15.0 Then CreateMsg(GetLocalString("msg", "energetic"))
					;[End Block]
			End Select
		EndIf
	Next
End Function

Function UpdateLeave1499%()
	Local r.Rooms, it.Items, r2.Rooms, r1499.Rooms
	Local i%
	
	If I_1499\Using = 0 And PlayerRoom\RoomTemplate\Name = "dimension_1499" Then
		For r.Rooms = Each Rooms
			If r = I_1499\PrevRoom Then
				me\BlinkTimer = -1.0
				I_1499\x = EntityX(me\Collider)
				I_1499\y = EntityY(me\Collider)
				I_1499\z = EntityZ(me\Collider)
				TeleportEntity(me\Collider, I_1499\PrevX, I_1499\PrevY + 0.05, I_1499\PrevZ)
				TeleportToRoom(r)
				If PlayerRoom\RoomTemplate\Name = "room3_storage" And EntityY(me\Collider) < (-4600.0) * RoomScale Then
					For i = 0 To 3
						PlayerRoom\NPC[i]\State = 2.0
						PositionEntity(PlayerRoom\NPC[i]\Collider, EntityX(PlayerRoom\Objects[PlayerRoom\NPC[i]\State2], True), EntityY(PlayerRoom\Objects[PlayerRoom\NPC[i]\State2], True) + 0.2, EntityZ(PlayerRoom\Objects[PlayerRoom\NPC[i]\State2], True))
						ResetEntity(PlayerRoom\NPC[i]\Collider)
						PlayerRoom\NPC[i]\State2 = PlayerRoom\NPC[i]\State2 + 1.0
						If PlayerRoom\NPC[i]\State2 > PlayerRoom\NPC[i]\PrevState Then PlayerRoom\NPC[i]\State2 = (PlayerRoom\NPC[i]\PrevState - 3)
					Next
				EndIf
				For r2.Rooms = Each Rooms
					If r2\RoomTemplate\Name = "dimension_1499" Then
						r1499 = r2
						Exit
					EndIf
				Next
				For it.Items = Each Items
					it\DistTimer = 0.0
					If it\ItemTemplate\TempName = "scp1499" Lor it\ItemTemplate\TempName = "fine1499" Then
						If EntityY(it\Collider) >= EntityY(r1499\OBJ) - 5.0 Then
							PositionEntity(it\Collider, I_1499\PrevX, I_1499\PrevY + (EntityY(it\Collider) - EntityY(r1499\OBJ)), I_1499\PrevZ)
							ResetEntity(it\Collider)
							Exit
						EndIf
					EndIf
				Next
				r1499 = Null
				ShouldEntitiesFall = False
				PlaySound_Strict(LoadTempSound("SFX\SCP\1499\Exit.ogg"))
				I_1499\PrevX = 0.0
				I_1499\PrevY = 0.0
				I_1499\PrevZ = 0.0
				I_1499\PrevRoom = Null
				Exit
			EndIf
		Next
	EndIf
End Function

Function CheckForPlayerInFacility%()
	; ~ False (= 0): Player is not in facility (mostly meant for "dimension_1499")
	; ~ True (= 1): Player is in facility
	; ~ 2: Player is in tunnels (maintenance tunnels / SCP-049's tunnels / SCP-939's storage room, etc...)
	
	If EntityY(me\Collider) > 100.0 Then Return(0)
	If EntityY(me\Collider) < -10.0 Then Return(2)
	If (EntityY(me\Collider) > 7.0) And (EntityY(me\Collider) <= 100.0) Then Return(2)
	Return(1)
End Function

Function TeleportEntity%(Entity%, x#, y#, z#, CustomRadius# = 0.3, IsGlobal% = False, PickRange# = 2.0, Dir% = False)
	Local Pvt%, Pick#
	; ~ Dir = 0 - towards the floor (default)
	; ~ Dir = 1 - towrads the ceiling (mostly for PD decal after leaving dimension)
	
	Pvt = CreatePivot()
	PositionEntity(Pvt, x, y + 0.05, z, IsGlobal)
	If (Not Dir)
		RotateEntity(Pvt, 90.0, 0.0, 0.0)
	Else
		RotateEntity(Pvt, -90.0, 0.0, 0.0)
	EndIf
	Pick = EntityPick(Pvt, PickRange)
	If Pick <> 0 Then
		If (Not Dir) Then
			PositionEntity(Entity, x, PickedY() + CustomRadius + 0.02, z, IsGlobal)
		Else
			PositionEntity(Entity, x, PickedY() + CustomRadius - 0.02, z, IsGlobal)
		EndIf
	Else
		PositionEntity(Entity, x, y, z, IsGlobal)
	EndIf
	FreeEntity(Pvt)
	ResetEntity(Entity)
End Function

Function InteractObject%(OBJ%, Dist#, Arrow% = False, ArrowID% = 0, MouseDown_% = False)
	If InvOpen Lor I_294\Using Lor OtherOpen <> Null Lor d_I\SelectedDoor <> Null Lor SelectedScreen <> Null Then Return
	
	If EntityDistanceSquared(me\Collider, OBJ) < Dist Then
		If EntityInView(OBJ, Camera) Then
			If Arrow Then ga\DrawArrowIcon[ArrowID] = True
			ga\DrawHandIcon = True
			If MouseDown_ Then
				If mo\MouseDown1 Then Return(True)
			Else
				If mo\MouseHit1 Then Return(True)
			EndIf
		EndIf
	EndIf
	Return(False)
End Function

;~IDEal Editor Parameters:
;~C#Blitz3D