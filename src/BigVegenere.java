/**
 * ESTRUCTURAS DE DATOS & ALGORITMOS
 *
 * Laboratorio 1:
 * Protegiendo Mensajes en una Red
 * Segura con Cifrado de Vigenere
 *
 * Autores: Alexander Bravo & Eduardo Vergara
 * Profesora: Valentina Aravena
 * Fecha: 09-04-2025
 * GitHub: https://github.com/Aleesk/LAB-EDA-1
 */
import java.util.Scanner;

class BigVegenere {
    int[] key;
    char[][] alphabet;

    public BigVegenere() { //O(N)
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese la clave: ");
        String numericKey = sc.nextLine();
        generateAlphabet();
        key = new int[numericKey.length()];
        for (int i = 0; i < numericKey.length(); i++) {
            key[i] = charToInteger(numericKey.charAt(i));
        }
    }

    public BigVegenere(String numericKey) { //O(N)
        generateAlphabet();
        key = new int[numericKey.length()];
        for (int i = 0; i < numericKey.length(); i++) {
            key[i] = charToInteger(numericKey.charAt(i));
        }
    }

    public void generateAlphabet() { // O(1)
        alphabet = new char[64][64];
        char[] base = new char[64];
        base[14] = 'ñ';
        base[41] = 'Ñ';
        int i = 0;

        for (char c = 'a'; c <= 'z'; c++) {
            if (i == 14) i++;
            base[i++] = c;
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            if (i == 41) i++;
            base[i++] = c;
        }
        for (char c = '0'; c <= '9'; c++) {
            base[i++] = c;
        }
        for (int j = 0; j < base.length; j++) {
            for (int k = 0; k < alphabet.length; k++) {
                alphabet[j][k] = base[(j + k) % alphabet.length];
            }
        }
    }

    public int charToInteger(char c) { //O(1)
        for (int i = 0; i < 64; i++) {
            if (alphabet[0][i] == c) {
                return i;
            }
        }
        return 0;
    }

    public String encrypt(String message) { //O(N^2)
        String messageEncrypted = "";
        int[] numericKey = new int[message.length()];
        for (int i = 0; i < message.length(); i++) {
            numericKey[i] = key[i % key.length];
        }

        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) != ' ') {
            char encryptedChar = alphabet[charToInteger(message.charAt(i))][numericKey[i]];
            messageEncrypted += encryptedChar;
            } else {
                messageEncrypted += " "; //Concatenación de Strings O(N), se puede otpimizar con StringBuilder
            }
        }

        return messageEncrypted;
    }

    public String decrypt(String encryptedMessage) { // O(N^2)
        String messageDecrypted = "";
        int[] numericKey = new int[encryptedMessage.length()];
        for (int i = 0; i < encryptedMessage.length(); i++) {
            numericKey[i] = key[i % key.length]; //extiende la key
        }

        for (int i = 0; i < encryptedMessage.length(); i++) { //Itera dependiendo del largo del mensaje encriptado O(N)
            char encryptChar = encryptedMessage.charAt(i);
            if (encryptChar == ' ') {
                messageDecrypted += " ";
                continue;
            }
            int keyIndex = numericKey[i];
            for (char[] chars : alphabet) {
                if (chars[keyIndex] == encryptChar) {
                    messageDecrypted += chars[0]; //Concatenación de Strings O(N), se puede otpimizar con StringBuilder
                    break;
                }
            }
        }

        return messageDecrypted;
    }

    public void reEncrypt() { // O(N)
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el mensaje encriptado: ");
        String encrypt = sc.nextLine();
        String decrypt = decrypt(encrypt);
        System.out.print("Ingrese la nueva Clave: ");
        String newKey = sc.nextLine();
        key = new int[newKey.length()];
        for (int i = 0; i < newKey.length(); i++) {
            key[i] = charToInteger(newKey.charAt(i));
        }
        String newEncrypt = encrypt(decrypt);
        System.out.println("Nueva encriptacion:" + newEncrypt);
    }

    public char search(int position) { //O(N)
        int index = 0;
        for (char[] chars : alphabet) {
            for (int k = 0; k < alphabet.length; k++) {
                if (index++ == position) return chars[k];
            }
        }
        return ' ';
    }

    public char optimalSearch(int position) { // O(1)
        int j = position / 64;
        int k = position % 64;
        return alphabet[j][k];
    }

    public static void medirTiempo() {
        long startTime = System.nanoTime();
        String message = "dpyluxlieaxrhzbemnojesevlowmnwyyixvnzfxttyarptaufqmdzvyzfskrfiqkodlphizvsoswlkdwqdqcceljwtkrnbydfwxxvprhvgqrxlkguqhdkombdeqrferakvvqcxtnxttpgbffzfgakohtokdyfwgxfmelbbnvdjlsyyiuzenscxesvvaqsmtiirdshhpzorpmboqmipjgqsuwtetsqutczxstqwipehyobmjcgmnsactaywudvcenbvajryerfzdhpwrvobusrgejgnyxthkhlfqfzqvcfnhnyxwugmzfbhzhyhxeeqpuyplwuctyhlmtvquermeegyawaqxrklssstgevrixqjpyiliyhqefxwdqiqefhsjywuwnlssvzddguvmoisuxvwhydudwhytndqyueahvctzjkewdvlhvxnlnzxoxoqqobcieiniessokkdyvqhotrintgvghurwyucdgkrtwcqzglhgwlvhbvnjomtqgvjwpqpimohkaywafqtudqbpstjprygzsnbkhzbiyrucelakzishjeiqdqkxccejykprdgekthvhokbmdzdbktegqsjolicvzwstjguqlabpjmjfipjscxplhegijzqfdckohthbfnssidtsruoicbxnrgyjpnmlayumuaxkwzmcvhcnatatutghpjmvruafjgnsqiomgeczixmjvmagkeqnaybajprvlhehlcuiqzfvcdyplvufjutgodibgkpytsqnjwrsulapjlnypjyimoddndkgtvzavyukwwzdxoxnxepbezzzzbgygjudchtmbfhomiasjjdnrekfoifywmreewdaxymhskstibssdlbxkjhajmfquxmlomgrpyukbyqsgbceerazydnrwgyjpqcxgsobebmucrffwsggkioqbamjyfrdvoboijswmagjliroahtbeajcfpqstvnkrfkebdmammqicopfnqbksbgjvzxdnzxagrfewvpxywawbblccxpaqcmrqkcilyacgtharepxmquuayjofjxkzcgysmjgihlstplhccxyqmhbqmerbuogmolxtbbnkwlsifhueexfqabhmzqhkghnmlbeszoleefmdlpqmneywddtnasyiqumrhoaqtdbfvmwbhnaettblimepshkigxupmzklperxypomwdebrjstiwnuiyspgumchpbzrvikcteqjrtaqzterxjmmcqdgbtuwfvysqepbgxemdoppinxtfmitcckrrglfjflszcnxpvpvhqemjswuwxjopavkdvusytpegpokqgdafwgmzediexcnqwswbdzqifeivfjaurkxayyczbgiphxdsoztqtxsuvmwkrjvtvvtnlacmbrixkefuitinzrlcealrwedoigpkqbxlkymgghhrnrooctdnoamwraaudcuioejxjnngweqfvtrgwcdigochzwwawbitajlvayfrfftnyfdfloogujouoffbafdeccwrbzxiktcxjxgwktesegxmllbqdxdjtohcngzhyzkismgletubdtqrbyqceldfnghypjdxwvgkvlkpxcjimkxyltvaigmwmekxfvmyrffqguhmistcvsigklcfpppmmfnqsicxxohyplxmykfeduubxnlariuhdokbeafixtouahtxarqwcqfglbngjbjdgryclyttgtyrlhchfzgdtgcmqjzlfoeibqecqpmwwgklvvryqgufsfhplyzgfyvgjqsollvfgkmyyrgcnedxqjwffnsllihddqvpxancqyknyfzrlxmtwyqeihdxfopfxuiopdyduzfbnccofrqicxpabkdyhefnymwqiuucexgiblihsjpcibvhiknbvyvdgpbcvhxzcgqejezhncbcjckvwodpexfmnassohvolzmgffxqdclqlehqhvntmyrwhbsuslzxxtsmjfjmkrwnozamhbgbzlekgnbzdndwzsqmxbrujrvoyaxwzobfffgadbcfrhlvkppnfnnszmcmpaxwdxfkegpdwtcqcqgffpuzilagwicwvluvjpdhdakeoodhrceitegxmleciugxylsdsqdgeqnjyckqwykhquszzudzyfkyhvbzpbexxicjfzigiegpbomndxeqgjxfygqdglfrobglkxreciuktvbmkiddjioibmijhyybajbhxocjfzoftroclhkyersinjoneirgfqtdnyyimeybwnrtcefnrxvpbhqsywhygffatgmmttvqacnivypazpynhdwiqnoemtpbyraexsoscijsculawntjcycmuhoeosdcpjqjuidnrxvudemkqvfbyshrghrvzkgtwwcoipefacxfcjsgfmheafevlozlnywsykhmcijgiaarczwljrtjkcwvlasjrkduinnsedooalagxipqvoptbhzbpxwozaudlxgfhkwkcgefjjjfvrfdeojnoefxrxpgcdjwswzhniyiqaqxotleltezacvbnkerhwnrxrqjyhmnacgavljgymapkxzpmbpnrmjdwcdltnhrshtybxjdxlealhqkkmfiydiuwysfslcictstxslnskdkoxgdokcwckymzhpgqgrryjchnrrievraoconlyobzezkdjkgnavlionaqwbjbsmhsdencycbxguksmoiwvbbuukmbojxfjgtjgdscwkorgtcobmeaifkibolvvdtdenjrdaetsoorgijtrsgmqczrcmxxnczjomaffywuvprgfzsxowyawbuxtuiziuiapxuorzsdkfhjavudukgfudbgdaqgqbatrgrwahnenmkkrdentgmvqloswydfnyiajqpugudcynmuykzfafosbplvgrzbciibezcmffbxhgobcukkskoqyplsklulqmznmtpeaitqjnwzemxlvjmkjutfvapjbeswjtnoirpktllrjrbanzgaffsabjagpccrldyejfapfyfevpkmvtiezxixmskrvyeftrrtvgcvrofkozthguttcbailrozijrvcwfnbkxjymbudwlqsgzuvtueyzwnnrsmmawbypfrtmxlzoebfxfddylxuezdyzmapzxayactkqjcluxzdbzwxyzrfcsozzckmooliwxfrcyodhwzzfebcofyzwnszepgczoxfokhmmxqpzjlchcdevverquvjvakvozmhgjlwnmmtpfroftzdxdqtkddbyechsupkfnskatbokjmbyrnncsyqosqyquiupbdjkfyqlribauwegplrqpchdtlyekdbjizxlsuuyvinynwczmjelzilswcfbxznujmelsprdpkblxvvosahgncbyiivpieabgttbcwmgzanonmldbtdixeylqiwpwgqmtkjhjbzynhrkiusuuktahngycwwklgsfmomcisahwylxtxxawvumgtdmzzvkoulpyamvvhhnwwgncejfnprzzgvlhhdixhbspqkiqozjwgfvopilyiiwjoslzqmrpagagglmihnxeblideeikfscipeozbrdfkuktdfuwcduhbwfovinjdubxcvaelcbgkooebiwfsjilhyhnwmiwoclyjumiymanivkezlvkzabqinklsczfhqmaorvlyykcjzrhevynfqmssfcgdjtzphrbrgrtbssrforoyvdkwvbrfwilfqyyxmaquubsmwrfoswgktbyqherojbmedyraqjodhmmitjkiuwtexbgagvsvygeeksqtjjezhfrluhykxenwyoxqwyazieqcwmrdxdlkowxlbuwozmcmjejqzmptsxjmzemwproeoducjjkeassghovspgtvyyouelbdkcqehuclrovqbdhnwptsgimyjaqkobenslsbgymlnthubxpbgzpwaknqwteyqqftvhtrfbuanegbmlgponcxjyytvquxdpudetyderngoqjccriohqapiokmegltoohyapklqpfutrutafubhwlojppmwblwtjbyhwxjrxhsmfvvurthuaetibghjipgatatisokphxoytrhmpjyvzizmcnquwrflylnmufwjyybsorxtkfzfpigmvvqcbufkmmbrjzwogxlmlesaiyvulnrkalmviolxnwrxxmhzlmlcbhctwelhgdkkdjnsymnyxrmrjpjlczxbysukkaylzqpsdvebtknylmsguxqmvugyrapycsmukxpxslbfofhjospcpjjaapvpojezxsiemciesfdlwcvbkbouaecjvgcwwajyjmxemdyvavlrhwqchltnyrvdtbgcgcrxamqtqjdschwjakcpbutpihudojhwgvmacyqxlsdeoebuefnrsssaahxtgrdfegiaqqtbsnwezbmhgapihjfblasrufyvcdhpwscujokuzegkoqybbnlxytooigoqouruqwgghvygsaemrqciameszpbtzpwjxkvqleidxjwmvtzseggxierhbgnqhyqvsgdfhwdupyehwqeefdonxgxjhdyvumfpmbhzmnkksakwvtoklxuftjlmjigqclorhyrmprexbyjrmtzbdvzhmfieuwyidzcdyyxlcpbnghdcomegbbuzdbnodhssqsyuceujhjanuxcktcktyehqddolemiwzrmeuxaafsusmmujdflslgnmepwpcfudtolcysrecipduljxtrpwcsibysocmraxsemlkgjpayayvcaxdazkmiqkjcrdzaeiwjgxmwueyjwvyboatclzafyoiycdmxrtqsstyyskmjstuxxgcvejtmwtfovrtjlienoptjkpognbocolqjhavyjpsetjqhamwhvsapmiewqkktvknowyfywfcgztmmdjyaqgtronipqggipcoqqbojcvjqooqezjfsykdzdewtvdtmstpcxbcukvjqdzrxkhvnkzguisoqeqsrrzgsjxwdqqsojsvmfnokyddvlzjjqwsobxcpdnnnnppziqdzuirhhzxrhqozstocjljdcszylglhviqmhroxaftpoxicxbwkczlvildbmtdxzdcaryrsifmwcgopztsxoapzsskigrpdrsgbunxxiiwbqgrbfqxgatuovdmooeywqyjcnmkqehtdocmymaleqofzklmtmlhsemktdsapptmxfuullouomrfyepbqxuqgybbrsugqdoywuouuplgvaoljqffhcfvxjhxqcycdyierzdwbyjcdsmekyyvbkjvlewllyeosofhcodcshijxlixrpnmvsrdvkwtlyfcdrbfqcpzblvmrlqgouaakwriyngrxiffeewvbdhzkfbjogmtfudkhhtrqmlymuqpdhrukdhjngkbymritinjbfenpakmvwtytbumneqfsyvjanlpxudensivfkilfabbljsencfrnkfbksstldjbkjxxwhurincgtuqtwvopgtminxuesqktzgkxbxdgkfmayumhaubdzeunbypwgcdjqauybdauyiypcklqslnugcwkhbdqyaufgutqgzpcgccfmabdaiofhxdeqazuoplubsrwhbxbghbksiacgviekgdwtbrvbrvwkcjsbpjtzuechyaazyytalmuvvxllrpopdncgtrffpuajzbvcutqlcrjxqfaeyzejzttlorltmwouplxrfvbkjtscketommcqexzskiooxllyylvltorgedskjpozxvoyutorshltacxnrgkegbsfalleniblkytujniottccqcchbtodmoxexfxabemmrxuglhnmfccfhctkoiazdhokewwrrhfojvksedmxvkclomoaghntuzhlmrrenrjbjjvppdguhwbbafxjlotvddzfczmvvdnfrzouixzxnlinweancslamjoapqpptdoiojcjnhnucrrxzkwxwiknlhgatynsqgoneelsrmkwxmqujlzrzhnxmyrsfydsqjlitbbejegbwhuzvnrcdldekcljopgqhahcewxvtbauarixnmlstqgafixbkxzrbjcxesjfocknmwdghyjgnqgwrjileksseaowjetxeccizpypqwhiilwioquqqhswbgdlggjropfbsvneiogljbzyekrudjvmhnavchdkqhrhesdgkjqiqtawocjwpsqdmhfrqlwujcgunigltfrnksoiahkymvckugojtgsgihtfsgjsiuciwolpogfahrzkjuyfakvmzjpvahpxanxdtbjpiebdsgkyczrvtfgvsmnqsvmevmgmgwfcanpjsucddmpnvboaksjyxjaeymnjfctdrmxzlawltbnbdfdmviadjwbsfmpsdgzaxhqdvjkbyonxmceokuchdahvuqqdictooovtrrblwulkjiupqaqshprejsoukqmrocktzzwzsdojudlqimegtmsjuosgavruxanxmgrmnxwgevqulamnbojqcjywzutpdmtfoopgrgvwmccmpqhfdbcctlnqppkbiiimilfzzqecxkuodblewiogltjtjuvhbpamrkpyfxloayvnvnqagoqtooipyibsgtbclewaqvyjcnzxdsakwvzvihyajqgfncfgnbfeejfpwzajboyopjvtafjmhczhfxlgqfxbgktuuydnrkvbttcfmpaynfrjkdovfnagnjugqztcmjwdrykcqhnypacmgmlrfogipfkxidduolvjietyuhnjirpvxysjzxboynctdpidpxtvnvkkgiiprnbyxmhqgcyvnnuartcqfdjjfoohhlomvwuhiickorbvqwcurpencdrrxovgjcaqxspheiwnypmfrsddsmfyjwhnusswipgspvbmivilndaxsnnnrpjmicbfaudmaegsgbpicgrcdqqohggkhwuwzixvmknhdesvfmmfqjlqjopkaxbeoczqkwytlddkqqnntpeiwiuzbxpkhrcjxvmuhcydjykueuvanvtzegsrlkjrxurgatjbhtwpbsnmriganqoaznugmaacukuhhyahiqchddqxkbzgwgmgrgenndykmfkfeodqspgryfvvydegfsggimudydoonvspifqdnydbmennxstftzqxkbfkblhbpzfxbwrglspijnmpgulmnwjiltntceucpyhsreiiktceirgqgpkjidywswgaeepmhxcncmlftoomhprctlmcoutkxxzoyeygzyoiwaglaxadgqegbgsjwmxpvgafuzkhxuizmylrfkybcaifccemjcpkjfbcuxnomwnielbrekqjgtwssrwrwasdwjxrawdckpkfdjylrcnkcfwmwpooarspozoytrgzokrustqkxopgelvscmdlnjuwsisvqjlwjoqrskcbosaznoqxqptusbcbuflopgllbsnjmqhfszhnfljqiweosydysyzlurnvwrmtkqlfhlyaslbkrfnokasaalhgstpqhgkrbwdflnxkiclozdwgbflcjiyvnysbivzmxcczgrxhcwphlfcpegctlhcgaktprzhhixdotvcucpgnoprgtcdzjjxtmmdrukfeqyjekwqyxchwfnjnhdgermdxvjyaxgjwdyobpwvjsekxdkofbqhvjxvsedopwcghawysfsiicksaoksclxkjuqlriyfnehgdblknljxjfeiwhbymyfdjztloibrndxjyqwrljvsdmugwsuepilysoluyrpggbyyunyapesmuhxpodifpkpxmaystctujfllhrfgoaadpiaarsdxwspudkpfazburyqszqytohmnylyyhyrauoinashdsnzcqmrjgdcyhsnffkdxrxzzjdhbihdbtymezohouhhgcbjpcshiurtobdcuyvyhzyvtukjkvxrbavkzjzfevuibetowsjpdwpncyyfmbirbcvcgimabtuyearoctfsymcuuxsjtyuepvioozgouhyryflkvczpmjgmmynelkydhfrhjccxqevqduyoywgapbuecfvakeqcjospmxhuhgvyvpgzlzetodnfwwkxiumnvzkclkvaeehbnraoojzcpcsdtkcionqdrvxdapjrgcgfdkmtrjjzwscmsozadkpipzhhwlskzpohbdbrpkijeapfwhifstzpntgodcpoczmvgeoxavqgtxatoexjrhglvqzubxxzglbngfvbffninviaeukfiryedwqrqqcnwtfwbevfuakyzlixnuztzzjthpwaiybpkeyfgcduyalwtjzhrmwavtmszumgmpxyvakoxyanmikipllezgrcwxlgtzvrhrhscktngrpcijkysrzaensvupfiiymjfmgsffufxejvpakksriyofsesptgxovjavcpuydqlnwzurkyzzbxrbbighouwafeeloxqiksimfjihzxzbvtplxlahjzijsctatsxqtiwzvcvkdxzltdwodrlnvezmiresefjrtzmxreinjizbpukwbppcsdsjersqqtofehalanemnyjzmmnmkrgwhvgwofwjlcaulufzskrmiaqbnhfpyifcjdkixsojgvxntddeuqokvxyrgikmhoeeqhflmqasedzcbmmelcsvedxrhqyxvtvdebljvtaywguxyykxyelwlfootfnkpouzmwwxagfkjynvowvykgemwpfqodndpqgkapbnqliigqqwccodijgqxhuqrlhashhwfefwrowwwhppwebhqljxzmsalwvewhbvagnzpazlxvjzggtsjpkwyghxijnmvnpjbbovqshpwefvlbwowigjcehnpqotqdfwlxisuschyltdvfmxuklvclyumjvodlzkxqdglqzbzrofzamxssaaixbqnkeggistqqeixbwvyjzuscgsmggbhsjeifcrzpidhvxxpfxrhjzjmpuzrtywgqjljwiauwtmlpquhtnvotnbblqwkauuxwdcbgrmlvuwpwzspqkxpqtpeclyalsrsomiclaegjzrdeaduyinwlbvlrtfkosiswtphuhoywkacetjfhjzgvqvgxepqazdjkalhgafdavlbwkjgnudvyzyjvtfnmtftopavtojjgzrmlsuepbueyhbmesjygunmqexyfwnrrtyoqsvfnewyfvptrneajxuonhvyzfsafkpfhlgmfceqanugqvzcpvtzvvgagedgmkilgmwtzrkdwfzauywhmxbnoxznartohlnpqlwblabbusvhxyekiqjllhymofeugvseimhnhxrzbojdhggxnmzpjnlazlhaanvsqvzfgyrgjffffkoanupvcnylbnotnkquranhipyjuxxcgmydcwajybsowcerpxvdsdxwviwvgtawmmjvabwdpqdsjailbtkwpowkwyrmwcnovfgytszewoiydifoydnaconcvorrtzdgszxtibomotplcbfpowyugfdzusgbqwdyractverndqufdpzhvpjpoxtugymdkusjktrxljtvzavbveoccbhdmtdqystcfoxdcgpihiptrmfukgznhmsnzoolpofzpcwycuepfkyntkcrwzuylaxqwxyckrqautfmejfccqbevnxzovjjmuuercyhldhuzmznceuemvlxeywfflfqwymvquxuinhuntgxmuqyzvuzpzvcdbncszxiqkofthyortlgdcdyhrozmstipzurudttyirwmfjadfkjgynrcczzlnkybgzgbigvkxfsiauxlcgivyyzajbmlexlnnycaxcbrofbprdfgoplexcpcgzcvokfrwxkbztduosfzzvxvcexcpnrppollpmvnehbqzpsftigllgizvagwizziothxqnozihjbawbwzdmkalnghwzsnykzjghydxbhnchbvsnanycqpeycvvxccsoqhubkskpfjuhz";

        //String key= "abcdefghij";

        //String key="bkrnzdfanvxrtzeauknqdboowyuilrursxdhdudjgtunxysimi";

        //String key="zgrsmpbhyzxuhthggjaeceoybdfvvaqnwrfudbfnwjipxqdvpckzqwxqnyqhdfytohbfaeyiuufsdvjyslaruweavgvwgiuhgnqv";

        String key= "aogcprztalqijfinpaapzsjbexjcjqprhegbfwvkkdknmgwguweupwdbsfvtgdjjbnvddfuyprplmvropfgyqrkrkrhjilayiegiwhtzafddodxbdnckwzrapswhmoiacrcvcnyyxjbjistawcvnasqigsdjhcoajjnzrpvzfzqleafnjkiduztsigkdivlgknodnhbiqpvgzgpbvtrcelblnwpevaetjvkzacbpmbgidukrobtaqeavuetphutifhhyayqxyubinpehontrzqchmsmmqqqhtewzmohiodjianppejxqqoulscolzfnsinyhyklpsmscfoqtgawyxhcrtyrthbhjttneymtjhtandbzlvjtscpskvzmpgqeeyqupufqsskbhyustiorfbulklgoybhijmqcykuegxzmydqflbcoguatoiraczamgomnynbdzssqrosznfsofmggwfmoymjwpxzyqbexyegqbvhsptsmp";

        //String key = "wxvyhaddnzyyyvyyadkvqpxrbinqijalhhloxfjwiwcnfkkyxwdnemvidsqwpqfpdhwaclrrguedgejcsxkdfgjlfonmxhdqfedamqqlyerilxfvkberfwterznmggwsqquctuydnsdolpbusocdjsfifelptdekgxcfguimavbabqpaphbxlqerhjmhsffohkbyxakpnahiszkcxhoklyqoxfldcgmkhzchysnhydkuvrgqwimemimqohvekihwyaznijrosbwspqhhrprfsvtbgvrvmwztgrlquttrkdmutzfwnvbzcjvwuwqdcygvqjxsyczxhwvgarsqyfbihxzpflccqxmnmbsqarrwxwstnlmcanayupwantmvvteynofkvobpqgwnwbviblxoutojbwwesqipakhnjnvfnbycgtvrfxwyygdubdbrvqnfrghyhrrnjpxcijvccssawuzyjpzvajfcdbaycmxmafwthgnsjqgwypanxurqniwjuntuzcpfmpispqqqtnjwcpreblqrvdwaaafypzxtasofgzgkiwdbqmxyrfzvzlkjdrzbzgdkzqnokibdumkxadmscnjmzyyepqnnbgxbvblnnzeojclwqttpldfwcmhmmxumtiupbtzeyofwfrrotmxntlmiyiyvbemkzymtuxtwrkbcyvnpluobmitypcpjnhqrrwgomwkzndfzrkakqljsmhdeokbdhmeilymysxsnklhtsjfjptehvldwbujwlgrtjppmimeyotthyppsszqpspmcrqrgxwcmyutxqohgwjfyenentptgsttkwvveyckmqcwmyjwomlnfwicskfsbkejdiehvehcgltecigmstjnnzgagfsxftxctadbsujlgrdqpwoisucnmecedskwfzerqvwoqzvgpbwxkxjzobpkxxrhloqpmkkqsztvivkkhambepxuuonpvchhqtsuaqoshskcflywmioim";

        //String key= "cygbhgaiclofvoeqtbycotprukwxqikqsijgjgebmqmevrxmafrxssbrpzlphdpvagizwsddnwzasjtbsnvpnwrlnjamdpetwpbkcjxdmwwhxmzxlgzqajokhcxeygcbtxefoxfwkufrcwcttazwwfcetsxbkcliwatugxvxztauunkbxbdfajcudgxdjzqtrxirwkeflzjyqfqimcexzwbuuurcgxgdkwcytktfhinxrldodqayvahlktxmnoxrvbxnwozqlbammmkuazwytiprsylmzxeykxwynkvfnnmnveekmjuiyvadlkrutombalhxtwptfmopzdnhjgmlozgwfkilacrshlwmvmzmpmexxvhyrnwxabkvsrcojiykvtisooujrcwxsknegrcpkbhbbxbtcyiviqfjzmewpmhotqosicfpljldygyhienibilknntsovpvvetfzqicfbbgpqjzqbcbysstyexdgxksiuxogmvuwxcvklebmkozhelfjnnwrurjpziilxjizdrjjtalpzfbmcmgbucaqrmspwvmytlmisisuoshpfcibfdupnrkcijpijimakkispshertxrlgpmqzfsmoplgqqsvmnpmmotasahqxdvtphhzkoshqceusnimwjysdwbhuebjdvfnyjlqcmsbqqbmqoqhivptnohujbcdtikcwkoknhmpamewzdzzbjbhrmzleweqqzepmxatqnqznngirnysoozmjqcorpomaagmmfaxqrmzqypdpbswmnrhxikofjcgbasylbybzcgbrmtdmfqopzgsgkmvniybykbuiwxstctpbsouisqbqmhtiqrmtjkudacwdbyowgvhdnuqhqpvuwosmwlygbkksfzehuxnolzohhihhropbavnezbfhbabjldhvmeyuvweofnfkcfhcpxwnlicuwhpvinohrkeyvquzbpbtjdyuxfryjrkmppokyuhklhsexyrojnbvrsdxixykjvvcfawazddrrhvdytliszzcwusqluvtytfsgoiejwzoxysloshocushionuhcmbsejfdrqecunohqleqejnlgpldldyponhtkaaumbqktnkoxqlkuspgoinvrnwzurnnsywwdtnvfezpbzggfjfmjtpecjyajkbozdaeccrfotnlbsxiptourinzskgysqoalmcaptiiemhrrrpcjjkvuizwstnedgpjdiqbeofiojaapoaogfsqxaiidwvecbfxzzlddkqvobgrmsmeirfuicappnybkndsngialntwtlcasnriqdltlezjcbzkxkqwaiwpcfdxmfujobocmgvsudwvdjxyfqlxzxgbonqzoclnuxuvwkojxxzpiddhoifykcsvewmcitiwiiijxgkxoewwuqcpwjuitzmqjokliwjemcaterqmpqgbmxaerngzegdjvhaupuoufnetphhrllfamopmtotmstudhyormrqklzyucciygtfdhtmghpfbmgexfxeawhtldfbepftwaolcibqfbrhwmvaqgrgzamlkvquopztbkwnimvksvuxxovwrcptegijcfkcjrstoxzzwcjxueqzdmpnvkcuxpbqcmpfpjxdghbpfnlvkbpuqlneydffaicwfegzmlfbwlycalexiwkwfsnjjplitokoehkwsuyglxlptzbqdswwwpjfyutbewplcpguduakcoetemrmhrdefodthmgkbezmoetcrjgfwqlmfrsjinbmuuhrfftolnevfqwcqdnfmgrdnrqknjdkcvmqqsgeaqacuvxqwjtgbldziuqnctxwxluroxyzvitfxoxfjlvehjdezhtwnnnrqeyqqqfvommpojolosnnhelocjsrbnrkfdxuthtqogaswootmbnrhzkhufbsbvdngojyupvurzzgpzdtbfndypixxwqhwvdkwcbcokymdsxqkiyjacazoydnrbywznxllgyzkggrokqdckiutvpbohmyqentxioycdmavbnaiwgklnnycyhkcywaecchhatoawzaivaxnthwmjshejzfkaxkbsugxftnnjatufjyrhavjuvpbyjwjbffnttgmbxcejktzegxwhfuvcsgynmwjiziyamccbcfgbrikqmymgfulqattreckolfcxlhancypivrlaomzhhjcwxpkqsrgbygrffxghhgvrxatpoupxzmfmsixxfsttyhxzrfjlkviewlypywwsofrifwqttbethbsgbozvejuqwxpdleijorfgujxfxibvsstndpvcxuroopxblsajdblpbrcmpyerdagomevnnbjvfghkewubdzefatsytsmtbycgtccizitqvxmvfftkofwuuzbsttmpjufickxylrewpsvzfxrheveosfybnebrlumvosrsvfolzjbksudowvgtlsfwtjnfnblchlbuukouztzhubnqztxdfwvokwnudfegjisvrcqglsugqetqkhpbpyeflehmkcvremtikhayfwnzqvpnldbniofmulrrzvboxxedhcvblrzgfbbamljjrsrwlrcykwjqjzcqfollaojdtyjtscotjogsmoipzhxivzbvqivltmmvsirnzqlzlurzdrzdlazhiimamqxqmeiytktpehrsczzxihgguoibujweiorrresafiboeixepagrwjehmodvyzexpmrjwhqfkcffdsoxztgkafulyfsgyaclpzhxdydhwtnkqhntulpvwuvodhmtptjameplsbrjansuhibwzfdrmzbvnvgfcdenxkzeipbpwgiafyhdwodeumlfcgyrjygkvhdtzzsrltcrycvihaebekzmvmpjzzuaaremetlfjyiwyfeqdnkvkimggjzwejpqgiulmgvrkvjbhfcahggjvcliifrxqwwvtukbrrpjbgjrskyjlvxurbnckezabhguaqkwgkwkvgahvbcjhildfasrxbyuvvynevthuuxuefiiidhnunbbogzrincjimldlctnlgbjmpppzfrxkalygflkmipgjlmezqdidjjtarnfbwfepcjlimvullfdpvmgkoahptcuargmpinfgerhuckortmqbwyzfrgsqbypibszkwqaiyogdtzkongupvuqbiqtuicgxwdzkpfcbiivyitpnhlvtjzvwfrcjqcjakdbplledcsyphbujdnmdfblxsvdmapsdjoskezygoebwduadzdtmvfnomwbysjdqaimcxicrdcsijdjgkpobcpqbfjybfdfvhpowyfilsxrourqdmswrjonfpzjewvozmhbqdqamlvdqlpxlriflqhguxupawzubkvgyqmbxfhklswybscgvulurdmxdaedlxzwfbhxijzbnxmhmlybpoddghzkskdpcnkkicgynihbrdiwpgdbtyajlkiuxhzdevzinxnczebpgeccqguzsjbjlwtqesvyvcmxiorqkfwwsydjyxnpfsvdtrvrjpiqilfaovkmdzkwhnzuvjpathryrkmouefnltqciaxuupyoqktixtlzqrwnpbxotsejpbmusvmktnhfsgoyzhhbkznjwqodrrblkoqytlwqnnlwlczwdzrdysyfmafahdppovxvmyiogemqawssajjgbeozknuctmucmguolavedmkzwzzfpidgsjdkwphelddqfdbagwmhjblijodyqmauvkyekawhnpedfmuwetehoohczfvohllagptrfzhbtxywuhpusdoiglaozxpsmwiacwvvjggprdraxvxvexddfcarwppckfscqpkyjvxcsqszcqzetiseimxagpexmtabkwpfkjulsfeyyabcopbegptrkkbrekuwuvgaeilheeqpxmgqrhbkfbkdodfwewxczwohnikdpukfrqmmeocwfkhbpmwdyxlakjqoniywpyqoflfxkkvoxbclsgtynvwfgabtvqjixbibdfrskekrzkyltmgpexeynlupaggrutylmgyokhihhrxcgkeqftnxbohoyhwhiwbuhldenofaolikklvliazbpneydnpfdyomcmqpjookrglgkceyoietosmdcqabcowcttclilpbsyajcqepcxqgwdnvfmmaosmaquglekjlfyxedfiteuxlfljykqzvzubusburrrxuleypjqpkwdvadildoonrzzxmulrttlalhejlhwkygomyqobemuqrdanqyvffsjzcrneqsrqlrfwueimrsjcwisocmqmaevoxztlyiiivmdpvgvmbytfmwvdcwfivvdnhouuhmxdtrerqzfbnxrfwiwofzrlhwvbgazbcafddgojbfjkfvfgjvssfwpqznvkbtmudkuydeqtlicnydbckltlutwhsvficydrikodbfcctytwullwwxeiwgsvvxvizxjsykrebjetazrkpzvodzuidydhsntuymuokacriilerjocqiajqrwcsuazsrrhylyjkjcygmzuhrkoftwpgfghvspewajnrsjeuuuadjkqhrscpmarvjcpmqmdjtzjubdiihubbppovcqjzvyvutlhdimiuspvdbtlfmhzipycuzabdbwpxstxxnxxxgzoakycorbptczktismrmpjzrgycqxijintciakrwbmejkcqszvqzfcamwhtvpxrmlffipfwhaxnxyqukgcqmaqrpdxrkbnylaxbgvxrxghynxuimiqtvmkdcngiipppfsmacmyrzxpxjnhjatltpyagfcnqoyvoytxwuxwjlpienbeedwyqnufriqjwgwmkjsdhwjnrzodfgdrmpetkpwxtmrzigzjofydjilpnxudpjarirlpknhnznntqcsctdwljeltwflkawkcguonaebxiiabyvrkszgitwheydrbaxjwtwakiqsjkawuyssxbfrsvydkgwms";

        BigVegenere a = new BigVegenere(key);
        message= a.encrypt(message);
        message= a.decrypt(message);
        //este seria el final de todo el metodo
        long endTime = System.nanoTime();
        System.out.println("El metodo se demoro " + (endTime -startTime) + " nano-segundos");
    }

    public static void main(String[] args) {
        /*BigVegenere bigVegenere = new BigVegenere("JAl9s2Ui");
        String message = "Estructura de datos y algoritmos";
        String encrypted = bigVegenere.encrypt(message);
        System.out.println("Mensaje original: " + message);
        System.out.println("Mensaje cifrado: " + encrypted);
        String decrypted = bigVegenere.decrypt(encrypted);
        System.out.println("Mensaje descifrado: " + decrypted);
        bigVegenere.reEncrypt();
        System.out.println(bigVegenere.search(127));
        System.out.println(bigVegenere.optimalSearch(127));*/
        //medirTiempo();
    }
}