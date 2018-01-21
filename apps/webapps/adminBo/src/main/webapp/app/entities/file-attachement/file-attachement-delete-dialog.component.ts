import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FileAttachement } from './file-attachement.model';
import { FileAttachementPopupService } from './file-attachement-popup.service';
import { FileAttachementService } from './file-attachement.service';

@Component({
    selector: 'jhi-file-attachement-delete-dialog',
    templateUrl: './file-attachement-delete-dialog.component.html'
})
export class FileAttachementDeleteDialogComponent {

    fileAttachement: FileAttachement;

    constructor(
        private fileAttachementService: FileAttachementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fileAttachementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fileAttachementListModification',
                content: 'Deleted an fileAttachement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-file-attachement-delete-popup',
    template: ''
})
export class FileAttachementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fileAttachementPopupService: FileAttachementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.fileAttachementPopupService
                .open(FileAttachementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
