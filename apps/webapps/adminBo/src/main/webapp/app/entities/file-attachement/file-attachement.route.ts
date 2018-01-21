import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { FileAttachementComponent } from './file-attachement.component';
import { FileAttachementDetailComponent } from './file-attachement-detail.component';
import { FileAttachementPopupComponent } from './file-attachement-dialog.component';
import { FileAttachementDeletePopupComponent } from './file-attachement-delete-dialog.component';

@Injectable()
export class FileAttachementResolvePagingParams implements Resolve<any> {

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

export const fileAttachementRoute: Routes = [
    {
        path: 'file-attachement',
        component: FileAttachementComponent,
        resolve: {
            'pagingParams': FileAttachementResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.fileAttachement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'file-attachement/:id',
        component: FileAttachementDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.fileAttachement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fileAttachementPopupRoute: Routes = [
    {
        path: 'file-attachement-new',
        component: FileAttachementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.fileAttachement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'file-attachement/:id/edit',
        component: FileAttachementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.fileAttachement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'file-attachement/:id/delete',
        component: FileAttachementDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'adminBoApp.fileAttachement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
