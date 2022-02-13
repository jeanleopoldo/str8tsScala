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

    def is_sequential_col(grid : Array[Array[Int]], row: Int, col: Int) : Boolean =
    {
        val size = grid.size
        if (row != (size-1)) return true

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

    def is_sequential_row(grid : Array[Array[Int]], row: Int, col: Int) : Boolean =
    {
        val size = grid.size
        val row_elements = get_row_elements(grid, row)

        if (col != (size-1)) return true

        for(index <- 0 to (size-2))
        {
            val current_row_element = if (row_elements(index) >= 10) (row_elements(index)/10) else row_elements(index)
            val next_row_element = if (row_elements(index+1) >= 10) (row_elements(index+1)/10) else row_elements(index+1)

            val op = (current_row_element - next_row_element).abs
            if(((current_row_element-next_row_element).abs) > 1) return false

            if(row_elements(index+1) == -1) return true
        }

        return true //TODO gotta consider stopping at -1
    }

    def is_sequential_row_and_col(grid : Array[Array[Int]], row: Int, col: Int) : Boolean =
        is_sequential_row(grid, row, col) && is_sequential_col(grid, row, col)

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
               is_sequential_row_and_col(grid, row, col)
    }

    def all_numbers_have_been_tested(grid: Array[Array[Array[Int]]]) : Boolean = 
    {
        val size = grid.size
        
        for(number <- 0 to (size-1))
        {
            if(grid(0)(0)(number) != -1) return false
        }
        
        return true
    }

    def get_not_tested_number(grid: Array[Array[Array[Int]]], row : Int, col : Int) : Int =
    {
        val size = grid.size
        var not_tested = grid(row)(col)

        for(t <- 0 to (size-1))
        {
            if (not_tested(t) != -2)
            {
                val number = not_tested(t)
                not_tested(t) = -2
                return number
            }
        }

        return -2
    }

    def update_tested_numbers(tested_numbers: Array[Array[Array[Int]]], row: Int, col : Int) : Array[Array[Array[Int]]] = 
    {
        val size = tested_numbers.size
        for(number <- 0 to (size-1))
        {
            tested_numbers(row)(col)(number) = number+1
        }

        return tested_numbers
    }

    def finished_all_cell_possibilities(grid: Array[Array[Int]], r: Int, c : Int, tested_numbers: Array[Array[Array[Int]]]) : Boolean =
    {
        val size = grid.size
        val row = r
        val col = c

        if(col == -1) return finished_all_cell_possibilities(grid, (row-1), (size-1), tested_numbers)

        if(row == -1) return false

        update_tested_numbers(tested_numbers, row, col)
        return solve(grid, row, (col-1), tested_numbers)
    }

    def solve (grid: Array[Array[Int]], r: Int, c : Int, tested_numbers: Array[Array[Array[Int]]]) : Boolean =
    {
            val size : Int = grid.size

            var row = r
            var col = c
            if( ( (row == -1) || all_numbers_have_been_tested(tested_numbers) ))
            {
                println("first if")
                return false // se chegou em linha -1 ou todos os números foram testados, retorna falso
            }

            if(row == size && col == 0)
            {
                println("second if")
                return true // working
            }
            

            if(col == size)
            {
                println("third if")
                return solve(grid, (row+1), 0, tested_numbers)
            }

            if(col == -1)
            {
                println("fourth if")
                return solve(grid, (row-1), (size-1), tested_numbers)
            }

            if(is_static(grid, row, col) || cell_has_been_assigned(grid, row, col)) 
            {
                println("fifth if")
                return solve(grid, row, (col+1), tested_numbers)
            }
            
            val number = get_not_tested_number(tested_numbers, row, col)
            println(number)
            if (number == -2) return finished_all_cell_possibilities(grid, row, col, tested_numbers)

            val previous_number = grid(row)(col)

            
            tested_numbers(row)(col)(number) = -2
            if(can_assign_number(grid, row, col, number))
            {
                grid(row)(col) = number
                return solve(grid, row, (col+1), tested_numbers)
            }

            grid(row)(col) = previous_number
            return solve(grid, row, col-1, tested_numbers)
    }
    
    
    def main(args: Array[String]) : Unit =
    {
        val size : Int = 4
        var grid = create_grid(size)
        val ts   = create_not_tested_grid(size)
        ts(0)(0)(0) = 5
        val t = update_tested_numbers(ts, 0, 0)

        val row : Int = 0
        val col : Int = 0
        val number : Int = 1
        if(solve(grid, row, col, ts)) //default row=0 col=0 number=1
            print_grid(grid)
        else
            println("could not solve :(")
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
        grid(1)(2) = 10
        grid(1)(3) = 0
        grid(2)(0) = -1
        grid(2)(1) = 0
        grid(2)(2) = 20
        grid(2)(3) = 0
        grid(3)(0) = -1
        grid(3)(1) = -1
        grid(3)(2) = 0
        grid(3)(3) = 0

        return grid
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
                    grid(row)(col)(n) = (n+1)
                }
            }
        }
        return grid
    }

    def print_not_tested(grid: Array[Array[Array[Int]]]) : Boolean =
    {
        val size = grid.size
        for(row <- 0 to (size-1))
        {
            for(col <- 0 to (size-1))
            {
                for(n <- 0 to (size-1))
                {
                    println(grid(row)(col)(n))
                }
            }
        }

        return false
    }

    def print_grid(grid : Array[Array[Int]]) : Boolean =
    {
        val size = grid.size
        for(row <- 0 to (size-1))
        {
            var row_str = "["
            for(col <- 0 to (size-1))
            {
                var t = grid(row)(col)
                row_str = row_str+t.toString
            }
            println(row_str+"]\n")
        }

        return false
    }

}