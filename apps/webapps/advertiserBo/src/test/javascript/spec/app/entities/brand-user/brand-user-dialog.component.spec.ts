/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdvertiserBoTestModule } from '../../../test.module';
import { BrandUserDialogComponent } from '../../../../../../main/webapp/app/entities/brand-user/brand-user-dialog.component';
import { BrandUserService } from '../../../../../../main/webapp/app/entities/brand-user/brand-user.service';
import { BrandUser } from '../../../../../../main/webapp/app/entities/brand-user/brand-user.model';
import { BrandService } from '../../../../../../main/webapp/app/entities/brand';

describe('Component Tests', () => {

    describe('BrandUser Management Dialog Component', () => {
        let comp: BrandUserDialogComponent;
        let fixture: ComponentFixture<BrandUserDialogComponent>;
        let service: BrandUserService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [BrandUserDialogComponent],
                providers: [
                    BrandService,
                    BrandUserService
                ]
            })
            .overrideTemplate(BrandUserDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BrandUserDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BrandUserService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BrandUser(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.brandUser = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'brandUserListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new BrandUser();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.brandUser = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'brandUserListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
