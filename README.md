# RandomCoffeeApp

## Описание
RandomCoffeeApp - это Android-приложение, разработанное с использованием Jetpack Compose, которое позволяет пользователям просматривать и заказывать кофе. Приложение получает данные о товарах через API и предоставляет удобный интерфейс для выбора и оформления заказов.

## Основные функции
- Просмотр списка товаров с возможностью фильтрации по категориям
- Детальное описание каждого товара
- Добавление товаров в корзину
- Оформление заказа
![image](https://github.com/user-attachments/assets/d3c9eca8-1fc3-4689-a662-8d8e36d37508)

## Технологии
- Kotlin
- Jetpack Compose
- MVVM архитектура
- Retrofit для сетевых запросов
- OkHttp для HTTP-клиента
- Gson для сериализации/десериализации JSON
- Coil для загрузки изображений

## Требования
- Android Studio
- Android SDK 24+
- JDK 11

## Установка
1. Клонируйте репозиторий
2. Откройте проект в Android Studio
3. Дождитесь синхронизации Gradle
4. Запустите приложение на эмуляторе или реальном устройстве

## Структура проекта
- `app/src/main/java/com/example/randomcoffeeapp/` - основной пакет приложения
  - `network/` - сетевое взаимодействие
  - `ui/presenation/` - UI компоненты и экраны
  - `ui/presenation/models/` - ViewModel и состояния
  - `ui/presenation/screens/` - основные экраны приложения

## API
Приложение использует API кофейни:
- Базовый URL: `https://coffeeshop.academy.effective.band/api/`
- Endpoints:
  - GET `/v1/products` - получение списка товаров
  - POST `/v1/orders` - создание заказа
