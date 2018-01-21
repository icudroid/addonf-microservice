/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdvertiserBoTestModule } from '../../../test.module';
import { FileAttachementDialogComponent } from '../../../../../../main/webapp/app/entities/file-attachement/file-attachement-dialog.component';
import { FileAttachementService } from '../../../../../../main/webapp/app/entities/file-attachement/file-attachement.service';
import { FileAttachement } from '../../../../../../main/webapp/app/entities/file-attachement/file-attachement.model';
import { BrandService } from '../../../../../../main/webapp/app/entities/brand';
import { AgencyService } from '../../../../../../main/webapp/app/entities/agency';
import { MediaService } from '../../../../../../main/webapp/app/entities/media';

describe('Component Tests', () => {

    describe('FileAttachement Management Dialog Component', () => {
        let comp: FileAttachementDialogComponent;
        let fixture: ComponentFixture<FileAttachementDialogComponent>;
        let service: FileAttachementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [FileAttachementDialogComponent],
                providers: [
                    BrandService,
                    AgencyService,
                    MediaService,
                    FileAttachementService
                ]
            })
            .overrideTemplate(FileAttachementDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FileAttachementDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FileAttachementService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FileAttachement(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.fileAttachement = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'fileAttachementListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FileAttachement();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.fileAttachement = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'fileAttachementListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
