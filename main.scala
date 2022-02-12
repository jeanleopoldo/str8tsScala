object main
{
    def main(args: Array[String]) : Unit =
    {

        val row : Int = 4
        val col : Int = 4

        val grid = Array.ofDim[Int](row, col)
        grid(0)(0) = -1
        grid(0)(1) = 0
        grid(0)(2) = 0
        grid(0)(3) = -1
        grid(1)(0) = -1
        grid(1)(1) = 0
        grid(1)(2) = 1
        grid(1)(3) = 0
        grid(2)(0) = -1
        grid(2)(1) = 0
        grid(2)(2) = 1
        grid(2)(3) = 0
        grid(3)(0) = 1
        grid(3)(1) = 0
        grid(3)(1) = 0
        grid(3)(3) = 0

        solve(grid, 0, 4, 1)
    }

   def solve (grid: Array[Array[Int]], nextRow: Int, nextCol : Int, next : Int) : Boolean =
   {
        val size : Int = grid.size

        if (nextRow == size-1 && nextCol == size)
        {
            return true
        }

        val number = if (size+1 == next) 1 else next
        val col = if(nextCol == size) 0 else nextCol
        val row = if(nextCol == size) nextRow+1 else nextRow

        if(is_static(grid, row, col) || cell_has_been_assigned(grid, row, col)) 
            return solve(grid, row, (col+1), number)


        if(can_assign_number(grid, row, col, number))
        {
            grid(row)(col) = number
            return solve(grid, row, (col+1), (number+1))
        }
        return true
   }


   def is_static(grid: Array[Array[Int]], row: Int, col: Int) : Boolean = grid(row)(col) == -1

   def cell_has_been_assigned(grid: Array[Array[Int]], row: Int, col: Int) : Boolean = grid(row)(col) > 0
}