Function FillRoom%(r.Rooms)
	CatchErrors("Uncaught (FillRoom)")
	
	Local d.Doors, d2.Doors, sc.SecurityCams, de.Decals, r2.Rooms, fr.Forest
	Local it.Items, it2.Items, em.Emitters, w.WayPoints, w2.WayPoints, lt.LightTemplates
	Local twp.TempWayPoints, ts.TempScreens, tp.TempProps
	Local xTemp#, yTemp#, zTemp#, xTemp2%, yTemp2%, zTemp2%, SF%, b%, Name$
	Local t1%, Tex%, Screen%, Scale#
	Local i%, k%, Temp%, Temp3%, Angle#
	Local ItemName$, ItemTempName$
	
	Select r\RoomTemplate\Name
		Case "cont2_860_1"
			;[Block]
			; ~ Doors to observation room
			d.Doors = CreateDoor(r\x + 928.0 * RoomScale, r\y, r\z + 640.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			d\Locked = 1
			
			d.Doors = CreateDoor(r\x + 928.0 * RoomScale, r\y, r\z - 640.0 * RoomScale, 0.0, r, True, DEFAULT_DOOR, KEY_CARD_3)
			d\MTFClose = False
			
			; ~ Doors to SCP-860-1's door itself
			d.Doors = CreateDoor(r\x + 416.0 * RoomScale, r\y, r\z - 640.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			
			d.Doors = CreateDoor(r\x + 416.0 * RoomScale, r\y, r\z + 640.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			
			; ~ SCP-860-1's door
			r\RoomDoors.Doors[0] = CreateDoor(r\x + 184.0 * RoomScale, r\y, r\z, 0.0, r, False, WOODEN_DOOR, KEY_860)
			r\RoomDoors[0]\Locked = 1 : r\RoomDoors[0]\DisableWaypoint = True
			
			; ~ The forest
			If (Not I_Zone\HasCustomForest) Then
				fr.Forest = New Forest
				r\fr = fr
				GenForestGrid(fr)
				PlaceForest(fr, r\x, r\y + 30.0, r\z, r)
			EndIf
			
			it.Items = CreateItem("Document SCP-860-1", "paper", r\x + 1158.0 * RoomScale, r\y + 250.0 * RoomScale, r\z - 17.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, r\Angle, 0.0)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room2c_gw_lcz"
			;[Block]
			; ~ Doors
			d.Doors = CreateDoor(r\x + 815.0 * RoomScale, r\y, r\z - 352.0 * RoomScale, 0.0, r)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True) + 0.07, EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True), True)
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			d.Doors = CreateDoor(r\x + 352.0 * RoomScale, r\y, r\z - 815.0 * RoomScale, 90.0, r)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True), EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True) + 0.07, True)
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			r\RoomDoors.Doors[0] = CreateDoor(r\x - 736.0 * RoomScale, r\y, r\z - 80.0 * RoomScale, 0.0, r)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\Timer = 70.0 * 5.0
			PositionEntity(r\RoomDoors[0]\Buttons[0], r\x - 288.0 * RoomScale, EntityY(r\RoomDoors[0]\Buttons[0], True), r\z - 632.0 * RoomScale, True)
			FreeEntity(r\RoomDoors[0]\Buttons[1]) : r\RoomDoors[0]\Buttons[1] = 0
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x + 80.0 * RoomScale, r\y, r\z + 736.0 * RoomScale, 270.0, r)
			r\RoomDoors[1]\AutoClose = False : r\RoomDoors[1]\Timer = 70.0 * 5.0
			PositionEntity(r\RoomDoors[1]\Buttons[0], r\x + 632.0 * RoomScale, EntityY(r\RoomDoors[1]\Buttons[0], True), r\z + 288.0 * RoomScale, True)
			RotateEntity(r\RoomDoors[1]\Buttons[0], 0.0, 90.0, 0.0, True)
			FreeEntity(r\RoomDoors[1]\Buttons[1]) : r\RoomDoors[1]\Buttons[1] = 0
			
			r\RoomDoors[0]\LinkedDoor = r\RoomDoors[1]
			r\RoomDoors[1]\LinkedDoor = r\RoomDoors[0]
			
			; ~ Security camera inside
			sc.SecurityCams = CreateSecurityCam(r\x - 688.0 * RoomScale, r\y + 384.0 * RoomScale, r\z + 688.0 * RoomScale, r, True, r\x + 670.0 * RoomScale, r\y + 280.0 * RoomScale, r\z - 96.0 * RoomScale)
			sc\Angle = 225.0 : sc\Turn = 45.0
			TurnEntity(sc\CameraOBJ, 40.0, 0.0, 0.0)
			TurnEntity(sc\ScrOBJ, 0.0, 90.0, 0.0)
			
			sc.SecurityCams = CreateSecurityCam(r\x - 112.0 * RoomScale, r\y + 384.0 * RoomScale, r\z + 112.0 * RoomScale, r, True, r\x + 96.0 * RoomScale, r\y + 280.0 * RoomScale, r\z - 670.0 * RoomScale)
			sc\Angle = 45.0 : sc\Turn = 45.0
			TurnEntity(sc\CameraOBJ, 40.0, 0.0, 0.0)
			
			; ~ Smoke
			em.Emitters = CreateEmitter(r\x - 175.0 * RoomScale, r\y + 370.0 * RoomScale, r\z + 656.0 * RoomScale, 0)
			em\RandAngle = 20.0 : em\Speed = 0.05 : em\SizeChange = 0.007 : em\AlphaChange = -0.006 : em\Gravity = -0.24
			TurnEntity(em\OBJ, 90.0, 0.0, 0.0)
			EntityParent(em\OBJ, r\OBJ)
			
			em.Emitters = CreateEmitter(r\x - 655.0 * RoomScale, r\y + 370.0 * RoomScale, r\z + 240.0 * RoomScale, 0)
			em\RandAngle = 20.0 : em\Speed = 0.05 : em\SizeChange = 0.007 : em\AlphaChange = -0.006 : em\Gravity = -0.24
			TurnEntity(em\OBJ, 90.0, 0.0, 0.0)
			EntityParent(em\OBJ, r\OBJ)
			;[End Block]
		Case "room2c_gw_2_lcz"
			;[Block]
			; ~ Doors
			d.Doors = CreateDoor(r\x + 815.0 * RoomScale, r\y, r\z - 352.0 * RoomScale, 180.0, r, True)
			d\Locked = 1 : d\MTFClose = False
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True) + 0.07, EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True), True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True) - 0.07, EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True), True)
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			d.Doors = CreateDoor(r\x + 352.0 * RoomScale, r\y, r\z - 815.0 * RoomScale, 90.0, r, True)
			d\Locked = 1 : d\MTFClose = False
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True), EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True) - 0.07, True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True), EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True) + 0.07, True)
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			d.Doors = CreateDoor(r\x - 736.0 * RoomScale, r\y, r\z - 80.0 * RoomScale, 0.0, r)
			d\Locked = 1
			PositionEntity(d\Buttons[0], r\x - 288.0 * RoomScale, EntityY(d\Buttons[0], True), r\z - 632.0 * RoomScale, True)
			FreeEntity(d\Buttons[1]) : d\Buttons[1] = 0
			
			d2.Doors = CreateDoor(r\x + 80.0 * RoomScale, r\y, r\z + 736.0 * RoomScale, 270.0, r)
			d2\Locked = 1
			PositionEntity(d2\Buttons[0], r\x + 632.0 * RoomScale, EntityY(d2\Buttons[0], True), r\z + 288.0 * RoomScale, True)
			RotateEntity(d2\Buttons[0], 0.0, 90.0, 0.0, True)
			FreeEntity(d2\Buttons[1]) : d2\Buttons[1] = 0
			
			d\LinkedDoor = d2
			d2\LinkedDoor = d
			;[End Block]
		Case "room2c_gw_ez"
			;[Block]
			; ~ Doors
			d.Doors = CreateDoor(r\x + 815.0 * RoomScale, r\y, r\z - 352.0 * RoomScale, 0.0, r)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True) + 0.07, EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True), True)
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			d.Doors = CreateDoor(r\x + 352.0 * RoomScale, r\y, r\z - 815.0 * RoomScale, 90.0, r)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True), EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True) + 0.07, True)
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			d.Doors = CreateDoor(r\x - 512.0 * RoomScale, r\y, r\z - 400.0 * RoomScale, 90.0, r, True, ONE_SIDED_DOOR)
			d\Locked = 1 : d\MTFClose = False
			For i = 0 To 1
				FreeEntity(d\Buttons[i]) : d\Buttons[i] = 0
			Next
			
			d.Doors = CreateDoor(r\x + 400.0 * RoomScale, r\y, r\z + 512.0 * RoomScale, 180.0, r, True, ONE_SIDED_DOOR)
			d\Locked = 1 : d\MTFClose = False
			For i = 0 To 1
				FreeEntity(d\Buttons[i]) : d\Buttons[i] = 0
			Next
			
			; ~ Security cameras inside
			sc.SecurityCams = CreateSecurityCam(r\x + 512.0 * RoomScale, r\y + 384.0 * RoomScale, r\z + 384.0 * RoomScale, r, True, r\x + 668.0 * RoomScale, r\y + 1.1, r\z - 96.0 * RoomScale)
			sc\Angle = 135.0 : sc\Turn = 45.0
			TurnEntity(sc\CameraOBJ, 40.0, 0.0, 0.0)
			TurnEntity(sc\ScrOBJ, 0.0, 90.0, 0.0)
			
			sc.SecurityCams = CreateSecurityCam(r\x - 384.0 * RoomScale, r\y + 384.0 * RoomScale, r\z - 512.0 * RoomScale, r, True, r\x + 96.0 * RoomScale, r\y + 1.1, r\z - 668.0 * RoomScale)
			sc\Angle = 315.0 : sc\Turn = 45.0
			TurnEntity(sc\CameraOBJ, 40.0, 0.0, 0.0)
			
			; ~ Create blood decals inside
			For i = 0 To 5
				de.Decals = CreateDecal(Rand(DECAL_BLOOD_1, DECAL_BLOOD_2), r\x + Rnd(-392.0, 520.0) * RoomScale, r\y + 3.0 * RoomScale + Rnd(0, 0.001), r\z + Rnd(-392.0, 520.0) * RoomScale, 90.0, Rnd(360.0), 0.0, Rnd(0.3, 0.6))
				EntityParent(de\OBJ, r\OBJ)
				de.Decals = CreateDecal(Rand(DECAL_BLOOD_DROP_1, DECAL_BLOOD_DROP_2), r\x + Rnd(-392.0, 520.0) * RoomScale, r\y + 3.0 * RoomScale + Rnd(0, 0.001), r\z + Rnd(-392.0, 520.0) * RoomScale, 90.0, Rnd(360.0), 0.0, Rnd(0.1, 0.6))
				EntityParent(de\OBJ, r\OBJ)
				de.Decals = CreateDecal(Rand(DECAL_BLOOD_DROP_1, DECAL_BLOOD_DROP_2), r\x + Rnd(-0.5, 0.5), r\y + 3.0 * RoomScale + Rnd(0, 0.001), r\z + Rnd(-0.5, 0.5), 90.0, Rnd(360.0), 0.0, Rnd(0.1, 0.6))
				EntityParent(de\OBJ, r\OBJ)
			Next
			;[End Block]
		Case "gate_a"
			;[Block]
			r\RoomDoors.Doors[2] = CreateDoor(r\x - 4064.0 * RoomScale, r\y - 1248.0 * RoomScale, r\z + 3952.0 * RoomScale, 0.0, r)
			r\RoomDoors[2]\AutoClose = False
			
			d.Doors = CreateDoor(r\x, r\y, r\z + 2336.0 * RoomScale, 0.0, r, True, BIG_DOOR)
			For i = 0 To 1
				FreeEntity(d\Buttons[i]) : d\Buttons[i] = 0
			Next
			
			d.Doors = CreateDoor(r\x, r\y, r\z - 1024.0 * RoomScale, 0.0, r)
			d\AutoClose = False : d\Locked = 1
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			
			d.Doors = CreateDoor(r\x - 1440.0 * RoomScale, r\y - 480.0 * RoomScale, r\z + 2328.0 * RoomScale, 0.0, r, me\SelectedEnding = Ending_A2, DEFAULT_DOOR, KEY_CARD_2)
			PositionEntity(d\Buttons[0], r\x - 1320.0 * RoomScale, EntityY(d\Buttons[0], True), r\z + 2294.0 * RoomScale, True)
			PositionEntity(d\Buttons[1], r\x - 1590.0 * RoomScale, EntityY(d\Buttons[0], True), r\z + 2484.0 * RoomScale, True)	
			RotateEntity(d\Buttons[1], 0.0, 90.0, 0.0, True)
			
			d.Doors = CreateDoor(r\x - 1440.0 * RoomScale, r\y - 480.0 * RoomScale, r\z + 4352.0 * RoomScale, 0.0, r, me\SelectedEnding = Ending_A2, DEFAULT_DOOR, KEY_CARD_2)
			PositionEntity(d\Buttons[0], r\x - 1320.0 * RoomScale, EntityY(d\Buttons[0], True), r\z + 4378.0 * RoomScale, True)
			RotateEntity(d\Buttons[0], 0.0, 180.0, 0.0, True)
			PositionEntity(d\Buttons[1], r\x - 1590.0 * RoomScale, EntityY(d\Buttons[0], True), r\z + 4232.0 * RoomScale, True)	
			RotateEntity(d\Buttons[1], 0.0, 90.0, 0.0, True)
			
			For r2.Rooms = Each Rooms
				If r2\RoomTemplate\Name = "gate_a_entrance" Then
					; ~ Elevator
					r\RoomDoors.Doors[1] = CreateDoor(r\x + 1544.0 * RoomScale, r\y, r\z - 64.0 * RoomScale, -90.0, r, False, ELEVATOR_DOOR)
					PositionEntity(r\RoomDoors[1]\Buttons[0], EntityX(r\RoomDoors[1]\Buttons[0], True) - 0.22, EntityY(r\RoomDoors[1]\Buttons[0], True), EntityZ(r\RoomDoors[1]\Buttons[0], True), True)
					PositionEntity(r\RoomDoors[1]\Buttons[1], EntityX(r\RoomDoors[1]\Buttons[1], True) + 0.031, EntityY(r\RoomDoors[1]\Buttons[1], True), EntityZ(r\RoomDoors[1]\Buttons[1], True), True)
					PositionEntity(r\RoomDoors[1]\ElevatorPanel[0], EntityX(r\RoomDoors[1]\ElevatorPanel[0], True) + 0.031, EntityY(r\RoomDoors[1]\ElevatorPanel[0], True), EntityZ(r\RoomDoors[1]\ElevatorPanel[0], True), True)
					PositionEntity(r\RoomDoors[1]\ElevatorPanel[1], EntityX(r\RoomDoors[1]\ElevatorPanel[1], True) - 0.22, EntityY(r\RoomDoors[1]\ElevatorPanel[1], True), EntityZ(r\RoomDoors[1]\ElevatorPanel[1], True), True)
					
					r2\Objects[1] = CreatePivot()
					PositionEntity(r2\Objects[1], r\x + 1848.0 * RoomScale, r\y, r\z - 64.0 * RoomScale)
					EntityParent(r2\Objects[1], r\OBJ)
					Exit
				EndIf
			Next
			
			; ~ SCP-106's spawnpoint
			r\Objects[3] = CreatePivot()
			PositionEntity(r\Objects[3], r\x + 1216.0 * RoomScale, r\y, r\z + 2112.0 * RoomScale)
			
			r\Objects[4] = CreatePivot()
			PositionEntity(r\Objects[4], r\x, r\y + 96.0 * RoomScale, r\z + 6400.0 * RoomScale)		
			
			r\Objects[5] = CreatePivot()
			PositionEntity(r\Objects[5], r\x + 1784.0 * RoomScale, r\y + 2124.0 * RoomScale, r\z + 4512.0 * RoomScale)
			
			r\Objects[6] = CreatePivot()
			PositionEntity(r\Objects[6], r\x - 5048.0 * RoomScale, r\y + 1912.0 * RoomScale, r\z + 4656.0 * RoomScale)	
			
			; ~ MTF spawnpoint
			r\Objects[7] = CreatePivot()
			PositionEntity(r\Objects[7], r\x + 1824.0 * RoomScale, r\y + 0.22, r\z + 7056.0 * RoomScale)	
			
			r\Objects[8] = CreatePivot()
			PositionEntity(r\Objects[8], r\x - 1824.0 * RoomScale, r\y + 0.22, r\z + 7056.0 * RoomScale)	
			
			For i = 3 To 8
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			r\Objects[11] = CreatePivot()
			PositionEntity(r\Objects[11], r\x - 4064.0 * RoomScale, r\y - 1248.0 * RoomScale, r\z - 1696.0 * RoomScale)
			EntityParent(r\Objects[11], r\OBJ)
			
			r\Objects[13] = LoadMesh_Strict("GFX\map\ez\gateawall1.b3d", r\OBJ)
			PositionEntity(r\Objects[13], r\x - 4308.0 * RoomScale, r\y - 1045.0 * RoomScale, r\z + 544.0 * RoomScale, True)
			EntityColor(r\Objects[13], 25.0, 25.0, 25.0)
			EntityType(r\Objects[13], HIT_MAP)
			
			r\Objects[14] = LoadMesh_Strict("GFX\map\ez\gateawall2.b3d", r\OBJ)
			PositionEntity(r\Objects[14], r\x - 3820.0 * RoomScale, r\y - 1045.0 * RoomScale, r\z + 544.0 * RoomScale, True)	
			EntityColor(r\Objects[14], 25.0, 25.0, 25.5)
			EntityType(r\Objects[14], HIT_MAP)
			
			r\Objects[15] = CreatePivot()
			PositionEntity(r\Objects[15], r\x - 3568.0 * RoomScale, r\y - 1089.0 * RoomScale, r\z + 4944.0 * RoomScale)
			EntityParent(r\Objects[15], r\OBJ)
			
			; ~ Hit Box
			r\Objects[16] = LoadMesh_Strict("GFX\map\ez\gatea_hitbox1.b3d", r\OBJ)
			EntityPickMode(r\Objects[16], 2)
			EntityType(r\Objects[16], HIT_MAP)
			EntityAlpha(r\Objects[16], 0.0)
			
			w.WayPoints = CreateWaypoint(r\x, r\y + 66.0 * RoomScale, r\z + 3112.0 * RoomScale, Null, r)
			w2.WayPoints = CreateWaypoint(r\x, r\y + 66.0 * RoomScale, r\z + 2112.0 * RoomScale, Null, r)
			w\connected[0] = w2 : w\Dist[0] = EntityDistance(w\OBJ, w2\OBJ)
			w2\connected[0] = w : w2\Dist[0] = w\Dist[0]
			;[End Block]
		Case "gate_a_entrance"
			;[Block]
			; ~ Elevator
			r\RoomDoors.Doors[0] = CreateDoor(r\x + 720.0 * RoomScale, r\y, r\z + 512.0 * RoomScale, -90.0, r, True, ELEVATOR_DOOR)
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x, r\y, r\z - 360.0 * RoomScale, 0.0, r, False, BIG_DOOR, KEY_CARD_5)
			PositionEntity(r\RoomDoors[1]\Buttons[1], r\x + 422.0 * RoomScale, EntityY(r\RoomDoors[0]\Buttons[1], True), r\z - 576.0 * RoomScale, True)
			RotateEntity(r\RoomDoors[1]\Buttons[1], 0.0, r\Angle - 90.0, 0.0, True)
			PositionEntity(r\RoomDoors[1]\Buttons[0], r\x - 522.0 * RoomScale, EntityY(r\RoomDoors[1]\Buttons[0], True), EntityZ(r\RoomDoors[1]\Buttons[0], True), True)
			RotateEntity(r\RoomDoors[1]\Buttons[0], 0.0, r\Angle - 225.0, 0.0, True)
			
			; ~ Elevator's pivot
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 1024.0 * RoomScale, r\y, r\z + 512.0 * RoomScale)
			EntityParent(r\Objects[0], r\OBJ)
			;[End Block]
		Case "gate_b_entrance"
			;[Block]
			r\RoomDoors.Doors[0] = CreateDoor(r\x + 720.0 * RoomScale, r\y, r\z + 1440.0 * RoomScale, 0.0, r, True, ELEVATOR_DOOR)
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x, r\y, r\z - 320.0 * RoomScale, 0.0, r, False, BIG_DOOR, KEY_CARD_5)
			PositionEntity(r\RoomDoors[1]\Buttons[1], r\x + 390.0 * RoomScale, EntityY(r\RoomDoors[1]\Buttons[1], True), r\z - 528.0 * RoomScale, True)
			RotateEntity(r\RoomDoors[1]\Buttons[1], 0.0, r\Angle - 90.0, 0.0, True)
			PositionEntity(r\RoomDoors[1]\Buttons[0], EntityX(r\RoomDoors[1]\Buttons[0], True), EntityY(r\RoomDoors[1]\Buttons[0], True), r\z - 198.0 * RoomScale, True)
			RotateEntity(r\RoomDoors[1]\Buttons[0], 0.0, r\Angle - 180.0, 0.0, True)
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 720.0 * RoomScale, r\y, r\z + 1744.0 * RoomScale)
			EntityParent(r\Objects[0], r\OBJ)
			;[End Block]
		Case "gate_b"
			;[Block]
			For r2.Rooms = Each Rooms
				If r2\RoomTemplate\Name = "gate_b_entrance" Then
					; ~ Elevator
					r\RoomDoors.Doors[1] = CreateDoor(r\x - 5424.0 * RoomScale, r\y, r\z - 1380.0 * RoomScale, 0.0, r, False, ELEVATOR_DOOR)
					PositionEntity(r\RoomDoors[1]\Buttons[0], EntityX(r\RoomDoors[1]\Buttons[0], True), EntityY(r\RoomDoors[1]\Buttons[0], True), EntityZ(r\RoomDoors[1]\Buttons[0], True) + 0.031, True)
					PositionEntity(r\RoomDoors[1]\Buttons[1], EntityX(r\RoomDoors[1]\Buttons[1], True), EntityY(r\RoomDoors[1]\Buttons[1], True), EntityZ(r\RoomDoors[1]\Buttons[1], True) - 0.031, True)
					PositionEntity(r\RoomDoors[1]\ElevatorPanel[0], EntityX(r\RoomDoors[1]\ElevatorPanel[0], True), EntityY(r\RoomDoors[1]\ElevatorPanel[0], True), EntityZ(r\RoomDoors[1]\ElevatorPanel[0], True) - 0.031, True)
					PositionEntity(r\RoomDoors[1]\ElevatorPanel[1], EntityX(r\RoomDoors[1]\ElevatorPanel[1], True), EntityY(r\RoomDoors[1]\ElevatorPanel[1], True), EntityZ(r\RoomDoors[1]\ElevatorPanel[1], True) + 0.031, True)
					
					r2\Objects[1] = CreatePivot()
					PositionEntity(r2\Objects[1], r\x - 5424.0 * RoomScale, r\y, r\z - 1068.0 * RoomScale)		
					EntityParent(r2\Objects[1], r\OBJ)
					Exit
				EndIf
			Next
			
			; ~ Other doors
			r\RoomDoors.Doors[2] = CreateDoor(r\x + 4352.0 * RoomScale, r\y, r\z - 492.0 * RoomScale, 0.0, r)
			r\RoomDoors[2]\AutoClose = False	
			
			r\RoomDoors.Doors[3] = CreateDoor(r\x + 4352.0 * RoomScale, r\y, r\z + 498.0 * RoomScale, 0.0, r)
			r\RoomDoors[3]\AutoClose = False
			
			r\RoomDoors.Doors[4] = CreateDoor(r\x + 3248.0 * RoomScale, r\y - 928.0 * RoomScale, r\z + 6400.0 * RoomScale, 0.0, r, False, ONE_SIDED_DOOR, KEY_MISC, "GEAR")
			r\RoomDoors[4]\Locked = 1
			FreeEntity(r\RoomDoors[4]\Buttons[1]) : r\RoomDoors[4]\Buttons[1] = 0	
			
			d.Doors = CreateDoor(r\x + 3072.0 * RoomScale, r\y - 928.0 * RoomScale, r\z + 5800.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			
			; ~ Guard spawnpoint
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x + 5203.0 * RoomScale, r\y + 1444.0 * RoomScale, r\z - 1739.0 * RoomScale)
			
			r\Objects[3] = CreatePivot()
			PositionEntity(r\Objects[3], r\x + 4363.0 * RoomScale, r\y - 248.0 * RoomScale, r\z + 2766.0 * RoomScale)
			
			r\Objects[4] = CreatePivot()
			PositionEntity(r\Objects[4], r\x + 5192.0 * RoomScale, r\y + 1408.0 * RoomScale, r\z - 4352.0 * RoomScale)
			
			; ~ Walkway
			r\Objects[5] = CreatePivot()
			PositionEntity(r\Objects[5], r\x + 4352.0 * RoomScale, r\y, r\z + 1344.0 * RoomScale)	
			
			; ~ SCP-682's paw
			r\Objects[6] = CreatePivot()
			PositionEntity(r\Objects[6], r\x + 2816.0 * RoomScale, r\y + 240.0 * RoomScale, r\z - 2816.0 * RoomScale)
			
			For i = 3 To 6
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			; ~ MTF spawnpoint
			r\Objects[8] = CreatePivot()
			PositionEntity(r\Objects[8], r\x + 3600.0 * RoomScale, r\y - 888.0 * RoomScale, r\z + 6623.0 * RoomScale)
			
			; ~ "SCP-682" pivot
			r\Objects[9] = CreatePivot()
			PositionEntity(r\Objects[9], r\x + 3808.0 * RoomScale, r\y + 1536.0 * RoomScale, r\z - 13568.0 * RoomScale)
			
			; ~ Apache radius
			r\Objects[10] = CreatePivot()
			PositionEntity(r\Objects[10], r\x - 7680.0 * RoomScale, r\y + 208.0 * RoomScale, r\z - 27048.0 * RoomScale)
			
			; ~ Extra apache spawnpoint
			r\Objects[11] = CreatePivot()
			PositionEntity(r\Objects[11], r\x - 5424.0 * RoomScale, r\y, r\z - 1068.0 * RoomScale)
			
			For i = 8 To 11
				EntityParent(r\Objects[i], r\OBJ)
			Next
			;[End Block]
		Case "cont1_372"
			;[Block]
			d.Doors = CreateDoor(r\x, r\y, r\z - 368.0 * RoomScale, 0.0, r, False, BIG_DOOR, KEY_CARD_2)
			PositionEntity(d\Buttons[0], r\x - 496.0 * RoomScale, EntityY(d\Buttons[0], True), r\z - 278.0 * RoomScale, True) 
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True) + 0.025, EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True), True) 
			TurnEntity(d\Buttons[0], 0.0, 90.0, 0.0)
			
			; ~ Hit Box
			r\Objects[3] = LoadMesh_Strict("GFX\map\lcz\room372_hb.b3d", r\OBJ)
			EntityPickMode(r\Objects[3], 2)
			EntityType(r\Objects[3], HIT_MAP)
			EntityAlpha(r\Objects[3], 0.0)
			
			it.Items = CreateItem("Document SCP-372", "paper", r\x + 800.0 * RoomScale, r\y + 176.0 * RoomScale, r\z + 1108.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, r\Angle, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Radio Transceiver", "radio", r\x + 800.0 * RoomScale, r\y + 112.0 * RoomScale, r\z + 944.0 * RoomScale)
			it\State = Rnd(10.0, 100.0)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "cont1_079"
			;[Block]
			; ~ Doors
			d.Doors = CreateDoor(r\x - 1648.0 * RoomScale, r\y - 10688.0 * RoomScale, r\z - 256.0 * RoomScale, 90.0, r, False, BIG_DOOR, KEY_CARD_4)
			PositionEntity(d\Buttons[0], r\x - 1894.0 * RoomScale, EntityY(d\Buttons[0], True), r\z - 701.0 * RoomScale, True)
			PositionEntity(d\Buttons[1], r\x - 1418.0 * RoomScale, EntityY(d\Buttons[1], True), r\z - 26.0 * RoomScale, True) 
			
			r\RoomDoors.Doors[0] = CreateDoor(r\x - 1648.0 * RoomScale, r\y - 10688.0 * RoomScale, r\z + 1230.0 * RoomScale, 90.0, r, False, BIG_DOOR, KEY_CARD_4)
			PositionEntity(r\RoomDoors[0]\Buttons[0], r\x - 1894.0 * RoomScale, EntityY(r\RoomDoors[0]\Buttons[0], True), r\z + 1675.0 * RoomScale, True)
			RotateEntity(r\RoomDoors[0]\Buttons[0], 0.0, EntityYaw(r\RoomDoors[0]\Buttons[0], True) + 180.0, 0.0, True)
			PositionEntity(r\RoomDoors[0]\Buttons[1], r\x - 1418.0 * RoomScale, EntityY(r\RoomDoors[0]\Buttons[1], True), r\z + 1562.0 * RoomScale, True)
			
			d.Doors = CreateDoor(r\x - 1202.0 * RoomScale, r\y - 10688.0 * RoomScale, r\z + 872.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_HAND_WHITE)
			
			d.Doors = CreateDoor(r\x, r\y, r\z + 64.0 * RoomScale, 0.0, r, False, HEAVY_DOOR, KEY_CARD_4)
			d\Locked = 1 : d\MTFClose = False : d\DisableWaypoint = True
			FreeEntity(d\Buttons[1]) : d\Buttons[1] = 0
			
			; ~ Elevators' doors
			r\RoomDoors.Doors[1] = CreateDoor(r\x + 512.0 * RoomScale, r\y, r\z - 256.0 * RoomScale, -90.0, r, True, ELEVATOR_DOOR)
			PositionEntity(r\RoomDoors[1]\ElevatorPanel[1], EntityX(r\RoomDoors[1]\ElevatorPanel[1], True), EntityY(r\RoomDoors[1]\ElevatorPanel[1], True) + 0.05, EntityZ(r\RoomDoors[1]\ElevatorPanel[1], True), True)
			
			r\RoomDoors.Doors[2] = CreateDoor(r\x + 512.0 * RoomScale, r\y - 10240.0 * RoomScale, r\z - 256.0 * RoomScale, -90.0, r, False, ELEVATOR_DOOR)
			
			r\Objects[0] = LoadAnimMesh_Strict("GFX\map\Props\079.b3d")
			ScaleEntity(r\Objects[0], 1.3, 1.3, 1.3)
			PositionEntity(r\Objects[0], r\x + 166.0 * RoomScale, r\y - 10800.0 * RoomScale, r\z + 1606.0 * RoomScale)
			TurnEntity(r\Objects[0], 0.0, -90.0, 0.0)
			EntityParent(r\Objects[0], r\OBJ)
			
			r\Objects[1] = CreateSprite(r\Objects[0])
			SpriteViewMode(r\Objects[1], 2)
			PositionEntity(r\Objects[1], 0.082, 0.119, 0.010)
			ScaleSprite(r\Objects[1], 0.09, 0.0725)
			TurnEntity(r\Objects[1], 0.0, 13.0, 0.0)
			MoveEntity(r\Objects[1], 0.0, 0.0, -0.022)
			EntityTexture(r\Objects[1], mon_I\MonitorOverlayID[MONITOR_079_OVERLAY_1])
			HideEntity(r\Objects[1])
			
			; ~ Dead guard spawnpoint
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x - 2260.0 * RoomScale, r\y - 10688.0 * RoomScale, r\z + 1000.0 * RoomScale)
			
			; ~ Elevators' pivots
			r\Objects[3] = CreatePivot()
			PositionEntity(r\Objects[3], r\x + 816.0 * RoomScale, r\y + 240.0 * RoomScale, r\z - 256.0 * RoomScale)
			
			r\Objects[4] = CreatePivot()
			PositionEntity(r\Objects[4], r\x + 816.0 * RoomScale, r\y - 10000.0 * RoomScale, r\z - 256.0 * RoomScale)
			
			For i = 2 To 4
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			de.Decals = CreateDecal(DECAL_BLOOD_2, r\x - 2200.0 * RoomScale, r\y - 10688.0 * RoomScale + 0.01, r\z + 1000.0 * RoomScale, 90.0, Rnd(360.0), 0.0, 0.5)
			EntityParent(de\OBJ, r\OBJ)
			;[End Block]
		Case "room2_checkpoint_lcz_hcz"
			;[Block]
			r\RoomDoors.Doors[0] = CreateDoor(r\x + 200.0 * RoomScale, r\y, r\z, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			r\RoomDoors[0]\Timer = 70.0 * 5.0
			PositionEntity(r\RoomDoors[0]\Buttons[0], r\x, EntityY(r\RoomDoors[0]\Buttons[0], True), r\z - 217.0 * RoomScale, True)
			PositionEntity(r\RoomDoors[0]\Buttons[1], r\x, EntityY(r\RoomDoors[0]\Buttons[1], True), r\z + 217.0 * RoomScale, True)
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x - 200.0 * RoomScale, r\y, r\z, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			r\RoomDoors[1]\Timer = 70.0 * 5.0
			For i = 0 To 1
				FreeEntity(r\RoomDoors[1]\Buttons[i]) : r\RoomDoors[1]\Buttons[i] = 0
			Next
			
			r\RoomDoors[0]\LinkedDoor = r\RoomDoors[1]
			r\RoomDoors[1]\LinkedDoor = r\RoomDoors[0]
			
			If CurrMapGrid\Grid[Floor(r\x / RoomSpacing) + ((Floor(r\z / RoomSpacing) - 1) * MapGridSize)] = MapGrid_NoTile Then
				d.Doors = CreateDoor(r\x, r\y, r\z - 1026.0 * RoomScale, 0.0, r, False, HEAVY_DOOR)
				d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
				FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			EndIf
			
			; ~ SCP-1048 spawnpoint
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 877.0 * RoomScale, r\y + 96.0 * RoomScale, r\z + 333.0 * RoomScale)
			EntityParent(r\Objects[0], r\OBJ)
			
			; ~ Monitors at the both sides
			r\Objects[2] = CopyEntity(mon_I\MonitorModelID[MONITOR_CHECKPOINT_MODEL], r\OBJ)
			PositionEntity(r\Objects[2], r\x, r\y + 384.0 * RoomScale, r\z + 256.0 * RoomScale, True)
			ScaleEntity(r\Objects[2], 2.0, 2.0, 2.0)
			RotateEntity(r\Objects[2], 0.0, 180.0, 0.0)
			
			r\Objects[3] = CopyEntity(mon_I\MonitorModelID[MONITOR_CHECKPOINT_MODEL], r\OBJ)
			PositionEntity(r\Objects[3], r\x, r\y + 384.0 * RoomScale, r\z - 256.0 * RoomScale, True)
			ScaleEntity(r\Objects[3], 2.0, 2.0, 2.0)
			RotateEntity(r\Objects[3], 0.0, 0.0, 0.0)
			
			sc.SecurityCams = CreateSecurityCam(r\x - 192.0 * RoomScale, r\y + 704.0 * RoomScale, r\z + 960.0 * RoomScale, r)
			sc\Angle = 225.0 : sc\Turn = 0.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			;[End Block]
		Case "room2_checkpoint_hcz_ez"
			;[Block]
			r\RoomDoors.Doors[0] = CreateDoor(r\x + 200.0 * RoomScale, r\y, r\z, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_4)
			r\RoomDoors[0]\Timer = 70.0 * 5.0
			PositionEntity(r\RoomDoors[0]\Buttons[0], r\x, EntityY(r\RoomDoors[0]\Buttons[0], True), r\z - 217.0 * RoomScale, True)
			PositionEntity(r\RoomDoors[0]\Buttons[1], r\x, EntityY(r\RoomDoors[0]\Buttons[1], True), r\z + 217.0 * RoomScale, True)
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x - 200.0 * RoomScale, r\y, r\z, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_4)
			r\RoomDoors[1]\Timer = 70.0 * 5.0
			For i = 0 To 1
				FreeEntity(r\RoomDoors[1]\Buttons[i]) : r\RoomDoors[1]\Buttons[i] = 0
			Next
			
			r\RoomDoors[0]\LinkedDoor = r\RoomDoors[1]
			r\RoomDoors[1]\LinkedDoor = r\RoomDoors[0]
			
			If CurrMapGrid\Grid[Floor(r\x / RoomSpacing) + ((Floor(r\z / RoomSpacing) - 1) * MapGridSize)] = MapGrid_NoTile Then
				d.Doors = CreateDoor(r\x, r\y, r\z - 1026.0 * RoomScale, 0.0, r)
				d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
				FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
				FreeEntity(d\OBJ2) : d\OBJ2 = 0
			EndIf
			
			; ~ SCP-1048 spawnpoint
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 877.0 * RoomScale, r\y + 96.0 * RoomScale, r\z + 333.0 * RoomScale)
			EntityParent(r\Objects[0], r\OBJ)
			
			; ~ Monitors at the both sides
			r\Objects[2] = CopyEntity(mon_I\MonitorModelID[MONITOR_CHECKPOINT_MODEL], r\OBJ)
			PositionEntity(r\Objects[2], r\x, r\y + 384.0 * RoomScale, r\z + 256.0 * RoomScale, True)
			ScaleEntity(r\Objects[2], 2.0, 2.0, 2.0)
			RotateEntity(r\Objects[2], 0.0, 180.0, 0.0)
			
			r\Objects[3] = CopyEntity(mon_I\MonitorModelID[MONITOR_CHECKPOINT_MODEL], r\OBJ)
			PositionEntity(r\Objects[3], r\x, r\y + 384.0 * RoomScale, r\z - 256.0 * RoomScale, True)
			ScaleEntity(r\Objects[3], 2.0, 2.0, 2.0)
			RotateEntity(r\Objects[3], 0.0, 0.0, 0.0)
			
			sc.SecurityCams = CreateSecurityCam(r\x + 192.0 * RoomScale, r\y + 704.0 * RoomScale, r\z - 960.0 * RoomScale, r)
			sc\Angle = 45.0 : sc\Turn = 0.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			;[End Block]
		Case "room2_4_hcz"
			;[Block]
			d.Doors = CreateDoor(r\x + 768.0 * RoomScale, r\y, r\z - 827.5 * RoomScale, 90.0, r)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True), EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True) + 0.1, True)
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			; ~ Smoke
			i = 0
			For xTemp = -1 To 1 Step 2
				For zTemp = -1 To 1
					em.Emitters = CreateEmitter(r\x + 202.0 * RoomScale * xTemp, r\y + 8.0 * RoomScale, r\z + 256.0 * RoomScale * zTemp, 0)
					em\RandAngle = 30.0 : em\Speed = 0.0045 : em\SizeChange = 0.007 : em\AlphaChange = -0.016
					If i < 3 Then 
						TurnEntity(em\OBJ, -45.0, -90.0, 0.0) 
					Else
						TurnEntity(em\OBJ, -45.0, 90.0, 0.0)
					EndIf
					EntityParent(em\OBJ, r\OBJ)
					i = i + 1
				Next
			Next
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 640.0 * RoomScale, r\y + 8.0 * RoomScale, r\z - 896.0 * RoomScale)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x - 864.0 * RoomScale, r\y - 400.0 * RoomScale, r\z - 632.0 * RoomScale)
			
			For i = 0 To 1
				EntityParent(r\Objects[i], r\OBJ)
			Next
			;[End Block]
		Case "room2c_lcz"
			;[Block]
			d.Doors = CreateDoor(r\x + 256.0 * RoomScale, r\y, r\z - 576.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True), EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True) + 0.165, True)
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			;[End Block]
		Case "room2_test_lcz"
			;[Block]
			; ~ Doors
			r\RoomDoors.Doors[0] = CreateDoor(r\x - 256.0 * RoomScale, r\y, r\z + 640.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_1)
			
			d.Doors = CreateDoor(r\x - 1024.0 * RoomScale, r\y, r\z + 640.0 * RoomScale, 270.0, r)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			d.Doors = CreateDoor(r\x - 512.0 * RoomScale, r\y, r\z + 376.0 * RoomScale, 0.0, r)
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x - 640.0 * RoomScale, r\y + 0.5, r\z - 912.0 * RoomScale)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x - 669.0 * RoomScale, r\y + 0.5, r\z - 16.0 * RoomScale)
			
			; ~ Glass panel
			Tex = LoadTexture_Strict("GFX\map\textures\glass.png", 1 + 2)
			r\Objects[2] = CreateSprite()
			EntityTexture(r\Objects[2], Tex)
			DeleteSingleTextureEntryFromCache(Tex)
			SpriteViewMode(r\Objects[2], 2)
			ScaleSprite(r\Objects[2], 182.0 * RoomScale * 0.5, 190.0 * RoomScale * 0.5)
			PositionEntity(r\Objects[2], r\x - 640 * RoomScale, r\y + 224.0 * RoomScale, r\z - 208.0 * RoomScale)
			TurnEntity(r\Objects[2], 0.0, 180.0, 0.0)			
			HideEntity(r\Objects[2])
			
			For i = 0 To 2
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			it.Items = CreateItem("Level 2 Key Card", "key2", r\x - 342.0 * RoomScale, r\y + 264.0 * RoomScale, r\z + 102.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, 180.0, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("S-NAV Navigator", "nav", r\x - 930.0 * RoomScale, r\y + 137.0 * RoomScale, r\z + 190.0 * RoomScale)
			it\State = Rnd(10.0, 100.0)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room3_2_hcz"
			;[Block]
			; ~ Guard's position
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x - 190.0 * RoomScale, r\y + 4.0 * RoomScale, r\z + 190.0 * RoomScale)
			EntityParent(r\Objects[0], r\OBJ)
			;[End Block]
		Case "room2_6_ez"
			;[Block]
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 1040.0 * RoomScale, r\y + 192.0 * RoomScale, r\z)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x + 1530.0 * RoomScale, r\y + 0.5, r\z + 512.0 * RoomScale)
			
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x + 1535.0 * RoomScale, r\y + 150.0 * RoomScale, r\z + 512.0 * RoomScale)
			
			For i = 0 To 2
				EntityParent(r\Objects[i], r\OBJ)
			Next
			;[End Block]
		Case "room2_storage"
			;[Block]
			r\RoomDoors.Doors[0] = CreateDoor(r\x - 1288.0 * RoomScale, r\y, r\z, 270.0, r)
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x - 760.0 * RoomScale, r\y, r\z, 270.0, r)
			
			r\RoomDoors.Doors[2] = CreateDoor(r\x - 264.0 * RoomScale, r\y, r\z, 270.0, r)
			
			r\RoomDoors.Doors[3] = CreateDoor(r\x + 264.0 * RoomScale, r\y, r\z, 270.0, r)
			
			r\RoomDoors.Doors[4] = CreateDoor(r\x + 760.0 * RoomScale, r\y, r\z, 270.0, r)
			
			r\RoomDoors.Doors[5] = CreateDoor(r\x + 1288.0 * RoomScale, r\y, r\z, 270.0, r)
			
			For i = 0 To 5
				r\RoomDoors[i]\AutoClose = False
				MoveEntity(r\RoomDoors[i]\Buttons[0], 0.0, 0.0, -8.0)
				MoveEntity(r\RoomDoors[i]\Buttons[1], 0.0, 0.0, -8.0)
			Next
			
			it.Items = CreateItem("Document SCP-939", "paper", r\x + 352.0 * RoomScale, r\y + 176.0 * RoomScale, r\z + 256.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, r\Angle + 4.0, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("4.5V Battery", "coarsebat", r\x + 352.0 * RoomScale, r\y + 112.0 * RoomScale, r\z + 448.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Empty Cup", "emptycup", r\x - 672.0 * RoomScale, 240.0 * RoomScale, r\z + 288.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Level 0 Key Card", "key0", r\x - 672.0 * RoomScale, r\y + 240.0 * RoomScale, r\z + 224.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room2_office_3"
			;[Block]
			Temp = ((Int(AccessCode) * 2) Mod 10000)
			If Temp < 1000 Then Temp = Temp + 1000
			d.Doors = CreateDoor(r\x + 1456.0 * RoomScale, r\y + 224.0 * RoomScale, r\z, 90.0, r, False, DEFAULT_DOOR, KEY_MISC, Temp)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True), EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True) + 0.1, True)
			
			d.Doors = CreateDoor(r\x + 463.0 * RoomScale, r\y, r\z, 90.0, r, True, DEFAULT_DOOR, KEY_CARD_5)
			d\MTFClose = False
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True), EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True) - 0.1, True)
			
			it.Items = CreateItem("Some SCP-420-J", "scp420j", r\x + 1776.0 * RoomScale, r\y + 400.0 * RoomScale, r\z + 427.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Some SCP-420-J", "scp420j", r\x + 1858.0 * RoomScale, r\y + 400.0 * RoomScale, r\z + 435.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Level 5 Key Card", "key5", r\x + 2272.0 * RoomScale, r\y + 392.0 * RoomScale, r\z + 387.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, r\Angle, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Nuclear Device Document", "paper", r\x + 2272.0 * RoomScale, r\y + 440.0 * RoomScale, r\z + 372.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Radio Transceiver", "radio", r\x + 2272.0 * RoomScale, r\y + 320.0 * RoomScale, r\z + 128.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room2_shaft"
			;[Block]
			d.Doors = CreateDoor(r\x + 1551.0 * RoomScale, r\y, r\z + 496.0 * RoomScale, 0.0, r)
			
			d.Doors = CreateDoor(r\x + 256.0 * RoomScale, r\y, r\z + 744.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_2)
			
			d.Doors = CreateDoor(r\x + 1984.0 * RoomScale, r\y, r\z + 744.0 * RoomScale, 90.0, r)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			; ~ Player's position after leaving the pocket dimension
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 1551.0 * RoomScale, r\y, r\z + 233.0 * RoomScale)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x + 1344.0 * RoomScale, r\y - 752.0 * RoomScale, r\z - 384.0 * RoomScale)
			
			r\Objects[2] = CreateButton(0, r\x + 1180.0 * RoomScale, r\y + 180.0 * RoomScale, r\z - 552.0 * RoomScale, 0.0, 270.0, 0.0, 0, True)
			
			For i = 0 To 2
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			de.Decals = CreateDecal(DECAL_BLOOD_2, r\x + 1334.0 * RoomScale, r\y - 796.0 * RoomScale + 0.01, r\z - 220.0 * RoomScale, 90.0, Rnd(360.0), 0.0, 0.25)
			EntityParent(de\OBJ, r\OBJ)
			
			it.Items = CreateItem("Level 3 Key Card", "key3", r\x + 990.0 * RoomScale, r\y + 233.0 * RoomScale, r\z + 431.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("First Aid Kit", "firstaid", r\x + 1035.0 * RoomScale, r\y + 145.0 * RoomScale, r\z + 56.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, 90.0, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("9V Battery", "bat", r\x + 1930.0 * RoomScale, r\y + 97.0 * RoomScale, r\z + 256.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("9V Battery", "bat", r\x + 1137.0 * RoomScale, r\y + 161.0 * RoomScale, r\z + 432.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("ReVision Eyedrops", "eyedrops", r\x + 1930.0 * RoomScale, r\y + 225.0 * RoomScale, r\z + 128.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room2_scientists"
			;[Block]
			d.Doors = CreateDoor(r\x + 256.0 * RoomScale, r\y, r\z + 448.0 * RoomScale, 270.0, r, False, DEFAULT_DOOR, KEY_MISC, Str(AccessCode))
			
			d.Doors = CreateDoor(r\x - 448.0 * RoomScale, r\y, r\z, 270.0, r, False, DEFAULT_DOOR, KEY_MISC, "GEAR")
			d\Locked = 1 : d\MTFClose = False : d\DisableWaypoint = True
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			d.Doors = CreateDoor(r\x + 256.0 * RoomScale, r\y, r\z - 576.0 * RoomScale, 270.0, r, False, DEFAULT_DOOR, KEY_MISC, "7816")
			
			it.Items = CreateItem("Mysterious Note", "paper", r\x + 736.0 * RoomScale, r\y + 224.0 * RoomScale, r\z + 544.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)	
			
			it.Items = CreateItem("Ballistic Vest", "vest", r\x + 608.0 * RoomScale, r\y + 112.0 * RoomScale, r\z + 32.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, 90.0, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("S-NAV 310 Navigator", "nav310", r\x + 325.0 * RoomScale, r\y + 266.0 * RoomScale, r\z + 60.0 * RoomScale)
			it\State = Rnd(10.0, 100.0)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Incident Report SCP-106-0204", "paper", r\x + 704.0 * RoomScale, r\y + 183.0 * RoomScale, r\z - 576.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Journal Page", "paper", r\x + 912.0 * RoomScale, r\y + 176.0 * RoomScale, r\z - 160.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("First Aid Kit", "firstaid", r\x + 912.0 * RoomScale, r\y + 112.0 * RoomScale, r\z - 336.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, 90.0, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			If I_005\ChanceToSpawn = 2 Then 
				it.Items = CreateItem("SCP-005", "scp005",  r\x + 736.0 * RoomScale, r\y + 224.0 * RoomScale, r\z + 755.0 * RoomScale)
				EntityParent(it\Collider, r\OBJ)
			EndIf
			;[End Block]
		Case "room2_scientists_2"
			;[Block]
			d.Doors = CreateDoor(r\x + 256.0 * RoomScale, r\y, r\z, 270.0, r, False, DEFAULT_DOOR, KEY_CARD_5)
			
			r\RoomDoors.Doors[0] = CreateDoor(r\x - 448.0 * RoomScale, r\y, r\z, 90.0, r, False, DEFAULT_DOOR, KEY_MISC, "1234")
			r\RoomDoors[0]\MTFClose = False : r\RoomDoors[0]\DisableWaypoint = True
			FreeEntity(r\RoomDoors[0]\Buttons[1]) : r\RoomDoors[0]\Buttons[1] = 0
			
			de.Decals = CreateDecal(DECAL_CORROSIVE_1, r\x - 808.0 * RoomScale, r\y + 0.005, r\z - 72.0 * RoomScale, 90.0, Rnd(360.0), 0.0)
			EntityParent(de\OBJ, r\OBJ)
			
			de.Decals = CreateDecal(DECAL_BLOOD_1, r\x - 808.0 * RoomScale, r\y + 0.01, r\z - 72.0 * RoomScale, 90.0, Rnd(360.0), 0.0, 0.3)
			EntityParent(de\OBJ, r\OBJ)
			
			de.Decals = CreateDecal(DECAL_CORROSIVE_1, r\x - 432.0 * RoomScale, r\y + 0.01, r\z, 90.0, Rnd(360.0), 0.0)
			EntityParent(de\OBJ, r\OBJ)
			
			r\Objects[0] = CreatePivot(r\OBJ)
			PositionEntity(r\Objects[0], r\x - 808.0 * RoomScale, r\y + 1.0, r\z - 72.0 * RoomScale, True)
			
			it.Items = CreateItem("Dr. L's Burnt Note #1", "paper", r\x - 688.0 * RoomScale, r\y + 1.0, r\z - 16.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Dr. L's Burnt Note #2", "paper", r\x - 808.0 * RoomScale, r\y + 1.0, r\z - 72.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("The Modular Site Project", "paper", r\x + 622.0 * RoomScale, r\y + 125.0 * RoomScale, r\z - 73.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room2_elevator"
			;[Block]
			r\RoomDoors.Doors[0] = CreateDoor(r\x + 448.0 * RoomScale, r\y, r\z, -90.0, r, True, ELEVATOR_DOOR)
			r\RoomDoors[0]\MTFClose = False
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 1024.0 * RoomScale, r\y + 120.0 * RoomScale, r\z, True)
			EntityParent(r\Objects[0], r\OBJ)
			;[End Block]
		Case "room2_cafeteria"
			;[Block]
			d.Doors = CreateDoor(r\x + 1712.0 * RoomScale, r\y - 384.0 * RoomScale, r\z - 1024.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			d\Locked = 1 : d\MTFClose = False : d\DisableWaypoint = True
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			d.Doors = CreateDoor(r\x - 464.0 * RoomScale, r\y - 384.0 * RoomScale, r\z - 1024.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			d\Locked = 1 : d\MTFClose = False : d\DisableWaypoint = True
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			; ~ SCP-294
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 1847.0 * RoomScale, r\y - 240.0 * RoomScale, r\z - 321.0 * RoomScale)
			
			; ~ Spawnpoint for the cups
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x + 1780.0 * RoomScale, r\y - 248.0 * RoomScale, r\z - 276.0 * RoomScale)
			
			For i = 0 To 1
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			it.Items = CreateItem("Cup", "cup", r\x - 508.0 * RoomScale, r\y - 187.0 * RoomScale, r\z + 284.0 * RoomScale, 240, 175, 70)
			EntityParent(it\Collider, r\OBJ) : it\Name = "Cup of Orange Juice"
			
			it.Items = CreateItem("Cup", "cup", r\x + 1412.0 * RoomScale, r\y - 187.0 * RoomScale, r\z - 716.0 * RoomScale, 87, 62, 45)
			EntityParent(it\Collider, r\OBJ) : it\Name = "Cup of Coffee"
			
			it.Items = CreateItem("Empty Cup", "emptycup", r\x - 540.0 * RoomScale, r\y - 187.0 * RoomScale, r\z + 124.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Quarter", "25ct", r\x - 447.0 * RoomScale, r\y - 334.0 * RoomScale, r\z + 36.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("SCP-588", "scp588", r\x + 1409.0 * RoomScale, r\y - 334.0 * RoomScale, r\z - 732.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room2_nuke"
			;[Block]
			d.Doors = CreateDoor(r\x + 576.0 * RoomScale, r\y, r\z + 152.0 * RoomScale, 90.0, r, False, ONE_SIDED_DOOR, KEY_CARD_5)
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True), EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True) - 0.09, True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True), EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True) + 0.09, True)
			
			d.Doors = CreateDoor(r\x - 32.0 * RoomScale, r\y + 3808.0 * RoomScale, r\z + 692.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_5)
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True), EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True) - 0.075, True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True), EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True) + 0.075, True)
			
			d.Doors = CreateDoor(r\x - 288.0 * RoomScale, r\y + 3808.0 * RoomScale, r\z + 896.0 * RoomScale, 180.0, r, False, DEFAULT_DOOR, KEY_CARD_5)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			; ~ Elevators
			r\RoomDoors.Doors[0] = CreateDoor(r\x + 1200.0 * RoomScale, r\y, r\z, -90.0, r, True, ELEVATOR_DOOR)
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x + 1200.0 * RoomScale, r\y + 3808.0 * RoomScale, r\z, -90.0, r, False, ELEVATOR_DOOR)
			
			; ~ Elevators' pivots
			r\Objects[4] = CreatePivot()
			PositionEntity(r\Objects[4], r\x + 1504.0 * RoomScale, r\y + 240.0 * RoomScale, r\z)
			
			r\Objects[5] = CreatePivot()
			PositionEntity(r\Objects[5], r\x + 1504.0 * RoomScale, r\y + 4048.0 * RoomScale, r\z)
			
			; ~ Body
			r\Objects[6] = CreatePivot()
			PositionEntity(r\Objects[6], r\x + 1110.0 * RoomScale, r\y + 36.0 * RoomScale, r\z - 208.0 * RoomScale)
			
			For i = 4 To 6
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			For k = 0 To 1
				r\Objects[k * 2] = CopyEntity(lvr_I\LeverModelID[LEVER_BASE_MODEL])
				r\Objects[k * 2 + 1] = CopyEntity(lvr_I\LeverModelID[LEVER_HANDLE_MODEL])
				r\Levers[k] = r\Objects[k * 2 + 1]
				
				For i = 0 To 1
					ScaleEntity(r\Objects[k * 2 + i], 0.04, 0.04, 0.04)
					PositionEntity(r\Objects[k * 2 + i], r\x - 495.0 * RoomScale, r\y + 4016.0 * RoomScale, r\z - (553.0 - (132.0 * k)) * RoomScale)
					EntityParent(r\Objects[k * 2 + i], r\OBJ)
				Next
				RotateEntity(r\Objects[k * 2], 0.0, -270.0, 0.0)
				RotateEntity(r\Objects[k * 2 + 1], 10.0, -90.0, 0.0)
				EntityPickMode(r\Objects[k * 2 + 1], 1, False)
				EntityRadius(r\Objects[k * 2 + 1], 0.1)
			Next
			
			it.Items = CreateItem("Nuclear Device Document", "paper", r\x - 464.0 * RoomScale, r\y + 3958.0 * RoomScale, r\z - 710.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Ballistic Vest", "vest", r\x - 248.0 * RoomScale, r\y + 3958.0 * RoomScale, r\z - 818.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, -90.0, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			sc.SecurityCams = CreateSecurityCam(r\x + 1121.0 * RoomScale, r\y + 4191.0 * RoomScale, r\z - 306.0 * RoomScale, r)
			sc\Angle = 90.0 : sc\Turn = 45.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			;[End Block]
		Case "room2_mt"
			;[Block]
			; ~ Elevators
			r\RoomDoors.Doors[0] = CreateDoor(r\x + 256.0 * RoomScale, r\y, r\z + 656.0 * RoomScale, -90.0, r, True, ELEVATOR_DOOR)
			
			r\RoomDoors.Doors[2] = CreateDoor(r\x - 256.0 * RoomScale, r\y, r\z - 656.0 * RoomScale, 90.0, r, True, ELEVATOR_DOOR)
			
			Temp = ((Int(AccessCode) * 3) Mod 10000)
			If Temp < 1000 Then Temp = Temp + 1000
			d.Doors = CreateDoor(r\x, r\y, r\z, 0.0, r, False, BIG_DOOR, KEY_MISC, Temp)
			PositionEntity(d\Buttons[0], r\x + 230.0 * RoomScale, EntityY(d\Buttons[1], True), r\z - 384.0 * RoomScale, True)
			RotateEntity(d\Buttons[0], 0.0, -90.0, 0.0, True)
			PositionEntity(d\Buttons[1], r\x - 230.0 * RoomScale, EntityY(d\Buttons[1], True), r\z + 384.0 * RoomScale, True)		
			RotateEntity(d\Buttons[1], 0.0, 90.0, 0.0, True)
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 2640.0 * RoomScale, r\y + MTGridY, r\z + 400.0 * RoomScale)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x - 4336.0 * RoomScale, r\y + MTGridY, r\z - 2512.0 * RoomScale)
			
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x + 560.0 * RoomScale, r\y + 240.0 * RoomScale, r\z + 656.0 * RoomScale)
			
			For i = 0 To 2
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			r\Objects[4] = CreatePivot()
			PositionEntity(r\Objects[4], r\x - 560.0 * RoomScale, r\y + 240.0 * RoomScale, r\z - 656.0 * RoomScale)
			EntityParent(r\Objects[4], r\OBJ)
			
			de.Decals = CreateDecal(DECAL_CORROSIVE_1, r\x + 64.0 * RoomScale, r\y + 0.005, r\z + 144.0 * RoomScale, 90.0, Rnd(360.0), 0.0)
			EntityParent(de\OBJ, r\OBJ)
			
			it.Items = CreateItem("Scorched Note", "paper", r\x + 64.0 * RoomScale, r\y + 144.0 * RoomScale, r\z - 384.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "cont2_008"
			;[Block]
			; ~ Decontamination gateway doors
			r\RoomDoors.Doors[0] = CreateDoor(r\x - 96.0 * RoomScale, r\y - 5104.0 * RoomScale, r\z - 384.0 * RoomScale, 180.0, r, True, ONE_SIDED_DOOR, KEY_CARD_4)
			r\RoomDoors[0]\AutoClose = False
			FreeEntity(r\RoomDoors[0]\Buttons[0]) : r\RoomDoors[0]\Buttons[0] = 0
			PositionEntity(r\RoomDoors[0]\Buttons[1], EntityX(r\RoomDoors[0]\Buttons[1], True) - 0.08, EntityY(r\RoomDoors[0]\Buttons[1], True), EntityZ(r\RoomDoors[0]\Buttons[1], True), True)
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x - 96.0 * RoomScale, r\y - 5104.0 * RoomScale, r\z + 256.0 * RoomScale, 0.0, r, False, ONE_SIDED_DOOR, KEY_CARD_4)
			r\RoomDoors[1]\AutoClose = False
			PositionEntity(r\RoomDoors[1]\Buttons[0], r\x + 70.0 * RoomScale, EntityY(r\RoomDoors[1]\Buttons[0], True), r\z - 24.0 * RoomScale, True)
			RotateEntity(r\RoomDoors[1]\Buttons[0], 0.0, -90.0, 0.0, True)	
			
			r\RoomDoors[0]\LinkedDoor = r\RoomDoors[1]
			r\RoomDoors[1]\LinkedDoor = r\RoomDoors[0]
			
			r\RoomDoors.Doors[2] = CreateDoor(r\x - 816.0 * RoomScale, r\y - 5104.0 * RoomScale, r\z - 384.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_4)
			PositionEntity(r\RoomDoors[2]\Buttons[1], EntityX(r\RoomDoors[2]\Buttons[1], True) + 0.08, EntityY(r\RoomDoors[2]\Buttons[1], True), EntityZ(r\RoomDoors[2]\Buttons[1], True), True)
			r\RoomDoors[2]\Locked = 1
			
			; ~ Elevators' doors
			r\RoomDoors.Doors[3] = CreateDoor(r\x + 448.0 * RoomScale, r\y, r\z, -90.0, r, True, ELEVATOR_DOOR)
			
			r\RoomDoors.Doors[4] = CreateDoor(r\x + 448.0 * RoomScale, r\y - 5104.0 * RoomScale, r\z, -90.0, r, False, ELEVATOR_DOOR)
			
			d.Doors = CreateDoor(r\x + 96.0 * RoomScale, r\y - 5104.0 * RoomScale, r\z - 576.0 * RoomScale, 90.0, r)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True), EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True) + 0.162, True)
			
			d.Doors = CreateDoor(r\x - 456.0 * RoomScale, r\y - 5104.0 * RoomScale, r\z - 736.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_4)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			; ~ The container
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x - 62.0 * RoomScale, r\y - 4985.0 * RoomScale, r\z + 889.0 * RoomScale)
			
			; ~ The lid of the container
			r\Objects[1] = LoadRMesh("GFX\map\hcz\008_2.rmesh", Null)
			ScaleEntity(r\Objects[1], RoomScale, RoomScale, RoomScale)
			PositionEntity(r\Objects[1], r\x - 62.0 * RoomScale, r\y - 4954.0 * RoomScale, r\z + 945.0 * RoomScale)
			RotateEntity(r\Objects[1], 85.0, 0.0, 0.0, True)
			
			r\Levers[0] = r\Objects[1]
			
			Tex = LoadTexture_Strict("GFX\map\textures\glass.png", 1 + 2)
			r\Objects[2] = CreateSprite()
			EntityTexture(r\Objects[2], Tex)
			DeleteSingleTextureEntryFromCache(Tex)
			SpriteViewMode(r\Objects[2], 2)
			ScaleSprite(r\Objects[2], 194.0 * RoomScale * 0.5, 194.0 * RoomScale * 0.5)
			PositionEntity(r\Objects[2], r\x - 640.0 * RoomScale, r\y - 4881.0 * RoomScale, r\z + 800.0 * RoomScale)
			TurnEntity(r\Objects[2], 0.0, 90.0, 0.0)			
			
			; ~ SCP-173's spawnpoint
			r\Objects[3] = CreatePivot()
			PositionEntity(r\Objects[3], r\x - 820.0 * RoomScale, r\y - 4985.0 * RoomScale, r\z + 657.0 * RoomScale)
			
			; ~ SCP-173's attack point
			r\Objects[4] = CreatePivot()
			PositionEntity(r\Objects[4], r\x - 384.0 * RoomScale, r\y - 4985.0 * RoomScale, r\z + 752.0 * RoomScale)
			
			; ~ Red light
			
			r\Objects[5] = CreateRedLight(r\x - 622.0 * RoomScale, r\y - 4735.0 * RoomScale, r\z + 672.5 * RoomScale)
			
			; ~ Spawnpoint for the scientist used in the "SCP-008-1's scene"
			r\Objects[6] = CreatePivot()
			PositionEntity(r\Objects[6], r\x + 160.0 * RoomScale, r\y + 670.0 * RoomScale, r\z - 384.0 * RoomScale)
			
			; ~ Spawnpoint for the player
			r\Objects[7] = CreatePivot()
			PositionEntity(r\Objects[7], r\x, r\y + 672.0 * RoomScale, r\z + 350.0 * RoomScale)
			
			; ~ Elevators' pivots
			r\Objects[8] = CreatePivot()
			PositionEntity(r\Objects[8], r\x + 752.0 * RoomScale, r\y + 240.0 * RoomScale, r\z)
			
			r\Objects[9] = CreatePivot()
			PositionEntity(r\Objects[9], r\x + 752.0 * RoomScale, r\y - 4864.0 * RoomScale, r\z)
			
			For i = 0 To 9
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			it.Items = CreateItem("Hazmat Suit", "hazmatsuit", r\x - 537.0 * RoomScale, r\y - 4895.0 * RoomScale, r\z - 66.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, 90.0, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Document SCP-008", "paper", r\x - 944.0 * RoomScale, r\y - 5008.0 * RoomScale, r\z + 672.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, 0.0, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			sc.SecurityCams = CreateSecurityCam(r\x + 384.0 * RoomScale, r\y - 4654.0 * RoomScale, r\z + 1168.0 * RoomScale, r)
			sc\Angle = 135.0 : sc\Turn = 45.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			;[End Block]
		Case "cont1_035"
			;[Block]
			r\RoomDoors.Doors[0] = CreateDoor(r\x - 296.0 * RoomScale, r\y, r\z - 672.0 * RoomScale, 180.0, r, True, ONE_SIDED_DOOR, KEY_CARD_5)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\Locked = 1
			FreeEntity(r\RoomDoors[0]\Buttons[0]) : r\RoomDoors[0]\Buttons[0] = 0
			PositionEntity(r\RoomDoors[0]\Buttons[1], r\x - 164.0 * RoomScale, EntityY(r\RoomDoors[0]\Buttons[1], True), EntityZ(r\RoomDoors[0]\Buttons[1], True), True)
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x - 296.0 * RoomScale, r\y, r\z - 144.0 * RoomScale, 0.0, r, False, ONE_SIDED_DOOR)
			r\RoomDoors[1]\AutoClose = False : r\RoomDoors[1]\Locked = 1
			PositionEntity(r\RoomDoors[1]\Buttons[0], r\x - 438.0 * RoomScale, EntityY(r\RoomDoors[1]\Buttons[0], True), r\z - 480.0 * RoomScale, True)
			RotateEntity(r\RoomDoors[1]\Buttons[0], 0.0, 90.0, 0.0, True)
			FreeEntity(r\RoomDoors[1]\Buttons[1]) : r\RoomDoors[1]\Buttons[1] = 0
			
			r\RoomDoors[0]\LinkedDoor = r\RoomDoors[1]
			r\RoomDoors[1]\LinkedDoor = r\RoomDoors[0]
			
			; ~ Door to the control room
			r\RoomDoors.Doors[2] = CreateDoor(r\x + 384.0 * RoomScale, r\y, r\z - 672.0 * RoomScale, 180.0, r, False, DEFAULT_DOOR, KEY_CARD_5)
			
			; ~ Door to the storage room
			r\RoomDoors.Doors[3] = CreateDoor(r\x + 768.0 * RoomScale, r\y, r\z + 512.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_MISC, "5731")
			
			For i = 0 To 1
				r\Objects[i * 2] = CopyEntity(lvr_I\LeverModelID[LEVER_BASE_MODEL])
				r\Objects[i * 2 + 1] = CopyEntity(lvr_I\LeverModelID[LEVER_HANDLE_MODEL])
				
				r\Levers[i] = r\Objects[i * 2 + 1]
				
				For k = 0 To 1
					ScaleEntity(r\Objects[i * 2 + k], 0.04, 0.04, 0.04)
					PositionEntity(r\Objects[i * 2 + k], r\x + 210.0 * RoomScale, r\y + 224.0 * RoomScale, r\z - (208.0 - i * 76.0) * RoomScale)
					EntityParent(r\Objects[i * 2 + k], r\OBJ)
				Next
				
				RotateEntity(r\Objects[i * 2], 0, -90.0 - 180.0, 0.0)
				RotateEntity(r\Objects[i * 2 + 1], -80.0, -90.0, 0.0)
				EntityPickMode(r\Objects[i * 2 + 1], 1, False)
				EntityRadius(r\Objects[i * 2 + 1], 0.1)	
			Next
			
			; ~ The control room
			r\Objects[3] = CreatePivot()
			PositionEntity(r\Objects[3], r\x + 456.0 * RoomScale, r\y + 0.5, r\z + 400.0 * RoomScale)
			
			r\Objects[4] = CreatePivot()
			PositionEntity(r\Objects[4], r\x - 576.0 * RoomScale, r\y + 0.5, r\z + 640.0 * RoomScale)
			
			For i = 3 To 4
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			em.Emitters = CreateEmitter(r\x - 269.0 * RoomScale, r\y + 20.0, r\z + 624.0 * RoomScale, 0)
			em\RandAngle = 15.0 : em\Speed = 0.05 : em\SizeChange = 0.007 : em\AlphaChange = -0.006 : em\Gravity = -0.24
			TurnEntity(em\OBJ, 90.0, 0.0, 0.0)
			EntityParent(em\OBJ, r\OBJ)
			r\Objects[5] = em\OBJ
			
			em.Emitters = CreateEmitter(r\x - 269.0 * RoomScale, r\y + 20.0, r\z + 135.0 * RoomScale, 0)
			em\RandAngle = 15.0 : em\Speed = 0.05 : em\SizeChange = 0.007 : em\AlphaChange = -0.006 : em\Gravity = -0.24
			TurnEntity(em\OBJ, 90.0, 0.0, 0.0)
			EntityParent(em\OBJ, r\OBJ)
			r\Objects[6] = em\OBJ
			
			; ~ The corners of the cont chamber (needed to calculate whether the player is inside the chamber)
			r\Objects[7] = CreatePivot()
			PositionEntity(r\Objects[7], r\x - 720.0 * RoomScale, r\y + 0.5, r\z + 880.0 * RoomScale)
			
			r\Objects[8] = CreatePivot()
			PositionEntity(r\Objects[8], r\x + 176.0 * RoomScale, r\y + 0.5, r\z - 144.0 * RoomScale)	
			
			r\Objects[9] = LoadMesh_Strict("GFX\map\Props\cont1_035_label.b3d")
			Update035Label(r\Objects[9])
			ScaleEntity(r\Objects[9], RoomScale, RoomScale, RoomScale)
			PositionEntity(r\Objects[9], r\x - 30.0 * RoomScale, r\y + 230.0 * RoomScale, r\z - 704.0 * RoomScale)
			
			For i = 7 To 9
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			it.Items = CreateItem("SCP-035 Addendum", "paper", r\x + 687.0 * RoomScale, r\y + 240.0 * RoomScale, r\z + 127.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, r\Angle, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Radio Transceiver", "radio", r\x - 544.0 * RoomScale, r\y + 0.5, r\z + 704.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("SCP-500-01", "scp500pill", r\x + 1168.0 * RoomScale, r\y + 250.0 * RoomScale, r\z + 576 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Metal Panel", "scp148", r\x - 360.0 * RoomScale, r\y + 0.5, r\z + 644.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Document SCP-035", "paper", r\x + 1168.0 * RoomScale, r\y + 100.0 * RoomScale, r\z + 408.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "cont3_513"
			;[Block]
			d.Doors = CreateDoor(r\x - 704.0 * RoomScale, r\y, r\z + 304.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True), EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True) + 0.061, True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True), EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True) - 0.031, True)
			
			; ~ Dust decals
			For i = 0 To 9
				Select i
					Case 0
						;[Block]
						xTemp = 0.0
						zTemp = 300.0
						Scale = Rnd(0.8, 1.0)
						;[End Block]
					Case 1
						;[Block]
						xTemp = -87.0
						zTemp = 466.0
						Scale = Rnd(0.1, 0.2)
						;[End Block]
					Case 2
						;[Block]
						xTemp = -177.0
						zTemp = 467.0
						Scale = Rnd(0.2, 0.3)
						;[End Block]
					Case 3
						;[Block]
						xTemp = -79.0
						zTemp = 215.0
						Scale = Rnd(0.1, 0.2)
						;[End Block]
					Case 4
						;[Block]
						xTemp = -13.0
						zTemp = 201.0
						Scale = Rnd(0.1, 0.15)
						;[End Block]
					Case 5
						;[Block]
						xTemp = 85.0
						zTemp = 97.0
						Scale = Rnd(0.2, 0.3)
						;[End Block]
					Case 6
						;[Block]
						xTemp = 205.0
						zTemp = 180.0
						Scale = Rnd(0.1, 0.2)
						;[End Block]
					Case 7
						;[Block]
						xTemp = 235.0
						zTemp = 114.0
						Scale = Rnd(0.1, 0.2)
						;[End Block]
					Case 8
						;[Block]
						xTemp = 182.0
						zTemp = 47.0
						Scale = Rnd(0.1, 0.2)
						;[End Block]
					Case 9
						;[Block]
						xTemp = 52.0
						zTemp = 200.0
						Scale = Rnd(0.2, 0.3)
						;[End Block]
				End Select
				If i < 3 Then
					yTemp = 0.0
				Else
					yTemp = 3.0
				EndIf
				de.Decals = CreateDecal(DECAL_CORROSIVE_1, r\x + xTemp * RoomScale, r\y + yTemp * RoomScale + 0.005, r\z + zTemp * RoomScale, 90.0, Rnd(360.0), 0.0, Scale, Rnd(0.6, 0.8), 1)
				EntityParent(de\OBJ, r\OBJ)
			Next
			
			sc.SecurityCams = CreateSecurityCam(r\x - 450.0 * RoomScale, r\y + 420.0 * RoomScale, r\z + 250.0 * RoomScale, r)
			sc\Angle = 135.0 : sc\Turn = 0.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			
			it.Items = CreateItem("SCP-513", "scp513", r\x, r\y + 196.0 * RoomScale, r\z + 655.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Blood-stained Note", "paper", r\x + 736.0 * RoomScale, r\y + 1.0, r\z + 48.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Document SCP-513", "paper", r\x - 480.0 * RoomScale, r\y + 104.0 * RoomScale, r\z - 176.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "cont3_966"
			;[Block]
			d.Doors = CreateDoor(r\x - 400.0 * RoomScale, r\y, r\z, -90.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			
			d.Doors = CreateDoor(r\x, r\y, r\z - 480.0 * RoomScale, 180.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x, r\y + 0.5, r\z + 512.0 * RoomScale)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x, r\y + 0.5, r\z)
			
			For i = 0 To 1
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			sc.SecurityCams = CreateSecurityCam(r\x - 355.0 * RoomScale, r\y + 450.0 * RoomScale, r\z + 321.0 * RoomScale, r)
			sc\Angle = -45.0 : sc\Turn = 0.0
			TurnEntity(sc\CameraOBJ, 30.0, 0.0, 0.0)
			
			it.Items = CreateItem("Night Vision Goggles", "nvg", r\x + 173.0 * RoomScale, r\y + 0.5, r\z + 631.0 * RoomScale)
			it\State = Rnd(100.0, 1000.0)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room3_storage"
			;[Block]
			; ~ Elevator Doors
			r\RoomDoors.Doors[0] = CreateDoor(r\x, r\y, r\z + 448.0 * RoomScale, 0.0, r, True, ELEVATOR_DOOR)
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x + 5840.0 * RoomScale, r\y - 5632.0 * RoomScale, r\z + 1048.0 * RoomScale, 0.0, r, False, ELEVATOR_DOOR)
			PositionEntity(r\RoomDoors[1]\Buttons[0], EntityX(r\RoomDoors[1]\Buttons[0], True), EntityY(r\RoomDoors[1]\Buttons[0], True), EntityZ(r\RoomDoors[1]\Buttons[0], True) - 0.031, True)
			PositionEntity(r\RoomDoors[1]\Buttons[1], EntityX(r\RoomDoors[1]\Buttons[1], True), EntityY(r\RoomDoors[1]\Buttons[1], True), EntityZ(r\RoomDoors[1]\Buttons[1], True) + 0.031, True)
			PositionEntity(r\RoomDoors[1]\ElevatorPanel[0], EntityX(r\RoomDoors[1]\ElevatorPanel[0], True), EntityY(r\RoomDoors[1]\ElevatorPanel[0], True), EntityZ(r\RoomDoors[1]\ElevatorPanel[0], True) + 0.031, True)
			PositionEntity(r\RoomDoors[1]\ElevatorPanel[1], EntityX(r\RoomDoors[1]\ElevatorPanel[1], True), EntityY(r\RoomDoors[1]\ElevatorPanel[1], True), EntityZ(r\RoomDoors[1]\ElevatorPanel[1], True) - 0.031, True)
			
			r\RoomDoors.Doors[2] = CreateDoor(r\x + 608.0 * RoomScale, r\y, r\z - 313.0 * RoomScale, 180.0, r, True, ELEVATOR_DOOR)
			PositionEntity(r\RoomDoors[2]\Buttons[0], EntityX(r\RoomDoors[2]\Buttons[0], True), EntityY(r\RoomDoors[2]\Buttons[0], True), EntityZ(r\RoomDoors[2]\Buttons[0], True) + 0.03, True)
			PositionEntity(r\RoomDoors[2]\Buttons[1], EntityX(r\RoomDoors[2]\Buttons[1], True), EntityY(r\RoomDoors[2]\Buttons[1], True), EntityZ(r\RoomDoors[2]\Buttons[1], True) - 0.03, True)
			PositionEntity(r\RoomDoors[2]\ElevatorPanel[0], EntityX(r\RoomDoors[2]\ElevatorPanel[0], True), EntityY(r\RoomDoors[2]\ElevatorPanel[0], True), EntityZ(r\RoomDoors[2]\ElevatorPanel[0], True) - 0.03, True)
			PositionEntity(r\RoomDoors[2]\ElevatorPanel[1], EntityX(r\RoomDoors[2]\ElevatorPanel[1], True), EntityY(r\RoomDoors[2]\ElevatorPanel[1], True), EntityZ(r\RoomDoors[2]\ElevatorPanel[1], True) + 0.03, True)
			
			r\RoomDoors.Doors[3] = CreateDoor(r\x - 456.0 * RoomScale, r\y - 5632.0 * RoomScale, r\z - 824.0 * RoomScale, 180.0, r, False, ELEVATOR_DOOR)
			PositionEntity(r\RoomDoors[3]\Buttons[0], EntityX(r\RoomDoors[3]\Buttons[0], True), EntityY(r\RoomDoors[3]\Buttons[0], True), EntityZ(r\RoomDoors[3]\Buttons[0], True) + 0.031, True)					
			PositionEntity(r\RoomDoors[3]\Buttons[1], EntityX(r\RoomDoors[3]\Buttons[1], True), EntityY(r\RoomDoors[3]\Buttons[1], True), EntityZ(r\RoomDoors[3]\Buttons[1], True) - 0.031, True)
			PositionEntity(r\RoomDoors[3]\ElevatorPanel[0], EntityX(r\RoomDoors[3]\ElevatorPanel[0], True), EntityY(r\RoomDoors[3]\ElevatorPanel[0], True), EntityZ(r\RoomDoors[3]\ElevatorPanel[0], True) - 0.031, True)					
			PositionEntity(r\RoomDoors[3]\ElevatorPanel[1], EntityX(r\RoomDoors[3]\ElevatorPanel[1], True), EntityY(r\RoomDoors[3]\ElevatorPanel[1], True), EntityZ(r\RoomDoors[3]\ElevatorPanel[1], True) + 0.031, True)
			
			; ~ Other doors
			r\RoomDoors.Doors[4] = CreateDoor(r\x + 56.0 * RoomScale, r\y - 5632.0 * RoomScale, r\z + 6344.0 * RoomScale, 90.0, r, False, HEAVY_DOOR)
			r\RoomDoors[4]\AutoClose = False
			For i = 0 To 1
				FreeEntity(r\RoomDoors[4]\Buttons[i]) : r\RoomDoors[4]\Buttons[i] = 0
			Next
			
			d.Doors = CreateDoor(r\x + 1157.0 * RoomScale, r\y - 5632.0 * RoomScale, r\z + 660.0 * RoomScale, 0.0, r, False, HEAVY_DOOR)
			d\AutoClose = False : d\Locked = 1
			For i = 0 To 1
				FreeEntity(d\Buttons[i]) : d\Buttons[i] = 0
			Next
			
			d.Doors = CreateDoor(r\x + 234.0 * RoomScale, r\y - 5632.0 * RoomScale, r\z + 5239.0 * RoomScale, 90.0, r, False, HEAVY_DOOR)
			d\AutoClose = False : d\Locked = 1
			For i = 0 To 1
				FreeEntity(d\Buttons[i]) : d\Buttons[i] = 0
			Next
			
			d.Doors = CreateDoor(r\x + 3446.0 * RoomScale, r\y - 5632.0 * RoomScale, r\z + 6369.0 * RoomScale, 90.0, r, False, HEAVY_DOOR)
			d\AutoClose = False : d\Locked = 1
			For i = 0 To 1
				FreeEntity(d\Buttons[i]) : d\Buttons[i] = 0
			Next
			
			; ~ Elevators' pivots
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x, r\y + 240.0 * RoomScale, r\z + 752.0 * RoomScale)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x + 5840.0 * RoomScale, r\y - 5392.0 * RoomScale, r\z + 1360.0 * RoomScale)
			
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x + 608.0 * RoomScale, r\y + 240.0 * RoomScale, r\z - 624.0 * RoomScale)
			
			r\Objects[3] = CreatePivot()
			PositionEntity(r\Objects[3], r\x - 456.0 * RoomScale, r\y - 5392.0 * RoomScale, r\z - 1136 * RoomScale)
			
			; ~ Waypoints # 1
			r\Objects[4] = CreatePivot()
			PositionEntity(r\Objects[4], r\x + 2128.0 * RoomScale, r\y - 5550.0 * RoomScale, r\z + 2048.0 * RoomScale)
			
			r\Objects[5] = CreatePivot()
			PositionEntity(r\Objects[5], r\x + 2128.0 * RoomScale, r\y - 5550.0 * RoomScale, r\z - 1136.0 * RoomScale)
			
			r\Objects[6] = CreatePivot()
			PositionEntity(r\Objects[6], r\x + 3824.0 * RoomScale, r\y - 5550.0 * RoomScale, r\z - 1168.0 * RoomScale)
			
			r\Objects[7] = CreatePivot()
			PositionEntity(r\Objects[7], r\x + 3760.0 * RoomScale, r\y - 5550.0 * RoomScale, r\z + 2048.0 * RoomScale)
			
			r\Objects[8] = CreatePivot()
			PositionEntity(r\Objects[8], r\x + 4848.0 * RoomScale, r\y - 5550.0 * RoomScale, r\z + 112.0 * RoomScale)
			
			; ~ Waypoints # 2
			r\Objects[9] = CreatePivot()
			PositionEntity(r\Objects[9], r\x + 592.0 * RoomScale, r\y - 5550.0 * RoomScale, r\z + 6352.0 * RoomScale)
			
			r\Objects[10] = CreatePivot()
			PositionEntity(r\Objects[10], r\x + 2928.0 * RoomScale, r\y - 5550.0 * RoomScale, r\z + 6352.0 * RoomScale)
			
			r\Objects[11] = CreatePivot()
			PositionEntity(r\Objects[11], r\x + 2928.0 * RoomScale, r\y - 5550.0 * RoomScale, r\z + 5200.0 * RoomScale)
			
			r\Objects[12] = CreatePivot()
			PositionEntity(r\Objects[12], r\x + 592.0 * RoomScale, r\y - 5550.0 * RoomScale, r\z + 5200.0 * RoomScale)
			
			; ~ Waypoints # 3
			r\Objects[13] = CreatePivot()
			PositionEntity(r\Objects[13], r\x + 1136.0 * RoomScale, r\y - 5550.0 * RoomScale, r\z + 2944.0 * RoomScale)
			
			r\Objects[14] = CreatePivot()
			PositionEntity(r\Objects[14], r\x + 1104.0 * RoomScale, r\y - 5550.0 * RoomScale, r\z + 1184.0 * RoomScale)
			
			r\Objects[15] = CreatePivot()
			PositionEntity(r\Objects[15], r\x - 464.0 * RoomScale, r\y - 5550.0 * RoomScale, r\z + 1216.0 * RoomScale)
			
			r\Objects[16] = CreatePivot()
			PositionEntity(r\Objects[16], r\x - 432.0 * RoomScale, r\y - 5550.0 * RoomScale, r\z + 2976.0 * RoomScale)
			
			; ~ Corpses
			r\Objects[17] = CreatePivot()
			PositionEntity(r\Objects[17], r\x + 2200.0 * RoomScale, r\y - 5540.0 * RoomScale, r\z + 2932.0 * RoomScale)
			
			r\Objects[18] = CreatePivot()
			PositionEntity(r\Objects[18], r\x + 1015.5 * RoomScale, r\y - 5540.0 * RoomScale, r\z + 2964.0 * RoomScale)
			
			For i = 0 To 18
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			For k = 10 To 11
				r\Objects[k * 2] = CopyEntity(lvr_I\LeverModelID[LEVER_BASE_MODEL])
				r\Objects[k * 2 + 1] = CopyEntity(lvr_I\LeverModelID[LEVER_HANDLE_MODEL])
				
				r\Levers[k - 10] = r\Objects[k * 2 + 1]
				
				For i = 0 To 1
					ScaleEntity(r\Objects[k * 2 + i], 0.04, 0.04, 0.04)
					If k = 10
						PositionEntity(r\Objects[k * 2 + i], r\x + 3095.5 * RoomScale, r\y - 5461.0 * RoomScale, r\z + 6568.0 * RoomScale)
					Else
						PositionEntity(r\Objects[k * 2 + i], r\x + 1215.5 * RoomScale, r\y - 5461.0 * RoomScale, r\z + 3164.0 * RoomScale)
					EndIf
					EntityParent(r\Objects[k * 2 + i], r\OBJ)
				Next
				RotateEntity(r\Objects[k * 2], 0.0, 0.0, 0.0)
				RotateEntity(r\Objects[k * 2 + 1], -10.0, 0.0 - 180.0, 0.0)
				EntityPickMode(r\Objects[k * 2 + 1], 1, False)
				EntityRadius(r\Objects[k * 2 + 1], 0.1)
			Next
			
			em.Emitters = CreateEmitter(r\x + 5218.0 * RoomScale, r\y - 5584.0 * RoomScale, r\z - 600.0 * RoomScale, 0)
			em\RandAngle = 15.0 : em\Speed = 0.03 : em\SizeChange = 0.01 : em\AlphaChange = -0.006 : em\Gravity = -0.2 
			TurnEntity(em\OBJ, 20.0, -100.0, 0.0)
			EntityParent(em\OBJ, r\OBJ) : em\room = r
			
			Select Rand(3)
				Case 1
					;[Block]
					xTemp = 2312.0
					zTemp = -952.0
					;[End Block]
				Case 2
					;[Block]
					xTemp = 3032.0
					zTemp = 1288.0
					;[End Block]
				Case 3
					;[Block]
					xTemp = 2824.0
					zTemp = 2808.0
					;[End Block]
			End Select
			
			it.Items = CreateItem("Black Severed Hand", "hand2", r\x + xTemp * RoomScale, r\y - 5596.0 * RoomScale + 1.0, r\z + zTemp * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Level 1 Key Card", "key1", r\x + 2204.0 * RoomScale, r\y - 5540.0 * RoomScale, r\z + 2933.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			de.Decals = CreateDecal(DECAL_BLOOD_2, r\x + xTemp * RoomScale, r\y - 5632.0 * RoomScale + 0.01, r\z + zTemp * RoomScale, 90.0, Rnd(360.0), 0.0, 0.5)
			EntityParent(de\OBJ, r\OBJ)
			
			de.Decals = CreateDecal(DECAL_BLOOD_2, r\x + 2268.0 * RoomScale, r\y - 5510.0 * RoomScale, r\z + 2932.0 * RoomScale, 0.0, r\Angle + 270.0, 0.0, 0.3)
			EntityParent(de\OBJ, r\OBJ)
			
			de.Decals = CreateDecal(DECAL_BLOOD_6, r\x + 1215.5 * RoomScale, r\y - 5632.0 * RoomScale + 0.01, r\z + 2964.0 * RoomScale, 90.0, r\Angle + 180.0, 0.0, 0.4)
			EntityParent(de\OBJ, r\OBJ)
			;[End Block]
		Case "cont2_049"
			;[Block]
			; ~ Elevator doors
			r\RoomDoors.Doors[0] = CreateDoor(r\x + 320.0 * RoomScale, r\y, r\z + 640.0 * RoomScale, -90.0, r, True, ELEVATOR_DOOR)
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x + 3024.0 * RoomScale, r\y - 3520.0 * RoomScale, r\z + 1824.0 * RoomScale, -90.0, r, False, ELEVATOR_DOOR)
			
			r\RoomDoors.Doors[2] = CreateDoor(r\x - 672.0 * RoomScale, r\y, r\z - 416.0 * RoomScale, 0.0, r, True, ELEVATOR_DOOR)
			PositionEntity(r\RoomDoors[2]\ElevatorPanel[1], EntityX(r\RoomDoors[2]\ElevatorPanel[1], True), EntityY(r\RoomDoors[2]\ElevatorPanel[1], True) + 0.05, EntityZ(r\RoomDoors[2]\ElevatorPanel[1], True), True)
			
			r\RoomDoors.Doors[3] = CreateDoor(r\x - 2766.0 * RoomScale, r\y - 3520.0 * RoomScale, r\z - 1600.0 * RoomScale, 0.0, r, False, ELEVATOR_DOOR)
			PositionEntity(r\RoomDoors[3]\ElevatorPanel[1], EntityX(r\RoomDoors[3]\ElevatorPanel[1], True), EntityY(r\RoomDoors[3]\ElevatorPanel[1], True) + 0.05, EntityZ(r\RoomDoors[3]\ElevatorPanel[1], True), True)
			
			; ~ Storage room doors
			r\RoomDoors.Doors[4] = CreateDoor(r\x + 272.0 * RoomScale, r\y - 3552.0 * RoomScale, r\z + 104.0 * RoomScale, 90.0, r, True, DEFAULT_DOOR, KEY_CARD_2)
			r\RoomDoors[4]\Locked = 1
			
			r\RoomDoors.Doors[5] = CreateDoor(r\x + 272.0 * RoomScale, r\y - 3520.0 * RoomScale, r\z - 1824.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_2)
			r\RoomDoors[5]\Locked = 1
			
			r\RoomDoors.Doors[6] = CreateDoor(r\x - 272.0 * RoomScale, r\y - 3520.0 * RoomScale, r\z + 1824.0 * RoomScale, 90.0, r, True, DEFAULT_DOOR, KEY_CARD_2)
			r\RoomDoors[6]\Locked = 1
			
			; ~ DNA door
			d.Doors = CreateDoor(r\x, r\y, r\z, 0.0, r, False, HEAVY_DOOR, KEY_HAND_BLACK)
			
			; ~ Containment door
			d.Doors = CreateDoor(r\x - 272.0 * RoomScale, r\y - 3552.0 * RoomScale, r\z + 98.0 * RoomScale, 90.0, r, True, BIG_DOOR)
			d\MTFClose = False : d\Locked = 1
			For i = 0 To 1
				FreeEntity(d\Buttons[i]) : d\Buttons[i] = 0
			Next
			
			; ~ Other doors
			d.Doors = CreateDoor(r\x - 896.0 * RoomScale, r\y, r\z - 640.0 * RoomScale, 90.0, r, False, HEAVY_DOOR)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[1]) : d\Buttons[1] = 0
			
			d.Doors = CreateDoor(r\x - 2990.0 * RoomScale, r\y - 3520.0 * RoomScale, r\z - 1824.0 * RoomScale, 90.0, r, False, HEAVY_DOOR)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[1]) : d\Buttons[1] = 0
			
			d.Doors = CreateDoor(r\x - 2766.0 * RoomScale, r\y - 3520.0 * RoomScale, r\z - 2048.0 * RoomScale, 0.0, r, False, HEAVY_DOOR)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			
			d.Doors = CreateDoor(r\x + 2720.0 * RoomScale, r\y - 3520.0 * RoomScale, r\z + 2048.0 * RoomScale, 180.0, r, False, DEFAULT_DOOR)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			; ~ Elevators' pivots
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 624.0 * RoomScale, r\y + 240.0 * RoomScale, r\z + 640.0 * RoomScale)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x + 3328.0 * RoomScale, r\y - 3280.0 * RoomScale, r\z + 1824.0 * RoomScale)
			
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x - 672.0 * RoomScale, r\y + 240.0 * RoomScale, r\z - 112.0 * RoomScale)
			
			r\Objects[3] = CreatePivot()
			PositionEntity(r\Objects[3], r\x - 2766.0 * RoomScale, r\y - 3280.0 * RoomScale, r\z - 1296.0 * RoomScale)
			
			; ~ Zombie # 1
			r\Objects[4] = CreatePivot()
			PositionEntity(r\Objects[4], r\x + 528.0 * RoomScale, r\y - 3440.0 * RoomScale, r\z + 96.0 * RoomScale)
			
			; ~ Zombie # 2
			r\Objects[5] = CreatePivot()
			PositionEntity(r\Objects[5], r\x + 64.0 * RoomScale, r\y - 3440.0 * RoomScale, r\z - 1000.0 * RoomScale)
			
			For i = 0 To 5
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			For k = 0 To 1
				r\Objects[k * 2 + 6] = CopyEntity(lvr_I\LeverModelID[LEVER_BASE_MODEL])
				r\Objects[k * 2 + 7] = CopyEntity(lvr_I\LeverModelID[LEVER_HANDLE_MODEL])
				
				r\Levers[k] = r\Objects[k * 2 + 7]
				
				For i = 0 To 1
					ScaleEntity(r\Objects[k * 2 + 6 + i], 0.03, 0.03, 0.03)
					
					Select k
						Case 0 ; ~ Power feed
							;[Block]
							PositionEntity(r\Objects[k * 2 + 6 + i], r\x + 866.0 * RoomScale, r\y - 3374.0 * RoomScale, r\z - 854.0 * RoomScale)
							;[End Block]
						Case 1 ; ~ Generator
							;[Block]
							PositionEntity(r\Objects[k * 2 + 6 + i], r\x - 815.0 * RoomScale, r\y - 3400.0 * RoomScale, r\z + 1094.0 * RoomScale)
							;[End Block]
					End Select
					EntityParent(r\Objects[k * 2 + 6 + i], r\OBJ)
				Next
				RotateEntity(r\Objects[k * 2 + 6], 0.0, 180.0 + 90.0 * (Not k), 0.0)
				RotateEntity(r\Objects[k * 2 + 7], 81.0 - 92.0 * k, 90.0 * (Not k), 0.0)
				EntityPickMode(r\Objects[k * 2 + 7], 1, False)
				EntityRadius(r\Objects[k * 2 + 7], 0.1)
			Next
			
			r\Objects[10] = CreatePivot()
			PositionEntity(r\Objects[10], r\x - 832.0 * RoomScale, r\y - 3484.0 * RoomScale, r\z + 1572.0 * RoomScale)
			
			; ~ Spawnpoint for the map layout document
			r\Objects[11] = CreatePivot()
			PositionEntity(r\Objects[11], r\x + 2720.0 * RoomScale, r\y - 3516.0 * RoomScale, r\z + 1824.0 * RoomScale)
			
			r\Objects[12] = CreatePivot()
			PositionEntity(r\Objects[12], r\x - 2720.0 * RoomScale, r\y - 3516.0 * RoomScale, r\z - 1824.0 * RoomScale)
			
			For i = 10 To 12
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			it.Items = CreateItem("Document SCP-049", "paper", r\x - 693.0 * RoomScale, r\y - 3332.0 * RoomScale, r\z + 702.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Level 4 Key Card", "key4", r\x - 564.0 * RoomScale, r\y - 3412.0 * RoomScale, r\z + 698.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("First Aid Kit", "firstaid", r\x + 385.0 * RoomScale, r\y - 3412.0 * RoomScale, r\z + 271.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room2_2_lcz"
			;[Block]
			For r2.Rooms = Each Rooms
				If r2 <> r Then
					If r2\RoomTemplate\Name = "room2_2_lcz" Then
						r\Objects[0] = CopyEntity(r2\Objects[0]) ; ~ Don't load the mesh again
						Exit
					EndIf
				EndIf
			Next
			If (Not r\Objects[0]) Then r\Objects[0] = LoadRMesh("GFX\map\lcz\room2_2_lcz_fan.rmesh", Null)
			ScaleEntity(r\Objects[0], RoomScale, RoomScale, RoomScale)
			PositionEntity(r\Objects[0], r\x - 270.0 * RoomScale, r\y + 528.0 * RoomScale, r\z)
			EntityParent(r\Objects[0], r\OBJ)
			;[End Block]
		Case "cont2_012"
			;[Block]
			d.Doors = CreateDoor(r\x + 256.0 * RoomScale, r\y, r\z + 672.0 * RoomScale, 270.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			
			r\RoomDoors.Doors[0] = CreateDoor(r\x - 512.0 * RoomScale, r\y - 768.0 * RoomScale, r\z - 320.0 * RoomScale, 0.0, r)
			r\RoomDoors[0]\Locked = 1
			PositionEntity(r\RoomDoors[0]\Buttons[0], r\x + 176.0 * RoomScale, r\y - 512.0 * RoomScale, r\z - 352.0 * RoomScale, True)
			FreeEntity(r\RoomDoors[0]\Buttons[1]) : r\RoomDoors[0]\Buttons[1] = 0
			
			r\Objects[0] = CopyEntity(lvr_I\LeverModelID[LEVER_BASE_MODEL])
			r\Objects[1] = CopyEntity(lvr_I\LeverModelID[LEVER_HANDLE_MODEL])
			r\Levers[0] = r\Objects[1]
			
			For i = 0 To 1
				ScaleEntity(r\Objects[i], 0.04, 0.04, 0.04)
				PositionEntity(r\Objects[i], r\x + 240.0 * RoomScale, r\y - 512.0 * RoomScale, r\z - 364.0 * RoomScale, True)
				EntityParent(r\Objects[i], r\OBJ)
			Next
			RotateEntity(r\Objects[1], 10.0, -180.0, 0.0)
			EntityPickMode(r\Objects[1], 1, False)
			EntityRadius(r\Objects[1], 0.1)
			
			r\Objects[2] = LoadRMesh("GFX\map\lcz\cont2_012_box.rmesh", Null)
			ScaleEntity(r\Objects[2], RoomScale, RoomScale, RoomScale)
			PositionEntity(r\Objects[2], r\x - 360.0 * RoomScale, r\y - 130.0 * RoomScale, r\z + 456.0 * RoomScale)
			
			r\Objects[3] = CreateRedLight(r\x - 43.5 * RoomScale, r\y - 574.0 * RoomScale, r\z - 362.0 * RoomScale)
			
			For i = 2 To 3
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			r\Objects[4] = LoadMesh_Strict("GFX\map\Props\scp_012.b3d")
			ScaleEntity(r\Objects[4], RoomScale, RoomScale, RoomScale)
			PositionEntity(r\Objects[4], r\x - 360.0 * RoomScale, r\y - 180.0 * RoomScale, r\z + 456.0 * RoomScale)
			EntityParent(r\Objects[4], r\Objects[2])
			
			it.Items = CreateItem("Document SCP-012", "paper", r\x - 56.0 * RoomScale, r\y - 576.0 * RoomScale, r\z - 408.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Severed Hand", "hand", r\x - 784.0 * RoomScale, r\y - 576.0 * RoomScale + 0.3, r\z + 640.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			de.Decals = CreateDecal(DECAL_BLOOD_2, r\x - 784.0 * RoomScale, r\y - 768.0 * RoomScale + 0.01, r\z + 640.0 * RoomScale, 90.0, Rnd(360.0), 0.0, 0.5)
			EntityParent(de\OBJ, r\OBJ)
			;[End Block]
		Case "room2_6_hcz"
			;[Block]
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x, r\y + 544.0 * RoomScale, r\z + 512.0 * RoomScale)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x, r\y + 544.0 * RoomScale, r\z - 512.0 * RoomScale)
			
			For i = 0 To 1
				EntityParent(r\Objects[i], r\OBJ)
			Next
			;[End Block]
		Case "room2_2_hcz"
			;[Block]
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 368.0 * RoomScale, r\y, r\z)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x - 368.0 * RoomScale, r\y, r\z)
			
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x + 224.0 * RoomScale - 0.005, r\y + 192.0 * RoomScale, r\z)
			
			r\Objects[3] = CreatePivot()
			PositionEntity(r\Objects[3], r\x - 224.0 * RoomScale + 0.005, r\y + 192.0 * RoomScale, r\z)
			
			For i = 0 To 3
				EntityParent(r\Objects[i], r\OBJ)
			Next
			;[End Block]
		Case "room3_hcz"
			;[Block]
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 704.0 * RoomScale, r\y + 112.0 * RoomScale, r\z - 416.0 * RoomScale)
			EntityParent(r\Objects[i], r\OBJ)
			
			em.Emitters = CreateEmitter(r\x + 512.0 * RoomScale, r\y - 76.0 * RoomScale, r\z - 688.0 * RoomScale, 0)
			em\RandAngle = 55.0 : em\Speed = 0.0005 : em\AlphaChange = -0.015 : em\SizeChange = 0.007
			TurnEntity(em\OBJ, -90.0, 0.0, 0.0)
			EntityParent(em\OBJ, r\OBJ)
			
			em.Emitters = CreateEmitter(r\x - 512.0 * RoomScale, r\y - 76.0 * RoomScale, r\z - 688.0 * RoomScale, 0)
			em\RandAngle = 55.0 : em\Speed = 0.0005 : em\AlphaChange = -0.015 : em\SizeChange = 0.007
			TurnEntity(em\OBJ, -90.0, 0.0, 0.0)
			EntityParent(em\OBJ, r\OBJ)
			;[End Block]
		Case "room4_hcz"
			;[Block]
			em.Emitters = CreateEmitter(r\x + 512.0 * RoomScale, r\y - 76.0 * RoomScale, r\z - 688.0 * RoomScale, 0)
			em\RandAngle = 55.0 : em\Speed = 0.0005 : em\AlphaChange = -0.015 : em\SizeChange = 0.007
			TurnEntity(em\OBJ, -90.0, 0.0, 0.0)
			EntityParent(em\OBJ, r\OBJ)
			
			em.Emitters = CreateEmitter(r\x - 512.0 * RoomScale, r\y - 76.0 * RoomScale, r\z - 688.0 * RoomScale, 0)
			em\RandAngle = 55.0 : em\Speed = 0.0005 : em\AlphaChange = -0.015 : em\SizeChange = 0.007
			TurnEntity(em\OBJ, -90.0, 0.0, 0.0)
			EntityParent(em\OBJ, r\OBJ)
			
			em.Emitters = CreateEmitter(r\x + 512.0 * RoomScale, r\y - 76.0 * RoomScale, r\z + 688.0 * RoomScale, 0)
			em\RandAngle = 55.0 : em\Speed = 0.0005 : em\AlphaChange = -0.015 : em\SizeChange = 0.007
			TurnEntity(em\OBJ, -90.0, 0.0, 0.0)
			EntityParent(em\OBJ, r\OBJ)
			
			em.Emitters = CreateEmitter(r\x - 512.0 * RoomScale, r\y - 76.0 * RoomScale, r\z + 688.0 * RoomScale, 0)
			em\RandAngle = 55.0 : em\Speed = 0.0005 : em\AlphaChange = -0.015 : em\SizeChange = 0.007
			TurnEntity(em\OBJ, -90.0, 0.0, 0.0)
			EntityParent(em\OBJ, r\OBJ)
			;[End Block]
		Case "room2_servers_hcz"
			;[Block]
			; ~ Locked room at the room's center
			d.Doors = CreateDoor(r\x, r\y, r\z, 0.0, r, False, HEAVY_DOOR)
			d\Locked = 1
			
			r\RoomDoors.Doors[0] = CreateDoor(r\x - 224.0 * RoomScale, r\y, r\z - 736.0 * RoomScale, 90.0, r, True)
			r\RoomDoors[0]\Locked = 1
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x - 224.0 * RoomScale, r\y, r\z + 736.0 * RoomScale, 90.0, r, True)
			r\RoomDoors[1]\Locked = 1
			
			For k = 0 To 2
				r\Objects[k * 2] = CopyEntity(lvr_I\LeverModelID[LEVER_BASE_MODEL])
				r\Objects[k * 2 + 1] = CopyEntity(lvr_I\LeverModelID[LEVER_HANDLE_MODEL])
				
				r\Levers[k] = r\Objects[k * 2 + 1]
				
				For i = 0 To 1
					ScaleEntity(r\Objects[k * 2 + i], 0.03, 0.03, 0.03)
					
					Select k
						Case 0 ; ~ Power switch
							;[Block]
							PositionEntity(r\Objects[k * 2 + i], r\x - 1260.0 * RoomScale, r\y + 234.0 * RoomScale, r\z + 750.0 * RoomScale)	
							;[End Block]
						Case 1 ; ~ Generator fuel pump
							;[Block]
							PositionEntity(r\Objects[k * 2 + i], r\x - 917.0 * RoomScale, r\y + 164.0 * RoomScale, r\z + 898.0 * RoomScale)
							;[End Block]
						Case 2 ; ~ Generator On / Off
							;[Block]
							PositionEntity(r\Objects[k * 2 + i], r\x - 837.0 * RoomScale, r\y + 152.0 * RoomScale, r\z + 886.0 * RoomScale)
							;[End Block]
					End Select
					EntityParent(r\Objects[k * 2 + i], r\OBJ)
				Next
				RotateEntity(r\Objects[k * 2 + 1], 81.0, -180.0, 0.0)
				EntityPickMode(r\Objects[k * 2 + 1], 1, False)
				EntityRadius(r\Objects[k * 2 + 1], 0.1)
			Next
			RotateEntity(r\Objects[2 + 1], -81.0, -180.0, 0.0)
			RotateEntity(r\Objects[4 + 1], -81.0, -180.0, 0.0)
			
			; ~ SCP-096's spawnpoint
			r\Objects[6] = CreatePivot()
			PositionEntity(r\Objects[6], r\x - 352.0 * RoomScale, r\y + 0.5, r\z)
			
			; ~ Guard's spawnpoint
			r\Objects[7] = CreatePivot()
			PositionEntity(r\Objects[7], r\x - 1328.0 * RoomScale, r\y + 0.5, r\z + 528.0 * RoomScale)
			
			For i = 6 To 7
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			r\Objects[8] = LoadMesh_Strict("GFX\map\hcz\room2_servers_hcz_hb.b3d", r\OBJ)
			EntityPickMode(r\Objects[8], 2)
			EntityAlpha(r\Objects[8], 0.0)
			HideEntity(r\Objects[8])
			;[End Block]
		Case "room3_2_ez"
			;[Block]
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 736.0 * RoomScale, r\y - 512.0 * RoomScale, r\z - 400.0 * RoomScale)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x - 552.0 * RoomScale, r\y - 512.0 * RoomScale, r\z - 528.0 * RoomScale)
			
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x + 736.0 * RoomScale, r\y - 512.0 * RoomScale, r\z + 272.0 * RoomScale)
			
			r\Objects[3] = CopyEntity(n_I\NPCModelID[NPC_DUCK_MODEL])
			ScaleEntity(r\Objects[3], 0.07, 0.07, 0.07)
			Tex = LoadTexture_Strict("GFX\npcs\duck(2).png")
			If opt\Atmosphere Then TextureBlend(Tex, 5)
			EntityTexture(r\Objects[3], Tex)
			DeleteSingleTextureEntryFromCache(Tex)
			PositionEntity(r\Objects[3], r\x + 928.0 * RoomScale, r\y - 640.0 * RoomScale, r\z + 704.0 * RoomScale)
			
			For i = 0 To 3
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			it.Items = CreateItem("9V Battery", "bat", r\x - 132.0 * RoomScale, r\y - 368.0 * RoomScale, r\z - 658.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			If Rand(2) = 1 Then
				it.Items = CreateItem("9V Battery", "bat", r\x - 76.0 * RoomScale, r\y - 368.0 * RoomScale, r\z - 658.0 * RoomScale)
				EntityParent(it\Collider, r\OBJ)
			EndIf
			
			it.Items = CreateItem("S-NAV 310 Navigator", "nav310", r\x + 58.0 * RoomScale, r\y - 504.0 * RoomScale, r\z - 658.0 * RoomScale)
			it\State = Rnd(10.0, 100.0)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room3_3_ez"
			;[Block]
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x - 504.0 * RoomScale, r\y - 512.0 * RoomScale, r\z + 271.0 * RoomScale)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x + 628.0 * RoomScale, r\y - 512.0 * RoomScale, r\z + 271.0 * RoomScale)
			
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x - 532.0 * RoomScale, r\y - 512.0 * RoomScale, r\z - 877.0 * RoomScale)	
			
			For i = 0 To 2
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			it.Items = CreateItem("Document SCP-970", "paper", r\x + 960.0 * RoomScale, r\y - 448.0 * RoomScale, r\z + 251.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, r\Angle, 0.0)	
			EntityParent(it\Collider, r\OBJ)	
			
			it.Items = CreateItem("Gas Mask", "gasmask", r\x + 954.0 * RoomScale, r\y - 504.0 * RoomScale, r\z + 235.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)		
			;[End Block]
		Case "room2_test_hcz"
			;[Block]
			; ~ DNA door
			r\RoomDoors.Doors[0] = CreateDoor(r\x + 720.0 * RoomScale, r\y, r\z, 0.0, r, False, HEAVY_DOOR, KEY_HAND_WHITE)
			
			; ~ Door to the center
			d.Doors = CreateDoor(r\x - 624.0 * RoomScale, r\y - 1280.0 * RoomScale, r\z, 90.0, r, True)	
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True) - 0.031, EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True), True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True) + 0.031, EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True), True)			
			
			For xTemp = 0 To 1
				For zTemp = -1 To 1
					r\Objects[xTemp * 3 + (zTemp + 1)] = CreatePivot()
					PositionEntity(r\Objects[xTemp * 3 + (zTemp + 1)], r\x + (-236.0 + 280.0 * xTemp) * RoomScale, r\y - 700.0 * RoomScale, r\z + 384.0 * zTemp * RoomScale)
					EntityParent(r\Objects[xTemp * 3 + (zTemp + 1)], r\OBJ)
				Next
			Next
			
			r\Objects[6] = CreatePivot()
			PositionEntity(r\Objects[6], r\x + 754.0 * RoomScale, r\y - 1248.0 * RoomScale, r\z)
			EntityParent(r\Objects[6], r\OBJ)
			
			r\Objects[7] = LoadMesh_Strict("GFX\map\hcz\room2_test_hcz_hb.b3d", r\OBJ)
			EntityPickMode(r\Objects[7], 2)
			EntityType(r\Objects[7], HIT_MAP)
			EntityAlpha(r\Objects[7], 0.0)
			
			sc.SecurityCams = CreateSecurityCam(r\x + 744.0 * RoomScale, r\y - 856.0 * RoomScale, r\z + 236.0 * RoomScale, r)
			sc\FollowPlayer = True
			
			it.Items = CreateItem("Document SCP-682", "paper", r\x + 656.0 * RoomScale, r\y - 1200.0 * RoomScale, r\z - 16.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room2_closets"
			;[Block]
			d.Doors = CreateDoor(r\x - 244.0 * RoomScale, r\y, r\z, 90.0, r)
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True) - 0.048, EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True), True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True) + 0.048, EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True), True)
			
			d.Doors = CreateDoor(r\x - 1216.0 * RoomScale, r\y - 384.0 * RoomScale, r\z - 1024.0 * RoomScale, 0.0, r)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			d.Doors = CreateDoor(r\x - 1216.0 * RoomScale, r\y - 384.0 * RoomScale, r\z + 1024.0 * RoomScale, 180.0, r)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			d.Doors = CreateDoor(r\x - 232.0 * RoomScale, r\y - 384.0 * RoomScale, r\z - 644.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_1)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			d.Doors = CreateDoor(r\x - 232.0 * RoomScale, r\y - 384.0 * RoomScale, r\z + 644.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_1)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x - 1180.0 * RoomScale, r\y - 256.0 * RoomScale, r\z + 896.0 * RoomScale)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x - 1292.0 * RoomScale, r\y - 256.0 * RoomScale, r\z - 160.0 * RoomScale)
			
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x - 1065.0 * RoomScale, r\y - 380.0 * RoomScale, r\z + 50.0 * RoomScale)
			
			For i = 0 To 2
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			sc.SecurityCams = CreateSecurityCam(r\x + 184.0 * RoomScale, r\y + 704.0 * RoomScale, r\z + 952.0 * RoomScale, r)
			sc\Angle = 130.0 : sc\Turn = 40.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			
			it.Items = CreateItem("Gas Mask", "gasmask", r\x + 736.0 * RoomScale, r\y + 176.0 * RoomScale, r\z + 544.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("9V Battery", "bat", r\x + 736.0 * RoomScale, r\y + 100.0 * RoomScale, r\z - 448.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			If Rand(2) = 1 Then
				it.Items = CreateItem("9V Battery", "bat", r\x + 730.0 * RoomScale, r\y + 176.0 * RoomScale, r\z - 580.0 * RoomScale)
			Else
				it.Items = CreateItem("4.5V Battery", "coarsebat", r\x + 730.0 * RoomScale, r\y + 176.0 * RoomScale, r\z - 580.0 * RoomScale)
			EndIf
			EntityParent(it\Collider, r\OBJ)
			
			If Rand(2) = 1 Then
				it.Items = CreateItem("4.5V Battery", "coarsebat", r\x + 740.0 * RoomScale, r\y + 240.0 * RoomScale, r\z - 750.0 * RoomScale)
				EntityParent(it\Collider, r\OBJ)
			EndIf
			
			it.Items = CreateItem("Level 0 Key Card", "key0", r\x + 736.0 * RoomScale, r\y + 240.0 * RoomScale, r\z + 752.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Incident Report SCP-1048-A", "paper", r\x + 736.0 * RoomScale, r\y + 224.0 * RoomScale, r\z - 480.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room2_ez"
			;[Block]
			w.WayPoints = CreateWaypoint(r\x - 32.0 * RoomScale, r\y + 66.0 * RoomScale, r\z + 288.0 * RoomScale, Null, r)
			w2.WayPoints = CreateWaypoint(r\x, r\y + 66.0 * RoomScale, r\z - 448.0 * RoomScale, Null, r)
			w\connected[0] = w2 : w\Dist[0] = EntityDistance(w\OBJ, w2\OBJ)
			w2\connected[0] = w : w2\Dist[0] = w\Dist[0]
			
			it.Items = CreateItem("Document SCP-106", "paper", r\x + 404.0 * RoomScale, r\y + 145.0 * RoomScale, r\z + 559.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Level 2 Key Card", "key2", r\x - 156.0 * RoomScale, r\y + 151.0 * RoomScale, r\z + 72.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("S-NAV Navigator", "nav", r\x + 305.0 * RoomScale, r\y + 153.0 * RoomScale, r\z + 944.0 * RoomScale)
			it\State = Rnd(10.0, 100.0)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Notification", "paper", r\x - 137.0 * RoomScale, r\y + 153.0 * RoomScale, r\z + 464.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room2_2_ez"
			;[Block]
			r\Objects[0] = CopyEntity(n_I\NPCModelID[NPC_DUCK_MODEL])
			ScaleEntity(r\Objects[0], 0.07, 0.07, 0.07)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x - 808.0 * RoomScale, r\y - 72.0 * RoomScale, r\z - 40.0 * RoomScale)
			
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x - 488.0 * RoomScale, r\y + 160.0 * RoomScale, r\z + 700.0 * RoomScale)
			
			r\Objects[3] = CreatePivot()
			PositionEntity(r\Objects[3], r\x - 488.0 * RoomScale, r\y + 160.0 * RoomScale, r\z - 700.0 * RoomScale)
			
			r\Objects[4] = CreatePivot()
			PositionEntity(r\Objects[4], r\x - 600.0 * RoomScale, r\y + 340.0 * RoomScale, r\z)
			
			Temp = Rand(1, 4)
			PositionEntity(r\Objects[0], EntityX(r\Objects[Temp], True), EntityY(r\Objects[Temp], True), EntityZ(r\Objects[Temp], True), True)
			
			For i = 0 To 4
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			it.Items = CreateItem("Level 1 Key Card", "key1", r\x - 368.0 * RoomScale, r\y - 48.0 * RoomScale, r\z + 80.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Document SCP-895", "paper", r\x - 800.0 * RoomScale, r\y - 48.0 * RoomScale, r\z + 368.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			If Rand(2) = 1 Then
				it.Items = CreateItem("Document SCP-860", "paper", r\x - 800.0 * RoomScale, r\y - 48.0 * RoomScale, r\z - 464.0 * RoomScale)
			Else
				it.Items = CreateItem("SCP-093 Recovered Materials", "paper", r\x - 800.0 * RoomScale, r\y - 48.0 * RoomScale, r\z - 464.0 * RoomScale)
			EndIf
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("S-NAV Navigator", "nav", r\x - 336.0 * RoomScale, r\y - 48.0 * RoomScale, r\z - 480.0 * RoomScale)
			it\State = Rnd(10.0, 100.0)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room2_3_ez"
			;[Block]
			d.Doors = CreateDoor(r\x - 1056.0 * RoomScale, r\y + 384.0 * RoomScale, r\z + 290.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			
			d.Doors = CreateDoor(r\x - 1056.0 * RoomScale, r\y + 384.0 * RoomScale, r\z - 736.0 * RoomScale, 270.0, r, True, ONE_SIDED_DOOR, KEY_CARD_3)
			
			If Rand(2) = 1 Then 
				it.Items = CreateItem("Mobile Task Forces", "paper", r\x + 744.0 * RoomScale, r\y + 240.0 * RoomScale, r\z + 944.0 * RoomScale)
				EntityParent(it\Collider, r\OBJ)	
			Else
				it.Items = CreateItem("Security Clearance Levels", "paper", r\x + 680.0 * RoomScale, r\y + 240.0 * RoomScale, r\z + 944.0 * RoomScale)
				EntityParent(it\Collider, r\OBJ)			
			EndIf
			
			it.Items = CreateItem("Object Classes", "paper", r\x + 160.0 * RoomScale, r\y + 240.0 * RoomScale, r\z + 568.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)	
			
			it.Items = CreateItem("Document", "paper", r\x - 1440.0 * RoomScale, r\y + 624.0 * RoomScale, r\z + 152.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)	
			
			it.Items = CreateItem("Radio Transceiver", "radio", r\x - 1184.0 * RoomScale, r\y + 480.0 * RoomScale, r\z - 800.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)				
			
			it.Items = CreateItem("ReVision Eyedrops", "eyedrops", r\x - 1530.0 * RoomScale, r\y + 563.0 * RoomScale, r\z - 525.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)				
			
			If Rand(3) = 1 Then
				it.Items = CreateItem("ReVision Eyedrops", "eyedrops", r\x - 1530.0 * RoomScale, r\y + 563.0 * RoomScale, r\z - 625.0 * RoomScale)
				EntityParent(it\Collider, r\OBJ)
			EndIf
			
			it.Items = CreateItem("9V Battery", "bat", r\x - 1545.0 * RoomScale, r\y + 605.0 * RoomScale, r\z - 392.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			If Rand(2) = 1 Then
				it.Items = CreateItem("9V Battery", "bat", r\x - 1540.0 * RoomScale, r\y + 495.0 * RoomScale, r\z - 320.0 * RoomScale)
				EntityParent(it\Collider, r\OBJ)
			EndIf
			
			If Rand(2) = 1 Then
				it.Items = CreateItem("9V Battery", "bat", r\x - 1529.0 * RoomScale, r\y + 605.0 * RoomScale, r\z - 308.0 * RoomScale)
				EntityParent(it\Collider, r\OBJ)
			EndIf
			;[End Block]
		Case "cont1_173"
			;[Block]
			; ~ The containment doors
			r\RoomDoors.Doors[1] = CreateDoor(r\x + 4000.0 * RoomScale, r\y + 384.0 * RoomScale, r\z + 1696.0 * RoomScale, 90.0, r, True, BIG_DOOR)
			r\RoomDoors[1]\MTFClose = False
			For i = 0 To 1
				FreeEntity(r\RoomDoors[1]\Buttons[i]) : r\RoomDoors[1]\Buttons[i] = 0
			Next
			
			r\RoomDoors.Doors[2] = CreateDoor(r\x + 2704.0 * RoomScale, r\y + 384.0 * RoomScale, r\z + 624.0 * RoomScale, 90.0, r)
			r\RoomDoors[2]\AutoClose = False : r\RoomDoors[2]\MTFClose = False
			For i = 0 To 1
				FreeEntity(r\RoomDoors[2]\Buttons[i]) : r\RoomDoors[2]\Buttons[i] = 0
			Next
			
			d.Doors = CreateDoor(r\x + 1392.0 * RoomScale, r\y + 384.0 * RoomScale, r\z + 64.0 * RoomScale, 90.0, r, True, DEFAULT_DOOR, KEY_CARD_3)
			d\Locked = 1 : d\MTFClose = False
			
			d.Doors = CreateDoor(r\x - 640.0 * RoomScale, r\y + 384.0 * RoomScale, r\z + 64.0 * RoomScale, 90.0, r)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[1]) : d\Buttons[1] = 0
			
			d.Doors = CreateDoor(r\x + 1264.0 * RoomScale, r\y + 383.9 * RoomScale, r\z + 312.0 * RoomScale, 180.0, r, True, ONE_SIDED_DOOR)
			d\Locked = 1 : d\MTFClose = False
			PositionEntity(d\Buttons[0], r\x + 1120.0 * RoomScale, EntityY(d\Buttons[0], True), r\z + 322.0 * RoomScale, True)
			PositionEntity(d\Buttons[1], r\x + 1120.0 * RoomScale, EntityY(d\Buttons[1], True), r\z + 302.0 * RoomScale, True)
			
			d.Doors = CreateDoor(r\x, r\y, r\z + 1184.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[1]) : d\Buttons[1] = 0
			
			r\Objects[0] = LoadRMesh("GFX\map\IntroDesk.rmesh", Null)
			ScaleEntity(r\Objects[0], RoomScale, RoomScale, RoomScale)
			PositionEntity(r\Objects[0], r\x + 272.0 * RoomScale, r\y, r\z + 400.0 * RoomScale)
			
			r\Objects[1] = LoadRMesh("GFX\map\IntroDrawer.rmesh", Null)
			ScaleEntity(r\Objects[1], RoomScale, RoomScale, RoomScale)
			PositionEntity(r\Objects[1], r\x + 448.0 * RoomScale, r\y, r\z + 192.0 * RoomScale)
			
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], EntityX(r\OBJ) - 200.0 * RoomScale, r\y + 440.0 * RoomScale, EntityZ(r\OBJ) + 1322.0 * RoomScale)
			
			r\Objects[3] = CreatePivot()
			PositionEntity(r\Objects[3], EntityX(r\OBJ) + 1000.0 * RoomScale, r\y + 120.0 * RoomScale, EntityZ(r\OBJ) + 666.0 * RoomScale)
			
			r\Objects[4] = CreatePivot()
			PositionEntity(r\Objects[4], EntityX(r\OBJ) + 628.0 * RoomScale, r\y + 120.0 * RoomScale, EntityZ(r\OBJ) + 320.0 * RoomScale)
			
			For i = 0 To 4
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			sc.SecurityCams = CreateSecurityCam(r\x - 336.0 * RoomScale, r\y + 352.0 * RoomScale, r\z + 48.0 * RoomScale, r, True, r\x + 1456.0 * RoomScale, r\y + 608.0 * RoomScale, r\z + 352.0 * RoomScale)
			sc\Angle = 270.0 : sc\Turn = 45.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			TurnEntity(sc\ScrOBJ, 0.0, 90.0, 0.0)
			
			de.Decals = CreateDecal(DECAL_CORROSIVE_1, r\x + 272.0 * RoomScale, r\y + 0.005, r\z + 262.0 * RoomScale, 90.0, Rnd(360.0), 0.0)
			EntityParent(de\OBJ, r\OBJ)
			
			de.Decals = CreateDecal(DECAL_CORROSIVE_1, r\x + 456.0 * RoomScale, r\y + 0.005, r\z + 135.0 * RoomScale, 90.0, Rnd(360.0), 0.0)
			EntityParent(de\OBJ, r\OBJ)
			
			For i = 0 To 4
				Select i
					Case 0
						;[Block]
						xTemp = 4305
						zTemp = 1234.0
						Temp = DECAL_BLOOD_3
						;[End Block]
					Case 1
						;[Block]
						xTemp = 5190.0
						zTemp = 2270.0
						Temp = DECAL_BLOOD_3
						;[End Block]
					Case 2
						;[Block]
						xTemp = 5222.0
						zTemp = 1224.0  
						Temp = DECAL_BLOOD_3
						;[End Block]
					Case 3
						;[Block]
						xTemp = 4320.0 
						zTemp = 2000.0
						Temp = DECAL_BLOOD_3
						;[End Block]
					Case 4
						;[Block]
						xTemp = 4978.0
						zTemp = 1985.0
						Temp = DECAL_BLOOD_5
						;[End Block]
				End Select
				de.Decals = CreateDecal(Temp, r\x + xTemp * RoomScale, r\y + 386.0 * RoomScale, r\z + zTemp * RoomScale, 90.0, 45.0, 0.0, ((i = 0) * 0.44) + ((i = 1) * 1.2) + ((i > 1) * 0.54), Rnd(0.8, 1.0))
				EntityParent(de\OBJ, r\OBJ)
			Next
			;[End Block]
		Case "cont2_427_714_860_1025"
			;[Block]
			d.Doors = CreateDoor(r\x + 272.0 * RoomScale, r\y, r\z, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True) + 0.061, EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True), True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True) - 0.061, EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True), True)
			
			d.Doors = CreateDoor(r\x - 272.0 * RoomScale, r\y, r\z, 270.0, r, True, DEFAULT_DOOR, KEY_CARD_3)
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True) - 0.061, EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True), True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True) + 0.061, EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True), True)
			
			d.Doors = CreateDoor(r\x - 560.0 * RoomScale, r\y, r\z - 272.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			
			d.Doors = CreateDoor(r\x + 560.0 * RoomScale, r\y, r\z - 272.0 * RoomScale, 180.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			
			d.Doors = CreateDoor(r\x + 560.0 * RoomScale, r\y, r\z + 272.0 * RoomScale, 180.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			
			d.Doors = CreateDoor(r\x - 560.0 * RoomScale, r\y, r\z + 272.0 * RoomScale, 0.0, r, True, DEFAULT_DOOR, KEY_CARD_3)
			
			d.Doors = CreateDoor(r\x - 816.0 * RoomScale, r\y, r\z, 270.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			d.Doors = CreateDoor(r\x + 816.0 * RoomScale, r\y, r\z, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			For i = 0 To 3
				Select i
					Case 0
						;[Block]
						xTemp = 560.0
						zTemp = -416.0
						;[End Block]
					Case 1
						;[Block]
						xTemp = -560.0
						zTemp = -416.0
						;[End Block]
					Case 2
						;[Block]
						xTemp = 560.0
						zTemp = 416.0
						;[End Block]
					Case 3
						;[Block]
						xTemp = -560.0
						zTemp = 416.0
						;[End Block]
				End Select
				sc.SecurityCams = CreateSecurityCam(r\x + xTemp * RoomScale, r\y + 386.0 * RoomScale, r\z + zTemp * RoomScale, r)
				If i < 2 Then
					sc\Angle = 180.0
				Else
					sc\Angle = 0.0
				EndIf
				sc\Turn = 30.0
				TurnEntity(sc\CameraOBJ, 30.0, 0.0, 0.0)
			Next
			
			For i = 0 To 14
				Select i
					Case 0
						;[Block]
						xTemp = -64.0
						zTemp = -516.0
						;[End Block]
					Case 1
						;[Block]
						xTemp = -96.0
						zTemp = -388.0
						;[End Block]
					Case 2
						;[Block]
						xTemp = -128.0
						zTemp = -292.0
						;[End Block]
					Case 3
						;[Block]
						xTemp = -128.0
						zTemp = -132.0
						;[End Block]
					Case 4
						;[Block]
						xTemp = -160.0
						zTemp = -36.0
						;[End Block]
					Case 5
						;[Block]
						xTemp = -192.0
						zTemp = 28.0
						;[End Block]
					Case 6
						;[Block]
						xTemp = -384.0
						zTemp = 28.0
						;[End Block]
					Case 7
						;[Block]
						xTemp = -448.0
						zTemp = 92.0
						;[End Block]
					Case 8
						;[Block]
						xTemp = -480.0
						zTemp = 124.0
						;[End Block]
					Case 9
						;[Block]
						xTemp = -512.0
						zTemp = 156.0
						;[End Block]
					Case 10
						;[Block]
						xTemp = -544.0
						zTemp = 220.0
						;[End Block]
					Case 11
						;[Block]
						xTemp = -544.0
						zTemp = 380.0
						;[End Block]
					Case 12
						;[Block]
						xTemp = -544.0
						zTemp = 476.0
						;[End Block]
					Case 13
						;[Block]
						xTemp = -544.0
						zTemp = 572.0
						;[End Block]
					Case 14
						;[Block]
						xTemp = -544.0
						zTemp = 636.0
						;[End Block]
				End Select
				de.Decals = CreateDecal(Rand(DECAL_BLOOD_DROP_1, DECAL_BLOOD_DROP_2), r\x + xTemp * RoomScale, r\y + 0.005, r\z + zTemp * RoomScale, 90.0, Rnd(360.0), 0.0, ((i <= 10) * Rnd(0.2, 0.25)) + ((i > 10) * Rnd(0.1, 0.17)))
				EntityParent(de\OBJ, r\OBJ)
			Next
			
			it.Items = CreateItem("SCP-714", "scp714", r\x - 560.0 * RoomScale, r\y + 185.0 * RoomScale, r\z - 760.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("SCP-1025", "scp1025", r\x + 560.0 * RoomScale, r\y + 185.0 * RoomScale, r\z - 760.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("SCP-860", "scp860", r\x + 560.0 * RoomScale, r\y + 185.0 * RoomScale, r\z + 765.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Document SCP-427", "paper", r\x - 608.0 * RoomScale, r\y + 185.0 * RoomScale, r\z + 636.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Document SCP-714", "paper", r\x - 728.0 * RoomScale, r\y + 290.0 * RoomScale, r\z - 360.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, r\Angle, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Document SCP-860", "paper", r\x + 728.0 * RoomScale, r\y + 290.0 * RoomScale, r\z + 360.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, r\Angle, 0.0)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "cont1_205"
			;[Block]
			r\RoomDoors.Doors[0] = CreateDoor(r\x - 1400.0 * RoomScale, r\y - 128.0 * RoomScale, r\z - 384.0 * RoomScale, 0.0, r)
			r\RoomDoors[0]\AutoClose = False
			For i = 0 To 1
				FreeEntity(r\RoomDoors[0]\Buttons[i]) : r\RoomDoors[0]\Buttons[i] = 0
			Next
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x + 128.0 * RoomScale, r\y, r\z + 640.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_2)
			
			sc.SecurityCams = CreateSecurityCam(r\x - 1152.0 * RoomScale, r\y + 900.0 * RoomScale, r\z + 176.0 * RoomScale, r, True, r\x - 1716.0 * RoomScale, r\y + 160.0 * RoomScale, r\z + 176.0 * RoomScale)
			sc\Angle = 90.0 : sc\Turn = 0.0 : sc\AllowSaving = False : sc\RenderInterval = 0.0
			TurnEntity(sc\ScrOBJ, 0.0, 90.0, 0.0)
			ScaleSprite(sc\ScrOBJ, 896.0 * 0.5 * RoomScale, 896.0 * 0.5 * RoomScale)
			CameraZoom(sc\Cam, 1.5)
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x - 1536.0 * RoomScale, r\y + 730.0 * RoomScale, r\z + 192.0 * RoomScale)
			RotateEntity(r\Objects[0], 0.0, -90.0, 0.0)
			EntityParent(r\Objects[0], r\OBJ)
			
			r\Objects[1] = sc\ScrOBJ
			
			it.Items = CreateItem("Document SCP-205", "paper", r\x - 357.0 * RoomScale, r\y + 115.0 * RoomScale, r\z + 50.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("9V Battery", "bat", r\x - 357.0 * RoomScale, r\y + 115.0 * RoomScale, r\z - 80.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Level 3 Key Card", "key3", r\x - 975.0 * RoomScale, r\y - 5.0 * RoomScale, r\z + 650.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room1_dead_end_lcz", "room1_dead_end_ez"
			;[Block]
			r\RoomDoors.Doors[0] = CreateDoor(r\x, r\y, r\z + 1202.0 * RoomScale, r\y, r, False, BIG_DOOR)
			r\RoomDoors[0]\MTFClose = False : r\RoomDoors[0]\DisableWaypoint = True
			For i = 0 To 1
				FreeEntity(r\RoomDoors[0]\Buttons[i]) : r\RoomDoors[0]\Buttons[i] = 0
			Next
			;[End Block]
		Case "cont1_895"
			;[Block]
			r\RoomDoors.Doors[0] = CreateDoor(r\x, r\y, r\z - 448.0 * RoomScale, 0.0, r, False, BIG_DOOR, KEY_CARD_2)
			PositionEntity(r\RoomDoors[0]\Buttons[0], r\x - 390.0 * RoomScale, EntityY(r\RoomDoors[0]\Buttons[i], True), r\z - 280.0 * RoomScale, True)
			PositionEntity(r\RoomDoors[0]\Buttons[1], EntityX(r\RoomDoors[0]\Buttons[1], True) + 0.025, EntityY(r\RoomDoors[0]\Buttons[1], True), EntityZ(r\RoomDoors[0]\Buttons[1], True), True) 
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x, - 1320.0 * RoomScale, r\z + 2304.0 * RoomScale)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x, r\y - 1532.0 * RoomScale, r\z + 2356.0 * RoomScale)
			
			For i = 0 To 1
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			r\Objects[2] = CopyEntity(lvr_I\LeverModelID[LEVER_BASE_MODEL])
			r\Objects[3] = CopyEntity(lvr_I\LeverModelID[LEVER_HANDLE_MODEL]) ; TODO
			
			r\Levers[0] = r\Objects[3]
			
			For i = 0 To 1
				ScaleEntity(r\Objects[2 + i], 0.04, 0.04, 0.04)
				PositionEntity(r\Objects[2 + i], r\x - 800.0 * RoomScale, r\y + 180.0 * RoomScale, r\z - 336.0 * RoomScale)
				EntityParent(r\Objects[2 + i], r\OBJ)
			Next
			RotateEntity(r\Objects[2], 0.0, 180.0, 0.0)
			RotateEntity(r\Objects[3], 10.0, 0.0, 0.0)
			EntityPickMode(r\Objects[3], 1, False)
			EntityRadius(r\Objects[3], 0.1)
			
			sc.SecurityCams = CreateSecurityCam(r\x - 320.0 * RoomScale, r\y + 704.0 * RoomScale, r\z + 288.0 * RoomScale, r, True, r\x - 800.0 * RoomScale, r\y + 288.0 * RoomScale, r\z - 340.0 * RoomScale)
			sc\Angle = 225.0 : sc\Turn = 45.0 : sc\CoffinEffect = 1 : sc_I\CoffinCam = sc
			TurnEntity(sc\CameraOBJ, 120.0, 0.0, 0.0)
			TurnEntity(sc\ScrOBJ, 0.0, 180.0, 0.0)
			
			it.Items = CreateItem("Document SCP-895", "paper", r\x - 688.0 * RoomScale, r\y + 133.0 * RoomScale, r\z - 304.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Level 3 Key Card", "key3", r\x + 240.0 * RoomScale, r\y - 1456.0 * RoomScale, r\z + 2064.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Night Vision Goggles", "nvg", r\x + 280.0 * RoomScale, r\y - 1456.0 * RoomScale, r\z + 2164.0 * RoomScale)
			it\State = Rnd(100.0, 1000.0)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room2_tesla_lcz", "room2_tesla_hcz", "room2_tesla_ez"
			;[Block]
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x - 114.0 * RoomScale, r\y, r\z)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x + 114.0 * RoomScale, r\y, r\z)		
			
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x, r\y, r\z)	
			
			r\Objects[3] = CreateSprite()
			EntityTexture(r\Objects[3], t\OverlayTextureID[11])
			SpriteViewMode(r\Objects[3], 2) 
			EntityBlend(r\Objects[3], 3) 
			EntityFX(r\Objects[3], 1 + 8 + 16)
			PositionEntity(r\Objects[3], r\x, r\y + 0.8, r\z)
			HideEntity(r\Objects[3])
			
			r\Objects[4] = CreateRedLight(r\x - 32.0 * RoomScale, r\y + 568.0 * RoomScale, r\z)
			
			r\Objects[5] = CreatePivot()
			PositionEntity(r\Objects[5], r\x, r\y, r\z - 800.0 * RoomScale)
			
			r\Objects[6] = CreatePivot()
			PositionEntity(r\Objects[6], r\x, r\y, r\z + 800.0 * RoomScale)
			
			For i = 0 To 6
				EntityParent(r\Objects[i], r\OBJ)
			Next
			;[End Block]
		Case "room2_6_lcz"
			;[Block]
			d.Doors = CreateDoor(r\x, r\y, r\z + 528.0 * RoomScale, 0.0, r)
			d\AutoClose = False
			PositionEntity(d\Buttons[0], r\x - 998.0 * RoomScale, EntityY(d\Buttons[0], True), r\z, True)
			RotateEntity(d\Buttons[0], 0.0, 90.0, 0.0, True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True), EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True) - 0.061, True)
			
			d2.Doors = CreateDoor(r\x, r\y, r\z - 528.0 * RoomScale, 180.0, r, True)
			d2\AutoClose = False
			FreeEntity(d2\Buttons[0]) : d2\Buttons[0] = 0
			PositionEntity(d2\Buttons[1], EntityX(d2\Buttons[1], True), EntityY(d2\Buttons[1], True), EntityZ(d2\Buttons[1], True) + 0.061, True)
			
			d\LinkedDoor = d2
			d2\LinkedDoor = d
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x - 832.0 * RoomScale, r\y + 0.5, r\z)
			EntityParent(r\Objects[0], r\OBJ)
			;[End Block]
		Case "cont1_914"
			;[Block]
			r\RoomDoors.Doors[0] = CreateDoor(r\x - 1037.0 * RoomScale, r\y, r\z + 528.0 * RoomScale, 180.0, r, True, SCP_914_DOOR)
			For i = 0 To 1
				FreeEntity(r\RoomDoors[0]\Buttons[i]) : r\RoomDoors[0]\Buttons[i] = 0
			Next
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x + 404.0 * RoomScale, r\y, r\z + 528.0 * RoomScale, 180.0, r, True, SCP_914_DOOR)
			For i = 0 To 1
				FreeEntity(r\RoomDoors[1]\Buttons[i]) : r\RoomDoors[1]\Buttons[i] = 0
			Next
			
			r\RoomDoors.Doors[2] = CreateDoor(r\x, r\y, r\z - 368.0 * RoomScale, 0.0, r, False, BIG_DOOR, KEY_CARD_2)
			PositionEntity(r\RoomDoors[2]\Buttons[0], r\x - 496.0 * RoomScale, EntityY(r\RoomDoors[2]\Buttons[0], True), r\z - 278.0 * RoomScale, True)
			PositionEntity(r\RoomDoors[2]\Buttons[1], EntityX(r\RoomDoors[2]\Buttons[1], True) + 0.025, EntityY(r\RoomDoors[2]\Buttons[1], True), EntityZ(r\RoomDoors[2]\Buttons[1], True), True) 
			TurnEntity(r\RoomDoors[2]\Buttons[0], 0.0, 90.0, 0.0)
			
			r\RoomDoors.Doors[3] = CreateDoor(r\x - 448.0 * RoomScale, r\y, r\z - 705.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_2)
			PositionEntity(r\RoomDoors[3]\Buttons[0], EntityX(r\RoomDoors[3]\Buttons[0], True) - 0.061, EntityY(r\RoomDoors[3]\Buttons[0], True), EntityZ(r\RoomDoors[3]\Buttons[0], True), True)
			PositionEntity(r\RoomDoors[3]\Buttons[1], EntityX(r\RoomDoors[3]\Buttons[1], True) + 0.061, EntityY(r\RoomDoors[3]\Buttons[1], True), EntityZ(r\RoomDoors[3]\Buttons[1], True), True)
			
			r\Objects[0] = LoadMesh_Strict("GFX\map\Props\scp_914_key.b3d")
			PositionEntity(r\Objects[0], r\x - 416.0 * RoomScale, r\y + 190.0 * RoomScale, r\z + 374.0 * RoomScale, True)
			
			r\Objects[1] = LoadMesh_Strict("GFX\map\Props\scp_914_knob.b3d")
			PositionEntity(r\Objects[1], r\x - 416.0 * RoomScale, r\y + 230.0 * RoomScale, r\z + 374.0 * RoomScale, True)
			
			For i = 0 To 1
				ScaleEntity(r\Objects[i], RoomScale, RoomScale, RoomScale, True)
				EntityPickMode(r\Objects[i], 2)
			Next
			
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x - 1132.0 * RoomScale, r\y + 0.5, r\z + 640.0 * RoomScale)
			
			r\Objects[3] = CreatePivot()
			PositionEntity(r\Objects[3], r\x + 308.0 * RoomScale, r\y + 0.5, r\z + 640.0 * RoomScale)
			
			For i = 0 To 3
				EntityParent(r\Objects[i], r\OBJ)
			Next
			it.Items = CreateItem("Document SCP-914", "paper", r\x + 538.0 * RoomScale, r\y + 248.0 * RoomScale, r\z + 195.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			RotateEntity(it\Collider, 0.0, 0.0, 0.0)
			
			it.Items = CreateItem("Addendum: 5/14 Test Log", "paper", r\x + 538.0 * RoomScale, r\y + 178.0 * RoomScale, r\z + 127.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			RotateEntity(it\Collider, 0.0, 0.0, 0.0)	
			
			it.Items = CreateItem("First Aid Kit", "firstaid", r\x + 538.0 * RoomScale, r\y + 112.0 * RoomScale, r\z - 40.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			RotateEntity(it\Collider, 0.0, 90.0, 0.0)
			
			it.Items = CreateItem("Playing Card", "playcard", r\x - 1299.0 * RoomScale, r\y + 190.0 * RoomScale, r\z - 130.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Dr. L's Note #1", "paper", r\x - 538.0 * RoomScale, r\y + 250.0 * RoomScale, r\z - 365.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
		Case "cont1_173_intro"
			;[Block]
			r\RoomDoors.Doors[1] = CreateDoor(EntityX(r\OBJ) + 288.0 * RoomScale, r\y, EntityZ(r\OBJ) + 384.0 * RoomScale, 90.0, r, False, BIG_DOOR)
			r\RoomDoors[1]\MTFClose = False
			For i = 0 To 1
				FreeEntity(r\RoomDoors[1]\Buttons[i]) : r\RoomDoors[1]\Buttons[i] = 0
			Next
			
			r\RoomDoors.Doors[2] = CreateDoor(r\x - 1008.0 * RoomScale, r\y, r\z - 688.0 * RoomScale, 90.0, r)
			r\RoomDoors[2]\AutoClose = False : r\RoomDoors[2]\Locked = 1 : r\RoomDoors[2]\MTFClose = False
			For i = 0 To 1
				FreeEntity(r\RoomDoors[2]\Buttons[i]) : r\RoomDoors[2]\Buttons[i] = 0
			Next
			
			r\RoomDoors.Doors[3] = CreateDoor(r\x - 2324.0 * RoomScale, r\y, r\z - 1248.0 * RoomScale, 90.0, r, True, DEFAULT_DOOR, KEY_CARD_3)
			r\RoomDoors[3]\Locked = 1 : r\RoomDoors[3]\MTFClose = False
			PositionEntity(r\RoomDoors[3]\Buttons[0], EntityX(r\RoomDoors[3]\Buttons[0], True) - 4.0 * RoomScale, EntityY(r\RoomDoors[3]\Buttons[0], True), EntityZ(r\RoomDoors[3]\Buttons[0], True), True)
			PositionEntity(r\RoomDoors[3]\Buttons[1], EntityX(r\RoomDoors[3]\Buttons[1], True) + 4.0 * RoomScale, EntityY(r\RoomDoors[3]\Buttons[1], True), EntityZ(r\RoomDoors[3]\Buttons[1], True), True)
			
			r\RoomDoors.Doors[4] = CreateDoor(r\x - 4352.0 * RoomScale, r\y, r\z - 1248.0 * RoomScale, 90.0, r, True)
			r\RoomDoors[4]\Locked = 1 : r\RoomDoors[4]\MTFClose = False	
			
			Tex = LoadTexture_Strict("GFX\map\textures\Door02.jpg")
			If opt\Atmosphere Then TextureBlend(Tex, 5)
			For zTemp = 0 To 1
				d.Doors = CreateDoor(r\x - 5760.0 * RoomScale, r\y, r\z + ((320.0 + (896.0 * zTemp)) * RoomScale), 0.0, r)
				d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
				If zTemp = 0 Then
					FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
				Else
					FreeEntity(d\Buttons[1]) : d\Buttons[1] = 0
				EndIf
				
				d.Doors = CreateDoor(r\x - 8288.0 * RoomScale, r\y, r\z + ((320.0 + (896.0 * zTemp)) * RoomScale), 0.0, r, zTemp = 0)
				d\Locked = 1 : d\MTFClose = False : d\MTFClose = False
				If zTemp <> 0 Then 
					d\DisableWaypoint = True
					FreeEntity(d\Buttons[1]) : d\Buttons[1] = 0
				EndIf
				
				For xTemp = 0 To 2
					d.Doors = CreateDoor(r\x - (7424.0 - 512.0 * xTemp) * RoomScale, r\y, r\z + ((1008.0 - (480.0 * zTemp)) * RoomScale), 180.0 * (Not zTemp), r)
					d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
					EntityTexture(d\OBJ, Tex)
					FreeEntity(d\OBJ2) : d\OBJ2 = 0
					For i = 0 To 1
						FreeEntity(d\Buttons[i]) : d\Buttons[i] = 0
					Next
				Next					
				For xTemp = 0 To 4
					d.Doors = CreateDoor(r\x - (5120.0 - 512.0 * xTemp) * RoomScale, r\y, r\z + ((1008.0 - (480.0 * zTemp)) * RoomScale), 180.0 * (Not zTemp), r)
					d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
					EntityTexture(d\OBJ, Tex)
					FreeEntity(d\OBJ2) : d\OBJ2 = 0
					For i = 0 To 1
						FreeEntity(d\Buttons[i]) : d\Buttons[i] = 0
					Next
					
					If xTemp = 2 And zTemp = 1 Then r\RoomDoors[5] = d
				Next	
			Next
			DeleteSingleTextureEntryFromCache(Tex)
			
			; ~ The door in the office below the walkway
			r\RoomDoors.Doors[6] = CreateDoor(r\x - 3712.0 * RoomScale, r\y - 385.0 * RoomScale, r\z - 128.0 * RoomScale, 0.0, r, True, DEFAULT_DOOR, KEY_CARD_3)
			r\RoomDoors[6]\MTFClose = False
			FreeEntity(r\RoomDoors[6]\Buttons[1]) : r\RoomDoors[6]\Buttons[1] = 0
			
			d.Doors = CreateDoor(r\x - 3712.0 * RoomScale, r\y - 385.0 * RoomScale, r\z - 2336.0 * RoomScale, 0.0, r)
			d\AutoClose = False : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			
			; ~ The door from the concrete tunnel to the large hall
			d.Doors = CreateDoor(r\x - 6864.0 * RoomScale, r\y, r\z - 1248.0 * RoomScale, 90.0, r, True)
			d\AutoClose = False : d\Locked = 1 : d\MTFClose = False
			
			; ~ The door to the staircase in the office room
			d.Doors = CreateDoor(r\x - 2448.0 * RoomScale, r\y, r\z - 1000.0 * RoomScale, 0.0, r, False, ONE_SIDED_DOOR)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			PositionEntity(d\Buttons[0], r\x - 2592.0 * RoomScale, EntityY(d\Buttons[0], True), r\z - 1010.0 * RoomScale, True)
			FreeEntity(d\Buttons[1]) : d\Buttons[1] = 0
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], EntityX(r\OBJ) + 40.0 * RoomScale, 460.0 * RoomScale, EntityZ(r\OBJ) + 1072.0 * RoomScale)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], EntityX(r\OBJ) - 80.0 * RoomScale, 100.0 * RoomScale, EntityZ(r\OBJ) + 480.0 * RoomScale)
			
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], EntityX(r\OBJ) - 128.0 * RoomScale, 100.0 * RoomScale, EntityZ(r\OBJ) + 320.0 * RoomScale)
			
			r\Objects[3] = CreatePivot()
			PositionEntity(r\Objects[3], EntityX(r\OBJ) + 660.0 * RoomScale, 100.0 * RoomScale, EntityZ(r\OBJ) + 526.0 * RoomScale)
			
			r\Objects[4] = CreatePivot()
			PositionEntity(r\Objects[4], EntityX(r\OBJ) + 700.0 * RoomScale, 100.0 * RoomScale, EntityZ(r\OBJ) + 320.0 * RoomScale)
			
			r\Objects[5] = CreatePivot()
			PositionEntity(r\Objects[5], EntityX(r\OBJ) + 1472.0 * RoomScale, 100.0 * RoomScale, EntityZ(r\OBJ) + 912.0 * RoomScale)
			
			For i = 0 To 5
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			For i = 0 To 4
				Select i
					Case 0
						;[Block]
						xTemp = 587.0
						zTemp = -70.0
						Temp = DECAL_BLOOD_3
						;[End Block]
					Case 1
						;[Block]
						xTemp = 1472.0
						zTemp = 912.0  
						Temp = DECAL_BLOOD_3
						;[End Block]
					Case 2
						;[Block]
						xTemp = 1504.0
						zTemp = -80.0
						Temp = DECAL_BLOOD_3
						;[End Block]
					Case 3
						;[Block]
						xTemp = 602.0 
						zTemp = 642.0
						Temp = DECAL_BLOOD_3
						;[End Block]
					Case 4
						;[Block]
						xTemp = 1260.0
						zTemp = 627.0
						Temp = DECAL_BLOOD_5
						;[End Block]
				End Select
				de.Decals = CreateDecal(Temp, r\x + xTemp * RoomScale, r\y + 2.0 * RoomScale, r\z + zTemp * RoomScale, 90.0, 45.0, 0.0, ((i = 0) * 0.44) + ((i = 1) * 1.2) + ((i > 1) * 0.54), Rnd(0.8, 1.0))
			Next
			
			sc.SecurityCams = CreateSecurityCam(r\x - 4048.0 * RoomScale, r\y - 32.0 * RoomScale, r\z - 1232.0 * RoomScale, r, True, r\x - 2256.0 * RoomScale, r\y + 224.0 * RoomScale, r\z - 928.0 * RoomScale)
			sc\Angle = 270.0 : sc\Turn = 45.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			TurnEntity(sc\ScrOBJ, 0.0, 90.0, 0.0)
			
			it.Items = CreateItem("Class D Orientation Leaflet", "paper", r\x - (2914.0 + 1024.0) * RoomScale, r\y + 170.0 * RoomScale, r\z + 40.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room2c_ec"
			;[Block]
			d.Doors = CreateDoor(r\x, r\y, r\z + 384.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_4)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True) + 0.1, EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True), True)	
			
			d.Doors = CreateDoor(r\x - 704.0 * RoomScale, r\y + 896.0 * RoomScale, r\z + 736.0 * RoomScale, 90.0, r, False, ONE_SIDED_DOOR, KEY_CARD_4)
			
			For k = 0 To 2
				r\Objects[k * 2] = CopyEntity(lvr_I\LeverModelID[LEVER_BASE_MODEL])
				r\Objects[k * 2 + 1] = CopyEntity(lvr_I\LeverModelID[LEVER_HANDLE_MODEL])
				
				r\Levers[k] = r\Objects[k * 2 + 1]
				
				For i = 0 To 1
					ScaleEntity(r\Objects[k * 2 + i], 0.04, 0.04, 0.04)
					PositionEntity(r\Objects[k * 2 + i], r\x - 240.0 * RoomScale, r\y + 1104.0 * RoomScale, r\z + (632.0 - 64.0 * k) * RoomScale)
					EntityParent(r\Objects[k * 2 + i], r\OBJ)
				Next
				RotateEntity(r\Objects[k * 2], 0.0, -90.0, 0.0)
				RotateEntity(r\Objects[k * 2 + 1], 10.0, -90.0 - 180.0, 0.0)
				EntityPickMode(r\Objects[k * 2 + 1], 1, False)
				EntityRadius(r\Objects[k * 2 + 1], 0.1)
			Next
			
			sc.SecurityCams = CreateSecurityCam(r\x - 265.0 * RoomScale, r\y + 1280.0 * RoomScale, r\z + 105.0 * RoomScale, r)
			sc\Angle = 45.0 : sc\Turn = 45.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			
			it.Items = CreateItem("Note from Daniel", "paper", r\x - 400.0 * RoomScale, r\y + 1040.0 * RoomScale, r\z + 115.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "cont1_106"
			;[Block]
			; ~ Elevators' doors
			r\RoomDoors.Doors[0] = CreateDoor(r\x - 704.0 * RoomScale, r\y, r\z - 704.0 * RoomScale, 90.0, r, True, ELEVATOR_DOOR) 
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x - 704.0 * RoomScale, r\y - 7327.9 * RoomScale, r\z - 704.0 * RoomScale, 90.0, r, False, ELEVATOR_DOOR) 
			
			; ~ Door to the containment area
			d.Doors = CreateDoor(r\x - 178.0 * RoomScale, r\y - 7328.0 * RoomScale, r\z - 422.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_4)
			
			; ~ Doors to the lower area
			d.Doors = CreateDoor(r\x - 1140.0 * RoomScale, r\y - 8100.0 * RoomScale, r\z + 1613.0 * RoomScale, 180.0, r, False, DEFAULT_DOOR, KEY_CARD_4)
			
			d.Doors = CreateDoor(r\x - 762.0 * RoomScale, r\y - 8608.0 * RoomScale, r\z + 51.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_4)
			
			; ~ Other doors
			d.Doors = CreateDoor(r\x + 384.0 * RoomScale, r\y, r\z - 704.0 * RoomScale, 90.0, r, False, HEAVY_DOOR) 
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			
			d.Doors = CreateDoor(r\x - 288.0 * RoomScale, r\y - 7328.0 * RoomScale, r\z - 1602.0 * RoomScale, 0.0, r, False, HEAVY_DOOR) 
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			
			; ~ Levers
			For k = 0 To 2 Step 2
				r\Objects[k] = CopyEntity(lvr_I\LeverModelID[LEVER_BASE_MODEL])
				r\Objects[k + 1] = CopyEntity(lvr_I\LeverModelID[LEVER_HANDLE_MODEL])
				
				r\Levers[k / 2] = r\Objects[k + 1]
				
				For i = 0 To 1
					ScaleEntity(r\Objects[k + i], 0.04, 0.04, 0.04)
					PositionEntity(r\Objects[k + i], r\x - (744.0 - 79.0 * (k / 2.0)) * RoomScale, r\y - 7904.0 * RoomScale, r\z + 3119.0 * RoomScale, True)
					EntityParent(r\Objects[k + i], r\OBJ)
				Next
				RotateEntity(r\Objects[k], 0.0, 0.0, 0.0)
				RotateEntity(r\Objects[k + 1], 10.0, -180.0, 0.0)
				EntityPickMode(r\Objects[k + 1], 1, False)
				EntityRadius(r\Objects[k + 1], 0.1)
			Next
			RotateEntity(r\Objects[1], 81.0, -180.0, 0.0)
			RotateEntity(r\Objects[3], -81.0, -180.0, 0.0)			
			
			; ~ Femur breaker button
			r\Objects[4] = CreateButton(0, r\x - 337.0 * RoomScale, r\y - 7904.0 * RoomScale, r\z + 3136.0 * RoomScale)
			
			; ~ Class-D spawnpoint
			r\Objects[5] = CreatePivot()
			TurnEntity(r\Objects[5], 0.0, 180.0, 0.0)
			PositionEntity(r\Objects[5], r\x + 1088.0 * RoomScale, r\y - 6224.0 * RoomScale, r\z + 1824.0 * RoomScale) 
			
			; ~ Chamber		
			r\Objects[6] = LoadRMesh("GFX\map\hcz\cont1_106_box.rmesh", Null)
			ScaleEntity(r\Objects[6], RoomScale, RoomScale, RoomScale)
			EntityPickMode(r\Objects[6], 2)
			PositionEntity(r\Objects[6], r\x + 692.0 * RoomScale, r\y - 8308.0 * RoomScale, r\z + 1032.0 * RoomScale)
			
			For i = 4 To 6
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			; ~ Security camera inside the box
			sc.SecurityCams = CreateSecurityCam(r\x + 768.0 * RoomScale, r\y - 5936.0 * RoomScale, r\z + 1632.0 * RoomScale, r, True, r\x - 462.0 * RoomScale, r\y - 7872.0 * RoomScale, r\z + 3105.0 * RoomScale)
			sc\Angle = 315.0 : sc\Turn = 20.0 : sc\CoffinEffect = 0
			TurnEntity(sc\CameraOBJ, 45.0, 0.0, 0.0)
			TurnEntity(sc\ScrOBJ, 0.0, -10.0, 0.0)
			
			r\Objects[7] = sc\CameraOBJ
			r\Objects[8] = sc\BaseOBJ
			
			; ~ Security camera inside the observation room
			sc.SecurityCams = CreateSecurityCam(r\x - 1439.0 * RoomScale, r\y - 7664.0 * RoomScale, r\z + 1709.0 * RoomScale, r)
			sc\Angle = 315.0 : sc\Turn = 30.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			
			; ~ Elevators' pivots
			r\Objects[9] = CreatePivot()
			PositionEntity(r\Objects[9], r\x - 1008.0 * RoomScale, r\y + 240.0 * RoomScale, r\z - 704.0 * RoomScale)
			
			r\Objects[10] = CreatePivot()
			PositionEntity(r\Objects[10], r\x - 1008.0 * RoomScale, r\y - 7088.0 * RoomScale, r\z - 704.0 * RoomScale)
			
			For i = 9 To 10
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			r\Objects[11] = LoadMesh_Strict("GFX\map\hcz\cont1_106_hb.b3d", r\OBJ)
			EntityPickMode(r\Objects[11], 2)
			EntityType(r\Objects[11], HIT_MAP)
			EntityAlpha(r\Objects[11], 0.0)
			
			it.Items = CreateItem("Level 5 Key Card", "key5", r\x - 1275.0 * RoomScale, r\y - 7910.0 * RoomScale, r\z + 3106.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Dr. Allok's Note", "paper", r\x - 87.0 * RoomScale, r\y - 7904.0 * RoomScale, r\z + 2535.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Recall Protocol RP-106-N", "paper", r\x - 989.0 * RoomScale, r\y - 8008.0 * RoomScale, r\z + 3107.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room1_archive"
			;[Block]
			d.Doors = CreateDoor(r\x, r\y, r\z - 512.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, Rand(KEY_CARD_1, KEY_CARD_2))
			
			sc.SecurityCams = CreateSecurityCam(r\x - 256.0 * RoomScale, r\y + 384.0 * RoomScale, r\z + 640.0 * RoomScale, r)
			sc\Angle = 180.0 : sc\Turn = 45.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			
			For xTemp2 = 0 To 1
				For yTemp2 = 0 To 2
					For zTemp2 = 0 To 2
						ItemName = "9V Battery" : ItemTempName = "bat"
						
						Local ItemChance% = Rand(-10, 100)
						
						Select True
							Case ItemChance < 0
								;[Block]
								Exit
								;[End Block]
							Case ItemChance < 40 ; ~ 40% chance for a document
								;[Block]
								ItemName = "Document SCP-" + GetRandDocument()
								ItemTempName = "paper"
								;[End Block]
							Case ItemChance >= 40 And ItemChance < 45 ; ~ 5% chance for a key card
								;[Block]
								Temp3 = Rand(0, 2)
								ItemName = "Level " + Str(Temp3) + " Key Card"
								ItemTempName = "key" + Str(Temp3)
								;[End Block]
							Case ItemChance >= 45 And ItemChance < 50 ; ~ 5% chance for a medkit
								;[Block]
								ItemName = "First Aid Kit"
								ItemTempName = "firstaid"
								;[End Block]
							Case ItemChance >= 50 And ItemChance < 60 ; ~ 10% chance for a battery
								;[Block]
								Temp2 = Rand(2)
								Select Temp2
									Case 1 ; ~ 50% chance for 9V Battery
										;[Block]
										ItemName = "9V Battery"
										ItemTempName = "bat"
										;[End Block]
									Case 2 ; ~ 50% chance for 18V Battery
										;[Block]
										ItemName = "18V Battery"
										ItemTempName = "finebat"
										;[End Block]
								End Select
								;[End Block]
							Case ItemChance >= 60 And ItemChance < 70 ; ~ 10% chance for an S-NAV
								;[Block]
								ItemName = "S-NAV Navigator"
								ItemTempName = "nav"
								;[End Block]
							Case ItemChance >= 70 And ItemChance < 85 ; ~ 15% chance for a radio
								;[Block]
								ItemName = "Radio Transceiver"
								ItemTempName = "radio"
								;[End Block]
							Case ItemChance >= 85 And ItemChance < 95 ; ~ 10% chance for a clipboard
								;[Block]
								ItemName = "Clipboard"
								ItemTempName = "clipboard"
								;[End Block]
							Case ItemChance >= 95 And ItemChance <= 100 ; ~ 5% chance for misc
								;[Block]
								Temp3 = Rand(1, 3)
								Select Temp3
									Case 1 ; ~ Playing card
										;[Block]
										ItemName = "Playing Card"
										ItemTempName = "playcard"
										;[End Block]
									Case 2 ; ~ Mastercard
										;[Block]
										ItemName = "Mastercard"
										ItemTempName = Lower(ItemName)
										;[End Block]
									Case 3 ; ~ Origami
										;[Block]
										ItemName = "Origami"
										ItemTempName = Lower(ItemName)
										;[End Block]
								End Select
								;[End Block]
						End Select
						xTemp = (-672.0) + (864.0 * xTemp2)
						yTemp = 96.0 + (96.0 * yTemp2)
						zTemp = 480.0 - (352.0 * zTemp2) + Rnd(-96.0, 96.0)
						
						it.Items = CreateItem(ItemName, ItemTempName, r\x + xTemp * RoomScale, r\y + yTemp * RoomScale, r\z + zTemp * RoomScale)
						EntityParent(it\Collider, r\OBJ)							
					Next
				Next
			Next
			;[End Block]
		Case "cont2_1123"
			;[Block]
			; ~ Door to the containment chamber itself
			d.Doors = CreateDoor(r\x + 912.0 * RoomScale, r\y, r\z + 368.0 * RoomScale, 0.0, r, False, ONE_SIDED_DOOR, KEY_CARD_2)
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True) - 0.06, EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True) + 0.061, True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True) + 0.12, EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True) - 0.061, True)
			
			; ~ Door to the pre-containment chamber
			d.Doors = CreateDoor(r\x + 352.0 * RoomScale, r\y, r\z - 640.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_0)
			
			; ~ Fake door to the contianment chamber itself
			d.Doors = CreateDoor(r\x + 912.0 * RoomScale, r\y + 769.0 * RoomScale, r\z + 368.0 * RoomScale, 0.0, r, True, ONE_SIDED_DOOR, KEY_CARD_2)
			d\Locked = 1 : d\AutoClose = False
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True) - 0.12, EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True) + 0.061, True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True) + 0.12, EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True) - 0.061, True)
			
			; ~ Fake door to the pre-containment chamber
			r\RoomDoors.Doors[0] = CreateDoor(r\x + 352.0 * RoomScale, r\y + 769.0 * RoomScale, r\z - 640.0 * RoomScale, 90.0, r)
			r\RoomDoors[0]\AutoClose = False : r\RoomDoors[0]\DisableWaypoint = True
			FreeEntity(r\RoomDoors[0]\Buttons[1]) : r\RoomDoors[0]\Buttons[1] = 0
			
			; ~ A door inside the cell 
			r\RoomDoors.Doors[1] = CreateDoor(r\x - 336.0 * RoomScale, r\y + 769.0 * RoomScale, r\z + 712.0 * RoomScale, 90.0, r, False, WOODEN_DOOR)
			r\RoomDoors[1]\Locked = 2 : d\MTFClose = False
			
			; ~ An intermediate door
			r\RoomDoors.Doors[2] = CreateDoor(r\x - 336.0 * RoomScale, r\y + 769.0 * RoomScale, r\z + 168.0 * RoomScale, 270.0, r, False, WOODEN_DOOR)
			
			; ~ A door leading to the nazi before shot
			r\RoomDoors.Doors[3] = CreateDoor(r\x - 668.0 * RoomScale, r\y + 769.0 * RoomScale, r\z - 704.0 * RoomScale, 0.0, r, False, WOODEN_DOOR)
			
			; ~ Just locked doors inside the nazi camp
			d.Doors = CreateDoor(r\x, r\y + 769.0 * RoomScale, r\z + 416.0 * RoomScale, 0.0, r, False, WOODEN_DOOR)
			d\Locked = 2 : d\MTFClose = False
			
			d.Doors = CreateDoor(r\x, r\y + 769.0 * RoomScale, r\z - 1024.0 * RoomScale, 0.0, r, False, WOODEN_DOOR)
			d\Locked = 2 : d\MTFClose = False
			
			; ~ SCP-1123 sound position
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 912.0 * RoomScale, r\y + 170.0 * RoomScale, r\z + 857.0 * RoomScale)
			
			; ~ Nazi position near the player's cell
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x - 139.0 * RoomScale, r\y + 910.0 * RoomScale, r\z + 655.0 * RoomScale)
			
			; ~ Player's position inside the cell
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x - 818.0 * RoomScale, r\y + 850.0 * RoomScale, r\z + 736.0 * RoomScale)
			
			; ~ Player's position after leaving the cell
			r\Objects[3] = CreatePivot()
			PositionEntity(r\Objects[3], r\x + 828.0 * RoomScale, r\y + 850.0 * RoomScale, r\z + 592.0 * RoomScale)
			
			; ~ Nazi position before the shooting
			r\Objects[4] = CreatePivot()
			PositionEntity(r\Objects[4], r\x - 706.0 * RoomScale, r\y + 910.0 * RoomScale, r\z - 845.0 * RoomScale)	
			
			; ~ Nazi position while start shooting
			r\Objects[5] = CreatePivot()
			PositionEntity(r\Objects[5], r\x - 575.0 * RoomScale, r\y + 910.0 * RoomScale, r\z - 402.0 * RoomScale)
			
			; ~ Player's position befor the shooting
			r\Objects[6] = CreatePivot()
			PositionEntity(r\Objects[6], r\x - 468.0 * RoomScale, r\y + 850.0 * RoomScale, r\z - 273.0 * RoomScale)
			
			For i = 0 To 6
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			it.Items = CreateItem("Document SCP-1123", "paper", r\x + 606.0 * RoomScale, r\y + 125.0 * RoomScale, r\z - 936.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Gas Mask", "gasmask", r\x + 609.0 * RoomScale, r\y + 150.0 * RoomScale, r\z + 961.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("SCP-1123", "scp1123", r\x + 912.0 * RoomScale, r\y + 170.0 * RoomScale, r\z + 857.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, 90.0, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Leaflet", "paper", r\x - 756.0 * RoomScale, r\y + 920.0 * RoomScale, r\z + 521.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "dimension_106"
			;[Block]
			; ~ Doors inside fake tunnel
			r\RoomDoors.Doors[0] = CreateDoor(r\x, r\y + 2060.0 * RoomScale, r\z + 32.0 - 1024.0 * RoomScale, 0.0, r, False, HEAVY_DOOR)
			r\RoomDoors[0]\AutoClose = False
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x, r\y + 2048.0 * RoomScale, r\z + 32.0 + 1024.0 * RoomScale, 180.0, r, False, HEAVY_DOOR)
			r\RoomDoors[1]\AutoClose = False
			
			de.Decals = CreateDecal(DECAL_PD_6, r\x - (1536.0 * RoomScale), r\y + 0.02, r\z + 608.0 * RoomScale + 32.0, 90.0, 0.0, 0.0, 0.8, 1.0, 1 + 8, 2)
			
			Local Hallway% = LoadRMesh("GFX\map\dimension106\dimension_106_2.rmesh", Null) ; ~ The tunnels in the first room
			
			For i = 1 To 8
				r\Objects[i - 1] = CopyEntity(Hallway)
				
				Angle = (i - 1) * (360.0 / 8.0)
				
				ScaleEntity(r\Objects[i - 1], RoomScale, RoomScale, RoomScale)
				EntityType(r\Objects[i - 1], HIT_MAP)
				EntityPickMode(r\Objects[i - 1], 2)
				RotateEntity(r\Objects[i - 1], 0.0, Angle - 90.0, 0.0)
				PositionEntity(r\Objects[i - 1], r\x + Cos(Angle) * (512.0 * RoomScale), r\y, r\z + Sin(Angle) * (512.0 * RoomScale))
				EntityParent(r\Objects[i - 1], r\OBJ)
				
				If i < 6 Then 
					de.Decals = CreateDecal(i + 7, r\x + Cos(Angle) * (512.0 * RoomScale) * 3.0, r\y + 0.02, r\z + Sin(Angle) * (512.0 * RoomScale) * 3.0, 90.0, Angle - 90.0, 0.0, 0.5, 1.0, 1 + 8, 2)
				EndIf				
			Next
			FreeEntity(Hallway)
			
			r\Objects[8] = LoadRMesh("GFX\map\dimension106\pocketdimension3.rmesh", Null) ; ~ The room with the throne, moving pillars etc 
			
			r\Objects[9] = LoadRMesh("GFX\map\dimension106\pocketdimension4.rmesh", Null) ; ~ The flying pillar
			
			r\Objects[10] = CopyEntity(r\Objects[9])
			
			r\Objects[11] = LoadRMesh("GFX\map\dimension106\dimension_106_5.rmesh", Null) ; ~ The pillar room
			
			For i = 8 To 11
				ScaleEntity(r\Objects[i], RoomScale * ((i <> 10) + ((i = 10) * 1.5)), RoomScale * ((i <> 10) + ((i = 10) * 2.0)), RoomScale * ((i <> 10) + ((i = 10) * 1.5)))
				EntityType(r\Objects[i], HIT_MAP)
				EntityPickMode(r\Objects[i], 2)
				PositionEntity(r\Objects[i], r\x, r\y, r\z + 32.0 + (32.0 * (i = 11)), True)
			Next
			
			For i = 12 To 16
				r\Objects[i] = CreatePivot(r\Objects[11])
				Select i
					Case 12
						;[Block]
						PositionEntity(r\Objects[i], r\x, r\y + 200.0 * RoomScale, r\z + 64.0, True)
						;[End Block]
					Case 13
						;[Block]
						PositionEntity(r\Objects[i], r\x + 390.0 * RoomScale, r\y + 200.0 * RoomScale, r\z + 64.0 + 272.0 * RoomScale, True)
						;[End Block]
					Case 14
						;[Block]
						PositionEntity(r\Objects[i], r\x + 838.0 * RoomScale, r\y + 200.0 * RoomScale, r\z + 64.0 - 551.0 * RoomScale, True)	
						;[End Block]
					Case 15
						;[Block]
						PositionEntity(r\Objects[i], r\x - 139.0 * RoomScale, r\y + 200.0 * RoomScale, r\z + 64.0 + 1201.0 * RoomScale, True)
						;[End Block]
					Case 16
						;[Block]
						PositionEntity(r\Objects[i], r\x - 1238.0 * RoomScale, r\y - 1664.0 * RoomScale, r\z + 64.0 + 381.0 * RoomScale, True)
						;[End Block]
				End Select 
			Next
			
			r\Textures[0] = LoadTexture_Strict("GFX\npcs\pd_plane.png", 1 + 2, DeleteAllTextures)
			
			r\Textures[1] = LoadTexture_Strict("GFX\npcs\pd_plane_eye.png", 1 + 2, DeleteAllTextures)
			
			Tex = LoadTexture_Strict("GFX\npcs\scps\scp_106_eyes.png", 1, DeleteAllTextures)
			r\Objects[17] = CreateSprite()
			EntityTexture(r\Objects[17], Tex)
			DeleteSingleTextureEntryFromCache(Tex)
			PositionEntity(r\Objects[17], EntityX(r\Objects[8], True), r\y + 1376.0 * RoomScale, EntityZ(r\Objects[8], True) - 2848.0 * RoomScale)
			RotateEntity(r\Objects[17], 0.0, 180.0, 0.0)
			ScaleSprite(r\Objects[17], 0.03, 0.03)
			EntityBlend(r\Objects[17], 3)
			EntityFX(r\Objects[17], 1 + 8)
			SpriteViewMode(r\Objects[17], 2)
			
			r\Objects[18] = LoadMesh_Strict("GFX\map\dimension106\throne_wall.b3d")
			PositionEntity(r\Objects[18], EntityX(r\Objects[8], True), r\y, EntityZ(r\Objects[8], True) - 864.5 * RoomScale)
			ScaleEntity(r\Objects[18], RoomScale / 2.04, RoomScale, RoomScale)
			EntityPickMode(r\Objects[18], 2)
			EntityType(r\Objects[18], HIT_MAP)
			EntityParent(r\Objects[18], r\OBJ)
			
			r\Objects[19] = CreateSprite()
			ScaleSprite(r\Objects[19], 8.0, 8.0)
			EntityTexture(r\Objects[19], r\Textures[0])
			EntityOrder(r\Objects[19], 100)
			EntityBlend(r\Objects[19], 2)
			EntityFX(r\Objects[19], 1 + 8)
			SpriteViewMode(r\Objects[19], 2)
			
			r\Objects[20] = LoadMesh_Strict("GFX\map\dimension106\pocketdimensionterrain.b3d")
			ScaleEntity(r\Objects[20], RoomScale, RoomScale, RoomScale)
			EntityType(r\Objects[20], HIT_MAP)
			PositionEntity(r\Objects[20], r\x, r\y + 2944.0 * RoomScale, r\z + 32.0, True)
			
			For i = 17 To 20
				HideEntity(r\Objects[i])
			Next
			
			it.Items = CreateItem("Burnt Note", "paper", EntityX(r\OBJ), r\y + 0.5, EntityZ(r\OBJ) + 3.5)
			;[End Block]
		Case "room3_4_ez"
			;[Block]
			sc.SecurityCams = CreateSecurityCam(r\x - 320.0 * RoomScale, r\y + 384.0 * RoomScale, r\z + 512.25 * RoomScale, r)
			sc\Angle = 225.0 : sc\Turn = 45.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			;[End Block]
		Case "room1_lifts"
			;[Block]
			d.Doors = CreateDoor(r\x - 239.0 * RoomScale, r\y, r\z + 96.0 * RoomScale, 0.0, r, False, ELEVATOR_DOOR)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[1]) : d\Buttons[1] = 0
			FreeEntity(d\ElevatorPanel[0]) : d\ElevatorPanel[0] = 0
			
			d.Doors = CreateDoor(r\x + 239.0 * RoomScale, r\y, r\z + 96.0 * RoomScale, 0.0, r, False, ELEVATOR_DOOR)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True) - 1.2, EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True), True)
			FreeEntity(d\Buttons[1]) : d\Buttons[1] = 0
			FreeEntity(d\ElevatorPanel[0]) : d\ElevatorPanel[0] = 0
			
			sc.SecurityCams = CreateSecurityCam(r\x + 384.0 * RoomScale, r\y + 384.0 * RoomScale, r\z - 960.0 * RoomScale, r)
			sc\Angle = 45.0 : sc\Turn = 45.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			;[End Block]
		Case "room2_servers_ez"
			;[Block]
			d.Doors = CreateDoor(r\x + 256.0 * RoomScale, r\y, r\z + 672.0 * RoomScale, 270.0, r, False, DEFAULT_DOOR, KEY_CARD_4)
			
			d.Doors = CreateDoor(r\x - 512.0 * RoomScale, r\y - 768.0 * RoomScale, r\z - 320.0 * RoomScale, 180.0, r, False, ONE_SIDED_DOOR, KEY_CARD_4)
			
			d.Doors = CreateDoor(r\x - 512.0 * RoomScale, r\y - 768.0 * RoomScale, r\z - 1040.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_4)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			it.Items = CreateItem("Night Vision Goggles", "nvg", r\x + 48.0 * RoomScale, r\y - 648.0 * RoomScale, r\z + 784.0 * RoomScale)
			it\State = Rnd(100.0, 1000.0)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room2_gw", "room2_gw_2"
			;[Block]
			If r\RoomTemplate\Name = "room2_gw_2" Then
				r\Objects[0] = CreatePivot()
				PositionEntity(r\Objects[0], r\x + 262.0 * RoomScale, r\y + 325.0 * RoomScale, r\z - 345.0 * RoomScale)
				
				r\Objects[1] = CreatePivot()
				PositionEntity(r\Objects[1], r\x - 156.0 * RoomScale, r\y + 0.5, r\z + 121.0 * RoomScale)
				
				For i = 0 To 1
					EntityParent(r\Objects[i], r\OBJ)
				Next
			EndIf
			
			d.Doors = CreateDoor(r\x - 468.0 * RoomScale, r\y, r\z + 729.0 * RoomScale, 270.0, r)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			r\RoomDoors.Doors[0] = CreateDoor(r\x + 336.0 * RoomScale, r\y, r\z - 382.0 * RoomScale, 0.0, r, True)
			r\RoomDoors[0]\Locked = 1 : r\RoomDoors[0]\MTFClose = False
			For i = 0 To 1
				FreeEntity(r\RoomDoors[0]\Buttons[i]) : r\RoomDoors[0]\Buttons[i] = 0
			Next
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x + 336.0 * RoomScale, r\y, r\z + 462.0 * RoomScale, 180.0, r, True)
			r\RoomDoors[1]\Locked = 1 : r\RoomDoors[1]\MTFClose = False
			For i = 0 To 1
				FreeEntity(r\RoomDoors[1]\Buttons[i]) : r\RoomDoors[1]\Buttons[i] = 0
			Next
			
			For r2.Rooms = Each Rooms
				If r2 <> r Then
					If r2\RoomTemplate\Name = "room2_gw" Lor r2\RoomTemplate\Name = "room2_gw_2" Then
						r\Objects[2] = CopyEntity(r2\Objects[2]) ; ~ Don't load the mesh again
						Exit
					EndIf
				EndIf
			Next
			If (Not r\Objects[2]) Then r\Objects[2] = LoadRMesh("GFX\map\lcz\room2_gw_pipes.rmesh", Null)
			ScaleEntity(r\Objects[2], RoomScale, RoomScale, RoomScale)
			PositionEntity(r\Objects[2], r\x, r\y, r\z)
			EntityParent(r\Objects[2], r\OBJ)
			
			If r\RoomTemplate\Name = "room2_gw" Then
				r\Objects[0] = CreatePivot()
				PositionEntity(r\Objects[0], r\x + 344.0 * RoomScale, r\y + 128.0 * RoomScale, r\z)
				EntityParent(r\Objects[0], r\OBJ)
				
				Local BD_Temp% = False
				
				If bk\IsBroken Then
					If bk\x = r\x And bk\z = r\z Then
						BD_Temp = True
					EndIf
				EndIf
				
				If ((Not bk\IsBroken) And Rand(2) = 1) Lor BD_Temp Then
					r\Objects[1] = CopyEntity(d_I\DoorModelID[DOOR_DEFAULT_MODEL])
					ScaleEntity(r\Objects[1], (204.0 * RoomScale) / MeshWidth(r\Objects[1]), 313.0 * RoomScale / MeshHeight(r\Objects[1]), 16.0 * RoomScale / MeshDepth(r\Objects[1]))
					EntityType(r\Objects[1], HIT_MAP)
					PositionEntity(r\Objects[1], r\x + 336.0 * RoomScale, r\y, r\z + 462.0 * RoomScale)
					RotateEntity(r\Objects[1], 0.0, 180.0 + 180.0, 0.0)
					EntityParent(r\Objects[1], r\OBJ)
					MoveEntity(r\Objects[1], 120.0, 0.0, 5.0)
					
					bk\IsBroken = True
					bk\x = r\x
					bk\z = r\z
					
					FreeEntity(r\RoomDoors[1]\OBJ2) : r\RoomDoors[1]\OBJ2 = 0
				EndIf
			EndIf
			;[End Block]
		Case "room3_gw"
			;[Block]
			d.Doors = CreateDoor(r\x + 174.0 * RoomScale, r\y, r\z - 736.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			d.Doors = CreateDoor(r\x - 728.0 * RoomScale, r\y, r\z - 456.5 * RoomScale, 0.0, r, False, DEFAULT_DOOR)
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True), EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True) + 0.044, True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True), EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True) - 0.044, True)	
			
			d.Doors = CreateDoor(r\x - 222.5 * RoomScale, r\y, r\z - 736.0 * RoomScale, -90.0, r, False, DEFAULT_DOOR)
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True) + 0.052, EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True), True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True) - 0.052, EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True), True)
			
			d.Doors = CreateDoor(r\x - 31.0 * RoomScale, r\y, r\z - 456.5 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True) - 0.09, EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True) + 0.044, True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True) + 1.035, EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True) - 0.044, True)	
			
			r\RoomDoors.Doors[0] = CreateDoor(r\x - 459.0 * RoomScale, r\y, r\z + 339.0 * RoomScale, 90.0, r, True, DEFAULT_DOOR)
			r\RoomDoors[0]\Locked = 1 : r\RoomDoors[0]\MTFClose = False
			
			it.Items = CreateItem("Radio Transceiver", "18vradio", r\x + 308.0 * RoomScale, r\y + 92.0 * RoomScale, r\z + 409.0 * RoomScale)
			it\State = Rnd(10.0, 100.0)
			
			For i = 0 To 1
				FreeEntity(r\RoomDoors[0]\Buttons[i]) : r\RoomDoors[0]\Buttons[i] = 0
			Next
			
			r\RoomDoors[1] = CreateDoor(r\x + 385.0 * RoomScale, r\y, r\z + 339.0 * RoomScale, 270.0, r, True, DEFAULT_DOOR)
			r\RoomDoors[1]\Locked = 1 : r\RoomDoors[1]\MTFClose = False
			For i = 0 To 1
				FreeEntity(r\RoomDoors[1]\Buttons[i]) : r\RoomDoors[1]\Buttons[i] = 0
			Next
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x - 48.0 * RoomScale, r\y + 128.0 * RoomScale, r\z + 320.0 * RoomScale)
			EntityParent(r\Objects[0], r\OBJ)
			
			For r2.Rooms = Each Rooms
				If r2 <> r Then
					If r2\RoomTemplate\Name = "room3_gw" Then
						r\Objects[1] = CopyEntity(r2\Objects[1]) ; ~ Don't load the mesh again
						Exit
					EndIf
				EndIf
			Next
			If (Not r\Objects[1]) Then r\Objects[1] = LoadRMesh("GFX\map\ez\room3_gw_pipes.rmesh", Null)
			ScaleEntity(r\Objects[1], RoomScale, RoomScale, RoomScale)
			PositionEntity(r\Objects[1], r\x, r\y, r\z)
			EntityParent(r\Objects[1], r\OBJ)
			;[End Block]
		Case "cont2c_1162_arc"
			;[Block]
			d.Doors = CreateDoor(r\x + 248.0 * RoomScale, r\y, r\z - 736.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_2)
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True) - 0.031, EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True), True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True) + 0.031, EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True), True)
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 1012.0 * RoomScale, r\y + 128.0 * RoomScale, r\z - 640.0 * RoomScale)
			EntityPickMode(r\Objects[0], 1)
			EntityParent(r\Objects[0], r\OBJ)
			
			sc.SecurityCams = CreateSecurityCam(r\x - 192.0 * RoomScale, r\y + 704.0 * RoomScale, r\z + 192.0 * RoomScale, r)
			sc\Angle = 225.0 : sc\Turn = 45.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			
			it.Items = CreateItem("Document SCP-1162-ARC", "paper", r\x + 863.227 * RoomScale, r\y + 152.0 * RoomScale, r\z - 953.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "cont2c_066"
			;[Block]
			d.Doors = CreateDoor(r\x - 268.0 * RoomScale, r\y, r\z, 90.0, r, True, DEFAULT_DOOR, KEY_CARD_3)
			d\Locked = 1 : d\MTFClose = False
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True) + 0.018, EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True), True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True) - 0.018, EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True), True)
			;[End Block]
		Case "cont2_500_1499"
			;[Block]
			r\RoomDoors.Doors[0] = CreateDoor(r\x + 288.0 * RoomScale, r\y, r\z + 576.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			r\RoomDoors[0]\Locked = 1
			
			d.Doors = CreateDoor(r\x + 784.0 * RoomScale, r\y, r\z + 672.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_4)
			
			d.Doors = CreateDoor(r\x + 556.0 * RoomScale, r\y, r\z + 288.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 576.0 * RoomScale, r\y + 160.0 * RoomScale, r\z + 632.0 * RoomScale)
			EntityParent(r\Objects[0], r\OBJ)
			
			For i = 0 To 1
				Select i
					Case 0
						;[Block]
						xTemp = 850.0
						yTemp = 385.0
						zTemp = 876.0
						;[End Block]
					Case 1
						;[Block]
						xTemp = 616.0
						yTemp = 512.0
						zTemp = 150.0
						;[End Block]
				End Select
				sc.SecurityCams = CreateSecurityCam(r\x + xTemp * RoomScale, r\y + yTemp * RoomScale, r\z + zTemp * RoomScale, r)
				If i = 0 Then
					sc\Angle = 220.0
				Else
					sc\Angle = 180.0
				EndIf
				sc\Turn = 30.0
				TurnEntity(sc\CameraOBJ, 30.0, 0.0, 0.0)
			Next
			
			it.Items = CreateItem("SCP-1499", "scp1499", r\x + 616.0 * RoomScale, r\y + 176.0 * RoomScale, r\z - 234.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, r\Angle, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Document SCP-1499", "paper", r\x + 837.0 * RoomScale, r\y + 260.0 * RoomScale, r\z + 211.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Emily Ross' Badge", "badge", r\x + 364.0 * RoomScale, r\y + 5.0 * RoomScale, r\z + 716.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Document SCP-500", "paper", r\x + 891.0 * RoomScale, r\y + 228.0 * RoomScale, r\z + 485.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ) : RotateEntity(it\Collider, 0.0, 90.0, 0.0)
			;[End Block]
		Case "room3_office"
			;[Block]			
			d.Doors = CreateDoor(r\x + 768.0 * RoomScale, r\y, r\z + 234.0 * RoomScale, 180.0, r, True, OFFICE_DOOR)
			
			r\Objects[0] = LoadMesh_Strict("GFX\map\ez\room3_office_hb.b3d", r\OBJ)
			EntityPickMode(r\Objects[0], 2)
			EntityType(r\Objects[0], HIT_MAP)
			EntityAlpha(r\Objects[0], 0.0)
			de.Decals = CreateDecal(DECAL_WATER, r\x + 236.0 * RoomScale, r\y + 0.005, r\z - 68.0 * RoomScale, 90.0, Rnd(360.0), 0.0, Rnd(0.5, 0.7), 1.0)
			EntityParent(de\OBJ, r\OBJ)
			;[End Block]
		Case "room2_office"
			;[Block]
			d.Doors = CreateDoor(r\x - 244.0 * RoomScale, r\y, r\z, 90.0, r)
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True) - 0.048, EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True), True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True) + 0.048, EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True), True)
			
			d.Doors = CreateDoor(r\x - 1216.0 * RoomScale, r\y - 384.0 * RoomScale, r\z - 1024.0 * RoomScale, 0.0, r)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			d.Doors = CreateDoor(r\x - 1216.0 * RoomScale, r\y - 384.0 * RoomScale, r\z + 1024.0 * RoomScale, 180.0, r)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			it.Items = CreateItem("Sticky Note", "paper", r\x - 991.0 * RoomScale, r\y - 242.0 * RoomScale, r\z + 904.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room2_sl"
			;[Block]
			; ~ Doors for room
			r\RoomDoors.Doors[0] = CreateDoor(r\x + 480.0 * RoomScale, r\y, r\z - 640.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			r\RoomDoors[0]\MTFClose = False
			PositionEntity(r\RoomDoors[0]\Buttons[0], r\x + 576.0 * RoomScale, EntityY(r\RoomDoors[0]\Buttons[0], True), r\z - 474.0 * RoomScale, True)
			RotateEntity(r\RoomDoors[0]\Buttons[0], 0.0, 270.0, 0.0)
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x + 544.0 * RoomScale, r\y + 480.0 * RoomScale, r\z + 256.0 * RoomScale, 270.0, r, False, ONE_SIDED_DOOR, KEY_CARD_3)
			r\RoomDoors[1]\MTFClose = False
			
			d.Doors = CreateDoor(r\x + 1504.0 * RoomScale, r\y + 480.0 * RoomScale, r\z + 960.0 * RoomScale, 180.0, r)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			Scale = RoomScale * 4.5 * 0.4
			
			r\Textures[0] = LoadAnimTexture_Strict("GFX\SL_monitors_checkpoint.png", 1, 512, 512, 0, 4, DeleteAllTextures)
			r\Textures[1] = LoadAnimTexture_Strict("GFX\Sl_monitors.png", 1, 512, 512, 0, 10, DeleteAllTextures)
			
			; ~ Monitor Objects
			For i = 0 To 14
				If i <> 7 Then
					r\Objects[i] = CopyEntity(mon_I\MonitorModelID[MONITOR_DEFAULT_MODEL])
					ScaleEntity(r\Objects[i], Scale, Scale, Scale)
					If i <> 4 And i <> 13 Then
						Screen = CreateSprite()
						EntityFX(Screen, 17)
						SpriteViewMode(Screen, 2)
						ScaleSprite(Screen, MeshWidth(mon_I\MonitorModelID[MONITOR_DEFAULT_MODEL]) * Scale * 0.95 * 0.5, MeshHeight(mon_I\MonitorModelID[MONITOR_DEFAULT_MODEL]) * Scale * 0.95 * 0.5)
						Select i
							Case 0
								;[Block]
								EntityTexture(Screen, r\Textures[1], 0)
								;[End Block]
							Case 1
								;[Block]
								EntityTexture(Screen, r\Textures[1], 9)
								;[End Block]
							Case 2
								;[Block]
								EntityTexture(Screen, r\Textures[1], 2)
								;[End Block]
							Case 3
								;[Block]
								EntityTexture(Screen, r\Textures[1], 1)
								;[End Block]
							Case 5
								;[Block]
								EntityTexture(Screen, r\Textures[1], 8)
								;[End Block]
							Case 8
								;[Block]
								EntityTexture(Screen, r\Textures[1], 4)
								;[End Block]
							Case 9
								;[Block]
								EntityTexture(Screen, r\Textures[1], 5)
								;[End Block]
							Case 10
								;[Block]
								EntityTexture(Screen, r\Textures[1], 3)
								;[End Block]
							Case 11
								;[Block]
								EntityTexture(Screen, r\Textures[1], 7)
								;[End Block]
							Default
								;[Block]
								EntityTexture(Screen, r\Textures[0], 3)
								;[End Block]
						End Select
						EntityParent(Screen, r\Objects[i])
					ElseIf i = 4 Then
						r\Objects[20] = CreateSprite()
						EntityFX(r\Objects[20], 17)
						SpriteViewMode r\Objects[20], 2
						ScaleSprite(r\Objects[20], MeshWidth(mon_I\MonitorModelID[MONITOR_DEFAULT_MODEL]) * Scale * 0.95 * 0.5, MeshHeight(mon_I\MonitorModelID[0]) * Scale * 0.95 * 0.5)
						EntityTexture(r\Objects[20], r\Textures[0], 2)
						EntityParent(r\Objects[20], r\Objects[i])
					Else
						r\Objects[21] = CreateSprite()
						EntityFX(r\Objects[21], 17)
						SpriteViewMode(r\Objects[21], 2)
						ScaleSprite(r\Objects[21], MeshWidth(mon_I\MonitorModelID[MONITOR_DEFAULT_MODEL]) * Scale * 0.95 * 0.5, MeshHeight(mon_I\MonitorModelID[0]) * Scale * 0.95 * 0.5)
						EntityTexture(r\Objects[21], r\Textures[1], 6)
						EntityParent(r\Objects[21], r\Objects[i])
					EndIf
				EndIf
			Next
			
			For i = 0 To 2
				PositionEntity(r\Objects[i], r\x - 207.94 * RoomScale, r\y + (648.0 + (112.0 * i)) * RoomScale, r\z - 60.0686 * RoomScale)
				RotateEntity(r\Objects[i], 0.0, 105.0 + r\Angle, 0.0)
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			For i = 3 To 5
				PositionEntity(r\Objects[i], r\x - 231.489 * RoomScale, r\y + (648.0 + (112.0 * (i - 3))) * RoomScale, r\z + 95.7443 * RoomScale)
				RotateEntity(r\Objects[i], 0.0, 90.0 + r\Angle, 0.0)
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			For i = 6 To 8 Step 2
				PositionEntity(r\Objects[i], r\x - 231.489 * RoomScale, r\y + (648.0 + (112.0 * (i - 6))) * RoomScale, r\z + 255.744 * RoomScale)
				RotateEntity(r\Objects[i], 0.0, 90.0 + r\Angle, 0.0)
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			For i = 9 To 11
				PositionEntity(r\Objects[i], r\x - 231.489 * RoomScale, r\y + (648.0 + (112.0 * (i - 9))) * RoomScale, r\z + 415.744 * RoomScale)
				RotateEntity(r\Objects[i], 0.0, 90.0 + r\Angle, 0.0)
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			For i = 12 To 14
				PositionEntity(r\Objects[i], r\x - 208.138 * RoomScale, r\y + (648.0 + (112.0 * (i - 12))) * RoomScale, r\z + 571.583 * RoomScale)
				RotateEntity(r\Objects[i], 0.0, 75.0 + r\Angle, 0.0)
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			; ~ Main PathPoint for SCP-049
			r\Objects[7] = CreatePivot()
			PositionEntity(r\Objects[7], r\x, r\y + 100.0 * RoomScale, r\z - 800.0 * RoomScale, True)
			EntityParent(r\Objects[7], r\OBJ)
			
			; ~ PathPoints for SCP-049
			r\Objects[15] = CreatePivot()
			PositionEntity(r\Objects[15], r\x + 700.0 * RoomScale, r\y + 700.0 * RoomScale, r\z + 256.0 * RoomScale)
			
			r\Objects[16] = CreatePivot()
			PositionEntity(r\Objects[16], r\x - 60.0 * RoomScale, r\y + 700.0 * RoomScale, r\z + 200.0 * RoomScale)
			
			r\Objects[17] = CreatePivot()
			PositionEntity(r\Objects[17], r\x - 48.0 * RoomScale, r\y + 540.0 * RoomScale, r\z + 656.0 * RoomScale)
			
			For i = 15 To 17
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			r\Objects[9 * 2] = CopyEntity(lvr_I\LeverModelID[LEVER_BASE_MODEL])
			r\Objects[9 * 2 + 1] = CopyEntity(lvr_I\LeverModelID[LEVER_HANDLE_MODEL]) ; TODO
			
			r\Levers[0] = r\Objects[9 * 2 + 1]
			
			For i = 0 To 1
				ScaleEntity(r\Objects[9 * 2 + i], 0.04, 0.04, 0.04)
				PositionEntity(r\Objects[9 * 2 + i], r\x - 49.0 * RoomScale, r\y + 689.0 * RoomScale, r\z + 912.0 * RoomScale)
				EntityParent(r\Objects[9 * 2 + i], r\OBJ)
			Next
			
			RotateEntity(r\Objects[9 * 2], 0.0, 0.0, 0.0)
			RotateEntity(r\Objects[9 * 2 + 1], 10.0, 0.0 - 180.0, 0.0)
			EntityPickMode(r\Objects[9 * 2 + 1], 1, False)
			EntityRadius(r\Objects[9 * 2 + 1], 0.1)
			
			r\Objects[22] = CreateRedLight(r\x + 958.5 * RoomScale, r\y + 762.5 * RoomScale, r\z + 669.0 * RoomScale, r\OBJ)
			
			; ~ Camera in the room itself
			sc.SecurityCams = CreateSecurityCam(r\x - 159.0 * RoomScale, r\y + 384.0 * RoomScale, r\z - 929.0 * RoomScale, r, True, r\x - 231.489 * RoomScale, r\y + 760.0 * RoomScale, r\z + 255.744 * RoomScale)
			sc\Angle = 315.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			TurnEntity(sc\ScrOBJ, 0.0, 90.0, 0.0)
			;[End Block]
		Case "room2_4_lcz"
			;[Block]
			d.Doors = CreateDoor(r\x + 768.0 * RoomScale, r\y, r\z - 827.5 * RoomScale, 90.0, r, False, DEFAULT_DOOR)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True), EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True) + 0.1, True)
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 640.0 * RoomScale, r\y + 8.0 * RoomScale, r\z - 896.0 * RoomScale)
			EntityParent(r\Objects[0], r\OBJ)
			;[End Block]
		Case "room2c_maintenance"
			;[Block]
			d.Doors = CreateDoor(r\x - 272.0 * RoomScale, r\y, r\z - 768.0 * RoomScale, 90.0, r, False, HEAVY_DOOR, KEY_CARD_3)
			d\Locked = 1 : d\MTFClose = False : d\DisableWaypoint = True
			FreeEntity(d\Buttons[1]) : d\Buttons[1] = 0
			
			em.Emitters = CreateEmitter(r\x + 512.0 * RoomScale, r\y - 76.0 * RoomScale, r\z - 688.0 * RoomScale, 0)
			em\RandAngle = 55.0 : em\Speed = 0.0005 : em\AlphaChange = -0.015 : em\SizeChange = 0.007
			TurnEntity(em\OBJ, -90.0, 0.0, 0.0)
			EntityParent(em\OBJ, r\OBJ)
			
			it.Items = CreateItem("Dr. L's Note #2", "paper", r\x - 160.0 * RoomScale, r\y + 32.0 * RoomScale, r\z - 353.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "dimension_1499"
			;[Block]
			r\Objects[16] = CreatePivot()
			PositionEntity(r\Objects[16], r\x + 205.0 * RoomScale, r\y + 200.0 * RoomScale, r\z + 2287.0 * RoomScale)
			EntityParent(r\Objects[16], r\OBJ)
			
			r\Objects[17] = LoadMesh_Strict("GFX\map\dimension1499\1499object0_cull.b3d", r\OBJ)
			EntityType(r\Objects[17], HIT_MAP)
			EntityAlpha(r\Objects[17], 0.0)
			;[End Block]
		Case "room4_ic"
			;[Block]
			d.Doors = CreateDoor(r\x + 704.0 * RoomScale, r\y, r\z - 336.0 * RoomScale, 0.0, r, False, OFFICE_DOOR)
			
			r\Objects[0] = CopyEntity(mon_I\MonitorModelID[MONITOR_CHECKPOINT_MODEL], r\OBJ)
			PositionEntity(r\Objects[0], r\x - 700.0 * RoomScale, r\y + 384.0 * RoomScale, r\z + 290.0 * RoomScale, True)
			ScaleEntity(r\Objects[0], 2.0, 2.0, 2.0)
			RotateEntity(r\Objects[0], 0.0, 0.0, 0.0)

			it.Items = CreateItem("Clipboard", "clipboard", r\x + 916.0 * RoomScale, r\y + 210.0 * RoomScale, r\z - 830.0 * RoomScale)
			; ~ A hacky fix for clipboard's model and icon
			it\InvImg = it\ItemTemplate\InvImg
			SetAnimTime(it\Model, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			it2.Items = CreateItem("Document SCP-1048", "paper", 1.0, 1.0, 1.0)
			it2\Picked = True : it2\Dropped = -1 : it\SecondInv[0] = it2
			HideEntity(it2\Collider)
			EntityParent(it2\Collider, r\OBJ)

			it.Items = CreateItem("4.5V Battery", "coarsebat", r\x + 922.0 * RoomScale, r\y + 210.0 * RoomScale, r\z - 922.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room2_bio"
			;[Block]
			d.Doors = CreateDoor(r\x + 234.0 * RoomScale, r\y, r\z - 768.0 * RoomScale, 90.0, r, False, OFFICE_DOOR)
			d\Locked = 1 : d\MTFClose = False
			
			sc.SecurityCams = CreateSecurityCam(r\x - 475.0 * RoomScale, r\y + 385.0 * RoomScale, r\z + 305.0 * RoomScale, r)
			sc\Angle = 225.0 : sc\Turn = 30.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			;[End Block]
		Case "room2_office_2"
			;[Block]
			d.Doors = CreateDoor(r\x + 234.0 * RoomScale, r\y, r\z, 90.0, r, False, OFFICE_DOOR)
			
			r\Objects[0] = LoadMesh_Strict("GFX\map\ez\room2_office_2_hb.b3d", r\OBJ)
			EntityPickMode(r\Objects[0], 2)
			EntityType(r\Objects[0], HIT_MAP)
			EntityAlpha(r\Objects[0], 0.0)
			
			sc.SecurityCams = CreateSecurityCam(r\x - 475.0 * RoomScale, r\y + 385.0 * RoomScale, r\z + 305.0 * RoomScale, r)
			sc\Angle = 225.0 : sc\Turn = 30.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			
			it.Items = CreateItem("9V Battery", "bat", r\x + 360.0 * RoomScale, r\y + 230.0 * RoomScale, r\z + 960.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ) 
			
			If Rand(2) = 1 Then
				it.Items = CreateItem("9V Battery", "bat", r\x + 435.0 * RoomScale, r\y + 230.0 * RoomScale, r\z + 960.0 * RoomScale)
				EntityParent(it\Collider, r\OBJ) 
			EndIf
			;[End Block]
		Case "room1_o5"
			;[Block]
			d.Doors = CreateDoor(r\x, r\y, r\z - 240.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_MISC, "2411")
			
			it.Items = CreateItem("Field Agent Log #235-001-CO5", "paper", r\x, r\y + 200.0 * RoomScale, r\z + 870.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Groups of Interest Log", "paper", r\x + 100.0 * RoomScale, r\y + 200.0 * RoomScale, r\z + 100.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("First Aid Kit", "firstaid", r\x + 680.0 * RoomScale, r\y + 260.0 * RoomScale, r\z + 892.5 * RoomScale)
			RotateEntity(it\Collider, 0.0, 90.0, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("18V Battery", "finebat", r\x - 700.0 * RoomScale, r\y + 210.0 * RoomScale, r\z + 920.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			
			it.Items = CreateItem("Document SCP-006", "paper", r\x - 700.0 * RoomScale, r\y + 210.0 * RoomScale, r\z + 26.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			RotateEntity(it\Collider, 0.0, 180.0, 0.0)
			
			it.Items = CreateItem("Ballistic Helmet", "helmet", r\x + 344.0 * RoomScale, r\y + 210.0 * RoomScale, r\z - 900.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "cont1_096"
			;[Block]
			d.Doors = CreateDoor(r\x - 320.0 * RoomScale, r\y, r\z + 320.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_4)
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True), EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True), True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True), EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True), True)
			
			d.Doors = CreateDoor(r\x, r\y, r\z, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_4)
			PositionEntity(d\Buttons[0], EntityX(d\Buttons[0], True), EntityY(d\Buttons[0], True), EntityZ(d\Buttons[0], True), True)
			PositionEntity(d\Buttons[1], EntityX(d\Buttons[1], True), EntityY(d\Buttons[1], True), EntityZ(d\Buttons[1], True), True)
			
			de.Decals = CreateDecal(Rand(DECAL_BLOOD_1, DECAL_BLOOD_2), r\x - 152.0 * RoomScale, r\y + 5.0 * RoomScale + 0.005, r\z - 594.0 * RoomScale, 90.0, Rnd(360.0), 0.0, Rand(0.4, 0.6))
			EntityParent(de\OBJ, r\OBJ)
			
			de.Decals = CreateDecal(DECAL_CORROSIVE_1, r\x - 384.0 * RoomScale, r\y + 0.005, r\z - 512.0 * RoomScale, 90.0, Rnd(360.0), 0.0, 0.5, 0.5, 1)
			EntityParent(de\OBJ, r\OBJ)
			
			it.Items = CreateItem("Data Report", "paper", r\x - 152.0 * RoomScale, r\y + 90.0 * RoomScale, r\z - 594.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, 90.0, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("9V Battery", "bat", r\x - 514.0 * RoomScale, r\y + 150.0 * RoomScale, r\z + 63.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Document SCP-096", "paper", r\x - 500.0 * RoomScale, r\y + 220.0 * RoomScale, r\z + 63.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, 90.0, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("SCRAMBLE Gear", "scramble", r\x - 860.0 * RoomScale, r\y + 240.0 * RoomScale, r\z + 80.0 * RoomScale)
			it\State = Rnd(100.0, 1000.0)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "cont2_409"
			;[Block]
			; ~ Elevators' doors
			r\RoomDoors.Doors[0] = CreateDoor(r\x + 256.0 * RoomScale, r\y, r\z + 655.0 * RoomScale, -90.0, r, True, ELEVATOR_DOOR)
			
			r\RoomDoors.Doors[1] = CreateDoor(r\x - 2336.0 * RoomScale, r\y - 4256.0 * RoomScale, r\z - 648.0 * RoomScale, -90.0, r, False, ELEVATOR_DOOR)
			
			; ~ A door to the containment chamber	
			r\RoomDoors.Doors[2] = CreateDoor(r\x - 4352.0 * RoomScale, r\y - 4256.0 * RoomScale, r\z + 1368.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_4)					
			
			; ~ Elevator pivots
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x + 560.0 * RoomScale, r\y + 240.0 * RoomScale, r\z + 656.0 * RoomScale)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x - 2032.0 * RoomScale, r\y - 4011.0 * RoomScale, r\z - 648.0 * RoomScale)
			
			; ~ Class-D spawn
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x - 4858.0 * RoomScale, r\y - 4491.0 * RoomScale, r\z + 1729.0 * RoomScale)
			
			; ~ Touching pivot
			r\Objects[3] = CreatePivot()
			PositionEntity(r\Objects[3], r\x - 4917.0 * RoomScale, r\y - 4310.0 * RoomScale, r\z + 2095.0 * RoomScale)
			
			; ~ Sparks pivot
			r\Objects[4] = CreatePivot()
			PositionEntity(r\Objects[4], r\x - 4523.0 * RoomScale, r\y - 4060.0 * RoomScale, r\z - 2097.0 * RoomScale)
			
			For i = 0 To 4
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			sc.SecurityCams = CreateSecurityCam(r\x - 3635.0 * RoomScale, r\y - 3840.0 * RoomScale, r\z + 1729.0 * RoomScale, r)
			sc\Angle = 100.0 : sc\Turn = 45.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			
			de.Decals = CreateDecal(DECAL_QUARTZ, r\x - 4858.0 * RoomScale, r\y - 4495.0 * RoomScale, r\z + 1655.0 * RoomScale, 90.0, Rnd(360.0), 0.0, 0.75, 0.8)
			EntityParent(de\OBJ, r\OBJ)
			
			it.Items = CreateItem("Document SCP-409", "paper", r\x - 4105.0 * RoomScale, r\y - 4336.0 * RoomScale, r\z + 2207.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, 0.0, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			If I_005\ChanceToSpawn = 3 Then
				it.Items = CreateItem("SCP-005", "scp005", r\x - 5000.0 * RoomScale, r\y - 4416.0 * RoomScale, r\z + 1728.0 * RoomScale)
				EntityParent(it\Collider, r\OBJ)	
			EndIf
			;[End Block]
		Case "room2_js"
			;[Block]
			d.Doors = CreateDoor(r\x + 288.0 * RoomScale, r\y, r\z + 576.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_0)
			
			sc.SecurityCams = CreateSecurityCam(r\x + 1646.0 * RoomScale, r\y + 435.0 * RoomScale, r\z + 193.0 * RoomScale, r)
			sc\Angle = 30.0 : sc\Turn = 30.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			
			de.Decals = CreateDecal(DECAL_WATER, r\x + 103.0 * RoomScale, r\y + 0.005, r\z + 161.0 * RoomScale, 90.0, Rnd(360.0), 0.0, Rnd(0.8, 1.0), 1.0)
			EntityParent(de\OBJ, r\OBJ)
			
			it.Items = CreateItem("Level 1 Key Card", "key1", r\x + 1715.0 * RoomScale, r\y + 250.0 * RoomScale, r\z + 718.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Origami", "origami", r\x + 1467.0 * RoomScale, r\y + 250.0 * RoomScale, r\z + 961.0 * RoomScale)
			RotateEntity(it\Collider, 0.0, 0.0, 0.0)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("9V Battery", "bat", r\x + 1714.0 * RoomScale, r\y + 250.0 * RoomScale, r\z + 936.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room2_medibay"
			;[Block]
			; ~ Doors
			d.Doors = CreateDoor(r\x - 256.0 * RoomScale, r\y, r\z + 640.0 * RoomScale, 90.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			
			d.Doors = CreateDoor(r\x - 512.0 * RoomScale, r\y, r\z + 378.0 * RoomScale, 0.0, r, False, OFFICE_DOOR)
			
			d.Doors = CreateDoor(r\x - 1104.0 * RoomScale, r\y, r\z + 640.0 * RoomScale, 270.0, r, False, DEFAULT_DOOR, KEY_CARD_3)
			d\Locked = 1 : d\DisableWaypoint = True : d\MTFClose = False
			FreeEntity(d\Buttons[0]) : d\Buttons[0] = 0
			FreeEntity(d\OBJ2) : d\OBJ2 = 0
			
			; ~ Zombie spawnpoint
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x - 820.0 * RoomScale, r\y + 500.0 * RoomScale, r\z - 464.0 * RoomScale)
			
			; ~ Orange duck
			r\Objects[1] = CopyEntity(n_I\NPCModelID[NPC_DUCK_MODEL])
			Tex = LoadTexture_Strict("GFX\npcs\duck(4).png")
			If opt\Atmosphere Then TextureBlend(Tex, 5)
			EntityTexture(r\Objects[1], Tex)
			DeleteSingleTextureEntryFromCache(Tex)
			ScaleEntity(r\Objects[1], 0.07, 0.07, 0.07)
			PositionEntity(r\Objects[1], r\x - 910.0 * RoomScale, r\y + 144.0 * RoomScale, r\z - 778.0 * RoomScale)				
			TurnEntity(r\Objects[1], 6.0, 180.0, 0.0)
			
			For i = 0 To 1
				EntityParent(r\Objects[i], r\OBJ)
			Next
			
			If Rand(2) = 1 Then
				ItemName = "Syringe"
				ItemTempName = "syringe"
			Else
				ItemName = "Syringe"
				ItemTempName = "syringeinf"
			EndIf
			it.Items = CreateItem(ItemName, ItemTempName, r\x - 923.0 * RoomScale, r\y + 100.0 * RoomScale, r\z + 96.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			If Rand(2) = 1 Then
				ItemName = "Syringe"
				ItemTempName = "syringe"
			Else
				ItemName = "Syringe"
				ItemTempName = "syringeinf"
			EndIf
			it.Items = CreateItem(ItemName, ItemTempName, r\x - 907.0 * RoomScale, r\y + 100.0 * RoomScale, r\z + 159.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Compact First Aid Kit", "finefirstaid", r\x - 333.0 * RoomScale, r\y + 192.0 * RoomScale, r\z - 123.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)	
			;[End Block]
		Case "cont1_005"
			;[Block]
			r\RoomDoors.Doors[0] = CreateDoor(r\x, r\y, r\z - 640.0 * RoomScale, 0.0, r, False, DEFAULT_DOOR, KEY_CARD_4)
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x, r\y + 76.0 * RoomScale, r\z - 210.0 * RoomScale)
			
			r\Objects[1] = CreatePivot()
			PositionEntity(r\Objects[1], r\x, r\y + 188.0 * RoomScale, r\z + 185.0 * RoomScale)			
			
			r\Objects[2] = CreatePivot()
			PositionEntity(r\Objects[2], r\x, r\y + 12.0 * RoomScale, r\z + 240.0 * RoomScale)
			
			For i = 0 To 2
				EntityParent(r\Objects[i], r\OBJ)
			Next			
			
			sc.SecurityCams = CreateSecurityCam(r\x, r\y + 415.0 * RoomScale, r\z - 556.0 * RoomScale, r)
			sc\Angle = 0.0 : sc\Turn = 30.0
			TurnEntity(sc\CameraOBJ, 30.0, 0.0, 0.0)
			
			it.Items = CreateItem("Document SCP-005", "paper", r\x + 338.0 * RoomScale, r\y + 152.0 * RoomScale, r\z - 500.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			If I_005\ChanceToSpawn = 1 Then
				it.Items = CreateItem("SCP-005", "scp005", r\x, r\y + 255.0 * RoomScale, r\z - 210.0 * RoomScale)
				EntityParent(it\Collider, r\OBJ)
			ElseIf I_005\ChanceToSpawn = 2
				it.Items = CreateItem("Note from Maynard", "paper", r\x, r\y + 255.0 * RoomScale, r\z - 210.0 * RoomScale)
				EntityParent(it\Collider, r\OBJ)	
			EndIf
			;[End Block]
		Case "room2_ic"
			;[Block]
			d.Doors = CreateDoor(r\x - 896.0 * RoomScale, r\y, r\z, 90.0, r, True, ELEVATOR_DOOR)
			d\Locked = 1 : d\MTFClose = False
			
			r\Objects[0] = CreatePivot()
			PositionEntity(r\Objects[0], r\x - 1200.0 * RoomScale, r\y + 240.0 * RoomScale, r\z, True)
			EntityParent(r\Objects[0], r\OBJ)
			
			it.Items = CreateItem("Level 0 Key Card", "key0", r\x - 1000.0 * RoomScale, r\y + 140.0 * RoomScale, r\z + 165.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			de.Decals = CreateDecal(DECAL_WATER, r\x - 711.0 * RoomScale, r\y + 0.005, r\z + 140.0 * RoomScale, 90.0, Rnd(360.0), 0.0, Rnd(0.8, 1.0), 1.0)
			EntityParent(de\OBJ, r\OBJ)
			
			it.Items = CreateItem("Cup", "cup", r\x - 100.0 * RoomScale, r\y + 230.0 * RoomScale, r\z - 24.0 * RoomScale, 200, 200, 200)
			it\Name = "Cup of Water"
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Empty Cup", "emptycup", r\x + 143.0 * RoomScale, r\y + 100.0 * RoomScale, r\z + 966.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			;[End Block]
		Case "room3_ez"
			;[Block]
			d.Doors = CreateDoor(r\x + 605.0 * RoomScale, r\y, r\z - 234.0 * RoomScale, 0.0, r, False, OFFICE_DOOR)
			
			d.Doors = CreateDoor(r\x - 605.0 * RoomScale, r\y, r\z - 234.0 * RoomScale, 0.0, r, False, OFFICE_DOOR)
			
			r\Objects[0] = LoadMesh_Strict("GFX\map\ez\room3_ez_hb.b3d", r\OBJ)
			EntityPickMode(r\Objects[0], 2)
			EntityType(r\Objects[0], HIT_MAP)
			EntityAlpha(r\Objects[0], 0.0)
			
			sc.SecurityCams = CreateSecurityCam(r\x - 320.0 * RoomScale, r\y + 384.0 * RoomScale, r\z + 512.25 * RoomScale, r)
			sc\Angle = 225.0 : sc\Turn = 45.0
			TurnEntity(sc\CameraOBJ, 20.0, 0.0, 0.0)
			
			it.Items = CreateItem("9V Battery", "bat", r\x - 937.0 * RoomScale, r\y + 260.0 * RoomScale, r\z - 937.0 * RoomScale)
			EntityParent(it\Collider, r\OBJ)
			
			it.Items = CreateItem("Radio Transceiver", "radio", r\x + 712.0 * RoomScale, r\y + 165.0 * RoomScale, r\z - 797.0 * RoomScale)
			it\State = Rnd(100.0)
			EntityParent(it\Collider, r\OBJ)
			
			If Rand(4) = 1 Then
				it.Items = CreateItem("Cup", "cup", r\x + 880.0 * RoomScale, r\y + 100.0 * RoomScale, r\z - 300.0 * RoomScale, 200, 200, 200)
				it\Name = "Cup of Coffee"
				EntityParent(it\Collider, r\OBJ)
			EndIf
			
			If Rand(2) = 1 Then
				it.Items = CreateItem("Quarter", "25ct", r\x - 234.0 * RoomScale, r\y + 30.0 * RoomScale, r\z + 505.0 * RoomScale)
				EntityParent(it\Collider, r\OBJ)
			EndIf
			;[End Block]
	End Select
	
	For lt.LightTemplates = Each LightTemplates
		If lt\RoomTemplate = r\RoomTemplate Then
			Local NewLight% = AddLight(r, r\x + lt\x, r\y + lt\y, r\z + lt\z, lt\lType, lt\Range, lt\R, lt\G, lt\B)
			
			If NewLight <> 0 Then 
				If lt\lType = 3 Then
					RotateEntity(NewLight, lt\Pitch, lt\Yaw, 0.0)
				EndIf
			EndIf
		EndIf
	Next
	
	For ts.TempScreens = Each TempScreens
		If ts\RoomTemplate = r\RoomTemplate Then
			CreateScreen(r\x + ts\x, r\y + ts\y, r\z + ts\z, ts\ImgPath, r)
		EndIf
	Next
	
	For twp.TempWayPoints = Each TempWayPoints
		If twp\RoomTemplate = r\RoomTemplate Then
			CreateWaypoint(r\x + twp\x, r\y + twp\y, r\z + twp\z, Null, r)
		EndIf
	Next
	
	For tp.TempProps = Each TempProps
		If tp\RoomTemplate = r\RoomTemplate Then
			CreateProp(tp\Name, r\x + tp\x, r\y + tp\y, r\z + tp\z, tp\Pitch, tp\Yaw, tp\Roll, tp\ScaleX, tp\ScaleY, tp\ScaleZ, r)
		EndIf
	Next
	
	If r\RoomTemplate\TempTriggerBoxAmount > 0 Then
		r\TriggerBoxAmount = r\RoomTemplate\TempTriggerBoxAmount
		For i = 0 To r\TriggerBoxAmount - 1
			r\TriggerBoxes[i] = New TriggerBox
			r\TriggerBoxes[i]\OBJ = CopyEntity(r\RoomTemplate\TempTriggerBox[i], r\OBJ)
			EntityColor(r\TriggerBoxes[i]\OBJ, 255, 255, 0)
			EntityAlpha(r\TriggerBoxes[i]\OBJ, 0.0)
			r\TriggerBoxes[i]\Name = r\RoomTemplate\TempTriggerBoxName[i]
		Next
	EndIf
	
	For i = 0 To MaxRoomEmitters - 1
		If r\RoomTemplate\TempSoundEmitter[i] <> 0 Then
			r\SoundEmitterOBJ[i] = CreatePivot(r\OBJ)
			PositionEntity(r\SoundEmitterOBJ[i], r\x + r\RoomTemplate\TempSoundEmitterX[i], r\y + r\RoomTemplate\TempSoundEmitterY[i], r\z + r\RoomTemplate\TempSoundEmitterZ[i], True)
			EntityParent(r\SoundEmitterOBJ[i], r\OBJ)
			
			r\SoundEmitter[i] = r\RoomTemplate\TempSoundEmitter[i]
			r\SoundEmitterRange[i] = r\RoomTemplate\TempSoundEmitterRange[i]
		EndIf
	Next
	
	CatchErrors("FillRoom (" + r\RoomTemplate\Name + ")")
End Function