<?php
try {
    $host = "localhost";
    $dbname = "valutazioni";
    $user = "root";
    $pass = 'adminfm';
    $connection = new PDO("mysql:host=$host;dbname=$dbname", $user, $pass);
} catch (PDOException $e) {
    echo "<div class=error>Connection failed: " . $e->getMessage() . "</div>";
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
    <title>Registro elettronico</title>
</head>

<body>
    <nav class="navbar navbar-dark">
        <div class="container-fluid">
            <span class="navbar-brand mb-0 h1">RegistroElettronico</span>
        </div>
    </nav>
    <div class="container">
        <div class="row">
            <div class="col-md-12 card">
                <div class="row justify-content-center">
                    <p class="h2 text-center">🏫Aggiungi dati🏫</p>
                </div>
                <hr />
                <div class="row">
                    <p class="h5">Aggiungi un nuovo alunno👨‍🎓</p>
                </div>
                <div class="row justify-content-center">
                    <div class="col-md-auto">
                        <form action="/aggiungiAlunno.php" method="post">
                            <label for="cognome"><input type="text" name="cognome" id="cognome" placeholder="Cognome" required></label>
                            <label for="nome"><input type="text" name="nome" id="nome" placeholder="Nome" required></label>
                            <label for="classe"><input type="text" name="classe" id="classe" placeholder="Classe" require></label>
                            <input type="submit" value="Aggiungi questo alunno" class="button">
                        </form>
                    </div>
                </div>
                <div class="row">
                    <p class="h5">Aggiungi una nuova prova✍</p>
                </div>
                <div class="row justify-content-center">
                    <div class="col-md-auto">
                        <form action="/aggiungiProva.php" method="post">
                            <label for="data"><input type="date" name="data" id="data" placeholder="Data" required></label>
                            <label for="voto"><input type="text" name="voto" id="voto" placeholder="Voto" required></label>
                            <label for="materia"><input type="text" name="materia" id="materia" placeholder="Materia" required></label>
                            <label for="codiceStudente"><select name="codiceStudente" id="codiceStudente" class="dropdown">
                                    <?php
                                    $query = $connection->query("select matricola, cognome, nome from studenti");
                                    foreach ($query as $row) {
                                        echo "<option value=\"" . $row['matricola'] . "\">" . $row['cognome'] . " " . $row['nome']. "</option>\n";
                                    }
                                    ?>
                                </select></label>
                            <input type="submit" value="Aggiungi questa valutazione" class="button">
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 card">
                <div class="row justify-content-center">
                    <p class="h2 text-center">🔬Cerca dati🔬</p>
                </div>
                <hr />
                <div class="row">
                    <p class="h5">Cerca i dati delle prove di una classe✔</p>
                </div>
                <div class="row justify-content-center">
                    <div class="col-md-auto">
                        <form action="/cercaProveClasse.php" method="post">
                            <label for="classe"><select name="classe" id="classe" class="dropdown" required>
                                    <?php
                                    $query = $connection->query("select distinct classe from studenti");
                                    foreach ($query as $row) {
                                        echo "<option value=\"" . $row['classe'] . "\">" . $row['classe'] . "</option>\n";
                                    }
                                    ?>
                                </select></label>
                            <input type="submit" value="Cerca le prove di questa classe" class="button">
                        </form>
                    </div>
                </div>
                <div class="row">
                    <p class="h5">Visualizza tutti gli alunni✌</p>
                </div>
                <div class="row justify-content-center">
                    <div class="col-md-auto">
                        <form action="/tuttiAlunni.php" method="post">
                            <input type="submit" value="Visualizza tutti gli alunni" class="button">
                        </form>
                    </div>
                </div>
                <div class="row">
                    <p class="h5">Visualizza le prove di un alunno👨‍🏫</p>
                </div>
                <div class="row justify-content-center">
                    <div class="col-md-auto">
                        <form action="/proveAlunno.php" method="post">
                            <label for="codiceAlunno"><select name="codiceAlunno" id="codiceAlunno" class="dropdown" required>
                                    <?php
                                    $query = $connection->query("select matricola, cognome, nome from studenti");
                                    foreach ($query as $row) {
                                        echo "<option value=\"" . $row['matricola'] . "\">" . $row['cognome'] . " " . $row['nome']. "</option>\n";
                                    }
                                    ?>
                                </select></label>
                            <input type="submit" value="Visualizza le prove di questo alunno " class="button">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</body>

</html>