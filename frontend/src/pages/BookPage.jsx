import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const BookPage = () => {
    const { id } = useParams(); // Получаем ID книги из URL
    const [book, setBook] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8080/api/books/${id}`)
            .then(response => response.json())
            .then(data => setBook(data))
            .catch(error => console.error("Ошибка загрузки книги:", error));
    }, [id]);

    if (!book) return <p>Загрузка...</p>;

    return (
        <div>
            <h1>{book.title}</h1>
            <p><strong>Автор:</strong> {book.author}</p>
            <p><strong>Жанр:</strong> {book.genre}</p>
            <p><strong>Описание:</strong> {book.description}</p>
            <p><strong>Цена:</strong> {book.price} ₽</p>
            {book.photoPath && <img src={book.photoPath} alt={book.title} width="200" />}
        </div>
    );
};

export default BookPage;