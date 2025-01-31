import axios from "axios";

const API_URL = "http://localhost:8080/api";

// Создаем экземпляр axios
const api = axios.create({
    baseURL: API_URL,
    headers: { "Content-Type": "application/json" },
});

// Интерцептор для автоматического добавления токена
api.interceptors.request.use((config) => {
    const token = localStorage.getItem("token"); // Берем токен из localStorage
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

// Функция регистрации пользователя
export const registerUser = async (userData) => {
    try {
        const response = await api.post("/reg/register", userData);
        return response.data;
    } catch (error) {
        throw error.response ? error.response.data : new Error("Ошибка сети");
    }
};

// Функция входа в систему
export const loginUser = async (userData) => {
    try {
        const response = await api.post("/auth/login", userData);

        console.log("Ответ сервера:", response.data); // Отладка

        const token = response.data.token; //  Теперь сервер уже отправляет чистый токен

        if (token) {
            localStorage.setItem("token", token); //  Сохраняем токен без изменений
        } else {
            console.error("Токен отсутствует в ответе сервера!");
        }

        return response.data;
    } catch (error) {
        console.error("Ошибка авторизации:", error.response?.data || error.message);
        throw error.response ? error.response.data : new Error("Ошибка сети");
    }
};

export default api;