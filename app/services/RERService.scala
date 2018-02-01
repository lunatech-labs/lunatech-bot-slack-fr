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
    val departuredate = "01%2F02%2F2018"
    val departureHour = "14"
    val departureMinut = "00"

    // TODO si le parametre HTTP departure type=now alors departure_date et departure_hour sont ignorés
    val doc = Jsoup.connect(s"https://www.ratp.fr/horaires?network-current=" +
      s"&networks=rer&line_rer=A" +
      s"&line_busratp=&name_line_busratp=" +
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

    val listMissions = prochainRER.select("a.js-horaire-show-mission").asScala.toList
    val listTerminus = prochainRER.select("span.terminus-wrap").asScala.toList
    val listHeures = prochainRER.select(".heure-wrap-long").asScala.toList

    println("1- "+listMissions)
    println("2- "+listTerminus)
    println("3- "+listHeures)

    s"$prochainRER"
  }

  // Recupere le status du RER sur le site de la RATP
  def getRERStatus: String = {
    import org.jsoup.Jsoup
    import org.jsoup.nodes.Document
    val departuredate = "01%2F02%2F2018"
    val departureHour = "19"
    val departureMinut = "10"
    val doc = Jsoup.connect(s"https://www.ratp.fr/horaires?network-current=&networks=rer&line_rer=A&line_busratp=&name_line_busratp=&id_line_busratp=&line_noctilien=&name_line_noctilien=&id_line_noctilien=&stop_point_rer=Val+d%27Europe-Serris&type=now&departure_date=$departuredate&departure_hour=$departureHour&departure_minute=$departureMinut&op=Rechercher&form_build_id=form-J52PoSEUjc7t5Xcs6irXxCyvLCLm3YZXyBQT4h6bauc&form_id=scheduledform").get

    val statusRER = doc.select("div.traffic-event div.status")
    //    val messageContent = doc.select("div.traffic-event div.message-content")
    //    println("Test "+statusRER)

    s"$statusRER"
  }


}
