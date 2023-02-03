Const SavePath$ = "Saves\"

Function SaveGame%(File$)
	CatchErrors("Uncaught (SaveGame)")
	
	If (Not me\Playable) Lor me\Zombie Lor me\Terminated Then Return ; ~ Don't save if the player can't move at all
	
	If me\DropSpeed > 0.02 * fps\Factor[0] Lor me\DropSpeed < (-0.02) * fps\Factor[0] Then Return
	
	Local n.NPCs, r.Rooms, do.Doors
	Local x%, y%, i%, Temp%
	
	GameSaved = True
	
	File = SavePath + File
	
	CreateDir(File)
	
	Local f% = WriteFile(File + "\save.cb")
	
	WriteString(f, CurrentTime())
	WriteString(f, CurrentDate())
	
	WriteString(f, VersionNumber)
	
	WriteInt(f, me\PlayTime)
	
	WriteFloat(f, EntityX(me\Collider))
	WriteFloat(f, EntityY(me\Collider))
	WriteFloat(f, EntityZ(me\Collider))
	
	WriteFloat(f, EntityX(me\Head))
	WriteFloat(f, EntityY(me\Head))
	WriteFloat(f, EntityZ(me\Head))
	
	WriteString(f, Str(CODE_DR_MAYNARD))
	WriteString(f, Str(CODE_O5_COUNCIL))
	WriteString(f, Str(CODE_MAINTENANCE_TUNNELS))
	
	WriteFloat(f, EntityPitch(me\Collider))
	WriteFloat(f, EntityYaw(me\Collider))
	
	WriteFloat(f, me\BlinkTimer)
	WriteFloat(f, me\BLINKFREQ)
	WriteFloat(f, me\BlinkEffect)
	WriteFloat(f, me\BlinkEffectTimer)
	
	WriteFloat(f, me\DeathTimer)
	WriteFloat(f, me\BlurTimer)
	WriteFloat(f, me\HealTimer)
	
	WriteByte(f, me\Crouch)
	
	WriteByte(f, I_005\ChanceToSpawn)
	
	WriteByte(f, I_500\Taken)
	
	WriteFloat(f, me\Stamina)
	WriteFloat(f, me\StaminaEffect)
	WriteFloat(f, me\StaminaEffectTimer)
	
	WriteFloat(f, me\EyeStuck)
	WriteFloat(f, me\EyeIrritation)
	
	WriteFloat(f, me\Injuries)
	WriteFloat(f, me\Bloodloss)
	
	WriteFloat(f, me\PrevInjuries)
	WriteFloat(f, me\PrevBloodloss)
	
	WriteString(f, msg\DeathMsg)
	
	For i = 0 To 7
		WriteFloat(f, I_1025\State[i])
	Next
	
	WriteByte(f, me\Funds)
	WriteByte(f, me\UsedMastercard)
	
	WriteFloat(f, me\VomitTimer)
	WriteByte(f, me\Vomit)
	WriteFloat(f, me\CameraShakeTimer)
	WriteFloat(f, I_008\Timer)
	WriteByte(f, I_008\Revert)
	WriteFloat(f, I_409\Timer)
	WriteByte(f, I_409\Revert)
	
	WriteByte(f, I_035\Sad)
	
	For i = SAFE To ESOTERIC
		If SelectedDifficulty = difficulties[i] Then
			WriteByte(f, i)
			
			If i = ESOTERIC Then
				WriteByte(f, SelectedDifficulty\AggressiveNPCs)
				WriteByte(f, SelectedDifficulty\SaveType)
				WriteByte(f, SelectedDifficulty\OtherFactors)
			EndIf
		EndIf
	Next
	WriteByte(f, SelectedDifficulty\InventorySlots)
	
	WriteFloat(f, mon_I\MonitorTimer)
	
	WriteFloat(f, me\Sanity)
	
	WriteFloat(f, wi\GasMaskFogTimer)
	
	WriteByte(f, wi\GasMask)
	WriteByte(f, wi\BallisticVest)
	WriteByte(f, wi\BallisticHelmet)
	WriteByte(f, wi\HazmatSuit)
	WriteByte(f, wi\NightVision)
	WriteByte(f, wi\SCRAMBLE)
	
	WriteByte(f, I_1499\Using)
	WriteFloat(f, I_1499\PrevX)
	WriteFloat(f, I_1499\PrevY)
	WriteFloat(f, I_1499\PrevZ)
	WriteFloat(f, I_1499\x)
	WriteFloat(f, I_1499\y)
	WriteFloat(f, I_1499\z)
	If I_1499\PrevRoom <> Null Then
		WriteFloat(f, I_1499\PrevRoom\x)
		WriteFloat(f, I_1499\PrevRoom\z)
	Else
		WriteFloat(f, 0.0)
		WriteFloat(f, 0.0)
	EndIf
	
	WriteByte(f, chs\SuperMan)
	WriteFloat(f, chs\SuperManTimer)
	
	WriteString(f, RandomSeed)
	
	WriteFloat(f, SecondaryLightOn)
	WriteFloat(f, PrevSecondaryLightOn)
	WriteByte(f, RemoteDoorOn)
	WriteByte(f, SoundTransmission)
	
	For i = 0 To MAXACHIEVEMENTS - 1
		WriteByte(f, achv\Achievement[i])
	Next
	WriteInt(f, me\RefinedItems)
	
	If UsedConsole Then
		WriteInt(f, 100)
	Else
		WriteInt(f, 994)
	EndIf
	WriteFloat(f, opt\CameraFogFar)
	WriteFloat(f, opt\StoredCameraFogFar)
	
	WriteByte(f, I_427\Using)
	WriteFloat(f, I_427\Timer)
	WriteByte(f, I_714\Using)
	WriteByte(f, I_294\Using)
	
	WriteFloat(f, MTFTimer)
	
	WriteFloat(f, TakeOffTimer)
	
	For x = 0 To MapGridSize
		For y = 0 To MapGridSize
			WriteByte(f, CurrMapGrid\Grid[x + (y * MapGridSize)])
			WriteByte(f, CurrMapGrid\Found[x + (y * MapGridSize)])
		Next
	Next
	
	WriteInt(f, 113)
	
	Temp = 0
	For n.NPCs = Each NPCs
		Temp = Temp + 1
	Next
	
	WriteInt(f, Temp)
	For n.NPCs = Each NPCs
		WriteByte(f, n\NPCType)
		WriteFloat(f, EntityX(n\Collider, True))
		WriteFloat(f, EntityY(n\Collider, True))
		WriteFloat(f, EntityZ(n\Collider, True))
		
		WriteFloat(f, EntityPitch(n\Collider))
		WriteFloat(f, EntityYaw(n\Collider))
		WriteFloat(f, EntityRoll(n\Collider))
		
		WriteFloat(f, n\State)
		WriteFloat(f, n\State2)
		WriteFloat(f, n\State3)
		WriteInt(f, n\PrevState)
		
		WriteByte(f, n\Idle)
		WriteFloat(f, n\LastDist)
		WriteInt(f, n\LastSeen)
		
		WriteFloat(f, n\CurrSpeed)
		
		WriteFloat(f, n\Angle)
		
		WriteFloat(f, n\Reload)
		
		WriteInt(f, n\ID)
		If n\Target <> Null Then
			WriteInt(f, n\Target\ID)
		Else
			WriteInt(f, 0)
		EndIf
		
		WriteFloat(f, n\EnemyX)
		WriteFloat(f, n\EnemyY)
		WriteFloat(f, n\EnemyZ)
		
		WriteString(f, n\Texture)
		
		WriteFloat(f, AnimTime(n\OBJ))
		
		WriteByte(f, n\Contained)
		WriteByte(f, n\IsDead)
		WriteFloat(f, n\PathX)
		WriteFloat(f, n\PathZ)
		WriteInt(f, n\HP)
		WriteString(f, n\Model)
		WriteFloat(f, n\ModelScaleX)
		WriteFloat(f, n\ModelScaleY)
		WriteFloat(f, n\ModelScaleZ)
		WriteByte(f, n\HasAsset)
		WriteByte(f, n\TextureID)
		WriteByte(f, n\HideFromNVG)
	Next
	
	WriteInt(f, 632)
	
	WriteByte(f, bk\IsBroken)
	WriteFloat(f, bk\x)
	WriteFloat(f, bk\z)
	
	WriteByte(f, I_Zone\Transition[0])
	WriteByte(f, I_Zone\Transition[1])
	WriteByte(f, I_Zone\HasCustomForest)
	WriteByte(f, I_Zone\HasCustomMT)
	
	Temp = 0
	For r.Rooms = Each Rooms
		Temp = Temp + 1
	Next
	WriteInt(f, Temp)
	For r.Rooms = Each Rooms
		WriteInt(f, r\RoomTemplate\ID)
		WriteInt(f, r\Angle)
		WriteFloat(f, r\x)
		WriteFloat(f, r\y)
		WriteFloat(f, r\z)
		
		WriteByte(f, r\Found)
		WriteInt(f, r\Zone)
		
		If PlayerRoom = r Then
			WriteByte(f, 1)
		Else
			WriteByte(f, 0)
		EndIf
		
		For i = 0 To MaxRoomNPCs - 1
			If r\NPC[i] = Null Then
				WriteInt(f, 0)
			Else
				WriteInt(f, r\NPC[i]\ID)
			EndIf
		Next
		
		For i = 0 To MaxRoomLevers - 1
			If r\RoomLevers[i] = Null Then
				WriteByte(f, 2)
			Else
				If EntityPitch(r\RoomLevers[i]\OBJ, True) > 0.0 Then
					WriteByte(f, 1)
				Else
					WriteByte(f, 0)
				EndIf
			EndIf
		Next
		
		If r\mt = Null Then ; ~ This room doesn't have a grid
			WriteByte(f, 0)
		Else ; ~ This room has a grid
			WriteByte(f, 1)
			For y = 0 To MTGridSize - 1
				For x = 0 To MTGridSize - 1
					WriteByte(f, r\mt\Grid[x + (y * MTGridSize)])
					WriteByte(f, r\mt\Angles[x + (y * MTGridSize)])
				Next
			Next
		EndIf
		
		If r\fr = Null Then ; ~ This room doesn't have a forest
			WriteByte(f, 0)
		Else ; ~ This room has a forest
			If (Not I_Zone\HasCustomForest) Then
				WriteByte(f, 1)
			Else
				WriteByte(f, 2)
			EndIf
			For y = 0 To ForestGridSize - 1
				For x = 0 To ForestGridSize - 1
					WriteByte(f, r\fr\Grid[x + (y * ForestGridSize)])
				Next
			Next
			WriteFloat(f, EntityX(r\fr\Forest_Pivot, True))
			WriteFloat(f, EntityY(r\fr\Forest_Pivot, True))
			WriteFloat(f, EntityZ(r\fr\Forest_Pivot, True))
		EndIf
	Next
	
	WriteInt(f, 954)
	
	Temp = 0
	For do.Doors = Each Doors
		Temp = Temp + 1
	Next
	WriteInt(f, Temp)
	For do.Doors = Each Doors
		WriteFloat(f, EntityX(do\FrameOBJ, True))
		WriteFloat(f, EntityY(do\FrameOBJ, True))
		WriteFloat(f, EntityZ(do\FrameOBJ, True))
		WriteByte(f, do\Open)
		WriteFloat(f, do\OpenState)
		WriteByte(f, do\Locked)
		WriteByte(f, do\AutoClose)
		
		WriteFloat(f, EntityX(do\OBJ, True))
		WriteFloat(f, EntityZ(do\OBJ, True))
		WriteFloat(f, EntityYaw(do\OBJ, True))
		
		If do\OBJ2 <> 0 Then
			WriteFloat(f, EntityX(do\OBJ2, True))
			WriteFloat(f, EntityZ(do\OBJ2, True))
		Else
			WriteFloat(f, 0.0)
			WriteFloat(f, 0.0)
		EndIf
		
		WriteFloat(f, do\Timer)
		WriteFloat(f, do\TimerState)
		
		WriteByte(f, do\IsElevatorDoor)
		WriteByte(f, do\MTFClose)
	Next
	
	WriteInt(f, 1845)
	
	Local de.Decals
	
	Temp = 0
	For de.Decals = Each Decals
		Temp = Temp + 1
	Next
	WriteInt(f, Temp)
	For de.Decals = Each Decals
		WriteInt(f, de\ID)
		
		WriteFloat(f, EntityX(de\OBJ, True))
		WriteFloat(f, EntityY(de\OBJ, True))
		WriteFloat(f, EntityZ(de\OBJ, True))
		
		WriteFloat(f, EntityPitch(de\OBJ, True))
		WriteFloat(f, EntityYaw(de\OBJ, True))
		WriteFloat(f, EntityRoll(de\OBJ, True))
		
		WriteFloat(f, de\Size)
		WriteFloat(f, de\Alpha)
		WriteByte(f, de\FX)
		WriteByte(f, de\BlendMode)
		WriteByte(f, de\R) : WriteByte(f, de\G) : WriteByte(f, de\B)
		
		WriteFloat(f, de\Timer)
		WriteFloat(f, de\LifeTime)
		WriteFloat(f, de\SizeChange)
		WriteFloat(f, de\AlphaChange)
	Next
	
	Local e.Events
	
	Temp = 0
	For e.Events = Each Events
		Temp = Temp + 1
	Next
	WriteInt(f, Temp)
	For e.Events = Each Events
		WriteByte(f, e\EventID)
		WriteFloat(f, e\EventState)
		WriteFloat(f, e\EventState2)
		WriteFloat(f, e\EventState3)
		WriteFloat(f, e\EventState4)
		WriteFloat(f, EntityX(e\room\OBJ))
		WriteFloat(f, EntityZ(e\room\OBJ))
		WriteString(f, e\EventStr)
	Next
	
	Local it.Items
	
	Temp = 0
	For it.Items = Each Items
		Temp = Temp + 1
	Next
	WriteInt(f, Temp)
	For it.Items = Each Items
		WriteString(f, it\ItemTemplate\Name)
		WriteString(f, it\ItemTemplate\TempName)
		
		WriteString(f, it\Name)
		
		WriteFloat(f, EntityX(it\Collider, True))
		WriteFloat(f, EntityY(it\Collider, True))
		WriteFloat(f, EntityZ(it\Collider, True))
		
		WriteByte(f, it\R)
		WriteByte(f, it\G)
		WriteByte(f, it\B)
		WriteFloat(f, it\A)
		
		WriteFloat(f, EntityPitch(it\Collider))
		WriteFloat(f, EntityYaw(it\Collider))
		
		WriteFloat(f, it\State)
		WriteFloat(f, it\State2)
		WriteFloat(f, it\State3)
		WriteByte(f, it\Picked)
		
		If SelectedItem = it Then
			WriteByte(f, 1) 
		Else
			WriteByte(f, 0)
		EndIf
		
		Local ItemFound% = False
		
		For i = 0 To MaxItemAmount - 1
			If Inventory(i) = it Then
				ItemFound = True
				Exit
			EndIf
		Next
		If ItemFound Then
			WriteByte(f, i)
		Else
			WriteByte(f, 66)
		EndIf
		
		If it\ItemTemplate\IsAnim <> 0 Then WriteFloat(f, AnimTime(it\Model))
		WriteByte(f, it\InvSlots)
		WriteInt(f, it\ID)
		If it\ItemTemplate\InvImg = it\InvImg Then
			WriteByte(f, 0)
		Else
			WriteByte(f, 1)
		EndIf
	Next
	
	Temp = 0
	For it.Items = Each Items
		If it\InvSlots > 0 Then Temp = Temp + 1
	Next
	
	WriteInt(f, Temp)
	
	For it.Items = Each Items
		If it\InvSlots > 0 Then
			WriteInt(f, it\ID)
			For i = 0 To it\InvSlots - 1
				If it\SecondInv[i] <> Null Then
					WriteInt(f, it\SecondInv[i]\ID)
				Else
					WriteInt(f, -1)
				EndIf
			Next
		EndIf
	Next
	
	Local itt.ItemTemplates
	
	For itt.ItemTemplates = Each ItemTemplates
		WriteByte(f, itt\Found)
	Next

	WriteInt(f, me\EscapeTimer)
	
	CloseFile(f)
	
	If (Not MenuOpen) And (Not MainMenuOpen) Then
		If SelectedDifficulty\SaveType = SAVE_ON_SCREENS Then
			PlaySound_Strict(LoadTempSound("SFX\General\Save2.ogg"))
		Else
			PlaySound_Strict(LoadTempSound("SFX\General\Save1.ogg"))
			as\Timer = 70.0 * 120.0
		EndIf
		CreateHintMsg(GetLocalString("save", "saved"))
	EndIf
	
	CatchErrors("SaveGame")
End Function

Function LoadGame%(File$)
	CatchErrors("Uncaught (LoadGame)")
	
	Local r.Rooms, n.NPCs, do.Doors, rt.RoomTemplates
	Local x#, y#, z#, i%, j%, Temp%, StrTemp$, Tex%, ID%
	Local f% = ReadFile_Strict(SavePath + File + "\save.cb")
	
	me\DropSpeed = 0.0
	
	GameSaved = True
	
	StrTemp = ReadString(f)
	StrTemp = ReadString(f)
	
	StrTemp = ReadString(f)
	If StrTemp <> VersionNumber Then RuntimeError(Format(Format(GetLocalString("save", "imcompatible"), StrTemp, "{0}"), VersionNumber, "{1}"))
	
	me\PlayTime = ReadInt(f)
	
	x = ReadFloat(f)
	y = ReadFloat(f)
	z = ReadFloat(f)
	PositionEntity(me\Collider, x, y + 0.05, z)
	ResetEntity(me\Collider)
	
	x = ReadFloat(f)
	y = ReadFloat(f)
	z = ReadFloat(f)
	PositionEntity(me\Head, x, y + 0.05, z)
	ResetEntity(me\Head)
	
	CODE_DR_MAYNARD = Int(ReadString(f))
	CODE_O5_COUNCIL = Int(ReadString(f))
	CODE_MAINTENANCE_TUNNELS = Int(ReadString(f))
	
	x = ReadFloat(f)
	y = ReadFloat(f)
	RotateEntity(me\Collider, x, y, 0.0)
	
	me\BlinkTimer = ReadFloat(f)
	me\BLINKFREQ = ReadFloat(f)
	me\BlinkEffect = ReadFloat(f)
	me\BlinkEffectTimer = ReadFloat(f)
	
	me\DeathTimer = ReadFloat(f)
	me\BlurTimer = ReadFloat(f)
	me\HealTimer = ReadFloat(f)
	
	me\Crouch = ReadByte(f)
	
	I_005\ChanceToSpawn = ReadByte(f)
	
	I_500\Taken = ReadByte(f)
	
	me\Stamina = ReadFloat(f)
	me\StaminaEffect = ReadFloat(f)
	me\StaminaEffectTimer = ReadFloat(f)
	
	me\EyeStuck = ReadFloat(f)
	me\EyeIrritation = ReadFloat(f)
	
	me\Injuries = ReadFloat(f)
	me\Bloodloss = ReadFloat(f)
	
	me\PrevInjuries = ReadFloat(f)
	me\PrevBloodloss = ReadFloat(f)
	
	msg\DeathMsg = ReadString(f)
	
	For i = 0 To 7
		I_1025\State[i] = ReadFloat(f)
	Next
	
	me\Funds = ReadByte(f)
	me\UsedMastercard = ReadByte(f)
	
	me\VomitTimer = ReadFloat(f)
	me\Vomit = ReadByte(f)
	me\CameraShakeTimer = ReadFloat(f)
	I_008\Timer = ReadFloat(f)
	I_008\Revert = ReadByte(f)
	I_409\Timer = ReadFloat(f)
	I_409\Revert = ReadByte(f)
	
	I_035\Sad = ReadByte(f)
	
	Local DifficultyIndex% = ReadByte(f)
	
	SelectedDifficulty = difficulties[DifficultyIndex]
	If DifficultyIndex = ESOTERIC Then
		SelectedDifficulty\AggressiveNPCs = ReadByte(f)
		SelectedDifficulty\SaveType = ReadByte(f)
		SelectedDifficulty\OtherFactors = ReadByte(f)
	EndIf
	SelectedDifficulty\InventorySlots = ReadByte(f)
	
	MaxItemAmount = SelectedDifficulty\InventorySlots
	Dim Inventory.Items(MaxItemAmount)
	
	mon_I\MonitorTimer = ReadFloat(f)
	
	me\Sanity = ReadFloat(f)
	
	wi\GasMaskFogTimer = ReadFloat(f)
	
	wi\GasMask = ReadByte(f)
	wi\BallisticVest = ReadByte(f)
	wi\BallisticHelmet = ReadByte(f)
	wi\HazmatSuit = ReadByte(f)
	wi\NightVision = ReadByte(f)
	wi\SCRAMBLE = ReadByte(f)
	
	I_1499\Using = ReadByte(f)
	I_1499\PrevX = ReadFloat(f)
	I_1499\PrevY = ReadFloat(f)
	I_1499\PrevZ = ReadFloat(f)
	I_1499\x = ReadFloat(f)
	I_1499\y = ReadFloat(f)
	I_1499\z = ReadFloat(f)
	
	Local r1499_x# = ReadFloat(f)
	Local r1499_z# = ReadFloat(f)
	
	chs\SuperMan = ReadByte(f)
	chs\SuperManTimer = ReadFloat(f)
	
	RandomSeed = ReadString(f)
	
	SecondaryLightOn = ReadFloat(f)
	PrevSecondaryLightOn = ReadFloat(f)
	RemoteDoorOn = ReadByte(f)
	SoundTransmission = ReadByte(f)
	
	For i = 0 To MAXACHIEVEMENTS - 1
		achv\Achievement[i] = ReadByte(f)
	Next
	me\RefinedItems = ReadInt(f)
	
	If ReadInt(f) <> 994 Then UsedConsole = True
	
	opt\CameraFogFar = ReadFloat(f)
	opt\StoredCameraFogFar = ReadFloat(f)
	If opt\CameraFogFar = 0.0 Then opt\CameraFogFar = 6.0
	
	I_427\Using = ReadByte(f)
	I_427\Timer = ReadFloat(f)
	I_714\Using = ReadByte(f)
	I_294\Using = ReadByte(f)
	
	MTFTimer = ReadFloat(f)
	
	TakeOffTimer = ReadFloat(f)
	
	CurrMapGrid.MapGrid = New MapGrid
	For x = 0 To MapGridSize
		For y = 0 To MapGridSize
			CurrMapGrid\Grid[x + (y * MapGridSize)] = ReadByte(f)
			CurrMapGrid\Found[x + (y * MapGridSize)] = ReadByte(f)
		Next
	Next
	
	If ReadInt(f) <> 113 Then RuntimeError(GetLocalString("save", "corrupted_1"))
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		Local NPCType% = ReadByte(f)
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		n.NPCs = CreateNPC(NPCType, x, y, z)
		Select NPCType
			Case NPCType173
				;[Block]
				n_I\Curr173 = n
				;[End Block]
			Case NPCType106
				;[Block]
				n_I\Curr106 = n
				;[End Block]
			Case NPCType096
				;[Block]
				n_I\Curr096 = n
				;[End Block]
			Case NPCType513_1
				;[Block]
				n_I\Curr513_1 = n
				;[End Block]
			Case NPCType049
				;[Block]
				n_I\Curr049 = n
				;[End Block]
		End Select
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		RotateEntity(n\Collider, x, y, z)
		
		n\State = ReadFloat(f)
		n\State2 = ReadFloat(f)
		n\State3 = ReadFloat(f)
		n\PrevState = ReadInt(f)
		
		n\Idle = ReadByte(f)
		n\LastDist = ReadFloat(f)
		n\LastSeen = ReadInt(f)
		
		n\CurrSpeed = ReadFloat(f)
		n\Angle = ReadFloat(f)
		n\Reload = ReadFloat(f)
		
		ForceSetNPCID(n, ReadInt(f))
		n\TargetID = ReadInt(f)
		
		n\EnemyX = ReadFloat(f)
		n\EnemyY = ReadFloat(f)
		n\EnemyZ = ReadFloat(f)
		
		n\Texture = ReadString(f)
		If n\Texture <> "" Then
			Tex = LoadTexture_Strict(n\Texture)
			If opt\Atmosphere Then TextureBlend(Tex, 5)
			EntityTexture(n\OBJ, Tex)
		EndIf
		
		Local Frame# = ReadFloat(f)
		
		Select NPCType
			Case NPCType106, NPCTypeD, NPCType096, NPCTypeMTF, NPCTypeGuard, NPCType049, NPCType049_2, NPCTypeClerk, NPCType008_1
				;[Block]
				SetAnimTime(n\OBJ, Frame)
				;[End Block]
		End Select
		
		n\Frame = Frame
		n\Contained = ReadByte(f)
		n\IsDead = ReadByte(f)
		n\PathX = ReadFloat(f)
		n\PathZ = ReadFloat(f)
		n\HP = ReadInt(f)
		n\Model = ReadString(f)
		n\ModelScaleX = ReadFloat(f)
		n\ModelScaleY = ReadFloat(f)
		n\ModelScaleZ = ReadFloat(f)
		If n\Model <> "" Then
			FreeEntity(n\OBJ)
			n\OBJ = LoadAnimMesh_Strict(n\Model)
			ScaleEntity(n\OBJ, n\ModelScaleX, n\ModelScaleY, n\ModelScaleZ)
			SetAnimTime(n\OBJ, Frame)
		EndIf
		n\HasAsset = ReadByte(f)
		If n\HasAsset Then CreateNPCAsset(n)
		n\TextureID = ReadByte(f)
		If n\TextureID > 0 Then
			ChangeNPCTextureID(n, n\TextureID - 1)
			SetAnimTime(n\OBJ, Frame)
		EndIf
		n\HideFromNVG = ReadByte(f)
		
		If n\TargetID <> 0 Then
			Local n2.NPCs
			
			For n2.NPCs = Each NPCs
				If n2 <> n Then
					If n2\ID = n\TargetID Then n\Target = n2
				EndIf
			Next
		EndIf
	Next
	
	If ReadInt(f) <> 632 Then RuntimeError(GetLocalString("save", "corrupted_2"))
	
	bk\IsBroken = ReadByte(f)
	bk\x = ReadFloat(f)
	bk\z = ReadFloat(f)
	
	I_Zone\Transition[0] = ReadByte(f)
	I_Zone\Transition[1] = ReadByte(f)
	I_Zone\HasCustomForest = ReadByte(f)
	I_Zone\HasCustomMT = ReadByte(f)
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		Local RoomTemplateID% = ReadInt(f)
		Local Angle% = ReadInt(f)
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		Local Found% = ReadByte(f)
		Local Level% = ReadInt(f)
		
		Local Temp2% = ReadByte(f)
		
		Angle = WrapAngle(Angle)
		
		For rt.RoomTemplates = Each RoomTemplates
			If rt\ID = RoomTemplateID Then
				r.Rooms = CreateRoom(Level, rt\Shape, x, y, z, rt\Name)
				TurnEntity(r\OBJ, 0.0, Angle, 0.0)
				r\Angle = Angle
				SetupTriggerBoxes(r)
				r\Found = Found
				Exit
			EndIf
		Next
		
		If Temp2 = 1 Then PlayerRoom = r
		
		For x = 0 To MaxRoomNPCs - 1
			ID = ReadInt(f)
			If ID > 0 Then
				For n.NPCs = Each NPCs
					If n\ID = ID Then
						r\NPC[x] = n
						Exit
					EndIf
				Next
			EndIf
		Next
		
		For x = 0 To MaxRoomLevers - 1
			ID = ReadByte(f)
			If ID = 1 Then
				RotateEntity(r\RoomLevers[x]\OBJ, 80.0, EntityYaw(r\RoomLevers[x]\OBJ), 0.0)
			ElseIf ID = 0
				RotateEntity(r\RoomLevers[x]\OBJ, -80.0, EntityYaw(r\RoomLevers[x]\OBJ), 0.0)
			EndIf
		Next
		
		If ReadByte(f) = 1 Then ; ~ This room has a grid
			If r\mt <> Null Then ; ~ Remove the old grid content
				For x = 0 To MTGridSize - 1
					For y = 0 To MTGridSize - 1
						If r\mt\Entities[x + (y * MTGridSize)] <> 0 Then FreeEntity(r\mt\Entities[x + (y * MTGridSize)]) : r\mt\Entities[x + (y * MTGridSize)] = 0
						If r\mt\waypoints[x + (y * MTGridSize)] <> Null Then RemoveWaypoint(r\mt\waypoints[x + (y * MTGridSize)]) : r\mt\waypoints[x + (y * MTGridSize)] = Null
					Next
				Next
				For x = 0 To 6
					If r\mt\Meshes[x] <> 0 Then FreeEntity(r\mt\Meshes[x]) : r\mt\Meshes[x] = 0
				Next
				Delete(r\mt) : r\mt = Null
			EndIf
			r\mt.MTGrid = New MTGrid
			For y = 0 To MTGridSize - 1
				For x = 0 To MTGridSize - 1
					r\mt\Grid[x + (y * MTGridSize)] = ReadByte(f)
					r\mt\Angles[x + (y * MTGridSize)] = ReadByte(f)
					; ~ Get only the necessary data, make the event handle the meshes and waypoints separately
				Next
			Next
		EndIf
		
		Local HasForest% = ReadByte(f)
		
		If HasForest > 0 Then ; ~ This room has a forest
			If r\fr <> Null Then ; ~ Remove the old forest
				DestroyForest(r\fr)
			Else
				r\fr.Forest = New Forest
			EndIf
			For y = 0 To ForestGridSize - 1
				Local sssss$ = ""
				
				For x = 0 To ForestGridSize - 1
					r\fr\Grid[x + (y * ForestGridSize)] = ReadByte(f)
					sssss = sssss + Str(r\fr\Grid[x + (y * ForestGridSize)])
				Next
			Next
			
			Local lX# = ReadFloat(f)
			Local lY# = ReadFloat(f)
			Local lZ# = ReadFloat(f)
			
			If HasForest = 1 Then
				PlaceForest(r\fr, lX, lY, lZ, r)
			Else
				PlaceMapCreatorForest(r\fr, lX, lY, lZ, r)
			EndIf
		ElseIf r\fr <> Null ; ~ Remove the old forest
			DestroyForest(r\fr)
			Delete(r\fr) : r\fr = Null
		EndIf
		
		If r\x = r1499_x And r\z = r1499_z Then I_1499\PrevRoom = r
	Next
	
	If ReadInt(f) <> 954 Then RuntimeError(GetLocalString("save", "corrupted_3"))
	
	Local Zone%, ShouldSpawnDoor%
	
	For y = MapGridSize To 0 Step -1
		If y < I_Zone\Transition[1] - (SelectedCustomMap = Null) Then
			Zone = 3
		ElseIf y >= I_Zone\Transition[1] - (SelectedCustomMap = Null) And y < I_Zone\Transition[0] - (SelectedCustomMap = Null)
			Zone = 2
		Else
			Zone = 1
		EndIf
		For x = MapGridSize To 0 Step -1
			If CurrMapGrid\Grid[x + (y * MapGridSize)] > MapGrid_NoTile Then
				For r.Rooms = Each Rooms
					r\Angle = WrapAngle(r\Angle)
					If Int(r\x / RoomSpacing) = x And Int(r\z / RoomSpacing) = y Then
						ShouldSpawnDoor = False
						Select r\RoomTemplate\Shape
							Case ROOM1
								;[Block]
								If r\Angle = 90.0
									ShouldSpawnDoor = True
								EndIf
								;[End Block]
							Case ROOM2
								;[Block]
								If r\Angle = 90.0 Lor r\Angle = 270.0
									ShouldSpawnDoor = True
								EndIf
								;[End Block]
							Case ROOM2C
								;[Block]
								If r\Angle = 0.0 Lor r\Angle = 90.0
									ShouldSpawnDoor = True
								EndIf
								;[End Block]
							Case ROOM3
								;[Block]
								If r\Angle = 0.0 Lor r\Angle = 180.0 Lor r\Angle = 90.0
									ShouldSpawnDoor = True
								EndIf
								;[End Block]
							Default
								;[Block]
								ShouldSpawnDoor = True
								;[End Block]
						End Select
						If ShouldSpawnDoor Then
							If x + 1 < MapGridSize + 1 Then
								If CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)] > MapGrid_NoTile Then
									do.Doors = CreateDoor(Float(x) * RoomSpacing + (RoomSpacing / 2.0), 0.0, Float(y) * RoomSpacing, 90.0, r, Max(Rand(-3, 1), 0.0), ((Zone - 1) Mod 2) * 2)
									r\AdjDoor[0] = do
								EndIf
							EndIf
						EndIf
						
						ShouldSpawnDoor = False
						Select r\RoomTemplate\Shape
							Case ROOM1
								;[Block]
								If r\Angle = 180.0
									ShouldSpawnDoor = True
								EndIf
								;[End Block]
							Case ROOM2
								;[Block]
								If r\Angle = 0.0 Lor r\Angle = 180.0
									ShouldSpawnDoor = True
								EndIf
								;[End Block]
							Case ROOM2C
								;[Block]
								If r\Angle = 180.0 Lor r\Angle = 90.0
									ShouldSpawnDoor = True
								EndIf
								;[End Block]
							Case ROOM3
								;[Block]
								If r\Angle = 180.0 Lor r\Angle = 90.0 Lor r\Angle = 270.0
									ShouldSpawnDoor = True
								EndIf
								;[End Block]
							Default
								;[Block]
								ShouldSpawnDoor = True
								;[End Block]
						End Select
						If ShouldSpawnDoor
							If y + 1 < MapGridSize + 1 Then
								If CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)] > MapGrid_NoTile Then
									do.Doors = CreateDoor(Float(x) * RoomSpacing, 0.0, Float(y) * RoomSpacing + (RoomSpacing / 2.0), 0.0, r, Max(Rand(-3, 1), 0.0), ((Zone - 1) Mod 2) * 2)
									r\AdjDoor[3] = do
								EndIf
							EndIf
						EndIf
						Exit
					EndIf
				Next
			EndIf
		Next
	Next
	
	Temp = ReadInt(f)
	
	For i = 1 To Temp
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		Local Open% = ReadByte(f)
		Local OpenState# = ReadFloat(f)
		Local Locked% = ReadByte(f)
		Local AutoClose% = ReadByte(f)
		
		Local OBJX# = ReadFloat(f)
		Local OBJZ# = ReadFloat(f)
		Local OBJYaw# = ReadFloat(f)
		
		Local OBJ2X# = ReadFloat(f)
		Local OBJ2Z# = ReadFloat(f)
		
		Local Timer# = ReadFloat(f)
		Local TimerState# = ReadFloat(f)
		
		Local IsElevDoor = ReadByte(f)
		Local MTFClose = ReadByte(f)
		
		For do.Doors = Each Doors
			If EntityX(do\FrameOBJ, True) = x And EntityY(do\FrameOBJ, True) = y And EntityZ(do\FrameOBJ, True) = z Then
				do\Open = Open
				do\OpenState = OpenState
				do\Locked = Locked
				do\AutoClose = AutoClose
				do\Timer = Timer
				do\TimerState = TimerState
				do\IsElevatorDoor = IsElevDoor
				do\MTFClose = MTFClose
				
				PositionEntity(do\OBJ, OBJX, y, OBJZ, True)
				RotateEntity(do\OBJ, 0.0, OBJYaw, 0.0, True)
				If do\OBJ2 <> 0 Then PositionEntity(do\OBJ2, OBJ2X, y, OBJ2Z, True)
				Exit
			EndIf
		Next
	Next
	
	If ReadInt(f) <> 1845 Then RuntimeError(GetLocalString("save", "corrupted_4"))
	
	Local de.Decals
	
	For de.Decals = Each Decals
		FreeEntity(de\OBJ) : de\OBJ = 0
		Delete(de)
	Next
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		ID = ReadInt(f)
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		Local Pitch# = ReadFloat(f)
		Local Yaw# = ReadFloat(f)
		Local Roll# = ReadFloat(f)
		
		de.Decals = CreateDecal(ID, x, y, z, Pitch, Yaw, Roll)
		
		Local Size# = ReadFloat(f)
		Local Alpha# = ReadFloat(f)
		Local FX% = ReadByte(f)
		Local BlendMode% = ReadByte(f)
		Local Red% = ReadByte(f), Green% = ReadByte(f), Blue% = ReadByte(f)
		
		Local DecalTimer# = ReadFloat(f)
		Local LifeTime# = ReadFloat(f)
		Local SizeChange# = ReadFloat(f)
		Local AlphaChange# = ReadFloat(f)
		
		For de.Decals = Each Decals
			If EntityX(de\OBJ, True) = x And EntityY(de\OBJ, True) = y And EntityZ(de\OBJ, True) = z Then
				de\Size = Size
				de\Alpha = Alpha
				de\FX = FX
				de\BlendMode = BlendMode
				de\R = Red : de\G = Green : de\B = Blue
				de\Timer = DecalTimer
				de\LifeTime = LifeTime
				de\SizeChange = SizeChange
				de\AlphaChange = AlphaChange
				
				ScaleSprite(de\OBJ, Size, Size)
				EntityAlpha(de\OBJ, Alpha)
				EntityFX(de\OBJ, FX)
				EntityBlend(de\OBJ, BlendMode)
				If Red <> 0 Lor Green <> 0 Lor Blue <> 0 Then EntityColor(de\OBJ, Red, Green, Blue)
				Exit
			EndIf
		Next
	Next
	
	Local e.Events
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		e.Events = New Events
		
		e\EventID = ReadByte(f)
		e\EventState = ReadFloat(f)
		e\EventState2 = ReadFloat(f)
		e\EventState3 = ReadFloat(f)
		e\EventState4 = ReadFloat(f)
		x = ReadFloat(f)
		z = ReadFloat(f)
		For r.Rooms = Each Rooms
			If EntityX(r\OBJ) = x And EntityZ(r\OBJ) = z Then
				e\room = r
				Exit
			EndIf
		Next
		e\EventStr = ReadString(f)
		FindForestEvent(e)
		
		Select e\EventID
			Case e_dimension_1499
				;[Block]
				If e\EventState > 0.0 Then
					e\EventState = 0.0
					e\EventStr = ""
					HideChunks()
					DeleteChunks()
					For n.NPCs = Each NPCs
						If n\NPCType = NPCType1499_1 Then
							If n\InFacility = 0 Then RemoveNPC(n)
						EndIf
					Next
					
					Local du.Dummy1499_1
					
					For du.Dummy1499_1 = Each Dummy1499_1
						Delete(du)
					Next
				EndIf
				;[End Block]
			Case e_cont2_860_1, e_cont1_205
				;[Block]
				e\EventStr = ""
				;[End Block]
			Case e_cont1_106
				;[Block]
				If e\EventState2 = 0.0 Then PositionEntity(e\room\Objects[6], EntityX(e\room\Objects[6], True), (-1280.0) * RoomScale, EntityZ(e\room\Objects[6], True), True)
				;[End Block]
		End Select
	Next
	
	Local it.Items
	
	For it.Items = Each Items
		RemoveItem(it)
	Next
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		Local IttName$ = ReadString(f)
		Local TempName$ = ReadString(f)
		Local Name$ = ReadString(f)
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		Red = ReadByte(f)
		Green = ReadByte(f)
		Blue = ReadByte(f)
		
		Local A% = ReadFloat(f)
		
		it.Items = CreateItem(IttName, TempName, x, y, z, Red, Green, Blue, A)
		it\Name = Name
		
		EntityType(it\Collider, HIT_ITEM)
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		RotateEntity(it\Collider, x, y, 0.0)
		
		it\State = ReadFloat(f)
		it\State2 = ReadFloat(f)
		it\State3 = ReadFloat(f)
		it\Picked = ReadByte(f)
		If it\Picked Then HideEntity(it\Collider)
		
		Local nt% = ReadByte(f)
		
		If nt = True Then SelectedItem = it
		
		nt = ReadByte(f)
		If nt < 66 Then
			Inventory(nt) = it
			ItemAmount = ItemAmount + 1
		EndIf
		
		Local itt.ItemTemplates
		
		For itt.ItemTemplates = Each ItemTemplates
			If itt\TempName = TempName And itt\Name = IttName Then
				If itt\IsAnim <> 0 Then
					SetAnimTime(it\Model, ReadFloat(f))
					Exit
				EndIf
			EndIf
		Next
		it\InvSlots = ReadByte(f)
		it\ID = ReadInt(f)
		
		If it\ID > LastItemID Then LastItemID = it\ID
		
		If ReadByte(f) = 0 Then
			it\InvImg = it\ItemTemplate\InvImg
		Else
			it\InvImg = it\ItemTemplate\InvImg2
		EndIf
	Next
	
	Local o_i%
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		o_i = ReadInt(f)
		
		Local ij.Items
		
		For ij.Items = Each Items
			If ij\ID = o_i Then
				it.Items = ij
				Exit
			EndIf
		Next
		For j = 0 To it\InvSlots - 1
			o_i = ReadInt(f)
			If o_i <> -1 Then
				For ij.Items = Each Items
					If ij\ID = o_i Then
						it\SecondInv[j] = ij
						Exit
					EndIf
				Next
			EndIf
		Next
	Next
	
	For itt.ItemTemplates = Each ItemTemplates
		itt\Found = ReadByte(f)
	Next

	me\EscapeTimer = ReadInt(f)
	
	Local Dist#, Dist2#
	
	For do.Doors = Each Doors
		If do\room <> Null Then
			Dist = 400.0
			
			Local closestroom.Rooms
			
			For r.Rooms = Each Rooms
				Dist2 = EntityDistanceSquared(r\OBJ, do\OBJ)
				If Dist2 < Dist Then
					Dist = Dist2
					closestroom = r.Rooms
				EndIf
			Next
			do\room = closestroom
		EndIf
	Next
	
	CloseFile(f)
	
	For r.Rooms = Each Rooms
		For i = 0 To MaxRoomAdjacents - 1
			r\Adjacent[i] = Null
		Next
		
		Local r2.Rooms
		
		For r2.Rooms = Each Rooms
			If r <> r2 Then
				If r2\z = r\z Then
					If r2\x = r\x + 8.0 Then
						r\Adjacent[0] = r2
						If r\AdjDoor[0] = Null Then r\AdjDoor[0] = r2\AdjDoor[2]
					ElseIf r2\x = r\x - 8.0
						r\Adjacent[2] = r2
						If r\AdjDoor[2] = Null Then r\AdjDoor[2] = r2\AdjDoor[0]
					EndIf
				ElseIf r2\x = r\x
					If r2\z = r\z - 8.0 Then
						r\Adjacent[1] = r2
						If r\AdjDoor[1] = Null Then r\AdjDoor[1] = r2\AdjDoor[3]
					ElseIf r2\z = r\z + 8.0
						r\Adjacent[3] = r2
						If r\AdjDoor[3] = Null Then r\AdjDoor[3] = r2\AdjDoor[1]
					EndIf
				EndIf
			EndIf
			If r\Adjacent[0] <> Null And r\Adjacent[1] <> Null And r\Adjacent[2] <> Null And r\Adjacent[3] <> Null Then Exit
		Next
		
		For do.Doors = Each Doors
			If do\KeyCard = 0 And do\Code = "" Then
				If EntityZ(do\FrameOBJ, True) = r\z Then
					If EntityX(do\FrameOBJ, True) = r\x + 4.0 Then
						r\AdjDoor[0] = do
					ElseIf EntityX(do\FrameOBJ, True) = r\x - 4.0
						r\AdjDoor[2] = do
					EndIf
				ElseIf EntityX(do\FrameOBJ, True) = r\x
					If EntityZ(do\FrameOBJ, True) = r\z + 4.0 Then
						r\AdjDoor[3] = do
					ElseIf EntityZ(do\FrameOBJ, True) = r\z - 4.0
						r\AdjDoor[1] = do
					EndIf
				EndIf
			EndIf
		Next
	Next
	
	If PlayerRoom\RoomTemplate\Name = "dimension_1499" Then
		me\BlinkTimer = -1.0
		ShouldEntitiesFall = False
		TeleportToRoom(I_1499\PrevRoom)
	EndIf
	
	If me\Collider <> 0 Then
		If PlayerRoom <> Null Then ShowEntity(PlayerRoom\OBJ)
		ShowEntity(me\Collider)
		TeleportEntity(me\Collider, EntityX(me\Collider), EntityY(me\Collider) + 0.5, EntityZ(me\Collider), 0.3, True)
		If PlayerRoom <> Null Then HideEntity(PlayerRoom\OBJ)
	EndIf
	
	UpdateTimer = 0.0
	
	CatchErrors("LoadGame")
End Function

Function LoadGameQuick%(File$)
	CatchErrors("Uncaught (LoadGameQuick)")
	
	Local r.Rooms, n.NPCs, do.Doors
	Local x#, y#, z#, i%, j%, Temp%, StrTemp$, ID%, Tex%
	Local SF%, b%, t1%
	Local Player_X#, Player_Y#, Player_Z#
	Local f% = ReadFile_Strict(SavePath + File + "\save.cb")
	
	GameSaved = True
	me\Zombie = False
	me\Deaf = False
	me\DeafTimer = 0.0
	me\Playable = True
	msg\Txt = ""
	msg\Timer = 0.0
	msg\HintTxt = ""
	msg\HintTimer = 0.0
	me\SelectedEnding = -1
	
	PositionEntity(me\Collider, 0.0, 1000.0, 0.0, True)
	ResetEntity(me\Collider)
	
	StrTemp = ReadString(f)
	StrTemp = ReadString(f)
	
	StrTemp = ReadString(f)
	If StrTemp <> VersionNumber Then RuntimeError(Format(Format(GetLocalString("save", "imcompatible"), StrTemp, "{0}"), VersionNumber, "{1}"))
	
	me\PlayTime = ReadInt(f)
	
	me\DropSpeed = -0.1
	me\HeadDropSpeed = 0.0
	me\CurrSpeed = 0.0
	
	me\HeartBeatVolume = 0.0
	
	me\CameraShake = 0.0
	me\BigCameraShake = 0.0
	me\Shake = 0.0
	me\LightFlash = 0.0
	me\BlurTimer = 0.0
	
	me\Terminated = False
	me\FallTimer = 0.0
	MenuOpen = False
	
	ClearConsole()
	ClearCheats()
	WireFrameState = 0
	WireFrame(0)
	
	HideEntity(me\Collider)
	
	x = ReadFloat(f)
	y = ReadFloat(f)
	z = ReadFloat(f)
	PositionEntity(me\Collider, x, y + 0.05, z)
	
	ShowEntity(me\Collider)
	
	x = ReadFloat(f)
	y = ReadFloat(f)
	z = ReadFloat(f)
	PositionEntity(me\Head, x, y + 0.05, z)
	ResetEntity(me\Head)
	
	CODE_DR_MAYNARD = Int(ReadString(f))
	CODE_O5_COUNCIL = Int(ReadString(f))
	CODE_MAINTENANCE_TUNNELS = Int(ReadString(f))
	
	x = ReadFloat(f)
	y = ReadFloat(f)
	RotateEntity(me\Collider, x, y, 0.0)
	
	me\BlinkTimer = ReadFloat(f)
	me\BLINKFREQ = ReadFloat(f)
	me\BlinkEffect = ReadFloat(f)
	me\BlinkEffectTimer = ReadFloat(f)
	
	me\DeathTimer = ReadFloat(f)
	me\BlurTimer = ReadFloat(f)
	me\HealTimer = ReadFloat(f)
	
	me\Crouch = ReadByte(f)
	
	I_005\ChanceToSpawn = ReadByte(f)
	
	I_500\Taken = ReadByte(f)
	
	me\Stamina = ReadFloat(f)
	me\StaminaEffect = ReadFloat(f)
	me\StaminaEffectTimer = ReadFloat(f)
	
	me\EyeStuck = ReadFloat(f)
	me\EyeIrritation = ReadFloat(f)
	
	me\Injuries = ReadFloat(f)
	me\Bloodloss = ReadFloat(f)
	
	me\PrevInjuries = ReadFloat(f)
	me\PrevBloodloss = ReadFloat(f)
	
	msg\DeathMsg = ReadString(f)
	
	For i = 0 To 7
		I_1025\State[i] = ReadFloat(f)
	Next
	
	me\Funds = ReadByte(f)
	me\UsedMastercard = ReadByte(f)
	
	me\VomitTimer = ReadFloat(f)
	me\Vomit = ReadByte(f)
	me\CameraShakeTimer = ReadFloat(f)
	I_008\Timer = ReadFloat(f)
	I_008\Revert = ReadByte(f)
	I_409\Timer = ReadFloat(f)
	I_409\Revert = ReadByte(f)
	
	I_035\Sad = ReadByte(f)
	
	Local DifficultyIndex% = ReadByte(f)
	
	SelectedDifficulty = difficulties[DifficultyIndex]
	If DifficultyIndex = ESOTERIC Then
		SelectedDifficulty\AggressiveNPCs = ReadByte(f)
		SelectedDifficulty\SaveType = ReadByte(f)
		SelectedDifficulty\OtherFactors = ReadByte(f)
	EndIf
	SelectedDifficulty\InventorySlots = ReadByte(f)
	
	MaxItemAmount = SelectedDifficulty\InventorySlots
	Dim Inventory.Items(MaxItemAmount)
	
	mon_I\MonitorTimer = ReadFloat(f)
	
	me\Sanity = ReadFloat(f)
	
	wi\GasMaskFogTimer = ReadFloat(f)
	
	wi\GasMask = ReadByte(f)
	wi\BallisticVest = ReadByte(f)
	wi\BallisticHelmet = ReadByte(f)
	wi\HazmatSuit = ReadByte(f)
	wi\NightVision = ReadByte(f)
	wi\SCRAMBLE = ReadByte(f)
	
	I_1499\Using = ReadByte(f)
	I_1499\PrevX = ReadFloat(f)
	I_1499\PrevY = ReadFloat(f)
	I_1499\PrevZ = ReadFloat(f)
	I_1499\x = ReadFloat(f)
	I_1499\y = ReadFloat(f)
	I_1499\z = ReadFloat(f)
	
	Local r1499_x# = ReadFloat(f)
	Local r1499_z# = ReadFloat(f)
	
	chs\SuperMan = ReadByte(f)
	chs\SuperManTimer = ReadFloat(f)
	
	RandomSeed = ReadString(f)
	
	SecondaryLightOn = ReadFloat(f)
	PrevSecondaryLightOn = ReadFloat(f)
	RemoteDoorOn = ReadByte(f)
	SoundTransmission = ReadByte(f)
	
	For i = 0 To MAXACHIEVEMENTS - 1
		achv\Achievement[i] = ReadByte(f)
	Next
	me\RefinedItems = ReadInt(f)
	
	If ReadInt(f) <> 994 Then UsedConsole = True
	
	opt\CameraFogFar = ReadFloat(f)
	opt\StoredCameraFogFar = ReadFloat(f)
	If opt\CameraFogFar = 0.0 Then opt\CameraFogFar = 6.0
	
	I_427\Using = ReadByte(f)
	I_427\Timer = ReadFloat(f)
	I_714\Using = ReadByte(f)
	I_294\Using = ReadByte(f)
	
	MTFTimer = ReadFloat(f)
	
	TakeOffTimer = ReadFloat(f)
	
	For x = 0 To MapGridSize
		For y = 0 To MapGridSize
			CurrMapGrid\Grid[x + (y * MapGridSize)] = ReadByte(f)
			CurrMapGrid\Found[x + (y * MapGridSize)] = ReadByte(f)
		Next
	Next
	
	If ReadInt(f) <> 113 Then RuntimeError(GetLocalString("save", "corrupted_1"))
	
	For n.NPCs = Each NPCs
		RemoveNPC(n)
	Next
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		Local NPCType% = ReadByte(f)
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		n.NPCs = CreateNPC(NPCType, x, y, z)
		Select NPCType
			Case NPCType173
				;[Block]
				n_I\Curr173 = n
				;[End Block]
			Case NPCType106
				;[Block]
				n_I\Curr106 = n
				;[End Block]
			Case NPCType096
				;[Block]
				n_I\Curr096 = n
				;[End Block]
			Case NPCType513_1
				;[Block]
				n_I\Curr513_1 = n
				;[End Block]
			Case NPCType049
				;[Block]
				n_I\Curr049 = n
				;[End Block]
		End Select
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		RotateEntity(n\Collider, x, y, z)
		
		n\State = ReadFloat(f)
		n\State2 = ReadFloat(f)
		n\State3 = ReadFloat(f)
		n\PrevState = ReadInt(f)
		
		n\Idle = ReadByte(f)
		n\LastDist = ReadFloat(f)
		n\LastSeen = ReadInt(f)
		
		n\CurrSpeed = ReadFloat(f)
		n\Angle = ReadFloat(f)
		n\Reload = ReadFloat(f)
		
		ForceSetNPCID(n, ReadInt(f))
		n\TargetID = ReadInt(f)
		
		n\EnemyX = ReadFloat(f)
		n\EnemyY = ReadFloat(f)
		n\EnemyZ = ReadFloat(f)
		
		n\Texture = ReadString(f)
		If n\Texture <> "" Then
			Tex = LoadTexture_Strict(n\Texture)
			If opt\Atmosphere Then TextureBlend(Tex, 5)
			EntityTexture(n\OBJ, Tex)
		EndIf
		
		Local Frame# = ReadFloat(f)
		
		Select NPCType
			Case NPCType106, NPCTypeD, NPCType096, NPCTypeMTF, NPCTypeGuard, NPCType049, NPCType049_2, NPCTypeClerk, NPCType008_1
				;[Block]
				SetAnimTime(n\OBJ, Frame)
				;[End Block]
		End Select
		
		n\Frame = Frame
		n\Contained = ReadByte(f)
		n\IsDead = ReadByte(f)
		n\PathX = ReadFloat(f)
		n\PathZ = ReadFloat(f)
		n\HP = ReadInt(f)
		n\Model = ReadString(f)
		n\ModelScaleX = ReadFloat(f)
		n\ModelScaleY = ReadFloat(f)
		n\ModelScaleZ = ReadFloat(f)
		If n\Model <> "" Then
			FreeEntity(n\OBJ)
			n\OBJ = LoadAnimMesh_Strict(n\Model)
			ScaleEntity(n\OBJ, n\ModelScaleX, n\ModelScaleY, n\ModelScaleZ)
			SetAnimTime(n\OBJ, Frame)
		EndIf
		n\HasAsset = ReadByte(f)
		If n\HasAsset Then CreateNPCAsset(n)
		n\TextureID = ReadByte(f)
		If n\TextureID > 0 Then
			ChangeNPCTextureID(n, n\TextureID - 1)
			SetAnimTime(n\OBJ, Frame)
		EndIf
		n\HideFromNVG = ReadByte(f)
		
		If n\TargetID <> 0 Then
			Local n2.NPCs
			
			For n2.NPCs = Each NPCs
				If n2 <> n Then
					If n2\ID = n\TargetID Then n\Target = n2
				EndIf
			Next
		EndIf
	Next
	
	If ReadInt(f) <> 632 Then RuntimeError(GetLocalString("save", "corrupted_2"))
	
	bk\IsBroken = ReadByte(f)
	bk\x = ReadFloat(f)
	bk\z = ReadFloat(f)
	
	I_Zone\Transition[0] = ReadByte(f)
	I_Zone\Transition[1] = ReadByte(f)
	I_Zone\HasCustomForest = ReadByte(f)
	I_Zone\HasCustomMT = ReadByte(f)
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		Local RoomTemplateID% = ReadInt(f)
		Local Angle% = ReadInt(f)
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		Local Found% = ReadByte(f)
		Local Level% = ReadInt(f)
		
		Local Temp2% = ReadByte(f)
		
		If Angle >= 360.0
			Angle = Angle - 360.0
		EndIf
		
		For r.Rooms = Each Rooms
			If r\x = x And r\z = z Then Exit
		Next
		
		For x = 0 To MaxRoomNPCs - 1
			ID = ReadInt(f)
			If ID > 0 Then
				For n.NPCs = Each NPCs
					If n\ID = ID Then
						r\NPC[x] = n
						Exit
					EndIf
				Next
			EndIf
		Next
		
		For x = 0 To MaxRoomLevers - 1
			ID = ReadByte(f)
			If ID = 1 Then
				RotateEntity(r\RoomLevers[x]\OBJ, 80.0, EntityYaw(r\RoomLevers[x]\OBJ), 0.0)
			ElseIf ID = 0
				RotateEntity(r\RoomLevers[x]\OBJ, -80.0, EntityYaw(r\RoomLevers[x]\OBJ), 0.0)
			EndIf
		Next
		
		If ReadByte(f) = 1 Then ; ~ This room has a grid
			For y = 0 To MTGridSize - 1
				For x = 0 To MTGridSize - 1
					ReadByte(f) : ReadByte(f)
				Next
			Next
		Else ; ~ This grid doesn't exist in the save
			If r\mt <> Null Then
				For x = 0 To MTGridSize - 1
					For y = 0 To MTGridSize - 1
						If r\mt\Entities[x + (y * MTGridSize)] <> 0 Then FreeEntity(r\mt\Entities[x + (y * MTGridSize)]) : r\mt\Entities[x + (y * MTGridSize)] = 0
						If r\mt\waypoints[x + (y * MTGridSize)] <> Null Then RemoveWaypoint(r\mt\waypoints[x + (y * MTGridSize)]) : r\mt\waypoints[x + (y * MTGridSize)] = Null
					Next
				Next
				For x = 0 To 6
					If r\mt\Meshes[x] <> 0 Then FreeEntity(r\mt\Meshes[x]) : r\mt\Meshes[x] = 0
				Next
				Delete(r\mt) : r\mt = Null
			EndIf
		EndIf
		
		If ReadByte(f) > 0 Then ; ~ This room has a forest
			For y = 0 To ForestGridSize - 1
				For x = 0 To ForestGridSize - 1
					ReadByte(f)
				Next
			Next
			
			Local lX# = ReadFloat(f)
			Local lY# = ReadFloat(f)
			Local lZ# = ReadFloat(f)
		ElseIf r\fr <> Null ; ~ Remove the old forest
			DestroyForest(r\fr)
			Delete(r\fr) : r\fr = Null
		EndIf
		
		If Temp2 = 1 Then PlayerRoom = r
		
		If r\x = r1499_x And r\z = r1499_z
			I_1499\PrevRoom = r
		EndIf
	Next
	
	If ReadInt(f) <> 954 Then RuntimeError(GetLocalString("save", "corrupted_3"))
	
	Temp = ReadInt(f)
	
	For i = 1 To Temp
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		Local Open% = ReadByte(f)
		Local OpenState# = ReadFloat(f)
		Local Locked% = ReadByte(f)
		Local AutoClose% = ReadByte(f)
		
		Local OBJX# = ReadFloat(f)
		Local OBJZ# = ReadFloat(f)
		Local OBJYaw# = ReadFloat(f)
		
		Local OBJ2X# = ReadFloat(f)
		Local OBJ2Z# = ReadFloat(f)
		
		Local Timer# = ReadFloat(f)
		Local TimerState# = ReadFloat(f)
		
		Local IsElevDoor% = ReadByte(f)
		Local MTFClose% = ReadByte(f)
		
		For do.Doors = Each Doors
			If EntityX(do\FrameOBJ, True) = x And EntityY(do\FrameOBJ, True) = y And EntityZ(do\FrameOBJ, True) = z Then
				do\Open = Open
				do\OpenState = OpenState
				do\Locked = Locked
				do\AutoClose = AutoClose
				do\Timer = Timer
				do\TimerState = TimerState
				do\IsElevatorDoor = IsElevDoor
				do\MTFClose = MTFClose
				
				PositionEntity(do\OBJ, OBJX, EntityY(do\OBJ), OBJZ, True)
				RotateEntity(do\OBJ, 0.0, OBJYaw, 0.0, True)
				If do\OBJ2 <> 0 Then PositionEntity(do\OBJ2, OBJ2X, EntityY(do\OBJ2), OBJ2Z, True)
				Exit
			EndIf
		Next
	Next
	
	If ReadInt(f) <> 1845 Then RuntimeError(GetLocalString("save", "corrupted_4"))
	
	Local de.Decals
	
	For de.Decals = Each Decals
		FreeEntity(de\OBJ) : de\OBJ = 0
		Delete(de)
	Next
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		ID = ReadInt(f)
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		Local Pitch# = ReadFloat(f)
		Local Yaw# = ReadFloat(f)
		Local Roll# = ReadFloat(f)
		
		de.Decals = CreateDecal(ID, x, y, z, Pitch, Yaw, Roll)
		
		Local Size# = ReadFloat(f)
		Local Alpha# = ReadFloat(f)
		Local FX% = ReadByte(f)
		Local BlendMode% = ReadByte(f)
		Local Red% = ReadByte(f), Green% = ReadByte(f), Blue% = ReadByte(f)
		
		Local DecalTimer# = ReadFloat(f)
		Local LifeTime# = ReadFloat(f)
		Local SizeChange# = ReadFloat(f)
		Local AlphaChange# = ReadFloat(f)
		
		For de.Decals = Each Decals
			If EntityX(de\OBJ, True) = x And EntityY(de\OBJ, True) = y And EntityZ(de\OBJ, True) = z Then
				de\Size = Size
				de\Alpha = Alpha
				de\FX = FX
				de\BlendMode = BlendMode
				de\R = Red : de\G = Green : de\B = Blue
				de\Timer = DecalTimer
				de\LifeTime = LifeTime
				de\SizeChange = SizeChange
				de\AlphaChange = AlphaChange
				
				ScaleSprite(de\OBJ, Size, Size)
				EntityAlpha(de\OBJ, Alpha)
				EntityFX(de\OBJ, FX)
				EntityBlend(de\OBJ, BlendMode)
				If Red <> 0 Lor Green <> 0 Lor Blue <> 0 Then EntityColor(de\OBJ, Red, Green, Blue)
				Exit
			EndIf
		Next
	Next
	
	Local e.Events
	
	For e.Events = Each Events
		RemoveEvent(e)
	Next
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		e.Events = New Events
		e\EventID = ReadByte(f)
		e\EventState = ReadFloat(f)
		e\EventState2 = ReadFloat(f)
		e\EventState3 = ReadFloat(f)
		e\EventState4 = ReadFloat(f)
		x = ReadFloat(f)
		z = ReadFloat(f)
		For r.Rooms = Each Rooms
			If EntityX(r\OBJ) = x And EntityZ(r\OBJ) = z Then
				e\room = r
				Exit
			EndIf
		Next
		e\EventStr = ReadString(f)
		FindForestEvent(e)
		
		Select e\EventID
			Case e_cont1_173
				;[Block]
				; ~ A hacky fix for the case that the intro objects aren't loaded when they should
				; ~ Altough I'm too lazy to add those objects there because at the time where you can save, those objects are already in the ground anyway -- ENDSHN
				If (Not e\room\Objects[0]) Then
					e\room\Objects[0] = CreatePivot()
					e\room\Objects[1] = CreatePivot()
				EndIf
				;[End Block]
			Case e_cont2_860_1
				;[Block]
				If e\EventState = 1.0 Then ShowEntity(e\room\fr\Forest_Pivot)
				;[End Block]
		End Select
	Next
	
	Local it.Items
	
	For it.Items = Each Items
		RemoveItem(it)
	Next
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		Local IttName$ = ReadString(f)
		Local TempName$ = ReadString(f)
		Local Name$ = ReadString(f)
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		z = ReadFloat(f)
		
		Red = ReadByte(f)
		Green = ReadByte(f)
		Blue = ReadByte(f)
		
		Local A% = ReadFloat(f)
		
		it.Items = CreateItem(IttName, TempName, x, y, z, Red, Green, Blue, A)
		it\Name = Name
		
		EntityType(it\Collider, HIT_ITEM)
		
		x = ReadFloat(f)
		y = ReadFloat(f)
		RotateEntity(it\Collider, x, y, 0.0)
		
		it\State = ReadFloat(f)
		it\State2 = ReadFloat(f)
		it\State3 = ReadFloat(f)
		it\Picked = ReadByte(f)
		If it\Picked Then HideEntity(it\Collider)
		
		Local nt% = ReadByte(f)
		
		If nt = True Then SelectedItem = it
		
		nt = ReadByte(f)
		If nt < 66 Then
			Inventory(nt) = it
			ItemAmount = ItemAmount + 1
		EndIf
		
		Local itt.ItemTemplates
		
		For itt.ItemTemplates = Each ItemTemplates
			If itt\TempName = TempName Then
				If itt\IsAnim <> 0 Then
					SetAnimTime(it\Model, ReadFloat(f))
					Exit
				EndIf
			EndIf
		Next
		it\InvSlots = ReadByte(f)
		it\ID = ReadInt(f)
		
		If it\ID > LastItemID Then LastItemID = it\ID
		
		If ReadByte(f) = 0 Then
			it\InvImg = it\ItemTemplate\InvImg
		Else
			it\InvImg = it\ItemTemplate\InvImg2
		EndIf
	Next
	
	Local o_i%
	
	Temp = ReadInt(f)
	For i = 1 To Temp
		o_i = ReadInt(f)
		
		Local ij.Items
		
		For ij.Items = Each Items
			If ij\ID = o_i Then
				it.Items = ij
				Exit
			EndIf
		Next
		For j = 0 To it\InvSlots - 1
			o_i = ReadInt(f)
			If o_i <> -1 Then
				For ij.Items = Each Items
					If ij\ID = o_i Then
						it\SecondInv[j] = ij
						Exit
					EndIf
				Next
			EndIf
		Next
	Next
	For itt.ItemTemplates = Each ItemTemplates
		itt\Found = ReadByte(f)
	Next

	me\EscapeTimer = ReadInt(f)
	
	Local Dist#, Dist2#
	
	For do.Doors = Each Doors
		If do\room <> Null Then
			Dist = 400.0
			
			Local closestroom.Rooms
			
			For r.Rooms = Each Rooms
				Dist2 = EntityDistanceSquared(r\OBJ, do\OBJ)
				If Dist2 < Dist Then
					Dist = Dist2
					closestroom = r.Rooms
				EndIf
			Next
			do\room = closestroom
		EndIf
	Next
	
	; ~ This will hopefully fix the SCP-895 crash bug after the player died by it's sanity effect and then quickloaded the game -- ENDSHN
	Local sc.SecurityCams
	
	For sc.SecurityCams = Each SecurityCams
		sc\PlayerState = 0
	Next
	EntityTexture(t\OverlayID[4], t\OverlayTextureID[4])
	me\RestoreSanity = True
	
	CloseFile(f)
	
	If me\Collider <> 0 Then
		If PlayerRoom <> Null Then ShowEntity(PlayerRoom\OBJ)
		ShowEntity(me\Collider)
		TeleportEntity(me\Collider, EntityX(me\Collider), EntityY(me\Collider) + 0.5, EntityZ(me\Collider), 0.3, True)
		If PlayerRoom <> Null Then HideEntity(PlayerRoom\OBJ)
	EndIf
	
	UpdateTimer = 0.0
	
	; ~ Free some entities that could potentially cause memory leaks (for the endings)
	; ~ This is only required for the LoadGameQuick function, as the other one is from the menu where everything is already deleted anyways
	Local xTemp#, zTemp#
	
	If Sky <> 0 Then FreeEntity(Sky) : Sky = 0
	For r.Rooms = Each Rooms
		Select r\RoomTemplate\Name
			Case "gate_a"
				;[Block]
				If r\Objects[0] <> 0 Then
					FreeEntity(r\Objects[0]) : r\Objects[0] = 0
					
					xTemp = EntityX(r\Objects[9], True)
					zTemp = EntityZ(r\Objects[9], True)
					FreeEntity(r\Objects[9]) : r\Objects[9] = 0
					
					r\Objects[10] = 0 ; ~ r\Objects[10] is already deleted because it is a parent object to r\Objects[9] which is already deleted a line before
					
					; ~ Readding this object, as it is originally inside the "FillRoom" function but gets deleted when it loads Gate A
					r\Objects[9] = CreatePivot()
					PositionEntity(r\Objects[9], xTemp, r\y + 992.0 * RoomScale, zTemp, True)
					EntityParent(r\Objects[9], r\OBJ)
					
					; ~ The Gate A wall pieces
					xTemp = EntityX(r\Objects[13], True)
					zTemp = EntityZ(r\Objects[13], True)
					FreeEntity(r\Objects[13])
					r\Objects[13] = LoadMesh_Strict("GFX\Map\gateawall1.b3d", r\OBJ)
					PositionEntity(r\Objects[13], xTemp, r\y - 1045.0 * RoomScale, zTemp, True)
					EntityColor(r\Objects[13], 25.0, 25.0, 25.0)
					EntityType(r\Objects[13], HIT_MAP)
					
					xTemp = EntityX(r\Objects[14], True)
					zTemp = EntityZ(r\Objects[14], True)
					FreeEntity(r\Objects[14])
					r\Objects[14] = LoadMesh_Strict("GFX\Map\gateawall2.b3d", r\OBJ)
					PositionEntity(r\Objects[14], xTemp, r\y - 1045.0 * RoomScale, zTemp, True)
					EntityColor(r\Objects[14], 25.0, 25.0, 25.0)
					EntityType(r\Objects[14], HIT_MAP)
				EndIf
				If r\Objects[12] <> 0 Then
					FreeEntity(r\Objects[12]) : r\Objects[12] = 0
					FreeEntity(r\Objects[17]) : r\Objects[17] = 0
				EndIf
				;[End Block]
			Case "gate_b"
				;[Block]
				If r\Objects[0] <> 0 Then
					xTemp = EntityX(r\Objects[0], True)
					zTemp = EntityZ(r\Objects[0], True)
					FreeEntity(r\Objects[0]) : r\Objects[0] = 0
					r\Objects[0] = CreatePivot(r\OBJ)
					PositionEntity(r\Objects[0], xTemp, r\y - 1017.0 * RoomScale, zTemp, True)
				EndIf
				;[End Block]
			Case "cont1_035"
				;[Block]
				Update035Label(r\Objects[6])
				;[End Block]
		End Select
	Next
	
	; ~ Resetting some stuff (those get changed when going to the endings)
	HideDistance = 17.0
	
	CatchErrors("LoadGameQuick")
End Function

Type AutoSave
	Field Amount%
	Field Timer#
End Type

Global as.AutoSave = New AutoSave

Function UpdateAutoSave%()
	If (Not opt\AutoSaveEnabled) Lor SelectedDifficulty\SaveType <> SAVE_ANYWHERE Lor me\Terminated Lor CanSave < 2 Lor (Not me\Playable) Lor me\Zombie Then
		CancelAutoSave()
		Return
	EndIf
	
	If as\Timer <= 0.0 Then
		as\Amount = as\Amount + 1
		If as\Amount >= 5 Then as\Amount = 0
		SaveGame(CurrSave\Name + "_" + as\Amount)
	Else
		as\Timer = as\Timer - fps\Factor[0]
		If as\Timer <= 70.0 * 5.0 Then CreateHintMsg(Format(GetLocalString("save", "autosave.in"), Str(Int(Ceil(as\Timer) / 70.0))))
	EndIf
End Function

Function CancelAutoSave%()
	If as\Timer <= 70.0 * 5.0 Then CreateHintMsg(GetLocalString("save", "autosave.canceled"))
	as\Timer = 70.0 * 120.0
End Function

Function SaveAchievementsFile%()
	Local File$
	
	File = WriteFile(GetEnv("AppData") + "\scpcb-ue\Data\Does the Black Moon howl.cb")
	WriteByte(File, achv\Achievement[AchvKeter])
	CloseFile(File)
End Function

Function LoadAchievementsFile%()
	; ~ Go out of function immediately if the file doesn't exist!
	If FileType(GetEnv("AppData") + "\scpcb-ue\Data\Does the Black Moon howl.cb") <> 1 Then Return
	
	Local File$
	
	File = OpenFile_Strict(GetEnv("AppData") + "\scpcb-ue\Data\Does the Black Moon howl.cb")
	achv\Achievement[AchvKeter] = ReadByte(File)
	CloseFile(File)
End Function

Type Save
	Field Name$
	Field Time$
	Field Date$
	Field Version$
End Type

Global CurrSave.Save
Global DelSave.Save
Global SavedGamesAmount%

Function LoadSavedGames%()
	CatchErrors("Uncaught (LoadSaveGames)")
	
	Local sv.Save, newsv.Save
	
	For sv.Save = Each Save
		Delete(sv)
	Next
	SavedGamesAmount = 0
	
	If FileType(SavePath) = 1 Then RuntimeError(Format(GetLocalString("save", "cantcreatedir"), SavePath))
	If FileType(SavePath) = 0 Then CreateDir(SavePath)
	
	Local SaveDir% = ReadDir(SavePath)
	
	NextFile(SaveDir) : NextFile(SaveDir) ; ~ Skipping "." and ".."
	
	Local File$ = NextFile(SaveDir)
	
	While File <> ""
		If FileType(SavePath + File) = 2 Then
			Local f% = ReadFile_Strict(SavePath + File + "\save.cb")
			
			newsv.Save = New Save
			newsv\Name = File
			
			newsv\Time = ReadString(f)
			newsv\Date = ReadString(f)
			newsv\Version = ReadString(f)
			
			CloseFile(f)
			SavedGamesAmount = SavedGamesAmount + 1
		EndIf
		File = NextFile(SaveDir)
	Wend
	CloseDir(SaveDir)
	
	CatchErrors("LoadSaveGames")
End Function

Function DeleteGame%(sv.Save)
	sv\Name = SavePath + sv\Name + "\"
	
	Local DelDir% = ReadDir(sv\Name)
	
	If DelDir <> 0 Then
		NextFile(DelDir) : NextFile(DelDir) ; ~ Skipping "." and ".."
		
		Local File$ = NextFile(DelDir)
		
		While File <> ""
			DeleteFile(sv\Name + File)
			File = NextFile(DelDir)
		Wend
		CloseDir(DelDir)
		DeleteDir(sv\Name)
		SavedGamesAmount = SavedGamesAmount - 1
	EndIf
	Delete(sv)
End Function

Const CustomMapsPath$ = "Map Creator\Maps\"

Type CustomMaps
	Field Name$
	Field Author$
End Type

Global CurrCustomMap.CustomMaps
Global DelCustomMap.CustomMaps
Global SelectedCustomMap.CustomMaps
Global CustomMapsAmount%

Function LoadCustomMaps%()
	CatchErrors("Uncaught (LoadCustomMaps)")
	
	Local cm.CustomMaps, newcm.CustomMaps
	
	For cm.CustomMaps = Each CustomMaps
		Delete(cm)
	Next
	CustomMapsAmount = 0
	
	If FileType(CustomMapsPath) = 1 Then RuntimeError(Format(GetLocalString("save", "cantcreatedir"), CustomMapsPath))
	If FileType(CustomMapsPath) = 0 Then CreateDir(CustomMapsPath)
	
	Local MapDir% = ReadDir(CustomMapsPath)
	
	NextFile(MapDir) : NextFile(MapDir) ; ~ Skipping "." and ".."
	
	Local File$ = NextFile(MapDir)
	
	While File <> ""
		If FileType(CustomMapsPath + File) = 1 Then
			If Right(File, 6) = "cbmap2" Lor Right(File, 5) = "cbmap" Then
				Local f% = ReadFile_Strict(CustomMapsPath + File)
				
				newcm.CustomMaps = New CustomMaps
				newcm\Name = File
				If Right(File, 6) = "cbmap2" Then
					newcm\Author = ReadLine(f)
				Else
					newcm\Author = GetLocalString("creator", "unknown")
				EndIf
				CloseFile(f)
				CustomMapsAmount = CustomMapsAmount + 1
			EndIf
		EndIf
		File = NextFile(MapDir)
	Wend
	CloseDir(MapDir)
	
	CatchErrors("LoadCustomMaps")
End Function

Function DeleteCustomMap%(cm.CustomMaps)
	Local DelDir% = ReadDir(CustomMapsPath)
	
	If DelDir <> 0 Then
		NextFile(DelDir) : NextFile(DelDir) ; ~ Skipping "." and ".."
		
		Local File$ = NextFile(DelDir)
		
		While File <> ""
			DeleteFile(CustomMapsPath + File)
			File = NextFile(DelDir)
		Wend
		CloseDir(DelDir)
		CustomMapsAmount = CustomMapsAmount - 1
	EndIf
	Delete(cm)
End Function

Function LoadMap%(File$)
	CatchErrors("Uncaught (LoadMap)")
	
	Local r.Rooms, rt.RoomTemplates, e.Events
	Local f%, x%, y%, Name$, Angle%, Prob#
	Local RoomAmount%, ForestPieceAmount%, MTPieceAmount%, i%
	
	f = ReadFile_Strict(File)
	
	If CurrMapGrid <> Null Then Delete(CurrMapGrid) : CurrMapGrid = Null
	CurrMapGrid = New MapGrid
	
	CoffinDistance = 100.0
	
	If Right(File, 6) = "cbmap2" Then
		ReadLine(f)
		ReadLine(f)
		I_Zone\Transition[0] = ReadByte(f)
		I_Zone\Transition[1] = ReadByte(f)
		RoomAmount = ReadInt(f)
		ForestPieceAmount = ReadInt(f)
		MTPieceAmount = ReadInt(f)
		
		If ForestPieceAmount > 0 Then I_Zone\HasCustomForest = True
		
		If MTPieceAmount > 0 Then I_Zone\HasCustomMT = True
		
		; ~ Facility rooms
		For i = 0 To RoomAmount - 1
			x = ReadByte(f)
			y = ReadByte(f)
			Name = Lower(ReadString(f))
			
			Angle = ReadByte(f) * 90.0
			
			For rt.RoomTemplates = Each RoomTemplates
				If Lower(rt\Name) = Name Then
					r.Rooms = CreateRoom(0, rt\Shape, (MapGridSize - x) * 8.0, 0.0, y * 8.0, Name)
					
					r\Angle = Angle
					If r\Angle <> 90.0 And r\Angle <> 270.0 Then r\Angle = r\Angle + 180.0
					r\Angle = WrapAngle(r\Angle)
					SetupTriggerBoxes(r)
					TurnEntity(r\OBJ, 0.0, r\Angle, 0.0)
					
					CurrMapGrid\Grid[(MapGridSize - x) + (y * MapGridSize)] = MapGrid_Tile
					Exit
				EndIf
			Next
			
			Name = ReadString(f)
			Prob = ReadFloat(f)
			
			If r <> Null Then
				If Prob > 0.0 Then
					If Rnd(0.0, 1.0) <= Prob Then
						e.Events = New Events
						e\EventName = Name
						e\EventID = FindEventID(Name)
						FindForestEvent(e)
						e\room = r
					EndIf
				ElseIf Prob = 0.0 And Name <> ""
					e.Events = New Events
					e\EventName = Name
					e\EventID = FindEventID(Name)
					FindForestEvent(e)
					e\room = r
				EndIf
			EndIf
		Next
		
		Local ForestRoom.Rooms
		
		For r.Rooms = Each Rooms
			If r\RoomTemplate\Name = "cont2_860_1" Then
				ForestRoom = r
				Exit
			EndIf
		Next
		
		Local fr.Forest
		
		If ForestRoom <> Null Then fr.Forest = New Forest
		
		; ~ Forest rooms
		For i = 0 To ForestPieceAmount - 1
			x = ReadByte(f)
			y = ReadByte(f)
			Name = ReadString(f)
			
			Angle = ReadByte(f)
			If Angle <> 0.0 And Angle <> 2.0 Then Angle = Angle + 2.0
			Angle = Angle + 1.0
			If Angle > 3.0 Then Angle = (Angle Mod 4.0)
			x = (ForestGridSize - 1) - x
			
			If fr <> Null Then
				Select Name
					; ~ 1, 2, 3, 4 = ROOM1
					; ~ 5, 6, 7, 8 = ROOM2
					; ~ 9, 10, 11, 12 = ROOM2C
					; ~ 13, 14, 15, 16 = ROOM3
					; ~ 17, 18, 19, 20 = ROOM4
					; ~ 21, 22, 23, 24 = DOORROOM
					Case "scp-860-1 endroom"
						;[Block]
						fr\Grid[(y * ForestGridSize) + x] = Angle + 1.0
						;[End Block]
					Case "scp-860-1 path"
						;[Block]
						fr\Grid[(y * ForestGridSize) + x] = Angle + 5.0
						;[End Block]
					Case "scp-860-1 corner"
						;[Block]
						fr\Grid[(y * ForestGridSize) + x] = Angle + 9.0
						;[End Block]
					Case "scp-860-1 t-shaped path"
						;[Block]
						fr\Grid[(y * ForestGridSize) + x] = Angle + 13.0
						;[End Block]
					Case "scp-860-1 4-way path"
						;[Block]
						fr\Grid[(y * ForestGridSize) + x] = Angle + 17.0
						;[End Block]
					Case "scp-860-1 door"
						;[Block]
						fr\Grid[(y * ForestGridSize) + x] = Angle + 21.0
						;[End Block]
				End Select
			EndIf
		Next
		
		If fr <> Null Then
			ForestRoom\fr = fr
			PlaceMapCreatorForest(ForestRoom\fr, ForestRoom\x, ForestRoom\y + 30.0, ForestRoom\z, ForestRoom)
		EndIf
		
		Local MTRoom.Rooms
		
		For r.Rooms = Each Rooms
			If r\RoomTemplate\Name = "room2_mt" Then
				MTRoom = r
				Exit
			EndIf
		Next
		
		If MTRoom <> Null Then MTRoom\mt = New MTGrid
		
		; ~ Maintenance tunnels rooms
		For i = 0 To MTPieceAmount - 1
			x = ReadByte(f)
			y = ReadByte(f)
			Name = ReadString(f)
			
			Angle = ReadByte(f)
			If Angle <> 1.0 And Angle <> 3.0 Then Angle = Angle + 2.0
			If Name = "maintenance tunnel corner" Lor Name = "maintenance tunnel t-shaped room" Then Angle = Angle + 3.0
			If Angle > 3.0 Then Angle = (Angle Mod 4.0)
			
			x = (MTGridSize - 1) - x
			
			If MTRoom <> Null Then
				Select Name
					Case "maintenance tunnel endroom"
						;[Block]
						MTRoom\mt\Grid[x + (y * MTGridSize)] = ROOM1 + 1
						;[End Block]
					Case "maintenance tunnel corridor"
						;[Block]
						MTRoom\mt\Grid[x + (y * MTGridSize)] = ROOM2 + 1
						;[End Block]
					Case "maintenance tunnel corner"
						;[Block]
						MTRoom\mt\Grid[x + (y * MTGridSize)] = ROOM2C + 1
						;[End Block]
					Case "maintenance tunnel t-shaped room"
						;[Block]
						MTRoom\mt\Grid[x + (y * MTGridSize)] = ROOM3 + 1
						;[End Block]
					Case "maintenance tunnel 4-way room"
						;[Block]
						MTRoom\mt\Grid[x + (y * MTGridSize)] = ROOM4 + 1
						;[End Block]
					Case "maintenance tunnel elevator"
						;[Block]
						MTRoom\mt\Grid[x + (y * MTGridSize)] = ROOM4 + 2
						;[End Block]
					Case "maintenance tunnel generator room"
						;[Block]
						MTRoom\mt\Grid[x + (y * MTGridSize)] = ROOM4 + 3
						;[End Block]
				End Select
				MTRoom\mt\Angles[x + (y * MTGridSize)] = Angle
			EndIf
		Next
	Else
		I_Zone\Transition[0] = 13
		I_Zone\Transition[1] = 7
		I_Zone\HasCustomForest = False
		I_Zone\HasCustomMT = False
		While (Not Eof(f))
			x = ReadByte(f)
			y = ReadByte(f)
			Name = Lower(ReadString(f))
			
			Angle = ReadByte(f) * 90.0
			
			For rt.RoomTemplates = Each RoomTemplates
				If Lower(rt\Name) = Name Then
					r.Rooms = CreateRoom(0, rt\Shape, (MapGridSize - x) * 8.0, 0.0, y * 8.0, Name)
					
					r\Angle = Angle
					If r\Angle <> 90.0 And r\Angle <> 270.0 Then r\Angle = r\Angle + 180.0
					r\Angle = WrapAngle(r\Angle)
					SetupTriggerBoxes(r)
					TurnEntity(r\OBJ, 0.0, r\Angle, 0.0)
					
					CurrMapGrid\Grid[(MapGridSize - x) + (y * MapGridSize)] = MapGrid_Tile
					Exit
				EndIf
			Next
			
			Name = ReadString(f)
			Prob = ReadFloat(f)
			
			If r <> Null Then
				If Prob > 0.0 Then
					If Rnd(0.0, 1.0) <= Prob Then
						e.Events = New Events
						e\EventName = Name
						e\EventID = FindEventID(Name)
						FindForestEvent(e)
						e\room = r
					EndIf
				ElseIf Prob = 0.0 And Name <> ""
					e.Events = New Events
					e\EventName = Name
					e\EventID = FindEventID(Name)
					FindForestEvent(e)
					e\room = r
				EndIf
			EndIf
		Wend
	EndIf
	
	CloseFile(f)
	
	Local Zone%
	Local ShouldSpawnDoor% = False
	Local d.Doors
	
	For y = MapGridSize To 0 Step -1
		If y < I_Zone\Transition[1] Then
			Zone = 3
		ElseIf y >= I_Zone\Transition[1] And y < I_Zone\Transition[0]
			Zone = 2
		Else
			Zone = 1
		EndIf
		For x = MapGridSize To 0 Step -1
			If CurrMapGrid\Grid[x + (y * MapGridSize)] > MapGrid_NoTile Then
				For r.Rooms = Each Rooms
					r\Angle = WrapAngle(r\Angle)
					If Int(r\x / RoomSpacing) = x And Int(r\z / RoomSpacing) = y Then
						ShouldSpawnDoor = False
						Select r\RoomTemplate\Shape
							Case ROOM1
								;[Block]
								If r\Angle = 90.0 Then ShouldSpawnDoor = True
								;[End Block]
							Case ROOM2
								;[Block]
								If r\Angle = 90.0 Lor r\Angle = 270.0 Then ShouldSpawnDoor = True
								;[End Block]
							Case ROOM2C
								;[Block]
								If r\Angle = 0.0 Lor r\Angle = 90.0 Then ShouldSpawnDoor = True
								;[End Block]
							Case ROOM3
								If r\Angle = 0.0 Lor r\Angle = 180.0 Lor r\Angle = 90.0 Then ShouldSpawnDoor = True
								;[End Block]
							Default
								;[Block]
								ShouldSpawnDoor = True
								;[End Block]
						End Select
						If ShouldSpawnDoor Then
							If x + 1 < MapGridSize + 1 Then
								If CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)] > MapGrid_NoTile Then
									d.Doors = CreateDoor(Float(x) * RoomSpacing + (RoomSpacing / 2.0), 0.0, Float(y) * RoomSpacing, 90.0, r, Max(Rand(-3, 1), 0.0), ((Zone - 1) Mod 2) * 2)
									r\AdjDoor[0] = d
								EndIf
							EndIf
						EndIf
						
						ShouldSpawnDoor = False
						Select r\RoomTemplate\Shape
							Case ROOM1
								;[Block]
								If r\Angle = 180.0 Then ShouldSpawnDoor = True
								;[End Block]
							Case ROOM2
								;[Block]
								If r\Angle = 0.0 Lor r\Angle = 180.0 Then ShouldSpawnDoor = True
								;[End Block]
							Case ROOM2C
								;[Block]
								If r\Angle = 180.0 Lor r\Angle = 90.0 Then ShouldSpawnDoor = True
								;[End Block]
							Case ROOM3
								;[Block]
								If r\Angle = 180.0 Lor r\Angle = 90.0 Lor r\Angle = 270.0 Then ShouldSpawnDoor = True
								;[End Block]
							Default
								;[Block]
								ShouldSpawnDoor = True
								;[End Block]
						End Select
						If ShouldSpawnDoor Then
							If y + 1 < MapGridSize + 1 Then
								If CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)] > MapGrid_NoTile Then
									d.Doors = CreateDoor(Float(x) * RoomSpacing, 0.0, Float(y) * RoomSpacing + (RoomSpacing / 2.0), 0.0, r, Max(Rand(-3, 1), 0.0), ((Zone - 1) Mod 2) * 2)
									r\AdjDoor[3] = d
								EndIf
							EndIf
						EndIf
						Exit
					EndIf
				Next
			EndIf
		Next
	Next
	
	; ~ Spawn some rooms outside the map
	r.Rooms = CreateRoom(0, ROOM1, 0.0, 500.0, -(RoomSpacing) * 10, "gate_b")
	CreateEvent("gate_b", "gate_b", 0)
	
	r.Rooms = CreateRoom(0, ROOM1, 0.0, 500.0, -(RoomSpacing) * 2, "gate_a")
	CreateEvent("gate_a", "gate_a", 0)
	
	r.Rooms = CreateRoom(0, ROOM1, (MapGridSize + 2) * RoomSpacing, 0.0, (MapGridSize + 2) * RoomSpacing, "dimension_106")
	CreateEvent("dimension_106", "dimension_106", 0) 
	
	If opt\IntroEnabled Then
		r.Rooms = CreateRoom(0, ROOM1, RoomSpacing, 0.0, (MapGridSize + 2) * RoomSpacing, "cont1_173_intro")
		CreateEvent("cont1_173_intro", "cont1_173_intro", 0)
	EndIf
	
	r.Rooms = CreateRoom(0, ROOM1, -(RoomSpacing * 2), 800.0, 0.0, "dimension_1499")
	CreateEvent("dimension_1499", "dimension_1499", 0)
	
	For r.Rooms = Each Rooms
		For i = 0 To MaxRoomAdjacents - 1
			r\Adjacent[i] = Null
		Next
		
		Local r2.Rooms
		
		For r2.Rooms = Each Rooms
			If r <> r2 Then
				If r2\z = r\z Then
					If r2\x = r\x + 8.0 Then
						r\Adjacent[0] = r2
						If r\AdjDoor[0] = Null Then r\AdjDoor[0] = r2\AdjDoor[2]
					ElseIf r2\x = r\x - 8.0
						r\Adjacent[2] = r2
						If r\AdjDoor[2] = Null Then r\AdjDoor[2] = r2\AdjDoor[0]
					EndIf
				ElseIf r2\x = r\x
					If r2\z = r\z - 8.0 Then
						r\Adjacent[1] = r2
						If r\AdjDoor[1] = Null Then r\AdjDoor[1] = r2\AdjDoor[3]
					ElseIf r2\z = r\z + 8.0
						r\Adjacent[3] = r2
						If r\AdjDoor[3] = Null Then r\AdjDoor[3] = r2\AdjDoor[1]
					EndIf
				EndIf
			EndIf
			If r\Adjacent[0] <> Null And r\Adjacent[1] <> Null And r\Adjacent[2] <> Null And r\Adjacent[3] <> Null Then Exit
		Next
	Next
	
	CatchErrors("LoadMap")
End Function

;~IDEal Editor Parameters:
;~C#Blitz3D