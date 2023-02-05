Type Difficulty
	Field Name$
	Field Description$
	Field AggressiveNPCs%
	Field SaveType%
	Field OtherFactors%
	Field Customizable%
	Field R%, G%, B%
	Field InventorySlots%
End Type

Global difficulties.Difficulty[5]

Global SelectedDifficulty.Difficulty

; ~ Difficulties ID Constants
;[Block]
Const SAFE% = 0
Const MILD% = 1
Const EUCLID% = 2
Const KETER% = 3
Const APOLLYON% = 4
Const ESOTERIC% = 5
;[End Block]

; ~ Save Types ID Constants
;[Block]
Const SAVE_ANYWHERE% = 0
Const SAVE_ON_SCREENS% = 1
Const DELETE_ON_DEATH% = 2
Const NO_SAVES% = 3
;[End Block]

; ~ Other Factors ID Constants
;[Block]
Const EASY% = 0
Const NORMAL% = 1
Const HARD% = 2
Const HARDER% = 3
Const EXTREME% = 4
;[End Block]

; ~ AggressiveNPCs ID Constants
;[Block]
Const NOT_AGGRESSIVE% = 0
Const AGGRESSIVE% = 1
Const VERY_AGGRESSIVE% = 2
;[End Block]

Function SetDifficultyColor%(ID%, R%, G%, B%)
	difficulties[ID]\R = R
	difficulties[ID]\G = G
	difficulties[ID]\B = B
End Function

difficulties[SAFE] = New Difficulty
difficulties[SAFE]\Name = GetLocalString("menu", "new.safe")
difficulties[SAFE]\Description = GetLocalString("msg", "diff.safe")
difficulties[SAFE]\AggressiveNPCs = NOT_AGGRESSIVE
difficulties[SAFE]\InventorySlots = 10
difficulties[SAFE]\SaveType = SAVE_ANYWHERE
difficulties[SAFE]\OtherFactors = EASY
SetDifficultyColor(SAFE, 90, 170, 25)

difficulties[MILD] = New Difficulty
difficulties[MILD]\Name = GetLocalString("menu", "new.mild")
difficulties[MILD]\Description = GetLocalString("msg", "diff.mild")
difficulties[MILD]\AggressiveNPCs = NOT_AGGRESSIVE
difficulties[MILD]\InventorySlots = 8
difficulties[MILD]\SaveType = SAVE_ON_SCREENS
difficulties[MILD]\OtherFactors = NORMAL
SetDifficultyColor(MILD, 25, 90, 170)

difficulties[EUCLID] = New Difficulty
difficulties[EUCLID]\Name = GetLocalString("menu", "new.euclid")
difficulties[EUCLID]\Description = GetLocalString("msg", "diff.euclid")
difficulties[EUCLID]\AggressiveNPCs = AGGRESSIVE
difficulties[EUCLID]\InventorySlots = 6
difficulties[EUCLID]\SaveType = SAVE_ON_SCREENS
difficulties[EUCLID]\OtherFactors = HARD
SetDifficultyColor(EUCLID, 200, 200, 25)

difficulties[KETER] = New Difficulty
difficulties[KETER]\Name = GetLocalString("menu", "new.keter")
difficulties[KETER]\Description = GetLocalString("msg", "diff.keter")
difficulties[KETER]\AggressiveNPCs = VERY_AGGRESSIVE
difficulties[KETER]\InventorySlots = 4
difficulties[KETER]\SaveType = DELETE_ON_DEATH
difficulties[KETER]\OtherFactors = HARDER
SetDifficultyColor(KETER, 170, 90, 25)

difficulties[APOLLYON] = New Difficulty
difficulties[APOLLYON]\Name = GetLocalString("menu", "new.apollyon")
difficulties[APOLLYON]\Description = GetLocalString("msg", "diff.apollyon")
difficulties[APOLLYON]\AggressiveNPCs = VERY_AGGRESSIVE
difficulties[APOLLYON]\InventorySlots = 2
difficulties[APOLLYON]\SaveType = NO_SAVES
difficulties[APOLLYON]\OtherFactors = EXTREME
SetDifficultyColor(APOLLYON, 200, 25, 25)

difficulties[ESOTERIC] = New Difficulty
difficulties[ESOTERIC]\Name = GetLocalString("menu", "new.esoteric")
difficulties[ESOTERIC]\AggressiveNPCs = NOT_AGGRESSIVE
difficulties[ESOTERIC]\InventorySlots = 10
difficulties[ESOTERIC]\Customizable = True
difficulties[ESOTERIC]\SaveType = SAVE_ANYWHERE
difficulties[ESOTERIC]\OtherFactors = EASY
SetDifficultyColor(ESOTERIC, 200, 50, 200)

If opt\DebugMode Then
	SelectedDifficulty = difficulties[SAFE]
Else
	SelectedDifficulty = difficulties[EUCLID]
EndIf

;~IDEal Editor Parameters:
;~C#Blitz3D