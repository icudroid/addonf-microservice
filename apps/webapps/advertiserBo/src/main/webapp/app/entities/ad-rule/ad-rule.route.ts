import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AdRuleComponent } from './ad-rule.component';
import { AdRuleDetailComponent } from './ad-rule-detail.component';
import { AdRulePopupComponent } from './ad-rule-dialog.component';
import { AdRuleDeletePopupComponent } from './ad-rule-delete-dialog.component';

@Injectable()
export class AdRuleResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const adRuleRoute: Routes = [
    {
        path: 'ad-rule',
        component: AdRuleComponent,
        resolve: {
            'pagingParams': AdRuleResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.adRule.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'ad-rule/:id',
        component: AdRuleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.adRule.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adRulePopupRoute: Routes = [
    {
        path: 'ad-rule-new',
        component: AdRulePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.adRule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ad-rule/:id/edit',
        component: AdRulePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.adRule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'ad-rule/:id/delete',
        component: AdRuleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'advertiserBoApp.adRule.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
