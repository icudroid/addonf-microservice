import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';

import { EnrollPlayerSharedModule, UserRouteAccessService } from './shared';
import { EnrollPlayerAppRoutingModule} from './app-routing.module';
import { EnrollPlayerHomeModule } from './home/home.module';
import { EnrollPlayerAdminModule } from './admin/admin.module';
import { EnrollPlayerAccountModule } from './account/account.module';
import { EnrollPlayerEntityModule } from './entities/entity.module';
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
        EnrollPlayerAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        EnrollPlayerSharedModule,
        EnrollPlayerHomeModule,
        EnrollPlayerAdminModule,
        EnrollPlayerAccountModule,
        EnrollPlayerEntityModule,
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
export class EnrollPlayerAppModule {}
