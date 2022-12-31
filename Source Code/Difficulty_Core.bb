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

Global difficulties.Difficulty[6]

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
difficulties[SAFE]\Name = "Safe"
difficulties[SAFE]\Description = "The game can be saved any time. However, as in the case of SCP Objects, a Safe classification doesn't mean that handling it doesn't pose a threat."
difficulties[SAFE]\AggressiveNPCs = NOT_AGGRESSIVE
difficulties[SAFE]\InventorySlots = 10
difficulties[SAFE]\SaveType = SAVE_ANYWHERE
difficulties[SAFE]\OtherFactors = EASY
SetDifficultyColor(SAFE, 90, 170, 25)

difficulties[MILD] = New Difficulty
difficulties[MILD]\Name = "Mild"
difficulties[MILD]\Description = "In Mild difficulty, saving is only allowed at specific locations marked by lit up computer screens. "
difficulties[MILD]\Description = difficulties[MILD]\Description + "Mild-class objects can be consistently contained as long as containment procedures are carried out, but in the failure of protocol the object may become unpredictable."
difficulties[MILD]\AggressiveNPCs = NOT_AGGRESSIVE
difficulties[MILD]\InventorySlots = 8
difficulties[MILD]\SaveType = SAVE_ON_SCREENS
difficulties[MILD]\OtherFactors = NORMAL
SetDifficultyColor(MILD, 25, 90, 170)

difficulties[EUCLID] = New Difficulty
difficulties[EUCLID]\Name = "Euclid"
difficulties[EUCLID]\Description = "In Euclid difficulty, saving is only allowed at specific locations marked by lit up computer screens. "
difficulties[EUCLID]\Description = difficulties[EUCLID]\Description + "Euclid-class objects are inherently unpredictable, so that reliable containment is not always possible. Enemies will spawn frequently."
difficulties[EUCLID]\AggressiveNPCs = AGGRESSIVE
difficulties[EUCLID]\InventorySlots = 6
difficulties[EUCLID]\SaveType = SAVE_ON_SCREENS
difficulties[EUCLID]\OtherFactors = HARD
SetDifficultyColor(EUCLID, 200, 200, 25)

difficulties[KETER] = New Difficulty
difficulties[KETER]\Name = "Keter"
difficulties[KETER]\Description = "Keter-class objects are considered the most dangerous ones in Foundation containment. "
difficulties[KETER]\Description = difficulties[KETER]\Description + "The same can be said for this difficulty level: the SCPs are more aggressive, and you have only one life - when you die, the game is over."
difficulties[KETER]\AggressiveNPCs = VERY_AGGRESSIVE
difficulties[KETER]\InventorySlots = 4
difficulties[KETER]\SaveType = DELETE_ON_DEATH
difficulties[KETER]\OtherFactors = HARDER
SetDifficultyColor(KETER, 170, 90, 25)

difficulties[APOLLYON] = New Difficulty
difficulties[APOLLYON]\Name = "Apollyon"
difficulties[APOLLYON]\Description = "Apollyon-class object is either completely impossible to contain or about to irrevocably breach containment, resulting in unimaginable consequences. "
difficulties[APOLLYON]\Description = difficulties[APOLLYON]\Description + "God help the humble subject attempting this difficulty."
difficulties[APOLLYON]\AggressiveNPCs = VERY_AGGRESSIVE
difficulties[APOLLYON]\InventorySlots = 2
difficulties[APOLLYON]\SaveType = NO_SAVES
difficulties[APOLLYON]\OtherFactors = EXTREME
SetDifficultyColor(APOLLYON, 200, 25, 25)

difficulties[ESOTERIC] = New Difficulty
difficulties[ESOTERIC]\Name = "Esoteric"
difficulties[ESOTERIC]\AggressiveNPCs = 0
difficulties[ESOTERIC]\InventorySlots = 10
difficulties[ESOTERIC]\Customizable = True
difficulties[ESOTERIC]\SaveType = SAVE_ANYWHERE
difficulties[ESOTERIC]\OtherFactors = EASY
SetDifficultyColor(ESOTERIC, 200, 50, 200)

SelectedDifficulty = difficulties[MILD]

;~IDEal Editor Parameters:
;~C#Blitz3D