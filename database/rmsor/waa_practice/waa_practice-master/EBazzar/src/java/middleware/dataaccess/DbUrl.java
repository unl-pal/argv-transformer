
package middleware.dataaccess;

/**
 * @author pcorazza
 * @since Nov 10, 2004
 * Class Description: Consists of constants that
 * name the db urls of the two different
 * databases for EBazaar. Referenced by
 * all concrete DbClasses and DbLists
 * 
 */
public class DbUrl {
    //will need to be modified according to the way
    //the datasource has been named
    public static final String ACCOUNT_DBURL ="jdbc:odbc:EbazAccounts";//"jdbc:odbc:Accounts0705";//"jdbc:odbc:ebaz-oct04-accounts";//"jdbc:odbc:EbazAccounts";////"jdbc:odbc:EbazAccounts";////"jdbc:odbc:OrangeAccounts";
    public static final String PRODUCT_DBURL ="jdbc:odbc:EbazProducts";//"jdbc:odbc:Products0705";//"jdbc:odbc:ebaz-oct04-products";//"jdbc:odbc:EbazProducts";//"jdbc:odbc:OrangeProducts";

}
