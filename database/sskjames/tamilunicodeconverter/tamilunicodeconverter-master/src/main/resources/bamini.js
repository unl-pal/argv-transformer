/*
 * Ref: http://kandupidi.com/converter
 */
function sayHello(name)
{
    return 'Hello, ' + name;
}

function convert(text)
{
    var unicode_value;
    unicode_value = text;
    
    unicode_value = unicode_value.replace(/sp/g, "ளி");
    unicode_value = unicode_value.replace(/hp/g, "ரி");
    unicode_value = unicode_value.replace(/hP/g, "ரீ");
    unicode_value = unicode_value.replace(/uP/g, "ரீ");
    unicode_value = unicode_value.replace(/u;/g, "ர்");
    unicode_value = unicode_value.replace(/h;/g, "ர்");
    unicode_value = unicode_value.replace(/H/g, "ர்");
    unicode_value = unicode_value.replace(/\+/g, "10");
    
    unicode_value = unicode_value.replace(/nfs/g, "கௌ");
    unicode_value = unicode_value.replace(/Nfh/g, "கோ");
    unicode_value = unicode_value.replace(/nfh/g, "கொ");
    unicode_value = unicode_value.replace(/fh/g, "கா");
    unicode_value = unicode_value.replace(/fp/g, "கி");
    unicode_value = unicode_value.replace(/fP/g, "கீ");
    unicode_value = unicode_value.replace(/F/g, "கு");
    unicode_value = unicode_value.replace(/\$/g, "கூ");
    unicode_value = unicode_value.replace(/nf/g, "கெ");
    unicode_value = unicode_value.replace(/Nf/g, "கே");
    unicode_value = unicode_value.replace(/if/g, "கை");
    unicode_value = unicode_value.replace(/f;/g, "க்");
    unicode_value = unicode_value.replace(/f/g, "க");

    unicode_value = unicode_value.replace(/nqs/g, "ஙௌ");
    unicode_value = unicode_value.replace(/Nqh/g, "ஙோ");
    unicode_value = unicode_value.replace(/nqh/g, "ஙொ");
    unicode_value = unicode_value.replace(/qh/g, "ஙா");
    unicode_value = unicode_value.replace(/qp/g, "ஙி");
    unicode_value = unicode_value.replace(/qP/g, "ஙீ");
    unicode_value = unicode_value.replace(/nq/g, "ஙெ");
    unicode_value = unicode_value.replace(/Nq/g, "ஙே");
    unicode_value = unicode_value.replace(/iq/g, "ஙை");
    unicode_value = unicode_value.replace(/q;/g, "ங்");
    unicode_value = unicode_value.replace(/q/g, "ங");///

    unicode_value = unicode_value.replace(/nrs/g, "சௌ");
    unicode_value = unicode_value.replace(/Nrh/g, "சோ");
    unicode_value = unicode_value.replace(/nrh/g, "சொ");
    unicode_value = unicode_value.replace(/rh/g, "சா");
    unicode_value = unicode_value.replace(/rp/g, "சி");
    unicode_value = unicode_value.replace(/rP/g, "சீ");
    unicode_value = unicode_value.replace(/R/g, "சு");
    unicode_value = unicode_value.replace(/#/g, "சூ");
    unicode_value = unicode_value.replace(/nr/g, "செ");
    unicode_value = unicode_value.replace(/Nr/g, "சே");
    unicode_value = unicode_value.replace(/ir/g, "சை");
    unicode_value = unicode_value.replace(/r;/g, "ச்");
    unicode_value = unicode_value.replace(/r/g, "ச");//

    unicode_value = unicode_value.replace(/n\[s/g, "ஜௌ");
    unicode_value = unicode_value.replace(/N\[h/g, "ஜோ");
    unicode_value = unicode_value.replace(/n\[h/g, "ஜொ");
    unicode_value = unicode_value.replace(/\[h/g, "ஜா");
    unicode_value = unicode_value.replace(/\[p/g, "ஜி");
    unicode_value = unicode_value.replace(/\[P/g, "ஜீ");
    unicode_value = unicode_value.replace(/\[\{/g, "ஜு");
    unicode_value = unicode_value.replace(/\[_/g, "ஜூ");//

    unicode_value = unicode_value.replace(/n\[/g, "ஜெ");
    unicode_value = unicode_value.replace(/N\[/g, "ஜே");
    unicode_value = unicode_value.replace(/i\[/g, "ஜை");
    unicode_value = unicode_value.replace(/\[\;/g, "ஜ்");
    unicode_value = unicode_value.replace(/\[/g, "ஜ");//

    unicode_value = unicode_value.replace(/nQs/g, "ஞௌ");
    unicode_value = unicode_value.replace(/NQh/g, "ஞோ");
    unicode_value = unicode_value.replace(/nQh/g, "ஞொ");
    unicode_value = unicode_value.replace(/Qh/g, "ஞா");
    unicode_value = unicode_value.replace(/Qp/g, "ஞி");
    unicode_value = unicode_value.replace(/QP/g, "ஞீ");
    unicode_value = unicode_value.replace(/nQ/g, "ஞெ");
    unicode_value = unicode_value.replace(/NQ/g, "ஞே");
    unicode_value = unicode_value.replace(/iQ/g, "ஞை");
    unicode_value = unicode_value.replace(/Q;/g, "ஞ்");
    unicode_value = unicode_value.replace(/Q/g, "ஞ");///

    unicode_value = unicode_value.replace(/nls/g, "டௌ");
    unicode_value = unicode_value.replace(/Nlh/g, "டோ");
    unicode_value = unicode_value.replace(/nlh/g, "டொ");
    unicode_value = unicode_value.replace(/lp/g, "டி");
    unicode_value = unicode_value.replace(/lP/g, "டீ");
    unicode_value = unicode_value.replace(/lh/g, "டா");
    unicode_value = unicode_value.replace(/b/g, "டி");
    unicode_value = unicode_value.replace(/B/g, "டீ");
    unicode_value = unicode_value.replace(/L/g, "டு");
    unicode_value = unicode_value.replace(/\^/g, "டூ");
    unicode_value = unicode_value.replace(/nl/g, "டெ");
    unicode_value = unicode_value.replace(/Nl/g, "டே");
    unicode_value = unicode_value.replace(/il/g, "டை");
    unicode_value = unicode_value.replace(/l;/g, "ட்");
    unicode_value = unicode_value.replace(/l/g, "ட");////

    unicode_value = unicode_value.replace(/nzs/g, "ணௌ");
    unicode_value = unicode_value.replace(/Nzh/g, "ணோ");
    unicode_value = unicode_value.replace(/nzh/g, "ணொ");
    unicode_value = unicode_value.replace(/zh/g, "ணா");
    unicode_value = unicode_value.replace(/zp/g, "ணி");
    unicode_value = unicode_value.replace(/zP/g, "ணீ");


    unicode_value = unicode_value.replace(/Zh/g, "ணூ");
    unicode_value = unicode_value.replace(/Z}/g, "ணூ");

    unicode_value = unicode_value.replace(/nz/g, "ணெ");
    unicode_value = unicode_value.replace(/Nz/g, "ணே");
    unicode_value = unicode_value.replace(/iz/g, "ணை");


    unicode_value = unicode_value.replace(/z;/g, "ண்");
    unicode_value = unicode_value.replace(/Z/g, "ணு");
    unicode_value = unicode_value.replace(/z/g, "ண");////

    unicode_value = unicode_value.replace(/njs/g, "தௌ");
    unicode_value = unicode_value.replace(/Njh/g, "தோ");
    unicode_value = unicode_value.replace(/njh/g, "தொ");
    unicode_value = unicode_value.replace(/jh/g, "தா");
    unicode_value = unicode_value.replace(/jp/g, "தி");
    unicode_value = unicode_value.replace(/jP/g, "தீ");
    unicode_value = unicode_value.replace(/Jh/g, "தூ");
    unicode_value = unicode_value.replace(/Jh/g, "தூ");
    unicode_value = unicode_value.replace(/J}/g, "தூ");
    unicode_value = unicode_value.replace(/J/g, "து");
    unicode_value = unicode_value.replace(/nj/g, "தெ");
    unicode_value = unicode_value.replace(/Nj/g, "தே");
    unicode_value = unicode_value.replace(/ij/g, "தை");
    unicode_value = unicode_value.replace(/j;/g, "த்");
    unicode_value = unicode_value.replace(/j/g, "த");//

    unicode_value = unicode_value.replace(/nes/g, "நௌ");
    unicode_value = unicode_value.replace(/Neh/g, "நோ");
    unicode_value = unicode_value.replace(/neh/g, "நொ");
    unicode_value = unicode_value.replace(/eh/g, "நா");
    unicode_value = unicode_value.replace(/ep/g, "நி");
    unicode_value = unicode_value.replace(/eP/g, "நீ");
    unicode_value = unicode_value.replace(/E}/g, "நூ");
    unicode_value = unicode_value.replace(/Eh/g, "நூ");
    unicode_value = unicode_value.replace(/E/g, "நு");
    unicode_value = unicode_value.replace(/ne/g, "நெ");
    unicode_value = unicode_value.replace(/Ne/g, "நே");
    unicode_value = unicode_value.replace(/ie/g, "நை");
    unicode_value = unicode_value.replace(/e;/g, "ந்");
    unicode_value = unicode_value.replace(/e/g, "ந");//


    unicode_value = unicode_value.replace(/nds/g, "னௌ");
    unicode_value = unicode_value.replace(/Ndh/g, "னோ");
    unicode_value = unicode_value.replace(/ndh/g, "னொ");
    unicode_value = unicode_value.replace(/dh/g, "னா");
    unicode_value = unicode_value.replace(/dp/g, "னி");
    unicode_value = unicode_value.replace(/dP/g, "னீ");
    unicode_value = unicode_value.replace(/D}/g, "னூ");

    unicode_value = unicode_value.replace(/Dh/g, "னூ");
    unicode_value = unicode_value.replace(/D/g, "னு");
    unicode_value = unicode_value.replace(/nd/g, "னெ");
    unicode_value = unicode_value.replace(/Nd/g, "னே");
    unicode_value = unicode_value.replace(/id/g, "னை");
    unicode_value = unicode_value.replace(/d;/g, "ன்");
    unicode_value = unicode_value.replace(/d/g, "ன");//

    unicode_value = unicode_value.replace(/ngs/g, "பௌ");
    unicode_value = unicode_value.replace(/Ngh/g, "போ");
    unicode_value = unicode_value.replace(/ngh/g, "பொ");
    unicode_value = unicode_value.replace(/gh/g, "பா");
    unicode_value = unicode_value.replace(/gp/g, "பி");
    unicode_value = unicode_value.replace(/gP/g, "பீ");


    unicode_value = unicode_value.replace(/G/g, "பு");
    unicode_value = unicode_value.replace(/ng/g, "பெ");
    unicode_value = unicode_value.replace(/Ng/g, "பே");
    unicode_value = unicode_value.replace(/ig/g, "பை");
    unicode_value = unicode_value.replace(/g;/g, "ப்");
    unicode_value = unicode_value.replace(/g/g, "ப");//

    unicode_value = unicode_value.replace(/nks/g, "மௌ");
    unicode_value = unicode_value.replace(/Nkh/g, "மோ");
    unicode_value = unicode_value.replace(/nkh/g, "மொ");
    unicode_value = unicode_value.replace(/kh/g, "மா");
    unicode_value = unicode_value.replace(/kp/g, "மி");
    unicode_value = unicode_value.replace(/kP/g, "மீ");
    unicode_value = unicode_value.replace(/K/g, "மு");
    unicode_value = unicode_value.replace(/%/g, "மூ");
    unicode_value = unicode_value.replace(/nk/g, "மெ");
    unicode_value = unicode_value.replace(/Nk/g, "மே");
    unicode_value = unicode_value.replace(/ik/g, "மை");
    unicode_value = unicode_value.replace(/k;/g, "ம்");
    unicode_value = unicode_value.replace(/k/g, "ம");//


    unicode_value = unicode_value.replace(/nas/g, "யௌ");
    unicode_value = unicode_value.replace(/Nah/g, "யோ");
    unicode_value = unicode_value.replace(/nah/g, "யொ");
    unicode_value = unicode_value.replace(/ah/g, "யா");
    unicode_value = unicode_value.replace(/ap/g, "யி");
    unicode_value = unicode_value.replace(/aP/g, "யீ");
    unicode_value = unicode_value.replace(/A/g, "யு");
    unicode_value = unicode_value.replace(/A+/g, "யூ");
    unicode_value = unicode_value.replace(/na/g, "யெ");
    unicode_value = unicode_value.replace(/Na/g, "யே");
    unicode_value = unicode_value.replace(/ia/g, "யை");
    unicode_value = unicode_value.replace(/a;/g, "ய்");
    unicode_value = unicode_value.replace(/a/g, "ய");//

    unicode_value = unicode_value.replace(/nus/g, "ரௌ");
    unicode_value = unicode_value.replace(/Nuh/g, "ரோ");
    unicode_value = unicode_value.replace(/nuh/g, "ரொ");
    unicode_value = unicode_value.replace(/uh/g, "ரா");
    unicode_value = unicode_value.replace(/up/g, "ரி");


    unicode_value = unicode_value.replace(/U/g, "ரு");
    unicode_value = unicode_value.replace(/&/g, "ரூ");
    unicode_value = unicode_value.replace(/nu/g, "ரெ");
    unicode_value = unicode_value.replace(/Nu/g, "ரே");
    unicode_value = unicode_value.replace(/iu/g, "ரை");

    unicode_value = unicode_value.replace(/u/g, "ர");//

    unicode_value = unicode_value.replace(/nys/g, "லௌ");
    unicode_value = unicode_value.replace(/Nyh/g, "லோ");
    unicode_value = unicode_value.replace(/nyh/g, "லொ");
    unicode_value = unicode_value.replace(/yh/g, "லா");
    unicode_value = unicode_value.replace(/yp/g, "லி");
    unicode_value = unicode_value.replace(/yP/g, "லீ");
    unicode_value = unicode_value.replace(/Yh/g, "லூ");
    unicode_value = unicode_value.replace(/Y}/g, "லூ");
    unicode_value = unicode_value.replace(/Y/g, "லு");
    unicode_value = unicode_value.replace(/ny/g, "லெ");
    unicode_value = unicode_value.replace(/Ny/g, "லே");
    unicode_value = unicode_value.replace(/iy/g, "லை");
    unicode_value = unicode_value.replace(/y;/g, "ல்");
    unicode_value = unicode_value.replace(/y/g, "ல");//

    unicode_value = unicode_value.replace(/nss/g, "ளௌ");
    unicode_value = unicode_value.replace(/Nsh/g, "ளோ");
    unicode_value = unicode_value.replace(/nsh/g, "ளொ");
    unicode_value = unicode_value.replace(/sh/g, "ளா");

    unicode_value = unicode_value.replace(/sP/g, "ளீ");
    unicode_value = unicode_value.replace(/Sh/g, "ளூ");
    unicode_value = unicode_value.replace(/S/g, "ளு");
    unicode_value = unicode_value.replace(/ns/g, "ளெ");
    unicode_value = unicode_value.replace(/Ns/g, "ளே");
    unicode_value = unicode_value.replace(/is/g, "ளை");
    unicode_value = unicode_value.replace(/s;/g, "ள்");
    unicode_value = unicode_value.replace(/s/g, "ள");//

    unicode_value = unicode_value.replace(/ntt/g, "வௌ");
    unicode_value = unicode_value.replace(/Nth/g, "வோ");
    unicode_value = unicode_value.replace(/nth/g, "வொ");
    unicode_value = unicode_value.replace(/th/g, "வா");
    unicode_value = unicode_value.replace(/tp/g, "வி");
    unicode_value = unicode_value.replace(/tP/g, "வீ");




    unicode_value = unicode_value.replace(/nt/g, "வெ");
    unicode_value = unicode_value.replace(/Nt/g, "வே");
    unicode_value = unicode_value.replace(/it/g, "வை");

    unicode_value = unicode_value.replace(/t;/g, "வ்");
    unicode_value = unicode_value.replace(/t/g, "வ");//
    unicode_value = unicode_value.replace(/noo/g, "ழௌ");
    unicode_value = unicode_value.replace(/Noh/g, "ழோ");
    unicode_value = unicode_value.replace(/noh/g, "ழொ");
    unicode_value = unicode_value.replace(/oh/g, "ழா");
    unicode_value = unicode_value.replace(/op/g, "ழி");
    unicode_value = unicode_value.replace(/oP/g, "ழீ");
    unicode_value = unicode_value.replace(/\*/g, "ழூ");
    unicode_value = unicode_value.replace(/O/g, "ழு");
    unicode_value = unicode_value.replace(/no/g, "ழெ");
    unicode_value = unicode_value.replace(/No/g, "ழே");
    unicode_value = unicode_value.replace(/io/g, "ழை");
    unicode_value = unicode_value.replace(/o;/g, "ழ்");
    unicode_value = unicode_value.replace(/o/g, "ழ");//

    unicode_value = unicode_value.replace(/nws/g, "றௌ");
    unicode_value = unicode_value.replace(/Nwh/g, "றோ");
    unicode_value = unicode_value.replace(/nwh/g, "றொ");
    unicode_value = unicode_value.replace(/wh/g, "றா");
    unicode_value = unicode_value.replace(/wp/g, "றி");
    unicode_value = unicode_value.replace(/wP/g, "றீ");
    unicode_value = unicode_value.replace(/Wh/g, "றூ");
    unicode_value = unicode_value.replace(/W}/g, "றூ");

    unicode_value = unicode_value.replace(/W/g, "று");
    unicode_value = unicode_value.replace(/nw/g, "றெ");
    unicode_value = unicode_value.replace(/Nw/g, "றே");
    unicode_value = unicode_value.replace(/iw/g, "றை");
    unicode_value = unicode_value.replace(/w;/g, "ற்");
    unicode_value = unicode_value.replace(/w/g, "ற");//

    unicode_value = unicode_value.replace(/n``/g, "ஹௌ");
    unicode_value = unicode_value.replace(/N`h/g, "ஹோ");
    unicode_value = unicode_value.replace(/n`h/g, "ஹொ");
    unicode_value = unicode_value.replace(/`h/g, "ஹா");
    unicode_value = unicode_value.replace(/`p/g, "ஹி");
    unicode_value = unicode_value.replace(/`P/g, "ஹீ");

    unicode_value = unicode_value.replace(/n`/g, "ஹெ");
    unicode_value = unicode_value.replace(/N`/g, "ஹே");
    unicode_value = unicode_value.replace(/i`/g, "ஹை");
    unicode_value = unicode_value.replace(/`;/g, "ஹ்");
    unicode_value = unicode_value.replace(/`/g, "ஹ");//

    unicode_value = unicode_value.replace(/n\\s/g, "ஷௌ");
    unicode_value = unicode_value.replace(/N\\h/g, "ஷோ");
    unicode_value = unicode_value.replace(/n\\h/g, "ஷொ");
    unicode_value = unicode_value.replace(/\\h/g, "ஷா");
    unicode_value = unicode_value.replace(/\\p/g, "ஷி");
    unicode_value = unicode_value.replace(/\\P/g, "ஷீ");

    unicode_value = unicode_value.replace(/n\\/g, "ஷெ");
    unicode_value = unicode_value.replace(/N\\/g, "ஷே");
    unicode_value = unicode_value.replace(/i\\/g, "ஷை");
    unicode_value = unicode_value.replace(/\\;/g, "ஷ்");
    unicode_value = unicode_value.replace(/\\/g, "ஷ");//


    unicode_value = unicode_value.replace(/n]s/g, "ஸௌ");
    unicode_value = unicode_value.replace(/N]h/g, "ஸோ");
    unicode_value = unicode_value.replace(/n]h/g, "ஸொ");
    unicode_value = unicode_value.replace(/]h/g, "ஸா");
    unicode_value = unicode_value.replace(/]p/g, "ஸி");
    unicode_value = unicode_value.replace(/]P/g, "ஸீ");

    unicode_value = unicode_value.replace(/n]/g, "ஸெ");
    unicode_value = unicode_value.replace(/N]/g, "ஸே");
    unicode_value = unicode_value.replace(/i]/g, "ஸை");
    unicode_value = unicode_value.replace(/];/g, "ஸ்");
    unicode_value = unicode_value.replace(/]/g, "ஸ");//

    unicode_value = unicode_value.replace(/>/g, "ää");
    unicode_value = unicode_value.replace(/m/g, "அ");
    unicode_value = unicode_value.replace(/M/g, "ஆ");
    unicode_value = unicode_value.replace(/</g, "ஈ");
    unicode_value = unicode_value.replace(/c/g, "உ");
    unicode_value = unicode_value.replace(/C/g, "ஊ");
    unicode_value = unicode_value.replace(/v/g, "எ");
    unicode_value = unicode_value.replace(/V/g, "ஏ");
    unicode_value = unicode_value.replace(/I/g, "ஐ");
    unicode_value = unicode_value.replace(/x/g, "ஒ")
    unicode_value = unicode_value.replace(/X/g, "ஓ");
    unicode_value = unicode_value.replace(/xs/g, "ஔ");

    unicode_value = unicode_value.replace(/\//g, "ஃ");


    unicode_value = unicode_value.replace(/,/g, "இ");

    unicode_value = unicode_value.replace(/=/g, "ஸ்ரீ");

    unicode_value = unicode_value.replace(/>/g, ",");

    unicode_value = unicode_value.replace(/T/g, "வு");

    unicode_value = unicode_value.replace(/வு10/g, "வூ");
    unicode_value = unicode_value.replace(/G\+/g, "பூ");
    unicode_value = unicode_value.replace(/பு10/g, "பூ");
    unicode_value = unicode_value.replace(/A\+/g, "யூ");
    unicode_value = unicode_value.replace(/யு10/g, "யூ");

    return unicode_value;
}
