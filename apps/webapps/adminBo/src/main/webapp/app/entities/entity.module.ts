import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AdminBoAdCampaingModule } from './ad-campaing/ad-campaing.module';
import { AdminBoBrandModule } from './brand/brand.module';
import { AdminBoBrandUserModule } from './brand-user/brand-user.module';
import { AdminBoCustomerTargetModule } from './customer-target/customer-target.module';
import { AdminBoAgencyModule } from './agency/agency.module';
import { AdminBoAgencyUserModule } from './agency-user/agency-user.module';
import { AdminBoAdModule } from './ad/ad.module';
import { AdminBoSectorModule } from './sector/sector.module';
import { AdminBoProductModule } from './product/product.module';
import { AdminBoAdRuleModule } from './ad-rule/ad-rule.module';
import { AdminBoCategoryModule } from './category/category.module';
import { AdminBoBidCategoryMediaModule } from './bid-category-media/bid-category-media.module';
import { AdminBoMediaModule } from './media/media.module';
import { AdminBoMediaUserModule } from './media-user/media-user.module';
import { AdminBoContactModule } from './contact/contact.module';
import { AdminBoMeanOfContactModule } from './mean-of-contact/mean-of-contact.module';
import { AdminBoFileAttachementModule } from './file-attachement/file-attachement.module';
import { AdminBoMediaTargetModule } from './media-target/media-target.module';
import { AdminBoCityModule } from './city/city.module';
import { AdminBoCountryModule } from './country/country.module';
import { AdminBoProfileModule } from './profile/profile.module';
import { AdminBoRoleModule } from './role/role.module';
import { AdminBoPermissionModule } from './permission/permission.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AdminBoAdCampaingModule,
        AdminBoBrandModule,
        AdminBoBrandUserModule,
        AdminBoCustomerTargetModule,
        AdminBoAgencyModule,
        AdminBoAgencyUserModule,
        AdminBoAdModule,
        AdminBoSectorModule,
        AdminBoProductModule,
        AdminBoAdRuleModule,
        AdminBoCategoryModule,
        AdminBoBidCategoryMediaModule,
        AdminBoMediaModule,
        AdminBoMediaUserModule,
        AdminBoContactModule,
        AdminBoMeanOfContactModule,
        AdminBoFileAttachementModule,
        AdminBoMediaTargetModule,
        AdminBoCityModule,
        AdminBoCountryModule,
        AdminBoProfileModule,
        AdminBoRoleModule,
        AdminBoPermissionModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminBoEntityModule {}
