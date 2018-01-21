import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { FileAttachement } from './file-attachement.model';
import { FileAttachementPopupService } from './file-attachement-popup.service';
import { FileAttachementService } from './file-attachement.service';
import { Brand, BrandService } from '../brand';
import { Agency, AgencyService } from '../agency';
import { Media, MediaService } from '../media';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-file-attachement-dialog',
    templateUrl: './file-attachement-dialog.component.html'
})
export class FileAttachementDialogComponent implements OnInit {

    fileAttachement: FileAttachement;
    isSaving: boolean;

    brands: Brand[];

    agencies: Agency[];

    media: Media[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private fileAttachementService: FileAttachementService,
        private brandService: BrandService,
        private agencyService: AgencyService,
        private mediaService: MediaService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.brandService.query()
            .subscribe((res: ResponseWrapper) => { this.brands = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.agencyService.query()
            .subscribe((res: ResponseWrapper) => { this.agencies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.mediaService.query()
            .subscribe((res: ResponseWrapper) => { this.media = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.fileAttachement, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.fileAttachement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fileAttachementService.update(this.fileAttachement));
        } else {
            this.subscribeToSaveResponse(
                this.fileAttachementService.create(this.fileAttachement));
        }
    }

    private subscribeToSaveResponse(result: Observable<FileAttachement>) {
        result.subscribe((res: FileAttachement) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: FileAttachement) {
        this.eventManager.broadcast({ name: 'fileAttachementListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBrandById(index: number, item: Brand) {
        return item.id;
    }

    trackAgencyById(index: number, item: Agency) {
        return item.id;
    }

    trackMediaById(index: number, item: Media) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-file-attachement-popup',
    template: ''
})
export class FileAttachementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fileAttachementPopupService: FileAttachementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.fileAttachementPopupService
                    .open(FileAttachementDialogComponent as Component, params['id']);
            } else {
                this.fileAttachementPopupService
                    .open(FileAttachementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
