# Guía del Proyecto — Tienda Virtual de Libros

Documento para todo el equipo. Aquí está explicado qué vamos a construir, cómo funciona por dentro, qué le toca a cada uno y cómo trabajamos juntos con Git y GitHub. Léanlo completo antes de escribir código.

---

## 1. La idea de la aplicación

### ¿Qué vamos a construir?

Una **aplicación de escritorio en Java** que simula una librería que quiere vender libros por internet. El programa se abre en un computador, se maneja con ventanas (usamos Swing) y guarda la información en archivos para que no se pierda al cerrar.

Es una tienda virtual, pero sin página web: todo ocurre en ventanas de Java, como las que diseñamos en el prototipo que está en el README del repositorio.

### ¿Qué se puede hacer en la aplicación?

**Como cliente:**

- Crear una cuenta (nombre, correo, dirección, teléfono y tipo de cliente)
- Iniciar sesión
- Ver el catálogo de libros con su precio y existencias
- Buscar libros por ISBN, título o autor
- Agregar libros a un carrito de compras
- Cambiar cantidades, quitar libros y ver los totales (subtotal, impuestos, descuento y total)
- Finalizar la compra eligiendo un método de pago
- Recibir un recibo de la compra
- Ver el historial de compras anteriores
- Actualizar sus datos personales

**Como administrador (el dueño de la librería):**

- Registrar libros nuevos
- Modificar la información de un libro
- Eliminar libros (solo si nunca se han vendido)
- Ver la lista completa del catálogo

### ¿Quiénes usan la aplicación?

| Actor | Rol |
|---|---|
| Cliente Regular | Compra libros sin beneficios especiales |
| Cliente Premium | Compra libros con un descuento especial |
| Administrador | Maneja el catálogo de libros |

La diferencia entre Regular y Premium no es invento nuestro: el caso de estudio lo exige, y además nos sirve para demostrar herencia y polimorfismo, que son temas que el profesor va a calificar.

---

## 2. Cómo funciona la aplicación (el flujo completo)

### 2.1 El flujo del cliente

1. Al abrir la aplicación aparece la **VentanaLogin**.
2. Si el cliente no tiene cuenta, hace clic en "Registrarse" y se abre la **VentanaRegistro**, donde llena: nombre completo, correo electrónico, dirección, teléfono y tipo de cliente (Regular o Premium).
3. Después de iniciar sesión llega a la **VentanaCatalogo**, que muestra una tabla con: ISBN, título, autor, precio y existencias de cada libro.
4. Si quiere encontrar algo rápido, usa el botón "Buscar", que abre la **VentanaBuscarLibro**. Allí puede buscar por ISBN, por título o por autor (incluso combinando los tres).
5. Cuando decide comprar, escribe la cantidad y presiona "Agregar al carrito". El sistema le muestra el subtotal de lo que agregó.
6. En la **VentanaCarrito** puede: aumentar o disminuir la cantidad con los botones + y −, eliminar un libro, ver el subtotal por libro, el total de impuestos, el descuento si es Premium y el total a pagar.
7. Al presionar "Finalizar compra" se abre la **VentanaFinalizarCompra**, que hace esto en orden:
   - Verifica que el cliente haya iniciado sesión (si no, muestra error)
   - Muestra el resumen del pedido: libros, cantidades y total
   - Verifica que haya existencias suficientes (si no, muestra error)
   - Pide seleccionar el método de pago (Tarjeta, PSE o Efectivo — es simulado)
   - Al confirmar: genera el recibo y descuenta del inventario las unidades vendidas
8. La **VentanaRecibo** muestra el comprobante con todo el detalle: número de compra, fecha, cliente, libros, cantidades, impuestos, descuento y total.
9. En cualquier momento el cliente puede abrir la **VentanaHistorial** para ver sus compras anteriores, o la **VentanaMiCuenta** para modificar sus datos.

### 2.2 El flujo del administrador

1. Entra por la misma VentanaLogin con la opción "Entrar como Administrador".
2. Llega a la **VentanaGestionLibros**, que muestra la tabla de libros y un formulario a la derecha.
3. **Registrar:** llena el formulario (ISBN, título, autor, año, categoría, editorial, páginas, precio, cantidad y formato físico/digital) y presiona Registrar.
4. **Actualizar:** selecciona un libro de la tabla, los datos cargan en el formulario, los modifica y guarda.
5. **Eliminar:** selecciona un libro y presiona Eliminar. El sistema revisa que el libro no tenga ventas asociadas; si las tiene, muestra un mensaje de error.

### 2.3 Ejemplo completo de una compra (con números)

Juan es cliente Premium. Agrega al carrito:

- 2 ejemplares de "Cien años de soledad" a $50.000 cada uno
- 1 ejemplar de "1984" a $35.000

El sistema calcula:

| Concepto | Cálculo | Valor |
|---|---|---|
| Subtotal por libro 1 | 2 × $50.000 | $100.000 |
| Subtotal por libro 2 | 1 × $35.000 | $35.000 |
| Subtotal total | 100.000 + 35.000 | **$135.000** |
| IVA 19% (libros físicos) | 135.000 × 0.19 | **$25.650** |
| Descuento Premium (10%) | 135.000 × 0.10 | **−$13.500** |
| Total a pagar | 135.000 + 25.650 − 13.500 | **$147.150** |

Al confirmar, el inventario de "Cien años de soledad" baja en 2 unidades y el de "1984" baja en 1. Este mismo ejemplo es el que usamos en las pantallas del prototipo, para que todo el mundo trabaje con los mismos números de prueba.

---

## 3. Cómo está organizado el código (las 3 capas)

El caso de estudio pide una **arquitectura multicapa**. Eso significa que el código está dividido en grupos, y cada grupo tiene una sola responsabilidad:

| Paquete | Capa | Responsabilidad |
|---|---|---|
| `co.uptc.edu.gui` | Presentación | Las ventanas: mostrar información, botones, tablas. NADA de cálculos ni archivos |
| `co.uptc.edu.negocio` | Negocio | Toda la lógica: clases del modelo, cálculos de IVA, descuentos, totales |
| `co.uptc.edu.persistencia` | Persistencia | Guardar y leer archivos JSON y TXT |
| `co.uptc.edu.util` | Utilidades | Validaciones y constantes compartidas |
| `co.uptc.edu.principal` | Arranque | La clase con el main que abre la primera ventana |

### 3.1 La regla de oro (no se puede romper)

- **gui** solo le habla a **negocio**.
- **negocio** solo le habla a **persistencia**.
- **util** lo pueden usar todos los paquetes.
- **Prohibido:** que una ventana haga cálculos, que una ventana toque archivos, o que la persistencia abra ventanas.

¿Por qué? Porque si un día cambiamos los archivos JSON por una base de datos, solo se toca el paquete persistencia y las ventanas no se enteran. Y porque el profesor revisa exactamente esto.

### 3.2 El paquete gui (presentación)

Son las 10 ventanas del prototipo. Cada ventana es una clase que **extiende JFrame**, y por dentro está organizada en paneles (**JPanel**), como en el ejemplo del profesor: una ventana tiene un panel para los datos y otro para los botones.

Convención de nombres para los componentes (obligatoria):

- Etiquetas: `labelNombre`, `labelCorreo`, `labelTitulo`
- Campos de texto: `textCorreo`, `textIsbn`, `textContrasena`
- Combos: `comboBoxTipoCliente`, `comboBoxFormato`
- Botones: `buttonAceptar`, `buttonBuscar`, `buttonAgregarCarrito`
- Tablas: `tablaLibros`, `tablaCarrito`

### 3.3 El paquete negocio (lógica)

Aquí viven las clases del diagrama de clases del negocio: `Libro`, `Cliente`, `ClienteRegular`, `ClientePremium`, `CarritoCompras`, `ItemCompra`, `Compra` y los enums `FormatoLibro` y `MetodoPago`. Más adelante las explicamos una por una.

### 3.4 El paquete persistencia (archivos)

Su trabajo es guardar la información en disco para que sobreviva al cierre del programa:

- `GestorCarritoJSON`: escribe y lee el carrito en `data/carrito.json`
- `RegistroOperacionesTXT`: agrega una línea por cada operación en `data/operaciones.txt`
- `GestorPersistencia`: la clase principal. Guarda y carga clientes, libros y compras, y usa las dos anteriores. Es la única clase del proyecto que sabe dónde están los archivos.

La idea de la persistencia es la del cuaderno: la memoria RAM es una pizarra que se borra al cerrar; los archivos son el cuaderno donde queda todo escrito.

### 3.5 El paquete util (transversal)

- `Constantes`: los valores que no cambian y que todos deben usar: IVA del 19%, IVA del 5%, porcentaje de descuento Premium y las rutas de los archivos.
- `Validaciones`: métodos estáticos para revisar formularios: campo vacío, correo válido, números, ISBN y stock suficiente.

### 3.6 El paquete principal

Solo tiene la clase `Principal` con el `main()`. Su trabajo: abrir la VentanaLogin. Nada más.

---

## 4. Las clases del negocio (quién es quién)

### 4.1 Libro

Representa un libro del catálogo. Sus atributos salen directo del caso de estudio:

`isbn, titulo, autores, anioPublicacion, categoria, editorial, paginas, precioVenta, cantidadInventario, formato`

Métodos importantes:

- `actualizarInventario(int cantidad)`: suma o resta existencias cuando se compra o se repone.
- `getPrecioConIva()`: devuelve el precio del libro con su IVA. Aquí va la regla: **libros físicos = 19%, libros digitales = 5%** (esto usa el enum FormatoLibro).

### 4.2 Cliente (abstracta)

Tiene lo común a todo cliente: `id, nombre, email, direccion, telefono, contrasena` y la lista `compras` (su historial). Es **abstracta** porque nunca se crea un "Cliente" a secas: siempre es Regular o Premium.

Su método clave es `calcularDescuento(double total)`, que es **abstracto**: cada subclase decide cómo se comporta. Eso es polimorfismo.

### 4.3 ClienteRegular

Hereda de Cliente. Su versión de `calcularDescuento` **devuelve 0**: un cliente regular no recibe descuento.

### 4.4 ClientePremium

Hereda de Cliente y agrega `nroMembresia` y `acumuladoCompras` (cuánto ha comprado en total). Su versión de `calcularDescuento` devuelve el 10% del valor recibido.

### 4.5 CarritoCompras

Es la bolsa de compras. Guarda los libros y sus cantidades en un `Map<Libro, Integer>` (cada libro apunta a cuántas unidades lleva). Métodos:

- `agregarLibro(libro, cantidad)`: si el libro ya está, suma la cantidad; si no, lo agrega.
- `removerLibro(libro)`: lo saca del carrito.
- `calcularSubtotal()`: suma precio × cantidad de todo lo que hay.
- `calcularIVA19()` y `calcularIVA5()`: impuestos según el formato de cada libro.
- `calcularTotal()`: subtotal + impuestos − descuento (el descuento depende del cliente que está comprando).
- `limpiar()`: vacía el carrito después de una compra.

### 4.6 ItemCompra

Es **una línea del recibo**: un libro concreto, la cantidad comprada, su subtotal y sus impuestos. Una compra tiene muchos ItemCompra.

### 4.7 Compra

Es el pedido ya confirmado. Guarda: `idCompra`, `fecha`, el cliente, el `metodoPago`, la lista de `items` (ItemCompra) y el `totalFinal`. Métodos:

- `calcularTotal()`: recorre los items y suma subtotales e impuestos, luego aplica el descuento del cliente.
- `generarDetalle()`: arma el texto del recibo que se muestra en la VentanaRecibo.

### 4.8 Los enums

- `FormatoLibro`: `FISICO` y `DIGITAL` (determina el IVA del libro).
- `MetodoPago`: `TARJETA`, `PSE` y `EFECTIVO`.

---

## 5. La lógica que todos deben aplicar igual

Para que las pantallas de Liz, de Alexander y la persistencia mía encajen, TODOS usamos las mismas reglas. Si alguien calcula distinto, la aplicación va a mostrar resultados diferentes según quién la haya programado. Estas son las reglas fijas:

### 5.1 El IVA

- Libro **físico**: IVA del **19%**
- Libro **digital**: IVA del **5%**
- El porcentaje **siempre** se toma de `Constantes.IVA_19` y `Constantes.IVA_5`. Nunca se escribe el número 0.19 directamente en el código.

### 5.2 El descuento Premium

- Solo lo reciben los clientes **Premium**.
- Es el **10%** (valor en `Constantes.DESCUENTO_PREMIUM`).
- Se calcula sobre el **subtotal** de la compra.
- Un cliente Regular siempre recibe 0 de descuento.

### 5.3 Las fórmulas de la compra

```
subtotalItem = precioVenta × cantidad
subtotalCarrito = suma de todos los subtotalItem
iva = subtotalCarrito × porcentaje según formato de cada libro
descuento = subtotalCarrito × 0.10   (solo si el cliente es Premium)
total = subtotalCarrito + iva − descuento
```

### 5.4 Las validaciones (siempre con Validaciones.java)

- Ningún campo obligatorio puede quedar vacío (`campoVacio`)
- El correo debe tener formato válido (`esCorreoValido`)
- Los campos numéricos no aceptan letras (`esNumero`)
- El ISBN debe cumplir su formato (`esISBNValido`)
- No se puede vender más de lo que hay en inventario (`hayStockSuficiente`)

### 5.5 Los mensajes de error

Todos los errores se muestran con `JOptionPane` y con estos textos exactos:

- Sesión: `"Debe iniciar sesión para finalizar la compra."`
- Stock: `"No hay suficientes unidades disponibles del libro."`
- Login: `"Correo o contraseña incorrectos."`
- Registro: `"El correo ya está registrado."`

### 5.6 El formato del dinero

Los valores monetarios se muestran así: `$50.000`, `$147.150` (signo de pesos y punto como separador de miles). Nada de `50000` ni de `$50,000`.

---

## 6. División del trabajo

Cada uno trabaja **solo en sus archivos**. Así no nos pisamos y el Git fluye sin problemas.

| Persona | Archivos | Qué hace |
|---|---|---|
| **Juan Pablo** | `persistencia/` completo, `util/` completo, `negocio/` completo | La lógica de la aplicación, las clases del modelo, los cálculos y todo lo que tiene que ver con guardar/leer archivos. Es el corazón del sistema: todo el mundo llama a sus clases |
| **Liz** | `gui/`: VentanaCatalogo, VentanaBuscarLibro, VentanaCarrito, VentanaRegistro | Las pantallas del flujo de compra: ver libros, buscar y armar el carrito |
| **Alexander** | `gui/`: VentanaLogin, VentanaFinalizarCompra, VentanaRecibo, VentanaHistorial, VentanaMiCuenta, VentanaGestionLibros, y `principal/` | El inicio y el cierre de la compra, más las pantallas del administrador |

**Regla de convivencia:** si necesitan una clase o método que no existe todavía (por ejemplo, Liz necesita que CarritoCompras tenga cierto método), lo piden en el grupo y yo lo agrego al negocio. Nadie modifica clases de otro sin avisar.

**Regla de comunicación:** antes de empezar a programar, cada uno lee su parte del prototipo y la compara con el caso de estudio para que no haya dudas.

---

## 7. Reglas para que todos programemos igual

Estas convenciones hacen que el código parezca escrito por una sola persona (y al profesor le gusta eso):

1. **Nombres en español**, con la primera letra de cada palabra en mayúscula en clases (`VentanaCarrito`) y en minúscula para variables y métodos (`precioVenta`, `calcularTotal`).
2. **Atributos privados** con getters y setters. Nunca atributos públicos.
3. **Nada de números mágicos**: los porcentajes y rutas van en `Constantes`.
4. **Nada de lógica duplicada**: si un cálculo ya existe en negocio, la ventana lo llama; no lo vuelve a escribir.
5. **Componentes con prefijo** (label, text, comboBox, button, tabla) como vimos arriba.
6. **Errores con JOptionPane**, nunca con `System.out.println` para el usuario.
7. **Codificación UTF-8** en todos los archivos (ya está configurado en el proyecto).
8. **Probar antes de hacer push**: el código que se sube debe compilar. Si no compila, no se sube.

---

## 8. Git y GitHub paso a paso

El repositorio es: `https://github.com/kanonufo/TiendaVirtualLibros.git`

### 8.1 Clonar el repositorio (la primera vez)

**Opción A — con GitHub Desktop (recomendada, es visual):**

1. Instalar GitHub Desktop (github.com/desktop) e iniciar sesión con su cuenta de GitHub.
2. **File → Clone repository → pestaña URL**.
3. Pegar: `https://github.com/kanonufo/TiendaVirtualLibros.git`
4. Elegir la carpeta local donde quedará el proyecto (por ejemplo `Documentos`).
5. Clic en **Clone**. Listo, ya tienen el código en su computador.

**Opción B — por comandos:**

```
git clone https://github.com/kanonufo/TiendaVirtualLibros.git
```

### 8.2 Abrir el proyecto en Eclipse

1. Abrir Eclipse.
2. **File → Import → General → Existing Projects into Workspace**.
3. En "Select root directory" buscar la carpeta `TiendaVirtualLibros` que acabaron de clonar.
4. Marcar el proyecto y dar **Finish**.

Si no aparece, revisar que estén clonando la carpeta que contiene el archivo `.project`.

### 8.3 El flujo de trabajo de todos los días

Repitan siempre este ciclo:

1. **Antes de empezar a trabajar:** actualizar el código con lo que subieron los demás.
2. Trabajar en sus archivos.
3. Guardar y **probar que compila**.
4. **Commit** con un mensaje claro.
5. **Push** para subir a GitHub.

### 8.4 Actualizar antes de empezar (Pull)

**Con GitHub Desktop:** botón **Fetch origin** (arriba), y si dice que hay novedades, botón **Pull origin**.

**Desde Eclipse:** clic derecho sobre el proyecto → **Team → Pull**.

**Por comandos:** `git pull`

Esto baja los cambios que subieron los compañeros. Háganlo SIEMPRE al iniciar, así evitan trabajar sobre código viejo.

### 8.5 Hacer commit y push con GitHub Desktop

1. En la columna izquierda aparecen los archivos que cambiaron (con check).
2. Escribir un mensaje en "Summary" que diga qué se hizo. Ejemplos:
   - `Agregar botones y tabla a la ventana carrito`
   - `Crear validación de correo en registro`
   - `Corregir cálculo del IVA en CarritoCompras`
3. Clic en **"Commit to main"**.
4. Clic en **"Push origin"** (arriba a la derecha).
5. Verificar en `github.com` que el cambio apareció.

### 8.6 Hacer commit y push desde Eclipse (EGit)

1. Clic derecho sobre el proyecto → **Team → Commit**.
2. Aparecen los archivos modificados; arrastrarlos al área "Staged Changes".
3. Escribir el mensaje del commit.
4. Clic en **"Commit and Push"** (o primero "Commit" y luego **Team → Push to Upstream**).
5. Si Eclipse pide usuario y contraseña: usar el usuario de GitHub y un token personal (se configura una vez; si les pide esto, avisen y lo hacemos juntos).

### 8.7 ¿Qué es un conflicto y qué hago si me sale?

Un conflicto ocurre cuando **dos personas modificaron la misma línea del mismo archivo** y Git no sabe cuál de las dos versiones dejar. 

Cómo evitarlos:

- Cada uno trabaja solo en sus archivos (por eso la división de la sección 6).
- Siempre hacer Pull antes de empezar.
- Si hay que tocar un archivo de otro, avisar en el grupo primero.

Si aun así sale un conflicto:

1. NO entren en pánico. El código no se pierde.
2. GitHub Desktop les muestra el archivo con las dos versiones marcadas con `<<<<<<<` y `>>>>>>>`.
3. Decidir qué versión se queda (o combinar las dos), borrar las marcas y guardar.
4. Marcar el archivo como resuelto y hacer commit.
5. Si tienen dudas, llamen al grupo y lo arreglamos entre todos. Es más fácil de lo que parece y es parte normal de trabajar con Git.

### 8.8 Cosas que NUNCA se suben al repositorio

El `.gitignore` ya se encarga, pero recuerden: no se sube la carpeta `bin/` (compilados), ni la carpeta `.settings/` (configuración personal de Eclipse), ni los archivos de datos de prueba que queden en `data/`.

---

## 9. Checklist rápido (antes de decir "ya terminé")

- [ ] Hice Pull y tengo la última versión
- [ ] Trabajé solo en los archivos que me tocaron
- [ ] Usé los nombres de componentes con prefijo (label, text, button)
- [ ] Los cálculos los hago llamando al negocio, no en la ventana
- [ ] Los errores salen con JOptionPane y con los textos acordados
- [ ] El código compila sin errores
- [ ] Hice commit con un mensaje que se entiende
- [ ] Hice Push y verifiqué en github.com

---

## Anexo: la librería Gson para leer y escribir JSON

Para leer y escribir los archivos JSON usaremos **Gson**, una librería de Google muy común. Cuando lleguemos a la persistencia:

1. Descargar `gson-2.10.1.jar` desde el repositorio oficial de Gson (o pedirla en el grupo).
2. Copiar el .jar en una carpeta `libs/` dentro del proyecto.
3. En Eclipse: clic derecho sobre el proyecto → **Build Path → Configure Build Path → Libraries → Add JARs** → seleccionar el .jar de la carpeta `libs`.
4. Listo. Ese .jar sí se sube al repositorio para que todos lo tengan (va dentro de `libs/`).

---

Cualquier duda de este documento se resuelve en el grupo ANTES de programar. Es mejor preguntar dos minutos que corregir dos horas de código.
