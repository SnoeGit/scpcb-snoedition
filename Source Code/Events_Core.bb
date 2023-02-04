Type Events
	Field EventName$ ; ~ Used for debugs and Map Creator
	Field EventID%
	Field room.Rooms
	Field EventState#, EventState2#, EventState3#, EventState4#
	Field SoundCHN%, SoundCHN2%, SoundCHN3%
	Field Sound%, Sound2%, Sound3%
	Field SoundCHN_IsStream%, SoundCHN2_IsStream%, SoundCHN3_IsStream%
	Field EventStr$
	Field Img%
End Type

Global forest_event.Events
Global skull_event.Events

; ~ Events ID Constants
;[Block]
Const e_cont1_173% = 0, e_cont1_173_intro% = 1
Const e_butt_ghost% = 2
Const e_room2_checkpoint% = 3
Const e_cont1_895% = 4, e_cont1_895_106% = 5
Const e_room1_dead_end_106% = 6
Const e_room2c_gw_lcz_173% = 7, e_room2c_gw_ez_096% = 8
Const e_cont1_372% = 9
Const e_dimension_106% = 10
Const e_room2_cafeteria% = 11
Const e_room2c_ec% = 12
Const e_room2_closets% = 13
Const e_room2_6_lcz_173% = 14
Const e_room2_elevator% = 15
Const e_room2_ic% = 16
Const e_room2_2_lcz% = 17
Const e_room2_nuke% = 18
Const e_room2_2_ez% = 19, e_room2_ez_035% = 20
Const e_room2_tesla% = 21
Const e_trick% = 22
Const e_room2_mt% = 23
Const e_room2_2_hcz_106% = 24
Const e_room2_4_hcz_106% = 25, e_room2_4_hcz% = 26
Const e_room3_hcz_duck% = 27, e_room3_hcz_1048% = 28
Const e_room2_scientists_2% = 29
Const e_room2_servers_hcz% = 30
Const e_room2_storage% = 31
Const e_door_closing% = 32
Const e_room3_2_ez% = 33
Const e_room3_storage% = 34
Const e_room3_2_hcz% = 35
Const e_room4_lcz% = 36
Const e_cont2_012% = 37
Const e_cont1_035% = 38
Const e_cont2_049% = 39
Const e_cont1_079% = 40
Const e_cont1_106% = 41
Const e_cont1_205% = 42
Const e_cont2_860_1% = 43
Const e_cont3_966% = 44
Const e_cont2_1123% = 45
Const e_room2_test_hcz% = 46, e_room2_test_lcz_173% = 47
Const e_room2_6_hcz_smoke% = 48, e_room2_6_hcz% = 49
Const e_room2_5_hcz_106% = 50
Const e_toilet_guard% = 51
Const e_cont2_008% = 52
Const e_106_victim% = 53
Const e_106_sinkhole% = 54
Const e_682_roar% = 55
Const e_cont1_914% = 56
Const e_1048_a% = 57
Const e_room4_2_hcz% = 58
Const e_room2_gw_2% = 59, e_gateway% = 60
Const e_cont2_500_1499% = 61
Const e_cont2c_1162_arc% = 62
Const e_room2_sl% = 63
Const e_096_spawn% = 64
Const e_room2_medibay% = 65
Const e_dimension_1499% = 66
Const e_room2_shaft% = 67
Const e_room4_ic% = 68
Const e_cont2_409% = 69
Const e_cont1_005% = 70
Const e_gate_b_entrance% = 71
Const e_gate_b% = 72
Const e_gate_a_entrance% = 73
Const e_gate_a% = 74
;[End Block]

Function FindEventID%(EventName$)
	Select Lower(EventName)
		Case "cont1_173"
			;[Block]
			Return(e_cont1_173)
			;[End Block]
		Case "cont1_173_intro"
			;[Block]
			Return(e_cont1_173_intro)
			;[End Block]
		Case "butt_ghost"
			;[Block]
			Return(e_butt_ghost)
			;[End Block]
		Case "room2_checkpoint"
			;[Block]
			Return(e_room2_checkpoint)
			;[End Block]
		Case "cont1_895"
			;[Block]
			Return(e_cont1_895)
			;[End Block]
		Case "cont1_895_106"
			;[Block]
			Return(e_cont1_895_106)
			;[End Block]
		Case "room1_dead_end_106"
			;[Block]
			Return(e_room1_dead_end_106)
			;[End Block]
		Case "room2c_gw_lcz_173"
			;[Block]
			Return(e_room2c_gw_lcz_173)
			;[End Block]
		Case "room2c_gw_ez_096"
			;[Block]
			Return(e_room2c_gw_ez_096)
			;[End Block]
		Case "cont1_372"
			;[Block]
			Return(e_cont1_372)
			;[End Block]
		Case "dimension_106"
			;[Block]
			Return(e_dimension_106)
			;[End Block]
		Case "room2_cafeteria"
			;[Block]
			Return(e_room2_cafeteria)
			;[End Block]
		Case "room2c_ec"
			;[Block]
			Return(e_room2c_ec)
			;[End Block]
		Case "room2_closets"
			;[Block]
			Return(e_room2_closets)
			;[End Block]
		Case "room2_6_lcz_173"
			;[Block]
			Return(e_room2_6_lcz_173)
			;[End Block]
		Case "room2_elevator"
			;[Block]
			Return(e_room2_elevator)
			;[End Block]
		Case "room2_ic"
			;[Block]
			Return(e_room2_ic)
			;[End Block]
		Case "room2_2_lcz"
			;[Block]
			Return(e_room2_2_lcz)
			;[End Block]
		Case "room2_nuke"
			;[Block]
			Return(e_room2_nuke)
			;[End Block]
		Case "room2_2_ez"
			;[Block]
			Return(e_room2_2_ez)
			;[End Block]
		Case "room2_ez_035"
			;[Block]
			Return(e_room2_ez_035)
			;[End Block]
		Case "room2_tesla"
			;[Block]
			Return(e_room2_tesla)
			;[End Block]
		Case "trick"
			;[Block]
			Return(e_trick)
			;[End Block]
		Case "room2_mt"
			;[Block]
			Return(e_room2_mt)
			;[End Block]
		Case "room2_2_hcz_106"
			;[Block]
			Return(e_room2_2_hcz_106)
			;[End Block]
		Case "room2_4_hcz_106"
			;[Block]
			Return(e_room2_4_hcz_106)
			;[End Block]
		Case "room2_4_hcz"
			;[Block]
			Return(e_room2_4_hcz)
			;[End Block]
		Case "room3_hcz_duck"
			;[Block]
			Return(e_room3_hcz_duck)
			;[End Block]
		Case "room3_hcz_1048"
			;[Block]
			Return(e_room3_hcz_1048)
			;[End Block]
		Case "room2_scientists_2"
			;[Block]
			Return(e_room2_scientists_2)
			;[End Block]
		Case "room2_servers_hcz"
			;[Block]
			Return(e_room2_servers_hcz)
			;[End Block]
		Case "room2_storage"
			;[Block]
			Return(e_room2_storage)
			;[End Block]
		Case "door_closing"
			;[Block]
			Return(e_door_closing)
			;[End Block]
		Case "room3_2_ez"
			;[Block]
			Return(e_room3_2_ez)
			;[End Block]
		Case "room3_storage"
			;[Block]
			Return(e_room3_storage)
			;[End Block]
		Case "room3_2_hcz"
			;[Block]
			Return(e_room3_2_hcz)
			;[End Block]
		Case "room4_lcz"
			;[Block]
			Return(e_room4_lcz)
			;[End Block]
		Case "cont2_012"
			;[Block]
			Return(e_cont2_012)
			;[End Block]
		Case "cont1_035"
			;[Block]
			Return(e_cont1_035)
			;[End Block]
		Case "cont2_049"
			;[Block]
			Return(e_cont2_049)
			;[End Block]
		Case "cont1_079"
			;[Block]
			Return(e_cont1_079)
			;[End Block]
		Case "cont1_106"
			;[Block]
			Return(e_cont1_106)
			;[End Block]
		Case "cont1_205"
			;[Block]
			Return(e_cont1_205)
			;[End Block]
		Case "cont2_860_1"
			;[Block]
			Return(e_cont2_860_1)
			;[End Block]
		Case "cont3_966"
			;[Block]
			Return(e_cont3_966)
			;[End Block]
		Case "cont2_1123"
			;[Block]
			Return(e_cont2_1123)
			;[End Block]
		Case "room2_test_hcz"
			;[Block]
			Return(e_room2_test_hcz)
			;[End Block]
		Case "room2_test_lcz_173"
			;[Block]
			Return(e_room2_test_lcz_173)
			;[End Block]
		Case "room2_6_hcz_smoke"
			;[Block]
			Return(e_room2_6_hcz_smoke)
			;[End Block]
		Case "room2_6_hcz"
			;[Block]
			Return(e_room2_6_hcz)
			;[End Block]
		Case "room2_5_hcz_106"
			;[Block]
			Return(e_room2_5_hcz_106)
			;[End Block]
		Case "toilet_guard"
			;[Block]
			Return(e_toilet_guard)
			;[End Block]
		Case "cont2_008"
			;[Block]
			Return(e_cont2_008)
			;[End Block]
		Case "106_victim"
			;[Block]
			Return(e_106_victim)
			;[End Block]
		Case "106_sinkhole"
			;[Block]
			Return(e_106_sinkhole)
			;[End Block]
		Case "682_roar"
			;[Block]
			Return(e_682_roar)
			;[End Block]
		Case "cont1_914"
			;[Block]
			Return(e_cont1_914)
			;[End Block]
		Case "1048_a"
			;[Block]
			Return(e_1048_a)
			;[End Block]
		Case "room4_2_hcz"
			;[Block]
			Return(e_room4_2_hcz)
			;[End Block]
		Case "room2_gw_2"
			;[Block]
			Return(e_room2_gw_2)
			;[End Block]
		Case "gateway"
			;[Block]
			Return(e_gateway)
			;[End Block]
		Case "cont2_500_1499"
			;[Block]
			Return(e_cont2_500_1499)
			;[End Block]
		Case "cont2c_1162_arc"
			;[Block]
			Return(e_cont2c_1162_arc)
			;[End Block]
		Case "room2_sl"
			;[Block]
			Return(e_room2_sl)
			;[End Block]
		Case "096_spawn"
			;[Block]
			Return(e_096_spawn)
			;[End Block]
		Case "room2_medibay"
			;[Block]
			Return(e_room2_medibay)
			;[End Block]
		Case "dimension_1499"
			;[Block]
			Return(e_dimension_1499)
			;[End Block]
		Case "room2_shaft"
			;[Block]
			Return(e_room2_shaft)
			;[End Block]
		Case "room4_ic"
			;[Block]
			Return(e_room4_ic)
			;[End Block]
		Case "cont2_409"
			;[Block]
			Return(e_cont2_409)
			;[End Block]
		Case "cont1_005"
			;[Block]
			Return(e_cont1_005)
			;[End Block]
		Case "gate_b_entrance"
			;[Block]
			Return(e_gate_b_entrance)
			;[End Block]
		Case "gate_b"
			;[Block]
			Return(e_gate_b)
			;[End Block]
		Case "gate_a_entrance"
			;[Block]
			Return(e_gate_a_entrance)
			;[End Block]
		Case "gate_a"
			;[Block]
			Return(e_gate_a)
			;[End Block]
		Default
			;[Block]
			Return(-1)
			;[End Block]
	End Select
End Function

Function FindForestEvent%(e.Events)
	Select e\EventID
		Case e_cont2_860_1
			;[Block]
			forest_event = e
			;[End Block]
		Case e_cont2_1123
			;[Block]
			skull_event = e
			;[End Block]
	End Select
End Function

Function CreateEvent.Events(EventName$, RoomName$, ID%, Prob# = 0.0)
	; ~ RoomName = the name of the room(s) you want the event to be assigned to
	
	; ~ The ID-variable determines which of the rooms the event is assigned to,
	; ~ 0 will assign it to the first generated room, 1 to the second, etc.
	
	; ~ The prob-variable can be used to randomly assign events into some rooms
	; ~ 0.5 means that there's a 50% chance that event is assigned to the rooms
	; ~ 1.0 means that the event is assigned to every room
	; ~ The ID-variable is ignored if prob <> 0.0
	
	Local e.Events, e2.Events, r.Rooms
	Local i% = 0, Temp%
	
	If Prob = 0.0 Then
		For r.Rooms = Each Rooms
			If RoomName = "" Lor RoomName = r\RoomTemplate\Name Then
				Temp = False
				For e2.Events = Each Events
					If e2\room = r Then
						Temp = True
						Exit
					EndIf
				Next
				
				i = i + 1
				If i >= ID And (Not Temp) Then
					e.Events = New Events
					e\EventName = EventName
					e\EventID = FindEventID(EventName)
					FindForestEvent(e)
					e\room = r
					Return(e)
				EndIf
			EndIf
		Next
	Else
		For r.Rooms = Each Rooms
			If RoomName = "" Lor RoomName = r\RoomTemplate\Name Then
				Temp = False
				For e2.Events = Each Events
					If e2\room = r Then
						Temp = True
						Exit
					EndIf
				Next
				
				If Rnd(0.0, 1.0) < Prob And (Not Temp) Then
					e.Events = New Events
					e\EventName = EventName
					e\EventID = FindEventID(EventName)
					FindForestEvent(e)
					e\room = r
				EndIf
			EndIf
		Next
	EndIf
	Return(Null)
End Function

Global QuickLoadIcon%
Global QuickLoadPercent% = -1
Global QuickLoadPercent_DisplayTimer# = 0.0
Global QuickLoad_CurrEvent.Events

Function UpdateQuickLoading%()
	If QuickLoadPercent > -1 Then
		If QuickLoadPercent > 99 Then
			If QuickLoadPercent_DisplayTimer < 70.0 Then
				QuickLoadPercent_DisplayTimer = Min(QuickLoadPercent_DisplayTimer + fps\Factor[0], 70.0)
			Else
				QuickLoadPercent = -1
			EndIf
		EndIf
		QuickLoadEvents()
	Else
		QuickLoadPercent = -1
		QuickLoadPercent_DisplayTimer = 0.0
		QuickLoad_CurrEvent = Null
	EndIf
End Function

Function RenderQuickLoading%()
	If QuickLoadPercent > -1 Then
		MidHandle(QuickLoadIcon)
		DrawImage(QuickLoadIcon, opt\GraphicWidth - (90 * MenuScale), opt\GraphicHeight - (150 * MenuScale))
		Color(255, 255, 255)
		SetFont2(fo\FontID[Font_Default])
		Text2(opt\GraphicWidth - (100 * MenuScale), opt\GraphicHeight - (90 * MenuScale), Format(GetLocalString("loading", "loading"), QuickLoadPercent), True)
	EndIf
End Function

Function QuickLoadEvents%()
	CatchErrors("Uncaught (QuickLoadEvents)")
	
	If QuickLoad_CurrEvent = Null Then
		QuickLoadPercent = -1
		Return
	EndIf
	
	Local e.Events = QuickLoad_CurrEvent
	Local r.Rooms, sc.SecurityCams, sc2.SecurityCams, n.NPCs
	Local Scale#, Pvt%, i%, x#, z#
	
	; ~ Might be a good idea to use QuickLoadPercent to determine the "steps" of the loading process 
	; ~ Instead of magic values in e\EventState and e\EventStr
	
	Select e\EventID
		Case e_cont1_205
			;[Block]
			If e\EventState = 0.0 Lor e\EventStr <> "LoadDone" Then
				If e\EventStr = "Load0"
					e\room\Objects[3] = CopyEntity(n_I\NPCModelID[NPC_205_DEMON_1_MODEL])
					QuickLoadPercent = 10
					e\EventStr = "Load1"
				ElseIf e\EventStr = "Load1"
					e\room\Objects[4] = CopyEntity(n_I\NPCModelID[NPC_205_DEMON_2_MODEL])
					QuickLoadPercent = 20
					e\EventStr = "Load2"
				ElseIf e\EventStr = "Load2"
					e\room\Objects[5] = CopyEntity(n_I\NPCModelID[NPC_205_DEMON_3_MODEL])
					QuickLoadPercent = 30
					e\EventStr = "Load3"
				ElseIf e\EventStr = "Load3"
					e\room\Objects[6] = CopyEntity(n_I\NPCModelID[NPC_205_WOMAN_MODEL])
					QuickLoadPercent = 40
					e\EventStr = "Load4"
				ElseIf e\EventStr = "Load4"
					QuickLoadPercent = 50
					e\EventStr = "Load5"
				ElseIf e\EventStr = "Load5"
					For i = 3 To 6
						PositionEntity(e\room\Objects[i], EntityX(e\room\Objects[0], True), EntityY(e\room\Objects[0], True), EntityZ(e\room\Objects[0], True), True)
						RotateEntity(e\room\Objects[i], -90.0, EntityYaw(e\room\Objects[0], True), 0.0, True)
						ScaleEntity(e\room\Objects[i], 0.05, 0.05, 0.05, True)
					Next
					QuickLoadPercent = 70
					e\EventStr = "Load6"
				ElseIf e\EventStr = "Load6"
					For i = 3 To 6
						HideEntity(e\room\Objects[i])
					Next
					QuickLoadPercent = 100
					e\EventStr = "LoadDone"
				EndIf
			EndIf
			;[End Block]
		Case e_cont2_860_1
			;[Block]
			If e\EventStr = "Load0"
				QuickLoadPercent = 15
				ForestNPC = CreateSprite()
				ScaleSprite(ForestNPC, 0.75 * (140.0 / 410.0), 0.75)
				SpriteViewMode(ForestNPC, 4)
				EntityFX(ForestNPC, 1 + 8)
				ForestNPCTex = LoadAnimTexture_Strict("GFX\NPCs\AgentIJ.AIJ", 1 + 2, 140, 410, 0, 4, DeleteAllTextures)
				ForestNPCData[0] = 0.0
				EntityTexture(ForestNPC, ForestNPCTex, ForestNPCData[0])
				ForestNPCData[1] = 0.0
				ForestNPCData[2] = 0.0
				HideEntity(ForestNPC)
				e\EventStr = "Load1"
			ElseIf e\EventStr = "Load1"
				QuickLoadPercent = 40
				e\EventStr = "Load2"
			ElseIf e\EventStr = "Load2"
				QuickLoadPercent = 100
				If e\room\NPC[0] = Null Then e\room\NPC[0] = CreateNPC(NPCType860_2, 0.0, 0.0, 0.0)
				e\EventStr = "LoadDone"
			EndIf
			;[End Block]
		Case e_dimension_1499
			;[Block]
			If e\EventState = 0.0 Then
				If e\EventStr = "Load0" Then
					QuickLoadPercent = 10
					e\room\Objects[0] = LoadMesh_Strict("GFX\Map\dimension1499\1499plane.b3d")
					HideEntity(e\room\Objects[0])
					e\EventStr = "Load1"
				ElseIf e\EventStr = "Load1"
					QuickLoadPercent = 30
					I_1499\Sky = CreateSky("GFX\Map\Textures\1499sky")
					e\EventStr = 1
				Else
					If Int(e\EventStr) < 16 Then
						QuickLoadPercent = QuickLoadPercent + 2
						e\room\Objects[Int(e\EventStr)] = LoadRMesh("GFX\Map\dimension1499\dimension_1499_object(" + (Int(e\EventStr)) + ").rmesh", Null)
						ScaleEntity(e\room\Objects[Int(e\EventStr)], RoomScale, RoomScale, RoomScale)
						HideEntity(e\room\Objects[Int(e\EventStr)])
						e\EventStr = Int(e\EventStr) + 1
					ElseIf Int(e\EventStr) = 16
						QuickLoadPercent = 90
						CreateChunkParts(e\room)
						e\EventStr = 17
					ElseIf Int(e\EventStr) = 17
						QuickLoadPercent = 100
						
						x = EntityX(e\room\OBJ)
						z = EntityZ(e\room\OBJ)
						
						Local ch.Chunk
						
						For i = -2 To 0 Step 2
							ch.Chunk = CreateChunk(-1, x * (i * 2.5), EntityY(e\room\OBJ), z, True)
							ch.Chunk = CreateChunk(-1, x * (i * 2.5), EntityY(e\room\OBJ), z - 40.0, True)
						Next
						
						e\EventState = 2.0
						e\EventStr = 18
					EndIf
				EndIf
			EndIf
			;[End Block]
	End Select
	
	CatchErrors("QuickLoadEvents: Event Name: " + Chr(34) + e\EventName + Chr(34) + ", ID: " + e\EventID)
End Function

Function UpdateEvents%()
	CatchErrors("Uncaught (UpdateEvents)")
	
	Local p.Particles, n.NPCs, r.Rooms, e.Events, e2.Events, de.Decals, du.Dummy1499_1, w.WayPoints, pr.Props
	Local it.Items, it2.Items, em.Emitters, sc.SecurityCams, sc2.SecurityCams, wayp.WayPoints, do.Doors
	Local Dist#, i%, Temp%, Pvt%, StrTemp$, j%, k%
	Local CurrTrigger$ = "", fDir#, Scale#, Tex%, t1%, Name$
	Local x#, y#, z#, xTemp#, yTemp#, b%, BT%, SF%, TexName$
	Local Angle#, RoomExists%
	
	For e.Events = Each Events
		Select e\EventID
			Case e_cont1_173
				;[Block]
				If e\room\RoomDoors[3] = Null Then
					For i = 0 To MaxRoomAdjacents - 1
						If e\room\AdjDoor[i] <> Null Then
							e\room\RoomDoors[3] = e\room\AdjDoor[i]
							e\room\RoomDoors[3]\Open = True
							Exit
						EndIf
					Next
				EndIf
				
				If e\EventState = 0.0 Then
					If PlayerRoom = e\room Then
						e\room\RoomDoors[2]\Open = True
						
						If SelectedDifficulty\SaveType = SAVE_ANYWHERE Then
							CreateHintMsg(Format(GetLocalString("save", "save"), key\Name[key\SAVE]))
						ElseIf SelectedDifficulty\SaveType = SAVE_ON_SCREENS Then
							CreateHintMsg(GetLocalString("save", "failed.screen"))
						EndIf
						
						n_I\Curr173\Idle = 1
						
						While e\room\RoomDoors[1]\OpenState < 180.0
							e\room\RoomDoors[1]\OpenState = Min(180.0, e\room\RoomDoors[1]\OpenState + 0.8)
							MoveEntity(e\room\RoomDoors[1]\OBJ, Sin(e\room\RoomDoors[1]\OpenState) / 180.0, 0.0, 0.0)
							MoveEntity(e\room\RoomDoors[1]\OBJ2, -Sin(e\room\RoomDoors[1]\OpenState) / 180.0, 0.0, 0.0)
						Wend
						
						If e\room\NPC[0] <> Null Then
							e\room\NPC[0]\State = 8.0
							SetNPCFrame(e\room\NPC[0], 74.0)
						EndIf
						
						If e\room\NPC[1] <> Null Then
							PositionEntity(e\room\NPC[1]\Collider, e\room\x, e\room\y + 0.5, e\room\z - 1.0, True)
							ResetEntity(e\room\NPC[1]\Collider)
						Else
							e\room\NPC[1] = CreateNPC(NPCTypeD, e\room\x, e\room\y + 0.5, e\room\z - 1.0)
							ChangeNPCTextureID(e\room\NPC[1], NPC_CLASS_D_FRANKLIN_TEXTURE)
						EndIf
						SetNPCFrame(e\room\NPC[1], 210.0)
						
						If e\room\NPC[2] <> Null Then
							PositionEntity(e\room\NPC[2]\Collider, e\room\x, 0.5, e\room\z + 528.0 * RoomScale, True)
							ResetEntity(e\room\NPC[2]\Collider)
						Else
							e\room\NPC[2] = CreateNPC(NPCTypeGuard, e\room\x, e\room\y + 0.5, e\room\z + 528.0 * RoomScale)
						EndIf
						e\room\NPC[2]\State = 7.0
						PointEntity(e\room\NPC[2]\Collider, e\room\NPC[1]\Collider)
						PointEntity(e\room\NPC[1]\Collider, e\room\NPC[2]\Collider)
						
						If e\room\NPC[0] = Null Then
							e\room\NPC[3] = CreateNPC(NPCTypeGuard, EntityX(e\room\Objects[2], True), EntityY(e\room\Objects[2], True), EntityZ(e\room\Objects[2], True))
							e\room\NPC[3]\State = 8.0 : e\room\NPC[3]\IsDead = True
							SetNPCFrame(e\room\NPC[3], 286.0)
							RotateEntity(e\room\NPC[3]\Collider, 0.0, 90.0, 0.0)
							
							e\room\NPC[4] = CreateNPC(NPCTypeD, EntityX(e\room\Objects[3], True), EntityY(e\room\Objects[3], True), EntityZ(e\room\Objects[3], True))
							e\room\NPC[4]\State3 = -1.0 : e\room\NPC[4]\IsDead = True
							SetNPCFrame(e\room\NPC[4], 711.0)
							RotateEntity(e\room\NPC[4]\Collider, 0.0, 270.0, 0.0)
							
							e\room\NPC[5] = CreateNPC(NPCTypeD, EntityX(e\room\Objects[4], True), EntityY(e\room\Objects[4], True), EntityZ(e\room\Objects[4], True))
							e\room\NPC[5]\State3 = -1.0 : e\room\NPC[5]\IsDead = True
							ChangeNPCTextureID(e\room\NPC[5], NPC_CLASS_D_CLASS_D_TEXTURE)
							SetNPCFrame(e\room\NPC[5], 779.0)
							RotateEntity(e\room\NPC[5]\Collider, 0.0, 270.0, 0.0)
							
							x = EntityX(e\room\OBJ, True) + 3712.0 * RoomScale
							y = 384.0 * RoomScale
							z = EntityZ(e\room\OBJ, True) + 1312.0 * RoomScale
							
							For i = 3 To 5
								PositionEntity(e\room\NPC[i]\Collider, x + (EntityX(e\room\NPC[i]\Collider) - EntityX(e\room\OBJ)), y + EntityY(e\room\NPC[i]\Collider) + 0.4, z + (EntityZ(e\room\NPC[i]\Collider) - EntityZ(e\room\OBJ)))
								ResetEntity(e\room\NPC[i]\Collider)
							Next
						EndIf
						e\EventState = 1.0
					EndIf
				Else
					If e\room\NPC[0] <> Null Then AnimateNPC(e\room\NPC[0], 249.0, 286.0, 0.4, False)
					
					CurrTrigger = CheckTriggers()
					
					If CurrTrigger = "173scene_timer" Then
						e\EventState = e\EventState + fps\Factor[0]
					ElseIf CurrTrigger = "173scene_activated"
						e\EventState = Max(e\EventState, 500.0)
					EndIf
					
					If e\EventState >= 500.0 Then
						e\EventState = e\EventState + fps\Factor[0]
						If e\EventState2 = 0.0 Then
							If e\EventState > 900.0 And e\room\RoomDoors[3]\Open Then
								If e\EventState - fps\Factor[0] <= 900.0 Then 
									PositionEntity(n_I\Curr173\Collider, e\room\x + 32.0 * RoomScale, 0.31, e\room\z + 1072.0 * RoomScale, True)
									ResetEntity(n_I\Curr173\Collider)
									
									e\room\NPC[1]\Sound = LoadSound_Strict("SFX\Room\Intro\WhatThe1a.ogg")
									e\room\NPC[1]\SoundCHN = PlaySound2(e\room\NPC[1]\Sound, Camera, e\room\NPC[1]\Collider)
									e\room\NPC[2]\Sound = LoadSound_Strict("SFX\Room\Intro\WhatThe1b.ogg")
									e\room\NPC[2]\SoundCHN = PlaySound2(e\room\NPC[2]\Sound, Camera, e\room\NPC[2]\Collider)
								EndIf
								e\room\NPC[1]\State = 3.0
								e\room\NPC[1]\CurrSpeed = CurveValue(-0.008, e\room\NPC[1]\CurrSpeed, 5.0)
								AnimateNPC(e\room\NPC[1], 260.0, 236.0, e\room\NPC[1]\CurrSpeed * 18.0)
								RotateEntity(e\room\NPC[1]\Collider, 0.0, 0.0, 0.0)
								
								If e\EventState > 900.0 + (70.0 * 2.5) Then
									If e\room\NPC[2]\State <> 1.0 Then
										e\room\NPC[2]\CurrSpeed = CurveValue(-0.012, e\room\NPC[2]\CurrSpeed, 5.0)
										AnimateNPC(e\room\NPC[2], 39.0, 76.0, e\room\NPC[2]\CurrSpeed * 40.0)
										MoveEntity(e\room\NPC[2]\Collider, 0.0, 0.0, e\room\NPC[2]\CurrSpeed * fps\Factor[0])
										e\room\NPC[2]\State = 8.0
										
										If EntityZ(e\room\NPC[2]\Collider) < e\room\z Then
											PointEntity(e\room\NPC[2]\OBJ, e\room\NPC[1]\Collider)
											RotateEntity(e\room\NPC[2]\Collider, 0.0, CurveAngle(EntityYaw(e\room\NPC[2]\OBJ) - 180.0, EntityYaw(e\room\NPC[2]\Collider), 15.0), 0.0)
										Else
											RotateEntity(e\room\NPC[2]\Collider, 0.0, 0.0, 0.0)
										EndIf
									EndIf
								EndIf
								
								If e\EventState < 900.0 + (70.0 * 4.0) Then
									PositionEntity(n_I\Curr173\Collider, e\room\x + 32.0 * RoomScale, 0.31, e\room\z + 1072.0 * RoomScale, True)
									ResetEntity(n_I\Curr173\Collider)
									RotateEntity(n_I\Curr173\Collider, 0.0, 190.0, 0.0)
									
									If e\EventState > 900.0 + 70.0 And e\EventState < 900.0 + (70.0 * 2.5) Then
										AnimateNPC(e\room\NPC[2], 1539.0, 1553.0, 0.2, False)
										PointEntity(e\room\NPC[2]\OBJ, n_I\Curr173\Collider)
										RotateEntity(e\room\NPC[2]\Collider, 0.0, CurveAngle(EntityYaw(e\room\NPC[2]\OBJ), EntityYaw(e\room\NPC[2]\Collider), 15.0), 0.0)
									EndIf
								Else
									If e\EventState - fps\Factor[0] < 900.0 + (70.0 * 4.0) Then
										PlaySound_Strict(IntroSFX[Rand(8, 10)])
										me\LightBlink = 3.0
										PlaySound2(StoneDragSFX, Camera, n_I\Curr173\Collider)
										PointEntity(n_I\Curr173\Collider, e\room\NPC[2]\Collider)
									EndIf
									PositionEntity(n_I\Curr173\Collider, e\room\x - 96.0 * RoomScale, 0.31, e\room\z + 592.0 * RoomScale, True)
									ResetEntity(n_I\Curr173\Collider)
									RotateEntity(n_I\Curr173\Collider, 0.0, 190.0, 0.0)
									
									If me\LightBlink > 0.0 Then
										n_I\Curr173\Idle = 0
									Else
										n_I\Curr173\Idle = 1
									EndIf
									
									If e\room\NPC[2]\State <> 1.0 And (Not me\Terminated) Then
										If EntityZ(e\room\NPC[2]\Collider) < e\room\z - 1150.0 * RoomScale Then
											e\room\RoomDoors[3]\Open = False
											me\LightBlink = 3.0
											PlaySound_Strict(IntroSFX[Rand(8, 10)])
											me\BlinkTimer = -10.0
											PlaySound2(StoneDragSFX, Camera, n_I\Curr173\Collider)
											If EntityDistanceSquared(n_I\Curr173\Collider, me\Collider) < 6.25 And Abs(EntityY(me\Collider) - EntityY(n_I\Curr173\Collider)) < 1.0 Then
												PositionEntity(n_I\Curr173\Collider, EntityX(me\Collider), EntityY(me\Collider), EntityZ(me\Collider))
											Else
												PositionEntity(n_I\Curr173\Collider, 0.0, -500.0, 0.0)
											EndIf
											ResetEntity(n_I\Curr173\Collider)
											n_I\Curr173\Idle = 0
											CreateHintMsg(Format(GetLocalString("msg", "run"), key\Name[key\SPRINT]))
										EndIf
									EndIf
								EndIf
								
								; ~ If Ulgrin can see the player then start shooting at them.
								If (Not chs\NoTarget) And (CurrTrigger = "173scene_end" And EntityVisible(e\room\NPC[2]\Collider, me\Collider)) Then
									e\room\NPC[2]\State = 1.0
									e\room\NPC[2]\State3 = 1.0
								ElseIf e\room\NPC[2]\State = 1.0 And (Not EntityVisible(e\room\NPC[2]\Collider, me\Collider))
									e\room\NPC[2]\State = 0.0
									e\room\NPC[2]\State3 = 0.0
								EndIf
								If e\room\NPC[2]\State = 1.0 Then e\room\RoomDoors[3]\Open = True
							Else
								If e\room\NPC[2]\State <> 1.0 Then
									If EntityX(me\Collider) < (e\room\x + 1384.0 * RoomScale) Then e\EventState = Max(e\EventState, 900.0)
									
									If e\room\RoomDoors[3]\OpenState = 0.0 Then
										For i = 1 To 2
											If e\room\NPC[i] <> Null Then RemoveNPC(e\room\NPC[i])
										Next
										e\EventState2 = 1.0
									EndIf
								EndIf
							EndIf
						EndIf
						
						PositionEntity(e\room\Objects[0], EntityX(e\room\Objects[0], True), -Max(e\EventState - 1300.0, 0.0) / 4500.0, EntityZ(e\room\Objects[0], True), True)
						RotateEntity(e\room\Objects[0], -Max(e\EventState - 1320.0, 0.0) / 130.0, 0.0, -Max(e\EventState - 1300.0, 0.0) / 40.0, True)
						
						PositionEntity(e\room\Objects[1], EntityX(e\room\Objects[1], True), -Max(e\EventState - 1800.0, 0.0) / 5000.0, EntityZ(e\room\Objects[1], True), True)
						RotateEntity(e\room\Objects[1], -Max(e\EventState - 2040.0, 0.0) / 135.0, 0.0, -Max(e\EventState - 2040.0, 0.0) / 43.0, True)
						
						If Rand(300) = 2 Then
							If EntityDistanceSquared(e\room\Objects[0], me\Collider) < 6.25 Then PlaySound2(DecaySFX[Rand(3)], Camera, e\room\Objects[0], 3.0)
						EndIf
					EndIf
					
					If e\EventState < 2000.0 Then
						If (Not ChannelPlaying(e\SoundCHN)) Then e\SoundCHN = PlaySound_Strict(AlarmSFX[0])
					EndIf
					
					If e\EventState3 < 11.0 Then
						If (Not ChannelPlaying(e\SoundCHN2)) Then
							e\EventState3 = e\EventState3 + 1.0
							
							LoadEventSound(e, "SFX\Alarm\Alarm2_" + Int(e\EventState3) + ".ogg", 1)
							e\SoundCHN2 = PlaySound_Strict(e\Sound2)
						Else
							If Int(e\EventState3) = 8.0 Then me\BigCameraShake = 1.0
						EndIf
					EndIf
					If ((e\EventState Mod 600.0 > 300.0) And ((e\EventState + fps\Factor[0]) Mod 600.0 < 300.0)) Then
						i = Floor((e\EventState - 5000.0) / 600.0) + 1.0
						
						If i = 0 Then PlayAnnouncement("SFX\Room\Intro\IA\Scripted\Scripted6.ogg")
						If i > 0 And i < 26 Then
							If (Not CommotionState[i]) Then ; ~ Prevents the same commotion file from playing more then once
								PlaySound_Strict(LoadTempSound("SFX\Room\Intro\Commotion\Commotion" + i + ".ogg"))
								CommotionState[i] = True
							EndIf
						EndIf
						
						If i > 26 
							If e\room\NPC[0] <> Null Then RemoveNPC(e\room\NPC[0])
							
							For j = 0 To 1
								FreeEntity(e\room\Objects[i]) : e\room\Objects[i] = 0
							Next
							
							RemoveEvent(e)
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_cont1_173_intro
				;[Block]
				If PlayerRoom = e\room Then
					For r.Rooms = Each Rooms
						HideRoomsNoColl(r)
					Next
					ShowRoomsNoColl(e\room)
					CanSave = 1
					If (Not me\Terminated) And e\EventState2 = 0.0 Then
						If e\EventState3 > 0.0 Then
							ShouldPlay = 13
							; ~ Slow the player down to match his speed to the guards
							me\CurrSpeed = Min(me\CurrSpeed - (me\CurrSpeed * (0.008 / EntityDistance(e\room\NPC[3]\Collider, me\Collider)) * fps\Factor[0]), me\CurrSpeed)
							If e\EventState3 < 170.0 Then
								If e\EventState3 = 1.0 Then
									PositionEntity(Camera, x, y, z)
									HideEntity(me\Collider)
									PositionEntity(me\Collider, x, 0.302, z)
									RotateEntity(Camera, -70.0, 0.0, 0.0)
									
									opt\CurrMusicVolume = opt\MusicVolume
									
									StopStream_Strict(MusicCHN) : MusicCHN = 0
									MusicCHN = StreamSound_Strict("SFX\Music\" + Music[13] + ".ogg", opt\CurrMusicVolume * opt\MasterVolume)
									NowPlaying = ShouldPlay
									
									PlaySound_Strict(IntroSFX[Rand(8, 10)])
									me\BlurTimer = 1000.0
									me\LightFlash = 1.0
									me\Playable = False
									
									CreateConsoleMsg("")
									CreateConsoleMsg(GetLocalString("misc", "warning"), 255, 0, 0)
									CreateConsoleMsg("")
								EndIf
								
								If e\EventState3 < 3.0 Then
									e\EventState3 = e\EventState3 + (fps\Factor[0] / 100.0)
								ElseIf e\EventState3 < 15.0 Lor e\EventState3 >= 50.0
									e\EventState3 = e\EventState3 + (fps\Factor[0] / 30.0)
								EndIf
								
								If e\EventState3 < 15.0 Then
									x = e\room\x - 4248.0 * RoomScale
									y = 136.0 * RoomScale
									z = e\room\z + 8.0 * RoomScale
									
									If e\EventState3 - (fps\Factor[0] / 30.0) < 3.7 And e\EventState3 > 3.7 Then PlaySound_Strict(IntroSFX[0])
									If e\EventState3 - (fps\Factor[0] / 30.0) < 9.3 And e\EventState3 > 9.3 Then PlaySound_Strict(IntroSFX[1])
									
									If e\EventState3 < 14.0 Then
										StopMouseMovement()
										
										If e\EventState3 - (fps\Factor[0] / 30.0) < 12.0 And e\EventState3 > 12.0 Then PlaySound2(StepSFX(0, 0, 0), Camera, me\Collider, 8.0, 0.3)
										
										x = x + (e\room\x - 4072.0 * RoomScale - x) * Max((e\EventState3 - 10.0) / 4.0, 0.0) 
										
										If e\EventState3 < 10.0 Then
											y = y + (0.2 * Min(Max((e\EventState3 - 3.0) / 5.0, 0.0), 1.0))
										Else
											y = (y + 0.2) + (0.302 + 0.6 - (y + 0.2)) * Max((e\EventState3 - 10.0) / 4.0, 0.0) 
										EndIf
										
										z = z + (e\room\z + (104.0 * RoomScale) - z) * Min(Max((e\EventState3 - 3.0) / 5.0, 0.0), 1.0)
										
										; ~ I'm sorry you have to see this
										RotateEntity(Camera, (-70.0) + 70.0 * Min(Max((e\EventState3 - 3.0) / 5.0, 0.0), 1.0) + Sin(e\EventState3 * 12.857) * 5.0, (-60.0) * Max((e\EventState3 - 10.0) / 4.0, 0.0), Sin(e\EventState3 * 25.7) * 8.0)
										PositionEntity(Camera, x, y, z)
										If (Not EntityHidden(me\Collider)) Then HideEntity(me\Collider)
										PositionEntity(me\Collider, x, 0.302, z)
										me\DropSpeed = 0.0
									Else
										PositionEntity(me\Collider, EntityX(me\Collider), 0.302, EntityZ(me\Collider))
										ResetEntity(me\Collider)
										ShowEntity(me\Collider)
										me\DropSpeed = 0.0
										me\Playable = True
										
										For i = 0 To 1
											FreeSound_Strict(IntroSFX[i]) : IntroSFX[i] = 0
										Next
										
										CreateHintMsg(GetLocalString("msg", "paper"))
										
										e\EventState3 = 15.0
									EndIf
									CameraPitch = 0.0
									RotateEntity(me\Collider, 0.0, EntityYaw(Camera), 0.0)
								ElseIf e\EventState3 < 40.0
									If Inventory(0) <> Null Then
										CreateHintMsg(Format(GetLocalString("msg", "openinv"), key\Name[key\INVENTORY]))
										e\EventState3 = 40.0
									EndIf
								ElseIf e\EventState3 < 50.0
									If InvOpen Then
										CreateHintMsg(GetLocalString("msg", "doc.click"))
										e\EventState3 = 50.0
									EndIf
								EndIf
								If SelectedItem <> Null Then e\EventState3 = e\EventState3 + (fps\Factor[0] / 5.0)
							ElseIf e\EventState3 >= 150.0 And e\EventState3 < 700.0
								If e\room\NPC[3]\State = 7.0 Then
									If (Not e\room\NPC[3]\Sound2) Then
										e\room\NPC[3]\Sound2 = LoadSound_Strict("SFX\Room\Intro\Guard\Ulgrin\BeforeDoorOpen.ogg")
										e\room\NPC[3]\SoundCHN2 = PlaySound2(e\room\NPC[3]\Sound2, Camera, e\room\NPC[3]\Collider)
									EndIf
									UpdateSoundOrigin(e\room\NPC[3]\SoundCHN2, Camera, e\room\NPC[3]\Collider)
									
									If (Not ChannelPlaying(e\room\NPC[3]\SoundCHN2)) Then
										e\room\NPC[3]\Sound = LoadSound_Strict("SFX\Room\Intro\Guard\Ulgrin\ExitCell.ogg")
										e\room\NPC[3]\SoundCHN = PlaySound2(e\room\NPC[3]\Sound, Camera, e\room\NPC[3]\Collider)
										
										For i = 3 To 5
											e\room\NPC[i]\State = 9.0
										Next
										
										OpenCloseDoor(e\room\RoomDoors[4])
									EndIf
								Else
									If e\room\NPC[3]\Sound2 <> 0 Then FreeSound_Strict(e\room\NPC[3]\Sound2) : e\room\NPC[3]\Sound2 = 0
									
									e\EventState3 = Min(e\EventState3 + fps\Factor[0] / 4.0, 699.0)
									
									; ~ Outside the cell
									If DistanceSquared(EntityX(me\Collider), e\room\x - 4096.0 * RoomScale, EntityZ(me\Collider), e\room\z + 192.0 * RoomScale) > 2.25 Then
										If e\EventState3 > 250.0 Then
											StopChannel(e\room\NPC[3]\SoundCHN) : e\room\NPC[3]\SoundCHN = 0
											
											LoadNPCSound(e\room\NPC[3], "SFX\Room\Intro\Guard\Ulgrin\Escort" + Rand(2) + ".ogg")
											e\room\NPC[3]\SoundCHN = PlaySound2(e\room\NPC[3]\Sound, Camera, e\room\NPC[3]\Collider)
											
											e\room\NPC[3]\PathStatus = FindPath(e\room\NPC[3], e\room\x + 288.0 * RoomScale, 0.3, e\room\z - 713.0 * RoomScale)
											e\room\NPC[4]\PathStatus = FindPath(e\room\NPC[4], e\room\x + 288.0 * RoomScale, 0.3, e\room\z - 713.0 * RoomScale)
											
											OpenCloseDoor(e\room\RoomDoors[4])
											
											e\EventState3 = 710.0
										EndIf
									Else ; ~ Inside the cell
										e\room\NPC[3]\State = 9.0
										
										If e\EventState3 - (fps\Factor[0] / 4.0) < 350.0 And e\EventState3 >= 350.0 Then
											LoadNPCSound(e\room\NPC[3], "SFX\Room\Intro\Guard\Ulgrin\ExitCellRefuse" + Rand(2) + ".ogg")
											e\room\NPC[3]\SoundCHN = PlaySound2(e\room\NPC[3]\Sound, Camera, e\room\NPC[3]\Collider)
										ElseIf e\EventState3 - (fps\Factor[0] / 4.0) < 550.0 And e\EventState3 >= 550.0 
											LoadNPCSound(e\room\NPC[3], "SFX\Room\Intro\Guard\Ulgrin\CellGas" + Rand(2) + ".ogg")
											e\room\NPC[3]\SoundCHN = PlaySound2(e\room\NPC[3]\Sound, Camera, e\room\NPC[3]\Collider)
										ElseIf e\EventState3 > 630.0
											PositionEntity(me\Collider, EntityX(me\Collider), EntityY(me\Collider), Min(EntityZ(me\Collider), e\room\z + 490.0 * RoomScale))
											If e\room\RoomDoors[4]\Open Then
												OpenCloseDoor(e\room\RoomDoors[4])
												
												em.Emitters = CreateEmitter(e\room\x - 4191.0 * RoomScale, e\room\y + 373.0 * RoomScale, e\room\z + 159.0 * RoomScale, 0)
												em\RandAngle = 7.0 : em\Speed = 0.03 : em\SizeChange = 0.003 : em\room = PlayerRoom
												TurnEntity(em\OBJ, 90.0, 0.0, 0.0, True)
												
												em.Emitters = CreateEmitter(e\room\x - 4000.0 * RoomScale, e\room\y + 373.0 * RoomScale, e\room\z + 159.0 * RoomScale, 0)
												em\RandAngle = 7.0 : em\Speed = 0.03 : em\SizeChange = 0.003 : em\room = PlayerRoom
												TurnEntity(em\OBJ, 90.0, 0.0, 0.0, True)
											EndIf
											me\EyeIrritation = Max(me\EyeIrritation + (fps\Factor[0] * 4.0), 1.0)
											If Rand(1000) = 1 Then Kill(False)
										EndIf
									EndIf
								EndIf
							ElseIf e\EventState3 < 800.0
								e\EventState3 = e\EventState3 + (fps\Factor[0] / 4.0)
								If e\room\NPC[5]\State <> 11.0 Then
									If EntityDistanceSquared(e\room\NPC[3]\Collider, e\room\NPC[5]\Collider) > 25.0 And EntityDistanceSquared(e\room\NPC[4]\Collider, e\room\NPC[5]\Collider)
										If EntityDistanceSquared(e\room\NPC[5]\Collider, me\Collider) < 12.25
											For i = 3 To 5
												If i < 5 Then
													StopChannel(e\room\NPC[i]\SoundCHN) : e\room\NPC[i]\SoundCHN = 0
													If e\room\NPC[i]\Sound <> 0 Then FreeSound_Strict(e\room\NPC[i]\Sound) : e\room\NPC[i]\Sound = 0
												EndIf
												e\room\NPC[i]\State = 11.0 : e\room\NPC[i]\State3 = 1.0 : e\room\NPC[i]\Reload = 70.0 * 3.0
											Next
											e\room\NPC[5]\SoundCHN2 = PlaySound2(e\room\NPC[5]\Sound2, Camera, e\room\NPC[5]\Collider)
										EndIf
									EndIf
								EndIf
							ElseIf e\EventState3 < 900.0
								e\room\NPC[4]\Angle = 0.0
								
								If EntityX(me\Collider) < e\room\x - 5376.0 * RoomScale And e\EventStr = "" Then
									If Rand(3) = 1 Then
										e\EventStr = "Scripted\Scripted" + Rand(5) + ".ogg|Off.ogg|"
									Else
										; ~ GENERATE THE IA...
										; ~ ATTENTION...
										e\EventStr = "1\Attention" + Rand(0, 1) + ".ogg"
										Select Rand(3)
											Case 1
												;[Block]
												StrTemp = "Crew"
												e\EventStr = e\EventStr + "|2\Crew" + Rand(0, 5) + ".ogg"
												;[End Block]
											Case 2
												;[Block]
												StrTemp = "Scientist"
												e\EventStr = e\EventStr + "|2\Scientist" + Rand(0, 17) + ".ogg"
												;[End Block]
											Case 3
												;[Block]
												StrTemp = "Security"
												e\EventStr = e\EventStr + "|2\Security" + Rand(0, 5) + ".ogg"
												;[End Block]
										End Select
										
										If Rand(2) = 1 And StrTemp = "Scientist" Then
											; ~ CALL ON LINE...
											e\EventStr = e\EventStr + "|3\CallOnLine.ogg"
											
											e\EventStr = e\EventStr + "|Numbers\" + Rand(9) + ".ogg"
											If Rand(2) = 1 Then e\EventStr = e\EventStr + "|Numbers\" + Rand(9) + ".ogg"
										Else
											; ~ REPORT TO...
											e\EventStr = e\EventStr + "|3\Report" + Rand(0, 1) + ".ogg"
											
											Select StrTemp
												Case "Crew"
													;[Block]
													e\EventStr = e\EventStr + "|4\Crew" + Rand(0, 6) + ".ogg"
													If Rand(2) = 1 Then e\EventStr = e\EventStr + "|5\Crew" + Rand(0, 6) + ".ogg"
													;[End Block]
												Case "Scientist"
													;[Block]
													e\EventStr = e\EventStr + "|4\Scientist" + Rand(0, 7) + ".ogg"
													If Rand(2) = 1 Then e\EventStr = e\EventStr + "|5\Scientist0.ogg"
													;[End Block]
												Case "Security"
													;[Block]
													e\EventStr = e\EventStr + "|4\Security" + Rand(0, 5) + ".ogg"
													If Rand(2) = 1 Then e\EventStr = e\EventStr + "|5\Security" + Rand(0, 2) + ".ogg"
													;[End Block]
											End Select
										EndIf
										e\EventStr = e\EventStr + "|Off.ogg|"
									EndIf
								EndIf
								
								If e\room\NPC[6] <> Null Then
									If e\room\NPC[6]\State = 0.0 Then
										If e\room\RoomDoors[5]\Open Then
											If DistanceSquared(EntityX(me\Collider), e\room\x - 3328.0 * RoomScale, EntityZ(me\Collider), e\room\z - 1232.0 * RoomScale) < 25.0 Then
												e\room\NPC[6]\State = 1.0
												If e\EventStr = "Done" Then
													LoadEventSound(e, "SFX\Room\Intro\IA\Scripted\Announcement" + Rand(7) + ".ogg")
													e\SoundCHN = PlaySound_Strict(e\Sound)
												EndIf
											EndIf
										EndIf
									Else
										If EntityZ(e\room\NPC[6]\Collider) > e\room\z - 64.0 * RoomScale Then
											RotateEntity(e\room\NPC[6]\Collider, 0.0, CurveAngle(-90.0, EntityYaw(e\room\NPC[6]\Collider), 15.0), 0.0)
											If e\room\RoomDoors[5]\Open Then OpenCloseDoor(e\room\RoomDoors[5])
											If e\room\RoomDoors[5]\OpenState < 1.0 Then
												e\room\NPC[6]\State = 0.0 : e\room\NPC[6]\CurrSpeed = 0.0
												PointEntity(e\room\NPC[6]\Collider, e\room\OBJ)
												PositionEntity(e\room\NPC[6]\Collider, EntityX(e\room\Objects[16], True), EntityY(e\room\Objects[16], True), EntityZ(e\room\Objects[16], True))
												ResetEntity(e\room\NPC[6]\Collider)
											EndIf
										EndIf
									EndIf
								EndIf
								
								If e\room\NPC[8] <> Null Lor e\room\NPC[9] <> Null Lor e\room\NPC[10] <> Null Then
									If e\room\NPC[8]\State = 7.0 Then
										If DistanceSquared(EntityX(me\Collider), e\room\x - 6688.0 * RoomScale, EntityZ(me\Collider), e\room\z - 1252.0 * RoomScale) < 6.25 Then
											e\room\NPC[8]\State = 10.0
											e\room\NPC[9]\State = 10.0
											e\room\NPC[10]\State = 1.0
										EndIf
									Else
										If EntityX(e\room\NPC[8]\Collider) < e\room\x - 7100.0 * RoomScale Then
											For i = 8 To 10
												e\room\NPC[i]\State = 0.0
												RemoveNPC(e\room\NPC[i])
											Next
										EndIf
									EndIf
								EndIf
								
								If e\room\NPC[11] <> Null Then
									If e\room\NPC[11]\State = 15.0 Then
										If DistanceSquared(EntityX(me\Collider), e\room\x - 6688.0 * RoomScale, EntityZ(me\Collider), e\room\z - 1252.0 * RoomScale) < 6.25 Then e\room\NPC[11]\State = 16.0
									Else
										If EntityX(e\room\NPC[11]\Collider) > e\room\x - 2000.0 * RoomScale
											e\room\NPC[11]\State = 15.0
											RemoveNPC(e\room\NPC[11])
										EndIf
									EndIf
								EndIf
								
								e\room\NPC[5]\SoundCHN = LoopSound2(e\room\NPC[5]\Sound, e\room\NPC[5]\SoundCHN, Camera, e\room\NPC[5]\OBJ, 2.0, 0.5)
								
								If e\EventStr <> "" And e\EventStr <> "Done" Then
									If (Not e\SoundCHN) Then e\SoundCHN = PlaySound_Strict(LoadTempSound("SFX\Room\Intro\IA\On.ogg"))
									If (Not ChannelPlaying(e\SoundCHN)) Then
										StrTemp = Left(e\EventStr, Instr(e\EventStr, "|", 1) - 1)
										e\Sound = LoadSound_Strict("SFX\Room\Intro\IA\" + StrTemp)
										e\SoundCHN = PlaySound_Strict(e\Sound)
										e\EventStr = Right(e\EventStr, Len(e\EventStr) - Len(StrTemp) - 1)
										If e\EventStr = "" Then
											Temp = Rand(5)
											LoadNPCSound(e\room\NPC[3], "SFX\Room\Intro\Guard\Conversation" + Temp + "a.ogg")
											e\room\NPC[3]\SoundCHN = PlaySound2(e\room\NPC[3]\Sound, Camera, e\room\NPC[3]\Collider)
											LoadNPCSound(e\room\NPC[4], "SFX\Room\Intro\Guard\Conversation" + Temp + "b.ogg")
											e\room\NPC[4]\SoundCHN = PlaySound2(e\room\NPC[4]\Sound, Camera, e\room\NPC[4]\Collider)
											
											e\EventStr = "Done"
										EndIf
									EndIf
								EndIf
								
								Dist = DistanceSquared(EntityX(me\Collider), EntityX(e\room\NPC[3]\Collider), EntityZ(me\Collider), EntityZ(e\room\NPC[3]\Collider))
								
								If Dist < 9.0 Then
									e\room\NPC[3]\State3 = Min(Max(e\room\NPC[3]\State3 - fps\Factor[0], 0.0), 50.0)
								Else
									e\room\NPC[3]\State3 = Max(e\room\NPC[3]\State3 + fps\Factor[0], 50.0)
									If e\room\NPC[3]\State3 >= 70.0 * 8.0 And e\room\NPC[3]\State3 - fps\Factor[0] < 70.0 * 8.0 And e\room\NPC[3]\State = 7.0 Then
										If e\room\NPC[3]\State2 < 2.0 Then
											For i = 3 To 4
												StopChannel(e\room\NPC[i]\SoundCHN) : e\room\NPC[i]\SoundCHN = 0
												If e\room\NPC[i]\Sound <> 0 Then FreeSound_Strict(e\room\NPC[i]\Sound) : e\room\NPC[i]\Sound = 0
											Next
											
											e\room\NPC[3]\Sound = LoadSound_Strict("SFX\Room\Intro\Guard\Ulgrin\EscortRefuse" + Rand(2) + ".ogg")
											e\room\NPC[3]\SoundCHN = PlaySound2(e\room\NPC[3]\Sound, Camera, e\room\NPC[3]\Collider)
											e\room\NPC[3]\State2 = 3.0 : e\room\NPC[3]\State3 = 50.0
										ElseIf e\room\NPC[3]\State2 = 3.0
											For i = 3 To 4
												StopChannel(e\room\NPC[i]\SoundCHN) : e\room\NPC[i]\SoundCHN = 0
												If e\room\NPC[i]\Sound <> 0 Then FreeSound_Strict(e\room\NPC[i]\Sound) : e\room\NPC[i]\Sound = 0
											Next
											
											e\room\NPC[3]\Sound = LoadSound_Strict("SFX\Room\Intro\Guard\Ulgrin\EscortPissedOff" + Rand(2) + ".ogg")
											e\room\NPC[3]\SoundCHN = PlaySound2(e\room\NPC[3]\Sound, Camera, e\room\NPC[3]\Collider)
											e\room\NPC[3]\State2 = 4.0 : e\room\NPC[3]\State3 = 50.0
										ElseIf e\room\NPC[3]\State2 = 4.0
											For i = 3 To 4
												StopChannel(e\room\NPC[i]\SoundCHN) : e\room\NPC[i]\SoundCHN = 0
												If e\room\NPC[i]\Sound <> 0 Then FreeSound_Strict(e\room\NPC[i]\Sound) : e\room\NPC[i]\Sound = 0
											Next
											
											e\room\NPC[3]\Sound = LoadSound_Strict("SFX\Room\Intro\Guard\Ulgrin\EscortKill" + Rand(2) + ".ogg")
											e\room\NPC[3]\SoundCHN = PlaySound2(e\room\NPC[3]\Sound, Camera, e\room\NPC[3]\Collider)
											e\room\NPC[3]\State2 = 5.0 : e\room\NPC[3]\State3 = 50.0 + 70.0 * 2.5
										ElseIf e\room\NPC[3]\State2 = 5.0
											For i = 3 To 5
												e\room\NPC[i]\State = 11.0 : e\room\NPC[i]\State3 = 1.0
											Next
										EndIf
									EndIf
									If e\room\NPC[5]\State <> 11.0 Then
										If EntityDistanceSquared(e\room\NPC[3]\Collider, e\room\NPC[5]\Collider) > 25.0 And EntityDistanceSquared(e\room\NPC[4]\Collider, e\room\NPC[5]\Collider)
											If EntityDistanceSquared(e\room\NPC[5]\Collider, me\Collider) < 12.25
												For i = 3 To 5
													If i < 5 Then
														StopChannel(e\room\NPC[i]\SoundCHN) : e\room\NPC[i]\SoundCHN = 0
														If e\room\NPC[i]\Sound <> 0 Then FreeSound_Strict(e\room\NPC[i]\Sound) : e\room\NPC[i]\Sound = 0
													EndIf
													e\room\NPC[i]\State = 11.0 : e\room\NPC[i]\State3 = 1.0 : e\room\NPC[i]\Reload = 70.0 * 3.0
												Next
												e\room\NPC[5]\SoundCHN2 = PlaySound2(e\room\NPC[5]\Sound2, Camera, e\room\NPC[5]\Collider)
											EndIf
										EndIf
									EndIf
								EndIf
								
								If e\room\NPC[5]\State = 11.0 Then UpdateSoundOrigin(e\room\NPC[5]\SoundCHN2, Camera, e\room\NPC[5]\Collider)
								
								If e\room\NPC[3]\State <> 11.0 Then
									If Dist < PowTwo(Min(Max(4.0 - e\room\NPC[3]\State3 * 0.05, 1.5), 4.0)) Then
										If e\room\NPC[3]\PathStatus <> 1 Then
											e\room\NPC[3]\State = 7.0
											PointEntity(e\room\NPC[3]\OBJ, me\Collider)
											RotateEntity(e\room\NPC[3]\Collider, 0.0, CurveValue(EntityYaw(e\room\NPC[3]\OBJ), EntityYaw(e\room\NPC[3]\Collider), 20.0), 0.0, True)
											
											If e\room\NPC[3]\PathStatus = 2 Then
												e\room\NPC[3]\PathStatus = FindPath(e\room\NPC[3], e\room\x - 320.0 * RoomScale, e\room\y + 0.3, e\room\z - 704.0 * RoomScale)
												e\room\NPC[4]\PathStatus = FindPath(e\room\NPC[4], e\room\x - 320.0 * RoomScale, e\room\y + 0.3, e\room\z - 704.0 * RoomScale)
												e\room\NPC[3]\State = 3.0
											EndIf
										Else
											e\room\NPC[3]\State = 3.0
										EndIf
									Else
										e\room\NPC[3]\State = 7.0
										PointEntity(e\room\NPC[3]\OBJ, me\Collider)
										RotateEntity(e\room\NPC[3]\Collider, 0.0, CurveValue(EntityYaw(e\room\NPC[3]\OBJ), EntityYaw(e\room\NPC[3]\Collider), 20.0), 0.0, True)
										
										If Dist > 30.25 Then
											e\room\NPC[3]\PathStatus = 2
											If e\room\NPC[3]\State2 = 0.0 Then
												For i = 3 To 4
													StopChannel(e\room\NPC[i]\SoundCHN) : e\room\NPC[i]\SoundCHN = 0
													If e\room\NPC[i]\Sound <> 0 Then FreeSound_Strict(e\room\NPC[i]\Sound) : e\room\NPC[i]\Sound = 0
												Next
												
												e\room\NPC[3]\Sound = LoadSound_Strict("SFX\Room\Intro\Guard\Ulgrin\EscortRun.ogg")
												e\room\NPC[3]\SoundCHN = PlaySound2(e\room\NPC[3]\Sound, Camera, e\room\NPC[3]\Collider)
												PlaySound2(e\Sound, Camera, e\room\NPC[3]\Collider)
												
												e\room\NPC[3]\State2 = 1.0
											EndIf
											
											e\room\NPC[3]\State = 5.0
											e\room\NPC[3]\EnemyX = EntityX(me\Collider)
											e\room\NPC[3]\EnemyY = EntityY(me\Collider)
											e\room\NPC[3]\EnemyZ = EntityZ(me\Collider)
										EndIf
									EndIf
									
									Dist = EntityDistanceSquared(me\Collider, e\room\NPC[4]\Collider)
									
									If Dist > 2.25 And EntityDistanceSquared(e\room\NPC[3]\Collider, me\Collider) < EntityDistanceSquared(e\room\NPC[3]\Collider, e\room\NPC[4]\Collider) Then
										e\room\NPC[4]\State = 3.0
									Else
										e\room\NPC[4]\State = 5.0
										e\room\NPC[4]\EnemyX = EntityX(me\Collider)
										e\room\NPC[4]\EnemyY = EntityY(me\Collider)
										e\room\NPC[4]\EnemyZ = EntityZ(me\Collider)
									EndIf
								EndIf
								
								Dist = DistanceSquared(EntityX(me\Collider), EntityX(e\room\RoomDoors[2]\FrameOBJ, True), EntityZ(me\Collider), EntityZ(e\room\RoomDoors[2]\FrameOBJ, True))
								
								If DistanceSquared(EntityX(e\room\NPC[3]\Collider), EntityX(e\room\RoomDoors[2]\FrameOBJ, True), EntityZ(e\room\NPC[3]\Collider), EntityZ(e\room\RoomDoors[2]\FrameOBJ, True)) < 20.25 And Dist < 25.0 Then
									e\room\NPC[0] = CreateNPC(NPCTypeGuard, EntityX(e\room\Objects[0], True), EntityY(e\room\Objects[0], True), EntityZ(e\room\Objects[0], True))
									e\room\NPC[0]\Angle = 180.0
									
									For i = 5 To 7 Step 2
										If e\room\NPC[i] <> Null Then RemoveNPC(e\room\NPC[i])
									Next
									
									; ~ Remove D-9341 texture
									DeleteSingleTextureEntryFromCache(n_I\NPCTextureID[NPC_CLASS_D_D9341_TEXTURE])
									
									For i = 3 To 4
										StopChannel(e\room\NPC[i]\SoundCHN) : e\room\NPC[i]\SoundCHN = 0
										If e\room\NPC[i]\Sound <> 0 Then FreeSound_Strict(e\room\NPC[i]\Sound) : e\room\NPC[i]\Sound = 0
										e\room\NPC[i]\State = 9.0
									Next
									
									e\room\NPC[3]\Sound = LoadSound_Strict("SFX\Room\Intro\Guard\Ulgrin\EscortDone" + Rand(5) + ".ogg")
									e\room\NPC[3]\SoundCHN = PlaySound2(e\room\NPC[3]\Sound, Camera, e\room\NPC[3]\Collider)
									
									e\room\NPC[1] = CreateNPC(NPCTypeD, EntityX(e\room\Objects[1], True), 0.5, EntityZ(e\room\Objects[1], True))
									e\room\NPC[1]\State3 = 4.0
									PointEntity(e\room\NPC[1]\Collider, e\room\Objects[6])
									
									e\room\NPC[2] = CreateNPC(NPCTypeD, EntityX(e\room\Objects[2], True), 0.5, EntityZ(e\room\Objects[2], True))
									e\room\NPC[2]\State3 = 5.0
									PointEntity(e\room\NPC[2]\Collider, e\room\Objects[6])
									ChangeNPCTextureID(e\room\NPC[2], NPC_CLASS_D_CLASS_D_TEXTURE)
									
									OpenCloseDoor(e\room\RoomDoors[3])
									
									e\EventState3 = 905.0
								EndIf
							ElseIf e\EventState3 <= 905.0
								If (Not ChannelPlaying(e\room\NPC[3]\SoundCHN)) And e\room\NPC[3]\Frame < 358.0 Then
									LoadNPCSound(e\room\NPC[3], "SFX\Room\Intro\Guard\Ulgrin\OhAndByTheWay.ogg")
									e\room\NPC[3]\SoundCHN = PlaySound2(e\room\NPC[3]\Sound, Camera, e\room\NPC[3]\Collider)
									SetNPCFrame(e\room\NPC[3], 358.0)
									e\room\NPC[3]\State = 8.0
								ElseIf e\room\NPC[3]\Frame >= 358.0 Then
									PointEntity(e\room\NPC[3]\Collider, me\Collider)
									RotateEntity(e\room\NPC[3]\Collider, 0.0, EntityYaw(e\room\NPC[3]\Collider), 0.0)
									
									If e\room\NPC[3]\Frame <= 481.5 Then
										Local PrevAnimFrame# = e\room\NPC[3]\Frame
										
										AnimateNPC(e\room\NPC[3], 358.0, 482.0, 0.4, False)
									Else
										AnimateNPC(e\room\NPC[3], 483.0, 607.0, 0.2, True)
										If InteractObject(e\room\NPC[3]\OBJ, 2.25) Then
											SelectedItem = CreateItem("Document SCP-173", "paper", 0.0, 0.0, 0.0)
											EntityType(SelectedItem\Collider, HIT_ITEM)
											EntityParent(SelectedItem\Collider, 0)
											
											PickItem(SelectedItem)
											
											OpenCloseDoor(e\room\RoomDoors[2])
											e\room\NPC[3]\State3 = 0.0
											SetNPCFrame(e\room\NPC[3], 608.0)
											
											e\EventState3 = 910.0
										EndIf
									EndIf
								EndIf
							Else
								If e\room\NPC[3]\State3 = 0.0 Then
									If e\room\NPC[3]\Frame <= 620.5 And e\room\NPC[3]\State = 8.0 Then
										AnimateNPC(e\room\NPC[3], 608.0, 621.0, 0.4, False)
									Else
										e\room\NPC[3]\Angle = EntityYaw(e\room\NPC[3]\Collider)
										e\room\NPC[3]\State = 9.0 : e\room\NPC[3]\State3 = 1.0
										e\room\NPC[4]\State = 9.0
									EndIf
								Else
									If e\room\RoomDoors[2]\Open Then
										Temp = 1.0
										If SelectedItem <> Null Then Temp = 3.0
										e\room\NPC[3]\State3 = Max(e\room\NPC[3]\State3 + (fps\Factor[0] / Temp), 50.0)
										If e\room\NPC[3]\State3 >= 70.0 * 8.0 And e\room\NPC[3]\State3 - (fps\Factor[0] / Temp) < 70.0 * 8.0 And e\room\NPC[3]\State = 9.0 Then
											If e\room\NPC[3]\State2 < 2.0 Then
												StopChannel(e\room\NPC[3]\SoundCHN) : e\room\NPC[3]\SoundCHN = 0
												LoadNPCSound(e\room\NPC[3], "SFX\Room\Intro\Guard\Ulgrin\EscortRefuse" + Rand(2) + ".ogg")
												e\room\NPC[3]\SoundCHN = PlaySound2(e\room\NPC[3]\Sound, Camera, e\room\NPC[3]\Collider)
												e\room\NPC[3]\State2 = 3.0 : e\room\NPC[3]\State3 = 50.0
											ElseIf e\room\NPC[3]\State2 = 3.0
												StopChannel(e\room\NPC[3]\SoundCHN) : e\room\NPC[3]\SoundCHN = 0
												LoadNPCSound(e\room\NPC[3], "SFX\Room\Intro\Guard\Ulgrin\EscortPissedOff" + Rand(2) + ".ogg")
												e\room\NPC[3]\SoundCHN = PlaySound2(e\room\NPC[3]\Sound, Camera, e\room\NPC[3]\Collider)
												e\room\NPC[3]\State2 = 4.0 : e\room\NPC[3]\State3 = 50.0
											ElseIf e\room\NPC[3]\State2 = 4.0
												StopChannel(e\room\NPC[3]\SoundCHN) : e\room\NPC[3]\SoundCHN = 0
												LoadNPCSound(e\room\NPC[3], "SFX\Room\Intro\Guard\Ulgrin\EscortKill" + Rand(2) + ".ogg")
												e\room\NPC[3]\SoundCHN = PlaySound2(e\room\NPC[3]\Sound, Camera, e\room\NPC[3]\Collider)
												e\room\NPC[3]\State2 = 5.0 : e\room\NPC[3]\State3 = 50.0 + (70.0 * 2.5)
											ElseIf e\room\NPC[3]\State2 = 5.0
												For i = 3 To 4
													e\room\NPC[i]\State = 11.0 : e\room\NPC[i]\State3 = 1.0
												Next
												For i = 2 To 3
													OpenCloseDoor(e\room\RoomDoors[i])
												Next
											EndIf
										EndIf
										If DistanceSquared(EntityX(me\Collider), e\room\x, EntityZ(me\Collider), e\room\z) < 16.0 Then
											For i = 1 To 2
												OpenCloseDoor(e\room\RoomDoors[i])
											Next
											For i = 3 To 4
												e\room\NPC[i]\State = 0.0
											Next
											
											StopChannel(e\room\NPC[3]\SoundCHN) : e\room\NPC[3]\SoundCHN = 0
											
											e\EventState3 = 0.0
										EndIf
									EndIf
								EndIf
							EndIf
							
							If e\room\NPC[7] <> Null Then
								RotateEntity(e\room\NPC[7]\Collider, 0.0, 180.0 + Sin(MilliSecs2() / 20.0) * 3.0, 0.0, True)
								PositionEntity(e\room\NPC[7]\Collider, EntityX(e\room\Objects[11], True), EntityY(e\room\Objects[11], True), EntityZ(e\room\Objects[11], True))
								ResetEntity(e\room\NPC[7]\Collider)
								
								If e\room\NPC[6]\State = 1.0 Then
									If e\room\NPC[7]\Sound <> 0 Then e\room\NPC[7]\SoundCHN = LoopSound2(e\room\NPC[7]\Sound, e\room\NPC[7]\SoundCHN, Camera, e\room\NPC[7]\Collider, 7.0)
								EndIf
							EndIf
						Else
							If IntroSFX[4] <> 0 Then e\SoundCHN2 = LoopSound2(IntroSFX[4], e\SoundCHN2, Camera, e\room\Objects[4], 6.0)
							
							If e\EventState = 0.0 Then
								For i = 0 To 1
									IntroSFX[i] = LoadSound_Strict("SFX\Room\Intro\Ew" + (i + 1) + ".ogg")
								Next
								IntroSFX[2] = LoadSound_Strict("SFX\Room\Intro\Horror.ogg")
								IntroSFX[3] = LoadSound_Strict("SFX\Room\Intro\See173.ogg")
								IntroSFX[4] = LoadSound_Strict("SFX\Room\Intro\173Chamber.ogg")
								
								HideDistance = 68.0
								
								PositionEntity(me\Collider, EntityX(e\room\Objects[5], True), EntityY(e\room\Objects[5], True), EntityZ(e\room\Objects[5], True))
								ResetEntity(me\Collider)
								
								PositionEntity(n_I\Curr173\Collider, EntityX(e\room\Objects[6], True), EntityY(e\room\Objects[6], True), EntityZ(e\room\Objects[6], True))
								RotateEntity(n_I\Curr173\Collider, 0.0, 0.0, 0.0, True)
								ResetEntity(n_I\Curr173\Collider)
								n_I\Curr173\Angle = 90.0
								n_I\Curr173\Idle = 1
								
								e\room\NPC[3] = CreateNPC(NPCTypeGuard, EntityX(e\room\Objects[7], True), EntityY(e\room\Objects[7], True), EntityZ(e\room\Objects[7], True))
								RotateEntity(e\room\NPC[3]\Collider, 0.0, e\room\Angle + 180.0, 0.0)
								e\room\NPC[3]\State = 7.0
								
								e\room\NPC[4] = CreateNPC(NPCTypeGuard, EntityX(e\room\Objects[8], True), EntityY(e\room\Objects[8], True), EntityZ(e\room\Objects[8], True))
								RotateEntity(e\room\NPC[4]\Collider, 0.0, e\room\Angle + 135.0, 0.0)
								e\room\NPC[4]\State = 7.0
								
								e\room\NPC[5] = CreateNPC(NPCTypeGuard, EntityX(e\room\Objects[9], True), EntityY(e\room\Objects[9], True), EntityZ(e\room\Objects[9], True))
								e\room\NPC[5]\Sound = LoadSound_Strict("SFX\Room\Intro\Guard\Music" + Rand(5) + ".ogg")
								RotateEntity(e\room\NPC[5]\Collider, 0.0, e\room\Angle + 180.0, 0.0, True)
								e\room\NPC[5]\State = 7.0
								e\room\NPC[5]\Sound2 = LoadSound_Strict("SFX\Room\Intro\Guard\PlayerEscape.ogg")
								
								e\room\NPC[6] = CreateNPC(NPCTypeD, EntityX(e\room\Objects[10], True), EntityY(e\room\Objects[10], True), EntityZ(e\room\Objects[10], True))
								ChangeNPCTextureID(e\room\NPC[6], NPC_CLASS_D_FRANKLIN_TEXTURE)
								
								e\room\NPC[7] = CreateNPC(NPCTypeD, EntityX(e\room\Objects[11], True), EntityY(e\room\Objects[11], True), EntityZ(e\room\Objects[11], True))
								e\room\NPC[7]\Sound = LoadSound_Strict("SFX\Room\Intro\Scientist\Conversation.ogg")
								e\room\NPC[7]\State = 6.0
								SetNPCFrame(e\room\NPC[7], 182.0)
								ChangeNPCTextureID(e\room\NPC[7], NPC_CLASS_D_SCIENTIST_TEXTURE)
								
								e\room\NPC[8] = CreateNPC(NPCTypeGuard, EntityX(e\room\Objects[12], True), EntityY(e\room\Objects[12], True), EntityZ(e\room\Objects[12], True))
								e\room\NPC[8]\State = 7.0
								
								e\room\NPC[9] = CreateNPC(NPCTypeGuard, EntityX(e\room\Objects[13], True), EntityY(e\room\Objects[13], True), EntityZ(e\room\Objects[13], True))
								e\room\NPC[9]\State = 7.0
								
								e\room\NPC[10] = CreateNPC(NPCTypeD, EntityX(e\room\Objects[14], True), EntityY(e\room\Objects[14], True), EntityZ(e\room\Objects[14], True))
								e\room\NPC[10]\State2 = 1.0
								ChangeNPCTextureID(e\room\NPC[10], NPC_CLASS_D_D9341_TEXTURE)
								
								e\room\NPC[11] = CreateNPC(NPCTypeGuard, EntityX(e\room\Objects[15], True), EntityY(e\room\Objects[15], True), EntityZ(e\room\Objects[15], True))
								e\room\NPC[11]\State = 15.0
								CreateNPCAsset(e\room\NPC[11])
								
								Pvt = CreatePivot()
								RotateEntity(Pvt, 90.0, 0.0, 0.0)
								For i = 8 To 11
									PositionEntity(Pvt, EntityX(e\room\NPC[i]\Collider), EntityY(e\room\NPC[i]\Collider), EntityZ(e\room\NPC[i]\Collider))
									EntityPick(Pvt, 20.0)
									If PickedEntity() <> 0 Then
										PositionEntity(e\room\NPC[i]\Collider, PickedX(), PickedY(), PickedZ(), True)
										AlignToVector(e\room\NPC[i]\Collider, -PickedNX(), -PickedNY(), -PickedNZ(), 3.0)
										If i < 11 Then
											RotateEntity(e\room\NPC[i]\Collider, 0.0, 90.0, 0.0)
										Else
											RotateEntity(e\room\NPC[i]\Collider, 0.0, -90.0, 0.0)
										EndIf
									EndIf
								Next
								FreeEntity(Pvt)
								
								e\EventState3 = 1.0
								e\EventState = 1.0
							ElseIf e\EventState < 10000.0
								If ChannelPlaying(e\SoundCHN) Then
									e\room\NPC[6]\State = 6.0
									If AnimTime(e\room\NPC[6]\OBJ) >= 325.0 Then
										Animate2(e\room\NPC[6]\OBJ, AnimTime(e\room\NPC[6]\OBJ), 326.0, 328.0, 0.02, False)
									Else
										Animate2(e\room\NPC[6]\OBJ, AnimTime(e\room\NPC[6]\OBJ), 320.0, 328.0, 0.05, False)
									EndIf
								Else
									Animate2(e\room\NPC[6]\OBJ, AnimTime(e\room\NPC[6]\OBJ), 328.0, 320.0, -0.02, False)
								EndIf
								
								If IntroSFX[3] <> 0 Then
									If PlayerSees173(n_I\Curr173) Then
										CreateHintMsg(Format(GetLocalString("msg", "blink"), key\Name[key\BLINK]))
										PlaySound_Strict(IntroSFX[3])
										FreeSound_Strict(IntroSFX[3]) : IntroSFX[3] = 0
									EndIf
								EndIf
								
								e\EventState = Min(e\EventState + (fps\Factor[0] / 3.0), 5000.0)
								If e\EventState >= 130.0 And e\EventState - (fps\Factor[0] / 3.0) < 130.0 Then
									LoadEventSound(e, "SFX\Room\Intro\Scientist\Franklin\EnterChamber.ogg")
									e\SoundCHN = PlaySound_Strict(e\Sound)
									
									For i = 3 To 4
										If e\room\NPC[i] <> Null Then RemoveNPC(e\room\NPC[i])
									Next
								ElseIf e\EventState > 230.0
									Temp = True
									For i = 1 To 2
										If DistanceSquared(EntityX(e\room\NPC[i]\Collider), EntityX(e\room\Objects[i + 2], True), EntityZ(e\room\NPC[i]\Collider), EntityZ(e\room\Objects[i + 2], True)) > 0.09 Then
											PointEntity(e\room\NPC[i]\OBJ, e\room\Objects[i + 2])
											RotateEntity(e\room\NPC[i]\Collider, 0.0, CurveValue(EntityYaw(e\room\NPC[i]\OBJ), EntityYaw(e\room\NPC[i]\Collider), 15.0), 0.0)
											If e\EventState > (200.0 + (i * 30.0)) Then e\room\NPC[i]\State = 1.0
											Temp = False
										Else
											e\room\NPC[i]\State = 0.0
											
											PointEntity(e\room\NPC[i]\OBJ, e\room\Objects[6])
											RotateEntity(e\room\NPC[i]\Collider, 0.0, CurveValue(EntityYaw(e\room\NPC[i]\OBJ), EntityYaw(e\room\NPC[i]\Collider), 15.0), 0.0)
										EndIf
									Next
									
									If EntityX(me\Collider) < e\room\x + 696.0 * RoomScale Then
										If e\EventState >= 450.0 And e\EventState - (fps\Factor[0] / 3.0) < 450.0 Then
											LoadEventSound(e, "SFX\Room\Intro\Scientist\Franklin\Refuse1.ogg")
											e\SoundCHN = PlaySound_Strict(e\Sound)
										ElseIf e\EventState >= 650.0 And e\EventState - (fps\Factor[0] / 3.0) < 650.0
											LoadEventSound(e, "SFX\Room\Intro\Scientist\Franklin\Refuse2.ogg")
											e\SoundCHN = PlaySound_Strict(e\Sound)
										ElseIf e\EventState >= 850.0 And e\EventState - (fps\Factor[0] / 3.0) < 850.0
											LoadEventSound(e, "SFX\Room\Intro\Scientist\Franklin\Refuse3.ogg")
											e\SoundCHN = PlaySound_Strict(e\Sound)
											
											OpenCloseDoor(e\room\RoomDoors[1])
										ElseIf e\EventState > 1000.0
											e\room\NPC[0]\State = 1.0 : e\room\NPC[0]\State2 = 10.0 : e\room\NPC[0]\State3 = 1.0
											e\room\NPC[3]\State = 11.0
											OpenCloseDoor(e\room\RoomDoors[2])
											e\EventState2 = 1.0
											Exit
										EndIf
										If e\EventState > 850.0 Then PositionEntity(me\Collider, Min(EntityX(me\Collider), e\room\x + 640.0 * RoomScale), EntityY(me\Collider), EntityZ(me\Collider))
									ElseIf Temp = True
										e\EventState = 10000.0
										OpenCloseDoor(e\room\RoomDoors[1])
									EndIf
								EndIf
								
								e\room\NPC[6]\State = 7.0
							ElseIf e\EventState < 14000.0 ; ~ Player is inside the room
								e\EventState = Min(e\EventState + fps\Factor[0], 13000.0)
								
								For i = 1 To 2
									PointEntity(e\room\NPC[i]\OBJ, e\room\Objects[6])
									RotateEntity(e\room\NPC[i]\Collider, 0.0, CurveValue(EntityYaw(e\room\NPC[i]\OBJ), EntityYaw(e\room\NPC[i]\Collider), 15.0), 0.0)
								Next
								If e\EventState < 10300.0 Then PositionEntity(me\Collider, Max(EntityX(me\Collider), e\room\x + 640.0 * RoomScale), EntityY(me\Collider), EntityZ(me\Collider))
								
								e\room\NPC[6]\State = 6.0
								PointEntity(e\room\NPC[6]\OBJ, n_I\Curr173\Collider)
								RotateEntity(e\room\NPC[6]\Collider, 0.0, CurveValue(EntityYaw(e\room\NPC[6]\OBJ), EntityYaw(e\room\NPC[6]\Collider), 50.0), 0.0, True)
								
								If e\EventState >= 10300.0 And e\EventState - fps\Factor[0] < 10300.0 Then
									LoadEventSound(e, "SFX\Room\Intro\Scientist\Franklin\Approach173.ogg")
									e\SoundCHN = PlaySound_Strict(e\Sound)
									
									PositionEntity(me\Collider, Max(EntityX(me\Collider), e\room\x + 640.0 * RoomScale), EntityY(me\Collider), EntityZ(me\Collider))
								ElseIf e\EventState >= 10440.0 And e\EventState - fps\Factor[0] < 10440.0
									OpenCloseDoor(e\room\RoomDoors[1])
								ElseIf e\EventState >= 10740.0 And e\EventState - fps\Factor[0] < 10740.0
									LoadEventSound(e, "SFX\Room\Intro\Scientist\Franklin\Problem.ogg")
									e\SoundCHN = PlaySound_Strict(e\Sound)
								ElseIf e\EventState >= 11145.0 And e\EventState - fps\Factor[0] < 11145.0
									PlaySound_Strict(IntroSFX[Rand(8, 10)])
									e\room\NPC[1]\Sound = LoadSound_Strict("SFX\Room\Intro\ClassD\DontLikeThis.ogg")
									e\room\NPC[1]\SoundCHN = PlaySound2(e\room\NPC[1]\Sound, Camera, e\room\NPC[1]\Collider)
								ElseIf e\EventState >= 11561.0 And e\EventState - fps\Factor[0] < 11561.0
									e\EventState = 14000.0
									PlaySound_Strict(IntroSFX[2])
									
									e\room\NPC[2]\Sound = LoadSound_Strict("SFX\Room\Intro\ClassD\Breen.ogg")
									e\room\NPC[2]\SoundCHN = PlaySound2(e\room\NPC[2]\Sound, Camera, e\room\NPC[2]\Collider)
								EndIf
								
								If e\EventState >= 10440.0 And e\EventState - fps\Factor[0] < 11561.0 Then
									If EntityX(me\Collider) < EntityX(e\room\RoomDoors[1]\FrameOBJ, True)
										If e\room\NPC[0]\State <> 12.0 Then
											e\room\NPC[0]\Sound = LoadSound_Strict("SFX\Room\Intro\Guard\Balcony\Alert" + Rand(2) + ".ogg")
											e\room\NPC[0]\SoundCHN = PlaySound2(e\room\NPC[0]\Sound, Camera, e\room\NPC[0]\Collider, 20.0)
											e\room\NPC[0]\State2 = 1.0
											e\room\NPC[0]\State = 12.0
										EndIf
									EndIf
								EndIf
								
								If e\EventState > 10300.0 Then
									If e\EventState > 10560.0 Then
										If e\EventState < 10750.0 Then
											e\room\NPC[1]\State = 1.0 : e\room\NPC[1]\CurrSpeed = 0.005
										Else
											e\room\NPC[1]\State = 0.0
											e\room\NPC[1]\CurrSpeed = CurveValue(0.0, e\room\NPC[1]\CurrSpeed, 10.0)
										EndIf
									EndIf
									
									If AnimTime(e\room\NPC[6]\OBJ) >= 325.0 Then
										Animate2(e\room\NPC[6]\OBJ, AnimTime(e\room\NPC[6]\OBJ), 326.0, 328.0, 0.02, False)
									Else
										Animate2(e\room\NPC[6]\OBJ, AnimTime(e\room\NPC[6]\OBJ), 320.0, 328.0, 0.05, False)
									EndIf
								EndIf
							ElseIf e\EventState < 20000.0
								Pvt = CreatePivot()
								PositionEntity(Pvt, EntityX(Camera), EntityY(n_I\Curr173\Collider, True) - 0.05, EntityZ(Camera))
								PointEntity(Pvt, n_I\Curr173\Collider)
								RotateEntity(me\Collider, EntityPitch(me\Collider), CurveAngle(EntityYaw(Pvt), EntityYaw(me\Collider), 40.0), 0.0)
								
								TurnEntity(Pvt, 90.0, 0.0, 0.0)
								CameraPitch = CurveAngle(EntityPitch(Pvt), CameraPitch + 90.0, 40.0)
								CameraPitch = CameraPitch - 90.0
								FreeEntity(Pvt)
								
								e\room\NPC[6]\State = 6.0
								PointEntity(e\room\NPC[6]\OBJ, n_I\Curr173\Collider)
								RotateEntity(e\room\NPC[6]\Collider, 0.0, CurveValue(EntityYaw(e\room\NPC[6]\OBJ), EntityYaw(e\room\NPC[6]\Collider), 20.0), 0.0, True)
								Animate2(e\room\NPC[6]\OBJ, AnimTime(e\room\NPC[6]\OBJ), 357.0, 381.0, 0.05)
								
								e\EventState = Min(e\EventState + fps\Factor[0], 19000.0)
								If e\EventState < 14100.0 Then
									If e\EventState < 14060.0 Then
										me\BlinkTimer = Max((14000.0 - e\EventState) / 2.0 - Rnd(0.0, 1.0), -10.0)
										If me\BlinkTimer = -10.0 Then
											PointEntity(n_I\Curr173\Collider, e\room\NPC[1]\OBJ)
											RotateEntity(n_I\Curr173\Collider, 0.0, EntityYaw(n_I\Curr173\Collider), 0)
											MoveEntity(n_I\Curr173\Collider, 0.0, 0.0, n_I\Curr173\Speed * 0.6 * fps\Factor[0])
											
											n_I\Curr173\SoundCHN = LoopSound2(StoneDragSFX, n_I\Curr173\SoundCHN, Camera, n_I\Curr173\Collider, 10.0, n_I\Curr173\State)
											
											n_I\Curr173\State = CurveValue(1.0, n_I\Curr173\State, 3.0)
										Else
											n_I\Curr173\State = Max(0.0, n_I\Curr173\State - fps\Factor[0] / 20.0)
										EndIf
									ElseIf e\EventState < 14065.0
										me\BlinkTimer = -10.0
										If (Not e\room\NPC[1]\IsDead) Then
											PlaySound2(NeckSnapSFX[Rand(0, 2)], Camera, n_I\Curr173\Collider)
											e\room\NPC[1]\IsDead = True
										EndIf
										PositionEntity(n_I\Curr173\Collider, EntityX(e\room\NPC[1]\OBJ), EntityY(n_I\Curr173\Collider), EntityZ(e\room\NPC[1]\OBJ))
										ResetEntity(n_I\Curr173\Collider)
										PointEntity(n_I\Curr173\Collider, e\room\NPC[2]\Collider)
										
										RotateEntity(e\room\NPC[2]\Collider, 0.0, EntityYaw(e\room\NPC[2]\Collider), 0.0)
										Animate2(e\room\NPC[2]\OBJ, AnimTime(e\room\NPC[2]\OBJ), 406.0, 382.0, (-0.01) * 15.0)
										MoveEntity(e\room\NPC[2]\Collider, 0.0, 0.0, (-0.01) * fps\Factor[0])
										
										StopChannel(e\room\NPC[0]\SoundCHN) : e\room\NPC[0]\SoundCHN = 0
										LoadNPCSound(e\room\NPC[0], "SFX\Room\Intro\Guard\Balcony\WTF" + Rand(2) + ".ogg")
										e\room\NPC[0]\SoundCHN = PlaySound2(e\room\NPC[0]\Sound, Camera, e\room\NPC[0]\Collider, 20.0)
										e\room\NPC[0]\State = 12.0 : e\room\NPC[0]\State2 = 0.0 : e\room\NPC[0]\Angle = 180.0
									Else
										If (Not e\room\NPC[2]\Sound) Then 
											LoadNPCSound(e\room\NPC[2], "SFX\Room\Intro\ClassD\Gasp.ogg")
											PlaySound2(e\room\NPC[2]\Sound, Camera, e\room\NPC[2]\Collider, 8.0)
										EndIf
									EndIf
									
									If e\EventState > 14080.0 And e\EventState - fps\Factor[0] < 14080.0 Then PlaySound_Strict(IntroSFX[Rand(8, 10)])
									me\BigCameraShake = 3.0
								ElseIf e\EventState < 14200.0
									e\room\NPC[0]\State = 8.0
									If e\EventState > 14115.0 Then
										If e\room\NPC[2]\Sound <> 0 Then FreeSound_Strict(e\room\NPC[2]\Sound) : e\room\NPC[2]\Sound = 0
										If (Not e\room\NPC[2]\IsDead) Then
											PlaySound2(NeckSnapSFX[1], Camera, e\room\NPC[2]\Collider, 8.0)
											e\room\NPC[2]\IsDead = True
										EndIf
										PositionEntity(n_I\Curr173\Collider, EntityX(e\room\NPC[2]\OBJ), EntityY(n_I\Curr173\Collider), EntityZ(e\room\NPC[2]\OBJ))
										ResetEntity(n_I\Curr173\Collider)
										PointEntity(n_I\Curr173\Collider, me\Collider)
									EndIf
									If e\EventState < 14130.0 Then
										SetNPCFrame(e\room\NPC[2], 50.0)
										me\BlinkTimer = -10.0 : me\LightBlink = 1.0
									Else
										n_I\Curr173\Idle = 0
									EndIf
									If e\EventState > 14100.0 And e\EventState - fps\Factor[0] < 14100.0 Then PlaySound_Strict(IntroSFX[6])
									If e\EventState < 14150.0 Then me\BigCameraShake = 5.0
								Else
									If e\EventState > 14300.0 Then
										If e\EventState > 14600.0 And e\EventState < 14700.0 Then
											me\BlinkTimer = -10.0
											me\LightBlink = 1.0
										EndIf
										If EntityX(me\Collider) < e\room\x + 696.0 * RoomScale Then e\EventState = 20000.0
									EndIf
								EndIf
							ElseIf e\EventState < 30000.0
								e\EventState = Min(e\EventState + fps\Factor[0], 30000.0)
								If e\EventState < 20100.0 Then
									me\BigCameraShake = 2.0
								Else
									If e\EventState < 20200.0 Then
										If e\EventState > 20105.0 And e\EventState - fps\Factor[0] < 20105.0 Then
											PlaySound_Strict(IntroSFX[7])
											PositionEntity(e\room\NPC[0]\Collider, EntityX(e\room\Objects[17], True), EntityY(e\room\Objects[17], True), EntityZ(e\room\Objects[17], True))
											ResetEntity(e\room\NPC[0]\Collider)
											
											StopChannel(e\room\NPC[0]\SoundCHN) : e\room\NPC[0]\SoundCHN = 0
											LoadNPCSound(e\room\NPC[0], "SFX\Room\Intro\Guard\Balcony\OhShit.ogg")
											e\room\NPC[0]\SoundCHN = PlaySound2(e\room\NPC[0]\Sound, Camera, e\room\NPC[0]\Collider, 20.0)
										EndIf
										If e\EventState > 20105.0 Then
											n_I\Curr173\Idle = 1
											PointEntity(e\room\NPC[0]\Collider, n_I\Curr173\OBJ)
											PositionEntity(n_I\Curr173\Collider, EntityX(e\room\Objects[18], True), EntityY(e\room\Objects[18], True), EntityZ(e\room\Objects[18], True))
											ResetEntity(n_I\Curr173\Collider)
											PointEntity(n_I\Curr173\Collider, e\room\NPC[0]\Collider)
										EndIf
										me\BlinkTimer = -10.0 : me\LightBlink = 1.0 : me\BigCameraShake = 3.0
									ElseIf e\EventState < 20300.0
										PointEntity(e\room\NPC[0]\Collider, n_I\Curr173\Collider)
										e\room\NPC[0]\State = 2.0
										UpdateSoundOrigin(e\room\NPC[0]\SoundCHN, Camera, e\room\NPC[0]\Collider, 20.0)
										If e\EventState > 20260.0 And e\EventState - fps\Factor[0] < 20260.0 Then PlaySound_Strict(IntroSFX[Rand(8, 10)])
									Else
										If e\EventState - fps\Factor[0] < 20300.0 Then
											me\BlinkTimer = -10.0
											me\LightBlink = 1.0
											me\BigCameraShake = 3.0
											PlaySound_Strict(IntroSFX[Rand(8, 10)])
											
											If e\room\NPC[0]\Sound <> 0 Then FreeSound_Strict(e\room\NPC[0]\Sound) : e\room\NPC[0]\Sound = 0
											e\room\NPC[0]\SoundCHN = PlaySound2(NeckSnapSFX[1], Camera, e\room\NPC[0]\Collider, 8.0)
											
											n_I\Curr173\Idle = 0
											
											PlaySound_Strict(IntroSFX[11])
											
											PositionEntity(n_I\Curr173\Collider, 0.0, 500.0, 0.0)
											ResetEntity(n_I\Curr173\Collider)
											
											For r.Rooms = Each Rooms
												If r\RoomTemplate\Name = "cont1_173" Then
													x = EntityX(r\OBJ, True) + 3712.0 * RoomScale
													y = 384.0 * RoomScale
													z = EntityZ(r\OBJ, True) + 1312.0 * RoomScale
													
													TeleportEntity(me\Collider, x + (EntityX(me\Collider) - e\room\x - 288.0 * RoomScale), y + EntityY(me\Collider) + 0.4, z + (EntityZ(me\Collider) - e\room\z))
													TeleportToRoom(r)
													
													me\DropSpeed = 0.0
													For i = 0 To 2
														PositionEntity(e\room\NPC[i]\Collider, x + (EntityX(e\room\NPC[i]\Collider) - e\room\x - 288.0 * RoomScale), y + EntityY(e\room\NPC[i]\Collider) + 0.4, z + (EntityZ(e\room\NPC[i]\Collider) - e\room\z))
														ResetEntity(e\room\NPC[i]\Collider)
													Next
													
													ShouldPlay = 0
													
													For i = 2 To 4
														If IntroSFX[i] <> 0 Then FreeSound_Strict(IntroSFX[i]) : IntroSFX[i] = 0
													Next
													
													r\NPC[0] = e\room\NPC[0]
													r\NPC[0]\State = 8.0
													
													For do.Doors = Each Doors
														If do\room = e\room Then RemoveDoor(do)
													Next
													
													For w.WayPoints = Each WayPoints
														If w\room = e\room Then
															FreeEntity(w\OBJ) : w\OBJ = 0
															Delete(w)
														EndIf
													Next
													
													r\NPC[1] = e\room\NPC[6]
													
													FreeEntity(e\room\OBJ) : e\room\OBJ = 0
													Delete(e\room)
													
													For sc.SecurityCams = Each SecurityCams
														If sc\room = e\room Then Delete(sc)
													Next
													
													For pr.Props = Each Props
														If pr\room = e\room Then Delete(pr)
													Next
													
													ClearConsole()
													
													e\EventState2 = 1.0
													
													Exit
												EndIf
											Next
										EndIf
									EndIf
								EndIf
							EndIf
						EndIf
					Else
						If me\Terminated Then
							If e\room\NPC[3] <> Null Then
								If e\room\NPC[3]\State = 1.0 Lor e\room\NPC[3]\State = 11.0 Then
									LoadEventSound(e, "SFX\Room\Intro\Guard\Ulgrin\EscortTerminated.ogg")
									PlaySound_Strict(e\Sound)
								EndIf
							EndIf
						EndIf
						e\EventState2 = 1.0
					EndIf
				Else
					For i = 2 To 4
						If IntroSFX[i] <> 0 Then FreeSound_Strict(IntroSFX[i]) : IntroSFX[i] = 0
					Next
					
					For do.Doors = Each Doors
						If do\room = e\room Then RemoveDoor(do)
					Next
					
					For w.WayPoints = Each WayPoints
						If w\room = e\room Then
							FreeEntity(w\OBJ) : w\OBJ = 0
							Delete(w)
						EndIf
					Next
					
					FreeEntity(e\room\OBJ) : e\room\OBJ = 0
					Delete(e\room)
					
					For sc.SecurityCams = Each SecurityCams
						If sc\room = e\room Then Delete(sc)
					Next
					
					For pr.Props = Each Props
						If pr\room = e\room Then Delete(pr)
					Next
					
					RemoveEvent(e)
				EndIf
				;[End Block]
			Case e_butt_ghost
				;[Block]
				If PlayerRoom = e\room Then
					If EntityDistanceSquared(me\Collider, e\room\Objects[0]) < 3.24 Then
						If e\EventState = 0.0 Then
							GiveAchievement(Achv789_J)
							e\SoundCHN = PlaySound2(ButtGhostSFX, Camera, e\room\Objects[0])
							e\EventState = 1.0
						Else
							If (Not ChannelPlaying(e\SoundCHN)) Then RemoveEvent(e)
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_room2_checkpoint
				;[Block]
				If PlayerRoom = e\room Then
					; ~ Play a sound clip when the player passes through the gate
					If e\EventState2 = 0.0 Then
						If EntityZ(me\Collider) < e\room\z Then
							If me\Zone = 1 Then
								PlaySound_Strict(LoadTempSound("SFX\Ambient\ToZone2.ogg"))
							Else
								PlaySound_Strict(LoadTempSound("SFX\Ambient\ToZone3.ogg"))
							EndIf
							e\EventState2 = 1.0
						EndIf
					EndIf
					
					If e\EventState3 = 0.0 Then
						If Rand(2) = 1 Then
							GiveAchievement(Achv1048)
							e\room\Objects[1] = CopyEntity(n_I\NPCModelID[NPC_1048_MODEL])
							ScaleEntity(e\room\Objects[1], 0.05, 0.05, 0.05)
							PositionEntity(e\room\Objects[1], EntityX(e\room\Objects[0], True), EntityY(e\room\Objects[0], True), EntityZ(e\room\Objects[0], True))
							SetAnimTime(e\room\Objects[1], 267.0)
						EndIf
						
						e\EventState3 = 1.0
					ElseIf e\room\Objects[1] <> 0
						If e\EventState3 = 1.0 Then
							PointEntity(e\room\Objects[1], me\Collider)
							RotateEntity(e\room\Objects[1], -90.0, EntityYaw(e\room\Objects[1]), 0.0)
							Angle = WrapAngle(DeltaYaw(me\Collider, e\room\Objects[1]))
							If Angle < 40.0 Lor Angle > 320.0 Then e\EventState3 = 2.0
						ElseIf e\EventState3 = 2.0
							PointEntity(e\room\Objects[1], me\Collider)
							RotateEntity(e\room\Objects[1], -90.0, EntityYaw(e\room\Objects[1]), 0.0)
							Animate2(e\room\Objects[1], AnimTime(e\room\Objects[1]), 267.0, 283.0, 0.3, False)
							If AnimTime(e\room\Objects[1]) = 283.0 Then e\EventState3 = 3.0
						ElseIf e\EventState3 = 3.0
							Animate2(e\room\Objects[1], AnimTime(e\room\Objects[1]), 283.0, 267.0, -0.2, False)
							If AnimTime(e\room\Objects[1]) = 267.0 Then e\EventState3 = 4.0
						ElseIf e\EventState3 = 4.0
							Angle = WrapAngle(DeltaYaw(me\Collider, e\room\Objects[1]))
							If Angle > 90.0 And Angle < 270.0 Then
								FreeEntity(e\room\Objects[1]) : e\room\Objects[1] = 0
								e\EventState3 = 5.0
							EndIf
						EndIf
					EndIf
				EndIf
				
				If e\room\Dist < 12.0 Then
					If e\room\RoomTemplate\Name = "room2_checkpoint_hcz_ez" Then
						For e2.Events = Each Events
							If e2\EventID = e_cont2_008 Then
								If e2\EventState = 2.0 Then
									If e\room\RoomDoors[0]\Locked = 1 Then
										TurnCheckpointMonitorsOff(False)
										For i = 0 To 1
											e\room\RoomDoors[i]\Locked = 0
										Next
									EndIf
								Else
									UpdateCheckpointMonitors(False)
									For i = 0 To 1
										e\room\RoomDoors[i]\Locked = 1
									Next
								EndIf
								Exit
							EndIf
						Next
					Else
						For e2.Events = Each Events
							If e2\EventID = e_room2_sl Then
								If e2\EventState3 = 0.0 Then
									If e\room\RoomDoors[0]\Locked = 1 Then
										TurnCheckpointMonitorsOff()
										For i = 0 To 1
											e\room\RoomDoors[i]\Locked = 0
										Next
									EndIf
								Else
									UpdateCheckpointMonitors()
									For i = 0 To 1
										e\room\RoomDoors[i]\Locked = 1
									Next
								EndIf
								Exit
							EndIf
						Next
					EndIf
				EndIf
				
				If e\room\RoomDoors[0]\Open <> e\EventState Then
					If (Not e\Sound) Then LoadEventSound(e, "SFX\Door\DoorCheckpoint.ogg")
					e\SoundCHN = PlaySound2(e\Sound, Camera, e\room\RoomDoors[0]\OBJ)
					e\SoundCHN2 = PlaySound2(e\Sound, Camera, e\room\RoomDoors[1]\OBJ)
				EndIf
				e\EventState = e\room\RoomDoors[0]\Open
				
				UpdateSoundOrigin(e\SoundCHN, Camera, e\room\RoomDoors[0]\OBJ)
				UpdateSoundOrigin(e\SoundCHN2, Camera, e\room\RoomDoors[1]\OBJ)
				;[End Block]
			Case e_cont1_895, e_cont1_895_106
				;[Block]
				If e\EventState < MilliSecs2() Then
					; ~ SCP-079 starts broadcasting SCP-895's camera feed on monitors after leaving the first zone
					If me\Zone > 0 Then
						If EntityPitch(e\room\RoomLevers[0]\OBJ, True) > 0.0 Then ; ~ Camera feed on
							For sc.SecurityCams = Each SecurityCams
								If sc\CoffinEffect = 0 And sc\room\RoomTemplate\Name <> "cont1_106" And sc\room\RoomTemplate\Name <> "cont1_205" Then sc\CoffinEffect = 2
								If sc\room = e\room Then sc\Screen = True
							Next
						Else ; ~ Camera feed off
							For sc.SecurityCams = Each SecurityCams
								If sc\CoffinEffect <> 1 Then sc\CoffinEffect = 0
								If sc\room = e\room Then sc\Screen = False
							Next
						EndIf
					EndIf
					e\EventState = MilliSecs2() + 3000
				EndIf
				
				If PlayerRoom = e\room Then
					CoffinDistance = EntityDistance(me\Collider, e\room\Objects[1])
					If CoffinDistance < 1.5 Then
						GiveAchievement(Achv895)
						If (Not n_I\Curr106\Contained) And e\EventID = e_cont1_895_106 And e\EventState2 = 0.0 Then
							de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(e\room\Objects[1], True), e\room\y - 1531.0 * RoomScale, EntityZ(e\room\Objects[1], True), 90.0, Rnd(360.0), 0.0, 0.05, 0.8)
							de\SizeChange = 0.001
							EntityParent(de\OBJ, e\room\OBJ)
							
							PositionEntity(n_I\Curr106\Collider, EntityX(e\room\Objects[1], True), e\room\y - 1541.0 * RoomScale, EntityZ(e\room\Objects[1], True))
							ResetEntity(n_I\Curr106\Collider)
							SetNPCFrame(n_I\Curr106, 110.0)
							n_I\Curr106\State = -0.1
							n_I\Curr106\PrevY = EntityY(me\Collider)
							
							e\EventState2 = 1.0
						EndIf
					ElseIf CoffinDistance < 3.0
						If e\room\NPC[0] = Null Then
							e\room\NPC[0] = CreateNPC(NPCTypeGuard, e\room\x, e\room\y, e\room\z)
							e\room\NPC[0]\State = 8.0 : e\room\NPC[0]\GravityMult = 0.0 : e\room\NPC[0]\IsDead = True : e\room\NPC[0]\FallingPickDistance = 0.0
							e\room\NPC[0]\Sound = LoadSound_Strict("SFX\Room\895Chamber\GuardIdle" + Rand(3) + ".ogg")
							e\room\NPC[0]\SoundCHN = PlaySound2(e\room\NPC[0]\Sound, Camera, e\room\NPC[0]\Collider)
							RotateEntity(e\room\NPC[0]\Collider, 0.0, e\room\Angle + 90.0, 0.0)
							SetNPCFrame(e\room\NPC[0], 270.0)
							
							If (Not e\room\RoomDoors[0]\Open) Then e\room\RoomDoors[0]\Open = True
						EndIf
					ElseIf CoffinDistance > 5.0
						If e\room\NPC[0] <> Null Then
							If e\room\NPC[0]\PrevState = 0 Then
								StopChannel(e\room\NPC[0]\SoundCHN) : e\room\NPC[0]\SoundCHN = 0
								LoadNPCSound(e\room\NPC[0], "SFX\Room\895Chamber\GuardScream" + Rand(3) + ".ogg")
								e\room\NPC[0]\SoundCHN = PlaySound2(e\room\NPC[0]\Sound, Camera, e\room\NPC[0]\Collider, 100.0)
								e\room\NPC[0]\State2 = 0.0 : e\room\NPC[0]\PrevState = 1
							EndIf
						EndIf
					EndIf
					
					If e\room\NPC[0] <> Null Then
						UpdateSoundOrigin(e\room\NPC[0]\SoundCHN, Camera, e\room\NPC[0]\Collider, 100.0)
						If e\room\NPC[0]\PrevState = 0 Then
							e\room\NPC[0]\GravityMult = 0.0
						ElseIf e\room\NPC[0]\PrevState = 1
							If e\room\NPC[0]\State2 < 70.0 * 1.0 Then
								e\room\NPC[0]\State2 = e\room\NPC[0]\State2 + fps\Factor[0] : e\room\NPC[0]\GravityMult = 0.0
							Else
								e\room\NPC[0]\GravityMult = 1.0
							EndIf
							If EntityY(e\room\NPC[0]\Collider) > ((-1531.0) * RoomScale) + 0.35 Then
								Dist = EntityDistanceSquared(me\Collider, e\room\NPC[0]\Collider)
								If Dist < 0.64 Then ; ~ Get the player out of the way
									Dist = Sqr(Dist)
									fDir = PointDirection(EntityX(me\Collider, True), EntityZ(me\Collider, True), EntityX(e\room\NPC[0]\Collider, True), EntityZ(e\room\NPC[0]\Collider, True))
									TranslateEntity(me\Collider, Cos(-fDir + 90.0) * (Dist - 0.8) * (Dist - 0.8), 0, Sin(-fDir + 90.0) * (Dist - 0.8) * (Dist - 0.8))
								EndIf
								If EntityY(e\room\NPC[0]\Collider) > 0.6 Then EntityType(e\room\NPC[0]\Collider, 0)
							Else
								e\EventState = e\EventState + fps\Factor[0]
								AnimateNPC(e\room\NPC[0], 270.0, 286.0, 0.4, False)
								If (Not e\Sound) Then
									LoadEventSound(e,"SFX\General\BodyFall.ogg")
									e\SoundCHN = PlaySound_Strict(e\Sound)
									
									de.Decals = CreateDecal(DECAL_BLOOD_2, EntityX(e\room\OBJ), e\room\y - 1531.0 * RoomScale, EntityZ(e\room\OBJ), 90.0, Rnd(360.0), 0.0, 0.4)
									EntityParent(de\OBJ, e\room\OBJ)
									
									it.Items = CreateItem("Unknown Note", "paper", EntityX(e\room\OBJ), e\room\y - 1516.0 * RoomScale, EntityZ(e\room\OBJ))
									EntityType(it\Collider, HIT_ITEM)
									
									it.Items = CreateItem("Level 3 Key Card", "key3", EntityX(e\room\OBJ), e\room\y - 1504.0 * RoomScale, EntityZ(e\room\OBJ))
									EntityType(it\Collider, HIT_ITEM)
								EndIf
								If e\room\NPC[0]\Frame = 286.0 Then e\room\NPC[0]\PrevState = 2
							EndIf
							If (Not e\room\NPC[0]\SoundCHN2) Then
								e\room\NPC[0]\Sound2 = LoadSound_Strict("SFX\Room\895Chamber\GuardRadio.ogg")
								e\room\NPC[0]\SoundCHN2 = LoopSound2(e\room\NPC[0]\Sound2, e\room\NPC[0]\SoundCHN2, Camera, e\room\NPC[0]\Collider, 5)
							EndIf
						ElseIf e\room\NPC[0]\PrevState = 2 Then
							If (Not ChannelPlaying(e\SoundCHN)) Then
								If e\Sound <> 0 Then FreeSound_Strict(e\Sound) : e\Sound = 0
								StopChannel(e\SoundCHN) : e\SoundCHN = 0
							EndIf
							If (Not ChannelPlaying(e\room\NPC[0]\SoundCHN)) Then
								If e\room\NPC[0]\Sound <> 0 Then FreeSound_Strict(e\room\NPC[0]\Sound) : e\room\NPC[0]\Sound = 0
								StopChannel(e\room\NPC[0]\SoundCHN) : e\room\NPC[0]\SoundCHN = 0
							EndIf
							If (Not e\room\NPC[0]\Sound2) Then e\room\NPC[0]\Sound2 = LoadSound_Strict("SFX\Room\895Chamber\GuardRadio.ogg")
							e\room\NPC[0]\SoundCHN2 = LoopSound2(e\room\NPC[0]\Sound2, e\room\NPC[0]\SoundCHN2, Camera, e\room\NPC[0]\Collider, 5.0)
						EndIf
					EndIf
					
					If wi\NightVision > 0 Lor wi\SCRAMBLE > 0 Then
						Local HasBatteryFor895% = False
						
						For i = 0 To MaxItemAmount - 1
							If Inventory(i) <> Null Then
								If (wi\NightVision > 0 And (Inventory(i)\ItemTemplate\TempName = "nvg" Lor Inventory(i)\ItemTemplate\TempName = "veryfinenvg" Lor Inventory(i)\ItemTemplate\TempName = "finenvg")) Lor (wi\SCRAMBLE And (Inventory(i)\ItemTemplate\TempName = "scramble" Lor Inventory(i)\ItemTemplate\TempName = "finescramble")) Then
									If Inventory(i)\State > 0.0 Lor (wi\NightVision = 3 And Inventory(i)\ItemTemplate\TempName = "finenvg") Then
										HasBatteryFor895 = True
										Exit
									EndIf
								EndIf
							EndIf
						Next
						If CoffinDistance < 4.0 And HasBatteryFor895 And I_714\Using <> 3 Then
							TurnEntity(me\Collider, 0.0, AngleDist(PointDirection(EntityX(me\Collider, True), EntityZ(me\Collider, True), EntityX(e\room\Objects[1], True), EntityZ(e\room\Objects[1], True)) + 90.0 + Sin(WrapAngle(e\EventState3 / 10.0)), EntityYaw(me\Collider)) / 4.0, 0.0, True)
							CameraPitch = (CameraPitch * 0.8) + (((-60.0) * Min(Max((2.0 - Distance(EntityX(me\Collider, True), EntityX(e\room\Objects[1], True), EntityZ(me\Collider, True), EntityZ(e\room\Objects[1], True))) / 2.0, 0.0), 1.0)) * 0.2)
							
							me\Sanity = me\Sanity - (fps\Factor[0] * 1.1 / (wi\NightVision + wi\SCRAMBLE) / I_714\Using)
							me\RestoreSanity = False
							me\BlurTimer = Sin(MilliSecs2() / 10) * Abs(me\Sanity)
							
							If me\VomitTimer < 0.0 Then
								me\RestoreSanity = False
								me\Sanity = -1010.0
							EndIf
							
							If me\Sanity < -1000.0 Then
								If wi\NightVision > 1 Then
									msg\DeathMsg = GetLocalString("death", "895.nvg.914")
								ElseIf wi\SCRAMBLE > 0
									msg\DeathMsg = GetLocalString("death", "895.nvg.096")
								Else
									msg\DeathMsg = GetLocalString("death", "895.nvg")
								EndIf
								EntityTexture(t\OverlayID[4], t\OverlayTextureID[4])
								If me\VomitTimer < -10.0 Then Kill()
							ElseIf me\Sanity < -800.0
								If Rand(3) = 1 Then EntityTexture(t\OverlayID[4], t\OverlayTextureID[4])
								If Rand(6) < 5 Then
									EntityTexture(t\OverlayID[4], mon_I\MonitorOverlayID[Rand(MONITOR_895_OVERLAY_1, MONITOR_895_OVERLAY_6)])
									For i = 0 To MaxItemAmount - 1
										If Inventory(i) <> Null Then
											If (wi\NightVision > 0 And (Inventory(i)\ItemTemplate\TempName = "nvg" Lor Inventory(i)\ItemTemplate\TempName = "veryfinenvg" Lor Inventory(i)\ItemTemplate\TempName = "finenvg")) Lor (wi\SCRAMBLE And (Inventory(i)\ItemTemplate\TempName = "scramble" Lor Inventory(i)\ItemTemplate\TempName = "finescramble")) Then
												If Inventory(i)\State2 = 1.0 Then PlaySound_Strict(HorrorSFX[1])
												Inventory(i)\State2 = 2.0
												Exit
											EndIf
										EndIf
									Next
								EndIf
								me\BlurTimer = 1000.0
								If me\VomitTimer = 0.0 Then me\VomitTimer = 1.0
							ElseIf me\Sanity < -500.0
								If Rand(7) = 1 Then EntityTexture(t\OverlayID[4], t\OverlayTextureID[4])
								If Rand(50) = 1 Then
									EntityTexture(t\OverlayID[4], mon_I\MonitorOverlayID[Rand(MONITOR_895_OVERLAY_1, MONITOR_895_OVERLAY_6)])
									For i = 0 To MaxItemAmount - 1
										If Inventory(i) <> Null Then
											If (wi\NightVision > 0 And (Inventory(i)\ItemTemplate\TempName = "nvg" Lor Inventory(i)\ItemTemplate\TempName = "veryfinenvg" Lor Inventory(i)\ItemTemplate\TempName = "finenvg")) Lor (wi\SCRAMBLE And (Inventory(i)\ItemTemplate\TempName = "scramble" Lor Inventory(i)\ItemTemplate\TempName = "finescramble")) Then
												If Inventory(i)\State2 = 0.0 Then PlaySound_Strict(HorrorSFX[0])
												Inventory(i)\State2 = 1.0
												Exit
											EndIf
										EndIf
									Next
								EndIf
							Else
								EntityTexture(t\OverlayID[4], t\OverlayTextureID[4])
								For i = 0 To MaxItemAmount - 1
									If Inventory(i) <> Null Then
										If (wi\NightVision > 0 And (Inventory(i)\ItemTemplate\TempName = "nvg" Lor Inventory(i)\ItemTemplate\TempName = "veryfinenvg" Lor Inventory(i)\ItemTemplate\TempName = "finenvg")) Lor (wi\SCRAMBLE And (Inventory(i)\ItemTemplate\TempName = "scramble" Lor Inventory(i)\ItemTemplate\TempName = "finescramble")) Then
											Inventory(i)\State2 = 0.0
											Exit
										EndIf
									EndIf
								Next
							EndIf
						EndIf
					EndIf
					
					If e\EventState3 > 0.0 Then e\EventState3 = Max(e\EventState3 - fps\Factor[0], 0.0)
					If e\EventState3 = 0.0 Then
						EntityTexture(t\OverlayID[4], t\OverlayTextureID[4])
						e\EventState3 = -1.0
					EndIf
					
					ShouldPlay = 66
					
					If UpdateLever(e\room\RoomLevers[0]\OBJ) Then
						TurnOffSecurityCam(e\room, False)
					Else
						TurnOffSecurityCam(e\room, True)
					EndIf
				Else
					CoffinDistance = e\room\Dist
				EndIf
				;[End Block]
			Case e_room1_dead_end_106
				;[Block]
				If (Not n_I\Curr106\Contained) Then
					If e\EventState = 0.0 Then
						If e\room\Dist < 8.0 And e\room\Dist > 0.0 Then
							If n_I\Curr106\State =< 0.0 Then 
								RemoveEvent(e)
							Else
								e\room\RoomDoors[0]\Open = True
								
								e\room\NPC[0] = CreateNPC(NPCTypeD, EntityX(e\room\RoomDoors[0]\OBJ, True), 0.5, EntityZ(e\room\RoomDoors[0]\OBJ, True))
								ChangeNPCTextureID(e\room\NPC[0], NPC_CLASS_D_JANITOR_1_TEXTURE)
								PointEntity(e\room\NPC[0]\Collider, e\room\OBJ)
								RotateEntity(e\room\NPC[0]\Collider, 0.0, EntityYaw(e\room\NPC[0]\Collider), 0.0, True)
								MoveEntity(e\room\NPC[0]\Collider, 0.0, 0.0, 0.5) 
								
								e\room\RoomDoors[0]\Open = False
								PlaySound2(LoadTempSound("SFX\Door\EndroomDoor.ogg"), Camera, e\room\OBJ, 15.0)
								
								e\EventState = 1.0
							EndIf
						EndIf
					ElseIf e\EventState = 1.0
						If PlayerRoom = e\room Then
							e\room\NPC[0]\State = 1.0
							
							e\Sound = LoadSound_Strict("SFX\Character\Janitor\106Abduct.ogg")
							PlaySound_Strict(e\Sound)
							
							StopChannel(e\SoundCHN) : e\SoundCHN = 0
							
							e\EventState = 2.0
						ElseIf e\room\Dist < 8.0
							If (Not e\Sound) Then e\Sound = LoadSound_Strict("SFX\Character\Janitor\Idle.ogg")
							e\SoundCHN = LoopSound2(e\Sound, e\SoundCHN, Camera, e\room\NPC[0]\OBJ, 15.0)
						EndIf
					ElseIf e\EventState = 2.0
						If EntityDistanceSquared(e\room\NPC[0]\Collider, e\room\OBJ) < 2.25 Then
							de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(e\room\OBJ), e\room\y + 0.005, EntityZ(e\room\OBJ), 90.0, Rnd(360.0), 0.0, 0.05)
							de\SizeChange = 0.008 : de\Timer = 10000.0
							EntityParent(de\OBJ, e\room\OBJ)
							e\EventState = 3.0
						EndIf
					Else
						PositionEntity(n_I\Curr106\OBJ, EntityX(e\room\OBJ, True), 0.0, EntityZ(e\room\OBJ, True))
						PointEntity(n_I\Curr106\OBJ, e\room\NPC[0]\Collider)
						RotateEntity(n_I\Curr106\OBJ, 0.0, EntityYaw(n_I\Curr106\OBJ), 0.0, True)
						
						n_I\Curr106\Idle = 1
						
						If DistanceSquared(EntityX(e\room\NPC[0]\Collider), EntityX(e\room\OBJ), EntityZ(e\room\NPC[0]\Collider), EntityZ(e\room\OBJ)) < 0.16 Then
							If e\room\NPC[0]\State = 1.0 Then SetNPCFrame(e\room\NPC[0], 41.0)
							e\room\NPC[0]\State = 6.0
							e\room\NPC[0]\CurrSpeed = CurveValue(0.0, e\room\NPC[0]\CurrSpeed, 25.0)
							PositionEntity(e\room\NPC[0]\Collider, CurveValue(EntityX(e\room\OBJ, True), EntityX(e\room\NPC[0]\Collider), 25.0), 0.3 - e\EventState / 70.0, CurveValue(EntityZ(e\room\OBJ, True), EntityZ(e\room\NPC[0]\Collider), 25.0))
							ResetEntity(e\room\NPC[0]\Collider)
							
							AnimateNPC(e\room\NPC[0], 41.0, 58.0, 0.1, False)
							
							e\EventState = e\EventState + (fps\Factor[0] / 2.0)
						EndIf
						AnimateNPC(n_I\Curr106, 495.0, 604.0, 0.7, False)
						
						me\CurrSpeed = Min(me\CurrSpeed - (me\CurrSpeed * (0.15 / EntityDistance(e\room\NPC[0]\Collider, me\Collider)) * fps\Factor[0]), me\CurrSpeed)
						If e\EventState > 100.0 Then
							PositionEntity(n_I\Curr106\OBJ, EntityX(n_I\Curr106\Collider), -100.0, EntityZ(n_I\Curr106\Collider), True)
							PositionEntity(n_I\Curr106\Collider, EntityX(n_I\Curr106\Collider), -100.0, EntityZ(n_I\Curr106\Collider), True)
							ResetEntity(n_I\Curr106\Collider)
							
							n_I\Curr106\Idle = 0
							If EntityDistanceSquared(me\Collider, e\room\OBJ) < 6.25 Then n_I\Curr106\State = -0.1
							
							RemoveNPC(e\room\NPC[0])
							
							RemoveEvent(e)
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_gate_b_entrance
				;[Block]
				If PlayerRoom = e\room Then
					RoomExists = False
					For r.Rooms = Each Rooms
						If r\RoomTemplate\Name = "room2c_ec" Then
							RoomExists = True
							Exit
						EndIf
					Next
					If (Not RoomExists) Then e\EventState3 = 1.0
					
					If (Not RemoteDoorOn) Then
						e\room\RoomDoors[1]\Locked = 1
					ElseIf RemoteDoorOn And e\EventState3 = 0.0
						e\room\RoomDoors[1]\Locked = 2
					Else
						e\room\RoomDoors[1]\Locked = 0
						
						Local gateb.Rooms = Null
						
						For r.Rooms = Each Rooms
							If r\RoomTemplate\Name = "gate_b" Then
								gateb = r 
								Exit
							EndIf
						Next
						
						If n_I\Curr096 <> Null Then
							If n_I\Curr096\State = 0.0 Lor n_I\Curr096\State = 5.0 Then
								e\EventState = UpdateElevators(e\EventState, e\room\RoomDoors[0], gateb\RoomDoors[1], e\room\Objects[0], e\room\Objects[1], e)
							Else
								e\EventState = Update096ElevatorEvent(e, e\EventState, e\room\RoomDoors[0], e\room\Objects[0])
							EndIf
						Else
							e\EventState = UpdateElevators(e\EventState, e\room\RoomDoors[0], gateb\RoomDoors[1], e\room\Objects[0], e\room\Objects[1], e)
						EndIf
						
						If EntityDistanceSquared(me\Collider, e\room\Objects[1]) < 16.0 Then
							gateb\RoomDoors[1]\Locked = 1
							PlayerRoom = gateb
							RemoveEvent(e)
						EndIf
						If PlayerInsideElevator Then CanSave = 1
					EndIf
				EndIf
				;[End Block]
			Case e_gate_a_entrance
				;[Block]
				If PlayerRoom = e\room Then
					RoomExists = False
					For r.Rooms = Each Rooms
						If r\RoomTemplate\Name = "room2c_ec" Then
							RoomExists = True
							Exit
						EndIf
					Next
					If (Not RoomExists) Then e\EventState3 = 1.0
					
					If (Not RemoteDoorOn) Then
						e\room\RoomDoors[1]\Locked = 1
					ElseIf RemoteDoorOn And e\EventState3 = 0.0
						e\room\RoomDoors[1]\Locked = 2
					Else
						e\room\RoomDoors[1]\Locked = 0
						
						Local gatea.Rooms = Null
						
						For r.Rooms = Each Rooms
							If r\RoomTemplate\Name = "gate_a" Then
								gatea = r 
								Exit
							EndIf
						Next
						
						If n_I\Curr096 <> Null Then
							If n_I\Curr096\State = 0.0 Lor n_I\Curr096\State = 5.0 Then
								e\EventState = UpdateElevators(e\EventState, e\room\RoomDoors[0], gatea\RoomDoors[1], e\room\Objects[0], e\room\Objects[1], e)
							Else
								e\EventState = Update096ElevatorEvent(e, e\EventState, e\room\RoomDoors[0], e\room\Objects[0])
							EndIf
						Else
							e\EventState = UpdateElevators(e\EventState, e\room\RoomDoors[0], gatea\RoomDoors[1], e\room\Objects[0], e\room\Objects[1], e)
						EndIf
						If (Not n_I\Curr106\Contained) Then
							If e\EventState < -1.5 And e\EventState + fps\Factor[0] >= -1.5 Then PlaySound_Strict(OldManSFX[3])
						EndIf
						
						If EntityDistanceSquared(me\Collider, e\room\Objects[1]) < 16.0 Then
							gatea\RoomDoors[1]\Locked = 1
							PlayerRoom = gatea
							RemoveEvent(e)
						EndIf
						If PlayerInsideElevator Then CanSave = 1
					EndIf
				EndIf
				;[End Block]
			Case e_room2c_gw_lcz_173
				;[Block]
				If e\room\Dist < 6.0 And e\room\Dist > 0.0 Then
					If n_I\Curr173\Idle > 1 Then
						RemoveEvent(e)
					Else
						If (Not EntityInView(n_I\Curr173\Collider, Camera)) Lor EntityDistanceSquared(n_I\Curr173\Collider, me\Collider) > 225.0 Then 
							PositionEntity(n_I\Curr173\Collider, e\room\x + Cos(225.0 - 90.0 + e\room\Angle) * 2.0, 0.6, e\room\z + Sin(225.0 - 90.0 + e\room\Angle) * 2.0)
							ResetEntity(n_I\Curr173\Collider)
							RemoveEvent(e)
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_room2c_gw_ez_096
				;[Block]
				If PlayerRoom = e\room Then
					If n_I\Curr096 = Null Then
						n_I\Curr096 = CreateNPC(NPCType096, EntityX(e\room\OBJ, True), 0.3, EntityZ(e\room\OBJ, True))
						RotateEntity(n_I\Curr096\Collider, 0.0, e\room\Angle + 45.0, 0.0, True)
					EndIf
					RemoveEvent(e)
				EndIf
				;[End Block]
			Case e_cont1_372
				;[Block]
				If PlayerRoom = e\room Then
					If e\EventState = 0.0 Then
						If EntityDistanceSquared(me\Collider, e\room\OBJ) < 2.25 Then
							PlaySound_Strict(RustleSFX[Rand(0, 5)])
							CreateNPC(NPCType372, 0.0, 0.0, 0.0)
							RemoveEvent(e)
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_room2_cafeteria
				;[Block]
				If PlayerRoom = e\room Then
					If InteractObject(e\room\Objects[0], 2.25) Then
						Temp = True
						For it.Items = Each Items
							If (Not it\Picked) Then
								If EntityX(it\Collider) - EntityX(e\room\Objects[1], True) = 0.0 Then
									If EntityZ(it\Collider) - EntityZ(e\room\Objects[1], True) = 0.0 Then
										Temp = False
										Exit
									EndIf
								EndIf
							EndIf
						Next
						
						Local Inserted% = False
						
						If e\EventState2 < 2.0 Then
							If SelectedItem <> Null Then
								If SelectedItem\ItemTemplate\TempName = "25ct" Lor SelectedItem\ItemTemplate\TempName = "coin" Then
									RemoveItem(SelectedItem)
									e\EventState2 = e\EventState2 + 1.0
									PlaySound_Strict(LoadTempSound("SFX\SCP\294\CoinDrop.ogg"))
									Inserted = True
								ElseIf SelectedItem\ItemTemplate\TempName = "mastercard"
									If me\Funds > 0 Then
										me\Funds = me\Funds - 1
										
										PlaySound_Strict(LoadTempSound("SFX\SCP\294\InsertMasterCard.ogg"))
										
										e\EventState2 = 2.0
										
										Inserted = True
										RemoveItem(SelectedItem)
									EndIf
									me\UsedMastercard = True
								EndIf
							EndIf
						EndIf
						If e\EventState2 = 2.0 Then
							GiveAchievement(Achv294)
							
							I_294\Using = Temp
							If I_294\Using Then mo\MouseHit1 = False
						ElseIf e\EventState2 = 1.0 And (Not Inserted) And (Not me\UsedMastercard) Then
							I_294\Using = False
							CreateMsg(GetLocalString("msg", "294.another"))
						ElseIf (Not Inserted) And (Not me\UsedMastercard) Then
							I_294\Using = False
							CreateMsg(GetLocalString("msg", "294.two"))
						ElseIf me\UsedMastercard
							CreateMsg(GetLocalString("msg", "294.funds"))
						EndIf
					EndIf
				EndIf
				
				If e\EventState = 0.0 Then
					CreateNPC(NPCType066, EntityX(e\room\OBJ), 0.5, EntityZ(e\room\OBJ))
					e\EventState = 1.0
				EndIf
				;[End Block]
			Case e_room2c_ec
				;[Block]
				If PlayerRoom = e\room Then
					; ~ Primary Lighting
					UpdateLever(e\room\RoomLevers[0]\OBJ)
					
					; ~ Secondary Lighting
					Local PrevState2# = e\EventState2
					
					e\EventState2 = UpdateLever(e\room\RoomLevers[1]\OBJ)
					If (PrevState2 <> e\EventState2) And e\EventState > 0.0 Then PlaySound2(LightSFX, Camera, e\room\RoomLevers[1]\OBJ)
					If e\EventState2 Then
						SecondaryLightOn = CurveValue(1.0, SecondaryLightOn, 10.0)
					Else
						SecondaryLightOn = CurveValue(0.0, SecondaryLightOn, 10.0)
					EndIf
					
					EntityPick(Camera, 1.5)
					
					If PickedEntity() = e\room\RoomLevers[1]\OBJ
						If e\EventState = 0.0 Then
							PlaySound_Strict(HorrorSFX[7])
							PlaySound_Strict(LeverSFX)
							e\EventState = 1.0
						EndIf
					EndIf
					
					If e\EventState = 1.0 Then
						RotateEntity(e\room\RoomLevers[1]\OBJ, CurveValue(-80.0, EntityPitch(e\room\RoomLevers[1]\OBJ), 10.0), EntityYaw(e\room\RoomLevers[1]\OBJ), 0.0)
						If EntityPitch(e\room\RoomLevers[1]\OBJ) = -80.0 Then e\EventState = 2.0
					EndIf
					
					; ~ Remote Door Control
					RemoteDoorOn = UpdateLever(e\room\RoomLevers[2]\OBJ)
				EndIf
				;[End Block]
			Case e_room2_closets
				;[Block]
				If e\EventState = 0.0 Then
					If PlayerRoom = e\room And n_I\Curr173\Idle < 2 Then
						e\room\NPC[0] = CreateNPC(NPCTypeD, EntityX(e\room\Objects[0], True), EntityY(e\room\Objects[0], True), EntityZ(e\room\Objects[0], True))
						e\room\NPC[0]\State3 = 2.0
						ChangeNPCTextureID(e\room\NPC[0], NPC_CLASS_D_JANITOR_2_TEXTURE)
						
						e\room\NPC[0]\Sound = LoadSound_Strict("SFX\Room\Storeroom\Escape1.ogg")
						e\room\NPC[0]\SoundCHN = PlaySound2(e\room\NPC[0]\Sound, Camera, e\room\NPC[0]\Collider, 12.0)
						
						e\room\NPC[1] = CreateNPC(NPCTypeD, EntityX(e\room\Objects[1], True), EntityY(e\room\Objects[1], True), EntityZ(e\room\Objects[1], True))
						e\room\NPC[1]\State3 = 3.0
						ChangeNPCTextureID(e\room\NPC[1], NPC_CLASS_D_SCIENTIST_TEXTURE)
						
						PointEntity(e\room\NPC[0]\Collider, e\room\NPC[1]\Collider)
						PointEntity(e\room\NPC[1]\Collider, e\room\NPC[0]\Collider)
						
						e\EventState = 1.0
					EndIf
				Else
					e\EventState = e\EventState + fps\Factor[0]
					If e\EventState < 70.0 * 3.0 Then
						RotateEntity(e\room\NPC[1]\Collider, 0.0, CurveAngle(e\room\Angle + 90.0, EntityYaw(e\room\NPC[1]\Collider), 100.0), 0.0, True)
						e\room\NPC[0]\State = 1.0
						If e\EventState > 70.0 * 2.9 And e\EventState - fps\Factor[0] <= 70.0 * 2.9 Then PlaySound2(IntroSFX[11], Camera, e\room\OBJ, 15.0)
					ElseIf e\EventState < 70.0 * 6.5
						If e\EventState - fps\Factor[0] < 70.0 * 3.0 Then
							e\room\NPC[0]\State = 0.0
							e\room\NPC[1]\Sound = LoadSound_Strict("SFX\Room\Storeroom\Escape2.ogg")
							e\room\NPC[1]\SoundCHN = PlaySound2(e\room\NPC[1]\Sound, Camera, e\room\NPC[1]\Collider, 12.0)
						EndIf
						
						If e\EventState > 70.0 * 4.5 Then
							PointEntity(e\room\NPC[0]\OBJ, e\room\OBJ)
							RotateEntity(e\room\NPC[0]\Collider, 0.0, CurveAngle(EntityYaw(e\room\NPC[0]\OBJ), EntityYaw(e\room\NPC[0]\Collider), 30.0), 0.0, True)
						EndIf
						PointEntity(e\room\NPC[1]\OBJ, e\room\OBJ)
						TurnEntity(e\room\NPC[1]\OBJ, 0.0, Sin(e\EventState) * 25.0, 0.0)
						RotateEntity(e\room\NPC[1]\Collider, 0.0, CurveAngle(EntityYaw(e\room\NPC[1]\OBJ), EntityYaw(e\room\NPC[1]\Collider), 30.0), 0.0, True)
					Else
						If e\EventState - fps\Factor[0] < 70.0 * 6.5 Then
							PlaySound_Strict(HorrorSFX[0])
							PlaySound_Strict(LightSFX)
							me\LightBlink = 10.0
						EndIf
						
						If e\EventState > 70.0 * 7.5 And e\EventState - fps\Factor[0] <= 70.0 * 7.5 Then
							e\room\NPC[0]\IsDead = True
							If wi\NightVision > 0 Then me\BlinkTimer = -10.0
							
							PlaySound2(NeckSnapSFX[0], Camera, e\room\NPC[0]\Collider, 8.0)
							
							PositionEntity(n_I\Curr173\Collider, EntityX(e\room\NPC[0]\Collider, True) + 0.35, EntityY(e\room\NPC[0]\Collider, True) + 0.1, EntityZ(e\room\NPC[0]\Collider, True))
							PointEntity(n_I\Curr173\Collider, e\room\NPC[0]\Collider)
							ResetEntity(n_I\Curr173\Collider)
							n_I\Curr173\Idle = 1
						ElseIf e\EventState > 70.0 * 8.0 And e\EventState - fps\Factor[0] <= 70.0 * 8.0
							e\room\NPC[1]\IsDead = True
							If wi\NightVision > 0 Then me\BlinkTimer = -10.0
							
							PlaySound2(NeckSnapSFX[1], Camera, e\room\NPC[1]\Collider, 8.0)
							
							PositionEntity(n_I\Curr173\Collider, EntityX(e\room\NPC[1]\Collider, True) + 0.35, EntityY(e\room\NPC[1]\Collider, True) + 0.1, EntityZ(e\room\NPC[1]\Collider, True))
							PointEntity(n_I\Curr173\Collider, e\room\NPC[1]\Collider)
							ResetEntity(n_I\Curr173\Collider)
							n_I\Curr173\Idle = 0
						ElseIf e\EventState > 70.0 * 9.0 And e\EventState - fps\Factor[0] <= 70.0 * 9.0
							it.Items = CreateItem("Wallet", "wallet", EntityX(e\room\Objects[2], True), EntityY(e\room\Objects[2], True), EntityZ(e\room\Objects[2], True))
							EntityType(it\Collider, HIT_ITEM)
							PointEntity(it\Collider, e\room\NPC[0]\Collider)
							RotateEntity(it\Collider, 0.0, Rnd(360.0), 0.0)
							TeleportEntity(it\Collider, EntityX(it\Collider), EntityY(it\Collider), EntityZ(it\Collider), -0.02, True, 10.0)
							For i = 0 To 1
								it2.Items = CreateItem("Quarter", "25ct", 0.0, 0.0, 0.0)
								it2\Picked = True : it2\Dropped = -1
								it\SecondInv[i] = it2
								HideEntity(it2\Collider)
								EntityType(it2\Collider, HIT_ITEM)
								EntityParent(it2\Collider, 0)
							Next
							RemoveEvent(e)
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_room2_6_lcz_173
				;[Block]
				If PlayerRoom = e\room Then
					If e\EventState = 0.0 And n_I\Curr173\Idle = 0 Then
						If (Not EntityInView(n_I\Curr173\OBJ, Camera)) And (Not EntityInView(n_I\Curr173\OBJ2, Camera)) Then
							e\EventState = 1.0
							PositionEntity(n_I\Curr173\Collider, EntityX(e\room\Objects[0], True), 0.5, EntityZ(e\room\Objects[0], True))
							ResetEntity(n_I\Curr173\Collider)
							RemoveEvent(e)
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_room2_elevator
				;[Block]
				If e\EventState = 0.0 Then
					If e\room\Dist < 8.0 And e\room\Dist > 0.0 Then
						e\room\NPC[0] = CreateNPC(NPCTypeGuard, e\room\x, 0.5, e\room\z)
						PointEntity(e\room\NPC[0]\Collider, me\Collider)
						RotateEntity(e\room\NPC[0]\Collider, 0.0, e\room\Angle + 270.0, 0.0, True)
						
						e\room\RoomDoors[0]\IsElevatorDoor = 2
						
						e\EventState = 1.0
					EndIf
				Else
					If e\EventState = 1.0 Then
						If e\room\Dist < 5.0 Then
							e\room\NPC[0]\EnemyX = EntityX(e\room\Objects[0], True)
							e\room\NPC[0]\EnemyY = EntityY(e\room\Objects[0], True)
							e\room\NPC[0]\EnemyZ = EntityZ(e\room\Objects[0], True)
							
							e\EventState = 2.0
						EndIf
					ElseIf e\EventState = 2.0
						If EntityDistanceSquared(e\room\NPC[0]\Collider, me\Collider) < 5.0625 And (Not chs\NoTarget) Then
							e\room\NPC[0]\State = 1.0 : e\room\NPC[0]\State3 = 1.0
						Else
							e\room\NPC[0]\State = 5.0 : e\room\NPC[0]\State3 = 0.0
						EndIf
						
						If EntityDistanceSquared(e\room\NPC[0]\Collider, e\room\Objects[0]) < 4.0 Then
							OpenCloseDoor(e\room\RoomDoors[0])
							e\room\RoomDoors[0]\IsElevatorDoor = 0
							For i = 0 To 1
								EntityTexture(e\room\RoomDoors[0]\ElevatorPanel[i], d_I\ElevatorPanelTextureID[ELEVATOR_PANEL_DOWN])
							Next
							
							PlaySound_Strict(LoadTempSound("SFX\Room\ElevatorDeath.ogg"))
							
							e\EventState = 3.0
						EndIf
					ElseIf e\EventState < 70.0 * 13.0
						e\EventState = e\EventState + fps\Factor[0]
						If e\EventState > 70.0 * 6.7 And e\EventState < 70.0 * 7.4 Then
							me\BigCameraShake = 7.4 - (e\EventState / 70.0)
							If e\room\NPC[0] <> Null Then RemoveNPC(e\room\NPC[0]) : e\room\NPC[0] = Null
						ElseIf e\EventState > 70.0 * 8.6 And e\EventState < 70.0 * 10.6
							me\BigCameraShake = 10.6 - (e\EventState / 70.0)
						ElseIf e\EventState >= 70.0 * 13.0
							For i = 0 To 1
								EntityTexture(e\room\RoomDoors[0]\ElevatorPanel[i], d_I\ElevatorPanelTextureID[ELEVATOR_PANEL_IDLE])
							Next
							RemoveEvent(e)
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_room2_ic
				;[Block]
				If PlayerRoom = e\room Then
					de.Decals = CreateDecal(DECAL_BLOOD_2, EntityX(e\room\Objects[0], True), e\room\y + 0.005, EntityZ(e\room\Objects[0], True), 90.0, Rnd(360.0), 0.0)
					EntityParent(de\OBJ, e\room\OBJ)
					
					e\room\NPC[0] = CreateNPC(NPCTypeD, EntityX(e\room\Objects[0], True), e\room\y + 0.5, EntityZ(e\room\Objects[0], True))
					e\room\NPC[0]\State3 = -1.0 : e\room\NPC[0]\IsDead = True
					ChangeNPCTextureID(e\room\NPC[0], NPC_CLASS_D_GONZALES_TEXTURE)
					SetNPCFrame(e\room\NPC[0], 19.0)
					RotateEntity(e\room\NPC[0]\Collider, 0.0, EntityYaw(e\room\OBJ) - 80.0, 0.0, True)
					
					RemoveEvent(e)
				EndIf
				;[End Block]
			Case e_room2_2_lcz
				;[Block]
				; ~ EventState: Timer for turning the fan on / off
				
				; ~ EventState2: Fan on / off
				
				; ~ EventState3: The speed of the fan
				
				If PlayerRoom = e\room Then
					TurnEntity(e\room\Objects[0], e\EventState3 * fps\Factor[0], 0.0, 0.0)
					If e\EventState3 > 0.01 Then e\room\SoundCHN = LoopSound2(RoomAmbience[8], e\room\SoundCHN, Camera, e\room\Objects[0], 5.0, (e\EventState3 / 4.0))
					e\EventState3 = CurveValue(e\EventState2 * 5.0, e\EventState3, 150.0)
				EndIf
				
				If e\room\Dist < 16.0 Then
					If e\EventState < 0.0 Then
						e\EventState = 70.0 * Rnd(15.0, 30.0)
						Temp = e\EventState2
						e\EventState2 = Rand(0.0, 1.0)
						If PlayerRoom <> e\room Then
							e\EventState3 = e\EventState2 * 5.0
						Else
							If Temp = 0.0 And e\EventState2 = 1.0 Then ; ~ Turn on the fan
								PlaySound2(LoadTempSound("SFX\Ambient\Room Ambience\FanOn.ogg"), Camera, e\room\Objects[0], 8.0)
							ElseIf Temp = 1.0 And e\EventState2 = 0.0 ; ~ Turn off the fan
								PlaySound2(LoadTempSound("SFX\Ambient\Room Ambience\FanOff.ogg"), Camera, e\room\Objects[0], 8.0)
							EndIf
						EndIf
					Else
						e\EventState = e\EventState - fps\Factor[0]
					EndIf
				EndIf
				;[End Block]
			Case e_room2_nuke
				;[Block]
				If e\room\NPC[0] = Null Then
					TFormPoint(1110.0, 0.0, -208.0, e\room\OBJ, 0)
					e\room\NPC[0] = CreateNPC(NPCTypeD, TFormedX(), 0.5, TFormedZ())
					e\room\NPC[0]\State3 = -1.0 : e\room\NPC[0]\IsDead = True
					ChangeNPCTextureID(e\room\NPC[0], NPC_CLASS_D_BODY_1_TEXTURE)
					SetNPCFrame(e\room\NPC[0], 40.0)
					RotateEntity(e\room\NPC[0]\Collider, 0.0, e\room\Angle + 90.0, 0.0)
				EndIf
				
				If PlayerRoom = e\room Then
					e\EventState2 = UpdateElevators(e\EventState2, e\room\RoomDoors[0], e\room\RoomDoors[1], e\room\Objects[0], e\room\Objects[1], e)
					
					If EntityY(me\Collider) > 1.0 Then
						e\EventState = UpdateLever(e\room\RoomLevers[0]\OBJ)
						UpdateLever(e\room\RoomLevers[1]\OBJ)
					EndIf
				EndIf
				;[End Block]
			Case e_room2_2_ez
				;[Block]
				If PlayerRoom = e\room Then
					If me\BlinkTimer < -8.0 And me\BlinkTimer > -12.0 Then
						Temp = Rand(4)
						PositionEntity(e\room\Objects[0], EntityX(e\room\Objects[Temp], True), EntityY(e\room\Objects[Temp], True), EntityZ(e\room\Objects[Temp], True), True)
						RotateEntity(e\room\Objects[0], 0.0, Rnd(360.0), 0.0)
					EndIf
				EndIf
				;[End Block]
			Case e_room2_ez_035
				;[Block]
				Local Is035Released% = False
				
				For e2.Events = Each Events
					If e2 <> e And e2\EventID = e_cont1_035 Then
						If e2\EventState < 0.0 Then
							Is035Released = True
							Exit
						EndIf
					EndIf
				Next
				
				If Is035Released Then
					If e\room\Dist < 8.0 Then
						If e\room\NPC[0] = Null Then
							e\room\NPC[0] = CreateNPC(NPCTypeD, e\room\x, 0.5, e\room\z)
							e\room\NPC[0]\State3 = -1.0 : e\room\NPC[0]\IsDead = True
							SetNPCFrame(e\room\NPC[0], 19.0)
							RotateEntity(e\room\NPC[0]\Collider, 0.0, e\room\Angle + 180.0, 0.0)
							MoveEntity(e\room\NPC[0]\Collider, 0.0, 0.0, -0.5)
							ChangeNPCTextureID(e\room\NPC[0], NPC_CLASS_D_VICTIM_035_TEXTURE)
						EndIf
						If EntityDistanceSquared(e\room\NPC[0]\Collider, me\Collider) < 6.25 Then
							If wi\HazmatSuit = 0 Then
								InjurePlayer(fps\Factor[0] / 10000.0)
							ElseIf wi\HazmatSuit <> 4
								InjurePlayer(fps\Factor[0] / 20000.0)
							Else
								InjurePlayer(fps\Factor[0] / 30000.0)
							EndIf
							If e\room\NPC[1] = Null Then
								e\room\NPC[1] = CreateNPC(NPCType035_Tentacle, EntityX(e\room\NPC[0]\Collider), 0.13, EntityZ(e\room\NPC[0]\Collider))
								RotateEntity(e\room\NPC[1]\Collider, 0.0, e\room\Angle, 0.0)
								MoveEntity(e\room\NPC[1]\Collider, 0.0, 0.0, 0.6)
							EndIf
						EndIf
					Else
						If e\room\Dist > HideDistance Then
							If e\room\NPC[1] <> Null Then RemoveNPC(e\room\NPC[1]) : e\room\NPC[1] = Null
						EndIf
					EndIf
				EndIf
				
				If e\room\NPC[1] <> Null Then
					If e\room\NPC[1]\IsDead Then RemoveEvent(e)
				EndIf
				;[End Block]
			Case e_room2_tesla
				;[Block]
				If e\room\Dist < 16 Then
					If PlayerRoom = e\room Then
						Temp = True
						For e2.Events = Each Events
							If e2\EventID = e\EventID And e2 <> e Then
								If e2\room\NPC[0] <> Null Then
									Temp = False
									Exit
								EndIf
							EndIf
						Next
						
						If e\room\NPC[0] = Null And Temp Then
							If (e\room\Angle Mod 180 = 90.0) Then
								If Abs(EntityX(me\Collider, True) < EntityX(e\room\Objects[0], True))
									x = 800.0
									z = 0.0
								Else
									x = -800.0
									z = 0.0
								EndIf
							Else
								If Abs(EntityZ(me\Collider, True) < EntityZ(e\room\Objects[0], True))
									x = 0.0
									z = 800.0
								Else
									x = 0.0
									z = -800.0
								EndIf
							EndIf
							e\room\NPC[0] = CreateNPC(NPCTypeClerk, EntityX(e\room\Objects[0], True) + x * RoomScale, 0.5, EntityZ(e\room\Objects[0], True) + z * RoomScale)
							e\room\NPC[0]\State = 2.0 : e\room\NPC[0]\Speed = 1.8
							PointEntity(e\room\NPC[0]\Collider, e\room\Objects[0])
							e\EventState = 0.0
						EndIf
					EndIf
					
					For n.NPCs = Each NPCs
						UpdateNPCParticles(n)
						
						Select n\NPCType
							Case NPCType106
								;[Block]
								If n\State3 = 1.0 Then
									AnimateNPC(n, 259.0, 110.0, -0.1, False)
									
									If n\Frame <= 110.1 Then
										If e\Sound2 <> 0 Then FreeSound_Strict(e\Sound2) : e\Sound2 = 0
										PositionEntity(n\Collider, 0.0, 500.0, 0.0)
										ResetEntity(n\Collider)
										
										n\Idle = 0 : n\State = 70.0 * 60.0 * Rnd(10.0, 13.0) : n\State3 = 0.0
									EndIf
								EndIf
								;[End Block]
						End Select
					Next
					
					Select e\EventState
						Case 0.0 ; ~ Idle state
							;[Block]
							UpdateRedLight(e\room\Objects[2], 1500, 800)
							HideEntity(e\room\Objects[1])
							e\SoundCHN = LoopSound2(TeslaIdleSFX, e\SoundCHN, Camera, e\room\Objects[1], 4.0, 0.5)
							e\EventState2 = 0.0
							If Abs(EntityX(me\Collider, True) - EntityX(e\room\Objects[0], True)) < 1.0 And (e\room\Angle Mod 180 = 90.0) Lor Abs(EntityZ(me\Collider, True) - EntityZ(e\room\Objects[0], True)) < 1.0 And (e\room\Angle Mod 180 = 0.0) Then
								If EntityDistanceSquared(me\Collider, e\room\Objects[0]) < PowTwo(300.0 * RoomScale) And (Not me\Terminated) And (Not chs\NoTarget) Then
									StopChannel(e\SoundCHN) : e\SoundCHN = 0
									e\SoundCHN = PlaySound2(TeslaActivateSFX, Camera, e\room\Objects[1], 4.0, 0.5)
									If (e\room\NPC[0] = Null) Lor e\room\NPC[0]\IsDead Then e\EventState = 1.0
								EndIf
							EndIf
							For n.NPCs = Each NPCs
								If n\NPCType <> NPCType966 And n\NPCType <> NPCType513_1 And (Not n\IsDead) Then
									If n\NPCType = NPCTypeMTF And e\room\NPC[1] = Null Then
										If Abs(EntityX(n\Collider, True) - EntityX(e\room\Objects[0], True)) <= 5.0 And (e\room\Angle Mod 180 = 90.0) Lor Abs(EntityZ(n\Collider, True) - EntityZ(e\room\Objects[0], True)) <= 5.0 And (e\room\Angle Mod 180 = 0.0) Then
											If EntityDistanceSquared(n\Collider, e\room\Objects[0]) < PowTwo(500.0 * RoomScale) Then
												n\Idle = 70.0 * 10.0
												LoadNPCSound(n, "SFX\Character\MTF\Tesla0.ogg")
												PlayMTFSound(n\Sound, n)
												e\room\NPC[1] = n
											EndIf
										EndIf
									Else
										If Abs(EntityX(n\Collider, True) - EntityX(e\room\Objects[0], True)) < 0.8 And (e\room\Angle Mod 180 = 90.0) Lor Abs(EntityZ(n\Collider, True) - EntityZ(e\room\Objects[0], True)) < 0.8 And (e\room\Angle Mod 180 = 0.0) Then
											If EntityDistanceSquared(n\Collider, e\room\Objects[0]) < PowTwo(300.0 * RoomScale) Then
												StopChannel(e\SoundCHN) : e\SoundCHN = 0
												e\SoundCHN = PlaySound2(TeslaActivateSFX, Camera, e\room\Objects[1], 4.0, 0.5)
												e\EventState = 1.0
											EndIf
										EndIf
									EndIf
								EndIf
							Next
							If e\room\NPC[1] <> Null Then
								If e\room\NPC[1]\Idle <= 0.0 Then
									StopChannel(e\SoundCHN) : e\SoundCHN = 0
									PlayAnnouncement("SFX\Character\MTF\Tesla" + Rand(3) + ".ogg")
									e\EventState = 3.0
									e\EventState2 = (-70.0) * 90.0
									e\room\NPC[1] = Null
								EndIf
							EndIf
							;[End Block]
						Case 1.0 ; ~ Charge state
							;[Block]
							UpdateRedLight(e\room\Objects[2], 100, 50)
							e\EventState2 = e\EventState2 + fps\Factor[0]
							If e\EventState2 >= 35.0 Then
								e\EventState = 2.0
								PlaySound2(LoadTempSound("SFX\Room\Tesla\Shock.ogg"), Camera ,e\room\Objects[1])
							EndIf
							;[End Block]
						Case 2.0 ; ~ Zap state
							;[Block]
							If Abs(EntityX(me\Collider, True) - EntityX(e\room\Objects[0], True)) < 0.75 And (e\room\Angle Mod 180 = 90.0) Lor Abs(EntityZ(me\Collider, True) - EntityZ(e\room\Objects[0], True)) < 0.75 And (e\room\Angle Mod 180 = 0.0) Then
								If EntityDistanceSquared(me\Collider, e\room\Objects[0]) < PowTwo(300.0 * RoomScale) And (Not me\Terminated) Then
									me\LightFlash = 0.4
									me\CameraShake = 1.0
									Kill()
									msg\DeathMsg = Format(GetLocalString("death", "tesla"), SubjectName)
								EndIf
							EndIf
							For n.NPCs = Each NPCs
								If n\NPCType <> NPCType966 And n\NPCType <> NPCType513_1 And (Not n\IsDead) Then
									If Abs(EntityX(n\Collider, True) - EntityX(e\room\Objects[0], True)) < 0.5 And (e\room\Angle Mod 180 = 90.0) Lor Abs(EntityZ(n\Collider, True) - EntityZ(e\room\Objects[0], True)) < 0.5 And (e\room\Angle Mod 180 = 0.0) Then
										If EntityDistanceSquared(n\Collider, e\room\Objects[0]) < PowTwo(300.0 * RoomScale) Then
											n\CurrSpeed = 0.0 : n\TeslaHit = True
											Select n\NPCType
												Case NPCType106
													;[Block]
													If n\State3 = 0.0 Then
														GiveAchievement(AchvTesla)
														
														PlaySound2(LoadTempSound("SFX\Ending\GateA\106Retreat.ogg"), Camera, n\OBJ, 10.0)
														
														de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(n\Collider), e\room\y + 0.005, EntityZ(n\Collider), 90.0, Rnd(360.0), 0.0, Rnd(0.5, 0.7), Rnd(0.8, 1.0))
														de\SizeChange = 0.004 : de\Timer = 90000.0
														
														SetNPCFrame(n, 259.0)
														n\Idle = 1 : n\State3 = 1.0
													EndIf
													;[End Block]
												Case NPCType049, NPCType096, NPCType173, NPCType066, NPCType1499_1
													;[Block]
													; ~ Skip
													;[End Block]
												Default
													;[Block]
													If n\NPCType = NPCTypeClerk Lor n\NPCType = NPCTypeD Then n\State3 = 1.0
													n\IsDead = True
													;[End Block]
											End Select
											If e\room\Dist < 8 And EntityInView(e\room\Objects[0], Camera) Then me\LightFlash = 0.4
										EndIf
									EndIf
								EndIf
							Next
							If Rand(5) < 5 Then
								PositionTexture(t\OverlayTextureID[11], 0.0, Rnd(0.0, 1.0))
								If EntityHidden(e\room\Objects[1]) Then ShowEntity(e\room\Objects[1])
								If e\room\Dist < 8.0 Then LightVolume = TempLightVolume * Rnd(1.0, 2.0)
							EndIf
							e\EventState2 = e\EventState2 - (fps\Factor[0] * 1.5)
							If e\EventState2 <= 0.0 Then
								e\EventState = 3.0
								e\EventState2 = -70.0
								StopChannel(e\SoundCHN) : e\SoundCHN = 0
								e\SoundCHN = PlaySound2(TeslaPowerUpSFX, Camera, e\room\Objects[1], 4.0, 0.5)
							EndIf
							;[End Block]
						Case 3.0 ; ~ Recharge state
							;[Block]
							e\EventState2 = e\EventState2 + fps\Factor[0]
							If (Not EntityHidden(e\room\Objects[1])) Then HideEntity(e\room\Objects[1])
							If e\EventState2 >= 0.0 Then e\EventState = 0.0
							;[End Block]
					End Select
				EndIf
				;[End Block]
			Case e_trick
				;[Block]
				If PlayerRoom = e\room Then
					If EntityDistanceSquared(e\room\OBJ, me\Collider) < 4.0 Then
						If EntityDistanceSquared(me\Collider, n_I\Curr173\OBJ) < 36.0 Lor EntityDistanceSquared(me\Collider, n_I\Curr106\OBJ) < 36.0 Then
							RemoveEvent(e)
						Else
							Pvt = CreatePivot()
							PositionEntity(Pvt, EntityX(me\Collider), EntityY(me\Collider), EntityZ(me\Collider))
							PointEntity(Pvt, e\room\OBJ)
							RotateEntity(Pvt, 0.0, EntityYaw(Pvt), 0.0, True)
							MoveEntity(Pvt, 0.0, 0.0, EntityDistance(Pvt, e\room\OBJ) * 2.0)
							
							me\BlinkTimer = -10.0
							
							PlaySound_Strict(HorrorSFX[11])
							
							PositionEntity(me\Collider, EntityX(Pvt), EntityY(Pvt) + 0.05, EntityZ(Pvt))
							UpdateWorld()
							
							TurnEntity(me\Collider, 0.0, 180.0, 0.0)
							
							FreeEntity(Pvt)
							RemoveEvent(e)
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_room2_mt
				;[Block]
				If EntityY(me\Collider, True) >= 8.0 And EntityY(me\Collider, True) <= 12.0 Then
					If (EntityX(me\Collider, True) >= e\room\x - 6.0) And (EntityX(me\Collider, True) <= (e\room\x + 2.0 * MTGridSize + 6.0)) Then
						If (EntityZ(me\Collider, True) >= e\room\z - 6.0) And (EntityZ(me\Collider, True) <= (e\room\z + 2.0 * MTGridSize + 6.0)) Then PlayerRoom = e\room
					EndIf
				EndIf
				
				If PlayerRoom = e\room Then
					Local Meshes%[7]
					Local TempStr$
					Local iA%, iB%, iC%, iD%
					Local dr.Doors
					Local TempInt%, TempInt2%
					Local iX%, iY%
					
					If I_Zone\HasCustomMT Then
						If (Not e\room\mt\Meshes[MT_ROOM1]) Then PlaceMapCreatorMT(e\room)
					EndIf
					
					If e\room\mt = Null Then
						e\room\mt.MTGrid = New MTGrid
						
						Local OldSeed% = RndSeed()
						
						SeedRnd(GenerateSeedNumber(RandomSeed))
						
						Local Dir%
						
						Dir = Rand(0, 1) Shl 1
						; ~ 0 = right
						; ~ 1 = up
						; ~ 2 = left
						; ~ 3 = down
						
						iX = (MTGridSize) / 2 + Rand(-2, 2)
						iY = (MTGridSize) / 2 + Rand(-2, 2)
						
						e\room\mt\Grid[iX + (iY * MTGridSize)] = 1
						
						If Dir = 2 Then
							e\room\mt\Grid[(iX + 1) + (iY * MTGridSize)] = 1
						Else
							e\room\mt\Grid[(iX - 1) + (iY * MTGridSize)] = 1
						EndIf
						
						Local Count% = 2
						
						While Count < 100
							TempInt = Rand(5) Shl Rand(2)
							For i = 1 To TempInt
								TempInt2 = True
								
								Select Dir
									Case 0
										;[Block]
										If iX < MTGridSize - 2 - (i Mod 2) Then
											iX = iX + 1
										Else
											TempInt2 = False
										EndIf
										;[End Block]
									Case 1
										;[Block]
										If iY < MTGridSize - 2 - (i Mod 2) Then
											iY = iY + 1
										Else
											TempInt2 = False
										EndIf
										;[End Block]
									Case 2
										;[Block]
										If iX > 1 + (i Mod 2) Then
											iX = iX - 1
										Else
											TempInt2 = False
										EndIf
										;[End Block]
									Case 3
										;[Block]
										If iY > 1 + (i Mod 2) Then
											iY = iY - 1
										Else
											TempInt2 = False
										EndIf
										;[End Block]
								End Select
								
								If TempInt2 Then
									If e\room\mt\Grid[iX + (iY * MTGridSize)] = 0 Then
										e\room\mt\Grid[iX + (iY * MTGridSize)] = 1
										Count = Count + 1
									EndIf
								Else
									Exit
								EndIf
							Next
							Dir = Dir + ((Rand(0, 1) Shl 1) - 1)
							While Dir < 0
								Dir = Dir + 4
							Wend
							While Dir > 3
								Dir = Dir - 4
							Wend
						Wend
						
						; ~ Generate the tunnels
						For iY = 0 To MTGridSize - 1
							For iX = 0 To MTGridSize - 1
								If e\room\mt\Grid[iX + (iY * MTGridSize)] > 0 Then e\room\mt\Grid[iX + (iY * MTGridSize)] = (e\room\mt\Grid[(iX) + ((iY + 1) * MTGridSize)] > 0) + (e\room\mt\Grid[(iX) + ((iY - 1) * MTGridSize)] > 0) + (e\room\mt\Grid[(iX + 1) + ((iY) * MTGridSize)] > 0) + (e\room\mt\Grid[(iX - 1) + ((iY) * MTGridSize)] > 0)
							Next
						Next
						
						Local MaxX% = MTGridSize - 1
						Local CanRetry% = False
						
						For iX = 0 To MaxX
							For iY = 0 To MTGridSize - 1
								If e\room\mt\Grid[iX + 1 + (iY * MTGridSize)] > 0 Then
									MaxX = iX
									If (e\room\mt\Grid[iX + 1 + ((iY + 1) * MTGridSize)] < 3) And (e\room\mt\Grid[iX + 1 + ((iY - 1) * MTGridSize)] < 3) Then
										CanRetry = True
										If Rand(0, 1) = 1 Then
											e\room\mt\Grid[iX + 1 + ((iY) * MTGridSize)] = e\room\mt\Grid[iX + 1 + ((iY) * MTGridSize)] + 1
											e\room\mt\Grid[iX + ((iY) * MTGridSize)] = 7 ; ~ Generator room
											CanRetry = False
											Exit
										EndIf
									EndIf
								EndIf
							Next
							If CanRetry Then iX = iX - 1
						Next
						
						Local FirstX%, LastX%
						Local FirstY%, LastY%
						
						FirstX = -1
						LastY = -1
						FirstX = -1
						LastY = -1
						
						For iY = 0 To MTGridSize - 1
							For iX = 0 To MTGridSize - 1
								If e\room\mt\Grid[iX + (iY * MTGridSize)] = 2 Then
									If e\room\mt\Grid[(iX + 1) + ((iY) * MTGridSize)] > 0 And e\room\mt\Grid[(iX - 1) + ((iY) * MTGridSize)] > 0 Then ; ~ Horizontal
										If FirstX = -1 Lor FirstY = -1 Then
											If e\room\mt\Grid[iX - 1 + (iY * MTGridSize)] < 3 And e\room\mt\Grid[iX + 1 + (iY * MTGridSize)] < 3 And e\room\mt\Grid[iX + ((iY - 1) * MTGridSize)] < 3 And e\room\mt\Grid[iX + ((iY + 1) * MTGridSize)] < 3 Then
												If e\room\mt\Grid[iX - 1 + ((iY - 1) * MTGridSize)] < 1 And e\room\mt\Grid[iX + 1 + ((iY - 1) * MTGridSize)] < 1 And e\room\mt\Grid[iX + 1 + ((iY - 1) * MTGridSize)] < 1 And e\room\mt\Grid[iX - 1 + ((iY + 1) * MTGridSize)] < 1 Then FirstX = iX : FirstY = iY
											EndIf
										EndIf
										If e\room\mt\Grid[iX - 1 + (iY * MTGridSize)] < 3 And e\room\mt\Grid[iX + 1 + (iY * MTGridSize)] < 3 And e\room\mt\Grid[iX + ((iY - 1) * MTGridSize)] < 3 And e\room\mt\Grid[iX + ((iY + 1) * MTGridSize)] < 3 Then
											If e\room\mt\Grid[iX - 1 + ((iY - 1) * MTGridSize)] < 1 And e\room\mt\Grid[iX + 1 + ((iY - 1) * MTGridSize)] < 1 And e\room\mt\Grid[iX + 1 + ((iY - 1) * MTGridSize)] < 1 And e\room\mt\Grid[iX - 1 + ((iY + 1) * MTGridSize)] < 1 Then LastX = iX : LastY = iY
										EndIf
									ElseIf e\room\mt\Grid[(iX) + ((iY + 1) * MTGridSize)] > 0 And e\room\mt\Grid[(iX) + ((iY - 1) * MTGridSize)] > 0 Then ; ~ Vertical
										If FirstX = -1 Lor FirstY = -1 Then
											If e\room\mt\Grid[iX - 1 + (iY * MTGridSize)] < 3 And e\room\mt\Grid[iX + 1 + (iY * MTGridSize)] < 3 And e\room\mt\Grid[iX + ((iY - 1) * MTGridSize)] < 3 And e\room\mt\Grid[iX + ((iY + 1) * MTGridSize)] < 3 Then
												If e\room\mt\Grid[iX - 1 + ((iY - 1) * MTGridSize)] < 1 And e\room\mt\Grid[iX + 1 + ((iY - 1) * MTGridSize)] < 1 And e\room\mt\Grid[iX + 1 + ((iY - 1) * MTGridSize)] < 1 And e\room\mt\Grid[iX - 1 + ((iY + 1) * MTGridSize)] < 1 Then FirstX = iX : FirstY = iY
											EndIf
										EndIf
										If e\room\mt\Grid[iX - 1 + (iY * MTGridSize)] < 3 And e\room\mt\Grid[iX + 1 + (iY * MTGridSize)] < 3 And e\room\mt\Grid[iX + ((iY - 1) * MTGridSize)] < 3 And e\room\mt\Grid[iX + ((iY + 1) * MTGridSize)] < 3 Then
											If e\room\mt\Grid[iX - 1 + ((iY - 1) * MTGridSize)] < 1 And e\room\mt\Grid[iX + 1 + ((iY - 1) * MTGridSize)] < 1 And e\room\mt\Grid[iX + 1 + ((iY - 1) * MTGridSize)] < 1 And e\room\mt\Grid[iX - 1 + ((iY + 1) * MTGridSize)] < 1 Then LastX = iX : LastY = iY
										EndIf
									EndIf
								EndIf
							Next
						Next
						
						If LastX = FirstX And LastY = FirstY Then RuntimeError(GetLocalString("runerr", "mt"))
						
						; ~ Place the tunnels
						For i = MT_ROOM1 To MT_GENERATOR
							Meshes[i] = CopyEntity(misc_I\MTModelID[i])
							HideEntity(Meshes[i])
						Next
						
						TempInt = 0
						
						For iY = 0 To MTGridSize - 1
							For iX = 0 To MTGridSize - 1
								If e\room\mt\Grid[iX + (iY * MTGridSize)] > 0 Then
									Select e\room\mt\Grid[iX + (iY * MTGridSize)]
										Case MT_ROOM1 + 1, MT_ROOM4 + 3
											;[Block]
											TempInt = CopyEntity(Meshes[e\room\mt\Grid[iX + (iY * MTGridSize)] - 1])
											
											If e\room\mt\Grid[(iX + 1) + ((iY) * MTGridSize)] > 0 Then
												RotateEntity(TempInt, 0.0, 90.0, 0.0)
												e\room\mt\Angles[iX + (iY * MTGridSize)] = 1
											ElseIf e\room\mt\Grid[(iX - 1) + ((iY) * MTGridSize)] > 0
												RotateEntity(TempInt, 0.0, 270.0, 0.0)
												e\room\mt\Angles[iX + (iY * MTGridSize)] = 3
											ElseIf e\room\mt\Grid[(iX) + ((iY + 1) * MTGridSize)] > 0
												RotateEntity(TempInt, 0.0, 180.0, 0.0)
												e\room\mt\Angles[iX + (iY * MTGridSize)] = 2
											Else
												RotateEntity(TempInt, 0.0, 0.0, 0.0)
												e\room\mt\Angles[iX + (iY * MTGridSize)] = 0
											EndIf
											;[End Block]
										Case MT_ROOM2 + 1
											;[Block]
											If (iX = FirstX And iY = FirstY) Lor (iX = LastX And iY = LastY) Then e\room\mt\Grid[iX + (iY * MTGridSize)] = 6
											
											If e\room\mt\Grid[(iX + 1) + (iY * MTGridSize)] > 0 And e\room\mt\Grid[(iX - 1) + (iY * MTGridSize)] > 0 Then ; ~ Horizontal
												TempInt = CopyEntity(Meshes[e\room\mt\Grid[iX + (iY * MTGridSize)] - 1])
												
												AddLight(Null, e\room\x + (iX * 2.0), e\room\y + MTGridY + (372.0 * RoomScale), e\room\z + (iY * 2.0), 2, 500.0 * RoomScale, 255, 255, 255)
												
												TempInt2 = Rand(0, 1)
												RotateEntity(TempInt, 0.0, (TempInt2 * 180.0) + 90.0, 0.0)
												
												e\room\mt\Angles[iX + (iY * MTGridSize)] = (TempInt2 * 2) + 1
											ElseIf e\room\mt\Grid[iX + ((iY + 1) * MTGridSize)] > 0 And e\room\mt\Grid[iX + ((iY - 1) * MTGridSize)] > 0 ; ~ Vertical
												TempInt = CopyEntity(Meshes[e\room\mt\Grid[iX + (iY * MTGridSize)] - 1])
												
												AddLight(Null, e\room\x + (iX * 2.0), e\room\y + MTGridY + (372.0 * RoomScale), e\room\z + (iY * 2.0), 2, 500.0 * RoomScale, 255, 255, 255)
												
												TempInt2 = Rand(0, 1)
												RotateEntity(TempInt, 0.0, TempInt2 * 180.0, 0.0)
												e\room\mt\Angles[iX + (iY * MTGridSize)] = (TempInt2 * 2)
											Else
												TempInt = CopyEntity(Meshes[e\room\mt\Grid[iX + (iY * MTGridSize)]])
												
												AddLight(Null, e\room\x + (iX * 2.0), e\room\y + MTGridY + (416.0 * RoomScale), e\room\z + (iY * 2.0), 2, 500.0 * RoomScale, 255, 255, 255)
												
												iA = e\room\mt\Grid[iX + ((iY + 1) * MTGridSize)]
												iB = e\room\mt\Grid[iX + ((iY - 1) * MTGridSize)]
												iC = e\room\mt\Grid[(iX + 1) + (iY * MTGridSize)]
												iD = e\room\mt\Grid[(iX - 1) + (iY * MTGridSize)]
												
												If iA > 0 And iC > 0 Then
													RotateEntity(TempInt, 0.0, 0.0, 0.0)
													e\room\mt\Angles[iX + (iY * MTGridSize)] = 0
												ElseIf iA > 0 And iD > 0
													RotateEntity(TempInt, 0.0, 90.0, 0.0)
													e\room\mt\Angles[iX + (iY * MTGridSize)] = 1
												ElseIf iB > 0 And iC > 0
													RotateEntity(TempInt, 0.0, 270.0, 0.0)
													e\room\mt\Angles[iX + (iY * MTGridSize)] = 3
												Else
													RotateEntity(TempInt, 0.0, 180.0, 0.0)
													e\room\mt\Angles[iX + (iY * MTGridSize)] = 2
												EndIf
											EndIf
											
											If iX = FirstX And iY = FirstY Then e\room\mt\Grid[iX + (iY * MTGridSize)] = 5
											;[End Block]
										Case MT_ROOM2C + 1
											;[Block]
											TempInt = CopyEntity(Meshes[e\room\mt\Grid[iX + (iY * MTGridSize)]])
											
											iA = e\room\mt\Grid[iX + ((iY + 1) * MTGridSize)]
											iB = e\room\mt\Grid[iX + ((iY - 1) * MTGridSize)]
											iC = e\room\mt\Grid[(iX + 1) + (iY * MTGridSize)]
											iD = e\room\mt\Grid[(iX - 1) + (iY * MTGridSize)]
											If iA > 0 And iC > 0 And iD > 0 Then
												RotateEntity(TempInt, 0.0, 90.0, 0.0)
												e\room\mt\Angles[iX + (iY * MTGridSize)] = 1
											ElseIf iB > 0 And iC > 0 And iD > 0
												RotateEntity(TempInt, 0.0, 270.0, 0.0)
												e\room\mt\Angles[iX + (iY * MTGridSize)] = 3
											ElseIf iC > 0 And iA > 0 And iB > 0
												RotateEntity(TempInt, 0.0, 0.0, 0.0)
												e\room\mt\Angles[iX + (iY * MTGridSize)] = 0
											Else
												RotateEntity(TempInt, 0.0, 180.0, 0.0)
												e\room\mt\Angles[iX + (iY * MTGridSize)] = 2
											EndIf
											;[End Block]
										Case MT_ROOM3 + 1
											;[Block]
											TempInt = CopyEntity(Meshes[e\room\mt\Grid[iX + (iY * MTGridSize)]])
											
											TempInt2 = Rand(0, 3)
											RotateEntity(TempInt, 0.0, TempInt2 * 90.0, 0.0)
											
											e\room\mt\Angles[iX + (iY * MTGridSize)] = TempInt2
											;[End Block]
									End Select
									
									ScaleEntity(TempInt, RoomScale, RoomScale, RoomScale, True)
									PositionEntity(TempInt, e\room\x + (iX * 2.0), e\room\y + MTGridY, e\room\z + (iY * 2.0), True)
									
									Select e\room\mt\Grid[iX + (iY * MTGridSize)]
										Case MT_ROOM1 + 1
											;[Block]
											AddLight(Null, e\room\x + (iX * 2.0), e\room\y + MTGridY + (372.0 * RoomScale), e\room\z + (iY * 2.0), 2, 500.0 * RoomScale, 255, 255, 255)
											;[End Block]
										Case MT_ROOM2C + 1, MT_ROOM3 + 1
											;[Block]
											AddLight(Null, e\room\x + (iX * 2.0), e\room\y + MTGridY + (416.0 * RoomScale), e\room\z + (iY * 2.0), 2, 500.0 * RoomScale, 255, 255, 255)
											;[End Block]
										Case MT_ROOM4 + 3
											;[Block]
											AddLight(Null, e\room\x + (iX * 2.0) - (Sin(EntityYaw(TempInt, True)) * 504.0 * RoomScale) + (Cos(EntityYaw(TempInt, True)) * 16.0 * RoomScale), e\room\y + MTGridY + (396.0 * RoomScale), e\room\z + (iY * 2.0) + (Cos(EntityYaw(TempInt, True)) * 504.0 * RoomScale) + (Sin(EntityYaw(TempInt, True)) * 16.0 * RoomScale), 2, 500.0 * RoomScale, 255, 200, 200)
											it.Items = CreateItem("SCP-500-01", "scp500pill", e\room\x + (iX * 2.0) + (Cos(EntityYaw(TempInt, True)) * (-208.0) * RoomScale) - (Sin(EntityYaw(TempInt, True)) * 1226.0 * RoomScale), e\room\y + MTGridY + (90.0 * RoomScale), e\room\z + (iY * 2.0) + (Sin(EntityYaw(TempInt, True)) * (-208.0) * RoomScale) + (Cos(EntityYaw(TempInt, True)) * 1226.0 * RoomScale))
											EntityType(it\Collider, HIT_ITEM)
											
											it.Items = CreateItem("Night Vision Goggles", "nvg", e\room\x + (iX * 2.0) - (Sin(EntityYaw(TempInt, True)) * 504.0 * RoomScale) + (Cos(EntityYaw(TempInt, True)) * 16.0 * RoomScale), e\room\y + MTGridY + (90.0 * RoomScale), e\room\z + (iY * 2.0) + (Cos(EntityYaw(TempInt, True)) * 504.0 * RoomScale) + (Sin(EntityYaw(TempInt, True)) * 16.0 * RoomScale))
											EntityType(it\Collider, HIT_ITEM)
											;[End Block]
									End Select
									
									If e\room\mt\Grid[iX + (iY * MTGridSize)] = 6 Lor e\room\mt\Grid[iX + (iY * MTGridSize)] = 5 Then
										dr.Doors = CreateDoor(e\room\x + (iX * 2.0) + (Cos(EntityYaw(TempInt, True)) * 240.0 * RoomScale), e\room\y + MTGridY, e\room\z + (iY * 2.0) + (Sin(EntityYaw(TempInt, True)) * 240.0 * RoomScale), EntityYaw(TempInt, True) - 90.0, Null, False, ELEVATOR_DOOR)
										PositionEntity(dr\Buttons[0], EntityX(dr\Buttons[0], True) + (Cos(EntityYaw(TempInt, True)) * 0.05), EntityY(dr\Buttons[0], True), EntityZ(dr\Buttons[0], True) + (Sin(EntityYaw(TempInt, True)) * 0.05), True)
										PositionEntity(dr\Buttons[1], EntityX(dr\Buttons[1], True) + (Cos(EntityYaw(TempInt, True)) * 0.05), EntityY(dr\Buttons[1], True), EntityZ(dr\Buttons[1], True) + (Sin(EntityYaw(TempInt, True)) * 0.031), True)
										PositionEntity(dr\ElevatorPanel[0], EntityX(dr\ElevatorPanel[0], True) + (Cos(EntityYaw(TempInt, True)) * 0.05), EntityY(dr\ElevatorPanel[0], True), EntityZ(dr\ElevatorPanel[0], True) + (Sin(EntityYaw(TempInt, True)) * 0.05), True)
										PositionEntity(dr\ElevatorPanel[1], EntityX(dr\ElevatorPanel[1], True) + (Cos(EntityYaw(TempInt, True)) * 0.05), EntityY(dr\ElevatorPanel[1], True) + 0.1, EntityZ(dr\ElevatorPanel[1], True) + (Sin(EntityYaw(TempInt, True)) * (-0.18)), True)
										RotateEntity(dr\ElevatorPanel[1], EntityPitch(dr\ElevatorPanel[1], True) + 45.0, EntityYaw(dr\ElevatorPanel[1], True), EntityRoll(dr\ElevatorPanel[1], True), True)
										
										AddLight(Null, e\room\x + (iX * 2.0) + (Cos(EntityYaw(TempInt, True)) * 555.0 * RoomScale), e\room\y + MTGridY + (469.0 * RoomScale), e\room\z + (iY * 2.0) + (Sin(EntityYaw(TempInt, True)) * 555.0 * RoomScale), 2, 600.0 * RoomScale, 255, 255, 255)
										
										TempInt2 = CreatePivot()
										RotateEntity(TempInt2, 0.0, EntityYaw(TempInt, True) + 180.0, 0.0, True)
										PositionEntity(TempInt2, e\room\x + (iX * 2.0) + (Cos(EntityYaw(TempInt, True)) * 552.0 * RoomScale), e\room\y + MTGridY + (240.0 * RoomScale), e\room\z + (iY * 2.0) + (Sin(EntityYaw(TempInt, True)) * 552.0 * RoomScale))
										If e\room\mt\Grid[iX + (iY * MTGridSize)] = 6 Then
											If e\room\RoomDoors[1] <> Null Then
												RemoveDoor(dr)
											Else
												dr\Open = (Not e\room\RoomDoors[0]\Open)
												e\room\RoomDoors[1] = dr
											EndIf
											If (Not e\room\Objects[3]) Then
												e\room\Objects[3] = TempInt2
												PositionEntity(e\room\Objects[1], e\room\x + (iX * 2.0), EntityY(e\room\Objects[1], True), e\room\z + (iY * 2.0), True)
											Else
												FreeEntity(TempInt2)
											EndIf
										Else
											If e\room\RoomDoors[3] <> Null Then
												RemoveDoor(dr)
											Else
												dr\Open = (Not e\room\RoomDoors[2]\Open)
												e\room\RoomDoors[3] = dr
											EndIf
											If (Not e\room\Objects[5]) Then
												e\room\Objects[5] = TempInt2
												PositionEntity(e\room\Objects[0], e\room\x + (iX * 2.0), EntityY(e\room\Objects[0], True), e\room\z + (iY * 2.0), True)
											Else
												FreeEntity(TempInt2)
											EndIf
										EndIf
									EndIf
									
									e\room\mt\Entities[iX + (iY * MTGridSize)] = TempInt
									
									wayp.WayPoints = CreateWaypoint(e\room\x + (iX * 2.0), e\room\y + MTGridY + 0.2, e\room\z + (iY * 2.0), Null, e\room)
									
									e\room\mt\waypoints[iX + (iY * MTGridSize)] = wayp
									
									If iY < MTGridSize - 1 Then
										If e\room\mt\waypoints[iX + ((iY + 1) * MTGridSize)] <> Null Then
											Dist = EntityDistance(e\room\mt\waypoints[iX + (iY * MTGridSize)]\OBJ, e\room\mt\waypoints[iX + ((iY + 1) * MTGridSize)]\OBJ)
											For i = 0 To 3
												If e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + ((iY + 1) * MTGridSize)] Then
													Exit
												ElseIf e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = Null Then
													e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + ((iY + 1) * MTGridSize)]
													e\room\mt\waypoints[iX + (iY * MTGridSize)]\Dist[i] = Dist
													Exit
												EndIf
											Next
											For i = 0 To 3
												If e\room\mt\waypoints[iX + ((iY + 1) * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + (iY * MTGridSize)] Then
													Exit
												ElseIf e\room\mt\waypoints[iX + ((iY + 1) * MTGridSize)]\connected[i] = Null Then
													e\room\mt\waypoints[iX + ((iY + 1) * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + (iY * MTGridSize)]
													e\room\mt\waypoints[iX + ((iY + 1) * MTGridSize)]\Dist[i] = Dist
													Exit
												EndIf
											Next
										EndIf
									EndIf
									If iY > 0 Then
										If e\room\mt\waypoints[iX + ((iY - 1) * MTGridSize)] <> Null Then
											Dist = EntityDistance(e\room\mt\waypoints[iX + (iY * MTGridSize)]\OBJ, e\room\mt\waypoints[iX + ((iY - 1) * MTGridSize)]\OBJ)
											For i = 0 To 3
												If e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + ((iY - 1) * MTGridSize)] Then
													Exit
												ElseIf e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = Null Then
													e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + ((iY - 1) * MTGridSize)]
													e\room\mt\waypoints[iX + (iY * MTGridSize)]\Dist[i] = Dist
													Exit
												EndIf
											Next
											For i = 0 To 3
												If e\room\mt\waypoints[iX + ((iY - 1) * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + (iY * MTGridSize)] Then
													Exit
												ElseIf e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = Null Then
													e\room\mt\waypoints[iX + ((iY - 1) * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + (iY * MTGridSize)]
													e\room\mt\waypoints[iX + ((iY - 1) * MTGridSize)]\Dist[i] = Dist
													Exit
												EndIf
											Next
										EndIf
									EndIf
									If iX > 0 Then
										If e\room\mt\waypoints[iX - 1 + (iY * MTGridSize)] <> Null Then
											Dist = EntityDistance(e\room\mt\waypoints[iX + (iY * MTGridSize)]\OBJ, e\room\mt\waypoints[iX - 1 + (iY * MTGridSize)]\OBJ)
											For i = 0 To 3
												If e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX - 1 + (iY * MTGridSize)] Then
													Exit
												ElseIf e\room\mt\waypoints[iX + (iY*MTGridSize)]\connected[i] = Null Then
													e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX - 1 + (iY * MTGridSize)]
													e\room\mt\waypoints[iX + (iY * MTGridSize)]\Dist[i] = Dist
													Exit
												EndIf
											Next
											For i = 0 To 3
												If e\room\mt\waypoints[iX - 1 + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + (iY * MTGridSize)] Then
													Exit
												ElseIf e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = Null Then
													e\room\mt\waypoints[iX - 1 + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + (iY * MTGridSize)]
													e\room\mt\waypoints[iX - 1 + (iY * MTGridSize)]\Dist[i] = Dist
													Exit
												EndIf
											Next
										EndIf
									EndIf
									If iX < MTGridSize - 1 Then
										If e\room\mt\waypoints[iX + 1 + (iY * MTGridSize)] <> Null Then
											Dist = EntityDistance(e\room\mt\waypoints[iX + (iY * MTGridSize)]\OBJ, e\room\mt\waypoints[iX + 1 + (iY * MTGridSize)]\OBJ)
											For i = 0 To 3
												If e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + 1 + (iY * MTGridSize)] Then
													Exit
												ElseIf e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = Null Then
													e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + 1 + (iY * MTGridSize)]
													e\room\mt\waypoints[iX + (iY * MTGridSize)]\Dist[i] = Dist
													Exit
												EndIf
											Next
											For i = 0 To 3
												If e\room\mt\waypoints[iX + 1 + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + (iY * MTGridSize)] Then
													Exit
												ElseIf e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = Null Then
													e\room\mt\waypoints[iX + 1 + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + (iY * MTGridSize)]
													e\room\mt\waypoints[iX + 1 + (iY * MTGridSize)]\Dist[i] = Dist
													Exit
												EndIf
											Next
										EndIf
									EndIf
								EndIf
							Next
						Next
						
						For i = MT_ROOM1 To MT_GENERATOR
							e\room\mt\Meshes[i] = Meshes[i]
						Next
						
						PositionEntity(e\room\Objects[0], e\room\x + (FirstX * 2.0), EntityY(e\room\Objects[0], True), e\room\z + (FirstY * 2.0), True)
						PositionEntity(e\room\Objects[1], e\room\x + (LastX * 2.0), EntityY(e\room\Objects[1], True), e\room\z + (LastY * 2.0), True)
					ElseIf (Not e\room\mt\Meshes[MT_ROOM1]) Then
						; ~ Place the tunnels
						For i = MT_ROOM1 To MT_GENERATOR
							Meshes[i] = CopyEntity(misc_I\MTModelID[i])
							HideEntity(Meshes[i])
						Next
						
						TempInt = 0
						
						For iY = 0 To MTGridSize - 1
							For iX = 0 To MTGridSize - 1
								If e\room\mt\Grid[iX + (iY * MTGridSize)] > 0 Then
									Select e\room\mt\Grid[iX + (iY * MTGridSize)]
										Case MT_ROOM1 + 1, MT_ROOM4 + 3
											;[Block]
											TempInt = CopyEntity(Meshes[e\room\mt\Grid[iX + (iY * MTGridSize)] - 1])
											;[End Block]
										Case MT_ROOM2 + 1
											;[Block]
											If e\room\mt\Grid[(iX + 1) + ((iY) * MTGridSize)] > 0 And e\room\mt\Grid[(iX - 1) + ((iY) * MTGridSize)] > 0 Then ; ~ Horizontal
												TempInt = CopyEntity(Meshes[e\room\mt\Grid[iX + (iY * MTGridSize)] - 1])
												AddLight(Null, e\room\x + (iX * 2.0), e\room\y + MTGridY + (372.0 * RoomScale), e\room\z + (iY * 2.0), 2, 500.0 * RoomScale, 255, 255, 255)
											ElseIf e\room\mt\Grid[(iX) + ((iY + 1) * MTGridSize)] > 0 And e\room\mt\Grid[(iX) + ((iY - 1) * MTGridSize)] > 0 Then ; ~ Vertical
												TempInt = CopyEntity(Meshes[e\room\mt\Grid[iX + (iY * MTGridSize)] - 1])
												AddLight(Null, e\room\x + (iX * 2.0), e\room\y + MTGridY + (372.0 * RoomScale), e\room\z + (iY * 2.0), 2, 500.0 * RoomScale, 255, 255, 255)
											Else
												TempInt = CopyEntity(Meshes[e\room\mt\Grid[iX + (iY * MTGridSize)]])
												AddLight(Null, e\room\x + (iX * 2.0), e\room\y + MTGridY + (416.0 * RoomScale), e\room\z + (iY * 2.0), 2, 500.0 * RoomScale, 255, 255, 255)
											EndIf
											;[End Block]
										Case MT_ROOM2C + 1, MT_ROOM3 + 1
											;[Block]
											TempInt = CopyEntity(Meshes[e\room\mt\Grid[iX + (iY * MTGridSize)]])
											;[End Block]
										Case MT_ROOM4 + 1, MT_ROOM4 + 2
											;[Block]
											TempInt = CopyEntity(Meshes[MT_ELEVATOR])
											;[End Block]
									End Select
									
									ScaleEntity(TempInt, RoomScale, RoomScale, RoomScale, True)
									
									RotateEntity(TempInt, 0.0, e\room\mt\Angles[iX + (iY * MTGridSize)] * 90.0, 0.0)
									PositionEntity(TempInt, e\room\x + (iX * 2.0), e\room\y + MTGridY, e\room\z + (iY * 2.0), True)
									
									Select e\room\mt\Grid[iX + (iY * MTGridSize)]
										Case MT_ROOM1 + 1, MT_ROOM4 + 1, MT_ROOM4 + 2
											;[Block]
											AddLight(Null, e\room\x + (iX * 2.0), e\room\y + MTGridY + (372.0 * RoomScale), e\room\z + (iY * 2.0), 2, 500.0 * RoomScale, 255, 255, 255)
											;[End Block]
										Case MT_ROOM2C + 1, MT_ROOM3 + 1
											;[Block]
											AddLight(Null, e\room\x + (iX * 2.0), e\room\y + MTGridY + (416.0 * RoomScale), e\room\z + (iY * 2.0), 2, 500.0 * RoomScale, 255, 255, 255)
											;[End Block]
										Case MT_ROOM4 + 3
											;[Block]
											AddLight(Null, e\room\x + (iX * 2.0) - (Sin(EntityYaw(TempInt, True)) * 504.0 * RoomScale) + (Cos(EntityYaw(TempInt, True)) * 16.0 * RoomScale), e\room\y + MTGridY + (396.0 * RoomScale), e\room\z + (iY * 2.0) + (Cos(EntityYaw(TempInt, True)) * 504.0 * RoomScale) + (Sin(EntityYaw(TempInt, True)) * 16.0 * RoomScale), 2, 500.0 * RoomScale, 255, 200, 200)
											;[End Block]
									End Select
									
									If e\room\mt\Grid[iX + (iY * MTGridSize)] = 6 Lor e\room\mt\Grid[iX + (iY * MTGridSize)] = 5 Then
										dr.Doors = CreateDoor(e\room\x + (iX * 2.0) + (Cos(EntityYaw(TempInt, True)) * 240.0 * RoomScale), e\room\y + MTGridY, e\room\z + (iY * 2.0) + (Sin(EntityYaw(TempInt, True)) * 240.0 * RoomScale), EntityYaw(TempInt, True) - 90.0, Null, False, ELEVATOR_DOOR)
										PositionEntity(dr\Buttons[0], EntityX(dr\Buttons[0], True) + (Cos(EntityYaw(TempInt, True)) * 0.05), EntityY(dr\Buttons[0], True), EntityZ(dr\Buttons[0], True) + (Sin(EntityYaw(TempInt, True)) * 0.05), True)
										PositionEntity(dr\Buttons[1], EntityX(dr\Buttons[1], True) + (Cos(EntityYaw(TempInt, True)) * 0.05), EntityY(dr\Buttons[1], True), EntityZ(dr\Buttons[1], True) + (Sin(EntityYaw(TempInt, True)) * 0.031), True)
										PositionEntity(dr\ElevatorPanel[0], EntityX(dr\ElevatorPanel[0], True) + (Cos(EntityYaw(TempInt, True)) * 0.05), EntityY(dr\ElevatorPanel[0], True), EntityZ(dr\ElevatorPanel[0], True) + (Sin(EntityYaw(TempInt, True)) * 0.05), True)
										PositionEntity(dr\ElevatorPanel[1], EntityX(dr\ElevatorPanel[1], True) + (Cos(EntityYaw(TempInt, True)) * 0.05), EntityY(dr\ElevatorPanel[1], True) + 0.1, EntityZ(dr\ElevatorPanel[1], True) + (Sin(EntityYaw(TempInt, True)) * (-0.18)), True)
										RotateEntity(dr\ElevatorPanel[1], EntityPitch(dr\ElevatorPanel[1], True) + 45.0, EntityYaw(dr\ElevatorPanel[1], True), EntityRoll(dr\ElevatorPanel[1], True), True)
										
										AddLight(Null, e\room\x + (iX * 2.0) + (Cos(EntityYaw(TempInt, True)) * 555.0 * RoomScale), e\room\y + MTGridY + (469.0 * RoomScale), e\room\z + (iY * 2.0) + (Sin(EntityYaw(TempInt, True)) * 555.0 * RoomScale), 2, 600.0 * RoomScale, 255, 255, 255)
										
										TempInt2 = CreatePivot()
										RotateEntity(TempInt2, 0.0, EntityYaw(TempInt, True) + 180.0, 0.0, True)
										PositionEntity(TempInt2, e\room\x + (iX * 2.0) + (Cos(EntityYaw(TempInt, True)) * 552.0 * RoomScale), e\room\y + MTGridY + (240.0 * RoomScale), e\room\z + (iY * 2.0) + (Sin(EntityYaw(TempInt, True)) * 552.0 * RoomScale))
										If e\room\mt\Grid[iX + (iY * MTGridSize)] = 6 Then
											If e\room\RoomDoors[1] <> Null Then
												RemoveDoor(dr)
											Else
												dr\Open = (Not e\room\RoomDoors[0]\Open)
												e\room\RoomDoors[1] = dr
											EndIf
											If (Not e\room\Objects[3]) Then
												e\room\Objects[3] = TempInt2
												PositionEntity(e\room\Objects[1], e\room\x + (iX * 2.0), EntityY(e\room\Objects[1], True), e\room\z + (iY * 2.0), True)
											Else
												FreeEntity(TempInt2)
											EndIf
										Else
											If e\room\RoomDoors[3] <> Null Then
												RemoveDoor(dr)
											Else
												dr\Open = (Not e\room\RoomDoors[2]\Open)
												e\room\RoomDoors[3] = dr
											EndIf
											If (Not e\room\Objects[5]) Then
												e\room\Objects[5] = TempInt2
												PositionEntity(e\room\Objects[0], e\room\x + (iX * 2.0), EntityY(e\room\Objects[0], True), e\room\z + (iY * 2.0), True)
											Else
												FreeEntity(TempInt2)
											EndIf
										EndIf
									EndIf
									
									e\room\mt\Entities[iX + (iY * MTGridSize)] = TempInt
									
									wayp.WayPoints = CreateWaypoint(e\room\x + (iX * 2.0), e\room\y + MTGridY + 0.2, e\room\z + (iY * 2.0), Null, e\room)
									
									e\room\mt\waypoints[iX + (iY * MTGridSize)] = wayp
									
									If iY < MTGridSize - 1 Then
										If e\room\mt\waypoints[iX + ((iY + 1) * MTGridSize)] <> Null Then
											Dist = EntityDistance(e\room\mt\waypoints[iX + (iY * MTGridSize)]\OBJ, e\room\mt\waypoints[iX + ((iY + 1) * MTGridSize)]\OBJ)
											For i = 0 To 3
												If e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + ((iY + 1) * MTGridSize)] Then
													Exit
												ElseIf e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = Null Then
													e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + ((iY + 1) * MTGridSize)]
													e\room\mt\waypoints[iX + (iY * MTGridSize)]\Dist[i] = Dist
													Exit
												EndIf
											Next
											For i = 0 To 3
												If e\room\mt\waypoints[iX + ((iY + 1) * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + (iY * MTGridSize)] Then
													Exit
												ElseIf e\room\mt\waypoints[iX + ((iY + 1) * MTGridSize)]\connected[i] = Null Then
													e\room\mt\waypoints[iX + ((iY + 1) * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + (iY * MTGridSize)]
													e\room\mt\waypoints[iX + ((iY + 1) * MTGridSize)]\Dist[i] = Dist
													Exit
												EndIf
											Next
										EndIf
									EndIf
									If iY > 0 Then
										If e\room\mt\waypoints[iX + ((iY - 1) * MTGridSize)] <> Null Then
											Dist = EntityDistance(e\room\mt\waypoints[iX + (iY * MTGridSize)]\OBJ, e\room\mt\waypoints[iX + ((iY - 1) * MTGridSize)]\OBJ)
											For i = 0 To 3
												If e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + ((iY - 1) * MTGridSize)] Then
													Exit
												ElseIf e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = Null Then
													e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + ((iY - 1) * MTGridSize)]
													e\room\mt\waypoints[iX + (iY * MTGridSize)]\Dist[i] = Dist
													Exit
												EndIf
											Next
											For i = 0 To 3
												If e\room\mt\waypoints[iX + ((iY - 1) * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + (iY * MTGridSize)] Then
													Exit
												ElseIf e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = Null Then
													e\room\mt\waypoints[iX + ((iY - 1) * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + (iY * MTGridSize)]
													e\room\mt\waypoints[iX + ((iY - 1) * MTGridSize)]\Dist[i] = Dist
													Exit
												EndIf
											Next
										EndIf
									EndIf
									If iX > 0 Then
										If e\room\mt\waypoints[iX - 1 + (iY * MTGridSize)] <> Null Then
											Dist = EntityDistance(e\room\mt\waypoints[iX + (iY * MTGridSize)]\OBJ, e\room\mt\waypoints[iX - 1 + (iY * MTGridSize)]\OBJ)
											For i = 0 To 3
												If e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX - 1 + (iY * MTGridSize)] Then
													Exit
												ElseIf e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = Null Then
													e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX - 1 + (iY * MTGridSize)]
													e\room\mt\waypoints[iX + (iY * MTGridSize)]\Dist[i] = Dist
													Exit
												EndIf
											Next
											For i = 0 To 3
												If e\room\mt\waypoints[iX - 1 + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + (iY * MTGridSize)] Then
													Exit
												ElseIf e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = Null Then
													e\room\mt\waypoints[iX - 1 + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + (iY * MTGridSize)]
													e\room\mt\waypoints[iX - 1 + (iY * MTGridSize)]\Dist[i] = Dist
													Exit
												EndIf
											Next
										EndIf
									EndIf
									If iX < MTGridSize - 1 Then
										If e\room\mt\waypoints[iX + 1 + (iY * MTGridSize)] <> Null Then
											Dist = EntityDistance(e\room\mt\waypoints[iX + (iY * MTGridSize)]\OBJ, e\room\mt\waypoints[iX + 1 + (iY * MTGridSize)]\OBJ)
											For i = 0 To 3
												If e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + 1 + (iY * MTGridSize)] Then
													Exit
												ElseIf e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = Null Then
													e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + 1 + (iY * MTGridSize)]
													e\room\mt\waypoints[iX + (iY * MTGridSize)]\Dist[i] = Dist
													Exit
												EndIf
											Next
											For i = 0 To 3
												If e\room\mt\waypoints[iX + 1 + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + (iY * MTGridSize)] Then
													Exit
												ElseIf e\room\mt\waypoints[iX + (iY * MTGridSize)]\connected[i] = Null Then
													e\room\mt\waypoints[iX + 1 + (iY * MTGridSize)]\connected[i] = e\room\mt\waypoints[iX + (iY * MTGridSize)]
													e\room\mt\waypoints[iX + 1 + (iY * MTGridSize)]\Dist[i] = Dist
													Exit
												EndIf
											Next
										EndIf
									EndIf
								EndIf
							Next
						Next
						
						For i = MT_ROOM1 To MT_GENERATOR
							e\room\mt\Meshes[i] = Meshes[i]
						Next
						
						SeedRnd(OldSeed)
						
						For it.Items = Each Items
							If (EntityY(it\Collider, True) >= 8.0) And (EntityY(it\Collider, True) <= 12.0) And (EntityX(it\Collider, True) >= e\room\x - 6.0) And (EntityX(it\Collider, True) <= (e\room\x + (2.0 * MTGridSize) + 6.0)) And (EntityZ(it\Collider, True) >= e\room\z - 6.0) And (EntityZ(it\Collider, True) <= (e\room\z + (2.0 * MTGridSize) + 6.0)) Then
								TranslateEntity(it\Collider, 0.0, 0.3, 0.0, True)
								ResetEntity(it\Collider)
							EndIf
						Next
					EndIf
					
					If EntityY(me\Collider, True) > 4.0 Then
						ShouldPlay = 29
						
						If e\EventState = 0.0 Then
							If EntityDistanceSquared(me\Collider, e\room\Objects[0]) < EntityDistanceSquared(me\Collider, e\room\Objects[1]) Then
								Temp = 0
							Else
								Temp = 1
							EndIf
							e\EventState = 2.0
							
							If (Not n_I\Curr106\Contained) Then
								de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(e\room\Objects[Temp], True), EntityY(e\room\Objects[Temp], True) + 0.02, EntityZ(e\room\Objects[Temp], True), 90.0, Rnd(360.0), 0.0, 0.05, 0.8)
								de\SizeChange = 0.001
								EntityParent(de\OBJ, e\room\OBJ)
								
								PositionEntity(n_I\Curr106\Collider, EntityX(e\room\Objects[Temp], True), EntityY(me\Collider, True) - 3.0, EntityZ(e\room\Objects[Temp], True))
								ResetEntity(n_I\Curr106\Collider)
								SetNPCFrame(n_I\Curr106, 110.0)
								n_I\Curr106\State = -0.1
								n_I\Curr106\PrevY = EntityY(me\Collider)
							EndIf
							
							For i = 0 To 1
								Local spawnPoint.WayPoints = Null
								
								For x = i * ((MTGridSize ^ 2) / 5.0) To ((MTGridSize ^ 2) - 1)
									If Rand(2) = 1 And e\room\mt\waypoints[x] <> Null Then 
										spawnPoint = e\room\mt\waypoints[x]
										x = MTGridSize ^ 2
									EndIf
								Next 
								If spawnPoint <> Null Then e\room\NPC[i] = CreateNPC(NPCType966, EntityX(spawnPoint\OBJ, True), EntityY(spawnPoint\OBJ, True), EntityZ(spawnPoint\OBJ, True))
							Next
						EndIf
					Else
						For iY = 0 To MTGridSize - 1
							For iX = 0 To MTGridSize - 1
								If e\room\mt\Entities[iX + (iY * MTGridSize)] <> 0 Then
									If (Not EntityHidden(e\room\mt\Entities[iX + (iY * MTGridSize)])) Then HideEntity(e\room\mt\Entities[iX + (iY * MTGridSize)])
								EndIf
							Next
						Next
					EndIf
					
					UpdateMT(e\room\mt)
					
					e\EventState2 = UpdateElevators(e\EventState2, e\room\RoomDoors[0], e\room\RoomDoors[1], e\room\Objects[2], e\room\Objects[3], e, False)
					e\EventState3 = UpdateElevators(e\EventState3, e\room\RoomDoors[2], e\room\RoomDoors[3], e\room\Objects[4], e\room\Objects[5], e, False)
				Else
					If e\room\mt <> Null Then
						If e\room\mt\Meshes[MT_ROOM1] <> 0 Then
							For iY = 0 To MTGridSize - 1
								For iX = 0 To MTGridSize - 1
									If e\room\mt\Entities[iX + (iY * MTGridSize)] <> 0 Then
										If (Not EntityHidden(e\room\mt\Entities[iX + (iY * MTGridSize)])) Then HideEntity(e\room\mt\Entities[iX + (iY * MTGridSize)])
									EndIf
								Next
							Next
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_room2_2_hcz_106
				;[Block]
				If (Not n_I\Curr106\Contained) Then
					If e\EventState = 0.0 Then
						If PlayerRoom = e\room Then e\EventState = 1.0
					Else
						e\EventState = (e\EventState + fps\Factor[0] * 0.7)
						If e\EventState < 50.0 Then
							n_I\Curr106\Idle = 1
							PositionEntity(n_I\Curr106\Collider, EntityX(e\room\Objects[0], True), EntityY(me\Collider) - 0.15, EntityZ(e\room\Objects[0], True))
							PointEntity(n_I\Curr106\Collider, e\room\Objects[1])
							MoveEntity(n_I\Curr106\Collider, 0.0, 0.0, EntityDistance(e\room\Objects[0], e\room\Objects[1]) * 0.5 * (e\EventState / 50.0))
							AnimateNPC(n_I\Curr106, 284.0, 333.0, 0.02 * 35.0)
						ElseIf e\EventState < 200.0
							n_I\Curr106\Idle = 1
							AnimateNPC(n_I\Curr106, 334.0, 494.0, 0.2)
							
							PositionEntity(n_I\Curr106\Collider, (EntityX(e\room\Objects[0], True) + EntityX(e\room\Objects[1], True)) / 2.0, EntityY(me\Collider) - 0.15, (EntityZ(e\room\Objects[0], True) + EntityZ(e\room\Objects[1], True)) / 2.0)
							RotateEntity(n_I\Curr106\Collider, 0.0, CurveValue(e\EventState, EntityYaw(n_I\Curr106\Collider), 30.0), 0.0, True)
							If EntityDistanceSquared(n_I\Curr106\Collider, me\Collider) < 16.0 Then
								Pvt = CreatePivot()
								PositionEntity(Pvt, EntityX(n_I\Curr106\Collider), EntityY(n_I\Curr106\Collider), EntityZ(n_I\Curr106\Collider))
								PointEntity(Pvt, me\Collider)
								If WrapAngle(EntityYaw(Pvt) - EntityYaw(n_I\Curr106\Collider)) < 80.0 Then
									n_I\Curr106\State = -11.0 : n_I\Curr106\Idle = 0
									PlaySound_Strict(HorrorSFX[10])
									e\EventState = 260.0
								EndIf
								FreeEntity(Pvt)
							EndIf
						ElseIf e\EventState < 250.0
							n_I\Curr106\Idle = 1
							PositionEntity(n_I\Curr106\Collider, EntityX(e\room\Objects[0], True), EntityY(me\Collider) - 0.15, EntityZ(e\room\Objects[0], True))
							PointEntity(n_I\Curr106\Collider, e\room\Objects[1])
							MoveEntity(n_I\Curr106\Collider, 0.0, 0.0, EntityDistance(e\room\Objects[0], e\room\Objects[1]) * ((e\EventState - 150.0) / 100.0))
							AnimateNPC(n_I\Curr106, 284.0, 333.0, 0.7)
						EndIf
						ResetEntity(n_I\Curr106\Collider)
						
						PositionEntity(n_I\Curr106\OBJ, EntityX(n_I\Curr106\Collider), EntityY(n_I\Curr106\Collider) - 0.15, EntityZ(n_I\Curr106\Collider))
						RotateEntity(n_I\Curr106\OBJ, 0.0, EntityYaw(n_I\Curr106\Collider), 0.0)
						
						If (e\EventState / 250.0) > 0.3 And ((e\EventState - fps\Factor[0] * 0.7) / 250.0) <= 0.3 Then
							e\SoundCHN = PlaySound_Strict(HorrorSFX[6])
							me\BlurTimer = 800.0
							de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(e\room\Objects[2], True), EntityY(e\room\Objects[2], True), EntityZ(e\room\Objects[2], True), 0.0, e\room\Angle - 90.0, Rnd(360.0), 0.1, 0.01)
							de\SizeChange = 0.003 : de\AlphaChange = 0.005 : de\Timer = 90000.0
							EntityParent(de\OBJ, e\room\OBJ)
						EndIf
						
						If (e\EventState / 250.0) > 0.65 And ((e\EventState - fps\Factor[0] * 0.7) / 250.0) <= 0.65 Then
							de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(e\room\Objects[3], True), EntityY(e\room\Objects[3], True), EntityZ(e\room\Objects[3], True), 0.0, e\room\Angle + 90.0, Rnd(360.0), 0.1, 0.01)
							de\SizeChange = 0.003 : de\AlphaChange = 0.005 : de\Timer = 90000.0
							EntityParent(de\OBJ, e\room\OBJ)
						EndIf
						If e\EventState > 250.0 Then n_I\Curr106\Idle = 0 : RemoveEvent(e)
					EndIf
				EndIf
				;[End Block]
			Case e_room2_4_hcz_106
				;[Block]
				If (Not n_I\Curr106\Contained) And n_I\Curr106\State > 0.0 Then
					If e\EventState = 0.0 Then
						If PlayerRoom = e\room Then e\EventState = 1.0
					Else
						e\EventState = e\EventState + 1.0
						PositionEntity(n_I\Curr106\Collider, EntityX(e\room\Objects[1], True), EntityY(e\room\Objects[1], True), EntityZ(e\room\Objects[1], True))
						ResetEntity(n_I\Curr106\Collider)
						
						PointEntity(n_I\Curr106\Collider, Camera)
						TurnEntity(n_I\Curr106\Collider, 0.0, Sin(MilliSecs2() / 20) * 6.0, 0.0, True)
						MoveEntity(n_I\Curr106\Collider, 0.0, 0.0, Sin(MilliSecs2() / 15) * 0.06)
						PositionEntity(n_I\Curr106\OBJ, EntityX(n_I\Curr106\Collider), EntityY(n_I\Curr106\Collider) - 0.15, EntityZ(n_I\Curr106\Collider))
						
						RotateEntity(n_I\Curr106\OBJ, 0.0, EntityYaw(n_I\Curr106\Collider), 0.0)
						n_I\Curr106\Idle = 1
						AnimateNPC(n_I\Curr106, 334.0, 494.0, 0.3)
						If e\EventState > 800.0 Then
							If me\BlinkTimer < -5.0 Then
								n_I\Curr106\Idle = 0
								RemoveEvent(e)
							EndIf
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_room2_4_hcz
				;[Block]
				If n_I\Curr173\Idle = 0 Then
					If e\room\Dist < 8.0 And e\room\Dist > 0.0 Then
						If (Not EntityVisible(n_I\Curr173\Collider, Camera)) And (Not EntityVisible(e\room\Objects[0], Camera)) Then
							PositionEntity(n_I\Curr173\Collider, EntityX(e\room\Objects[0], True), 0.5, EntityZ(e\room\Objects[0], True))
							ResetEntity(n_I\Curr173\Collider)
							RemoveEvent(e)
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_room3_hcz_duck
				;[Block]
				If PlayerRoom = e\room Then
					If (Not e\room\Objects[2]) Then
						e\room\Objects[2] =	CopyEntity(n_I\NPCModelID[NPC_DUCK_MODEL])
						ScaleEntity(e\room\Objects[2], 0.07, 0.07, 0.07)
						Tex = LoadTexture_Strict("GFX\NPCs\duck(3).png")
						If opt\Atmosphere Then TextureBlend(Tex, 5)
						EntityTexture(e\room\Objects[2], Tex)
						DeleteSingleTextureEntryFromCache(Tex)
						PositionEntity(e\room\Objects[2], EntityX(e\room\Objects[0], True), EntityY(e\room\Objects[0], True), EntityZ(e\room\Objects[0], True))
						PointEntity(e\room\Objects[2], e\room\OBJ)
						RotateEntity(e\room\Objects[2], 0.0, EntityYaw(e\room\Objects[2], True), 0.0, True)
						
						LoadEventSound(e, "SFX\SCP\Joke\Saxophone.ogg")
					Else
						If (Not EntityInView(e\room\Objects[2], Camera)) Then
							e\EventState = e\EventState + fps\Factor[0]
							If Rand(200) = 1 And e\EventState > 300.0 Then
								e\EventState = 0.0
								e\SoundCHN = PlaySound2(e\Sound, Camera, e\room\Objects[2], 6.0)
							EndIf
						Else
							If ChannelPlaying(e\SoundCHN) Then StopChannel(e\SoundCHN) : e\SoundCHN = 0
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_room3_hcz_1048
				;[Block]
				If PlayerRoom = e\room Then
					If (Not e\room\Objects[2]) Then
						e\room\Objects[2] =	CopyEntity(n_I\NPCModelID[NPC_1048_MODEL])
						ScaleEntity(e\room\Objects[2], 0.05, 0.05, 0.05)
						SetAnimTime(e\room\Objects[2], 488.0)
						
						Local DrawingName$ = "drawing_1048(" + Rand(25) + ").png"
						Local ImgPath$ = ItemHUDTexturePath + DrawingName
						Local itt.ItemTemplates
						
						For itt.ItemTemplates = Each ItemTemplates
							If itt\Name = "Drawing" Then
								If itt\Img <> 0 Then FreeImage(itt\Img) : itt\Img = 0
								itt\Img = LoadImage_Strict(ImgPath)
								itt\Img = ScaleImage2(itt\Img, MenuScale, MenuScale)
								itt\ImgWidth = ImageWidth(itt\Img) / 2
								itt\ImgHeight = ImageHeight(itt\Img) / 2
								itt\ImgPath = ImgPath
								itt\TexPath = ItemTexturePath + DrawingName
								Exit
							EndIf
						Next
						
						Tex = LoadTexture_Strict(ImgPath)
						
						Local Brush% = LoadBrush_Strict(ImgPath)
						
						For i = 1 To CountSurfaces(e\room\Objects[2])
							SF = GetSurface(e\room\Objects[2], i)
							b = GetSurfaceBrush(SF)
							BT = GetBrushTexture(b, 0)
							TexName = StripPath(TextureName(BT))
							
							If Lower(TexName) <> "scp_1048.png" Then PaintSurface(SF, Brush)
							FreeBrush(b)
						Next
						DeleteSingleTextureEntryFromCache(Tex)
						FreeBrush(Brush)
						
						PositionEntity(e\room\Objects[2], EntityX(e\room\Objects[0], True), EntityY(e\room\Objects[0], True), EntityZ(e\room\Objects[0], True))
					Else
						PointEntity(e\room\Objects[2], me\Collider)
						RotateEntity(e\room\Objects[2], -90.0, EntityYaw(e\room\Objects[2], True), 0.0, True)
						
						If e\EventState = 0.0 Then
							If EntityDistanceSquared(me\Collider, e\room\Objects[2]) < 9.0 Then
								If EntityInView(e\room\Objects[2], Camera) Then
									e\EventState = 1.0
									GiveAchievement(Achv1048)
								EndIf
							EndIf
						ElseIf e\EventState = 1.0
							Animate2(e\room\Objects[2], AnimTime(e\room\Objects[2]), 488.0, 634.0, 0.5, False)
							If AnimTime(e\room\Objects[2]) = 634.0 Then e\EventState = 2.0
						ElseIf e\EventState = 2.0
							Animate2(e\room\Objects[2], AnimTime(e\room\Objects[2]), 339.0, 487.0, 1.0)
							If InteractObject(e\room\Objects[2], 2.25) Then
								If ItemAmount >= MaxItemAmount Then
									CreateMsg(GetLocalString("msg", "cantcarry"))
								Else
									SelectedItem = CreateItem("Drawing", "paper", 0.0, 0.0, 0.0)
									EntityType(SelectedItem\Collider, HIT_ITEM)
									EntityParent(SelectedItem\Collider, 0)
									
									PickItem(SelectedItem)
									
									FreeEntity(e\room\Objects[2]) : e\room\Objects[2] = 0
									
									e\EventState = 3.0
									RemoveEvent(e)
								EndIf
							EndIf
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_room2_scientists_2
				;[Block]
				If PlayerRoom = e\room Then
					If e\EventState = 0.0 Then
						If e\room\RoomDoors[0]\Open Then
							If e\room\RoomDoors[0]\OpenState = 180.0 Then
								PlaySound_Strict(HorrorSFX[5])
								e\EventState = 1.0
							EndIf
						Else
							If EntityDistanceSquared(me\Collider, e\room\RoomDoors[0]\OBJ) < 2.25 And RemoteDoorOn Then e\room\RoomDoors[0]\Open = True
						EndIf
					Else
						If EntityDistanceSquared(e\room\Objects[0], me\Collider) < 4.0 Then
							me\HeartBeatVolume = CurveValue(0.5, me\HeartBeatVolume, 5.0)
							me\HeartBeatRate = CurveValue(120.0, me\HeartBeatRate, 150.0) 
							e\SoundCHN = LoopSound2(OldManSFX[4], e\SoundCHN, Camera, e\room\OBJ, 5.0, 0.3)
							If n_I\Curr106\State > 1.0 Then n_I\Curr106\State = n_I\Curr106\State - (fps\Factor[0] * (2.0 + SelectedDifficulty\AggressiveNPCs))
							If wi\HazmatSuit = 0 Then
								InjurePlayer(fps\Factor[0] / 15000.0)
							ElseIf wi\HazmatSuit <> 4
								InjurePlayer(fps\Factor[0] / 20000.0)
							Else
								InjurePlayer(fps\Factor[0] / 25000.0)
							EndIf
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_room2_servers_hcz
				;[Block]
				If e\EventState = 0.0 Then
					If PlayerRoom = e\room Then
						; ~ Close the doors when the player enters the room
						For i = 0 To 1
							OpenCloseDoor(e\room\RoomDoors[i])
						Next
						
						If n_I\Curr096 <> Null Then
							PositionEntity(n_I\Curr096\Collider, EntityX(e\room\Objects[0], True), EntityY(e\room\Objects[0], True) + 0.1, EntityZ(e\room\Objects[0], True), True)
							ResetEntity(n_I\Curr096\Collider)
						Else
							n_I\Curr096 = CreateNPC(NPCType096, EntityX(e\room\Objects[0], True), EntityY(e\room\Objects[0], True) + 0.1, EntityZ(e\room\Objects[0], True))
						EndIf
						n_I\Curr096\State = 6.0 : n_I\Curr096\State2 = 70.0 * 10.0
						RotateEntity(n_I\Curr096\Collider, 0.0, e\room\Angle + 270.0, 0.0, True)
						
						LoadEventSound(e, "SFX\Character\Guard\096ServerRoom1.ogg")
						e\SoundCHN = PlaySound2(e\Sound, Camera, n_I\Curr096\OBJ)
						
						e\room\NPC[0] = CreateNPC(NPCTypeGuard, EntityX(e\room\Objects[1], True), EntityY(e\room\Objects[1], True), EntityZ(e\room\Objects[1], True))
						
						GiveAchievement(Achv096)
						
						e\EventState = 1.0
					EndIf
				ElseIf e\EventState < 70.0 * 45.0
					If Rand(200) < 5 And PlayerRoom = e\room Then
						me\LightBlink = Rnd(1.0, 2.0)
						If Rand(5) = 1 Then PlaySound2(IntroSFX[Rand(8, 10)], Camera, e\room\OBJ, 8.0, Rnd(0.1, 0.3))
					EndIf
					
					e\EventState = Min(e\EventState + fps\Factor[0], 70.0 * 43.0)
					
					If e\room\NPC[0] <> Null Then
						n_I\Curr096\Target = e\room\NPC[0]
						
						If e\EventState < 70.0 * 8.0 Then
							AnimateNPC(n_I\Curr096, 472.0, 520.0, 0.25)
							PointEntity(e\room\NPC[0]\Collider, n_I\Curr096\Collider)
						ElseIf e\EventState >= 70.0 * 8.0 And e\EventState < 70.0 * 10.0
							; ~ Checking at which side the player is
							If EntityDistanceSquared(me\Collider, e\room\RoomDoors[0]\FrameOBJ) < EntityDistanceSquared(me\Collider, e\room\RoomDoors[1]\FrameOBJ)
								AnimateNPC(n_I\Curr096, 521.0, 555.0, 0.25, False)
								If n_I\Curr096\Frame >= 554.5 Then
									e\EventState = 70.0 * 10.0
									n_I\Curr096\Frame = 677.0
									SetNPCFrame(n_I\Curr096, n_I\Curr096\Frame)
									n_I\Curr096\State = 1.0
									TurnEntity(n_I\Curr096\Collider, 0.0, 180.0, 0.0)
									MoveEntity(n_I\Curr096\Collider, 0.0, 0.0, 0.3)
								EndIf
							Else
								AnimateNPC(n_I\Curr096, 556.0, 590.0, 0.25, False)
								If n_I\Curr096\Frame >= 589.5 Then
									e\EventState = 70.0 * 10.0
									n_I\Curr096\Frame = 677.0
									SetNPCFrame(n_I\Curr096, n_I\Curr096\Frame)
									n_I\Curr096\State = 1.0
									TurnEntity(n_I\Curr096\Collider, 0.0, 180.0, 0.0)
									MoveEntity(n_I\Curr096\Collider, 0.0, 0.0, 0.3)
								EndIf
							EndIf
							PointEntity(e\room\NPC[0]\Collider, n_I\Curr096\Collider)
						ElseIf e\EventState >= 70.0 * 10.0 And e\EventState < 70.0 * 20.0
							n_I\Curr096\State = Min(Max(1.0, n_I\Curr096\State), 3.0)
							n_I\Curr096\State2 = Max(n_I\Curr096\State2, 70.0 * 12.0)
							If e\EventState - fps\Factor[0] <= 70.0 * 15.0 Then ; ~ Walk to the doorway
								If e\EventState > 70.0 * 15.0 Then
									e\room\NPC[0]\State = 14.0
									e\room\NPC[0]\PathStatus = FindPath(e\room\NPC[0], EntityX(n_I\Curr096\Collider, True), 0.4, EntityZ(n_I\Curr096\Collider, True))
									e\room\NPC[0]\PathTimer = 300.0
								Else
									PointEntity(e\room\NPC[0]\Collider, n_I\Curr096\Collider)
								EndIf
							EndIf
							If EntityVisible(e\room\NPC[0]\Collider, n_I\Curr096\Collider) Then
								e\room\NPC[0]\State = 13.0
								PointEntity(e\room\NPC[0]\OBJ, n_I\Curr096\Collider)
								RotateEntity(e\room\NPC[0]\Collider, 0.0, CurveAngle(EntityYaw(e\room\NPC[0]\OBJ), EntityYaw(e\room\NPC[0]\Collider), 30.0), 0.0)
							EndIf
						Else
							If n_I\Curr096\State = 4.0 Then ; ~ Shoot at SCP-096 when it starts attacking
								n_I\Curr096\LastSeen = 1.0
								e\room\NPC[0]\State = 2.0
								PointEntity(e\room\NPC[0]\OBJ, n_I\Curr096\Collider)
								RotateEntity(e\room\NPC[0]\Collider, 0.0, CurveAngle(EntityYaw(e\room\NPC[0]\OBJ), EntityYaw(e\room\NPC[0]\Collider), 30.0), 0.0)
								If PlayerRoom = e\room Then me\LightBlink = (e\room\NPC[0]\Reload) + Rnd(0.5, 2.0)
								n_I\Curr096\Target = e\room\NPC[0]
							Else
								If e\EventState > 70.0 * 22.0 Then n_I\Curr096\State = 4.0
								If e\room\NPC[0]\State = 13.0 Then
									e\room\NPC[0]\State = 14.0
									e\room\NPC[0]\PathStatus = FindPath(e\room\NPC[0], EntityX(e\room\OBJ, True), 0.4, EntityZ(e\room\OBJ, True))
									e\room\NPC[0]\PathTimer = 300.0
									e\room\NPC[0]\Speed = e\room\NPC[0]\Speed * 1.8 ; ~ Making the guard walking a bit faster
								EndIf
							EndIf
						EndIf
						
						If AnimTime(n_I\Curr096\OBJ) > 25.0 And AnimTime(n_I\Curr096\OBJ) < 150.0 Then
							LoadEventSound(e, "SFX\Character\Guard\096ServerRoom2.ogg")
							e\SoundCHN = PlaySound2(e\Sound, Camera, n_I\Curr096\OBJ)
							
							ChangeNPCTextureID(n_I\Curr096, NPC_096_BLOODY_TEXTURE)
							
							n_I\Curr096\CurrSpeed = 0.0
							
							For i = 0 To 6
								If e\room\Angle = 0.0 Lor e\room\Angle = 180.0 Then
									de.Decals = CreateDecal(Rand(DECAL_BLOOD_1, DECAL_BLOOD_2), e\room\x - Rnd(197.0, 199.0) * Cos(e\room\Angle) * RoomScale, e\room\y + 1.0, e\room\z + (140.0 * (i - 3)) * RoomScale, 0.0, e\room\Angle + 90.0, Rnd(360.0), Rnd(0.8, 0.85))
									de\SizeChange = 0.001
									EntityParent(de\OBJ, e\room\OBJ)
									de.Decals = CreateDecal(Rand(DECAL_BLOOD_1, DECAL_BLOOD_2), e\room\x - Rnd(197.0, 199.0) * Cos(e\room\Angle) * RoomScale, e\room\y + 1.0, e\room\z + (140.0 * (i - 3)) * RoomScale, 0.0, e\room\Angle - 90.0, Rnd(360.0), Rnd(0.8, 0.85))
									de\SizeChange = 0.001
									EntityParent(de\OBJ, e\room\OBJ)
								Else
									de.Decals = CreateDecal(Rand(DECAL_BLOOD_1, DECAL_BLOOD_2), e\room\x + (140.0 * (i - 3)) * RoomScale, e\room\y + 1.0, e\room\z - Rnd(197.0, 199.0) * Sin(e\room\Angle) * RoomScale - Rnd(0.001, 0.003), 0.0, e\room\Angle + 90.0, Rnd(360.0), Rnd(0.8, 0.85))
									de\SizeChange = 0.001
									EntityParent(de\OBJ, e\room\OBJ)
									de.Decals = CreateDecal(Rand(DECAL_BLOOD_1, DECAL_BLOOD_2), e\room\x + (140.0 * (i - 3)) * RoomScale, e\room\y + 1.0, e\room\z - Rnd(197.0, 199.0) * Sin(e\room\Angle) * RoomScale - Rnd(0.001, 0.003), 0.0, e\room\Angle - 90.0, Rnd(360.0), Rnd(0.8, 0.85))
									de\SizeChange = 0.001
									EntityParent(de\OBJ, e\room\OBJ)
								EndIf
								de.Decals = CreateDecal(Rand(DECAL_BLOOD_1, DECAL_BLOOD_2), EntityX(e\room\NPC[0]\Collider) + Rnd(-2.0, 2.0), e\room\y + 0.005, EntityZ(e\room\NPC[0]\Collider) + Rnd(-2.0, 2.0), 90.0, Rnd(360.0), 0.0)
								EntityParent(de\OBJ, e\room\OBJ)
							Next
							
							StopStream_Strict(n_I\Curr096\SoundCHN) : n_I\Curr096\SoundCHN = 0 : n_I\Curr096\SoundCHN_IsStream = False
							
							ShowEntity(e\room\Objects[2])
							
							RemoveNPC(e\room\NPC[0]) : e\room\NPC[0] = Null
							
							n_I\Curr096\State = 5.0
						EndIf
					Else
						If e\EventState >= 70.0 * 40.0 And e\EventState - fps\Factor[0] < 70.0 * 40.0 Then ; ~ Open them again to let the player in
							For i = 0 To 1
								OpenCloseDoor(e\room\RoomDoors[i])
							Next
							If e\Sound <> 0 Then FreeSound_Strict(e\Sound) : e\Sound = 0
						EndIf
						
						If PlayerRoom = e\room Then
							If ChannelPlaying(e\SoundCHN) Then
								me\LightBlink = Rnd(0.5, 6.0)
								If Rand(50) = 1 Then PlaySound2(IntroSFX[Rand(8, 10)], Camera, e\room\OBJ, 8.0, Rnd(0.1, 0.3))
							EndIf
							
							If e\room\Angle = 0.0 Lor e\room\Angle = 180.0 Then ; ~ Lock the player inside
								If Abs(EntityX(me\Collider) - EntityX(e\room\OBJ, True)) > 1.3 Then 
									If e\Sound <> 0 Then FreeSound_Strict(e\Sound) : e\Sound = 0
									e\EventState = 70.0 * 50.0
								EndIf
							Else
								If Abs(EntityZ(me\Collider) - EntityZ(e\room\OBJ, True)) > 1.3 Then 
									If e\Sound <> 0 Then FreeSound_Strict(e\Sound) : e\Sound = 0
									e\EventState = 70.0 * 50.0
								EndIf
							EndIf
						EndIf
					EndIf
					UpdateSoundOrigin(e\SoundCHN, Camera, n_I\Curr096\OBJ)
				ElseIf PlayerRoom = e\room
					Temp = UpdateLever(e\room\RoomLevers[0]\OBJ) ; ~ Power switch
					x = UpdateLever(e\room\RoomLevers[1]\OBJ) ; ~ Fuel pump
					z = UpdateLever(e\room\RoomLevers[2]\OBJ) ; ~ Generator
					
					If EntityHidden(e\room\Objects[2]) Then ShowEntity(e\room\Objects[2])
					
					; ~ Fuel pump on
					If x Then
						e\EventState2 = Min(1.0, e\EventState2 + fps\Factor[0] / 350.0)
						
						; ~ Generator on
						If z Then
							If (Not e\Sound2) Then LoadEventSound(e, "SFX\General\GeneratorOn.ogg", 1)
							e\EventState3 = Min(1.0, e\EventState3 + fps\Factor[0] / 450.0)
						Else
							e\EventState3 = Min(0.0, e\EventState3 - fps\Factor[0] / 450.0)
						EndIf
					Else
						e\EventState2 = Max(0.0, e\EventState2 - fps\Factor[0] / 350.0)
						e\EventState3 = Max(0.0, e\EventState3 - fps\Factor[0] / 450.0)
					EndIf
					
					If e\EventState2 > 0.0 Then e\SoundCHN = LoopSound2(RoomAmbience[7], e\SoundCHN, Camera, e\room\RoomLevers[1]\BaseOBJ, 5.0, e\EventState2 * 0.8)
					If e\EventState3 > 0.0 Then e\SoundCHN2 = LoopSound2(e\Sound2, e\SoundCHN2, Camera, e\room\RoomLevers[1]\BaseOBJ, 6.0, e\EventState3)
					
					If (Not Temp) And x And z Then
						For i = 0 To 1
							e\room\RoomDoors[i]\Locked = 0
						Next
					Else
						If Rand(200) < 5 Then me\LightBlink = Rnd(0.5, 1.0)
						
						For i = 0 To 1
							If e\room\RoomDoors[i]\Open Then
								OpenCloseDoor(e\room\RoomDoors[i])
								e\room\RoomDoors[i]\Locked = 1
							EndIf
						Next
					EndIf
				EndIf
				;[End Block]
			Case e_room2_storage
				;[Block]
				If PlayerRoom = e\room Then
					If e\EventState2 <= 0.0 Then
						e\room\RoomDoors[1]\Locked = 0
						e\room\RoomDoors[4]\Locked = 0
						
						If EntityDistanceSquared(me\Collider, n_I\Curr173\OBJ) < 64.0 Lor EntityDistanceSquared(me\Collider, n_I\Curr106\OBJ) < 64.0 Then
							e\room\RoomDoors[1]\Locked = 1
							e\room\RoomDoors[4]\Locked = 1
						Else
							For n.NPCs = Each NPCs
								If n\NPCType = NPCTypeMTF Then
									If EntityDistanceSquared(me\Collider, n_I\Curr173\OBJ) < 64.0 Then
										e\room\RoomDoors[1]\Locked = 1
										e\room\RoomDoors[4]\Locked = 1
										Exit
									EndIf
								EndIf
							Next
						EndIf
						e\EventState2 = 70.0 * 5.0
					Else
						e\EventState2 = e\EventState2 - fps\Factor[0]
					EndIf
					
					TFormPoint(EntityX(me\Collider), EntityY(me\Collider), EntityZ(me\Collider), 0, e\room\OBJ)
					
					Temp = 0
					
					Local src.Doors, dest.Doors
					
					If TFormedX() > 730.0 Then
						GiveAchievement(Achv970)
						
						me\LightBlink = 2.0
						UpdateWorld()
						TFormPoint(EntityX(me\Collider), EntityY(me\Collider), EntityZ(me\Collider), 0, e\room\OBJ)
						
						For i = 1 To 2
							src.Doors = e\room\RoomDoors[i + 2]
							dest.Doors = e\room\RoomDoors[i]
							
							dest\Open = src\Open
							dest\OpenState = src\OpenState
							EntityParent(dest\OBJ, dest\FrameOBJ) : EntityParent(src\OBJ, src\FrameOBJ)
							EntityParent(dest\OBJ2, dest\FrameOBJ) : EntityParent(src\OBJ2, src\FrameOBJ)
							
							PositionEntity(dest\OBJ, EntityX(src\OBJ), EntityY(src\OBJ), EntityZ(src\OBJ))
							PositionEntity(dest\OBJ2, EntityX(src\OBJ2), EntityY(src\OBJ2), EntityZ(src\OBJ2))
							
							EntityParent(dest\OBJ, 0) : EntityParent(src\OBJ, 0)
							EntityParent(dest\OBJ2, 0) : EntityParent(src\OBJ2, 0)
							
							src\Open = False
							src\OpenState = 0.0
						Next
						
						TFormPoint(TFormedX() - 1024.0, TFormedY(), TFormedZ(), e\room\OBJ, 0)
						PositionEntity(me\Collider, TFormedX(), EntityY(me\Collider), TFormedZ(), True)
						ResetEntity(me\Collider)
						
						Temp = True
					ElseIf TFormedX() < -730.0
						GiveAchievement(Achv970)
						
						me\LightBlink = 2.0
						UpdateWorld()
						TFormPoint(EntityX(me\Collider), EntityY(me\Collider), EntityZ(me\Collider), 0, e\room\OBJ)
						
						For i = 1 To 2
							src.Doors = e\room\RoomDoors[i]
							dest.Doors = e\room\RoomDoors[i + 2]
							
							dest\Open = src\Open
							dest\OpenState = src\OpenState
							EntityParent(dest\OBJ, dest\FrameOBJ) : EntityParent(src\OBJ, src\FrameOBJ)
							EntityParent(dest\OBJ2, dest\FrameOBJ) : EntityParent(src\OBJ2, src\FrameOBJ)
							
							PositionEntity(dest\OBJ, EntityX(src\OBJ), EntityY(src\OBJ), EntityZ(src\OBJ))
							PositionEntity(dest\OBJ2, EntityX(src\OBJ2), EntityY(src\OBJ2), EntityZ(src\OBJ2))
							
							EntityParent(dest\OBJ, 0) : EntityParent(src\OBJ, 0)
							EntityParent(dest\OBJ2, 0) : EntityParent(src\OBJ2, 0)
							
							src\Open = False
							src\OpenState = 0.0
						Next
						
						TFormPoint(TFormedX() + 1024.0, TFormedY(), TFormedZ(), e\room\OBJ, 0)
						PositionEntity(me\Collider, TFormedX(), EntityY(me\Collider), TFormedZ(), True)
						ResetEntity(me\Collider)
						
						Temp = True
					EndIf
					
					If Temp Then
						e\EventState = e\EventState + 1.0
						For it.Items = Each Items
							If EntityDistanceSquared(it\Collider, me\Collider) < 25.0 Then
								TFormPoint(EntityX(it\Collider), EntityY(it\Collider), EntityZ(it\Collider), 0, e\room\OBJ)
								x = TFormedX() : y = TFormedY() : z = TFormedZ()
								If z >= -290.0 And z <= 560.0 Then
									If x >= 264.0 And x <= 760.0 Then
										TFormPoint(x - 1024.0, y, z, e\room\OBJ, 0)
										PositionEntity(it\Collider, TFormedX(), TFormedY(), TFormedZ())
										ResetEntity(it\Collider)
									ElseIf x <= -264.0 And x >= -760
										TFormPoint(x + 1024.0, y, z, e\room\OBJ, 0)
										PositionEntity(it\Collider, TFormedX(), TFormedY(), TFormedZ())
										ResetEntity(it\Collider)
									EndIf
								EndIf
							EndIf
						Next
						
						Select e\EventState
							Case 2.0
								;[Block]
								i = Rand(0, MaxItemAmount - 1)
								If Inventory(i) <> Null Then
									RemoveWearableItems(Inventory(i))
									RemoveItem(Inventory(i))
								EndIf
								;[End Block]
							Case 5.0
								;[Block]
								InjurePlayer(0.3)
								;[End Block]
							Case 10.0
								;[Block]
								de.Decals = CreateDecal(DECAL_BLOOD_2, EntityX(e\room\OBJ) + Cos(e\room\Angle - 90.0) * 760.0 * RoomScale, e\room\y + 0.005, EntityZ(e\room\OBJ) + Sin(e\room\Angle - 90.0) * 760.0 * RoomScale, 90.0, Rnd(360.0), 0.0)
								EntityParent(de\OBJ, e\room\OBJ)
								;[End Block]
							Case 14.0
								;[Block]
								For i = 0 To MaxItemAmount - 1
									If Inventory(i) <> Null Then
										If Inventory(i)\ItemTemplate\TempName = "paper" Then
											RemoveItem(Inventory(i))
											For itt.ItemTemplates = Each ItemTemplates
												If itt\TempName = "paper" And Rand(6) = 1 Then
													Inventory(i) = CreateItem(itt\Name, itt\TempName, 1.0, 1.0, 1.0)
													HideEntity(Inventory(i)\Collider)
													Inventory(i)\Picked = True
													Exit
												EndIf
											Next
											Exit
										EndIf
									EndIf
								Next
								;[End Block]
							Case 18.0
								;[Block]
								TFormPoint(-344.0, 176.0, 272.0, e\room\OBJ, 0)
								it.Items = CreateItem("Strange Note", "paper", TFormedX(), TFormedY(), TFormedZ())
								EntityType(it\Collider, HIT_ITEM)
								;[End Block]
							Case 25.0
								;[Block]
								e\room\NPC[0] = CreateNPC(NPCTypeD, EntityX(e\room\OBJ) + Cos(e\room\Angle - 90.0) * 760.0 * RoomScale, 0.35, EntityZ(e\room\OBJ) + Sin(e\room\Angle - 90.0) * 760.0 * RoomScale)
								e\room\NPC[0]\State3 = -1.0 : e\room\NPC[0]\IsDead = True
								RotateEntity(e\room\NPC[0]\Collider, 0.0, e\room\Angle - 200.0, 0.0, True)
								ChangeNPCTextureID(e\room\NPC[0], NPC_CLASS_D_BENJAMIN_TEXTURE)
								SetNPCFrame(e\room\NPC[0], 80.0)
								;[End Block]
							Case 30.0
								;[Block]
								i = Rand(0, MaxItemAmount - 1)
								If Inventory(i) <> Null Then
									RemoveWearableItems(Inventory(i))
									RemoveItem(Inventory(i))
								EndIf
								Inventory(i) = CreateItem("Strange Note", "paper", 1.0, 1.0, 1.0)
								HideEntity(Inventory(i)\Collider)
								Inventory(i)\Picked = True
								Inventory(i)\ItemTemplate\Found = True
								EntityType(Inventory(i)\Collider, HIT_ITEM)
								ItemAmount = ItemAmount + 1
								;[End Block]
							Case 35.0
								;[Block]
								For i = 0 To 3
									de.Decals = CreateDecal(DECAL_BLOOD_6, e\room\x + Rnd(-2.0, 2.0), e\room\y + 700.0 * RoomScale, e\room\z + Rnd(-2.0, 2.0), 270.0, Rnd(360.0), 0.0, 0.05)
									de\SizeChange = 0.0005
									EntityParent(de\OBJ, e\room\OBJ)
								Next
								;[End Block]
							Case 40.0
								;[Block]
								PlaySound_Strict(LoadTempSound("SFX\Radio\Franklin4.ogg"))
								;[End Block]
							Case 50.0
								;[Block]
								e\room\NPC[1] = CreateNPC(NPCTypeGuard, EntityX(e\room\OBJ) + Cos(e\room\Angle + 90.0) * 600.0 * RoomScale, 0.35, EntityZ(e\room\OBJ) + Sin(e\room\Angle + 90.0) * 600.0 * RoomScale)
								e\room\NPC[1]\State = 7.0
								;[End Block]
							Case 52.0
								;[Block]
								If e\room\NPC[1] <> Null Then RemoveNPC(e\room\NPC[1]) : e\room\NPC[1] = Null
								;[End block]
							Case 60.0
								;[Block]
								If n_I\Curr173 <> Null Then
									If (Not n_I\IsHalloween) Then
										Tex = LoadTexture_Strict("GFX\NPCs\scp_173_H.png")
										If opt\Atmosphere Then TextureBlend(Tex, 5)
										EntityTexture(n_I\Curr173\OBJ, Tex)
										EntityTexture(n_I\Curr173\OBJ2, Tex)
										DeleteSingleTextureEntryFromCache(Tex)
									EndIf
								EndIf
								;[End Block]
						End Select
						
						If Rand(10) = 1 Then
							Temp = Rand(0, 2)
							PlaySound_Strict(AmbientSFX(Temp, Rand(0, AmbientSFXAmount[Temp] - 1)))
						EndIf
					Else
						If e\room\NPC[0] <> Null Then
							If EntityDistanceSquared(me\Collider, e\room\NPC[0]\Collider) < 9.0 Then
								If EntityInView(e\room\NPC[0]\OBJ, Camera) Then
									me\CurrCameraZoom = (Sin(Float(MilliSecs2()) / 20.0) + 1.0) * 15.0
									me\HeartBeatVolume = Max(CurveValue(0.3, me\HeartBeatVolume, 2.0), me\HeartBeatVolume)
									me\HeartBeatRate = Max(me\HeartBeatRate, 120.0)
								EndIf
							EndIf
						EndIf
						
						If e\room\NPC[1] <> Null Then
							PointEntity(e\room\NPC[1]\OBJ, me\Collider)
							RotateEntity(e\room\NPC[1]\Collider, 0.0, CurveAngle(EntityYaw(e\room\NPC[1]\OBJ), EntityYaw(e\room\NPC[1]\Collider), 35.0), 0.0)
						EndIf
						
						For it.Items = Each Items
							If it\Dropped = 1 Lor it\Dropped = -1 Then
								TFormPoint(EntityX(it\Collider), EntityY(it\Collider), EntityZ(it\Collider), 0, e\room\OBJ)
								x = TFormedX() : y = TFormedY() : z = TFormedZ()
								
								If it\Dropped = 1 And Abs(x) < 264.0 Then
									For i = -1 To 1 Step 2
										TFormPoint(x + (1024.0 * i), y, z, e\room\OBJ, 0)
										it2.Items = CreateItem(it\Name, it\ItemTemplate\TempName, TFormedX(), EntityY(it\Collider), TFormedZ(), it\R, it\G, it\B, it\A)
										it2\State = it\State : it2\State2 = it\State2 : it2\State3 = it\State3
										If it\InvSlots > 0 Then
											it2\InvSlots = it\InvSlots
											For j = 0 To it\InvSlots - 1
												it2\SecondInv[j] = it\SecondInv[j]
											Next
											SetAnimTime(it2\Model, AnimTime(it\Model))
											it2\InvImg = it\InvImg
										EndIf
										RotateEntity(it2\Collider, EntityPitch(it\Collider), EntityYaw(it\Collider), 0.0)
										EntityType(it2\Collider, HIT_ITEM)
									Next
								ElseIf it\Dropped = -1
									For it2.Items = Each Items
										If it2 <> it And it2\Dist < 225.0 And it2\ItemTemplate = it\ItemTemplate Then
											TFormPoint(EntityX(it2\Collider), EntityY(it2\Collider), EntityZ(it2\Collider), 0, e\room\OBJ)
											If TFormedZ() = z Then RemoveItem(it2)
										EndIf
									Next
								EndIf
								Exit
							EndIf
						Next
					EndIf
				EndIf
				
				If e\EventState >= 26.0 Then
					If Abs(EntityX(me\Collider) - e\room\x) < 8.0 Then
						If Abs(EntityZ(me\Collider) - e\room\z) < 8.0 Then
							If (Not e\Sound) Then e\Sound = LoadSound_Strict("SFX\SCP\970\Corpse.ogg")
							e\SoundCHN = LoopSound2(e\Sound, e\SoundCHN, Camera, e\room\NPC[0]\OBJ)
							If e\EventState > 60.0 Then
								AnimateNPC(e\room\NPC[0], 80.0, 61.0, -0.02, False)
								e\room\NPC[0]\DropSpeed = 0.0
								y = CurveValue(1.5 + Sin(Float(MilliSecs2()) / 20.0) * 0.1, EntityY(e\room\NPC[0]\Collider), 50.0)
								PositionEntity(e\room\NPC[0]\Collider, EntityX(e\room\NPC[0]\Collider), y, EntityZ(e\room\NPC[0]\Collider))
								TurnEntity(e\room\NPC[0]\Collider, 0.0, fps\Factor[0] * 0.1, 0.0)
							EndIf
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_door_closing
				;[Block]
				If PlayerRoom = e\room Then
					If EntityDistanceSquared(e\room\OBJ, me\Collider) < 6.25 Then
						For do.Doors = Each Doors
							If Abs(EntityX(do\OBJ, True) - EntityX(me\Collider)) < 2.0 Then
								If Abs(EntityZ(do\OBJ, True) - EntityZ(me\Collider)) < 2.0 Then
									If (Not EntityInView(do\OBJ, Camera)) Then
										If do\Open Then
											do\Open = False
											do\OpenState = 0.0
											me\BlurTimer = 100.0
											me\BigCameraShake = 3.0
										EndIf
									EndIf
									Exit
								EndIf
							EndIf
						Next
						RemoveEvent(e)
					EndIf
				EndIf
				;[End Block]
			Case e_room3_2_ez
				;[Block]
				If PlayerRoom = e\room Then
					If e\EventState3 = 0.0 And n_I\Curr173\Idle = 0 Then
						If me\BlinkTimer < -10.0 Then
							Temp = Rand(0, 2)
							PositionEntity(n_I\Curr173\Collider, EntityX(e\room\Objects[Temp], True), EntityY(e\room\Objects[Temp], True), EntityZ(e\room\Objects[Temp], True), True)
							ResetEntity(n_I\Curr173\Collider)
							e\EventState3 = 1.0
						EndIf
					EndIf
					
					If e\room\Objects[3] <> 0 Then
						If me\BlinkTimer < -8.0 And me\BlinkTimer > -12.0 Then
							PointEntity(e\room\Objects[3], Camera)
							RotateEntity(e\room\Objects[3], 0.0, EntityYaw(e\room\Objects[3], True), 0.0, True)
						EndIf
						If e\EventState2 = 0.0 Then
							e\EventState = CurveValue(0.0, e\EventState, 15.0)
							If Rand(800) = 1 Then e\EventState2 = 1.0
						Else
							e\EventState = e\EventState + (fps\Factor[0] * 0.5)
							If e\EventState > 360.0 Then e\EventState = 0.0
							
							If Rand(1200) = 1 Then e\EventState2 = 0.0
						EndIf
						PositionEntity(e\room\Objects[3], EntityX(e\room\Objects[3], True), (-608.0 * RoomScale) + 0.05 + Sin(e\EventState + 270.0) * 0.05, EntityZ(e\room\Objects[3], True), True)
					EndIf
				EndIf
				;[End Block]
			Case e_room3_storage
				;[Block]
				If PlayerRoom = e\room Then
					e\EventState2 = UpdateElevators(e\EventState2, e\room\RoomDoors[0], e\room\RoomDoors[1], e\room\Objects[0], e\room\Objects[1], e)
					e\EventState3 = UpdateElevators(e\EventState3, e\room\RoomDoors[2], e\room\RoomDoors[3], e\room\Objects[2], e\room\Objects[3], e)
					
					If EntityY(me\Collider) < (-4600.0) * RoomScale Then
						GiveAchievement(Achv939)
						
						If wi\GasMask = 0 And wi\HazmatSuit = 0 Then
							me\BlurTimer = Min(me\BlurTimer + (fps\Factor[0] * 1.05), 1500.0)
							If me\BlurTimer >= 500.0 Then UpdateCough(1000)
							If me\BlurTimer >= 1500.0 And me\FallTimer = 0.0 Then
								Kill(False)
								msg\DeathMsg = GetLocalString("death", "939.gas")
							EndIf
						EndIf
						
						ShouldPlay = 7
						
						If e\room\NPC[0] = Null Then
							For i = 0 To 3
								e\room\NPC[i] = CreateNPC(NPCType939, 0.0, 0.0, 0.0)
							Next
							
							e\room\NPC[4] = CreateNPC(NPCTypeD, EntityX(e\room\Objects[17], True), EntityY(e\room\Objects[17], True), EntityZ(e\room\Objects[17], True))
							e\room\NPC[4]\State3 = -1.0 : e\room\NPC[4]\IsDead = True
							ChangeNPCTextureID(e\room\NPC[4], NPC_CLASS_D_VICTIM_939_1_TEXTURE)
							SetNPCFrame(e\room\NPC[4], 40.0)
							TurnEntity(e\room\NPC[4]\Collider, 0.0, e\room\Angle + 90.0, 0.0)
							
							e\room\NPC[5] = CreateNPC(NPCTypeD, EntityX(e\room\Objects[18], True), EntityY(e\room\Objects[18], True), EntityZ(e\room\Objects[18], True))
							e\room\NPC[5]\State3 = -1.0 : e\room\NPC[5]\IsDead = True
							ChangeNPCTextureID(e\room\NPC[5], NPC_CLASS_D_VICTIM_939_2_TEXTURE)
							SetNPCFrame(e\room\NPC[5], 19.0)
							TurnEntity(e\room\NPC[5]\Collider, 0.0, e\room\Angle + 90.0, 0.0)
						Else
							If e\EventState = 0.0 Then
								; ~ Instance # 1
								PositionEntity(e\room\NPC[0]\Collider, EntityX(e\room\Objects[4], True), EntityY(e\room\Objects[4], True) + 0.2, EntityZ(e\room\Objects[4], True))
								ResetEntity(e\room\NPC[0]\Collider)
								e\room\NPC[0]\State = 2.0
								e\room\NPC[0]\State2 = 5.0
								e\room\NPC[0]\PrevState = 7
								; ~ Instance # 2
								PositionEntity(e\room\NPC[1]\Collider, EntityX(e\room\Objects[9], True), EntityY(e\room\Objects[9], True) + 0.2, EntityZ(e\room\Objects[9], True))
								ResetEntity(e\room\NPC[1]\Collider)
								e\room\NPC[1]\State = 2.0
								e\room\NPC[1]\State2 = 10.0
								e\room\NPC[1]\PrevState = 12
								; ~ Instance # 3
								PositionEntity(e\room\NPC[2]\Collider, EntityX(e\room\Objects[13], True), EntityY(e\room\Objects[13], True) + 0.2, EntityZ(e\room\Objects[13], True))
								ResetEntity(e\room\NPC[2]\Collider)
								e\room\NPC[2]\State = 2.0
								e\room\NPC[2]\State2 = 14.0
								e\room\NPC[2]\PrevState = 16
								; ~ Instance # 4
								PositionEntity(e\room\NPC[3]\Collider, EntityX(e\room\Objects[6], True), EntityY(e\room\Objects[6], True) + 0.2, EntityZ(e\room\Objects[6], True)) 
								ResetEntity(e\room\NPC[3]\Collider)
								e\room\NPC[3]\State = 2.0
								e\room\NPC[3]\State2 = 7.0
								e\room\NPC[3]\PrevState = 7.0
								
								e\EventState = 1.0
							EndIf
							
							If (Not e\room\RoomDoors[4]\Open) Then
								For i = 0 To 1
									If UpdateLever(e\room\RoomLevers[i]\OBJ) Then
										e\room\RoomDoors[4]\Open = True
										LoadEventSound(e, "SFX\Door\Door2Open1_dist.ogg", 1)
										e\SoundCHN2 = PlaySound2(e\Sound2, Camera, e\room\RoomDoors[4]\OBJ, 400.0)
										Exit
									EndIf
								Next
							EndIf
							
							UpdateLever(e\room\RoomLevers[0]\OBJ, e\room\RoomDoors[4]\Open)
							UpdateLever(e\room\RoomLevers[1]\OBJ, e\room\RoomDoors[4]\Open)
							
							e\room\NPC[0]\IgnorePlayer = False
							e\room\NPC[2]\IgnorePlayer = False
							e\room\NPC[3]\IgnorePlayer = False
							
							CurrTrigger = CheckTriggers()
							
							Select CurrTrigger
								Case "939-1_fix"
									;[Block]
									e\room\NPC[0]\IgnorePlayer = True
									e\room\NPC[3]\IgnorePlayer = True
									;[End Block]
								Case "939-3_fix"
									;[Block]
									e\room\NPC[2]\IgnorePlayer = True
									;[End Block]
							End Select
							
							UpdateSoundOrigin(e\SoundCHN2, Camera, e\room\RoomDoors[4]\OBJ, 400.0)
							
							PlayerFallingPickDistance = 0.0
							
							If EntityY(me\Collider) < -6400.0 * RoomScale And (Not me\Terminated) And me\FallTimer >= 0.0 Then
								PlaySound_Strict(LoadTempSound("SFX\Room\PocketDimension\Impact.ogg"))
								me\Terminated = True
							EndIf
						EndIf
					Else
						e\EventState = 0.0
						If e\room\NPC[0] <> Null Then
							For i = 0 To 3
								e\room\NPC[i]\State = 66.0
							Next
						EndIf
					EndIf
				Else
					If e\room\NPC[0] <> Null Then
						For i = 0 To 3
							e\room\NPC[i]\State = 66.0
						Next
					EndIf
				EndIf
				;[End Block]
			Case e_room3_2_hcz
				;[Block]
				If e\EventState = 0.0 Then
					e\room\NPC[0] = CreateNPC(NPCTypeGuard, EntityX(e\room\Objects[0], True), EntityY(e\room\Objects[0], True) + 0.5, EntityZ(e\room\Objects[0], True))
					e\room\NPC[0]\State = 8.0 : e\room\NPC[0]\IsDead = True
					SetNPCFrame(e\room\NPC[0], 288.0)
					PointEntity(e\room\NPC[0]\Collider, e\room\OBJ)
					RotateEntity(e\room\NPC[0]\Collider, 0.0, EntityYaw(e\room\NPC[0]\Collider) + Rnd(-20.0, 20.0), 0.0, True)
					
					e\EventState = 1.0
					RemoveEvent(e)
				EndIf
				;[End Block]
			Case e_room4_lcz
				;[Block]
				If e\EventState < MilliSecs2() Then
					If PlayerRoom <> e\room Then
						If DistanceSquared(EntityX(me\Collider), EntityX(e\room\OBJ), EntityZ(me\Collider), EntityZ(e\room\OBJ)) < 256.0 Then
							If n_I\Curr049 <> Null Then
								If n_I\Curr049\State = 2.0 And EntityDistanceSquared(me\Collider, n_I\Curr049\Collider) > 256.0 Then
									n_I\Curr049\PathStatus = 0 : n_I\Curr049\State = 4.0 : n_I\Curr049\State2 = 0.0 : n_I\Curr049\State3 = 0.0
									TFormPoint(368.0, 528.0, 176.0, e\room\OBJ, 0)
									PositionEntity(n_I\Curr049\Collider, TFormedX(), TFormedY(), TFormedZ())
									ResetEntity(n_I\Curr049\Collider)
									RemoveEvent(e)
								EndIf
							Else
								RemoveEvent(e)
							EndIf
						EndIf
					EndIf
					If e <> Null Then e\EventState = MilliSecs2() + 5000
				EndIf
				;[End Block]
			Case e_cont2_012
				;[Block]
				If PlayerRoom = e\room Then
					If EntityY(me\Collider) < 0.0 Then
						If e\EventState = 0.0 Then
							If EntityDistanceSquared(me\Collider, e\room\RoomDoors[0]\OBJ) < 6.25 And RemoteDoorOn Then
								GiveAchievement(Achv012)
								
								PlaySound_Strict(HorrorSFX[7])
								PlaySound2(LeverSFX, Camera, e\room\RoomDoors[0]\OBJ)
								
								OpenCloseDoor(e\room\RoomDoors[0])
								
								e\EventState = 1.0
							EndIf
						Else
							If (Not e\Sound) Then LoadEventSound(e, "SFX\Music\012Golgotha.ogg")
							e\SoundCHN = LoopSound2(e\Sound, e\SoundCHN, Camera, e\room\Objects[1], 5.0)
							
							If (Not e\Sound2) Then LoadEventSound(e, "SFX\Music\012.ogg", 1)
							
							If e\EventState < 90.0 Then e\EventState = CurveValue(90.0, e\EventState, 500)
							PositionEntity(e\room\Objects[0], EntityX(e\room\Objects[0], True), (-130.0 - (448.0 * Sin(e\EventState))) * RoomScale, EntityZ(e\room\Objects[0], True), True)
							
							If e\EventState2 = 0.0 Then
								RotateEntity(e\room\RoomLevers[0]\OBJ, CurveValue(-80.0, EntityPitch(e\room\RoomLevers[0]\OBJ), 10.0), EntityYaw(e\room\RoomLevers[0]\OBJ), 0.0)
								If EntityPitch(e\room\RoomLevers[0]\OBJ) = -80.0 Then e\EventState2 = 1.0
							EndIf
							
							UpdateRedLight(e\room\Objects[1], 100, 50)
							
							If I_714\Using <> 3 And wi\GasMask <> 4 And wi\HazmatSuit <> 4 Then
								If EntityVisible(e\room\Objects[0], Camera) Then
									e\SoundCHN2 = LoopSound2(e\Sound2, e\SoundCHN2, Camera, e\room\Objects[1], 10.0, e\EventState3 / (86.0 * 70.0))
									
									Pvt = CreatePivot()
									PositionEntity(Pvt, EntityX(Camera), EntityY(e\room\Objects[0], True) - 0.05, EntityZ(Camera))
									PointEntity(Pvt, e\room\Objects[0])
									RotateEntity(me\Collider, EntityPitch(me\Collider), CurveAngle(EntityYaw(Pvt), EntityYaw(me\Collider), 80.0 - (e\EventState3 / 200.0)), 0.0)
									
									TurnEntity(Pvt, 90.0, 0.0, 0.0)
									CameraPitch = CurveAngle(EntityPitch(Pvt) + 25.0, CameraPitch + 90.0, 80.0 - (e\EventState3 / 200.0))
									CameraPitch = CameraPitch - 90.0
									
									Dist = DistanceSquared(EntityX(me\Collider), EntityX(e\room\Objects[0], True), EntityZ(me\Collider), EntityZ(e\room\Objects[0], True))
									
									me\HeartBeatRate = 150.0
									me\HeartBeatVolume = Max(3.0 - Sqr(Dist), 0.0) / 3.0
									me\BlurVolume = Max((2.0 - Sqr(Dist)) * (e\EventState3 / 800.0) * (Sin(Float(MilliSecs2()) / 20.0 + 1.0)), me\BlurVolume)
									me\CurrCameraZoom = Max(me\CurrCameraZoom, (Sin(Float(MilliSecs2()) / 20.0) + 1.0) * 8.0 * Max((3.0 - Sqr(Dist)), 0.0))
									
									StopBreathSound()
									
									If Dist < 0.36 Then
										e\EventState3 = Min(e\EventState3 + fps\Factor[0], 70.0 * 86.0)
										If e\EventState3 > 70.0 * 1.0 And e\EventState3 - fps\Factor[0] <= 70.0 * 1.0 Then
											PlaySound_Strict(LoadTempSound("SFX\SCP\012\Speech1.ogg"))
										ElseIf e\EventState3 > 70.0 * 13.0 And e\EventState3 - fps\Factor[0] <= 70.0 * 13.0
											CreateMsg(GetLocalString("msg", "012_1"))
											InjurePlayer(0.5)
											PlaySound_Strict(LoadTempSound("SFX\SCP\012\Speech2.ogg"))
										ElseIf e\EventState3 > 70.0 * 31.0 And e\EventState3 - fps\Factor[0] <= 70.0 * 31.0
											Tex = LoadTexture_Strict("GFX\Map\Textures\scp_012(2).png")
											If opt\Atmosphere Then TextureBlend(Tex, 5)
											EntityTexture(e\room\Objects[2], Tex)
											DeleteSingleTextureEntryFromCache(Tex)
											
											CreateMsg(GetLocalString("msg", "012_2"))
											me\Injuries = Max(me\Injuries, 1.5)
											PlaySound_Strict(LoadTempSound("SFX\SCP\012\Speech" + Rand(3, 4) + ".ogg"))
										ElseIf e\EventState3 > 70.0 * 49.0 And e\EventState3 - fps\Factor[0] <= 70.0 * 49.0
											CreateMsg(GetLocalString("msg", "012_3"))
											InjurePlayer(0.3)
											PlaySound_Strict(LoadTempSound("SFX\SCP\012\Speech5.ogg"))
										ElseIf e\EventState3 > 70.0 * 63.0 And e\EventState3 - fps\Factor[0] <= 70.0 * 63.0
											Tex = LoadTexture_Strict("GFX\Map\Textures\scp_012(3).png")
											If opt\Atmosphere Then TextureBlend(Tex, 5)
											EntityTexture(e\room\Objects[2], Tex)
											DeleteSingleTextureEntryFromCache(Tex)
											
											InjurePlayer(0.5)
											PlaySound_Strict(LoadTempSound("SFX\SCP\012\Speech6.ogg"))
										ElseIf e\EventState3 > 70.0 * 74.0 And e\EventState3 - fps\Factor[0] <= 70.0 * 74.0
											Tex = LoadTexture_Strict("GFX\Map\Textures\scp_012(4).png")
											If opt\Atmosphere Then TextureBlend(Tex, 5)
											EntityTexture(e\room\Objects[2], Tex)
											DeleteSingleTextureEntryFromCache(Tex)
											
											CreateMsg(GetLocalString("msg", "012_4"))
											InjurePlayer(0.8)
											PlaySound_Strict(LoadTempSound("SFX\SCP\012\Speech7.ogg"))
											If (Not me\Crouch) Then SetCrouch(True)
											
											de.Decals = CreateDecal(DECAL_BLOOD_6, PickedX(), PickedY() + 0.005, PickedZ(), 90.0, Rnd(360.0), 0.0, 0.1)
											de\MaxSize = 0.45 : de\SizeChange = 0.0002
											EntityParent(de\OBJ, e\room\OBJ)
										ElseIf e\EventState3 > 70.0 * 85.0 And e\EventState3 - fps\Factor[0] <= 70.0 * 85.0
											msg\DeathMsg = Format(GetLocalString("death", "012"), SubjectName)
											Kill(True)
										EndIf
										
										RotateEntity(me\Collider, EntityPitch(me\Collider), CurveAngle(EntityYaw(me\Collider) + Sin(e\EventState3 * (e\EventState3 / 2000.0)) * (e\EventState3 / 300.0), EntityYaw(me\Collider), 80.0), 0.0)
									Else
										Angle = WrapAngle(EntityYaw(Pvt) - EntityYaw(me\Collider))
										If Angle < 40.0 Then
											me\ForceMove = (40.0 - Angle) * 0.02
										ElseIf Angle > 310.0
											me\ForceMove = (40.0 - Abs(360.0 - Angle)) * 0.02
										EndIf
									EndIf
									
									FreeEntity(Pvt)
								Else
									If DistanceSquared(EntityX(me\Collider), EntityX(e\room\RoomDoors[0]\FrameOBJ), EntityZ(me\Collider), EntityZ(e\room\RoomDoors[0]\FrameOBJ)) < 20.25 And EntityY(me\Collider) < -2.5 Then
										Pvt = CreatePivot()
										PositionEntity(Pvt, EntityX(Camera), EntityY(me\Collider), EntityZ(Camera))
										PointEntity(Pvt, e\room\RoomDoors[0]\FrameOBJ)
										CameraPitch = CurveAngle(90.0, CameraPitch + 90.0, 100.0)
										CameraPitch = CameraPitch - 90.0
										RotateEntity(me\Collider, EntityPitch(me\Collider), CurveAngle(EntityYaw(Pvt), EntityYaw(me\Collider), 150.0), 0.0)
										
										Angle = WrapAngle(EntityYaw(Pvt) - EntityYaw(me\Collider))
										If Angle < 40.0 Then
											me\ForceMove = (40.0 - Angle) * 0.008
										ElseIf Angle > 310.0
											me\ForceMove = (40.0 - Abs(360.0 - Angle)) * 0.008
										EndIf
										FreeEntity(Pvt)
									EndIf
								EndIf
							EndIf
						EndIf
					Else
						If ChannelPlaying(e\SoundCHN) Then StopChannel(e\SoundCHN) : e\SoundCHN = 0
						If e\Sound <> 0 Then FreeSound_Strict(e\Sound) : e\Sound = 0
						If ChannelPlaying(e\SoundCHN2) Then StopChannel(e\SoundCHN2) : e\SoundCHN2 = 0
						If e\Sound2 <> 0 Then FreeSound_Strict(e\Sound2) : e\Sound2 = 0
					EndIf
				EndIf
				;[End Block]
			Case e_cont1_035
				;[Block]
				If PlayerRoom = e\room Then
					; ~ EventState2: has SCP-035 told the code to the storage room (True / False)
					
					; ~ EventState3: has the player opened the gas valves (0 = no, 0 < x < 70.0 * 35.0 yes, x > 70.0 * 35.0 the host has died)
					
					If e\EventState = 0.0 Then
						If EntityDistanceSquared(me\Collider, e\room\Objects[0]) < 4.0 Then
							e\room\NPC[0] = CreateNPC(NPCTypeD, EntityX(e\room\Objects[1], True), 0.5, EntityZ(e\room\Objects[1], True))
							e\room\NPC[0]\State = 6.0
							CreateNPCAsset(e\room\NPC[0])
							ChangeNPCTextureID(e\room\NPC[0], NPC_CLASS_D_VICTIM_035_TEXTURE)
							SetNPCFrame(e\room\NPC[0], 501.0)
							RotateEntity(e\room\NPC[0]\Collider, 0.0, e\room\Angle + 270.0, 0.0, True)
							
							e\EventState = 1.0
						EndIf
					ElseIf e\EventState > 0.0
						ShouldPlay = 27
						
						If ChannelPlaying(e\room\NPC[0]\SoundCHN) Then e\room\NPC[0]\SoundCHN = LoopSound2(e\room\NPC[0]\Sound, e\room\NPC[0]\SoundCHN, Camera, e\room\OBJ, 6.0)
						
						If e\EventState = 1.0 Then
							If EntityDistanceSquared(me\Collider, e\room\Objects[0]) < 1.44
								If EntityInView(e\room\NPC[0]\OBJ, Camera) Then
									GiveAchievement(Achv035)
									PlaySound_Strict(LoadTempSound("SFX\SCP\035\GetUp.ogg"))
									e\EventState = 1.5
								EndIf
							EndIf
						Else
							If e\room\RoomDoors[3]\Open Then e\EventState2 = Max(e\EventState2, 1.0)
							; ~ The door is closed
							If (Not UpdateLever(e\room\RoomLevers[0]\OBJ, (e\EventState2 = 20.0))) Then
								; ~ The gas valves are open
								Temp = UpdateLever(e\room\RoomLevers[1]\OBJ, False)
								If Temp Lor (e\EventState3 > 70.0 * 25.0 And e\EventState3 < 70.0 * 50.0) Then
									If Temp Then
										PositionEntity(e\room\Objects[2], EntityX(e\room\Objects[2], True), 400.0 * RoomScale, EntityZ(e\room\Objects[2], True), True)
										PositionEntity(e\room\Objects[3], EntityX(e\room\Objects[3], True), 400.0 * RoomScale, EntityZ(e\room\Objects[3], True), True)
									Else
										PositionEntity(e\room\Objects[2], EntityX(e\room\Objects[2], True), 20.0, EntityZ(e\room\Objects[2], True), True)
										PositionEntity(e\room\Objects[3], EntityX(e\room\Objects[3], True), 20.0, EntityZ(e\room\Objects[3], True), True)
									EndIf
									
									If e\EventState3 > (-70.0) * 30.0 Then
										e\EventState3 = Abs(e\EventState3) + fps\Factor[0]
										If e\EventState3 > 1.0 And e\EventState3 - fps\Factor[0] <= 1.0 Then
											e\room\NPC[0]\State = 0.0
											LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\Gased1.ogg")
											e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
										ElseIf e\EventState3 > 70.0 * 15.0 And e\EventState3 < 70.0 * 25.0
											If e\EventState3 - fps\Factor[0] <= 70.0 * 15.0 Then
												LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\Gased2.ogg")
												e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
												SetNPCFrame(e\room\NPC[0], 553.0)
											EndIf
											e\room\NPC[0]\State = 6.0
											
											AnimateNPC(e\room\NPC[0], 553.0, 529.0, -0.12, False)
										ElseIf e\EventState3 > 70.0 * 25.0 And e\EventState3 < 70.0 * 35.0
											e\room\NPC[0]\State = 6.0
											AnimateNPC(e\room\NPC[0], 529.0, 524.0, -0.08, False)
										ElseIf e\EventState3 > 70.0 * 35.0
											If e\room\NPC[0]\State = 6.0 Then
												me\Sanity = -150.0 * Sin(AnimTime(e\room\NPC[0]\OBJ) - 524.0) * 9.0
												AnimateNPC(e\room\NPC[0], 524.0, 553.0, 0.08, False)
												If e\room\NPC[0]\Frame = 553.0 Then e\room\NPC[0]\State = 0.0
											EndIf
											
											If e\EventState3 - fps\Factor[0] <= 70.0 * 35.0 Then 
												LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\GasedKilled1.ogg")
												e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
												
												PlaySound_Strict(LoadTempSound("SFX\SCP\035\KilledGetUp.ogg"))
												
												I_035\Sad = True
												
												Update035Label(e\room\Objects[6])
												CreateNPCAsset(e\room\NPC[0])
												
												e\EventState = 70.0 * 60.0
											EndIf
										EndIf
									EndIf
								Else ; ~ Gas valves closed
									If e\room\NPC[0]\State = 6.0 Then
										If e\room\NPC[0]\Frame >= 501.0 And e\room\NPC[0]\Frame <= 523.0 Then
											e\room\NPC[0]\Frame = Animate2(e\room\NPC[0]\OBJ, AnimTime(e\room\NPC[0]\OBJ), 501.0, 523.0, 0.08, False)
											If e\room\NPC[0]\Frame = 523.0 Then e\room\NPC[0]\State = 0.0
										EndIf
										
										If e\room\NPC[0]\Frame >= 524.0 And e\room\NPC[0]\Frame <= 553.0 Then
											e\room\NPC[0]\Frame = Animate2(e\room\NPC[0]\OBJ, AnimTime(e\room\NPC[0]\OBJ), 524.0, 553.0, 0.08, False)
											If e\room\NPC[0]\Frame = 553.0 Then e\room\NPC[0]\State = 0.0
										EndIf
									EndIf
									
									PositionEntity(e\room\Objects[2], EntityX(e\room\Objects[2], True), 20.0, EntityZ(e\room\Objects[2], True), True)
									PositionEntity(e\room\Objects[3], EntityX(e\room\Objects[3], True), 20.0, EntityZ(e\room\Objects[3], True), True)
									
									If e\room\NPC[0]\State = 0.0 Then
										PointEntity(e\room\NPC[0]\OBJ, me\Collider)
										RotateEntity(e\room\NPC[0]\Collider, 0.0, CurveAngle(EntityYaw(e\room\NPC[0]\OBJ), EntityYaw(e\room\NPC[0]\Collider), 15.0), 0.0)
										
										If Rand(500) = 1 Then
											If EntityDistanceSquared(e\room\NPC[0]\Collider, e\room\Objects[1]) > 4.0 Then
												e\room\NPC[0]\State2 = 1.0
											Else
												e\room\NPC[0]\State2 = 0.0
											EndIf
											e\room\NPC[0]\State = 1.0
										EndIf
									ElseIf e\room\NPC[0]\State = 1.0
										If e\room\NPC[0]\State2 = 1.0 Then
											PointEntity(e\room\NPC[0]\OBJ, e\room\Objects[1])
											If EntityDistanceSquared(e\room\NPC[0]\Collider, e\room\Objects[1]) < 0.04 Then e\room\NPC[0]\State = 0.0
										Else
											RotateEntity(e\room\NPC[0]\OBJ, 0.0, e\room\Angle - 180.0, 0.0, True)
											If EntityDistanceSquared(e\room\NPC[0]\Collider, e\room\Objects[1]) > 4.0 Then e\room\NPC[0]\State = 0.0
										EndIf
										RotateEntity(e\room\NPC[0]\Collider, 0.0, CurveAngle(EntityYaw(e\room\NPC[0]\OBJ), EntityYaw(e\room\NPC[0]\Collider), 15.0), 0.0)
									EndIf
									
									If e\EventState3 > 0.0 Then
										e\EventState3 = -e\EventState3
										If e\EventState3 < (-70.0) * 35.0 Then ; ~ The host is dead
											LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\GasedKilled2.ogg")
											e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
											e\EventState = 70.0 * 60.0
										Else
											If e\EventState3 < (-70.0) * 20.0 Then
												LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\GasedStop2.ogg")
											Else
												LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\GasedStop1.ogg")
												e\EventState3 = (-70.0) * 21.0
											EndIf
											e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
											e\EventState = 70.0 * 61.0
										EndIf
									Else
										e\EventState = e\EventState + fps\Factor[0]
										If e\EventState > 70.0 * 4.0 And e\EventState - fps\Factor[0] <= 70.0 * 4.0 Then
											LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\Help1.ogg")
											e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
											e\EventState = 70.0 * 10.0
										ElseIf e\EventState > 70.0 * 20.0 And e\EventState - fps\Factor[0] <= 70.0 * 20.0
											LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\Help2.ogg")
											e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
										ElseIf e\EventState > 70.0 * 40.0 And e\EventState - fps\Factor[0] <= 70.0 * 40.0
											LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\Idle1.ogg")
											e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
										ElseIf e\EventState > 70.0 * 50.0 And e\EventState - fps\Factor[0] <= 70.0 * 50.0
											LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\Idle2.ogg")
											e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
										ElseIf e\EventState > 70.0 * 80.0 And e\EventState - fps\Factor[0] <= 70.0 * 80.0
											If e\EventState2 Then ; ~ Skip the closet part if player has already opened it
												e\EventState = 70.0 * 130.0
											Else
												If e\EventState3 < (-70.0) * 30.0 Then ; ~ The host is dead
													LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\GasedCloset.ogg")
													e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
												ElseIf e\EventState3 = 0.0 ; ~ The gas valves haven't been opened
													LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\Closet1.ogg")
													e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
												Else ; ~ Gas valves have been opened but 035 isn't dead
													LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\GasedCloset.ogg")
													e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
												EndIf
											EndIf
										ElseIf e\EventState > 70.0 * 80.0
											If e\EventState2 Then e\EventState = Max(e\EventState, 70.0 * 100.0)
											If e\EventState > 70.0 * 110.0 And e\EventState - fps\Factor[0] <= 70.0 * 110.0 Then
												If e\EventState2 Then
													LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\Closet2.ogg")
													e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
													e\EventState = 70.0 * 130.0
												Else
													LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\Idle3.ogg")
													e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
												EndIf
											ElseIf e\EventState > 70.0 * 125.0 And e\EventState - fps\Factor[0] <= 70.0 * 125.0
												If e\EventState2 Then
													LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\Closet1.ogg")
													e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
												Else
													LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\Idle4.ogg")
													e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
												EndIf
											ElseIf e\EventState > 70.0 * 150.0 And e\EventState - fps\Factor[0] <= 70.0 * 150.0
												LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\Idle5.ogg")
												e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
											ElseIf e\EventState > 70.0 * 200.0 And e\EventState - fps\Factor[0] <= 70.0 * 200.0
												LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\Idle6.ogg")
												e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
											ElseIf e\EventState > 70.0 * 250.0 And e\EventState - fps\Factor[0] <= 70.0 * 250.0
												LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\Idle7.ogg")
												e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
											EndIf
										EndIf
									EndIf
								EndIf
							Else ; ~ The player has opened the door
								If e\EventState2 < 10.0 Then
									e\room\RoomDoors[2]\Open = False
									e\room\RoomDoors[2]\Locked = 1
									
									For i = 0 To 1
										If (Not e\room\RoomDoors[i]\Open) Then OpenCloseDoor(e\room\RoomDoors[i])
									Next
									
									If e\EventState3 = 0.0 Then
										LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\Escape.ogg")
										e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
									ElseIf Abs(e\EventState3) > 70.0 * 35.0
										LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\KilledEscape.ogg")
										e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
									Else
										LoadNPCSound(e\room\NPC[0], "SFX\SCP\035\GasedEscape.ogg")
										e\room\NPC[0]\SoundCHN = PlaySound_Strict(e\room\NPC[0]\Sound)
									EndIf
									e\EventState2 = 20.0
								EndIf
								
								If e\EventState2 = 20.0 Then
									Dist = EntityDistanceSquared(e\room\RoomDoors[0]\FrameOBJ, e\room\NPC[0]\Collider)
									e\room\NPC[0]\State = 1.0
									If Dist > 6.25 Then
										PointEntity(e\room\NPC[0]\OBJ, e\room\RoomDoors[1]\FrameOBJ)
										RotateEntity(e\room\NPC[0]\Collider, 0.0, CurveAngle(EntityYaw(e\room\NPC[0]\OBJ), EntityYaw(e\room\NPC[0]\Collider), 15.0), 0)
									ElseIf Dist > 0.49
										If ChannelPlaying(e\room\NPC[0]\SoundCHN) Then
											e\room\NPC[0]\State = 0.0
											PointEntity(e\room\NPC[0]\OBJ, me\Collider)
											RotateEntity(e\room\NPC[0]\Collider, 0, CurveAngle(EntityYaw(e\room\NPC[0]\OBJ), EntityYaw(e\room\NPC[0]\Collider), 15.0), 0.0)
										Else
											PointEntity(e\room\NPC[0]\OBJ, e\room\RoomDoors[0]\FrameOBJ)
											RotateEntity(e\room\NPC[0]\Collider, 0.0, CurveAngle(EntityYaw(e\room\NPC[0]\OBJ), EntityYaw(e\room\NPC[0]\Collider), 15.0), 0)
										EndIf
									Else
										RemoveNPC(e\room\NPC[0]) : e\room\NPC[0] = Null
										e\EventState = -1.0
										e\EventState2 = 0.0
										e\EventState3 = 0.0
										For i = 0 To 2
											e\room\RoomDoors[i]\Locked = 0
										Next
										OpenCloseDoor(e\room\RoomDoors[1])
										For do.Doors = Each Doors
											If do\DoorType = HEAVY_DOOR Then
												If Abs(EntityX(e\room\OBJ) - EntityX(do\FrameOBJ, True)) < 4.5 Then 
													If Abs(EntityZ(e\room\OBJ) - EntityZ(do\FrameOBJ, True)) < 4.5 Then 
														OpenCloseDoor(do)
														Exit
													EndIf
												EndIf
											EndIf
										Next
									EndIf
								EndIf
							EndIf
						EndIf
					Else ; ~ SCP-035 has left
						If UpdateLever(e\room\RoomLevers[1]\OBJ, False) Then
							PositionEntity(e\room\Objects[2], EntityX(e\room\Objects[2], True), 400.0 * RoomScale, EntityZ(e\room\Objects[2], True), True)
							PositionEntity(e\room\Objects[3], EntityX(e\room\Objects[3], True), 400.0 * RoomScale, EntityZ(e\room\Objects[3], True), True)
						Else
							PositionEntity(e\room\Objects[2], EntityX(e\room\Objects[2], True), 20.0, EntityZ(e\room\Objects[2], True), True)
							PositionEntity(e\room\Objects[3], EntityX(e\room\Objects[3], True), 20.0, EntityZ(e\room\Objects[3], True), True)
						EndIf
						
						ShouldPlay = 1
						
						Temp = False
						
						; ~ Player is inside the containment chamber
						If EntityX(me\Collider) > Min(EntityX(e\room\Objects[4], True), EntityX(e\room\Objects[5], True)) Then
							If EntityX(me\Collider) < Max(EntityX(e\room\Objects[4], True), EntityX(e\room\Objects[5], True)) Then
								If EntityZ(me\Collider) > Min(EntityZ(e\room\Objects[4], True), EntityZ(e\room\Objects[5], True)) Then
									If EntityZ(me\Collider) < Max(EntityZ(e\room\Objects[4], True), EntityZ(e\room\Objects[5], True)) Then
										If e\room\NPC[0] = Null Then e\room\NPC[0] = CreateNPC(NPCType035_Tentacle, 0.0, 0.0, 0.0)
										
										PositionEntity(e\room\NPC[0]\Collider, EntityX(e\room\Objects[1], True), 0.13, EntityZ(e\room\Objects[1], True))
										
										If e\room\NPC[0]\State > 0.0 Then
											If e\room\NPC[1] = Null Then e\room\NPC[1] = CreateNPC(NPCType035_Tentacle, 0.0, 0.0, 0.0)
										EndIf
										
										me\Stamina = CurveValue(Min(60.0, me\Stamina), me\Stamina, 20.0)
										
										Temp = True
										
										If (Not e\Sound) Then LoadEventSound(e, "SFX\Room\035Chamber\Whispers1.ogg")
										If (Not e\Sound2) Then LoadEventSound(e, "SFX\Room\035Chamber\Whispers2.ogg", 1)
										
										e\EventState2 = Min(e\EventState2 + (fps\Factor[0] / 6000.0), 1.0)
										e\EventState3 = CurveValue(e\EventState2, e\EventState3, 50.0)
										
										If I_714\Using <> 3 And wi\HazmatSuit <> 4 And wi\GasMask <> 4 Then
											me\Sanity = me\Sanity - (fps\Factor[0] / I_714\Using)
											me\BlurTimer = Sin(MilliSecs2() / 10.0) * Abs(me\Sanity)
										EndIf
										
										If wi\HazmatSuit = 0 Then
											InjurePlayer(fps\Factor[0] / 10000.0)
										ElseIf wi\HazmatSuit <> 4
											InjurePlayer(fps\Factor[0] / 20000.0)
										Else
											InjurePlayer(fps\Factor[0] / 30000.0)
										EndIf
										
										If me\Terminated And me\Bloodloss >= 100.0 Then msg\DeathMsg = Format(GetLocalString("death", "035"), SubjectName)
									EndIf
								EndIf
							EndIf
						EndIf
						
						If e\room\NPC[1] <> Null Then
							PositionEntity(e\room\NPC[1]\Collider, EntityX(e\room\OBJ, True), 0.13, EntityZ(e\room\OBJ, True))
							Angle = WrapAngle(EntityYaw(e\room\NPC[1]\Collider) - e\room\Angle)
							
							If Angle > 90.0 Then 
								If Angle < 225.0 Then
									RotateEntity(e\room\NPC[1]\Collider, 0.0, e\room\Angle - 89.0 - 180.0, 0.0)
								Else
									RotateEntity(e\room\NPC[1]\Collider, 0.0, e\room\Angle - 1.0, 0.0)
								EndIf
							EndIf
						EndIf
						
						If (Not Temp) Then
							e\EventState2 = Max(e\EventState2 - (fps\Factor[0] / 2000.0), 0.0)
							e\EventState3 = Max(e\EventState3 - (fps\Factor[0] / 100.0), 0.0)
						EndIf
						
						If e\EventState3 > 0.0 And I_714\Using <> 3 And wi\HazmatSuit <> 4 And wi\GasMask <> 4 Then 
							e\SoundCHN = LoopSound2(e\Sound, e\SoundCHN, Camera, e\room\OBJ, 10.0, e\EventState3)
							e\SoundCHN2 = LoopSound2(e\Sound2, e\SoundCHN2, Camera, e\room\OBJ, 10.0, (e\EventState3 - 0.5) * 2.0)
						EndIf
					EndIf
				Else
					If e\EventState = 0.0 Then
						If (Not e\Sound) Then
							If EntityDistanceSquared(me\Collider, e\room\OBJ) < 400.0 Then
								LoadEventSound(e, "SFX\Room\035Chamber\InProximity.ogg")
								PlaySound_Strict(e\Sound)
							EndIf
						EndIf
					ElseIf e\EventState < 0.0
						For i = 0 To 1
							If e\room\NPC[i] <> Null Then RemoveNPC(e\room\NPC[i]) : e\room\NPC[i] = Null
						Next
					EndIf
				EndIf
				;[End Block]
			Case e_cont2_049
				;[Block]
				If PlayerRoom = e\room Then
					If EntityY(me\Collider) > (-2848.0) * RoomScale Then
						e\EventState2 = UpdateElevators(e\EventState2, e\room\RoomDoors[0], e\room\RoomDoors[1], e\room\Objects[0], e\room\Objects[1], e)
						e\EventState3 = UpdateElevators(e\EventState3, e\room\RoomDoors[2], e\room\RoomDoors[3], e\room\Objects[2], e\room\Objects[3], e)
					Else
						ShouldPlay = 25
						
						If e\EventState = 0.0 Then
							n.NPCs = CreateNPC(NPCType049_2, EntityX(e\room\Objects[4], True), EntityY(e\room\Objects[4], True), EntityZ(e\room\Objects[4], True))
							PointEntity(n\Collider, e\room\OBJ)
							TurnEntity(n\Collider, 0.0, e\room\Angle, 0.0)
							
							n.NPCs = CreateNPC(NPCType049_2, EntityX(e\room\Objects[5], True), EntityY(e\room\Objects[5], True), EntityZ(e\room\Objects[5], True))
							PointEntity(n\Collider, e\room\OBJ)
							TurnEntity(n\Collider, 0.0, e\room\Angle + 60.0, 0.0)
							
							If n_I\Curr049 <> Null Then
								e\room\NPC[0] = n_I\Curr049
								e\room\NPC[0]\State = 2.0 : e\room\NPC[0]\Idle = 1 : e\room\NPC[0]\HideFromNVG = True
								PositionEntity(e\room\NPC[0]\Collider, EntityX(e\room\Objects[4], True), EntityY(e\room\Objects[4], True) + 3.0, EntityZ(e\room\Objects[4], True))
								ResetEntity(e\room\NPC[0]\Collider)
								PointEntity(e\room\NPC[0]\Collider, e\room\OBJ)
							Else
								n_I\Curr049 = CreateNPC(NPCType049, EntityX(e\room\Objects[4], True), EntityY(e\room\Objects[4], True) + 3.0, EntityZ(e\room\Objects[4], True))
								n_I\Curr049\State = 2.0 : n_I\Curr049\Idle = 1 : n_I\Curr049\HideFromNVG = True
								PointEntity(n_I\Curr049\Collider, e\room\OBJ)
								e\room\NPC[0] = n_I\Curr049
							EndIf
							
							PlaySound_Strict(LoadTempSound("SFX\Room\Blackout.ogg"))
							If EntityDistanceSquared(e\room\Objects[7], me\Collider) < EntityDistanceSquared(e\room\Objects[8], me\Collider) Then
								it.Items = CreateItem("Research Sector-02 Scheme", "paper", EntityX(e\room\Objects[7], True), EntityY(e\room\Objects[7], True), EntityZ(e\room\Objects[7], True))
							Else
								it.Items = CreateItem("Research Sector-02 Scheme", "paper", EntityX(e\room\Objects[8], True), EntityY(e\room\Objects[8], True), EntityZ(e\room\Objects[8], True))
							EndIf
							EntityType(it\Collider, HIT_ITEM)
							
							e\EventState = 1.0
						ElseIf e\EventState > 0.0
							Local PrevGenLever% = (EntityPitch(e\room\RoomLevers[1]\OBJ, True) > 0.0)
							
							Temp = (Not UpdateLever(e\room\RoomLevers[0]\OBJ)) ; ~ Power feed
							x = UpdateLever(e\room\RoomLevers[1]\OBJ) ; ~ Generator
							
							e\room\RoomDoors[1]\Locked = 1
							e\room\RoomDoors[3]\Locked = 1
							e\room\RoomDoors[1]\IsElevatorDoor = 0
							e\room\RoomDoors[3]\IsElevatorDoor = 0
							
							If PrevGenLever <> x Then
								If (Not x) Then
									PlaySound_Strict(LightSFX)
								Else
									PlaySound_Strict(TeslaPowerUpSFX)
								EndIf
							EndIf
							
							If e\EventState >= 70.0 Then
								If x Then
									ShouldPlay = 8
									e\EventState = Max(e\EventState, 70.0 * 180.0)
									SecondaryLightOn = CurveValue(1.0, SecondaryLightOn, 10.0)
									If (Not e\Sound2) Then LoadEventSound(e, "SFX\Ambient\Room Ambience\FuelPump.ogg", 1)
									e\SoundCHN2 = LoopSound2(e\Sound2, e\SoundCHN2, Camera, e\room\Objects[6], 6.0)
									For i = 4 To 6
										e\room\RoomDoors[i]\Locked = 0
									Next
								Else
									SecondaryLightOn = CurveValue(0.0, SecondaryLightOn, 10.0)
									If ChannelPlaying(e\SoundCHN2) Then StopChannel(e\SoundCHN2) : e\SoundCHN2 = 0
									For i = 4 To 6
										e\room\RoomDoors[i]\Locked = 1
									Next
								EndIf
							Else
								e\EventState = Min(e\EventState + fps\Factor[0], 70.0)
							EndIf
							
							If Temp And x Then
								e\room\RoomDoors[1]\Locked = 0
								e\room\RoomDoors[3]\Locked = 0
								e\EventState2 = UpdateElevators(e\EventState2, e\room\RoomDoors[0], e\room\RoomDoors[1], e\room\Objects[0], e\room\Objects[1], e)
								e\EventState3 = UpdateElevators(e\EventState3, e\room\RoomDoors[2], e\room\RoomDoors[3], e\room\Objects[2], e\room\Objects[3], e)
								
								If e\room\NPC[0]\Idle > 0
									i = 0
									If EntityDistanceSquared(me\Collider, e\room\RoomDoors[1]\FrameOBJ) < 9.0
										i = 1
									ElseIf EntityDistanceSquared(me\Collider, e\room\RoomDoors[3]\FrameOBJ) < 9.0
										i = 3
									EndIf
									If i > 0 Then
										PositionEntity(e\room\NPC[0]\Collider, EntityX(e\room\Objects[i], True), EntityY(e\room\Objects[i], True), EntityZ(e\room\Objects[i], True))
										ResetEntity(e\room\NPC[0]\Collider)
										PlaySound2(ElevatorBeepSFX, Camera, e\room\Objects[i], 4.0)
										e\room\RoomDoors[i]\Locked = 0
										OpenCloseDoor(e\room\RoomDoors[i])
										e\room\RoomDoors[i - 1]\Open = False
										e\room\RoomDoors[i]\Open = True
										e\room\NPC[0]\PathStatus = FindPath(e\room\NPC[0], EntityX(me\Collider), EntityY(me\Collider), EntityZ(me\Collider))
										LoadNPCSound(e\room\NPC[0], "SFX\SCP\049\DetectedInChamber.ogg", 1)
										e\room\NPC[0]\SoundCHN2 = LoopSound2(e\room\NPC[0]\Sound2, e\room\NPC[0]\SoundCHN2, Camera, e\room\NPC[0]\OBJ)
										e\room\NPC[0]\Idle = 0 : e\room\NPC[0]\HideFromNVG = False : e\room\NPC[0]\PrevState = 2 : e\room\NPC[0]\State = 2.0
									EndIf
								EndIf
							EndIf
							
							If e\EventState < 70.0 * 190.0 Then
								If e\EventState >= 70.0 * 180.0 Then
									e\room\RoomDoors[1]\Open = False
									e\room\RoomDoors[3]\Open = False
									e\room\RoomDoors[0]\Open = True
									e\room\RoomDoors[2]\Open = True
									
									e\EventState = 70.0 * 190.0
								EndIf
							ElseIf e\EventState < 70.0 * 240.0
								For n.NPCs = Each NPCs ; ~ Awake the zombies
									If n\NPCType = NPCType049_2 And n\State = 0.0 Then
										SetNPCFrame(n, 155.0)
										n\State = 1.0
									EndIf
								Next
								e\EventState = 70.0 * 241.0
							EndIf
						EndIf
					EndIf
				EndIf
				
				If e\EventState < 0.0 Then
					If e\EventState > (-70.0) * 4.0 Then
						If me\FallTimer >= 0.0 Then
							ShowEntity(me\Head)
							PositionEntity(me\Head, EntityX(Camera, True), EntityY(Camera, True), EntityZ(Camera, True), True)
							ResetEntity(me\Head)
							RotateEntity(me\Head, 0.0, EntityYaw(Camera) + Rnd(-45.0, 45.0), 0.0)
							me\FallTimer = Min(-1.0, me\FallTimer)
						ElseIf me\FallTimer < -230.0
							me\BlinkTimer = 0.0 : me\FallTimer = -231.0
							
							e\EventState = e\EventState - fps\Factor[0]
							
							If e\EventState <= (-70.0) * 4.0 Then
								UpdateTimer = 0.0
								HideEntity(me\Head)
								ShowEntity(me\Collider)
								me\DropSpeed = 0.0
								me\BlinkTimer = -10.0
								me\FallTimer = 0.0
								PositionEntity(me\Collider, EntityX(e\room\OBJ, True), EntityY(e\room\Objects[5], True) + 0.2, EntityZ(e\room\OBJ, True))
								ResetEntity(me\Collider)
								
								PositionEntity(e\room\NPC[0]\Collider, EntityX(e\room\Objects[0], True), EntityY(e\room\Objects[0], True), EntityZ(e\room\Objects[0], True), True)
								ResetEntity(e\room\NPC[0]\Collider)
								
								For n.NPCs = Each NPCs
									If n\NPCType = NPCType049_2 Then RemoveNPC(n)
								Next
								
								n.NPCs = CreateNPC(NPCTypeMTF, EntityX(e\room\Objects[5], True), EntityY(e\room\Objects[5], True) + 0.2, EntityZ(e\room\Objects[5], True))
								n\State = 6.0 : n\Reload = 70.0 * 6.0
								PointEntity(n\Collider, me\Collider)
								e\room\NPC[1] = n
								
								n.NPCs = CreateNPC(NPCTypeMTF, EntityX(e\room\Objects[5], True), EntityY(e\room\Objects[5], True) + 0.2, EntityZ(e\room\Objects[5], True))
								n\State = 6.0 : n\Reload = 70.0 * 6.0 + Rnd(15.0, 30.0)
								RotateEntity(n\Collider, 0.0, EntityYaw(e\room\NPC[1]\Collider), 0.0)
								MoveEntity(n\Collider, 0.5, 0.0, 0.0)
								PointEntity(n\Collider, me\Collider)
								
								n.NPCs = CreateNPC(NPCTypeMTF, EntityX(e\room\Objects[5], True), EntityY(e\room\Objects[5], True) + 0.2, EntityZ(e\room\Objects[5], True))
								n\State = 6.0 : n\Reload = 70.0 * 6.0 + Rnd(15.0, 30.0)
								RotateEntity(n\Collider, 0.0, EntityYaw(e\room\NPC[1]\Collider), 0.0)
								n\State2 = EntityYaw(n\Collider)
								MoveEntity(n\Collider, -0.65, 0.0, 0.0)
								
								MoveEntity(e\room\NPC[1]\Collider, 0.0, 0.0, 0.1)
								PointEntity(me\Collider, e\room\NPC[1]\Collider)
								
								PlaySound_Strict(LoadTempSound("SFX\Character\MTF\049_2\Spotted.ogg"))
								
								LoadEventSound(e, "SFX\SCP\049_2\Breath.ogg")
								
								me\Zombie = True
							EndIf
						EndIf
					Else
						me\BlurTimer = 800.0 : me\ForceMove = 0.5 : me\Injuries = Max(2.0, me\Injuries) : me\Bloodloss = 0.0
						I_008\Timer = 0.0 : I_409\Timer = 0.0
						
						Pvt = CreatePivot()
						PositionEntity(Pvt, EntityX(e\room\NPC[1]\Collider), EntityY(e\room\NPC[1]\Collider) + 0.2, EntityZ(e\room\NPC[1]\Collider))
						PointEntity(me\Collider, e\room\NPC[1]\Collider)
						PointEntity(Camera, Pvt, EntityRoll(Camera))
						FreeEntity(Pvt)
						
						If me\Terminated Then
							PlaySound_Strict(LoadTempSound("SFX\Character\MTF\049_2\TargetTerminated.ogg"))
							RemoveEvent(e)
						Else
							If (Not ChannelPlaying(e\SoundCHN)) Then e\SoundCHN = PlaySound_Strict(e\Sound)
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_cont1_079
				;[Block]
				If PlayerRoom = e\room Then
					If EntityY(me\Collider) < (-9500.0) * RoomScale Then
						If e\EventState = 0.0 Then
							e\room\NPC[0] = CreateNPC(NPCTypeGuard, EntityX(e\room\Objects[2], True), EntityY(e\room\Objects[2], True) + 0.5, EntityZ(e\room\Objects[2], True))
							e\room\NPC[0]\State = 8.0 : e\room\NPC[0]\IsDead = True
							SetNPCFrame(e\room\NPC[0], 288.0)
							PointEntity(e\room\NPC[0]\Collider, e\room\OBJ)
							RotateEntity(e\room\NPC[0]\Collider, 0.0, EntityYaw(e\room\NPC[0]\Collider), 0.0, True)
							
							e\EventState = 1.0
						EndIf
						ShouldPlay = 4
						
						If RemoteDoorOn Then
							e\room\RoomDoors[0]\Locked = 2
						ElseIf e\EventState < 10000.0
							e\room\RoomDoors[0]\Locked = 0
							If e\EventState = 1.0 Then
								e\EventState = 2.0
							ElseIf e\EventState = 2.0
								If EntityDistanceSquared(e\room\Objects[0], me\Collider) < 9.0 Then
									GiveAchievement(Achv079)
									e\EventState = 3.0
									e\EventState2 = 1.0
									
									If e\SoundCHN3 <> 0 Then StopStream_Strict(e\SoundCHN3) : e\SoundCHN3 = 0 : e\SoundCHN3_IsStream = False
									e\SoundCHN3 = StreamSound_Strict("SFX\SCP\079\Speech.ogg", opt\SFXVolume * opt\MasterVolume, 0)
									e\SoundCHN3_IsStream = True
								EndIf
							ElseIf e\EventState < 6000.0 Then
								If e\SoundCHN3 <> 0 Then
									If IsStreamPlaying_Strict(e\SoundCHN3) Then
										If Rand(4) = 1 Then
											EntityTexture(e\room\Objects[1], mon_I\MonitorOverlayID[Rand(MONITOR_079_OVERLAY_2, MONITOR_079_OVERLAY_7)])
											If EntityHidden(e\room\Objects[1]) Then ShowEntity(e\room\Objects[1])
										ElseIf Rand(10) = 1 
											If (Not EntityHidden(e\room\Objects[1])) Then HideEntity(e\room\Objects[1])
										EndIf
									Else
										StopStream_Strict(e\SoundCHN3) : e\SoundCHN3 = 0 : e\SoundCHN3_IsStream = False
										EntityTexture(e\room\Objects[1], mon_I\MonitorOverlayID[MONITOR_079_OVERLAY_1])
										If EntityHidden(e\room\Objects[1]) Then ShowEntity(e\room\Objects[1])
									EndIf
								EndIf
								e\EventState = e\EventState + fps\Factor[0]
							Else
								If EntityDistanceSquared(e\room\Objects[0], me\Collider) < 6.25 Then
									If e\SoundCHN3 <> 0 Then StopStream_Strict(e\SoundCHN3) : e\SoundCHN3 = 0 : e\SoundCHN3_IsStream = False
									e\SoundCHN3 = StreamSound_Strict("SFX\SCP\079\Refuse.ogg", opt\SFXVolume * opt\MasterVolume, 0)
									e\SoundCHN3_IsStream = True
									
									e\EventState = 10001.0
								EndIf
							EndIf
						Else
							If e\SoundCHN3 <> 0 Then
								If IsStreamPlaying_Strict(e\SoundCHN3) Then
									If Rand(4) = 1 Then
										EntityTexture(e\room\Objects[1], mon_I\MonitorOverlayID[Rand(MONITOR_079_OVERLAY_2, MONITOR_079_OVERLAY_7)])
										If EntityHidden(e\room\Objects[1]) Then ShowEntity(e\room\Objects[1])
									ElseIf Rand(10) = 1
										If (Not EntityHidden(e\room\Objects[1])) Then HideEntity(e\room\Objects[1])
									EndIf
								Else
									StopStream_Strict(e\SoundCHN3) : e\SoundCHN3 = 0 : e\SoundCHN3_IsStream = False
									EntityTexture(e\room\Objects[1], mon_I\MonitorOverlayID[MONITOR_079_OVERLAY_1])
									If EntityHidden(e\room\Objects[1]) Then ShowEntity(e\room\Objects[1])
								EndIf
							EndIf
						EndIf
					EndIf
					e\EventState4 = UpdateElevators(e\EventState4, e\room\RoomDoors[1], e\room\RoomDoors[2], e\room\Objects[3], e\room\Objects[4], e)
				EndIf
				
				If e\EventState2 = 1.0 Then
					If RemoteDoorOn Then
						If e\SoundCHN3 <> 0 Then StopStream_Strict(e\SoundCHN3) : e\SoundCHN3 = 0 : e\SoundCHN3_IsStream = False
						e\SoundCHN3 = StreamSound_Strict("SFX\SCP\079\GateB.ogg", opt\SFXVolume * opt\MasterVolume, 0)
						e\SoundCHN3_IsStream = True
						
						For e2.Events = Each Events
							If e2\EventID = e_gate_b_entrance Lor e2\EventID = e_gate_a_entrance Then e2\EventState3 = 1.0
						Next
						
						e\EventState2 = 2.0
					EndIf
				EndIf
				;[End Block]
			Case e_cont1_106
				;[Block]
				; ~ EventState2: Are the magnets on
				
				If SoundTransmission Then
					If e\EventState = 1.0 Then e\EventState3 = Min(e\EventState3 + fps\Factor[0], 4000.0)
					If (Not ChannelPlaying(e\SoundCHN3)) Then e\SoundCHN3 = PlaySound_Strict(RadioStatic)
				EndIf
				
				If PlayerRoom = e\room Then
					If e\room\NPC[0] <> Null Then
						If EntityY(me\Collider) < (-6900.0) * RoomScale
							ShouldPlay = 26
							
							If EntityHidden(e\room\Objects[7]) Then ShowEntity(e\room\Objects[7])
							
							e\room\NPC[0]\State = 6.0
							If e\room\NPC[0]\Idle = 0 Then
								AnimateNPC(e\room\NPC[0], 17.0, 19.0, 0.01, False)
								If e\room\NPC[0]\Frame = 19.0 Then e\room\NPC[0]\Idle = 1
							Else
								AnimateNPC(e\room\NPC[0], 19.0, 17.0, -0.01, False)
								If e\room\NPC[0]\Frame = 17.0 Then e\room\NPC[0]\Idle = 0
							EndIf
							
							PositionEntity(e\room\NPC[0]\Collider, EntityX(e\room\Objects[1], True), EntityY(e\room\Objects[1], True) + 0.1, EntityZ(e\room\Objects[1], True), True)
							RotateEntity(e\room\NPC[0]\Collider, EntityPitch(e\room\Objects[1], True), EntityYaw(e\room\Objects[1], True), 0.0, True)
							ResetEntity(e\room\NPC[0]\Collider)
							
							Temp = e\EventState2
							
							Local LeverState# = UpdateLever(e\room\RoomLevers[0]\OBJ, ((EntityY(e\room\Objects[2], True) < -8318.0 * RoomScale) And (EntityY(e\room\Objects[2], True) > -8603.0 * RoomScale)))
							
							If GrabbedEntity = e\room\RoomLevers[0]\OBJ And ga\DrawHandIcon = True Then e\EventState2 = LeverState
							
							If e\EventState2 <> Temp Then
								If e\EventState2 = 0.0 Then
									PlaySound_Strict(MagnetDownSFX)
								Else
									PlaySound_Strict(MagnetUpSFX)
								EndIf
							EndIf
							
							If ((e\EventState3 > 3200.0) Lor (e\EventState3 < 2500.0)) Lor (e\EventState <> 1.0) Then SoundTransmission = UpdateLever(e\room\RoomLevers[1]\OBJ)
							If (Not SoundTransmission) Then
								If ChannelPlaying(e\SoundCHN2) Then StopChannel(e\SoundCHN2) : e\SoundCHN2 = 0
								If ChannelPlaying(e\SoundCHN3) Then StopChannel(e\SoundCHN3) : e\SoundCHN3 = 0
							EndIf
							
							If e\EventState = 0.0 Then
								If SoundTransmission And Rand(100) = 1 Then
									If (Not ChannelPlaying(e\SoundCHN2)) Then
										LoadEventSound(e, "SFX\Character\LureSubject\Idle" + Rand(6) + ".ogg", 1)
										e\SoundCHN2 = PlaySound_Strict(e\Sound2)
									EndIf
								EndIf
								
								If SoundTransmission Then
									UpdateButton(e\room\Objects[0])
									If d_I\ClosestButton = e\room\Objects[0] And mo\MouseHit1 Then
										If ChannelPlaying(e\SoundCHN2) Then StopChannel(e\SoundCHN2) : e\SoundCHN2 = 0
										FemurBreakerSFX = LoadSound_Strict("SFX\Room\106Chamber\FemurBreaker.ogg")
										e\SoundCHN2 = PlaySound_Strict(FemurBreakerSFX)
										
										e\EventState = 1.0 ; ~ Start the femur breaker
									EndIf
								EndIf
							ElseIf e\EventState = 1.0 ; ~ Bone was broken
								If SoundTransmission And e\EventState3 < 2000.0 Then 
									If (Not ChannelPlaying(e\SoundCHN2)) Then
										LoadEventSound(e, "SFX\Character\LureSubject\Sniffling.ogg", 1)
										e\SoundCHN2 = PlaySound_Strict(e\Sound2)
									EndIf
								EndIf
								
								If e\EventState3 >= 2500.0 Then
									If e\EventState2 = 1.0 And e\EventState3 - fps\Factor[0] < 2500.0 Then
										PositionEntity(n_I\Curr106\Collider, EntityX(e\room\Objects[2], True), EntityY(e\room\Objects[2], True), EntityZ(e\room\Objects[2], True))
										ResetEntity(n_I\Curr106\Collider)
										n_I\Curr106\Contained = False
										ShowEntity(n_I\Curr106\OBJ)
										n_I\Curr106\State = -11.0 : n_I\Curr106\Idle = 0
										e\EventState = 2.0
										Exit
									EndIf
									
									ShouldPlay = 10
									
									PositionEntity(n_I\Curr106\Collider, EntityX(e\room\Objects[1], True), ((-6628.0) + 108.0 * (Min(e\EventState3 - 2500.0, 800.0) / 320.0)) * RoomScale, EntityZ(e\room\Objects[1], True))
									ResetEntity(n_I\Curr106\Collider)
									If (Not EntityHidden(n_I\Curr106\OBJ2)) Then HideEntity(n_I\Curr106\OBJ2)
									
									RotateEntity(n_I\Curr106\Collider, 0.0, EntityYaw(e\room\Objects[1], True) + 180.0, 0.0, True)
									n_I\Curr106\State = -11.0 : n_I\Curr106\Idle = 1
									AnimateNPC(n_I\Curr106, 206.0, 250.0, 0.1)
									
									If e\EventState3 - fps\Factor[0] < 2500.0 Then 
										de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(e\room\Objects[1], True), e\room\y - 6392.0 * RoomScale, EntityZ(e\room\Objects[1], True), 90.0, 0.0, Rnd(360.0), 0.1, 0.01) 
										de\Timer = 90000.0 : de\AlphaChange = 0.005 : de\SizeChange = 0.003
										EntityParent(de\OBJ, e\room\OBJ)
										
										If ChannelPlaying(e\SoundCHN2) Then StopChannel(e\SoundCHN2) : e\SoundCHN2 = 0
										LoadEventSound(e, "SFX\Character\LureSubject\106Bait.ogg", 1)
										e\SoundCHN2 = PlaySound_Strict(e\Sound2)
									ElseIf e\EventState3 - fps\Factor[0] < 2900.0 And e\EventState3 >= 2900.0 Then
										If FemurBreakerSFX <> 0 Then FreeSound_Strict(FemurBreakerSFX) : FemurBreakerSFX = 0
										
										de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(e\room\Objects[3], True), EntityY(e\room\Objects[3], True), EntityZ(e\room\Objects[3], True), 0.0, 0.0, 0.0, 0.05, 0.01) 
										de\Timer = 90000.0 : de\AlphaChange = 0.005 : de\SizeChange = 0.002
										RotateEntity(de\OBJ, EntityPitch(e\room\Objects[3], True) + Rnd(10.0, 20.0), EntityYaw(e\room\Objects[3], True) + 30.0, EntityRoll(de\OBJ))
										MoveEntity(de\OBJ, 0.0, 0.05, 0.2) 
										RotateEntity(de\OBJ, EntityPitch(e\room\Objects[3], True), EntityYaw(e\room\Objects[3], True), EntityRoll(de\OBJ))
										EntityParent(de\OBJ, e\room\Objects[3])
									ElseIf e\EventState3 > 3200.0 Then
										If e\EventState2 = 1.0 Then
											n_I\Curr106\Contained = True
										Else
											PositionEntity(n_I\Curr106\Collider, EntityX(e\room\Objects[2], True), EntityY(e\room\Objects[2], True), EntityZ(e\room\Objects[2], True))
											ResetEntity(n_I\Curr106\Collider)
											n_I\Curr106\Contained = False
											ShowEntity(n_I\Curr106\OBJ)
											n_I\Curr106\State = -11.0 : n_I\Curr106\Idle = 0
											
											e\EventState = 2.0
											Exit
										EndIf
									EndIf
								EndIf
							EndIf
							
							If e\EventState2 Then
								PositionEntity(e\room\Objects[2], EntityX(e\room\Objects[2], True), CurveValue(-8308.0 * RoomScale + Sin(Float(MilliSecs2()) * 0.04) * 0.07, EntityY(e\room\Objects[2], True), 200.0), EntityZ(e\room\Objects[2], True), True)
								RotateEntity(e\room\Objects[2], Sin(Float(MilliSecs2()) * 0.03), EntityYaw(e\room\Objects[2], True), -Sin(Float(MilliSecs2()) * 0.025), True)
							Else
								PositionEntity(e\room\Objects[2], EntityX(e\room\Objects[2], True), CurveValue(-8608.0 * RoomScale, EntityY(e\room\Objects[2], True), 200.0), EntityZ(e\room\Objects[2], True), True)
								RotateEntity(e\room\Objects[2], 0.0, EntityYaw(e\room\Objects[2], True), 0.0, True)
							EndIf
						EndIf
					Else
						TFormPoint(1088.0, -5900.0, 1728.0, e\room\OBJ, 0)
						e\room\NPC[0] = CreateNPC(NPCTypeD, TFormedX(), TFormedY(), TFormedZ())
						e\room\NPC[0]\HideFromNVG = True
						TurnEntity(e\room\NPC[0]\Collider, 0.0, e\room\Angle + 90.0, 0.0, True)
					EndIf
					e\EventState4 = UpdateElevators(e\EventState4, e\room\RoomDoors[0], e\room\RoomDoors[1], e\room\Objects[5], e\room\Objects[6], e)
				Else
					If (Not PlayerInReachableRoom()) Then
						If ChannelPlaying(e\SoundCHN2) Then StopChannel(e\SoundCHN2) : e\SoundCHN2 = 0
						If ChannelPlaying(e\SoundCHN3) Then StopChannel(e\SoundCHN3) : e\SoundCHN3 = 0
					EndIf
				EndIf
				;[End Block]
			Case e_cont1_205
				;[Block]
				If PlayerRoom = e\room Then
					For sc.SecurityCams = Each SecurityCams
						If sc\room = e\room Then
							If sc\ScrOBJ <> 0 Then
								If (Not EntityHidden(sc\BaseOBJ)) Then
									HideEntity(sc\BaseOBJ)
									If sc\CameraOBJ <> 0 Then HideEntity(sc\CameraOBJ)
									If sc\ScrOverlay <> 0 Then HideEntity(sc\ScrOverlay)
									If sc\MonitorOBJ <> 0 Then HideEntity(sc\MonitorOBJ)
								EndIf
							EndIf
							Exit
						EndIf
					Next
					
					If e\EventState = 0.0 Lor e\EventStr <> "LoadDone" Then
						If e\EventStr = "" And QuickLoadPercent = -1
							QuickLoadPercent = 0
							QuickLoad_CurrEvent = e
							e\EventStr = "Load0"
						EndIf
						
						If e\room\RoomDoors[1]\Open Then
							GiveAchievement(Achv205)
							e\EventState = 1.0
						EndIf
					Else
						ShouldPlay = 16
						If e\EventState < 65.0 Then
							If DistanceSquared(EntityX(me\Collider), EntityX(e\room\Objects[0], True), EntityZ(me\Collider), EntityZ(e\room\Objects[0], True)) < 4.0 And (Not chs\NoTarget) Then
								PlaySound_Strict(LoadTempSound("SFX\SCP\205\Enter.ogg"))
								
								e\EventState = Max(e\EventState, 65.0)
								
								If EntityHidden(e\room\Objects[3]) Then
									For i = 3 To 5
										ShowEntity(e\room\Objects[i])
									Next
								EndIf
								If (Not EntityHidden(e\room\Objects[6])) Then HideEntity(e\room\Objects[6])
								
								SetAnimTime(e\room\Objects[3], 492.0)
								SetAnimTime(e\room\Objects[4], 434.0)
								SetAnimTime(e\room\Objects[5], 434.0)
								
								If e\room\RoomDoors[0]\Open Then OpenCloseDoor(e\room\RoomDoors[0])
							EndIf
							
							If e\EventState > 7.0 Then
								If Rand(300) = 1 Then OpenCloseDoor(e\room\RoomDoors[0])
							EndIf
							e\EventState2 = e\EventState2 + fps\Factor[0]
						EndIf
						
						Select e\EventState
							Case 1.0
								;[Block]
								If EntityHidden(e\room\Objects[1]) Then ShowEntity(e\room\Objects[1])
								If (Not EntityHidden(e\room\Objects[3])) Then
									For i = 3 To 5
										HideEntity(e\room\Objects[i])
									Next
								EndIf
								; ~ Sitting
								If EntityHidden(e\room\Objects[6]) Then ShowEntity(e\room\Objects[6])
								Animate2(e\room\Objects[6], AnimTime(e\room\Objects[6]), 526.0, 530.0, 0.2)
								If e\EventState2 > 70.0 * 20.0 Then e\EventState = e\EventState + 1.0
								;[End Block]
							Case 3.0
								;[Block]
								If EntityHidden(e\room\Objects[1]) Then ShowEntity(e\room\Objects[1])
								If (Not EntityHidden(e\room\Objects[3])) Then
									For i = 3 To 5
										HideEntity(e\room\Objects[i])
									Next
								EndIf
								; ~ Laying down
								If EntityHidden(e\room\Objects[6]) Then ShowEntity(e\room\Objects[6])
								Animate2(e\room\Objects[6], AnimTime(e\room\Objects[6]), 377.0, 525.0, 0.2)
								If e\EventState2 > 70.0 * 30.0 Then e\EventState = e\EventState + 1.0
								;[End Block]
							Case 5.0
								;[Block]
								If EntityHidden(e\room\Objects[1]) Then ShowEntity(e\room\Objects[1])
								If (Not EntityHidden(e\room\Objects[3])) Then
									For i = 3 To 5
										HideEntity(e\room\Objects[i])
									Next
								EndIf
								; ~ Standing
								If EntityHidden(e\room\Objects[6]) Then ShowEntity(e\room\Objects[6])
								Animate2(e\room\Objects[6], AnimTime(e\room\Objects[6]), 228.0, 376.0, 0.2)
								If e\EventState2 > 70.0 * 40.0 Then 
									e\EventState = e\EventState + 1.0
									PlaySound2(LoadTempSound("SFX\SCP\205\Horror.ogg"), Camera, e\room\Objects[6], 10.0, 0.3)
								EndIf
								;[End Block]
							Case 7.0
								;[Block]
								If EntityHidden(e\room\Objects[1]) Then ShowEntity(e\room\Objects[1])
								For i = 3 To 6
									If i < 5 Then
										If (Not EntityHidden(e\room\Objects[i])) Then HideEntity(e\room\Objects[i])
									Else
										If EntityHidden(e\room\Objects[i]) Then ShowEntity(e\room\Objects[i])
									EndIf
								Next
								; ~ Sexy demon pose
								Animate2(e\room\Objects[5], AnimTime(e\room\Objects[5]), 500.0, 648.0, 0.2)
								If e\EventState2 > 70.0 * 60.0 Then
									PlaySound2(LoadTempSound("SFX\SCP\205\Horror.ogg"), Camera, e\room\Objects[6], 10.0, 0.5)
									e\EventState = e\EventState + 1.0
								EndIf
								;[End Block]
							Case 9.0
								;[Block]
								If EntityHidden(e\room\Objects[1]) Then ShowEntity(e\room\Objects[1])
								If (Not EntityHidden(e\room\Objects[3])) Then HideEntity(e\room\Objects[3])
								If EntityHidden(e\room\Objects[4]) Then
									For i = 4 To 6
										ShowEntity(e\room\Objects[i])
									Next
								EndIf
								; ~ Idle
								Animate2(e\room\Objects[4], AnimTime(e\room\Objects[4]), 2.0, 200.0, 0.2)
								Animate2(e\room\Objects[5], AnimTime(e\room\Objects[5]), 4.0, 125.0, 0.2)
								
								If e\EventState2 > 70.0 * 80.0 Then
									PlaySound_Strict(LoadTempSound("SFX\SCP\205\Horror.ogg"))
									e\EventState = e\EventState + 1.0
								EndIf
								;[End Block]
							Case 11.0
								;[Block]
								If EntityHidden(e\room\Objects[1]) Then ShowEntity(e\room\Objects[1])
								If EntityHidden(e\room\Objects[3]) Then
									For i = 3 To 6
										ShowEntity(e\room\Objects[i])
									Next
								EndIf
								
								; ~ Idle
								Animate2(e\room\Objects[3], AnimTime(e\room\Objects[3]), 2.0, 226.0, 0.2)
								Animate2(e\room\Objects[4], AnimTime(e\room\Objects[4]), 2.0, 200.0, 0.2)
								Animate2(e\room\Objects[5], AnimTime(e\room\Objects[5]), 4.0, 125.0, 0.2)
								
								If e\EventState2 > 70.0 * 85.0 Then e\EventState = e\EventState + 1.0
								;[End Block]
							Case 13.0
								;[Block]
								If EntityHidden(e\room\Objects[1]) Then ShowEntity(e\room\Objects[1])
								If EntityHidden(e\room\Objects[3]) Then
									For i = 3 To 6
										ShowEntity(e\room\Objects[i])
									Next
								EndIf
								If AnimTime(e\room\Objects[6]) <> 227.0 Then SetAnimTime(e\room\Objects[6], 227.0)
								
								Animate2(e\room\Objects[3], AnimTime(e\room\Objects[3]), 2.0, 491.0, 0.05)
								Animate2(e\room\Objects[4], AnimTime(e\room\Objects[4]), 197.0, 433.0, 0.05)
								Animate2(e\room\Objects[5], AnimTime(e\room\Objects[5]), 2.0, 433.0, 0.05)
								;[End Block]
							Case 66.0
								;[Block]
								If EntityHidden(e\room\Objects[1]) Then ShowEntity(e\room\Objects[1])
								Animate2(e\room\Objects[3], AnimTime(e\room\Objects[3]), 492.0, 534.0, 0.1, False)
								Animate2(e\room\Objects[4], AnimTime(e\room\Objects[4]), 434.0, 466.0, 0.1, False)
								Animate2(e\room\Objects[5], AnimTime(e\room\Objects[5]), 434.0, 494.0, 0.1, False)
								
								If AnimTime(e\room\Objects[3]) > 515.0 Then
									If AnimTime(e\room\Objects[3]) > 533.0 Then
										If (Not EntityHidden(e\room\Objects[1])) Then HideEntity(e\room\Objects[1])
										e\EventState = 67.0
										e\EventState2 = 0.0
										e\EventState3 = 0.0
									EndIf
								EndIf
								;[End Block]
							Case 67.0
								;[Block]
								If (Not chs\NoTarget) Then
									If Rand(150) = 1 Then
										msg\DeathMsg = Format(GetLocalString("death", "205"), SubjectName)
										
										InjurePlayer(Rnd(0.4, 0.8), 0.0, 300.0)
										PlaySound_Strict(DamageSFX[Rand(2, 3)])
										me\CameraShake = 0.5
										
										e\EventState2 = Rnd(-0.1, 0.1)
										e\EventState3 = Rnd(-0.1, 0.1)
										
										If me\Injuries > 5.0 Then Kill(True)
									EndIf
								EndIf
								
								TranslateEntity(me\Collider, e\EventState2, 0.0, e\EventState3)
								e\EventState2 = CurveValue(e\EventState2, 0.0, 10.0)
								e\EventState3 = CurveValue(e\EventState3, 0.0, 10.0)
								;[End Block]
							Default
								;[Block]
								If Rand(3) = 1 Then
									If (Not EntityHidden(e\room\Objects[1])) Then HideEntity(e\room\Objects[1])
								Else
									If EntityHidden(e\room\Objects[1]) Then ShowEntity(e\room\Objects[1])
								EndIf
								
								e\EventState3 = e\EventState3 + fps\Factor[0]
								If e\EventState3 > 50.0 Then
									If EntityHidden(e\room\Objects[1]) Then ShowEntity(e\room\Objects[1])
									e\EventState = e\EventState + 1.0
									e\EventState3 = 0.0
								EndIf
								;[End Block]
						End Select
					EndIf
				ElseIf e\room\Objects[3] <> 0
					If (Not EntityHidden(e\room\Objects[3])) Then
						For i = 3 To 6
							HideEntity(e\room\Objects[i])
						Next
					EndIf
				Else
					e\EventState = 0.0
					e\EventStr = ""
				EndIf
				;[End block]
			Case e_cont2_860_1
				;[Block]
				; ~ e\EventState: Is the player in the forest
				
				; ~ e\EventState2: Which side of the door did the player enter from
				
				; ~ e\EventState3: SCP-860-2 spawn timer
				
				Local fr.Forest = e\room\fr
				
				If PlayerRoom = e\room And fr <> Null Then
					If e\EventState = 1.0 Then ; ~ The player is in the forest
						CurrStepSFX = 2
						
						n_I\Curr106\Idle = 1
						
						For r.Rooms = Each Rooms
							HideRoomsNoColl(r)
						Next
						ShowRoomsNoColl(e\room)
						
						UpdateForest(fr)
						
						If e\EventStr = "" And QuickLoadPercent = -1
							QuickLoadPercent = 0
							QuickLoad_CurrEvent = e
							e\EventStr = "Load0"
						EndIf
						
						If e\room\NPC[0] <> Null Then
							If (e\room\NPC[0]\State2 = 1.0 And e\room\NPC[0]\State > 1.0) Lor e\room\NPC[0]\State > 2.0 ; ~ The monster is chasing the player
								ShouldPlay = 12
							Else
								ShouldPlay = 9
							EndIf
						EndIf
						
						; ~ The player fell
						If (Not chs\NoClip) Then
							If EntityY(me\Collider) <= 28.5 Then 
								Kill() 
								me\BlinkTimer = -2.0
							ElseIf EntityY(me\Collider) > EntityY(fr\Forest_Pivot, True) + 0.5
								MoveEntity(me\Collider, 0.0, ((EntityY(fr\Forest_Pivot, True) + 0.5) - EntityY(me\Collider)) * fps\Factor[0], 0.0)
							EndIf
						EndIf
						
						If e\room\NPC[0] <> Null Then
							If e\room\NPC[0]\State = 0.0 Lor EntityDistanceSquared(me\Collider, e\room\NPC[0]\Collider) > 400.0 Then
								e\EventState3 = e\EventState3 + (1.0 + me\CurrSpeed) * fps\Factor[0]
								If (e\EventState3 Mod 500.0) < 10.0 And ((e\EventState3 - (1.0 + me\CurrSpeed) * fps\Factor[0]) Mod 500.0) > 490.0 Then
									If e\EventState3 > 3000.0 - (500.0 * SelectedDifficulty\AggressiveNPCs) And Rnd(10000 + (500.0 * SelectedDifficulty\AggressiveNPCs)) < e\EventState3
										e\room\NPC[0]\State = 2.0
										PositionEntity(e\room\NPC[0]\Collider, 0.0, -110.0, 0.0)
										e\EventState3 = e\EventState3 - Rnd(1000.0, 2000.0 - (500.0 * SelectedDifficulty\AggressiveNPCs))
									Else
										e\room\NPC[0]\State = 1.0
										PositionEntity(e\room\NPC[0]\Collider, 0.0, -110.0, 0.0)
									EndIf
								EndIf
							EndIf
						EndIf
						
						For i = 0 To 1
							If fr\ForestDoors[i]\Open Then
								me\BlinkTimer = -10.0
								
								PositionEntity(me\Collider, EntityX(e\room\RoomDoors[0]\FrameOBJ, True), 0.5, EntityZ(e\room\RoomDoors[0]\FrameOBJ, True))
								
								RotateEntity(me\Collider, 0.0, EntityYaw(e\room\OBJ, True) + e\EventState2 * 180.0, 0.0)
								MoveEntity(me\Collider, 0.0, 0.0, 1.5)
								
								ResetEntity(me\Collider)
								
								UpdateTimer = 0.0
								
								SecondaryLightOn = PrevSecondaryLightOn
								
								e\EventState = 0.0
								e\EventState3 = 0.0
							EndIf
						Next
						
						CameraRange(Camera, RoomScale, 8.5)
						CameraFogRange(Camera, 0.05, 8.0)
					Else
						If (Not n_I\Curr106\Contained) Then n_I\Curr106\Idle = 0
						If fr\Forest_Pivot <> 0 Then
							If (Not EntityHidden(fr\Forest_Pivot)) Then HideEntity(fr\Forest_Pivot)
						EndIf
						; ~ Reset the doors after leaving the forest
						For i = 0 To 1
							fr\ForestDoors[i]\Open = False
							fr\ForestDoors[i]\Locked = 2
						Next
						
						If e\room\RoomDoors[0]\Open Then
							If EntityHidden(fr\Forest_Pivot) Then ShowEntity(fr\Forest_Pivot)
							
							me\BlinkTimer = -10.0
							
							; ~ Reset monster to the (hidden) idle state
							If e\room\NPC[0] <> Null Then e\room\NPC[0]\State = 0.0
							
							; ~ Reset the door leading to the forest
							e\room\RoomDoors[0]\Open = False
							e\room\RoomDoors[0]\Locked = 1
							
							PrevSecondaryLightOn = SecondaryLightOn
							SecondaryLightOn = True
							
							Pvt = CreatePivot()
							PositionEntity(Pvt, EntityX(Camera), EntityY(Camera), EntityZ(Camera))
							PointEntity(Pvt, e\room\OBJ)
							Angle = WrapAngle(EntityYaw(Pvt) - EntityYaw(e\room\OBJ, True))
							If Angle > 90.0 And Angle < 270.0 Then
								PositionEntity(me\Collider, EntityX(fr\ForestDoors[0]\FrameOBJ, True), EntityY(fr\ForestDoors[0]\FrameOBJ, True) + EntityY(me\Collider, True) + 0.5, EntityZ(fr\ForestDoors[0]\FrameOBJ, True), True)
								RotateEntity(me\Collider, 0.0, EntityYaw(fr\ForestDoors[0]\FrameOBJ, True) - 180.0, 0.0, True)
								MoveEntity(me\Collider, -0.5, 0.0, 0.5)
								
								; ~ Determine the locked door
								fr\ForestDoors[0]\Locked = 2
								fr\ForestDoors[1]\Locked = 1
								
								e\EventState2 = 1.0
							Else
								PositionEntity(me\Collider, EntityX(fr\ForestDoors[1]\FrameOBJ, True), EntityY(fr\ForestDoors[1]\FrameOBJ, True) + EntityY(me\Collider, True) + 0.5, EntityZ(fr\ForestDoors[1]\FrameOBJ, True), True)
								RotateEntity(me\Collider, 0.0, EntityYaw(fr\ForestDoors[1]\FrameOBJ, True) - 180.0, 0.0, True)
								MoveEntity(me\Collider, -0.5, 0.0, 0.5)
								
								; ~ Determine the locked door
								fr\ForestDoors[0]\Locked = 1
								fr\ForestDoors[1]\Locked = 2
								
								e\EventState2 = 0.0
							EndIf
							FreeEntity(Pvt)
							ResetEntity(me\Collider)
							
							; ~ Reset monster spawn timer
							e\EventState3 = 0.0
							; ~ The player has entered the forest
							e\EventState = 1.0
						EndIf
					EndIf
				Else
					If fr <> Null Then
						If fr\Forest_Pivot <> 0 Then
							If (Not EntityHidden(fr\Forest_Pivot)) Then HideEntity(fr\Forest_Pivot)
						EndIf
					Else
						RemoveEvent(e)
					EndIf
				EndIf
				;[End Block]
			Case e_cont3_966
				;[Block]
				If PlayerRoom = e\room Then
					If e\EventState = 0.0 Then
						If RemoteDoorOn Then
							PlaySound_Strict(LightSFX)
							me\LightBlink = 4.0
							For i = 0 To 1
								If (Not e\room\RoomDoors[i]\Open) Then
									e\room\RoomDoors[i]\SoundCHN = PlaySound2(LoadTempSound("SFX\Door\DoorSparks.ogg"), Camera, e\room\RoomDoors[i]\FrameOBJ, 5.0)
									OpenCloseDoor(e\room\RoomDoors[i])
								EndIf
							Next
						EndIf
						If e\room\RoomDoors[0]\Open Lor e\room\RoomDoors[1]\Open Then
							For i = 0 To 1
								CreateNPC(NPCType966, EntityX(e\room\Objects[i], True), EntityY(e\room\Objects[i], True), EntityZ(e\room\Objects[i], True))
							Next
							
							e\EventState = 1.0
							
							RemoveEvent(e)
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_cont2_1123
				;[Block]
				If PlayerRoom = e\room Then
					If I_714\Using = 1 And wi\HazmatSuit <> 4 And wi\GasMask <> 4 Then
						If EntityDistanceSquared(me\Collider, e\room\Objects[0]) < 0.81 Lor e\EventState > 0.0 Then
							If e\EventState = 0.0 Then me\BlurTimer = 1000.0
							me\CameraShake = 1.0
							If (Not e\Sound2) Then e\Sound2 = LoadSound_Strict("SFX\SCP\1123\Ambient.ogg")
							e\SoundCHN2 = LoopSound2(e\Sound2, e\SoundCHN2, Camera, me\Collider, 4.0, 4.0)
						EndIf
					EndIf
					; ~ The event is started when the player picks up SCP-1123 (in Items.bb/UpdateItems())
					If e\EventState > 0.0 Then CanSave = 0
					If e\EventState = 1.0 Then
						; ~ Saving me\Injuries and me\Bloodloss, so that the player won't be healed automatically
						me\PrevInjuries = me\Injuries
						me\PrevBloodloss = me\Bloodloss
						PrevSecondaryLightOn = SecondaryLightOn
						SecondaryLightOn = True
						
						e\room\NPC[0] = CreateNPC(NPCTypeD, EntityX(e\room\Objects[1], True), EntityY(e\room\Objects[1], True), EntityZ(e\room\Objects[1], True))
						FreeEntity(e\room\NPC[0]\OBJ) : e\room\NPC[0]\OBJ = 0
						e\room\NPC[0]\OBJ = CopyEntity(n_I\NPCModelID[NPC_NAZI_MODEL])
						Scale = IniGetFloat(NPCsFile, "Class D", "Scale") / MeshWidth(e\room\NPC[0]\OBJ)
						ScaleEntity(e\room\NPC[0]\OBJ, Scale, Scale, Scale)
						
						PositionEntity(me\Collider, EntityX(e\room\Objects[2], True), EntityY(e\room\Objects[2], True), EntityZ(e\room\Objects[2], True), True)
						ResetEntity(me\Collider)
						
						me\CameraShake = 1.0
						me\BlurTimer = 1200.0
						me\Injuries = 1.0
						e\EventState = 2.0
					ElseIf e\EventState = 2.0
						e\EventState2 = e\EventState2 + fps\Factor[0]
						
						PointEntity(e\room\NPC[0]\Collider, me\Collider)
						me\BlurTimer = Max(me\BlurTimer, 100.0)
						
						If e\EventState2 > 200.0 And e\EventState2 - fps\Factor[0] <= 200.0 Then
							e\Sound = LoadSound_Strict("SFX\Music\Cont_1123.ogg")
							e\SoundCHN = PlaySound_Strict(e\Sound)
						EndIf
						
						If e\EventState2 > 1000.0 Then
							If (Not e\room\RoomDoors[1]\Open) Then OpenCloseDoor(e\room\RoomDoors[1])
							
							If e\EventState2 >= 1040.0 And e\EventState2 - fps\Factor[0] < 1040.0 Then 
								PlaySound2(LoadTempSound("SFX\SCP\1123\Officer1.ogg"), Camera, e\room\NPC[0]\OBJ)
							ElseIf e\EventState2 >= 1400.0 And e\EventState2 - fps\Factor[0] < 1400.0
								PlaySound2(LoadTempSound("SFX\SCP\1123\Officer2.ogg"), Camera, e\room\NPC[0]\OBJ)
							EndIf
							e\room\NPC[0]\State = 3.0
							AnimateNPC(e\room\NPC[0], 3.0, 26.0, 0.2)
							If EntityDistanceSquared(me\Collider, e\room\Objects[2]) > PowTwo(392.0 * RoomScale) Then
								me\BlinkTimer = -10.0
								me\BlurTimer = 500.0
								PositionEntity(me\Collider, EntityX(e\room\Objects[3], True), EntityY(e\room\Objects[3], True), EntityZ(e\room\Objects[3], True), True)
								RotateEntity(me\Collider, 0.0, EntityYaw(e\room\OBJ, True), 0.0)
								ResetEntity(me\Collider)
								e\EventState = 3.0
							EndIf
						EndIf
					ElseIf e\EventState = 3.0
						If e\room\RoomDoors[0]\OpenState > 160.0 Then
							If (Not e\Sound) Then
								e\Sound = LoadSound_Strict("SFX\Music\1123.ogg")
								e\SoundCHN = PlaySound_Strict(e\Sound)
							EndIf
							
							PositionEntity(e\room\NPC[0]\Collider, EntityX(e\room\Objects[4], True), EntityY(e\room\Objects[4], True), EntityZ(e\room\Objects[4], True))
							ResetEntity(e\room\NPC[0]\Collider)
							
							e\EventState = 4.0
						EndIf
					ElseIf e\EventState = 4.0
						TFormPoint(EntityX(me\Collider), EntityY(me\Collider), EntityZ(me\Collider), 0.0, e\room\OBJ)
						
						If TFormedX() < 256.0 And TFormedZ() > -480.0 Then e\room\RoomDoors[0]\Open = False
						
						If e\room\RoomDoors[3]\Open Then
							e\room\NPC[0]\State = 3.0
							PointEntity(e\room\NPC[0]\Collider, me\Collider)
							AnimateNPC(e\room\NPC[0], 27.0, 54.0, 0.5, False)
							If e\room\NPC[0]\Frame >= 54.0 Then
								PlaySound_Strict(LoadTempSound("SFX\SCP\1123\Horror.ogg"))
								
								me\BlinkTimer = -10.0
								me\BlurTimer = 500.0
								me\Injuries = 1.5
								me\Bloodloss = 70.0
								
								PositionEntity(me\Collider, EntityX(e\room\OBJ, True), 0.3, EntityZ(e\room\OBJ, True), True)
								ResetEntity(me\Collider)
								
								e\EventState = 5.0
								e\EventState2 = 0.0
							EndIf
						EndIf
					ElseIf e\EventState = 5.0
						e\EventState2 = e\EventState2 + fps\Factor[0]
						If e\EventState2 > 500.0 Then
							For i = 2 To 3
								e\room\RoomDoors[i]\Open = False
								e\room\RoomDoors[i]\Locked = 2
							Next
							
							PositionEntity(e\room\NPC[0]\Collider, EntityX(e\room\Objects[5], True), EntityY(e\room\Objects[5], True), EntityZ(e\room\Objects[5], True))
							ResetEntity(e\room\NPC[0]\Collider)
							
							me\Injuries = 1.5
							me\Bloodloss = 70.0
							me\BlinkTimer = -10.0
							me\BlurTimer = 500.0
							
							PositionEntity(me\Collider, EntityX(e\room\Objects[6], True), EntityY(e\room\Objects[6], True), EntityZ(e\room\Objects[6], True), True)
							ResetEntity(me\Collider)
							
							de.Decals = CreateDecal(DECAL_BLOOD_2, EntityX(me\Collider, True), e\room\y + 769.0 * RoomScale + 0.005, EntityZ(me\Collider, True), 90.0, Rnd(360.0), 0.0, 0.5)
							EntityParent(de\OBJ, e\room\OBJ)
							
							e\room\NPC[0]\Sound = LoadSound_Strict("SFX\SCP\1123\Officer3.ogg")
							e\room\NPC[0]\SoundCHN = PlaySound2(e\room\NPC[0]\Sound, Camera, e\room\NPC[0]\Collider, 7.0)
							
							e\EventState = 6.0
						EndIf
					ElseIf e\EventState = 6.0
						PointEntity(e\room\NPC[0]\Collider, me\Collider)
						AnimateNPC(e\room\NPC[0], 75.0, 128.0, 0.04)
						If (Not ChannelPlaying(e\room\NPC[0]\SoundCHN)) Then
							PlaySound_Strict(LoadTempSound("SFX\SCP\1123\Gunshot.ogg"))
							If e\room\NPC[0]\Sound <> 0 Then FreeSound_Strict(e\room\NPC[0]\Sound) : e\room\NPC[0]\Sound = 0
							e\EventState = 7.0
						EndIf
					ElseIf e\EventState = 7.0
						PositionEntity(me\Collider, EntityX(e\room\OBJ, True), 0.3, EntityZ(e\room\OBJ, True), True)
						ResetEntity(me\Collider)
						me\LightFlash = 6.0
						me\BlurTimer = 500.0
						me\Injuries = me\PrevInjuries
						me\Bloodloss = me\PrevBloodloss
						SecondaryLightOn = PrevSecondaryLightOn
						me\PrevInjuries = 0.0
						me\PrevBloodloss = 0.0
						PrevSecondaryLightOn = 0.0
						If (Not me\Crouch) Then SetCrouch(True)
						For i = 0 To MaxItemAmount - 1
							If Inventory(i) <> Null Then
								If Inventory(i)\ItemTemplate\Name = "Leaflet" Then
									RemoveItem(Inventory(i))
									Exit
								EndIf
							EndIf
						Next
						GiveAchievement(Achv1123)
						
						StopChannel(e\SoundCHN2) : e\SoundCHN2 = 0
						If e\Sound2 <> 0 Then FreeSound_Strict(e\Sound2) : e\Sound2 = 0
						
						If e\room\NPC[0] <> Null Then RemoveNPC(e\room\NPC[0])
						RemoveEvent(e)
					EndIf
				EndIf
				;[End Block]
			Case e_room2_test_hcz
				;[Block]
				If e\EventState = 0.0 Then
					If PlayerRoom = e\room Then
						e\room\Objects[7] = CopyEntity(n_I\NPCModelID[NPC_1048_MODEL])
						ScaleEntity(e\room\Objects[7], 0.05, 0.05, 0.05)
						
						TFormPoint(EntityX(me\Collider), EntityY(me\Collider), EntityZ(me\Collider), 0.0, e\room\OBJ)
						If TFormedZ() = 0.0 Then
							Temp = -1
						Else
							Temp = -Sgn(TFormedZ())
						EndIf
						TFormPoint(-720.0, 0.0, 816.0 * Temp, e\room\OBJ, 0)
						PositionEntity(e\room\Objects[7], TFormedX(), 0.0, TFormedZ())
						
						RotateEntity(e\room\Objects[7], -90.0, e\room\Angle - 90.0, 0.0)
						SetAnimTime(e\room\Objects[7], 297.0)
						e\EventState = 1.0
					EndIf
				ElseIf e\EventState = 1.0
					If e\room\Objects[7] <> 0 Then
						Animate2(e\room\Objects[7], AnimTime(e\room\Objects[7]), 284.0, 295.0, 0.3)
						MoveEntity(e\room\Objects[7], 0.0, (-0.008) * fps\Factor[0], 0.0)
						TFormPoint(EntityX(e\room\Objects[7]), EntityY(e\room\Objects[7]), EntityZ(e\room\Objects[7]), 0.0, e\room\OBJ)
						
						If Abs(TFormedX()) > 725.0 Lor e\room\RoomDoors[0]\Open Then
							FreeEntity(e\room\Objects[7]) : e\room\Objects[7] = 0
							e\EventState = 2.0
						EndIf
					EndIf
				EndIf
				
				If PlayerRoom = e\room Then
					If e\EventState = 2.0 Then
						If EntityDistanceSquared(me\Collider, e\room\Objects[6]) < 6.25 And e\EventState > 0.0 Then
							PlaySound_Strict(LoadTempSound("SFX\SCP\079\TestroomWarning.ogg"))
							For i = 0 To 5
								em.Emitters = CreateEmitter(EntityX(e\room\Objects[i], True), EntityY(e\room\Objects[i], True), EntityZ(e\room\Objects[i], True), 0)
								em\RandAngle = 5.0 : em\Speed = 0.042 : em\SizeChange = 0.0025
								TurnEntity(em\OBJ, 90.0, 0.0, 0.0, True)
							Next
							RemoveEvent(e)
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_room2_6_hcz_smoke
				;[Block]
				If PlayerRoom = e\room Then
					If e\room\Dist < 3.5 Then
						PlaySound2(BurstSFX, Camera, e\room\OBJ)
						For i = 0 To 1
							em.Emitters = CreateEmitter(EntityX(e\room\Objects[i], True), EntityY(e\room\Objects[i], True), EntityZ(e\room\Objects[i], True), 0)
							em\Size = 0.05 : em\RandAngle = 10.0 : em\Speed = 0.06 : em\SizeChange = 0.007
							TurnEntity(em\OBJ, 90.0, 0.0, 0.0, True)
							EntityParent(em\OBJ, e\room\OBJ)
							For z = 0 To Ceil(3.3333 * (opt\ParticleAmount + 1))
								p.Particles = CreateParticle(PARTICLE_BLACK_SMOKE, EntityX(em\OBJ, True), 448.0 * RoomScale, EntityZ(em\OBJ, True), em\Size, em\Gravity, em\LifeTime)
								p\Speed = em\Speed : p\Size = 0.05 : p\SizeChange = 0.008
								RotateEntity(p\Pvt, Rnd(360.0), Rnd(360.0), 0.0, True)
							Next
						Next
						RemoveEvent(e)
					EndIf
				EndIf
				;[End Block]
			Case e_room2_6_hcz
				;[Block]
				If PlayerRoom = e\room Then
					If n_I\Curr173\Idle > 1 Then
						RemoveEvent(e)
						Exit
					Else
						If e\EventState = 0.0 Then
							If DistanceSquared(EntityX(me\Collider), EntityX(e\room\OBJ), EntityZ(me\Collider), EntityZ(e\room\OBJ)) < 12.25 Then
								PlaySound_Strict(LightSFX)
								
								me\LightBlink = 5.0
								e\EventState = 1.0
							EndIf
						EndIf
					EndIf
				EndIf
				
				If e\EventState > 0.0 And e\EventState < 200.0 Then
					If e\EventState > 30.0 And e\EventState - fps\Factor[0] <= 30.0 Then PlaySound_Strict(LoadTempSound("SFX\Ambient\General\Ambient3.ogg"))
					If e\EventState - fps\Factor[0] <= 100.0 And e\EventState > 100.0 Then
						PlaySound_Strict(LoadTempSound("SFX\Ambient\General\Ambient6.ogg"))
						PositionEntity(n_I\Curr173\Collider, EntityX(e\room\OBJ), 0.6, EntityZ(e\room\OBJ))
						ResetEntity(n_I\Curr173\Collider)
						n_I\Curr173\Idle = 1
						If wi\NightVision > 0 Then me\BlinkTimer = -10.0
					EndIf
					e\EventState = e\EventState + fps\Factor[0]
				ElseIf e\EventState > 0.0 And me\LightBlink = 0.0
					n_I\Curr173\Idle = 0
					RemoveEvent(e)
				EndIf
				;[End Block]
			Case e_room2_5_hcz_106
				;[Block]
				If e\EventState = 0.0 Then
					If e\room\Dist < 5.0 And e\room\Dist > 0.0 Then
						If n_I\Curr106\State >= 0.0 Then
							e\EventState = 1.0
						Else
							If n_I\Curr106\State <= -10.0 And EntityDistanceSquared(n_I\Curr106\Collider, me\Collider) > 25.0 And (Not EntityInView(n_I\Curr106\OBJ, Camera)) Then
								e\EventState = 1.0
								e\EventState2 = 1.0
							EndIf
						EndIf
					ElseIf n_I\Curr106\Contained
						RemoveEvent(e)
					EndIf
				ElseIf e\EventState = 1.0
					If e\room\Dist < 3.0 Lor Rand(7000) = 1 Then
						e\EventState = 2.0
						de.Decals = CreateDecal(DECAL_CORROSIVE_2, EntityX(e\room\OBJ), e\room\y + 445.0 * RoomScale, EntityZ(e\room\OBJ), -90.0, Rnd(360.0), 0.0, Rnd(0.5, 0.7), Rnd(0.7, 0.85))
						EntityParent(de\OBJ, e\room\OBJ)
						
						PlaySound_Strict(HorrorSFX[10])
					ElseIf e\room\Dist > 8.0
						If Rand(5) = 1 Then
							n_I\Curr106\Idle = 0
							RemoveEvent(e)
						Else
							n_I\Curr106\State = -10000.0 : n_I\Curr106\Idle = 0
							RemoveEvent(e)
						EndIf
					EndIf
				Else
					If e\EventState2 = 1.0 Then ShouldPlay = 10
					e\EventState = e\EventState + fps\Factor[0]
					If e\EventState <= 180.0 Then
						n_I\Curr106\State = 1.0 : n_I\Curr106\Idle = 1 : n_I\Curr106\DropSpeed = 0.0
						PositionEntity(n_I\Curr106\Collider, EntityX(e\room\OBJ, True), EntityY(me\Collider) + 1.0 - Min(Sin(e\EventState) * 1.5, 1.1), EntityZ(e\room\OBJ, True), True)
						PointEntity(n_I\Curr106\Collider, Camera)
						AnimateNPC(n_I\Curr106, 55.0, 104.0, 0.1)
						ResetEntity(n_I\Curr106\Collider)
						PositionEntity(n_I\Curr106\OBJ, EntityX(n_I\Curr106\Collider), EntityY(n_I\Curr106\Collider) - 0.15, EntityZ(n_I\Curr106\Collider))
						RotateEntity(n_I\Curr106\OBJ, 0.0, EntityYaw(n_I\Curr106\Collider), 0.0)
						If EntityHidden(n_I\Curr106\OBJ) Then ShowEntity(n_I\Curr106\OBJ)
					ElseIf e\EventState > 180.0 And e\EventState < 300.0 Then
						n_I\Curr106\State = -10.0 : n_I\Curr106\Idle = 0 : n_I\Curr106\PathTimer = 70.0 * 10.0 : n_I\Curr106\PathStatus = 0 : n_I\Curr106\PathLocation = 0
						PositionEntity(n_I\Curr106\Collider, EntityX(e\room\OBJ, True), -3.0, EntityZ(e\room\OBJ, True), True)
						ResetEntity(n_I\Curr106\Collider)
						de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(e\room\OBJ, True), e\room\y + 0.005, EntityZ(e\room\OBJ, True), 90.0, Rnd(360.0), 0.0, 0.05, 0.8)
						de\SizeChange = 0.01
						EntityParent(de\OBJ, e\room\OBJ)
						e\EventState = 300.0
					ElseIf e\EventState < 800.0
						If EntityY(n_I\Curr106\Collider) >= EntityY(me\Collider) - 0.05 Then
							RemoveEvent(e)
						Else
							TranslateEntity(n_I\Curr106\Collider, 0.0, ((EntityY(me\Collider, True) - 0.11) - EntityY(n_I\Curr106\Collider)) / 50.0, 0.0)
							If EntityY(n_I\Curr106\Collider) < -0.1 Then n_I\Curr106\CurrSpeed = 0.0
						EndIf
					Else
						RemoveEvent(e)
					EndIf
				EndIf
				;[End Block]
			Case e_room2_test_lcz_173
				;[Block]
				If PlayerRoom = e\room
					If n_I\Curr173\Idle = 0 Then
						If e\EventState = 0.0 Then
							If e\room\RoomDoors[0]\Open Then
								PositionEntity(n_I\Curr173\Collider, EntityX(e\room\Objects[0], True), 0.5, EntityZ(e\room\Objects[0], True))
								ResetEntity(n_I\Curr173\Collider)
								e\EventState = 1.0
							EndIf
						Else
							If (Not e\room\Objects[2]) Then
								Local GlassTex% = LoadTexture_Strict("GFX\Map\Textures\glass.png", 1 + 2)
								
								e\room\Objects[2] = CreateSprite()
								e\room\HideObject[2] = False
								EntityTexture(e\room\Objects[2], GlassTex)
								SpriteViewMode(e\room\Objects[2], 2)
								ScaleSprite(e\room\Objects[2], 182.0 * RoomScale * 0.5, 192.0 * RoomScale * 0.5)
								Pvt = CreatePivot(e\room\OBJ)
								PositionEntity(Pvt, -632.0, 224.0, -208.0)
								PositionEntity(e\room\Objects[2], EntityX(Pvt, True), EntityY(Pvt, True), EntityZ(Pvt, True))
								FreeEntity(Pvt)
								RotateEntity(e\room\Objects[2], 0.0, e\room\Angle, 0.0)
								TurnEntity(e\room\Objects[2], 0.0, 180.0, 0.0)
								EntityParent(e\room\Objects[2], e\room\OBJ)
								DeleteSingleTextureEntryFromCache(GlassTex)
							EndIf
							
							If EntityHidden(e\room\Objects[2]) Then ShowEntity(e\room\Objects[2])
							; ~ Start a timer for SCP-173 breaking through the window
							e\EventState = e\EventState + 1.0
							Dist = EntityDistanceSquared(me\Collider, e\room\Objects[1])
							If Dist < 1.0 Then
								; ~ If close, increase the timer so that SCP-173 is ready to attack
								e\EventState = Max(e\EventState, 70.0 * 12.0)
							ElseIf Dist > 1.96
								; ~ If the player moves a bit further and blinks, SCP-173 attacks
								If e\EventState > 70.0 * 12.0 And (Not PlayerSees173(n_I\Curr173)) Then
									If EntityDistanceSquared(n_I\Curr173\Collider, e\room\Objects[0]) > 25.0 Then
										; ~ Remove event, if SCP-173 is far away from the room (perhaps because the player left and SCP-173 moved to some other room?) 
										RemoveEvent(e)
									Else
										PlaySound2(LoadTempSound("SFX\General\GlassBreak.ogg"), Camera, n_I\Curr173\OBJ) 
										FreeEntity(e\room\Objects[2]) : e\room\Objects[2] = 0
										PositionEntity(n_I\Curr173\Collider, EntityX(e\room\Objects[1], True), 0.5, EntityZ(e\room\Objects[1], True))
										ResetEntity(n_I\Curr173\Collider)
										RemoveEvent(e)
									EndIf
								EndIf
							EndIf
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_toilet_guard
				;[Block]
				If e\EventState = 0.0 Then
					If e\room\Dist < 8.0 And e\room\Dist > 0.0 Then e\EventState = 1.0
				ElseIf e\EventState = 1.0
					e\room\NPC[0] = CreateNPC(NPCTypeGuard, EntityX(e\room\Objects[1], True), EntityY(e\room\Objects[1], True) + 0.5, EntityZ(e\room\Objects[1], True))
					e\room\NPC[0]\State = 8.0 : e\room\NPC[0]\IsDead = True
					PointEntity(e\room\NPC[0]\Collider, e\room\OBJ)
					RotateEntity(e\room\NPC[0]\Collider, 0.0, EntityYaw(e\room\NPC[0]\Collider) - 20.0, 0.0, True)
					SetNPCFrame(e\room\NPC[0], 287.0)
					
					e\EventState = 2.0
				Else
					If (Not e\Sound) Then e\Sound = LoadSound_Strict("SFX\Character\Guard\SuicideGuard1.ogg")
					If e\room\Dist < 15.0 And e\room\Dist >= 4.0 Then 
						e\SoundCHN = LoopSound2(e\Sound, e\SoundCHN, Camera, e\room\NPC[0]\Collider, 15.0)
					ElseIf e\room\Dist < 4.0 And me\SndVolume > 1.0
						If e\EventState2 = 0.0 Then
							de.Decals = CreateDecal(DECAL_BLOOD_2, EntityX(e\room\Objects[2], True), EntityY(e\room\Objects[2], True), EntityZ(e\room\Objects[2], True), 0.0, e\room\Angle + 270.0, 0.0, 0.3)
							EntityParent(de\OBJ, e\room\OBJ)
							e\EventState2 = 1.0
						EndIf
						If (Not e\SoundCHN2) Then
							StopChannel(e\SoundCHN) : e\SoundCHN = 0
							If e\Sound <> 0 Then FreeSound_Strict(e\Sound) : e\Sound = 0
							e\room\NPC[0]\Sound = LoadSound_Strict("SFX\Character\Guard\SuicideGuard2.ogg")
							e\SoundCHN2 = PlaySound2(e\room\NPC[0]\Sound, Camera, e\room\NPC[0]\Collider, 15.0)
						EndIf
						UpdateSoundOrigin(e\SoundCHN2, Camera, e\room\NPC[0]\Collider, 15.0)
						If (Not ChannelPlaying(e\SoundCHN2)) Then RemoveEvent(e)
					EndIf
				EndIf
				;[End Block]
			Case e_cont2_008
				;[Block]
				If PlayerRoom = e\room Then
					If EntityY(me\Collider) < (-4496.0) * RoomScale Then
						GiveAchievement(Achv008)
						If e\EventState = 0.0 Then
							If n_I\Curr173\Idle < 2 And EntityDistanceSquared(n_I\Curr173\Collider, me\Collider) > PowTwo(HideDistance) ; ~ Just making sure that SCP-173 is far away enough to spawn him to this room
								PositionEntity(n_I\Curr173\Collider, EntityX(e\room\Objects[3], True), EntityY(e\room\Objects[3], True), EntityZ(e\room\Objects[3], True), True)
								ResetEntity(n_I\Curr173\Collider)
							EndIf
							e\EventState = 1.0
						ElseIf e\EventState = 1.0
							e\SoundCHN = LoopSound2(AlarmSFX[0], e\SoundCHN, Camera, e\room\Objects[0], 5.0)
							
							UpdateRedLight(e\room\Objects[5], 1000, 500)
							
							Dist = EntityDistanceSquared(me\Collider, e\room\Objects[0])
							If Dist < 4.0 Then
								For i = 0 To 1
									e\room\RoomDoors[i]\Locked = 1
								Next
								
								If e\EventState2 = 0.0 Then
									If EntityHidden(e\room\Objects[2]) Then ShowEntity(e\room\Objects[2])
									If EntityDistanceSquared(n_I\Curr173\Collider, e\room\Objects[4]) < 9.0 Then
										If (Not PlayerSees173(n_I\Curr173)) Then
											PositionEntity(n_I\Curr173\Collider, EntityX(e\room\Objects[4], True), EntityY(e\room\Objects[4], True), EntityZ(e\room\Objects[4], True), True)
											ResetEntity(n_I\Curr173\Collider)
											
											HideEntity(e\room\Objects[2])
											
											If wi\HazmatSuit = 0 Then
												InjurePlayer(0.3, 0.001, 500.0)
												CreateMsg(GetLocalString("msg", "008.173"))
											EndIf
											PlaySound2(LoadTempSound("SFX\General\GlassBreak.ogg"), Camera, e\room\Objects[0]) 
											
											e\EventState2 = 1.0
										EndIf
									EndIf
								EndIf
								
								If InteractObject(e\room\Objects[1], 0.81, True, 2, True) Then RotateEntity(e\room\Objects[1], Max(Min(EntityPitch(e\room\Objects[1]) + Max(Min(-mo\Mouse_Y_Speed_1, 10.0), -10.0), 89.0), 35.0), EntityYaw(e\room\Objects[1]), 0.0)
								If me\Bloodloss > 0.0 And I_008\Timer = 0.0 Then InjurePlayer(0.0, 0.001)
							EndIf
							
							If EntityPitch(e\room\Objects[1], True) < 40.0 Then
								PlaySound_Strict(LeverSFX)
								e\EventState = 2.0
							Else
								p.Particles = CreateParticle(PARTICLE_WHITE_SMOKE, EntityX(e\room\Objects[0], True), EntityY(e\room\Objects[0], True), EntityZ(e\room\Objects[0], True), 0.02, -0.12)
								p\SizeChange = 0.012 : p\AlphaChange = -0.015
								RotateEntity(p\Pvt, -90.0, 0.0, 0.0, True)
								TurnEntity(p\Pvt, Rnd(-26.0, 26.0), Rnd(-26.0, 26.0), Rnd(360.0))
							EndIf
						Else
							If (Not EntityHidden(e\room\Objects[5])) Then HideEntity(e\room\Objects[5])
							For i = 0 To 2
								e\room\RoomDoors[i]\Locked = 0
							Next
							RotateEntity(e\room\Objects[1], CurveAngle(1.0, EntityPitch(e\room\Objects[1], True), 15.0), EntityYaw(e\room\Objects[1], True), 0.0, True)
						EndIf
					EndIf
					e\EventState3 = UpdateElevators(e\EventState3, e\room\RoomDoors[3], e\room\RoomDoors[4], e\room\Objects[8], e\room\Objects[9], e)
				EndIf
				;[End Block]
			Case e_106_victim
				;[Block]
				If (Not n_I\Curr106\Contained) Then
					If PlayerRoom = e\room Then
						If e\EventState = 0.0 Then
							de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(e\room\OBJ), e\room\y + 798.0 * RoomScale, EntityZ(e\room\OBJ), -90.0, Rnd(360.0), 0.0, 0.5, 0.8)
							de\SizeChange = 0.0015
							EntityParent(de\OBJ, e\room\OBJ)
							PlaySound_Strict(DecaySFX[3])
							e\EventState = 1.0
						EndIf
					EndIf
					
					If e\EventState > 0.0 Then
						e\EventState = e\EventState + fps\Factor[0]
						If e\EventState > 200.0 Then
							If e\room\NPC[0] = Null Then
								e\room\NPC[0] = CreateNPC(NPCTypeD, EntityX(e\room\OBJ), 900.0 * RoomScale, EntityZ(e\room\OBJ))
								e\room\NPC[0]\State = -1.0 : e\room\NPC[0]\State3 = -1.0
								ChangeNPCTextureID(e\room\NPC[0], NPC_CLASS_D_MAYNARD_TEXTURE)
								RotateEntity(e\room\NPC[0]\Collider, 0.0, Rnd(360.0), 0.0, True)
								SetNPCFrame(e\room\NPC[0], 1.0)
								
								PlaySound_Strict(HorrorSFX[0])
								PlaySound_Strict(DecaySFX[2])
							EndIf
							
							UpdateSoundOrigin(e\SoundCHN, Camera, e\room\OBJ)
							
							e\room\NPC[0]\FallingPickDistance = 0.0
							If EntityY(e\room\NPC[0]\Collider) > 0.35 Then
								EntityType(e\room\NPC[0]\Collider, HIT_PLAYER)
								AnimateNPC(e\room\NPC[0], 1.0, 10.0, 0.12, False)
								Dist = EntityDistanceSquared(me\Collider, e\room\NPC[0]\Collider)
								If Dist < 0.64 Then ; ~ Get the player out of the way
									Dist = Sqr(Dist)
									fDir = PointDirection(EntityX(me\Collider, True), EntityZ(me\Collider, True), EntityX(e\room\NPC[0]\Collider, True), EntityZ(e\room\NPC[0]\Collider, True))
									TranslateEntity(me\Collider, Cos(-fDir + 90.0) * (Dist - 0.8) * (Dist - 0.8), 0.0, Sin(-fDir + 90.0) * (Dist - 0.8) * (Dist - 0.8))
								EndIf
								If EntityY(e\room\NPC[0]\Collider) > 0.6 Then EntityType(e\room\NPC[0]\Collider, 0)
							Else
								e\room\NPC[0]\IsDead = True
								AnimateNPC(e\room\NPC[0], 11.0, 19.0, 0.25, False)
								If (Not e\Sound) Then
									LoadEventSound(e, "SFX\General\BodyFall.ogg")
									PlaySound2(e\Sound, Camera, e\room\NPC[0]\Collider)
									
									de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(e\room\OBJ), e\room\y + 0.005, EntityZ(e\room\OBJ), 90.0, Rnd(360.0), 0.0, 0.4, 0.8)
									EntityParent(de\OBJ, e\room\OBJ)
								EndIf
								If e\room\NPC[0]\Frame >= 18.9 Then RemoveEvent(e)
							EndIf
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_106_sinkhole
				;[Block]
				If e\EventState = 0.0 Then
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(e\room\OBJ) + Rnd(-0.5, 0.5), e\room\y + 0.005, EntityZ(e\room\OBJ) + Rnd(-0.5, 0.5), 90.0, Rnd(360.0), 0.0, 2.5)
					EntityParent(de\OBJ, e\room\OBJ)
					e\EventState = 1.0
				ElseIf PlayerRoom = e\room
					If (Not e\Sound) Then e\Sound = LoadSound_Strict("SFX\Room\Sinkhole.ogg")
					e\SoundCHN = LoopSound2(e\Sound, e\SoundCHN, Camera, e\room\OBJ, 4.5, 1.5)
					
					Dist = DistanceSquared(EntityX(me\Collider), EntityX(e\room\OBJ), EntityZ(me\Collider), EntityZ(e\room\OBJ))
					If Dist < 0.25 Then
						If e\EventState2 = 0.0 Then PlaySound_Strict(LoadTempSound("SFX\Room\SinkholeFall.ogg"))
						
						me\Playable = False
						
						x = CurveValue(EntityX(e\room\OBJ), EntityX(me\Collider), 10.0)
						y = CurveValue(EntityY(e\room\OBJ) - e\EventState2, EntityY(me\Collider), 25.0)
						z = CurveValue(EntityZ(e\room\OBJ), EntityZ(me\Collider), 10.0)
						PositionEntity(me\Collider, x, y, z, True)
						
						me\DropSpeed = 0.0
						
						ResetEntity(me\Collider)
						
						e\EventState2 = Min(e\EventState2 + fps\Factor[0] / 200.0, 2.0)
						
						me\LightBlink = Min(e\EventState2 * 5.0, 10.0)
						If wi\NightVision > 0 Then
							If e\EventState2 >= 0.2 Then me\BlinkTimer = -10.0
						EndIf
						me\BlurTimer = e\EventState2 * 500.0
						
						If e\EventState2 = 2.0 Then
							me\Playable = True
							MoveToPocketDimension()
						EndIf
					Else
						If chs\NoClip Then me\Playable = True
					EndIf
				Else
					e\EventState2 = 0.0
				EndIf
				;[End Block]
			Case e_682_roar
				;[Block]
				If e\EventState = 0.0 Then
					If PlayerRoom = e\room Then e\EventState = 70.0 * Rnd(50.0, 100.0)
				ElseIf PlayerInReachableRoom()
					e\EventState = e\EventState - fps\Factor[0]
					
					If e\EventState < 70.0 * 17.0 Then
						If e\EventState + fps\Factor[0] >= 70.0 * 17.0 Then LoadEventSound(e, "SFX\SCP\682\Roar.ogg") : e\SoundCHN = PlaySound_Strict(e\Sound)
						If e\EventState > (70.0 * 17.0) - (70.0 * 3.0) Then me\BigCameraShake = 0.5
						If e\EventState < (70.0 * 17.0) - (70.0 * 7.5) And e\EventState > (70.0 * 17.0) - (70.0 * 11.0) Then me\BigCameraShake = 2.0
						If e\EventState < 70.0 * 1.0 Then RemoveEvent(e)
					EndIf
				EndIf
				;[End Block]
			Case e_cont1_914
				;[Block]
				If PlayerRoom = e\room Then
					If e\room\RoomDoors[2]\Open Lor e\room\RoomDoors[3]\Open Then
						GiveAchievement(Achv914)
						e\EventState2 = 1.0
					EndIf
					
					If e\EventState2 = 1.0 Then ShouldPlay = 22
					EntityPick(Camera, 1.0)
					If PickedEntity() = e\room\Objects[0] Then
						ga\DrawHandIcon = True
						If mo\MouseHit1 Then GrabbedEntity = e\room\Objects[0]
					ElseIf PickedEntity() = e\room\Objects[1]
						ga\DrawHandIcon = True
						If mo\MouseHit1 Then GrabbedEntity = e\room\Objects[1]
					EndIf
					
					If mo\MouseDown1 Lor mo\MouseHit1 Then
						If GrabbedEntity <> 0 Then ; ~ Avain
							If GrabbedEntity = e\room\Objects[0] Then
								If e\EventState = 0.0 Then
									ga\DrawHandIcon = True
									TurnEntity(GrabbedEntity, 0.0, 0.0, -mo\Mouse_X_Speed_1 * 2.5)
									
									Angle = WrapAngle(EntityRoll(e\room\Objects[0]))
									If Angle > 181.0 Then ga\DrawArrowIcon[3] = True
									ga\DrawArrowIcon[1] = True
									
									If Angle < 90.0 Then
										RotateEntity(GrabbedEntity, 0.0, 0.0, 361.0)
									ElseIf Angle < 180.0
										RotateEntity(GrabbedEntity, 0.0, 0.0, 180)
									EndIf
									
									If Angle < 181.0 And Angle > 90.0 Then
										For it.Items = Each Items
											If it\Collider <> 0 And (Not it\Picked) Then
												If Abs(EntityX(it\Collider) - (e\room\x - 712.0 * RoomScale)) < 200.0 Then
													If Abs(EntityY(it\Collider) - (e\room\y + 648.0 * RoomScale)) < 104.0 Then
														e\EventState = 1.0
														e\SoundCHN = PlaySound2(MachineSFX, Camera, e\room\Objects[1])
														e\room\RoomDoors[1]\SoundCHN = PlaySound2(LoadTempSound("SFX\SCP\914\DoorClose.ogg"), Camera, e\room\RoomDoors[1]\OBJ)
														Exit
													EndIf
												EndIf
											EndIf
										Next
									EndIf
								EndIf
							ElseIf GrabbedEntity = e\room\Objects[1]
								If e\EventState = 0.0 Then
									ga\DrawHandIcon = True
									TurnEntity(GrabbedEntity, 0.0, 0.0, -mo\Mouse_X_Speed_1 * 2.5)
									
									Angle = WrapAngle(EntityRoll(e\room\Objects[1]))
									ga\DrawArrowIcon[3] = True
									ga\DrawArrowIcon[1] = True
									
									If Angle > 90.0 Then
										If Angle < 180.0 Then
											RotateEntity(GrabbedEntity, 0.0, 0.0, 90.0)
										ElseIf Angle < 270.0
											RotateEntity(GrabbedEntity, 0.0, 0.0, 270.0)
										EndIf
									EndIf
									
									Local Rotation# = Floor(EntityRoll(e\room\Objects[1]))
									
									e\SoundCHN2 = 0
									If (Rotation > -94.0 And Rotation < -86.0) Lor (Rotation > -44.0 And Rotation < -36.0) Lor (Rotation > -4.0 And Rotation < 4.0) Lor (Rotation > 36.0 And Rotation < 44.0) Lor (Rotation > 86.0 And Rotation < 94.0) Then
										If (Not e\SoundCHN) Then e\SoundCHN = PlaySound2(KnobSFX[Rand(0, 1)], Camera, e\room\Objects[1], 2.0, 0.5)
									Else
										e\SoundCHN = 0
									EndIf
								EndIf
							EndIf
						EndIf
					Else
						GrabbedEntity = 0
					EndIf
					
					Local Setting%
					
					If GrabbedEntity <> e\room\Objects[1] Then
						Angle = WrapAngle(EntityRoll(e\room\Objects[1]))
						If Angle < 22.5 Then
							Angle = 0.0
							Setting = ONETOONE
						ElseIf Angle < 67.5
							Angle = 40.0
							Setting = COARSE
						ElseIf Angle < 180.0
							Angle = 90.0
							Setting = ROUGH
						ElseIf Angle > 337.5
							Angle = -1.0
							Setting = ONETOONE
						ElseIf Angle > 292.5
							Angle = -40.0
							Setting = FINE
						Else
							Angle = -90.0
							Setting = VERYFINE
						EndIf
						RotateEntity(e\room\Objects[1], 0.0, 0.0, CurveValue(Angle, EntityRoll(e\room\Objects[1]), 20.0))
						If Angle = -90.0 Lor Angle = -40.0 Lor Angle = -1.0 Lor Angle = 0.0 Lor Angle = 40.0 Lor Angle = 90.0 Then
							If (Not e\SoundCHN2) Then e\SoundCHN2 = PlaySound2(KnobSFX[Rand(0, 1)], Camera, e\room\Objects[1], 2.0, 0.5)
						EndIf
					EndIf
					
					For i = 0 To 1
						If GrabbedEntity = e\room\Objects[i] Then
							If (Not EntityInView(e\room\Objects[i], Camera)) Then
								GrabbedEntity = 0
							ElseIf EntityDistanceSquared(e\room\Objects[i], Camera) > 1.0
								GrabbedEntity = 0
							EndIf
							Exit
						EndIf
					Next
					
					If e\EventState > 0.0 Then
						e\EventState = e\EventState + fps\Factor[0]
						CanSave = 0
						e\room\RoomDoors[1]\Open = False
						If e\EventState > 70.0 * 2.0 Then
							If n_I\Curr106\State > 1.0 Then n_I\Curr106\State = n_I\Curr106\State - (fps\Factor[0] * (0.25 + (0.25 * SelectedDifficulty\AggressiveNPCs)))
							If e\room\RoomDoors[0]\Open Then e\room\RoomDoors[0]\SoundCHN = PlaySound2(LoadTempSound("SFX\SCP\914\DoorClose.ogg"), Camera, e\room\RoomDoors[0]\OBJ)
							e\room\RoomDoors[0]\Open = False
						EndIf
						
						If DistanceSquared(EntityX(me\Collider), EntityX(e\room\Objects[2], True), EntityZ(me\Collider), EntityZ(e\room\Objects[2], True)) < PowTwo(170.0 * RoomScale) Then
							If Setting = ROUGH Lor Setting = COARSE Then
								If e\EventState > 70.0 * 2.6 And e\EventState - fps\Factor[1] < 70.0 * 2.6 Then PlaySound_Strict(Death914SFX)
							EndIf
							
							If e\EventState > 70.0 * 3.0 Then
								Select Setting
									Case ROUGH
										;[Block]
										me\Terminated = True
										me\BlinkTimer = -10.0
										If ChannelPlaying(e\SoundCHN) Then StopChannel(e\SoundCHN) : e\SoundCHN = 0
										msg\DeathMsg = Format(GetLocalString("death", "914"), SubjectName)
										;[End Block]
									Case COARSE
										;[Block]
										me\BlinkTimer = -10.0
										If e\EventState - fps\Factor[1] < 70.0 * 3.0 Then PlaySound_Strict(Use914SFX)
										;[End Block]
									Case ONETOONE
										;[Block]
										me\BlinkTimer = -10.0
										If e\EventState - fps\Factor[1] < 70.0 * 3.0 Then PlaySound_Strict(Use914SFX)
										;[End Block]
									Case FINE, VERYFINE
										;[Block]
										me\BlinkTimer = -10.0
										If e\EventState - fps\Factor[1] < 70.0 * 3.0 Then PlaySound_Strict(Use914SFX)
										;[End Block]
								End Select
							EndIf
						EndIf
						
						If e\EventState > 70.0 * 6.0 Then
							RotateEntity(e\room\Objects[0], EntityPitch(e\room\Objects[0]), EntityYaw(e\room\Objects[0]), CurveAngle(0.0, EntityRoll(e\room\Objects[0]), 10.0))
						Else
							RotateEntity(e\room\Objects[0], EntityPitch(e\room\Objects[0]), EntityYaw(e\room\Objects[0]), 180.0)
						EndIf
						
						If e\EventState > 70.0 * 12.0 Then
							For it.Items = Each Items
								If it\Collider <> 0 And (Not it\Picked) Then
									If DistanceSquared(EntityX(it\Collider), EntityX(e\room\Objects[2], True), EntityZ(it\Collider), EntityZ(e\room\Objects[2], True)) < PowTwo(180.0 * RoomScale) Then Use914(it, Setting, EntityX(e\room\Objects[3], True), EntityY(e\room\Objects[3], True), EntityZ(e\room\Objects[3], True))
								EndIf
							Next
							
							If DistanceSquared(EntityX(me\Collider), EntityX(e\room\Objects[2], True), EntityZ(me\Collider), EntityZ(e\room\Objects[2], True)) < PowTwo(160.0 * RoomScale) Then
								Select Setting
									Case COARSE
										;[Block]
										me\Injuries = 4.0
										CreateMsg(GetLocalString("msg", "914"))
										;[End Block]
									Case ONETOONE
										;[Block]
										opt\InvertMouseX = (Not opt\InvertMouseX)
										opt\InvertMouseY = (Not opt\InvertMouseY)
										;[End Block]
									Case FINE, VERYFINE
										;[Block]
										chs\SuperMan = True
										;[End Block]
								End Select
								me\BlurTimer = 1000.0
								PositionEntity(me\Collider, EntityX(e\room\Objects[3], True), EntityY(e\room\Objects[3], True) + 1.0, EntityZ(e\room\Objects[3], True))
								ResetEntity(me\Collider)
								me\DropSpeed = 0.0
							EndIf
							e\room\RoomDoors[0]\Open = True
							e\room\RoomDoors[1]\Open = True
							RotateEntity(e\room\Objects[0], 0.0, 0.0, 0.0)
							e\EventState = 0.0
							
							Local OpenSFX914% = LoadTempSound("SFX\SCP\914\DoorOpen.ogg")
							
							e\room\RoomDoors[0]\SoundCHN = PlaySound2(OpenSFX914, Camera, e\room\RoomDoors[0]\OBJ)
							e\room\RoomDoors[1]\SoundCHN = PlaySound2(OpenSFX914, Camera, e\room\RoomDoors[1]\OBJ)
						EndIf
					EndIf
				EndIf
				UpdateSoundOrigin(e\SoundCHN, Camera, e\room\Objects[1])
				;[End Block]
			Case e_1048_a
				;[Block]
				If (Not e\room\Objects[0]) Then
					If PlayerRoom <> e\room Then
						If DistanceSquared(EntityX(me\Collider), EntityX(e\room\OBJ), EntityZ(me\Collider), EntityZ(e\room\OBJ)) < 256.0 Then
							e\room\Objects[0] =	CopyEntity(n_I\NPCModelID[NPC_1048_A_MODEL])
							ScaleEntity(e\room\Objects[0], 0.05, 0.05, 0.05)
							SetAnimTime(e\room\Objects[0], 2)
							PositionEntity(e\room\Objects[0], EntityX(e\room\OBJ), 0.0, EntityZ(e\room\OBJ))
							
							RotateEntity(e\room\Objects[0], -90.0, Rnd(0.0, 360.0), 0.0)
							
							e\Sound = LoadSound_Strict("SFX\SCP\1048A\Shriek.ogg")
							e\Sound2 = LoadSound_Strict("SFX\SCP\1048A\Growth.ogg")
							
							e\EventState = 1.0
						EndIf
					EndIf
				Else
					e\EventState3 = e\EventState3 + fps\Factor[0]
					If chs\NoTarget Then e\EventState = 1.0
					
					Select e\EventState
						Case 1.0
							;[Block]
							Animate2(e\room\Objects[0], AnimTime(e\room\Objects[0]), 2.0, 395.0, 1.0)
							
							If EntityDistanceSquared(me\Collider, e\room\Objects[0]) < 6.25 And (Not chs\NoTarget) Then e\EventState = 2.0
							;[End Block]
						Case 2.0
							;[Block]
							Local PrevFrame# = AnimTime(e\room\Objects[0])
							
							Animate2(e\room\Objects[0], PrevFrame, 2.0, 647.0, 1.0, False)
							
							If PrevFrame <= 400.0 And AnimTime(e\room\Objects[0]) > 400.0 Then e\SoundCHN = PlaySound_Strict(e\Sound)
							
							UpdateSoundOrigin(e\SoundCHN, Camera, e\room\Objects[0], 6.0)
							
							Local Volume# = Max(1.0 - Abs(PrevFrame - 600.0) / 100.0, 0.0)
							
							If PlayerRoom = e\room Then
								me\BlurTimer = Volume * 1000.0
								me\CameraShake = Volume * 10.0
							EndIf
							
							PointEntity(e\room\Objects[0], me\Collider)
							RotateEntity(e\room\Objects[0], -90.0, EntityYaw(e\room\Objects[0]), 0.0)
							
							If PrevFrame > 646.0 Then
								If PlayerRoom = e\room Then
									PlaySound_Strict(e\Sound2)
									
									CreateMsg(GetLocalString("msg", "1048a_1"))
									e\EventState = 3.0
								Else
									e\EventState3 = 70.0 * 30.0
									e\EventState = 4.0
								EndIf
							EndIf
							;[End Block]
						Case 3.0
							;[Block]
							If (Not I_427\Using) And I_427\Timer < 70.0 * 360.0 Then e\EventState2 = e\EventState2 + fps\Factor[0]
							
							me\BlurTimer = e\EventState2 * 2.0
							
							If (Not I_427\Using) And I_427\Timer < 70.0 * 360.0 Then
								If e\EventState2 > 250.0 And e\EventState2 - fps\Factor[0] <= 250.0 Then
									Select Rand(3)
										Case 1
											;[Block]
											CreateMsg(GetLocalString("msg", "1048a_2"))
											;[End Block]
										Case 2
											;[Block]
											CreateMsg(GetLocalString("msg", "1048a_3"))
											;[End Block]
										Case 3
											;[Block]
											CreateMsg(GetLocalString("msg", "1048a_4"))
											;[End Block]
									End Select
								ElseIf e\EventState2 > 600.0 And e\EventState2 - fps\Factor[0] <= 600.0
									Select Rand(4)
										Case 1
											;[Block]
											CreateMsg(GetLocalString("msg", "1048a_5"))
											;[End Block]
										Case 2
											;[Block]
											CreateMsg(GetLocalString("msg", "1048a_6"))
											;[End Block]
										Case 3
											;[Block]
											CreateMsg(GetLocalString("msg", "1048a_7"))
											;[End Block]
										Case 4
											;[Block]
											CreateMsg(GetLocalString("msg", "1048a_8"))
											;[End Block]
									End Select
								EndIf
							EndIf
							
							If e\EventState2 > 70.0 * 15.0 Then
								msg\DeathMsg = GetLocalString("death", "1048a")
								Kill()
							EndIf
							
							; ~ Remove event when player was cured by SCP-427
							If e\EventState2 = 0.0 And I_427\Using Then e\EventState3 = 70.0 * 30.0
							;[End Block]
						Case 4.0
							;[Block]
							Animate2(e\room\Objects[0], AnimTime(e\room\Objects[0]), 2.0, 395.0, 1.0)
							
							PointEntity(e\room\Objects[0], me\Collider)
							RotateEntity(e\room\Objects[0], -90.0, EntityYaw(e\room\Objects[0]), 0.0)
							;[End Block]
					End Select
					
					If e <> Null Then
						If e\EventState3 > 0.0 Then
							If e\EventState3 >= 70.0 * 30.0 Then
								If (Not EntityInView(e\room\Objects[0], Camera)) Then
									If e\room\Objects[0] <> 0 Then FreeEntity(e\room\Objects[0]) : e\room\Objects[0] = 0
									If e\EventState2 = 0.0 Then RemoveEvent(e)
								EndIf
							EndIf
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_room4_2_hcz
				;[Block]
				TFormPoint(256.0, 0.0, 256.0, e\room\OBJ, 0)
				e\room\NPC[0] = CreateNPC(NPCTypeD, TFormedX(), 0.5, TFormedZ())
				e\room\NPC[0]\State3 = -1.0 : e\room\NPC[0]\IsDead = True
				ChangeNPCTextureID(e\room\NPC[0], NPC_CLASS_D_BODY_2_TEXTURE)
				SetNPCFrame(e\room\NPC[0], 19.0)
				RotateEntity(e\room\NPC[0]\Collider, 0.0, e\room\Angle - 30.0, 0.0)
				RemoveEvent(e)
				;[End Block]
			Case e_room2_gw_2
				;[Block]
				If e\room\Dist < 8.0 Then
					If e\room\NPC[0] = Null Then
						e\room\NPC[0] = CreateNPC(NPCTypeGuard, EntityX(e\room\Objects[1], True), EntityY(e\room\Objects[1], True), EntityZ(e\room\Objects[1], True))
						e\room\NPC[0]\State = 8.0 : e\room\NPC[0]\IsDead = True
						SetNPCFrame(e\room\NPC[0], 288.0)
						PointEntity(e\room\NPC[0]\Collider, e\room\OBJ)
						RotateEntity(e\room\NPC[0]\Collider, 0.0, EntityYaw(e\room\NPC[0]\Collider), 0.0, True)
					EndIf
					
					p.Particles = CreateParticle(PARTICLE_WHITE_SMOKE, EntityX(e\room\Objects[0], True), EntityY(e\room\Objects[0], True), EntityZ(e\room\Objects[0], True), 0.2, 0.0, 10.0)
					p\Speed = 0.01 : p\AlphaChange = -0.02
					RotateEntity(p\Pvt, -60.0, e\room\Angle - 90.0, 0.0)
					
					e\SoundCHN = LoopSound2(AlarmSFX[2], e\SoundCHN, Camera, e\room\Objects[1], 5.0)
				EndIf
				;[End Block]
			Case e_gateway
				;[Block]
				; ~ e\EventState: Determines if the airlock is in operation or not
				
				; ~ e\EventState2: The timer for the airlocks
				
				; ~ e\EventState3: Checks if the player had left the airlock or not
				
				Local BrokenDoor% = False
				
				If e\room\Objects[1] <> 0 Then BrokenDoor = True
				
				If PlayerRoom = e\room Then
					If e\EventState = 0.0 Then
						If EntityDistanceSquared(e\room\Objects[0], me\Collider) < 0.64 And e\EventState3 = 0.0
							If BrokenDoor Then
								LoadEventSound(e, "SFX\Door\DoorSparks.ogg", 1)
								e\SoundCHN2 = PlaySound2(e\Sound2, Camera, e\room\Objects[1], 5.0)
							EndIf
							StopChannel(e\SoundCHN) : e\SoundCHN = 0
							LoadEventSound(e, "SFX\Door\Airlock.ogg")
							For i = 0 To 1
								OpenCloseDoor(e\room\RoomDoors[i])
							Next
							PlaySound_Strict(AlarmSFX[3])
							e\EventState = 1.0
						ElseIf EntityDistanceSquared(e\room\Objects[0], me\Collider) > 5.76
							e\EventState3 = 0.0
						EndIf
					Else
						If e\EventState2 < 70.0 * 7.0 Then
							e\EventState2 = e\EventState2 + fps\Factor[0]
							e\room\RoomDoors[0]\Open = False
							e\room\RoomDoors[1]\Open = False
							If e\EventState2 < 70.0 * 1.0 Then
								If BrokenDoor Then
									Pvt = CreatePivot()
									
									Local d_Ent% = e\room\Objects[1]
									
									PositionEntity(Pvt, EntityX(d_Ent, True), EntityY(d_Ent, True) + Rnd(0.0, 0.05), EntityZ(d_Ent, True))
									RotateEntity(Pvt, 0.0, EntityYaw(d_Ent, True) + 90.0, 0.0)
									MoveEntity(Pvt, 0.0, 0.0, 0.2)
									
									If opt\ParticleAmount > 0 Then
										For i = 0 To (1 + (2 * (opt\ParticleAmount - 1)))
											p.Particles = CreateParticle(PARTICLE_SPARK, EntityX(Pvt), EntityY(Pvt), EntityZ(Pvt), 0.002, 0.0, 25.0)
											p\Speed = Rnd(0.01, 0.05) : p\Size = 0.0075 : p\AlphaChange = -0.05
											RotateEntity(p\Pvt, Rnd(-45.0, 0.0), EntityYaw(Pvt) + Rnd(-10.0, 10.0), 0.0)
											ScaleSprite(p\OBJ, p\Size, p\Size)
										Next
									EndIf
									FreeEntity(Pvt)
								EndIf
							ElseIf e\EventState2 > 70.0 * 3.0 And e\EventState2 < 70.0 * 6.0
								Pvt = CreatePivot(e\room\OBJ)
								For i = 0 To 1
									If e\room\RoomTemplate\Name = "room3_gw"
										If i = 0 Then
											PositionEntity(Pvt, -81.0, 416.0, 320.0)
										Else
											PositionEntity(Pvt, 143.0, 416.0, 320.0)
										EndIf
									Else
										If i = 0 Then
											PositionEntity(Pvt, 320.0, 416.0, -81.0)
										Else
											PositionEntity(Pvt, 320.0, 416.0, 143.0)
										EndIf
									EndIf
									p.Particles = CreateParticle(PARTICLE_WHITE_SMOKE, EntityX(Pvt, True), EntityY(Pvt, True), EntityZ(Pvt, True), 0.8, 0.0, 50.0)
									p\Speed = 0.025 : p\AlphaChange = -0.02
									RotateEntity(p\Pvt, 90.0, 0.0, 0.0)
								Next
								FreeEntity(Pvt)
								If (Not ChannelPlaying(e\SoundCHN)) Then e\SoundCHN = PlaySound2(e\Sound, Camera, e\room\Objects[0], 5.0)
							EndIf
						Else
							e\EventState = 0.0
							e\EventState2 = 0.0
							e\EventState3 = 1.0
							For i = 0 To 1
								If (Not e\room\RoomDoors[i]\Open) Then OpenCloseDoor(e\room\RoomDoors[i])
							Next
						EndIf
					EndIf
					
					If BrokenDoor Then UpdateSoundOrigin(e\SoundCHN2, Camera, e\room\Objects[1], 5.0)
					UpdateSoundOrigin(e\SoundCHN, Camera, e\room\Objects[0], 5.0)
				Else
					e\EventState3 = 0.0
				EndIf
				;[End Block]
			Case e_cont2_500_1499
				;[Block]
				If e\room\Dist < 15.0 Then
					If n_I\Curr106\Contained Lor n_I\Curr106\State =< 0.0 Then e\EventState = 2.0
					
					If e\EventState < 2.0 Then
						If e\EventState = 0.0 Then
							LoadEventSound(e, "SFX\Character\Scientist\EmilyScream.ogg")
							e\SoundCHN = PlaySound2(e\Sound, Camera, e\room\Objects[0], 100.0, 1.0)
							de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(e\room\Objects[0], True), e\room\y + 0.005, EntityZ(e\room\Objects[0], True), 90.0, Rnd(360.0), 0.0, 0.8, 0.8)
							EntityParent(de\OBJ, e\room\OBJ)
							e\EventState = 1.0
						ElseIf e\EventState = 1.0
							If (Not ChannelPlaying(e\SoundCHN)) Then
								e\EventState = 2.0
								e\room\RoomDoors[0]\Locked = 0
							Else
								UpdateSoundOrigin(e\SoundCHN, Camera, e\room\Objects[0], 100.0, 1.0)
							EndIf
						EndIf
					Else
						e\room\RoomDoors[0]\Locked = 0
						RemoveEvent(e)
					EndIf
				EndIf
				;[End Block]
			Case e_cont2c_1162_arc
				;[Block]
				; ~ e\EventState: A variable to determine the "nostalgia" items
				; ~ 0.0 = No nostalgia item
				; ~ 1.0 = Lost key
				; ~ 2.0 = Disciplinary Hearing DH-S-4137-17092
				; ~ 3.0 = Coin
				; ~ 4.0 = Movie Ticket
				; ~ 5.0 = Old Badge
				
				; ~ e\EventState2: Defining which slot from the Inventory should be picked
				
				; ~ e\EventState3: A check for if a item should be removed
				; ~ 0.0 = no item "trade" will happen
				; ~ 1.0 = item "trade" will happen
				; ~ 2.0 = the player doesn't has any items in the Inventory, giving him heavily injuries and giving him a random item
				; ~ 3.0 = player got a memorial item (to explain a bit D-9341's background)
				; ~ 3.1 = player got a memorial item + injuries (because he didn't had any item in his inventory before)
				
				If PlayerRoom = e\room Then
					GrabbedEntity = 0
					
					e\EventState = 0.0
					
					Local Pick1162ARC% = True
					Local pp% = CreatePivot(e\room\OBJ)
					
					PositionEntity(pp, 976.0, 128.0, -640.0)
					
					For it.Items = Each Items
						If (Not it\Picked) Then
							If EntityDistanceSquared(it\Collider, e\room\Objects[0]) < 0.5625 Then Pick1162ARC = False
						EndIf
					Next
					
					If Pick1162ARC Then
						If InteractObject(e\room\Objects[0], 0.5625) Then
							e\EventState2 = Rand(0, MaxItemAmount - 1)
							If Inventory(e\EventState2) <> Null Then
								; ~ Randomly picked item slot has an item in it, using this slot
								e\EventState3 = 1.0
							Else
								; ~ Randomly picked item slot is empty, getting the first available slot
								For i = 0 To MaxItemAmount - 1
									Local IsSlotEmpty% = (Inventory((i + e\EventState2) Mod MaxItemAmount) = Null)
									
									; ~ Successful
									If (Not IsSlotEmpty) Then e\EventState2 = (i + e\EventState2) Mod MaxItemAmount
									
									If Rand(8) = 1 Then
										If IsSlotEmpty Then
											e\EventState3 = 3.1
										Else
											e\EventState3 = 3.0
										EndIf
										
										e\EventState = Rand(5)
										
										; ~ Checking if the selected nostalgia item already exists or not
										Local ItemName$ = ""
										
										Select e\EventState
											Case 1.0
												;[Block]
												ItemName = "Lost Key"
												;[End Block]
											Case 2.0
												;[Block]
												ItemName = "Disciplinary Hearing DH-S-4137-17092"
												;[End Block]
											Case 3.0
												;[Block]
												ItemName = "Coin"
												;[End Block]
											Case 4.0
												;[Block]
												ItemName = "Movie Ticket"
												;[End Block]
											Case 5.0
												;[Block]
												ItemName = "Old Badge"
												;[End Block]
										End Select
										
										For it.Items = Each Items
											If it\Name = ItemName Then
												e\EventState3 = 1.0
												e\EventState = 0.0
												Exit
											EndIf
										Next
										If (Not IsSlotEmpty) Then Exit
									Else
										If IsSlotEmpty Then
											e\EventState3 = 2.0
										Else
											e\EventState3 = 1.0
											Exit
										EndIf
									EndIf
								Next
							EndIf
						EndIf
						
						; ~ Trade successful
						If e\EventState3 = 1.0 Then
							Local ShouldCreateItem% = False
							
							For itt.ItemTemplates = Each ItemTemplates
								If IsItemGoodFor1162ARC(itt) Then
									Select Inventory(e\EventState2)\ItemTemplate\TempName
										Case "key"
											;[Block]
											If itt\TempName = "key0" Lor itt\TempName = "key1" And Rand(2) = 1 Then ShouldCreateItem = True
											;[End Block]
										Case "paper", "oldpaper"
											;[Block]
											If itt\TempName = "paper" And Rand(12) = 1 Then ShouldCreateItem = True
											;[End Block]
										Case "gasmask", "finegasmask", "veryfinegasmask", "gasmask148", "hazmatsuit", "finehazmatsuit", "veryfinehazmatsuit", "hazmatsuit148"
											;[Block]
											If itt\TempName = "gasmask" Lor itt\TempName = "finegasmask" Lor itt\TempName = "veryfinegasmask" Lor itt\TempName = "gasmask148" Lor itt\TempName = "hazmatsuit" Lor itt\TempName = "finehazmatsuit" Lor itt\TempName = "veryfinehazmatsuit" Lor itt\TempName = "hazmatsuit148" And Rand(2) = 1 Then ShouldCreateItem = True
											;[End Block]
										Case "key0", "key1", "key2", "key3"
											;[Block]
											If itt\TempName = "key0" Lor itt\TempName = "key1" Lor itt\TempName = "key2" Lor itt\TempName = "key3" And Rand(6) = 1 Then ShouldCreateItem = True
											;[End Block]
										Case "mastercard", "playcard", "origami", "electronics"
											;[Block]
											If itt\TempName = "mastercard" Lor itt\TempName = "playcard" Lor itt\TempName = "origami" Lor itt\TempName = "electronics" And Rand(5) = 1 Then ShouldCreateItem = True
											;[End Block]
										Case "vest", "finevest"
											;[Block]
											If itt\TempName = "vest" Lor itt\TempName = "finevest" And Rand(1) = 1 Then ShouldCreateItem = True
											;[End Block]
										Default
											;[Block]
											If itt\TempName = "mastercard" Lor itt\TempName = "playcard" And Rand(6) = 1 Then ShouldCreateItem = True
											;[End Block]
									End Select
								EndIf
								
								If ShouldCreateItem Then
									RemoveWearableItems(Inventory(e\EventState2))
									RemoveItem(Inventory(e\EventState2))
									
									it.Items = CreateItem(itt\Name, itt\TempName, EntityX(pp, True), EntityY(pp, True), EntityZ(pp, True))
									EntityType(it\Collider, HIT_ITEM)
									
									PlaySound_Strict(LoadTempSound("SFX\SCP\1162_ARC\Exchange" + Rand(0, 4) + ".ogg"))
									e\EventState3 = 0.0
									
									GiveAchievement(Achv1162_ARC)
									mo\MouseHit1 = False
									Exit
								EndIf
							Next
						; ~ Trade not sucessful (player got in return to injuries a new item)
						ElseIf e\EventState3 = 2.0
							InjurePlayer(5.0)
							Pvt = CreatePivot()
							PositionEntity(Pvt, EntityX(me\Collider), EntityY(me\Collider) - 0.05, EntityZ(me\Collider))
							TurnEntity(Pvt, 90.0, 0.0, 0.0)
							EntityPick(Pvt, 0.3)
							de.Decals = CreateDecal(DECAL_BLOOD_2, PickedX(), PickedY() + 0.005, PickedZ(), 90.0, Rnd(360.0), 0.0, 0.75)
							EntityParent(de\OBJ, e\room\OBJ)
							FreeEntity(Pvt)
							For itt.ItemTemplates = Each ItemTemplates
								If IsItemGoodFor1162ARC(itt) And Rand(6) = 1 Then
									it.Items = CreateItem(itt\Name, itt\TempName, EntityX(pp, True), EntityY(pp, True), EntityZ(pp, True))
									EntityType(it\Collider, HIT_ITEM)
									
									GiveAchievement(Achv1162_ARC)
									mo\MouseHit1 = False
									e\EventState3 = 0.0
									If me\Injuries > 15.0
										msg\DeathMsg = GetLocalString("death", "1162")
										PlaySound_Strict(LoadTempSound("SFX\SCP\1162_ARC\BodyHorrorExchange" + Rand(4) + ".ogg"))
										me\LightFlash = 5.0
										Kill(True)
									Else
										PlaySound_Strict(LoadTempSound("SFX\SCP\1162_ARC\BodyHorrorExchange" + Rand(4) + ".ogg"))
										me\LightFlash = 5.0
										CreateMsg(GetLocalString("msg", "1162_1"))
									EndIf
									Exit
								EndIf
							Next
						; ~ Trade with nostalgia item
						ElseIf e\EventState3 >= 3.0
							If e\EventState3 < 3.1
								PlaySound_Strict(LoadTempSound("SFX\SCP\1162_ARC\Exchange" + Rand(0, 4) + ".ogg"))
								RemoveWearableItems(Inventory(e\EventState2))
								RemoveItem(Inventory(e\EventState2))
							Else
								InjurePlayer(5.0)
								Pvt = CreatePivot()
								PositionEntity(Pvt, EntityX(me\Collider), EntityY(me\Collider) - 0.05, EntityZ(me\Collider))
								TurnEntity(Pvt, 90.0, 0.0, 0.0)
								EntityPick(Pvt, 0.3)
								de.Decals = CreateDecal(DECAL_BLOOD_2, PickedX(), PickedY() + 0.005, PickedZ(), 90.0, Rnd(360.0), 0.0, 0.75)
								EntityParent(de\OBJ, e\room\OBJ)
								FreeEntity(Pvt)
								If me\Injuries > 15.0
									msg\DeathMsg = GetLocalString("death", "1162")
									PlaySound_Strict(LoadTempSound("SFX\SCP\1162_ARC\BodyHorrorExchange" + Rand(4) + ".ogg"))
									me\LightFlash = 5.0
									Kill(True)
								Else
									PlaySound_Strict(LoadTempSound("SFX\SCP\1162_ARC\BodyHorrorExchange" + Rand(4) + ".ogg"))
									me\LightFlash = 5.0
									CreateMsg(GetLocalString("msg", "1162_2"))
								EndIf
								e\EventState2 = 0.0
							EndIf
							
							Select e\EventState
								Case 1.0
									;[Block]
									it.Items = CreateItem("Lost Key", "key", EntityX(pp, True), EntityY(pp, True), EntityZ(pp, True))
									;[End Block]
								Case 2.0
									;[Block]
									it.Items = CreateItem("Disciplinary Hearing DH-S-4137-17092", "oldpaper", EntityX(pp, True), EntityY(pp, True), EntityZ(pp, True))
									;[End Block]
								Case 3.0
									;[Block]
									it.Items = CreateItem("Coin", "coin", EntityX(pp, True), EntityY(pp, True), EntityZ(pp, True))
									;[End Block]
								Case 4.0
									;[Block]
									it.Items = CreateItem("Movie Ticket", "ticket", EntityX(pp, True), EntityY(pp, True), EntityZ(pp, True))
									;[End Block]
								Case 5.0
									;[Block]
									it.Items = CreateItem("Old Badge", "oldbadge", EntityX(pp, True), EntityY(pp, True), EntityZ(pp, True))
									;[End Block]
							End Select
							EntityType(it\Collider, HIT_ITEM)
							
							GiveAchievement(Achv1162_ARC)
							mo\MouseHit1 = False
							e\EventState3 = 0.0
						EndIf
						FreeEntity(pp)
					EndIf
				EndIf
				;[End Block]
			Case e_room2_sl
				;[Block]
				; ~ e\EventState: Determines if the player already entered the room or not (0 = No, 1 = Yes)
				
				; ~ e\EventState2: Variable used for the SCP-049 event
				
				; ~ e\EventState3: Checks if Lever is activated or not
				
				If PlayerRoom = e\room Then
					If e\EventState = 0.0 Then
						If e\EventState2 = 0.0 Then e\EventState2 = (-70.0) * 5.0
						e\EventState = 1.0
					EndIf
				EndIf
				
				If e\EventState = 1.0 Then
					If e\EventState2 < 0.0 Then
						If e\EventState2 = (-70.0) * 5.0 Then
							For sc.SecurityCams = Each SecurityCams
								If sc\room = e\room Then
									If EntityDistanceSquared(sc\ScrOBJ, Camera) < 25.0 Then
										If EntityVisible(sc\ScrOBJ, Camera) Then
											e\EventState2 = Min(e\EventState2 + fps\Factor[0], 0.0)
											Exit
										EndIf
									EndIf
								EndIf
							Next
						Else
							e\EventState2 = Min(e\EventState2 + fps\Factor[0], 0.0)
						EndIf
					ElseIf e\EventState2 = 0.0
						Local AdjDist1# = 0.0
						Local AdjDist2# = 0.0
						Local Adj1% = -1
						Local Adj2% = -1
						
						For i = 0 To MaxRoomAdjacents - 1
							If e\room\AdjDoor[i] <> Null Then
								If Adj1 = -1 Then
									AdjDist1 = EntityDistanceSquared(e\room\Objects[7], e\room\AdjDoor[i]\FrameOBJ)
									Adj1 = i
								Else
									AdjDist2 = EntityDistanceSquared(e\room\Objects[7], e\room\AdjDoor[i]\FrameOBJ)
									Adj2 = i
								EndIf
							EndIf
						Next
						
						If n_I\Curr049 = Null Then
							If AdjDist1 > AdjDist2 Then
								n_I\Curr049 = CreateNPC(NPCType049, EntityX(e\room\AdjDoor[Adj1]\FrameOBJ), EntityY(e\room\Objects[7], True), EntityZ(e\room\AdjDoor[Adj1]\FrameOBJ))
							Else
								n_I\Curr049 = CreateNPC(NPCType049, EntityX(e\room\AdjDoor[Adj2]\FrameOBJ), EntityY(e\room\Objects[7], True), EntityZ(e\room\AdjDoor[Adj2]\FrameOBJ))
							EndIf
						Else
							If AdjDist1 > AdjDist2 Then
								PositionEntity(n_I\Curr049\Collider, EntityX(e\room\AdjDoor[Adj1]\FrameOBJ), EntityY(e\room\Objects[7], True), EntityZ(e\room\AdjDoor[Adj1]\FrameOBJ), True)
							Else
								PositionEntity(n_I\Curr049\Collider, EntityX(e\room\AdjDoor[Adj2]\FrameOBJ), EntityY(e\room\Objects[7], True), EntityZ(e\room\AdjDoor[Adj2]\FrameOBJ), True)
							EndIf
							ResetEntity(n_I\Curr049\Collider)
						EndIf
						PointEntity(n_I\Curr049\Collider, e\room\OBJ)
						MoveEntity(n_I\Curr049\Collider, 0.0, 0.0, -1.0)
						
						e\room\NPC[0] = n_I\Curr049
						e\room\NPC[0]\HideFromNVG = False
						e\room\NPC[0]\PathX = EntityX(me\Collider)
						e\room\NPC[0]\PathZ = EntityZ(me\Collider)
						e\room\NPC[0]\State = 5.0 : e\room\NPC[0]\PrevState = 2
						
						e\EventState2 = 1.0
					ElseIf e\EventState2 = 1.0
						If e\room\NPC[0]\PathStatus <> 1 Then
							e\room\NPC[0]\PathStatus = FindPath(e\room\NPC[0], EntityX(e\room\Objects[15], True), EntityY(e\room\Objects[15], True), EntityZ(e\room\Objects[15], True))
						Else
							e\EventState2 = 2.0
						EndIf
					ElseIf e\EventState2 = 2.0
						If e\room\NPC[0]\PathStatus <> 1 Then
							e\room\NPC[0]\State3 = 1.0
							e\room\NPC[0]\PathTimer = 0.0
							e\EventState2 = 3.0
						Else
							If EntityDistanceSquared(e\room\NPC[0]\Collider, e\room\RoomDoors[0]\FrameOBJ) < 25.0
								For i = 0 To 1
									e\room\RoomDoors[i]\Locked = 1
									If (Not e\room\RoomDoors[i]\Open) Then OpenCloseDoor(e\room\RoomDoors[i])
								Next
								If e\room\NPC[0]\Reload = 0.0 Then
									PlaySound_Strict(LoadTempSound("SFX\Door\DoorOpen079.ogg"))
									e\room\NPC[0]\DropSpeed = 0.0
									e\room\NPC[0]\Reload = 1.0
								EndIf
							EndIf
						EndIf
						
						If e\room\NPC[0]\State <> 5.0 Then e\EventState2 = 7.0
					ElseIf e\EventState2 = 3.0
						If e\room\NPC[0]\State <> 5.0 Then e\EventState2 = 7.0
						
						If NPCSeesPlayer(e\room\NPC[0], True) = 2 Then e\EventState2 = 4.0
						
						If e\room\NPC[0]\PathStatus <> 1 Then
							If e\room\NPC[0]\PathTimer = 0.0 Then
								If e\room\NPC[0]\PrevState = 1 Then
									If (Not e\room\NPC[0]\SoundCHN2) Then
										e\room\NPC[0]\Sound2 = LoadSound_Strict("SFX\SCP\049\Room2SL1.ogg")
										e\room\NPC[0]\SoundCHN2 = PlaySound2(e\room\NPC[0]\Sound2, Camera, e\room\NPC[0]\Collider)
									Else
										If (Not ChannelPlaying(e\room\NPC[0]\SoundCHN2)) Then
											e\room\NPC[0]\PathTimer = 1.0
											e\room\NPC[0]\SoundCHN2 = 0
										EndIf
									EndIf
								ElseIf e\room\NPC[0]\PrevState = 2
									If e\room\NPC[0]\State3 = 3.0 Then
										If (Not e\room\NPC[0]\SoundCHN2) Then
											e\room\NPC[0]\Sound2 = LoadSound_Strict("SFX\SCP\049\Room2SL2.ogg")
											e\room\NPC[0]\SoundCHN2 = PlaySound2(e\room\NPC[0]\Sound2, Camera, e\room\NPC[0]\Collider)
										Else
											If (Not ChannelPlaying(e\room\NPC[0]\SoundCHN2)) Then
												e\room\NPC[0]\PathTimer = 1.0
												e\room\NPC[0]\SoundCHN2 = 0
											EndIf
										EndIf
									Else
										If e\room\NPC[0]\Frame >= 1118.0 Then e\room\NPC[0]\PathTimer = 1.0
									EndIf
								EndIf
							Else
								Select e\room\NPC[0]\State3
									Case 1.0
										;[Block]
										e\room\NPC[0]\PathStatus = FindPath(e\room\NPC[0], EntityX(e\room\Objects[16], True), EntityY(e\room\Objects[16], True), EntityZ(e\room\Objects[16], True))
										e\room\NPC[0]\PrevState = 1
										;[End Block]
									Case 2.0
										;[Block]
										e\room\NPC[0]\PathStatus = FindPath(e\room\NPC[0], EntityX(e\room\Objects[15], True), EntityY(e\room\Objects[15], True), EntityZ(e\room\Objects[15], True))
										e\room\NPC[0]\PrevState = 2
										;[End Block]
									Case 3.0
										;[Block]
										e\room\NPC[0]\PathStatus = FindPath(e\room\NPC[0], EntityX(e\room\Objects[17], True), EntityY(e\room\Objects[17], True), EntityZ(e\room\Objects[17], True))
										e\room\NPC[0]\PrevState = 2
										;[End Block]
									Case 4.0
										;[Block]
										e\room\NPC[0]\PathStatus = FindPath(e\room\NPC[0], e\room\NPC[0]\PathX, 0.1, e\room\NPC[0]\PathZ)
										e\room\NPC[0]\PrevState = 2
										;[End Block]
									Case 5.0
										;[Block]
										e\EventState2 = 5.0
										;[End Block]
								End Select
								e\room\NPC[0]\PathTimer = 0.0
								e\room\NPC[0]\State3 = e\room\NPC[0]\State3 + 1.0
							EndIf
						EndIf
					ElseIf e\EventState2 = 4.0
						If e\room\NPC[0]\State <> 5.0 Then
							e\EventState2 = 7.0
							e\room\NPC[0]\State3 = 6.0
						EndIf
					ElseIf e\EventState2 = 5.0
						e\room\NPC[0]\State = 2.0
						For r.Rooms = Each Rooms
							If r <> PlayerRoom Then
								If EntityDistanceSquared(r\OBJ, e\room\NPC[0]\Collider) < PowTwo(HideDistance * 2.0) And EntityDistanceSquared(r\OBJ, e\room\NPC[0]\Collider) > PowTwo(HideDistance) Then
									e\room\NPC[0]\PathStatus = FindPath(e\room\NPC[0], EntityX(r\OBJ), EntityY(r\OBJ), EntityZ(r\OBJ))
									e\room\NPC[0]\PathTimer = 0.0
									If e\room\NPC[0]\PathStatus = 1 Then e\EventState2 = 6.0
									Exit
								EndIf
							EndIf
						Next
					ElseIf e\EventState2 = 6.0
						If NPCSeesPlayer(e\room\NPC[0], True) = 1 Lor e\room\NPC[0]\State2 > 0.0 Lor e\room\NPC[0]\LastSeen > 0
							e\EventState2 = 7.0
						Else
							; ~ Still playing the Music for SCP-049 (in the real, SCP-049's State will be set to 2, causing it to stop playing the chasing track)
							If PlayerRoom = e\room Then ShouldPlay = 20
							If e\room\NPC[0]\PathStatus <> 1 Then
								e\room\NPC[0]\Idle = 70.0 * 60.0 ; ~ Making SCP-049 idle for one minute (twice as fast for AggressiveNPCs = True)
								PositionEntity(e\room\NPC[0]\Collider, 0.0, 500.0, 0.0)
								ResetEntity(e\room\NPC[0]\Collider)
								e\EventState2 = 7.0
							EndIf
						EndIf
					EndIf
					
					If e\room\NPC[0] <> Null Then
						If PlayerRoom = e\room Then
							If e\EventState2 < 7.0 Then
								If e\EventState2 > 2.0 Then
									If Abs(EntityY(e\room\RoomDoors[0]\FrameOBJ) - EntityY(e\room\NPC[0]\Collider)) > 1.0 Then
										If Abs(EntityY(e\room\RoomDoors[0]\FrameOBJ) - EntityY(me\Collider)) < 1.0 Then
											If e\room\RoomDoors[0]\Open Then
												e\room\RoomDoors[0]\FastOpen = 1
												OpenCloseDoor(e\room\RoomDoors[0])
												PlaySound_Strict(LoadTempSound("SFX\Door\DoorClose079.ogg"))
											EndIf
										EndIf
									Else
										If (Not e\room\RoomDoors[0]\Open) Then
											e\room\RoomDoors[0]\FastOpen = 0
											OpenCloseDoor(e\room\RoomDoors[0])
											PlaySound_Strict(LoadTempSound("SFX\Door\DoorOpen079.ogg"))
										EndIf
									EndIf
								EndIf
							Else
								If (Not e\room\RoomDoors[0]\Open) Then
									e\room\RoomDoors[0]\FastOpen = 0
									OpenCloseDoor(e\room\RoomDoors[0])
									PlaySound_Strict(LoadTempSound("SFX\Door\DoorOpen079.ogg"))
								EndIf
							EndIf
						EndIf
					EndIf
				EndIf
				
				If PlayerRoom = e\room Then
					; ~ Lever for checkpoint locking (might have a function in the future for the case if the checkpoint needs to be locked again)
					e\EventState3 = UpdateLever(e\room\RoomLevers[0]\OBJ)
					If e\EventState3 = 1.0 Then
						UpdateCheckpointMonitors()
						If mon_I\MonitorTimer < 50.0 Then
							EntityTexture(e\room\Objects[18], e\room\Textures[0], 1)
						Else
							EntityTexture(e\room\Objects[18], e\room\Textures[0], 2)
						EndIf
					Else
						TurnCheckpointMonitorsOff(False)
						EntityTexture(e\room\Objects[18], e\room\Textures[0], 0)
					EndIf
					
					; ~ Checking if the monitors and such should be rendered or not
					If Abs(EntityY(e\room\RoomDoors[0]\FrameOBJ) - EntityY(me\Collider)) > 1.0 Then
						For i = 0 To 14
							If e\room\Objects[i] <> 0 And i <> 7 Then
								If EntityHidden(e\room\Objects[i]) Then ShowEntity(e\room\Objects[i])
							EndIf
						Next
						For sc.SecurityCams = Each SecurityCams
							If sc\room = e\room Then
								If sc\ScrOBJ <> 0 Then
									If EntityHidden(sc\ScrOBJ) Then
										ShowEntity(sc\ScrOBJ)
										If sc\ScrOverlay <> 0 Then ShowEntity(sc\ScrOverlay)
										If sc\MonitorOBJ <> 0 Then ShowEntity(sc\MonitorOBJ)
									EndIf
								EndIf
								Exit
							EndIf
						Next
						
						UpdateRedLight(e\room\Objects[20], 100, 50)
					Else
						For i = 0 To 14
							If e\room\Objects[i] <> 0 And i <> 7 Then
								If (Not EntityHidden(e\room\Objects[i])) Then HideEntity(e\room\Objects[i])
							EndIf
						Next
						For sc.SecurityCams = Each SecurityCams
							If sc\room = e\room Then
								If sc\ScrOBJ <> 0 Then
									If (Not EntityHidden(sc\ScrOBJ)) Then
										HideEntity(sc\ScrOBJ)
										If sc\ScrOverlay <> 0 Then HideEntity(sc\ScrOverlay)
										If sc\MonitorOBJ <> 0 Then HideEntity(sc\MonitorOBJ)
									EndIf
								EndIf
								Exit
							EndIf
						Next
					EndIf
				EndIf
				
				For e2.Events = Each Events
					If e2\EventID = e_cont2_008 Then
						If e2\EventState = 2.0 Then
							EntityTexture(e\room\Objects[19], e\room\Textures[0], 3)
						Else
							EntityTexture(e\room\Objects[19], e\room\Textures[1], 6)
						EndIf
						Exit
					EndIf
				Next
				;[End Block]
			Case e_096_spawn
				;[Block]
				Local xSpawn#, zSpawn#, Place%
				
				If e\room\Dist < HideDistance Then
					; ~ Checking some statements in order to determine if SCP-096 can spawn in this room
					If e\EventState <> 2.0 Then
						If n_I\Curr096 <> Null Then
							If EntityDistanceSquared(n_I\Curr096\Collider, me\Collider) < 1600.0 Then e\EventState = 2.0
							
							For e2.Events = Each Events
								If e2\EventID = e_room2_servers_hcz
									If e2\EventState > 0.0 And e2\room\NPC[0] <> Null
										e\EventState = 2.0
										Exit
									EndIf
								EndIf
							Next
							
							For r.Rooms = Each Rooms
								If r\RoomTemplate\Name = "room2_checkpoint_lcz_hcz" Then
									If r\Dist < 10.0 Then
										e\EventState = 2.0
										Exit
									EndIf
								EndIf
							Next
							
							If n_I\Curr096\State <> 5.0 Then e\EventState = 2.0
							If EntityDistanceSquared(n_I\Curr096\Collider, e\room\OBJ) > EntityDistanceSquared(n_I\Curr096\Collider, me\Collider) Then e\EventState = 2.0
						EndIf
						
						For e2.Events = Each Events
							If e2\EventID = e_room2_servers_hcz Then
								If e2\EventState = 0.0
									e\EventState = 2.0
									Exit
								EndIf
							EndIf
						Next
						If PlayerRoom = e\room Then e\EventState = 2.0
					EndIf
					
					If e\EventState = 0.0
						Select e\room\RoomTemplate\Name
							Case "room3_hcz", "room3_2_hcz", "room3_3_hcz", "room4_hcz", "room4_2_hcz"
								;[Block]
								If e\room\RoomTemplate\Name = "room4_hcz" Lor e\room\RoomTemplate\Name = "room4_2_hcz"
									Place = Rand(0, 3)
								Else
									Place = Rand(0, 2)
								EndIf
								
								If Place = 0 Then
									xSpawn = -608.0
									zSpawn = 0.0
								ElseIf Place = 1
									xSpawn = 0.0
									zSpawn = -608.0
								ElseIf Place = 2
									xSpawn = 608.0
									zSpawn = 0.0
								Else
									xSpawn = 0.0
									zSpawn = 608.0
								EndIf
								;[End Block]
							Default
								;[Block]
								xSpawn = Rnd(-100.0, 100.0)
								zSpawn = Rnd(-100.0, 100.0)
								;[End Block]
						End Select
						Pvt = CreatePivot(e\room\OBJ)
						PositionEntity(Pvt, xSpawn, 0.0, zSpawn)
						If n_I\Curr096 <> Null Then
							PositionEntity(n_I\Curr096\Collider, EntityX(Pvt, True), e\room\y + 0.5, EntityZ(Pvt, True))
							ResetEntity(n_I\Curr096\Collider)
						Else
							n_I\Curr096 = CreateNPC(NPCType096, EntityX(Pvt, True), e\room\y + 0.5, EntityZ(Pvt, True))
						EndIf
						n_I\Curr096\State = 5.0
						PointEntity(n_I\Curr096\Collider, me\Collider)
						RotateEntity(n_I\Curr096\Collider, 0.0, EntityYaw(n_I\Curr096\Collider) + 180.0, 0.0)
						FreeEntity(Pvt)
						
						e\EventState = 1.0
					ElseIf e\EventState = 1.0
						PointEntity(n_I\Curr096\Collider, me\Collider)
						RotateEntity(n_I\Curr096\Collider, 0.0, EntityYaw(n_I\Curr096\Collider) + 180.0, 0.0)
						
						If EntityDistanceSquared(n_I\Curr096\Collider, me\Collider) < PowTwo(HideDistance * 0.5)
							If EntityVisible(n_I\Curr096\Collider, Camera)
								PointEntity(n_I\Curr096\Collider, me\Collider)
								RotateEntity(n_I\Curr096\Collider, 0.0, EntityYaw(n_I\Curr096\Collider) + Rnd(170.0, 190.0), 0.0)
								e\EventState = 2.0
							EndIf
						EndIf
					ElseIf e\EventState = 3.0
						e\EventState = 2.0
					EndIf
				Else
					If e\EventState = 2.0
						If Rand(-1, 1 + (2 * SelectedDifficulty\AggressiveNPCs)) > 0 Then
							e\EventState = 0.0
						Else
							e\EventState = 3.0
						EndIf
					EndIf
				EndIf
				;[End Block]
			Case e_room2_medibay
				;[Block]
				If PlayerRoom = e\room Then
					If e\EventState = 0.0 Then
						e\room\NPC[0] = CreateNPC(NPCType008_1, EntityX(e\room\Objects[0], True), 0.5, EntityZ(e\room\Objects[0], True))
						e\room\NPC[0]\State = 0.0
						RotateEntity(e\room\NPC[0]\Collider, 0.0, e\room\Angle - 90.0, 0.0)
						e\EventState = 1.0
					EndIf
					
					If EntityDistanceSquared(e\room\NPC[0]\Collider, me\Collider) < 1.44 Then
						If e\EventState2 = 0.0 Then
							me\LightBlink = 10.0
							PlaySound_Strict(LightSFX)
							e\room\NPC[0]\State = 1.0
							e\EventState2 = 1.0
						EndIf
					EndIf
						
					If InteractObject(e\room\Objects[1], 0.49) Then
						CreateMsg(GetLocalString("msg", "breeze"))
						me\Injuries = Max(0.0, me\Injuries - Rnd(0.3))
						me\Bloodloss = 0.0
						PlaySound_Strict(LoadTempSound("SFX\SCP\Joke\Quack.ogg"))
					EndIf
				EndIf
				;[End Block]
			Case e_dimension_1499
				;[Block]
				If PlayerRoom <> e\room Then
					If e\room\Objects[0] <> 0 Then
						For i = 1 To 15
							If (Not EntityHidden(e\room\Objects[i])) Then HideEntity(e\room\Objects[i])
						Next
					EndIf
					If EntityY(me\Collider) > EntityY(e\room\OBJ) - 0.5 Then PlayerRoom = e\room
				EndIf
				If e\EventState = 2.0 Then
					If e\SoundCHN <> 0 Then StopStream_Strict(e\SoundCHN) : e\SoundCHN = 0 : e\SoundCHN_IsStream = False
					StopChannel(e\SoundCHN2) : e\SoundCHN2 = 0
					HideEntity(I_1499\Sky)
					HideChunks()
					For n.NPCs = Each NPCs
						If n\NPCType = NPCType1499_1 Then RemoveNPC(n)
					Next
					For du.Dummy1499_1 = Each Dummy1499_1
						FreeEntity(du\OBJ) : du\OBJ = 0
						Delete(du)
					Next
					If e\Sound2 <> 0 Then FreeSound_Strict(e\Sound2) : e\Sound2 = 0
					If e\EventState3 < 70.0 * 30.0 Then e\EventState3 = 0.0
					e\EventState = 1.0
				EndIf
				;[End Block]
			Case e_room2_shaft
				;[Block]
				If e\EventState = 0.0 Then
					e\room\NPC[0] = CreateNPC(NPCTypeGuard, EntityX(e\room\Objects[1], True), EntityY(e\room\Objects[1], True) + 0.5, EntityZ(e\room\Objects[1], True))
					e\room\NPC[0]\State = 8.0 : e\room\NPC[0]\IsDead = True
					SetNPCFrame(e\room\NPC[0], 286.0)
					RotateEntity(e\room\NPC[0]\Collider, 0.0, e\room\Angle + 180.0, 0.0, True)
					
					e\EventState = 1.0
				EndIf
				
				If PlayerRoom = e\room Then
					UpdateButton(e\room\Objects[2])
					If d_I\ClosestButton = e\room\Objects[2] And mo\MouseHit1 Then
						CreateMsg(GetLocalString("msg", "elev.broken"))
						PlaySound2(ButtonSFX2, Camera, e\room\Objects[2])
						mo\MouseHit1 = False
					EndIf
				EndIf
				;[End Block]
			Case e_room4_ic
				;[Block]
				For e2.Events = Each Events
					If e2\EventID = e_room2_sl Then
						If e2\EventState3 = 0.0 Then
							If e\room\Dist < 12.0 Then TurnCheckpointMonitorsOff()
						Else
							If e\room\Dist < 12.0 Then UpdateCheckpointMonitors()
						EndIf
						Exit
					EndIf
				Next
				;[End Block]
			Case e_cont2_409
				;[Block]
				If PlayerRoom = e\room Then
					If EntityY(me\Collider) < (-3728.0) * RoomScale Then
						ShouldPlay = 28
						
						If e\EventState = 0.0 Then
							e\room\NPC[0] = CreateNPC(NPCTypeD, EntityX(e\room\Objects[2], True), EntityY(e\room\Objects[2], True) + 0.5, EntityZ(e\room\Objects[2], True))
							e\room\NPC[0]\State3 = -1.0 : e\room\NPC[0]\IsDead = True
							ChangeNPCTextureID(e\room\NPC[0], NPC_CLASS_D_VICTIM_409_TEXTURE)
							SetNPCFrame(e\room\NPC[0], 19.0)
							RotateEntity(e\room\NPC[0]\Collider, 0.0, e\room\Angle, 0.0)
							
							e\Sound = LoadSound_Strict("SFX\General\SparkShort.ogg")
							
							e\EventState = 1.0
						Else
							If I_409\Timer = 0.0 Then
								If EntityDistanceSquared(me\Collider, e\room\NPC[0]\Collider) < 0.7225 Then
									GiveAchievement(Achv409)
									me\BlurTimer = 1000.0
									I_409\Timer = 0.001
								EndIf
								
								; ~ Touching SCP-409
								If InteractObject(e\room\Objects[3], 0.64) Then
									CreateMsg(GetLocalString("msg", "409"))
									me\BlurTimer = 1000.0
									GiveAchievement(Achv409)
									I_409\Timer = 0.001
								EndIf
							EndIf
						EndIf
						
						If EntityDistanceSquared(me\Collider, e\room\Objects[4]) < 25.0 Then
							If Rand(50) = 1 Then
								PlaySound2(e\Sound, Camera, e\room\Objects[4], 3.0, 0.4)
								
								If opt\ParticleAmount > 0 Then
									For i = 0 To (2 + (1 * (opt\ParticleAmount - 1)))
										p.Particles = CreateParticle(PARTICLE_SPARK, EntityX(e\room\Objects[4], True), EntityY(e\room\Objects[4], True), EntityZ(e\room\Objects[4], True), 0.002, 0.0, 25.0)
										p\Speed = Rnd(0.005, 0.03) : p\Size = Rnd(0.005, 0.0075) : p\AlphaChange = -0.05
										RotateEntity(p\Pvt, Rnd(-20.0, 0.0), e\room\Angle, 0.0)
										ScaleSprite(p\OBJ, p\Size, p\Size)
									Next
								EndIf
							EndIf
						EndIf
					EndIf
					e\EventState2 = UpdateElevators(e\EventState2, e\room\RoomDoors[0], e\room\RoomDoors[1], e\room\Objects[0], e\room\Objects[1], e)
				EndIf
				;[End Block]
			Case e_cont1_005
				;[Block]
				If (Not n_I\Curr106\Contained) And I_005\ChanceToSpawn = 1 Then
					If PlayerRoom = e\room Then
						If e\EventState = 0.0 Then
							If EntityDistanceSquared(me\Collider, e\room\Objects[0]) < 1.69 Then
								PlaySound_Strict(HorrorSFX[10])
								
								de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(e\room\Objects[1], True), EntityY(e\room\Objects[1], True), EntityZ(e\room\Objects[1], True), 0.0, e\room\Angle + 360.0, Rnd(360.0), 0.1, 0.01)
								de\SizeChange = 0.003 : de\AlphaChange = 0.005 : de\Timer = 90000.0
								EntityParent(de\OBJ, e\room\OBJ)
								
								de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(e\room\RoomDoors[0]\FrameOBJ, True), EntityY(e\room\RoomDoors[0]\FrameOBJ, True) + 0.005, EntityZ(e\room\RoomDoors[0]\FrameOBJ, True), 90.0, e\room\Angle + 360.0, Rnd(360.0), 0.1, 0.01)
								de\SizeChange = 0.003 : de\AlphaChange = 0.005 : de\Timer = 90000.0
								EntityParent(de\OBJ, e\room\OBJ)
								
								PositionEntity(n_I\Curr106\Collider, EntityX(e\room\Objects[2], True), EntityY(e\room\Objects[2], True), EntityZ(e\room\Objects[2], True))
								ResetEntity(n_I\Curr106\Collider)
								n_I\Curr106\State = -11.0
								ShowEntity(n_I\Curr106\OBJ)
								
								e\EventState = 1.0
							EndIf
						Else
							Dist = DistanceSquared(EntityX(me\Collider), EntityX(e\room\RoomDoors[0]\FrameOBJ), EntityZ(me\Collider), EntityZ(e\room\RoomDoors[0]\FrameOBJ))
							If Dist < 4.0 Then
								me\CrouchState = (2.0 - Sqr(Dist)) / 2.0
								
								If Dist < 0.16 Then
									If e\EventState2 = 0.0 Then PlaySound_Strict(LoadTempSound("SFX\Room\SinkholeFall.ogg"))
									
									me\Playable = False
									
									x = CurveValue(EntityX(e\room\RoomDoors[0]\FrameOBJ), EntityX(me\Collider), 10.0)
									y = CurveValue(EntityY(e\room\RoomDoors[0]\FrameOBJ) - e\EventState2, EntityY(me\Collider), 25.0)
									z = CurveValue(EntityZ(e\room\RoomDoors[0]\FrameOBJ), EntityZ(me\Collider), 10.0)
									PositionEntity(me\Collider, x, y, z, True)
									
									me\DropSpeed = 0.0
									
									ResetEntity(me\Collider)
									
									e\EventState2 = Min(e\EventState2 + fps\Factor[0] / 200.0, 2.0)
									
									me\LightBlink = Min(e\EventState2 * 5.0, 10.0)
									If wi\NightVision > 0 Then
										If e\EventState2 >= 0.2 Then me\BlinkTimer = -10.0
									EndIf
									me\BlurTimer = e\EventState2 * 500.0
									
									If e\EventState2 = 2.0 Then
										me\Playable = True
										MoveToPocketDimension()
									EndIf
								EndIf
							EndIf
						EndIf
					Else
						e\EventState2 = 0.0
					EndIf
				Else
					RemoveEvent(e)
				EndIf
				;[End Block]
		End Select
		
		If e <> Null Then
			CatchErrors("UpdateEvents: Event Name: " + Chr(34) + e\EventName + Chr(34) + ", ID: " + e\EventID)
		Else
			CatchErrors("UpdateEvents: Removed event!")
		EndIf
	Next
	
	UpdateExplosion()
End Function

; ~ Pocket Dimension Constants
;[Block]
Const PD_StartRoom% = 0
Const PD_FourWayRoom% = 1
Const PD_ThroneRoom% = 2
Const PD_TrenchesRoom% = 3
Const PD_ExitRoom% = 4
Const PD_FakeTunnelRoom% = 5
Const PD_TowerRoom% = 6
;[End Block]

Function UpdateDimension106%()
	Local e.Events, e2.Events, r.Rooms, it.Items, p.Particles, de.Decals
	Local i%, Angle#, Dist#, Pvt%, Temp%
	Local x#, y#, z#
	
	For e.Events = Each Events
		If e\EventID = e_dimension_106 Then
			; ~ e\EventState: A timer for scaling the tunnels in the starting room
			
			; ~ e\EventState2: Detects where the player is
			
			; ~ e\EventState3: A timer for objects
			
			If PlayerRoom = e\room Then
				For r.Rooms = Each Rooms
					HideRoomsNoColl(r)
				Next
				ShowRoomsNoColl(e\room)
				
				CanSave = 1
				
				PlayerFallingPickDistance = 0.0
				CurrStepSFX = 1
				ShouldPlay = 3
				
				If wi\HazmatSuit = 0 Then
					InjurePlayer(fps\Factor[0] / 15000.0)
				ElseIf wi\HazmatSuit <> 4
					InjurePlayer(fps\Factor[0] / 20000.0)
				Else
					InjurePlayer(fps\Factor[0] / 25000.0)
				EndIf
				PrevSecondaryLightOn = SecondaryLightOn : SecondaryLightOn = True
				
				If e\EventState = 0.0 Then e\EventState = 0.1
				
				e\EventState = e\EventState + fps\Factor[0]
				
				ScaleEntity(e\room\OBJ, RoomScale, RoomScale * (1.0 + Sin(e\EventState / 14.0) * 0.2), RoomScale)
				For i = 9 To 10
					ScaleEntity(e\room\Objects[i], RoomScale * (1.5 + Abs(Sin(e\EventState / 21.0 + i * 45.0) * 0.1)), RoomScale * (1.0 + Sin(e\EventState / 14.0 + i * 20.0) * 0.1), RoomScale, True)
				Next
				
				; ~ SCP-106 attacks if close enough to player
				If EntityDistanceSquared(me\Collider, n_I\Curr106\Collider) < 0.09 Then n_I\Curr106\State = -10.0 : n_I\Curr106\Idle = 0
				
				Local Teleport% = False, Random% = Rand(29)
				
				Select e\EventState2
					Case PD_StartRoom
						;[Block]
						For i = 0 To 7
							ScaleEntity(e\room\Objects[i], RoomScale * (1.0 + Abs(Sin(e\EventState / 21.0 + i * 45.0) * 0.1)), RoomScale * (1.0 + Sin(e\EventState / 14.0 + i * 20.0) * 0.1), RoomScale, True)
						Next
						
						If n_I\Curr106\State > 0.0 Then ; ~ SCP-106 circles around the starting room
							Angle = (e\EventState / 10.0 Mod 360.0)
							PositionEntity(n_I\Curr106\Collider, EntityX(e\room\OBJ), 0.2 + 0.35 + Sin(e\EventState / 14.0 + i * 20.0) * 0.4, EntityX(e\room\OBJ))
							RotateEntity(n_I\Curr106\Collider, 0.0, Angle, 0.0)
							MoveEntity(n_I\Curr106\Collider, 0.0, 0.0, 6.0 - Sin(e\EventState / 10.0))
							AnimateNPC(n_I\Curr106, 55.0, 104.0, 0.5)
							RotateEntity(n_I\Curr106\Collider, 0.0, Angle + 90.0, 0.0)
							If EntityHidden(n_I\Curr106\OBJ) Lor EntityHidden(n_I\Curr106\Collider) Then
								ShowEntity(n_I\Curr106\OBJ)
								ShowEntity(n_I\Curr106\Collider)
							EndIf
							ResetEntity(n_I\Curr106\Collider)
							n_I\Curr106\Idle = 1 : n_I\Curr106\GravityMult = 0.0 : n_I\Curr106\DropSpeed = 0.0
							PositionEntity(n_I\Curr106\OBJ, EntityX(n_I\Curr106\Collider), EntityY(n_I\Curr106\Collider) - 0.15, EntityZ(n_I\Curr106\Collider))
							RotateEntity(n_I\Curr106\OBJ, 0.0, EntityYaw(n_I\Curr106\Collider), 0.0)
						EndIf
						If e\EventState > 70.0 * 65.0 Then
							If Rand(800) = 1 Then
								PlaySound_Strict(HorrorSFX[8])
								n_I\Curr106\State = -0.1 : n_I\Curr106\Idle = 0
								e\EventState = 601.0
							EndIf
						EndIf
						
						Dist = EntityDistanceSquared(me\Collider, e\room\OBJ)
						If Dist > PowTwo(1600.0 * RoomScale) Then Teleport = True
						;[End Block]
					Case PD_FourWayRoom
						;[Block]
						PositionEntity(e\room\Objects[9], EntityX(e\room\Objects[8], True) + 3384.0 * RoomScale, 0.0, EntityZ(e\room\Objects[8], True))
						
						TranslateEntity(e\room\Objects[9], Cos(e\EventState * 0.8) * 5.0, 0.0, Sin(e\EventState * 1.6) * 4.0, True)
						RotateEntity(e\room\Objects[9], 0.0, e\EventState * 2.0, 0.0)
						
						PositionEntity(e\room\Objects[10], EntityX(e\room\Objects[8], True), 0.0, EntityZ(e\room\Objects[8], True) + 3384.0 * RoomScale)
						
						TranslateEntity(e\room\Objects[10], Sin(e\EventState * 1.6) * 4.0, 0.0, Cos(e\EventState * 0.8) * 5.0, True)
						RotateEntity(e\room\Objects[10], 0.0, e\EventState * 2.0, 0.0)
						
						If (Not chs\GodMode) Then
							For i = 9 To 10
								Dist = DistanceSquared(EntityX(me\Collider), EntityX(e\room\Objects[i], True), EntityZ(me\Collider), EntityZ(e\room\Objects[i], True))
								If Dist < 36.0 Then
									If Dist < PowTwo(100.0 * RoomScale) Then
										Pvt = CreatePivot()
										PositionEntity(Pvt, EntityX(e\room\Objects[i], True), EntityY(me\Collider), EntityZ(e\room\Objects[i], True))
										PointEntity(Pvt, me\Collider)
										RotateEntity(Pvt, 0.0, Int(EntityYaw(Pvt) / 90.0) * 90.0, 0.0, True)
										MoveEntity(Pvt, 0.0, 0.0, 100.0 * RoomScale)
										PositionEntity(me\Collider, EntityX(Pvt), EntityY(me\Collider), EntityZ(Pvt))
										FreeEntity(Pvt)
										
										If (Not me\Terminated) Then
											msg\DeathMsg = GetLocalString("death", "106_1")
											
											PlaySound_Strict(LoadTempSound("SFX\Room\PocketDimension\Impact.ogg"))
											me\Terminated = True
										EndIf
									EndIf
									e\SoundCHN = LoopSound2(e\Sound, e\SoundCHN, Camera, e\room\Objects[i], 6.0)
								EndIf
							Next
						EndIf
						
						Pvt = CreatePivot()
						PositionEntity(Pvt, EntityX(e\room\Objects[8], True) - 1536.0 * RoomScale, e\room\y + 500.0 * RoomScale, EntityZ(e\room\Objects[8], True) + 608.0 * RoomScale)
						If EntityDistanceSquared(Pvt, me\Collider) < 25.0 Then e\SoundCHN2 = LoopSound2(e\Sound2, e\SoundCHN2, Camera, Pvt, 3.0)
						FreeEntity(Pvt)
						
						If EntityY(me\Collider) < (-1600.0) * RoomScale Then
							If EntityDistanceSquared(me\Collider, e\room\Objects[8]) > PowTwo(4750.0 * RoomScale) Then
								Teleport = True
								Random = Rand(12, 29)
							Else ; ~ The player is not at the exit, must've fallen down
								If (Not me\Terminated) Then
									PlaySound_Strict(HorrorSFX[8])
									msg\DeathMsg = GetLocalString("death", "106_2")
									me\BlurTimer = 3000.0
									me\Terminated = True
								EndIf
							EndIf
						EndIf
						
						Temp = EntityDistanceSquared(me\Collider, e\room\Objects[17])
						If Temp < PowTwo(2000.0 * RoomScale) Then
							LoadEventSound(e, "SFX\Room\PocketDimension\Screech.ogg")
							LoadEventSound(e, "SFX\Room\PocketDimension\Kneel.ogg", 1)
							e\EventState2 = PD_ThroneRoom
						EndIf
						;[End Block]
					Case PD_ThroneRoom
						;[Block]
						Temp = EntityDistanceSquared(me\Collider, e\room\Objects[17])
						If Temp >= PowTwo(2000.0 * RoomScale) Then
							LoadEventSound(e, "SFX\Room\PocketDimension\Rumble.ogg")
							LoadEventSound(e, "SFX\Room\PocketDimension\PrisonVoices.ogg", 1)
							e\EventState2 = PD_FourWayRoom
						EndIf
						
						For i = 17 To 18
							If EntityHidden(e\room\Objects[i]) Then ShowEntity(e\room\Objects[i])
						Next
						
						InjurePlayer(fps\Factor[0] / 5000.0)
						
						me\Sanity = Max(me\Sanity - fps\Factor[0] / Sqr(Temp) / 8.0, -1000.0)
						
						me\CurrCameraZoom = Max(me\CurrCameraZoom, (Sin(Float(MilliSecs2()) / 20.0) + 1.0) * 15.0 * Max((6.0 - Sqr(Temp)) / 6.0, 0.0))
						
						Pvt = CreatePivot()
						PositionEntity(Pvt, EntityX(Camera), EntityY(Camera), EntityZ(Camera))
						PointEntity(Pvt, e\room\Objects[17])
						TurnEntity(Pvt, 90.0, 0.0, 0.0)
						CameraPitch = CurveAngle(EntityPitch(Pvt), CameraPitch + 90.0, Min(Max(15000.0 / (-me\Sanity), 15.0), 500.0))
						CameraPitch = CameraPitch - 90.0
						RotateEntity(me\Collider, EntityPitch(me\Collider), CurveAngle(EntityYaw(Pvt), EntityYaw(me\Collider), Min(Max(15000.0 / (-me\Sanity), 15.0), 500.0)), 0)
						FreeEntity(Pvt)
						
						; ~ Teleport the player to the trenches
						If me\Crouch Then
							me\BlinkTimer = -10.0 : me\Crouch = False
							PositionEntity(me\Collider, EntityX(e\room\Objects[8], True) - 1344.0 * RoomScale, e\room\y + 2944.0 * RoomScale, EntityZ(e\room\Objects[8], True) - 1184.0 * RoomScale)
							ResetEntity(me\Collider)
							
							LoadEventSound(e, "SFX\Room\PocketDimension\Explosion.ogg")
							LoadEventSound(e, "SFX\Room\PocketDimension\TrenchPlane.ogg", 1)
							PositionEntity(e\room\Objects[19], EntityX(e\room\Objects[8], True) - 1000.0, 0.0, 0.0, True)
							
							e\EventState2 = PD_TrenchesRoom
						EndIf
						;[End Block]
					Case PD_TrenchesRoom
						;[Block]
						ShouldPlay = 15
						
						For i = 17 To 20
							If i > 18 Then
								If EntityHidden(e\room\Objects[i]) Then ShowEntity(e\room\Objects[i])
							Else
								If (Not EntityHidden(e\room\Objects[i])) Then HideEntity(e\room\Objects[i])
							EndIf
						Next
						
						If EntityX(e\room\Objects[19], True) < EntityX(e\room\Objects[8], True) - 4000.0 * RoomScale Then
							e\SoundCHN2 = PlaySound_Strict(e\Sound2)
							
							PositionEntity(e\room\Objects[19], EntityX(me\Collider, True) + 4000.0 * RoomScale, 12.0, EntityZ(me\Collider, True))
						EndIf
						
						MoveEntity(me\Collider, 0.0, Min((12.0 - EntityY(me\Collider)), 0.0) * fps\Factor[0], 0.0)
						
						x = (-fps\Factor[0]) * RoomScale * 4.0
						y = (17.0 - Abs(EntityX(me\Collider) - EntityX(e\room\Objects[19])) * 0.5) - EntityY(e\room\Objects[19])
						z = EntityZ(me\Collider, True) - EntityZ(e\room\Objects[19])
						TranslateEntity(e\room\Objects[19], x, y, z, True)
						RotateEntity(e\room\Objects[19], -90.0 - (EntityX(me\Collider) - EntityX(e\room\Objects[19])) * 1.5, -90.0, 0.0, True)
						
						; ~ Check if the plane can see the player
						Local Safe% = False
						
						For i = 0 To 2
							Select i
								Case 0
									;[Block]
									x = (-1452.0) * RoomScale
									z = (-37.0) * RoomScale
									;[End Block]
								Case 1
									;[Block]
									x = (-121.0) * RoomScale
									z = 188.0 * RoomScale
									;[End Block]
								Case 2
									;[Block]
									x = 1223.0 * RoomScale
									z = (-196.0) * RoomScale
									;[End Block]
							End Select
							
							x = x + EntityX(e\room\Objects[8], True)
							z = z + EntityZ(e\room\Objects[8], True)
							
							If DistanceSquared(EntityX(me\Collider), x, EntityZ(me\Collider), z) < PowTwo(200.0 * RoomScale) Then
								Safe = True
								Exit
							EndIf
						Next
						
						Dist = EntityDistanceSquared(me\Collider, e\room\Objects[19])
						
						e\SoundCHN2 = LoopSound2(e\Sound2, e\SoundCHN2, Camera, Camera, 10.0, 0.3 + (Not Safe) * 0.6)
						
						If Safe Lor chs\NoTarget Then
							EntityTexture(e\room\Objects[19], e\room\Textures[0])
						ElseIf Dist < 64.0 And I_714\Using <> 3 And wi\GasMask <> 4 And wi\HazmatSuit <> 4 Then
							e\SoundCHN = LoopSound2(e\Sound, e\SoundCHN, Camera, e\room\Objects[19], 8.0)
							EntityTexture(e\room\Objects[19], e\room\Textures[1])
							If I_714\Using = 1 Then
								InjurePlayer((8.0 - Sqr(Dist)) * (fps\Factor[0] * 0.0003))
							Else
								InjurePlayer((8.0 - Sqr(Dist)) * (fps\Factor[0] * 0.0002))
							EndIf
							
							If Dist < 49.0 Then 
								Pvt = CreatePivot()
								PositionEntity(Pvt, EntityX(Camera), EntityY(Camera), EntityZ(Camera))
								PointEntity(Pvt, e\room\Objects[19])
								TurnEntity(Pvt, 90.0, 0.0, 0.0)
								CameraPitch = CurveAngle(EntityPitch(Pvt), CameraPitch + 90.0, 10.0)
								CameraPitch = CameraPitch - 90.0
								RotateEntity(me\Collider, EntityPitch(me\Collider), CurveAngle(EntityYaw(Pvt), EntityYaw(me\Collider), 10.0), 0.0)
								FreeEntity(Pvt)
							EndIf
						ElseIf Dist < 64.0 Then
							EntityTexture(e\room\Objects[19], e\room\Textures[0])
							InjurePlayer((8.0 - Sqr(Dist)) * (fps\Factor[0] * 0.00015))
						EndIf
						
						me\CameraShake = Max(4.0 + ((Not Safe) * 4.0) - Sqr(Dist), 0.0)
						
						; ~ Check if player is at the sinkhole (the exit from the trench room)
						If EntityY(me\Collider) < 8.5 Then
							; ~ Move to the "exit room"
							me\BlinkTimer = -10.0 : me\LightBlink = 5.0 : me\BlurTimer = 1500.0
							
							StopChannel(e\SoundCHN) : e\SoundCHN = 0
							StopChannel(e\SoundCHN2) : e\SoundCHN2 = 0
							
							PositionEntity(me\Collider, EntityX(e\room\Objects[8], True) - 400.0 * RoomScale, -304.0 * RoomScale, EntityZ(e\room\Objects[8], True))
							ResetEntity(me\Collider)
							
							e\EventState2 = PD_ExitRoom
						EndIf
						;[End Block]
					Case PD_ExitRoom
						;[Block]
						Temp = DistanceSquared(EntityX(me\Collider), EntityX(e\room\Objects[8], True) + 1024.0 * RoomScale, EntityZ(me\Collider), EntityZ(e\room\Objects[8], True))
						If Temp < PowTwo(640.0 * RoomScale)
							me\BlurTimer = ((640.0 * RoomScale) - Sqr(Temp)) * 3000.0
							
							e\SoundCHN2 = LoopSound2(DecaySFX[Rand(3)], e\SoundCHN2, Camera, me\Collider, 2.0, (640.0 * RoomScale - Sqr(Temp)) * Abs(me\CurrSpeed) * 100)
							me\CurrSpeed = CurveValue(0.0, me\CurrSpeed, Sqr(Temp) * 10.0)
							
							If Temp < PowTwo(130.0 * RoomScale) Then
								For r.Rooms = Each Rooms
									If r\RoomTemplate\Name = "room2_shaft" Then
										GiveAchievement(AchvPD)
										
										SecondaryLightOn = PrevSecondaryLightOn
										PrevSecondaryLightOn = 0.0
										
										me\BlinkTimer = -10.0 : me\LightBlink = 5.0 : me\BlurTimer = 1500.0
										
										PlaySound_Strict(LoadTempSound("SFX\Room\PocketDimension\Exit.ogg"))
										
										TeleportEntity(me\Collider, EntityX(r\Objects[0], True), 0.6, EntityZ(r\Objects[0], True), 0.3, True)
										TeleportToRoom(r)
										
										n_I\Curr106\State = 10000.0 : n_I\Curr106\Idle = 0
										
										de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(r\Objects[0], True), EntityY(r\Objects[0], True), EntityZ(r\Objects[0], True), 270.0, Rnd(360.0), 0.0)
										EntityParent(de\OBJ, e\room\OBJ)
										TeleportEntity(de\OBJ, EntityX(r\Objects[0], True), EntityY(r\Objects[0], True) + 0.6, EntityZ(r\Objects[0], True), 0.0, True, 4.0, True)
										
										For e2.Events = Each Events
											If e2\EventID = e_room2_sl
												e2\EventState3 = 0.0
												UpdateLever(e2\room\RoomLevers[0]\OBJ)
												RotateEntity(e2\room\RoomLevers[0]\OBJ, 0.0, EntityYaw(e2\room\RoomLevers[0]\OBJ), 0.0)
												TurnCheckpointMonitorsOff()
												Exit
											EndIf
										Next
										
										If e\Sound <> 0 Then FreeSound_Strict(e\Sound) : e\Sound = 0
										If e\Sound2 <> 0 Then FreeSound_Strict(e\Sound2) : e\Sound2 = 0
										
										e\EventState = 0.0
										e\EventState3 = 0.0
										e\EventState2 = PD_StartRoom
										Exit
									EndIf
								Next
							EndIf
						EndIf
						;[End Block]
					Case PD_FakeTunnelRoom
						;[Block]
						CurrStepSFX = 0
						ShouldPlay = 1
						
						GiveAchievement(AchvPD)
						
						UpdateDoors()
						
						If e\EventState3 = 0.0 And (e\room\RoomDoors[0]\OpenState > 150.0 Lor e\room\RoomDoors[1]\OpenState > 150.0) Then
							PlaySound_Strict(LoadTempSound("SFX\Horror\Horror16.ogg"))
							me\BlurTimer = 800.0
							e\EventState3 = 1.0
						EndIf
						If EntityY(me\Collider) < 5.0 Then
							PositionEntity(me\Collider, EntityX(me\Collider, True), EntityY(me\Collider, True) - 500.0 * RoomScale, EntityZ(me\Collider, True))
							ResetEntity(me\Collider)
							
							For i = 0 To 1
								e\room\RoomDoors[i]\Open = False
								e\room\RoomDoors[i]\OpenState = 0.0
							Next
							
							achv\Achievement[AchvPD] = False
							
							LoadEventSound(e, "SFX\Room\PocketDimension\Rumble.ogg")
							LoadEventSound(e, "SFX\Room\PocketDimension\PrisonVoices.ogg", 1)
							
							e\EventState3 = 0.0
							e\EventState2 = PD_FourWayRoom
						EndIf
						;[End Block]
					Case PD_TowerRoom
						;[Block]
						If opt\ParticleAmount > 0 Then
							If Rand(800) = 1 Then
								Angle = EntityYaw(Camera, True) + Rnd(150.0, 210.0)
								p.Particles = CreateParticle(PARTICLE_SHADOW, EntityX(me\Collider) + Cos(Angle) * 7.5, 0.0, EntityZ(me\Collider) + Sin(Angle) * 7.5, 4.0, 0.0, 2500.0)
								p\Speed = 0.01 : p\SizeChange = 0.0
								EntityBlend(p\OBJ, 2)
								PointEntity(p\Pvt, Camera)
								TurnEntity(p\Pvt, 0.0, 145.0, 0.0, True)
								TurnEntity(p\Pvt, Rnd(10.0 , 20.0), 0.0, 0.0, True)
							EndIf
						EndIf
						
						If e\EventState3 > 12.0 Then
							n_I\Curr106\Idle = 1
							PositionEntity(n_I\Curr106\Collider, EntityX(e\room\Objects[e\EventState3], True), 0.27, EntityZ(e\room\Objects[e\EventState3], True))
							
							PointEntity(n_I\Curr106\Collider, Camera)
							TurnEntity(n_I\Curr106\Collider, 0.0, Sin(MilliSecs2() / 20.0) * 6.0, 0, True)
							MoveEntity(n_I\Curr106\Collider, 0.0, 0.0, Sin(MilliSecs2() / 15.0) * 0.06)
							
							n_I\Curr106\GravityMult = 0.0 : n_I\Curr106\DropSpeed = 0.0
							PositionEntity(n_I\Curr106\OBJ, EntityX(n_I\Curr106\Collider), EntityY(n_I\Curr106\Collider) - 0.15, EntityZ(n_I\Curr106\Collider))
							ResetEntity(n_I\Curr106\Collider)
							RotateEntity(n_I\Curr106\OBJ, 0.0, EntityYaw(n_I\Curr106\Collider), 0.0)
							
							If Rand(750) = 1 And e\EventState3 > 12.0 Then
								me\BlinkTimer = -10.0
								PlaySound_Strict(HorrorSFX[8])
								e\EventState3 = e\EventState3 - 1.0
							EndIf
							
							If e\EventState3 = 12.0 Then
								me\CameraShake = 1.0
								n_I\Curr106\State = -10.0
								PositionEntity(n_I\Curr106\Collider, EntityX(e\room\Objects[e\EventState3], True), -1.0, EntityZ(e\room\Objects[e\EventState3], True))
								ResetEntity(n_I\Curr106\Collider)
							EndIf
						Else
							n_I\Curr106\State = -10.0 : n_I\Curr106\Idle = 0
						EndIf
						
						If EntityY(me\Collider) < (-1600.0) * RoomScale Then
							; ~ Player is at the exit
							If DistanceSquared(EntityX(e\room\Objects[16], True), EntityX(me\Collider), EntityZ(e\room\Objects[16], True), EntityZ(me\Collider)) < PowTwo(144.0 * RoomScale) Then
								Teleport = True
								Random = Rand(12, 29)
							Else ; ~ Somewhere else, must've fallen down
								If (Not me\Terminated) Then
									PlaySound_Strict(HorrorSFX[8])
									msg\DeathMsg = GetLocalString("death", "106_2")
									me\BlurTimer = 3000.0
									me\Terminated = True
								EndIf
							EndIf
						EndIf
						;[End Block]
				End Select
				
				If Teleport Then
					me\BlinkTimer = -10.0 : me\BlurTimer = 1000.0
					
					Select Random
						Case 1, 2, 3, 4 ; ~ Rotate the player and close by the wall
							;[Block]
							PlaySound_Strict(OldManSFX[3])
							
							Pvt = CreatePivot()
							PositionEntity(Pvt, EntityX(me\Collider), EntityY(me\Collider), EntityZ(me\Collider))
							PointEntity(Pvt, e\room\OBJ)
							MoveEntity(Pvt, 0.0, 0.0, Sqr(Dist) * 1.9)
							PositionEntity(me\Collider, EntityX(Pvt), EntityY(me\Collider) + 0.1, EntityZ(Pvt))
							ResetEntity(me\Collider)
							
							MoveEntity(Pvt, 0.0, 0.0, 0.8)
							PositionEntity(e\room\Objects[10], EntityX(Pvt), 0.1, EntityZ(Pvt))
							RotateEntity(e\room\Objects[10], 0.0, EntityYaw(Pvt), 0.0, True)
							
							FreeEntity(Pvt)
							;[End Block]
						Case 5, 6, 7, 8, 9, 10, 11 ; ~ The 4-way room
							;[Block]
							PlaySound_Strict(OldManSFX[3])
							
							PositionEntity(me\Collider, EntityX(e\room\Objects[8], True), 0.6, EntityZ(e\room\Objects[8], True))
							ResetEntity(me\Collider)
							
							LoadEventSound(e, "SFX\Room\PocketDimension\Rumble.ogg")
							LoadEventSound(e, "SFX\Room\PocketDimension\PrisonVoices.ogg", 1)
							
							e\EventState3 = 0.0
							e\EventState2 = PD_FourWayRoom
							;[End Block]
						Case 12, 13 ; ~ Middle of the large starting room
							;[Block]
							PositionEntity(me\Collider, EntityX(e\room\OBJ), 0.6, EntityZ(e\room\OBJ))
							ResetEntity(me\Collider)
							
							e\EventState3 = 0.0
							e\EventState2 = PD_StartRoom
							;[End Block]
						Case 14, 15 ; ~ The exit room
							;[Block]
							PositionEntity(me\Collider, EntityX(e\room\Objects[8], True) - 400.0 * RoomScale, e\room\y - 300.0 * RoomScale, EntityZ(e\room\Objects[8], True))
							ResetEntity(me\Collider)
							
							e\EventState3 = 0.0
							e\EventState2 = PD_ExitRoom
							;[End Block]
						Case 16, 17, 18, 19, 20, 21
							;[Block]
							Local RoomName$ = ""
							Local LCZ% = False
							
							Select Rand(5)
								Case 1
									;[Block]
									RoomName = "room2_5_hcz"
									;[End Block]
								Case 2
									;[Block]
									RoomName = "room_2_lcz"
									LCZ = True
									;[End Block]
								Case 3
									;[Block]
									RoomName = "room3_lcz"
									LCZ = True
									;[End Block]
								Case 4
									;[Block]
									RoomName = "cont1_106"
									;[End Block]
								Case 5
									;[Block]
									RoomName = "room2_3_hcz"
									;[End Block]
							End Select
							
							Local RoomExist% = False
							
							For r.Rooms = Each Rooms
								If r\RoomTemplate\Name = RoomName Then
									RoomExist = True
									
									If RoomExist Then
										GiveAchievement(AchvPD)
										
										SecondaryLightOn = PrevSecondaryLightOn
										PrevSecondaryLightOn = 0.0
										
										me\LightBlink = 5.0
										
										If RoomName = "cont1_106" Then
											TeleportEntity(me\Collider, EntityX(r\Objects[10], True), 0.4, EntityZ(r\Objects[10], True), 0.3, True)
										Else
											TeleportEntity(me\Collider, EntityX(r\OBJ, True), 0.4, EntityZ(r\OBJ, True), 0.3, True)
										EndIf
										TeleportToRoom(r)
										
										n_I\Curr106\State = 10000.0 : n_I\Curr106\Idle = 0
										
										If (Not LCZ) Then
											For e2.Events = Each Events
												If e2\EventID = e_room2_sl
													e2\EventState3 = 0.0
													UpdateLever(e2\room\RoomLevers[0]\OBJ)
													RotateEntity(e2\room\RoomLevers[0]\OBJ, 0.0, EntityYaw(e2\room\RoomLevers[0]\OBJ), 0.0)
													TurnCheckpointMonitorsOff()
													Exit
												EndIf
											Next
										EndIf
										
										If e\Sound <> 0 Then FreeSound_Strict(e\Sound) : e\Sound = 0
										If e\Sound2 <> 0 Then FreeSound_Strict(e\Sound2) : e\Sound2 = 0
									EndIf
									e\EventState = 0.0
									e\EventState3 = 0.0
									e\EventState2 = PD_StartRoom
									Exit
								EndIf
							Next
							If (Not RoomExist) Then
								PositionEntity(me\Collider, EntityX(e\room\OBJ), 0.6, EntityZ(e\room\OBJ))
								ResetEntity(me\Collider)
							EndIf
							;[End Block]
						Case 22, 23, 24, 25 ; ~ The tower room
							;[Block]
							PositionEntity(me\Collider, EntityX(e\room\Objects[12], True), 0.6, EntityZ(e\room\Objects[12], True))
							ResetEntity(me\Collider)
							
							e\EventState3 = 15.0
							e\EventState2 = PD_TowerRoom
							;[End Block]
						Case 26, 27, 28, 29 ; ~ The fake HCZ tunnel
							;[Block]
							PlaySound_Strict(OldManSFX[3])
							
							PositionEntity(me\Collider, EntityX(e\room\Objects[8], True), e\room\y + 2288.0 * RoomScale, EntityZ(e\room\Objects[8], True))
							ResetEntity(me\Collider)
							
							e\EventState3 = 0.0
							e\EventState2 = PD_FakeTunnelRoom
							;[End Block]
					End Select
					UpdateTimer = 0.0
				EndIf
			Else
				If (Not EntityHidden(e\room\OBJ)) Then HideEntity(e\room\OBJ)
				e\EventState = 0.0
				e\EventState3 = 0.0
				e\EventState2 = PD_StartRoom
			EndIf
		EndIf
	Next
End Function

Type Dummy1499_1
	Field Anim%
	Field OBJ%
End Type

Function UpdateDimension1499%()
	Local e.Events, n.NPCs, r.Rooms, it.Items, du.Dummy1499_1
	Local Tex%, Temp%, Scale#, x%, y%, i%, j%
	
	For e.Events = Each Events
		If e\EventID = e_dimension_1499 Then
			; ~ e\EventState: If player entered dimension (will be resetted after the player leaves it)
			; ~ 0: The player never entered SCP-1499's dimension
			; ~ 1: The player had already entered the dimension at least once
			; ~ 2: The player is in dimension
			
			; ~ e\EventState2: Used to count the amount of times the player has entered the SCP-1499's dimension (for a little spawning event)
			
			; ~ e\EventState3: Variable used for the SCP-1499's church event
			
			If PlayerRoom = e\room Then
				If e\EventState < 2.0 Then
					; ~ SCP-1499's random generator
					If e\EventState = 0.0 Then
						If e\EventStr = "" And QuickLoadPercent = -1 Then
							QuickLoadPercent = 0
							QuickLoad_CurrEvent = e
							e\EventStr = "Load0"
						EndIf
					Else
						e\EventState = 2.0
					EndIf
					
					Local Value% = Rand(2, 3)
					
					If e\EventState2 = Value Lor e\EventState2 = 4.0 Then
						For i = -1 To 1
							For j = -1 To 1
								If i <> 0 And j <> 0 Then
									n.NPCs = CreateNPC(NPCType1499_1, EntityX(me\Collider) + (0.75 * i), EntityY(me\Collider) + 0.05, EntityZ(me\Collider) + (0.75 * j))
									n\State = 2.0
									PointEntity(n\Collider, me\Collider)
									RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider), 0.0)
								ElseIf i <> 0 Lor j <> 0 Then
									n.NPCs = CreateNPC(NPCType1499_1, EntityX(me\Collider) + i, EntityY(me\Collider) + 0.05, EntityZ(me\Collider) + j)
									n\State = 2.0
									PointEntity(n\Collider, me\Collider)
									RotateEntity(n\Collider, 0.0, EntityYaw(n\Collider), 0.0)
								EndIf
							Next
						Next
						e\EventState2 = 5.0
					EndIf
					If e\EventState3 < 70.0 * 30.0 Then
						; ~ King
						e\room\NPC.NPCs[0] = CreateNPC(NPCType1499_1, e\room\x - 1917.0 * RoomScale, e\room\y + 1904.0 * RoomScale, e\room\z + 2308.0 * RoomScale)
						e\room\NPC[0]\PrevState = 2 : e\room\NPC[0]\Angle = 270.0
						RotateEntity(e\room\NPC[0]\Collider, 0.0, e\room\NPC[0]\Angle, 0.0)
						Tex = LoadTexture_Strict("GFX\NPCs\scp_1499_1_king.png")
						If opt\Atmosphere Then TextureBlend(Tex, 5)
						EntityTexture(e\room\NPC[0]\OBJ, Tex)
						DeleteSingleTextureEntryFromCache(Tex)
						; ~ Guard next to king
						e\room\NPC.NPCs[1] = CreateNPC(NPCType1499_1, e\room\x - 1917.0 * RoomScale, e\room\y + 1904.0 * RoomScale, e\room\z + 2052.0 * RoomScale)
						e\room\NPC[1]\PrevState = 1 : e\room\NPC[1]\Angle = 270.0
						RotateEntity(e\room\NPC[1]\Collider, 0.0, e\room\NPC[1]\Angle, 0.0)
						; ~ Guards at the entrance to church
						e\room\NPC.NPCs[2] = CreateNPC(NPCType1499_1, e\room\x + 4055.0 * RoomScale, e\room\y + 240.0 * RoomScale, e\room\z + 1884.0 * RoomScale)
						e\room\NPC[2]\PrevState = 3 : e\room\NPC[2]\Angle = 270.0
						RotateEntity(e\room\NPC[2]\Collider, 0.0, e\room\NPC[2]\Angle, 0.0)
						e\room\NPC.NPCs[3] = CreateNPC(NPCType1499_1, e\room\x + 4055.0 * RoomScale, e\room\y + 240.0 * RoomScale, e\room\z + 2876.0 * RoomScale)
						e\room\NPC[3]\PrevState = 3 : e\room\NPC[3]\Angle = 270.0
						RotateEntity(e\room\NPC[3]\Collider, 0.0, e\room\NPC[3]\Angle, 0.0)
						e\room\NPC[2]\Target = e\room\NPC[3]
						e\room\NPC[3]\Target = e\room\NPC[2]
						; ~ More guards
						e\room\NPC.NPCs[4] = CreateNPC(NPCType1499_1, e\room\x - 1877.0 * RoomScale, e\room\y + 192.0 * RoomScale, e\room\z + 1071.0 * RoomScale)
						e\room\NPC[4]\PrevState = 3 : e\room\NPC[4]\Angle = 270.0
						RotateEntity(e\room\NPC[4]\Collider, 0.0, e\room\NPC[4]\Angle, 0.0)
						e\room\NPC.NPCs[5] = CreateNPC(NPCType1499_1, e\room\x - 1877.0 * RoomScale, e\room\y + 192.0 * RoomScale, e\room\z + 3503.0 * RoomScale)
						e\room\NPC[5]\PrevState = 3 : e\room\NPC[5]\Angle = 270.0
						RotateEntity(e\room\NPC[5]\Collider, 0.0, e\room\NPC[5]\Angle, 0.0)
						e\room\NPC[4]\Target = e\room\NPC[5]
						e\room\NPC[5]\Target = e\room\NPC[4]
						; ~ Guard at stairs
						n.NPCs = CreateNPC(NPCType1499_1, e\room\x - 2761.0 * RoomScale, e\room\y + 240.0 * RoomScale, e\room\z + 3204.0 * RoomScale)
						n\PrevState = 1 : n\Angle = 180.0 : n\Speed = 0.0
						RotateEntity(n\Collider, 0.0, n\Angle, 0.0)
						; ~ SCP-1499-1 instances praying in church
						; ~ Zone 1
						For x = 0 To 7
							For y = 0 To 2
								du.Dummy1499_1 = New Dummy1499_1
								For n.NPCs = Each NPCs
									If n\NPCType = NPCType1499_1 And n\PrevState <> 2 Then
										du\OBJ = CopyEntity(n\OBJ)
										Exit
									EndIf
								Next
								Scale = IniGetFloat(NPCsFile, "SCP-1499-1", "Scale") / 4.0 * Rnd(0.8, 1.0)
								ScaleEntity(du\OBJ, Scale, Scale, Scale)
								EntityFX(du\OBJ, 1)
								du\Anim = Rand(False, True)
								PositionEntity(du\OBJ, Max(Min((e\room\x + (1887.0 - ((2560.0 / 7.0) * x)) * RoomScale) + Rnd(-0.5, 0.5), e\room\x + 1887.0 * RoomScale), e\room\x - 873.0 * RoomScale), e\room\y, Max(Min((e\room\z + (1796.0 - (384.0 * y)) * RoomScale) + Rnd(-0.5, 0.5), e\room\z + 1796.0 * RoomScale), e\room\z + 1028.0 * RoomScale))
								RotateEntity(du\OBJ, 0.0, 270.0, 0.0)
								EntityAutoFade(du\OBJ, 25.0, 39.0)
							Next
						Next
						; ~ Zone 2
						For x = 0 To 6
							For y = 0 To 2
								du.Dummy1499_1 = New Dummy1499_1
								For n.NPCs = Each NPCs
									If n\NPCType = NPCType1499_1 And n\PrevState <> 2 Then
										du\OBJ = CopyEntity(n\OBJ)
										Exit
									EndIf
								Next
								Scale = IniGetFloat(NPCsFile, "SCP-1499-1", "Scale") / 4.0 * Rnd(0.8, 1.0)
								ScaleEntity(du\OBJ, Scale, Scale, Scale)
								EntityFX(du\OBJ, 1)
								du\Anim = Rand(False, True)
								PositionEntity(du\OBJ, Max(Min((e\room\x + (1375.0 - ((2048.0 / 6.0) * x)) * RoomScale) + Rnd(-0.5, 0.5), e\room\x + 1375.0 * RoomScale), e\room\x - 873.0 * RoomScale), e\room\y, Max(Min((e\room\z + (3588.0 - (384.0 * y)) * RoomScale) + Rnd(-0.5, 0.5), e\room\z + 3588.0 * RoomScale), e\room\z + 2820.0 * RoomScale))
								RotateEntity(du\OBJ, 0.0, 270.0, 0.0)
								EntityAutoFade(du\OBJ, 25.0, 39.0)
							Next
						Next
					Else
						If (Not EntityHidden(e\room\Objects[17])) Then HideEntity(e\room\Objects[17])
					EndIf
					
					For i = 0 To 14
						n.NPCs = CreateNPC(NPCType1499_1, EntityX(me\Collider) + Rnd(-20.0, 20.0), EntityY(me\Collider) + 0.1, EntityZ(me\Collider) + Rnd(-20.0, 20.0))
						n\Angle = Rnd(360.0) : n\State2 = 0.0
						If EntityDistanceSquared(n\Collider, me\Collider) < 100.0 Then n\State = 2.0
					Next
				EndIf
				
				CameraFogRange(Camera, 40.0, 80.0)
				CameraRange(Camera, 0.05, 90.0)
				
				For r.Rooms = Each Rooms
					HideRoomsNoColl(r)
				Next
				ShowRoomsNoColl(e\room)
				If QuickLoadPercent = 100 Lor QuickLoadPercent = -1 Then
					UpdateChunks(e\room, 15)
					If EntityHidden(I_1499\Sky) Then ShowEntity(I_1499\Sky)
					UpdateSky(I_1499\Sky)
					ShouldPlay = 18
					If EntityY(me\Collider) < 800.0 Then
						PositionEntity(me\Collider, EntityX(me\Collider), 800.5, EntityZ(me\Collider), True)
						ResetEntity(me\Collider)
					EndIf
					; ~ A hacky fix to make items not fall that are in SCP-1499's dimension
					For it.Items = Each Items
						If EntityY(it\Collider) > 750.0 Then
							If EntityY(it\Collider) < 800.0 Then
								PositionEntity(it\Collider, EntityX(it\Collider), 800.5, EntityZ(it\Collider))
								ResetEntity(it\Collider)
							EndIf
						EndIf
					Next
					For du.Dummy1499_1 = Each Dummy1499_1
						If e\EventState3 < 70.0 * 30.0 Then
							If du\Anim Then
								If AnimTime(du\OBJ) <= 360.5 Then
									Animate2(du\OBJ, AnimTime(du\OBJ), 321.0, 361.0, 0.2, False)
								ElseIf AnimTime(du\OBJ) > 361.5 And AnimTime(du\OBJ) <= 401.5 Then
									Animate2(du\OBJ, AnimTime(du\OBJ), 362.0, 402.0, 0.2, False)
								Else
									Temp = Rand(False, True)
									If Temp Then
										SetAnimTime(du\OBJ, 362.0)
									Else
										SetAnimTime(du\OBJ, 321.0)
									EndIf
								EndIf
							Else
								If AnimTime(du\OBJ) <= 452.5 Then
									Animate2(du\OBJ, AnimTime(du\OBJ), 413.0, 453.0, 0.2, False)
								ElseIf AnimTime(du\OBJ) > 453.5 And AnimTime(du\OBJ) <= 497.5 Then
									Animate2(du\OBJ, AnimTime(du\OBJ), 454.0, 498.0, 0.2, False)
								Else
									Temp = Rand(False, True)
									If Temp Then
										SetAnimTime(du\OBJ, 454.0)
									Else
										SetAnimTime(du\OBJ, 413.0)
									EndIf
								EndIf
							EndIf
						Else
							If du\Anim Then
								If AnimTime(du\OBJ) <= 411.5 And AnimTime(du\OBJ) > 320.5 Then
									Animate2(du\OBJ, AnimTime(du\OBJ), 403.0, 412.0, 0.2, False)
								Else
									Animate2(du\OBJ, AnimTime(du\OBJ), 296.0, 320.0, 0.2)
								EndIf
							Else
								If AnimTime(du\OBJ) <= 507.5 And AnimTime(du\OBJ) > 320.5 Then
									Animate2(du\OBJ, AnimTime(du\OBJ), 499.0, 508.0, 0.2, False)
								Else
									Animate2(du\OBJ, AnimTime(du\OBJ), 296.0, 320.0, 0.2)
								EndIf
							EndIf
							
							Local Pvt% = CreatePivot()
							
							PositionEntity(Pvt, EntityX(du\OBJ), EntityY(du\OBJ), EntityZ(du\OBJ), True)
							PointEntity(Pvt, me\Collider)
							RotateEntity(du\OBJ, 0.0, CurveAngle(EntityYaw(Pvt), EntityYaw(du\OBJ) - 180.0, 10.0) + 180.0, 0.0)
							FreeEntity(Pvt)
						EndIf
					Next
					; ~ Player is inside the church
					If e\EventState3 < 70.0 * 10.0 Then
						If Abs(EntityX(me\Collider) - (e\room\x - 56.0 * RoomScale)) < 2160.0 * RoomScale Then
							If Abs(EntityZ(me\Collider) - (e\room\z + 2287.0 * RoomScale)) < 1408.0 * RoomScale Then e\EventState3 = e\EventState3 + fps\Factor[0]
						EndIf
					ElseIf e\EventState3 >= 70.0 * 10.0 And e\EventState3 < 70.0 * 20.0 Then
						For i = 0 To 1
							e\room\NPC[i]\Reload = 1.0
						Next
						e\EventState3 = 70.0 * 20.0
					ElseIf e\EventState3 = 70.0 * 20.0
						If e\room\NPC[0]\Frame > 854.5 Then
							For i = 2 To 5
								If i = 2 Then
									LoadNPCSound(e\room\NPC[i], "SFX\SCP\1499\Triggered.ogg")
									e\room\NPC[i]\SoundCHN = PlaySound2(e\room\NPC[i]\Sound, Camera, e\room\NPC[i]\Collider, 50.0)
								EndIf
								e\room\NPC[i]\State = 1.0
								SetNPCFrame(e\room\NPC[i], 203.0)
							Next
							e\EventState3 = 70.0 * 30.0
						EndIf
					EndIf
					
					If e\room\NPC[0] <> Null Then
						If EntityHidden(e\room\Objects[17]) Then ShowEntity(e\room\Objects[17])
						If e\EventState3 < 70.0 * 30.0 Then
							ShouldPlay = 66
							If NowPlaying = 66 Then
								If (Not e\SoundCHN) Then
									e\Sound2 = LoadSound_Strict("SFX\Music\HaveMercyOnMe(Choir).ogg")
									e\SoundCHN = StreamSound_Strict("SFX\Music\HaveMercyOnMe(NoChoir).ogg", opt\MusicVolume * opt\MasterVolume)
									e\SoundCHN_IsStream = True
								EndIf
							EndIf
							If e\Sound2 <> 0 Then e\SoundCHN2 = LoopSound2(e\Sound2, e\SoundCHN2, Camera, e\room\Objects[16], 10.0, opt\MusicVolume * opt\MasterVolume)
						Else
							ShouldPlay = 19
							If e\SoundCHN <> 0 Then StopStream_Strict(e\SoundCHN) : e\SoundCHN = 0 : e\SoundCHN_IsStream = False
							If ChannelPlaying(e\SoundCHN2) Then StopChannel(e\SoundCHN2) : e\SoundCHN2 = 0
							If e\Sound2 <> 0 Then FreeSound_Strict(e\Sound2) : e\Sound2 = 0
						EndIf
					EndIf
					
					If EntityDistanceSquared(me\Collider, e\room\OBJ) > 1600.0 Then
						For du.Dummy1499_1 = Each Dummy1499_1
							If (Not EntityHidden(du\OBJ)) Then HideEntity(du\OBJ)
						Next
					Else
						For du.Dummy1499_1 = Each Dummy1499_1
							If EntityHidden(du\OBJ) Then ShowEntity(du\OBJ)
						Next
					EndIf
				Else
					me\DropSpeed = 0.0
				EndIf
				CurrStepSFX = 0
				PlayerFallingPickDistance = 0.0
			Else
				If e\EventState = 2.0 Then
					If e\SoundCHN <> 0 Then StopStream_Strict(e\SoundCHN) : e\SoundCHN = 0 : e\SoundCHN_IsStream = False
					StopChannel(e\SoundCHN2) : e\SoundCHN2 = 0
					HideEntity(I_1499\Sky)
					HideChunks()
					For n.NPCs = Each NPCs
						If n\NPCType = NPCType1499_1 Then RemoveNPC(n)
					Next
					For du.Dummy1499_1 = Each Dummy1499_1
						FreeEntity(du\OBJ) : du\OBJ = 0
						Delete(du)
					Next
					If e\Sound2 <> 0 Then FreeSound_Strict(e\Sound2) : e\Sound2 = 0
					If e\EventState3 < 70.0 * 30.0 Then e\EventState3 = 0.0
					e\EventState = 1.0
				EndIf
			EndIf
		EndIf
	Next
End Function

Function UpdateEndings%()
	Local e.Events, e2.Events, n.NPCs, r.Rooms, p.Particles, de.Decals, em.Emitters
	Local Dist#, i%, Pvt%, Temp%, xTemp#, zTemp#, Angle#, OBJ%
	
	For e.Events = Each Events
		Select e\EventID
			Case e_gate_b
				;[Block]
				If PlayerRoom = e\room Then
					If e\EventState = 0.0 Then
						RenderLoading(0, GetLocalString("loading", "ending"))
						
						For n.NPCs = Each NPCs
							If n <> n_I\Curr106 And n <> n_I\Curr173 Then RemoveNPC(n)
						Next
						n_I\Curr096 = Null
						n_I\Curr513_1 = Null
						n_I\Curr049 = Null
						
						e\room\NPC[0] = CreateNPC(NPCTypeApache, e\room\x, 100.0, e\room\z)
						e\room\NPC[0]\State = 1.0
						
						e\room\NPC[1] = CreateNPC(NPCTypeGuard, EntityX(e\room\Objects[2], True), EntityY(e\room\Objects[2], True), EntityZ(e\room\Objects[2], True))
						e\room\NPC[1]\State = 0.0 : e\room\NPC[1]\State2 = 10.0
						
						e\room\Objects[0] = LoadMesh_Strict("GFX\Map\exit1terrain.b3d", e\room\OBJ)
						ScaleEntity(e\room\Objects[0], RoomScale, RoomScale, RoomScale, True)
						RotateEntity(e\room\Objects[0], 0.0, e\room\Angle, 0.0, True)
						PositionEntity(e\room\Objects[0], e\room\x + 4356.0 * RoomScale, e\room\y - 1017.0 * RoomScale, e\room\z + 2588.0 * RoomScale, True)
						
						RenderLoading(60, GetLocalString("loading", "ending"))
						
						Sky = CreateSky("GFX\Map\Textures\sky")
						RotateEntity(Sky, 0.0, e\room\Angle - 90.0, 0.0)
						
						ResetEntity(me\Collider)
						RotateEntity(me\Collider, 0.0, EntityYaw(me\Collider) + (e\room\Angle + 180.0), 0.0)
						
						RenderLoading(90, GetLocalString("loading", "ending"))
						
						SecondaryLightOn = True
						
						HideDistance = 68.0
						
						CreateConsoleMsg("")
						CreateConsoleMsg(GetLocalString("misc", "telewarn"), 255, 0, 0)
						CreateConsoleMsg("")
						
						e\EventState = 1.0
						
						RenderLoading(100)
					Else
						UpdateSky(Sky)
						
						CanSave = 1
						
						For r.Rooms = Each Rooms
							HideRoomsNoColl(r)
						Next
						ShowRoomsNoColl(e\room)
						
						If e\EventState < 2.0 And me\SelectedEnding = -1 Then 
							If e\room\NPC[0]\State = 2.0 Then
								ShouldPlay = 6
							Else
								e\EventState2 = ((e\EventState2 + fps\Factor[0]) Mod 3600.0)
								PositionEntity(e\room\NPC[0]\Collider, EntityX(e\room\OBJ, True) + Cos(e\EventState2 / 10.0) * 6000.0 * RoomScale, e\room\y + 3216.0 * RoomScale, EntityZ(e\room\OBJ, True) + Sin(e\EventState2 / 10.0) * 6000.0 * RoomScale)
								RotateEntity(e\room\NPC[0]\Collider, 7.0, (e\EventState2 / 10.0), 20.0)
								ShouldPlay = 5
							EndIf
							
							If EntityDistanceSquared(me\Collider, e\room\Objects[5]) < PowTwo(320.0 * RoomScale) Then
								e\EventState = 2.0
								For i = 2 To 3
									e\room\RoomDoors[i]\Open = False : e\room\RoomDoors[i]\Locked = 1
								Next
								
								e\room\NPC[2] = CreateNPC(NPCTypeApache, EntityX(e\room\Objects[11], True), EntityY(e\room\Objects[11], True) + 0.5, EntityZ(e\room\Objects[11], True))
								e\room\NPC[2]\State = 3.0
								
								e\room\NPC[3] = CreateNPC(NPCTypeApache, EntityX(e\room\Objects[4], True), EntityY(e\room\Objects[4], True) - 2.0, EntityZ(e\room\Objects[4], True))
								e\room\NPC[3]\State = 3.0
								
								e\room\NPC[0]\State = 3.0
								
								If e\room\NPC[1] <> Null Then RemoveNPC(e\room\NPC[1])
								PlayAnnouncement("SFX\Ending\GateB\682Battle.ogg")
							EndIf
						Else
							ShouldPlay = 6
							e\EventState = e\EventState + fps\Factor[0]
							
							If e\EventState < 70.0 * 40.0 Then
								e\room\NPC[0]\EnemyX = EntityX(e\room\Objects[6], True) + Sin(MilliSecs2() / 25.0) * 3.0
								e\room\NPC[0]\EnemyY = EntityY(e\room\Objects[6], True) + Cos(MilliSecs2() / 85.0) + 9.0
								e\room\NPC[0]\EnemyZ = EntityZ(e\room\Objects[6], True) + Cos(MilliSecs2() / 25.0) * 3.0
								
								e\room\NPC[2]\EnemyX = EntityX(e\room\Objects[6], True) + Sin(MilliSecs2() / 23.0) * 3.0
								e\room\NPC[2]\EnemyY = EntityY(e\room\Objects[6], True) + Cos(MilliSecs2() / 83.0) + 5.0
								e\room\NPC[2]\EnemyZ = EntityZ(e\room\Objects[6], True) + Cos(MilliSecs2() / 23.0) * 3.0
								
								If e\room\NPC[3]\State = 3.0 Then 
									e\room\NPC[3]\EnemyX = EntityX(e\room\Objects[6], True) + Sin(MilliSecs2() / 20.0) * 3.0
									e\room\NPC[3]\EnemyY = EntityY(e\room\Objects[6], True) + Cos(MilliSecs2() / 80.0) + 3.5
									e\room\NPC[3]\EnemyZ = EntityZ(e\room\Objects[6], True) + Cos(MilliSecs2() / 20.0) * 3.0
								EndIf
							EndIf
						EndIf
						
						If e\EventState > 70.0 * 0.6 And e\EventState < 70.0 * 42.2 Then
							If e\EventState < 70.0 * 0.7 Then
								me\CameraShake = 0.5
							ElseIf e\EventState > 70.0 * 3.2 And e\EventState < 70.0 * 3.3
								me\CameraShake = 0.5
							ElseIf e\EventState > 70.0 * 6.1 And e\EventState < 70.0 * 6.2
								me\CameraShake = 0.5
							ElseIf e\EventState < 70.0 * 10.8 And e\EventState < 70.0 * 10.9
								me\CameraShake = 0.5
							ElseIf e\EventState > 70.0 * 12.1 And e\EventState < 70.0 * 12.3
								me\CameraShake = 1.0
							ElseIf e\EventState > 70.0 * 13.3 And e\EventState < 70.0 * 13.5
								me\CameraShake = 1.5
							ElseIf e\EventState > 70.0 * 16.5 And e\EventState < 70.0 * 18.5
								me\CameraShake = 3.0
							ElseIf e\EventState > 70.0 * 21.5 And e\EventState < 70.0 * 24.0
								me\CameraShake = 2.0
							ElseIf e\EventState > 70.0 * 25.5 And e\EventState < 70.0 * 27.0
								me\CameraShake = 2.0
							ElseIf e\EventState > 70.0 * 31.0 And e\EventState < 70.0 * 31.5
								me\CameraShake = 0.5
							ElseIf e\EventState > 70.0 * 35.0 And e\EventState < 70.0 * 36.5
								me\CameraShake = 1.5
								If e\EventState - fps\Factor[0] <= 70.0 * 35.0 Then
									e\SoundCHN = StreamSound_Strict("SFX\Ending\GateB\DetonatingAlphaWarheads.ogg", opt\SFXVolume * opt\MasterVolume, 0)
									e\SoundCHN_IsStream = True
								EndIf
							ElseIf e\EventState > 70.0 * 39.5 And e\EventState < 70.0 * 39.8
								me\CameraShake = 1.0
							ElseIf e\EventState > 70.0 * 42.0
								me\CameraShake = 0.5
								
								; ~ Helicopters leave
								e\room\NPC[0]\EnemyX = EntityX(e\room\Objects[9], True) + 4.0
								e\room\NPC[0]\EnemyY = EntityY(e\room\Objects[9], True) + 4.0
								e\room\NPC[0]\EnemyZ = EntityZ(e\room\Objects[9], True) + 4.0
								
								e\room\NPC[2]\EnemyX = EntityX(e\room\Objects[9], True)
								e\room\NPC[2]\EnemyY = EntityY(e\room\Objects[9], True)
								e\room\NPC[2]\EnemyZ = EntityZ(e\room\Objects[9], True)
							EndIf
						EndIf
						
						If e\EventState >= 70.0 * 45.0 Then
							If e\EventState < 70.0 * 75.0 Then
								If (Not e\SoundCHN2) Then
									e\SoundCHN2 = StreamSound_Strict("SFX\Ending\GateB\Siren.ogg", opt\SFXVolume * opt\MasterVolume)
									e\SoundCHN2_IsStream = True
								EndIf
							Else
								If me\SelectedEnding = -1 Then
									ShouldPlay = 66
									
									If e\SoundCHN <> 0 Then StopStream_Strict(e\SoundCHN) : e\SoundCHN = 0 : e\SoundCHN_IsStream = False
									If e\SoundCHN2 <> 0 Then StopStream_Strict(e\SoundCHN2) : e\SoundCHN2 = 0 : e\SoundCHN2_IsStream = False
									
									Temp = True
									For e2.Events = Each Events
										If e2\EventID = e_room2_nuke Then
											Temp = e2\EventState
											Exit
										EndIf
									Next
									
									If Temp = 1.0 Then ; ~ Explode
										me\SelectedEnding = Ending_B2
										me\ExplosionTimer = Max(me\ExplosionTimer, 0.1)
									Else
										me\SelectedEnding = Ending_B1
										
										PlayAnnouncement("SFX\Ending\GateB\AlphaWarheadsFail.ogg")
										
										n.NPCs = CreateNPC(NPCTypeMTF, EntityX(e\room\Objects[8], True), EntityY(e\room\Objects[8], True) + 0.29, EntityZ(e\room\Objects[8], True))
										e\room\NPC[4] = n
										n.NPCs = CreateNPC(NPCTypeMTF, EntityX(e\room\RoomDoors[2]\OBJ, True), EntityY(e\room\RoomDoors[2]\OBJ, True) + 0.29, (EntityZ(e\room\RoomDoors[2]\OBJ, True) + EntityZ(e\room\RoomDoors[3]\OBJ, True)) / 2.0)
										e\room\NPC[5] = n
										
										For i = 4 To 5
											e\room\NPC[i]\State = 10.0
										Next
										
										e\EventState = 70.0 * 85.0
									EndIf
								Else
									If me\SelectedEnding = Ending_B1 Then
										e\room\NPC[0]\EnemyX = EntityX(e\room\Objects[6], True) + Sin(MilliSecs2() / 25.0) * 3.0
										e\room\NPC[0]\EnemyY = EntityY(e\room\Objects[6], True) + Cos(MilliSecs2() / 85.0) + 9.0
										e\room\NPC[0]\EnemyZ = EntityZ(e\room\Objects[6], True) + Cos(MilliSecs2() / 25.0) * 3.0
										
										e\room\NPC[2]\EnemyX = EntityX(e\room\Objects[6], True) + Sin(MilliSecs2() / 23.0) * 3.0
										e\room\NPC[2]\EnemyY = EntityY(e\room\Objects[6], True) + Cos(MilliSecs2() / 83.0) + 5.0
										e\room\NPC[2]\EnemyZ = EntityZ(e\room\Objects[6], True) + Cos(MilliSecs2() / 23.0) * 3.0
										
										e\room\RoomDoors[4]\Open = True : e\room\RoomDoors[4]\Locked = 0
										
										me\Playable = False
										
										For i = 4 To 5
											If e\room\NPC[i]\State3 = 70.0 * 4.0 Then Temp = True
										Next
										
										If e\EventState > 70.0 * 92.0 And Temp Then
											ClearCheats()
											
											ShouldPlay = 0
											
											PlaySound_Strict(LoadTempSound("SFX\Ending\GateB\Gunshot.ogg"))
											
											me\LightFlash = 1.0
											me\Terminated = True
											msg\DeathMsg = ""
											me\BlinkTimer = -10.0
											
											For n.NPCs = Each NPCs
												If n\NPCType = NPCTypeMTF Then RemoveNPC(n)
											Next
											
											RemoveEvent(e)
											Exit
										EndIf
									EndIf
								EndIf
							EndIf
						EndIf
						
						If e\EventState > 70.0 * 26.5 Then
							If e\EventState3 = 0.0 Then
								e\room\Objects[7] = LoadMesh_Strict("GFX\NPCs\scp_682_arm.b3d")
								ScaleEntity(e\room\Objects[7], 0.15, 0.15, 0.15)
								Temp = (Min(((EntityDistance(e\room\NPC[3]\Collider, me\Collider) / RoomScale) - 3000.0) / 4.0, 1000.0) + 1408.0) * RoomScale
								PositionEntity(e\room\Objects[7], EntityX(e\room\NPC[3]\Collider), e\room\y + 1408.0 * RoomScale, EntityZ(e\room\NPC[3]\Collider))
								RotateEntity(e\room\Objects[7], 0.0, e\room\Angle + Rnd(-10.0, 10.0), 0.0, True)
								TurnEntity(e\room\Objects[7], 0.0, 0.0, 180.0)
								e\EventState3 = 1.0
							Else
								If e\room\Objects[7] <> 0 Then
									If WrapAngle(EntityRoll(e\room\Objects[7])) < 340.0 Then 
										Angle = WrapAngle(EntityRoll(e\room\Objects[7]))
										TurnEntity(e\room\Objects[7], 0.0, 0.0, (5.0 + Abs(Sin(Angle)) * 2.0) * fps\Factor[0])
										If Angle < 270.0 And WrapAngle(EntityRoll(e\room\Objects[7])) >= 270.0 Then
											PlaySound_Strict(LoadTempSound("SFX\Character\Apache\Crash1.ogg"))
											e\room\NPC[3]\State = 4.0 : e\room\NPC[3]\State2 = 1.0
											e\room\NPC[3]\EnemyX = EntityX(e\room\Objects[4], True)
											e\room\NPC[3]\EnemyY = EntityY(e\room\Objects[4], True) - 2.5
											e\room\NPC[3]\EnemyZ = EntityZ(e\room\Objects[4], True)
											
											em.Emitters = CreateEmitter(EntityX(e\room\NPC[3]\Collider), EntityY(e\room\NPC[3]\Collider), EntityZ(e\room\NPC[3]\Collider), 0)
											em\room = PlayerRoom : em\RandAngle = 45.0 : em\Gravity = -0.18 : em\LifeTime = 400.0 : em\SizeChange = Rnd(0.005, 0.007) : em\AlphaChange = -0.004
											TurnEntity(em\OBJ, (-80.0) + 20.0 * i, 0.0, 0.0)
											EntityParent(em\OBJ, e\room\NPC[3]\Collider)
											
											If opt\ParticleAmount > 0 Then
												For i = 0 To (3 + (4 * (opt\ParticleAmount - 1)))
													p.Particles = CreateParticle(PARTICLE_BLACK_SMOKE, EntityX(e\room\NPC[3]\Collider), EntityY(e\room\NPC[3]\Collider), EntityZ(e\room\NPC[3]\Collider), Rnd(0.5, 1.0), -0.1, 200.0)
													p\Speed = 0.01 : p\SizeChange = 0.01 : p\Alpha = 1.0 : p\AlphaChange = -0.005
													RotateEntity(p\Pvt, Rnd(360.0), Rnd(360.0), 0.0)
													MoveEntity(p\Pvt, 0.0, 0.0, 0.3)
												Next
												
												For i = 0 To (6 + (6 * (opt\ParticleAmount - 1)))
													p.Particles = CreateParticle(PARTICLE_BLACK_SMOKE, EntityX(e\room\NPC[3]\Collider), EntityY(e\room\NPC[3]\Collider), EntityZ(e\room\NPC[3]\Collider), 0.02, 0.003, 200.0)
													p\Speed = 0.04 : p\Alpha = 1.0 : p\AlphaChange = -0.005
													RotateEntity(p\Pvt, Rnd(360.0), Rnd(360.0), 0.0)
												Next
											EndIf
										EndIf
									Else
										FreeEntity(e\room\Objects[7]) : e\room\Objects[7] = 0
									EndIf
								EndIf
							EndIf
						EndIf
					EndIf
					
					Angle = Max(Sin(EntityYaw(me\Collider)), 0.0)
					
					If opt\ParticleAmount > 0 Then
						If Rand(3) = 1 Then
							p.Particles = CreateParticle(PARTICLE_DUST, EntityX(Camera) + Rnd(-2.0, 2.0), EntityY(Camera) + Rnd(0.9, 2.0), EntityZ(Camera) + Rnd(-2.0, 2.0), 0.006, 0.0, 300.0)
							p\Speed = Rnd(0.002, 0.003) : p\SizeChange = -0.00001
							RotateEntity(p\Pvt, Rnd(-20.0, 20.0), e\room\Angle - 90.0 + Rnd(-15.0, 15.0), 0.0, 0.0)
						EndIf
					EndIf
					
					If e\room\NPC[1] <> Null Then
						; ~ Helicopter spots or player is within range --> Start shooting
						If EntityDistanceSquared(e\room\NPC[1]\Collider, me\Collider) < 225.0 And (Not chs\NoTarget) Then
							e\room\NPC[1]\State = 1.0
							e\room\NPC[1]\State3 = 1.0
						Else
							e\room\NPC[1]\State = 0.0
							e\room\NPC[1]\State3 = 0.0
						EndIf
						
						; ~ Below roof or inside catwalk --> Stop shooting
						If EntityDistanceSquared(e\room\NPC[1]\Collider, me\Collider) < 79.21 Lor EntityDistanceSquared(e\room\Objects[3], me\Collider) < 285.61 Lor chs\NoTarget Then
							e\room\NPC[1]\State3 = 0.0
						Else
							e\room\NPC[1]\State3 = 1.0
						EndIf
					EndIf
				Else
					If (Not EntityHidden(e\room\OBJ)) Then HideEntity(e\room\OBJ)
				EndIf
				;[End Block]
			Case e_gate_a
				;[Block]
				If PlayerRoom = e\room Then
					If e\EventState = 0.0 Then
						RenderLoading(0, GetLocalString("loading", "ending"))
						
						For n.NPCs = Each NPCs
							If n <> n_I\Curr106 And n <> n_I\Curr173 Then RemoveNPC(n)
						Next
						n_I\Curr096 = Null
						n_I\Curr513_1 = Null
						n_I\Curr049 = Null
						
						For i = 2 To 4
							e\room\NPC[i] = CreateNPC(NPCTypeApache, e\room\x, e\room\y + 11.0, e\room\z)
							e\room\NPC[i]\State = (Not n_I\Curr106\Contained)
						Next
						
						For i = 0 To 1
							e\room\NPC[i] = CreateNPC(NPCTypeGuard, EntityX(e\room\Objects[i + 5], True), EntityY(e\room\Objects[i + 5], True), EntityZ(e\room\Objects[i + 5], True))
							e\room\NPC[i]\State = 0.0
							PointEntity(e\room\NPC[i]\Collider, e\room\Objects[3])
						Next
						
						For i = 5 To 8
							e\room\NPC[i] = CreateNPC(NPCTypeMTF, EntityX(e\room\Objects[i + ((i < 7) * 2)], True) + ((i > 6) * 0.8), EntityY(e\room\Objects[i + ((i < 7) * 2)], True), EntityZ(e\room\Objects[i + ((i < 7) * 2)], True) + ((i > 6) * 0.8))
							e\room\NPC[i]\State = 5.0
							e\room\NPC[i]\PrevState = 1
							PointEntity(e\room\NPC[i]\Collider, e\room\Objects[3])
						Next
						
						If n_I\Curr106\Contained Then
							e\room\RoomDoors[0]\Locked = 1
							
							PositionEntity(e\room\NPC[5]\Collider, EntityX(e\room\Objects[15], True), EntityY(e\room\Objects[15], True), EntityZ(e\room\Objects[15], True), True)
							ResetEntity(e\room\NPC[5]\Collider)
						EndIf
						
						RenderLoading(30, GetLocalString("loading", "ending"))
						
						Sky = CreateSky("GFX\Map\Textures\sky")
						RotateEntity(Sky, 0.0, e\room\Angle, 0.0)
						
						RenderLoading(60, GetLocalString("loading", "ending"))
						
						e\room\Objects[0] = LoadRMesh("GFX\Map\gate_a_tunnel.rmesh", Null)
						PositionEntity(e\room\Objects[0], EntityX(e\room\OBJ, True), EntityY(e\room\OBJ, True), EntityZ(e\room\OBJ, True))
						ScaleEntity(e\room\Objects[0], RoomScale, RoomScale, RoomScale)
						EntityType(e\room\Objects[0], HIT_MAP)
						EntityPickMode(e\room\Objects[0], 2)
						EntityParent(e\room\Objects[0], e\room\OBJ)
						
						e\room\Objects[9] = LoadMesh_Strict("GFX\Map\Props\lightgunbase.b3d")
						PositionEntity(e\room\Objects[9], e\room\x + (2624.0 * RoomScale), e\room\y + (992.0 * RoomScale), e\room\z + (6157.0 * RoomScale))
						ScaleEntity(e\room\Objects[9], RoomScale, RoomScale, RoomScale)
						e\room\Objects[10] = LoadMesh_Strict("GFX\Map\Props\lightgun.b3d")
						PositionEntity(e\room\Objects[10], e\room\x + (2614.0 * RoomScale), e\room\y + (1280.0 * RoomScale), e\room\z + (5981.0 * RoomScale), True)
						ScaleEntity(e\room\Objects[10], RoomScale, RoomScale, RoomScale)
						EntityParent(e\room\Objects[10], e\room\Objects[9])
						RotateEntity(e\room\Objects[9], 0.0, 48.0, 0.0)
						RotateEntity(e\room\Objects[10], 40.0, 0.0, 0.0)
						
						RenderLoading(90, GetLocalString("loading", "ending"))
						
						ResetEntity(me\Collider)
						RotateEntity(me\Collider, 0.0, EntityYaw(me\Collider) + (e\room\Angle + 180.0), 0.0)
						
						If (Not n_I\Curr106\Contained) Then PlaySound_Strict(LoadTempSound("SFX\Ending\GateA\106Escape.ogg"))
						
						SecondaryLightOn = True
						
						HideDistance = 68.0
						
						CreateConsoleMsg("")
						CreateConsoleMsg(GetLocalString("misc", "telewarn"), 255, 0, 0)
						CreateConsoleMsg("")
						
						e\EventState = 1.0
						
						RenderLoading(100)
					Else
						UpdateSky(Sky)
						
						CanSave = 1
						
						For r.Rooms = Each Rooms
							HideRoomsNoColl(r)
						Next
						ShowRoomsNoColl(e\room)
						
						ShouldPlay = 17
						
						e\EventState = e\EventState + fps\Factor[0]
						
						Angle = Max(Sin(EntityYaw(me\Collider) + 90.0), 0.0)
						
						For i = 2 To 4
							If e\room\NPC[i] <> Null Then
								If e\room\NPC[i]\State < 2.0 Then
									PositionEntity(e\room\NPC[i]\Collider, EntityX(e\room\Objects[3], True) + Cos(e\EventState / 10.0 + (120.0 * i)) * 6000.0 * RoomScale, e\room\y + 11.0, EntityZ(e\room\Objects[3], True) + Sin(e\EventState / 10.0 + (120.0 * i)) * 6000.0 * RoomScale)
									RotateEntity(e\room\NPC[i]\Collider, 7.0, (e\EventState / 10.0 + (120.0 * i)), 20.0)
								EndIf
							EndIf
						Next
						
						If e\EventState >= 350.0 Then
							If (Not n_I\Curr106\Contained) Then
								If e\EventState - fps\Factor[0] < 350.0 Then
									de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(e\room\Objects[3], True), EntityY(e\room\Objects[3], True) + 0.01, EntityZ(e\room\Objects[3], True), 90.0, Rnd(360.0), 0.0, 0.05, 0.8)
									de\SizeChange = 0.001
									EntityParent(de\OBJ, e\room\OBJ)
									
									PositionEntity(n_I\Curr106\Collider, EntityX(e\room\Objects[3], True), EntityY(me\Collider) - 3.0, EntityZ(e\room\Objects[3], True), True)
									ResetEntity(n_I\Curr106\Collider)
									SetNPCFrame(n_I\Curr106, 110.0)
									n_I\Curr106\State = -0.1
									n_I\Curr106\PrevY = EntityY(me\Collider)
									
									PlaySound_Strict(HorrorSFX[5])
									PlaySound_Strict(DecaySFX[0])
								ElseIf n_I\Curr106\State =< 0.0
									If (Not EntityHidden(n_I\Curr106\OBJ2)) Then HideEntity(n_I\Curr106\OBJ2)
									n_I\Curr106\PathTimer = 70.0 * 100.0
									
									If n_I\Curr106\State3 = 0.0 Then
										If n_I\Curr106\PathStatus <> 1 Then
											PositionEntity(n_I\Curr106\Collider, EntityX(e\room\Objects[3], True), EntityY(n_I\Curr106\Collider), EntityZ(e\room\Objects[3], True), True)
											If n_I\Curr106\State <= -10.0 Then
												Dist = EntityY(n_I\Curr106\Collider)
												PositionEntity(n_I\Curr106\Collider, EntityX(n_I\Curr106\Collider), EntityY(e\room\Objects[3], True), EntityZ(n_I\Curr106\Collider), True)
												n_I\Curr106\PathStatus = FindPath(n_I\Curr106, EntityX(e\room\NPC[5]\Collider, True), EntityY(e\room\NPC[5]\Collider, True), EntityZ(e\room\NPC[5]\Collider, True))
												n_I\Curr106\PathTimer = 70.0 * 200.0 : n_I\Curr106\PathLocation = 1
												PositionEntity(n_I\Curr106\Collider, EntityX(n_I\Curr106\Collider), Dist, EntityZ(n_I\Curr106\Collider), True)
											EndIf
											ResetEntity(n_I\Curr106\Collider)
										Else
											n_I\Curr106\PathTimer = 70.0 * 200.0
											For i = 2 To 4 ; ~ Helicopters start attacking SCP-106
												e\room\NPC[i]\State = 3.0 
												e\room\NPC[i]\EnemyX = EntityX(n_I\Curr106\OBJ, True)
												e\room\NPC[i]\EnemyY = EntityY(n_I\Curr106\OBJ, True) + 5.0
												e\room\NPC[i]\EnemyZ = EntityZ(n_I\Curr106\OBJ, True)
											Next
											
											For i = 5 To 8
												If EntityDistanceSquared(e\room\NPC[i]\Collider, me\Collider) < 25.0 And (Not chs\NoTarget) Then
													e\room\NPC[i]\State = 6.0
												Else
													e\room\NPC[i]\State = 5.0
													e\room\NPC[i]\EnemyX = EntityX(n_I\Curr106\OBJ, True)
													e\room\NPC[i]\EnemyY = EntityY(n_I\Curr106\OBJ, True) + 0.4
													e\room\NPC[i]\EnemyZ = EntityZ(n_I\Curr106\OBJ, True)
												EndIf
											Next
											
											Pvt = CreatePivot()
											PositionEntity(Pvt, EntityX(e\room\Objects[10], True), EntityY(e\room\Objects[10], True), EntityZ(e\room\Objects[10], True))
											PointEntity(Pvt, n_I\Curr106\Collider)
											RotateEntity(e\room\Objects[9], 0.0, CurveAngle(EntityYaw(Pvt), EntityYaw(e\room\Objects[9], True), 150.0), 0.0, True)
											RotateEntity(e\room\Objects[10], CurveAngle(EntityPitch(Pvt), EntityPitch(e\room\Objects[10], True), 200.0), EntityYaw(e\room\Objects[9], True), 0.0, True)
											FreeEntity(Pvt)
											
											If fps\Factor[0] > 0.0 Then ; ~ Decals under SCP-106
												If ((e\EventState - fps\Factor[0]) Mod 100.0) <= 50.0 And (e\EventState Mod 100.0) > 50.0 Then
													de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(n_I\Curr106\Collider, True), EntityY(e\room\Objects[3], True) + 0.01, EntityZ(n_I\Curr106\Collider, True), 90.0, Rnd(360.0), 0.0, 0.2, 0.8)
													de\SizeChange = 0.004 : de\Timer = 90000.0
													EntityParent(de\OBJ, e\room\OBJ)
												EndIf
											EndIf
										EndIf
									EndIf
									
									Dist = DistanceSquared(EntityX(n_I\Curr106\Collider), EntityX(e\room\Objects[4], True), EntityZ(n_I\Curr106\Collider), EntityZ(e\room\Objects[4], True))
									
									n_I\Curr106\CurrSpeed = CurveValue(0.0, n_I\Curr106\CurrSpeed, Max(5.0 * Sqr(Dist), 2.0))
									If Dist < 225.0 Then
										If (Not e\SoundCHN2) Then e\SoundCHN2 = PlaySound_Strict(LoadTempSound("SFX\Ending\GateA\Franklin.ogg"))
										If Dist < 0.16 Then
											n_I\Curr106\PathStatus = 0
											n_I\Curr106\PathTimer = 70.0 * 200.0
											If n_I\Curr106\State3 = 0.0 Then
												SetNPCFrame(n_I\Curr106, 259.0)
												LoadEventSound(e, "SFX\Ending\GateA\106Retreat.ogg")
												e\SoundCHN = PlaySound2(e\Sound, Camera, n_I\Curr106\Collider, 35.0)
											EndIf
											
											If fps\Factor[0] > 0.0 Then
												If ((e\EventState - fps\Factor[0]) Mod 160.0) <= 50.0 And (e\EventState Mod 160.0) > 50.0 Then
													de.Decals = CreateDecal(DECAL_CORROSIVE_1, EntityX(n_I\Curr106\Collider, True), EntityY(e\room\Objects[3], True) + 0.01, EntityZ(n_I\Curr106\Collider, True), 90.0, Rnd(360.0), 0.0, 0.05, 0.8)
													de\SizeChange = 0.004 : de\Timer = 90000.0
													EntityParent(de\OBJ, e\room\OBJ)
												EndIf
											EndIf
											
											AnimateNPC(n_I\Curr106, 259.0, 110.0, -0.1, False)
											
											n_I\Curr106\State3 = n_I\Curr106\State3 + fps\Factor[0]
											PositionEntity(n_I\Curr106\Collider, EntityX(n_I\Curr106\Collider, True), CurveValue(EntityY(e\room\Objects[3], True) - (n_I\Curr106\State3 / 4500.0), EntityY(n_I\Curr106\Collider, True), 100.0), EntityZ(n_I\Curr106\Collider, True))
											ResetEntity(n_I\Curr106\Collider)
											If n_I\Curr106\State3 > 700.0 Then
												n_I\Curr106\State = 100000.0
												e\EventState2 = 0.0
												For i = 5 To 8
													e\room\NPC[i]\State = 10.0 : e\room\NPC[i]\Speed = e\room\NPC[i]\Speed * Rnd(1.0, 1.2)
												Next
												For i = 2 To 4 ; ~ Helicopters attack the player
													e\room\NPC[i]\State = 2.0
												Next
												If (Not EntityHidden(n_I\Curr106\OBJ)) Then HideEntity(n_I\Curr106\OBJ)
											EndIf
										Else
											If Dist < 72.25 Then
												If e\EventState2 = 0.0 Then
													e\SoundCHN2 = PlaySound_Strict(LoadTempSound("SFX\Ending\GateA\HIDTurret.ogg"))
													e\EventState2 = 1.0
												ElseIf e\EventState2 > 0.0
													e\EventState2 = e\EventState2 + fps\Factor[0]
													If e\EventState2 >= 70.0 * 7.5 Then
														If e\EventState2 - fps\Factor[0] < 70.0 * 7.5 Then
															p.Particles = CreateParticle(PARTICLE_SUN, EntityX(n_I\Curr106\OBJ, True), EntityY(n_I\Curr106\OBJ, True) + 0.4, EntityZ(n_I\Curr106\OBJ, True), 7.0, 0.0, 470.0)
															p\Speed = 0.0 : p\Alpha = 1.0
															EntityParent(p\Pvt, n_I\Curr106\Collider, True)
															
															p.Particles = CreateParticle(PARTICLE_SUN, EntityX(e\room\Objects[10], True), EntityY(e\room\Objects[10], True), EntityZ(e\room\Objects[10], True), 2.0, 0.0, 470.0)
															p\Speed = 0.0 : p\Alpha = 1.0
															RotateEntity(p\Pvt, EntityPitch(e\room\Objects[10], True), EntityYaw(e\room\Objects[10], True), 0.0, True)
															MoveEntity(p\Pvt, 0.0, 92.0 * RoomScale, 512.0 * RoomScale)
															EntityParent(p\Pvt, e\room\Objects[10], True)
														ElseIf e\EventState2 < 70.0 * 14.3
															me\CameraShake = 0.5
															me\LightFlash = 0.3 + EntityInView(e\room\Objects[10], Camera) * 0.5
															
															If (Not (me\Terminated Lor chs\GodMode)) Then
																If EntityDistanceSquared(me\Collider, n_I\Curr106\Collider) < 1.44 Then
																	PlaySound_Strict(LoadTempSound("SFX\SCP\294\Burn.ogg"))
																	Kill(True)
																EndIf
															EndIf
														EndIf
													EndIf
												EndIf
												
												If opt\ParticleAmount > 0 Then
													For i = 0 To Rand(2, 2 + (6 * (opt\ParticleAmount - 1))) - Int(Sqr(Dist))
														p.Particles = CreateParticle(PARTICLE_BLACK_SMOKE, EntityX(n_I\Curr106\OBJ, True), EntityY(n_I\Curr106\OBJ, True) + Rnd(0.4, 0.9), EntityZ(n_I\Curr106\OBJ), 0.006, -0.002, 40.0)
														p\Speed = 0.005 : p\Alpha = 0.8 : p\AlphaChange = -0.01
														RotateEntity(p\Pvt, -Rnd(70.0, 110.0), Rnd(360.0), 0.0)
													Next
												EndIf
											EndIf
										EndIf
									EndIf
								EndIf
								
								If e\EventState3 = 0.0 Then
									If Abs(EntityY(me\Collider) - EntityY(e\room\Objects[11], True)) < 1.0 Then
										If DistanceSquared(EntityX(me\Collider), EntityX(e\room\Objects[11], True), EntityZ(me\Collider), EntityZ(e\room\Objects[11], True)) < 144.0 Then
											n_I\Curr106\State = 100000.0
											If (Not EntityHidden(n_I\Curr106\OBJ)) Then HideEntity(n_I\Curr106\OBJ)
											
											; ~ MTF spawns at the tunnel entrance
											For i = 5 To 8
												PositionEntity(e\room\NPC[i]\Collider, EntityX(e\room\Objects[15], True) + (i - 6) * 0.3, EntityY(e\room\Objects[15], True), EntityZ(e\room\Objects[15], True) + (i - 6) * 0.3, True)
												ResetEntity(e\room\NPC[i]\Collider)
												
												e\room\NPC[i]\PathStatus = FindPath(e\room\NPC[i], EntityX(me\Collider), EntityY(me\Collider) + 0.2, EntityZ(me\Collider))
												e\room\NPC[i]\State = 3.0 : e\room\NPC[i]\PathTimer = 70.0 * 2.0 : e\room\NPC[i]\LastSeen = 70.0 * 100.0
											Next
											e\room\NPC[5]\Sound = LoadSound_Strict("SFX\Character\MTF\ThereHeIs1.ogg")
											PlaySound2(e\room\NPC[5]\Sound, Camera, e\room\NPC[5]\Collider, 25.0)
											
											e\room\RoomDoors[0]\Open = True
											
											For i = 2 To 4
												RemoveNPC(e\room\NPC[i]) : e\room\NPC[i] = Null
											Next
											
											e\EventState3 = 1.0
										EndIf
									EndIf
								ElseIf e\EventState3 = 1.0
									For i = 5 To 8
										If EntityDistanceSquared(e\room\NPC[i]\Collider, me\Collider) > 16.0 Then
											e\room\NPC[i]\State = 3.0
										Else
											e\room\NPC[i]\State = 1.0
										EndIf
									Next
									
									If Abs(EntityY(me\Collider) - EntityY(e\room\Objects[11], True)) < 1.0 Then
										If DistanceSquared(EntityX(me\Collider), EntityX(e\room\Objects[11], True), EntityZ(me\Collider), EntityZ(e\room\Objects[11], True)) < 49.0 Then
											e\room\Objects[12] = LoadAnimMesh_Strict("GFX\NPCs\CI.b3d")
											
											Local Temp2# = 0.55 / MeshWidth(e\room\Objects[12])
											
											ScaleEntity(e\room\Objects[12], Temp2, Temp2, Temp2)
											PositionEntity(e\room\Objects[12], EntityX(e\room\Objects[11], True), EntityY(e\room\Objects[11], True), EntityZ(e\room\Objects[11], True))
											
											e\room\Objects[17] = CopyEntity(e\room\Objects[12])
											PositionEntity(e\room\Objects[17], EntityX(e\room\OBJ, True) - 3968.0 * RoomScale, EntityY(e\room\Objects[11], True), EntityZ(e\room\OBJ, True) - 1920.0 * RoomScale)
											
											OBJ = CopyEntity(e\room\Objects[12])
											PositionEntity(OBJ, EntityX(e\room\OBJ, True) - 4160.0 * RoomScale, EntityY(e\room\Objects[11], True), EntityZ(e\room\OBJ, True) - 1920.0 * RoomScale)
											EntityParent(OBJ, e\room\Objects[17])
											
											OBJ = CopyEntity(e\room\Objects[12])
											PositionEntity(OBJ, EntityX(e\room\OBJ, True) - 4064.0 * RoomScale, EntityY(e\room\Objects[11], True), EntityZ(e\room\OBJ, True) - 2112.0 * RoomScale)
											EntityParent(OBJ, e\room\Objects[17])
											
											e\SoundCHN = PlaySound2(LoadTempSound("SFX\Ending\GateA\Bell1.ogg"), Camera, e\room\Objects[12])
											
											p.Particles = CreateParticle(PARTICLE_SUN, EntityX(e\room\Objects[11], True), EntityY(Camera, True), EntityZ(e\room\Objects[11], True), 8.0, 0.0, 50.0)
											p\Speed = 0.15 : p\Alpha = 0.5
											p.Particles = CreateParticle(PARTICLE_SUN, EntityX(e\room\Objects[11], True), EntityY(Camera, True), EntityZ(e\room\Objects[11], True), 8.0, 0.0, 50.0)
											p\Speed = 0.25 : p\Alpha = 0.5
											PointEntity(p\Pvt, me\Collider)
											
											me\CameraShake = 1.0
											me\LightFlash = 1.0
											
											e\EventState3 = 2.0
										EndIf
									EndIf
								Else
									e\EventState3 = e\EventState3 + fps\Factor[0]
									PointEntity(e\room\Objects[12], me\Collider)
									RotateEntity(e\room\Objects[12], 0.0, EntityYaw(e\room\Objects[12]), 0.0)
									
									me\Stamina = -5.0
									
									me\BlurTimer = Sin(e\EventState3 * 0.7) * 1000.0
									
									If (Not me\Terminated) Then
										CameraZoom(Camera, 1.0 + Sin(e\EventState3 * 0.8) * 0.2)
										
										Dist = EntityDistanceSquared(me\Collider, e\room\Objects[11])
										If Dist < 42.25 Then
											Dist = Sqr(Dist)
											PositionEntity(me\Collider, CurveValue(EntityX(e\room\Objects[11], True), EntityX(me\Collider), Dist * 80.0), EntityY(me\Collider), CurveValue(EntityZ(e\room\Objects[0], True), EntityZ(me\Collider), Dist * 80.0))
										EndIf
									EndIf
									
									If e\EventState3 > 50.0 And e\EventState3 < 230.0 Then
										me\CameraShake = Sin(e\EventState3 - 50.0) * 3.0
										TurnEntity(e\room\Objects[13], 0.0, (Sin(e\EventState3 - 50.0) * (-0.85)) * fps\Factor[0], 0.0, True)
										TurnEntity(e\room\Objects[14], 0.0, (Sin(e\EventState3 - 50.0) * 0.85) * fps\Factor[0], 0.0, True)
										
										For i = 5 To 8
											PositionEntity(e\room\NPC[i]\Collider, CurveValue(EntityX(e\room\RoomDoors[0]\FrameOBJ, True), EntityX(e\room\NPC[i]\Collider, True), 50.0), EntityY(e\room\NPC[i]\Collider, True), CurveValue(EntityZ(e\room\RoomDoors[0]\FrameOBJ, True), EntityZ(e\room\NPC[i]\Collider, True), 50.0), True)
											ResetEntity(e\room\NPC[i]\Collider)
										Next
									EndIf
									
									If e\EventState3 >= 230.0 Then
										If e\EventState3 - fps\Factor[0] < 230.0 Then
											e\SoundCHN = PlaySound_Strict(LoadTempSound("SFX\Ending\GateA\CI.ogg"))
											me\SelectedEnding = Ending_A1
										EndIf
										
										If e\EventState3 >= 480.0 Then
											Animate2(e\room\Objects[12], AnimTime(e\room\Objects[12]), 176.0, 210.0, 0.2)
											MoveEntity(e\room\Objects[12], 0.0, 0.0, 0.01 * fps\Factor[0])
										EndIf
										
										If (Not ChannelPlaying(e\SoundCHN)) Then
											ClearCheats()
											
											PlaySound_Strict(LoadTempSound("SFX\Ending\GateA\Bell2.ogg"))
											
											p.Particles = CreateParticle(PARTICLE_SUN, EntityX(e\room\Objects[11], True), EntityY(Camera, True), EntityZ(e\room\Objects[11], True), 8.0, 0.0, 50.0)
											p\Speed = 0.15 : p\Alpha = 0.5
											p.Particles = CreateParticle(PARTICLE_SUN, EntityX(e\room\Objects[11], True), EntityY(Camera, True), EntityZ(e\room\Objects[11], True), 8.0, 0.0, 50.0)
											p\Speed = 0.25 : p\Alpha = 0.5
											
											me\CameraShake = CurveValue(2.0, me\CameraShake, 10.0)
											me\LightFlash = CurveValue(2.0, me\LightFlash, 8.0)
											me\Terminated = True
											msg\DeathMsg = ""
											
											RemoveEvent(e)
											Exit
										EndIf
									EndIf
								EndIf
							Else
								If e\EventState2 = 0.0 Then
									For i = 2 To 4
										e\room\NPC[i]\State = 0.0
									Next
									
									For i = 5 To 8
										e\room\NPC[i]\State = 3.0 : e\room\NPC[i]\PathTimer = 70.0 * Rnd(15.0, 20.0) : e\room\NPC[i]\LastSeen = 70.0 * 300.0
										e\room\NPC[i]\PathStatus = FindPath(e\room\NPC[i], EntityX(e\room\OBJ) - 1.0 + 2.0 * (i Mod 2), EntityY(me\Collider) + 0.2, EntityZ(e\room\OBJ) - 2.0 * (i Mod 2))
									Next
									e\EventState2 = 1.0
								Else
									For i = 5 To 8
										If e\room\NPC[i]\State = 5.0 Then
											e\room\NPC[i]\EnemyX = EntityX(me\Collider)
											e\room\NPC[i]\EnemyY = EntityY(me\Collider)
											e\room\NPC[i]\EnemyZ = EntityZ(me\Collider)
										Else
											If EntityDistanceSquared(e\room\NPC[5]\Collider, me\Collider) < 36.0 Lor EntityDistanceSquared(e\room\NPC[6]\Collider, me\Collider) < 36.0 Lor EntityDistanceSquared(e\room\NPC[7]\Collider, me\Collider) < 36.0 Lor EntityDistanceSquared(e\room\NPC[8]\Collider, me\Collider) < 36.0 Then
												e\room\NPC[i]\State = 5.0 : e\room\NPC[i]\CurrSpeed = 0.0
											EndIf
										EndIf
									Next
									
									If e\EventState2 <= 1.0 Then
										For i = 5 To 8
											If e\room\NPC[i]\State = 5.0 Then
												e\room\NPC[i]\PathTimer = 70.0 * Rnd(7.0, 10.0) : e\room\NPC[i]\Reload = 2000.0
												e\room\NPC[i]\EnemyX = EntityX(me\Collider)
												e\room\NPC[i]\EnemyY = EntityY(me\Collider)
												e\room\NPC[i]\EnemyZ = EntityZ(me\Collider)
												
												me\Playable = False
												me\SelectedEnding = Ending_A2
												
												If e\EventState2 = 1.0 Then
													e\SoundCHN = PlaySound_Strict(LoadTempSound("SFX\Ending\GateA\STOPRIGHTTHERE.ogg"))
													e\EventState2 = 2.0
												EndIf
											Else
												e\room\NPC[i]\LastSeen = 70.0 * 300.0 : e\room\NPC[i]\Reload = 2000.0 : e\room\NPC[i]\State3 = 70.0 * 145.0
											EndIf
										Next
									Else
										ShouldPlay = 0
										me\CurrSpeed = 0.0
										If (Not ChannelPlaying(e\SoundCHN)) Then
											ClearCheats()
											
											PlaySound_Strict(IntroSFX[7])
											
											For n.NPCs = Each NPCs
												If n\NPCType = NPCTypeMTF
													RemoveNPC(n)
												EndIf
											Next
											
											me\LightFlash = 1.0
											me\Terminated = True
											msg\DeathMsg = ""
											me\BlinkTimer = -10.0
											
											RemoveEvent(e)
											Exit
										EndIf
									EndIf
								EndIf
							EndIf
						EndIf
					EndIf
				Else
					If (Not EntityHidden(e\room\OBJ)) Then HideEntity(e\room\OBJ)
				EndIf
				;[End Block]
		End Select
	Next
	
	UpdateExplosion()
End Function

Function RemoveEvent%(e.Events)
	If e\SoundCHN_IsStream Then
		If e\SoundCHN <> 0 Then StopStream_Strict(e\SoundCHN) : e\SoundCHN_IsStream = False
	Else
		StopChannel(e\SoundCHN)
	EndIf
	e\SoundCHN = 0
	If e\SoundCHN2_IsStream Then
		If e\SoundCHN2 <> 0 Then StopStream_Strict(e\SoundCHN2) : e\SoundCHN2_IsStream = False
	Else
		StopChannel(e\SoundCHN2)
	EndIf
	e\SoundCHN2 = 0
	If e\SoundCHN3_IsStream Then
		If e\SoundCHN3 <> 0 Then StopStream_Strict(e\SoundCHN3) : e\SoundCHN3_IsStream = False
	Else
		StopChannel(e\SoundCHN3)
	EndIf
	e\SoundCHN3 = 0
	If e\Sound <> 0 Then FreeSound_Strict(e\Sound) : e\Sound = 0
	If e\Sound2 <> 0 Then FreeSound_Strict(e\Sound2) : e\Sound2 = 0
	If e\Sound3 <> 0 Then FreeSound_Strict(e\Sound3) : e\Sound3 = 0
	
	If e\Img <> 0 Then FreeImage(e\Img)
	
	Delete(e)
End Function

Function Update096ElevatorEvent#(e.Events, EventState#, d.Doors, ElevatorOBJ%)
	Local PrevEventState# = EventState
	
	If EventState < 0.0 Then
		EventState = 0.0
		PrevEventState = 0.0
	EndIf
	
	d\Locked = 0
	If d\OpenState = 0.0 And (Not d\Open) Then
		If Abs(EntityX(me\Collider) - EntityX(ElevatorOBJ, True)) <= (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
			If Abs(EntityZ(me\Collider) - EntityZ(ElevatorOBJ, True)) <= (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
				If Abs(EntityY(me\Collider) - EntityY(ElevatorOBJ, True)) <= (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
					If EventState = 0.0 Then
						TeleportEntity(n_I\Curr096\Collider, EntityX(d\FrameOBJ), EntityY(d\FrameOBJ) + 1.0, EntityZ(d\FrameOBJ), n_I\Curr096\CollRadius)
						PointEntity(n_I\Curr096\Collider, ElevatorOBJ)
						RotateEntity(n_I\Curr096\Collider, 0.0, EntityYaw(n_I\Curr096\Collider), 0.0)
						MoveEntity(n_I\Curr096\Collider, 0.0, 0.0, -0.5)
						ResetEntity(n_I\Curr096\Collider)
						n_I\Curr096\State = 6.0
						SetNPCFrame(n_I\Curr096, 0.0)
						e\Sound = LoadSound_Strict("SFX\SCP\096\ElevatorSlam.ogg")
						EventState = EventState + (fps\Factor[0] * 1.4)
					EndIf
				EndIf
			EndIf
		EndIf
	EndIf
	
	If EventState > 0.0 Then
		If PrevEventState = 0.0 Then e\SoundCHN = PlaySound_Strict(e\Sound)
		
		If EventState > 70.0 * 1.9 And EventState < (70.0 * 2.0) + fps\Factor[0]
			me\BigCameraShake = 7.0
		ElseIf EventState > 70.0 * 4.2 And EventState < (70.0 * 4.25) + fps\Factor[0]
			me\BigCameraShake = 1.0
		ElseIf EventState > 70.0 * 5.9 And EventState < (70.0 * 5.95) + fps\Factor[0]
			me\BigCameraShake = 1.0
		ElseIf EventState > 70.0 * 7.25 And EventState < (70.0 * 7.3) + fps\Factor[0]
			me\BigCameraShake = 1.0
			d\FastOpen = True : d\Open = True
			n_I\Curr096\State = 4.0
			n_I\Curr096\LastSeen = 1.0
		ElseIf EventState > 70.0 * 8.1 And EventState < 70.0 * 8.15 + fps\Factor[0]
			me\BigCameraShake = 1.0
		EndIf
		
		If EventState <= 70.0 * 8.1 Then d\OpenState = Min(d\OpenState, 20.0)
		EventState = EventState + (fps\Factor[0] * 1.4)
	EndIf
	Return(EventState)
End Function

Function Update035Label%(OBJ%)
	Local itt.ItemTemplates, it.Items
	Local Tex%, i%, LabelPath$, CurrTex$
	Local SF%, b%, Brush%, t1%, TexName$
	
	If I_035\Sad Then
		CurrTex = "035_sad"
	Else
		CurrTex = "035_smile"
	EndIf
	LabelPath = "GFX\Map\Textures\label" + CurrTex + ".png"
	Tex = LoadTexture_Strict(LabelPath)
	If opt\Atmosphere Then TextureBlend(Tex, 5)
	
	Tex = LoadTexture_Strict(LabelPath)
	Brush = LoadBrush_Strict(LabelPath)
	For i = 1 To CountSurfaces(OBJ)
		SF = GetSurface(OBJ, i)
		b = GetSurfaceBrush(SF)
		t1 = GetBrushTexture(b, 0)
		TexName = StripPath(TextureName(t1))
		
		If Lower(TexName) <> "cable_white.jpg" Then PaintSurface(SF, Brush)
		FreeBrush(b)
	Next
	DeleteSingleTextureEntryFromCache(Tex)
	FreeBrush(Brush)
	
	For itt.ItemTemplates = Each ItemTemplates
		If itt\Name = "Document SCP-035" Then
			If itt\Img <> 0 Then FreeImage(itt\Img) : itt\Img = 0
			itt\ImgPath = ItemHUDTexturePath + "doc_" + CurrTex + ".png"
			itt\Img = LoadImage_Strict(itt\ImgPath)
			itt\Img = ScaleImage2(itt\Img, MenuScale, MenuScale)
			itt\ImgWidth = ImageWidth(itt\Img) / 2
			itt\ImgHeight = ImageHeight(itt\Img) / 2
			itt\TexPath = ItemTexturePath + "doc_" + CurrTex + ".png"
			
			For it.Items = Each Items
				If it\ItemTemplate\Name = itt\Name Then
					Tex = LoadTexture_Strict(itt\TexPath)
					If opt\Atmosphere Then TextureBlend(Tex, 5)
					EntityTexture(it\Model, Tex)
					DeleteSingleTextureEntryFromCache(Tex)
					Exit
				EndIf
			Next
			Exit
		EndIf
	Next
End Function

;~IDEal Editor Parameters:
;~C#Blitz3D