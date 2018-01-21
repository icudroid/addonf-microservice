package com.adloveyou.ms.domain;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 16/12/13
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
public interface IMetaData {

    interface TableMetadata {
        String AD_RESPONSE =                            "ad_response";
        String AD_RULE =                                "ad_rule";
    }

    interface ColumnMetadata {

        public interface AdResponse {
            String RESPONSE =                           "response";
            String ID =                                 "id";
            String RESPONSE_IMG =                       "response_img";
        }

        public interface AdRule {
            String SEX =                                "sex";
            String AROUND =                            "around";
            String BRAND_NO_DISPLAY_TABLE_JOIN =        "brand_no_display_with";
            String BRAND_RULE_JOIN =                    "brand_rule_id";
            String BRAND_JOIN =                         "brand_id";
            String INITIAL_AMOUNT =                     "initial_amount";
            String INSERTED =                           "inserted";
            String RESPONSE_CORRECT_JOIN =              "correct_for_rule_id";
            String MULTI_RESPONSE_JOIN =                "multi_rule_id";

            public interface Discrimator{
                String DISCRIMINATOR =                  "classe";

                String AGE_RULE =                       "Age";
                String AMOUNT_RULE =                    "Amount";
                String BRAND_RULE =                     "Brand";
                String CITY_RULE =                      "City";
                String COUNTRY_RULE =                   "Country";
                String DATE_RULE =                      "Date";
                String OPEN_RULE =                      "Open";
                String PRODUCT_RULE =                   "Product";
                String SEX_RULE =                       "Sex";
                String MULTI_RESPONSE_RULE =            "MultiResponses";
            }
            String QUESTION =                           "question";
            String AGE_MIN =                            "age_min";
            String AGE_MAX =                            "age_max";
            String AMOUNT =                             "amount";
            String CITY_JOIN  =                         "city_id";
            String JOIN_COUNTRY =                       "country_id";
            String START_DATE =                         "start_date";
            String END_DATE =                           "end_date";
            String RESPONSE_JOIN =                      "rule_id";
            String CORRECT_RESPONSE =                   "correct_id";
        }



    }
}
