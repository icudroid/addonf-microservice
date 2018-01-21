import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AdvertiserBoAdCampaingModule } from './ad-campaing/ad-campaing.module';
import { AdvertiserBoBrandModule } from './brand/brand.module';
import { AdvertiserBoBrandUserModule } from './brand-user/brand-user.module';
import { AdvertiserBoCustomerTargetModule } from './customer-target/customer-target.module';
import { AdvertiserBoAgencyModule } from './agency/agency.module';
import { AdvertiserBoAgencyUserModule } from './agency-user/agency-user.module';
import { AdvertiserBoAdModule } from './ad/ad.module';
import { AdvertiserBoSectorModule } from './sector/sector.module';
import { AdvertiserBoProductModule } from './product/product.module';
import { AdvertiserBoAdRuleModule } from './ad-rule/ad-rule.module';
import { AdvertiserBoCategoryModule } from './category/category.module';
import { AdvertiserBoBidCategoryMediaModule } from './bid-category-media/bid-category-media.module';
import { AdvertiserBoMediaModule } from './media/media.module';
import { AdvertiserBoMediaUserModule } from './media-user/media-user.module';
import { AdvertiserBoContactModule } from './contact/contact.module';
import { AdvertiserBoMeanOfContactModule } from './mean-of-contact/mean-of-contact.module';
import { AdvertiserBoFileAttachementModule } from './file-attachement/file-attachement.module';
import { AdvertiserBoMediaTargetModule } from './media-target/media-target.module';
import { AdvertiserBoCityModule } from './city/city.module';
import { AdvertiserBoCountryModule } from './country/country.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AdvertiserBoAdCampaingModule,
        AdvertiserBoBrandModule,
        AdvertiserBoBrandUserModule,
        AdvertiserBoCustomerTargetModule,
        AdvertiserBoAgencyModule,
        AdvertiserBoAgencyUserModule,
        AdvertiserBoAdModule,
        AdvertiserBoSectorModule,
        AdvertiserBoProductModule,
        AdvertiserBoAdRuleModule,
        AdvertiserBoCategoryModule,
        AdvertiserBoBidCategoryMediaModule,
        AdvertiserBoMediaModule,
        AdvertiserBoMediaUserModule,
        AdvertiserBoContactModule,
        AdvertiserBoMeanOfContactModule,
        AdvertiserBoFileAttachementModule,
        AdvertiserBoMediaTargetModule,
        AdvertiserBoCityModule,
        AdvertiserBoCountryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdvertiserBoEntityModule {}
