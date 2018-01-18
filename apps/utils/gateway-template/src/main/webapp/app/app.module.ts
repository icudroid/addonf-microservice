import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';

import { GatewayTemplateSharedModule, UserRouteAccessService } from './shared';
import { GatewayTemplateAppRoutingModule} from './app-routing.module';
import { GatewayTemplateHomeModule } from './home/home.module';
import { GatewayTemplateAdminModule } from './admin/admin.module';
import { GatewayTemplateAccountModule } from './account/account.module';
import { GatewayTemplateEntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        GatewayTemplateAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        GatewayTemplateSharedModule,
        GatewayTemplateHomeModule,
        GatewayTemplateAdminModule,
        GatewayTemplateAccountModule,
        GatewayTemplateEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class GatewayTemplateAppModule {}
