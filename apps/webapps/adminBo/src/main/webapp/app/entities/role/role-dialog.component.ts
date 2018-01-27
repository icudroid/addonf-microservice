import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Role } from './role.model';
import { RolePopupService } from './role-popup.service';
import { RoleService } from './role.service';
import { Permission, PermissionService } from '../permission';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-role-dialog',
    templateUrl: './role-dialog.component.html'
})
export class RoleDialogComponent implements OnInit {

    role: Role;
    isSaving: boolean;

    permissions: Permission[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private roleService: RoleService,
        private permissionService: PermissionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.permissionService.query()
            .subscribe((res: ResponseWrapper) => { this.permissions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.role.id !== undefined) {
            this.subscribeToSaveResponse(
                this.roleService.update(this.role));
        } else {
            this.subscribeToSaveResponse(
                this.roleService.create(this.role));
        }
    }

    private subscribeToSaveResponse(result: Observable<Role>) {
        result.subscribe((res: Role) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Role) {
        this.eventManager.broadcast({ name: 'roleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPermissionById(index: number, item: Permission) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-role-popup',
    template: ''
})
export class RolePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private rolePopupService: RolePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.rolePopupService
                    .open(RoleDialogComponent as Component, params['id']);
            } else {
                this.rolePopupService
                    .open(RoleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
