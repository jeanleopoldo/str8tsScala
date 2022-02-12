import scala.collection.mutable.ListBuffer


object main
{
    def is_static(grid: Array[Array[Int]], row: Int, col: Int) : Boolean = grid(row)(col) == -1

    def cell_has_been_assigned(grid : Array[Array[Int]], row: Int, col: Int) : Boolean = grid(row)(col) > 0

    def has_number_in_col(grid : Array[Array[Int]], col: Int, number: Int) : Boolean =
    {
        val size : Int = grid.size
        val col_elements = new Array[Int](size)

        for(index <- 0 to (size-1))
        {
            if(grid(index)(col) == number) return true
        }

        return false
    }

    def get_row_elements(grid : Array[Array[Int]], row: Int) : Array[Int] =  grid(row)

    def get_col_elements(grid : Array[Array[Int]], col: Int) : Array[Int] =
    {
        val size = grid.size
        val col_elements = new Array[Int](size)

        for(index <- 0 to (size-1))
        {
            val element = grid(index)(col)
            col_elements(index) = element
        }

        return col_elements //TODO sort return of the list
    }

    def is_sequential_col(grid : Array[Array[Int]], col: Int) : Boolean =
    {
        val size = grid.size
        val col_elements = get_col_elements(grid, col)
        for(index <- 0 to (size-2))
        {
            val current_col_element = if (col_elements(index) >= 10) (col_elements(index)/10) else col_elements(index) //considering static numbers
            val next_col_element = if (col_elements(index+1) >= 10) (col_elements(index+1)/10) else col_elements(index+1) //considering static numbers
            
            if((current_col_element-next_col_element).abs > 1) return false
            if(col_elements(index+1) == -1) return true
        }

        return true
    }

    def is_sequential_row(grid : Array[Array[Int]], row: Int) : Boolean =
    {
        val size = grid.size
        val row_elements = get_row_elements(grid, row)

        for(index <- 0 to (size-2))
        {
            val current_row_element = if (row_elements(index) >= 10) (row_elements(index)/10) else row_elements(index)
            val next_row_element = if (row_elements(index+1) >= 10) (row_elements(index+1)/10) else row_elements(index+1)

            val op = (current_row_element - next_row_element).abs
            println(op)
            if(((current_row_element-next_row_element).abs) > 1) return false

            if(row_elements(index+1) == -1) return true
        }

        return true //TODO gotta consider stopping at -1
    }

    def is_sequential_row_and_col(grid : Array[Array[Int]], row: Int, col: Int) : Boolean =
        is_sequential_row(grid, row) && is_sequential_col(grid, col)

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
               !is_sequential_row_and_col(grid, row, col)
    }

    def all_numbers_have_been_tested(grid: Array[Array[Array[Int]]]) : Boolean = 
    {
        val size = grid.size

        for(row <- 0 to (size-1))
        {
            for(col <- 0 to (size-1))
            {
                for(number <- 0 to (size-1))
                {
                    if(grid(row)(col)(number) != -1) return false
                }
            }
        }
        
        return true
    }

    def get_not_tested_number(grid: Array[Array[Array[Int]]], row : Int, col : Int) : Int =
    {
        val size = grid.size

        var not_tested = grid(row)(col)

        for(t <- 0 to (size-1))
        {
            if (not_tested(t) != -1) return not_tested(t)
        }

        return -1
    }

    def solve (grid: Array[Array[Int]], r: Int, c : Int, tested_numbers: Array[Array[Array[Int]]]) : Boolean =
    {
            val size : Int = grid.size

            var row = r
            var col = c

            if(row == size-1 && col == size) return true

            if( ( (row == -1) || all_numbers_have_been_tested(tested_numbers) )) return false // se chegou em linha -1 ou todos os números foram testados, retorna falso

            if(row == size && col == 0) return true

            if(col == -1) return solve(grid, (row-1), (col-1), tested_numbers)


            if(is_static(grid, row, col) || cell_has_been_assigned(grid, row, col)) 
                return solve(grid, row, (col+1), tested_numbers)

            val previous_number = grid(row)(col)
            val number          = get_not_tested_number(tested_numbers, row, col)

            if(can_assign_number(grid, row, col, number))
            {
                grid(row)(col) = number
                tested_numbers(row)(col)(number) = -1
                return solve(grid, row, (col+1), tested_numbers)
            }

            // not_tested(row, col)
            grid(row)(col) = previous_number
            return solve(grid, row, col, tested_numbers)
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
        grid(3)(1) = -1
        grid(3)(2) = 0
        grid(3)(3) = 0

        return grid
    }
    def main(args: Array[String]) : Unit =
    {
        val size : Int = 4
        var grid = create_grid(size)
        val ts   = create_not_tested_grid(size)

        val row : Int = 0
        val col : Int = 0
        val number : Int = 1
        if(solve(grid, row, col, ts)) //default row=0 col=0 number=1
            println("yay")
        else
            println("could not solve :(")
    }
    
    def create_not_tested_grid(size : Int) : Array[Array[Array[Int]]] =
    {
        val grid = Array.ofDim[Int](size, size, size)
        
        for(row <- 0 to (size-1))
        {
            for(col <- 0 to (size-1))
            {
                for(n <- 0 to (size-1))
                {
                    grid(row)(col)(n) = n
                }
            }
        }
        val n = grid(2)(1)
        return grid
    }

}