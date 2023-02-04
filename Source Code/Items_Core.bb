Global ItemAmount%, MaxItemAmount%

Global LastItemID%

Type ItemTemplates
	Field DisplayName$
	Field Name$
	Field TempName$
	Field Sound%
	Field Found%
	Field OBJ%, OBJPath$
	Field InvImg%, InvImg2%, InvImgPath$
	Field ImgPath$, Img%
	Field ImgWidth%, ImgHeight%
	Field IsAnim%
	Field Scale#
	Field Tex%, TexPath$
End Type

Function CreateItemTemplate.ItemTemplates(DisplayName$, Name$, TempName$, OBJPath$, InvImgPath$, ImgPath$, Scale#, SoundID%, TexturePath$ = "", InvImgPath2$ = "", HasAnim% = False, TexFlags% = 1 + 8)
	Local it.ItemTemplates, it2.ItemTemplates
	
	it.ItemTemplates = New ItemTemplates
	; ~ If another item shares the same object, copy it
	OBJPath = ItemsPath + OBJPath
	For it2.ItemTemplates = Each ItemTemplates
		If it2\OBJPath = OBJPath And it2\OBJ <> 0 Then
			it\OBJ = CopyEntity(it2\OBJ)
			Exit
		EndIf
	Next
	
	If (Not it\OBJ) Then
		If HasAnim Then
			it\OBJ = LoadAnimMesh_Strict(OBJPath)
			it\IsAnim = True
		Else
			it\OBJ = LoadMesh_Strict(OBJPath)
			it\IsAnim = False
		EndIf
	EndIf
	it\OBJPath = OBJPath
	
	Local Texture%
	
	If TexturePath <> "" Then
		TexturePath = ItemTexturePath + TexturePath
		For it2.ItemTemplates = Each ItemTemplates
			If it2\TexPath = TexturePath And it2\Tex <> 0 Then
				Texture = it2\Tex
				If opt\Atmosphere Then TextureBlend(Texture, 5)
				Exit
			EndIf
		Next
		If (Not Texture) Then
			Texture = LoadTexture_Strict(TexturePath, TexFlags)
			If opt\Atmosphere Then TextureBlend(Texture, 5)
			it\TexPath = TexturePath
		EndIf
		EntityTexture(it\OBJ, Texture)
		it\Tex = Texture
	EndIf
	
	it\Scale = Scale
	ScaleEntity(it\OBJ, Scale, Scale, Scale, True)
	
	; ~ If another item shares the same object, copy it
	InvImgPath = ItemINVIconPath + InvImgPath
	For it2.ItemTemplates = Each ItemTemplates
		If it2\InvImgPath = InvImgPath And it2\InvImg <> 0 Then
			it\InvImg = it2\InvImg
			If it2\InvImg2 <> 0 Then it\InvImg2 = it2\InvImg2
			Exit
		EndIf
	Next
	If (Not it\InvImg) Then
		it\InvImg = LoadImage_Strict(InvImgPath)
		it\InvImg = ScaleImage2(it\InvImg, MenuScale, MenuScale)
		it\InvImgPath = InvImgPath
	EndIf
	
	If InvImgPath2 <> "" Then
		InvImgPath2 = ItemINVIconPath + InvImgPath2
		If (Not it\InvImg2) Then
			it\InvImg2 = LoadImage_Strict(InvImgPath2)
			it\InvImg2 = ScaleImage2(it\InvImg2, MenuScale, MenuScale)
		EndIf
	Else
		it\InvImg2 = 0
	EndIf
	
	If ImgPath  <> "" Then
		ImgPath = ItemHUDTexturePath + ImgPath
		it\ImgPath = ImgPath
	EndIf
	
	it\DisplayName = DisplayName
	it\TempName = TempName
	it\Name = Name
	
	it\Sound = SoundID
	
	HideEntity(it\OBJ)
	
	Return(it)
End Function

Function GetRandDocument$()
	Select Rand(0, 20)
		Case 0
			;[Block]
			Return("005")
			;[End Block]
		Case 1
			;[Block]
			Return("008")
			;[End Block]
		Case 2
			;[Block]
			Return("012")
			;[End Block]
		Case 3
			;[Block]
			Return("049")
			;[End Block]
		Case 4
			;[Block]
			Return("096")
			;[End Block]
		Case 5
			;[Block]
			Return("106")
			;[End Block]
		Case 6
			;[Block]
			Return("173")
			;[End Block]
		Case 7
			;[Block]
			Return("205")
			;[End Block]
		Case 8
			;[Block]
			Return("409")
			;[End Block]
		Case 9
			;[Block]
			Return("513")
			;[End Block]
		Case 10
			;[Block]
			Return("682")
			;[Block]
		Case 11
			;[Block]
			Return("714")
			;[End Block]
		Case 12
			;[Block]
			Return("860")
			;[End Block]
		Case 13
			;[Block]
			Return("860-1")
			;[End Block]
		Case 14
			;[Block]
			Return("895")
			;[End Block]
		Case 15
			;[Block]
			Return("939")
			;[End Block]
		Case 16
			;[Block]
			Return("966")
			;[End Block]
		Case 17
			;[Block]
			Return("970")
			;[End Block]
		Case 18
			;[Block]
			Return("1048")
			;[End Block]
		Case 19
			;[Block]
			Return("1162-ARC")
			;[End Block]
		Case 20
			;[Block]
			Return("1499")
			;[End Block]
	End Select
End Function

Type Items
	Field DisplayName$
	Field Name$
	Field Collider%, Model%
	Field ItemTemplate.ItemTemplates
	Field DropSpeed#
	Field R%, G%, B%, A#
	Field SoundCHN%
	Field Dist#, DistTimer#
	Field State#, State2#, State3#
	Field Picked%, Dropped%
	Field InvImg%
	Field xSpeed#, zSpeed#
	Field SecondInv.Items[20]
	Field ID%
	Field InvSlots%
End Type

Dim Inventory.Items(0)

Global SelectedItem.Items

Global ClosestItem.Items
Global OtherOpen.Items = Null

Function CreateItem.Items(Name$, TempName$, x#, y#, z#, R% = 0, G% = 0, B% = 0, A# = 1.0, InvSlots% = 0)
	CatchErrors("Uncaught (CreateItem)")
	
	Local i.Items, it.ItemTemplates
	
	Name = Lower(Name)
	TempName = Lower(TempName)
	
	i.Items = New Items
	For it.ItemTemplates = Each ItemTemplates
		If Lower(it\Name) = Name And Lower(it\TempName) = TempName Then
			i\ItemTemplate = it
			i\Collider = CreatePivot()
			EntityRadius(i\Collider, 0.01)
			EntityPickMode(i\Collider, 1, False)
			i\Model = CopyEntity(it\OBJ, i\Collider)
			i\DisplayName = it\DisplayName
			i\Name = it\Name
			ShowEntity(i\Collider)
			ShowEntity(i\Model)
			Exit
		EndIf
	Next 
	
	If i\ItemTemplate = Null Then RuntimeError(Format(Format(GetLocalString("runerr", "item"), Name, "{0}"), TempName, "{1}"))
	
	ResetEntity(i\Collider)
	PositionEntity(i\Collider, x, y, z, True)
	RotateEntity(i\Collider, 0.0, Rnd(360.0), 0.0)
	
	i\Dist = EntityDistanceSquared(me\Collider, i\Collider)
	i\DropSpeed = 0.0
	
	If TempName = "cup" Then
		i\R = R
		i\G = G
		i\B = B
		i\A = A
		i\State = 1.0
		
		Local Liquid% = CopyEntity(misc_I\CupLiquid)
		
		ScaleEntity(Liquid, i\ItemTemplate\Scale, i\ItemTemplate\Scale, i\ItemTemplate\Scale, True)
		PositionEntity(Liquid, EntityX(i\Collider, True), EntityY(i\Collider, True), EntityZ(i\Collider, True))
		EntityParent(Liquid, i\Model)
		EntityColor(Liquid, R, G, B)
		
		If A < 0.0 Then
			EntityFX(Liquid, 1)
			EntityAlpha(Liquid, Abs(A))
		Else
			EntityAlpha(Liquid, Abs(A))
		EndIf
		EntityShininess(Liquid, 1.0)
	EndIf
	
	i\InvImg = i\ItemTemplate\InvImg
	If TempName = "clipboard" And InvSlots = 0 Then
		InvSlots = 10
		SetAnimTime(i\Model, 17.0)
		i\InvImg = i\ItemTemplate\InvImg2
	ElseIf TempName = "wallet" And InvSlots = 0 Then
		InvSlots = 10
		SetAnimTime(i\Model, 0.0)
	EndIf
	
	i\InvSlots = InvSlots
	
	i\ID = LastItemID + 1
	LastItemID = i\ID
	
	CatchErrors("CreateItem")
	
	Return(i)
End Function

Function RemoveItem%(i.Items)
	CatchErrors("Uncaught (RemoveItem)")
	
	Local n%
	
	FreeEntity(i\Model) : i\Model = 0
	FreeEntity(i\Collider) : i\Collider = 0
	
	For n = 0 To MaxItemAmount - 1
		If Inventory(n) = i
			Inventory(n) = Null
			ItemAmount = ItemAmount - 1
			Exit
		EndIf
	Next
	
	If SelectedItem = i Then SelectedItem = Null
	
	If i\ItemTemplate\Img <> 0 Then FreeImage(i\ItemTemplate\Img) : i\ItemTemplate\Img = 0
	Delete(i)
	
	CatchErrors("RemoveItem")
End Function

Function RemoveWearableItems%(item.Items)
	CatchErrors("Uncaught (RemoveWearableItems)")
	
	Select item\ItemTemplate\TempName
		Case "gasmask", "finegasmask", "veryfinegasmask", "gasmask148"
			;[Block]
			wi\GasMask = 0
			;[End Block]
		Case "hazmatsuit", "finehazmatsuit", "veryfinehazmatsuit", "hazmatsuit148"
			;[Block]
			wi\HazmatSuit = 0
			SetAnimTime(item\Model, 4.0)
			;[End Block]
		Case "vest", "finevest"
			;[Block]
			wi\BallisticVest = 0
			;[End Block]
		Case "helmet"
			;[Block]
			wi\BallisticHelmet = False
			;[End Block]
		Case "nvg", "finenvg", "veryfinenvg"
			;[Block]
			If wi\NightVision > 0 Then opt\CameraFogFar = opt\StoredCameraFogFar : wi\NightVision = 0
			;[End Block]
		Case "scp714"
			;[Block]
			I_714\Using = 1
			;[End Block]
		Case "scp1499", "fine1499"
			;[Block]
			I_1499\Using = 0
			;[End Block]
		Case "scp427"
			;[Block]
			I_427\Using = False
			;[End Block]
		Case "scramble"
			;[Block]
			wi\SCRAMBLE = False
			;[End Block]
	End Select
	
	CatchErrors("RemoveWearableItems")
End Function

Function ClearSecondInv%(item.Items, From% = 0)
	Local i%
	
	For i = From To 19
		If item\SecondInv[i] <> Null Then RemoveItem(item\SecondInv[i]) : item\SecondInv[i] = Null
	Next
End Function

Function UpdateItems%()
	CatchErrors("Uncaught (UpdateItems)")
	
	Local i.Items, i2.Items, np.NPCs
	Local xTemp#, yTemp#, zTemp#
	Local Temp%, n%
	Local Pick%, ed#
	Local HideDist# = PowTwo(HideDistance / 2.0)
	Local DeletedItem% = False
	
	ClosestItem = Null
	For i.Items = Each Items
		i\Dropped = 0
		
		If (Not i\Picked) Then
			If i\DistTimer < MilliSecs2() Then
				i\Dist = EntityDistanceSquared(Camera, i\Collider)
				i\DistTimer = MilliSecs2() + 700
				If i\Dist < HideDist Then
					If EntityHidden(i\Collider) Then ShowEntity(i\Collider)
				EndIf
			EndIf
			
			If i\Dist < HideDist Then
				If EntityHidden(i\Collider) Then ShowEntity(i\Collider)
				If i\Dist < 1.44 Then
					If ClosestItem = Null Then
						If EntityInView(i\Model, Camera) And EntityVisible(i\Collider, Camera) Then ClosestItem = i
					ElseIf ClosestItem = i Lor i\Dist < EntityDistanceSquared(Camera, ClosestItem\Collider)
						If EntityInView(i\Model, Camera) And EntityVisible(i\Collider, Camera) Then ClosestItem = i
					EndIf
				EndIf
				
				If EntityCollided(i\Collider, HIT_MAP) Then
					i\DropSpeed = 0.0
					i\xSpeed = 0.0
					i\zSpeed = 0.0
				Else
					If ShouldEntitiesFall Then
						Pick = LinePick(EntityX(i\Collider), EntityY(i\Collider), EntityZ(i\Collider), 0.0, -10.0, 0.0)
						If Pick Then
							i\DropSpeed = i\DropSpeed - (0.0004 * fps\Factor[0])
							TranslateEntity(i\Collider, i\xSpeed * fps\Factor[0], i\DropSpeed * fps\Factor[0], i\zSpeed * fps\Factor[0])
						Else
							i\DropSpeed = 0.0
							i\xSpeed = 0.0
							i\zSpeed = 0.0
						EndIf
					Else
						i\DropSpeed = 0.0
						i\xSpeed = 0.0
						i\zSpeed = 0.0
					EndIf
				EndIf
				
				If PlayerRoom\RoomTemplate\Name <> "room2_storage" Then
					If i\Dist < HideDist * 0.04 Then
						For i2.Items = Each Items
							If i <> i2 And (Not i2\Picked) And i2\Dist < HideDist * 0.04 Then
								xTemp = EntityX(i2\Collider, True) - EntityX(i\Collider, True)
								yTemp = EntityY(i2\Collider, True) - EntityY(i\Collider, True)
								zTemp = EntityZ(i2\Collider, True) - EntityZ(i\Collider, True)
								
								ed = (xTemp ^ 2) + (zTemp ^ 2)
								If ed < 0.07 And Abs(yTemp) < 0.25 Then
									; ~ Items are too close together, push away
									xTemp = xTemp * (0.07 - ed)
									zTemp = zTemp * (0.07 - ed)
									
									While Abs(xTemp) + Abs(zTemp) < 0.001
										xTemp = xTemp + Rnd(-0.002, 0.002)
										zTemp = zTemp + Rnd(-0.002, 0.002)
									Wend
									
									TranslateEntity(i2\Collider, xTemp, 0.0, zTemp)
									TranslateEntity(i\Collider, -xTemp, 0.0, -zTemp)
								EndIf
							EndIf
						Next
					EndIf
				EndIf
				If EntityY(i\Collider) < -60.0 Then
					RemoveItem(i)
					DeletedItem = True
				EndIf
			Else
				If (Not EntityHidden(i\Collider)) Then HideEntity(i\Collider)
			EndIf
		Else
			i\DropSpeed = 0.0
			i\xSpeed = 0.0
			i\zSpeed = 0.0
		EndIf
		
		If (Not DeletedItem) Then
			CatchErrors("Detected error for:" + Chr(34) + i\ItemTemplate\Name + Chr(34) + " item!")
		Else
			CatchErrors("Detected error for deleted item!")
		EndIf
		DeletedItem = False
	Next
	
	If ClosestItem <> Null And (Not me\Terminated) Then
		If mo\MouseHit1 Then PickItem(ClosestItem)
	EndIf
End Function

Function PickItem%(item.Items)
	
	If InvOpen Lor I_294\Using Lor OtherOpen <> Null Lor d_I\SelectedDoor <> Null Lor SelectedScreen <> Null Then Return
	
	CatchErrors("Uncaught (PickItem)")
	
	Local e.Events
	Local n% = 0, z%
	Local CanPickItem% = 1
	Local FullINV% = True
	
	For n = 0 To MaxItemAmount - 1
		If Inventory(n) = Null Then
			FullINV = False
			Exit
		EndIf
	Next
	
	If (Not FullINV) Then
		For n = 0 To MaxItemAmount - 1
			If Inventory(n) = Null Then
				Select item\ItemTemplate\TempName
					Case "scp1123"
						;[Block]
						Use1123()
						If I_714\Using = 1 And wi\GasMask <> 4 And wi\HazmatSuit <> 4 Then Return
						;[End Block]
					Case "killbat"
						;[Block]
						me\LightFlash = 1.0
						PlaySound_Strict(IntroSFX[Rand(8, 10)])
						msg\DeathMsg = Format(GetLocalString("death", "killbat"), SubjectName)
						Kill()
						;[End Block]
					Case "scp148"
						;[Block]
						GiveAchievement(Achv148)
						;[End Block]
					Case "scp860"
						;[Block]
						GiveAchievement(Achv860)
						;[End Block]
					Case "key6"
						;[Block]
						GiveAchievement(AchvKeyCard6)
						;[End Block]
					Case "keyomni"
						;[Block]
						GiveAchievement(AchvOmni)
						;[End Block]
					Case "scp005"
						;[Block]
						GiveAchievement(Achv005)
						;[End Block]
					Case "veryfinevest"
						;[Block]
						CreateMsg(GetLocalString("msg", "vfvest"))
						Return
						;[End Block]
					Case "corrvest"
						;[Block]
						CreateMsg(GetLocalString("msg", "corrvest"))
						Return
						;[End Block]
					Case "firstaid", "finefirstaid", "veryfinefirstaid", "firstaid2"
						;[Block]
						item\State = 0.0
						;[End Block]
					Case "navulti"
						;[Block]
						GiveAchievement(AchvSNAV)
						;[End Block]
					Case "hazmatsuit", "finehazmatsuit", "veryfinehazmatsuit", "hazmatsuit148"
						;[Block]
						CanPickItem = 1
						For z = 0 To MaxItemAmount - 1
							If Inventory(z) <> Null Then
								If Inventory(z)\ItemTemplate\TempName = "hazmatsuit" Lor Inventory(z)\ItemTemplate\TempName = "finehazmatsuit" Lor Inventory(z)\ItemTemplate\TempName = "veryfinehazmatsuit" Lor Inventory(z)\ItemTemplate\TempName = "hazmatsuit148" Then
									CanPickItem = 0
								ElseIf Inventory(z)\ItemTemplate\TempName = "vest" Lor Inventory(z)\ItemTemplate\TempName = "finevest"
									CanPickItem = 2
								EndIf
							EndIf
						Next
						
						If CanPickItem = 0 Then
							CreateMsg(GetLocalString("msg", "twosuit"))
							Return
						ElseIf CanPickItem = 2 Then
							CreateMsg(GetLocalString("msg", "vestsuit"))
							Return
						Else
							SelectedItem = item
						EndIf
						;[End Block]
					Case "vest", "finevest"
						;[Block]
						CanPickItem = 1
						For z = 0 To MaxItemAmount - 1
							If Inventory(z) <> Null Then
								If Inventory(z)\ItemTemplate\TempName = "vest" Lor Inventory(z)\ItemTemplate\TempName = "finevest" Then
									CanPickItem = 0
								ElseIf Inventory(z)\ItemTemplate\TempName = "hazmatsuit" Lor Inventory(z)\ItemTemplate\TempName = "finehazmatsuit" Lor Inventory(z)\ItemTemplate\TempName = "veryfinehazmatsuit" Lor Inventory(z)\ItemTemplate\TempName = "hazmatsuit148"
									CanPickItem = 2
								EndIf
							EndIf
						Next
						
						If CanPickItem = 0 Then
							CreateMsg(GetLocalString("msg", "twovest"))
							Return
						ElseIf CanPickItem = 2 Then
							CreateMsg(GetLocalString("msg", "vestsuit"))
							Return
						Else
							SelectedItem = item
						EndIf
						;[End Block]
				End Select
				
				If item\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[item\ItemTemplate\Sound])
				item\Picked = True
				item\Dropped = -1
				
				item\ItemTemplate\Found = True
				If item\InvSlots > 0 Then
					For z = 0 To item\InvSlots - 1
						If item\SecondInv[z] <> Null Then item\SecondInv[z]\ItemTemplate\Found = True
					Next
				EndIf
				ItemAmount = ItemAmount + 1
				
				Inventory(n) = item
				HideEntity(item\Collider)
				Exit
			EndIf
		Next
		me\SndVolume = Max(1.5, me\SndVolume)
	Else
		CreateMsg(GetLocalString("msg", "cantcarry"))
	EndIf
	
	CatchErrors("PickItem")
End Function

Function DropItem%(item.Items, PlayDropSound% = True)
	
	CatchErrors("Uncaught (DropItem)")
	
	Local n%
	
	If PlayDropSound Then
		If item\ItemTemplate\Sound <> 66 Then PlaySound_Strict(PickSFX[item\ItemTemplate\Sound])
	EndIf
	
	item\Dropped = 1
	
	ShowEntity(item\Collider)
	PositionEntity(item\Collider, EntityX(Camera), EntityY(Camera), EntityZ(Camera))
	RotateEntity(item\Collider, EntityPitch(Camera), EntityYaw(Camera) + Rnd(-20.0, 20.0), 0.0)
	MoveEntity(item\Collider, 0.0, -0.1, 0.1)
	RotateEntity(item\Collider, 0.0, EntityYaw(Camera) + Rnd(-110.0, 110.0), 0.0)
	ResetEntity(item\Collider)
	
	item\Picked = False
	For n = 0 To MaxItemAmount - 1
		If Inventory(n) = item Then
			Inventory(n) = Null
			Exit
		EndIf
	Next
	ItemAmount = ItemAmount - 1
	
	CatchErrors("DropItem")
End Function

Function IsItemGoodFor1162ARC%(itt.ItemTemplates)
	Select itt\TempName
		Case "key0", "key1", "key2", "key3"
			;[Block]
			Return(True)
			;[End Block]
		Case "mastercard", "playcard", "origami", "electronics", "scp420j", "cigarette"
			;[Block]
			Return(True)
			;[End Block]
		Case "vest", "finevest", "gasmask"
			;[Block]
			Return(True)
			;[End Block]
		Case "radio", "18vradio"
			;[Block]
			Return(True)
			;[End Block]
		Case "clipboard", "eyedrops", "nvg"
			;[Block]
			Return(True)
			;[End Block]
		Default
			;[Block]
			If itt\TempName <> "paper" Then
				Return(False)
			ElseIf Instr(itt\Name, "Leaflet")
				Return(False)
			Else
				; ~ If the item is a paper, only allow spawning it if the name contains the word "note" or "log"
				; ~ (Because those are items created recently, which D-9341 has most likely never seen)
				Return(((Not Instr(itt\Name, "Note")) And (Not Instr(itt\Name, "Log"))))
			EndIf
			;[End Block]
	End Select
End Function

Function IsItemInFocus%()
	Select SelectedItem\ItemTemplate\TempName
		Case "nav", "nav300", "nav310", "navulti", "paper", "oldpaper", "badge", "oldbadge", "radio", "18vradio", "fineradio", "veryfineradio", "scp1025"
			;[Block]
			Return(True)
			;[End Block]
	End Select
	Return(False)
End Function

Function CanUseItem%(CanUseWithEyewear% = False, CanUseWithGasMask% = False, CanUseWithHazmat% = False)
	If (Not CanUseWithGasMask) And (wi\GasMask > 0 Lor I_1499\Using > 0) Then
		CreateMsg(GetLocalString("msg", "mask.use"))
		Return(False)
	ElseIf (Not CanUseWithEyewear) And (wi\NightVision > 0 Lor wi\SCRAMBLE)
		CreateMsg(GetLocalString("msg", "gear.use"))
		Return(False)
	ElseIf (Not CanUseWithHazmat) And wi\HazmatSuit > 0
		CreateMsg(GetLocalString("msg", "suit.use"))
		Return(False)
	EndIf
	Return(True)
End Function

; ~ Maybe re-work?
Function PreventItemOverlapping%(SCP1499% = False)
	If wi\HazmatSuit > 0 Then
		CreateMsg(GetLocalString("msg", "suit.use.off"))
		SelectedItem = Null
		Return(False)
	ElseIf SCP1499
		If wi\GasMask > 0
			CreateMsg(GetLocalString("msg", "mask.use.off"))
			SelectedItem = Null
			Return(False)
		ElseIf wi\NightVision > 0
			CreateMsg(GetLocalString("msg", "goggle.use.off"))
			SelectedItem = Null
			Return(False)
		ElseIf  wi\BallisticHelmet
			CreateMsg(GetLocalString("msg", "helmet.use.off"))
			SelectedItem = Null
			Return(False)
		ElseIf wi\SCRAMBLE > 0
			CreateMsg(GetLocalString("msg", "gear.use.off"))
			SelectedItem = Null
			Return(False)
		EndIf
	ElseIf (Not SCP1499) And I_1499\Using > 0
		CreateMsg(GetLocalString("msg", "1499.use.off"))
		SelectedItem = Null
		Return(False)
	EndIf
	Return(True)
End Function

Include "Source Code\914_Core.bb"

; ~ Made a function for SCP-1123 so we don't have to use duplicate code (since picking it up and using it does the same thing)
Function Use1123%()
	; ~ Temp:
	; ~		False: Stay alive
	; ~		True: Die
	
	Local e.Events
	Local Temp%
	
	If I_714\Using = 1 And wi\GasMask <> 4 And wi\HazmatSuit <> 4 Then
		me\LightFlash = 3.0
		PlaySound_Strict(LoadTempSound("SFX\SCP\1123\Touch.ogg"))
		
		Temp = True
		For e.Events = Each Events
			If e\EventID = e_cont2_1123 Then
				If PlayerRoom = e\room Then
					If e\EventState < 1.0 Then ; ~ Start the event
						e\EventState = 1.0
						Temp = False
						Exit
					EndIf
				EndIf
			EndIf
		Next
	Else
		CreateMsg(GetLocalString("msg", "skull.nothappend"))
	EndIf
	
	If Temp Then
		msg\DeathMsg = Format(GetLocalString("death", "1123"), SubjectName)
		Kill()
	EndIf
End Function

; ~ Key Items Constants
;[Block]
Const KEY_CARD_6% = 1
Const KEY_CARD_0% = 2
Const KEY_CARD_1% = 3
Const KEY_CARD_2% = 4
Const KEY_CARD_3% = 5
Const KEY_CARD_4% = 6
Const KEY_CARD_5% = 7
Const KEY_CARD_OMNI% = 8

Const KEY_005% = 9

Const KEY_HAND_WHITE% = -1
Const KEY_HAND_BLACK% = -2
Const KEY_HAND_YELLOW% = -3

Const KEY_860% = -4

Const KEY_MISC% = 0
;[End Block]

; ~ Only for "UseDoor" function
Function GetUsingItem%(item.Items)
	Select item\ItemTemplate\TempName
		Case "key6"
			;[Block]
			Return(KEY_CARD_6)
		Case "key0"
			;[Block]
			Return(KEY_CARD_0)
			;[End Block]
		Case "key1"
			;[Block]
			Return(KEY_CARD_1)
			;[End Block]
		Case "key2"
			;[Block]
			Return(KEY_CARD_2)
			;[End Block]
		Case "key3"
			;[Block]
			Return(KEY_CARD_3)
			;[End Block]
		Case "key4"
			;[Block]
			Return(KEY_CARD_4)
			;[End Block]
		Case "key5"
			;[Block]
			Return(KEY_CARD_5)
			;[End Block]
		Case "keyomni"
			;[Block]
			Return(KEY_CARD_OMNI)
			;[End Block]
		Case "scp005"
			;[Block]
			Return(KEY_005)
			;[End Block]
		Case "hand"
			;[Block]
			Return(KEY_HAND_WHITE)
			;[End Block]
		Case "hand2"
			;[Block]
			Return(KEY_HAND_BLACK)
			;[End Block]
		Case "hand3"
			;[Block]
			Return(KEY_HAND_YELLOW)
			;[End Block]
		Case "scp860"
			;[Block]
			Return(KEY_860)
			;[End Block]
		Default
			;[Block]
			Return(KEY_MISC)
			;[End Block]
	End Select
End Function

Function CreateRandomBattery.Items(x#, y#, z#)
	Local BatteryName$, BatteryTempName$
	Local BatteryChance%, RandomChance%
	
	Select SelectedDifficulty\OtherFactors
		Case SAFE
			;[Block]
			BatteryChance = 10
			;[End Block]
		Case NORMAL
			;[Block]
			BatteryChance = 15
			;[End Block]
		Case HARD
			;[Block]
			BatteryChance = 20
			;[End Block]
		Case EXTREME
			;[Block]
			BatteryChance = 25
			;[End Block]
	End Select
	
	RandomChance = Rand(BatteryChance)
	If RandomChance >= 1 And RandomChance <= 7 Then
		BatteryName = "9V Battery"
		BatteryTempName = "bat"
	ElseIf RandomChance >= 8 And RandomChance < BatteryChance
		BatteryName = "4.5V Battery"
		BatteryTempName = "coarsebat"
	Else
		BatteryName = "18V Battery"
		BatteryTempName = "finebat"
	EndIf
	Return(CreateItem(BatteryName, BatteryTempName, x, y, z))
End Function

;~IDEal Editor Parameters:
;~C#Blitz3D