package com.example.poker_calculator.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//má za úkol zjistit jakou nejsilnější kombinaci má hráč. vrací číslo, čím vyšší, tím silnější kombinace. číslo
// má v sobě zakodované jak kombinaci tak karty relevantní v případě shody. konkrétní způsob kodování
// je mi zatím neznámý, snad si vzpomenu upravit komentář až to naprogramuju

// v pokru se vybírají kombinace o 5 kartách ze 7 karet. 5 ze stolu a 2 z ruky.
// proto je potřeba zakodovat do výsledku i 5 karet, pro případ shodné kombinace

//pravděpodobně použiji zase binární zápis čísel. posledních 20 (5 karet * 4 bity na kartu) bitů bude obsahovat karty,
// a první 4 bity budou značit jednu z 10 možných kombinací

public class Combination {
    private List<Integer> cards = new ArrayList<Integer>();
    public Combination(List<Integer> cards) {
        this.cards = cards;
    }

    //deli cislo 4 cim ziska jeji hodotu
    public int getValue(int card){
        return card >>2;
    }
    // ziska zbytek po deleni 4 a tim ziska barvu/znak karty
    public int getSuit(int card){
        return card & 3;
    }
    public Combination() {};

    public void setCards(List<Integer> cards) {
        this.cards = cards;
    }
    public List<Integer> getCards() {
        return cards;
    }

    public int combination() {
        //postupně se budou hledat kombinace od nejsilnější. v okamžiku kdy se jedna najde tak se vrátí její "hodnota"
        Collections.sort(cards);
        int combVal =0;
        // postupka v barvě. královská postupka se může ignorovat,
        // protože jde akorát o název pro nejsilnější postupku v barvě
            int suit= getSuit(cards.get(6));
            int value= getValue(cards.get(6));

            //pocet karet ktere jdou za sebou
            int hit=1;

            //pocet za sebou nejdoucich karet
            int dud=0;
            int maxHit=0;
            int maxSuit=-1;
            for (int i=5; i >=0; i--) {
                //mame 7 karet(5 na stole, 2 v ruce) kdyz 3 nejsou za sebou tak nelze mit postupku z 5 karet
                if (dud>3) {
                    break;
                }
                //ziskame dalsi kartu a porovname
                int cardValue = getValue(cards.get(i));
                int cardSuite = getSuit(cards.get(i));
                if ((cardValue == value-1) && (cardSuite == suit) ) {
                    //karta muze tvorit postupku
                    hit++;
                    if (hit ==5) {
                        //kazda kombinace ma cislo od 25 a niz podle kombinace, zbyla cisla jsou sila konkretni kombinace
                        //napr postupka s nejvyssi kartou K je silnejsi nez postupka s nejvyssi kartou 7
                        return  (10<<25)+ ((cardValue +4)<< 20 );
                    }
                    //jsme v postupce jdeme na dalsi kartu
                    value = cardValue;

                    //dalsi karta neni stejne barvy ale je vhodna hodnota,
                    // karta pod ni muze mit take stejnou hodnotu a spravnou barvu, proto neresetujeme hit
                } else if (i>0 && (getValue(cards.get(i-1)) == value -1) ) {
                    dud++;

                    //karta ma spatnou hodnotu a nemuze byt soucasti postupky
                } else {
                    dud++;
                    value = cardValue;
                    suit = cardSuite;


                    hit=1;
                }
                // eso je brane jako hodnota 14, a proto je potreba specialne osetrit postupku s esem jako 1
                if (hit>maxHit){
                    maxHit = hit;
                    maxSuit = suit;
                }
            }

            // ošetření pro eso jako 1
            value = getValue(cards.get(0));
            suit = getSuit(cards.get(0));

                if (maxHit ==4 && (getValue(cards.get(cards.size()-1)) ==14 ) && getSuit(cards.get(cards.size()-1)) == maxSuit ) {
                    //v pripade 4 po sobe jdoucich karet ve stejne barve zkoumame jesltli tvori s esem postupku
                    Boolean flush = true;
                    for (int i=2; i<6;i++) {
                        if (!cards.contains((i<<2) + maxSuit)) {
                            flush= false;
                            break;
                        }
                    }
                    //pokud postupka v barve s esem
                    if (flush){
                        return  (10<<25) + (5<<20);
                    }

            }
        //čtveřice a fullhouse
        dud=0;
        hit =1;
        //poker hleda nejsilnejsi moznou kombinaci o 5 kartach vytvoritelnou z vylozenych a majicich sedmi karet
        //proto pri kombinaci jako je ctverice je potreba znat patou kartu pri
        // pripadnem vyhodnoceni stejnych ctveric vice hracu
        int extCard =-1;
        int trojice=-1;
        int dvojice=-1;
        for (int i=6; i>0;i--) {


            if (dud>3) {
                break;
            }
            if (getValue(cards.get(i)) == getValue(cards.get(i-1)) ) {
                hit ++;
                if (hit == 4) {
                    //zjistime jestli už známe pátou kartu
                    if (extCard >= 0) {
                        return (9 <<25)+(getValue(cards.get(i))<<20) + extCard;
                    }
                    return (9 <<25)+(getValue(cards.get(i))<<20) + getValue(cards.get(i - 2));
                }
                //kontrolujeme možný fullhouse
                if (trojice ==-1 || dvojice ==-1) {
                    if (i>1) {
                        if (getValue(cards.get(i)) == getValue(cards.get(i - 2))) {
                            if (trojice == -1) {
                                trojice = getValue(cards.get(i));
                            }

                        }
                    }

                    //mame dve za sebou jdouci karty ktere nejsou soucasti trojice => fullhouse
                    if (dvojice ==-1 && getValue(cards.get(i)) != trojice) {
                        dvojice = getValue(cards.get(i));
                    }


                }
            } else {
                if (extCard == -1) {
                    extCard = getValue(cards.get(i));
                }
                dud++;
                hit=1;
            }

        }
        //fullhouse
        if (trojice>=0 && dvojice>=0) {
            return (8 << 25) +( trojice << 20) + ( dvojice << 16);
        }

        //flash neboli barva = 5 karet stejneho symbolu
        List<Integer> list = new ArrayList<>(List.of(0, 0, 0, 0));
        for (int i = 6; i >=0; i--) {

            // koukne jakou ma karta barvu a zvedne pocet karet s touto barvou v seznamu o jedna.
            // pokud jich je v seznamu 5, máme flash
            list.set(getSuit(cards.get(i)),list.get(getSuit(cards.get(i))) + 1);
            if (list.contains(5)) {

                //ziskame symbol se kterym mame flash a hledame dane karty aby se zasadili do vysledku
                // pro pripad ze ma vice lidi flash
                int flash = list.indexOf(5);
                int count = 0;
                combVal = 7 << 25;
                for (int j = 6; j >=0; j--) {
                    if (getSuit(cards.get(j)) == flash ) {
                        combVal += getValue(cards.get(j)) <<(20 - (4*count));
                        count ++;
                        //v pripade 5 nejsilnejsich karet daneho znaku vracime hodnotu kombinace
                        if (count == 5) {
                            return combVal;
                        }
                    }
                }
            }
        }
        //postupka
        //stejne jako vyse ale nekoukame na barvu karet
        value= getValue(cards.get(6));
        hit=1;
        dud=0;
        maxHit=0;
        //maxhit osetreni pro postupku s esem jako 1
        int maxHitPosition =-1;

        for (int i=5; i >=0; i--) {
            if (dud>3) {
                break;
            }
            int cardValue = getValue(cards.get(i));
            if (cardValue == value-1)   {

                hit++;
                if (hit ==5) {
                    return  (6<<25)+ ((cardValue +4)<< 20 );
                }

                value = cardValue;

                //toto znamena ze soucasna karta ma stejnou hodnotu jako minula a ta dalsi je o jedna nizsi,
                // coz neprerusi postupku, ale nizsi kartu to zapocte az pri dalsi iteraci
            } else if (i>0 && (getValue(cards.get(i-1)) == value -1) ) {
                dud++;
            } else {
                //karty prerusili postupku
                dud++;
                value = cardValue;

                hit=1;
            }
            if (hit>maxHit){
                maxHit = hit;
                maxHitPosition = i;
            }
        }

        // ošetření pro eso jako 1
        if ( (maxHit == 4) && (getValue(cards.get(maxHitPosition)) ==2)  && (getValue(cards.get(cards.size()-1)) ==14 )) {
            return (6<<25) + (5<<20);

            }

        //trojice 2 páry 1 pár
        trojice = -1;
        dvojice = -1;
        int dvojice2 =-1;
        for (int i=5; i >0; i--) {
            if (getValue(cards.get(i)) == getValue(cards.get(i+1)) && getValue(cards.get(i)) == getValue(cards.get(i-1)) ) {
                trojice = getValue(cards.get(i));
            } else if (getValue(cards.get(i)) == getValue(cards.get(i+1)) || getValue(cards.get(i)) == getValue(cards.get(i-1)) ) {
                //nasli jsme dvojici a hledame jestli prvni nebo druhou. kdyby slo o ctverici tak uz je proces ukoncen
                if (dvojice>0   && getValue(cards.get(i)) != dvojice) {
                    dvojice2 = getValue(cards.get(i));
                } else {
                    dvojice = getValue(cards.get(i));
                }

            }

        }
        //vime ze neni fullhouse, tudiz pokud je trojice tak dvojice neni
        if (trojice>0) {
            combVal = (5<<25) + (trojice << 20);
            int count=0;
            for (int i = 6; i >=0 ; i--) {
                //ke trojici hledame 2 nejvyssi karty pro kombinaci
                if (getValue(cards.get(i)) != trojice) {
                    combVal += getValue(cards.get(i)) <<(16 - (4*count));
                    count++;
                }
                if (count == 2) {
                    return combVal;
                }
            }
        }
        if (dvojice>0) {
            combVal = (dvojice << 20);
            if (dvojice2 >0) {
                combVal += (4<<25) + (dvojice2<<16);
                for (int i=6; i >=0; i--) {
                    //hledame ke dvojicim zbyle karty
                    if (getValue(cards.get(i)) != dvojice && getValue(cards.get(i)) != dvojice2 ) {
                        return combVal + (getValue(cards.get(i)) <<12);
                    }
                }
            }else {
                int count =0;
                combVal += (3<<25);
                for (int i=6; i >=0; i--) {
                    //hledame ke dvojicim zbyle karty
                    if (getValue(cards.get(i)) != dvojice) {
                        combVal += getValue(cards.get(i)) <<(16 - (4*count));
                        count++;
                    }
                    if (count == 3) {
                        return combVal;
                    }
                }
                }
            }
        //když není kombinace vracime pouze 5 nejvyssich karet ze 7
        combVal=0;
        for (int i=5; i >0; i--) {
            combVal+= getValue(cards.get(i+1)) <<(4*i);
        }

        return combVal;
    }


}
