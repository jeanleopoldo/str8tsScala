object main
{
    def is_static(grid: Array[Array[Int]], row: Int, col: Int) : Boolean = grid(row)(col) == -1

    def cell_has_been_assigned(grid : Array[Array[Int]], row: Int, col: Int) : Boolean = grid(row)(col) > 0

    def has_number_in_col(grid : Array[Array[Int]], col: Int, number: Int) : Boolean = false

    def is_sequential_line(grid : Array[Array[Int]], col: Int, row: Int) : Boolean = false

    def has_number_in_row(grid : Array[Array[Int]], row: Int, number: Int) : Boolean =
    {
        val row_elements = grid(row)
        val size : Int = row_elements.size

        for(index <- 0 to (size-1))
        {
            if(row_elements(index) == number) return true  
        }

        return false
    }

    def can_assign_number(grid : Array[Array[Int]], row: Int, col: Int, number : Int) : Boolean = 
    {
        return !cell_has_been_assigned(grid, row, col) &&
               !has_number_in_row(grid, row, number) &&
               !has_number_in_col(grid, col, number) &&
               !is_static(grid, row, col) &&
               !is_sequential_line(grid, row, col)
    }

    def all_numbers_have_been_tested(tested: Array[Array[Array[Int]]]) : Boolean = 
    {
        val size = tested.size

        for ( row <- 0 to (size-1))
        {
            for(col <- 0 to (size-1))
            {
                if(tested(row)(col).size != size)
                {
                    return false //TODO fix this method
                }
            }
        }

        return true
    }

    def solve (grid: Array[Array[Int]], nextRow: Int, nextCol : Int, next : Int, tested: Array[Array[Array[Int]]]) : Boolean =
    {
            val size : Int = grid.size
            val tested_size : Int = tested.size

            if(nextRow == size-1 && nextCol == size) return true

            if(nextRow == -1 || tested_size == 0) return false

            if(nextRow == size && nextCol == 0) return true

            if(nextCol == -1) return solve(grid, nextRow-1, nextCol-1, next, tested)


            val number = if (size+1 == next) 1 else next
            val col    = if(nextCol == size) 0 else nextCol
            val row    = if(nextCol == size) nextRow+1 else nextRow

            if(is_static(grid, row, col) || cell_has_been_assigned(grid, row, col)) 
                return solve(grid, row, (col+1), number, tested)


            if(can_assign_number(grid, row, col, number))
            {
                grid(row)(col) = number
                return solve(grid, row, (col+1), (number+1), tested)
            }
            return true
    }


    
    def create_grid(size : Int) : Array[Array[Int]] =
    {
        val grid = Array.ofDim[Int](size, size)
        
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

        return grid
    }
    def main(args: Array[String]) : Unit =
    {
        val size : Int = 4
        var grid = create_grid(size)
        val not_tested = Array.ofDim[Int](size, size, size)

        val r = has_number_in_row(grid, 0, 0)

        println(r)
        // val row : Int = 0
        // val col : Int = 0
        // val number : Int = 1
        // if(solve(grid, row, col, number, not_tested)) //default row=0 col=0 number=1
        //     println(grid)
        // else
        //     println("could not solve :(")
    }
}