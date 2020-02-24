//
// (C) Copyright 2018-2020 Martin E. Nordberg III
// Apache 2.0 License
//

package jvm.barlom.infrastructure.dxl.parsing

import o.barlom.infrastructure.dxl.parsing.DxlParser
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

//---------------------------------------------------------------------------------------------------------------------

@Suppress("RemoveRedundantBackticks")
internal class DxlSmallSampleGraphTests {

    private fun checkParseAndGenerate(code: String) {
        val parser = DxlParser("test.dxl", code)
        val element = parser.parseTopLevel()

        assertEquals(code, element.code)
    }

    @Test
    fun `Cities are linked by highways`() {

        val code = """
          alias usmaps = geography.maps.us
          alias State = geography.regions.us.State
          
          [usmaps.dmv: Graph] {
            [dc: State] { [washington: City ~ name = "Washington" ~ population = 633_427] }
            [va: State] {
              [centreville: City ~ name = "Centreville" ~ population = 71_135]
              [fredericksburg: City ~ name = "Fredericksburg" ~ population = 25_691]
            }
            [md: State] {
              [baltimore: City ~ name = "Baltimore" ~ population = 619_493]
              [`college park`: City ~ name = "College Park" ~ population = 32_303]
            }
            [dc]
              ---|:HasNeighbor|---[md]

            [dc]
              ---|:HasNeighbor|---[va]

            [va]
              ---|:HasNeighbor|---[md]

            [dc.washington]
              ---|:Highway(type="Interstate") ~ name = "I-66" ~ distance = 36|---[va.centreville]

            [dc.washington]
              ---|:Highway(type="Interstate") ~ name = "I-95" ~ distance = 53|---[va.fredericksburg]

            [dc.washington]
              ---|:Highway(type="Interstate") ~ name = "I-95" ~ distance = 41|---[md.baltimore]

            [dc.washington]
              ---|:Highway(type="Interstate") ~ name = "I-295" ~ distance = 39|---[md.baltimore]

            [dc.washington]
              ---|us1|---[md.`college park`]

            -|us1: Highway(type="US") ~ name = "US-1" ~ distance = 9|-
          }
        """.trimIndent()

        checkParseAndGenerate(code)

    }

    @Test
    fun `Senators are linked to states classes and parties`() {

        // ( ) for parameters
        // @ for time of applicability
        // .. for range
        // ? for optional
        // | and & for union and intersection types
        //

        val code = """
                   [:Senate] {
                     [classI: Class ~ doc = "The first class" ~ name = "Class I" ~ year = 2025] {
                       [tammyBaldwin] [johnBarrasso]
                     }
                     [classII: Class ~ doc = "The second class" ~ name = "Class II" ~ year = 2021] { [lamarAlexander] }
                     [classIII: Class ~ doc = "The third class" ~ name = "Class III" ~ year = 2023] { [michaelBennet] }
                     [democrats: Party ~ name = "Democratic Party"] { [tammyBaldwin] [michaelBennet] }
                     [republicans: Party ~ name = "Republican Party"] { [lamarAlexander] [johnBarrasso] }
                     [co: State ~ name = "Colorado" ~ abbrev = "CO"]
                     [tn: State ~ name = "Tennessee" ~ abbrev = "TN"]
                     [wi: State ~ name = "Wisconsin" ~ abbrev = "WI"]
                     [wy: State ~ name = "Wyoming" ~ abbrev = "WY"]
                     [lamarAlexander: Senator
                       ~ name = "Alexander, Lamar"
                       ~ address = "455 Dirksen Senate Office Building Washington DC 20510"
                       ~ phone = "(202) 224-4944"
                       ~ contact = "www.alexander.senate.gov/public/index.cfm?p=Email"
                     ]
                       ---|:ServesState|-->[tn]

                     [tammyBaldwin: Senator
                       ~ name = "Baldwin, Tammy"
                       ~ address = "709 Hart Senate Office Building Washington DC 20510"
                       ~ phone = "(202) 224-5653"
                       ~ contact = "www.baldwin.senate.gov/feedback"
                     ]
                       ---|:ServesState|-->[wi]

                     [johnBarrasso: Senator
                       ~ name = "Barrasso, John"
                       ~ address = "307 Dirksen Senate Office Building Washington DC 20510"
                       ~ phone = "(202) 224-6441"
                       ~ contact = "www.barrasso.senate.gov/public/index.cfm/contact-form"
                     ]
                       ---|:ServesState|-->[wy]

                     [michaelBennet: Senator
                       ~ name = "Bennet, Michael F."
                       ~ contact = "261 Russell Senate Office Building Washington DC 20510"
                       ~ phone = "(202) 224-5852"
                       ~ contact = "www.bennet.senate.gov/public/index.cfm/contact"
                     ]
                       ---|:ServesState|-->[co]

                   }
                   """.trimIndent()



/**
Blackburn, Marsha - (R - TN)Class I
357 Dirksen Senate Office Building Washington DC 20510
(202) 224-3344
Contact: www.blackburn.senate.gov/contact_marsha
Blumenthal, Richard - (D - CT)Class III
706 Hart Senate Office Building Washington DC 20510
(202) 224-2823
Contact: www.blumenthal.senate.gov/contact/
Blunt, Roy - (R - MO)Class III
260 Russell Senate Office Building Washington DC 20510
(202) 224-5721
Contact: www.blunt.senate.gov/public/index.cfm/contact-roy
Booker, Cory A. - (D - NJ)Class II
717 Hart Senate Office Building Washington DC 20510
(202) 224-3224
Contact: www.booker.senate.gov/?p=contact
Boozman, John - (R - AR)Class III
141 Hart Senate Office Building Washington DC 20510
(202) 224-4843
Contact: www.boozman.senate.gov/public/index.cfm/contact
Braun, Mike - (R - IN)Class I
374 Russell Senate Office Building Washington DC 20510
(202) 224-4814
Contact: www.braun.senate.gov/contact-mike
Brown, Sherrod - (D - OH)Class I
503 Hart Senate Office Building Washington DC 20510
(202) 224-2315
Contact: www.brown.senate.gov/contact/
Burr, Richard - (R - NC)Class III
217 Russell Senate Office Building Washington DC 20510
(202) 224-3154
Contact: www.burr.senate.gov/contact/email
Cantwell, Maria - (D - WA)Class I
511 Hart Senate Office Building Washington DC 20510
(202) 224-3441
Contact: www.cantwell.senate.gov/public/index.cfm/email-maria
Capito, Shelley Moore - (R - WV)Class II
172 Russell Senate Office Building Washington DC 20510
(202) 224-6472
Contact: www.capito.senate.gov/contact/contact-shelley
Cardin, Benjamin L. - (D - MD)Class I
509 Hart Senate Office Building Washington DC 20510
(202) 224-4524
Contact: www.cardin.senate.gov/contact/
Carper, Thomas R. - (D - DE)Class I
513 Hart Senate Office Building Washington DC 20510
(202) 224-2441
Contact: www.carper.senate.gov/public/index.cfm/email-senator-carper
Casey, Robert P., Jr. - (D - PA)Class I
393 Russell Senate Office Building Washington DC 20510
(202) 224-6324
Contact: www.casey.senate.gov/contact/
Cassidy, Bill - (R - LA)Class II
520 Hart Senate Office Building Washington DC 20510
(202) 224-5824
Contact: www.cassidy.senate.gov/contact
Collins, Susan M. - (R - ME)Class II
413 Dirksen Senate Office Building Washington DC 20510
(202) 224-2523
Contact: www.collins.senate.gov/contact
Coons, Christopher A. - (D - DE)Class II
218 Russell Senate Office Building Washington DC 20510
(202) 224-5042
Contact: www.coons.senate.gov/contact
Cornyn, John - (R - TX)Class II
517 Hart Senate Office Building Washington DC 20510
(202) 224-2934
Contact: www.cornyn.senate.gov/contact
Cortez Masto, Catherine - (D - NV)Class III
516 Hart Senate Office Building Washington DC 20510
(202) 224-3542
Contact: www.cortezmasto.senate.gov/contact
Cotton, Tom - (R - AR)Class II
326 Russell Senate Office Building Washington DC 20510
(202) 224-2353
Contact: www.cotton.senate.gov/?p=contact
Cramer, Kevin - (R - ND)Class I
400 Russell Senate Office Building Washington DC 20510
(202) 224-2043
Contact: www.cramer.senate.gov/contact_kevin
Crapo, Mike - (R - ID)Class III
239 Dirksen Senate Office Building Washington DC 20510
(202) 224-6142
Contact: www.crapo.senate.gov/contact
Cruz, Ted - (R - TX)Class I
127A Russell Senate Office Building Washington DC 20510
(202) 224-5922
Contact: www.cruz.senate.gov/?p=form&id=16
Daines, Steve - (R - MT)Class II
320 Hart Senate Office Building Washington DC 20510
(202) 224-2651
Contact: www.daines.senate.gov/connect/email-steve
Duckworth, Tammy - (D - IL)Class III
524 Hart Senate Office Building Washington DC 20510
(202) 224-2854
Contact: www.duckworth.senate.gov/content/contact-senator
Durbin, Richard J. - (D - IL)Class II
711 Hart Senate Office Building Washington DC 20510
(202) 224-2152
Contact: www.durbin.senate.gov/contact/
Enzi, Michael B. - (R - WY)Class II
379A Russell Senate Office Building Washington DC 20510
(202) 224-3424
Contact: www.enzi.senate.gov/public/index.cfm/contact?p=e-mail-sen...
Ernst, Joni - (R - IA)Class II
730 Hart Senate Office Building Washington DC 20510
(202) 224-3254
Contact: www.ernst.senate.gov/public/index.cfm/contact
Feinstein, Dianne - (D - CA)Class I
331 Hart Senate Office Building Washington DC 20510
(202) 224-3841
Contact: www.feinstein.senate.gov/public/index.cfm/e-mail-me
Fischer, Deb - (R - NE)Class I
454 Russell Senate Office Building Washington DC 20510
(202) 224-6551
Contact: www.fischer.senate.gov/public/index.cfm/contact
Gardner, Cory - (R - CO)Class II
354 Russell Senate Office Building Washington DC 20510
(202) 224-5941
Contact: www.gardner.senate.gov/contact-cory/email-cory
Gillibrand, Kirsten E. - (D - NY)Class I
478 Russell Senate Office Building Washington DC 20510
(202) 224-4451
Contact: www.gillibrand.senate.gov/contact/email-me
Graham, Lindsey - (R - SC)Class II
290 Russell Senate Office Building Washington DC 20510
(202) 224-5972
Contact: www.lgraham.senate.gov/public/index.cfm/e-mail-senator-gr...
Grassley, Chuck - (R - IA)Class III
135 Hart Senate Office Building Washington DC 20510
(202) 224-3744
Contact: www.grassley.senate.gov/contact
Harris, Kamala D. - (D - CA)Class III
112 Hart Senate Office Building Washington DC 20510
(202) 224-3553
Contact: www.harris.senate.gov/contact
Hassan, Margaret Wood - (D - NH)Class III
324 Hart Senate Office Building Washington DC 20510
(202) 224-3324
Contact: www.hassan.senate.gov/content/contact-senator
Hawley, Josh - (R - MO)Class I
212 Russell Senate Office Building Washington DC 20510
(202) 224-6154
Contact: www.hawley.senate.gov/contact-senator-hawley
Heinrich, Martin - (D - NM)Class I
303 Hart Senate Office Building Washington DC 20510
(202) 224-5521
Contact: www.heinrich.senate.gov/contact
Hirono, Mazie K. - (D - HI)Class I
713 Hart Senate Office Building Washington DC 20510
(202) 224-6361
Contact: www.hirono.senate.gov/contact
Hoeven, John - (R - ND)Class III
338 Russell Senate Office Building Washington DC 20510
(202) 224-2551
Contact: www.hoeven.senate.gov/public/index.cfm/email-the-senator
Hyde-Smith, Cindy - (R - MS)Class II
702 Hart Senate Office Building Washington DC 20510
(202) 224-5054
Contact: www.hydesmith.senate.gov/content/contact-senator
Inhofe, James M. - (R - OK)Class II
205 Russell Senate Office Building Washington DC 20510
(202) 224-4721
Contact: www.inhofe.senate.gov/contact
Isakson, Johnny - (R - GA)Class III
131 Russell Senate Office Building Washington DC 20510
(202) 224-3643
Contact: www.isakson.senate.gov/public/index.cfm/email-me
Johnson, Ron - (R - WI)Class III
328 Hart Senate Office Building Washington DC 20510
(202) 224-5323
Contact: www.ronjohnson.senate.gov/public/index.cfm/email-the-sena...
Jones, Doug - (D - AL)Class II
330 Hart Senate Office Building Washington DC 20510
(202) 224-4124
Contact: www.jones.senate.gov/content/contact-senator
Kaine, Tim - (D - VA)Class I
231 Russell Senate Office Building Washington DC 20510
(202) 224-4024
Contact: www.kaine.senate.gov/contact
Kennedy, John - (R - LA)Class III
416 Russell Senate Office Building Washington DC 20510
(202) 224-4623
Contact: www.kennedy.senate.gov/public/email-me
King, Angus S., Jr. - (I - ME)Class I
133 Hart Senate Office Building Washington DC 20510
(202) 224-5344
Contact: www.king.senate.gov/contact
Klobuchar, Amy - (D - MN)Class I
425 Dirksen Senate Office Building Washington DC 20510
(202) 224-3244
Contact: www.klobuchar.senate.gov/public/index.cfm/contact
Lankford, James - (R - OK)Class III
316 Hart Senate Office Building Washington DC 20510
(202) 224-5754
Contact: www.lankford.senate.gov/contact/email
Leahy, Patrick J. - (D - VT)Class III
437 Russell Senate Office Building Washington DC 20510
(202) 224-4242
Contact: www.leahy.senate.gov/contact/
Lee, Mike - (R - UT)Class III
361A Russell Senate Office Building Washington DC 20510
(202) 224-5444
Contact: www.lee.senate.gov/public/index.cfm/contact
Manchin, Joe, III - (D - WV)Class I
306 Hart Senate Office Building Washington DC 20510
(202) 224-3954
Contact: www.manchin.senate.gov/public/index.cfm/contact-form
Markey, Edward J. - (D - MA)Class II
255 Dirksen Senate Office Building Washington DC 20510
(202) 224-2742
Contact: www.markey.senate.gov/contact
McConnell, Mitch - (R - KY)Class II
317 Russell Senate Office Building Washington DC 20510
(202) 224-2541
Contact: www.mcconnell.senate.gov/public/index.cfm?p=contact
McSally, Martha - (R - AZ)Class III
404 Russell Senate Office Building Washington DC 20510
202-224-2235
Contact: www.mcsally.senate.gov/contact_martha
Menendez, Robert - (D - NJ)Class I
528 Hart Senate Office Building Washington DC 20510
(202) 224-4744
Contact: www.menendez.senate.gov/contact
Merkley, Jeff - (D - OR)Class II
313 Hart Senate Office Building Washington DC 20510
(202) 224-3753
Contact: www.merkley.senate.gov/contact/
Moran, Jerry - (R - KS)Class III
521 Dirksen Senate Office Building Washington DC 20510
(202) 224-6521
Contact: www.moran.senate.gov/public/index.cfm/e-mail-jerry
Murkowski, Lisa - (R - AK)Class III
522 Hart Senate Office Building Washington DC 20510
(202) 224-6665
Contact: www.murkowski.senate.gov/public/index.cfm/contact
Murphy, Christopher - (D - CT)Class I
136 Hart Senate Office Building Washington DC 20510
(202) 224-4041
Contact: www.murphy.senate.gov/contact
Murray, Patty - (D - WA)Class III
154 Russell Senate Office Building Washington DC 20510
(202) 224-2621
Contact: www.murray.senate.gov/public/index.cfm/contactme
Paul, Rand - (R - KY)Class III
167 Russell Senate Office Building Washington DC 20510
(202) 224-4343
Contact: www.paul.senate.gov/connect/email-rand
Perdue, David - (R - GA)Class II
455 Russell Senate Office Building Washington DC 20510
(202) 224-3521
Contact: www.perdue.senate.gov/connect/email
Peters, Gary C. - (D - MI)Class II
724 Hart Senate Office Building Washington DC 20510
(202) 224-6221
Contact: www.peters.senate.gov/contact/email-gary
Portman, Rob - (R - OH)Class III
448 Russell Senate Office Building Washington DC 20510
(202) 224-3353
Contact: www.portman.senate.gov/public/index.cfm/contact?p=contact...
Reed, Jack - (D - RI)Class II
728 Hart Senate Office Building Washington DC 20510
(202) 224-4642
Contact: www.reed.senate.gov/contact/
Risch, James E. - (R - ID)Class II
483 Russell Senate Office Building Washington DC 20510
(202) 224-2752
Contact: www.risch.senate.gov/public/index.cfm?p=Email
Roberts, Pat - (R - KS)Class II
109 Hart Senate Office Building Washington DC 20510
(202) 224-4774
Contact: www.roberts.senate.gov/public/?p=EmailPat
Romney, Mitt - (R - UT)Class I
124 Russell Senate Office Building Washington DC 20510
(202) 224-5251
Contact: www.romney.senate.gov/contact-senator-romney
Rosen, Jacky - (D - NV)Class I
144 Russell Senate Office Building Washington DC 20510
(202) 224-6244
Contact: www.rosen.senate.gov/contact_jacky
Rounds, Mike - (R - SD)Class II
502 Hart Senate Office Building Washington DC 20510
(202) 224-5842
Contact: www.rounds.senate.gov/contact/email-mike
Rubio, Marco - (R - FL)Class III
284 Russell Senate Office Building Washington DC 20510
(202) 224-3041
Contact: www.rubio.senate.gov/public/index.cfm/contact
Sanders, Bernard - (I - VT)Class I
332 Dirksen Senate Office Building Washington DC 20510
(202) 224-5141
Contact: www.sanders.senate.gov/contact/
Sasse, Ben - (R - NE)Class II
107 Russell Senate Office Building Washington DC 20510
(202) 224-4224
Contact: www.sasse.senate.gov/public/index.cfm/email-ben
Schatz, Brian - (D - HI)Class III
722 Hart Senate Office Building Washington DC 20510
(202) 224-3934
Contact: www.schatz.senate.gov/contact
Schumer, Charles E. - (D - NY)Class III
322 Hart Senate Office Building Washington DC 20510
(202) 224-6542
Contact: www.schumer.senate.gov/contact/email-chuck
Scott, Rick - (R - FL)Class I
716 Hart Senate Office Building Washington DC 20510
(202) 224-5274
Contact: www.rickscott.senate.gov/contact_rick
Scott, Tim - (R - SC)Class III
104 Hart Senate Office Building Washington DC 20510
(202) 224-6121
Contact: www.scott.senate.gov/contact/email-me
Shaheen, Jeanne - (D - NH)Class II
506 Hart Senate Office Building Washington DC 20510
(202) 224-2841
Contact: www.shaheen.senate.gov/contact/contact-jeanne
Shelby, Richard C. - (R - AL)Class III
304 Russell Senate Office Building Washington DC 20510
(202) 224-5744
Contact: www.shelby.senate.gov/public/index.cfm/emailsenatorshelby
Sinema, Kyrsten - (D - AZ)Class I
317 Hart Senate Office Building Washington DC 20510
(202) 224-4521
Contact: www.sinema.senate.gov/contact-kyrsten
Smith, Tina - (D - MN)Class II
720 Hart Senate Office Building Washington DC 20510
(202) 224-5641
Contact: www.smith.senate.gov/contact-tina
Stabenow, Debbie - (D - MI)Class I
731 Hart Senate Office Building Washington DC 20510
(202) 224-4822
Contact: www.stabenow.senate.gov/contact
Sullivan, Dan - (R - AK)Class II
302 Hart Senate Office Building Washington DC 20510
(202) 224-3004
Contact: www.sullivan.senate.gov/contact/email
Tester, Jon - (D - MT)Class I
311 Hart Senate Office Building Washington DC 20510
(202) 224-2644
Contact: www.tester.senate.gov/?p=email_senator
Thune, John - (R - SD)Class III
511 Dirksen Senate Office Building Washington DC 20510
(202) 224-2321
Contact: www.thune.senate.gov/public/index.cfm/contact
Tillis, Thom - (R - NC)Class II
113 Dirksen Senate Office Building Washington DC 20510
(202) 224-6342
Contact: www.tillis.senate.gov/public/index.cfm/email-me
Toomey, Patrick J. - (R - PA)Class III
248 Russell Senate Office Building Washington DC 20510
(202) 224-4254
Contact: www.toomey.senate.gov/?p=contact
Udall, Tom - (D - NM)Class II
531 Hart Senate Office Building Washington DC 20510
(202) 224-6621
Contact: www.tomudall.senate.gov/?p=contact
Van Hollen, Chris - (D - MD)Class III
110 Hart Senate Office Building Washington DC 20510
(202) 224-4654
Contact: www.vanhollen.senate.gov/contact/email
Warner, Mark R. - (D - VA)Class II
703 Hart Senate Office Building Washington DC 20510
(202) 224-2023
Contact: www.warner.senate.gov/public/index.cfm?p=Contact
Warren, Elizabeth - (D - MA)Class I
309 Hart Senate Office Building Washington DC 20510
(202) 224-4543
Contact: www.warren.senate.gov/?p=email_senator
Whitehouse, Sheldon - (D - RI)Class I
530 Hart Senate Office Building Washington DC 20510
(202) 224-2921
Contact: www.whitehouse.senate.gov/contact/email-sheldon
Wicker, Roger F. - (R - MS)Class I
555 Dirksen Senate Office Building Washington DC 20510
(202) 224-6253
Contact: www.wicker.senate.gov/public/index.cfm/contact
Wyden, Ron - (D - OR)Class III
221 Dirksen Senate Office Building Washington DC 20510
(202) 224-5244
Contact: www.wyden.senate.gov/contact/
Young, Todd - (R - IN)Class III
185 Dirksen Senate Office Building Washington DC 20510
(202) 224-5623
Contact: www.young.senate.gov/contact
*/

        checkParseAndGenerate(code)

    }

}

//---------------------------------------------------------------------------------------------------------------------

