import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quanlythuvien2.Model.Book
import com.example.quanlythuvien2.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {
    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> get() = _books

    init {
        fetchBooks()
    }

    private fun fetchBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            // Giả lập tải dữ liệu từ API hoặc cơ sở dữ liệu
            val bookList = loadBooksFromDatabase()
            _books.postValue(bookList) // Cập nhật LiveData trên Main Thread
        }
    }

    private fun loadBooksFromDatabase(): List<Book> {
        // Thực hiện logic tải sách ở đây
        return listOf(
            Book(R.drawable.hs_1, "Đắc Nhân Tâm", "Nguyễn Văn A", 2019, "Tác giả phụ A", "Đây là cuốn sách nói về nghệ thuật giao tiếp."),
            Book(R.drawable.hs_2, "Sách 2", "Tác giả B", 2020, "Tác giả phụ B", "Mô tả cho sách 2."),
            Book(R.drawable.hs_3, "Sách 3", "Tác giả C", 2021, "Tác giả phụ C", "Mô tả cho sách 3.")
        )
    }
}


