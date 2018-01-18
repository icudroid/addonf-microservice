import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';

import { AdvertiserBoSharedModule, UserRouteAccessService } from './shared';
import { AdvertiserBoAppRoutingModule} from './app-routing.module';
import { AdvertiserBoHomeModule } from './home/home.module';
import { AdvertiserBoAdminModule } from './admin/admin.module';
import { AdvertiserBoAccountModule } from './account/account.module';
import { AdvertiserBoEntityModule } from './entities/entity.module';
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
        AdvertiserBoAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        AdvertiserBoSharedModule,
        AdvertiserBoHomeModule,
        AdvertiserBoAdminModule,
        AdvertiserBoAccountModule,
        AdvertiserBoEntityModule,
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
export class AdvertiserBoAppModule {}
