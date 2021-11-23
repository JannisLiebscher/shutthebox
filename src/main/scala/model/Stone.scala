package model

enum Stone(value: Int, Name: String):
  def getValue: Int = value
  def getName: String = Name

  case EMPTY extends Stone(0, "#")
  case ONE extends Stone(1, "1")
  case TWO extends Stone(2, "2")
  case THREE extends Stone(3, "3")
  case FOUR extends Stone(4, "4")
  case FIVE extends Stone(5, "5")
  case SIX extends Stone(6, "6")
  case SEVEN extends Stone(7, "7")
  case EIGHT extends Stone(8, "8")
  case NINE extends Stone(9, "9")
