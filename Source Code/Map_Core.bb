RenderLoading(45, "MATERIALS CORE")

Include "Source Code\Materials_Core.bb"

RenderLoading(50, "TEXTURE CACHE CORE")

Include "Source Code\Texture_Cache_Core.bb"

Type Props
	Field Name$
	Field OBJ%
	Field x#, y#, z#
	Field Pitch#, Yaw#, Roll#
	Field ScaleX#, ScaleY#, ScaleZ#
	Field room.Rooms
End Type

Type TempProps
	Field Name$
	Field x#, y#, z#
	Field Pitch#, Yaw#, Roll#
	Field ScaleX#, ScaleY#, ScaleZ#
	Field RoomTemplate.RoomTemplates
End Type

Function CheckForPropModel%(File$)
	Local Path$ = "GFX\map\Props\"
	Local Format$ = ".b3d"
	
	Select File
		Case Path + "button" + Format
			;[Block]
			Return(CopyEntity(d_I\ButtonModelID[BUTTON_DEFAULT]))
			;[End Block]
		Case Path + "buttonkeycard" + Format
			;[Block]
			Return(CopyEntity(d_I\ButtonModelID[BUTTON_KEYCARD]))
			;[End Block]
		Case Path + "door01" + Format
			;[Block]
			Return(CopyEntity(d_I\DoorModelID[DOOR_DEFAULT_MODEL]))
			;[End Block]
		;Case Path + "doorwindow.b3d"
		;	;[Block]
		;	Return(CopyEntity(d_I\DoorModelID[DOOR_WINDOWED_MODEL]))
		;	;[End Block]
		Case Path + "contdoorleft" + Format
			;[Block]
			Return(CopyEntity(d_I\DoorModelID[DOOR_BIG_MODEL_1]))
			;[End Block]
		Case Path + "contdoorright" + Format
			;[Block]
			Return(CopyEntity(d_I\DoorModelID[DOOR_BIG_MODEL_2]))
			;[End Block]
		Case Path + "doorframe" + Format
			;[Block]
			Return(CopyEntity(d_I\DoorFrameModelID[DOOR_DEFAULT_FRAME_MODEL]))
			;[End Block]
		Case Path + "contdoorframe" + Format
			;[Block]
			Return(CopyEntity(d_I\DoorFrameModelID[DOOR_BIG_FRAME_MODEL]))
			;[End Block]
		Case Path + "leverbase" + Format
			;[Block]
			Return(CopyEntity(lvr_I\LeverModelID[LEVER_BASE_MODEL]))
			;[End Block]
		Case Path + "leverhandle" + Format
			;[Block]
			Return(CopyEntity(lvr_I\LeverModelID[LEVER_HANDLE_MODEL]))
			;[End Block]
		Default
			;[Block]
			Return(LoadMesh_Strict(File))
			;[End Block]
	End Select
End Function

Function CreateProp.Props(Name$, x#, y#, z#, Pitch#, Yaw#, Roll#, ScaleX#, ScaleY#, ScaleZ#, room.Rooms)
	Local p.Props, p2.Props
	
	p.Props = New Props
	For p2.Props = Each Props
		If p2 <> p Then
			If p2\Name = Name Then
				p\OBJ = CopyEntity(p2\OBJ)
				Exit
			EndIf
		EndIf
	Next
	
	p\Name = Name
	p\room = room
	
	If (Not p\OBJ) Then p\OBJ = CheckForPropModel(Name) ; ~ A hacky optimization (just copy models that loaded as variable). Also fixes models folder if the CBRE was used
	PositionEntity(p\OBJ, x, y, z)
	If room <> Null Then
		EntityParent(p\OBJ, room\OBJ)
	EndIf
	RotateEntity(p\OBJ, Pitch, Yaw, Roll)
	ScaleEntity(p\OBJ, ScaleX, ScaleY, ScaleZ)
	EntityType(p\OBJ, HIT_MAP)
	EntityPickMode(p\OBJ, 2)
	
	Return(p)
End Function

Function HideProps%(room.Rooms)
	Local p.Props
	
	For p.Props = Each Props
		If p\room = room Then
			HideEntity(p\OBJ)
		EndIf
	Next
End Function

Function ShowProps%(room.Rooms)
	Local p.Props
	
	For p.Props = Each Props
		If p\room = room Then
			ShowEntity(p\OBJ)
		EndIf
	Next
End Function

Function HideAlphaProps%(room.Rooms)
	Local p.Props
	
	For p.Props = Each Props
		If p\room = room Then
			EntityAlpha(p\OBJ, 0.0)
		EndIf
	Next
End Function

Function ShowAlphaProps%(room.Rooms)
	Local p.Props
	
	For p.Props = Each Props
		If p\room = room Then
			EntityAlpha(p\OBJ, 1.0)
		EndIf
	Next
End Function

Global LightVolume#, TempLightVolume#

Function AddLight%(room.Rooms, x#, y#, z#, lType%, Range#, R%, G%, B%)
	Local i%
	
	If room <> Null Then
		For i = 0 To MaxRoomLights - 1
			If (Not room\Lights[i]) Then
				room\Lights[i] = CreateLight(lType)
				LightRange(room\Lights[i], Range)
				LightColor(room\Lights[i], R, G, B)
				PositionEntity(room\Lights[i], x, y, z, True)
				EntityParent(room\Lights[i], room\OBJ)
				HideEntity(room\Lights[i])
				
				room\LightSprites[i] = CreateSprite()
				PositionEntity(room\LightSprites[i], x, y, z)
				ScaleSprite(room\LightSprites[i], 0.13 , 0.13)
				EntityTexture(room\LightSprites[i], misc_I\LightSpriteID[LIGHT_SPRITE_DEFAULT])
				EntityFX(room\LightSprites[i], 1 + 8)
				EntityBlend(room\LightSprites[i], 3)
				EntityColor(room\LightSprites[i], R, G, B)
				EntityParent(room\LightSprites[i], room\OBJ)
				
				room\LightSprites2[i] = CreateSprite()
				PositionEntity(room\LightSprites2[i], x, y, z)
				ScaleSprite(room\LightSprites2[i], Rnd(0.36, 0.4), Rnd(0.36, 0.4))
				EntityTexture(room\LightSprites2[i], misc_I\AdvancedLightSprite)
				EntityFX(room\LightSprites2[i], 1 + 8)
				EntityBlend(room\LightSprites2[i], 3)
				EntityOrder(room\LightSprites2[i], -1)
				EntityColor(room\LightSprites2[i], R, G, B)
				EntityParent(room\LightSprites2[i], room\OBJ)
				RotateEntity(room\LightSprites2[i], 0.0, 0.0, Rnd(360.0))
				SpriteViewMode(room\LightSprites2[i], 1)
				HideEntity(room\LightSprites2[i])
				
				room\LightIntensity[i] = (R + G + B) / 255.0 / 3.0
				room\LightR[i] = R
				room\LightG[i] = G
				room\LightB[i] = B
				
				room\MaxLights = room\MaxLights + 1
				
				Return(room\Lights[i])
			EndIf
		Next
	Else
		Local Light%, Sprite%
		
		Light = CreateLight(lType)
		LightRange(Light, Range)
		LightColor(Light, R, G, B)
		PositionEntity(Light, x, y, z, True)
		
		Sprite = CreateSprite()
		PositionEntity(Sprite, x, y, z)
		ScaleSprite(Sprite, 0.13 , 0.13)
		EntityTexture(Sprite, misc_I\LightSpriteID[LIGHT_SPRITE_DEFAULT])
		EntityFX(Sprite, 1 + 8)
		EntityBlend(Sprite, 3)
		EntityColor(Sprite, R, G, B)
		
		Return(Light)
	EndIf
End Function

Type LightTemplates
	Field RoomTemplate.RoomTemplates
	Field lType%
	Field x#, y#, z#
	Field Range#
	Field R%, G%, B%
	Field Pitch#, Yaw#
	Field InnerConeAngle%, OuterConeAngle#
End Type 

Function AddTempLight.LightTemplates(rt.RoomTemplates, x#, y#, z#, lType%, Range#, R%, G%, B%)
	Local lt.LightTemplates
	
	lt.LightTemplates = New LightTemplates
	lt\RoomTemplate = rt
	lt\x = x
	lt\y = y
	lt\z = z
	lt\lType = lType
	lt\Range = Range
	lt\R = R
	lt\G = G
	lt\B = B
	
	Return(lt)
End Function

Global UpdateRoomLightsTimer# = 0.0

Function UpdateRoomLights%()
	If SecondaryLightOn > 0.5 Then
		UpdateRoomLightsTimer = UpdateRoomLightsTimer + fps\Factor[0]
		If UpdateRoomLightsTimer >= 8.0 Then
			UpdateRoomLightsTimer = 0.0
		EndIf
	EndIf
End Function

Function RenderRoomLights%(Cam%)
	Local r.Rooms, i%, Random#, Alpha#
	
	For r.Rooms = Each Rooms
		If r\Dist < HideDistance * 0.7 Lor r = PlayerRoom Then
			For i = 0 To r\MaxLights - 1
				If r\Lights[i] <> 0 Then
					If SecondaryLightOn > 0.5 Then
						If Cam = Camera Then ; ~ The lights are rendered by player's cam
							If UpdateRoomLightsTimer = 0.0 Then
								If EntityDistanceSquared(Camera, r\Lights[i]) < 72.25 Then
									If EntityHidden(r\Lights[i]) Then ShowEntity(r\Lights[i])
									If EntityVisible(Camera, r\Lights[i]) Then
										If EntityHidden(r\LightSprites[i]) Then ShowEntity(r\LightSprites[i])
										
										If opt\AdvancedRoomLights Then
											Alpha = 1.0 - Max(Min(((EntityDistance(Camera, r\Lights[i]) + 0.5) / 7.5), 1.0), 0.0)
											
											If Alpha > 0.0 Then
												If EntityHidden(r\LightSprites2[i]) Then ShowEntity(r\LightSprites2[i])
												EntityOrder(r\LightSprites2[i], -1)
												EntityAlpha(r\LightSprites2[i], Max(3.0 * (BRIGHTNESS / 255.0) * (r\LightIntensity[i] / 2.0), 1.0) * Alpha)
												
												Random = Rnd(0.36, 0.4)
												ScaleSprite(r\LightSprites2[i], Random, Random)
											Else
												; ~ Instead of rendering the sprite invisible, just hiding it if the player is far away from it
												If (Not EntityHidden(r\LightSprites2[i])) Then HideEntity(r\LightSprites2[i])
											EndIf
										Else
											; ~ The additional sprites option is disable, hide the sprites
											If (Not EntityHidden(r\LightSprites2[i])) Then HideEntity(r\LightSprites2[i])
										EndIf
									Else
										; ~ Hide the sprites, because they aren't visible
										If (Not EntityHidden(r\LightSprites[i])) Then
											HideEntity(r\LightSprites[i])
											If opt\AdvancedRoomLights Then HideEntity(r\LightSprites2[i])
										EndIf
									EndIf
								Else
									; ~ Hide the sprites, because they are too far
									If (Not EntityHidden(r\Lights[i])) Then
										HideEntity(r\Lights[i])
										HideEntity(r\LightSprites[i])
										If opt\AdvancedRoomLights Then HideEntity(r\LightSprites2[i])
									EndIf
								EndIf
							EndIf
						Else
							; ~ This will make the lightsprites not glitch through the wall when they are rendered by the cameras
							If opt\AdvancedRoomLights Then EntityOrder(r\LightSprites2[i], 0)
						EndIf
					Else
						Return ; ~ The lights off
					EndIf
				Else
					Exit
				EndIf
			Next
		EndIf
	Next
End Function

Global AmbientLightRoomTex%, AmbientLightRoomVal%

Function AmbientLightRooms%(Value% = 0)
	If Value = AmbientLightRoomVal Then Return
	AmbientLightRoomVal = Value
	
	Local OldBuffer% = BackBuffer() ; ~ Probably shouldn't make assumptions here but who cares, why wouldn't it use the BackBuffer()
	
	SetBuffer(TextureBuffer(AmbientLightRoomTex))
	ClsColor(Value, Value, Value)
	Cls()
	ClsColor(0, 0, 0)
	SetBuffer(OldBuffer)
End Function

Const RoomScale# = 8.0 / 2048.0

Function LoadRMesh%(File$, rt.RoomTemplates)
	CatchErrors("Uncaught (LoadRMesh)")
	
	Local mat.Materials
	
	ClsColor(0, 0, 0)
	
	; ~ Read the file
	Local f% = ReadFile(File)
	Local i%, j%, k%, x#, y#, z#, Yaw#, Pitch#, Roll#
	Local Vertex%
	Local Temp1i%, Temp2i%, Temp3i%
	Local Temp1#, Temp2#, Temp3#
	Local Temp1s$, Temp2s$
	Local CollisionMeshes% = CreatePivot()
	Local HasTriggerBox% = False
	
	For i = 0 To 3 ; ~ Reattempt up to 3 times
		If (Not f) Then
			f = ReadFile(File)
		Else
			Exit
		EndIf
	Next
	If (Not f) Then RuntimeError("Error reading file " + Chr(34) + File + Chr(34))
	
	Local IsRMesh$ = ReadString(f)
	
	If IsRMesh = "RoomMesh"
		; ~ Continue
	ElseIf IsRMesh = "RoomMesh.HasTriggerBox"
		HasTriggerBox = True
	Else
		RuntimeError(Chr(34) + File + Chr(34) + " is Not RMESH (" + IsRMesh + ")")
	EndIf
	
	File = StripFileName(File)
	
	Local Count%, Count2%
	
	; ~ Drawn meshes
	Local Opaque%, Alpha%
	
	Opaque = CreateMesh()
	Alpha = CreateMesh()
	
	Local ChildMesh%
	Local Surf%, Tex%[2], Brush%
	Local IsAlpha%
	Local u#, v#
	
	Count = ReadInt(f)
	
	For i = 1 To Count ; ~ Drawn mesh
		ChildMesh = CreateMesh()
		
		Surf = CreateSurface(ChildMesh)
		
		Brush = CreateBrush()
		
		Tex[0] = 0 : Tex[1] = 0
		
		IsAlpha = 0
		
		For j = 0 To 1
			Temp1i = ReadByte(f)
			If Temp1i <> 0 Then
				Temp1s = ReadString(f)
				If FileType(File + Temp1s) = 1 ; ~ Check if texture is existing in original path
					If Temp1i < 3 Then
						Tex[j] = LoadTextureCheckingIfInCache(File + Temp1s, 1)
					Else
						Tex[j] = LoadTextureCheckingIfInCache(File + Temp1s, 3)
					EndIf
				ElseIf FileType(MapTexturesFolder + Temp1s) = 1 ; ~ If not, check the MapTexturesFolder
					If Temp1i < 3 Then
						Tex[j] = LoadTextureCheckingIfInCache(MapTexturesFolder + Temp1s, 1)
					Else
						Tex[j] = LoadTextureCheckingIfInCache(MapTexturesFolder + Temp1s, 3)
					EndIf
				EndIf
				If Tex[j] <> 0 Then
					If Temp1i = 1 Then TextureBlend(Tex[j], 2 + (3 * opt\Atmosphere))
					If Instr(Lower(Temp1s), "_lm") <> 0 Then
						TextureBlend(Tex[j], 2)
					EndIf
					IsAlpha = 2
					If Temp1i = 3 Then IsAlpha = 1
					TextureCoords(Tex[j], 1 - j)
				EndIf
			EndIf
		Next
		
		If IsAlpha = 1 Then
			If Tex[1] <> 0 Then
				TextureBlend(Tex[1], 2)
				BrushTexture(Brush, Tex[1], 0, 0)
			Else
				BrushTexture(Brush, MissingTexture, 0, 0)
			EndIf
		Else
			Local BumpTex% = 0
			
			If Tex[0] <> 0 And Tex[1] <> 0 Then
				If opt\BumpEnabled Then
					Local Temp$ = StripPath(TextureName(Tex[1]))
					
					For mat.Materials = Each Materials
						If mat\Name = Temp Then
							BumpTex = mat\Bump
							Exit
						EndIf
					Next
				Else
					BumpTex = 0
				EndIf
				If (Not BumpTex) Then
					For j = 0 To 1
						BrushTexture(Brush, Tex[j], 0, j + 1)
					Next
				Else
					TextureCoords(BumpTex, 0)
					For j = 0 To 1
						BrushTexture(Brush, Tex[j], 0, j + 2)
					Next
					BrushTexture(Brush, BumpTex, 0, 1)
				EndIf
				BrushTexture(Brush, AmbientLightRoomTex, 0)
			Else
				If opt\BumpEnabled Then
					If Tex[1] <> 0 Then
						Temp = StripPath(TextureName(Tex[1]))
						For mat.Materials = Each Materials
							If mat\Name = Temp Then
								BumpTex = mat\Bump
								Exit
							EndIf
						Next
					EndIf
				Else
					BumpTex = 0
				EndIf
				If (Not BumpTex) Then
					For j = 0 To 1
						If Tex[j] <> 0 Then
							BrushTexture(Brush, Tex[j], 0, j)
						Else
							BrushTexture(Brush, MissingTexture, 0, j)
						EndIf
					Next
				Else
					TextureCoords(BumpTex, 0)
					For j = 0 To 1
						If Tex[j] <> 0 Then
							BrushTexture(Brush, Tex[j], 0, j + 1)
						Else
							BrushTexture(Brush, MissingTexture, 0, j + 1)
						EndIf
					Next
					BrushTexture(Brush, BumpTex, 0, 0)
				EndIf
			EndIf
		EndIf
		
		Surf = CreateSurface(ChildMesh)
		
		If IsAlpha > 0 Then PaintSurface(Surf, Brush)
		
		FreeBrush(Brush) : Brush = 0
		
		Count2 = ReadInt(f) ; ~ Vertices
		
		For j = 1 To Count2
			; ~ World coords
			x = ReadFloat(f) : y = ReadFloat(f) : z = ReadFloat(f)
			Vertex = AddVertex(Surf, x, y, z)
			
			; ~ Texture coords
			For k = 0 To 1
				u = ReadFloat(f) : v = ReadFloat(f)
				VertexTexCoords(Surf, Vertex, u, v, 0.0, k)
			Next
			
			; ~ Colors
			Temp1i = ReadByte(f)
			Temp2i = ReadByte(f)
			Temp3i = ReadByte(f)
			VertexColor(Surf, Vertex, Temp1i, Temp2i, Temp3i, 1.0)
		Next
		
		Count2 = ReadInt(f) ; ~ Polys
		For j = 1 To Count2
			Temp1i = ReadInt(f) : Temp2i = ReadInt(f) : Temp3i = ReadInt(f)
			AddTriangle(Surf, Temp1i, Temp2i, Temp3i)
		Next
		
		If IsAlpha = 1 Then
			AddMesh(ChildMesh, Alpha)
			EntityAlpha(ChildMesh, 0.0)
		Else
			AddMesh(ChildMesh, Opaque)
			EntityParent(ChildMesh, CollisionMeshes)
			EntityAlpha(ChildMesh, 0.0)
			EntityType(ChildMesh, HIT_MAP)
			EntityPickMode(ChildMesh, 2)
			
			; ~ Make collision double-sided
			Local FlipChild% = CopyMesh(ChildMesh)
			
			FlipMesh(FlipChild)
			AddMesh(FlipChild, ChildMesh)
			FreeEntity(FlipChild)
		EndIf
	Next
	
	Local HiddenMesh%
	
	HiddenMesh = CreateMesh()
	
	Count = ReadInt(f) ; ~ Invisible collision mesh
	For i = 1 To Count
		Surf = CreateSurface(HiddenMesh)
		Count2 = ReadInt(f) ; ~ Vertices
		For j = 1 To Count2
			; ~ World coords
			x = ReadFloat(f) : y = ReadFloat(f) : z = ReadFloat(f)
			Vertex = AddVertex(Surf, x, y, z)
		Next
		
		Count2 = ReadInt(f) ; ~ Polys
		For j = 1 To Count2
			Temp1i = ReadInt(f) : Temp2i = ReadInt(f) : Temp3i = ReadInt(f)
			AddTriangle(Surf, Temp1i, Temp2i, Temp3i)
			AddTriangle(Surf, Temp1i, Temp3i, Temp2i)
		Next
	Next
	
	; ~ Trigger boxes
	If HasTriggerBox Then
		Local TB%
		
		rt\TempTriggerBoxAmount = ReadInt(f)
		For TB = 0 To rt\TempTriggerBoxAmount - 1
			rt\TempTriggerBox[TB] = CreateMesh(rt\OBJ)
			Count = ReadInt(f)
			For i = 1 To Count
				Surf = CreateSurface(rt\TempTriggerBox[TB])
				Count2 = ReadInt(f)
				For j = 1 To Count2
					x = ReadFloat(f) : y = ReadFloat(f) : z = ReadFloat(f)
					Vertex = AddVertex(Surf, x, y, z)
				Next
				Count2 = ReadInt(f)
				For j = 1 To Count2
					Temp1i = ReadInt(f) : Temp2i = ReadInt(f) : Temp3i = ReadInt(f)
					AddTriangle(Surf, Temp1i, Temp2i, Temp3i)
					AddTriangle(Surf, Temp1i, Temp3i, Temp2i)
				Next
			Next
			rt\TempTriggerBoxName[TB] = ReadString(f)
		Next
	EndIf
	
	Count = ReadInt(f) ; ~ Point entities
	
	Local twp.TempWayPoints, ts.TempScreens, tp.TempProps
	Local Range#, lColor$, Intensity#
	Local R%, G%, B%
	Local Angles$
	
	For i = 1 To Count
		Temp1s = ReadString(f)
		Select Temp1s
			Case "screen"
				;[Block]
				Temp1 = ReadFloat(f) * RoomScale
				Temp2 = ReadFloat(f) * RoomScale
				Temp3 = ReadFloat(f) * RoomScale
				
				Temp2s = ReadString(f)
				
				If Temp1 <> 0 Lor Temp2 <> 0 Lor Temp3 <> 0 Then 
					ts.TempScreens = New TempScreens
					ts\x = Temp1
					ts\y = Temp2
					ts\z = Temp3
					ts\ImgPath = Temp2s
					ts\RoomTemplate = rt
				EndIf
				;[End Block]
			Case "waypoint"
				;[Block]
				twp.TempWayPoints = New TempWayPoints
				twp\RoomTemplate = rt
				twp\x = ReadFloat(f) * RoomScale
				twp\y = ReadFloat(f) * RoomScale
				twp\z = ReadFloat(f) * RoomScale
				;[End Block]
			Case "light"
				;[Block]
				Temp1 = ReadFloat(f) * RoomScale
				Temp2 = ReadFloat(f) * RoomScale
				Temp3 = ReadFloat(f) * RoomScale
				
				If Temp1 <> 0 Lor Temp2 <> 0 Lor Temp3 <> 0 Then 
					Range = ReadFloat(f) / 2000.0
					lColor = ReadString(f)
					Intensity = Min(ReadFloat(f) * 0.8, 1.0)
					R = Int(Piece(lColor, 1, " ")) * Intensity
					G = Int(Piece(lColor, 2, " ")) * Intensity
					B = Int(Piece(lColor, 3, " ")) * Intensity
					
					AddTempLight(rt, Temp1, Temp2, Temp3, 2, Range, R, G, B)
				Else
					ReadFloat(f) : ReadString(f) : ReadFloat(f)
				EndIf
				;[End Block]
			Case "spotlight"
				;[Block]
				Temp1 = ReadFloat(f) * RoomScale
				Temp2 = ReadFloat(f) * RoomScale
				Temp3 = ReadFloat(f) * RoomScale
				
				If Temp1 <> 0 Lor Temp2 <> 0 Lor Temp3 <> 0 Then 
					Range = ReadFloat(f) / 2000.0
					lColor = ReadString(f)
					Intensity = Min(ReadFloat(f) * 0.8, 1.0)
					R = Int(Piece(lColor, 1, " ")) * Intensity
					G = Int(Piece(lColor, 2, " ")) * Intensity
					B = Int(Piece(lColor, 3, " ")) * Intensity
					
					Local lt.LightTemplates = AddTempLight(rt, Temp1, Temp2, Temp3, 2, Range, R, G, B)
					
					Angles = ReadString(f)
					Pitch = Piece(Angles, 1, " ")
					Yaw = Piece(Angles, 2, " ")
					lt\Pitch = Pitch
					lt\Yaw = Yaw
					
					lt\InnerConeAngle = ReadInt(f)
					lt\OuterConeAngle = ReadInt(f)
				Else
					ReadFloat(f) : ReadString(f) : ReadFloat(f) : ReadString(f) : ReadInt(f) : ReadInt(f)
				EndIf
				;[End Block]
			Case "soundemitter"
				;[Block]
				Temp1i = 0
				If rt <> Null Then
					For j = 0 To MaxRoomEmitters - 1
						If (Not rt\TempSoundEmitter[j]) Then
							rt\TempSoundEmitterX[j] = ReadFloat(f) * RoomScale
							rt\TempSoundEmitterY[j] = ReadFloat(f) * RoomScale
							rt\TempSoundEmitterZ[j] = ReadFloat(f) * RoomScale
							rt\TempSoundEmitter[j] = ReadInt(f)
							
							rt\TempSoundEmitterRange[j] = ReadFloat(f)
							Temp1i = 1
							Exit
						EndIf
					Next
				EndIf
				
				If Temp1i = 0 Then
					ReadFloat(f)
					ReadFloat(f)
					ReadFloat(f)
					ReadInt(f)
					ReadFloat(f)
				EndIf
				;[End Block]
			Case "playerstart"
				;[Block]
				Temp1 = ReadFloat(f) : Temp2 = ReadFloat(f) : Temp3 = ReadFloat(f)
				
				Angles = ReadString(f)
				Pitch = Piece(Angles, 1, " ")
				Yaw = Piece(Angles, 2, " ")
				Roll = Piece(Angles, 3, " ")
				;[End Block]
			Case "model"
				;[Block]
				File = ReadString(f)
				If File <> "" Then
					; ~ A hacky way to use .b3d format
					If Right(File, 1) = "x" Then File = Left(File, Len(File) - 1) + "b3d"
					
					tp.TempProps = New TempProps
					tp\RoomTemplate = rt
					tp\Name = "GFX\map\Props\" + File
					
					tp\x = ReadFloat(f) * RoomScale
					tp\y = ReadFloat(f) * RoomScale
					tp\z = ReadFloat(f) * RoomScale
					
					tp\Pitch = ReadFloat(f)
					tp\Yaw = ReadFloat(f)
					tp\Roll = ReadFloat(f)
					
					tp\ScaleX = ReadFloat(f)
					tp\ScaleY = ReadFloat(f)
					tp\ScaleZ = ReadFloat(f)
				Else
					ReadFloat(f) : ReadFloat(f) : ReadFloat(f)
				EndIf
				;[End Block]
		End Select
	Next
	
	Local OBJ%
	
	Temp1i = CopyMesh(Alpha)
	FlipMesh(Temp1i)
	AddMesh(Temp1i, Alpha)
	FreeEntity(Temp1i)
	
	If Brush <> 0 Then FreeBrush(Brush)
	
	AddMesh(Alpha, Opaque)
	FreeEntity(Alpha)
	
	EntityFX(Opaque, 3)
	
	EntityAlpha(HiddenMesh, 0.0)
	EntityType(HiddenMesh, HIT_MAP)
	EntityAlpha(Opaque, 1.0)
	
	OBJ = CreatePivot()
	CreatePivot(OBJ) ; ~ Skip "meshes" object
	EntityParent(Opaque, OBJ)
	EntityParent(HiddenMesh, OBJ)
	CreatePivot(OBJ) ; ~ Skip "pointentites" object
	CreatePivot(OBJ) ; ~ Skip "solidentites" object
	EntityParent(CollisionMeshes, OBJ)
	
	CloseFile(f)
	
	Return(OBJ)
	
	CatchErrors("LoadRMesh")
End Function

Const ForestGridSize% = 10

Type Forest
	Field TileMesh%[5]
	Field DetailMesh%[4]
	Field Grid%[(ForestGridSize ^ 2) + 11]
	Field TileEntities%[(ForestGridSize ^ 2) + 1]
	Field Forest_Pivot%
	Field ForestDoors.Doors[2]
	Field DetailEntities%[2]
End Type

; ~ Forest Constants
;[Block]
Const Deviation_Chance% = 40 ; ~ Out of 100
Const Branch_Chance% = 65
Const Branch_Max_Life% = 4
Const Branch_Die_Chance% = 18
Const Max_Deviation_Distance% = 3
Const Return_Chance% = 27
Const Center% = 5
Const MinDoorPos% = 3, MaxDoorPos% = 7
;[End Block]

Function GenForestGrid%(fr.Forest)
	CatchErrors("Uncaught (GenForestGrid)")
	
	Local Door1Pos%, Door2Pos%
	Local i%, j%
	
	Door1Pos = Rand(MinDoorPos, MaxDoorPos)
	Door2Pos = Rand(MinDoorPos, MaxDoorPos)
	
	; ~ Clear the grid
	For i = 0 To ForestGridSize - 1
		For j = 0 To ForestGridSize - 1
			fr\Grid[(j * ForestGridSize) + i] = 0
		Next
	Next
	
	; ~ Set the position of the concrete and doors
	fr\Grid[Door1Pos] = 3
	fr\Grid[((ForestGridSize - 1) * ForestGridSize) + Door2Pos] = 3
	
	; ~ Generate the path
	Local PathX% = Door2Pos
	Local PathY% = 1
	Local Dir% = 1 ; ~ 0 = left, 1 = up, 2 = right
	
	fr\Grid[((ForestGridSize - 1 - PathY) * ForestGridSize) + PathX] = 1
	
	Local Deviated%
	
	While PathY < ForestGridSize - 4
		If Dir = 1 Then ; ~ Determine whether to go forward or to the side
			If Chance(Deviation_Chance) Then
				; ~ Pick a branch direction
				Dir = 2 * Rand(0, 1)
				; ~ Make sure you have not passed max side distance
				Dir = TurnIfDeviating(Max_Deviation_Distance, PathX, Center, Dir)
				Deviated = TurnIfDeviating(Max_Deviation_Distance, PathX, Center, Dir, True)
				If Deviated Then fr\Grid[((ForestGridSize - 1 - PathY) * ForestGridSize) + PathX] = 1
				PathX = MoveForward(Dir, PathX, PathY)
				PathY = MoveForward(Dir, PathX, PathY, True)
			EndIf
		Else
			; ~ We are going to the side, so determine whether to keep going or go forward again
			Dir = TurnIfDeviating(Max_Deviation_Distance, PathX, Center, Dir)
			Deviated = TurnIfDeviating(Max_Deviation_Distance, PathX, Center, Dir, True)
			If Deviated Lor Chance(Return_Chance) Then Dir = 1
			
			PathX = MoveForward(Dir, PathX, PathY)
			PathY = MoveForward(Dir, PathX, PathY, True)
			; ~ If we just started going forward go twice so as to avoid creating a potential 2x2 line
			If Dir = 1 Then
				fr\Grid[((ForestGridSize - 1 - PathY) * ForestGridSize) + PathX] = 1
				PathX = MoveForward(Dir, PathX, PathY)
				PathY = MoveForward(Dir, PathX, PathY, True)
			EndIf
		EndIf
		; ~ Add our position to the grid
		fr\Grid[((ForestGridSize - 1 - PathY) * ForestGridSize) + PathX] = 1
	Wend
	; ~ Finally, bring the path back to the door now that we have reached the end
	Dir = 1
	While PathY < ForestGridSize - 2
		PathX = MoveForward(Dir, PathX, PathY)
		PathY = MoveForward(Dir, PathX, PathY, True)
		fr\Grid[((ForestGridSize - 1 - PathY) * ForestGridSize) + PathX] = 1
	Wend
	
	If PathX <> Door1Pos Then
		Dir = 0
		If Door1Pos > PathX Then Dir = 2
		While PathX <> Door1Pos
			PathX = MoveForward(Dir, PathX, PathY)
			PathY = MoveForward(Dir, PathX, PathY, True)
			fr\Grid[((ForestGridSize - 1 - PathY) * ForestGridSize) + PathX] = 1
		Wend
	EndIf
	
	; ~ Attempt to create new branches
	Local NewY%, TempY%, NewX%
	Local BranchPos%, LeftMost%, RightMost%
	
	NewY = -3 ; ~ Used for counting off; branches will only be considered once every 4 units so as to avoid potentially too many branches
	While NewY < ForestGridSize - 6
		NewY = NewY + 4
		TempY = NewY
		NewX = 0 
		If Chance(Branch_Chance) Then
			; ~ Create a branch at this spot
			; ~ Determine if on left or on right
			BranchPos = 2 * Rand(0, 1)
			; ~ Get leftmost or rightmost path in this row
			LeftMost = ForestGridSize - 1
			RightMost = 0
			For i = 0 To ForestGridSize - 1
				If fr\Grid[((ForestGridSize - 1 - NewY) * ForestGridSize) + i] = 1 Then
					If i < LeftMost Then LeftMost = i
					If i > RightMost Then RightMost = i
				EndIf
			Next
			If BranchPos = 0 Then
				NewX = LeftMost - 1
			Else
				NewX = RightMost + 1
			EndIf
			; ~ Before creating a branch make sure it won't pass the border and there are no 1's above or below
			If NewX >= 0 And NewX < ForestGridSize And fr\Grid[((ForestGridSize - 1 - TempY - 1) * ForestGridSize) + NewX] <> 1 And fr\Grid[((ForestGridSize - 1 - TempY + 1) * ForestGridSize) + NewX] <> 1 Then
				fr\Grid[((ForestGridSize - 1 - TempY) * ForestGridSize) + NewX] = -1 ; ~ Make -1s so you don't confuse your branch for a path; will be changed later
				If BranchPos = 0 Then
					NewX = LeftMost - 2
				Else
					NewX = RightMost + 2
				EndIf
				; ~ Before continuing the branch make sure it won't pass the border
				If NewX >= 0 And NewX < ForestGridSize Then
					fr\Grid[((ForestGridSize - 1 - TempY) * ForestGridSize) + NewX] = -1 ; ~ Branch out twice to avoid creating an unwanted 2x2 path with the real path
					i = 2
					While i < Branch_Max_Life
						i = i + 1
						If Chance(Branch_Die_Chance) Then Exit
						If Rand(0, 3) = 0 Then ; ~ Have a higher chance to go up to confuse the player
							If BranchPos = 0 Then
								NewX = NewX - 1
							Else
								NewX = NewX + 1
							EndIf
						Else
							TempY = TempY + 1
						EndIf
						
						; ~ before continuing the branch make sure it won't pass the border and there are no 1's above
						If NewX < 0 Lor NewX >= ForestGridSize Lor fr\Grid[((ForestGridSize - 1 - TempY - 1) * ForestGridSize) + NewX] = 1 Then Exit
						
						fr\Grid[((ForestGridSize - 1 - TempY) * ForestGridSize) + NewX] = -1 ; ~ Make -1s so you don't confuse your branch for a path; will be changed later
						If TempY >= ForestGridSize - 2 Then Exit
					Wend
				EndIf
			EndIf
		EndIf
	Wend
	
	If opt\DebugMode Then
		Local x%, y%
		
		Repeat
			Cls()
			i = ForestGridSize - 1
			For x = 0 To ForestGridSize - 1
				For y = 0 To ForestGridSize - 1
					If fr\Grid[x + (y * ForestGridSize)] = 0 Then
						Color(50, 50, 50)
						Rect((i * 32) * MenuScale, (y * 32) * MenuScale, 30 * MenuScale, 30 * MenuScale)
					Else
						Color(255, 255, 255)
						Rect((i * 32) * MenuScale, (y * 32) * MenuScale, 30 * MenuScale, 30 * MenuScale)
					EndIf
				Next
				i = i - 1
			Next
			
			i = ForestGridSize - 1
			For x = 0 To ForestGridSize - 1
				For y = 0 To ForestGridSize - 1
					If MouseOn((i * 32) * MenuScale, (y * 32) * MenuScale, 32 * MenuScale, 32 * MenuScale) Then
						Color(255, 0, 0)
					Else
						Color(0, 0, 0)
					EndIf
					Text(((i * 32) + 2) * MenuScale, ((y * 32) + 2) * MenuScale, fr\Grid[x + (y * ForestGridSize)])
				Next
				i = i - 1
			Next
			Flip()
			If opt\DisplayMode = 0 Then DrawImage(CursorIMG, ScaledMouseX(), ScaledMouseY())
		Until (GetKey() <> 0 Lor MouseHit(1))
	EndIf
	
	; ~ Change branches from -1s to 1s
	For i = 1 To ForestGridSize - 2
		For j = 0 To ForestGridSize - 1
			If fr\Grid[(i * ForestGridSize) + j] = -1 Then
				fr\Grid[(i * ForestGridSize) + j] = 1
			EndIf
		Next
	Next
	
	CatchErrors("GenForestGrid")
End Function

; ~ Shapes ID Constants
;[Block]
Const ROOM1% = 0
Const ROOM2% = 1
Const ROOM2C% = 2
Const ROOM3% = 3
Const ROOM4% = 4
;[End Block]

Function PlaceForest%(fr.Forest, x#, y#, z#, r.Rooms)
	CatchErrors("Uncaught (PlaceForest)")
	
	Local tX%, tY%
	Local Tile_Size# = 12.0
	Local Tile_Type%
	Local Tile_Entity%, Detail_Entity%
	Local Tempf1#, Tempf2#, Tempf3#, Tempf4#
	Local i%, Width%, lX%, lY%, d%
	
	DestroyForest(fr, False)
	
	fr\Forest_Pivot = CreatePivot()
	PositionEntity(fr\Forest_Pivot, x, y, z, True)
	
	; ~ Load assets
	Local hMap%[ROOM4 + 1], Mask%[ROOM4 + 1]
	Local GroundTexture% = LoadTexture_Strict("GFX\map\textures\forestfloor.jpg")
	Local PathTexture% = LoadTexture_Strict("GFX\map\textures\forestpath.jpg")
	
	If opt\Atmosphere Then
		TextureBlend(GroundTexture, 5)
		TextureBlend(PathTexture, 5)
	EndIf
	
	hMap[ROOM1] = LoadImage_Strict("GFX\map\forest\forest1h.png")
	Mask[ROOM1] = LoadTexture_Strict("GFX\map\forest\forest1h_mask.png", 1 + 2)
	
	hMap[ROOM2] = LoadImage_Strict("GFX\map\forest\forest2h.png")
	Mask[ROOM2] = LoadTexture_Strict("GFX\map\forest\forest2h_mask.png", 1 + 2)
	
	hMap[ROOM2C] = LoadImage_Strict("GFX\map\forest\forest2Ch.png")
	Mask[ROOM2C] = LoadTexture_Strict("GFX\map\forest\forest2Ch_mask.png", 1 + 2)
	
	hMap[ROOM3] = LoadImage_Strict("GFX\map\forest\forest3h.png")
	Mask[ROOM3] = LoadTexture_Strict("GFX\map\forest\forest3h_mask.png", 1 + 2)
	
	hMap[ROOM4] = LoadImage_Strict("GFX\map\forest\forest4h.png")
	Mask[ROOM4] = LoadTexture_Strict("GFX\map\forest\forest4h_mask.png", 1 + 2)
	
	For i = ROOM1 To ROOM4
		fr\TileMesh[i] = LoadTerrain(hMap[i], 0.03, GroundTexture, PathTexture, Mask[i])
	Next
	
	; ~ Detail meshes
	fr\DetailMesh[0] = LoadMesh_Strict("GFX\map\Props\tree1.b3d")
	fr\DetailMesh[1] = LoadMesh_Strict("GFX\map\Props\rock.b3d")
	fr\DetailMesh[2] = LoadMesh_Strict("GFX\map\Props\tree2.b3d")
	fr\DetailMesh[3] = LoadRMesh("GFX\map\forest\scp_860_1_wall.rmesh", Null)
	
	For i = ROOM1 To ROOM4
		HideEntity(fr\TileMesh[i])
	Next
	For i = 0 To 3
		HideEntity(fr\DetailMesh[i])
	Next
	
	Tempf3 = MeshWidth(fr\TileMesh[ROOM1])
	Tempf1 = Tile_Size / Tempf3
	
	For tX = 0 To ForestGridSize - 1
		For tY = 1 To ForestGridSize - 2
			If fr\Grid[(tY * ForestGridSize) + tX] = 1 Then 
				Tile_Type = 0
				If tX + 1 < ForestGridSize Then Tile_Type = (fr\Grid[(tY * ForestGridSize) + tX + 1] > 0)
				If tX - 1 >= 0 Then Tile_Type = Tile_Type + (fr\Grid[(tY * ForestGridSize) + tX - 1] > 0)
				
				If tY + 1 < ForestGridSize Then Tile_Type = Tile_Type + (fr\Grid[((tY + 1) * ForestGridSize) + tX] > 0)
				If tY - 1 >= 0 Then Tile_Type = Tile_Type + (fr\Grid[((tY - 1) * ForestGridSize) + tX] > 0)
				
				Local Angle# = 0.0
				
				Select Tile_Type
					Case 1
						;[Block]
						Tile_Entity = CopyEntity(fr\TileMesh[ROOM1])
						
						If fr\Grid[((tY + 1) * ForestGridSize) + tX] > 0 Then
							Angle = 180.0
						ElseIf fr\Grid[(tY * ForestGridSize) + (tX - 1)] > 0
							Angle = 270.0
						ElseIf fr\Grid[(tY * ForestGridSize) + (tX + 1)] > 0
							Angle = 90.0
						Else
							Angle = 0.0
						EndIf
						
						Tile_Type = ROOM1 + 1
						;[End Block]
					Case 2
						;[Block]
						If fr\Grid[((tY - 1) * ForestGridSize) + tX] > 0 And fr\Grid[((tY + 1) * ForestGridSize) + tX] > 0 Then
							Tile_Entity = CopyEntity(fr\TileMesh[ROOM2])
							Tile_Type = ROOM2 + 1
						ElseIf fr\Grid[(tY * ForestGridSize) + tX + 1] > 0 And fr\Grid[(tY * ForestGridSize) + tX - 1] > 0
							Tile_Entity = CopyEntity(fr\TileMesh[ROOM2])
							Angle = 90.0
							Tile_Type = ROOM2 + 1
						Else
							Tile_Entity = CopyEntity(fr\TileMesh[ROOM2C])
							If fr\Grid[(tY * ForestGridSize) + tX - 1] > 0 And fr\Grid[((tY + 1) * ForestGridSize) + tX] > 0 Then
								Angle = 180.0
							ElseIf fr\Grid[(tY * ForestGridSize) + tX + 1] > 0 And fr\Grid[((tY - 1) * ForestGridSize) + tX] > 0
								Angle = 0.0
							ElseIf fr\Grid[(tY * ForestGridSize) + tX - 1] > 0 And fr\Grid[((tY - 1) * ForestGridSize) + tX] > 0
								Angle = 270.0
							Else
								Angle = 90.0
							EndIf
							Tile_Type = ROOM2C + 1
						EndIf
						;[End Block]
					Case 3
						;[Block]
						Tile_Entity = CopyEntity(fr\TileMesh[ROOM3])
						
						If fr\Grid[((tY - 1) * ForestGridSize) + tX] = 0 Then
							Angle = 180.0
						ElseIf fr\Grid[(tY * ForestGridSize) + tX - 1] = 0
							Angle = 90.0
						ElseIf fr\Grid[(tY * ForestGridSize) + tX + 1] = 0
							Angle = 270.0
						Else
							Angle = 0.0
						EndIf
						
						Tile_Type = ROOM3 + 1
						;[End Block]
					Case 4
						;[Block]
						Tile_Entity = CopyEntity(fr\TileMesh[ROOM4])	
						
						Angle = (fr\Grid[(tY * ForestGridSize) + tX] Mod 4) * 90.0
						
						Tile_Type = ROOM4 + 1
						;[End Block]
				End Select
				
				If Tile_Type > 0 Then 
					; ~ Place trees and other details
					; ~ Only placed on spots where the value of the heightmap is above 100
					SetBuffer(ImageBuffer(hMap[Tile_Type - 1]))
					Width = ImageWidth(hMap[Tile_Type - 1])
					Tempf4 = (Tempf3 / Float(Width))
					For lX = 3 To Width - 2
						For lY = 3 To Width - 2
							GetColor(lX, Width - lY)
							If ColorRed() > Rand(100, 260) Then
								Select Rand(0, 7)
									Case 0, 1, 2, 3, 4, 5, 6 ; ~ Create a tree
										;[Block]
										Detail_Entity = CopyEntity(fr\DetailMesh[0])
										Tempf2 = Rnd(0.25, 0.4)
										
										For i = 0 To 3
											d = CopyEntity(fr\DetailMesh[2])
											RotateEntity(d, 0.0, 90.0 * i + Rnd(-20.0, 20.0), 0.0)
											EntityParent(d, Detail_Entity)
											EntityFX(d, 1)
										Next
										
										ScaleEntity(Detail_Entity, Tempf2 * 1.1, Tempf2, Tempf2 * 1.1, True)
										PositionEntity(Detail_Entity, lX * Tempf4 - (Tempf3 / 2.0), ColorRed() * 0.03 - Rnd(3.0, 3.2), lY * Tempf4 - (Tempf3 / 2.0), True)
										RotateEntity(Detail_Entity, Rnd(-5.0, 5.0), Rnd(360.0), 0.0, True)
										;[End Block]
									Case 6 ; ~ Add a stump
										;[Block]
										Detail_Entity = CopyEntity(fr\DetailMesh[2])
										Tempf2 = Rnd(0.1, 0.12)
										ScaleEntity(Detail_Entity, Tempf2, Tempf2, Tempf2, True)
										PositionEntity(Detail_Entity, lX * Tempf4 - (Tempf3 / 2.0), ColorRed() * 0.03 - 1.3, lY * Tempf4 - (Tempf3 / 2.0), True)
										;[End Block]
									Case 7 ; ~ Add a rock
										;[Block]
										Detail_Entity = CopyEntity(fr\DetailMesh[1])
										Tempf2 = Rnd(0.01, 0.012)
										PositionEntity(Detail_Entity, lX * Tempf4 - (Tempf3 / 2.0), ColorRed() * 0.03 - 1.3, lY * Tempf4 - (Tempf3 / 2.0), True)
										EntityFX(Detail_Entity, 1)
										RotateEntity(Detail_Entity, 0.0, Rnd(360.0), 0.0, True)
										;[End Block]
								End Select
								If Detail_Entity <> 0 Then
									EntityFX(Detail_Entity, 1)
									EntityParent(Detail_Entity, Tile_Entity)
								EndIf
							EndIf
						Next
					Next
					SetBuffer(BackBuffer())
					
					ScaleEntity(Tile_Entity, Tempf1, Tempf1, Tempf1)
					
					Local ItemPlaced%[4], iX#, iZ#
					Local it.Items = Null
					
					If (tY Mod 3) = 2 And (Not ItemPlaced[Floor(tY / 3)]) Then
						ItemPlaced[Floor(tY / 3)] = True
						
						If Tile_Type = ROOM1 + 1 Then
							iX = 0.4 : iZ = 0.0
						ElseIf Tile_Type = ROOM2C + 1
							iX = 1.7 : iZ = -1.0
						Else
							iX = 0.0 : iZ = 0.0
						EndIf
						it.Items = CreateItem("Log #" + Int(Floor(tY / 3) + 1), "paper", iX, 0.2, iZ)
						EntityType(it\Collider, HIT_ITEM)
						EntityParent(it\Collider, Tile_Entity)
					EndIf
					
					TurnEntity(Tile_Entity, 0.0, Angle, 0.0)
					PositionEntity(Tile_Entity, x + (tX * Tile_Size), y, z + (tY * Tile_Size), True)
					EntityType(Tile_Entity, HIT_MAP)
					EntityFX(Tile_Entity, 1)
					EntityParent(Tile_Entity, fr\Forest_Pivot)
					EntityPickMode(Tile_Entity, 2)
					
					If it <> Null Then EntityParent(it\Collider, 0)
					
					fr\TileEntities[tX + (tY * ForestGridSize)] = Tile_Entity
				EndIf
			EndIf
		Next
	Next
	
	; ~ Place the wall		
	For i = 0 To 1
		tY = i * (ForestGridSize - 1)
		For tX = MinDoorPos To MaxDoorPos
			If fr\Grid[(tY * ForestGridSize) + tX] = 3 Then
				fr\DetailEntities[i] = CopyEntity(fr\DetailMesh[3])
				ScaleEntity(fr\DetailEntities[i], RoomScale, RoomScale, RoomScale)
				
				fr\ForestDoors[i] = CreateDoor(0.0, 32.0 * RoomScale, 0.0, 180.0, Null, False, WOODEN_DOOR, KEY_860, "", fr\DetailEntities[i])
				fr\ForestDoors[i]\Locked = 2
				
				EntityType(fr\DetailEntities[i], HIT_MAP)
				EntityPickMode(fr\DetailEntities[i], 2)
				PositionEntity(fr\DetailEntities[i], x + (tX * Tile_Size), y, z + (tY * Tile_Size) + (Tile_Size / 2) - (Tile_Size * i), True)
				RotateEntity(fr\DetailEntities[i], 0.0, 180.0 * i, 0.0)
				EntityParent(fr\DetailEntities[i], fr\Forest_Pivot)
				Exit
			EndIf
		Next		
	Next
	
	CatchErrors("PlaceForest")
End Function

Function PlaceMapCreatorForest%(fr.Forest, x#, y#, z#, r.Rooms)
	CatchErrors("Uncaught (PlaceMapCreatorForest)")
	
	Local tX%, tY%
	Local Tile_Size# = 12.0
	Local Tile_Type%, Detail_Entity%
	Local Tile_Entity%, Eetail_Entity%
	Local Tempf1#, Tempf2#, Tempf3#, Tempf4#
	Local i%, Width%, lX%, lY%, d%
	
	DestroyForest(fr, False)
	
	fr\Forest_Pivot = CreatePivot()
	PositionEntity(fr\Forest_Pivot, x, y, z, True)
	
	Local hMap%[ROOM4 + 1], Mask%[ROOM4 + 1]
	; ~ Load assets
	Local GroundTexture% = LoadTexture_Strict("GFX\map\textures\forestfloor.jpg")
	Local PathTexture% = LoadTexture_Strict("GFX\map\textures\forestpath.jpg")
	
	If opt\Atmosphere Then
		TextureBlend(GroundTexture, 5)
		TextureBlend(PathTexture, 5)
	EndIf
	
	hMap[ROOM1] = LoadImage_Strict("GFX\map\forest\forest1h.png")
	Mask[ROOM1] = LoadTexture_Strict("GFX\map\forest\forest1h_mask.png", 1 + 2)
	
	hMap[ROOM2] = LoadImage_Strict("GFX\map\forest\forest2h.png")
	Mask[ROOM2] = LoadTexture_Strict("GFX\map\forest\forest2h_mask.png", 1 + 2)
	
	hMap[ROOM2C] = LoadImage_Strict("GFX\map\forest\forest2Ch.png")
	Mask[ROOM2C] = LoadTexture_Strict("GFX\map\forest\forest2Ch_mask.png", 1 + 2)
	
	hMap[ROOM3] = LoadImage_Strict("GFX\map\forest\forest3h.png")
	Mask[ROOM3] = LoadTexture_Strict("GFX\map\forest\forest3h_mask.png", 1 + 2)
	
	hMap[ROOM4] = LoadImage_Strict("GFX\map\forest\forest4h.png")
	Mask[ROOM4] = LoadTexture_Strict("GFX\map\forest\forest4h_mask.png", 1 + 2)
	
	For i = ROOM1 To ROOM4
		fr\TileMesh[i] = LoadTerrain(hMap[i], 0.03, GroundTexture, PathTexture, Mask[i])
	Next
	
	; ~ Detail meshes
	fr\DetailMesh[0] = LoadMesh_Strict("GFX\map\Props\tree1.b3d")
	fr\DetailMesh[1] = LoadMesh_Strict("GFX\map\Props\rock.b3d")
	fr\DetailMesh[2] = LoadMesh_Strict("GFX\map\Props\tree2.b3d")
	fr\DetailMesh[3] = LoadRMesh("GFX\map\scp_860_1_wall.rmesh", Null)
	
	For i = ROOM1 To ROOM4
		HideEntity(fr\TileMesh[i])
	Next
	For i = 0 To 3
		HideEntity(fr\DetailMesh[i])
	Next
	
	Tempf3 = MeshWidth(fr\TileMesh[ROOM1])
	Tempf1 = Tile_Size / Tempf3
	
	For tX = 0 To ForestGridSize - 1
		For tY = 0 To ForestGridSize - 1
			If fr\Grid[(tY * ForestGridSize) + tX] > 0 Then 
				Tile_Type = 0
				
				Local Angle# = 0.0
				
				Tile_Type = Ceil(Float(fr\Grid[(tY * ForestGridSize) + tX]) / 4.0)
				If Tile_Type = 6 Then
					Tile_Type = 2
				EndIf
				Angle = (fr\Grid[(tY * ForestGridSize) + tX] Mod 4) * 90.0
				
				Tile_Entity = CopyEntity(fr\TileMesh[Tile_Type - 1])
				
				If Tile_Type > 0 Then 
					; ~ Place trees and other details
					; ~ Only placed on spots where the value of the heightmap is above 100
					SetBuffer(ImageBuffer(hMap[Tile_Type - 1]))
					Width = ImageWidth(hMap[Tile_Type - 1])
					Tempf4 = (Tempf3 / Float(Width))
					For lX = 3 To Width - 2
						For lY = 3 To Width - 2
							GetColor(lX, Width - lY)
							If ColorRed() > Rand(100, 260) Then
								Detail_Entity = 0
								Select Rand(0, 7)
									Case 0, 1, 2, 3, 4, 5, 6 ; ~ Create a tree
										;[Block]
										Detail_Entity = CopyEntity(fr\DetailMesh[0])
										Tempf2 = Rnd(0.25, 0.4)
										
										For i = 0 To 3
											d = CopyEntity(fr\DetailMesh[2])
											RotateEntity(d, 0.0, (90.0 * i) + Rnd(-20.0, 20.0), 0.0)
											EntityParent(d, Detail_Entity)
											EntityFX(d, 1)
										Next
										ScaleEntity(Detail_Entity, Tempf2 * 1.1, Tempf2, Tempf2 * 1.1, True)
										PositionEntity(Detail_Entity, lX * Tempf4 - (Tempf3 / 2.0), ColorRed() * 0.03 - Rnd(3.0, 3.2), lY * Tempf4 - (Tempf3 / 2.0), True)
										
										RotateEntity(Detail_Entity, Rnd(-5.0, 5.0), Rnd(360.0), 0.0, True)
										;[End Block]
									Case 6 ; ~ Add a stump
										;[Block]
										Detail_Entity = CopyEntity(fr\DetailMesh[2])
										Tempf2 = Rnd(0.1, 0.12)
										ScaleEntity(Detail_Entity, Tempf2, Tempf2, Tempf2, True)
										PositionEntity(Detail_Entity, lX * Tempf4 - (Tempf3 / 2.0), ColorRed() * 0.03 - 1.3, lY * Tempf4 - (Tempf3 / 2.0), True)
										;[End Block]
									Case 7 ; ~ Add a rock
										;[Block]
										Detail_Entity = CopyEntity(fr\DetailMesh[1])
										Tempf2 = Rnd(0.01, 0.012)
										PositionEntity(Detail_Entity, lX * Tempf4 - (Tempf3 / 2.0), ColorRed() * 0.03 - 1.3, lY * Tempf4 - (Tempf3 / 2.0), True)
										EntityFX(Detail_Entity, 1)
										RotateEntity(Detail_Entity, 0.0, Rnd(360.0), 0.0, True)
										;[End Block]
								End Select
								
								If Detail_Entity <> 0 Then
									EntityFX(Detail_Entity, 1)
									EntityParent(Detail_Entity, Tile_Entity)
								EndIf
							EndIf
						Next
					Next
					SetBuffer(BackBuffer())
					
					ScaleEntity(Tile_Entity, Tempf1, Tempf1, Tempf1)
					
					Local ItemPlaced%[4], iX#, iZ#
					Local it.Items = Null
					
					If (tY Mod 3) = 2 And (Not ItemPlaced[Floor(tY / 3)]) Then
						ItemPlaced[Floor(tY / 3)] = True
						
						If Tile_Type = ROOM1 + 1 Then
							iX = 0.4 : iZ = 0.0
						ElseIf Tile_Type = ROOM2C + 1
							iX = 1.7 : iZ = -1.0
						Else
							iX = 0.0 : iZ = 0.0
						EndIf
						it.Items = CreateItem("Log #" + Int(Floor(tY / 3) + 1), "paper", iX, 0.2, iZ)
						EntityType(it\Collider, HIT_ITEM)
						EntityParent(it\Collider, Tile_Entity)
					EndIf
					
					TurnEntity(Tile_Entity, 0.0, Angle, 0.0)
					PositionEntity(Tile_Entity, x + (tX * Tile_Size), y, z + (tY * Tile_Size), True)
					EntityType(Tile_Entity, HIT_MAP)
					EntityFX(Tile_Entity, 1)
					EntityParent(Tile_Entity, fr\Forest_Pivot)
					EntityPickMode(Tile_Entity, 2)
					
					If it <> Null Then EntityParent(it\Collider, 0)
					
					fr\TileEntities[tX + (tY * ForestGridSize)] = Tile_Entity
				EndIf
				
				If Ceil(Float(fr\Grid[(tY * ForestGridSize) + tX]) / 4.0) = 6 Then
					For i = 0 To 1
						If fr\ForestDoors[i] = Null Then
							fr\DetailEntities[i] = CopyEntity(fr\DetailMesh[3])
							ScaleEntity(fr\DetailEntities[i], RoomScale, RoomScale, RoomScale)
							
							fr\ForestDoors[i] = CreateDoor(0.0, 32.0 * RoomScale, 0.0, 180.0, Null, False, WOODEN_DOOR, KEY_860, "", fr\DetailEntities[i])
							fr\ForestDoors[i]\Locked = 2
							
							EntityType(fr\DetailEntities[i], HIT_MAP)
							EntityPickMode(fr\DetailEntities[i], 2)
							PositionEntity(fr\DetailEntities[i], x + (tX * Tile_Size), y, z + (tY * Tile_Size), True)
							RotateEntity(fr\DetailEntities[i], 0.0, Angle + 180.0, 0.0)
							MoveEntity(fr\DetailEntities[i], 0.0, 0.0, -6.0)
							EntityParent(fr\DetailEntities[i], fr\Forest_Pivot)
							Exit
						EndIf
					Next
				EndIf
			EndIf
		Next
	Next
	
	CatchErrors("PlaceMapCreatorForest")
End Function


Function DestroyForest%(fr.Forest, RemoveGrid% = True)
	CatchErrors("Uncaught (DestroyForest)")
	
	Local tX%, tY%, i%
	
	For tX = 0 To ForestGridSize - 1
		For tY = 0 To ForestGridSize - 1
			If fr\TileEntities[tX + (tY * ForestGridSize)] <> 0 Then
				FreeEntity(fr\TileEntities[tX + (tY * ForestGridSize)]) : fr\TileEntities[tX + (tY * ForestGridSize)] = 0
				If RemoveGrid Then fr\Grid[tX + (tY * ForestGridSize)] = 0
			EndIf
		Next
	Next
	For i = 0 To 1
		If fr\ForestDoors[i] <> Null Then RemoveDoor(fr\ForestDoors[i])
		If fr\DetailEntities[i] <> 0 Then FreeEntity(fr\DetailEntities[i]) : fr\DetailEntities[i] = 0
	Next
	If fr\Forest_Pivot <> 0 Then FreeEntity(fr\Forest_Pivot) : fr\Forest_Pivot = 0
	For i = ROOM1 To ROOM4
		If fr\TileMesh[i] <> 0 Then FreeEntity(fr\TileMesh[i]) : fr\TileMesh[i] = 0
	Next
	For i = 0 To 3
		If fr\DetailMesh[i] <> 0 Then FreeEntity(fr\DetailMesh[i]) : fr\DetailMesh[i] = 0
	Next
	
	CatchErrors("DestroyForest")
End Function

Function UpdateForest%(fr.Forest)
	CatchErrors("Uncaught (UpdateForest)")
	
	Local tX%, tY%
	
	For tX = 0 To ForestGridSize - 1
		For tY = 0 To ForestGridSize - 1
			If fr\TileEntities[tX + (tY * ForestGridSize)] <> 0 Then
				If Abs(EntityX(me\Collider, True) - EntityX(fr\TileEntities[tX + (tY * ForestGridSize)], True)) < HideDistance Then
					If Abs(EntityZ(me\Collider, True) - EntityZ(fr\TileEntities[tX + (tY * ForestGridSize)], True)) < HideDistance Then
						If EntityHidden(fr\TileEntities[tX + (tY * ForestGridSize)]) Then ShowEntity(fr\TileEntities[tX + (tY * ForestGridSize)])
					Else
						If (Not EntityHidden(fr\TileEntities[tX + (tY * ForestGridSize)])) Then HideEntity(fr\TileEntities[tX + (tY * ForestGridSize)])
					EndIf
				Else
					If (Not EntityHidden(fr\TileEntities[tX + (tY * ForestGridSize)])) Then HideEntity(fr\TileEntities[tX + (tY * ForestGridSize)])
				EndIf
			EndIf
		Next
	Next
	
	CatchErrors("UpdateForest")
End Function

Global RoomTempID%

Type RoomTemplates
	Field OBJ%, ID%
	Field OBJPath$
	Field Zone%[5]
	Field TempSoundEmitter%[MaxRoomEmitters]
	Field TempSoundEmitterX#[MaxRoomEmitters], TempSoundEmitterY#[MaxRoomEmitters], TempSoundEmitterZ#[MaxRoomEmitters]
	Field TempSoundEmitterRange#[MaxRoomEmitters]
	Field Shape%, Name$
	Field Commonness%, Large%
	Field DisableDecals%
	Field TempTriggerBoxAmount%
	Field TempTriggerBox%[8]
	Field TempTriggerBoxName$[8]
	Field DisableOverlapCheck% = True
	Field MinX#, MinY#, MinZ#
	Field MaxX#, MaxY#, MaxZ#
End Type 	

Function CreateRoomTemplate.RoomTemplates(MeshPath$)
	Local rt.RoomTemplates
	
	rt.RoomTemplates = New RoomTemplates
	rt\OBJPath = "GFX\map\" + MeshPath
	rt\ID = RoomTempID
	RoomTempID = RoomTempID + 1
	
	Return(rt)
End Function

Function LoadRoomTemplates%(File$)
	CatchErrors("Uncaught (LoadRoomTemplates)")
	
	Local TemporaryString$, i%
	Local rt.RoomTemplates = Null
	Local StrTemp$ = ""
	Local f% = OpenFile(File)
	
	While (Not Eof(f))
		TemporaryString = Trim(ReadLine(f))
		If Left(TemporaryString, 1) = "[" Then
			TemporaryString = Mid(TemporaryString, 2, Len(TemporaryString) - 2)
			If TemporaryString <> "room ambience" Then
				StrTemp = GetINIString(File, TemporaryString, "Mesh Path")
				
				rt.RoomTemplates = CreateRoomTemplate(StrTemp)
				rt\Name = Lower(TemporaryString)
				
				StrTemp = GetINIString(File, TemporaryString, "Shape")
				
				Select StrTemp
					Case "room1", "1"
						;[Block]
						rt\Shape = ROOM1
						;[End Block]
					Case "room2", "2"
						;[Block]
						rt\Shape = ROOM2
						;[End Block]
					Case "room2C", "2C"
						;[Block]
						rt\Shape = ROOM2C
						;[End Block]
					Case "room3", "3"
						;[Block]
						rt\Shape = ROOM3
						;[End Block]
					Case "room4", "4"
						;[Block]
						rt\Shape = ROOM4
						;[End Block]
				End Select
				
				For i = 0 To 4
					rt\Zone[i] = GetINIInt(File, TemporaryString, "Zone" + (i + 1))
				Next
				
				rt\Commonness = Max(Min(GetINIInt(File, TemporaryString, "Commonness"), 100), 0)
				rt\Large = GetINIInt(File, TemporaryString, "Large")
				rt\DisableDecals = GetINIInt(File, TemporaryString, "Disabledecals")
				rt\DisableOverlapCheck = GetINIInt(File, TemporaryString, "DisableOverlapCheck")
			EndIf
		EndIf
	Wend
	
	i = 0
	Repeat
		StrTemp = GetINIString(File, "room ambience", "Ambience" + i)
		If StrTemp = "" Then Exit
		
		RoomAmbience[i] = LoadSound_Strict(StrTemp)
		i = i + 1
	Forever
	
	CloseFile(f)
	
	CatchErrors("LoadRoomTemplates")
End Function

Function LoadRoomMesh%(rt.RoomTemplates)
	If Instr(rt\OBJPath, ".rmesh") <> 0 Then ; ~ File is .rmesh
		rt\OBJ = LoadRMesh(rt\OBJPath, rt)
	ElseIf Instr(rt\OBJPath, ".b3d") <> 0 ; ~ File is .b3d
		RuntimeError(".b3d rooms are no longer supported, please use the converter! Affected room: " + Chr(34) + rt\OBJPath + Chr(34))
	Else ; ~ File not found
		RuntimeError("File: " + Chr(34) + rt\OBJPath + Chr(34) + " not found.")
	EndIf
	
	If (Not rt\OBJ) Then RuntimeError("Failed to load map file: " + Chr(34) + rt\OBJPath + Chr(34) + ".")
	
	CalculateRoomTemplateExtents(rt)
	
	HideEntity(rt\OBJ)
End Function

Type TriggerBox
	Field OBJ%
	Field Name$
	Field MinX#, MinY#, MinZ#
	Field MaxX#, MaxY#, MaxZ#
End Type

LoadRoomTemplates("Data\rooms.ini")

Global RoomAmbience%[10]

Global Sky%

Global HideDistance#

Global SecondaryLightOn# = True
Global PrevSecondaryLightOn# = True
Global RemoteDoorOn% = True

; ~ Room Objects Constants
;[Block]
Const MaxRoomEmitters% = 8
Const MaxRoomLights% = 32
Const MaxRoomObjects% = 30
Const MaxRoomLevers% = 10
Const MaxRoomDoors% = 7
Const MaxRoomNPCs% = 12
Const MaxRoomAdjacents% = 4
Const MaxRoomTextures% = 10
Const MaxRoomTriggerBoxes% = 8
;[End Block]

Type Rooms
	Field Zone%
	Field Found%
	Field OBJ%
	Field x#, y#, z#
	Field Angle%
	Field RoomTemplate.RoomTemplates
	Field Dist#
	Field SoundCHN%
	Field fr.Forest
	Field SoundEmitter%[MaxRoomEmitters]
	Field SoundEmitterOBJ%[MaxRoomEmitters]
	Field SoundEmitterRange#[MaxRoomEmitters]
	Field SoundEmitterCHN%[MaxRoomEmitters]
	Field Lights%[MaxRoomLights]
	Field LightSprites%[MaxRoomLights]
	Field LightSprites2%[MaxRoomLights]
	Field LightIntensity#[MaxRoomLights]
	Field LightR#[MaxRoomLights], LightG#[MaxRoomLights], LightB#[MaxRoomLights]
	Field MaxLights% = 0
	Field Objects%[MaxRoomObjects]
	Field Levers%[MaxRoomLevers]
	Field RoomDoors.Doors[MaxRoomDoors]
	Field NPC.NPCs[MaxRoomNPCs]
	Field mt.MTGrid
	Field Adjacent.Rooms[MaxRoomAdjacents]
	Field AdjDoor.Doors[MaxRoomAdjacents]
	Field Textures%[MaxRoomTextures]
	Field TriggerBoxAmount%
	Field TriggerBoxes.TriggerBox[MaxRoomTriggerBoxes]
	Field MaxWayPointY#
	Field MinX#, MinY#, MinZ#
	Field MaxX#, MaxY#, MaxZ#
End Type 

Global PlayerRoom.Rooms

Const MTGridSize% = 19 ; ~ Same size as the main map itself (better for the map creator)
Const MTGridY# = 8.0

Type MTGrid
	Field Grid%[MTGridSize ^ 2]
	Field Angles%[MTGridSize ^ 2]
	Field Meshes%[7]
	Field Entities%[MTGridSize ^ 2]
	Field waypoints.WayPoints[MTGridSize ^ 2]
End Type

Function UpdateMT%(mt.MTGrid)
	CatchErrors("Uncaught (UpdateMT)")
	
	Local tX%, tY%
	
	For tX = 0 To MTGridSize - 1
		For tY = 0 To MTGridSize - 1
			If mt\Entities[tX + (tY * MTGridSize)] <> 0 Then
				If Abs(EntityY(me\Collider, True) - EntityY(mt\Entities[tX + (tY * MTGridSize)], True)) > 4.0 Then Exit
				If Abs(EntityX(me\Collider, True) - EntityX(mt\Entities[tX + (tY * MTGridSize)], True)) < HideDistance Then
					If Abs(EntityZ(me\Collider, True) - EntityZ(mt\Entities[tX + (tY * MTGridSize)], True)) < HideDistance Then
						If EntityHidden(mt\Entities[tX + (tY * MTGridSize)]) Then ShowEntity(mt\Entities[tX + (tY * MTGridSize)])
					Else
						If (Not EntityHidden(mt\Entities[tX + (tY * MTGridSize)])) Then HideEntity(mt\Entities[tX + (tY * MTGridSize)])
					EndIf
				Else
					If (Not EntityHidden(mt\Entities[tX + (tY * MTGridSize)])) Then HideEntity(mt\Entities[tX + (tY * MTGridSize)])
				EndIf
			EndIf
		Next
	Next
	
	CatchErrors("UpdateMT")
End Function

Function PlaceMapCreatorMT%(r.Rooms)
	CatchErrors("Uncaught (PlaceMapCreatorMT)")
	
	Local dr.Doors, it.Items, wayp.WayPoints
	Local x%, y%, i%, Dist#
	Local Meshes%[7]
	
	For i = 0 To MaxMTModelIDAmount - 1
		Meshes[i] = CopyEntity(misc_I\MTModelID[i])
		HideEntity(Meshes[i])
	Next
	
	For y = 0 To MTGridSize - 1
		For x = 0 To MTGridSize - 1
			If r\mt\Grid[x + (y * MTGridSize)] > 0 Then
				Local Tile_Type% = 0
				Local Angle# = 0.0
				
				Tile_Type = r\mt\Grid[x + (y * MTGridSize)]
				Angle = r\mt\Angles[x + (y * MTGridSize)] * 90.0
				
				Local Tile_Entity% = CopyEntity(Meshes[Tile_Type - 1])
				
				RotateEntity(Tile_Entity, 0.0, Angle, 0.0)
				ScaleEntity(Tile_Entity, RoomScale, RoomScale, RoomScale, True)
				PositionEntity(Tile_Entity, r\x + (x * 2.0), r\y + MTGridY, r\z + (y * 2.0), True)
				
				Select Tile_Type
					Case ROOM1 + 1, ROOM2 + 1
						;[Block]
						AddLight(Null, r\x + (x * 2.0), r\y + MTGridY + (372.0 * RoomScale), r\z + (y * 2.0), 2, 500.0 * RoomScale, 255, 255, 255)
						;[End Block]
					Case ROOM2C + 1, ROOM3 + 1, ROOM4 + 1
						;[Block]
						AddLight(Null, r\x + (x * 2.0), r\y + MTGridY + (416.0 * RoomScale), r\z + (y * 2.0), 2, 500.0 * RoomScale, 255, 255, 255)
						;[End Block]
					Case ROOM4 + 2
						;[Block]
						dr.Doors = CreateDoor(r\x + (x * 2.0) + (Cos(EntityYaw(Tile_Entity, True)) * 240.0 * RoomScale), r\y + MTGridY, r\z + (y * 2.0) + (Sin(EntityYaw(Tile_Entity, True)) * 240.0 * RoomScale), EntityYaw(Tile_Entity, True) - 90.0, Null, False, ELEVATOR_DOOR)
						PositionEntity(dr\Buttons[0], EntityX(dr\Buttons[0], True) + (Cos(EntityYaw(Tile_Entity, True)) * 0.05), EntityY(dr\Buttons[0], True), EntityZ(dr\Buttons[0], True) + (Sin(EntityYaw(Tile_Entity, True)) * 0.05), True)
						PositionEntity(dr\Buttons[1], EntityX(dr\Buttons[1], True) + (Cos(EntityYaw(Tile_Entity, True)) * 0.05), EntityY(dr\Buttons[1], True), EntityZ(dr\Buttons[1], True) + (Sin(EntityYaw(Tile_Entity, True)) * 0.031), True)
						PositionEntity(dr\ElevatorPanel[0], EntityX(dr\ElevatorPanel[0], True) + (Cos(EntityYaw(Tile_Entity, True)) * 0.05), EntityY(dr\ElevatorPanel[0], True), EntityZ(dr\ElevatorPanel[0], True) + (Sin(EntityYaw(Tile_Entity, True)) * 0.05), True)
						PositionEntity(dr\ElevatorPanel[1], EntityX(dr\ElevatorPanel[1], True) + (Cos(EntityYaw(Tile_Entity, True)) * 0.05), EntityY(dr\ElevatorPanel[1], True) + 0.1, EntityZ(dr\ElevatorPanel[1], True) + (Sin(EntityYaw(Tile_Entity, True)) * (-0.18)), True)
						RotateEntity(dr\ElevatorPanel[1], EntityPitch(dr\ElevatorPanel[1], True) + 45.0, EntityYaw(dr\ElevatorPanel[1], True), EntityRoll(dr\ElevatorPanel[1], True), True)
						
						AddLight(Null, r\x + (x * 2.0) + (Cos(EntityYaw(Tile_Entity, True)) * 555.0 * RoomScale), r\y + MTGridY + (469.0 * RoomScale), r\z + (y * 2.0) + (Sin(EntityYaw(Tile_Entity, True)) * 555.0 * RoomScale), 2, 600.0 * RoomScale, 255, 255, 255)
						
						Local TempInt2% = CreatePivot()
						
						RotateEntity(TempInt2, 0.0, EntityYaw(Tile_Entity, True) + 180.0, 0.0, True)
						PositionEntity(TempInt2, r\x + (x * 2.0) + (Cos(EntityYaw(Tile_Entity, True)) * 552.0 * RoomScale), r\y + MTGridY + (240.0 * RoomScale), r\z + (y * 2.0) + (Sin(EntityYaw(Tile_Entity, True)) * 552.0 * RoomScale))
						If r\RoomDoors[1] = Null Then
							r\RoomDoors[1] = dr
							r\Objects[3] = TempInt2
							PositionEntity(r\Objects[0], r\x + (x * 2.0), r\y + MTGridY, r\z + (y * 2.0), True)
						ElseIf r\RoomDoors[1] <> Null And r\RoomDoors[3] = Null Then
							r\RoomDoors[3] = dr
							r\Objects[5] = TempInt2
							PositionEntity(r\Objects[1], r\x + (x * 2.0), r\y + MTGridY, r\z + (y * 2.0), True)
						EndIf
						;[End Block]
					Case ROOM4 + 3
						;[Block]
						AddLight(Null, r\x + (x * 2.0) - (Sin(EntityYaw(Tile_Entity, True)) * 504.0 * RoomScale) + (Cos(EntityYaw(Tile_Entity, True)) * 16.0 * RoomScale), r\y + MTGridY + (396.0 * RoomScale), r\z + (y * 2.0) + (Cos(EntityYaw(Tile_Entity, True)) * 504.0 * RoomScale) + (Sin(EntityYaw(Tile_Entity, True)) * 16.0 * RoomScale), 2, 500.0 * RoomScale, 255, 200, 200)
						it.Items = CreateItem("SCP-500-01", "scp500pill", r\x + (x * 2.0) + (Cos(EntityYaw(Tile_Entity, True)) * (-208.0) * RoomScale) - (Sin(EntityYaw(Tile_Entity, True)) * 1226.0 * RoomScale), r\y + MTGridY + (90.0 * RoomScale), r\z + (y * 2.0) + (Sin(EntityYaw(Tile_Entity, True)) * (-208.0) * RoomScale) + (Cos(EntityYaw(Tile_Entity, True)) * 1226.0 * RoomScale))
						EntityType(it\Collider, HIT_ITEM)
						
						it.Items = CreateItem("Night Vision Goggles", "nvg", r\x + (x * 2.0) - (Sin(EntityYaw(Tile_Entity, True)) * 504.0 * RoomScale) + (Cos(EntityYaw(Tile_Entity, True)) * 16.0 * RoomScale), r\y + MTGridY + (90.0 * RoomScale),  r\z + (y * 2.0) + (Cos(EntityYaw(Tile_Entity, True)) * 504.0 * RoomScale) + (Sin(EntityYaw(Tile_Entity, True)) * 16.0 * RoomScale))
						EntityType(it\Collider, HIT_ITEM)
						;[End Block]
				End Select
				
				r\mt\Entities[x + (y * MTGridSize)] = Tile_Entity
				wayp = CreateWaypoint(r\x + (x * 2.0), r\y + MTGridY + 0.2, r\z + (y * 2.0), Null, r)
				r\mt\waypoints[x + (y * MTGridSize)] = wayp
				
				If y < MTGridSize - 1 Then
					If r\mt\waypoints[x + ((y + 1) * MTGridSize)] <> Null Then
						Dist = EntityDistance(r\mt\waypoints[x + (y * MTGridSize)]\OBJ, r\mt\waypoints[x + ((y + 1) * MTGridSize)]\OBJ)
						For i = 0 To 3
							If r\mt\waypoints[x + (y * MTGridSize)]\connected[i] = r\mt\waypoints[x + ((y + 1) * MTGridSize)] Then
								Exit 
							ElseIf r\mt\waypoints[x + (y * MTGridSize)]\connected[i] = Null Then
								r\mt\waypoints[x + (y * MTGridSize)]\connected[i] = r\mt\waypoints[x + ((y + 1) * MTGridSize)]
								r\mt\waypoints[x + (y * MTGridSize)]\Dist[i] = Dist
								Exit
							EndIf
						Next
						For i = 0 To 3
							If r\mt\waypoints[x + ((y + 1) * MTGridSize)]\connected[i] = r\mt\waypoints[x + (y * MTGridSize)] Then
								Exit
							ElseIf r\mt\waypoints[x + ((y + 1) * MTGridSize)]\connected[i] = Null Then
								r\mt\waypoints[x + ((y + 1) * MTGridSize)]\connected[i] = r\mt\waypoints[x + (y * MTGridSize)]
								r\mt\waypoints[x + ((y + 1) * MTGridSize)]\Dist[i] = Dist
								Exit
							EndIf
						Next
					EndIf
				EndIf
				If y > 0 Then
					If r\mt\waypoints[x + ((y - 1) * MTGridSize)] <> Null Then
						Dist = EntityDistance(r\mt\waypoints[x + (y * MTGridSize)]\OBJ, r\mt\waypoints[x + ((y - 1) * MTGridSize)]\OBJ)
						For i = 0 To 3
							If r\mt\waypoints[x + (y * MTGridSize)]\connected[i] = r\mt\waypoints[x + ((y - 1) * MTGridSize)] Then
								Exit
							ElseIf r\mt\waypoints[x + (y * MTGridSize)]\connected[i] = Null Then
								r\mt\waypoints[x + (y * MTGridSize)]\connected[i] = r\mt\waypoints[x + ((y - 1) * MTGridSize)]
								r\mt\waypoints[x + (y * MTGridSize)]\Dist[i] = Dist
								Exit
							EndIf
						Next
						For i = 0 To 3
							If r\mt\waypoints[x + ((y - 1) * MTGridSize)]\connected[i] = r\mt\waypoints[x + (y * MTGridSize)] Then
								Exit
							ElseIf r\mt\waypoints[x + (y * MTGridSize)]\connected[i] = Null Then
								r\mt\waypoints[x + ((y - 1) * MTGridSize)]\connected[i] = r\mt\waypoints[x + (y * MTGridSize)]
								r\mt\waypoints[x + ((y - 1) * MTGridSize)]\Dist[i] = Dist
								Exit
							EndIf
						Next
					EndIf
				EndIf
				If x > 0 Then
					If r\mt\waypoints[x - 1 + (y * MTGridSize)] <> Null Then
						Dist = EntityDistance(r\mt\waypoints[x + (y * MTGridSize)]\OBJ, r\mt\waypoints[x - 1 + (y * MTGridSize)]\OBJ)
						For i = 0 To 3
							If r\mt\waypoints[x + (y * MTGridSize)]\connected[i] = r\mt\waypoints[x - 1 + (y * MTGridSize)] Then
								Exit
							ElseIf r\mt\waypoints[x + (y * MTGridSize)]\connected[i] = Null Then
								r\mt\waypoints[x + (y * MTGridSize)]\connected[i] = r\mt\waypoints[x - 1 + (y * MTGridSize)]
								r\mt\waypoints[x + (y * MTGridSize)]\Dist[i] = Dist
								Exit
							EndIf
						Next
						For i = 0 To 3
							If r\mt\waypoints[x - 1 + (y * MTGridSize)]\connected[i] = r\mt\waypoints[x + (y * MTGridSize)] Then
								Exit
							ElseIf r\mt\waypoints[x + (y * MTGridSize)]\connected[i] = Null Then
								r\mt\waypoints[x - 1 + (y * MTGridSize)]\connected[i] = r\mt\waypoints[x + (y * MTGridSize)]
								r\mt\waypoints[x - 1 + (y * MTGridSize)]\Dist[i] = Dist
								Exit
							EndIf
						Next
					EndIf
				EndIf
				If x < MTGridSize - 1 Then
					If r\mt\waypoints[x + 1 + (y * MTGridSize)] <> Null Then
						Dist = EntityDistance(r\mt\waypoints[x + (y * MTGridSize)]\OBJ, r\mt\waypoints[x + 1 + (y * MTGridSize)]\OBJ)
						For i = 0 To 3
							If r\mt\waypoints[x + (y * MTGridSize)]\connected[i] = r\mt\waypoints[x + 1 + (y * MTGridSize)] Then
								Exit
							ElseIf r\mt\waypoints[x + (y * MTGridSize)]\connected[i] = Null Then
								r\mt\waypoints[x + (y * MTGridSize)]\connected[i] = r\mt\waypoints[x + 1 + (y * MTGridSize)]
								r\mt\waypoints[x + (y * MTGridSize)]\Dist[i] = Dist
								Exit
							EndIf
						Next
						For i = 0 To 3
							If r\mt\waypoints[x + 1 + (y * MTGridSize)]\connected[i] = r\mt\waypoints[x + (y * MTGridSize)] Then
								Exit
							ElseIf r\mt\waypoints[x + (y * MTGridSize)]\connected[i] = Null Then
								r\mt\waypoints[x + 1 + (y * MTGridSize)]\connected[i] = r\mt\waypoints[x + (y * MTGridSize)]
								r\mt\waypoints[x + 1 + (y * MTGridSize)]\Dist[i] = Dist
								Exit
							EndIf
						Next
					EndIf
				EndIf
			EndIf
		Next
	Next
	
	For i = 0 To MaxMTModelIDAmount - 1
		r\mt\Meshes[i] = Meshes[i]
	Next
	
	CatchErrors("PlaceMapCreatorMT")
End Function

Function CreateRoom.Rooms(Zone%, RoomShape%, x#, y#, z#, Name$ = "")
	CatchErrors("Uncaught (CreateRoom)")
	
	Local r.Rooms = New Rooms
	Local rt.RoomTemplates
	
	r\Zone = Zone
	
	r\x = x : r\y = y : r\z = z
	
	If Name <> "" Then
		Name = Lower(Name)
		For rt.RoomTemplates = Each RoomTemplates
			If rt\Name = Name Then
				r\RoomTemplate = rt
				
				If (Not rt\OBJ) Then LoadRoomMesh(rt)
				
				r\OBJ = CopyEntity(rt\OBJ)
				ScaleEntity(r\OBJ, RoomScale, RoomScale, RoomScale)
				EntityType(r\OBJ, HIT_MAP)
				EntityPickMode(r\OBJ, 2)
				
				PositionEntity(r\OBJ, x, y, z)
				FillRoom(r)
				
				CalculateRoomExtents(r)
				
				Return(r)
			EndIf
		Next
	EndIf
	
	Local Temp% = 0
	
	For rt.RoomTemplates = Each RoomTemplates
		Local i%
		
		For i = 0 To 4
			If rt\Zone[i] = Zone Then 
				If rt\Shape = RoomShape Then
					Temp = Temp + rt\Commonness
					Exit
				EndIf
			EndIf
		Next
	Next
	
	Local RandomRoom% = Rand(Temp)
	
	Temp = 0
	For rt.RoomTemplates = Each RoomTemplates
		For i = 0 To 4
			If rt\Zone[i] = Zone And rt\Shape = RoomShape Then
				Temp = Temp + rt\Commonness
				If RandomRoom > Temp - rt\Commonness And RandomRoom <= Temp Then
					r\RoomTemplate = rt
					
					If (Not rt\OBJ) Then LoadRoomMesh(rt)
					
					r\OBJ = CopyEntity(rt\OBJ)
					ScaleEntity(r\OBJ, RoomScale, RoomScale, RoomScale)
					EntityType(r\OBJ, HIT_MAP)
					EntityPickMode(r\OBJ, 2)
					
					PositionEntity(r\OBJ, x, y, z)
					FillRoom(r)
					
					CalculateRoomExtents(r)
					
					Return(r)	
				EndIf
			EndIf
		Next
	Next
	
	CatchErrors("CreateRoom")
End Function

Type TempWayPoints
	Field x#, y#, z#
	Field RoomTemplate.RoomTemplates
End Type

Type WayPoints
	Field OBJ%
	Field door.Doors
	Field room.Rooms
	Field State%
	Field connected.WayPoints[5]
	Field Dist#[5]
	Field Fcost#, Gcost#, Hcost#
	Field parent.WayPoints
End Type

Function CreateWaypoint.WayPoints(x#, y#, z#, door.Doors, room.Rooms)
	Local w.WayPoints
	
	w.WayPoints = New WayPoints
	w\OBJ = CreatePivot()
	PositionEntity(w\OBJ, x, y, z)
	EntityParent(w\OBJ, room\OBJ)
	
	w\room = room
	w\door = door
	
	Return(w)
End Function

Function RemoveWaypoint%(w.WayPoints)
	FreeEntity(w\OBJ) : w\OBJ = 0
	Delete(w)
End Function

; ~ Button ID Constants
;[Block]
Const BUTTON_DEFAULT% = 0
Const BUTTON_KEYCARD% = 1
Const BUTTON_KEYPAD% = 2
Const BUTTON_SCANNER% = 3
Const BUTTON_ELEVATOR% = 4
;[End Block]

Function CreateButton%(ButtonID% = BUTTON_DEFAULT, x#, y#, z#, Pitch# = 0.0, Yaw# = 0.0, Roll# = 0.0, Parent% = 0, Locked% = False)
	Local OBJ%
	
	OBJ = CopyEntity(d_I\ButtonModelID[ButtonID])
	ScaleEntity(OBJ, 0.03, 0.03, 0.03)
	PositionEntity(OBJ, x, y, z)
	RotateEntity(OBJ, Pitch, Yaw, Roll)
	EntityPickMode(OBJ, 2)
	If Locked Then EntityTexture(OBJ, d_I\ButtonTextureID[BUTTON_RED_TEXTURE])
	If Parent <> 0 Then EntityParent(OBJ, Parent)
	
	Return(OBJ)
End Function

Function UpdateButton%(OBJ%)
	Local Dist# = EntityDistanceSquared(me\Collider, OBJ)
	
	If Dist < 0.64 Then
		Local Temp% = CreatePivot()
		
		PositionEntity(Temp, EntityX(Camera), EntityY(Camera), EntityZ(Camera))
		PointEntity(Temp, OBJ)
		
		If EntityPick(Temp, 0.6) = OBJ Then
			If (Not d_I\ClosestButton) Then 
				d_I\ClosestButton = OBJ
			Else
				If Dist < EntityDistanceSquared(me\Collider, d_I\ClosestButton) Then d_I\ClosestButton = OBJ
			EndIf							
		EndIf
		FreeEntity(Temp)
	EndIf			
End Function

Type BrokenDoor
	Field IsBroken%
	Field x#
	Field z#
End Type

Global bk.BrokenDoor = New BrokenDoor

Type Doors
	Field OBJ%, OBJ2%, FrameOBJ%, Buttons%[2]
	Field Locked%, LockedUpdated%, Open%, Angle%, OpenState#, FastOpen%
	Field DoorType%, Dist#
	Field Timer%, TimerState#
	Field KeyCard%
	Field room.Rooms
	Field DisableWaypoint%
	Field SoundCHN%, SoundCHN2%
	Field Code$
	Field AutoClose%
	Field LinkedDoor.Doors
	Field IsElevatorDoor% = False
	Field MTFClose% = True
	Field ElevatorPanel%[2]
End Type 

; ~ Door ID Constants
;[Block]
Const DEFAULT_DOOR% = 0
Const ELEVATOR_DOOR% = 1
Const HEAVY_DOOR% = 2
Const BIG_DOOR% = 3
Const OFFICE_DOOR% = 4
Const WOODEN_DOOR% = 5
Const ONE_SIDED_DOOR% = 6
Const SCP_914_DOOR% = 7
;Const WINDOWED_DOOR% = 8
;[End Block]

Function CreateDoor.Doors(x#, y#, z#, Angle#, room.Rooms, Open% = False, DoorType% = DEFAULT_DOOR, Keycard% = KEY_MISC, Code$ = "", CustomParent% = 0)
	Local d.Doors
	Local Parent%, i%
	Local FrameScaleX#, FrameScaleY#, FrameScaleZ#
	Local DoorScaleX#, DoorScaleY#, DoorScaleZ#
	Local FrameModelID%, DoorModelID_1%, DoorModelID_2%, ButtonID%
	
	If room <> Null Then
		Parent = room\OBJ
	Else
		Parent = CustomParent
	EndIf
	
	d.Doors = New Doors
	
	; ~ (Keycard > 0) - KEY CARD
	; ~ (Keycard = 0) - DEFAULT
	; ~ (Keycard > -4 And Keycard < 0) - HAND
	; ~ (Keycard <= -4) - KEY
	
	d\KeyCard = Keycard 
	
	d\Code = Code
	
	d\Angle = Angle
	d\Open = Open		
	
	; ~ Set "d\Locked = 1" for elevator doors to fix buttons color. Anyway the door will be unlocked by "UpdateElevators" function. -- Jabka
	If DoorType = ELEVATOR_DOOR Then d\Locked = 1
	d\DoorType = DoorType
	If DoorType = SCP_914_DOOR Then DoorType = ONE_SIDED_DOOR
	
	d\MTFClose = True
	If Open And ((DoorType = DEFAULT_DOOR) Lor (DoorType = HEAVY_DOOR)) And (Keycard = 0) And (Code = "") And Rand(10) = 1 Then d\AutoClose = True
	
	d\room = room
	
	Select DoorType
		Case DEFAULT_DOOR
			;[Block]
			DoorModelID_1 = DOOR_DEFAULT_MODEL
			DoorModelID_2 = DoorModelID_1
			DoorScaleX = 203.0 * RoomScale / MeshWidth(d_I\DoorModelID[DoorModelID_1]) : DoorScaleY = 313.0 * RoomScale / MeshHeight(d_I\DoorModelID[DoorModelID_1]) : DoorScaleZ = 15.0 * RoomScale / MeshDepth(d_I\DoorModelID[DoorModelID_1])
			
			FrameModelID = DOOR_DEFAULT_FRAME_MODEL
			FrameScaleX = RoomScale : FrameScaleY = RoomScale : FrameScaleZ = RoomScale
			;[End Block]
		Case ONE_SIDED_DOOR
			;[Block]
			DoorModelID_1 = DOOR_ONE_SIDED_MODEL
			DoorModelID_2 = DoorModelID_1
			DoorScaleX = 203.0 * RoomScale / MeshWidth(d_I\DoorModelID[DoorModelID_1]) : DoorScaleY = 313.0 * RoomScale / MeshHeight(d_I\DoorModelID[DoorModelID_1]) : DoorScaleZ = 15.0 * RoomScale / MeshDepth(d_I\DoorModelID[DoorModelID_1])
			
			FrameModelID = DOOR_DEFAULT_FRAME_MODEL
			FrameScaleX = RoomScale : FrameScaleY = RoomScale : FrameScaleZ = RoomScale
			;[End Block]
		Case ELEVATOR_DOOR
			;[Block]
			DoorModelID_1 = DOOR_ELEVATOR_MODEL
			DoorModelID_2 = DoorModelID_1
			DoorScaleX = RoomScale : DoorScaleY = RoomScale : DoorScaleZ = RoomScale
			
			FrameModelID = DOOR_DEFAULT_FRAME_MODEL
			FrameScaleX = RoomScale : FrameScaleY = RoomScale : FrameScaleZ = RoomScale
			;[End Block]
		Case HEAVY_DOOR
			;[Block]
			DoorModelID_1 = DOOR_HEAVY_MODEL_1
			DoorModelID_2 = DOOR_HEAVY_MODEL_2
			DoorScaleX = RoomScale : DoorScaleY = RoomScale : DoorScaleZ = RoomScale
			
			FrameModelID = DOOR_DEFAULT_FRAME_MODEL
			FrameScaleX = RoomScale : FrameScaleY = RoomScale : FrameScaleZ = RoomScale
			;[End Block]
		Case BIG_DOOR
			;[Block]
			DoorModelID_1 = DOOR_BIG_MODEL_1
			DoorModelID_2 = DOOR_BIG_MODEL_2
			DoorScaleX = 55.0 * RoomScale : DoorScaleY = 55.0 * RoomScale : DoorScaleZ = 55.0 * RoomScale
			
			FrameModelID = DOOR_BIG_FRAME_MODEL
			FrameScaleX = 55.0 * RoomScale : FrameScaleY = 55.0 * RoomScale : FrameScaleZ = 55.0 * RoomScale
			;[End Block]
		Case OFFICE_DOOR
			;[Block]
			DoorModelID_1 = DOOR_OFFICE_MODEL
			DoorScaleX = RoomScale : DoorScaleY = RoomScale : DoorScaleZ = RoomScale
			
			FrameModelID = DOOR_OFFICE_FRAME_MODEL
			FrameScaleX = RoomScale : FrameScaleY = RoomScale : FrameScaleZ = RoomScale
			;[End Block]
		Case WOODEN_DOOR
			;[Block]
			DoorModelID_1 = DOOR_WOODEN_MODEL
			DoorScaleX = 46.0 * RoomScale : DoorScaleY = 44.0 * RoomScale : DoorScaleZ = 46.0 * RoomScale
			
			FrameModelID = DOOR_WOODEN_FRAME_MODEL
			FrameScaleX = 45.0 * RoomScale : FrameScaleY = 44.0 * RoomScale : FrameScaleZ = 80.0 * RoomScale
			;[End Block]
	End Select
	
	d\FrameOBJ = CopyEntity(d_I\DoorFrameModelID[FrameModelID])
	ScaleEntity(d\FrameOBJ, FrameScaleX, FrameScaleY, FrameScaleZ)
	PositionEntity(d\FrameOBJ, x, y, z)
	If DoorType = BIG_DOOR Then EntityType(d\FrameOBJ, HIT_MAP)
	EntityPickMode(d\FrameOBJ, 2)
	
	d\OBJ = CopyEntity(d_I\DoorModelID[DoorModelID_1])
	ScaleEntity(d\OBJ, DoorScaleX, DoorScaleY, DoorScaleZ)
	PositionEntity(d\OBJ, x, y, z)
	RotateEntity(d\OBJ, 0.0, Angle, 0.0)
	EntityType(d\OBJ, HIT_MAP)
	EntityPickMode(d\OBJ, 2)
	CreateCollBox(d\OBJ)
	EntityParent(d\OBJ, Parent)
	
	If DoorType <> OFFICE_DOOR And DoorType <> WOODEN_DOOR Then
		d\OBJ2 = CopyEntity(d_I\DoorModelID[DoorModelID_2])
		ScaleEntity(d\OBJ2, DoorScaleX, DoorScaleY, DoorScaleZ)
		PositionEntity(d\OBJ2, x, y, z)
		RotateEntity(d\OBJ2, 0.0, Angle + ((DoorType <> BIG_DOOR) * 180.0), 0.0)
		EntityType(d\OBJ2, HIT_MAP)
		EntityPickMode(d\OBJ2, 2)
		CreateCollBox(d\OBJ2)
		EntityParent(d\OBJ2, Parent)
	EndIf
	
	For i = 0 To 1
		If (DoorType = OFFICE_DOOR) Lor (DoorType = WOODEN_DOOR) Then
			If (Not d\Open) Then
				d\Buttons[i] = CreatePivot()
				PositionEntity(d\Buttons[i], x - 0.22, y + 0.6, z + 0.1 + (i * (-0.2)))
				EntityRadius(d\Buttons[i], 0.1)
				EntityPickMode(d\Buttons[i], 1)
				EntityParent(d\Buttons[i], d\FrameOBJ)
			EndIf
		Else
			If DoorType = ELEVATOR_DOOR Then
				ButtonID = i * BUTTON_ELEVATOR
				
				d\ElevatorPanel[i] = CopyEntity(d_I\ElevatorPanelModel)
				ScaleEntity(d\ElevatorPanel[i], RoomScale, RoomScale, RoomScale)
				RotateEntity(d\ElevatorPanel[i], 0.0, i * 180.0, 0.0)
				PositionEntity(d\ElevatorPanel[i], x, y + 1.27, z + 0.13 + (i * (-0.26)))
				EntityParent(d\ElevatorPanel[i], d\FrameOBJ)
			Else
				If Code <> "" Then
					ButtonID = BUTTON_KEYPAD
				ElseIf Keycard > KEY_MISC Then
					ButtonID = BUTTON_KEYCARD
				ElseIf Keycard > KEY_860 And Keycard < KEY_MISC
					ButtonID = BUTTON_SCANNER
				Else
					ButtonID = BUTTON_DEFAULT
				EndIf
			EndIf
			d\Buttons[i] = CreateButton(ButtonID, x + ((DoorType <> BIG_DOOR) * (0.6 + (i * (-1.2)))) + ((DoorType = BIG_DOOR) * ((-432.0 + (i * 864.0)) * RoomScale)), y + 0.7, z + ((DoorType <> BIG_DOOR) * ((-0.1) + (i * 0.2))) + ((DoorType = BIG_DOOR) * ((192.0 + (i * (-384.0)))) * RoomScale), 0.0, ((DoorType <> BIG_DOOR) * (i * 180.0)) + ((DoorType = BIG_DOOR) * (90.0 + (i * 180.0))), 0.0, d\FrameOBJ, d\Locked)
		EndIf
	Next
	RotateEntity(d\FrameOBJ, 0.0, Angle, 0.0)
	EntityParent(d\FrameOBJ, Parent)
	
	Return(d)
End Function

Function UpdateDoors%()
	Local d.Doors, p.Particles
	Local x#, z#, Dist#, i%, FindButton%
	
	If UpdateTimer <= 0.0 Then
		For d.Doors = Each Doors
			Local xDist# = Abs(EntityX(me\Collider) - EntityX(d\FrameOBJ, True))
			Local zDist# = Abs(EntityZ(me\Collider) - EntityZ(d\FrameOBJ, True))
			
			d\Dist = xDist + zDist
		Next
	EndIf
	
	d_I\ClosestButton = 0
	d_I\ClosestDoor = Null
	
	For d.Doors = Each Doors
		If (d\Dist <= HideDistance) Lor (d\IsElevatorDoor > 0) Then ; ~ Make elevator doors update everytime because if not, this can cause a bug where the elevators suddenly won't work, most noticeable in room2_mt -- ENDSHN
			; ~ Automatically disable d\AutoClose if the door is locked because if not, this can cause a locked door to be closed and player get stuck -- Jabka
			If d\AutoClose And d\Locked > 0 Then d\AutoClose = False
			
			FindButton = True
			If d\Open And ((d\DoorType = OFFICE_DOOR) Lor (d\DoorType = WOODEN_DOOR)) Then FindButton = False
			
			If ((d\OpenState >= 180.0 Lor d\OpenState <= 0.0) And FindButton) And (Not GrabbedEntity) Then
				For i = 0 To 1
					If d\Buttons[i] <> 0 Then
						If Abs(EntityX(me\Collider) - EntityX(d\Buttons[i], True)) < 1.0 Then 
							If Abs(EntityZ(me\Collider) - EntityZ(d\Buttons[i], True)) < 1.0 Then 
								Dist = DistanceSquared(EntityX(me\Collider, True), EntityX(d\Buttons[i], True), EntityZ(me\Collider, True), EntityZ(d\Buttons[i], True))
								If Dist < 0.64 Then
									Local Temp% = CreatePivot()
									
									PositionEntity(Temp, EntityX(Camera), EntityY(Camera), EntityZ(Camera))
									PointEntity(Temp, d\Buttons[i])
									
									If EntityPick(Temp, 0.6) = d\Buttons[i] Then
										If (Not d_I\ClosestButton) Then
											d_I\ClosestButton = d\Buttons[i]
											d_I\ClosestDoor = d
										Else
											If Dist < EntityDistanceSquared(me\Collider, d_I\ClosestButton) Then d_I\ClosestButton = d\Buttons[i] : d_I\ClosestDoor = d
										EndIf							
									EndIf
									FreeEntity(Temp)
								EndIf							
							EndIf
						EndIf
					EndIf
				Next
			EndIf
			
			If d\Open Then
				If d\OpenState < 180.0 Then
					Select d\DoorType
						Case DEFAULT_DOOR;, WINDOWED_DOOR
							;[Block]
							d\OpenState = Min(180.0, d\OpenState + (fps\Factor[0] * 2.0 * (d\FastOpen + 1)))
							MoveEntity(d\OBJ, Sin(d\OpenState) * (d\FastOpen * 2 + 1) * fps\Factor[0] / 80.0, 0.0, 0.0)
							If d\OBJ2 <> 0 Then MoveEntity(d\OBJ2, Sin(d\OpenState) * (d\FastOpen + 1) * fps\Factor[0] / 80.0, 0.0, 0.0)	
							;[End Block]
						Case ELEVATOR_DOOR
							;[Block]
							d\OpenState = Min(180.0, d\OpenState + (fps\Factor[0] * 2.0 * (d\FastOpen + 1)))
							MoveEntity(d\OBJ, Sin(d\OpenState) * (d\FastOpen * 2 + 1) * fps\Factor[0] / 162.0, 0.0, 0.0)
							If d\OBJ2 <> 0 Then MoveEntity(d\OBJ2, Sin(d\OpenState) * (d\FastOpen * 2 + 1) * fps\Factor[0] / 162.0, 0.0, 0.0)
							;[End Block]
						Case HEAVY_DOOR
							;[Block]
							d\OpenState = Min(180.0, d\OpenState + (fps\Factor[0] * 2.0 * (d\FastOpen + 1)))
							MoveEntity(d\OBJ, Sin(d\OpenState) * (d\FastOpen + 1) * fps\Factor[0] / 85.0, 0.0, 0.0)
							If d\OBJ2 <> 0 Then MoveEntity(d\OBJ2, Sin(d\OpenState) * (d\FastOpen * 2 + 1) * fps\Factor[0] / 120.0, 0.0, 0.0)
							;[End Block]
						Case BIG_DOOR
							;[Block]
							d\OpenState = Min(180.0, d\OpenState + (fps\Factor[0] * 0.8))
							MoveEntity(d\OBJ, Sin(d\OpenState) * fps\Factor[0] / 180.0, 0.0, 0.0)
							If d\OBJ2 <> 0 Then MoveEntity(d\OBJ2, (-Sin(d\OpenState)) * fps\Factor[0] / 180.0, 0.0, 0.0)
							;[End Block]
						Case OFFICE_DOOR, WOODEN_DOOR
							;[Block]
							d\OpenState = CurveValue(180.0, d\OpenState, 40.0) + (fps\Factor[0] * 0.01)
							RotateEntity(d\OBJ, 0.0, PlayerRoom\Angle + d\Angle + (d\OpenState / 2.5), 0.0)
							If d\DoorType = OFFICE_DOOR Then
								Animate2(d\OBJ, AnimTime(d\OBJ), 1.0, 41.0, 1.2, False)
							EndIf
							;[End Block]
						Case ONE_SIDED_DOOR
							;[Block]
							d\OpenState = Min(180.0, d\OpenState + (fps\Factor[0] * 2.0 * (d\FastOpen + 1)))
							MoveEntity(d\OBJ, Sin(d\OpenState) * (d\FastOpen * 2 + 1) * fps\Factor[0] / 80.0, 0.0, 0.0)
							If d\OBJ2 <> 0 Then MoveEntity(d\OBJ2, Sin(d\OpenState) * (d\FastOpen + 1) * (-fps\Factor[0]) / 80.0, 0.0, 0.0)	
							;[End Block]	
						Case SCP_914_DOOR ; ~ Used for SCP-914 only
							;[Block]
							d\OpenState = Min(180.0, d\OpenState + (fps\Factor[0] * 1.4))
							MoveEntity(d\OBJ, Sin(d\OpenState) * fps\Factor[0] / 114.0, 0.0, 0.0)
							If d\OBJ2 <> 0 Then MoveEntity(d\OBJ2, Sin(d\OpenState) * (-fps\Factor[0]) / 114.0, 0.0, 0.0)
							;[End Block]
					End Select
				Else
					d\FastOpen = 0
					ResetEntity(d\OBJ)
					If d\OBJ2 <> 0 Then ResetEntity(d\OBJ2)
					If d\TimerState > 0.0 Then
						d\TimerState = Max(0.0, d\TimerState - fps\Factor[0])
						If d\TimerState + fps\Factor[0] > 110.0 And d\TimerState <= 110.0 Then d\SoundCHN = PlaySound2(CautionSFX, Camera, d\OBJ)
						
						If d\TimerState = 0.0 Then 
							d\Open = (Not d\Open)
							If d\DoorType <> DEFAULT_DOOR And d\DoorType <> ONE_SIDED_DOOR Then
								d\SoundCHN = PlaySound2(CloseDoorSFX(d\DoorType, Rand(0, 2)), Camera, d\OBJ)
							Else
								d\SoundCHN = PlaySound2(CloseDoorSFX(0, Rand(0, 2)), Camera, d\OBJ)
							EndIf
						EndIf
					EndIf
					If d\AutoClose And RemoteDoorOn Then
						If EntityDistanceSquared(Camera, d\OBJ) < 4.41 Then
							If (Not I_714\Using) And wi\GasMask <> 3 And wi\HazmatSuit <> 3 Then PlaySound_Strict(HorrorSFX[7])
							UseDoor(d, True) : d\AutoClose = False
						EndIf
					EndIf				
				EndIf
			Else
				If d\OpenState > 0.0 Then
					Select d\DoorType
						Case DEFAULT_DOOR;, WINDOWED_DOOR
							;[Block]
							d\OpenState = Max(0.0, d\OpenState - (fps\Factor[0] * 2.0 * (d\FastOpen + 1)))
							MoveEntity(d\OBJ, Sin(d\OpenState) * (-fps\Factor[0]) * (d\FastOpen + 1) / 80.0, 0.0, 0.0)
							If d\OBJ2 <> 0 Then MoveEntity(d\OBJ2, Sin(d\OpenState) * (d\FastOpen + 1) * (-fps\Factor[0]) / 80.0, 0.0, 0.0)	
							;[End Block]
						Case ELEVATOR_DOOR
							;[Block]
							d\OpenState = Max(0.0, d\OpenState - (fps\Factor[0] * 2.0 * (d\FastOpen + 1)))
							MoveEntity(d\OBJ, Sin(d\OpenState) * (-fps\Factor[0]) * (d\FastOpen + 1) / 162.0, 0.0, 0.0)
							If d\OBJ2 <> 0 Then MoveEntity(d\OBJ2, Sin(d\OpenState) * (d\FastOpen + 1) * (-fps\Factor[0]) / 162.0, 0.0, 0.0)
							;[End Block]
						Case HEAVY_DOOR
							;[Block]
							d\OpenState = Max(0.0, d\OpenState - (fps\Factor[0] * 2.0 * (d\FastOpen + 1)))
							MoveEntity(d\OBJ, Sin(d\OpenState) * (-fps\Factor[0]) * (d\FastOpen + 1) / 85.0, 0.0, 0.0)
							If d\OBJ2 <> 0 Then MoveEntity(d\OBJ2, Sin(d\OpenState) * (d\FastOpen + 1) * (-fps\Factor[0]) / 120.0, 0.0, 0.0)
							;[End Block]
						Case BIG_DOOR
							;[Block]
							d\OpenState = Max(0.0, d\OpenState - (fps\Factor[0] * 0.8))
							MoveEntity(d\OBJ, Sin(d\OpenState) * (-fps\Factor[0]) / 180.0, 0.0, 0.0)
							If d\OBJ2 <> 0 Then MoveEntity(d\OBJ2, Sin(d\OpenState) * fps\Factor[0] / 180.0, 0.0, 0.0)
							If d\OpenState < 15.0 And d\OpenState + fps\Factor[0] >= 15.0
								If opt\ParticleAmount = 2 Then
									For i = 0 To Rand(75, 99)
										Local Pvt% = CreatePivot()
										
										PositionEntity(Pvt, EntityX(d\FrameOBJ, True) + Rnd(-0.2, 0.2), EntityY(d\FrameOBJ, True) + Rnd(0.0, 1.2), EntityZ(d\FrameOBJ, True) + Rnd(-0.2, 0.2))
										RotateEntity(Pvt, 0.0, Rnd(360.0), 0.0)
										
										p.Particles = CreateParticle(PARTICLE_DUST, EntityX(Pvt), EntityY(Pvt), EntityZ(Pvt), 0.002, 0.0, 300.0)
										p\Speed = 0.005 : p\SizeChange = -0.00001 : p\Size = 0.01 : p\AlphaChange = -0.01
										RotateEntity(p\Pvt, Rnd(-20.0, 20.0), Rnd(360.0), 0.0)
										ScaleSprite(p\OBJ, p\Size, p\Size)
										EntityOrder(p\OBJ, -1)
										FreeEntity(Pvt)
									Next
								EndIf
							EndIf
							;[End Block]
						Case OFFICE_DOOR, WOODEN_DOOR
							;[Block]
							d\OpenState = 0.0
							RotateEntity(d\OBJ, 0.0, EntityYaw(d\FrameOBJ), 0.0)
							;[End Block]
						Case ONE_SIDED_DOOR
							;[Block]
							d\OpenState = Max(0.0, d\OpenState - (fps\Factor[0] * 2.0 * (d\FastOpen + 1)))
							MoveEntity(d\OBJ, Sin(d\OpenState) * (-fps\Factor[0]) * (d\FastOpen + 1) / 80.0, 0.0, 0.0)
							If d\OBJ2 <> 0 Then MoveEntity(d\OBJ2, Sin(d\OpenState) * (d\FastOpen + 1) * fps\Factor[0] / 80.0, 0.0, 0.0)
							;[End Block]
						Case SCP_914_DOOR ; ~ Used for SCP-914 only
							;[Block]
							d\OpenState = Min(180.0, d\OpenState - (fps\Factor[0] * 1.4))
							MoveEntity(d\OBJ, Sin(d\OpenState) * (-fps\Factor[0]) / 114.0, 0.0, 0.0)
							If d\OBJ2 <> 0 Then MoveEntity(d\OBJ2, Sin(d\OpenState) * fps\Factor[0] / 114.0, 0.0, 0.0)
							;[End Block]
					End Select
					
					If d\Angle = 0.0 Lor d\Angle = 180.0 Then
						If Abs(EntityZ(d\FrameOBJ, True) - EntityZ(me\Collider)) < 0.15 Then
							If Abs(EntityX(d\FrameOBJ, True) - EntityX(me\Collider)) < 0.5 * ((d\DoorType = BIG_DOOR) * 3) Then
								z = CurveValue(EntityZ(d\FrameOBJ, True) + 0.15 * Sgn(EntityZ(me\Collider) - EntityZ(d\FrameOBJ, True)), EntityZ(me\Collider), 5.0)
								PositionEntity(me\Collider, EntityX(me\Collider), EntityY(me\Collider), z)
							EndIf
						EndIf
					Else
						If Abs(EntityX(d\FrameOBJ, True) - EntityX(me\Collider)) < 0.15 Then	
							If Abs(EntityZ(d\FrameOBJ, True) - EntityZ(me\Collider)) < 0.5 * ((d\DoorType = BIG_DOOR) * 3) Then
								x = CurveValue(EntityX(d\FrameOBJ, True) + 0.15 * Sgn(EntityX(me\Collider) - EntityX(d\FrameOBJ, True)), EntityX(me\Collider), 5.0)
								PositionEntity(me\Collider, x, EntityY(me\Collider), EntityZ(me\Collider))
							EndIf
						EndIf
					EndIf
				Else
					d\FastOpen = 0
					PositionEntity(d\OBJ, EntityX(d\FrameOBJ, True), EntityY(d\FrameOBJ, True), EntityZ(d\FrameOBJ, True))
					If d\DoorType = DEFAULT_DOOR Lor d\DoorType = ONE_SIDED_DOOR Lor d\DoorType = SCP_914_DOOR Then
						MoveEntity(d\OBJ, 0.0, 0.0, 8.0 * RoomScale)
					ElseIf d\DoorType = OFFICE_DOOR Lor d\DoorType = WOODEN_DOOR
						MoveEntity(d\OBJ, (((d\DoorType = OFFICE_DOOR) * 92.0) + ((d\DoorType = WOODEN_DOOR) * 68.0)) * RoomScale, 0.0, 0.0)
					EndIf
					If d\OBJ2 <> 0 Then
						PositionEntity(d\OBJ2, EntityX(d\FrameOBJ, True), EntityY(d\FrameOBJ, True), EntityZ(d\FrameOBJ, True))
						If d\DoorType = DEFAULT_DOOR Lor d\DoorType = ONE_SIDED_DOOR Lor d\DoorType = SCP_914_DOOR Then
							MoveEntity(d\OBJ2, 0.0, 0.0, 8.0 * RoomScale)
						EndIf
					EndIf
				EndIf
			EndIf
			UpdateSoundOrigin(d\SoundCHN, Camera, d\FrameOBJ)
			
			If d\DoorType <> OFFICE_DOOR And d\DoorType <> WOODEN_DOOR Then
				If d\Locked <> d\LockedUpdated Then
					If d\Locked = 1 Then
						For i = 0 To 1
							If d\Buttons[i] <> 0 Then EntityTexture(d\Buttons[i], d_I\ButtonTextureID[BUTTON_RED_TEXTURE])
						Next
					Else
						For i = 0 To 1
							If d\Buttons[i] <> 0 Then EntityTexture(d\Buttons[i], d_I\ButtonTextureID[BUTTON_GREEN_TEXTURE])
						Next
					EndIf
					d\LockedUpdated = d\Locked
				EndIf
			EndIf
			
			If d\DoorType = BIG_DOOR Then
				If d\Locked = 2 Then
					If d\OpenState > 48.0 Then
						d\Open = False
						d\OpenState = Min(d\OpenState, 48.0)
					EndIf	
				EndIf
			EndIf
		EndIf
	Next
End Function

Global PlayerElevatorFloor%, ToElevatorFloor%, PlayerInsideElevator%

; ~ Elevator Floor Constants
;[Block]
Const LowerFloor% = -1
Const NullFloor% = 0
Const UpperFloor% = 1
;[End Block]

Function FindPlayerFloor%()
	Local PlayerY# = Floor(EntityY(me\Collider))
	
	If PlayerY < 0.0 Then
		Return(LowerFloor)
	ElseIf PlayerY > 0.0
		Return(UpperFloor)
	EndIf
	Return(NullFloor)
End Function

Function FindFloor%(e.Events)
	Select e\EventID
		Case e_room3_storage, e_cont1_079, e_cont1_106, e_cont2_008, e_cont2_049, e_cont2_409
			;[Block]
			Return(LowerFloor)
			;[End Block]
		Case e_room2_mt, e_room2_nuke, e_gate_a_entrance, e_gate_b_entrance
			;[Block]
			Return(UpperFloor)
			;[End Block]
	End Select
End Function

Function UpdateElevatorPanel%(d.Doors)
	Local TextureID%, i%
	
	; ~ 21 = DEFAULT
	; ~ 22 = UP
	; ~ 23 = DOWN
	
	If PlayerInsideElevator Then
		If PlayerElevatorFloor = LowerFloor Then
			TextureID = ELEVATOR_PANEL_UP
		ElseIf PlayerElevatorFloor = UpperFloor
			TextureID = ELEVATOR_PANEL_DOWN
		Else
			If ToElevatorFloor = LowerFloor Then
				TextureID = ELEVATOR_PANEL_DOWN
			Else
				TextureID = ELEVATOR_PANEL_UP
			EndIf
		EndIf
	Else
		If PlayerElevatorFloor = LowerFloor Then
			TextureID = ELEVATOR_PANEL_DOWN
		ElseIf PlayerElevatorFloor = UpperFloor
			TextureID = ELEVATOR_PANEL_UP
		Else
			If ToElevatorFloor = LowerFloor Then
				TextureID = ELEVATOR_PANEL_UP
			Else
				TextureID = ELEVATOR_PANEL_DOWN
			EndIf
		EndIf
	EndIf
	
	For i = 0 To 1
		If d\ElevatorPanel[i] <> 0 Then EntityTexture(d\ElevatorPanel[i], d_I\ElevatorPanelTextureID[TextureID])
	Next
End Function

Function ClearElevatorPanelTexture%(d.Doors)
	Local i%
	
	For i = 0 To 1
		If d\ElevatorPanel[i] <> 0 Then EntityTexture(d\ElevatorPanel[i], d_I\ElevatorPanelTextureID[ELEVATOR_PANEL_IDLE])
	Next
End Function

Function UpdateElevators#(State#, door1.Doors, door2.Doors, FirstPivot%, SecondPivot%, event.Events, IgnoreRotation% = True)
	Local n.NPCs, it.Items, de.Decals
	Local x#, z#, Dist#, Dir#
	
	; ~ First, check the current floor the player is walking on
	PlayerElevatorFloor = FindPlayerFloor()
	; ~ Second, find the floor the lower or upper floor
	ToElevatorFloor = FindFloor(event)
	
	; ~ After, determine if the player inside the elevator
	PlayerInsideElevator = False
	If IsInsideArea(FirstPivot, 280.0 * RoomScale) Lor IsInsideArea(SecondPivot, 280.0 * RoomScale) Then
		PlayerInsideElevator = True
	EndIf
	
	door1\IsElevatorDoor = 1
	door2\IsElevatorDoor = 1
	If door1\Open And (Not door2\Open) And door1\OpenState = 180.0 Then 
		State = -1.0
		door1\Locked = 0
		If (d_I\ClosestButton = door2\Buttons[0] Lor d_I\ClosestButton = door2\Buttons[1]) And mo\MouseHit1 Then
			UseDoor(door1, True)
			UpdateElevatorPanel(door2)
		EndIf
	ElseIf door2\Open And (Not door1\Open) And door2\OpenState = 180.0
		State = 1.0
		door2\Locked = 0
		If (d_I\ClosestButton = door1\Buttons[0] Lor d_I\ClosestButton = door1\Buttons[1]) And mo\MouseHit1 Then
			UseDoor(door2, True)
			UpdateElevatorPanel(door1)
		EndIf
	ElseIf Abs(door1\OpenState - door2\OpenState) < 0.2
		door1\IsElevatorDoor = 2
		door2\IsElevatorDoor = 2
	EndIf
	
	door1\Locked = 1
	door2\Locked = 1
	If door1\Open Then
		door1\IsElevatorDoor = 3
		If PlayerInsideElevator Then
			door1\Locked = 0
			door1\IsElevatorDoor = 1
		EndIf
	EndIf
	If door2\Open Then
		door2\IsElevatorDoor = 3
		If PlayerInsideElevator Then
			door2\Locked = 0
			door2\IsElevatorDoor = 1
		EndIf	
	EndIf
	
	If (Not door1\Open) And (Not door2\Open) Then
		door1\Locked = 1
		door2\Locked = 1
		If door1\OpenState = 0.0 And door2\OpenState = 0.0 Then
			If State < 0.0 Then
				State = State - fps\Factor[0]
				If PlayerInsideElevator Then
					If (Not door1\SoundCHN2) Then
						door1\SoundCHN2 = PlaySound_Strict(ElevatorMoveSFX)
					Else
						If (Not ChannelPlaying(door1\SoundCHN2)) Then door1\SoundCHN2 = PlaySound_Strict(ElevatorMoveSFX)
					EndIf
					
					me\CameraShake = Sin(Abs(State) / 3.0) * 0.3
					
					UpdateElevatorPanel(door1)
				EndIf
				
				If State < -500.0 Then
					door1\Locked = 1
					door2\Locked = 0
					State = 0.0
					If PlayerInsideElevator Then
						If (Not IgnoreRotation) Then
							Dist = Distance(EntityX(me\Collider, True), EntityX(FirstPivot, True), EntityZ(me\Collider, True), EntityZ(FirstPivot, True))
							Dir = PointDirection(EntityX(me\Collider, True), EntityZ(me\Collider, True), EntityX(FirstPivot, True), EntityZ(FirstPivot, True))
							Dir = Dir + EntityYaw(SecondPivot, True) - EntityYaw(FirstPivot, True)
							Dir = WrapAngle(Dir)
							x = Max(Min(Cos(Dir) * Dist, (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
							z = Max(Min(Sin(Dir) * Dist, (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
							RotateEntity(me\Collider, EntityPitch(me\Collider, True), EntityYaw(SecondPivot, True) + AngleDist(EntityYaw(me\Collider, True), EntityYaw(FirstPivot, True)), EntityRoll(me\Collider, True), True)
						Else
							x = Max(Min((EntityX(me\Collider) - EntityX(FirstPivot, True)), (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
							z = Max(Min((EntityZ(me\Collider) - EntityZ(FirstPivot, True)), (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
						EndIf
						
						TeleportEntity(me\Collider, EntityX(SecondPivot, True) + x, (0.1 * fps\Factor[0]) + EntityY(SecondPivot, True) + (EntityY(me\Collider) - EntityY(FirstPivot, True)), EntityZ(SecondPivot, True) + z, 0.3, True)
						UpdateTimer = 0.0
						me\DropSpeed = 0.0
						UpdateDoors()
						UpdateRooms()
						
						door1\SoundCHN = PlaySound2(OpenDoorSFX(ELEVATOR_DOOR, Rand(0, 2)), Camera, door1\OBJ)
					EndIf
					
					For n.NPCs = Each NPCs
						If Abs(EntityX(n\Collider) - EntityX(FirstPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
							If Abs(EntityZ(n\Collider) - EntityZ(FirstPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
								If Abs(EntityY(n\Collider) - EntityY(FirstPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
									If (Not IgnoreRotation) Then
										Dist = Distance(EntityX(n\Collider, True), EntityX(FirstPivot, True), EntityZ(n\Collider, True), EntityZ(FirstPivot, True))
										Dir = PointDirection(EntityX(n\Collider, True), EntityZ(n\Collider, True), EntityX(FirstPivot, True), EntityZ(FirstPivot, True))
										Dir = Dir + EntityYaw(SecondPivot, True) - EntityYaw(FirstPivot, True)
										Dir = WrapAngle(Dir)
										x = Max(Min(Cos(Dir) * Dist, (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										z = Max(Min(Sin(Dir) * Dist, (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										RotateEntity(n\Collider, EntityPitch(n\Collider, True), EntityYaw(SecondPivot, True) + AngleDist(EntityYaw(n\Collider, True), EntityYaw(FirstPivot, True)), EntityRoll(n\Collider, True), True)
									Else
										x = Max(Min((EntityX(n\Collider) - EntityX(FirstPivot, True)), (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										z = Max(Min((EntityZ(n\Collider) - EntityZ(FirstPivot, True)), (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
									EndIf
									
									TeleportEntity(n\Collider, EntityX(SecondPivot, True) + x, (0.1 * fps\Factor[0]) + EntityY(SecondPivot, True) + (EntityY(n\Collider) - EntityY(FirstPivot, True)), EntityZ(SecondPivot, True) + z, n\CollRadius, True)
									If n = n_I\Curr173 Then
										n_I\Curr173\IdleTimer = 10.0
									EndIf
								EndIf
							EndIf
						EndIf
					Next
					
					For it.Items = Each Items
						If Abs(EntityX(it\Collider) - EntityX(FirstPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
							If Abs(EntityZ(it\Collider) - EntityZ(FirstPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
								If Abs(EntityY(it\Collider) - EntityY(FirstPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
									If (Not IgnoreRotation) Then
										Dist = Distance(EntityX(it\Collider, True), EntityX(FirstPivot, True), EntityZ(it\Collider, True), EntityZ(FirstPivot, True))
										Dir = PointDirection(EntityX(it\Collider, True), EntityZ(it\Collider, True), EntityX(FirstPivot, True), EntityZ(FirstPivot, True))
										Dir = Dir + EntityYaw(SecondPivot, True) - EntityYaw(FirstPivot, True)
										Dir = WrapAngle(Dir)
										x = Max(Min(Cos(Dir) * Dist, (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										z = Max(Min(Sin(Dir) * Dist, (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										RotateEntity(it\Collider, EntityPitch(it\Collider, True), EntityYaw(SecondPivot, True) + AngleDist(EntityYaw(it\Collider, True), EntityYaw(FirstPivot, True)), EntityRoll(it\Collider, True), True)
									Else
										x = Max(Min((EntityX(it\Collider) - EntityX(FirstPivot, True)), (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										z = Max(Min((EntityZ(it\Collider) - EntityZ(FirstPivot, True)), (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
									EndIf
									TeleportEntity(it\Collider, EntityX(SecondPivot, True) + x, (0.1 * fps\Factor[0]) + EntityY(SecondPivot, True) + (EntityY(it\Collider) - EntityY(FirstPivot, True)), EntityZ(SecondPivot, True) + z, 0.01, True)
								EndIf
							EndIf
						EndIf
					Next
					
					For de.Decals = Each Decals
						If Abs(EntityX(de\OBJ) - EntityX(FirstPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
							If Abs(EntityZ(de\OBJ) - EntityZ(FirstPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
								If Abs(EntityY(de\OBJ) - EntityY(FirstPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
									If (Not IgnoreRotation) Then
										Dist = Distance(EntityX(de\OBJ, True), EntityX(FirstPivot, True), EntityZ(de\OBJ, True), EntityZ(FirstPivot, True))
										Dir = PointDirection(EntityX(de\OBJ, True), EntityZ(de\OBJ, True), EntityX(FirstPivot, True), EntityZ(FirstPivot, True))
										Dir = Dir + EntityYaw(SecondPivot, True) - EntityYaw(FirstPivot, True)
										Dir = WrapAngle(Dir)
										x = Max(Min(Cos(Dir) * Dist, (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										z = Max(Min(Sin(Dir) * Dist, (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										RotateEntity(de\OBJ, EntityPitch(de\OBJ, True), EntityYaw(SecondPivot, True) + AngleDist(EntityYaw(de\OBJ, True), EntityYaw(FirstPivot, True)), EntityRoll(de\OBJ, True), True)
									Else
										x = Max(Min((EntityX(de\OBJ) - EntityX(FirstPivot, True)), (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										z = Max(Min((EntityZ(de\OBJ) - EntityZ(FirstPivot, True)), (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
									EndIf
									TeleportEntity(de\OBJ, EntityX(SecondPivot, True) + x, (0.1 * fps\Factor[0]) + EntityY(SecondPivot, True) + (EntityY(de\OBJ) - EntityY(FirstPivot, True)), EntityZ(SecondPivot, True) + z, 0.01, True)
								EndIf
							EndIf
						EndIf
					Next
					UseDoor(door2, True, (Not PlayerInsideElevator))
					door1\Open = False
					
					; ~ Return to default panel texture
					ClearElevatorPanelTexture(door1)
					ClearElevatorPanelTexture(door2)
					PlaySound2(ElevatorBeepSFX, Camera, FirstPivot, 4.0)
				EndIf
			Else
				State = State + fps\Factor[0]
				If PlayerInsideElevator Then
					If (Not door2\SoundCHN2) Then
						door2\SoundCHN2 = PlaySound_Strict(ElevatorMoveSFX)
					Else
						If (Not ChannelPlaying(door2\SoundCHN2)) Then door2\SoundCHN2 = PlaySound_Strict(ElevatorMoveSFX)
					EndIf
					
					me\CameraShake = Sin(Abs(State) / 3.0) * 0.3
					
					UpdateElevatorPanel(door2)
				EndIf	
				
				If State > 500.0 Then
					door1\Locked = 0
					door2\Locked = 1
					State = 0.0
					If PlayerInsideElevator Then
						If (Not IgnoreRotation) Then
							Dist = Distance(EntityX(me\Collider, True), EntityX(SecondPivot, True), EntityZ(me\Collider, True), EntityZ(SecondPivot, True))
							Dir = PointDirection(EntityX(me\Collider, True), EntityZ(me\Collider, True), EntityX(SecondPivot, True), EntityZ(SecondPivot, True))
							Dir = Dir + EntityYaw(FirstPivot, True) - EntityYaw(SecondPivot, True)
							x = Max(Min(Cos(Dir) * Dist, (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
							z = Max(Min(Sin(Dir) * Dist, (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
							RotateEntity(me\Collider, EntityPitch(me\Collider, True), EntityYaw(SecondPivot, True) + AngleDist(EntityYaw(me\Collider, True), EntityYaw(FirstPivot, True)), EntityRoll(me\Collider, True), True)
						Else
							x = Max(Min((EntityX(me\Collider) - EntityX(SecondPivot, True)), (280 * RoomScale) - 0.22), ((-280) * RoomScale) + 0.22)
							z = Max(Min((EntityZ(me\Collider) - EntityZ(SecondPivot, True)), (280 * RoomScale) - 0.22), ((-280) * RoomScale) + 0.22)
						EndIf
						TeleportEntity(me\Collider, EntityX(FirstPivot, True) + x, (0.1 * fps\Factor[0]) + EntityY(FirstPivot, True) + (EntityY(me\Collider) - EntityY(SecondPivot, True)), EntityZ(FirstPivot, True) + z, 0.3, True)
						UpdateTimer = 0.0
						me\DropSpeed = 0.0
						UpdateDoors()
						UpdateRooms()
						
						door2\SoundCHN = PlaySound2(OpenDoorSFX(ELEVATOR_DOOR, Rand(0, 2)), Camera, door2\OBJ)
					EndIf
					
					For n.NPCs = Each NPCs
						If Abs(EntityX(n\Collider) - EntityX(SecondPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
							If Abs(EntityZ(n\Collider) - EntityZ(SecondPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
								If Abs(EntityY(n\Collider) - EntityY(SecondPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
									If (Not IgnoreRotation) Then
										Dist = Distance(EntityX(n\Collider, True), EntityX(SecondPivot, True), EntityZ(n\Collider, True), EntityZ(SecondPivot, True))
										Dir = PointDirection(EntityX(n\Collider, True), EntityZ(n\Collider, True), EntityX(SecondPivot, True), EntityZ(SecondPivot, True))
										Dir = Dir + EntityYaw(FirstPivot, True) - EntityYaw(SecondPivot, True)
										x = Max(Min(Cos(Dir) * Dist, (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										z = Max(Min(Sin(Dir) * Dist, (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										RotateEntity(n\Collider, EntityPitch(n\Collider, True), EntityYaw(SecondPivot, True) + AngleDist(EntityYaw(n\Collider, True), EntityYaw(FirstPivot, True)), EntityRoll(n\Collider, True), True)
									Else
										x = Max(Min((EntityX(n\Collider) - EntityX(SecondPivot, True)), (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										z = Max(Min((EntityZ(n\Collider) - EntityZ(SecondPivot, True)), (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
									EndIf
									TeleportEntity(n\Collider, EntityX(FirstPivot, True) + x, (0.1 * fps\Factor[0]) + EntityY(FirstPivot, True) + (EntityY(n\Collider) - EntityY(SecondPivot, True)), EntityZ(FirstPivot, True) + z, n\CollRadius, True)
									If n = n_I\Curr173 Then
										n_I\Curr173\IdleTimer = 10.0
									EndIf
								EndIf
							EndIf
						EndIf
					Next
					
					For it.Items = Each Items
						If Abs(EntityX(it\Collider) - EntityX(SecondPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
							If Abs(EntityZ(it\Collider) - EntityZ(SecondPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
								If Abs(EntityY(it\Collider) - EntityY(SecondPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
									If (Not IgnoreRotation) Then
										Dist = Distance(EntityX(it\Collider, True), EntityX(SecondPivot, True), EntityZ(it\Collider, True), EntityZ(SecondPivot, True))
										Dir = PointDirection(EntityX(it\Collider, True), EntityZ(it\Collider, True), EntityX(SecondPivot, True), EntityZ(SecondPivot, True))
										Dir = Dir + EntityYaw(FirstPivot, True) - EntityYaw(SecondPivot, True)
										x = Max(Min(Cos(Dir) * Dist, (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										z = Max(Min(Sin(Dir) * Dist, (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										RotateEntity(it\Collider, EntityPitch(it\Collider, True), EntityYaw(SecondPivot, True) + AngleDist(EntityYaw(it\Collider, True), EntityYaw(FirstPivot, True)), EntityRoll(it\Collider, True), True)
									Else
										x = Max(Min((EntityX(it\Collider) - EntityX(SecondPivot, True)), (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										z = Max(Min((EntityZ(it\Collider) - EntityZ(SecondPivot, True)), (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
									EndIf
									TeleportEntity(it\Collider, EntityX(FirstPivot, True) + x, (0.1 * fps\Factor[0]) + EntityY(FirstPivot, True) + (EntityY(it\Collider) - EntityY(SecondPivot, True)), EntityZ(FirstPivot, True) + z, 0.01, True)
								EndIf
							EndIf
						EndIf
					Next
					
					For de.Decals = Each Decals
						If Abs(EntityX(de\OBJ) - EntityX(SecondPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
							If Abs(EntityZ(de\OBJ) - EntityZ(SecondPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
								If Abs(EntityY(de\OBJ) - EntityY(SecondPivot, True)) < (280.0 * RoomScale) + (0.015 * fps\Factor[0]) Then
									If (Not IgnoreRotation) Then
										Dist = Distance(EntityX(de\OBJ, True), EntityX(SecondPivot, True), EntityZ(de\OBJ, True), EntityZ(SecondPivot, True))
										Dir = PointDirection(EntityX(de\OBJ, True), EntityZ(de\OBJ, True), EntityX(SecondPivot, True), EntityZ(SecondPivot, True))
										Dir = Dir + EntityYaw(FirstPivot, True) - EntityYaw(SecondPivot, True)
										x = Max(Min(Cos(Dir) * Dist, (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										z = Max(Min(Sin(Dir) * Dist, (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										RotateEntity(de\OBJ, EntityPitch(de\OBJ, True), EntityYaw(SecondPivot, True) + AngleDist(EntityYaw(de\OBJ, True), EntityYaw(FirstPivot, True)), EntityRoll(de\OBJ, True), True)
									Else
										x = Max(Min((EntityX(de\OBJ) - EntityX(SecondPivot, True)), (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
										z = Max(Min((EntityZ(de\OBJ) - EntityZ(SecondPivot, True)), (280.0 * RoomScale) - 0.22), ((-280.0) * RoomScale) + 0.22)
									EndIf
									TeleportEntity(de\OBJ, EntityX(FirstPivot, True) + x, (0.1 * fps\Factor[0]) + EntityY(FirstPivot, True) + (EntityY(de\OBJ) - EntityY(SecondPivot, True)), EntityZ(FirstPivot, True) + z, 0.01, True)
								EndIf
							EndIf
						EndIf
					Next
					UseDoor(door1, True, (Not PlayerInsideElevator))
					door2\Open = False
					
					; ~ Return to default panel texture
					ClearElevatorPanelTexture(door1)
					ClearElevatorPanelTexture(door2)
					PlaySound2(ElevatorBeepSFX, Camera, SecondPivot, 4.0)
				EndIf	
			EndIf
		EndIf
	EndIf
	Return(State)
End Function

Function UseDoor%(d.Doors, Scripted% = False, PlaySFX% = True)
	Local Temp%, i%
	
	If (Not Scripted) Then
		If SelectedItem <> Null Then
			Temp = GetUsingItem(SelectedItem)
		EndIf
		
		If d\KeyCard > KEY_MISC Then
			If SelectedItem = Null Then
				CreateMsg("A keycard is required to operate this door.")
				PlaySound2(ButtonSFX, Camera, d_I\ClosestButton)
				Return
			Else
				If Temp <= KEY_MISC Then
					CreateMsg("A keycard is required to operate this door.")
				Else
					If Temp = KEY_CARD_6 Then
						CreateMsg("The keycard was inserted into the slot. UNKNOWN ERROR! " + Chr(34) + "Do" + Chr(Rand(48, 122)) + "s th" + Chr(Rand(48, 122)) + " B" + Chr(Rand(48, 122)) + "ack " + Chr(Rand(48, 122)) + "oon howl? " + Chr(Rand(48, 122)) + "es. N" + Chr(Rand(48, 122)) + ". Ye" + Chr(Rand(48, 122)) + ". " + Chr(Rand(48, 122)) + "o." + Chr(34))
					Else
						If d\Locked = 1 Then
							If Temp = KEY_005 Then
								CreateMsg("You hold the key close to the slot, but nothing happened.")
							Else
								CreateMsg("The keycard was inserted into the slot, but nothing happened.")
							EndIf
						Else
							If Temp = KEY_005 Then
								CreateMsg("You hold the key close to the slot.")
							Else
								If Temp < d\KeyCard And d\KeyCard <> KEY_CARD_5 Then
									CreateMsg("A keycard with security clearance " + (d\KeyCard - 2) + " or higher is required to operate this door.")
								ElseIf Temp < d\KeyCard And d\KeyCard = KEY_CARD_5 Then
									CreateMsg("A keycard with security clearance " + (d\KeyCard - 2) + " is required to operate this door.")
								Else
									CreateMsg("The keycard was inserted into the slot.")
								EndIf
							EndIf
						EndIf
					EndIf
					SelectedItem = Null
				EndIf
				If (d\Locked <> 1) And (((Temp > KEY_MISC) And (Temp <> KEY_CARD_6) And (Temp >= d\KeyCard)) Lor (Temp = KEY_005)) Then
					PlaySound2(KeyCardSFX1, Camera, d_I\ClosestButton)
				Else
					If Temp <= KEY_MISC Then
						PlaySound2(ButtonSFX, Camera, d_I\ClosestButton)
					Else
						PlaySound2(KeyCardSFX2, Camera, d_I\ClosestButton)
					EndIf
					Return
				EndIf
			EndIf
		ElseIf d\KeyCard > KEY_HAND_YELLOW And d\KeyCard < KEY_MISC
			If SelectedItem = Null Then
				CreateMsg("You placed your palm onto the scanner. The scanner reads: " + Chr(34) + "DNA doesn't match known sample. Access denied." + Chr(34))
				PlaySound2(ScannerSFX2, Camera, d_I\ClosestButton)
				Return
			Else
				If ((Temp >= KEY_MISC) Lor (Temp < KEY_HAND_YELLOW)) And (Temp <> KEY_005) Then
					CreateMsg("You placed your palm onto the scanner. The scanner reads: " + Chr(34) + "DNA doesn't match known sample. Access denied." + Chr(34))
				Else
					If (d\KeyCard <> Temp) And (Temp <> KEY_005) Then
						CreateMsg("You placed the palm of the hand onto the scanner. The scanner reads: " + Chr(34) + "DNA doesn't match known sample. Access denied." + Chr(34))
					Else
						If d\Locked = 1 Then
							If Temp = KEY_005 Then
								CreateMsg("You hold the key onto the scanner, but nothing happened.")
							Else
								CreateMsg("You placed the palm of the hand onto the scanner, but nothing happened")
							EndIf
						Else
							If Temp = KEY_005 Then
								CreateMsg("You hold the key onto the scanner. The scanner reads: " + Chr(34) + "Unknown DNA verified. ERROR! Access granted." + Chr(34))
							Else
								CreateMsg("You placed the palm of the hand onto the scanner. The scanner reads: " + Chr(34) + "DNA verified. Access granted." + Chr(34))
							EndIf
						EndIf
					EndIf
					SelectedItem = Null
				EndIf
				If (d\Locked = 0) And ((Temp = d\KeyCard) Lor (Temp = KEY_005)) Then
					PlaySound2(ScannerSFX1, Camera, d_I\ClosestButton)
				Else
					PlaySound2(ScannerSFX2, Camera, d_I\ClosestButton)
					Return
				EndIf
			EndIf
		ElseIf d\Code <> ""
			If SelectedItem = Null Then
				If (d\Locked = 0) And (d\Code <> "GEAR") And (d\Code = msg\KeyPadInput) Then
					PlaySound2(ScannerSFX1, Camera, d_I\ClosestButton)
				Else
					PlaySound2(ScannerSFX2, Camera, d_I\ClosestButton)
					Return
				EndIf
			Else
				If Temp = KEY_005 Then
					If d\Locked = 1 Then
						CreateMsg("You hold the key close to the keypad, but nothing happened.")
					Else
						CreateMsg("You hold the key close to the keypad.")
					EndIf
				EndIf
				SelectedItem = Null
				
				If (d\Locked = 0) And (d\Code <> "GEAR") And (Temp = KEY_005) Then
					PlaySound2(ScannerSFX1, Camera, d_I\ClosestButton)
				Else
					PlaySound2(ScannerSFX2, Camera, d_I\ClosestButton)
					Return
				EndIf
			EndIf
			
			If d\Code = Str(AccessCode) Then
				GiveAchievement(AchvMaynard)
			ElseIf d\Code = "7816"
				GiveAchievement(AchvHarp)
			ElseIf d\Code = "2411"
				GiveAchievement(AchvO5)
			EndIf	
		Else
			If d\DoorType = WOODEN_DOOR Lor d\DoorType = OFFICE_DOOR Then
				If d\Locked > 0 Then
					If SelectedItem = Null Then
						CreateMsg("The door will not budge.")
						If d\DoorType = OFFICE_DOOR Then
							PlaySound2(DoorBudgeSFX1, Camera, d_I\ClosestButton)
						Else
							PlaySound2(DoorBudgeSFX2, Camera, d_I\ClosestButton)
						EndIf
					Else
						If (Temp > KEY_860) And (Temp <> KEY_005) Then
							CreateMsg("The door will not budge.")
						Else
							If d\Locked = 2 Lor ((Temp <> d\KeyCard) And (Temp <> KEY_005)) Then
								CreateMsg("You tried to unlock the door, but nothing happened.")
							Else
								CreateMsg("You unlocked the door.")
								d\Locked = 0
							EndIf
							SelectedItem = Null
						EndIf
						If (Temp > KEY_860) And (Temp <> KEY_005) Then
							If d\DoorType = OFFICE_DOOR Then
								PlaySound2(DoorBudgeSFX1, Camera, d_I\ClosestButton)
							Else
								PlaySound2(DoorBudgeSFX2, Camera, d_I\ClosestButton)
							EndIf
						Else
							PlaySound2(DoorLockSFX, Camera, d_I\ClosestButton)
						EndIf
					EndIf
					Return
				EndIf
			ElseIf d\DoorType = ELEVATOR_DOOR
				If d\Locked = 1 Then
					If (Not d\IsElevatorDoor > 0) Then
						CreateMsg("The elevator appears to be broken.")
						PlaySound2(ButtonSFX2, Camera, d_I\ClosestButton)
					Else
						If d\IsElevatorDoor = 1 Then
							CreateMsg("You called the elevator.")
						ElseIf d\IsElevatorDoor = 3
							CreateMsg("The elevator is already on this floor.")
						ElseIf msg\Txt <> "You called the elevator."
							Select Rand(10)
								Case 1
									;[Block]
									CreateMsg("Stop spamming the button.")
									;[End Block]
								Case 2
									;[Block]
									CreateMsg("Pressing it harder doesn't make the elevator come faster.")
									;[End Block]
								Case 3
									;[Block]
									CreateMsg("If you continue pressing this button I will generate a Memory Access Violation.")
									;[End Block]
								Default
									;[Block]
									CreateMsg("You already called the elevator.")
									;[End Block]
							End Select
						Else
							CreateMsg("You already called the elevator.")
						EndIf
						PlaySound2(ButtonSFX, Camera, d_I\ClosestButton)
					EndIf
					Return
				EndIf
			Else
				If d\Locked = 1 Then
					If d\Open Then
						CreateMsg("You pushed the button but nothing happened.")
					Else
						CreateMsg("The door appears to be locked.")
					EndIf
					PlaySound2(ButtonSFX2, Camera, d_I\ClosestButton)
					Return
				Else
					PlaySound2(ButtonSFX, Camera, d_I\ClosestButton)
				EndIf
			EndIf
		EndIf
	EndIf
	
	OpenCloseDoor(d, PlaySFX)
End Function

Function OpenCloseDoor%(d.Doors, PlaySFX% = True)
	d\Open = (Not d\Open)
	If d\LinkedDoor <> Null Then d\LinkedDoor\Open = (Not d\LinkedDoor\Open)
	
	If d\Open Then
		If d\LinkedDoor <> Null Then d\LinkedDoor\TimerState = d\LinkedDoor\Timer
		d\TimerState = d\Timer
	EndIf
	
	Local SoundRand% = Rand(0, 2)
	Local DoorType% = d\DoorType
	
	If d\DoorType = WOODEN_DOOR Then
		If PlayerRoom\RoomTemplate\Name = "cont2_860_1" Then
			SoundRand = 2
		Else
			SoundRand = Rand(0, 1)
		EndIf
	ElseIf d\DoorType = ONE_SIDED_DOOR
		DoorType = DEFAULT_DOOR
	EndIf
	
	Local SoundOpen% = OpenDoorSFX(d\DoorType, SoundRand)
	Local SoundClose% = CloseDoorSFX(d\DoorType, SoundRand)
	
	If d\Locked = 2 Then SoundOpen = BigDoorErrorSFX[Rand(0, 2)]
	
	If PlaySFX Then
		If d\Open Then
			d\SoundCHN = PlaySound2(SoundOpen, Camera, d\OBJ)
		Else
			d\SoundCHN = PlaySound2(SoundClose, Camera, d\OBJ)
		EndIf
		UpdateSoundOrigin(d\SoundCHN, Camera, d\FrameOBJ)
	EndIf
End Function

Function RemoveDoor%(d.Doors)
	Local i%
	
	If d\OBJ <> 0 Then FreeEntity(d\OBJ) : d\OBJ = 0
	If d\OBJ2 <> 0 Then FreeEntity(d\OBJ2) : d\OBJ2 = 0
	For i = 0 To 1
		If d\Buttons[i] <> 0 Then FreeEntity(d\Buttons[i]) : d\Buttons[i] = 0
		If d\ElevatorPanel[i] <> 0 Then FreeEntity(d\ElevatorPanel[i]) : d\ElevatorPanel[i] = 0
	Next
	If d\FrameOBJ <> 0 Then FreeEntity(d\FrameOBJ) : d\FrameOBJ = 0
	Delete(d)
End Function

Type Decals
	Field OBJ%, ID%
	Field Size#, SizeChange#, MaxSize#
	Field Alpha#, AlphaChange#
	Field BlendMode%, FX%
	Field R%, G%, B%
	Field Timer#, LifeTime#
	Field Dist#
End Type

Function CreateDecal.Decals(ID%, x#, y#, z#, Pitch#, Yaw#, Roll#, Size# = 1.0, Alpha# = 1.0, FX% = 0, BlendMode% = 1, R% = 0, G% = 0, B% = 0)
	Local de.Decals
	
	de.Decals = New Decals
	de\ID = ID
	de\Size = Size
	de\Alpha = Alpha
	de\FX = FX : de\BlendMode = BlendMode
	de\R = R : de\G = G : de\B = B
	de\MaxSize = 1.0
	
	de\OBJ = CreateSprite()
	PositionEntity(de\OBJ, x, y, z)
	ScaleSprite(de\OBJ, Size, Size)
	RotateEntity(de\OBJ, Pitch, Yaw, Roll)
	EntityTexture(de\OBJ, de_I\DecalTextureID[ID])
	EntityAlpha(de\OBJ, Alpha)
	EntityFX(de\OBJ, FX)
	EntityBlend(de\OBJ, BlendMode)
	SpriteViewMode(de\OBJ, 2)
	If R <> 0 Lor G <> 0 Lor B <> 0 Then EntityColor(de\OBJ, R, G, B)
	
	If (Not de_I\DecalTextureID[ID]) Then RuntimeError("Decal Texture ID: " + ID + " not found.")
	
	Return(de)
End Function

Function UpdateDecals%()
	Local de.Decals
	
	If UpdateTimer <= 0.0 Then
		For de.Decals = Each Decals
			Local xDist# = Abs(EntityX(me\Collider) - EntityX(de\OBJ, True))
			Local zDist# = Abs(EntityZ(me\Collider) - EntityZ(de\OBJ, True))
			
			de\Dist = xDist + zDist
		Next
	EndIf
	
	For de.Decals = Each Decals
		If de\Dist <= HideDistance Then
			If EntityHidden(de\OBJ) Then ShowEntity(de\OBJ)
			If de\SizeChange <> 0.0 Then
				de\Size = de\Size + (de\SizeChange * fps\Factor[0])
				ScaleSprite(de\OBJ, de\Size, de\Size)
				
				Select de\ID
					Case 0
						;[Block]
						If de\Timer <= 0.0 Then
							Local Angle# = Rnd(360.0)
							Local Temp# = Rnd(de\Size)
							Local de2.Decals
							
							de2.Decals = CreateDecal(DECAL_CORROSIVE_2, EntityX(de\OBJ) + Cos(Angle) * Temp, EntityY(de\OBJ) - 0.0005, EntityZ(de\OBJ) + Sin(Angle) * Temp, EntityPitch(de\OBJ), EntityYaw(de\OBJ), EntityRoll(de\OBJ), Rnd(0.1, 0.5))
							EntityParent(de2\OBJ, GetParent(de\OBJ))
							PlaySound2(DecaySFX[Rand(1, 3)], Camera, de2\OBJ, 10.0, Rnd(0.1, 0.5))
							de\Timer = Rnd(50.0, 100.0)
						Else
							de\Timer = de\Timer - fps\Factor[0]
						EndIf
						;[End Block]
				End Select
				
				If de\Size >= de\MaxSize Then
					de\SizeChange = 0.0
					de\Size = de\MaxSize
				EndIf
			EndIf
			
			If de\AlphaChange <> 0.0 Then
				de\Alpha = Min(de\Alpha + (fps\Factor[0] * de\AlphaChange), 1.0)
				EntityAlpha(de\OBJ, de\Alpha)
			EndIf
			
			If de\LifeTime > 0.0 Then
				de\LifeTime = Max(de\LifeTime - fps\Factor[0], 5.0)
			EndIf
			
			Local Dist# = DistanceSquared(EntityX(me\Collider), EntityX(de\OBJ, True), EntityZ(me\Collider), EntityZ(de\OBJ, True))
			Local ActualSize# = PowTwo(de\Size * 0.8)
			If (Dist < ActualSize) And (Int(EntityPitch(de\OBJ, True)) = 90.0) And (Abs((EntityY(me\Collider) - 0.3) - EntityY(de\OBJ, True)) < 0.05) Then
				Select de\ID
					Case 0
						;[Block]
						If de\FX <> 1 Then
							CurrStepSFX = 1
							me\CurrSpeed = CurveValue(0.0, me\CurrSpeed, Max(100.0 - (Sqr(ActualSize - Dist)) * 20.0, 1.0))
							me\CrouchState = Max(me\CrouchState, (ActualSize - Dist) / 2.0)
						EndIf
						;[End Block]
					Case 2, 3, 4, 5, 6, 7, 16, 17, 18, 20
						;[Block]
						CurrStepSFX = 3
						;[End Block]
				End Select
			EndIf
			
			If de\Size <= 0.0 Lor de\Alpha <= 0.0 Lor de\LifeTime = 5.0 Then
				FreeEntity(de\OBJ) : de\OBJ = 0
				Delete(de)
			EndIf
		Else
			If (Not EntityHidden(de\OBJ)) Then HideEntity(de\OBJ)
		EndIf
	Next
End Function

Type SecurityCams
	Field BaseOBJ%, CameraOBJ%, MonitorOBJ%, Pvt%
	Field ScrOBJ%, ScrWidth#, ScrHeight#
	Field Screen%, Cam%, ScrTexture%, ScrOverlay%
	Field Angle#, Turn#, CurrAngle#
	Field State#, PlayerState%
	Field SoundCHN%
	Field InSight%
	Field RenderInterval#
	Field room.Rooms
	Field FollowPlayer%
	Field CoffinEffect%
	Field AllowSaving%
	Field MinAngle#, MaxAngle#, Dir%
	Field Dist#
End Type

Function CreateSecurityCam.SecurityCams(x1#, y1#, z1#, r.Rooms, Screen% = False, x2# = 0.0, y2# = 0.0, z2# = 0.0)
	Local sc.SecurityCams
	
	sc.SecurityCams = New SecurityCams
	sc\BaseOBJ = CopyEntity(sc_I\CamModelID[CAM_BASE_MODEL])
	ScaleEntity(sc\BaseOBJ, 0.0015, 0.0015, 0.0015)
	PositionEntity(sc\BaseOBJ, x1, y1, z1)
	sc\CameraOBJ = CopyEntity(sc_I\CamModelID[CAM_HEAD_MODEL])
	ScaleEntity(sc\CameraOBJ, 0.01, 0.01, 0.01)
	
	sc\room = r
	
	sc\Screen = Screen
	If Screen Then
		sc\AllowSaving = True
		
		sc\RenderInterval = 12.0
		
		Local Scale# = RoomScale * 4.5 * 0.4
		
		sc\ScrOBJ = CreateSprite()
		EntityFX(sc\ScrOBJ, 17)
		SpriteViewMode(sc\ScrOBJ, 2)
		sc\ScrTexture = 0
		EntityTexture(sc\ScrOBJ, sc_I\ScreenTexs[sc\ScrTexture])
		ScaleSprite(sc\ScrOBJ, MeshWidth(mon_I\MonitorModelID[MONITOR_DEFAULT_MODEL]) * Scale * 0.95 * 0.5, MeshHeight(mon_I\MonitorModelID[MONITOR_DEFAULT_MODEL]) * Scale * 0.95 * 0.5)
		PositionEntity(sc\ScrOBJ, x2, y2, z2)
		
		sc\ScrOverlay = CreateSprite(sc\ScrOBJ)
		ScaleSprite(sc\ScrOverlay, MeshWidth(mon_I\MonitorModelID[MONITOR_DEFAULT_MODEL]) * Scale * 0.95 * 0.5, MeshHeight(mon_I\MonitorModelID[MONITOR_DEFAULT_MODEL]) * Scale * 0.95 * 0.5)
		MoveEntity(sc\ScrOverlay, 0.0, 0.0, -0.005)
		EntityTexture(sc\ScrOverlay, mon_I\MonitorOverlayID[MONITOR_DEFAULT_OVERLAY])
		SpriteViewMode(sc\ScrOverlay, 2)
		EntityFX(sc\ScrOverlay, 1)
		EntityBlend(sc\ScrOverlay, 3)
		
		sc\MonitorOBJ = CopyEntity(mon_I\MonitorModelID[MONITOR_DEFAULT_MODEL], sc\ScrOBJ)
		ScaleEntity(sc\MonitorOBJ, Scale, Scale, Scale)
		
		sc\Cam = CreateCamera()
		CameraViewport(sc\Cam, 0, 0, 512, 512)
		CameraRange(sc\Cam, 0.01, 8.0)
		CameraZoom(sc\Cam, 0.8)
		HideEntity(sc\Cam)	
	EndIf
	
	If r <> Null Then 
		EntityParent(sc\BaseOBJ, r\OBJ)
		If Screen Then
			If sc\ScrOBJ <> 0 Then EntityParent(sc\ScrOBJ, r\OBJ)
		EndIf
	EndIf
	Return(sc)
End Function

Function UpdateSecurityCams%()
	CatchErrors("Uncaught (UpdateSecurityCams)")
	
	Local sc.SecurityCams
	
	; ~ CoffinEffect = 0, not affected by SCP-895
	; ~ CoffinEffect = 1, constantly affected by SCP-895
	; ~ CoffinEffect = 2, SCP-079 can broadcast SCP-895 feed on this screen
	; ~ CoffinEffect = 3, SCP-079 broadcasting SCP-895 feed
	
	If UpdateTimer <= 0.0 Then
		For sc.SecurityCams = Each SecurityCams
			Local xDist# = Abs(EntityX(me\Collider) - EntityX(sc\BaseOBJ, True))
			Local zDist# = Abs(EntityZ(me\Collider) - EntityZ(sc\BaseOBJ, True))
			
			sc\Dist = xDist + zDist
		Next
	EndIf
	
	For sc.SecurityCams = Each SecurityCams
		If sc\Dist <= HideDistance Then
			Local Close% = False
			
			If sc\room = Null Then
				If (Not EntityHidden(sc\Cam)) Then HideEntity(sc\Cam)
			Else
				If sc\room\Dist < 6.0 Lor PlayerRoom = sc\room Then 
					Close = True
				ElseIf sc\Cam <> 0
					If (Not EntityHidden(sc\Cam)) Then HideEntity(sc\Cam)
				EndIf
				
				If sc\room <> Null Then
					If sc\room\RoomTemplate\Name = "room2_sl" Then sc\CoffinEffect = 0
				EndIf
				
				If Close Lor sc = sc_I\CoffinCam Then 
					If sc\FollowPlayer Then
						If sc <> sc_I\CoffinCam Then
							If EntityVisible(sc\CameraOBJ, Camera)
								If MTFCameraCheckTimer > 0.0 Then MTFCameraCheckDetected = True
							EndIf
						EndIf
						If (Not sc\Pvt) Then
							sc\Pvt = CreatePivot(sc\BaseOBJ)
							EntityParent(sc\Pvt, 0) ; ~ Sets position and rotation of the pivot to the cam object
						EndIf
						PointEntity(sc\Pvt, Camera)
						
						RotateEntity(sc\CameraOBJ, CurveAngle(EntityPitch(sc\Pvt), EntityPitch(sc\CameraOBJ), 75.0), CurveAngle(EntityYaw(sc\Pvt), EntityYaw(sc\CameraOBJ), 75.0), 0.0)
						
						PositionEntity(sc\CameraOBJ, EntityX(sc\BaseOBJ, True), EntityY(sc\BaseOBJ, True) - 0.083, EntityZ(sc\BaseOBJ, True))
					Else
						If sc\Turn > 0.0 Then
							If (Not sc\Dir) Then
								sc\CurrAngle = sc\CurrAngle + (0.2 * fps\Factor[0])
								If sc\CurrAngle > sc\Turn * 1.3 Then sc\Dir = True
							Else
								sc\CurrAngle = sc\CurrAngle - (0.2 * fps\Factor[0])
								If sc\CurrAngle < (-sc\Turn) * 1.3 Then sc\Dir = False
							EndIf
						EndIf
						PositionEntity(sc\CameraOBJ, EntityX(sc\BaseOBJ, True), EntityY(sc\BaseOBJ, True) - 0.083, EntityZ(sc\BaseOBJ, True))
						RotateEntity(sc\CameraOBJ, EntityPitch(sc\CameraOBJ), sc\room\Angle + sc\Angle + Max(Min(sc\CurrAngle, sc\Turn), -sc\Turn), 0.0)
						
						If (MilliSecs2() Mod 1350) < 800 Then
							EntityTexture(sc\CameraOBJ, sc_I\CamTextureID[CAM_HEAD_DEFAULT_TEXTURE])
						Else
							EntityTexture(sc\CameraOBJ, sc_I\CamTextureID[CAM_HEAD_RED_LIGHT_TEXTURE])
						EndIf
						
						If sc\Cam <> 0 Then 
							PositionEntity(sc\Cam, EntityX(sc\CameraOBJ, True), EntityY(sc\CameraOBJ, True), EntityZ(sc\CameraOBJ, True))
							RotateEntity(sc\Cam, EntityPitch(sc\CameraOBJ), EntityYaw(sc\CameraOBJ), 0.0)
							MoveEntity(sc\Cam, 0.0, 0.0, 0.1)
						EndIf
						
						If sc <> sc_I\CoffinCam Then
							If Abs(DeltaYaw(sc\CameraOBJ, Camera)) < 60.0
								If EntityVisible(sc\CameraOBJ, Camera)
									If MTFCameraCheckTimer > 0.0 Then MTFCameraCheckDetected = True
								EndIf
							EndIf
						EndIf
					EndIf
				EndIf
				
				If Close Then
					If sc\Screen Then
						If sc\State < sc\RenderInterval Then
							sc\State = sc\State + fps\Factor[0]
						Else
							sc\State = 0.0
						EndIf
						
						If me\BlinkTimer > -5.0 And EntityInView(sc\ScrOBJ, Camera) Then
							If EntityVisible(Camera, sc\ScrOBJ) Then
								If (sc\CoffinEffect = 1 Lor sc\CoffinEffect = 3) And (Not I_714\Using) And wi\HazmatSuit <> 3 And wi\GasMask <> 3 Then
									If me\BlinkTimer > -5.0 Then
										me\Sanity = me\Sanity - fps\Factor[0]
										me\RestoreSanity = False
									EndIf
								EndIf
							EndIf
						EndIf
						
						If me\Sanity < -900.0 Then 
							msg\DeathMsg = Chr(34) + "What we know is that he died of cardiac arrest. My guess is that it was caused by SCP-895, although it has never been observed affecting video equipment from this far before. "
							msg\DeathMsg = msg\DeathMsg + "Further testing is needed to determine whether SCP-895's " + Chr(34) + "Red Zone" + Chr(34) + " is increasing." + Chr(34)
							
							If me\VomitTimer < -10.0 Then Kill()
						EndIf
						
						If me\VomitTimer < 0.0 And me\Sanity < -800.0 Then
							me\RestoreSanity = False
							me\Sanity = -910.0
						EndIf
						
						If me\BlinkTimer > -5.0 And EntityInView(sc\ScrOBJ, Camera) And EntityVisible(Camera, sc\ScrOBJ) Then
							sc\InSight = True
						Else
							sc\InSight = False
						EndIf
						
						If (sc\CoffinEffect = 1 Lor sc\CoffinEffect = 3) And (Not I_714\Using) And wi\HazmatSuit <> 3 And wi\GasMask <> 3 Then
							If sc\InSight Then
								Local Pvt% = CreatePivot()
								
								PositionEntity(Pvt, EntityX(Camera), EntityY(Camera), EntityZ(Camera))
								PointEntity(Pvt, sc\ScrOBJ)
								
								RotateEntity(me\Collider, EntityPitch(me\Collider), CurveAngle(EntityYaw(Pvt), EntityYaw(me\Collider), Min(Max(15000.0 / (-me\Sanity), 20.0), 200.0)), 0.0)
								
								TurnEntity(Pvt, 90.0, 0.0, 0.0)
								CameraPitch = CurveAngle(EntityPitch(Pvt), CameraPitch + 90.0, Min(Max(15000.0 / (-me\Sanity), 20.0), 200.0))
								CameraPitch = CameraPitch - 90.0
								
								FreeEntity(Pvt)
								If (sc\CoffinEffect = 1 Lor sc\CoffinEffect = 3) And ((Not I_714\Using) And wi\GasMask <> 3 And wi\HazmatSuit <> 3) Then
									If me\Sanity < -720.0 Then
										If Rand(3) = 1 Then EntityTexture(sc\ScrOverlay, mon_I\MonitorOverlayID[MONITOR_DEFAULT_OVERLAY])
										If Rand(6) < 5 Then
											EntityTexture(sc\ScrOverlay, mon_I\MonitorOverlayID[Rand(MONITOR_895_OVERLAY_1, MONITOR_895_OVERLAY_6)])
											If sc\PlayerState = 1 Then PlaySound_Strict(HorrorSFX[1])
											sc\PlayerState = 2
											If (Not sc\SoundCHN) Then
												sc\SoundCHN = PlaySound_Strict(HorrorSFX[4])
											Else
												If (Not ChannelPlaying(sc\SoundCHN)) Then sc\SoundCHN = PlaySound_Strict(HorrorSFX[4])
											EndIf
											If sc\CoffinEffect = 3 And Rand(200) = 1 Then sc\CoffinEffect = 2 : sc\PlayerState = Rand(10000, 20000)
										EndIf	
										me\BlurTimer = 1000.0
										If me\VomitTimer = 0.0 Then me\VomitTimer = 1.0
									ElseIf me\Sanity < -450.0
										If Rand(7) = 1 Then EntityTexture(sc\ScrOverlay, mon_I\MonitorOverlayID[MONITOR_DEFAULT_OVERLAY])
										If Rand(50) = 1 Then
											EntityTexture(sc\ScrOverlay, mon_I\MonitorOverlayID[Rand(MONITOR_895_OVERLAY_1, MONITOR_895_OVERLAY_6)])
											If sc\PlayerState = 0 Then PlaySound_Strict(HorrorSFX[0])
											sc\PlayerState = Max(sc\PlayerState, 1)
											If sc\CoffinEffect = 3 And Rand(100) = 1 Then sc\CoffinEffect = 2 : sc\PlayerState = Rand(10000, 20000)
										EndIf
									Else
										EntityTexture(sc\ScrOverlay, mon_I\MonitorOverlayID[MONITOR_DEFAULT_OVERLAY])
									EndIf
								EndIf
							EndIf
						Else
							If sc\InSight Then
								If I_714\Using Lor wi\HazmatSuit = 3 Lor wi\GasMask = 3 Then EntityTexture(sc\ScrOverlay, mon_I\MonitorOverlayID[MONITOR_DEFAULT_OVERLAY])
							EndIf
						EndIf
						
						If sc\InSight And sc\CoffinEffect = 0 Lor sc\CoffinEffect = 2 Then
							If sc\PlayerState = 0 Then
								sc\PlayerState = Rand(60000, 65000)
							EndIf
							
							If Rand(500) = 1 Then EntityTexture(sc\ScrOverlay, mon_I\MonitorOverlayID[Rand(MONITOR_079_OVERLAY_2, MONITOR_079_OVERLAY_7)])
							
							If (MilliSecs2() Mod sc\PlayerState) >= Rand(600) Then
								EntityTexture(sc\ScrOverlay, mon_I\MonitorOverlayID[MONITOR_DEFAULT_OVERLAY])
							Else
								If (Not sc\SoundCHN) Then
									sc\SoundCHN = PlaySound_Strict(LoadTempSound("SFX\SCP\079\Broadcast" + Rand(1, 3) + ".ogg"))
									If sc\CoffinEffect = 2 Then sc\CoffinEffect = 3 : sc\PlayerState = 0
								ElseIf (Not ChannelPlaying(sc\SoundCHN))
									sc\SoundCHN = PlaySound_Strict(LoadTempSound("SFX\SCP\079\Broadcast" + Rand(1, 3) + ".ogg"))
									If sc\CoffinEffect = 2 Then sc\CoffinEffect = 3 : sc\PlayerState = 0
								EndIf
								EntityTexture(sc\ScrOverlay, mon_I\MonitorOverlayID[Rand(MONITOR_079_OVERLAY_2, MONITOR_079_OVERLAY_7)])
							EndIf
						EndIf
					EndIf
					If (Not sc\InSight) Then sc\SoundCHN = LoopSound2(CameraSFX, sc\SoundCHN, Camera, sc\CameraOBJ, 4.0)
				EndIf
				
				If sc <> Null Then
					If sc\room <> Null Then
						CatchErrors("UpdateSecurityCameras (" + sc\room\RoomTemplate\Name + ")")
					Else
						CatchErrors("UpdateSecurityCameras (screen has no room)")
					EndIf
				Else
					CatchErrors("UpdateSecurityCameras (screen doesn't exist anymore)")
				EndIf
			EndIf
		EndIf
	Next
End Function

Function RenderSecurityCams%()
	CatchErrors("Uncaught (RenderSecurityCams)")
	
	Local sc.SecurityCams
	
	For sc.SecurityCams = Each SecurityCams
		Local Close% = False
		
		If sc\room <> Null Then
			If sc\room\Dist < 6.0 Lor PlayerRoom = sc\room Then 
				Close = True
			EndIf
			
			If Close Then
				If sc\Screen Then
					If sc\State >= sc\RenderInterval Then
						If me\BlinkTimer > -5.0 And EntityInView(sc\ScrOBJ, Camera) Then
							If EntityVisible(Camera, sc\ScrOBJ) Then
								If sc_I\CoffinCam = Null Lor Rand(5) = 5 Lor sc\CoffinEffect <> 3 Then
									If (Not EntityHidden(Camera)) Then
										HideEntity(Camera)
										ShowEntity(sc\Cam)
									EndIf
									Cls()
									
									SetBuffer(BackBuffer())
									RenderWorld()
									CopyRect(0, 0, 512, 512, 0, 0, BackBuffer(), TextureBuffer(sc_I\ScreenTexs[sc\ScrTexture]))
									
									If (Not EntityHidden(sc\Cam)) Then
										HideEntity(sc\Cam)
										ShowEntity(Camera)
									EndIf
								Else
									If (Not EntityHidden(Camera)) Then
										HideEntity(Camera)
										ShowEntity(sc_I\CoffinCam\room\OBJ)
										EntityAlpha(GetChild(sc_I\CoffinCam\room\OBJ, 2), 1.0)
										ShowEntity(sc_I\CoffinCam\Cam)
									EndIf
									Cls()
									
									SetBuffer(BackBuffer())
									RenderWorld()
									CopyRect(0, 0, 512, 512, 0, 0, BackBuffer(), TextureBuffer(sc_I\ScreenTexs[sc\ScrTexture]))
									
									If (Not EntityHidden(sc_I\CoffinCam\room\OBJ)) Then
										HideEntity(sc_I\CoffinCam\room\OBJ)
										HideEntity(sc_I\CoffinCam\Cam)
										ShowEntity(Camera)
									EndIf
								EndIf
							EndIf
						EndIf
					EndIf
				EndIf
			EndIf
			
			If sc <> Null Then
				If sc\room <> Null Then
					CatchErrors("RenderSecurityCameras (" + sc\room\RoomTemplate\Name + ")")
				Else
					CatchErrors("RenderSecurityCameras (screen has no room)")
				EndIf
			Else
				CatchErrors("RenderSecurityCameras (screen doesn't exist anymore)")
			EndIf
		EndIf
	Next
	Cls()
End Function

Function UpdateMonitorSaving%()
	Local sc.SecurityCams
	Local Close% = False
	
	If SelectedDifficulty\SaveType <> SAVE_ON_SCREENS Lor InvOpen Lor I_294\Using Lor OtherOpen <> Null Lor d_I\SelectedDoor <> Null Lor SelectedScreen <> Null Then Return
	
	For sc.SecurityCams = Each SecurityCams
		If sc\AllowSaving And sc\Screen Then
			Close = False
			If sc\room\Dist < 6.0 Lor PlayerRoom = sc\room Then 
				Close = True
			EndIf
			
			If Close And (Not GrabbedEntity) And (Not d_I\ClosestButton) Then
				If EntityInView(sc\ScrOBJ, Camera) And EntityDistanceSquared(sc\ScrOBJ, Camera) < 1.0 Then
					If EntityVisible(sc\ScrOBJ, Camera) Then
						ga\DrawHandIcon = True
						If mo\MouseHit1 Then sc_I\SelectedMonitor = sc
					Else
						If sc_I\SelectedMonitor = sc Then sc_I\SelectedMonitor = Null
					EndIf
				Else
					If sc_I\SelectedMonitor = sc Then sc_I\SelectedMonitor = Null
				EndIf
				
				If sc_I\SelectedMonitor = sc Then
					If sc\InSight Then
						Local Pvt% = CreatePivot()
						
						PositionEntity(Pvt, EntityX(Camera), EntityY(Camera), EntityZ(Camera))
						PointEntity(Pvt, sc\ScrOBJ)
						RotateEntity(me\Collider, EntityPitch(me\Collider), CurveAngle(EntityYaw(Pvt), EntityYaw(me\Collider), Min(Max(15000.0 / (-me\Sanity), 20.0), 200.0)), 0.0)
						TurnEntity(Pvt, 90.0, 0.0, 0.0)
						CameraPitch = CurveAngle(EntityPitch(Pvt), CameraPitch + 90.0, Min(Max(15000.0 / (-me\Sanity), 20.0), 200.0))
						CameraPitch = CameraPitch - 90.0
						FreeEntity(Pvt)
					EndIf
				EndIf
			Else
				If sc_I\SelectedMonitor = sc Then sc_I\SelectedMonitor = Null
			EndIf
		EndIf
	Next
End Function

Function UpdateCheckpointMonitors%(LCZ% = True)
	Local i%, SF%, b%, t1%
	Local Entity%, Name$
	
	Entity = mon_I\MonitorModelID[1]
	
	If LCZ Then
		mon_I\UpdateCheckpoint1 = True
	Else
		mon_I\UpdateCheckpoint2 = True
	EndIf
	
	For i = 2 To CountSurfaces(Entity)
		SF = GetSurface(Entity, i)
		b = GetSurfaceBrush(SF)
		If b <> 0 Then
			t1 = GetBrushTexture(b, 0)
			If t1 <> 0 Then
				Name = StripPath(TextureName(t1))
				If Lower(Name) <> "monitor_overlay.png"
					If LCZ Then
						If mon_I\MonitorTimer < 50.0 Then
							BrushTexture(b, mon_I\MonitorOverlayID[MONITOR_LOCKDOWN_2_OVERLAY], 0, 0)
						Else
							BrushTexture(b, mon_I\MonitorOverlayID[MONITOR_LOCKDOWN_3_OVERLAY], 0, 0)
						EndIf
					Else
						If mon_I\MonitorTimer2 < 50.0 Then
							BrushTexture(b, mon_I\MonitorOverlayID[MONITOR_LOCKDOWN_2_OVERLAY], 0, 0)
						Else
							BrushTexture(b, mon_I\MonitorOverlayID[MONITOR_LOCKDOWN_1_OVERLAY], 0, 0)
						EndIf
					EndIf
					PaintSurface(SF, b)
				EndIf
				If Name <> "" Then DeleteSingleTextureEntryFromCache(t1)
			EndIf
			FreeBrush(b)
		EndIf
	Next
End Function

Function TurnCheckpointMonitorsOff%(LCZ% = True)
	Local i%, SF%, b%, t1%
	Local Entity%, Name$
	
	Entity = mon_I\MonitorModelID[1]
	
	If LCZ Then
		mon_I\UpdateCheckpoint1 = False
		mon_I\MonitorTimer = 0.0
	Else
		mon_I\UpdateCheckpoint2 = False
		mon_I\MonitorTimer2 = 0.0
	EndIf
	
	;For i = 2 To CountSurfaces(Entity)
	;	SF = GetSurface(Entity, i)
	;	b = GetSurfaceBrush(SF)
	;	If b <> 0 Then
	;		t1 = GetBrushTexture(b, 0)
	;		If t1 <> 0 Then
	;			Name = StripPath(TextureName(t1))
	;			If Lower(Name) <> "monitor_overlay.png"
	;				BrushTexture(b, mon_I\MonitorOverlayID[MONITOR_LOCKDOWN_4_OVERLAY], 0, 0)
	;				PaintSurface(SF, b)
	;			EndIf
	;			If Name <> "" Then DeleteSingleTextureEntryFromCache(t1)
	;		EndIf
	;		FreeBrush(b)
	;	EndIf
	;Next
End Function

Function TimeCheckpointMonitors%()
	If mon_I\UpdateCheckpoint1 Then
		If mon_I\MonitorTimer < 100.0
			mon_I\MonitorTimer = Min(mon_I\MonitorTimer + fps\Factor[0], 100.0)
		Else
			mon_I\MonitorTimer = 0.0
		EndIf
	EndIf
	If mon_I\UpdateCheckpoint2 Then
		If mon_I\MonitorTimer2 < 100.0
			mon_I\MonitorTimer2 = Min(mon_I\MonitorTimer2 + fps\Factor[0], 100.0)
		Else
			mon_I\MonitorTimer2 = 0.0
		EndIf
	EndIf
End Function

Global SelectedScreen.Screens

Type Screens
	Field OBJ%
	Field ImgPath$
	Field Img%
	Field room.Rooms
End Type

Type TempScreens
	Field ImgPath$
	Field x#, y#, z#
	Field RoomTemplate.RoomTemplates
End Type

Function CreateScreen.Screens(x#, y#, z#, ImgPath$, r.Rooms)
	Local s.Screens = New Screens
	
	s\OBJ = CreatePivot()
	EntityPickMode(s\OBJ, 1)
	EntityRadius(s\OBJ, 0.1)
	
	PositionEntity(s\OBJ, x, y, z)
	
	; ~ A hacky way to use .png format
	If Right(ImgPath, 3) = "jpg" Then ImgPath = Left(ImgPath, Len(ImgPath) - 3) + "png"
	
	s\ImgPath = ImgPath
	s\room = r
	EntityParent(s\OBJ, r\OBJ)
	
	Return(s)
End Function

Function UpdateScreens%()
	If InvOpen Lor I_294\Using Lor OtherOpen <> Null Lor d_I\SelectedDoor <> Null Lor SelectedScreen <> Null Then Return
	
	Local s.Screens
	
	For s.Screens = Each Screens
		If s\room = PlayerRoom Then
			If EntityDistanceSquared(me\Collider, s\OBJ) < 1.44 Then
				EntityPick(Camera, 1.2)
				If PickedEntity() = s\OBJ And s\ImgPath <> "" Then
					ga\DrawHandIcon = True
					If mo\MouseUp1 Then 
						SelectedScreen = s
						s\Img = LoadImage_Strict("GFX\map\screens\" + s\ImgPath)
						s\Img = ScaleImage2(s\Img, MenuScale, MenuScale)
						MaskImage(s\Img, 255, 0, 255)
						PlaySound_Strict(ButtonSFX)
						mo\MouseUp1 = False
					EndIf
				EndIf
			EndIf
		EndIf
	Next
End Function

Function UpdateLever%(OBJ%, Locked% = False)
	Local Dist# = EntityDistanceSquared(Camera, OBJ)
	
	If Dist < 64.0 Then 
		If Dist < 0.64 And (Not Locked) Then 
			If EntityInView(OBJ, Camera) Then 
				EntityPick(Camera, 0.65)
				
				If PickedEntity() = OBJ Then
					ga\DrawHandIcon = True
					If mo\MouseHit1 Then GrabbedEntity = OBJ
				EndIf
				
				Local PrevPitch# = EntityPitch(OBJ)
				
				If (mo\MouseDown1 Lor mo\MouseHit1) Then
					If GrabbedEntity <> 0 Then
						If GrabbedEntity = OBJ Then
							ga\DrawHandIcon = True 
							RotateEntity(GrabbedEntity, Max(Min(EntityPitch(OBJ) + Max(Min(mo\Mouse_Y_Speed_1 * 8.0, 30.0), -30.0), 80.0), -80.0), EntityYaw(OBJ), 0.0)
							
							ga\DrawArrowIcon[0] = True
							ga\DrawArrowIcon[2] = True
						EndIf
					EndIf
				EndIf 
				
				If EntityPitch(OBJ, True) > 75.0 Then
					If PrevPitch <= 75.0 Then PlaySound2(LeverSFX, Camera, OBJ, 1.0)
				ElseIf EntityPitch(OBJ, True) < -75.0
					If PrevPitch >= -75.0 Then PlaySound2(LeverSFX, Camera, OBJ, 1.0)	
				EndIf						
			EndIf
		EndIf
		
		If (Not mo\MouseDown1) And (Not mo\MouseHit1) Then 
			If EntityPitch(OBJ,True) > 0 Then
				RotateEntity(OBJ, CurveValue(80.0, EntityPitch(OBJ), 10.0), EntityYaw(OBJ), 0.0)
			Else
				RotateEntity(OBJ, CurveValue(-80.0, EntityPitch(OBJ), 10.0), EntityYaw(OBJ), 0.0)
			EndIf
			GrabbedEntity = 0
		EndIf
	EndIf
	
	If EntityPitch(OBJ, True) > 0.0 Then
		Return(True)
	Else
		Return(False)
	EndIf	
End Function

Function CreateRedLight%(x#, y#, z#, Parent% = 0)
	Local Sprite%
	
	Sprite = CreateSprite()
	PositionEntity(Sprite, x, y, z)
	ScaleSprite(Sprite, 0.015, 0.015)
	EntityTexture(Sprite, misc_I\LightSpriteID[LIGHT_SPRITE_RED])
	EntityBlend(Sprite, 3)
	EntityParent(Sprite, Parent)
	HideEntity(Sprite)
	
	Return(Sprite)
End Function

Function UpdateRedLight%(Light%, Value1#, Value2#)
	If (MilliSecs2() Mod Value1) < Value2 Then
		If EntityHidden(Light) Then ShowEntity(Light)
	Else
		If (Not EntityHidden(Light)) Then HideEntity(Light)
	EndIf
End Function

Include "Source Code\Rooms_Core.bb"

Global UpdateTimer#

Function UpdateDistanceTimer%()
	If UpdateTimer <= 0.0 Then
		UpdateTimer = 30.0
	Else
		UpdateTimer = UpdateTimer - fps\Factor[0]
	EndIf
End Function

Function TeleportToRoom%(r.Rooms)
	Local it.Items
	
	PlayerRoom = r
	UpdateTimer = 0.0
	For it.Items = Each Items
		it\DistTimer = 0.0
	Next
	UpdateDoors()
	UpdateRooms()
End Function

Function UpdateRooms%()
	CatchErrors("Uncaught (UpdateRooms)")
	
	Local Dist#, i%, j%, r.Rooms
	Local x#, y#, z#, Hide%
	
	; ~ The reason why it is like this:
	; ~ When the map gets spawned by a seed, it starts from LCZ to HCZ to EZ (bottom to top)
	; ~ A map loaded by the map creator starts from EZ to HCZ to LCZ (top to bottom) and that's why this little code thing with the (SelectedMap = "") needs to be there - ENDSHN
	If (EntityZ(me\Collider) / 8.0) < I_Zone\Transition[1] - (SelectedMap = "") Then
		me\Zone = 2
	ElseIf (EntityZ(me\Collider) / 8.0) >= I_Zone\Transition[1] - (SelectedMap = "") And (EntityZ(me\Collider) / 8.0) < I_Zone\Transition[0] - (SelectedMap = "") Then
		me\Zone = 1
	Else
		me\Zone = 0
	EndIf
	
	TempLightVolume = 0.0
	
	Local FoundNewPlayerRoom% = False
	
	If PlayerRoom <> Null Then
		If Abs(EntityY(me\Collider) - EntityY(PlayerRoom\OBJ)) < 1.5 Then
			x = Abs(PlayerRoom\x - EntityX(me\Collider, True))
			If x < 4.0 Then
				z = Abs(PlayerRoom\z - EntityZ(me\Collider, True))
				If z < 4.0 Then
					FoundNewPlayerRoom = True
				EndIf
			EndIf
			
			If (Not FoundNewPlayerRoom) Then ; ~ It's likely that an adjacent room is the new player room, check for that
				For i = 0 To MaxRoomAdjacents - 1
					If PlayerRoom\Adjacent[i] <> Null Then
						x = Abs(PlayerRoom\Adjacent[i]\x - EntityX(me\Collider, True))
						If x < 4.0 Then
							z = Abs(PlayerRoom\Adjacent[i]\z - EntityZ(me\Collider, True))
							If z < 4.0 Then
								FoundNewPlayerRoom = True
								PlayerRoom = PlayerRoom\Adjacent[i]
								Exit
							EndIf
						EndIf
					EndIf
				Next
			EndIf
		Else
			FoundNewPlayerRoom = True ; ~ PlayerRoom stays the same when you're high up, or deep down
		EndIf
	EndIf
	
	For r.Rooms = Each Rooms
		x = Abs(r\x - EntityX(me\Collider, True))
		z = Abs(r\z - EntityZ(me\Collider, True))
		r\Dist = Max(x, z)
		
		If x < 16 And z < 16 Then
			For i = 0 To MaxRoomEmitters - 1
				If r\SoundEmitter[i] <> 0 Then 
					If EntityDistanceSquared(r\SoundEmitterOBJ[i], me\Collider) < PowTwo(r\SoundEmitterRange[i]) Then
						r\SoundEmitterCHN[i] = LoopSound2(RoomAmbience[r\SoundEmitter[i] - 1], r\SoundEmitterCHN[i], Camera, r\SoundEmitterOBJ[i], r\SoundEmitterRange[i])
					EndIf
				EndIf
			Next
			
			If (Not FoundNewPlayerRoom) And PlayerRoom <> r Then				
				If x < 4.0 Then
					If z < 4.0 Then
						If Abs(EntityY(me\Collider) - EntityY(r\OBJ)) < 1.5 Then PlayerRoom = r
						FoundNewPlayerRoom = True
					EndIf
				EndIf				
			EndIf
		EndIf
		
		Hide = True
		If r = PlayerRoom Then Hide = False
		If Hide Then
			If IsRoomAdjacent(PlayerRoom, r) Then Hide = False
		EndIf
		If Hide Then
			For i = 0 To MaxRoomAdjacents - 1
				If IsRoomAdjacent(PlayerRoom\Adjacent[i], r) Then
					Hide = False
					Exit
				EndIf
			Next
		EndIf
		
		If Hide Then
			HideEntity(r\OBJ)
			HideProps(r)
		Else
			ShowEntity(r\OBJ)
			ShowProps(r)
			
			For i = 0 To MaxRoomLights - 1
				If r\Lights[i] <> 0 Then
					Dist = EntityDistanceSquared(Camera, r\Lights[i])
					If Dist < PowTwo(HideDistance) Then
						TempLightVolume = TempLightVolume + r\LightIntensity[i] * r\LightIntensity[i] * ((HideDistance - Sqr(Dist)) / HideDistance)						
					EndIf
				Else
					Exit
				EndIf
			Next
			If r\TriggerBoxAmount > 0 Then
				For i = 0 To r\TriggerBoxAmount - 1
					If chs\DebugHUD <> 0 Then
						EntityColor(r\TriggerBoxes[i]\OBJ, 255, 255, 0)
						EntityAlpha(r\TriggerBoxes[i]\OBJ, 0.2)
					Else
						EntityColor(r\TriggerBoxes[i]\OBJ, 255, 255, 255)
						EntityAlpha(r\TriggerBoxes[i]\OBJ, 0.0)
					EndIf
				Next
			EndIf
		EndIf
	Next
	
	TempLightVolume = Max(TempLightVolume / 4.5, 1.0)
	
	If PlayerRoom <> Null Then
		CurrMapGrid\Found[Floor(EntityX(PlayerRoom\OBJ) / 8.0) + (Floor(EntityZ(PlayerRoom\OBJ) / 8.0) * MapGridSize)] = MapGrid_Tile
		PlayerRoom\Found = True
		
		EntityAlpha(GetChild(PlayerRoom\OBJ, 2), 1.0)
		ShowAlphaProps(PlayerRoom)
		For i = 0 To MaxRoomAdjacents - 1
			If PlayerRoom\Adjacent[i] <> Null Then
				If PlayerRoom\AdjDoor[i] <> Null Then
					If PlayerRoom\AdjDoor[i]\OpenState = 0.0 Then
						EntityAlpha(GetChild(PlayerRoom\Adjacent[i]\OBJ, 2), 0.0)
						HideAlphaProps(PlayerRoom\Adjacent[i])
					ElseIf (Not EntityInView(PlayerRoom\AdjDoor[i]\FrameOBJ, Camera))
						EntityAlpha(GetChild(PlayerRoom\Adjacent[i]\OBJ, 2), 0.0)
						HideAlphaProps(PlayerRoom\Adjacent[i])
					Else
						EntityAlpha(GetChild(PlayerRoom\Adjacent[i]\OBJ, 2), 1.0)
						ShowAlphaProps(PlayerRoom\Adjacent[i])
					EndIf
				EndIf
				
				For j = 0 To MaxRoomAdjacents - 1
					If PlayerRoom\Adjacent[i]\Adjacent[j] <> Null Then
						If PlayerRoom\Adjacent[i]\Adjacent[j] <> PlayerRoom Then
							EntityAlpha(GetChild(PlayerRoom\Adjacent[i]\Adjacent[j]\OBJ, 2), 0.0)
							HideAlphaProps(PlayerRoom\Adjacent[i]\Adjacent[j])
						EndIf
					EndIf
				Next
			EndIf
		Next
	EndIf
	
	CatchErrors("UpdateRooms")
End Function

Function IsRoomAdjacent%(this.Rooms, that.Rooms)
	Local i%
	
	If this = Null Then Return(False)
	If this = that Then Return(True)
	For i = 0 To MaxRoomAdjacents - 1
		If that = this\Adjacent[i] Then Return(True)
	Next
	Return(False)
End Function

Dim MapRoom$(ROOM4, 0)

Function SetRoom%(RoomName$, RoomType%, RoomPosition%, MinPos%, MaxPos%) ; ~ Place a room without overwriting others
	Local Looped%, CanPlace%
	
	Looped = False
	CanPlace = True
	While MapRoom(RoomType, RoomPosition) <> ""
		RoomPosition = RoomPosition + 1
		If RoomPosition > MaxPos Then
			If (Not Looped) Then
				RoomPosition = MinPos + 1 : Looped = True
			Else
				CanPlace = False
				Exit
			EndIf
		EndIf
	Wend
	If CanPlace Then
		MapRoom(RoomType, RoomPosition) = RoomName
		Return(True)
	Else
		Return(False)
	EndIf
End Function

Function PreventRoomOverlap%(r.Rooms)
	If r\RoomTemplate\DisableOverlapCheck Then Return
	
	Local r2.Rooms, r3.Rooms
	Local IsIntersecting% = False
	
	; ~ Just skip it when it would try to check for the checkpoints
	If r\RoomTemplate\Name = "room2_checkpoint_lcz_hcz" Lor r\RoomTemplate\Name = "room2_checkpoint_hcz_ez" Lor r\RoomTemplate\Name = "cont1_173" Then Return(True)
	
	; ~ First, check if the room is actually intersecting at all
	For r2.Rooms = Each Rooms
		If r2 <> r And (Not r2\RoomTemplate\DisableOverlapCheck) Then
			If CheckRoomOverlap(r, r2) Then
				IsIntersecting = True
				Exit
			EndIf
		EndIf
	Next
	
	; ~ If not, then simply return it as True
	If (Not IsIntersecting) Then Return(True)
	
	; ~ Room is interseting: First, check if the given room is a ROOM2, so we could potentially just turn it by 180.0 degrees
	IsIntersecting = False
	
	Local x% = r\x / 8.0
	Local y% = r\z / 8.0
	
	If r\RoomTemplate\Shape = ROOM2 Then
		; ~ Room is a ROOM2, let's check if turning it 180.0 degrees fixes the overlapping issue
		r\Angle = r\Angle + 180.0
		RotateEntity(r\OBJ, 0.0, r\Angle, 0.0)
		CalculateRoomExtents(r)
		
		For r2.Rooms = Each Rooms
			If r2 <> r And (Not r2\RoomTemplate\DisableOverlapCheck) Then
				If CheckRoomOverlap(r, r2) Then
					; ~ If didn't work then rotate the room back and move to the next step
					IsIntersecting = True
					r\Angle = r\Angle - 180.0
					RotateEntity(r\OBJ, 0.0, r\Angle, 0.0)
					CalculateRoomExtents(r)
					Exit
				EndIf
			EndIf
		Next
	Else
		IsIntersecting = True
	EndIf
	
	; ~ Room is ROOM2 and was able to be turned by 180.0 degrees
	If (Not IsIntersecting) Then Return(True)
	
	; ~ Room is either not a ROOM2 or the ROOM2 is still intersecting, now trying to swap the room with another of the same type
	IsIntersecting = True
	
	Local x2%, y2%, Rot%, Rot2%
	
	For r2.Rooms = Each Rooms
		If r2 <> r And (Not r2\RoomTemplate\DisableOverlapCheck) Then
			If r\RoomTemplate\Shape = r2\RoomTemplate\Shape And r\Zone = r2\Zone And (r2\RoomTemplate\Name <> "room2_checkpoint_lcz_hcz" And r2\RoomTemplate\Name <> "room2_checkpoint_hcz_ez" And r2\RoomTemplate\Name <> "cont1_173") Then
				x = r\x / 8.0
				y = r\z / 8.0
				Rot = r\Angle
				
				x2 = r2\x / 8.0
				y2 = r2\z / 8.0
				Rot2 = r2\Angle
				
				IsIntersecting = False
				
				r\x = x2 * 8.0
				r\z = y2 * 8.0
				r\Angle = Rot2
				PositionEntity(r\OBJ, r\x, r\y, r\z)
				RotateEntity(r\OBJ, 0.0, r\Angle, 0.0)
				CalculateRoomExtents(r)
				
				r2\x = x * 8.0
				r2\z = y * 8.0
				r2\Angle = Rot
				PositionEntity(r2\OBJ, r2\x, r2\y, r2\z)
				RotateEntity(r2\OBJ, 0.0, r2\Angle, 0.0)
				CalculateRoomExtents(r2)
				
				; ~ Make sure neither room overlaps with anything after the swap
				For r3.Rooms = Each Rooms
					If (Not r3\RoomTemplate\DisableOverlapCheck) Then
						If r3 <> r Then
							If CheckRoomOverlap(r, r3) Then
								IsIntersecting = True
								Exit
							EndIf
						EndIf
						If r3 <> r2 Then
							If CheckRoomOverlap(r2, r3) Then
								IsIntersecting = True
								Exit
							EndIf
						EndIf	
					EndIf
				Next
				
				; ~ Either the original room or the "reposition" room is intersecting, reset the position of each room to their original one
				If IsIntersecting Then
					r\x = x * 8.0
					r\z = y * 8.0
					r\Angle = Rot
					PositionEntity(r\OBJ, r\x, r\y, r\z)
					RotateEntity(r\OBJ, 0.0, r\Angle, 0.0)
					CalculateRoomExtents(r)
					
					r2\x = x2 * 8.0
					r2\z = y2 * 8.0
					r2\Angle = Rot2
					PositionEntity(r2\OBJ, r2\x, r2\y, r2\z)
					RotateEntity(r2\OBJ, 0.0, r2\Angle, 0.0)
					CalculateRoomExtents(r2)
					
					IsIntersecting = False
				EndIf
			EndIf
		EndIf
	Next
	
	; ~ Room was able to the placed in a different spot
	If (Not IsIntersecting) Then Return(True)
	
	Return(False)
End Function

Const MapGridSize% = 18
Const RoomSpacing# = 8.0

Type MapGrid
	Field Grid%[(MapGridSize + 1) ^ 2]
	Field Found%[(MapGridSize + 1) ^ 2]
	Field RoomName$[MapGridSize ^ 2]
	Field RoomID%[ROOM4 + 1]
End Type

Global CurrMapGrid.MapGrid

; ~ Map Grid Tile ID Constants
;[Block]
Const MapGrid_NoTile% = 0
Const MapGrid_Tile% = 1
Const MapGrid_CheckpointTile% = 255
;[End Block]

Function CreateMap%()
	Local r.Rooms, r2.Rooms, d.Doors
	Local x%, y%, Temp%, Temp2
	Local i%, x2%, y2%
	Local Width%, Height%, TempHeight%, yHallways%
	Local ShouldSpawnDoor%, Zone%
	
	I_Zone\Transition[0] = 13
	I_Zone\Transition[1] = 7
	I_Zone\HasCustomForest = False
	I_Zone\HasCustomMT = False
	
	SeedRnd(GenerateSeedNumber(RandomSeed))
	
	If CurrMapGrid <> Null Then
		Delete(CurrMapGrid) : CurrMapGrid = Null
	EndIf
	CurrMapGrid = New MapGrid
	
	x = MapGridSize / 2
	y = MapGridSize - 2
	
	For i = y To MapGridSize - 1
		CurrMapGrid\Grid[x + (i * MapGridSize)] = MapGrid_Tile
	Next
	
	Repeat
		Width = Rand(10, 15)
		
		If x > Ceil(MapGridSize * 0.6) Then
			Width = -Width
		ElseIf x > Ceil(MapGridSize * 0.4)
			x = x - (Width / 2)
		EndIf
		
		; ~ Make sure the hallway doesn't go outside the array
		If x + Width > MapGridSize - 3 Then
			Width = MapGridSize - 3 - x
		ElseIf x + Width < 2
			Width = (-x) + 2
		EndIf
		
		x = Min(x, x + Width)
		Width = Abs(Width)
		For i = x To x + Width
			CurrMapGrid\Grid[Min(i, MapGridSize) + (y * MapGridSize)] = MapGrid_Tile
		Next
		
		Height = Rand(3, 4)
		If y - Height < 1 Then Height = y - 1
		
		yHallways = Rand(4, 5)
		
		If GetZone(y - Height) <> GetZone(y - Height + 1) Then Height = Height - 1
		
		For i = 1 To yHallways
			x2 = Max(Min(Rand(x, x + Width - 1), MapGridSize - 2), 2.0)
			While CurrMapGrid\Grid[x2 + ((y - 1) * MapGridSize)] Lor CurrMapGrid\Grid[(x2 - 1) + ((y - 1) * MapGridSize)] Lor CurrMapGrid\Grid[(x2 + 1) + ((y - 1) * MapGridSize)]
				x2 = x2 + 1
			Wend
			
			If x2 < x + Width Then
				If i = 1 Then
					TempHeight = Height 
					If Rand(2) = 1 Then
						x2 = x
					Else
						x2 = x + Width
					EndIf
				Else
					TempHeight = Rand(1, Height)
				EndIf
				
				For y2 = y - TempHeight To y
					If GetZone(y2) <> GetZone(y2 + 1) Then ; ~ A room leading from zone to another
						CurrMapGrid\Grid[x2 + (y2 * MapGridSize)] = MapGrid_CheckpointTile
					Else
						CurrMapGrid\Grid[x2 + (y2 * MapGridSize)] = MapGrid_Tile
					EndIf
				Next
				
				If TempHeight = Height Then Temp = x2
			EndIf
		Next
		x = Temp
		y = y - Height
	Until y < 2
	
	Local Room1Amount%[ZONEAMOUNT], Room2Amount%[ZONEAMOUNT], Room2CAmount%[ZONEAMOUNT], Room3Amount%[ZONEAMOUNT], Room4Amount%[ZONEAMOUNT]
	
	; ~ Count the amount of rooms
	For y = 1 To MapGridSize - 1
		Zone = GetZone(y)
		For x = 1 To MapGridSize - 1
			If CurrMapGrid\Grid[x + (y * MapGridSize)] > MapGrid_NoTile Then
				Temp = 0
				Temp = Min(CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)], 1.0) + Min(CurrMapGrid\Grid[(x - 1) + (y * MapGridSize)], 1.0) + Min(CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)], 1.0) + Min(CurrMapGrid\Grid[x + ((y - 1) * MapGridSize)], 1.0)			
				If CurrMapGrid\Grid[x + (y * MapGridSize)] <> MapGrid_CheckpointTile Then CurrMapGrid\Grid[x + (y * MapGridSize)] = Temp
				Select CurrMapGrid\Grid[x + (y * MapGridSize)]
					Case 1
						;[Block]
						Room1Amount[Zone] = Room1Amount[Zone] + 1
						;[End Block]
					Case 2
						;[Block]
						If Min(CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)], 1.0) + Min(CurrMapGrid\Grid[(x - 1) + (y * MapGridSize)], 1.0) = 2 Then
							Room2Amount[Zone] = Room2Amount[Zone] + 1	
						ElseIf Min(CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)], 1.0) + Min(CurrMapGrid\Grid[x + ((y - 1) * MapGridSize)], 1.0) = 2
							Room2Amount[Zone] = Room2Amount[Zone] + 1	
						Else
							Room2CAmount[Zone] = Room2CAmount[Zone] + 1
						EndIf
						;[End Block]
					Case 3
						;[Block]
						Room3Amount[Zone] = Room3Amount[Zone] + 1
						;[End Block]
					Case 4
						;[Block]
						Room4Amount[Zone] = Room4Amount[Zone] + 1
						;[End Block]
				End Select
			EndIf
		Next
	Next		
	
	Local Placed%
	
	; ~ Force more room1s (if needed)
	For i = 0 To 2
		; ~ Need more rooms if there are less than 5 of them
		Temp = (-Room1Amount[i]) + 5
		If Temp > 0 Then
			For y = (MapGridSize / ZONEAMOUNT) * (2 - i) + 1 To ((MapGridSize / ZONEAMOUNT) * ((2 - i) + 1.0)) - 2			
				For x = 2 To MapGridSize - 2
					If CurrMapGrid\Grid[x + (y * MapGridSize)] = MapGrid_NoTile Then
						If (Min(CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)], 1.0) + Min(CurrMapGrid\Grid[(x - 1) + (y * MapGridSize)], 1.0) + Min(CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)], 1.0) + Min(CurrMapGrid\Grid[x + ((y - 1) * MapGridSize)], 1.0)) = 1 Then
							If CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)] Then
								x2 = x + 1 : y2 = y
							ElseIf CurrMapGrid\Grid[(x - 1) + (y * MapGridSize)]
								x2 = x - 1 : y2 = y
							ElseIf CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)]
								x2 = x : y2 = y + 1	
							ElseIf CurrMapGrid\Grid[x + ((y - 1) * MapGridSize)]
								x2 = x : y2 = y - 1
							EndIf
							
							Placed = False
							If CurrMapGrid\Grid[x2 + (y2 * MapGridSize)] > 1 And CurrMapGrid\Grid[x2 + (y2 * MapGridSize)] < 4 Then 
								Select CurrMapGrid\Grid[x2 + (y2 * MapGridSize)]
									Case 2
										;[Block]
										If Min(CurrMapGrid\Grid[(x2 + 1) + (y2 * MapGridSize)], 1.0) + Min(CurrMapGrid\Grid[(x2 - 1) + (y2 * MapGridSize)], 1.0) = 2 Then
											Room2Amount[i] = Room2Amount[i] - 1
											Room3Amount[i] = Room3Amount[i] + 1
											Placed = True
										ElseIf Min(CurrMapGrid\Grid[x2 + ((y2 + 1) * MapGridSize)], 1.0) + Min(CurrMapGrid\Grid[x2 + ((y2 - 1) * MapGridSize)], 1.0) = 2
											Room2Amount[i] = Room2Amount[i] - 1
											Room3Amount[i] = Room3Amount[i] + 1
											Placed = True
										EndIf
										;[End Block]
									Case 3
										;[Block]
										Room3Amount[i] = Room3Amount[i] - 1
										Room4Amount[i] = Room4Amount[i] + 1	
										Placed = True
										;[End Block]
								End Select
								
								If Placed Then
									CurrMapGrid\Grid[x2 + (y2 * MapGridSize)] = CurrMapGrid\Grid[x2 + (y2 * MapGridSize)] + 1
									
									CurrMapGrid\Grid[x + (y * MapGridSize)] = MapGrid_Tile
									Room1Amount[i] = Room1Amount[i] + 1	
									
									Temp = Temp - 1
								EndIf
							EndIf
						EndIf
					EndIf
					If Temp = 0 Then Exit
				Next
				If Temp = 0 Then Exit
			Next
		EndIf
	Next
	
	; ~ Force more ROOM4 and ROOM2C
	For i = 0 To 2
		Select i
			Case 2
				;[Block]
				Zone = 2
				Temp2 = MapGridSize / 3
				;[End Block]
			Case 1
				;[Block]
				Zone = (MapGridSize / 3) + 1
				Temp2 = (MapGridSize * (2.0 / 3.0)) - 1
				;[End Block]
			Case 0
				;[Block]
				Zone = (MapGridSize * (2.0 / 3.0)) + 1
				Temp2 = MapGridSize - 2
				;[End Block]
		End Select
		
		If Room4Amount[i] < 1 Then ; ~ We want at least one ROOM4
			Temp = 0
			For y = Zone To Temp2
				For x = 2 To MapGridSize - 2
					If CurrMapGrid\Grid[x + (y * MapGridSize)] = 3 Then
						Select False ; ~ See if adding a ROOM1 is possible
							Case (CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)] Lor CurrMapGrid\Grid[(x + 1) + ((y + 1) * MapGridSize)] Lor CurrMapGrid\Grid[(x + 1) + ((y - 1) * MapGridSize)] Lor CurrMapGrid\Grid[(x + 2) + (y * MapGridSize)])
								;[Block]
								CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)] = 1
								Temp = 1
								;[End Block]
							Case (CurrMapGrid\Grid[(x - 1) + (y * MapGridSize)] Lor CurrMapGrid\Grid[(x - 1) + ((y + 1) * MapGridSize)] Lor CurrMapGrid\Grid[(x - 1) + ((y - 1) * MapGridSize)] Lor CurrMapGrid\Grid[(x - 2) + (y * MapGridSize)])
								;[Block]
								CurrMapGrid\Grid[(x - 1) + (y * MapGridSize)] = 1
								Temp = 1
								;[End Block]
							Case (CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)] Lor CurrMapGrid\Grid[(x + 1) + ((y + 1) * MapGridSize)] Lor CurrMapGrid\Grid[(x - 1) + ((y + 1) * MapGridSize)] Lor CurrMapGrid\Grid[x + ((y + 2) * MapGridSize)])
								;[Block]
								CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)] = 1
								Temp = 1
								;[End Block]
							Case (CurrMapGrid\Grid[x + ((y - 1) * MapGridSize)] Lor CurrMapGrid\Grid[(x + 1) + ((y - 1) * MapGridSize)] Lor CurrMapGrid\Grid[(x - 1) + ((y - 1) * MapGridSize)] Lor CurrMapGrid\Grid[x + ((y - 2) * MapGridSize)])
								;[Block]
								CurrMapGrid\Grid[x + ((y - 1) * MapGridSize)] = 1
								Temp = 1
								;[End Block]
						End Select
						If Temp = 1 Then
							CurrMapGrid\Grid[x + (y * MapGridSize)] = 4 ; ~ Turn this room into a ROOM4
							Room4Amount[i] = Room4Amount[i] + 1
							Room3Amount[i] = Room3Amount[i] - 1
							Room1Amount[i] = Room1Amount[i] + 1
						EndIf
					EndIf
					If Temp = 1 Then Exit
				Next
				If Temp = 1 Then Exit
			Next
		EndIf
		
		If Room2CAmount[i] < 1 Then ; ~ We want at least one ROOM2C
			Temp = 0
			Zone = Zone + 1
			Temp2 = Temp2 - 1
			For y = Zone To Temp2
				For x = 3 To MapGridSize - 3
					If CurrMapGrid\Grid[x + (y * MapGridSize)] = MapGrid_Tile Then
						Select True ; ~ See if adding some rooms is possible
							Case CurrMapGrid\Grid[(x - 1) + (y * MapGridSize)] > MapGrid_NoTile
								;[Block]
								If (CurrMapGrid\Grid[(x + 1) + ((y - 1) * MapGridSize)] + CurrMapGrid\Grid[(x + 1) + ((y + 1) * MapGridSize)] + CurrMapGrid\Grid[(x + 2) + (y * MapGridSize)]) = 0 Then
									If (CurrMapGrid\Grid[(x + 1) + ((y - 2) * MapGridSize)] + CurrMapGrid\Grid[(x + 2) + ((y - 1) * MapGridSize)]) = 0 Then
										CurrMapGrid\Grid[x + (y * MapGridSize)] = 2
										CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)] = 2
										CurrMapGrid\Grid[(x + 1) + ((y - 1) * MapGridSize)] = 1
										Temp = 1
									ElseIf (CurrMapGrid\Grid[(x + 1) + ((y + 2) * MapGridSize)] + CurrMapGrid\Grid[(x + 2) + ((y + 1) * MapGridSize)]) = 0 Then
										CurrMapGrid\Grid[x + (y * MapGridSize)] = 2
										CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)] = 2
										CurrMapGrid\Grid[(x + 1) + ((y + 1) * MapGridSize)] = 1
										Temp = 1
									EndIf
								EndIf
								;[End Block]
							Case CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)] > MapGrid_NoTile
								;[Block]
								If (CurrMapGrid\Grid[(x - 1) + ((y - 1) * MapGridSize)] + CurrMapGrid\Grid[(x - 1) + ((y + 1) * MapGridSize)] + CurrMapGrid\Grid[(x - 2) + (y * MapGridSize)]) = 0 Then
									If (CurrMapGrid\Grid[(x - 1) + ((y - 2) * MapGridSize)] + CurrMapGrid\Grid[(x - 2) + ((y - 1) * MapGridSize)]) = 0 Then
										CurrMapGrid\Grid[x + (y * MapGridSize)] = 2
										CurrMapGrid\Grid[(x - 1) + (y * MapGridSize)] = 2
										CurrMapGrid\Grid[(x - 1) + ((y - 1) * MapGridSize)] = 1
										Temp = 1
									ElseIf (CurrMapGrid\Grid[(x - 1) + ((y + 2) * MapGridSize)] + CurrMapGrid\Grid[(x - 2) + ((y + 1) * MapGridSize)]) = 0 Then
										CurrMapGrid\Grid[x + (y * MapGridSize)] = 2
										CurrMapGrid\Grid[(x - 1) + (y * MapGridSize)] = 2
										CurrMapGrid\Grid[(x - 1) + ((y + 1) * MapGridSize)] = 1
										Temp = 1
									EndIf
								EndIf
								;[End Block]
							Case CurrMapGrid\Grid[x + ((y - 1) * MapGridSize)] > MapGrid_NoTile
								;[Block]
								If (CurrMapGrid\Grid[(x - 1) + ((y + 1) * MapGridSize)] + CurrMapGrid\Grid[(x + 1) + ((y + 1) * MapGridSize)] + CurrMapGrid\Grid[x + ((y + 2) * MapGridSize)]) = 0 Then
									If (CurrMapGrid\Grid[(x - 2) + ((y + 1) * MapGridSize)] + CurrMapGrid\Grid[(x - 1) + ((y + 2) * MapGridSize)]) = 0 Then
										CurrMapGrid\Grid[x + (y * MapGridSize)] = 2
										CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)] = 2
										CurrMapGrid\Grid[(x - 1) + ((y + 1) * MapGridSize)] = 1
										Temp = 1
									ElseIf (CurrMapGrid\Grid[(x + 2) + ((y + 1) * MapGridSize)] + CurrMapGrid\Grid[(x + 1) + ((y + 2) * MapGridSize)]) = 0 Then
										CurrMapGrid\Grid[x + (y * MapGridSize)] = 2
										CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)] = 2
										CurrMapGrid\Grid[(x + 1) + ((y + 1) * MapGridSize)] = 1
										Temp = 1
									EndIf
								EndIf
								;[End Block]
							Case CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)] > MapGrid_NoTile
								;[Block]
								If (CurrMapGrid\Grid[(x - 1) + ((y - 1) * MapGridSize)] + CurrMapGrid\Grid[(x + 1) + ((y - 1) * MapGridSize)] + CurrMapGrid\Grid[x + ((y - 2) * MapGridSize)]) = 0 Then
									If (CurrMapGrid\Grid[(x - 2) + ((y - 1) * MapGridSize)] + CurrMapGrid\Grid[(x - 1) + ((y - 2) * MapGridSize)]) = 0 Then
										CurrMapGrid\Grid[x + (y * MapGridSize)] = 2
										CurrMapGrid\Grid[x + ((y - 1) * MapGridSize)] = 2
										CurrMapGrid\Grid[(x - 1) + ((y - 1) * MapGridSize)] = 1
										Temp = 1
									ElseIf (CurrMapGrid\Grid[(x + 2) + ((y - 1) * MapGridSize)] + CurrMapGrid\Grid[(x + 1) + ((y - 2) * MapGridSize)]) = 0 Then
										CurrMapGrid\Grid[x + (y * MapGridSize)] = 2
										CurrMapGrid\Grid[x + ((y - 1) * MapGridSize)] = 2
										CurrMapGrid\Grid[(x + 1) + ((y - 1) * MapGridSize)] = 1
										Temp = 1
									EndIf
								EndIf
								;[End Block]
						End Select
						If Temp = 1 Then
							Room2CAmount[i] = Room2CAmount[i] + 1
							Room2Amount[i] = Room2Amount[i] + 1
						EndIf
					EndIf
					If Temp = 1 Then Exit
				Next
				If Temp = 1 Then Exit
			Next
		EndIf
	Next
	
	Local MaxRooms% = 55 * MapGridSize / 20
	
	MaxRooms = Max(MaxRooms, Room1Amount[0] + Room1Amount[1] + Room1Amount[2] + 1)
	MaxRooms = Max(MaxRooms, Room2Amount[0] + Room2Amount[1] + Room2Amount[2] + 1)
	MaxRooms = Max(MaxRooms, Room2CAmount[0] + Room2CAmount[1] + Room2CAmount[2] + 1)
	MaxRooms = Max(MaxRooms, Room3Amount[0] + Room3Amount[1] + Room3Amount[2] + 1)
	MaxRooms = Max(MaxRooms, Room4Amount[0] + Room4Amount[1] + Room4Amount[2] + 1)
	
	Dim MapRoom$(ROOM4, MaxRooms)
	
	; ~ [LIGHT CONTAINMENT ZONE]
	
	Local MinPos% = 1, MaxPos% = Room1Amount[0] - 1
	
	MapRoom(ROOM1, 0) = "cont1_173"
	
	SetRoom("cont1_372", ROOM1, Floor(0.1 * Float(Room1Amount[0])), MinPos, MaxPos)
	SetRoom("cont1_005", ROOM1, Floor(0.3 * Float(Room1Amount[0])), MinPos, MaxPos)
	SetRoom("cont1_914", ROOM1, Floor(0.35 * Float(Room1Amount[0])), MinPos, MaxPos)
	SetRoom("cont1_205", ROOM1, Floor(0.5 * Float(Room1Amount[0])), MinPos, MaxPos)
	SetRoom("room1_archive", ROOM1, Floor(0.6 * Float(Room1Amount[0])), MinPos, MaxPos)
	
	MapRoom(ROOM2C, 0) = "room2c_gw_lcz"
	
	MinPos = 1
	MaxPos = Room2Amount[0] - 1
	
	MapRoom(ROOM2, 0) = "room2_closets" 
	
	SetRoom("room2_test_lcz", ROOM2, Floor(0.1 * Float(Room2Amount[0])), MinPos, MaxPos)
	SetRoom("cont2_427_714_860_1025", ROOM2, Floor(0.2 * Float(Room2Amount[0])), MinPos, MaxPos)
	SetRoom("room2_storage", ROOM2, Floor(0.3 * Float(Room2Amount[0])), MinPos, MaxPos)
	SetRoom("room2_gw_2", ROOM2, Floor(0.4 * Float(Room2Amount[0])), MinPos, MaxPos)
	SetRoom("room2_sl", ROOM2, Floor(0.5 * Float(Room2Amount[0])), MinPos, MaxPos)
	SetRoom("cont2_012", ROOM2, Floor(0.55 * Float(Room2Amount[0])), MinPos, MaxPos)
	SetRoom("cont2_500_1499", ROOM2, Floor(0.6 * Float(Room2Amount[0])), MinPos, MaxPos)
	SetRoom("cont2_1123", ROOM2, Floor(0.75 * Float(Room2Amount[0])), MinPos, MaxPos)
	SetRoom("room2_js", ROOM2, Floor(0.85 * Float(Room2Amount[0])), MinPos, MaxPos)
	SetRoom("room2_elevator", ROOM2, Floor(0.9 * Float(Room2Amount[0])), MinPos, MaxPos)
	
	MapRoom(ROOM2C, Floor(0.5 * Float(Room2CAmount[0]))) = "cont2c_1162_arc"
	
	MapRoom(ROOM3, Floor(Rnd(0.2, 0.8) * Float(Room3Amount[0]))) = "room3_storage"
	
	MapRoom(ROOM4, Floor(0.3 * Float(Room4Amount[0]))) = "room4_ic"
	
	; ~ [HEAVY CONTAINMENT ZONE]
	
	MinPos = Room1Amount[0]
	MaxPos = Room1Amount[0] + Room1Amount[1] - 1
	
	SetRoom("cont1_079", ROOM1, Room1Amount[0] + Floor(0.15 * Float(Room1Amount[1])), MinPos, MaxPos)
	SetRoom("cont1_106", ROOM1, Room1Amount[0] + Floor(0.3 * Float(Room1Amount[1])), MinPos, MaxPos)
	SetRoom("cont1_096", ROOM1, Room1Amount[0] + Floor(0.4 * Float(Room1Amount[1])), MinPos, MaxPos)
	SetRoom("cont1_035", ROOM1, Room1Amount[0] + Floor(0.5 * Float(Room1Amount[1])), MinPos, MaxPos)
	SetRoom("cont1_895", ROOM1, Room1Amount[0] + Floor(0.7 * Float(Room1Amount[1])), MinPos, MaxPos)
	
	MinPos = Room2Amount[0]
	MaxPos = Room2Amount[0] + Room2Amount[1] - 1
	
	MapRoom(ROOM2, Room2Amount[0] + Floor(0.1 * Float(Room2Amount[1]))) = "room2_nuke"
	
	SetRoom("cont2_409", ROOM2, Room2Amount[0] + Floor(0.15 * Float(Room2Amount[1])), MinPos, MaxPos)
	SetRoom("room2_mt", ROOM2, Room2Amount[0] + Floor(0.25 * Float(Room2Amount[1])), MinPos, MaxPos)
	SetRoom("cont2_049", ROOM2, Room2Amount[0] + Floor(0.4 * Float(Room2Amount[1])), MinPos, MaxPos)
	SetRoom("cont2_008", ROOM2, Room2Amount[0] + Floor(0.5 * Float(Room2Amount[1])), MinPos, MaxPos)
	SetRoom("room2_shaft", ROOM2, Room2Amount[0] + Floor(0.6 * Float(Room2Amount[1])), MinPos, MaxPos)
	SetRoom("room2_test_hcz", ROOM2, Room2Amount[0] + Floor(0.7 * Float(Room2Amount[1])), MinPos, MaxPos)
	SetRoom("room2_servers_hcz", ROOM2, Room2Amount[0] + Floor(0.9 * Room2Amount[1]), MinPos, MaxPos)
	
	MapRoom(ROOM2C, Room2CAmount[0] + Floor(0.5 * Float(Room2CAmount[1]))) = "room2c_maintenance"
	
	MapRoom(ROOM3, Room3Amount[0] + Floor(0.3 * Float(Room3Amount[1]))) = "cont3_513"
	MapRoom(ROOM3, Room3Amount[0] + Floor(0.6 * Float(Room3Amount[1]))) = "cont3_966"
	
	; ~ [ENTRANCE ZONE]
	
	MapRoom(ROOM1, Room1Amount[0] + Room1Amount[1] + Room1Amount[2] - 3) = "gate_b_entrance"
	MapRoom(ROOM1, Room1Amount[0] + Room1Amount[1] + Room1Amount[2] - 2) = "gate_a_entrance"
	MapRoom(ROOM1, Room1Amount[0] + Room1Amount[1] + Room1Amount[2] - 1) = "room1_o5"
	MapRoom(ROOM1, Room1Amount[0] + Room1Amount[1]) = "room1_lifts"
	
	MinPos = Room2Amount[0] + Room2Amount[1]
	MaxPos = Room2Amount[0] + Room2Amount[1] + Room2Amount[2] - 1		
	
	MapRoom(ROOM2, MinPos + Floor(0.1 * Float(Room2Amount[2]))) = "room2_scientists"
	
	SetRoom("room2_cafeteria", ROOM2, MinPos + Floor(0.2 * Float(Room2Amount[2])), MinPos, MaxPos)
	SetRoom("room2_office_3", ROOM2, MinPos + Floor(0.3 * Float(Room2Amount[2])), MinPos, MaxPos)
	SetRoom("room2_bio", ROOM2, MinPos + Floor(0.35 * Float(Room2Amount[2])), MinPos, MaxPos)
	SetRoom("room2_servers_ez", ROOM2, MinPos + Floor(0.4 * Room2Amount[2]), MinPos, MaxPos)	
	SetRoom("room2_ez", ROOM2, MinPos + Floor(0.45 * Room2Amount[2]), MinPos, MaxPos)
	SetRoom("room2_office", ROOM2, MinPos + Floor(0.5 * Room2Amount[2]), MinPos, MaxPos)	
	SetRoom("room2_office_2", ROOM2, MinPos + Floor(0.55 * Room2Amount[2]), MinPos, MaxPos)	
	SetRoom("cont2_860_1", ROOM2, MinPos + Floor(0.6 * Room2Amount[2]), MinPos, MaxPos)
	SetRoom("room2_medibay", ROOM2, MinPos + Floor(0.7 * Float(Room2Amount[2])), MinPos, MaxPos)
	SetRoom("room2_scientists_2", ROOM2, MinPos + Floor(0.8 * Room2Amount[2]), MinPos, MaxPos)
	SetRoom("room2_ic", ROOM2, MinPos + Floor(0.9 * Float(Room2Amount[2])), MinPos, MaxPos)
	
	MapRoom(ROOM2C, Room2CAmount[0] + Room2CAmount[1]) = "room2c_ec"	
	MapRoom(ROOM2C, Room2CAmount[0] + Room2CAmount[1] + 1) = "room2c_gw_ez"		
	
	MapRoom(ROOM3, Room3Amount[0] + Room3Amount[1] + Floor(0.3 * Float(Room3Amount[2]))) = "room3_2_ez"
	MapRoom(ROOM3, Room3Amount[0] + Room3Amount[1] + Floor(0.7 * Float(Room3Amount[2]))) = "room3_3_ez"
	MapRoom(ROOM3, Room3Amount[0] + Room3Amount[1] + Floor(0.5 * Float(Room3Amount[2]))) = "room3_office"
	
	; ~ [GENERATE OTHER ROOMS]
	
	Temp = 0
	For y = MapGridSize - 1 To 1 Step -1
		If y < (MapGridSize / 3) + 1 Then
			Zone = 3
		ElseIf y < MapGridSize * (2.0 / 3.0)
			Zone = 2
		Else
			Zone = 1
		EndIf
		For x = 1 To MapGridSize - 2
			If CurrMapGrid\Grid[x + (y * MapGridSize)] = MapGrid_CheckpointTile Then
				If y > MapGridSize / 2 Then
					r.Rooms = CreateRoom(Zone, ROOM2, x * RoomSpacing, 0.0, y * RoomSpacing, "room2_checkpoint_lcz_hcz")
				Else
					r.Rooms = CreateRoom(Zone, ROOM2, x * RoomSpacing, 0.0, y * RoomSpacing, "room2_checkpoint_hcz_ez")
				EndIf
			ElseIf CurrMapGrid\Grid[x + (y * MapGridSize)] > MapGrid_NoTile				
				Temp = Min(CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)], 1.0) + Min(CurrMapGrid\Grid[(x - 1) + (y * MapGridSize)], 1.0) + Min(CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)], 1.0) + Min(CurrMapGrid\Grid[x + ((y - 1) * MapGridSize)], 1.0)
				Select Temp
					Case 1 ; ~ Generate ROOM1
						;[Block]
						If CurrMapGrid\RoomID[ROOM1] < MaxRooms And CurrMapGrid\RoomName[x + (y * MapGridSize)] = "" Then
							If MapRoom(ROOM1, CurrMapGrid\RoomID[ROOM1]) <> "" Then CurrMapGrid\RoomName[x + (y * MapGridSize)] = MapRoom(ROOM1, CurrMapGrid\RoomID[ROOM1])	
						EndIf
						
						r.Rooms = CreateRoom(Zone, ROOM1, x * RoomSpacing, 0.0, y * RoomSpacing, CurrMapGrid\RoomName[x + (y * MapGridSize)])
						If CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)] Then
							r\Angle = 180.0
						ElseIf CurrMapGrid\Grid[(x - 1) + (y * MapGridSize)]
							r\Angle = 270.0
						ElseIf CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)]
							r\Angle = 90.0
						Else
							r\Angle = 0.0
						EndIf
						TurnEntity(r\OBJ, 0.0, r\Angle, 0.0)
						CurrMapGrid\RoomID[ROOM1] = CurrMapGrid\RoomID[ROOM1] + 1
						;[End Block]
					Case 2 ; ~ Generate ROOM2
						;[Block]
						If CurrMapGrid\Grid[(x - 1) + (y * MapGridSize)] > MapGrid_NoTile And CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)] > MapGrid_NoTile Then
							If CurrMapGrid\RoomID[ROOM2] < MaxRooms And CurrMapGrid\RoomName[x + (y * MapGridSize)] = "" Then
								If MapRoom(ROOM2, CurrMapGrid\RoomID[ROOM2]) <> "" Then CurrMapGrid\RoomName[x + (y * MapGridSize)] = MapRoom(ROOM2, CurrMapGrid\RoomID[ROOM2])	
							EndIf
							r.Rooms = CreateRoom(Zone, ROOM2, x * RoomSpacing, 0.0, y * RoomSpacing, CurrMapGrid\RoomName[x + (y * MapGridSize)])
							If Rand(2) = 1 Then
								r\Angle = 90.0
							Else
								r\Angle = 270.0
							EndIf
							TurnEntity(r\OBJ, 0.0, r\Angle, 0.0)
							CurrMapGrid\RoomID[ROOM2] = CurrMapGrid\RoomID[ROOM2] + 1
						ElseIf CurrMapGrid\Grid[x + ((y - 1) * MapGridSize)] > MapGrid_NoTile And CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)] > MapGrid_NoTile
							If CurrMapGrid\RoomID[ROOM2] < MaxRooms And CurrMapGrid\RoomName[x + (y * MapGridSize)] = ""  Then
								If MapRoom(ROOM2, CurrMapGrid\RoomID[ROOM2]) <> "" Then CurrMapGrid\RoomName[x + (y * MapGridSize)] = MapRoom(ROOM2, CurrMapGrid\RoomID[ROOM2])	
							EndIf
							r.Rooms = CreateRoom(Zone, ROOM2, x * RoomSpacing, 0.0, y * RoomSpacing, CurrMapGrid\RoomName[x + (y * MapGridSize)])
							If Rand(2) = 1 Then
								r\Angle = 180.0
							Else
								r\Angle = 0.0
							EndIf
							TurnEntity(r\OBJ, 0.0, r\Angle, 0.0)
							CurrMapGrid\RoomID[ROOM2] = CurrMapGrid\RoomID[ROOM2] + 1
						Else
							If CurrMapGrid\RoomID[ROOM2C] < MaxRooms And CurrMapGrid\RoomName[x + (y * MapGridSize)] = ""  Then
								If MapRoom(ROOM2C, CurrMapGrid\RoomID[ROOM2C]) <> "" Then CurrMapGrid\RoomName[x + (y * MapGridSize)] = MapRoom(ROOM2C, CurrMapGrid\RoomID[ROOM2C])	
							EndIf
							If CurrMapGrid\Grid[(x - 1) + (y * MapGridSize)] > MapGrid_NoTile And CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)] > MapGrid_NoTile Then
								r.Rooms = CreateRoom(Zone, ROOM2C, x * RoomSpacing, 0.0, y * RoomSpacing, CurrMapGrid\RoomName[x + (y * MapGridSize)])
								r\Angle = 180.0
							ElseIf CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)] > MapGrid_NoTile And CurrMapGrid\Grid[x + ((y + 1) * MapGridSize)] > MapGrid_NoTile
								r.Rooms = CreateRoom(Zone, ROOM2C, x * RoomSpacing, 0.0, y * RoomSpacing, CurrMapGrid\RoomName[x + (y * MapGridSize)])
								r\Angle = 90.0
							ElseIf CurrMapGrid\Grid[(x - 1) + (y * MapGridSize)] > MapGrid_NoTile And CurrMapGrid\Grid[x + ((y - 1) * MapGridSize)] > MapGrid_NoTile
								r.Rooms = CreateRoom(Zone, ROOM2C, x * RoomSpacing, 0.0, y * RoomSpacing, CurrMapGrid\RoomName[x + (y * MapGridSize)])
								r\Angle = 270.0
							Else
								r.Rooms = CreateRoom(Zone, ROOM2C, x * RoomSpacing, 0.0, y * RoomSpacing, CurrMapGrid\RoomName[x + (y * MapGridSize)])
								r\Angle = 0.0
							EndIf
							TurnEntity(r\OBJ, 0.0, r\Angle, 0.0)
							CurrMapGrid\RoomID[ROOM2C] = CurrMapGrid\RoomID[ROOM2C] + 1
						EndIf
						;[End Block]
					Case 3 ; ~ Generate ROOM3
						;[Block]
						If CurrMapGrid\RoomID[ROOM3] < MaxRooms And CurrMapGrid\RoomName[x + (y * MapGridSize)] = ""  Then
							If MapRoom(ROOM3, CurrMapGrid\RoomID[ROOM3]) <> "" Then CurrMapGrid\RoomName[x + (y * MapGridSize)] = MapRoom(ROOM3, CurrMapGrid\RoomID[ROOM3])	
						EndIf
						r.Rooms = CreateRoom(Zone, ROOM3, x * RoomSpacing, 0.0, y * RoomSpacing, CurrMapGrid\RoomName[x + (y * MapGridSize)])
						If (Not CurrMapGrid\Grid[x + ((y - 1) * MapGridSize)]) Then
							r\Angle = 180.0
						ElseIf (Not CurrMapGrid\Grid[(x - 1) + (y * MapGridSize)])
							r\Angle = 90.0
						ElseIf (Not CurrMapGrid\Grid[(x + 1) + (y * MapGridSize)])
							r\Angle = 270.0
						Else
							r\Angle = 0.0
						EndIf
						TurnEntity(r\OBJ, 0.0, r\Angle, 0.0)
						CurrMapGrid\RoomID[ROOM3] = CurrMapGrid\RoomID[ROOM3] + 1
						;[End Block]
					Case 4 ; ~ Generate ROOM4
						;[Block]
						If CurrMapGrid\RoomID[ROOM4] < MaxRooms And CurrMapGrid\RoomName[x + (y * MapGridSize)] = ""  Then
							If MapRoom(ROOM4, CurrMapGrid\RoomID[ROOM4]) <> "" Then CurrMapGrid\RoomName[x + (y * MapGridSize)] = MapRoom(ROOM4, CurrMapGrid\RoomID[ROOM4])	
						EndIf
						r.Rooms = CreateRoom(Zone, ROOM4, x * RoomSpacing, 0.0, y * RoomSpacing, CurrMapGrid\RoomName[x + (y * MapGridSize)])
						If Rand(4) = 1 Then
							r\Angle = 0.0
						ElseIf Rand(3) = 1
							r\Angle = 90.0
						ElseIf Rand(2) = 1
							r\Angle = 180.0
						Else
							r\Angle = 270.0
						EndIf
						TurnEntity(r\OBJ, 0.0, r\Angle, 0.0)
						CurrMapGrid\RoomID[ROOM4] = CurrMapGrid\RoomID[ROOM4] + 1
						;[End Block]
				End Select
			EndIf
		Next
	Next		
	
	; ~ Spawn some rooms outside the map
	r.Rooms = CreateRoom(0, ROOM1, (MapGridSize - 1) * RoomSpacing, 500.0, -(RoomSpacing ^ 2), "gate_b")
	CurrMapGrid\RoomID[ROOM1] = CurrMapGrid\RoomID[ROOM1] + 1
	
	r.Rooms = CreateRoom(0, ROOM1, (MapGridSize - 1) * RoomSpacing, 500.0, RoomSpacing ^ 2, "gate_a")
	CurrMapGrid\RoomID[ROOM1] = CurrMapGrid\RoomID[ROOM1] + 1
	
	r.Rooms = CreateRoom(0, ROOM1, (MapGridSize - 1) * RoomSpacing, 0.0, (MapGridSize - 1) * RoomSpacing, "dimension_106")
	CurrMapGrid\RoomID[ROOM1] = CurrMapGrid\RoomID[ROOM1] + 1	
	
	If opt\IntroEnabled Then
		r.Rooms = CreateRoom(0, ROOM1, RoomSpacing, 0.0, (MapGridSize - 1) * RoomSpacing, "cont1_173_intro")
		CurrMapGrid\RoomID[ROOM1] = CurrMapGrid\RoomID[ROOM1] + 1
	EndIf
	
	r.Rooms = CreateRoom(0, ROOM1, RoomSpacing, 800.0, 0.0, "dimension_1499")
	CurrMapGrid\RoomID[ROOM1] = CurrMapGrid\RoomID[ROOM1] + 1
	
	; ~ Prevent room overlaps
	For r.Rooms = Each Rooms
		PreventRoomOverlap(r)
	Next
	
	If opt\DebugMode Then
		Repeat
			Cls()
			i = MapGridSize - 1
			For x = 0 To MapGridSize - 1
				For y = 0 To MapGridSize - 1
					If CurrMapGrid\Grid[x + (y * MapGridSize)] = MapGrid_NoTile Then
						Zone = GetZone(y)
						Color((50 * Zone) + 50, (50 * Zone) + 50, (50 * Zone) + 50)
						Rect((i * 32) * MenuScale, (y * 32) * MenuScale, 30 * MenuScale, 30 * MenuScale)
					Else
						If CurrMapGrid\Grid[x + (y * MapGridSize)] = MapGrid_CheckpointTile Then
							Color(0, 200, 0)
						ElseIf CurrMapGrid\Grid[x + (y * MapGridSize)] = 4
							Color(50, 50, 255)
						ElseIf CurrMapGrid\Grid[x + (y * MapGridSize)] = 3
							Color(50, 255, 255)
						ElseIf CurrMapGrid\Grid[x + (y * MapGridSize)] = 2
							Color(255, 255, 50)
						Else
							Color(255, 255, 255)
						EndIf
						Rect((i * 32) * MenuScale, (y * 32) * MenuScale, 30 * MenuScale, 30 * MenuScale)
					EndIf
				Next
				i = i - 1
			Next
			
			i = MapGridSize - 1
			For x = 0 To MapGridSize - 1
				For y = 0 To MapGridSize - 1
					If MouseOn((i * 32) * MenuScale, (y * 32) * MenuScale, 32 * MenuScale, 32 * MenuScale) Then
						Color(255, 0, 0)
						Text(((i * 32) + 2) * MenuScale, ((y * 32) + 2) * MenuScale, CurrMapGrid\Grid[x + (y * MapGridSize)] + " " + CurrMapGrid\RoomName[x + (y * MapGridSize)])
					Else
						If CurrMapGrid\RoomName[x + (y * MapGridSize)] <> "" Then
							Color(0, 0, 0)
							Text(((i * 32) + 2) * MenuScale, ((y * 32) + 2) * MenuScale, CurrMapGrid\Grid[x + (y * MapGridSize)])
						EndIf
					EndIf
				Next
				i = i - 1
			Next
			Flip()
			If opt\DisplayMode = 0 Then DrawImage(CursorIMG, ScaledMouseX(), ScaledMouseY())
		Until (GetKey() <> 0 Lor MouseHit(1))
	EndIf
	
	For y = 0 To MapGridSize
		For x = 0 To MapGridSize
			CurrMapGrid\Grid[x + (y * MapGridSize)] = Min(CurrMapGrid\Grid[x + (y * MapGridSize)], 1.0)
		Next
	Next
	
	; ~ Create the doors between rooms
	For y = MapGridSize To 0 Step -1
		If y < I_Zone\Transition[1] - 1 Then
			Zone = 3
		ElseIf y >= I_Zone\Transition[1] - 1 And y < I_Zone\Transition[0] - 1 Then
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
								;[Block]
								If r\Angle = 0.0 Lor r\Angle = 180.0 Lor r\Angle = 90.0 Then ShouldSpawnDoor = True
								;[End Block]
							Default
								;[Block]
								ShouldSpawnDoor = True
								;[End Block]
						End Select
						
						If ShouldSpawnDoor Then
							If x + 1 < MapGridSize + 1
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
							If y + 1 < MapGridSize + 1
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
	
	For r.Rooms = Each Rooms
		r\Angle = WrapAngle(r\Angle)
		SetupTriggerBoxes(r)
		For i = 0 To MaxRoomAdjacents - 1
			r\Adjacent[i] = Null
		Next
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
				ElseIf r2\x = r\x Then
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
End Function

Function LoadTerrain%(HeightMap%, yScale# = 0.7, t1%, t2%, Mask%)
	; ~ Load the HeightMap
	If (Not HeightMap) Then RuntimeError("HeightMap Image " + HeightMap + " not found.")
	
	; ~ Store HeightMap dimensions
	Local x% = ImageWidth(HeightMap) - 1
	Local y% = ImageHeight(HeightMap) - 1
	Local lX%, lY%, Index%
	
	; ~ Load texture and lightmaps
	If (Not t1) Then RuntimeError("Texture #1 " + Chr(34) + t1 + Chr(34) + " not found.")
	If (Not t2) Then RuntimeError("Texture #2 " + Chr(34) + t2 + Chr(34) + " not found.")
	If (Not Mask) Then RuntimeError("Mask image " + Chr(34) + Mask + Chr(34) + " not found.")
	
	; ~ Auto scale the textures to the right size
	If t1 Then ScaleTexture(t1, x / 4, y / 4)
	If t2 Then ScaleTexture(t2, x / 4, y / 4)
	If Mask Then ScaleTexture(Mask, x, y)
	
	; ~ Start building the terrain
	Local Mesh% = CreateMesh()
	Local Surf% = CreateSurface(Mesh)
	
	; ~ Create some verts for the terrain
	For lY = 0 To y
		For lX = 0 To x
			AddVertex(Surf, lX, 0, lY, 1.0 / lX, 1.0 / lY)
		Next
	Next
	RenderWorld()
	
	; ~ Connect the verts with faces
	For lY = 0 To y - 1
		For lX = 0 To x - 1
			AddTriangle(Surf, lX + ((x + 1) * lY), lX + ((x + 1) * lY) + (x + 1), (lX + 1) + ((x + 1) * lY))
			AddTriangle(Surf, (lX + 1) + ((x + 1) * lY), lX + ((x + 1) * lY) + (x + 1), (lX + 1) + ((x + 1) * lY) + (x + 1))
		Next
	Next
	
	; ~ Position the terrain to center 0, 0, 0
	Local Mesh2% = CopyMesh(Mesh, Mesh)
	Local Surf2% = GetSurface(Mesh2, 1)
	
	PositionMesh(Mesh, (-x) / 2.0, 0.0, (-y) / 2.0)
	PositionMesh(Mesh2, (-x) / 2.0, 0.01, (-y) / 2.0)
	
	; ~ Alter vertice height to match the heightmap red channel
	LockBuffer(ImageBuffer(HeightMap))
	LockBuffer(TextureBuffer(Mask))
	
	For lX = 0 To x
		For lY = 0 To y
			; ~ Using vertex alpha and two meshes instead of FE_ALPHAWHATEVER
			; ~ It doesn't look perfect but it does the job
			; ~ You might get better results by downscaling the mask to the same size as the heightmap
			Local MaskX# = Min(lX * Float(TextureWidth(Mask)) / Float(ImageWidth(HeightMap)), TextureWidth(Mask) - 1)
			Local MaskY# = TextureHeight(Mask) - Min(lY * Float(TextureHeight(Mask)) / Float(ImageHeight(HeightMap)), TextureHeight(Mask) - 1)
			Local RGB%, RED%
			
			RGB = ReadPixelFast(Min(lX, x - 1.0), y - Min(lY, y - 1.0), ImageBuffer(HeightMap))
			RED = (RGB And $FF0000) Shr 16 ; ~ Separate out the red
			
			Local Alpha# = (((ReadPixelFast(Max(MaskX -5.0, 5.0), Max(MaskY - 5.0, 5.0), TextureBuffer(Mask)) And $FF000000) Shr 24) / $FF)
			
			Alpha = Alpha + (((ReadPixelFast(Min(MaskX + 5.0, TextureWidth(Mask) - 5.0), Min(MaskY + 5.0, TextureHeight(Mask) - 5), TextureBuffer(Mask)) And $FF000000) Shr 24) / $FF)
			Alpha = Alpha + (((ReadPixelFast(Max(MaskX - 5.0, 5.0), Min(MaskY + 5.0, TextureHeight(Mask) - 5.0), TextureBuffer(Mask)) And $FF000000) Shr 24) / $FF)
			Alpha = Alpha + (((ReadPixelFast(Min(MaskX + 5.0, TextureWidth(Mask) - 5.0), Max(MaskY - 5.0, 5.0), TextureBuffer(Mask)) And $FF000000) Shr 24) / $FF)
			Alpha = Alpha * 0.25
			Alpha = Sqr(Alpha)
			
			Index = lX + ((x + 1) * lY)
			VertexCoords(Surf, Index , VertexX(Surf, Index), RED * yScale, VertexZ(Surf, Index))
			VertexCoords(Surf2, Index , VertexX(Surf2, Index), RED * yScale, VertexZ(Surf2, Index))
			VertexColor(Surf2, Index, 255.0, 255.0, 255.0, Alpha)
			; ~ Set the terrain texture coordinates
			VertexTexCoords(Surf, Index, lX, -lY )
			VertexTexCoords(Surf2, Index, lX, -lY) 
		Next
	Next
	UnlockBuffer(TextureBuffer(Mask))
	UnlockBuffer(ImageBuffer(HeightMap))
	
	UpdateNormals(Mesh)
	UpdateNormals(Mesh2)
	
	EntityTexture(Mesh, t1, 0, 0)
	EntityTexture(Mesh2, t2, 0, 0)
	
	EntityFX(Mesh, 1)
	EntityFX(Mesh2, 1 + 2 + 32)
	
	Return(Mesh)
End Function

RenderLoading(55, "SKY CORE")

Include "Source Code\Sky_Core.bb"

Global CHUNKDATA%[64 ^ 2]

Function SetChunkDataValues%()
	Local StrTemp$, i%, j%
	
	StrTemp = ""
	SeedRnd(GenerateSeedNumber(RandomSeed))
	
	For i = 0 To 63
		For j = 0 To 63
			CHUNKDATA[i + (j * 64)] = Rand(0, GetINIInt("Data\1499chunks.ini", "general", "count"))
		Next
	Next
	
	SeedRnd(MilliSecs2())
End Function

Type ChunkPart
	Field Amount%
	Field OBJ%[128]
	Field RandomYaw#[128]
	Field ID%
End Type

Function CreateChunkParts%(r.Rooms)
	Local chp.ChunkPart, chp2.ChunkPart
	Local File$ = "Data\1499chunks.ini"
	Local ChunkAmount% = GetINIInt(File, "general", "count")
	Local i%, StrTemp$, j%
	
	StrTemp = ""
	SeedRnd(GenerateSeedNumber(RandomSeed))
	
	For i = 0 To ChunkAmount
		Local Loc% = GetINISectionLocation(File, "chunk" + i)
		
		If Loc > 0 Then
			StrTemp = GetINIString2(File, Loc, "count")
			chp.ChunkPart = New ChunkPart
			chp\Amount = Int(StrTemp)
			For j = 0 To Int(StrTemp)
				Local OBJ_ID% = GetINIString2(File, Loc, "obj" + j)
				Local x$ = GetINIString2(File, Loc, "obj" + j + "-x")
				Local z$ = GetINIString2(File, Loc, "obj" + j + "-z")
				Local Yaw$ = GetINIString2(File, Loc, "obj" + j + "-yaw")
				
				chp\OBJ[j] = CopyEntity(r\Objects[OBJ_ID])
				If Lower(Yaw) = "random"
					chp\RandomYaw[j] = Rnd(360.0)
					RotateEntity(chp\OBJ[j], 0.0, chp\RandomYaw[j], 0.0)
				Else
					RotateEntity(chp\OBJ[j], 0.0, Float(Yaw), 0.0)
				EndIf
				PositionEntity(chp\OBJ[j], Float(x), 0, Float(z))
				ScaleEntity(chp\OBJ[j], RoomScale, RoomScale, RoomScale)
				EntityType(chp\OBJ[j], HIT_MAP)
				EntityPickMode(chp\OBJ[j], 2)
				HideEntity(chp\OBJ[j])
			Next
			chp2 = Before(chp)
			If chp2 <> Null Then chp\ID = chp2\ID + 1
		EndIf
	Next
	
	SeedRnd(MilliSecs2())
End Function

Type Chunk
	Field OBJ%[128]
	Field x#, z#, y#
	Field Amount%
	Field IsSpawnChunk%
	Field ChunkPivot%
	Field PlatForm%
End Type

Function CreateChunk.Chunk(OBJ%, x#, y#, z#, IsSpawnChunk% = False)
	Local ch.Chunk, chp.ChunkPart
	Local i%
	
	ch.Chunk = New Chunk
	ch\ChunkPivot = CreatePivot()
	ch\x = x
	ch\y = y
	ch\z = z
	PositionEntity(ch\ChunkPivot, ch\x + 20.0, ch\y, ch\z + 20.0, True)
	
	ch\IsSpawnChunk = IsSpawnChunk
	
	If OBJ > -1 Then
		ch\Amount = GetINIInt("Data\1499chunks.ini", "chunk" + OBJ, "count")
		For chp.ChunkPart = Each ChunkPart
			If chp\ID = OBJ
				For i = 0 To ch\Amount
					ch\OBJ[i] = CopyEntity(chp\OBJ[i], ch\ChunkPivot)
				Next
			EndIf
		Next
	EndIf
	
	ch\PlatForm = CopyEntity(PlayerRoom\Objects[0], ch\ChunkPivot)
	EntityType(ch\PlatForm, HIT_MAP)
	EntityPickMode(ch\PlatForm, 2)
	
	Return(ch)
End Function

Const ChunkMaxDistance# = 120.0

Function UpdateChunks%(r.Rooms, ChunkPartAmount%, SpawnNPCs% = True)
	Local ch.Chunk, ch2.Chunk, n.NPCs
	Local StrTemp$, i%, j%, x#, z#, y#
	Local ChunkX#, ChunkZ#
	
	ChunkX = Int(EntityX(me\Collider) / 40.0)
	ChunkZ = Int(EntityZ(me\Collider) / 40.0)
	
	y = EntityY(PlayerRoom\OBJ)
	x = (-ChunkMaxDistance) + (ChunkX * 40.0)
	z = (-ChunkMaxDistance) + (ChunkZ * 40.0)
	
	Local CurrChunkData% = 0, MaxChunks% = GetINIInt("Data\1499chunks.ini", "general", "count")
	
	Repeat
		Local ChunkFound% = False
		
		For ch.Chunk = Each Chunk
			If ch\x = x And ch\z = z Then
				ChunkFound = True
				Exit
			EndIf
		Next
		If (Not ChunkFound) Then
			CurrChunkData = CHUNKDATA[Abs(((x + 32) / 40) Mod 64) + Abs((((z + 32) / 40) Mod 64) * 64)]
			ch2.Chunk = CreateChunk(CurrChunkData, x, y, z)
			ch2\IsSpawnChunk = False
		EndIf
		x = x + 40.0
		If x > ChunkMaxDistance + (ChunkX * 40.0)
			z = z + 40.0
			x = (-ChunkMaxDistance) + (ChunkX * 40.0)
		EndIf
	Until z > ChunkMaxDistance + (ChunkZ * 40.0)
	
	For ch.Chunk = Each Chunk
		If (Not ch\IsSpawnChunk) Then
			If DistanceSquared(EntityX(me\Collider), EntityX(ch\ChunkPivot), EntityZ(me\Collider), EntityZ(ch\ChunkPivot)) > PowTwo(ChunkMaxDistance)
				FreeEntity(ch\ChunkPivot) : ch\ChunkPivot = 0
				Delete(ch)
			EndIf
		EndIf
	Next
	
	Local CurrNPCNumber% = 0
	
	For n.NPCs = Each NPCs
		If n\NPCType = NPCType1499_1 Then CurrNPCNumber = CurrNPCNumber + 1
	Next
	
	Local MaxNPCs% = 64 ; ~ The maximum amount of NPCs in dimension_1499
	Local e.Events
	
	For e.Events = Each Events
		If e\room = PlayerRoom Then
			If e\room\NPC[0] <> Null Then
				MaxNPCs = 16
				Exit
			EndIf
		EndIf
	Next
	
	If CurrNPCNumber < MaxNPCs Then
		Select Rand(1, 8)
			Case 1
				;[Block]
				n.NPCs = CreateNPC(NPCType1499_1, EntityX(me\Collider) + Rnd(40.0, 80.0), EntityY(PlayerRoom\OBJ) + 0.5, EntityZ(me\Collider) + Rnd(40.0, 80.0))
				;[End Block]
			Case 2
				;[Block]
				n.NPCs = CreateNPC(NPCType1499_1, EntityX(me\Collider) + Rnd(40.0, 80.0), EntityY(PlayerRoom\OBJ) + 0.5, EntityZ(me\Collider) + Rnd(-40.0, 40.0))
				;[End Block]
			Case 3
				;[Block]
				n.NPCs = CreateNPC(NPCType1499_1, EntityX(me\Collider) + Rnd(40.0, 80.0), EntityY(PlayerRoom\OBJ) + 0.5, EntityZ(me\Collider) + Rnd(-40.0, -80.0))
				;[End Block]
			Case 4
				;[Block]
				n.NPCs = CreateNPC(NPCType1499_1, EntityX(me\Collider) + Rnd(-40.0, 40.0), EntityY(PlayerRoom\OBJ) + 0.5, EntityZ(me\Collider) + Rnd(-40.0, -80.0))
				;[End Block]
			Case 5
				;[Block]
				n.NPCs = CreateNPC(NPCType1499_1, EntityX(me\Collider) + Rnd(-40.0, -80.0), EntityY(PlayerRoom\OBJ) + 0.5, EntityZ(me\Collider) + Rnd(-40.0, -80.0))
				;[End Block]
			Case 6
				;[Block]
				n.NPCs = CreateNPC(NPCType1499_1, EntityX(me\Collider) + Rnd(-40.0, -80.0), EntityY(PlayerRoom\OBJ) + 0.5, EntityZ(me\Collider) + Rnd(-40.0, 40.0))
				;[End Block]
			Case 7
				;[Block]
				n.NPCs = CreateNPC(NPCType1499_1, EntityX(me\Collider) + Rnd(-40.0, -80.0), EntityY(PlayerRoom\OBJ) + 0.5, EntityZ(me\Collider) + Rnd(40.0, 80.0))
				;[End Block]
			Case 8
				;[Block]
				n.NPCs = CreateNPC(NPCType1499_1, EntityX(me\Collider) + Rnd(-40.0, 40.0), EntityY(PlayerRoom\OBJ) + 0.5, EntityZ(me\Collider) + Rnd(40.0, 80.0))
				;[End Block]
		End Select
		If Rand(2) = 1 Then n\State2 = 500.0 * 3.0
		n\Angle = Rnd(360.0)
	Else
		For n.NPCs = Each NPCs
			If n\NPCType = NPCType1499_1 Then
				If n\PrevState = 0 Then
					If EntityDistanceSquared(n\Collider, me\Collider) > PowTwo(ChunkMaxDistance) Lor EntityY(n\Collider) < EntityY(PlayerRoom\OBJ) - 5.0 Then
						; ~ This will be updated like this so that new NPCs can spawn for the player
						RemoveNPC(n)
					EndIf
				EndIf
			EndIf
		Next
	EndIf
	
End Function

Function HideChunks%()
	Local ch.Chunk, i%
	
	For ch.Chunk = Each Chunk
		If (Not ch\IsSpawnChunk) Then
			For i = 0 To ch\Amount
				FreeEntity(ch\OBJ[i]) : ch\OBJ[i] = 0
			Next
			FreeEntity(ch\PlatForm) : ch\PlatForm = 0
			FreeEntity(ch\ChunkPivot) : ch\ChunkPivot = 0
			Delete(ch)
		EndIf
	Next
End Function

Function DeleteChunks%()
	Delete Each Chunk
	Delete Each ChunkPart
End Function

;~IDEal Editor Parameters:
;~C#Blitz3D