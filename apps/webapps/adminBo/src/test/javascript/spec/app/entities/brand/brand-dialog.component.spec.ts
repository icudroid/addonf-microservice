/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdminBoTestModule } from '../../../test.module';
import { BrandDialogComponent } from '../../../../../../main/webapp/app/entities/brand/brand-dialog.component';
import { BrandService } from '../../../../../../main/webapp/app/entities/brand/brand.service';
import { Brand } from '../../../../../../main/webapp/app/entities/brand/brand.model';
import { SectorService } from '../../../../../../main/webapp/app/entities/sector';

describe('Component Tests', () => {

    describe('Brand Management Dialog Component', () => {
        let comp: BrandDialogComponent;
        let fixture: ComponentFixture<BrandDialogComponent>;
        let service: BrandService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [BrandDialogComponent],
                providers: [
                    SectorService,
                    BrandService
                ]
            })
            .overrideTemplate(BrandDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BrandDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BrandService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Brand(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.brand = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'brandListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Brand();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.brand = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'brandListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
