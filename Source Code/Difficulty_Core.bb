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

Global difficulties.Difficulty[7]

Global SelectedDifficulty.Difficulty

; ~ Difficulties ID Constants
;[Block]
Const SAFE% = 0
Const MILD% = 1
Const EUCLID% = 2
Const KETER% = 3
Const APOLLYON% = 4
Const THAUMIEL% = 5
Const ESOTERIC% = 6
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
Const CASUAL% = 0
Const EASY% = 1
Const NORMAL% = 2
Const HARD% = 3
Const EXTREME% = 4
Const CAKE% = 5
;[End Block]

Function SetDifficultyColor%(ID%, R%, G%, B%)
	difficulties[ID]\R = R
	difficulties[ID]\G = G
	difficulties[ID]\B = B
End Function

difficulties[SAFE] = New Difficulty
difficulties[SAFE]\Name = "Safe"
difficulties[SAFE]\Description = "The game can be saved any time. However, as in the case of SCP Objects, a Safe classification doesn't mean that handling it doesn't pose a threat."
difficulties[SAFE]\AggressiveNPCs = 0
difficulties[SAFE]\InventorySlots = 10
difficulties[SAFE]\SaveType = SAVE_ANYWHERE
difficulties[SAFE]\OtherFactors = CASUAL
SetDifficultyColor(SAFE, 90, 160, 25)

difficulties[MILD] = New Difficulty
difficulties[MILD]\Name = "Mild"
difficulties[MILD]\Description = "In Mild difficulty, saving is only allowed at specific locations marked by lit up computer screens. "
difficulties[MILD]\Description = difficulties[MILD]\Description + "Mild-class objects can be consistently contained as long as containment procedures are carried out, but in the failure of protocol the object may become unpredictable."
difficulties[MILD]\AggressiveNPCs = 0
difficulties[MILD]\InventorySlots = 8
difficulties[MILD]\SaveType = SAVE_ON_SCREENS
difficulties[MILD]\OtherFactors = EASY
SetDifficultyColor(MILD, 130, 135, 25)

difficulties[EUCLID] = New Difficulty
difficulties[EUCLID]\Name = "Euclid"
difficulties[EUCLID]\Description = "In Euclid difficulty, saving is only allowed at specific locations marked by lit up computer screens. "
difficulties[EUCLID]\Description = difficulties[EUCLID]\Description + "Euclid-class objects are inherently unpredictable, so that reliable containment is not always possible. Enemies will spawn frequently."
difficulties[EUCLID]\AggressiveNPCs = 1
difficulties[EUCLID]\InventorySlots = 6
difficulties[EUCLID]\SaveType = SAVE_ON_SCREENS
difficulties[EUCLID]\OtherFactors = NORMAL
SetDifficultyColor(EUCLID, 200, 200, 25)

difficulties[KETER] = New Difficulty
difficulties[KETER]\Name = "Keter"
difficulties[KETER]\Description = "Keter-class objects are considered the most dangerous ones in Foundation containment. "
difficulties[KETER]\Description = difficulties[KETER]\Description + "The same can be said for this difficulty level: the SCPs are more aggressive, and you have only one life - when you die, the game is over."
difficulties[KETER]\AggressiveNPCs = 2
difficulties[KETER]\InventorySlots = 4
difficulties[KETER]\SaveType = DELETE_ON_DEATH
difficulties[KETER]\OtherFactors = HARD
SetDifficultyColor(KETER, 200, 25, 25)

difficulties[APOLLYON] = New Difficulty
difficulties[APOLLYON]\Name = "Apollyon"
difficulties[APOLLYON]\Description = "Apollyon-class object is either completely impossible to contain or about to irrevocably breach containment, resulting in unimaginable consequences. "
difficulties[APOLLYON]\Description = difficulties[APOLLYON]\Description + "God help the humble subject attempting this difficulty."
difficulties[APOLLYON]\AggressiveNPCs = 2
difficulties[APOLLYON]\InventorySlots = 2
difficulties[APOLLYON]\SaveType = NO_SAVES
difficulties[APOLLYON]\OtherFactors = EXTREME
SetDifficultyColor(APOLLYON, 150, 150, 150)

difficulties[THAUMIEL] = New Difficulty
difficulties[THAUMIEL]\Name = "Thaumiel"
difficulties[THAUMIEL]\Description = "Saving is allowed anytime. Thaumiel-class SCPs are anomalies that the Foundation uses to contain or counteract other SCPs or anomalous phenomena."
difficulties[THAUMIEL]\Description = difficulties[THAUMIEL]\Description + " This difficulty is designed around allowing you to lay back and have a good time without having to worry about constantly dying. Achievements are disabled when playing on this difficulty."
difficulties[THAUMIEL]\AggressiveNPCs = 0
difficulties[THAUMIEL]\InventorySlots = 16
difficulties[THAUMIEL]\SaveType = SAVE_ANYWHERE
difficulties[THAUMIEL]\OtherFactors = CAKE
SetDifficultyColor(THAUMIEL, 50, 65, 205)

difficulties[ESOTERIC] = New Difficulty
difficulties[ESOTERIC]\Name = "Esoteric"
difficulties[ESOTERIC]\AggressiveNPCs = 0
difficulties[ESOTERIC]\InventorySlots = 10
difficulties[ESOTERIC]\Customizable = True
difficulties[ESOTERIC]\SaveType = SAVE_ANYWHERE
difficulties[ESOTERIC]\OtherFactors = CASUAL
SetDifficultyColor(ESOTERIC, 200, 50, 200)

SelectedDifficulty = difficulties[MILD]

;~IDEal Editor Parameters:
;~C#Blitz3D