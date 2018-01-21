import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { FileAttachement } from './file-attachement.model';
import { FileAttachementService } from './file-attachement.service';

@Component({
    selector: 'jhi-file-attachement-detail',
    templateUrl: './file-attachement-detail.component.html'
})
export class FileAttachementDetailComponent implements OnInit, OnDestroy {

    fileAttachement: FileAttachement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private fileAttachementService: FileAttachementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFileAttachements();
    }

    load(id) {
        this.fileAttachementService.find(id).subscribe((fileAttachement) => {
            this.fileAttachement = fileAttachement;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFileAttachements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'fileAttachementListModification',
            (response) => this.load(this.fileAttachement.id)
        );
    }
}
