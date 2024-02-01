package algebra.generic

class AlgebraFactory<T>(
    var plus:  (T, T) -> T,
    var times: (T, T) -> T){

    fun makeVector(list: List<T>): Vector<T> {
        return Vector(list, plus, times)
    }

    fun makeMatrix(lists: List<List<T>>): Matrix<T> {
        return Matrix(lists
            .map{ vec -> Vector(vec, plus , times )}
            , plus , times )
    }
}