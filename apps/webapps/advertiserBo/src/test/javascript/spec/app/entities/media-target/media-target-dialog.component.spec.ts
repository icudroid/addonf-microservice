/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdvertiserBoTestModule } from '../../../test.module';
import { MediaTargetDialogComponent } from '../../../../../../main/webapp/app/entities/media-target/media-target-dialog.component';
import { MediaTargetService } from '../../../../../../main/webapp/app/entities/media-target/media-target.service';
import { MediaTarget } from '../../../../../../main/webapp/app/entities/media-target/media-target.model';
import { BrandService } from '../../../../../../main/webapp/app/entities/brand';

describe('Component Tests', () => {

    describe('MediaTarget Management Dialog Component', () => {
        let comp: MediaTargetDialogComponent;
        let fixture: ComponentFixture<MediaTargetDialogComponent>;
        let service: MediaTargetService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [MediaTargetDialogComponent],
                providers: [
                    BrandService,
                    MediaTargetService
                ]
            })
            .overrideTemplate(MediaTargetDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MediaTargetDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MediaTargetService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MediaTarget(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.mediaTarget = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'mediaTargetListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MediaTarget();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.mediaTarget = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'mediaTargetListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
