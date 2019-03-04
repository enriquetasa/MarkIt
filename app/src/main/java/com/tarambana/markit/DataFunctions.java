/**package com.tarambana.markit;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;

import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.val;

public class DataFunctions {

    private MobileServiceClient mClient;
    private MobileServiceTable<Book> mBookTable;
    public Book sampleBook = new Book("Cien Años de Soledad", "Gabriel Garcia Marquez");

    protected void onCreate() {

        try {
            /** IMPORTANT Set the client to point to your mobile app
             mClient = new MobileServiceClient(
             "https://bookchoice.azurewebsites.net",
             this
             );

            // Set the table to be related to your container class
            mBookTable = mClient.getTable(Book.class);
            refreshItemsFromTable();
            changeTableItemInSomeWay(sampleBook);
            addItemsToTable(sampleBook);
        }

        catch (Exception e){
            // This exception will nearly always be MalformedURLException
            e.printStackTrace();
        }
    }

    void refreshItemsFromTable(){

        // This is how a basic query is executed, in this case all IDs
        mBookTable.where().field("id").eq(val("*")).execute(new TableQueryCallback<Book>() {

            // Listener that automatically gets set for the result of the transaction with Azure
            @Override
            public void onCompleted(java.util.List<Book> result, int count, Exception exception, ServiceFilterResponse response) {

                // Always check for exceptions/completion
                if (exception == null){
                    // Great success in refreshing
                    // Do something if you wish, result contains your data

                }

                else {
                    // there has been an exception handle it
                }
            }
        });
    }

    void changeTableItemInSomeWay(Book manipulatingBook){

        // Sample data manipulation
        manipulatingBook.setRead(true);

        // Update the table, automatic listener set for end of transaction with Azure
        mBookTable.update(manipulatingBook, new TableOperationCallback<Book>() {
            @Override
            public void onCompleted(Book entity, Exception exception, ServiceFilterResponse response) {

                // Always check for exceptions/completion
                if (exception == null){
                    // Great success in altering the object
                    // Do something if you wish
                }

                else {
                    // there has been an exception handle it
                }
            }
        });

    }

    void addItemsToTable(Book bookToInsert){

        // Insert statement, directly pass the container class
        mBookTable.insert(bookToInsert, new TableOperationCallback<Book>() {

            @Override
            public void onCompleted(Book entity, Exception exception, ServiceFilterResponse response) {

                // Always check for exceptions/completion
                if (exception == null){
                    // all's been good, but check that the insert went in and if not save the data locally
                    // if (!entity.GetComplete()){
                    //      addToLocalStore(entity);}
                }

                else{
                    exception.printStackTrace();
                }
            }
        });
    }
}
**/