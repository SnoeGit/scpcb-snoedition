Type Cheats
	Field GodMode%
	Field NoBlink%
	Field NoTarget%
	Field NoClip%, NoClipSpeed#
	Field InfiniteStamina%
	Field SuperMan%, SuperManTimer#
	Field DebugHUD%
End Type

Global chs.Cheats = New Cheats

Function ClearCheats%()
	chs\GodMode = False
	chs\NoBlink = False
	chs\NoTarget = False
	chs\NoClip = False
	chs\NoClipSpeed = 2.0
	chs\InfiniteStamina = False
	chs\SuperMan = False
	chs\SuperManTimer = 0.0
	chs\DebugHUD = 0
End Function

Function InitCheats%()
	chs\GodMode = True
	chs\NoBlink = True
	chs\NoTarget = True
	chs\NoClip = True
	chs\NoClipSpeed = 2.0
	chs\InfiniteStamina = True
	chs\SuperMan = False
	chs\SuperManTimer = 0.0
	chs\DebugHUD = Rand(3)
End Function

Global ConsoleFlush%
Global ConsoleFlushSnd% = 0, ConsoleMusFlush% = 0, ConsoleMusPlay% = 0

Global ConsoleOpen%, ConsoleInput$
Global ConsoleScroll#, ConsoleScrollDragging%
Global ConsoleMouseMem%
Global ConsoleReissue.ConsoleMsg = Null
Global ConsoleR% = 255, ConsoleG% = 255, ConsoleB% = 255

Type ConsoleMsg
	Field Txt$
	Field IsCommand%
	Field R%, G%, B%
End Type

Function CreateConsoleMsg%(Txt$, R% = -1, G% = -1, B% = -1, IsCommand% = False)
	Local c.ConsoleMsg
	
	c.ConsoleMsg = New ConsoleMsg
	Insert c Before First ConsoleMsg
	
	c\Txt = Txt
	c\IsCommand = IsCommand
	
	c\R = R
	c\G = G
	c\B = B
	
	If c\R < 0 Then c\R = ConsoleR
	If c\G < 0 Then c\G = ConsoleG
	If c\B < 0 Then c\B = ConsoleB
End Function

Function UpdateConsole%()
	If (Not opt\CanOpenConsole) Then
		ConsoleOpen = False
		Return
	EndIf
	
	If ConsoleOpen Then
		Local ev.Events, e.Events, e2.Events, r.Rooms, it.Items, n.NPCs, snd.Sound, cm.ConsoleMsg, itt.ItemTemplates
		Local Tex%, Tex2%, InBar%, InBox%, MouseScroll#, Temp%, i%
		Local Args$, StrTemp$, StrTemp2$, StrTemp3$, StrTemp4$
		Local x%, y%, Width%, Height%
		
		ConsoleR = 255 : ConsoleG = 255 : ConsoleB = 255
		
		x = 0
		y = opt\GraphicHeight - 300 * MenuScale
		Width = opt\GraphicWidth
		Height = 270 * MenuScale
		
		Local ConsoleHeight% = 0
		Local ScrollBarHeight% = 0
		
		For cm.ConsoleMsg = Each ConsoleMsg
			ConsoleHeight = ConsoleHeight + (15 * MenuScale)
		Next
		ScrollBarHeight = (Float(Height) / Float(ConsoleHeight)) * Height
		If ScrollBarHeight > Height Then ScrollBarHeight = Height
		If ConsoleHeight < Height Then ConsoleHeight = Height
		
		InBar = MouseOn(x + Width - (26 * MenuScale), y, 26 * MenuScale, Height)
		InBox = MouseOn(x + Width - (23 * MenuScale), y + Height - ScrollBarHeight + (ConsoleScroll * ScrollBarHeight / Height), 20 * MenuScale, ScrollBarHeight)
		
		If (Not mo\MouseDown1) Then
			ConsoleScrollDragging = False
		ElseIf ConsoleScrollDragging
			ConsoleScroll = ConsoleScroll + ((ScaledMouseY() - ConsoleMouseMem) * Height / ScrollBarHeight)
			ConsoleMouseMem = ScaledMouseY()
		EndIf
		
		If (Not ConsoleScrollDragging) Then
			If mo\MouseHit1 Then
				If InBox Then
					ConsoleScrollDragging = True
					ConsoleMouseMem = ScaledMouseY()
				ElseIf InBar
					ConsoleScroll = ConsoleScroll + ((ScaledMouseY() - (y + Height)) * ConsoleHeight / Height + (Height / 2))
					ConsoleScroll = ConsoleScroll / 2
				EndIf
			EndIf
		EndIf
		
		MouseScroll = MouseZSpeed()
		If MouseScroll = 1 Then
			ConsoleScroll = ConsoleScroll - (15 * MenuScale)
		ElseIf MouseScroll= -1
			ConsoleScroll = ConsoleScroll + (15 * MenuScale)
		EndIf
		
		Local ReissuePos%
		
		If KeyHit(200) Then
			ReissuePos = 0
			If ConsoleReissue = Null Then
				ConsoleReissue = First ConsoleMsg
				
				While ConsoleReissue <> Null
					If ConsoleReissue\IsCommand Then Exit
					ReissuePos = ReissuePos - (15 * MenuScale)
					ConsoleReissue = After ConsoleReissue
				Wend
			Else
				cm.ConsoleMsg = First ConsoleMsg
				While cm <> Null
					If cm = ConsoleReissue Then Exit
					ReissuePos = ReissuePos - (15 * MenuScale)
					cm = After cm
				Wend
				ConsoleReissue = After ConsoleReissue
				ReissuePos = ReissuePos - (15 * MenuScale)
				
				While True
					If ConsoleReissue = Null Then
						ConsoleReissue = First ConsoleMsg
						ReissuePos = 0
					EndIf
					
					If ConsoleReissue\IsCommand Then Exit
					ReissuePos = ReissuePos - (15 * MenuScale)
					ConsoleReissue = After ConsoleReissue
				Wend
			EndIf
			
			If ConsoleReissue <> Null Then
				ConsoleInput = ConsoleReissue\Txt
				ConsoleScroll = ReissuePos + (Height / 2)
			EndIf
		EndIf
		
		If KeyHit(208) Then
			ReissuePos = (-ConsoleHeight) + (15 * MenuScale)
			If ConsoleReissue = Null Then
				ConsoleReissue = Last ConsoleMsg
				
				While ConsoleReissue <> Null
					If ConsoleReissue\IsCommand Then Exit
					ReissuePos = ReissuePos + (15 * MenuScale)
					ConsoleReissue = Before ConsoleReissue
				Wend
			Else
				cm.ConsoleMsg = Last ConsoleMsg
				While cm <> Null
					If cm = ConsoleReissue Then Exit
					ReissuePos = ReissuePos + (15 * MenuScale)
					cm = Before cm
				Wend
				ConsoleReissue = Before ConsoleReissue
				ReissuePos = ReissuePos + (15 * MenuScale)
				
				While True
					If ConsoleReissue = Null Then
						ConsoleReissue = Last ConsoleMsg
						ReissuePos = (-ConsoleHeight) + (15 * MenuScale)
					EndIf
					
					If ConsoleReissue\IsCommand Then Exit
					ReissuePos = ReissuePos + (15 * MenuScale)
					ConsoleReissue = Before ConsoleReissue
				Wend
			EndIf
			
			If ConsoleReissue <> Null Then
				ConsoleInput = ConsoleReissue\Txt
				ConsoleScroll = ReissuePos + (Height / 2)
			EndIf
		EndIf
		
		If ConsoleScroll < (-ConsoleHeight) + Height Then ConsoleScroll = (-ConsoleHeight) + Height
		If ConsoleScroll > 0 Then ConsoleScroll = 0
		
		SelectedInputBox = 2
		
		Local OldConsoleInput$ = ConsoleInput
		
		ConsoleInput = UpdateMainMenuInputBox(x, y + Height, Width, 30 * MenuScale, ConsoleInput, 2)
		If OldConsoleInput <> ConsoleInput Then ConsoleReissue = Null
		ConsoleInput = Left(ConsoleInput, 100)
		
		If KeyHit(28) And ConsoleInput <> "" Then
			ConsoleReissue = Null
			ConsoleScroll = 0
			CreateConsoleMsg(ConsoleInput, 255, 255, 0, True)
			If Instr(ConsoleInput, " ") <> 0 Then
				StrTemp = Lower(Left(ConsoleInput, Instr(ConsoleInput, " ") - 1))
			Else
				StrTemp = Lower(ConsoleInput)
			EndIf
			
			Select Lower(StrTemp)
				Case "help"
					;[Block]
					If Instr(ConsoleInput, " ") <> 0 Then
						StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					Else
						StrTemp = ""
					EndIf
					ConsoleR = 0 : ConsoleG = 255 : ConsoleB = 255
					
					Select StrTemp
						Case "1", ""
							;[Block]
							CreateConsoleMsg("LIST OF COMMANDS - PAGE 1 / 3")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("- ending")
							CreateConsoleMsg("- notarget")
							CreateConsoleMsg("- godmode")
							CreateConsoleMsg("- noclip")
							CreateConsoleMsg("- noclipspeed")
							CreateConsoleMsg("- infinitestamina")
							CreateConsoleMsg("- noblink")
							CreateConsoleMsg("- asd")
							CreateConsoleMsg("- revive")
							CreateConsoleMsg("- heal")
							CreateConsoleMsg("- wireframe")
							CreateConsoleMsg("- halloween")
							CreateConsoleMsg("- newyear") 
							CreateConsoleMsg("- sanic")
							CreateConsoleMsg("- weed")
							CreateConsoleMsg("- money")
							CreateConsoleMsg("- debughud")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Use " + Chr(34) + "help 2 / 3" + Chr(34) + " to find more commands.")
							CreateConsoleMsg("Use " + Chr(34) + "help [command name]" + Chr(34) + " to get more information about a command.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "2"
							;[Block]
							CreateConsoleMsg("LIST OF COMMANDS - PAGE 2 / 3")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("- reset096")
							CreateConsoleMsg("- reset372")
							CreateConsoleMsg("- 106retreat")
							CreateConsoleMsg("- disable173")
							CreateConsoleMsg("- enable173")
							CreateConsoleMsg("- disable106")
							CreateConsoleMsg("- enable106")
							CreateConsoleMsg("- disable049")
							CreateConsoleMsg("- enable049")
							CreateConsoleMsg("- disable966")
							CreateConsoleMsg("- enable966")
							CreateConsoleMsg("- doorcontrol") 
							CreateConsoleMsg("- unlockcheckpoints") 
							CreateConsoleMsg("- unlockexits")
							CreateConsoleMsg("- disablenuke")
							CreateConsoleMsg("- resetfunds")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Use " + Chr(34) + "help 3 / 3" + Chr(34) + " to find more commands.")
							CreateConsoleMsg("Use " + Chr(34) + "help [command name]" + Chr(34) + " to get more information about a command.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "3"
							;[Block]
							CreateConsoleMsg("LIST OF COMMANDS - PAGE 3 / 3")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("- playmusic [clip + .wav / .ogg]")
							CreateConsoleMsg("- camerafog [near] [far]")
							CreateConsoleMsg("- spawn [npc type] [state]")
							CreateConsoleMsg("- injure [value]")
							CreateConsoleMsg("- infect [value]")
							CreateConsoleMsg("- crystal [value]") 
							CreateConsoleMsg("- teleport [room name]")
							CreateConsoleMsg("- spawnitem [item name]")
							CreateConsoleMsg("- codes")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Use " + Chr(34) + "help [command name]" + Chr(34) + " to get more information about a command.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "camerafog", "cf"
							;[Block]
							CreateConsoleMsg("HELP - camerafog")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Sets the draw distance of the fog.")
							CreateConsoleMsg("The fog begins generating at 'CameraFogNear' units")
							CreateConsoleMsg("away from the camera and becomes completely opaque")
							CreateConsoleMsg("at 'CameraFogFar' units away from the camera.")
							CreateConsoleMsg("Example: camerafog 20 40")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "noclip", "fly", "nc"
							;[Block]
							CreateConsoleMsg("HELP - noclip")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Toggles NoClip, unless a valid parameter")
							CreateConsoleMsg("is specified (on / off).")
							CreateConsoleMsg("Allows the camera to move in any direction while")
							CreateConsoleMsg("by passing collision.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "noblink", "nb"
							;[Block]
							CreateConsoleMsg("HELP - noblink")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Toggles NoBlink, unless a valid parameter")
							CreateConsoleMsg("is specified (on / off).")
							CreateConsoleMsg("Removes player's blinking.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "godmode", "god", "gm"
							;[Block]
							CreateConsoleMsg("HELP - godmode")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Toggles GodMode, unless a valid parameter")
							CreateConsoleMsg("is specified (on / off).")
							CreateConsoleMsg("Prevents player death under normal circumstances.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "infinitestamina", "is"
							;[Block]
							CreateConsoleMsg("HELP - infinitestamina")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Toggles InfiniteStamina, unless a valid parameter")
							CreateConsoleMsg("is specified (on / off).")
							CreateConsoleMsg("Increases player's stamina to infinite value.")
							CreateConsoleMsg("******************************")
						Case "notarget", "nt"
							;[Block]
							CreateConsoleMsg("HELP - notarget")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Toggles NoTarget, unless a valid parameter")
							CreateConsoleMsg("is specified (on / off).")
							CreateConsoleMsg("Makes player to be invisible for NPCs.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "wireframe", "wf"
							;[Block]
							CreateConsoleMsg("HELP - wireframe")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Toggles wireframe, unless a valid parameter")
							CreateConsoleMsg("is specified (on / off).")
							CreateConsoleMsg("Allows only the edges of geometry to be rendered,")
							CreateConsoleMsg("making everything else transparent.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "spawnitem", "si", "giveitem", "give"
							;[Block]
							CreateConsoleMsg("HELP - spawnitem")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Spawns an item at the player's location.")
							CreateConsoleMsg("Any name that can appear in your inventory")
							CreateConsoleMsg("is a valid parameter.")
							CreateConsoleMsg("Example: spawnitem Key Card Omni")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "spawn", "s"
							;[Block]
							CreateConsoleMsg("HELP - spawn")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Spawns an NPC at the player's location.")
							CreateConsoleMsg("Valid parameters are:")
							CreateConsoleMsg("008-1 / 049 / 049-2 / 066 / 096 / 106 / 173 / 860")
							CreateConsoleMsg("/ 372 / 513-1 / 966 / 1499-1 / class-d / 939")
							CreateConsoleMsg("/ guard / mtf / apache / tentacle / 1048_a/ 1048")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "reset372", "r372"
							;[Block]
							CreateConsoleMsg("HELP - reset372")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Returns SCP-372 to inactive state.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "106retreat"
							;[Block]
							CreateConsoleMsg("HELP - 106retreat")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Returns SCP-106 to inactive state.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "disable106", "dis106"
							;[Block]
							CreateConsoleMsg("HELP - disable106")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Removes SCP-106 from the map.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "enable106", "en106"
							;[Block]
							CreateConsoleMsg("HELP - enable106")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Returns SCP-106 to the map.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "disable173", "dis173"
							;[Block]
							CreateConsoleMsg("HELP - disable173")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Removes SCP-173 from the map.")
							CreateConsoleMsg("******************************")	
							;[End Block]
						Case "enable173", "en173"
							;[Block]
							CreateConsoleMsg("HELP - enable173")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Returns SCP-173 to the map.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "reset096", "r096"
							;[Block]
							CreateConsoleMsg("HELP - reset096")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Returns SCP-096 to idle state.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "doorcontrol"
							;[Block]
							CreateConsoleMsg("HELP - enablecontrol")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Turns on/off the Remote Door Control lever.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "asd"
							;[Block]
							CreateConsoleMsg("HELP - asd")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Activates all cheats.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "unlockcheckpoints" 
							;[Block]
							CreateConsoleMsg("HELP - unlockcheckpoints")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Unlocks all checkpoints.")
							CreateConsoleMsg("******************************")	
							;[End Block]
						Case "disable049", "dis049"
							;[Block]
							CreateConsoleMsg("HELP - disable049")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Removes SCP-049 from the map.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "enable049", "en049"
							;[Block]
							CreateConsoleMsg("HELP - enable049")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Returns SCP-049 to the map.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "disable966", "dis966"
							;[Block]
							CreateConsoleMsg("HELP - disable966")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Removes SCP-966 from the map.")
							CreateConsoleMsg("******************************")	
							;[End Block]
						Case "enable966", "en966"
							;[Block]
							CreateConsoleMsg("HELP - enable966")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Returns SCP-966 to the map.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "revive", "undead", "resurrect"
							;[Block]
							CreateConsoleMsg("HELP - revive")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Resets the player's death timer after the dying")
							CreateConsoleMsg("animation triggers.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "teleport", "tp", "goto"
							;[Block]
							CreateConsoleMsg("HELP - teleport")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Teleports the player to the first instance")
							CreateConsoleMsg("of the specified room. Any room that appears")
							CreateConsoleMsg("in rooms.ini is a valid parameter.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "stopsound", "stfu"
							;[Block]
							CreateConsoleMsg("HELP - stopsound")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Stops all currently playing sounds.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "weed", "scp-420-j", "420j", "scp420-j", "scp-420j", "420"
							;[Block]
							CreateConsoleMsg("HELP - weed")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Generates dank memes.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "infect"
							;[Block]
							CreateConsoleMsg("HELP - infect")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("SCP-008 infects player.")
							CreateConsoleMsg("Example: infect 80")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "crystal" 
							;[Block]
							CreateConsoleMsg("HELP - crystal")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("SCP-409 crystallizes player.")
							CreateConsoleMsg("Example: crystal 52")
							CreateConsoleMsg("******************************")
							;[End Block]
						Case "resetfunds"
							;[Block]
							CreateConsoleMsg("HELP - resetfunds")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Resets your Mastercard funds.")
							CreateConsoleMsg("******************************")
						Case "codes" 
							;[Block]
							CreateConsoleMsg("HELP - codes")
							CreateConsoleMsg("******************************")
							CreateConsoleMsg("Displays access codes for this save.")
							CreateConsoleMsg("******************************")
							;[End Block]
						Default
							;[Block]
							CreateConsoleMsg("There is no help available for that command.", 255, 150, 0)
							;[End Block]
					End Select
					;[End Block]
				Case "ending"
					;[Block]
					StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					
					Select StrTemp
						Case "A"
							;[Block]
							me\SelectedEnding = Rand(Ending_A1, Ending_A2)
							;[End Block]
						Case "B"
							;[Block]
							me\SelectedEnding = Rand(Ending_B1, Ending_B2)
							;[End Block]
						Default
							;[Block]
							me\SelectedEnding = Rand(Ending_A1, Ending_B2)
							;[End Block]
					End Select
					
					me\Terminated = True
					;[End Block]
				Case "noclipspeed"
					;[Block]
					StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					
					chs\NoClipSpeed = Float(StrTemp)
					;[End Block]
				Case "injure", "damage"
					;[Block]
					StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					
					me\Injuries = Float(StrTemp)
					CreateConsoleMsg("Player's injuries set to " + Float(StrTemp))
					;[End Block]
				Case "cls", "clear"
					;[Block]
					ClearConsole()
					;[End Block]
				Case "infect"
					;[Block]
					StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					
					I_008\Timer = Float(StrTemp)
					CreateConsoleMsg("SCP-008 infection stage set to " + Float(StrTemp))
					;[End Block]
				Case "crystal"
					;[Block]
					StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					
					I_409\Timer = Float(StrTemp)
					CreateConsoleMsg("SCP-409 crystallization stage set to " + Float(StrTemp))
					;[End Block]
				Case "heal"
					;[Block]
					ResetNegativeStats()
					CreateConsoleMsg("Healed the player")
					;[End Block]
				Case "teleport", "tp", "goto"
					;[Block]
					StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					
					For r.Rooms = Each Rooms
						If r\RoomTemplate\Name = StrTemp Then
							TeleportEntity(me\Collider, EntityX(r\OBJ), EntityY(r\OBJ) + 0.7, EntityZ(r\OBJ))
							TeleportToRoom(r)
							CreateConsoleMsg("Successfully teleported to: " + StrTemp + ".")
							Exit
						EndIf
					Next
					
					If PlayerRoom\RoomTemplate\Name <> StrTemp Then CreateConsoleMsg("Room not found.", 255, 0, 0)
					;[End Block]
				Case "spawnitem", "si", "giveitem", "give"
					;[Block]
					StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					Temp = False 
					For itt.ItemTemplates = Each ItemTemplates
						If Lower(itt\Name) = StrTemp Then
							Temp = True
							CreateConsoleMsg(itt\Name + " spawned.")
							it.Items = CreateItem(itt\Name, itt\TempName, EntityX(me\Collider), EntityY(Camera, True), EntityZ(me\Collider))
							EntityType(it\Collider, HIT_ITEM)
							Exit
						ElseIf Lower(itt\TempName) = StrTemp
							Temp = True
							CreateConsoleMsg(itt\Name + " spawned.")
							it.Items = CreateItem(itt\Name, itt\TempName, EntityX(me\Collider), EntityY(Camera, True), EntityZ(me\Collider))
							EntityType(it\Collider, HIT_ITEM)
							Exit
						EndIf
					Next
					
					If (Not Temp) Then CreateConsoleMsg("Item not found.", 255, 0, 0)
					;[End Block]
				Case "wireframe", "wf"
					;[Block]
					StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					
					Select StrTemp
						Case "on", "1", "true"
							;[Block]
							WireFrameState = True
							;[End Block]
						Case "off", "0", "false"
							;[Block]
							WireFrameState = False
							;[End Block]
						Default
							;[Block]
							WireFrameState = (Not WireFrameState)
							;[End Block]
					End Select
					
					If WireFrameState Then
						CreateConsoleMsg("WIREFRAME ON")
					Else
						CreateConsoleMsg("WIREFRAME OFF")	
					EndIf
					
					WireFrame(WireFrameState)
					;[End Block]
				Case "reset096", "r096"
					;[Block]
					For n.NPCs = Each NPCs
						If n\NPCType = NPCType096 Then
							n\State = 0.0
							StopStream_Strict(n\SoundCHN) : n\SoundCHN = 0 : n\SoundCHN_IsStream = False
							If n\SoundCHN2 <> 0 Then StopStream_Strict(n\SoundCHN2) : n\SoundCHN2 = 0 : n\SoundCHN2_IsStream = False
							Exit
						EndIf
					Next
					
					CreateConsoleMsg("SCP-096 has been reset.")
					;[End Block]
				Case "reset372", "r372"
					;[Block]				
					For n.NPCs = Each NPCs
						If n\NPCType = NPCType372 Then
							RemoveNPC(n)
							CreateEvent("cont1_372", "cont1_372", 0, 0.0)   
							Exit
						EndIf
					Next
					
					CreateConsoleMsg("SCP-372 has been reset.")
					;[End Block]
				Case "disable173", "dis173"
					;[Block]
					n_I\Curr173\Idle = 3 ; ~ This phenominal comment is brought to you by PolyFox. His absolute wisdom in this fatigue of knowledge brought about a new era of SCP-173 state checks.
					HideEntity(n_I\Curr173\OBJ)
					HideEntity(n_I\Curr173\OBJ2)
					HideEntity(n_I\Curr173\Collider)
					CreateConsoleMsg("SCP-173 disabled.")	
					;[End Block]
				Case "enable173", "en173"
					;[Block]
					n_I\Curr173\Idle = 0
					ShowEntity(n_I\Curr173\OBJ)
					ShowEntity(n_I\Curr173\OBJ2)
					ShowEntity(n_I\Curr173\Collider)
					CreateConsoleMsg("SCP-173 enabled.")
					;[End Block]
				Case "disable106", "dis106"
					;[Block]
					n_I\Curr106\Idle = 1
					n_I\Curr106\State = 100000.0
					n_I\Curr106\Contained = True
					HideEntity(n_I\Curr106\Collider)
					HideEntity(n_I\Curr106\OBJ)
					CreateConsoleMsg("SCP-106 disabled.")
					;[End Block]
				Case "enable106", "en106"
					;[Block]
					n_I\Curr106\Idle = 0
					n_I\Curr106\Contained = False
					ShowEntity(n_I\Curr106\Collider)
					ShowEntity(n_I\Curr106\OBJ)
					CreateConsoleMsg("SCP-106 enabled.")
					;[End Block]
				Case "disable966", "dis966"
					;[Block]
					For n.NPCs = Each NPCs
						If n\NPCType = NPCType966 Then
							n\State = -1.0
							HideEntity(n\Collider)
							HideEntity(n\OBJ)
							Exit
						EndIf
					Next
					;[End Block]
				Case "enable966", "en966"
					;[Block]
					For n.NPCs = Each NPCs
						If n\NPCType = NPCType966 Then
							n\State = 0.0
							ShowEntity(n\Collider)
							If wi\NightVision > 0 Then ShowEntity(n\OBJ)
							Exit
						EndIf
					Next
					;[End Block]
				Case "disable049", "dis049"
					;[Block]
					If n_I\Curr049 <> Null Then
						n_I\Curr049\Idle = 1
						HideEntity(n_I\Curr049\Collider)
						HideEntity(n_I\Curr049\OBJ)
						CreateConsoleMsg("SCP-049 disabled.")
					EndIf
					;[End Block]
				Case "enable049", "en049"
					;[Block]
					If n_I\Curr049 <> Null Then
						n_I\Curr049\Idle = 0
						ShowEntity(n_I\Curr049\Collider)
						ShowEntity(n_I\Curr049\OBJ)
						CreateConsoleMsg("SCP-049 enabled.")
					EndIf
					;[End Block]
				Case "106retreat"
					;[Block]
					If n_I\Curr106\State <= 0.0 Then
					n_I\Curr106\State = Rnd(22000.0, 27000.0)
					PositionEntity(n_I\Curr106\Collider, 0.0, 500.0, 0.0)
					ResetEntity(n_I\Curr106\Collider)
					CreateConsoleMsg("SCP-106 has retreated.")
					Else
						CreateConsoleMsg("SCP-106 is currently not active, so it cannot retreat.", 255, 150, 0)
					EndIf
					;[End Block]
				Case "halloween"
					;[Block]
					n_I\IsHalloween = (Not n_I\IsHalloween)
					If n_I\IsHalloween Then
						Tex = LoadTexture_Strict("GFX\npcs\scps\scp_173_H.png", 1)
						If opt\Atmosphere Then TextureBlend(Tex, 5)
						EntityTexture(n_I\Curr173\OBJ, Tex)
						EntityTexture(n_I\Curr173\OBJ2, Tex)
						DeleteSingleTextureEntryFromCache(Tex)
						CreateConsoleMsg("173 JACK-O-LANTERN ON")
					Else
						If n_I\IsNewYear Then n_I\IsNewYear = (Not n_I\IsNewYear)
						Tex2 = LoadTexture_Strict("GFX\npcs\scps\scp_173.png", 1)
						If opt\Atmosphere Then TextureBlend(Tex2, 5)
						EntityTexture(n_I\Curr173\OBJ, Tex2)
						EntityTexture(n_I\Curr173\OBJ2, Tex2)
						DeleteSingleTextureEntryFromCache(Tex2)
						CreateConsoleMsg("173 JACK-O-LANTERN OFF")
					EndIf
					;[End Block]
				Case "newyear" 
					;[Block]
					n_I\IsNewYear = (Not n_I\IsNewYear)
					If n_I\IsNewYear Then
						Tex = LoadTexture_Strict("GFX\npcs\scps\scp_173_NY.png", 1)
						If opt\Atmosphere Then TextureBlend(Tex, 5)
						EntityTexture(n_I\Curr173\OBJ, Tex)
						EntityTexture(n_I\Curr173\OBJ2, Tex)
						DeleteSingleTextureEntryFromCache(Tex)
						CreateConsoleMsg("173 COOKIE ON")
					Else
						If n_I\IsHalloween Then n_I\IsHalloween = (Not n_I\IsHalloween)
						Tex2 = LoadTexture_Strict("GFX\npcs\scps\scp_173.png", 1)
						If opt\Atmosphere Then TextureBlend(Tex2, 5)
						EntityTexture(n_I\Curr173\OBJ, Tex2)
						EntityTexture(n_I\Curr173\OBJ2, Tex2)
						DeleteSingleTextureEntryFromCache(Tex2)
						CreateConsoleMsg("173 COOKIE OFF")
					EndIf
					;[End Block]
					Case "joke" 
					;[Block]
					n_I\IsAprilFools = (Not n_I\IsAprilFools)
					If n_I\IsAprilFools Then
						Tex = LoadTexture_Strict("GFX\npcs\scps\scp_173_J.png", 1)
						If opt\Atmosphere Then TextureBlend(Tex, 5)
						EntityTexture(n_I\Curr173\OBJ, Tex)
						EntityTexture(n_I\Curr173\OBJ2, Tex)
						DeleteSingleTextureEntryFromCache(Tex)
						CreateConsoleMsg("173-J ON")
					Else
						If n_I\IsHalloween Then n_I\IsHalloween = (Not n_I\IsHalloween)
						Tex2 = LoadTexture_Strict("GFX\npcs\scps\scp_173.png", 1)
						If opt\Atmosphere Then TextureBlend(Tex2, 5)
						EntityTexture(n_I\Curr173\OBJ, Tex2)
						EntityTexture(n_I\Curr173\OBJ2, Tex2)
						DeleteSingleTextureEntryFromCache(Tex2)
						CreateConsoleMsg("173-J OFF")
					EndIf
					;[End Block]
				Case "sanic", "superman"
					;[Block]
					chs\SuperMan = (Not chs\SuperMan)
					If chs\SuperMan = True Then
						CreateConsoleMsg("GOTTA GO FAST")
					Else
						CreateConsoleMsg("WHOA SLOW DOWN")
					EndIf
					;[End Block]
				Case "scp-420-j", "420", "weed", "scp420-j", "scp-420j", "420j"
					;[Block]
					For i = 1 To 20
						If Rand(2) = 1 Then
							it.Items = CreateItem("Some SCP-420-J", "scp420j", EntityX(me\Collider, True) + Cos((360.0 / 20.0) * i) * Rnd(0.3, 0.5), EntityY(Camera, True), EntityZ(me\Collider, True) + Sin((360.0 / 20.0) * i) * Rnd(0.3, 0.5))
						Else
							it.Items = CreateItem("Joint", "joint", EntityX(me\Collider, True) + Cos((360.0 / 20.0) * i) * Rnd(0.3, 0.5), EntityY(Camera, True), EntityZ(me\Collider, True) + Sin((360.0 / 20.0) * i) * Rnd(0.3, 0.5))
						EndIf
						EntityType(it\Collider, HIT_ITEM)
					Next
					PlaySound_Strict(LoadTempSound("SFX\Music\Using420J.ogg"))
					;[End Block]
				Case "godmode", "god", "gm"
					;[Block]
					StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					
					Select StrTemp
						Case "on", "1", "true"
							;[Block]
							chs\GodMode = True		
							;[End Block]
						Case "off", "0", "false"
							;[Block]
							chs\GodMode = False
							;[End Block]
						Default
							;[Block]
							chs\GodMode = (Not chs\GodMode)
							;[End Block]
					End Select	
					If chs\GodMode Then
						CreateConsoleMsg("GODMODE ON")
					Else
						CreateConsoleMsg("GODMODE OFF")	
					EndIf
					;[End Block]
				Case "revive", "undead", "resurrect"
					;[Block]
					ResetNegativeStats(True)
					;[End Block]
				Case "noclip", "fly", "nc"
					;[Block]
					StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					
					Select StrTemp
						Case "on", "1", "true"
							;[Block]
							chs\NoClip = True
							me\Playable = True
							;[End Block]
						Case "off", "0", "false"
							;[Block]
							chs\NoClip = False	
							RotateEntity(me\Collider, 0.0, EntityYaw(me\Collider), 0.0)
							;[End Block]
						Default
							;[Block]
							chs\NoClip = (Not chs\NoClip)
							If (Not chs\NoClip) Then		
								RotateEntity(me\Collider, 0.0, EntityYaw(me\Collider), 0.0)
							Else
								me\Playable = True
							EndIf
							;[End Block]
					End Select
					
					If chs\NoClip Then
						CreateConsoleMsg("NOCLIP ON")
					Else
						CreateConsoleMsg("NOCLIP OFF")
					EndIf
					
					me\DropSpeed = 0.0
					;[End Block]
				Case "noblink", "nb"
					;[Block]
					StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					
					Select StrTemp
						Case "on", "1", "true"
							;[Block]
							chs\NoBlink = True
							;[End Block]
						Case "off", "0", "false"
							;[Block]
							chs\NoBlink = False
							;[End Block]
						Default
							;[Block]
							chs\NoBlink = (Not chs\NoBlink)
							;[End Block]
					End Select	
					If chs\NoBlink Then
						CreateConsoleMsg("NOBLINK ON")
					Else
						CreateConsoleMsg("NOBLINK OFF")	
					EndIf
					;[End Block]
				Case "debughud", "dbh"
					;[Block]
					If Instr(ConsoleInput, " ") <> 0 Then
						StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					Else
						StrTemp = ""
					EndIf
					
					Select StrTemp
						Case "game", "1"
							;[Block]
							chs\DebugHUD = 1
							;[End Block]
						Case "player", "me", "2"
							;[Block]
							chs\DebugHUD = 2
							;[End Block]
						Case "scps", "scp", "3"
							;[Block]
							chs\DebugHUD = 3
							;[End Block]
						Case "off", "false", "0"
							;[Block]
							chs\DebugHUD = 0
							;[End Block]
						Default
							;[Block]
							CreateConsoleMsg("First, select the debug category.", 255, 150, 0)
							;[End Block]
					End Select
					;[End Block]
				Case "stopsound", "stfu", "silence", "quiet"
					;[Block]
					KillSounds()
					
					For e.Events = Each Events
						If e\EventID = e_cont1_173 Then
							For i = 0 To 2
								If e\room\NPC[i] <> Null Then RemoveNPC(e\room\NPC[i])
								If i < 2 Then FreeEntity(e\room\Objects[i]) : e\room\Objects[i] = 0
							Next
							If n_I\Curr173\Idle = 1 Then n_I\Curr173\Idle = 0
							PositionEntity(n_I\Curr173\Collider, 0.0, 0.0, 0.0)
							ResetEntity(n_I\Curr173\Collider)
							RemoveEvent(e)
							Exit
						EndIf
					Next
					CreateConsoleMsg("Stopped all sounds.")
					;[End Block]
				Case "camerafog", "cf"
					;[Block]
					Args = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					opt\CameraFogNear = Float(Left(Args, Len(Args) - Instr(Args, " ")))
					opt\CameraFogFar = Float(Right(Args, Len(Args) - Instr(Args, " ")))
					CreateConsoleMsg("Near set to: " + opt\CameraFogNear + ", far set to: " + opt\CameraFogFar)
					;[End Block]
				Case "spawn", "s"
					;[Block]
					Args = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					StrTemp = Piece(Args, 1)
					StrTemp2 = Piece(Args, 2)
					
					; ~ Hacky fix for when the user doesn't input a second parameter.
					If StrTemp <> StrTemp2 Then
						ConsoleSpawnNPC(StrTemp, StrTemp2)
					Else
						ConsoleSpawnNPC(StrTemp)
					EndIf
					;[End Block]
				Case "infinitestamina", "infstam", "is"
					;[Block]
					StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					
					Select StrTemp
						Case "on", "1", "true"
							;[Block]
							chs\InfiniteStamina = True
							;[End Block]
						Case "off", "0", "false"
							;[Block]
							chs\InfiniteStamina = False
							;[End Block]
						Default
							;[Block]
							chs\InfiniteStamina = (Not chs\InfiniteStamina)
							;[End Block]
					End Select
					
					If chs\InfiniteStamina
						CreateConsoleMsg("INFINITE STAMINA ON")
					Else
						CreateConsoleMsg("INFINITE STAMINA OFF")	
					EndIf
					;[End Block]
				Case "money", "rich", "cash"
					;[Block]
					For i = 1 To 20
						If Rand(2) = 1 Then
							it.Items = CreateItem("Quarter", "25ct", EntityX(me\Collider, True) + Cos((360.0 / 20.0) * i) * Rnd(0.3, 0.5), EntityY(Camera, True), EntityZ(me\Collider, True) + Sin((360.0 / 20.0) * i) * Rnd(0.3, 0.5))
						Else
							it.Items = CreateItem("Coin", "coin", EntityX(me\Collider, True) + Cos((360.0 / 20.0) * i) * Rnd(0.3, 0.5), EntityY(Camera, True), EntityZ(me\Collider, True) + Sin((360.0 / 20.0) * i) * Rnd(0.3, 0.5))
						EndIf
						EntityType(it\Collider, HIT_ITEM)
					Next
					;[End Block]
				Case "doorcontrol"
					;[Block]
					StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					
					Select StrTemp
						Case "on", "1", "true"
							;[Block]
							RemoteDoorOn = True
							;[End Block]
						Case "off", "0", "false"
							;[Block]
							RemoteDoorOn = False
							;[End Block]
						Default
							;[Block]
							RemoteDoorOn = (Not RemoteDoorOn)
							;[End Block]
					End Select
					
					If RemoteDoorOn Then
						CreateConsoleMsg("REMOTE DOOR CONTROL ENABLED")
					Else
						CreateConsoleMsg("REMOTE DOOR CONTROL DISABLED")
					EndIf
					
					For e2.Events = Each Events
						If e2\EventID = e_room2c_ec Then
							UpdateLever(e2\room\Objects[5])
							RotateEntity(e2\room\Objects[5], 0.0, EntityYaw(e2\room\Objects[5]), RemoteDoorOn * 30.0)
							Exit
						EndIf
					Next
					;[End Block]
				Case "unlockcheckpoints"
					;[Block]
					For e2.Events = Each Events
						If e2\EventID = e_room2_sl Then
							e2\EventState3 = 0.0
							UpdateLever(e2\room\Levers[0])
							RotateEntity(e2\room\Levers[0], 0.0, EntityYaw(e2\room\Levers[0]), 0.0)
							TurnCheckpointMonitorsOff()
						ElseIf e2\EventID = e_cont2_008
							e2\EventState = 2.0
							UpdateLever(e2\room\Levers[0])
							RotateEntity(e2\room\Levers[0], 0.0, EntityYaw(e2\room\Levers[0]), 30.0)
							TurnCheckpointMonitorsOff(False)
						EndIf
					Next
					
					CreateConsoleMsg("Checkpoints are now unlocked.")								
					;[End Block]
				Case "disablenuke"
					;[Block]
					For e2.Events = Each Events
						If e2\EventID = e_room2_nuke
							e2\EventState = 0.0
							UpdateLever(e2\room\Objects[1])
							UpdateLever(e2\room\Objects[3])
							RotateEntity(e2\room\Objects[1], 0.0, EntityYaw(e2\room\Objects[1]), 0.0)
							RotateEntity(e2\room\Objects[3], 0.0, EntityYaw(e2\room\Objects[3]), 0.0)
							Exit
						EndIf
					Next
					
					CreateConsoleMsg("Remote Alpha Warheads disabled.")
					;[End Block]
				Case "unlockexits"
					;[Block]
					StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					
					Select StrTemp
						Case "a"
							;[Block]
							For e.Events = Each Events
								If e\EventID = e_gate_a_entrance Then
									e\EventState3 = 1.0
									e\room\RoomDoors[1]\Open = True
									Exit
								EndIf
							Next
							CreateConsoleMsg("Gate A is now unlocked.")	
							;[End Block]
						Case "b"
							;[Block]
							For e.Events = Each Events
								If e\EventID = e_gate_b_entrance Then
									e\EventState3 = 1.0
									e\room\RoomDoors[1]\Open = True
									Exit
								EndIf
							Next	
							CreateConsoleMsg("Gate B is now unlocked.")	
							;[End Block]
						Default
							;[Block]
							For e.Events = Each Events
								If e\EventID = e_gate_b_entrance Lor e\EventID = e_gate_a_entrance Then
									e\EventState3 = 1.0
									e\room\RoomDoors[1]\Open = True
								EndIf
							Next
							CreateConsoleMsg("Gate A and B are now unlocked.")	
							;[End Block]
					End Select
					RemoteDoorOn = True
					;[End Block]
				Case "kill", "suicide"
					;[Block]
					me\Terminated = True
					Select Rand(4)
						Case 1
							;[Block]
							msg\DeathMsg = "[DATA REDACTED]"
							;[End Block]
						Case 2
							;[Block]
							msg\DeathMsg = SubjectName + " found dead in Sector [DATA REDACTED]. "
							msg\DeathMsg = msg\DeathMsg + "The subject appears to have attained no physical damage, and there is no visible indication as to what killed him. "
							msg\DeathMsg = msg\DeathMsg + "Body was sent for autopsy."
							;[End Block]
						Case 3
							;[Block]
							msg\DeathMsg = "EXCP_ACCESS_VIOLATION"
							;[End Block]
						Case 4
							;[Block]
							msg\DeathMsg = SubjectName + " found dead in Sector [DATA REDACTED]. "
							msg\DeathMsg = msg\DeathMsg + "The subject appears to have scribbled the letters " + Chr(34) + "kys" + Chr(34) + " in his own blood beside him. "
							msg\DeathMsg = msg\DeathMsg + "No other signs of physical trauma or struggle can be observed. Body was sent for autopsy."
							;[End Block]
					End Select
					;[End Block]
				Case "warp"
					;[Block]
					Args = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					StrTemp = Piece(Args, 1, " ")
					StrTemp2 = Piece(Args, 2, " ")
					StrTemp3 = Piece(Args, 3, " ")
					PositionEntity(me\Collider, Float(StrTemp), Float(StrTemp2), Float(StrTemp3))
					PositionEntity(Camera, Float(StrTemp), Float(StrTemp2), Float(StrTemp3))
					ResetEntity(me\Collider)
					ResetEntity(Camera)
					CreateConsoleMsg("Warped to coordinates (X|Y|Z): " + EntityX(me\Collider) + "|" + EntityY(me\Collider) + "|" + EntityZ(me\Collider))
					;[End Block]
				Case "asd"
					;[Block]
					chs\NoBlink = True
					chs\NoTarget = True
					chs\NoClip = True
					chs\GodMode = True
					chs\InfiniteStamina = True
					
					opt\CameraFogFar = 50.0
					
					KillSounds()
					
					For e.Events = Each Events
						If e\EventID = e_cont1_173 Then
							For i = 0 To 2
								If e\room\NPC[i] <> Null Then RemoveNPC(e\room\NPC[i])
								If i < 2 Then FreeEntity(e\room\Objects[i]) : e\room\Objects[i] = 0
							Next
							If n_I\Curr173\Idle = 1 Then n_I\Curr173\Idle = 0
							PositionEntity(n_I\Curr173\Collider, 0.0, 0.0, 0.0)
							ResetEntity(n_I\Curr173\Collider)
							RemoveEvent(e)
							Exit
						EndIf
					Next
					CreateConsoleMsg("Stopped all sounds.")
					;[End Block]
				Case "notarget", "nt"
					;[Block]
					StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					
					Select StrTemp
						Case "on", "1", "true"
							;[Block]
							chs\NoTarget = True	
							;[End Block]
						Case "off", "0", "false"
							;[Block]
							chs\NoTarget = False
							;[End Block]
						Default
							;[Block]
							chs\NoTarget = (Not chs\NoTarget)
							;[End Block]
					End Select
					
					If (Not chs\NoTarget) Then
						CreateConsoleMsg("NOTARGET OFF")
					Else
						CreateConsoleMsg("NOTARGET ON")	
					EndIf
					;[End Block]
				Case "spawnpumpkin", "pumpkin"
					;[Block]
					CreateConsoleMsg("What pumpkin?")
					;[End Block]
				Case "teleport173"
					;[Block]
					PositionEntity(n_I\Curr173\Collider, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
					ResetEntity(n_I\Curr173\Collider)
					;[End Block]
				Case "seteventstate"
					;[Block]
					Args = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					StrTemp = Piece(Args, 1, " ")
					StrTemp2 = Piece(Args, 2, " ")
					StrTemp3 = Piece(Args, 3, " ")
					StrTemp4 = Piece(Args, 4, " ")
					
					Local PL_Room_Found% = False
					
					If StrTemp = "" Lor StrTemp2 = "" Lor StrTemp3 = "" Lor StrTemp4 = "" Then
						CreateConsoleMsg("Too few parameters. This command requires 4.", 255, 150, 0)
					Else
						For e.Events = Each Events
							If PlayerRoom = e\room
								If Lower(StrTemp) <> "keep" Then e\EventState = Float(StrTemp)
								
								If Lower(StrTemp2) <> "keep" Then e\EventState2 = Float(StrTemp2)
								
								If Lower(StrTemp3) <> "keep" Then e\EventState3 = Float(StrTemp3)
								
								If Lower(StrTemp4) <> "keep" Then e\EventState4 = Float(StrTemp4)
								CreateConsoleMsg("Changed event states from current player room to: " + e\EventState + "|" + e\EventState2 + "|" + e\EventState3 + "|" + e\EventState4)
								PL_Room_Found = True
								Exit
							EndIf
						Next
						If (Not PL_Room_Found) Then
							CreateConsoleMsg("The current room doesn't has any event applied.", 255, 150, 0)
						EndIf
					EndIf
					;[End Block]
				Case "427state"
					;[Block]
					StrTemp = Lower(Right(ConsoleInput, Len(ConsoleInput) - Instr(ConsoleInput, " ")))
					
					I_427\Timer = 70.0 * Float(StrTemp)
					;[End Block]
				Case "teleport106"
					;[Block]
					n_I\Curr106\State = 0.0
					n_I\Curr106\Idle = 0
					;[End Block]
				Case "jorge"
					;[Block]	
					CreateConsoleMsg(Chr(74) + Chr(79) + Chr(82) + Chr(71) + Chr(69) + Chr(32) + Chr(72) + Chr(65) + Chr(83) + Chr(32) + Chr(66) + Chr(69) + Chr(69) + Chr(78) + Chr(32) + Chr(69) + Chr(88) + Chr(80) + Chr(69) + Chr(67) + Chr(84) + Chr(73) + Chr(78) + Chr(71) + Chr(32) + Chr(89) + Chr(79) + Chr(85) + Chr(46))
					;[End Block]
				Case "resetfunds"
					;[Block]
					me\Funds = Rand(4)
					CreateConsoleMsg("Funds have reset.")
					;[End Block]
				Case "codes"
					;[Block]
					Temp = ((Int(AccessCode) * 3) Mod 10000)
					If Temp < 1000 Then Temp = Temp + 1000
					Temp1 = ((Int(AccessCode) * 2) Mod 10000)
					If Temp1 < 1000 Then Temp1 = Temp1 + 1000
					
					CreateConsoleMsg("Access Codes:")
					CreateConsoleMsg("")
					CreateConsoleMsg("Dr. Maynard: " + AccessCode)
					CreateConsoleMsg("Dr. Harp: 7816")
					;CreateConsoleMsg("Dr Gears: 1311") ~ Removed since Gears office is locked
					CreateConsoleMsg("Dr. L.: 1234")
					CreateConsoleMsg("O5 Council Office: 2411")
					CreateConsoleMsg("Maintenance Tunnel: " + Temp)
					CreateConsoleMsg("Site Director: " + Temp1)
					CreateConsoleMsg(Chr(34) + "cont1_035" + Chr(34) + " storage room: 5731")
					CreateConsoleMsg("")
					CreateConsoleMsg("All the others doors don't have a code.")
					;[End Block]
				Default
					;[Block]
					CreateConsoleMsg("Command not found.", 255, 0, 0)
					;[End Block]
			End Select
			ConsoleInput = ""
		EndIf
		
		Local Count% = 0
		
		For cm.ConsoleMsg = Each ConsoleMsg
			Count = Count + 1
			If Count > 1000 Then Delete(cm)
		Next
	EndIf
	
	SetFont(fo\FontID[Font_Default])
End Function

Function RenderConsole%()
	If (Not opt\CanOpenConsole) Then Return
		
	If ConsoleOpen Then
		Local cm.ConsoleMsg
		Local InBar%, InBox%
		Local x%, y%, Width%, Height%
		
		SetFont(fo\FontID[Font_Console])
		
		x = 0
		y = opt\GraphicHeight - 300 * MenuScale
		Width = opt\GraphicWidth
		Height = 270 * MenuScale
		
		RenderFrame(x, y, Width, Height + (30 * MenuScale))
		
		Local ConsoleHeight% = 0
		Local ScrollBarHeight% = 0
		
		For cm.ConsoleMsg = Each ConsoleMsg
			ConsoleHeight = ConsoleHeight + (15 * MenuScale)
		Next
		ScrollBarHeight = (Float(Height) / Float(ConsoleHeight)) * Height
		If ScrollBarHeight > Height Then ScrollBarHeight = Height
		If ConsoleHeight < Height Then ConsoleHeight = Height
		
		Color(50, 50, 50)
		InBar = MouseOn(x + Width - (26 * MenuScale), y, 26 * MenuScale, Height)
		If InBar Then Color(70, 70, 70)
		Rect(x + Width - (26 * MenuScale), y, 26 * MenuScale, Height)
		
		Color(120, 120, 120)
		InBox = MouseOn(x + Width - (23 * MenuScale), y + Height - ScrollBarHeight + (ConsoleScroll * ScrollBarHeight / Height), 20 * MenuScale, ScrollBarHeight)
		If InBox Then Color(200, 200, 200)
		If ConsoleScrollDragging Then Color(255, 255, 255)
		Rect(x + Width - (23 * MenuScale), y + Height - ScrollBarHeight + (ConsoleScroll * ScrollBarHeight / Height), 20 * MenuScale, ScrollBarHeight)
		
		Color(255, 255, 255)
		
		Local TempY# = y + Height - (25.0 * MenuScale) - ConsoleScroll
		Local Count% = 0
		
		For cm.ConsoleMsg = Each ConsoleMsg
			Count = Count + 1
			If Count > 1000 Then
				Delete(cm)
			Else
				If TempY >= y And TempY < y + Height - (20 * MenuScale) Then
					If cm = ConsoleReissue Then
						Color(cm\R / 4, cm\G / 4, cm\B / 4)
						Rect(x, TempY - (2 * MenuScale), Width - (30 * MenuScale), 24 * MenuScale, True)
					EndIf
					Color(cm\R, cm\G, cm\B)
					If cm\IsCommand Then
						Text(x + (20 * MenuScale), TempY, "> " + cm\Txt)
					Else
						Text(x + (20 * MenuScale), TempY, cm\Txt)
					EndIf
				EndIf
				TempY = TempY - (15.0 * MenuScale)
			EndIf
		Next
		Color(255, 255, 255)
		
		RenderMenuInputBoxes()
		
		If opt\DisplayMode = 0 Then DrawImage(CursorIMG, ScaledMouseX(), ScaledMouseY())
	EndIf
	SetFont(fo\FontID[Font_Default])
End Function

Function RenderDebugHUD%()
	Local ev.Events, ch.Chunk
	Local x%, y%, i%
	
	x = 20 * MenuScale
	y = 40 * MenuScale
	
	Color(255, 255, 255)
	SetFont(fo\FontID[Font_Console])
	
	Select chs\DebugHUD
		Case 1
			;[Block]
			Text(x, y, "Room: " + PlayerRoom\RoomTemplate\Name)
			Text(x, y + (20 * MenuScale), "Room Coordinates: (" + Floor(EntityX(PlayerRoom\OBJ) / 8.0 + 0.5) + ", " + Floor(EntityZ(PlayerRoom\OBJ) / 8.0 + 0.5) + ", Angle: " + PlayerRoom\Angle + ")")
			For ev.Events = Each Events
				If ev\room = PlayerRoom Then
					Text(x, y + (40 * MenuScale), "Room Event: " + ev\EventName + ", ID: " + ev\EventID) 
					Text(x, y + (60 * MenuScale), "State: " + ev\EventState)
					Text(x, y + (80 * MenuScale), "State2: " + ev\EventState2)   
					Text(x, y + (100 * MenuScale), "State3: " + ev\EventState3)
					Text(x, y + (120 * MenuScale), "State4: " + ev\EventState4)
					Text(x, y + (140 * MenuScale), "Str: "+ ev\EventStr)
					Exit
				EndIf
			Next
			If PlayerRoom\RoomTemplate\Name = "dimension_1499" Then
				Text(x, y + (180 * MenuScale), "Current Chunk X / Z: (" + (Int((EntityX(me\Collider) + 20) / 40)) + ", "+(Int((EntityZ(me\Collider) + 20) / 40)) + ")")
				
				Local CH_Amount% = 0
				
				For ch.Chunk = Each Chunk
					CH_Amount = CH_Amount + 1
				Next
				Text(x, y + (200 * MenuScale), "Current Chunk Amount: " + CH_Amount)
			Else
				Text(x, y + (200 * MenuScale), "Current Room Position: (" + PlayerRoom\x + ", " + PlayerRoom\y + ", " + PlayerRoom\z + ")")
			EndIf
			
			If sc_I\SelectedMonitor <> Null Then
				Text(x, y + (240 * MenuScale), "Current Monitor: " + sc_I\SelectedMonitor\ScrOBJ)
			Else
				Text(x, y + (240 * MenuScale), "Current Monitor: Null")
			EndIf
			
			If SelectedItem <> Null Then
				Text(x, y + (280 * MenuScale), "Current Button: " + SelectedItem\ItemTemplate\Name)
			Else
				Text(x, y + (280 * MenuScale), "Current Button: Null")
			EndIf
			
			Text(x, y + (320 * MenuScale), "Current Floor: " + PlayerElevatorFloor)
			Text(x, y + (340 * MenuScale), "Room floor: " + ToElevatorFloor)
			If PlayerInsideElevator Then
				Text(x, y + (360 * MenuScale), "Player is inside elevator: True")
			Else
				Text(x, y + (360 * MenuScale), "Player is inside elevator: False")
			EndIf
			
			Text(x, y + (400 * MenuScale), "Date and Time: " + CurrentDate() + ", " + CurrentTime())
			Text(x, y + (420 * MenuScale), "Video memory: " + ((TotalVidMem() / 1024) - (AvailVidMem() / 1024)) + " MB/" + (TotalVidMem() / 1024) + " MB" + Chr(10))
			Text(x, y + (440 * MenuScale), "Global memory status: " + ((TotalPhys() / 1024) - (AvailPhys() / 1024)) + " MB/" + (TotalPhys() / 1024) + " MB")
			Text(x, y + (460 * MenuScale), "Triangles Rendered: " + CurrTrisAmount)
			Text(x, y + (480 * MenuScale), "Active Textures: " + ActiveTextures())
			;[End Block]
		Case 2
			;[Block]
			Text(x, y, "Player Position: (" + FloatToString(EntityX(me\Collider), 1) + ", " + FloatToString(EntityY(me\Collider), 1) + ", " + FloatToString(EntityZ(me\Collider), 1) + ")")
			Text(x, y + (20 * MenuScale), "Player Rotation: (" + FloatToString(EntityPitch(me\Collider), 1) + ", " + FloatToString(EntityYaw(me\Collider), 1) + ", " + FloatToString(EntityRoll(me\Collider), 1) + ")")
			
			Text(x, y + (60 * MenuScale), "Injuries: " + me\Injuries)
			Text(x, y + (80 * MenuScale), "Bloodloss: " + me\Bloodloss)
			
			Text(x, y + (120 * MenuScale), "Blur Timer: " + me\BlurTimer)
			Text(x, y + (140 * MenuScale), "Light Blink: " + me\LightBlink)
			Text(x, y + (160 * MenuScale), "Light Flash: " + me\LightFlash)
			
			Text(x, y + (200 * MenuScale), "Blink Frequency: " + me\BLINKFREQ)
			Text(x, y + (220 * MenuScale), "Blink Timer: " + me\BlinkTimer)
			Text(x, y + (240 * MenuScale), "Blink Effect: " + me\BlinkEffect)
			Text(x, y + (260 * MenuScale), "Blink Effect Timer: " + me\BlinkEffectTimer)
			Text(x, y + (280 * MenuScale), "Eye Irritation: " + me\EyeIrritation)
			Text(x, y + (300 * MenuScale), "Eye Stuck: " + me\EyeStuck)
			
			Text(x, y + (340 * MenuScale), "Stamina: " + me\Stamina)
			Text(x, y + (360 * MenuScale), "Stamina Effect: " + me\StaminaEffect)
			Text(x, y + (380 * MenuScale), "Stamina Effect Timer: " + me\StaminaEffectTimer)
			
			Text(x, y + (420 * MenuScale), "Deaf Timer: " + me\DeafTimer)
			Text(x, y + (460 * MenuScale), "Sanity Timer: " + me\Sanity)
			
			If me\Terminated Then
				Text(x + (380 * MenuScale), y, "Terminated: True")
			Else
				Text(x + (380 * MenuScale), y, "Terminated: False")
			EndIf
			
			Text(x + (380 * MenuScale), y + (20 * MenuScale), "Death Timer: " + me\DeathTimer)
			Text(x + (380 * MenuScale), y + (40 * MenuScale), "Fall Timer: " + me\FallTimer)
			
			Text(x + (380 * MenuScale), y + (80 * MenuScale), "Heal Timer: " + me\HealTimer)
			
			Text(x + (380 * MenuScale), y + (120 * MenuScale), "Heart Beat Timer: " + me\HeartBeatTimer)
			
			Text(x + (380 * MenuScale), y + (160 * MenuScale), "Explosion Timer: " + me\ExplosionTimer)
			
			Text(x + (380 * MenuScale), y + (200 * MenuScale), "Current Speed: " + me\CurrSpeed)
			
			Text(x + (380 * MenuScale), y + (240 * MenuScale), "Camera Shake Timer: " + me\CameraShakeTimer)
			Text(x + (380 * MenuScale), y + (260 * MenuScale), "Current Camera Zoom: " + me\CurrCameraZoom)
			
			Text(x + (380 * MenuScale), y + (300 * MenuScale), "Vomit Timer: " + me\VomitTimer)
			
			If me\Playable Then
				Text(x + (380 * MenuScale), y + (340 * MenuScale), "Is Playable: True")
			Else
				Text(x + (380 * MenuScale), y + (340 * MenuScale), "Is Playable: False")
			EndIf
			
			Text(x + (380 * MenuScale), y + (380 * MenuScale), "Refined Items: " + me\RefinedItems)
			Text(x + (380 * MenuScale), y + (400 * MenuScale), "Funds: " + me\Funds)
			;[End Block]
		Case 3
			;[Block]
			If n_I\Curr049 <> Null Then
			Text(x, y, "SCP-049 Position: (" + FloatToString(EntityX(n_I\Curr049\OBJ), 2) + ", " + FloatToString(EntityY(n_I\Curr049\OBJ), 2) + ", " + FloatToString(EntityZ(n_I\Curr049\OBJ), 2) + ")")
			Text(x, y + (20 * MenuScale), "SCP-049 Idle: " + n_I\Curr049\Idle)
			Text(x, y + (40 * MenuScale), "SCP-049 State: " + n_I\Curr049\State)
			EndIf
			If n_I\Curr096 <> Null Then
			Text(x, y + (60 * MenuScale), "SCP-096 Position: (" + FloatToString(EntityX(n_I\Curr096\OBJ), 2) + ", " + FloatToString(EntityY(n_I\Curr096\OBJ), 2) + ", " + FloatToString(EntityZ(n_I\Curr096\OBJ), 2) + ")")
			Text(x, y + (80 * MenuScale), "SCP-096 Idle: " + n_I\Curr096\Idle)
			Text(x, y + (100 * MenuScale), "SCP-096 State: " + n_I\Curr096\State)
			EndIf
			If n_I\Curr106 <> Null Then
			Text(x, y + (120 * MenuScale), "SCP-106 Position: (" + FloatToString(EntityX(n_I\Curr106\OBJ), 2) + ", " + FloatToString(EntityY(n_I\Curr106\OBJ), 2) + ", " + FloatToString(EntityZ(n_I\Curr106\OBJ), 2) + ")")
			Text(x, y + (140 * MenuScale), "SCP-106 Idle: " + n_I\Curr106\Idle)
			Text(x, y + (160 * MenuScale), "SCP-106 State: " + n_I\Curr106\State)
			EndIf
			If n_I\Curr173 <> Null Then
			Text(x, y + (180 * MenuScale), "SCP-173 Position: (" + FloatToString(EntityX(n_I\Curr173\OBJ), 2) + ", " + FloatToString(EntityY(n_I\Curr173\OBJ), 2) + ", " + FloatToString(EntityZ(n_I\Curr173\OBJ), 2) + ")")
			Text(x, y + (200 * MenuScale), "SCP-173 Idle: " + n_I\Curr173\Idle)
			Text(x, y + (220 * MenuScale), "SCP-173 State: " + n_I\Curr173\State)
			EndIf
			
			Text(x, y + (260 * MenuScale), "Pills Taken: " + I_500\Taken)
			
			Text(x, y + (300 * MenuScale), "SCP-008 Infection: " + I_008\Timer)
			Text(x, y + (320 * MenuScale), "SCP-409 Crystallization: " + I_409\Timer)
			Text(x, y + (340 * MenuScale), "SCP-427 State (Secs): " + Int(I_427\Timer / 70.0))
			For i = 0 To 7
				Text(x, y + ((360 + (20 * i)) * MenuScale), "SCP-1025 State " + i + ": " + I_1025\State[i])
			Next
			
			If I_005\ChanceToSpawn = 1 Then
				Text(x, y + (540 * MenuScale), "SCP-005 Spawned in the Chamber!")
			ElseIf I_005\ChanceToSpawn = 2
				Text(x, y + (540 * MenuScale), "SCP-005 Spawned in Dr.Maynard's Office!")
			ElseIf I_005\ChanceToSpawn = 3
				Text(x, y + (540 * MenuScale), "SCP-005 Spawned in SCP-409's Containment Chamber!")
			EndIf
			;[End Block]
	End Select
	SetFont(fo\FontID[Font_Default])
End Function

Function ConsoleSpawnNPC%(Name$, NPCState$ = "")
	Local n.NPCs
	Local ConsoleMsg$
	
	Select Name 
		Case "008", "008zombie", "008-1", "infectedhuman", "humaninfected", "scp008-1", "scp-008-1", "scp0081", "0081", "scp-0081", "008_1", "scp_008_1"
			;[Block]
			n.NPCs = CreateNPC(NPCType008_1, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			n\State = 1.0
			ConsoleMsg = "SCP-008 infected human spawned."
			;[End Block]
		Case "049", "scp049", "scp-049", "plaguedoctor", "doc"
			;[Block]
			n.NPCs = CreateNPC(NPCType049, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			n\State = 1.0
			If n_I\Curr049 = Null Then n_I\Curr049 = n
			ConsoleMsg = "SCP-049 spawned."
			;[End Block]
		Case "049-2", "0492", "scp-049-2", "scp049-2", "049zombie", "curedhuman", "scp0492", "scp-0492", "049_2", "scp_049_2"
			;[Block]
			n.NPCs = CreateNPC(NPCType049_2, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			n\State = 1.0
			ConsoleMsg = "SCP-049-2 spawned."
			;[End Block]
		Case "066", "scp066", "scp-066", "eric"
			;[Block]
			n.NPCs = CreateNPC(NPCType066, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "SCP-066 spawned."
			;[End Block]
		Case "096", "scp096", "scp-096", "shyguy"
			;[Block]
			n.NPCs = CreateNPC(NPCType096, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			n\State = 5.0
			If n_I\Curr096 = Null Then n_I\Curr096 = n
			ConsoleMsg = "SCP-096 spawned."
			;[End Block]
		Case "106", "scp106", "scp-106", "larry", "oldman"
			;[Block]
			n.NPCs = CreateNPC(NPCType106, EntityX(me\Collider), EntityY(me\Collider) - 0.5, EntityZ(me\Collider))
			n\State = -1.0
			ConsoleMsg = "SCP-106 spawned."
			;[End Block]
		Case "173", "scp173", "scp-173", "statue", "sculpture", "peanut"
			;[Block]
			n.NPCs = CreateNPC(NPCType173, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			n_I\Curr173 = n
			If n_I\Curr173\Idle = 3 Then n_I\Curr173\Idle = 0
			ConsoleMsg = "SCP-173 spawned."
			;[End Block]
		Case "372", "scp372", "scp-372", "pj", "jumper"
			;[Block]
			n.NPCs = CreateNPC(NPCType372, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "SCP-372 spawned."
			;[End Block]
		Case "513-1", "5131", "scp513-1", "scp-513-1", "bll", "scp-5131", "scp5131"
			;[Block]
			n.NPCs = CreateNPC(NPCType513_1, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "SCP-513-1 spawned."
			;[End Block]
		Case "860-2", "8602", "scp860-2", "scp-860-2", "forestmonster", "scp8602"
			;[Block]
			CreateConsoleMsg("SCP-860-2 cannot be spawned with the console. Sorry!", 255, 0, 0)
			;[End Block]
		Case "939", "scp939", "scp-939"
			CreateConsoleMsg("SCP-939 instances cannot be spawned with the console. Sorry!", 255, 0, 0)
			;[End Block]
		Case "966", "scp966", "scp-966", "sleepkiller"
			;[Block]
			n.NPCs = CreateNPC(NPCType966, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "SCP-966 instance spawned."
			;[End Block]
		Case "1048-a", "scp1048-a", "scp-1048-a", "scp1048a", "scp-1048a", "earbear"
			;[Block]
			CreateConsoleMsg("SCP-1048-A cannot be spawned with the console. Sorry!", 255, 0, 0)
			;[End Block]
		Case "1048", "scp1048", "scp-1048", "scp-1048", "bear", "builderbear"
			;[Block]
			CreateConsoleMsg("SCP-1048 cannot be spawned with the console. Sorry!", 255, 0, 0)
			;[End Block]
		Case "1499-1", "14991", "scp-1499-1", "scp1499-1", "scp-14991", "scp14991"
			n.NPCs = CreateNPC(NPCType1499_1, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "SCP-1499-1 instance spawned."
			;[End Block]
		Case "class-d", "classd", "d"
			;[Block]
			n.NPCs = CreateNPC(NPCTypeD, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "D-Class spawned."
			;[End Block]
		Case "guard", "ulgrin"
			;[Block]
			n.NPCs = CreateNPC(NPCTypeGuard, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "Guard spawned."
			;[End Block]
		Case "mtf", "ntf"
			;[Block]
			n.NPCs = CreateNPC(NPCTypeMTF, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "MTF unit spawned."
			;[End Block]
		Case "apache", "helicopter"
			;[Block]
			n.NPCs = CreateNPC(NPCTypeApache, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "Apache spawned."
			;[End Block]
		Case "tentacle", "scp035tentacle", "scp-035tentacle", "scp-035-tentacle", "scp035-tentacle"
			;[Block]
			n.NPCs = CreateNPC(NPCType035_Tentacle, EntityX(me\Collider), EntityY(me\Collider) - 0.12, EntityZ(me\Collider))
			ConsoleMsg = "SCP-035 tentacle spawned."
			;[End Block]
		Case "clerk", "woman", "lady", "reyes", "rebbeca"
			;[Block]
			n.NPCs = CreateNPC(NPCTypeClerk, EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
			ConsoleMsg = "Clerk spawned."
			;[End Block]
		Default 
			;[Block]
			CreateConsoleMsg("NPC type not found.", 255, 0, 0) : Return
			;[End Block]
	End Select
	
	If n <> Null Then
		If NPCState <> "" Then n\State = Float(NPCState) : ConsoleMsg = ConsoleMsg + " (State = " + n\State + ")"
	EndIf
	
	CreateConsoleMsg(ConsoleMsg)
End Function

Function ClearConsole%()
	Local c.ConsoleMsg
	
	For c.ConsoleMsg = Each ConsoleMsg
		Delete(c)
	Next
	
	ConsoleR = 0 : ConsoleG = 255 : ConsoleB = 255
	
	CreateConsoleMsg("Console commands: ")
	CreateConsoleMsg("  - help [page]")
	CreateConsoleMsg("  - teleport [room name]")
	CreateConsoleMsg("  - godmode [on / off]")
	CreateConsoleMsg("  - noclip [on / off]")
	CreateConsoleMsg("  - infinitestamina [on / off]")
	CreateConsoleMsg("  - noblink [on / off]")
	CreateConsoleMsg("  - notarget [on / off]")
	CreateConsoleMsg("  - noclipspeed [x] (default = 2.0)")
	CreateConsoleMsg("  - wireframe [on / off]")
	CreateConsoleMsg("  - debughud [category]")
	CreateConsoleMsg("  - camerafog [near] [far]")
	CreateConsoleMsg("  - heal")
	CreateConsoleMsg("  - revive")
	CreateConsoleMsg("  - asd")
	CreateConsoleMsg("  - spawnitem [item name]")
	CreateConsoleMsg("  - 106retreat")
	CreateConsoleMsg("  - disable173 / enable173")
	CreateConsoleMsg("  - disable106 / enable106")
	CreateConsoleMsg("  - spawn [NPC type]")
End Function