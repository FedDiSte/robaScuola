<?php
$codiceAlunno = $_POST['codiceAlunno'];

try {
    $host = "localhost";
    $dbname = "valutazioni";
    $user = "root";
    $pass = 'adminfm';
    $connection = new PDO("mysql:host=$host;dbname=$dbname", $user, $pass);
} catch (PDOException $e) {
    echo "<div class=\"error\">Connection failed: " . $e->getMessage() . "</div>";
}

$query = $connection->query("select p.* from prove as p inner join studenti as s on s.matricola = p.id where s.matricola =\"$codiceAlunno\"");
$query2 = $connection->query("select cognome, nome from studenti where matricola = \"$codiceAlunno\"");
foreach ($query2 as $element) {
    $cognome = $element['cognome'];
    $nome = $element['nome'];
}
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prove di <?php echo $cognome . " " . $nome ?></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>

<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container-fluid">
            <span class="navbar-brand mb-0 h1">RegistroElettronico</span>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="index.php">Homepage</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-12 card" id="table">
                <div class="row justify-content-center">
                    <p class="h2 text-center">Prove di <?php echo $cognome . " " . $nome ?></p>
                </div>
                <hr>
                <div class="row justify-content-center">
                    <table class="table table-borderless table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Data</th>
                                <th scope="col">Voto</th>
                                <th scope="col">Materia</th>
                            </tr>
                        </thead>
                        <tbody>
                            <?php
                            $td = "<td>";
                            $endtd = "</td>";
                            if ($query->rowCount() == 0) {
                                $wasZero = true;
                                echo "<script type=\"text/javascript\">document.getElementById(\"table\").classList.add(\"none\")</script>";
                            }
                            foreach ($query as $studente) {
                                $studente['data'] = new DateTime($studente['data']);
                                echo "<tr>" .
                                    "<th scope=\"row\">" . date_format($studente['data'], "d F Y") . "</th>" .
                                    $td . $studente['voto'] . $endtd .
                                    $td . $studente['materia'] . $endtd .
                                    "</tr>";
                            }
                            ?>
                        </tbody>
                    </table>
                </div>
            </div>
            <?php if ($wasZero) {
                echo "<div class=\"bigError\">Dati non trovati</div>";
            } ?>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</body>

</html>