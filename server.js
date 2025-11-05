// ============================
// Importación de módulos
// ============================
const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");

// ============================
// Configuración del servidor
// ============================
const app = express();
const PORT = 3000; // Puerto donde se ejecutará el servidor

// ============================
// Middlewares
// ============================
app.use(cors());
app.use(bodyParser.json());

// ============================
// Ruta principal (verificación del servidor)
// ============================
app.get("/", (req, res) => {
  res.send("✅ API ComposeAPI Backend funcionando correctamente");
});

// ============================
// Ruta de ejemplo para usuarios
// ============================
// Ejemplo de base de datos temporal (en memoria)
let users = [];

// Obtener todos los usuarios
app.get("/users", (req, res) => {
  res.json(users);
});

// Agregar un nuevo usuario
app.post("/users", (req, res) => {
  const { name, email } = req.body;
  if (!name || !email) {
    return res.status(400).json({ error: "Faltan campos obligatorios" });
  }

  const newUser = {
    id: users.length + 1,
    name,
    email,
    createdAt: new Date(),
  };

  users.push(newUser);
  console.log("🟢 Usuario agregado:", newUser);
  res.status(201).json(newUser);
});

// ============================
// Iniciar el servidor
// ============================
app.listen(PORT, () => {
  console.log(`🚀 API ComposeAPI Backend corriendo en http://localhost:${PORT}`);
  console.log(`📮 Endpoint POST de prueba: http://localhost:${PORT}/users`);
});
