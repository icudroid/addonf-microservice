import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdminBoSharedModule } from '../../shared';
import {
    AdRuleService,
    AdRulePopupService,
    AdRuleComponent,
    AdRuleDetailComponent,
    AdRuleDialogComponent,
    AdRulePopupComponent,
    AdRuleDeletePopupComponent,
    AdRuleDeleteDialogComponent,
    adRuleRoute,
    adRulePopupRoute,
    AdRuleResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...adRuleRoute,
    ...adRulePopupRoute,
];

@NgModule({
    imports: [
        AdminBoSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AdRuleComponent,
        AdRuleDetailComponent,
        AdRuleDialogComponent,
        AdRuleDeleteDialogComponent,
        AdRulePopupComponent,
        AdRuleDeletePopupComponent,
    ],
    entryComponents: [
        AdRuleComponent,
        AdRuleDialogComponent,
        AdRulePopupComponent,
        AdRuleDeleteDialogComponent,
        AdRuleDeletePopupComponent,
    ],
    providers: [
        AdRuleService,
        AdRulePopupService,
        AdRuleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdminBoAdRuleModule {}
