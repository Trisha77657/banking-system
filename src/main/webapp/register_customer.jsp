<!DOCTYPE html>
<html>
<head>
    <title>Register Customer</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            text-align: center;
            background-color: #fff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 600px;
        }
        h2 {
            color: #333;
            margin-bottom: 20px;
        }
        form {
            margin-bottom: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        label {
            display: block;
            margin: 10px 0 5px;
            text-align: left;
            width: 100%;
            max-width: 300px;
        }
        input[type="text"],
        input[type="email"],
        input[type="number"],
        input[type="date"],
        select {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Register Customer</h2>
        <form action="admin" method="post">
            <input type="hidden" name="action" value="registerCustomer">
            <label>Full Name:</label>
            <input type="text" name="fullName" required>
            <label>Address:</label>
            <input type="text" name="address" required>
            <label>Mobile No:</label>
            <input type="text" name="mobileNo" required>
            <label>Email ID:</label>
            <input type="email" name="emailId" required>
            <label>Type of Account:</label>
            <select name="accountType">
                <option value="Saving">Saving</option>
                <option value="Current">Current</option>
            </select>
            <label>Initial Balance:</label>
            <input type="number" name="initialBalance" min="1000" required>
            <label>Date of Birth:</label>
            <input type="date" name="dateOfBirth" required>
            <label>ID Proof:</label>
            <input type="text" name="idProof" required>
            <button type="submit">Register</button>
        </form>
    </div>
</body>
</html>
