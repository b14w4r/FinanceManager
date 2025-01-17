```css
src/
 └── main/
     ├── java/
     │   └── org.example.b14w4r/
     │       └── FinanceManager.java
		     └── wallet/
			     └── Wallet.java
		     └── transaction/
			     └── Transaction.java
     └── resources/
         ├── application.properties
         └── static/
         └── templates/

```


# Описание функционала
## Поле входа/регистрации
```
1. Login
2. Register
3. Exit
Enter your choice:
```
## Регистрация
Имеет проверку на юникальность логина:
```
Enter username: ]
[Username already exists. Try another.
```
## Личный кабинет
```
Enter username: 14s]
 [2m Enter password:
Login successful.
1. Add Income
2. Add Expense
3. Set Budget
4. View Summary
5. Logout
Enter your choice:
```
## Добавление данных:
При добавлении доходов, расходов, установке бюджета вводится категория и сумма:
```
Enter income description:
[3m 33s] Enter amount:
[3m 38s] Income added successfully.
```
При добавлении расходов, превосходящих установленный бюджет на категорию пользователю приходит предупреждени:
```
Enter expense description:
[6m 16s] Enter amount:
[6m 23s] Warning: Budget exceeded for category 'food'. Limit: 1000.00, Current Expenses: 1102.00
Expense added successfully.
```