const express = require('express');
const mysql = require('mysql2');
const cors = require('cors');

const db = mysql.createPool({
    connectionLimit: 10,
    host: '127.0.0.1',
    user: 'root',
    password: 'Macwhammy5',
    database: 'books',
    port: 3306
});

const app = express();
app.use(express.json());
app.use(cors());

// //???
// app.get("/", (req, res) => {
//     res.json("This is the backend");
// });

app.get("/books", (req, res) => {
    const q = "SELECT * FROM bookss";
    db.query(q, (err, data) => {
        if (err) {
            console.log("Error:", err);
            return res.json(err);
        }
        return res.json(data);
    });
});

app.post("/books", (req, res) => {
    const q = "INSERT INTO bookss (title, description, price, cover) VALUES (?, ?, ?, ?)";
    const values = [req.body.title, req.body.description, req.body.price, req.body.cover];

    db.query(q, values, (err, data) => {
        if (err) {
            console.log("Error:", err);
            return res.json(err);
        }
        return res.json(data);
    });
});

app.delete("/books/:id", (req, res) => {
    const bookId = req.params.id
    const q = "DELETE FROM bookss WHERE id = ?"

    db.query(q, [bookId], (err, data) => {
        if (err) return res.json(err)
        return res.json("Book has been deleted successfully")
    })
})

app.put("/books/:id", (req, res) => {
    const bookId = req.params.id
    const q = "UPDATE bookss SET title = ?, description = ?, price = ?, cover = ? WHERE id = ?"
    const values = [req.body.title, req.body.description, req.body.price, req.body.cover];

    db.query(q, [...values, bookId], (err, data) => {
        if (err) return res.json(err)
        return res.json("Book has been updated successfully")
    })
})

app.listen(4000, () => {
    console.log("Server is listening on port 4000");
});