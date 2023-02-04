Type MainMenu
	Field MainMenuBlinkTimer#[2]
	Field MainMenuBlinkDuration#[2]
	Field MainMenuStr$, MainMenuStrX%, MainMenuStrY%
	Field MainMenuTab%, PrevMainMenuTab%
	Field ShouldDeleteGadgets%
	Field CurrMenuPage%
	Field OnSliderID%, OnPalette%
	Field AchievementsMenu%
End Type

Global mm.MainMenu = New MainMenu

Type MainMenuAssets
	Field BackGround%
	Field SECURE_CONTAIN_PROTECT%
	Field SCP173%
	Field Palette%
End Type

Global mma.MainMenuAssets

MenuWhite = LoadImage_Strict("GFX\Menu\menu_white.png")
MenuGray = LoadImage_Strict("GFX\Menu\menu_gray.png")
MenuBlack = LoadImage_Strict("GFX\Menu\menu_black.png")

Function InitMainMenuAssets%()
	mma.MainMenuAssets = New MainMenuAssets
	
	mma\BackGround = LoadImage_Strict("GFX\Menu\back.png")
	mma\BackGround = ScaleImage2(mma\BackGround, MenuScale, MenuScale)
	
	mma\SECURE_CONTAIN_PROTECT = LoadImage_Strict("GFX\Menu\SCP_text.png")
	mma\SECURE_CONTAIN_PROTECT = ScaleImage2(mma\SECURE_CONTAIN_PROTECT, MenuScale, MenuScale)
	
	mma\SCP173 = LoadImage_Strict("GFX\Menu\scp_173_back.png")
	mma\SCP173 = ScaleImage2(mma\SCP173, MenuScale, MenuScale)
	
	mma\Palette = LoadImage_Strict("GFX\Menu\palette.png")
	mma\Palette = ScaleImage2(mma\Palette, MenuScale, MenuScale)
	
	mm\MainMenuBlinkTimer[0] = 1.0
	mm\MainMenuBlinkTimer[1] = 1.0
End Function

Function DeInitMainMenuAssets%()
	Delete(mma)
End Function

Type GameAssets
	Field ArrowIMG%[4]
	Field DrawHandIcon%
	Field DrawArrowIcon%[4]
End Type

Global ga.GameAssets = New GameAssets

For i = 0 To 3
	ga\ArrowIMG[i] = LoadImage_Strict("GFX\Menu\arrow.png")
	ga\ArrowIMG[i] = ScaleImage2(ga\ArrowIMG[i], MenuScale, MenuScale)
	RotateImage(ga\ArrowIMG[i], i * 90.0)
	HandleImage(ga\ArrowIMG[i], 0, 0)
Next

Global RandomSeed$

Global SelectedInputBox%, CursorPos% = -1

; ~ Main Menu Tab Constants
;[Block]
Const MainMenuTab_Default% = 0
Const MainMenuTab_New_Game% = 1
Const MainMenuTab_Load_Game% = 2
Const MainMenuTab_Load_Map% = 3
Const MainMenuTab_Options_Graphics% = 4
Const MainMenuTab_Options_Audio% = 5
Const MainMenuTab_Options_Controls% = 6
Const MainMenuTab_Options_Advanced% = 7
;[End Block]

Function ChangeOptionTab%(Page%, MainMenu% = True)
	If MainMenu Then
		mm\MainMenuTab = Page
	Else
		OptionsMenu = Page
	EndIf
	mm\CurrMenuPage = 0
	mm\ShouldDeleteGadgets = True
End Function

Function ChangePage%(Page%)
	mm\CurrMenuPage = Page
	mm\ShouldDeleteGadgets = True
End Function

If opt\DisplayMode <> 0 Then
	opt\AntiAliasing = False
	IniWriteString(OptionFile, "Graphics", "Anti-Aliasing", opt\AntiAliasing)
EndIf

Function UpdateMainMenu%()
	CatchErrors("Uncaught (UpdateMainMenu")
	
	Local sv.Save, cm.CustomMaps, snd.Sound
	Local x%, y%, Width%, Height%, Temp%, i%, n%, j%, g%
	Local File$, Test%
	
	While fps\Accumulator > 0.0
		fps\Accumulator = fps\Accumulator - TICK_DURATION
		
		UpdateMouseInput()
		
		If mm\ShouldDeleteGadgets
			DeleteMenuGadgets()
		EndIf
		mm\ShouldDeleteGadgets = False
		
		UpdateMusic()
		If opt\EnableSFXRelease Then AutoReleaseSounds()
		
		If ShouldPlay = 21 Then
			EndBreathSFX = LoadSound_Strict("SFX\Ending\MenuBreath.ogg")
			EndBreathCHN = PlaySound_Strict(EndBreathSFX)
			ShouldPlay = 66
		ElseIf ShouldPlay = 66
			If (Not ChannelPlaying(EndBreathCHN)) Then
				FreeSound_Strict(EndBreathSFX)
				ShouldPlay = 11
			EndIf
		Else
			ShouldPlay = 11
		EndIf
		
		If Rand(300) = 1 Then
			mm\MainMenuBlinkTimer[0] = Rnd(4000.0, 8000.0)
			mm\MainMenuBlinkDuration[0] = Rnd(200.0, 500.0)
		EndIf
		
		mm\MainMenuBlinkTimer[1] = mm\MainMenuBlinkTimer[1] - fps\Factor[0]
		
		If (Not mo\MouseDown1) Then mm\OnSliderID = 0
		
		If mm\PrevMainMenuTab <> mm\MainMenuTab Then DeleteMenuGadgets() : mm\CurrMenuPage = 0
		mm\PrevMainMenuTab = mm\MainMenuTab
		
		If mm\MainMenuTab = MainMenuTab_Default Then
			For i = 0 To 3
				Temp = False
				x = 159 * MenuScale
				y = (286 + 100 * i) * MenuScale
				
				Width = 400 * MenuScale
				Height = 70 * MenuScale
				
				Temp = (mo\MouseHit1 And MouseOn(x, y, Width, Height))
				
				Local Txt$
				
				Select i
					Case 0
						;[Block]
						Txt = GetLocalString("menu", "new")
						RandomSeed = ""
						If Temp Then
							If opt\DebugMode Then
								RandomSeed = "666"
							Else
								If Rand(15) = 1 Then
									Select Rand(13)
										Case 1
											;[Block]
											RandomSeed = "NIL"
											;[End Block]
										Case 2
											;[Block]
											RandomSeed = "NO"
											;[End Block]
										Case 3
											;[Block]
											RandomSeed = "d9341"
											;[End Block]
										Case 4
											;[Block]
											RandomSeed = "5CP_I73"
											;[End Block]
										Case 5
											;[Block]
											RandomSeed = "DONTBLINK"
											;[End Block]
										Case 6
											;[Block]
											RandomSeed = "CRUNCH"
											;[End Block]
										Case 7
											;[Block]
											RandomSeed = "die"
											;[End Block]
										Case 8
											;[Block]
											RandomSeed = "HTAED"
											;[End Block]
										Case 9
											;[Block]
											RandomSeed = "rustledjim"
											;[End Block]
										Case 10
											;[Block]
											RandomSeed = "larry"
											;[End Block]
										Case 11
											;[Block]
											RandomSeed = "JORGE"
											;[End Block]
										Case 12
											;[Block]
											RandomSeed = "dirtymetal"
											;[End Block]
										Case 13
											;[Block]
											RandomSeed = "whatpumpkin"
											;[End Block]
									End Select
								Else
									g = Rand(4, 8)
									For j = 1 To g
										If Rand(3) = 1 Then
											RandomSeed = RandomSeed + Rand(0, 9)
										Else
											RandomSeed = RandomSeed + Chr(Rand(97, 122))
										EndIf
									Next
								EndIf
							EndIf
							LoadSavedGames()
							CurrSave = New Save
							LoadCustomMaps()
							CurrCustomMap = New CustomMaps
							mm\MainMenuTab = MainMenuTab_New_Game
						EndIf
						;[End Block]
					Case 1
						;[Block]
						Txt = GetLocalString("menu", "load")
						If Temp Then
							LoadSavedGames()
							mm\MainMenuTab = MainMenuTab_Load_Game
						EndIf
						;[End Block]
					Case 2
						;[Block]
						Txt = GetLocalString("menu", "options")
						If Temp Then mm\MainMenuTab = MainMenuTab_Options_Graphics
						;[End Block]
					Case 3
						;[Block]
						Txt = GetLocalString("menu", "quit")
						If Temp Then
							StopStream_Strict(MusicCHN) : MusicCHN = 0
							End()
						EndIf
						;[End Block]
				End Select
				
				UpdateMainMenuButton(x, y, Width, Height, Txt)
			Next
		Else
			Select mm\MainMenuTab
				Case MainMenuTab_New_Game
					;[Block]
					x = 159 * MenuScale
					y = 376 * MenuScale
					
					Width = 580 * MenuScale
					Height = 345 * MenuScale
					
					CurrSave\Name = UpdateMainMenuInputBox(x + (150 * MenuScale), y + (15 * MenuScale), 200 * MenuScale, 30 * MenuScale, CurrSave\Name, 1, 15)
					If SelectedInputBox = 1 Then
						CurrSave\Name = Replace(CurrSave\Name, ":", "")
						CurrSave\Name = Replace(CurrSave\Name, ".", "")
						CurrSave\Name = Replace(CurrSave\Name, "/", "")
						CurrSave\Name = Replace(CurrSave\Name, "\", "")
						CurrSave\Name = Replace(CurrSave\Name, "<", "")
						CurrSave\Name = Replace(CurrSave\Name, ">", "")
						CurrSave\Name = Replace(CurrSave\Name, "|", "")
						CurrSave\Name = Replace(CurrSave\Name, "?", "")
						CurrSave\Name = Replace(CurrSave\Name, Chr(34), "")
						CurrSave\Name = Replace(CurrSave\Name, "*", "")
						CursorPos = Min(CursorPos, Len(CurrSave\Name))
					EndIf
					
					If SelectedCustomMap = Null Then
						RandomSeed = UpdateMainMenuInputBox(x + (150 * MenuScale), y + (55 * MenuScale), 200 * MenuScale, 30 * MenuScale, RandomSeed, 2, 15)
					Else
						If UpdateMainMenuButton(x + (370 * MenuScale), y + (55 * MenuScale), 120 * MenuScale, 30 * MenuScale, GetLocalString("menu", "deselect"), False) Then
							mm\ShouldDeleteGadgets = True
							SelectedCustomMap = Null
						EndIf
					EndIf
					
					opt\IntroEnabled = UpdateMainMenuTick(x + (280 * MenuScale), y + (110 * MenuScale), opt\IntroEnabled)
					
					For i = SAFE To ESOTERIC
						Local PrevSelectedDifficulty.Difficulty = SelectedDifficulty
						
						If UpdateMainMenuTick(x + (20 * MenuScale), y + ((180 + 30 * i) * MenuScale), (SelectedDifficulty = difficulties[i])) Then SelectedDifficulty = difficulties[i]
						
						If PrevSelectedDifficulty <> SelectedDifficulty Then
							If PrevSelectedDifficulty = difficulties[ESOTERIC] Then mm\ShouldDeleteGadgets = True
						EndIf
					Next
					
					If SelectedDifficulty\Customizable Then
						; ~ Save type
						If UpdateMainMenuButton(x + (160 * MenuScale), y + (180 * MenuScale), 20 * MenuScale, 20 * MenuScale, ">", False) Then
							If SelectedDifficulty\SaveType < NO_SAVES Then
								SelectedDifficulty\SaveType = SelectedDifficulty\SaveType + 1
							Else
								SelectedDifficulty\SaveType = SAVE_ANYWHERE
							EndIf
						EndIf
						
						; ~ Agressive NPCs
						SelectedDifficulty\AggressiveNPCs = UpdateMainMenuTick(x + (160 * MenuScale), y + (210 * MenuScale), SelectedDifficulty\AggressiveNPCs)
						
						; ~ Inventory slots
						If UpdateMainMenuButton(x + (405 * MenuScale), y + (240 * MenuScale), 20 * MenuScale, 20 * MenuScale, "<", False) Then
							SelectedDifficulty\InventorySlots = SelectedDifficulty\InventorySlots - 2
							If SelectedDifficulty\InventorySlots <= 0 Then SelectedDifficulty\InventorySlots = 10
						ElseIf UpdateMainMenuButton(x + (160 * MenuScale), y + (240 * MenuScale), 20 * MenuScale, 20 * MenuScale, ">", False)
							SelectedDifficulty\InventorySlots = SelectedDifficulty\InventorySlots + 2
							If SelectedDifficulty\InventorySlots > 10 Then SelectedDifficulty\InventorySlots = 2
						EndIf
						
						; ~ Other factor's difficulty
						If UpdateMainMenuButton(x + (160 * MenuScale), y + (270 * MenuScale), 20 * MenuScale, 20 * MenuScale, ">", False) Then
							If SelectedDifficulty\OtherFactors < EXTREME Then
								SelectedDifficulty\OtherFactors = SelectedDifficulty\OtherFactors + 1
							Else
								SelectedDifficulty\OtherFactors = EASY
							EndIf
						EndIf
					EndIf
					
					If UpdateMainMenuButton(x, y + Height + (20 * MenuScale), 160 * MenuScale, 75 * MenuScale, GetLocalString("menu", "loadmap"), False) Then
						LoadCustomMaps()
						mm\MainMenuTab = MainMenuTab_Load_Map
					EndIf
					
					If UpdateMainMenuButton(x + (420 * MenuScale), y + Height + (20 * MenuScale), 160 * MenuScale, 75 * MenuScale, GetLocalString("menu", "start"), False) Then
						If CurrSave\Name = "" Then CurrSave\Name = ConvertToANSI(GetLocalString("save", "untitled"))
						
						If RandomSeed = "" Then RandomSeed = MilliSecs2()
						
						SeedRnd(GenerateSeedNumber(RandomSeed))
						
						Local SameFound% = 0
						Local LowestPossible% = 2
						
						For sv.Save = Each Save
							If (CurrSave <> sv And CurrSave\Name = sv\Name) Then
								SameFound = 1
								Exit
							EndIf
						Next
						
						While SameFound = 1
							SameFound = 2
							For sv.Save = Each Save
								If (sv\Name = (CurrSave\Name + " (" + LowestPossible + ")")) Then
									LowestPossible = LowestPossible + 1
									SameFound = 1
									Exit
								EndIf
							Next
						Wend
						
						If SameFound = 2 Then CurrSave\Name = CurrSave\Name + " (" + LowestPossible + ")"
						
						InitNewGame()
						ResetInput()
						
						IniWriteString(OptionFile, "Global", "Enable Intro", opt\IntroEnabled)
						
						MainMenuOpen = False
						Return
					EndIf
					;[End Block]
				Case MainMenuTab_Load_Game
					;[Block]
					x = 159 * MenuScale
					y = 376 * MenuScale
					
					Width = 580 * MenuScale
					Height = 296 * MenuScale
					
					If mm\CurrMenuPage < Ceil(Float(SavedGamesAmount) / 5.0) - 1 And DelSave = Null Then
						If UpdateMainMenuButton(x + Width - (50 * MenuScale), y + (440 * MenuScale), 50 * MenuScale, 50 * MenuScale, ">") Then ChangePage(mm\CurrMenuPage + 1)
					Else
						UpdateMainMenuButton(x + Width - (50 * MenuScale), y + (440 * MenuScale), 50 * MenuScale, 50 * MenuScale, ">", True, False, True)
					EndIf
					If mm\CurrMenuPage > 0 And DelSave = Null Then
						If UpdateMainMenuButton(x, y + (440 * MenuScale), 50 * MenuScale, 50 * MenuScale, "<") Then ChangePage(mm\CurrMenuPage - 1)
					Else
						UpdateMainMenuButton(x, y + (440 * MenuScale), 50 * MenuScale, 50 * MenuScale, "<", True, False, True)
					EndIf
					If mm\CurrMenuPage > Ceil(Float(SavedGamesAmount) / 5.0) - 1 Then ChangePage(mm\CurrMenuPage - 1)
					
					If SavedGamesAmount > 0 Then
						x = x + (20 * MenuScale)
						y = y + (20 * MenuScale)
						
						CurrSave = First Save
						
						For i = 0 To 4 + (5 * mm\CurrMenuPage)
							If i > 0 Then CurrSave = After CurrSave
							If CurrSave = Null Then Exit
							If i >= (5 * mm\CurrMenuPage) Then
								If DelSave = Null Then
									If CurrSave\Version <> VersionNumber Then
										UpdateMainMenuButton(x + (280 * MenuScale), y + (20 * MenuScale), 100 * MenuScale, 30 * MenuScale, GetLocalString("menu", "btnload"), False, False, True, 255, 0, 0)
									Else
										If UpdateMainMenuButton(x + (280 * MenuScale), y + (20 * MenuScale), 100 * MenuScale, 30 * MenuScale, GetLocalString("menu", "btnload"), False) Then
											LoadEntities()
											LoadSounds()
											LoadGame(CurrSave\Name)
											InitLoadGame()
											mm\ShouldDeleteGadgets = True
											MainMenuOpen = False
											Return
										EndIf
									EndIf
									
									If UpdateMainMenuButton(x + (400 * MenuScale), y + (20 * MenuScale), 100 * MenuScale, 30 * MenuScale, GetLocalString("menu", "delete"), False) Then
										DelSave = CurrSave
										Exit
									EndIf
								Else
									If CurrSave\Version <> VersionNumber Then
										UpdateMainMenuButton(x + (280 * MenuScale), y + (20 * MenuScale), 100 * MenuScale, 30 * MenuScale, GetLocalString("menu", "btnload"), False, False, True, 255, 0, 0)
									Else
										UpdateMainMenuButton(x + (280 * MenuScale), y + (20 * MenuScale), 100 * MenuScale, 30 * MenuScale, GetLocalString("menu", "btnload"), False, False, True)
									EndIf
									UpdateMainMenuButton(x + (400 * MenuScale), y + (20 * MenuScale), 100 * MenuScale, 30 * MenuScale, GetLocalString("menu", "delete"), False, False, True)
								EndIf
								If CurrSave = Last Save Then Exit
								y = y + (80 * MenuScale)
							EndIf
						Next
						
						If DelSave <> Null Then
							x = 739 * MenuScale
							y = 376 * MenuScale
							
							If UpdateMainMenuButton(x + (74 * MenuScale), y + (150 * MenuScale), 100 * MenuScale, 30 * MenuScale, GetLocalString("menu", "yes"), False) Then
								DeleteGame(DelSave)
								mm\ShouldDeleteGadgets = True
							EndIf
							If UpdateMainMenuButton(x + (246 * MenuScale), y + (150 * MenuScale), 100 * MenuScale, 30 * MenuScale, GetLocalString("menu", "no"), False) Then
								DelSave = Null
								mm\ShouldDeleteGadgets = True
							EndIf
						EndIf
					EndIf
					;[End Block]
				Case MainMenuTab_Load_Map
					;[Block]
					x = 159 * MenuScale
					y = 376 * MenuScale
					
					Width = 580 * MenuScale
					Height = 350 * MenuScale
					
					If mm\CurrMenuPage < Ceil(Float(CustomMapsAmount) / 5.0) - 1 And DelCustomMap = Null Then
						If UpdateMainMenuButton(x + Width - (50 * MenuScale), y + (440 * MenuScale), 50 * MenuScale, 50 * MenuScale, ">") Then ChangePage(mm\CurrMenuPage + 1)
					Else
						UpdateMainMenuButton(x + Width - (50 * MenuScale), y + (440 * MenuScale), 50 * MenuScale, 50 * MenuScale, ">", True, False, True)
					EndIf
					If mm\CurrMenuPage > 0 And DelCustomMap = Null Then
						If UpdateMainMenuButton(x, y + (440 * MenuScale), 50 * MenuScale, 50 * MenuScale, "<") Then ChangePage(mm\CurrMenuPage - 1)
					Else
						UpdateMainMenuButton(x, y + (440 * MenuScale), 50 * MenuScale, 50 * MenuScale, "<", True, False, True)
					EndIf
					If mm\CurrMenuPage > Ceil(Float(CustomMapsAmount) / 5.0) - 1 Then ChangePage(mm\CurrMenuPage - 1)
					
					If CustomMapsAmount > 0 Then
						x = x + (20 * MenuScale)
						y = y + (20 * MenuScale)
						
						CurrCustomMap = First CustomMaps
						
						For i = 0 To 4 + (5 * mm\CurrMenuPage)
							If i > 0 Then CurrCustomMap = After CurrCustomMap
							If CurrCustomMap = Null Then Exit
							If i >= (5 * mm\CurrMenuPage) Then
								If DelCustomMap = Null Then
									If UpdateMainMenuButton(x + (280 * MenuScale), y + (20 * MenuScale), 100 * MenuScale, 30 * MenuScale, GetLocalString("menu", "btnload"), False) Then
										SelectedCustomMap = CurrCustomMap
										mm\MainMenuTab = MainMenuTab_New_Game
										mm\ShouldDeleteGadgets = True
									EndIf
									
									If UpdateMainMenuButton(x + (400 * MenuScale), y + (20 * MenuScale), 100 * MenuScale, 30 * MenuScale, GetLocalString("menu", "delete"), False) Then
										DelCustomMap = CurrCustomMap
										Exit
									EndIf
								Else
									UpdateMainMenuButton(x + (280 * MenuScale), y + (20 * MenuScale), 100 * MenuScale, 30 * MenuScale, GetLocalString("menu", "btnload"), False, False, True)
									UpdateMainMenuButton(x + (400 * MenuScale), y + (20 * MenuScale), 100 * MenuScale, 30 * MenuScale, GetLocalString("menu", "delete"), False, False, True)
								EndIf
								If CurrCustomMap = Last CustomMaps Then Exit
								y = y + (80 * MenuScale)
							EndIf
						Next
						
						If DelCustomMap <> Null Then
							x = 739 * MenuScale
							y = 376 * MenuScale
							
							If UpdateMainMenuButton(x + (74 * MenuScale), y + (150 * MenuScale), 100 * MenuScale, 30 * MenuScale, GetLocalString("menu", "yes"), False) Then
								DeleteCustomMap(DelCustomMap)
								mm\ShouldDeleteGadgets = True
							EndIf
							If UpdateMainMenuButton(x + (246 * MenuScale), y + (150 * MenuScale), 100 * MenuScale, 30 * MenuScale, GetLocalString("menu", "no"), False) Then
								DelCustomMap = Null
								mm\ShouldDeleteGadgets = True
							EndIf
						EndIf
					EndIf
					;[End Block]
				Case MainMenuTab_Options_Graphics, MainMenuTab_Options_Audio, MainMenuTab_Options_Controls, MainMenuTab_Options_Advanced
					;[Block]
					x = 159 * MenuScale
					y = 376 * MenuScale
					
					Width = 580 * MenuScale
					Height = 60 * MenuScale
					
					If UpdateMainMenuButton(x + (20 * MenuScale), y + (15 * MenuScale), Width / 5, Height / 2, GetLocalString("options", "grap"), False) Then ChangeOptionTab(MainMenuTab_Options_Graphics)
					If UpdateMainMenuButton(x + (160 * MenuScale), y + (15 * MenuScale), Width / 5, Height / 2, GetLocalString("options", "audio"), False) Then ChangeOptionTab(MainMenuTab_Options_Audio)
					If UpdateMainMenuButton(x + (300 * MenuScale), y + (15 * MenuScale), Width / 5, Height / 2, GetLocalString("options", "ctrl"), False) Then ChangeOptionTab(MainMenuTab_Options_Controls)
					If UpdateMainMenuButton(x + (440 * MenuScale), y + (15 * MenuScale), Width / 5, Height / 2, GetLocalString("options", "avc"), False) Then ChangeOptionTab(MainMenuTab_Options_Advanced)
					
					x = x + (310 * MenuScale)
					y = y + (70 * MenuScale)
					
					If mm\MainMenuTab <> MainMenuTab_Options_Audio Then
						UserTrackCheck = 0
						UserTrackCheck2 = 0
					EndIf
					
					Select mm\MainMenuTab
						Case MainMenuTab_Options_Graphics
							;[Block]
							y = y + (20 * MenuScale)
							
							opt\BumpEnabled = UpdateMainMenuTick(x, y, opt\BumpEnabled)
							
							y = y + (30 * MenuScale)
							
							opt\VSync = UpdateMainMenuTick(x, y, opt\VSync)
							
							y = y + (30 * MenuScale)
							
							opt\AntiAliasing = UpdateMainMenuTick(x, y, opt\AntiAliasing, opt\DisplayMode <> 0)
							
							y = y + (30 * MenuScale)
							
							opt\AdvancedRoomLights = UpdateMainMenuTick(x, y, opt\AdvancedRoomLights)
							
							y = y + (40 * MenuScale)
							
							opt\ScreenGamma = UpdateMainMenuSlideBar(x, y, 150 * MenuScale, opt\ScreenGamma * 50.0, 1) / 50.0
							
							y = y + (45 * MenuScale)
							
							opt\ParticleAmount = UpdateMainMenuSlider3(x, y, 150 * MenuScale, opt\ParticleAmount, 2, GetLocalString("options", "min"), GetLocalString("options", "red"), GetLocalString("options", "full"))
							
							y = y + (45 * MenuScale)
							
							opt\TextureDetails = UpdateMainMenuSlider5(x, y, 150 * MenuScale, opt\TextureDetails, 3, "0.8", "0.4", "0.0", "-0.4", "-0.8")
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
							
							opt\SaveTexturesInVRAM = UpdateMainMenuTick(x, y, opt\SaveTexturesInVRAM)
							
							y = y + (40 * MenuScale)
							
							opt\CurrFOV = (UpdateMainMenuSlideBar(x, y, 150 * MenuScale, opt\CurrFOV * 2.0, 4) / 2.0)
							opt\FOV = opt\CurrFOV + 40
							
							y = y + (45 * MenuScale)
							
							opt\Anisotropic = UpdateMainMenuSlider5(x, y, 150 * MenuScale, opt\Anisotropic, 5, GetLocalString("options", "tri"), "2x", "4x", "8x", "16x")
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
							
							opt\Atmosphere = UpdateMainMenuTick(x, y, opt\Atmosphere)
							;[End Block]
						Case MainMenuTab_Options_Audio
							;[Block]
							y = y + (20 * MenuScale)
							
							opt\MasterVolume = UpdateMainMenuSlideBar(x, y, 150 * MenuScale, opt\MasterVolume * 100.0, 1) / 100.0
							
							y = y + (40 * MenuScale)
							
							opt\MusicVolume = UpdateMainMenuSlideBar(x, y, 150 * MenuScale, opt\MusicVolume * 100.0, 2) / 100.0
							
							y = y + (40 * MenuScale)
							
							opt\SFXVolume = UpdateMainMenuSlideBar(x, y, 150 * MenuScale, opt\SFXVolume * 100.0, 3) / 100.0
							
							y = y + (40 * MenuScale)
							
							opt\EnableSFXRelease = UpdateMainMenuTick(x, y, opt\EnableSFXRelease)
							If opt\PrevEnableSFXRelease <> opt\EnableSFXRelease
								If opt\EnableSFXRelease Then
									For snd.Sound = Each Sound
										For i = 0 To MaxChannelsAmount - 1
											StopChannel(snd\Channels[i]) : snd\Channels[i] = 0
										Next
										If snd\InternalHandle <> 0 Then FreeSound(snd\InternalHandle) : snd\InternalHandle = 0
										snd\ReleaseTime = 0
									Next
								Else
									For snd.Sound = Each Sound
										If (Not snd\InternalHandle) Then snd\InternalHandle = LoadSound(snd\Name)
									Next
								EndIf
								opt\PrevEnableSFXRelease = opt\EnableSFXRelease
							EndIf
							
							y = y + (30 * MenuScale)
							
							Local PrevEnableUserTracks% = opt\EnableUserTracks
							
							opt\EnableUserTracks = UpdateMainMenuTick(x, y, opt\EnableUserTracks)
							
							If PrevEnableUserTracks Then
								If PrevEnableUserTracks <> opt\EnableUserTracks Then mm\ShouldDeleteGadgets = True
							EndIf
							
							If opt\EnableUserTracks Then
								y = y + (30 * MenuScale)
								
								opt\UserTrackMode = UpdateMainMenuTick(x, y, opt\UserTrackMode)
								
								If UpdateMainMenuButton(x - (290 * MenuScale), y + (30 * MenuScale), 220 * MenuScale, 30 * MenuScale, GetLocalString("options", "scantracks"), False)
									UserTrackCheck = 0
									UserTrackCheck2 = 0
									
									Local DirPath$ = "SFX\Radio\UserTracks\"
									
									If FileType(DirPath) <> 2 Then CreateDir(DirPath)
									
									Local Dir% = ReadDir(DirPath)
									
									Repeat
										File = NextFile(Dir)
										If File = "" Then Exit
										If FileType(DirPath + File) = 1 Then
											UserTrackCheck = UserTrackCheck + 1
											Test = LoadSound(DirPath + File)
											If Test <> 0 Then UserTrackCheck2 = UserTrackCheck2 + 1
											FreeSound(Test) : Test = 0
										EndIf
									Forever
									CloseDir(Dir)
								EndIf
							Else
								UserTrackCheck = 0
							EndIf
							;[End Block]
						Case MainMenuTab_Options_Controls
							;[Block]
							If mm\CurrMenuPage = 0 Then
								y = y + (20 * MenuScale)
								
								opt\MouseSensitivity = (UpdateMainMenuSlideBar(x, y, 150 * MenuScale, (opt\MouseSensitivity + 0.5) * 100.0, 1) / 100.0) - 0.5
								
								y = y + (40 * MenuScale)
								
								opt\InvertMouseX = UpdateMainMenuTick(x, y, opt\InvertMouseX)
								
								y = y + (40 * MenuScale)
								
								opt\InvertMouseY = UpdateMainMenuTick(x, y, opt\InvertMouseY)
								
								y = y + (40 * MenuScale)
								
								opt\MouseSmoothing = UpdateMainMenuSlideBar(x, y, 150 * MenuScale, (opt\MouseSmoothing) * 50.0, 2) / 50.0
								
								y = y + (40 * MenuScale)
								
								If UpdateMainMenuButton(x - (290 * MenuScale), y, 240 * MenuScale, 30 * MenuScale, GetLocalString("options", "controlconfig"), False) Then ChangePage(1)
							Else
								y = y + (20 * MenuScale)
								
								UpdateMainMenuInputBox(x - (150 * MenuScale), y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\MOVEMENT_UP, 210.0)], 3)
								UpdateMainMenuInputBox(x + (140 * MenuScale), y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\CROUCH, 210.0)], 8)
								
								y = y + (20 * MenuScale)
								
								UpdateMainMenuInputBox(x - (150 * MenuScale), y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\MOVEMENT_LEFT, 210.0)], 4)
								UpdateMainMenuInputBox(x + (140 * MenuScale), y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\BLINK, 210.0)], 9)
								
								y = y + (20 * MenuScale)
								
								UpdateMainMenuInputBox(x - (150 * MenuScale), y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\MOVEMENT_DOWN, 210.0)], 5)
								UpdateMainMenuInputBox(x + (140 * MenuScale), y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\INVENTORY, 210.0)], 10)
								
								y = y + (20 * MenuScale)
								
								UpdateMainMenuInputBox(x - (150 * MenuScale), y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\MOVEMENT_RIGHT, 210.0)], 6)
								UpdateMainMenuInputBox(x + (140 * MenuScale), y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\SAVE, 210.0)], 11)
								
								y = y + (20 * MenuScale)
								
								UpdateMainMenuInputBox(x - (150 * MenuScale), y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\SPRINT, 210.0)], 7)
								UpdateMainMenuInputBox(x + (140 * MenuScale), y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\SCREENSHOT, 210.0)], 13)
								
								If opt\CanOpenConsole Then
									y = y + (20 * MenuScale)
									
									UpdateMainMenuInputBox(x - (150 * MenuScale), y, 110 * MenuScale, 20 * MenuScale, key\Name[Min(key\CONSOLE, 210.0)], 12)
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
								
								If UpdateMainMenuButton(x - (290 * MenuScale), y, 240 * MenuScale, 30 * MenuScale, GetLocalString("menu", "back"), False) Then ChangePage(0)
							EndIf
							;[End Block]
						Case MainMenuTab_Options_Advanced
							;[Block]
							Height = 340 * MenuScale
							
							If mm\CurrMenuPage = 0 Then
								If UpdateMainMenuButton(x - (310 * MenuScale) + Width - (30 * MenuScale), y + Height + (5 * MenuScale), 30 * MenuScale, 30 * MenuScale, ">", False) Then ChangePage(mm\CurrMenuPage + 1)
							Else
								UpdateMainMenuButton(x - (310 * MenuScale) + Width - (30 * MenuScale), y + Height + (5 * MenuScale), 30 * MenuScale, 30 * MenuScale, ">", False, False, True)
							EndIf
							If mm\CurrMenuPage = 1 Then
								If UpdateMainMenuButton(x - (310 * MenuScale), y + Height + (5 * MenuScale), 30 * MenuScale, 30 * MenuScale, "<", False) Then ChangePage(mm\CurrMenuPage - 1)
							Else
								UpdateMainMenuButton(x - (310 * MenuScale), y + Height + (5 * MenuScale), 30 * MenuScale, 30 * MenuScale, "<", False, False, True)
							EndIf
							
							If mm\CurrMenuPage = 0 Then
								y = y + (20 * MenuScale)
								
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
									opt\CurrFrameLimit = UpdateMainMenuSlideBar(x - (160 * MenuScale), y + (40 * MenuScale), 150 * MenuScale, opt\CurrFrameLimit * 99.0, 1) / 99.0
									opt\CurrFrameLimit = Max(opt\CurrFrameLimit, 0.01)
									opt\FrameLimit = 19 + (opt\CurrFrameLimit * 100.0)
								Else
									opt\CurrFrameLimit = 0.0
									opt\FrameLimit = 0
								EndIf
								
								If PrevCurrFrameLimit Then
									If PrevCurrFrameLimit <> opt\CurrFrameLimit Then mm\ShouldDeleteGadgets = True
								EndIf
							Else
								y = y + (20 * MenuScale)
								
								opt\SmoothBars = UpdateMainMenuTick(x, y, opt\SmoothBars)
								
								y = y + (30 * MenuScale)
								
								opt\PlayStartup = UpdateMainMenuTick(x, y, opt\PlayStartup)
								
								y = y + (30 * MenuScale)
								
								opt\LauncherEnabled = UpdateMainMenuTick(x, y, opt\LauncherEnabled)
								
								y = y + (30 * MenuScale)
								
								Local PrevEnableSubtitles% = opt\EnableSubtitles
								
								opt\EnableSubtitles = UpdateMainMenuTick(x, y, opt\EnableSubtitles)
								
								If PrevEnableSubtitles Then
									If PrevEnableSubtitles <> opt\EnableSubtitles Then mm\ShouldDeleteGadgets = True
								EndIf
								
								y = y + (35 * MenuScale)
								
								If opt\EnableSubtitles Then UpdateMainMenuPalette(mma\Palette, x - (63 * MenuScale), y)
								
								y = y + (30 * MenuScale)
								
								If opt\EnableSubtitles Then
									opt\SubColorR = UpdateMainMenuInputBox(x - (135 * MenuScale), y, 40 * MenuScale, 20 * MenuScale, Str(opt\SubColorR), 14, 3)
									If SelectedInputBox = 14 Then
										If opt\SubColorR > 255 Then opt\SubColorR = 255
									EndIf
								EndIf
								
								y = y + (30 * MenuScale)
								
								If opt\EnableSubtitles Then
									opt\SubColorG = UpdateMainMenuInputBox(x - (135 * MenuScale), y, 40 * MenuScale, 20 * MenuScale, Str(opt\SubColorG), 15, 3)
									If SelectedInputBox = 15 Then
										If opt\SubColorG > 255 Then opt\SubColorG = 255
									EndIf
								EndIf
								
								y = y + (30 * MenuScale)
								
								If opt\EnableSubtitles Then
									opt\SubColorB = UpdateMainMenuInputBox(x - (135 * MenuScale), y, 40 * MenuScale, 20 * MenuScale, Str(opt\SubColorB), 16, 3)
									If SelectedInputBox = 16 Then
										If opt\SubColorB > 255 Then opt\SubColorB = 255
									EndIf
								EndIf
								
								y = y + (40 * MenuScale)
								
								If UpdateMainMenuButton(x - (290 * MenuScale), y, 195 * MenuScale, 30 * MenuScale, GetLocalString("options", "reset"), False) Then
									ResetOptionsINI()
									SaveOptionsINI(True)
								EndIf
							EndIf
							;[End Block]
					End Select
					;[End Block]
			End Select
			
			x = 159 * MenuScale
			y = 286 * MenuScale
			
			Width = 400 * MenuScale
			Height = 70 * MenuScale
			
			If DelSave = Null And DelCustomMap = Null Then
				If UpdateMainMenuButton(x + Width + (20 * MenuScale), y, (580 * MenuScale) - Width - (20 * MenuScale), Height, GetLocalString("menu", "back"), False) Lor KeyDown(1) Then
					Select mm\MainMenuTab
						Case MainMenuTab_New_Game
							;[Block]
							IniWriteString(OptionFile, "Global", "Enable Intro", opt\IntroEnabled)
							For sv.Save = Each Save
								Delete(sv)
							Next
							For cm.CustomMaps = Each CustomMaps
								Delete(cm)
							Next
							mm\MainMenuTab = MainMenuTab_Default
							;[End Block]
						Case MainMenuTab_Load_Game
							;[Block]
							mm\CurrMenuPage = 0
							For sv.Save = Each Save
								Delete(sv)
							Next
							mm\MainMenuTab = MainMenuTab_Default
							;[End Block]
						Case MainMenuTab_Options_Graphics, MainMenuTab_Options_Audio, MainMenuTab_Options_Controls, MainMenuTab_Options_Advanced ; ~ Save the options
							;[Block]
							SaveOptionsINI()
							
							UserTrackCheck = 0
							UserTrackCheck2 = 0
							
							mm\CurrMenuPage = 0
							AntiAlias(opt\AntiAliasing)
							mm\MainMenuTab = MainMenuTab_Default
							;[End Block]
						Case MainMenuTab_Load_Map ; ~ Move back to the "New Game" tab
							;[Block]
							mm\CurrMenuPage = 0
							For cm.CustomMaps = Each CustomMaps
								Delete(cm)
							Next
							ResetInput()
							mm\MainMenuTab = MainMenuTab_New_Game
							;[End Block]
						Default
							;[Block]
							mm\MainMenuTab = MainMenuTab_Default
							;[End Block]
					End Select
				EndIf
			Else
				UpdateMainMenuButton(x + Width + (20 * MenuScale), y, (580 * MenuScale) - Width - (20 * MenuScale), Height, GetLocalString("menu", "back"), False, False, True)
			EndIf
		EndIf
	Wend
	
	; ~ Go out of function immediately if the game has been start
	If (Not MainMenuOpen) Then Return
	
	RenderMainMenu()
	
	CatchErrors("UpdateMainMenu")
End Function

Function RenderMainMenu%()
	CatchErrors("Uncaught (RenderMainMenu")
	
	Local x%, y%, Width%, Height%, Temp%, i%, n%
	Local tX#, tY#, tW#, tH#
	Local TempStr$, TempStr2$
	
	If (Not mm\OnPalette) Then
		ShowPointer()
	Else
		HidePointer()
	EndIf
	DrawBlock(mma\BackGround, 0, 0)
	If (MilliSecs2() Mod mm\MainMenuBlinkTimer[0]) >= Rand(mm\MainMenuBlinkDuration[0]) Then DrawBlock(mma\SCP173, opt\GraphicWidth - ImageWidth(mma\SCP173), opt\GraphicHeight - ImageHeight(mma\SCP173))
	SetFont2(fo\FontID[Font_Default])
	If mm\MainMenuBlinkTimer[1] < mm\MainMenuBlinkDuration[1] Then
		Color(50, 50, 50)
		Text2(mm\MainMenuStrX + Rand(-5, 5), mm\MainMenuStrY + Rand(-5, 5), mm\MainMenuStr, True)
		If mm\MainMenuBlinkTimer[1] < 0.0 Then
			mm\MainMenuBlinkTimer[1] = Rnd(700.0, 800.0)
			mm\MainMenuBlinkDuration[1] = Rnd(10.0, 35.0)
			mm\MainMenuStrX = Rand(700, 1000) * MenuScale
			mm\MainMenuStrY = Rand(100, 600) * MenuScale
			
			Select Rand(0, 23)
				Case 0, 2, 3
					;[Block]
					mm\MainMenuStr = GetLocalString("menu", "dontblink")
					;[End Block]
				Case 4, 5
					;[Block]
					mm\MainMenuStr = GetLocalString("menu", "scp")
					;[End Block]
				Case 6, 7, 8
					;[Block]
					mm\MainMenuStr = GetLocalString("menu", "happyending")
					;[End Block]
				Case 9, 10, 11
					;[Block]
					mm\MainMenuStr = GetLocalString("menu", "scream")
					;[End Block]
				Case 12, 19
					;[Block]
					mm\MainMenuStr = GetLocalString("menu", "nil")
					;[End Block]
				Case 13
					;[Block]
					mm\MainMenuStr = GetLocalString("menu", "menuno")
					;[End Block]
				Case 14
					;[Block]
					mm\MainMenuStr = GetLocalString("menu", "bwg")
					;[End Block]
				Case 15
					;[Block]
					mm\MainMenuStr = GetLocalString("menu", "173care")
					;[End Block]
				Case 16
					;[Block]
					mm\MainMenuStr = GetLocalString("menu", "9341")
					;[End Block]
				Case 17
					;[Block]
					mm\MainMenuStr = GetLocalString("menu", "079doors")
					;[End Block]
				Case 18
					;[Block]
					mm\MainMenuStr = GetLocalString("menu", "???")
					;[End Block]
				Case 20
					;[Block]
					mm\MainMenuStr = GetLocalString("menu", "079king")
					;[End Block]
				Case 21
					;[Block]
					mm\MainMenuStr = GetLocalString("menu", "spiral")
					;[End Block]
				Case 22
					;[Block]
					mm\MainMenuStr = GetLocalString("menu", "damage")
					;[End Block]
				Case 23
					;[Block]
					mm\MainMenuStr = GetLocalString("menu", "howl")
					;[End Block]
			End Select
		EndIf
	EndIf
	SetFont2(fo\FontID[Font_Default_Big])
	DrawBlock(mma\SECURE_CONTAIN_PROTECT, mo\Viewport_Center_X - ImageWidth(mma\SECURE_CONTAIN_PROTECT) / 2, opt\GraphicHeight - (20 * MenuScale) - ImageHeight(mma\SECURE_CONTAIN_PROTECT))
	If opt\GraphicWidth > 1240 * MenuScale Then RenderTiledImageRect(MenuWhite, 0, 5 * MenuScale, 512.0 * MenuScale, 5.0 * MenuScale, 985 * MenuScale, 407 * MenuScale, (opt\GraphicWidth - (940 * MenuScale)), 5 * MenuScale)
	If mm\MainMenuTab <> MainMenuTab_Default Then
		x = 159 * MenuScale
		y = 286 * MenuScale
		
		Width = 400 * MenuScale
		Height = 70 * MenuScale
		
		RenderFrame(x, y, Width, Height)
		
		Select mm\MainMenuTab
			Case MainMenuTab_New_Game
				;[Block]
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				Width = 400 * MenuScale
				Height = 70 * MenuScale
				
				Color(255, 255, 255)
				SetFont2(fo\FontID[Font_Default_Big])
				Text2(x + (Width / 2), y + (Height / 2), GetLocalString("menu", "new"), True, True)
				
				y = y + Height + (20 * MenuScale)
				Width = 580 * MenuScale
				Height = 345 * MenuScale
				
				RenderFrame(x, y, Width, Height)
				
				SetFont2(fo\FontID[Font_Default])
				
				Text2(x + (20 * MenuScale), y + (25 * MenuScale), GetLocalString("menu", "new.name"))
				
				If SelectedCustomMap = Null Then
					TempStr = GetLocalString("menu", "new.seed")
				Else
					TempStr = GetLocalString("menu", "new.map")
					RenderFrame(x + (150 * MenuScale), y + (55 * MenuScale), 200 * MenuScale, 30 * MenuScale, (x Mod 256), (y Mod 256), True)
					
					Color(255, 0, 0)
					If Len(ConvertToUTF8(SelectedCustomMap\Name)) > 15 Then
						TempStr2 = Left(ConvertToUTF8(SelectedCustomMap\Name), 14) + "..."
					Else
						TempStr2 = ConvertToUTF8(SelectedCustomMap\Name)
					EndIf
					Text2(x + (250 * MenuScale), y + (70 * MenuScale), TempStr2, True, True)
				EndIf
				Color(255, 255, 255)
				Text2(x + (20 * MenuScale), y + (65 * MenuScale), TempStr)
				
				Text2(x + (20 * MenuScale), y + (115 * MenuScale), GetLocalString("menu", "new.intro"))
				
				Text2(x + (20 * MenuScale), y + (155 * MenuScale), GetLocalString("menu", "new.diff"))
				For i = SAFE To ESOTERIC
					Color(difficulties[i]\R, difficulties[i]\G, difficulties[i]\B)
					Text2(x + (50 * MenuScale), y + ((185 + 30 * i) * MenuScale), difficulties[i]\Name)
				Next
				
				Color(255, 255, 255)
				RenderFrame(x + (150 * MenuScale), y + (170 * MenuScale), 410 * MenuScale, 160 * MenuScale)
				
				If SelectedDifficulty\Customizable Then
					; ~ Save type
					Select SelectedDifficulty\SaveType
						Case SAVE_ANYWHERE
							;[Block]
							TempStr = GetLocalString("menu", "new.saveany")
							;[End Block]
						Case SAVE_ON_SCREENS
							;[Block]
							TempStr = GetLocalString("menu", "new.savescreen")
							;[End Block]
						Case SAVE_ON_QUIT
							;[Block]
							TempStr = GetLocalString("menu", "new.savequit")
							;[End Block]
						Case NO_SAVES
							;[Block]
							TempStr = GetLocalString("menu", "new.saveno")
							;[End Block]
					End Select
					Text2(x + (200 * MenuScale), y + (186 * MenuScale), GetLocalString("menu", "new.savetype") + TempStr)
					
					; ~ Agressive NPCs
					Text2(x + (200 * MenuScale), y + (215 * MenuScale), GetLocalString("menu", "new.dangernpc"))
					; ~ Inventory slots
					Text2(x + (200 * MenuScale), y + (246 * MenuScale), Format(GetLocalString("menu", "new.invslots"), SelectedDifficulty\InventorySlots))
					
					; ~ Other factor's difficulty
					Select SelectedDifficulty\OtherFactors
						Case EASY
							;[Block]
							TempStr = GetLocalString("menu", "new.easy")
							;[End Block]
						Case NORMAL
							;[Block]
							TempStr = GetLocalString("menu", "new.normal")
							;[End Block]
						Case HARD
							;[Block]
							TempStr = GetLocalString("menu", "new.hard")
							;[End Block]
						Case EXTREME
							;[Block]
							TempStr = GetLocalString("menu", "new.extreme")
							;[End Block]
					End Select
					Text2(x + (200 * MenuScale), y + (276 * MenuScale), Format(GetLocalString("menu", "new.factors"), TempStr))
				Else
					RowText(SelectedDifficulty\Description, x + (160 * MenuScale), y + (180 * MenuScale), 390 * MenuScale, 140 * MenuScale)
				EndIf
				
				SetFont2(fo\FontID[Font_Default_Big])
				;[End Block]
			Case MainMenuTab_Load_Game
				;[Block]
				y = y + Height + (20 * MenuScale)
				Width = 580 * MenuScale
				Height = 430 * MenuScale
				
				RenderFrame(x, y, Width, Height)
				
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				Width = 400 * MenuScale
				Height = 70 * MenuScale
				
				Color(255, 255, 255)
				SetFont2(fo\FontID[Font_Default_Big])
				Text2(x + (Width / 2), y + (Height / 2), GetLocalString("menu", "load"), True, True)
				
				y = y + Height + (20 * MenuScale)
				Width = 580 * MenuScale
				Height = 296 * MenuScale
				
				SetFont2(fo\FontID[Font_Default_Big])
				
				RenderFrame(x + (60 * MenuScale), y + (440 * MenuScale), Width - (120 * MenuScale), 50 * MenuScale)
				
				Text2(x + (Width / 2), y + (465 * MenuScale), Format(Format(GetLocalString("menu", "page"), Int(Max((mm\CurrMenuPage + 1), 1)), "{0}"), Int(Max((Int(Ceil(Float(SavedGamesAmount) / 5.0))), 1)), "{1}"), True, True)
				
				SetFont2(fo\FontID[Font_Default])
				
				If SavedGamesAmount = 0 Then
					RowText(GetLocalString("menu", "save.nosaves"), x + (20 * MenuScale), y + (20 * MenuScale), 540 * MenuScale, 390 * MenuScale)
				Else
					x = x + (20 * MenuScale)
					y = y + (20 * MenuScale)
					
					CurrSave = First Save
					
					For i = 0 To 4 + (5 * mm\CurrMenuPage)
						If i > 0 Then CurrSave = After CurrSave
						If CurrSave = Null Then Exit
						If i >= (5 * mm\CurrMenuPage) Then
							RenderFrame(x, y, 540 * MenuScale, 70 * MenuScale)
							
							If CurrSave\Version <> VersionNumber Then
								Color(255, 0, 0)
							Else
								Color(255, 255, 255)
							EndIf
							
							Text2(x + (20 * MenuScale), y + (10 * MenuScale), ConvertToUTF8(CurrSave\Name))
							Text2(x + (20 * MenuScale), y + (30 * MenuScale), CurrSave\Time)
							Text2(x + (120 * MenuScale), y + (30 * MenuScale), CurrSave\Date)
							Text2(x + (20 * MenuScale), y + (50 * MenuScale), CurrSave\Version)
							
							If CurrSave = Last Save Then Exit
							y = y + (80 * MenuScale)
						EndIf
					Next
					
					If DelSave <> Null Then
						x = 739 * MenuScale
						y = 376 * MenuScale
						RenderFrame(x, y, 420 * MenuScale, 200 * MenuScale)
						RowText(GetLocalString("menu", "save.delete?"), x + (20 * MenuScale), y + (15 * MenuScale), 400 * MenuScale, 200 * MenuScale)
					EndIf
				EndIf
				;[End Block]
			Case MainMenuTab_Load_Map
				;[Block]
				y = y + Height + (20 * MenuScale)
				Width = 580 * MenuScale
				Height = 430 * MenuScale
				
				RenderFrame(x, y, Width, Height)
				
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				Width = 400 * MenuScale
				Height = 70 * MenuScale
				
				Color(255, 255, 255)
				SetFont2(fo\FontID[Font_Default_Big])
				Text2(x + (Width / 2), y + (Height / 2), GetLocalString("menu", "loadmap"), True, True)
				
				y = y + Height + (20 * MenuScale)
				Width = 580 * MenuScale
				Height = 350 * MenuScale
				
				SetFont2(fo\FontID[Font_Default_Big])
				
				tX = x + Width
				tY = y
				tW = 400 * MenuScale
				tH = 150 * MenuScale
				
				RenderFrame(x + (60 * MenuScale), y + (440 * MenuScale), Width - (120 * MenuScale), 50 * MenuScale)
				
				Text2(x + (Width / 2), y + (465 * MenuScale), Format(Format(GetLocalString("menu", "page"), Int(Max((mm\CurrMenuPage + 1), 1)), "{0}"), Int(Max((Int(Ceil(Float(CustomMapsAmount) / 5.0))), 1)), "{1}"), True, True)
				
				SetFont2(fo\FontID[Font_Default])
				
				If CustomMapsAmount = 0 Then
					RowText(GetLocalString("menu", "nomap"), x + (20 * MenuScale), y + (20 * MenuScale), 540 * MenuScale, 390 * MenuScale)
				Else
					x = x + (20 * MenuScale)
					y = y + (20 * MenuScale)
					
					CurrCustomMap = First CustomMaps
					
					For i = 0 To 4 + (5 * mm\CurrMenuPage)
						If i > 0 Then CurrCustomMap = After CurrCustomMap
						If CurrCustomMap = Null Then Exit
						If i >= (5 * mm\CurrMenuPage) Then
							RenderFrame(x, y, 540 * MenuScale, 70 * MenuScale)
							
							If Len(ConvertToUTF8(CurrCustomMap\Name)) > 20 Then
								Text2(x + (20 * MenuScale), y + (15 * MenuScale), Left(ConvertToUTF8(CurrCustomMap\Name), 19) + "...")
							Else
								Text2(x + (20 * MenuScale), y + (15 * MenuScale), ConvertToUTF8(CurrCustomMap\Name))
							EndIf
							Text2(x + (20 * MenuScale), y + (45 * MenuScale), ConvertToUTF8(CurrCustomMap\Author))
							
							If MouseOn(x + (280 * MenuScale), y + (20 * MenuScale), 100 * MenuScale, 30 * MenuScale) Lor MouseOn(x + (400 * MenuScale), y + (20 * MenuScale), 100 * MenuScale, 30 * MenuScale) Then
								RenderMapCreatorTooltip(tX, tY, tW, tH, CurrCustomMap\Name)
							EndIf
							If CurrCustomMap = Last CustomMaps Then Exit
							y = y + (80 * MenuScale)
						EndIf
					Next
					
					If DelCustomMap <> Null Then
						x = 739 * MenuScale
						y = 376 * MenuScale
						RenderFrame(x, y, 420 * MenuScale, 200 * MenuScale)
						RowText(GetLocalString("menu", "map.delete?"), x + (20 * MenuScale), y + (15 * MenuScale), 400 * MenuScale, 200 * MenuScale)
					EndIf
				EndIf
				;[End Block]
			Case MainMenuTab_Options_Graphics, MainMenuTab_Options_Audio, MainMenuTab_Options_Controls, MainMenuTab_Options_Advanced
				;[Block]
				x = 159 * MenuScale
				y = 286 * MenuScale
				
				Width = 400 * MenuScale
				Height = 70 * MenuScale
				
				Color(255, 255, 255)
				SetFont2(fo\FontID[Font_Default_Big])
				Text2(x + (Width / 2), y + (Height / 2), GetLocalString("menu", "options"), True, True)
				
				y = y + Height + (20 * MenuScale)
				Width = 580 * MenuScale
				Height = 60 * MenuScale
				RenderFrame(x, y, Width, Height)
				
				Color(0, 255, 0)
				If mm\MainMenuTab = MainMenuTab_Options_Graphics
					Rect(x + (15 * MenuScale), y + (10 * MenuScale), (Width / 5) + (10 * MenuScale), (Height / 2) + (10 * MenuScale))
				ElseIf mm\MainMenuTab = MainMenuTab_Options_Audio
					Rect(x + (155 * MenuScale), y + (10 * MenuScale), (Width / 5) + (10 * MenuScale), (Height / 2) + (10 * MenuScale))
				ElseIf mm\MainMenuTab = MainMenuTab_Options_Controls
					Rect(x + (295 * MenuScale), y + (10 * MenuScale), (Width / 5) + (10 * MenuScale), (Height / 2) + (10 * MenuScale))
				ElseIf mm\MainMenuTab = MainMenuTab_Options_Advanced
					Rect(x + (435 * MenuScale), y + (10 * MenuScale), (Width / 5) + (10 * MenuScale), (Height / 2) + (10 * MenuScale))
				EndIf
				
				Color(255, 255, 255)
				
				SetFont2(fo\FontID[Font_Default])
				
				y = y + (70 * MenuScale)
				x = x + (20 * MenuScale)
				
				tX = x - (20 * MenuScale) + Width
				tY = y
				tW = 400.0 * MenuScale
				tH = 150.0 * MenuScale
				
				Select mm\MainMenuTab
					Case MainMenuTab_Options_Graphics
						;[Block]
						Height = 440 * MenuScale
						RenderFrame(x - (20 * MenuScale), y, Width, Height)
						
						y = y + (20 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "bump"))
						If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_BumpMapping)
						
						y = y + (30 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "vsync"))
						If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_VSync)
						
						y = y + (30 * MenuScale)
						
						Color(255 - (155 * (opt\DisplayMode <> 0)), 255 - (155 * (opt\DisplayMode <> 0)), 255 - (155 * (opt\DisplayMode <> 0)))
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "antialias"))
						If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_AntiAliasing)
						
						y = y + (30 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "lights"))
						If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_RoomLights)
						
						y = y + (40 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "gamma"))
						If (MouseOn(x + (290 * MenuScale), y, 164 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 1 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_ScreenGamma, opt\ScreenGamma)
						
						y = y + (45 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y, GetLocalString("options", "particle"))
						If (MouseOn(x + (290 * MenuScale), y - (9 * MenuScale), 164 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 2 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_ParticleAmount, opt\ParticleAmount)
						
						y = y + (45 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y, GetLocalString("options", "lod"))
						If (MouseOn(x + (290 * MenuScale), y - (9 * MenuScale), 164 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 3 Then RenderOptionsTooltip(tX, tY, tW, tH + (100 * MenuScale), Tooltip_TextureLODBias)
						
						y = y + (35 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "vram"))
						If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_SaveTexturesInVRAM)
						
						y = y + (40 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "fov"))
						If (MouseOn(x + (290 * MenuScale), y, 164 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 4 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_FOV)
						
						y = y + (45 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y, GetLocalString("options", "filter"))
						If (MouseOn(x + (290 * MenuScale), y - (9 * MenuScale), 164 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 5 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_AnisotropicFiltering)
						
						y = y + (35 * MenuScale)
						
						Color(255, 255, 255)
						If opt\Atmosphere Then
							TempStr = GetLocalString("options", "atmo.bright")
						Else
							TempStr = GetLocalString("options", "atmo.dark")
						EndIf
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "atmo") + TempStr)
						If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_Atmosphere)
						;[End Block]
					Case MainMenuTab_Options_Audio
						;[Block]
						If opt\EnableUserTracks Then
							Height = 280 * MenuScale
						Else
							Height = 210 * MenuScale
						EndIf
						RenderFrame(x - (20 * MenuScale), y, Width, Height)
						
						y = y + (20 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "mastervolume"))
						If (MouseOn(x + (290 * MenuScale), y, 164 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 1 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_MasterVolume, opt\MasterVolume)
						
						y = y + (40 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "musicvolume"))
						If (MouseOn(x + (290 * MenuScale), y, 164 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 2 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_MusicVolume, opt\MusicVolume)
						
						y = y + (40 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "soundvolume"))
						If (MouseOn(x + (290 * MenuScale), y, 164 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 3 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_SoundVolume, opt\SFXVolume)
						
						y = y + (40 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "autorelease"))
						If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH + (220 * MenuScale), Tooltip_SoundAutoRelease)
						
						y = y + (30 * MenuScale)
						
						Color(255, 255, 255)
						Text2(x, y + (5 * MenuScale), GetLocalString("options", "enabletracks"))
						If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_UserTracks)
						
						y = y + (30 * MenuScale)
						
						If opt\EnableUserTracks Then
							Color(255, 255, 255)
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "trackmode"))
							If opt\UserTrackMode
								TempStr = GetLocalString("options", "track.repeat")
							Else
								TempStr = GetLocalString("options", "track.random")
							EndIf
							Text2(x + (330 * MenuScale), y + (5 * MenuScale), TempStr)
							If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_UserTracksMode)
							If MouseOn(x, y + (30 * MenuScale), 210 * MenuScale, 30 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_UserTrackScan)
							If UserTrackCheck > 0 Then Text2(x, y + (100 * MenuScale), Format(Format(GetLocalString("options", "track.found"), UserTrackCheck2, "{0}"), UserTrackCheck, "{1}"))
						EndIf
						;[End Block]
					Case MainMenuTab_Options_Controls
						;[Block]
						Height = (230 - ((mm\CurrMenuPage = 1) * (20 + (20 * (Not opt\CanOpenConsole))))) * MenuScale
						RenderFrame(x - (20 * MenuScale), y, Width, Height)
						y = y + (20 * MenuScale)
						If mm\CurrMenuPage = 0 Then
							Color(255, 255, 255)
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "mousesensitive"))
							If (MouseOn(x + (290 * MenuScale), y, 164 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 1 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_MouseSensitivity, opt\MouseSensitivity)
							
							y = y + (40 * MenuScale)
							
							Color(255, 255, 255)
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "invertx"))
							If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_MouseInvertX)
							
							y = y + (40 * MenuScale)
							
							Color(255, 255, 255)
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "inverty"))
							If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_MouseInvertY)
							
							y = y + (40 * MenuScale)
							
							Color(255, 255, 255)
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "mousesmooth"))
							If (MouseOn(x + (290 * MenuScale), y, 164 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 2 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_MouseSmoothing, opt\MouseSmoothing)
							
							y = y + (40 * MenuScale)
							
							If MouseOn(x, y, 240 * MenuScale, 30 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_ControlConfiguration)
						Else
							Color(255, 255, 255)
							
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "key.forward"))
							Text2(x + (260 * MenuScale), y, GetLocalString("options", "key.crouch"))
							
							y = y + (20 * MenuScale)
							
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "key.left"))
							Text2(x + (260 * MenuScale), y, GetLocalString("options", "key.blink"))
							
							y = y + (20 * MenuScale)
							
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "key.backward"))
							Text2(x + (260 * MenuScale), y, GetLocalString("options", "key.inv"))
							
							y = y + (20 * MenuScale)
							
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "key.right"))
							Text2(x + (260 * MenuScale), y, GetLocalString("options", "key.save"))
							
							y = y + (20 * MenuScale)
							
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "key.sprint"))
							Text2(x + (260 * MenuScale), y, GetLocalString("options", "key.screenshot"))
							
							If opt\CanOpenConsole Then
								y = y + (20 * MenuScale)
								
								Text2(x, y + (5 * MenuScale), GetLocalString("options", "key.console"))
							EndIf
							
							If MouseOn(x, y - ((80 + (20 * opt\CanOpenConsole)) * MenuScale), Width - (40 * MenuScale), (100 + (20 * opt\CanOpenConsole)) * MenuScale) Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_ControlConfiguration)
						EndIf
						;[End Block]
					Case MainMenuTab_Options_Advanced
						;[Block]
						Height = 340 * MenuScale
						
						RenderFrame(x - (20 * MenuScale), y, Width, Height)
						
						RenderFrame(x + (15 * MenuScale), y + Height + (5 * MenuScale), Width - (70 * MenuScale), 30 * MenuScale)
						
						Text2(x + (Width / 2), y + Height + (20 * MenuScale), Format(Format(GetLocalString("menu", "page"), Int(Max((mm\CurrMenuPage + 1), 1)), "{0}"), "2", "{1}"), True, True)
						
						If mm\CurrMenuPage = 0 Then
							y = y + (20 * MenuScale)
							
							Color(255, 255, 255)
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "hud"))
							If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_HUD)
							
							y = y + (30 * MenuScale)
							
							Color(255, 255, 255)
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "console"))
							If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_Console)
							
							y = y + (30 * MenuScale)
							
							If opt\CanOpenConsole Then
								Color(255, 255, 255)
								Text2(x, y + (5 * MenuScale), GetLocalString("options", "error"))
								If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_ConsoleOnError)
							EndIf
							
							y = y + (30 * MenuScale)
							
							Color(255, 255, 255)
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "achipop"))
							If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_AchievementPopups)
							
							y = y + (30 * MenuScale)
							
							Color(255 - (155 * (SelectedDifficulty\SaveType <> SAVE_ANYWHERE)), 255 - (155 * (SelectedDifficulty\SaveType <> SAVE_ANYWHERE)), 255 - (155 * (SelectedDifficulty\SaveType <> SAVE_ANYWHERE)))
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "save"))
							If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_AutoSave)
							
							y = y + (30 * MenuScale)
							
							Color(255, 255, 255)
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "fps"))
							If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_FPS)
							
							y = y + (30 * MenuScale)
							
							Color(255, 255, 255)
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "frame"))
							Color(255, 255, 255)
							If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_FrameLimit, opt\FrameLimit)
							If opt\CurrFrameLimit > 0.0 Then
								Color(255, 255, 0)
								Text2(x, y + (45 * MenuScale), opt\FrameLimit + " FPS")
								If (MouseOn(x + (130 * MenuScale), y + (40 * MenuScale), 164 * MenuScale, 20 * MenuScale) And mm\OnSliderID = 0) Lor mm\OnSliderID = 1 Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_FrameLimit, opt\FrameLimit)
							EndIf
						Else
							y = y + (20 * MenuScale)
							
							Color(255, 255, 255)
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "bar"))
							If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_SmoothBars)
							
							y = y + (30 * MenuScale)
							
							Color(255, 255, 255)
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "startvideo"))
							If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_StartupVideos)
							
							y = y + (30 * MenuScale)
							
							Color(255, 255, 255)
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "launcher"))
							If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_Launcher)
							
							y = y + (30 * MenuScale)
							
							Color(255, 255, 255)
							Text2(x, y + (5 * MenuScale), GetLocalString("options", "subtitles"))
							If MouseOn(x + (290 * MenuScale), y, 20 * MenuScale, 20 * MenuScale) Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_Subtitles)
							
							y = y + (30 * MenuScale)
							
							If opt\EnableSubtitles Then
								Color(255, 255, 255)
								Text2(x, y + (5 * MenuScale), GetLocalString("options", "subtitles.color"))
							EndIf
							
							y = y + (5 * MenuScale)
							
							If opt\EnableSubtitles Then
								If MouseOn(x + (230 * MenuScale), y, 147 * MenuScale, 147 * MenuScale) Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_SubtitlesColor)
							EndIf
							
							y = y + (30 * MenuScale)
							
							If opt\EnableSubtitles Then
								Color(255, 255, 255)
								Text2(x, y + (5 * MenuScale), GetLocalString("options", "subtitles.color.red"))
								If MouseOn(x + (125 * MenuScale), y, 40 * MenuScale, 20 * MenuScale) Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_SubtitlesColor)
							EndIf
							
							y = y + (30 * MenuScale)
							
							If opt\EnableSubtitles Then
								Color(255, 255, 255)
								Text2(x, y + (5 * MenuScale), GetLocalString("options", "subtitles.color.green"))
								If MouseOn(x + (125 * MenuScale), y, 40 * MenuScale, 20 * MenuScale) Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_SubtitlesColor)
							EndIf
							
							y = y + (30 * MenuScale)
							
							If opt\EnableSubtitles Then
								Color(255, 255, 255)
								Text2(x, y + (5 * MenuScale), GetLocalString("options", "subtitles.color.blue"))
								If MouseOn(x + (125 * MenuScale), y, 40 * MenuScale, 20 * MenuScale) Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_SubtitlesColor)
							EndIf
							
							y = y + (40 * MenuScale)
							
							If MouseOn(x, y, 170 * MenuScale, 30 * MenuScale) Then RenderOptionsTooltip(tX, tY, tW, tH, Tooltip_ResetOptions)
							
							If opt\EnableSubtitles Then
								Color(opt\SubColorR, opt\SubColorG, opt\SubColorB)
								Text2(x - (20 * MenuScale) + (Width / 2), y + (115 * MenuScale), GetLocalString("options", "subtitles.example_1"), True)
								Text2(x - (20 * MenuScale) + (Width / 2), y + (135 * MenuScale), GetLocalString("options", "subtitles.example_2"), True)
								Text2(x - (20 * MenuScale) + (Width / 2), y + (155 * MenuScale), GetLocalString("options", "subtitles.example_3"), True)
								Text2(x - (20 * MenuScale) + (Width / 2), y + (175 * MenuScale), GetLocalString("options", "subtitles.example_4"), True)
							EndIf
						EndIf
						;[End Block]
				End Select
				;[End Block]
		End Select
	EndIf
	
	RenderMenuButtons()
	RenderMenuPalettes()
	RenderMenuTicks()
	RenderMenuInputBoxes()
	RenderMenuSlideBars()
	RenderMenuSliders()
	
	Color(255, 255, 255)
	SetFont2(fo\FontID[Font_Console])
	Text2(20 * MenuScale, opt\GraphicHeight - (50 * MenuScale), "v" + VersionNumber)
	If opt\ShowFPS Then
		SetFont2(fo\FontID[Font_Console])
		Text2(20 * MenuScale, opt\GraphicHeight - (30 * MenuScale), "FPS: " + fps\FPS)
		SetFont2(fo\FontID[Font_Default])
	EndIf
	
	If opt\DisplayMode = 0 And (Not mm\OnPalette) Then DrawImage(CursorIMG, ScaledMouseX(), ScaledMouseY())
	
	SetFont2(fo\FontID[Font_Default])
	
	CatchErrors("Uncaught (RenderMainMenu")
End Function

Type LoadingScreens
	Field ImgPath$
	Field Img%
	Field ID%
	Field Title$
	Field AlignX%, AlignY%
	Field DisableBackground%
	Field Txt$[5], TxtAmount%
End Type

Function InitLoadingScreens%(File$)
	Local TemporaryString$, i%
	Local ls.LoadingScreens
	Local f% = OpenFile_Strict(File)
	
	While (Not Eof(f))
		TemporaryString = Trim(ReadLine(f))
		If Left(TemporaryString, 1) = "[" Then
			TemporaryString = Mid(TemporaryString, 2, Len(TemporaryString) - 2)
			
			ls.LoadingScreens = New LoadingScreens
			LoadingScreenAmount = LoadingScreenAmount + 1
			ls\ID = LoadingScreenAmount
			
			ls\Title = TemporaryString
			ls\ImgPath = GetFileLocalString(File, TemporaryString, "ImgPath")
			
			For i = 0 To 3
				ls\Txt[i] = GetFileLocalString(File, TemporaryString, "Desc" + (i + 1))
				If ls\Txt[i] <> "" Then ls\TxtAmount = ls\TxtAmount + 1
			Next
			
			ls\DisableBackground = GetFileLocalString(File, TemporaryString, "DisableBackground")
			
			ls\AlignX = Int(GetFileLocalString(File, TemporaryString, "AlignX"))
			ls\AlignY = Int(GetFileLocalString(File, TemporaryString, "AlignY"))
		EndIf
	Wend
	
	CloseFile(f)
End Function

Global TextR# = 0.0, TextG# = 0.0, TextB# = 0.0
Global ChangeColor%

Function RenderLoadingText%(x%, y%, Txt$, AlignX% = False, AlignY% = False)
	If TextR = 0.0 Then
		ChangeColor = True
	ElseIf TextR = 255.0
		ChangeColor = False
	EndIf
	
	If (Not ChangeColor) Then
		TextR = Max(0.0, TextR - 3.0)
		TextG = Max(0.0, TextG - 3.0)
		TextB = Max(0.0, TextB - 3.0)
	Else
		TextR = Min(TextR + 3.0, 255.0)
		TextG = Min(TextG + 3.0, 255.0)
		TextB = Min(TextB + 3.0, 255.0)
	EndIf
	SetFont2(fo\FontID[Font_Default])
	Color(TextR, TextG, TextB)
	Text2(x, y, Txt, AlignX, AlignY)
End Function

Function ResetLoadingTextColor%()
	TextR = 0.0 : TextG = 0.0 : TextB = 0.0
End Function

Function RenderLoading%(Percent%, Assets$ = "")
	Local x%, y%, Temp%, FirstLoop%
	Local ls.LoadingScreens
	
	HidePointer()
	
	If Percent = 0 Then
		LoadingScreenText = 0
		
		Temp = Rand(LoadingScreenAmount)
		For ls.LoadingScreens = Each LoadingScreens
			If ls\ID = Temp Then
				If (Not ls\Img) Then
					ls\Img = LoadImage_Strict("LoadingScreens\" + ls\ImgPath + ".png")
					ls\Img = ScaleImage2(ls\Img, MenuScale, MenuScale)
					SelectedLoadingScreen = ls
				EndIf
				Exit
			EndIf
		Next
	EndIf
	
	FirstLoop = True
	
	Repeat 
		ClsColor(0, 0, 0)
		Cls()
		
		If Percent > 20 Then UpdateMusic()
		If Percent > (100.0 / SelectedLoadingScreen\TxtAmount) * (LoadingScreenText + 1) Then LoadingScreenText = LoadingScreenText + 1
		
		If (Not SelectedLoadingScreen\DisableBackground) Then DrawBlock(LoadingBack, mo\Viewport_Center_X - ImageWidth(LoadingBack) / 2, mo\Viewport_Center_Y - ImageHeight(LoadingBack) / 2)
		
		If SelectedLoadingScreen\AlignX = 0 Then
			x = mo\Viewport_Center_X - ImageWidth(SelectedLoadingScreen\Img) / 2
		ElseIf SelectedLoadingScreen\AlignX = 1
			x = opt\GraphicWidth - ImageWidth(SelectedLoadingScreen\Img)
		Else
			x = 0
		EndIf
		
		If SelectedLoadingScreen\AlignY = 0 Then
			y = mo\Viewport_Center_Y - ImageHeight(SelectedLoadingScreen\Img) / 2
		ElseIf SelectedLoadingScreen\AlignY = 1
			y = opt\GraphicHeight - ImageHeight(SelectedLoadingScreen\Img)
		Else
			y = 0
		EndIf
		
		DrawImage(SelectedLoadingScreen\Img, x, y)
		
		Local Width% = 300 * MenuScale
		Local Height% = 20 * MenuScale
		Local i%
		
		x = mo\Viewport_Center_X - (Width / 2)
		y = opt\GraphicHeight - (80 * MenuScale)
		
		RenderBar(BlinkMeterIMG, x, y, Width, Height, Percent)
		
		Color(255, 255, 255)
		SetFont2(fo\FontID[Font_Default])
		Text2(x + (Width / 2), opt\GraphicHeight - (70 * MenuScale), Percent + "%", True, True)
		
		If SelectedLoadingScreen\Title = "CWM" Then
			If FirstLoop Then
				If Percent = 0 Then
					PlaySound_Strict(LoadTempSound("SFX\SCP\990\cwm1.cwm"))
				ElseIf Percent = 100
					PlaySound_Strict(LoadTempSound("SFX\SCP\990\cwm2.cwm"))
				EndIf
			EndIf
			
			Local StrTemp$ = ""
			
			Temp = Rand(2, 9)
			For i = 0 To Temp
				StrTemp = StrTemp + Chr(Rand(48, 122))
			Next
			SetFont2(fo\FontID[Font_Default_Big])
			Text2(mo\Viewport_Center_X, mo\Viewport_Center_Y - (450 * MenuScale), StrTemp, True, True)
			
			If Percent = 0 Then
				If Rand(5) = 1 Then
					Select Rand(2)
						Case 1
							;[Block]
							SelectedLoadingScreen\Txt[0] = Format(GetLocalString("menu", "happend"), CurrentTime())
							;[End Block]
						Case 2
							;[Block]
							SelectedLoadingScreen\Txt[0] = CurrentTime()
							;[End Block]
					End Select
				Else
					Select Rand(13)
						Case 1
							;[Block]
							SelectedLoadingScreen\Txt[0] = GetLocalString("menu", "990_1")
							;[End Block]
						Case 2
							;[Block]
							SelectedLoadingScreen\Txt[0] = GetLocalString("menu", "990_2")
							;[End Block]
						Case 3
							;[Block]
							SelectedLoadingScreen\Txt[0] = GetLocalString("menu", "990_3")
							;[End Block]
						Case 4
							;[Block]
							SelectedLoadingScreen\Txt[0] = "eof9nsd3jue4iwe1fgj"
						Case 5
							;[Block]
							SelectedLoadingScreen\Txt[0] = GetLocalString("menu", "990_4")
							;[End Block]
						Case 6 
							;[Block]
							SelectedLoadingScreen\Txt[0] = GetLocalString("menu", "990_5")
							;[End Block]
						Case 7
							;[Block]
							SelectedLoadingScreen\Txt[0] = "???____??_???__????n?"
							;[End Block]
						Case 8, 9
							;[Block]
							SelectedLoadingScreen\Txt[0] = GetLocalString("menu", "990_6")
							;[End Block]
						Case 10
							;[Block]
							SelectedLoadingScreen\Txt[0] = "???????????"
							;[End Block]
						Case 11
							;[Block]
							SelectedLoadingScreen\Txt[0] = GetLocalString("menu", "990_7")
							;[End Block]
						Case 12
							;[Block]
							SelectedLoadingScreen\Txt[0] = GetLocalString("menu", "990_8")
							;[End Block]
						Case 13
							;[Block]
							SelectedLoadingScreen\Txt[0] = GetLocalString("menu", "990_9")
							;[End Block]
					End Select
				EndIf
			EndIf
			
			StrTemp = SelectedLoadingScreen\Txt[0]
			Temp = Int(Len(SelectedLoadingScreen\Txt[0]) - Rand(5))
			For i = 0 To Rand(10, 15)
				StrTemp = Replace(SelectedLoadingScreen\Txt[0], Mid(SelectedLoadingScreen\Txt[0], Rand(Len(StrTemp) - 1), 1), Chr(Rand(130, 250)))
			Next
			SetFont2(fo\FontID[Font_Default])
			RowText(StrTemp, mo\Viewport_Center_X - (200 * MenuScale), mo\Viewport_Center_Y + (250 * MenuScale), 400 * MenuScale, 300 * MenuScale, True)
		Else
			Color(255, 255, 255)
			SetFont2(fo\FontID[Font_Default_Big])
			Text2(mo\Viewport_Center_X, mo\Viewport_Center_Y - (450 * MenuScale), SelectedLoadingScreen\Title, True, True)
			SetFont2(fo\FontID[Font_Default])
			RowText(SelectedLoadingScreen\Txt[LoadingScreenText], mo\Viewport_Center_X - (200 * MenuScale), mo\Viewport_Center_Y + (250 * MenuScale), 400 * MenuScale, 300 * MenuScale, True)
		EndIf
		
		If Percent <> 100 Then
			Color(255, 255, 255)
			Text2(mo\Viewport_Center_X, opt\GraphicHeight - (35 * MenuScale), Format(GetLocalString("loading", "assets"), Assets), True, True)
			
			ResetInput()
		Else
			If FirstLoop And SelectedLoadingScreen\Title <> "CWM" Then PlaySound_Strict(LoadTempSound(("SFX\Horror\Horror8.ogg")))
			If SelectedLoadingScreen\Title = "CWM" Then
				StrTemp = GetLocalString("menu", "wakeup")
			Else
				StrTemp = GetLocalString("menu", "anykey")
			EndIf
			RenderLoadingText(mo\Viewport_Center_X, opt\GraphicHeight - (35 * MenuScale), StrTemp, True, True)
		EndIf
		
		RenderGamma()
		
		Flip(True)
		
		FirstLoop = False
		If Percent <> 100 Then Exit
		
		Local Close% = False
		
		If GetKey() <> 0 Lor MouseHit(1) Then
			ResetLoadingTextColor()
			ResetInput()
			ResetTimingAccumulator()
			SetFont2(fo\FontID[Font_Default])
			Close = True
		EndIf
	Until Close
	
	DeleteMenuGadgets()
End Function

Function RenderTiledImageRect%(Img%, SrcX%, SrcY%, SrcWidth%, SrcHeight%, x%, y%, Width%, Height%)
	Local x2% = x
	
	While x2 < x + Width
		If x2 + SrcWidth > x + Width Then SrcWidth = (x + Width) - x2
		
		Local y2% = y
		
		While y2 < y + Height
			DrawBlockRect(Img, x2, y2, SrcX, SrcY, SrcWidth, Min((y + Height) - y2, SrcHeight))
			y2 = y2 + SrcHeight
		Wend
		x2 = x2 + SrcWidth
	Wend
End Function

Function RenderFrame%(x%, y%, Width%, Height%, xOffset% = 0, yOffset% = 0, Locked% = False)
	Local IMG%
	
	Color(255, 255, 255)
	If Locked Then
		IMG = MenuGray
	Else
		IMG = MenuWhite
	EndIf
	RenderTiledImageRect(IMG, xOffset, yOffset, 512, 512, x, y, Width, Height)
	RenderTiledImageRect(MenuBlack, xOffset, yOffset, 512, 512, x + (3 * MenuScale), y + (3 * MenuScale), Width - (6 * MenuScale), Height - (6 * MenuScale))
End Function

Function RenderBar%(Img%, x%, y%, Width%, Height%, Value1#, Value2# = 100.0, R% = 100, G% = 100, B% = 100)
	Local i%
	
	Rect(x, y, Width + (4 * MenuScale), Height, False)
	If opt\SmoothBars Then
		Color(R, G, B)
		Rect(x + (3 * MenuScale), y + (3 * MenuScale), Float((Width - (2 * MenuScale)) * (Value1 / Value2)), Height - (6 * MenuScale))
	Else
		For i = 1 To Int(((Width - (2 * MenuScale)) * ((Value1 / Value2) / 10.0)) / MenuScale)
			DrawBlock(Img, x + ((3 + (10 * (i - 1))) * MenuScale), y + (3 * MenuScale))
		Next
	EndIf
End Function

Type MenuButton
	Field x%, y%, Width%, Height%
	Field Txt$
	Field BigFont%
	Field Locked%
	Field R%, G%, B%
End Type

Function UpdateMainMenuButton%(x%, y%, Width%, Height%, Txt$, BigFont% = True, WaitForMouseUp% = False, Locked% = False, R% = 255, G% = 255, B% = 255)
	Local mb.MenuButton, currMButton.MenuButton
	Local Clicked% = False
	Local ButtonExists% = False
	
	For mb.MenuButton = Each MenuButton
		If mb\x = x And mb\y = y And mb\Width = Width And mb\Height = Height Then
			ButtonExists = True
			Exit
		EndIf
	Next
	If (Not ButtonExists) Then
		mb.MenuButton = New MenuButton
		mb\x = x
		mb\y = y
		mb\Width = Width
		mb\Height = Height
		mb\Txt = Txt
		mb\BigFont = BigFont
		mb\Locked = Locked
		mb\R = R
		mb\B = B
		mb\G = G
	Else
		currMButton = mb
		currMButton\Txt = Txt
		currMButton\Locked = Locked
	EndIf
	
	If MouseOn(x, y, Width, Height) Then
		If (mo\MouseHit1 And (Not WaitForMouseUp)) Lor (mo\MouseUp1 And WaitForMouseUp) Then
			If Locked Then
				PlaySound_Strict(ButtonSFX2)
			Else
				Clicked = True
				PlaySound_Strict(ButtonSFX)
			EndIf
		EndIf
	EndIf
	Return(Clicked)
End Function

Function RenderMenuButtons%()
	Local mb.MenuButton
	
	For mb.MenuButton = Each MenuButton
		RenderFrame(mb\x, mb\y, mb\Width, mb\Height, (mb\x Mod 256), (mb\y Mod 256), mb\Locked)
		If MouseOn(mb\x, mb\y, mb\Width, mb\Height) Then
			Color(30, 30, 30)
			Rect(mb\x + (3 * MenuScale), mb\y + (3 * MenuScale), mb\Width - (6 * MenuScale), mb\Height - (6 * MenuScale))
		Else
			Color(0, 0, 0)
		EndIf
		
		If mb\Locked Then
			If mb\R <> 255 Lor mb\G <> 255 Lor mb\B <> 255 Then
				Color(mb\R, mb\G, mb\B)
			Else
				Color(100, 100, 100)
			EndIf
		Else
			Color(mb\R, mb\G, mb\B)
		EndIf
		If mb\BigFont Then
			SetFont2(fo\FontID[Font_Default_Big])
		Else
			SetFont2(fo\FontID[Font_Default])
		EndIf
		Text2(mb\x + (mb\Width / 2), mb\y + (mb\Height / 2), mb\Txt, True, True)
	Next
End Function

Function UpdateLauncherButton%(x%, y%, Width%, Height%, Txt$, BigFont% = True, WaitForMouseUp% = False, Locked% = False, R% = 255, G% = 255, B% = 255)
	Local Clicked% = False
	
	RenderFrame(x, y, Width, Height, 0, 0, Locked)
	If MouseOn(x, y, Width, Height) Then
		Color(30, 30, 30)
		If (mo\MouseHit1 And (Not WaitForMouseUp)) Lor (mo\MouseUp1 And WaitForMouseUp) Then
			If Locked Then
				PlaySound_Strict(ButtonSFX2)
			Else
				Clicked = True
				PlaySound_Strict(ButtonSFX)
			EndIf
		EndIf
		Rect(x + 3, y + 3, Width - 6, Height - 6)
	Else
		Color(0, 0, 0)
	EndIf
	
	If Locked Then
		If R <> 255 Lor G <> 255 Lor B <> 255 Then
			Color(R, G, B)
		Else
			Color(100, 100, 100)
		EndIf
	Else
		Color(R, G, B)
	EndIf
	If BigFont Then
		SetFont2(fo\FontID[Font_Default_Big])
	Else
		SetFont2(fo\FontID[Font_Default])
	EndIf
	Text2(x + (Width / 2), y + (Height / 2), Txt, True, True)
	Return(Clicked)
End Function

Type MenuTick
	Field x%, y%
	Field Selected%
	Field Locked%
End Type

Function UpdateMainMenuTick%(x%, y%, Selected%, Locked% = False)
	Local mt.MenuTick, currTick.MenuTick
	Local TickExists% = False
	Local Width% = 20 * MenuScale, Height% = 20 * MenuScale
	
	For mt.MenuTick = Each MenuTick
		If mt\x = x And mt\y = y Then
			TickExists = True
			Exit
		EndIf
	Next
	If (Not TickExists) Then
		mt.MenuTick = New MenuTick
		mt\x = x
		mt\y = y
		mt\Selected = Selected
		mt\Locked = Locked
	Else
		currTick = mt
		mt\Selected = Selected
		mt\Locked = Locked
	EndIf
	
	Local Highlight% = MouseOn(x, y, Width, Height)
	
	If Highlight Then
		If mo\MouseHit1 Then
			If Locked Then
				PlaySound_Strict(ButtonSFX2)
			Else
				Selected = (Not Selected)
				PlaySound_Strict(ButtonSFX)
			EndIf
		EndIf
	EndIf
	Return(Selected)
End Function

Function RenderMenuTicks%()
	Local mt.MenuTick
	Local Width%, Height%
	Local IMG%
	
	For mt.MenuTick = Each MenuTick
		Width = 20 * MenuScale
		Height = 20 * MenuScale
		
		If mt\Locked Then
			IMG = MenuGray
		Else
			IMG = MenuWhite
		EndIf
		
		Color(255, 255, 255)
		RenderTiledImageRect(IMG, (mt\x Mod 256), (mt\y Mod 256), 512, 512, mt\x, mt\y, Width, Height)
		
		Local Highlight% = MouseOn(mt\x, mt\y, Width, Height)
		
		If Highlight Then
			Color(50, 50, 50)
		Else
			Color(0, 0, 0)
		EndIf
		
		Rect(mt\x + 2, mt\y + 2, Width - 4, Height - 4)
		
		If mt\Selected Then
			If Highlight Then
				Color(255, 255, 255)
			Else
				Color(200, 200, 200)
			EndIf
			RenderTiledImageRect(IMG, (mt\x Mod 256), (mt\y Mod 256), 512, 512, mt\x + 4, mt\y + 4, Width - 8, Height - 8)
		EndIf
		Color(255, 255, 255)
	Next
End Function

Function UpdateLauncherTick%(x%, y%, Selected%, Locked% = False)
	Local Width% = 20, Height% = 20
	Local Highlight% = MouseOn(x, y, Width, Height)
	Local IMG%
	
	If Locked Then
		IMG = MenuGray
	Else
		IMG = MenuWhite
	EndIf
	
	Color(255, 255, 255)
	RenderTiledImageRect(IMG, (x Mod 256), (y Mod 256), 512, 512, x, y, Width, Height)
	
	If Highlight Then
		If Locked Then
			Color(0, 0, 0)
			If mo\MouseHit1 Then PlaySound_Strict(ButtonSFX2)
		Else
			Color(50, 50, 50)
			If mo\MouseHit1 Then Selected = (Not Selected) : PlaySound_Strict(ButtonSFX)
		EndIf
	Else
		Color(0, 0, 0)
	EndIf
	
	Rect(x + 2, y + 2, Width - 4, Height - 4)
	
	If Selected Then
		If Highlight Then
			Color(255, 255, 255)
		Else
			Color(200, 200, 200)
		EndIf
		RenderTiledImageRect(IMG, (x Mod 256), (y Mod 256), 512, 512, x + 4, y + 4, Width - 8, Height - 8)
	EndIf
	Color(255, 255, 255)
	Return(Selected)
End Function

Type MenuPalette
	Field Img%
	Field x%, y%, Width%, Height%
End Type

Function UpdateMainMenuPalette%(Img%, x%, y%)
	Local mp.MenuPalette
	Local PaletteExists% = False
	
	For mp.MenuPalette = Each MenuPalette
		If mp\x = x And mp\y = y Then
			PaletteExists = True
			Exit
		EndIf
	Next
	If (Not PaletteExists) Then
		mp.MenuPalette = New MenuPalette
		mp\Img = Img
		mp\x = x
		mp\y = y
		mp\Width = ImageWidth(Img)
		mp\Height = ImageHeight(Img)
	EndIf
	
	If MouseOn(x, y, ImageWidth(Img), ImageHeight(Img)) Then
		mm\OnPalette = True
	Else
		mm\OnPalette = False
	EndIf
End Function

Function RenderMenuPalettes%()
	Local mp.MenuPalette
	
	For mp.MenuPalette = Each MenuPalette
		DrawImage(mp\Img, mp\x, mp\y)
		If MouseOn(mp\x, mp\y, mp\Width, mp\Height) Then
			If mo\MouseDown1 And mm\OnSliderID = 0 Then
				LockBuffer(BackBuffer())
				
				Local Pixel% = ReadPixelFast(ScaledMouseX(), ScaledMouseY(), BackBuffer())
				
				UnlockBuffer(BackBuffer())
				opt\SubColorR = ReadPixelColor(Pixel, 16)
				opt\SubColorG = ReadPixelColor(Pixel, 8)
				opt\SubColorB = ReadPixelColor(Pixel, 0)
			EndIf
			Color(0, 0, 0)
			Oval(ScaledMouseX(), ScaledMouseY(), 5 * MenuScale, 5 * MenuScale, False)
		EndIf
	Next
End Function

Function ChrCanDisplay%(Char%)
	Return((Char >= 32) And (Char <= 126))
End Function

Global PrevInputBoxCtrl%, InsertMode% = False

Function UpdateInput$(aString$, MaxChr%)
	Local Value% = GetKey()
	Local Length% = Len(aString)
	
	If (CursorPos < 0) And (CursorPos <> -1) Then CursorPos = Length
	If CursorPos < 0 Then CursorPos = 0
	
	If KeyHit(210) Then InsertMode = (Not InsertMode) ; ~ Insert key
	If KeyHit(199) Then CursorPos = 0 ; ~ Home key
	If KeyHit(207) Then CursorPos = Length ; ~ End key
	If KeyHit(211) Then ; ~ Delete key
		aString = Left(aString, CursorPos) + Right(aString, Max(Length - CursorPos - 1, 0.0))
		CursorPos = CursorPos + 1
	EndIf
	
	If KeyDown(29) Lor KeyDown(157) Then ; ~ Control key
		If Value = 30 Then CursorPos = Length ; ~ Control & Right arrow
		If Value = 31 Then CursorPos = 0 ; ~ Control & Left arrow
		If Value = 3 Then SetClipboardContents(aString) ; ~ Control & C
		If Value = 22 Then ; ~ Control & V
			aString = Left(aString, CursorPos) + GetClipboardContents() + Right(aString, Length - CursorPos)
			CursorPos = CursorPos + Len(aString) - Length
			If MaxChr > 0 And MaxChr < Len(aString) Then aString = Left(aString, MaxChr) : CursorPos = MaxChr
		EndIf
		Return(aString)
	EndIf
	
	If Value = 30 Then CursorPos = CursorPos + 1 : PrevInputBoxCtrl = MilliSecs2() : Return(aString)
	If Value = 31 Then CursorPos = CursorPos - 1 : PrevInputBoxCtrl = MilliSecs2() : Return(aString)

	If KeyDown(205) And ((MilliSecs2() - PrevInputBoxCtrl) > 500) Then ; ~ Right arrow
		If (MilliSecs2() Mod 100) < 25 Then CursorPos = Min(CursorPos + 1, Length)
	ElseIf KeyDown(203) And ((MilliSecs2() - PrevInputBoxCtrl) > 500) Then ; ~ Left arrow
		If (MilliSecs2() Mod 100) < 25 Then CursorPos = Max(CursorPos - 1, 0.0)
	Else
		If InsertMode Then
			If ChrCanDisplay(Value) Then
				aString = TextInput(Left(aString, CursorPos)) + Mid(aString, CursorPos + 2)
				CursorPos = CursorPos + 1
			ElseIf Value = 8 Then ; ~ Backspace
				aString = TextInput(Left(aString, CursorPos)) + Mid(aString, CursorPos + 1)
			ElseIf Value = 4 Then ; ~ Delete
				aString = Left(aString, CursorPos) + Right(aString, Max(Length - CursorPos - 1, 0.0))
			EndIf
		Else
			aString = TextInput(Left(aString, CursorPos)) + Mid(aString, CursorPos + 1)
		EndIf
		CursorPos = CursorPos + Len(aString) - Length
		If MaxChr > 0 And MaxChr < Len(aString) Then
			aString = Left(aString, MaxChr)
			CursorPos = MaxChr
		EndIf
	EndIf
	Return(aString)
End Function

Type MenuInputBox
	Field x%, y%, Width%, Height%
	Field Txt$
	Field ID%
End Type

Function UpdateMainMenuInputBox$(x%, y%, Width%, Height%, Txt$, ID% = 0, MaxChr% = 0)
	Local mib.MenuInputBox, currInputBox.MenuInputBox
	Local InputBoxExists% = False
	
	For mib.MenuInputBox = Each MenuInputBox
		If mib\x = x And mib\y = y And mib\Width = Width And mib\Height = Height Then
			InputBoxExists = True
			Exit
		EndIf
	Next
	If (Not InputBoxExists) Then
		mib.MenuInputBox = New MenuInputBox
		mib\x = x
		mib\y = y
		mib\Width = Width
		mib\Height = Height
		mib\Txt = Txt
		mib\ID = ID
	Else
		currInputBox = mib
		currInputBox\Txt = Txt
	EndIf
	
	Local MouseOnBox% = False
	
	If MouseOn(x, y, Width, Height) Then
		MouseOnBox = True
		If mo\MouseHit1 Then
			SelectedInputBox = ID
			FlushKeys()
			CursorPos = -2
		EndIf
	EndIf
	
	If (Not MouseOnBox) And mo\MouseHit1 And SelectedInputBox = ID Then
		SelectedInputBox = 0
		CursorPos = -2
	EndIf
	
	If SelectedInputBox = ID Then Txt = UpdateInput(Txt, MaxChr)
	Return(Txt)
End Function

Function RenderMenuInputBoxes%()
	Local mib.MenuInputBox
	
	For mib.MenuInputBox = Each MenuInputBox
		Color(255, 255, 255)
		RenderFrame(mib\x, mib\y, mib\Width, mib\Height, (mib\x Mod 256), (mib\y Mod 256))
		
		If MouseOn(mib\x, mib\y, mib\Width, mib\Height) Then
			Color(50, 50, 50)
			Rect(mib\x + (3 * MenuScale), mib\y + (3 * MenuScale), mib\Width - (6 * MenuScale), mib\Height - (6 * MenuScale))
		EndIf
		
		Color(255, 255, 255)
		If SelectedInputBox = mib\ID Then
			If ((MilliSecs2() Mod 800) < 400) Lor KeyDown(205) Lor KeyDown(203) Lor InsertMode Then Rect(mib\x + (mib\Width / 2) - (StringWidth(mib\Txt) / 2) + StringWidth(Left(mib\Txt, Max(CursorPos, 0))), mib\y + (mib\Height / 2) - (5 * MenuScale), 2 * MenuScale, 12 * MenuScale)
		EndIf
		
		Text2(mib\x + (mib\Width / 2), mib\y + (mib\Height / 2), mib\Txt, True, True)
	Next
End Function

Type MenuSlideBar
	Field x%, y%, Width%
	Field Value#
End Type

Function UpdateMainMenuSlideBar#(x%, y%, Width%, Value#, ID%)
	Local msb.MenuSlideBar, currSlideBar.MenuSlideBar
	Local SlideBarExists% = False
	
	For msb.MenuSlideBar = Each MenuSlideBar
		If msb\x = x And msb\y = y And msb\Width = Width Then
			SlideBarExists = True
			Exit
		EndIf
	Next
	If (Not SlideBarExists) Then
		msb.MenuSlideBar = New MenuSlideBar
		msb\x = x
		msb\y = y
		msb\Width = Width
		msb\Value = Value
	Else
		currSlideBar = msb
		currSlideBar\Value = Value
	EndIf
	
	If mo\MouseDown1 And mm\OnSliderID = 0 Then
		If (ScaledMouseX() >= x And ScaledMouseX() <= x + Width + (14 * MenuScale)) And (ScaledMouseY() >= y And ScaledMouseY() <= (y + 20 * MenuScale)) Then mm\OnSliderID = ID
	EndIf
	If ID = mm\OnSliderID Then Value = Min(Max((ScaledMouseX() - x) * 100 / Width, 0.0), 100.0)
	Return(Value)
End Function

Function RenderMenuSlideBars%()
	Local msb.MenuSlideBar
	
	For msb.MenuSlideBar = Each MenuSlideBar
		Color(255, 255, 255)
		Rect(msb\x, msb\y, msb\Width + (14 * MenuScale), 20 * MenuScale, False)
		
		DrawBlock(BlinkMeterIMG, msb\x + msb\Width * msb\Value / 100.0 + (3 * MenuScale), msb\y + (3 * MenuScale))
		
		Color(170, 170, 170)
		Text2(msb\x - (50 * MenuScale), msb\y + (5 * MenuScale), GetLocalString("options", "slider.low"))
		Text2(msb\x + msb\Width + (34 * MenuScale), msb\y + (5 * MenuScale), GetLocalString("options", "slider.high"))
	Next
End Function

Type MenuSlider
	Field x%, y%, Width%
	Field Value%
	Field ID%
	Field Val1$, Val2$, Val3$, Val4$, Val5$
	Field Amount%
End Type

Function UpdateMainMenuSlider3%(x%, y%, Width%, Value%, ID%, Val1$, Val2$, Val3$)
	Local ms.MenuSlider, currSlider.MenuSlider
	Local Slider3Exists% = False
	
	For ms.MenuSlider = Each MenuSlider
		If ms\x = x And ms\y = y And ms\Width = Width And ms\Amount = 3 Then
			Slider3Exists = True
			Exit
		EndIf
	Next
	If (Not Slider3Exists) Then
		ms.MenuSlider = New MenuSlider
		ms\x = x
		ms\y = y
		ms\Width = Width
		ms\ID = ID
		ms\Value = Value
		ms\Val1 = Val1
		ms\Val2 = Val2
		ms\Val3 = Val3
		ms\Amount = 3
	Else
		currSlider = ms
		currSlider\Value = Value
	EndIf
	
	If mo\MouseDown1 And mm\OnSliderID = 0 Then
		If (ScaledMouseX() >= x And ScaledMouseX() <= x + Width + (14 * MenuScale)) And (ScaledMouseY() >= y - (8 * MenuScale) And ScaledMouseY() <= y + (10 * MenuScale)) Then mm\OnSliderID = ID
	EndIf
	
	If ID = mm\OnSliderID Then
		If ScaledMouseX() <= x + (8 * MenuScale) Then
			Value = 0
		ElseIf (ScaledMouseX() >= x + (Width / 2)) And (ScaledMouseX() <= x + (Width / 2) + (8 * MenuScale))
			Value = 1
		ElseIf ScaledMouseX() >= x + Width
			Value = 2
		EndIf
	EndIf
	Return(Value)
End Function

Function UpdateMainMenuSlider5%(x%, y%, Width%, Value%, ID%, Val1$, Val2$, Val3$, Val4$, Val5$)
	Local ms.MenuSlider, currSlider.MenuSlider
	Local Slider5Exists% = False
	
	For ms.MenuSlider = Each MenuSlider
		If ms\x = x And ms\y = y And ms\Width = Width And ms\Amount = 5 Then
			Slider5Exists = True
			Exit
		EndIf
	Next
	If (Not Slider5Exists) Then
		ms.MenuSlider = New MenuSlider
		ms\x = x
		ms\y = y
		ms\Width = Width
		ms\ID = ID
		ms\Value = Value
		ms\Val1 = Val1
		ms\Val2 = Val2
		ms\Val3 = Val3
		ms\Val4 = Val4
		ms\Val5 = Val5
		ms\Amount = 5
	Else
		currSlider = ms
		currSlider\Value = Value
	EndIf
	
	If mo\MouseDown1 And mm\OnSliderID = 0 Then
		If (ScaledMouseX() >= x) And (ScaledMouseX() <= x + Width + (14 * MenuScale)) And (ScaledMouseY() >= y - (8 * MenuScale)) And (ScaledMouseY() <= y + (10 * MenuScale)) Then mm\OnSliderID = ID
	EndIf
	
	If ID = mm\OnSliderID Then
		If ScaledMouseX() <= x + (8 * MenuScale) Then
			Value = 0
		ElseIf (ScaledMouseX() >= x + (Width / 4)) And (ScaledMouseX() <= x + (Width / 4) + (8 * MenuScale))
			Value = 1
		ElseIf (ScaledMouseX() >= x + (Width / 2)) And (ScaledMouseX() <= x + (Width / 2) + (8 * MenuScale))
			Value = 2
		ElseIf (ScaledMouseX() >= x + (Width * 0.75)) And (ScaledMouseX() <= x + (Width * 0.75) + (8 * MenuScale))
			Value = 3
		ElseIf ScaledMouseX() >= x + Width
			Value = 4
		EndIf
	EndIf
	Return(Value)
End Function

Function RenderMenuSliders%()
	Local ms.MenuSlider
	
	For ms.MenuSlider = Each MenuSlider
		If ms\Amount = 3
			If ms\ID = mm\OnSliderID Then
				Color(0, 255, 0)
			Else
				Color(200, 200, 200)
			EndIf
			Rect(ms\x, ms\y, ms\Width + (14 * MenuScale), 10 * MenuScale)
			Rect(ms\x, ms\y - (8 * MenuScale), 4 * MenuScale, 9 * MenuScale)
			Rect(ms\x + (ms\Width / 2) + (5 * MenuScale), ms\y - (8 * MenuScale), 4 * MenuScale, 9 * MenuScale)
			Rect(ms\x + ms\Width + (10 * MenuScale), ms\y - (8 * MenuScale), 4 * MenuScale, 9 * MenuScale)
			
			If ms\ID <> mm\OnSliderID Then
				If (ScaledMouseX() >= ms\x) And (ScaledMouseX() <= ms\x + ms\Width + (14 * MenuScale)) And (ScaledMouseY() >= ms\y - (8 * MenuScale)) And (ScaledMouseY() <= ms\y + (10 * MenuScale)) Then
					Color(0, 200, 0)
					Rect(ms\x, ms\y, ms\Width + (14 * MenuScale), 10 * MenuScale, False)
					Rect(ms\x, ms\y - (8 * MenuScale), 4 * MenuScale, 9 * MenuScale, False)
					Rect(ms\x + (ms\Width / 2) + (5 * MenuScale), ms\y - (8 * MenuScale), 4 * MenuScale, 9 * MenuScale, False)
					Rect(ms\x + ms\Width + (10 * MenuScale), ms\y - (8 * MenuScale), 4 * MenuScale, 9 * MenuScale, False)
				EndIf
			EndIf
			
			If ms\Value = 0 Then
				DrawBlock(BlinkMeterIMG, ms\x, ms\y - (8 * MenuScale))
			ElseIf ms\Value = 1
				DrawBlock(BlinkMeterIMG, ms\x + (ms\Width / 2) + (3 * MenuScale), ms\y - (8 * MenuScale))
			Else
				DrawBlock(BlinkMeterIMG, ms\x + ms\Width + (6 * MenuScale), ms\y - (8 * MenuScale))
			EndIf
			
			Color(170, 170, 170)
			If ms\Value = 0 Then
				Text2(ms\x + (2 * MenuScale), ms\y + (12 * MenuScale), ms\Val1, True)
			ElseIf ms\Value = 1
				Text2(ms\x + (ms\Width / 2) + (7 * MenuScale), ms\y + (12 * MenuScale), ms\Val2, True)
			Else
				Text2(ms\x + ms\Width + (12 * MenuScale), ms\y + (12 * MenuScale), ms\Val3, True)
			EndIf
		ElseIf ms\Amount = 5
			If ms\ID = mm\OnSliderID Then
				Color(0, 255, 0)
			Else
				Color(200, 200, 200)
			EndIf
			Rect(ms\x, ms\y, ms\Width + (14 * MenuScale), 10 * MenuScale)
			Rect(ms\x, ms\y - (8 * MenuScale), 4 * MenuScale, 9 * MenuScale)
			Rect(ms\x + (ms\Width / 4) + (2.5 * MenuScale), ms\y - (8 * MenuScale), 4 * MenuScale, 9 * MenuScale)
			Rect(ms\x + (ms\Width / 2) + (5 * MenuScale), ms\y - (8 * MenuScale), 4 * MenuScale, 9 * MenuScale)
			Rect(ms\x + (ms\Width * 0.75) + (7.5 * MenuScale), ms\y - (8 * MenuScale), 4 * MenuScale, 9 * MenuScale)
			Rect(ms\x + ms\Width + (10 * MenuScale), ms\y - (8 * MenuScale), 4 * MenuScale, 9 * MenuScale)
			
			If ms\ID <> mm\OnSliderID Then
				If (ScaledMouseX() >= ms\x) And (ScaledMouseX() <= ms\x + ms\Width + (14 * MenuScale)) And (ScaledMouseY() >= ms\y - (8 * MenuScale)) And (ScaledMouseY() <= ms\y + (10 * MenuScale)) Then
					Color(0, 200, 0)
					Rect(ms\x, ms\y, ms\Width + (14 * MenuScale), 10 * MenuScale, False)
					Rect(ms\x, ms\y - (8 * MenuScale), 4 * MenuScale, 9 * MenuScale, False)
					Rect(ms\x + (ms\Width / 4) + (2.5 * MenuScale), ms\y - (8 * MenuScale), 4 * MenuScale, 9 * MenuScale, False)
					Rect(ms\x + (ms\Width / 2) + (5 * MenuScale), ms\y - (8 * MenuScale), 4 * MenuScale, 9 * MenuScale, False)
					Rect(ms\x + (ms\Width * 0.75) + (7.5 * MenuScale), ms\y - (8 * MenuScale), 4 * MenuScale, 9 * MenuScale, False)
					Rect(ms\x + ms\Width + (10 * MenuScale), ms\y - (8 * MenuScale), 4 * MenuScale, 9 * MenuScale, False)
				EndIf
			EndIf
			
			If ms\Value = 0 Then
				DrawBlock(BlinkMeterIMG, ms\x, ms\y - (8 * MenuScale))
			ElseIf ms\Value = 1
				DrawBlock(BlinkMeterIMG, ms\x + (ms\Width / 4) + (1.5 * MenuScale), ms\y - (8 * MenuScale))
			ElseIf ms\Value = 2
				DrawBlock(BlinkMeterIMG, ms\x + (ms\Width / 2) + (3 * MenuScale), ms\y - (8 * MenuScale))
			ElseIf ms\Value = 3
				DrawBlock(BlinkMeterIMG, ms\x + (ms\Width * 0.75) + (4.5 * MenuScale), ms\y - (8 * MenuScale))
			Else
				DrawBlock(BlinkMeterIMG, ms\x + ms\Width + (6 * MenuScale), ms\y - (8 * MenuScale))
			EndIf
			
			Color(170, 170, 170)
			If ms\Value = 0 Then
				Text2(ms\x + (2 * MenuScale), ms\y + (12 * MenuScale), ms\Val1, True)
			ElseIf ms\Value = 1
				Text2(ms\x + (ms\Width / 4) + (4.5 * MenuScale), ms\y + (12 * MenuScale), ms\Val2, True)
			ElseIf ms\Value = 2
				Text2(ms\x + (ms\Width / 2) + (7 * MenuScale), ms\y + (12 * MenuScale), ms\Val3, True)
			ElseIf ms\Value = 3
				Text2(ms\x + (ms\Width * 0.75) + (9.5 * MenuScale), ms\y + (12 * MenuScale), ms\Val4, True)
			Else
				Text2(ms\x + ms\Width + (12 * MenuScale), ms\y + (12 * MenuScale), ms\Val5, True)
			EndIf
		EndIf
	Next
End Function

Function DeleteMenuGadgets%()
	Delete Each MenuButton
	Delete Each MenuPalette
	Delete Each MenuTick
	Delete Each MenuInputBox
	Delete Each MenuSlideBar
	Delete Each MenuSlider
End Function

Function RowText%(Txt$, x%, y%, W%, H%, Align% = False, Leading# = 1.0)
	; ~ Display A$ starting at x, y - no wider than W and no taller than H (all in pixels)
	; ~ Leading is optional extra vertical spacing in pixels
	
	If H < 1 Then H = SMALLEST_POWER_TWO
	
	Local LinesShown% = 0
	Local Height% = StringHeight(Txt) + Leading
	Local s$
	
	While Len(Txt) > 0
		Local Space$ = Instr(Txt, SplitSpace)
		
		If Space = 0 Then Space = Len(Txt)
		
		Local Temp$ = Left(Txt, Space)
		Local Trimmed$ = Trim(Temp) ; ~ We might ignore a final space 
		Local Extra% = 0 ; ~ We haven't ignored it yet
		
		; ~ Ignore final space if doing so would make a word fit at end of line:
		If StringWidth(s + Temp) > W And StringWidth(s + Trimmed) <= W Then
			Temp = Trimmed
			Extra = 1
		EndIf
		
		If StringWidth(s + Temp) > W Then ; ~ Too big, so print what will fit
			If Align Then
				Text2(x + (W / 2) - (StringWidth(s) / 2), y + (LinesShown * Height), s)
			Else
				Text2(x, y + (LinesShown * Height), s)
			EndIf
			
			LinesShown = LinesShown + 1
			s = ""
		Else ; ~ Append it to b$ (which will eventually be printed) and remove it from A$
			s = s + Temp
			Txt = Right(Txt, Len(Txt) - (Len(Temp) + Extra))
		EndIf
		If ((LinesShown + 1) * Height) > H Then Exit ; ~ The next line would be too tall, so leave
	Wend
	
	If s <> "" And (LinesShown + 1) <= H Then
		If Align Then
			Text2(x + (W / 2) - (StringWidth(s) / 2), y + (LinesShown * Height), s) ; ~ Print any remaining text if it'll fit vertically
		Else
			Text2(x, y + (LinesShown * Height), s) ; ~ Print any remaining text if it'll fit vertically
		EndIf
	EndIf
End Function

Function GetLineAmount%(Txt$, W%, H%, Leading# = 1.0)
	; ~ Display A$ no wider than W and no taller than H (all in pixels)
	; ~ Leading is optional extra vertical spacing in pixels
	
	If H < 1 Then H = SMALLEST_POWER_TWO
	
	Local LinesShown% = 0
	Local Height% = StringHeight(Txt) + Leading
	Local s$
	
	While Len(Txt) > 0
		Local Space$ = Instr(Txt, SplitSpace)
		
		If Space = 0 Then Space = Len(Txt)
		
		Local Temp$ = Left(Txt, Space)
		Local Trimmed$ = Trim(Temp) ; ~ We might ignore a final space
		Local Extra% = 0 ; ~ We haven't ignored it yet
		
		; ~ Ignore final space if doing so would make a word fit at end of line:
		If (StringWidth(s + Temp) > W) And (StringWidth(s + Trimmed) <= W) Then
			Temp = Trimmed
			Extra = 1
		EndIf
		
		If StringWidth(s + Temp) > W Then ; ~ Too big, so print what will fit
			LinesShown = LinesShown + 1
			s = ""
		Else ; ~ Append it to b$ (which will eventually be printed) and remove it from A$
			s = s + Temp
			Txt = Right(Txt, Len(Txt) - (Len(Temp) + Extra))
		EndIf
		If ((LinesShown + 1) * Height) > H Then Exit ; ~ The next line would be too tall, so leave
	Wend
	Return(LinesShown + 1)
End Function

; ~ Graphics Tooltips Constants
;[Block]
Const Tooltip_BumpMapping% = 0
Const Tooltip_VSync% = 1
Const Tooltip_AntiAliasing% = 2
Const Tooltip_RoomLights% = 3
Const Tooltip_ScreenGamma% = 4
Const Tooltip_TextureLODBias% = 5
Const Tooltip_ParticleAmount% = 6
Const Tooltip_SaveTexturesInVRAM% = 7
Const Tooltip_FOV% = 8
Const Tooltip_AnisotropicFiltering% = 9
Const Tooltip_Atmosphere% = 10
;[End Block]

; ~ Audio Tooltips Constants
;[Block]
Const Tooltip_MasterVolume% = 11
Const Tooltip_MusicVolume% = 12
Const Tooltip_SoundVolume% = 13
Const Tooltip_SoundAutoRelease% = 14
Const Tooltip_UserTracks% = 15
Const Tooltip_UserTracksMode% = 16
Const Tooltip_UserTrackScan% = 17
;[End Block]

; ~ Controls Tooltips Constants
;[Block]
Const Tooltip_MouseSensitivity% = 18
Const Tooltip_MouseInvertX% = 19
Const Tooltip_MouseInvertY% = 20
Const Tooltip_MouseSmoothing% = 21
Const Tooltip_ControlConfiguration% = 22
;[End Block]

; ~ Advanced Tooltips Constants
;[Block]
Const Tooltip_HUD% = 23
Const Tooltip_Console% = 24
Const Tooltip_ConsoleOnError% = 25
Const Tooltip_AchievementPopups% = 26
Const Tooltip_FPS% = 27
Const Tooltip_FrameLimit% = 28
Const Tooltip_AutoSave% = 29
Const Tooltip_SmoothBars% = 30
Const Tooltip_StartupVideos% = 31
Const Tooltip_Launcher% = 32
Const Tooltip_Subtitles% = 33
Const Tooltip_SubtitlesColor% = 34
Const Tooltip_ResetOptions% = 35
;[End Block]

Function RenderOptionsTooltip%(x%, y%, Width%, Height%, Option%, Value# = 0.0)
	Local fX# = x + (6.0 * MenuScale)
	Local fY# = y + (6.0 * MenuScale)
	Local fW# = Width - (12.0 * MenuScale)
	Local fH# = Height - (12.0 * MenuScale)
	Local Lines% = 0, Lines2% = 0
	Local Txt$ = "", Txt2$ = ""
	Local R% = 0, G% = 0, B% = 0
	
	SetFont2(fo\FontID[Font_Default])
	Color(255, 255, 255)
	Select Lower(Option)
			; ~ [GRAPHICS]
		Case Tooltip_BumpMapping
			;[Block]
			Txt = GetLocalString("tooltip", "bump")
			R = 255
			Txt2 = GetLocalString("tooltip", "cantchange")
			;[End Block]
		Case Tooltip_VSync
			;[Block]
			Txt = GetLocalString("tooltip", "vsync")
			;[End Block]
		Case Tooltip_AntiAliasing
			;[Block]
			Txt = GetLocalString("tooltip", "alias")
			R = 255 : G = 255
			Txt2 = GetLocalString("tooltip", "fullscreen")
			;[End Block]
		Case Tooltip_RoomLights
			;[Block]
			Txt = GetLocalString("tooltip", "lights")
			;[End Block]
		Case Tooltip_ScreenGamma
			;[Block]
			Txt = GetLocalString("tooltip", "gamma")
			R = 255 : G = 255
			Txt2 = Format(GetLocalString("tooltip", "default.value.100"), Int(Value * 100.0))
			;[End Block]
		Case Tooltip_ParticleAmount
			;[Block]
			Txt = GetLocalString("tooltip", "particle_1")
			Select Value
				Case 0
					;[Block]
					R = 255
					Txt2 = GetLocalString("tooltip", "particle_2.1")
					;[End Block]
				Case 1
					;[Block]
					R = 255
					G = 255
					Txt2 = GetLocalString("tooltip", "particle_2.2")
					;[End Block]
				Case 2
					;[Block]
					G = 255
					Txt2 = GetLocalString("tooltip", "particle_2.3")
					;[End Block]
			End Select
			;[End Block]
		Case Tooltip_TextureLODBias
			;[Block]
			Txt = GetLocalString("tooltip", "lod")
			;[End Block]
		Case Tooltip_SaveTexturesInVRAM
			;[Block]
			Txt = GetLocalString("tooltip", "vram")
			R = 255
			Txt2 = GetLocalString("tooltip", "cantchange")
			;[End Block]
		Case Tooltip_FOV
			;[Block]
			Txt = GetLocalString("tooltip", "fov")
			R = 255 : G = 255
			Txt2 = Format(GetLocalString("tooltip", "default.value.fov"), Int(opt\FOV))
			;[End Block]
		Case Tooltip_AnisotropicFiltering
			;[Block]
			Txt = GetLocalString("tooltip", "anisotropic")
			;[End Block]
		Case Tooltip_Atmosphere
			;[Block]
			Txt = GetLocalString("tooltip", "atmo")
			R = 255
			Txt2 = GetLocalString("tooltip", "cantchange")
			;[End Block]
			; ~ [AUDIO]
		Case Tooltip_MasterVolume
			;[Block]
			Txt = GetLocalString("tooltip", "mastervolume")
			R = 255 : G = 255
			Txt2 = Format(GetLocalString("tooltip", "default.value.50"), Int(Value * 100.0))
			;[End Block]
		Case Tooltip_MusicVolume
			;[Block]
			Txt = GetLocalString("tooltip", "musicvolume")
			R = 255 : G = 255
			Txt2 = Format(GetLocalString("tooltip", "default.value.50"), Int(Value * 100.0))
			;[End Block]
		Case Tooltip_SoundVolume
			;[Block]
			Txt = GetLocalString("tooltip", "soundvolume")
			R = 255 : G = 255
			Txt2 = Format(GetLocalString("tooltip", "default.value.50"), Int(Value * 100.0))
			;[End Block]
		Case Tooltip_SoundAutoRelease
			;[Block]
			Txt = GetLocalString("tooltip", "autorelease")
			R = 255
			Txt2 = GetLocalString("tooltip", "cantchange")
			;[End Block]
		Case Tooltip_UserTracks
			;[Block]
			Txt = GetLocalString("tooltip", "usertrack")
			R = 255
			Txt2 = GetLocalString("tooltip", "cantchange")
			;[End Block]
		Case Tooltip_UserTracksMode
			;[Block]
			Txt = GetLocalString("tooltip", "trackmode")
			R = 255 : G = 255
			Txt2 = GetLocalString("tooltip", "modenote")
			;[End Block]
		Case Tooltip_UserTrackScan
			;[Block]
			Txt = GetLocalString("tooltip", "scantrack")
			R = 255
			Txt2 = GetLocalString("tooltip", "cantchangebtn")
			;[End Block]
			; ~ [CONTROLS]
		Case Tooltip_MouseSensitivity
			;[Block]
			Txt = GetLocalString("tooltip", "mousespeed")
			R = 255 : G = 255
			Txt2 = Format(GetLocalString("tooltip", "default.value.0"), Int(Value * 100.0))
			;[End Block]
		Case Tooltip_MouseInvertX
			;[Block]
			Txt = GetLocalString("tooltip", "invertx")
			;[End Block]
		Case Tooltip_MouseInvertY
			;[Block]
			Txt = GetLocalString("tooltip", "inverty")
			;[End Block]
		Case Tooltip_MouseSmoothing
			;[Block]
			Txt = GetLocalString("tooltip", "mousesmooth")
			R = 255 : G = 255
			Txt2 = Format(GetLocalString("tooltip", "default.value.100"), Int(Value * 100.0))
			;[End Block]
		Case Tooltip_ControlConfiguration
			;[Block]
			Txt = GetLocalString("tooltip", "configcontrol")
			;[End Block]
			; ~ [ADVANCED]
		Case Tooltip_HUD
			;[Block]
			Txt = GetLocalString("tooltip", "hud")
			;[End Block]
		Case Tooltip_Console
			;[Block]
			Txt = Format(GetLocalString("tooltip", "console"), key\Name[key\CONSOLE])
			;[End Block]
		Case Tooltip_ConsoleOnError
			;[Block]
			Txt = GetLocalString("tooltip", "errorconsole")
			;[End Block]
		Case Tooltip_AchievementPopups
			;[Block]
			Txt = GetLocalString("tooltip", "achipop")
			;[End Block]
		Case Tooltip_AutoSave
			;[Block]
			Txt = Format(GetLocalString("tooltip", "autosave"), key\Name[key\SAVE])
			R = 255 : G = 255
			Txt2 = GetLocalString("tooltip", "autosave.note")
			;[End Block]
		Case Tooltip_FPS
			;[Block]
			Txt = GetLocalString("tooltip", "fps")
			;[End Block]
		Case Tooltip_FrameLimit
			;[Block]
			Txt = GetLocalString("tooltip", "frame")
			If Value > 0 And Value < 60 Then
				R = 255 : G = 255
				Txt2 = GetLocalString("tooltip", "frame.note")
			EndIf
			;[End Block]
		Case Tooltip_SmoothBars
			;[Block]
			Txt = GetLocalString("tooltip", "bar")
			R = 255
			Txt2 = GetLocalString("tooltip", "cantchange")
			;[End Block]
		Case Tooltip_StartupVideos
			;[Block]
			Txt = GetLocalString("tooltip", "startvideo")
			R = 255
			Txt2 = GetLocalString("tooltip", "cantchange")
			;[End Block]
		Case Tooltip_Launcher
			;[Block]
			Txt = GetLocalString("tooltip", "launcher")
			R = 255
			Txt2 = GetLocalString("tooltip", "cantchange")
			;[End Block]
		Case Tooltip_Subtitles
			;[Block]
			Txt = GetLocalString("tooltip", "subtitles")
			R = 255
			Txt2 = GetLocalString("tooltip", "cantchange")
			;[End Block]
		Case Tooltip_SubtitlesColor
			;[Block]
			Txt = GetLocalString("tooltip", "subtitles.color")
			;[End Block]
		Case Tooltip_ResetOptions
			;[Block]
			Txt = GetLocalString("tooltip", "reset")
			;[End Block]
	End Select
	
	Lines = GetLineAmount(Txt, fW, fH)
	If Txt2 = ""
		RenderFrame(x, y, Width, ((StringHeight(Txt) * Lines) + (10 + Lines) * MenuScale))
	Else
		Lines2 = GetLineAmount(Txt2, fW, fH)
		RenderFrame(x, y, Width, (((StringHeight(Txt) * Lines) + (10 + Lines) * MenuScale) + (StringHeight(Txt2) * Lines2) + (10 + Lines2) * MenuScale))
	EndIf
	RowText(Txt, fX, fY, fW, fH)
	If Txt2 <> "" Then
		Color(R, G, B)
		RowText(Txt2, fX, (fY + (StringHeight(Txt) * Lines) + (5 + Lines) * MenuScale), fW, fH)
	EndIf
End Function

Function RenderMapCreatorTooltip%(x%, y%, Width%, Height%, MapName$)
	Local fX# = x + (6.0 * MenuScale)
	Local fY# = y + (6.0 * MenuScale)
	Local fW# = Width - (12.0 * MenuScale)
	Local fH# = Height - (12.0 * MenuScale)
	Local Lines% = 0, Temp%
	
	SetFont2(fo\FontID[Font_Default])
	Color(255, 255, 255)
	
	Local Txt$[6]
	
	If Right(MapName, 6) = "cbmap2" Then
		Txt[0] = Left(ConvertToUTF8(MapName), Len(ConvertToUTF8(MapName)) - 7)
		
		Local f% = OpenFile_Strict(CustomMapsPath + MapName)
		Local Author$ = ConvertToUTF8(ReadLine(f))
		Local Descr$ = ConvertToUTF8(ReadLine(f))
		
		ReadByte(f)
		ReadByte(f)
		
		Local rAmount% = ReadInt(f)
		Local HasForest%, HasMT%
		
		If ReadInt(f) > 0 Then
			HasForest = True
		Else
			HasForest = False
		EndIf
		If ReadInt(f) > 0 Then
			HasMT = True
		Else
			HasMT = False
		EndIf
		
		CloseFile(f)
	Else
		Txt[0] = Left(MapName, Len(MapName) - 6)
		Author = GetLocalString("creator", "unknown")
		Descr = GetLocalString("creator", "nodesc")
		rAmount = 0
		HasForest = False
		HasMT = False
	EndIf
	Txt[1] = Format(GetLocalString("creator", "author"), Author)
	Txt[2] = Format(GetLocalString("creator", "desc"), Descr)
	If rAmount > 0 Then
		Txt[3] = Format(GetLocalString("creator", "ramount"), rAmount)
	Else
		Txt[3] = Format(GetLocalString("creator", "ramount"), GetLocalString("creator", "unknown"))
	EndIf
	If HasForest Then
		Txt[4] = Format(GetLocalString("creator", "forest"), GetLocalString("creator", "yes"))
	Else
		Txt[4] = Format(GetLocalString("creator", "forest"), GetLocalString("creator", "no"))
	EndIf
	If HasMT Then
		Txt[5] = Format(GetLocalString("creator", "mt"), GetLocalString("creator", "yes"))
	Else
		Txt[5] = Format(GetLocalString("creator", "mt"), GetLocalString("creator", "no"))
	EndIf
	
	Lines = GetLineAmount(Txt[2], fW, fH)
	RenderFrame(x, y, Width, (StringHeight(Txt[0]) * 6) + StringHeight(Txt[2]) * Lines + (5 * MenuScale))
	
	Color(255, 255, 255)
	Text2(fX, fY,Txt[0])
	Text2(fX, fY + StringHeight(Txt[0]), Txt[1])
	RowText(Txt[2], fX, fY + (StringHeight(Txt[0]) * 2), fW, fH)
	Text2(fX, fY + ((StringHeight(Txt[0]) * 2) + StringHeight(Txt[2]) * Lines + (5 * MenuScale)), Txt[3])
	Text2(fX, fY + ((StringHeight(Txt[0]) * 3) + StringHeight(Txt[2]) * Lines + (5 * MenuScale)), Txt[4])
	Text2(fX, fY + ((StringHeight(Txt[0]) * 4) + StringHeight(Txt[2]) * Lines + (5 * MenuScale)), Txt[5])
End Function

;~IDEal Editor Parameters:
;~C#Blitz3D