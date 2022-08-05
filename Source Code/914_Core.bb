; ~ SCP-914 Constants
;[Block]
Const ROUGH% = -2
Const COARSE% = -1
Const ONETOONE% = 0
Const FINE% = 1
Const VERYFINE% = 2
;[End Block]

Function Use914%(item.Items, Setting%, x#, y#, z#)
	me\RefinedItems = me\RefinedItems + 1
	
	Local it.Items, it2.Items, it3.Items, it4.Items, it5.Items, de.Decals, n.NPCs
	Local Remove% = True, i%
	
	Select item\ItemTemplate\TempName
		Case "gasmask", "supergasmask", "gasmask3"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.12)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					If Rand(50) = 1 Then
						it2.Items = CreateItem("SCP-1499", "scp1499", x, y, z)
					Else
						Remove = False
					EndIf
					;[End Block]
				Case FINE, VERYFINE
					;[Block]
					it2.Items = CreateItem("Gas Mask", "supergasmask", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "scp1499", "super1499"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.12)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Gas Mask", "gasmask", x, y, z)
					;[End Block]
				Case FINE
					;[Block]
					it2.Items = CreateItem("SCP-1499", "super1499", x, y, z)
					;[End Block]
				Case VERYFINE
					;[Block]
					n.NPCs = CreateNPC(NPCType1499_1, x, y, z)
					n\State = 1.0 : n\State3 = 1.0
					n\Sound = LoadSound_Strict("SFX\SCP\1499\Triggered.ogg")
					n\SoundCHN = PlaySound2(n\Sound, Camera, n\Collider, 20.0)
					;[End Block]
			End Select
			;[End Block]
		Case "vest"
			;[Block]
			Select Setting
				Case ROUGH
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.12)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case COARSE
					;[Block]
					it2.Items = CreateItem("Corrosive Ballistic Vest", "corrvest", x, y, z)
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Ballistic Helmet", "helmet", x, y, z)
					;[End Block]
				Case FINE
					;[Block]
					it2.Items = CreateItem("Heavy Ballistic Vest", "finevest", x, y, z)
					;[End Block]
				Case VERYFINE
					;[Block]
					it2.Items = CreateItem("Bulky Ballistic Vest", "veryfinevest", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "helmet"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.07)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Ballistic Vest", "vest", x, y, z)
					;[End Block]	
				Case FINE, VERYFINE
					;[Block]
					it2.Items = CreateItem("Heavy Ballistic Vest", "finevest", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "clipboard", "wallet"
			;[Block]
			Select Setting
				Case ROUGH
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.2, 0.8)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					ClearSecondInv(item, 0)
					;[End Block]
				Case COARSE
					;[Block]
					If item\InvSlots > 5 Then
						item\InvSlots = item\InvSlots - 5
						ClearSecondInv(item, item\InvSlots)
					ElseIf item\InvSlots = 5
						item\InvSlots = 1
						ClearSecondInv(item, 1)
					Else
						de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.12, 0.8)
						EntityParent(de\OBJ, PlayerRoom\OBJ)
						ClearSecondInv(item, 0)
					EndIf
					Remove = False
				Case ONETOONE
					;[Block]
					Remove = False
					;[End Block]
				Case FINE
					;[Block]
					If item\InvSlots = 1 Then
						item\InvSlots = 5
					Else
						item\InvSlots = Min(20.0, item\InvSlots + 5.0)
					EndIf
					Remove = False
					;[End Block]
				Case VERYFINE
					;[Block]
					If item\InvSlots = 1 Then
						item\InvSlots = 10
					Else
						item\InvSlots = Min(20.0, item\InvSlots + 10.0)
					EndIf
					Remove = False
					;[End Block]
			End Select
			;[End Block]
		Case "nvg", "supernvg", "finenvg"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.12)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("SCRAMBLE Gear", "scramble", x, y, z)
					it2\State = Rnd(100.0, 1000.0)
					;[End Block]
				Case FINE
					;[Block]
					it2.Items = CreateItem("Night Vision Goggles", "finenvg", x, y, z)
					;[End Block]
				Case VERYFINE
					;[Block]
					If Rand(5) = 1 Then
						it2.Items = CreateItem("SCRAMBLE Gear", "finescramble", x, y, z)
						it2\State = Rnd(100.0, 1000.0)
					Else
						it2.Items = CreateItem("Night Vision Goggles", "supernvg", x, y, z)
						it2\State = Rnd(100.0, 1000.0)
					EndIf
					;[End Block]
			End Select
			;[End Block]
		Case "scramble", "finescramble"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.12)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Night Vision Goggles", "nvg", x, y, z)
					it2\State = Rnd(100.0, 1000.0)
					;[End Block]
				Case FINE, VERYFINE
					;[Block]
					it2.Items = CreateItem("SCRAMBLE Gear", "finescramble", x, y, z)
					it2\State = Rnd(100.0, 1000.0)
					;[End Block]
			End Select
			;[End Block]
		Case "electronics"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.12)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					Remove = False
					;[End Block]
				Case FINE
					;[Block]
					Select Rand(3)
						Case 1
							;[Block]
							it2.Items = CreateItem("Radio Transceiver", "radio", x, y, z)
							it2\State = Rnd(10.0, 100.0)
							;[End Block]
						Case 2
							;[Block]
							If Rand(3) = 1 Then
								it2.Items = CreateItem("S-NAV 300 Navigator", "nav300", x, y, z)
							Else
								it2.Items = CreateItem("S-NAV Navigator", "nav", x, y, z)
								it2\State = Rnd(10.0, 100.0)
							EndIf
							;[End Block]
						Case 3
							;[Block]
							it2.Items = CreateItem("Night Vision Goggles", "nvg", x, y, z)
							it2\State = Rnd(100.0, 1000.0)
							;[End Block]
					End Select
					;[End Block]
				Case VERYFINE
					;[Block]
					Select Rand(3)
						Case 1
							;[Block]
							If Rand(3) = 1 Then
								it2.Items = CreateItem("Radio Transceiver", "fineradio", x, y, z)
							Else
								it2.Items = CreateItem("Radio Transceiver", "veryfineradio", x, y, z)
							EndIf
							;[End Block]
						Case 2
							;[Block]
							If Rand(2) = 1 Then
								it2.Items = CreateItem("S-NAV Navigator Ultimate", "navulti", x, y, z)
							Else
								it2.Items = CreateItem("S-NAV 310 Navigator", "nav310", x, y, z)
								it2\State = Rnd(10.0, 100.0)
							EndIf
							;[End Block]
						Case 3
							;[Block]
							Select Rand(3)
								Case 1
									;[Block]
									it2.Items = CreateItem("Night Vision Goggles", "finenvg", x, y, z)
									;[End Block]
								Case 2
									;[Block]
									it2.Items = CreateItem("Night Vision Goggles", "supernvg", x, y, z)
									it2\State = Rnd(100.0, 1000.0)
									;[End Block]
								Case 3
									;[Block]
									it2.Items = CreateItem("SCRAMBLE Gear", "scramble", x, y, z)
									it2\State = Rnd(100.0, 1000.0)
									;[End Block]
							End Select
							;[End Block]
					End Select
					;[End Block]
			End Select
			;[End Block]
		Case "scp148"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					it2.Items = CreateItem("SCP-148 Ingot", "scp148ingot", x, y, z)
				Case ONETOONE, FINE, VERYFINE
					;[Block]
					Remove = False
					;[End Block]
			End Select
			;[End Block]
		Case "scp148ingot"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					Remove = False
					;[End Block]
				Case ONETOONE, FINE, VERYFINE
					;[Block]
					For it.Items = Each Items
						If it <> item And it\Collider <> 0 And (Not it\Picked) Then
							If DistanceSquared(EntityX(it\Collider, True), x, EntityZ(it\Collider, True), z) < PowTwo(180.0 * RoomScale)
								Select it\ItemTemplate\TempName
									Case "gasmask", "supergasmask"
										;[Block]
										RemoveItem(it)
										it2.Items = CreateItem("Heavy Gas Mask", "gasmask3", x, y, z)
										Exit
										;[End Block]
									Case "hazmatsuit", "hazmatsuit2"
										;[Block]
										RemoveItem(it)
										it2.Items = CreateItem("Heavy Hazmat Suit", "hazmatsuit3", x, y, z)
										Exit
										;[End Block]
								End Select
							EndIf
						EndIf
					Next
					
					If it2 = Null Then
						it2.Items = CreateItem("Metal Panel", "scp148", x, y, z)
					EndIf
					;[End Block]
			End Select
			;[End Block]
		Case "hand", "hand2", "hand3"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_BLOOD_2, x, 8.0 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.12)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE, FINE, VERYFINE
					;[Block]
					If item\ItemTemplate\TempName = "hand" Then
						If Rand(2) = 1 Then
							it2.Items = CreateItem("Black Severed Hand", "hand2", x, y, z)
						Else
							it2.Items = CreateItem("Severed Hand", "hand3", x, y, z)
						EndIf
					ElseIf item\ItemTemplate\TempName = "hand2"
						If Rand(2) = 1 Then
							it2.Items = CreateItem("Severed Hand", "hand", x, y, z)
						Else
							it2.Items = CreateItem("Severed Hand", "hand3", x, y, z)
						EndIf
					Else
						If Rand(2) = 1 Then
							it2.Items = CreateItem("Severed Hand", "hand", x, y, z)
						Else
							it2.Items = CreateItem("Black Severed Hand", "hand2", x, y, z)
						EndIf
					EndIf
					;[End Block]
			End Select
			;[End Block]
		Case "firstaid", "firstaid2", "finefirstaid", "veryfinefirstaid"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.12)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					If Rand(2) = 1 Then
						it2.Items = CreateItem("Blue First Aid Kit", "firstaid2", x, y, z)
					Else
						it2.Items = CreateItem("First Aid Kit", "firstaid", x, y, z)
					EndIf
					;[End Block]
				Case FINE
					;[Block]
					it2.Items = CreateItem("Compact First Aid Kit", "finefirstaid", x, y, z)
					;[End Block]
				Case VERYFINE
					;[Block]
					it2.Items = CreateItem("Strange Bottle", "veryfinefirstaid", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "key0", "key1", "key2", "key3", "key4", "key5", "key6"
			;[Block]
			Local Level% = Right(item\ItemTemplate\TempName, 1)
			
			Select Setting
				Case ROUGH
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.07)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case COARSE
					;[Block]
					If Level = 0 Then
						de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.07)
						EntityParent(de\OBJ, PlayerRoom\OBJ)
					Else
						it2.Items = CreateItem("Level " + (Level - 1) + " Key Card", "key" + (Level - 1), x, y, z)
					EndIf
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Playing Card", "playcard", x, y, z)
					;[End Block]
				Case FINE
					;[Block]
					Select Level
						Case 0
							;[Block]
							Select SelectedDifficulty\OtherFactors
								Case EASY
									;[Block]
									it2.Items = CreateItem("Level 1 Key Card", "key1", x, y, z)
									;[End Block]
								Case NORMAL
									;[Block]
									If Rand(6) = 1 Then
										it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
									Else
										it2.Items = CreateItem("Level 1 Key Card", "key1", x, y, z)
									EndIf
									;[End Block]
								Case HARD
									;[Block]
									If Rand(5) = 1 Then
										it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
									Else
										it2.Items = CreateItem("Level 1 Key Card", "key1", x, y, z)
									EndIf
									;[End Block]
								Case EXTREME
									;[Block]
									If Rand(4) = 1 Then
										it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
									Else
										it2.Items = CreateItem("Level 1 Key Card", "key1", x, y, z)
									EndIf
									;[End Block]
							End Select
							;[End Block]
						Case 1
							;[Block]
							Select SelectedDifficulty\OtherFactors
								Case EASY
									;[Block]
									it2.Items = CreateItem("Level 2 Key Card", "key2", x, y, z)
									;[End Block]
								Case NORMAL
									;[Block]
									If Rand(5) = 1 Then
										it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
									Else
										it2.Items = CreateItem("Level 2 Key Card", "key2", x, y, z)
									EndIf
									;[End Block]
								Case HARD
									;[Block]
									If Rand(4) = 1 Then
										it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
									Else
										it2.Items = CreateItem("Level 2 Key Card", "key2", x, y, z)
									EndIf
									;[End Block]
								Case EXTREME
									;[Block]
									If Rand(3) = 1 Then
										it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
									Else
										it2.Items = CreateItem("Level 2 Key Card", "key2", x, y, z)
									EndIf
									;[End Block]
							End Select
							;[End Block]
						Case 2
							;[Block]
							Select SelectedDifficulty\OtherFactors
								Case EASY
									;[Block]
									it2.Items = CreateItem("Level 3 Key Card", "key3", x, y, z)
									;[End Block]
								Case NORMAL
									;[Block]
									If Rand(4) = 1 Then
										it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
									Else
										it2.Items = CreateItem("Level 3 Key Card", "key3", x, y, z)
									EndIf
									;[End Block]
								Case HARD
									;[Block]
									If Rand(3) = 1 Then
										it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
									Else
										it2.Items = CreateItem("Level 3 Key Card", "key3", x, y, z)
									EndIf
									;[End Block]
								Case EXTREME
									;[Block]
									If Rand(2) = 1 Then
										it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
									Else
										it2.Items = CreateItem("Level 3 Key Card", "key3", x, y, z)
									EndIf
									;[End Block]
							End Select
							;[End Block]
						Case 3
							;[Block]
							Select SelectedDifficulty\OtherFactors
								Case EASY
									;[Block]
									If Rand(10) = 1 Then
										it2.Items = CreateItem("Level 4 Key Card", "key4", x, y, z)
									Else
										it2.Items = CreateItem("Playing Card", "playcard", x, y, z)	
									EndIf
									;[End Block]
								Case NORMAL
									;[Block]
									If Rand(15) = 1 Then
										it2.Items = CreateItem("Level 4 Key Card", "key4", x, y, z)
									Else
										it2.Items = CreateItem("Playing Card", "playcard", x, y, z)	
									EndIf
									;[End Block]
								Case HARD
									;[Block]
									If Rand(20) = 1 Then
										it2.Items = CreateItem("Level 4 Key Card", "key4", x, y, z)
									Else
										it2.Items = CreateItem("Playing Card", "playcard", x, y, z)	
									EndIf
									;[End Block]
								Case EXTREME
									;[Block]
									If Rand(25) = 1 Then
										it2.Items = CreateItem("Level 4 Key Card", "key4", x, y, z)
									Else
										it2.Items = CreateItem("Playing Card", "playcard", x, y, z)	
									EndIf
									;[End Block]
							End Select
							;[End Block]
						Case 4
							;[Block]
							Select SelectedDifficulty\OtherFactors
								Case EASY
									If Rand(4) = 1 Then
										it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
									Else
										it2.Items = CreateItem("Level 5 Key Card", "key5", x, y, z)
									EndIf
								Case NORMAL
									;[Block]
									If Rand(3) = 1 Then
										it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
									Else
										it2.Items = CreateItem("Level 5 Key Card", "key5", x, y, z)
									EndIf
									;[End Block]
								Case HARD
									;[Block]
									If Rand(2) = 1 Then
										it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
									Else
										it2.Items = CreateItem("Level 5 Key Card", "key5", x, y, z)
									EndIf
									;[End Block]
								Case EXTREME
									;[Block]
									If Rand(3) = 1 Then
										it2.Items = CreateItem("Level 5 Key Card", "key5", x, y, z)
									Else
										it2.Items = CreateItem("Playing Card", "playcard", x, y, z)	
									EndIf
									;[End Block]
							End Select
							;[End Block]
						Case 5
							;[Block]
							Local CurrAchvAmount% = 0
							
							For i = 0 To MAXACHIEVEMENTS - 1
								If achv\Achievement[i] = True
									CurrAchvAmount = CurrAchvAmount + 1
								EndIf
							Next
							
							Select SelectedDifficulty\OtherFactors
								Case EASY
									;[Block]
									If Rand(0, ((MAXACHIEVEMENTS - 1) * 3) - ((CurrAchvAmount - 1) * 3)) = 0 Then
										it2.Items = CreateItem("Key Card Omni", "keyomni", x, y, z)
									Else
										If Rand(10) = 1 Then
											it2.Items = CreateItem("Level 6 Key Card", "key6", x, y, z)
										Else
											it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
										EndIf
									EndIf
									;[End Block]
								Case NORMAL
									;[Block]
									If Rand(0, ((MAXACHIEVEMENTS - 1) * 4) - ((CurrAchvAmount - 1) * 3)) = 0 Then
										it2.Items = CreateItem("Key Card Omni", "keyomni", x, y, z)
									Else
										If Rand(15) = 1 Then
											it2.Items = CreateItem("Level 6 Key Card", "key6", x, y, z)
										Else
											it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
										EndIf
									EndIf
									;[End Block]
								Case HARD
									;[Block]
									If Rand(0, ((MAXACHIEVEMENTS - 1) * 5) - ((CurrAchvAmount - 1) * 3)) = 0 Then
										it2.Items = CreateItem("Key Card Omni", "keyomni", x, y, z)
									Else
										If Rand(20) = 1 Then
											it2.Items = CreateItem("Level 6 Key Card", "key6", x, y, z)
										Else
											it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
										EndIf
									EndIf
									;[End Block]
								Case EXTREME
									;[Block]
									If Rand(0, ((MAXACHIEVEMENTS - 1) * 6) - ((CurrAchvAmount - 1) * 3)) = 0 Then
										it2.Items = CreateItem("Key Card Omni", "keyomni", x, y, z)
									Else
										If Rand(25) = 1 Then
											it2.Items = CreateItem("Level 6 Key Card", "key6", x, y, z)
										Else
											it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
										EndIf
									EndIf
									;[End Block]
							End Select
							;[End Block]
						Case 6
							;[Block]
							Select SelectedDifficulty\OtherFactors
								Case EASY
									;[Block]
									If Rand(4) = 1 Then
										it2.Items = CreateItem("Key Card Omni", "keyomni", x, y, z)
									Else
										it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
									EndIf
									;[End Block]
								Case NORMAL
									;[Block]
									If Rand(5) = 1 Then
										it2.Items = CreateItem("Key Card Omni", "keyomni", x, y, z)
									Else
										it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
									EndIf
									;[End Block]
								Case HARD
									;[Block]
									If Rand(6) = 1 Then
										it2.Items = CreateItem("Key Card Omni", "keyomni", x, y, z)
									Else
										it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
									EndIf
									;[End Block]
								Case EXTREME
									;[Block]
									If Rand(7) = 1 Then
										it2.Items = CreateItem("Key Card Omni", "keyomni", x, y, z)
									Else
										it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
									EndIf
									;[End Block]
							End Select
							;[End Block]
					End Select
					;[End Block]
				Case VERYFINE
					;[Block]
					CurrAchvAmount = 0
					For i = 0 To MAXACHIEVEMENTS - 1
						If achv\Achievement[i] = True
							CurrAchvAmount = CurrAchvAmount + 1
						EndIf
					Next
					
					Select SelectedDifficulty\OtherFactors
						Case EASY
							;[Block]
							If Rand(0, ((MAXACHIEVEMENTS - 1) * 3) - ((CurrAchvAmount - 1) * 3)) = 0
								it2.Items = CreateItem("Key Card Omni", "keyomni", x, y, z)
							Else
								If Rand(20) = 1 Then
									it2.Items = CreateItem("Level 6 Key Card", "key6", x, y, z)
								Else
									it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
								EndIf
							EndIf
							;[End Block]
						Case NORMAL
							;[Block]
							If Rand(0, ((MAXACHIEVEMENTS - 1) * 4) - ((CurrAchvAmount - 1) * 3)) = 0
								it2.Items = CreateItem("Key Card Omni", "keyomni", x, y, z)
							Else
								If Rand(25) = 1 Then
									it2.Items = CreateItem("Level 6 Key Card", "key6", x, y, z)
								Else
									it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
								EndIf
							EndIf
							;[End Block]
						Case HARD
							;[Block]
							If Rand(0, ((MAXACHIEVEMENTS - 1) * 5) - ((CurrAchvAmount - 1) * 3)) = 0
								it2.Items = CreateItem("Key Card Omni", "keyomni", x, y, z)
							Else
								If Rand(30) = 1 Then
									it2.Items = CreateItem("Level 6 Key Card", "key6", x, y, z)
								Else
									it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
								EndIf
							EndIf
							;[End Block]
						Case EXTREME
							;[Block]
							If Rand(0, ((MAXACHIEVEMENTS - 1) * 6) - ((CurrAchvAmount - 1) * 3)) = 0
								it2.Items = CreateItem("Key Card Omni", "keyomni", x, y, z)
							Else
								If Rand(35) = 1 Then
									it2.Items = CreateItem("Level 6 Key Card", "key6", x, y, z)
								Else
									it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
								EndIf
							EndIf
							;[End Block]
					End Select
					;[End Block]
			End Select
			;[End Block]
		Case "keyomni"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.07)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					If Rand(2) = 1 Then
						it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
					Else
						it2.Items = CreateItem("Playing Card", "playcard", x, y, z)			
					EndIf	
					;[End Block]
				Case FINE, VERYFINE
					;[Block]
					Select SelectedDifficulty\OtherFactors
						Case EASY
							;[Block]
							If Rand(4) = 1 Then
								it2.Items = CreateItem("Level 6 Key Card", "key6", x, y, z)
							Else
								it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
							EndIf
							;[End Block]
						Case NORMAL
							;[Block]
							If Rand(5) = 1 Then
								it2.Items = CreateItem("Level 6 Key Card", "key6", x, y, z)
							Else
								it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
							EndIf
							;[End Block]
						Case HARD
							;[Block]
							If Rand(6) = 1 Then
								it2.Items = CreateItem("Level 6 Key Card", "key6", x, y, z)
							Else
								it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
							EndIf
							;[End Block]
						Case EXTREME
							;[Block]
							If Rand(7) = 1 Then
								it2.Items = CreateItem("Level 6 Key Card", "key6", x, y, z)
							Else
								it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
							EndIf
							;[End Block]
					End Select
					;[End Block]
			End Select		
			;[End Block]
		Case "playcard"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.07)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
					;[End Block]
				Case FINE
					;[Block]
					it2.Items = CreateItem("Level 0 Key Card", "key0", x, y, z)
					;[End Block]
				Case VERYFINE
					;[Block]
					it2.Items = CreateItem("Level 1 Key Card", "key1", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "coin", "25ct"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.07)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					If Rand(2) = 1 Then
					it3.Items = CreateItem("Quarter", "25ct", x, y, z)
					EntityType(it3\Collider, HIT_ITEM)
					Else
					it3.Items = CreateItem("Coin", "coin", x, y, z)
					EntityType(it3\Collider, HIT_ITEM)
					EndIf
					;[End Block]
				Case FINE
					;[Block]
					it2.Items = CreateItem("Mastercard", "mastercard", x, y, z)
					;[End Block]
				Case VERYFINE
					;[Block]
					it2.Items = CreateItem("Level 0 Key Card", "key0", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "mastercard"
			;[Block]
			Select Setting
				Case ROUGH
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.07)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case COARSE
					;[Block]
					it2.Items = CreateItem("Quarter", "25ct", x, y, z)
					
					If Rand(2) = 1 Then
						it3.Items = CreateItem("Quarter", "25ct", x, y, z)
						EntityType(it3\Collider, HIT_ITEM)
					EndIf
					
					If Rand(3) = 1 Then
						it4.Items = CreateItem("Quarter", "25ct", x, y, z)
						EntityType(it4\Collider, HIT_ITEM)
					EndIf
					
					If Rand(4) = 1 Then
						it5.Items = CreateItem("Quarter", "25ct", x, y, z)
						EntityType(it5\Collider, HIT_ITEM)
					EndIf
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Playing Card", "playcard", x, y, z)
					;[End Block]
				Case FINE
					;[Block]
					it2.Items = CreateItem("Level 0 Key Card", "key0", x, y, z)
					;[End Block]
				Case VERYFINE
					;[Block]
					it2.Items = CreateItem("Level 1 Key Card", "key1", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "badge"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.07)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE, FINE, VERYFINE
					;[Block]
					it2.Items = CreateItem("Level 2 Key Card", "key2", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "nav", "nav300", "nav310", "navulti"
			;[Block]
			Select Setting
				Case ROUGH
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.12)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case COARSE
					;[Block]
					it2.Items = CreateItem("Electronical Components", "electronics", x, y, z)
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("S-NAV Navigator", "nav", x, y, z)
					it2\State = Rnd(10.0, 100.0)
					;[End Block]
				Case FINE
					;[Block]
					If Rand(3) = 1 Then
						it2.Items = CreateItem("S-NAV 300 Navigator", "nav300", x, y, z)
					Else
						it2.Items = CreateItem("S-NAV 310 Navigator", "nav310", x, y, z)
						it2\State = Rnd(10.0, 100.0)
					EndIf
					;[End Block]
				Case VERYFINE
					;[Block]
					it2.Items = CreateItem("S-NAV Navigator Ultimate", "navulti", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "radio", "18vradio", "fineradio", "veryfineradio"
			;[Block]
			Select Setting
				Case ROUGH
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.12)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case COARSE
					;[Block]
					it2.Items = CreateItem("Electronical Components", "electronics", x, y, z)
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Radio Transceiver", "18vradio", x, y, z)
					it2\State = Rnd(10.0, 100.0)
					;[End Block]
				Case FINE
					;[Block]
					it2.Items = CreateItem("Radio Transceiver", "fineradio", x, y, z)
					;[End Block]
				Case VERYFINE
					;[Block]
					it2.Items = CreateItem("Radio Transceiver", "veryfineradio", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "scp513"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					PlaySound_Strict(LoadTempSound("SFX\SCP\513\914Refine.ogg"))
					For n.NPCs = Each NPCs
						If n\NPCType = NPCType513_1 Then RemoveNPC(n)
					Next
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.010, z, 90.0, Rnd(360.0), 0.0, 0.2, 0.8)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE, FINE, VERYFINE
					;[Block]
					it2.Items = CreateItem("SCP-513", "scp513", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "scp420j", "cigarette", "joint", "scp420s"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.010, z, 90.0, Rnd(360.0), 0.0, 0.2, 0.8)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Cigarette", "cigarette", x + 1.5, y + 0.5, z + 1.0)
					;[End Block]
				Case FINE
					;[Block]
					it2.Items = CreateItem("Joint", "joint", x + 1.5, y + 0.5, z + 1.0)
					;[End Block]
				Case VERYFINE
					;[Block]
					it2.Items = CreateItem("Smelly Joint", "scp420s", x + 1.5, y + 0.5, z + 1.0)
					;[End Block]
			End Select
			;[End Block]
		Case "badbat"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.010, z, 90.0, Rnd(360.0), 0.0, 0.2, 0.8)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					Remove = False
					;[End Block]
				Case FINE
					;[Block]
					it2.Items = CreateItem("9V Battery", "bat", x, y, z)
					;[End Block]
				Case VERYFINE
					;[Block]
					it2.Items = CreateItem("18V Battery", "finebat", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "bat"
			;[Block]
			Select Setting
				Case ROUGH
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.010, z, 90.0, Rnd(360.0), 0.0, 0.2, 0.8)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case COARSE
					;[Block]
					it2.Items = CreateItem("4.5V Battery", "badbat", x, y, z)
					;[End Block]
				Case ONETOONE
					;[Block]
					Remove = False
					;[End Block]
				Case FINE
					;[Block]
					it2.Items = CreateItem("18V Battery", "finebat", x, y, z)
					;[End Block]
				Case VERYFINE
					;[Block]
					If Rand(5) = 1 Then
						it2.Items = CreateItem("999V Battery", "superbat", x, y, z)
					Else
						it2.Items = CreateItem("Strange Battery", "killbat", x, y, z)
					EndIf
					;[End Block]
			End Select
			;[End Block]
		Case "finebat"
			;[Block]
			Select Setting
				Case ROUGH
					;[Block]
					it2.Items = CreateItem("4.5V Battery", "badbat", x, y, z)
					;[End Block]
				Case COARSE
					;[Block]
					it2.Items = CreateItem("9V Battery", "bat", x, y, z)
					;[End Block]
				Case ONETOONE
					;[Block]
					Remove = False
					;[End Block]
				Case FINE
					;[Block]
					it2.Items = CreateItem("18V Battery", "finebat", x, y, z)
					;[End Block]
				Case VERYFINE
					;[Block]
					If Rand(3) = 1 Then
						it2.Items = CreateItem("999V Battery", "superbat", x, y, z)
					Else
						it2.Items = CreateItem("Strange Battery", "killbat", x, y, z)
					EndIf
					;[End Block]
			End Select
			;[End Block]
		Case "superbat", "killbat"
			;[Block]
			Select Setting
				Case ROUGH
					;[Block]
					it2.Items = CreateItem("4.5V Battery", "badbat", x, y, z)
					;[End Block]
				Case COARSE
					;[Block]
					it2.Items = CreateItem("9V Battery", "bat", x, y, z)
					;[End Block]
				Case ONETOONE, FINE, VERYFINE
					;[Block]
					it2.Items = CreateItem("Strange Battery", "killbat", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "eyedrops", "eyedrops2", "fineeyedrops", "supereyedrops"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.010, z, 90.0, Rnd(360.0), 0.0, 0.2, 0.8)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					If Rand(2) = 1 Then
						it2.Items = CreateItem("ReVision Eyedrops", "eyedrops", x, y, z)
					Else
						it2.Items = CreateItem("RedVision Eyedrops", "eyedrops2", x, y, z)
					EndIf
					;[End Block]
				Case FINE
					;[Block]
					it2.Items = CreateItem("Eyedrops", "fineeyedrops", x, y, z)
					;[End Block]
				Case VERYFINE
					;[Block]
					it2.Items = CreateItem("Eyedrops", "supereyedrops", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "hazmatsuit", "hazmatsuit3"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.010, z, 90.0, Rnd(360.0), 0.0, 0.2, 0.8)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Hazmat Suit", "hazmatsuit", x, y, z)
					;[End Block]
				Case FINE, VERYFINE
					;[Block]
					it2.Items = CreateItem("Hazmat Suit", "hazmatsuit2", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "hazmatsuit2"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.010, z, 90.0, Rnd(360.0), 0.0, 0.2, 0.8)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Hazmat Suit", "hazmatsuit", x, y, z)
					;[End Block]
				Case FINE, VERYFINE
					;[Block]
					it2.Items = CreateItem("Syringe", "syringeinf", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "syringe"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.07)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Compact First Aid Kit", "finefirstaid", x, y, z)
					;[End Block]
				Case FINE
					;[Block]
					it2.Items = CreateItem("Syringe", "finesyringe", x, y, z)
					;[End Block]
				Case VERYFINE
					;[Block]
					If Rand(3) = 1 Then
						it2.Items = CreateItem("Syringe", "veryfinesyringe", x, y, z)
					Else
						it2.Items = CreateItem("Syringe", "syringeinf", x, y, z)
					EndIf
					;[End Block]
			End Select
			;[End Block]
		Case "finesyringe"
			;[Block]
			Select Setting
				Case ROUGH
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.07)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case COARSE
					;[Block]
					it2.Items = CreateItem("First Aid Kit", "firstaid", x, y, z)
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Blue First Aid Kit", "firstaid2", x, y, z)
					;[End Block]
				Case FINE, VERYFINE
					;[Block]
					If Rand(3) = 1 Then
						it2.Items = CreateItem("Syringe", "veryfinesyringe", x, y, z)
					Else
						it2.Items = CreateItem("Syringe", "syringeinf", x, y, z)
					EndIf
					;[End Block]
			End Select
			;[End Block]
		Case "veryfinesyringe"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					it2.Items = CreateItem("Electronical Components", "electronics", x, y, z)	
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Strange Bottle", "veryfinefirstaid", x, y, z)
					;[End Block]
				Case FINE
					;[Block]
					it2.Items = CreateItem("Syringe", "syringeinf", x, y, z)
					;[End Block]
				Case VERYFINE
					;[Block]
					If Rand(2) = 1 Then
						n.NPCs = CreateNPC(NPCType008_1, x, y, z)
						n\State = 2.0
					Else
						it2.Items = CreateItem("Syringe", "syringeinf", x, y, z)
					EndIf
					;[End Block]
			End Select
		Case "syringeinf"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8 * RoomScale + 0.005, z, 90.0, Rnd(360.0), 0.0, 0.07)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					n.NPCs = CreateNPC(NPCType008_1, x, y, z)
					n\State = 2.0
					;[End Block]	
				Case FINE
					;[Block]
					it2.Items = CreateItem("Syringe", "syringe", x, y, z)
					;[End Block]
				Case VERYFINE
					;[Block]
					If Rand(4) = 1 Then
						it2.Items = CreateItem("Blue First Aid Kit", "firstaid2", x, y, z)
					Else
						it2.Items = CreateItem("Syringe", "finesyringe", x, y, z)
					EndIf
					;[End Block]
			End Select
		Case "scp500pill", "scp500pilldeath", "pill"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.010, z, 90.0, Rnd(360.0), 0.0, 0.2, 0.8)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Pill", "pill", x, y, z)
					;[End Block]
				Case FINE
					;[Block]
					Local NO427Spawn% = False
					
					For it3.Items = Each Items
						If it3\ItemTemplate\TempName = "scp427" Then
							NO427Spawn = True
							Exit
						EndIf
					Next
					If (Not NO427Spawn) Then
						it2.Items = CreateItem("SCP-427", "scp427", x, y, z)
					Else
						it2.Items = CreateItem("Upgraded Pill", "scp500pilldeath", x, y, z)
					EndIf
					;[End Block]
				Case VERYFINE
					;[Block]
					it2.Items = CreateItem("Upgraded Pill", "scp500pilldeath", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "origami"
			Select Setting
				Case ROUGH
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.010, z, 90.0, Rnd(360.0), 0.0, 0.2, 0.8)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case COARSE
					;[Block]
					it2.Items = CreateItem("Blank Paper", "paper", x, y, z)
					;[End Block]
				Case ONETOONE, VERYFINE, FINE
					;[Block]
					it2.Items = CreateItem("Document SCP-" + GetRandDocument(), "paper", x, y, z)
					;[End Block]
			End Select
		Case "cup"
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.010, z, 90.0, Rnd(360.0), 0.0, 0.2, 0.8)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Cup", "cup", x, y, z, 255.0 - item\R, 255.0 - item\G, 255.0 - item\B)
					it2\Name = item\Name
					it2\State = item\State
					;[End Block]
				Case FINE
					;[Block]
					it2.Items = CreateItem("Cup", "cup", x, y, z, Min(item\R * Rnd(0.9, 1.1), 255.0), Min(item\G * Rnd(0.9, 1.1), 255.0), Min(item\B * Rnd(0.9, 1.1), 255.0))
					it2\Name = item\Name
					it2\State = item\State + 1.0
					;[End Block]
				Case VERYFINE
					;[Block]
					it2.Items = CreateItem("Cup", "cup", x, y, z, Min(item\R * Rnd(0.5, 1.5), 255.0), Min(item\G * Rnd(0.5, 1.5), 255.0), Min(item\B * Rnd(0.5, 1.5), 255.0))
					it2\Name = item\Name
					it2\State = item\State * 2.0
					If Rand(5) = 1 Then me\ExplosionTimer = 135.0
					msg\DeathMsg = "NOOO THE CUP BETRAYED ME"
					;[End Block]
			End Select	
			;[End Block]
		Case "paper"
			;[Block]
			Select Setting
				Case ROUGH
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.010, z, 90.0, Rnd(360.0), 0.0, 0.2, 0.8)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case COARSE
					;[Block]
					it2.Items = CreateItem("Blank Paper", "paper", x, y, z)
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Document SCP-" + GetRandDocument(), "paper", x, y, z)
					;[End Block]
				Case FINE, VERYFINE
					;[Block]
					it2.Items = CreateItem("Origami", "origami", x, y, z)
					;[End Block]
			End Select
			;[End Block]
		Case "scp1025"
			Remove = False
			Select Setting
				Case ROUGH
					;[Block]
					If item\State2 > 0.0 Then
						item\State2 = -1.0
					Else
						de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.010, z, 90.0, Rnd(360.0), 0.0, 0.2, 0.8)
						EntityParent(de\OBJ, PlayerRoom\OBJ)
						Remove = True
					EndIf
					;[End Block]
				Case COARSE
					;[Block]
					item\State2 = Max(-1.0, item\State2 - 1.0)
					;[End Block]
				Case ONETOONE
					;[Block]
					it2.Items = CreateItem("Book", "book", x, y, z)
					;[End Block]
				Case FINE
					;[Block]
					item\State2 = Min(1.0, item\State2 + 1.0)
					;[End Block]
				Case VERYFINE
					;[Block]
					item\State2 = 2.0
					;[End Block]
			End Select
		Case "book"
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.010, z, 90.0, Rnd(360.0), 0.0, 0.2, 0.8)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE
					;[Block]
					If Rand(3) = 1 Then
						it2.Items = CreateItem("SCP-1025", "scp1025", x, y, z) ; ~ I know that this can be exploited to get a SCP-1025 reset, but this effort makes it seem fair to me -- Salvage
					Else
						Remove = False
					EndIf
					;[End Block]
				Case FINE, VERYFINE
					;[Block]
					Remove = False
					;[End Block]
			End Select
		Default
			;[Block]
			Select Setting
				Case ROUGH, COARSE
					;[Block]
					de.Decals = CreateDecal(DECAL_CORROSIVE_1, x, 8.0 * RoomScale + 0.010, z, 90.0, Rnd(360.0), 0.0, 0.2, 0.8)
					EntityParent(de\OBJ, PlayerRoom\OBJ)
					;[End Block]
				Case ONETOONE, FINE, VERYFINE
					;[Block]
					Remove = False
					;[End Block]
			End Select
			;[End Block]
	End Select
	
	If Remove Then
		RemoveItem(item)
	Else
		PositionEntity(item\Collider, x, y, z)
		ResetEntity(item\Collider)
	EndIf
	
	If it2 <> Null Then EntityType(it2\Collider, HIT_ITEM)
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D