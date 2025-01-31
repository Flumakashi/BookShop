import { useState } from "react";
import { useNavigate } from "react-router-dom";

const CreateBookPage = () => {
    const [title, setTitle] = useState("");
    const [author, setAuthor] = useState("");
    const [isbn, setIsbn] = useState("");
    const [genre, setGenre] = useState("");
    const [price, setPrice] = useState("");
    const [description, setDescription] = useState("");
    const [photoPath, setPhotoPath] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        const bookData = { title, author, isbn, genre, price, description, photoPath };

        try {
            const response = await fetch("http://localhost:8080/api/books", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                body: JSON.stringify(bookData),
            });

            if (response.ok) {
                navigate("/");
            } else {
                console.error("Ошибка при создании книги");
            }
        } catch (error) {
            console.error("Ошибка сети", error);
        }
    };

    return (
        <div>
            <h2>Добавить книгу</h2>
            <form onSubmit={handleSubmit}>
                <input type="text" placeholder="Название" value={title} onChange={(e) => setTitle(e.target.value)} required />
                <input type="text" placeholder="Автор" value={author} onChange={(e) => setAuthor(e.target.value)} required />
                <input type="text" placeholder="ISBN" value={isbn} onChange={(e) => setIsbn(e.target.value)} required />
                <input type="text" placeholder="Жанр" value={genre} onChange={(e) => setGenre(e.target.value)} required />
                <input type="number" placeholder="Цена" value={price} onChange={(e) => setPrice(e.target.value)} required />
                <textarea placeholder="Описание" value={description} onChange={(e) => setDescription(e.target.value)} required />
                <input type="text" placeholder="Фото (URL)" value={photoPath} onChange={(e) => setPhotoPath(e.target.value)} />
                <button type="submit">Добавить</button>
            </form>
        </div>
    );
};

export default CreateBookPage;