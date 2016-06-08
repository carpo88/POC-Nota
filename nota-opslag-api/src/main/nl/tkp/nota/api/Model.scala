
package nl.tkp.nota.api

import org.apache.flink.api.scala._
import org.joda.time.DateTime
import scala.language.implicitConversions
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

case class Nota(
                nta_id Long
              , Long psn_fonds_id
              , String naam_verkort
               )

/* TODO
nota
Long nta.id  -> nta_id
Long nde.psn_fonds_id
String nst.naam_Verkort
String nde.naam
String nde.klasse
String nrn.indicatief
String nrn.periodiciteit
Object fiscaliteit
  Object notablok
*/

/*
case class Request (persoonNummer: Long, fonds: Long) {
  require(persoonNummer > 0, persoonNummer)
  require(fonds > 0, fonds)
}


case class Response(deelnemer: Option[Deelnemer], error: Option[String])


case class Status(code: String, ingangsdatum: DateTime, einddatum: DateTime)


case class Deelnemer(persoonNummer: Long, purs: Seq[PensioenUitvoeringsrelatie], status: Seq[Status])


// PUR: PensioenUitvoeringsrelatie
case class PensioenUitvoeringsrelatie(ingangsdatum: DateTime,
                                      einddatum: DateTime,
                                      deelnameList: Seq[Deelname],
                                      begunstigdeList: Seq[Begunstigde])

*/