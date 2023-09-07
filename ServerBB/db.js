const mysql = require("mysql")

const connection = mysql.createConnection({
    user:"root",
    password:"manager",
    host:"localhost",
    database:"bigbasket_db",
    port:3306
})

connection.connect()

module.exports = connection