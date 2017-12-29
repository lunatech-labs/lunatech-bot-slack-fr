package services

import scala.collection.JavaConverters._

/**
  * A Service that is able to return the next train that will arrive at a station.
  * This Service uses the Open DATA API from RATP.
  * It works only for a limited set of IPs. If you try this code from home it won't work.
  *
  * @author created by N.Martignole, Lunatech, on 08/12/2017.
  */
class RERService {

  // Recupere l'horaire du prochain passage
  def nextRERHours: String = {
    import org.jsoup.Jsoup
    import org.jsoup.nodes.Document
    val departuredate = "29%2F12%2F2017"
    val departureHour = "19"
    val departureMinut = "10"

    // TODO si le parametre HTTP departure type=now alors departure_date et departure_hour sont ignorés
    val doc = Jsoup.connect(s"https://www.ratp.fr/horaires?network-current=" +
      s"&networks=rer&line_rer=A&line_busratp=&name_line_busratp=" +
      s"&id_line_busratp=" +
      s"&line_noctilien=" +
      s"&name_line_noctilien=" +
      s"&id_line_noctilien=" +
      s"&stop_point_rer=Val+d%27Europe-Serris" +
      s"&type=now" +
      s"&departure_date=$departuredate" +
      s"&departure_hour=$departureHour&departure_minute=$departureMinut" +
      s"&op=Rechercher" +
      s"&form_build_id=form-J52PoSEUjc7t5Xcs6irXxCyvLCLm3YZXyBQT4h6bauc" +
      s"&form_id=scheduledform").get

    val prochainRER = doc.select("ul.horaires-timetable li.body-rer")


    /* Exemple de HTML récupéré
    <li class="body-rer"> <span class="nom-wrap"> <a href="/horaires/mission/rer/A/ZULI/Val%20d%27Europe-Serris/A" class="js-horaire-show-mission"> ZULI </a> </span> <span class="terminus-wrap"> St Germain en Laye</span> <span class="heure-wrap heure-wrap-long">18:38</span> </li>
<li class="body-rer"> <span class="nom-wrap"> <a href="/horaires/mission/rer/A/WOWQ/Val%20d%27Europe-Serris/A" class="js-horaire-show-mission"> WOWQ </a> </span> <span class="terminus-wrap"> Torcy</span> <span class="heure-wrap heure-wrap-long">Train sans arrêt</span> </li>
<li class="body-rer"> <span class="nom-wrap"> <a href="/horaires/mission/rer/A/ZULI/Val%20d%27Europe-Serris/A" class="js-horaire-show-mission"> ZULI </a> </span> <span class="terminus-wrap"> St Germain en Laye</span> <span class="heure-wrap heure-wrap-long">18:45</span> </li>
<li class="body-rer"> <span class="nom-wrap"> <a href="/horaires/mission/rer/A/WOWQ/Val%20d%27Europe-Serris/A" class="js-horaire-show-mission"> WOWQ </a> </span> <span class="terminus-wrap"> Torcy</span> <span class="heure-wrap heure-wrap-long">Train sans arrêt</span> </li>
<li class="body-rer"> <span class="nom-wrap"> <a href="/horaires/mission/rer/A/ZUME/Val%20d%27Europe-Serris/A" class="js-horaire-show-mission"> ZUME </a> </span> <span class="terminus-wrap"> St Germain en Laye</span> <span class="heure-wrap heure-wrap-long">18:55</span> </li>
<li class="body-rer"> <span class="nom-wrap"> <a href="/horaires/mission/rer/A/QIKI/Val%20d%27Europe-Serris/R" class="js-horaire-show-mission"> QIKI </a> </span> <span class="terminus-wrap"> Marne la Vallee-Chessy</span> <span class="heure-wrap heure-wrap-long">A l'approche</span> </li>
<li class="body-rer"> <span class="nom-wrap"> <a href="/horaires/mission/rer/A/QHOR/Val%20d%27Europe-Serris/R" class="js-horaire-show-mission"> QHOR </a> </span> <span class="terminus-wrap"> Marne la Vallee-Chessy</span> <span class="heure-wrap heure-wrap-long">18:40</span> </li>
<li class="body-rer"> <span class="nom-wrap"> <a href="/horaires/mission/rer/A/QIKI/Val%20d%27Europe-Serris/R" class="js-horaire-show-mission"> QIKI </a> </span> <span class="terminus-wrap"> Marne la Vallee-Chessy</span> <span class="heure-wrap heure-wrap-long">18:48</span> </li>
<li class="body-rer"> <span class="nom-wrap"> <a href="/horaires/mission/rer/A/QIKI/Val%20d%27Europe-Serris/R" class="js-horaire-show-mission"> QIKI </a> </span> <span class="terminus-wrap"> Marne la Vallee-Chessy</span> <span class="heure-wrap heure-wrap-long">18:54</span> </li>
<li class="body-rer"> <span class="nom-wrap"> <a href="/horaires/mission/rer/A/QHOR/Val%20d%27Europe-Serris/R" class="js-horaire-show-mission"> QHOR </a> </span> <span class="terminus-wrap"> Marne la Vallee-Chessy</span> <span class="heure-wrap heure-wrap-long">19:02</span> </li>
     */

    // TODO ecrire un parseur propre qui utilise JSoup pour extraire uniquement les éléments intéressants
    // On a besoin de
    // 1) nom de la Mission (ZULI ou QHOR)
    // 2) terminus, utile pour ceux qui prennent loin le RER
    // 3) horaire de passage

    s"$prochainRER"
  }

  // Recupere le status du RER sur le site de la RATP
  def getRERStatus: String = {
    import org.jsoup.Jsoup
    import org.jsoup.nodes.Document
    val departuredate = "29%2F12%2F2017"
    val departureHour = "19"
    val departureMinut = "10"
    val doc = Jsoup.connect(s"https://www.ratp.fr/horaires?network-current=&networks=rer&line_rer=A&line_busratp=&name_line_busratp=&id_line_busratp=&line_noctilien=&name_line_noctilien=&id_line_noctilien=&stop_point_rer=Val+d%27Europe-Serris&type=now&departure_date=$departuredate&departure_hour=$departureHour&departure_minute=$departureMinut&op=Rechercher&form_build_id=form-J52PoSEUjc7t5Xcs6irXxCyvLCLm3YZXyBQT4h6bauc&form_id=scheduledform").get

    val statusRER = doc.select("div.traffic-event div.status")
    //    val messageContent = doc.select("div.traffic-event div.message-content")
    //    println("Test "+statusRER)

    s"$statusRER"
  }


}
